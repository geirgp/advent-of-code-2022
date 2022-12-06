import Dec04.Range.Companion.toRange

class Dec04(assignmentPairsRaw: String) {

    private val assignmentPairs = assignmentPairsRaw
        .split(System.lineSeparator())
        .map {
            AssignmentPair(
                it.substringBefore(",").toRange(),
                it.substringAfter(",").toRange()
            )
        }


    fun pairsFullOverlap(): List<AssignmentPair> = assignmentPairs
            .filter(AssignmentPair::fullOverlap)

    fun pairsPartialOverlap(): List<AssignmentPair> = assignmentPairs
        .filter(AssignmentPair::partialOverlap)

    data class Range(val from: Int, val to: Int) {
        fun containsAll(other: Range) =
            from <= other.from
                    && to >= other.to

        fun containsSome(other: Range): Boolean {
            val b = (from <= other.from
                    && to >= other.from)
                    || (
                    to >= other.from
                            && to <= other.to
                            )
            println("Overlap($b) $this | $other")
            return b
        }

        companion object {
            fun String.toRange() =
                Range(
                    this.substringBefore("-").toInt(),
                    this.substringAfter("-").toInt(),
                )

        }
    }

    class AssignmentPair(val rangeOne: Range, val rangeTwo: Range) {
        fun fullOverlap() = rangeOne.containsAll(rangeTwo) || rangeTwo.containsAll(rangeOne)

        fun partialOverlap() = rangeOne.containsSome(rangeTwo) || rangeTwo.containsSome((rangeOne))
    }
}