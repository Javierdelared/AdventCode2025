package puzzle

import utils.memorize

class Day7: BaseDay(7) {
    override fun solvePart1() = countBeamSplits(
        lineIndex = 1,
        beamIndexes = setOf(input[0].indexOf(STARTING_BEAM)),
        totalBeamSplits = 0
    )

    override fun solvePart2() = countTimelines(
        lineIndex = 1,
        beamIndex = input[0].indexOf(STARTING_BEAM),
    )

    private fun countBeamSplits(lineIndex: Int, beamIndexes: Set<Int>, totalBeamSplits: Int): Int {
        if (lineIndex >= input.size) return totalBeamSplits
        var lineBeamSplits = 0
        val newBeamIndexes = mutableSetOf<Int>()
        beamIndexes.forEach {
            if (input[lineIndex][it] == SPLITTER) {
                lineBeamSplits++
                newBeamIndexes.add(it+1)
                newBeamIndexes.add(it-1)
            } else {
                newBeamIndexes.add(it)
            }
        }
        return countBeamSplits(lineIndex + 1, newBeamIndexes,  totalBeamSplits + lineBeamSplits)
    }

    private fun countTimelines(lineIndex: Int, beamIndex: Int): Long {
        if (lineIndex >= input.size) return 1L
        val newLineIndex = lineIndex + 1
        return if (input[lineIndex][beamIndex] == SPLITTER) {
            countTimelines(Pair(newLineIndex, beamIndex + 1)) +
                    countTimelines(Pair(newLineIndex, beamIndex - 1))
        } else {
            countTimelines(Pair(newLineIndex, beamIndex))
        }
    }

    val countTimelines: (Pair<Int, Int>) -> Long  = memorize {
        p ->  countTimelines(p.first, p.second)
    }
    companion object {
        private const val STARTING_BEAM = 'S'
        private const val SPLITTER = '^'
    }
}