package puzzle

class Day1: BaseDay(1) {
    override fun solvePart1(): Int {
        var result = 0
        var pos = INITIAL_POS
        input
            .map { mapDialMove(it) }
            .forEach {
                pos = it.move(pos)
                if (pos < 0) pos += DIAL_SIZE
                if (pos >= DIAL_SIZE) pos -= DIAL_SIZE
                if (pos == 0) result++
            }
        return result
    }

    override fun solvePart2(): Int {
        var result = 0
        var pos = INITIAL_POS
        input
            .map { mapDialMove(it) }
            .forEach {
                result += it.scale / DIAL_SIZE
                if (pos == 0 && it.dir == 'L') pos = DIAL_SIZE
                pos = it.move(pos)
                if (pos == 0) result++
                if (pos < 0) {
                    result++
                    pos += DIAL_SIZE
                }
                if (pos >= DIAL_SIZE) {
                    result++
                    pos -= DIAL_SIZE
                }
        }
        return result
    }

    companion object {
        private const val DIAL_SIZE = 100
        private const val INITIAL_POS = 50
    }
    data class DialMove(val dir: Char, val scale: Int) {
        private val allowedDir = arrayOf('R', 'L')
        fun move(pos: Int): Int {
            val shift = scale % DIAL_SIZE
            return when (dir) {
                'R' -> pos + shift
                'L' -> pos - shift
                else -> throw IllegalArgumentException()
            }
        }
        init {
            require(allowedDir.contains(dir))
        }
    }

    private fun mapDialMove(line: String) = DialMove(
        dir = line.first(),
        scale = line.substring(1).toInt(),
    )
}