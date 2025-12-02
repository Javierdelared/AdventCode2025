package puzzle

import org.slf4j.Logger
import org.slf4j.LoggerFactory
import utils.getLines

/**
 * Base class for Advent of Code solutions that automatically loads input based on the subclass day.
 */
abstract class BaseDay(val day: Int) {

    protected val logger: Logger = LoggerFactory.getLogger(BaseDay::class.java)

    protected val input: List<String> = getLines(day)
    abstract fun solvePart1(): Any
    abstract fun solvePart2(): Any
}