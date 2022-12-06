class Dec06() {

    /**
     * Locates start-of-packet marker in [datastream] and returns the position of where the packet starts.
     */
    fun startOfPacketIndex(datastream: String): Int =
        datastream.endOfDistinctMarkerIndex(4)

    /**
     * Locates start-of-message marker in [datastream] and returns the position of where the message starts.
     */
    fun startOfMessageIndex(datastream: String): Int =
        datastream.endOfDistinctMarkerIndex(14)

    private fun String.endOfDistinctMarkerIndex(markerLength: Int): Int {
        for (i in 0 until length - markerLength) {
            if (distinctCharacterSequenceAt(i, markerLength))
                return i + markerLength
        }
        throw RuntimeException("marker not found")
    }

    /**
     * Returns true if the substring starting at [index] with a length of [sequenceLength] contains all unique characters.
     */
    private fun String.distinctCharacterSequenceAt(index: Int, sequenceLength: Int) =
        substring(index, index + sequenceLength)
            .toCharArray()
            .map { it.code }
            .distinct()
            .count() == sequenceLength
}