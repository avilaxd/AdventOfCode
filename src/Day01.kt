fun main() {

    fun part1(input: List<String>): Int {
        var sum = 0
        for (line in input) {
            val digits = line.filter { it.isDigit() }
            val number = "${digits.first()}${digits.last()}".toIntOrNull()
            number?.let { sum += number }
        }
        return sum
    }

    fun part2(input: List<String>): Int {
        val replacedList = mutableListOf<String>()
        input.forEach {
            val overlappingIndexes = it.getOverlappingIndexes()
            var line = it
            overlappingIndexes.forEachIndexed { index, value ->
                val letter = line[value]
                 line = line.substring(0, value + index) + letter + line.substring(value + index)
            }
            replacedList.add(line.replaceSpelledDigits())
        }
        return part1(replacedList)
    }

    val input = readInput("Day01")
    part1(input).println()
    part2(input).println()
}

fun getSpelledDigitsMap(): Map<String, String> {
    return mapOf(
        "one" to "1",
        "two" to "2",
        "three" to "3",
        "four" to "4",
        "five" to "5",
        "six" to "6",
        "seven" to "7",
        "eight" to "8",
        "nine" to "9"
    )
}

fun String.getOverlappingIndexes(): List<Int> {
    val indexes = mutableListOf<Int>()
    getSpelledDigitsMap().forEach { mapEntry ->
        val firstIndexes = Regex(mapEntry.key).findAll(this)
        .map { appearance -> appearance.range.first }
        .toList()
        firstIndexes.forEach {
            indexes.add(it)
            indexes.add(it + (mapEntry.key.length - 1))
        }
    }
    return findDuplicates(indexes)
}

fun findDuplicates(items: List<Int>): List<Int> {
    return items.filter { item -> items.count { it == item } > 1 }.toSet().toList()
}

fun String.replaceSpelledDigits(): String {
    var finalString = this
    getSpelledDigitsMap().forEach { finalString = finalString.replace(it.key, it.value) }
    return finalString
}