package puzzle

class Day5: BaseDay(5) {
    override fun solvePart1(): Int {
        val ranges = mutableListOf<LongRange>()
        val ingredients = mutableListOf<Long>()
        input
            .filter { it.isNotBlank() }
            .forEach { line ->
                if (line.contains('-')) ranges.add(mapRange(line))
                else ingredients.add(line.toLong())
            }
        return ingredients.count { ingredient ->
            ranges.any { it.contains(ingredient) }
        }
    }

    override fun solvePart2(): Any {
        val combinedRanges = mutableListOf<LongRange>()
        input
            .filter { it.contains('-') }
            .map { mapRange(it) }
            .forEach { range ->
                val first = combinedRanges.find { it.contains(range.first) }?.first ?: range.first
                val last = combinedRanges.find { it.contains(range.last) }?.last ?: range.last
                combinedRanges
                    .filter { it.first >= first && it.last <= last }
                    .forEach { combinedRanges.remove(it) }
                combinedRanges.add(LongRange(first, last))
            }
        return combinedRanges.sumOf { it.last - it.first + 1 }
    }

    private fun mapRange(line: String) =
        line
            .split('-')
            .map { it.toLong() }
            .let { LongRange(it[0], it[1]) }
}