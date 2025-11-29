package puzzle

import utils.getLines

/**
 * Base class for Advent of Code solutions that automatically loads input based on the subclass name.
 */
abstract class BaseDay(val day: Int) {

    protected val input: List<String> = getLines(day)
    abstract fun solvePart1(): Any
    abstract fun solvePart2(): Any
}