import java.lang.System.lineSeparator

class Dec01 {

    private val emptyLine = lineSeparator() + lineSeparator()

    fun mostCalories(elvesCaloriesList: String): Int =
        sumTopNCalories(elvesCaloriesList, 1)

    fun sumTop3Calories(elvesCaloriesList: String): Int =
        sumTopNCalories(elvesCaloriesList, 3)

    private fun sumTopNCalories(elvesCaloriesList: String, count: Int): Int =
        elvesCaloriesList
            .split(emptyLine)
            .map { elfCalories ->
                elfCalories
                    .split(lineSeparator())
                    .map(String::trim)
                    .map(String::toInt)
                    .sum()
            }
            .sortedDescending()
            .subList(0, count)
            .sum()

}