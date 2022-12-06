class Dec03 {


    fun sumOfPriorities(rawRucksacks: String): Int {

        return rawRucksacks
            .split(System.lineSeparator())
            .map { rucksack -> Rucksack(rucksack) }
            .filter { it.commonItem() != null }
            .sumOf { it.priority() }


    }

    fun sumOfBadgePriorities(rawRucksacks: String): Int {
        val elves = rawRucksacks.split(System.lineSeparator()).map { Rucksack(it) }
        val groupSize = 3

        return (elves.indices step groupSize).sumOf {
            Group(elves.subList(it, it + groupSize))
                .badge()
                .priority()
        }

    }

    class Group(
        val elves: List<Rucksack>
    ) {

        init {
            if (elves.size != 3) {
                throw RuntimeException("Wrong amount of elves (${elves.size})")
            }

        }

        /**
         * The one item type that is common between all three Elves in each group.
         */
        fun badge(): Item {

            val firstRucksack = elves.first()
            val otherRucksacks = elves.slice(1 until elves.size)

            return firstRucksack.rucksackRaw
                .filter { item ->
                    otherRucksacks.none { rucksack ->
                        !rucksack.rucksackRaw.contains(item)
                    }
                }
                .first().let(::Item)
        }

    }

    class Rucksack(val rucksackRaw: String) {

        init {
            println("Rucksack[$rucksackRaw]")
            println(" -#1 ${compartment1}")
            println(" -#2 ${compartment2}")
            println(" common item/priority: ${commonItem()}/${priority()}")
        }

        private val compartment1 get() = rucksackRaw.substring(0, rucksackRaw.length / 2)
        private val compartment2 get() = rucksackRaw.substring(rucksackRaw.length / 2)

        fun commonItem(): Char? = compartment1.find { compartment2.contains(it) }

        fun priority(): Int = commonItem()
            ?.let {
                return Item(it).priority()
            }
            ?: 0
    }

    class Item(private val value: Char) {
        fun priority(): Int = value
            .code
            .let {
                // Lowercase, priority 1-26
                if (it >= 97) {
                    return (it - 96)
                }
                // Uppercase, prioriti 27-
                return (it - 65 + 27)
            }
    }


}
