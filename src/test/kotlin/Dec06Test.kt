import org.junit.jupiter.api.Test
import kotlin.test.assertEquals

internal class Dec06Test {

    val exampleInput = mapOf(
        "bvwbjplbgvbhsrlpgdmjqwftvncz" to 5,
        "nppdvjthqldpwncqszvftbrmjlhg" to 6,
        "nznrnfrfntjfmvfwmzdfjlvtqnbhcprsg:" to 10,
        "zcfzfwzzqfrljwzlrfnpqdbhtmscgvjw:" to 11
    )

    @Test
    fun verifyExample1() {
        exampleInput.forEach { chars, charsNeeded ->
            println(chars)
            assertEquals(charsNeeded, Dec06().startOfPacketMarker(chars))
        }
    }
}