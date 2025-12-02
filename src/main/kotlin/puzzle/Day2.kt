package puzzle

import utils.memoize

class Day2: BaseDay(2) {
    override fun solvePart1() =
        input[0]
            .split(",")
            .map { IdRange(it) }
            .sumOf { sumIdsRepeatedTwice(it) }

    override fun solvePart2() =
        input[0]
            .split(",")
            .map { IdRange(it).ids }
            .flatten()
            .filter { isRepeatedAtLeastTwice(it) }
            .sum()

    data class IdRange(val value: String) {
        val min: Long
        val max: Long
        val ids: List<Long>
            get() = (min..max).toList()
        init {
            value.split("-").let {
                min = it[0].toLong()
                max = it[1].toLong()
            }
        }
    }

    private fun sumIdsRepeatedTwice(range: IdRange) =
        sumIdsRepeatedTwice(
            id = range.min,
            max = range.max,
            sum = if (isRepeatedTwice(range.min)) range.min else 0L,
        )

    private fun sumIdsRepeatedTwice(
        id: Long,
        max: Long,
        sum: Long,
    ): Long =
        nextInvalidId(id)
            .let {
                if (it <= max) sumIdsRepeatedTwice(it, max, sum + it) else sum
            }

    private fun isRepeatedTwice(id: Long): Boolean {
        val stringId = id.toString()
        val size = stringId.length
        if (size % 2 == 1) return false
        return stringId.substring(0, size/2) == stringId.substring(size/2)
    }

    private fun nextInvalidId(id: Long): Long {
        val stringId = id.toString()
        val size = stringId.length
        var part1: String
        if (size % 2 == 1) {
            part1 = "1" + "0".repeat(size/2)
        } else {
            part1 = stringId.substring(0, size/2)
            val invalidId = part1.repeat(2).toLong()
            if (invalidId > id) return invalidId
            part1 = (stringId.substring(0, size/2).toLong() + 1).toString()
        }
        return part1.repeat(2).toLong()
    }

    private fun isRepeatedAtLeastTwice(id: Long): Boolean {
        val stringId = id.toString()
        val size = stringId.length
        return calculateDivisors(size).any { divisor ->
            val partSize = size/divisor
            val part1 = stringId.substring(0, partSize)
            (1..<divisor).map { part ->
                stringId.substring(part*partSize, (part+1)*partSize)
            }.all { it == part1 }
        }
    }

    val calculateDivisors: (Int) -> Set<Int> = memoize {
        n -> if (n == 1) setOf() else calculateDivisors(2, n, n, mutableSetOf(n))
    }

    private fun calculateDivisors(
        minDivisor: Int,
        maxDivisor: Int,
        size: Int,
        divisorList: MutableSet<Int>,
    ): Set<Int> {
        if (minDivisor >= maxDivisor) return divisorList
        var newMaxDivisor = maxDivisor
        if (size % minDivisor == 0) {
            divisorList.add(minDivisor)
            newMaxDivisor = size / minDivisor
            divisorList.add(newMaxDivisor)
        }
        return calculateDivisors(minDivisor + 1, newMaxDivisor, size, divisorList)
    }
}