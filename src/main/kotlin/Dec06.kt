class Dec06() {

    /**
     * first position where the four most recently received characters were all different
     */
    fun startOfPacketMarkerIndex(packet: String): Int =
        packet.endOfMarkerPosition(4)

    fun startOfMessageMarkerIndex(packet: String): Int =
        packet.endOfMarkerPosition(14)

    private fun String.endOfMarkerPosition(markerLength: Int): Int {
        for (i in 0 until length - markerLength) {
            if (distinctCharacterSequenceAt(i, markerLength))
                return i + markerLength
        }
        throw RuntimeException("marker not found")
    }

    private fun String.distinctCharacterSequenceAt(index: Int, sequenceLength: Int) =
        substring(index, index + sequenceLength)
            .toCharArray()
            .map { it.code }
            .distinct()
            .count() == sequenceLength


}