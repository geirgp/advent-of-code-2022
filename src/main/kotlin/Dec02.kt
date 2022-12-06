class Dec02 {


    fun totalScoreActual(handStrategy: String) =
        handStrategy
            .split(System.lineSeparator())
            .sumOf {
                FixedHandRound(
                    opponentHand = Hand.fromFirstHandCode(it[0]),
                    playHand = Hand.fromSecondHandCode(it[2])
                )
                    .points()
            }

    fun totalScoreDesired(handStrategy: String) =
        handStrategy
            .split(System.lineSeparator())
            .sumOf {
                FixedOutcomeRound(
                    opponentHand = Hand.fromFirstHandCode(it[0]),
                    desiredOutcome = Outcome.fromCode(it[2])
                )
                    .also {
                        //println("${it.opponentHand} ${it.desiredOutcome} + ${it.desiredOutcome.needsHand(it.opponentHand)} ${it.points()}")
                    }
                    .points()
            }


    private class FixedOutcomeRound(val opponentHand: Hand, val desiredOutcome: Outcome) {
        fun points() = desiredOutcome.points +
                desiredOutcome.needsHand(opponentHand).score
    }

    private class FixedHandRound(val opponentHand: Hand, val playHand: Hand) {
        fun points() = playHand.score + outcome().points

        fun outcome() =
            if (opponentHand == playHand) {
                Outcome.Draw
            } else if (playHand.defeats(opponentHand)) {
                Outcome.Win
            } else {
                Outcome.Loss
            }
    }

    enum class Hand(val firstCol: Char, val secondCol: Char, val score: Int) {
        Rock('A', 'X', 1),
        Paper('B', 'Y', 2),
        Scissor('C', 'Z', 3);

        companion object {
            val beatsMatrix = mapOf<Hand, Hand>(
                Rock to Scissor,
                Scissor to Paper,
                Paper to Rock
            )

            fun fromFirstHandCode(code: Char) = Hand.values().first { it.firstCol == code }
            fun fromSecondHandCode(code: Char) = Hand.values().first { it.secondCol == code }

            fun defeats(otherHand: Hand): Hand {
                return values().first { it.defeats(otherHand) }
            }

            fun losesTo(otherHand: Hand): Hand {
                return values().first { otherHand.defeats(it) }
            }
        }

        fun defeats(otherHand: Hand): Boolean =
            beatsMatrix[this] == otherHand
    }

    enum class Outcome(val points: Int, val code: Char) {
        Win(6, 'Z'),
        Draw(3, 'Y'),
        Loss(0, 'X');

        companion object {
            fun fromCode(code: Char): Outcome =
                values().firstOrNull { it.code == code }!!
        }

        fun needsHand(otherHand: Hand): Hand = when (this) {
            Draw -> otherHand
            Win -> Hand.defeats(otherHand)
            Loss -> Hand.losesTo(otherHand)
        }
    }
}