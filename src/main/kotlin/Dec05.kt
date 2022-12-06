import java.lang.System.lineSeparator

class Dec05(val stacksAndCommands: String) {

    val emptyLine = lineSeparator() + lineSeparator()

    fun parseStacks() = stacksAndCommands
        .substringBefore(emptyLine)
        .split(lineSeparator())
        .reversed()
        .map { row ->
            (row.indices step 4)
                .map { row.substring(it, it + 3).trim() }
        }
        .transpose()
        .map {
            Stack(
                it[0].trim().toInt(),
                it.subList(1, it.size).filter(String::isNotEmpty)
            )
        }

    fun parseMoveInstructions(): List<MoveInstruction> = stacksAndCommands
        .substringAfter(emptyLine)
        .split(lineSeparator())
        .map { textualMoveInstruction ->
            textualMoveInstruction
                .split(" ")
                .filterIndexed { idx, v ->
                    when (idx) {
                        1, 3, 5 -> true
                        else -> false
                    }
                }
                .map(String::toInt)
                .let { MoveInstruction(it[0], it[1], it[2]) }
        }

    fun topCratesAfterRearrange(crane: Crane): String {
        val stacks = parseStacks()
        parseMoveInstructions()
            .forEachIndexed { idx, it ->
                print("[$idx] ")
                it.execute(stacks, crane)
            }

        return stacks.joinToString(separator = "", transform = Stack::topCrate)
    }

    interface Crane {
        fun move(count: Int, from: Stack, to: Stack)
    }

    class SingleCrateCrane : Crane {
        override fun move(numberOfCrates: Int, sourceStack: Stack, targetStack: Stack) {
            for (n in 1..numberOfCrates) {
                sourceStack.liftCrate().also {
                    targetStack.lowerCrate(it)
                }
            }
        }
    }

    class MultiCrateCrane : Crane {
        override fun move(numberOfCrates: Int, sourceStack: Stack, targetStack: Stack) {
            sourceStack.liftCrates(numberOfCrates).also {
                targetStack.lowerCrates(it)
            }
        }
    }

    class MoveInstruction(
        val numberOfCrates: Int,
        val fromStackId: Int,
        val toStackId: Int
    ) {
        fun execute(stacks: List<Stack>, crane: Crane) {
            val from = stacks.find { it.id == fromStackId }!!
            val to = stacks.find { it.id == toStackId }!!
            println("${crane.javaClass.simpleName} Move $numberOfCrates crates from $fromStackId to $toStackId")
            println("FROM: $from TO: $to")
            crane.move(numberOfCrates, from, to)
            println("FROM: $from TO: $to")
        }
    }


    class Stack(val id: Int, crates: List<String>) {
        private val crates = crates.toMutableList()

        fun topCrate() = crates.last().substring(1, 2)

        fun liftCrate() = crates.removeLast()

        fun liftCrates(count: Int): List<String> {
            val popped = crates
                .reversed()
                .subList(0, count)
                .reversed()
            for(n in 0 until count)
                crates.removeLast()
            return popped
        }

        fun lowerCrate(singleCrate: String) = crates.add(singleCrate)
        fun lowerCrates(multipleCrates: List<String>) = crates.addAll(multipleCrates)
        val size: Int get() = crates.size

        override fun toString(): String = "Stack[@${id}, ${crates.size}:" + crates.joinToString("") + "]"

    }

    /**
     *     1   2   3
     *    [Z] [M] [P]
     *    [N] [C]
     *    [D]
     *
     *      |
     *      |
     *      v
     *
     *     1 [Z] [N] [D]
     *     2 [M] [C]
     *     3 [P]
     */
    fun <T> List<List<T>>.transpose(): List<List<T>> {
        return mutableListOf<MutableList<T>>()
            .also { newRows ->
                forEachIndexed { rowIdx, row ->
                    row.forEachIndexed { colIdx, value ->
                        if (rowIdx == 0) {
                            // add a (target) row for each column
                            newRows.add(mutableListOf())
                        }
                        // Add the col values to the correct row
                        newRows[colIdx].add(value)
                    }
                }
            }
            .toList()
    }


}