package puzzle

import utils.Map2D

class Day4: BaseDay(4) {
    override fun solvePart1() =
        findAccessibleRolls(Map2D(input)).count()

    override fun solvePart2() =
        countAllAccessibleRolls(Map2D(input), 0)

    private fun findAccessibleRolls(map2D: Map2D) = with(map2D) {
        map
            .filter { isRoll(it.value) }
            .filter { e -> findAdjacentPoints(e.key).count { isRoll(map[it]) } < 4 }
            .map { it.key }.toList()
    }

    private fun countAllAccessibleRolls(map2D: Map2D, total: Int): Int {
        val accessibleRolls = findAccessibleRolls(map2D)
        if (accessibleRolls.isEmpty()) return total
        removeAccessibleRolls(map2D, accessibleRolls)
        return countAllAccessibleRolls(map2D, total + accessibleRolls.count())
    }

    private fun removeAccessibleRolls(map2D: Map2D, accessibleRolls: List<Map2D.Point2D>) =
        accessibleRolls.forEach { map2D.map[it] = EMPTY_SPACE_CHAR }

    companion object {
        private const val EMPTY_SPACE_CHAR = '.'
        private const val ROLL_CHAR = '@'
        fun isRoll(c: Char?) = c == ROLL_CHAR
    }
}