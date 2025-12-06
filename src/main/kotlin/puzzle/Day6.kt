package puzzle

class Day6: BaseDay(6) {
    override fun solvePart1(): Long {
        val isSum: List<Boolean> = input.last()
            .split(Regex(" +"))
            .map { it.first() == SUM }
        val results = isSum.map { op -> 0L.takeIf { op } ?: 1L }.toMutableList()
        input
            .subList(0, input.size - 1)
            .forEach { line ->
                line.split(Regex(" +"))
                    .filter { it.isNotBlank() }
                    .map { it.toLong() }
                    .forEachIndexed { i, n ->
                        if (isSum[i]) results[i] += n
                        else results[i] *= n
                    }
            }
        return results.sum()
    }

    override fun solvePart2(): Long {
        val maxLength = input.maxOf { it.length }
        val paddedInput = input.map {
            it + SPACE.repeat(maxLength - it.length)
        }

        val operationsLine = paddedInput.last()
        val operationIndexes = operationsLine
            .mapIndexedNotNull{ i, op ->
                i.takeIf{ OPERATIONS.contains(op) }
            }
        val operationLimits = operationIndexes + (maxLength + 1)

        val numbersByOperation: List<List<String>> = MutableList(operationIndexes.size) { mutableListOf() }
        paddedInput
            .subList(0, paddedInput.size - 1).forEach { line ->
                operationIndexes.indices
                    .forEach { i ->
                        val number = line.substring(operationLimits[i], operationLimits[i+1] - 1)
                        numbersByOperation[i].addLast(number)
                    }
            }

        return numbersByOperation.map { numList ->
            val rearrangedNumbers = MutableList(numList[0].length) { EMPTY }
            rearrangedNumbers.indices.forEach { column ->
                numList
                    .map { it[column] }
                    .forEach { digit ->
                        rearrangedNumbers[column] = rearrangedNumbers[column] + digit
                    }
            }
            rearrangedNumbers.map { it.replace(SPACE, EMPTY).toLong() }
        }.mapIndexed { i, numList ->
            if (operationsLine[operationIndexes[i]] == SUM) numList.sum()
            else numList.fold(1L) { acc, n -> acc * n }
        }.sum()
    }

    companion object {
        private const val SUM = '+'
        private const val MUL = '*'
        private val OPERATIONS = listOf(SUM, MUL)
        private const val SPACE = " "
        private const val EMPTY = ""
    }
}