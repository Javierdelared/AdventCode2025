package puzzle

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.slf4j.LoggerFactory
import kotlin.reflect.full.primaryConstructor

abstract class BaseDayTest {

    private val logger = LoggerFactory.getLogger(BaseDayTest::class.java)

    private lateinit var daySolver: BaseDay

    abstract val expectedPart1: Any
    abstract val expectedPart2: Any

    @BeforeEach
    fun setUp() {
        daySolver = Class.forName(
            this::class.java.name.removeSuffix("Test")
        ).kotlin.primaryConstructor!!.call() as BaseDay
    }
    @Test
    fun solvePart1() {
        logger.info("Running Test for AoC - Day ${daySolver.day} - Part 1")
        val result = daySolver.solvePart1()
        logger.info("Result: $result")
        assertEquals(result, expectedPart1)
    }

    @Test
    fun solvePart2() {
        logger.info("Running Test for AoC - Day ${daySolver.day} - Part 2")
        val result = daySolver.solvePart2()
        logger.info("Result: $result")
        assertEquals(result, expectedPart2)
    }
}