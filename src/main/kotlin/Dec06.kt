class Dec06() {

    /**
     * first position where the four most recently received characters were all different
     */
    fun startOfPacketMarker(packet: String): Int {
        for (i in 0..packet.length - 4 - 1) {
            if( packet.startOfPacketMarkerAt(i))
                return i +4
        }
        throw RuntimeException("SOP marker not found")
    }


    fun String.startOfPacketMarkerAt(index: Int) =
        substring(index, index + 4)
            .toCharArray()
            .map { it.code }
            .distinct()
            .count() == 4
}