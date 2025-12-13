package puzzle

import utils.Point2D
import utils.Point2DPair
import utils.memorize

class Day9: BaseDay(9) {

    private val redTiles = redTiles()
    private val redTilesPairs = redTilesPairs()
    private val perimeter = perimeter()
    private val perimeterY = perimeter().filter { it.point1.y == it.point2.y }
    private val perimeterPoints = perimeterPoints()

    override fun solvePart1() = redTilesPairs.maxOf { it.squareArea }

    override fun solvePart2(): Long {
        val redTilesPairsSorted = redTilesPairs.sortedByDescending { it.squareArea }
        for(redTilesPair in redTilesPairsSorted) {
            if (checkOppositeCorners(redTilesPair) && checkFullPerimeter(redTilesPair)) return redTilesPair.squareArea
        }
        return 0L
    }

    private fun redTiles(): List<Point2D> =
        input
            .map { line ->
                line
                    .split(',')
                    .map { it.toInt() }
                    .let { Point2D(it[0], it[1]) }
            }

    private fun redTilesPairs() =
        redTiles
            .mapIndexed { i, point1 ->
                redTiles
                    .subList(i + 1, redTiles.size)
                    .map { Point2DPair(point1, it) }
            }.flatten()

    private fun perimeter(): List<Point2DPair> =
        redTiles.indices
            .map {
                Point2DPair(
                    point1 = redTiles[it],
                    point2 = redTiles[(it + 1) % redTiles.size]
                )
            }

    private fun perimeterPoints(): Set<Point2D> =
        perimeter
            .map { it.linePoints }
            .flatten()
            .toSet()

    // Quick check to discard items faster
    private fun checkOppositeCorners(redTilesPair: Point2DPair) =
        isInsideCache(Point2D(redTilesPair.point1.x, redTilesPair.point2.y)) &&
                isInsideCache(Point2D(redTilesPair.point2.x, redTilesPair.point1.y))

    private fun checkFullPerimeter(redTilesPair: Point2DPair) =
        redTilesPair.squarePerimeter
            .all { linePerimeter ->
                linePerimeter.linePoints
                    .all { isInsideCache(it) }
            }

    val isInsideCache: (Point2D) -> Boolean  = memorize { p ->  isInside(p) }

    private fun isInside(point: Point2D): Boolean {
        if (perimeterPoints.contains(point)) return true
        return perimeterY
            .filter { it.point1.y < point.y }
            .filter { isInColumn(it, point) }
            .maxByOrNull { it.point1.y }
            ?.let { it.point1.x < it.point2.x }
            ?: return false
    }

    private fun isInColumn(it: Point2DPair, point: Point2D) =
        (it.point1.x <= point.x && it.point2.x >= point.x) || (it.point1.x >= point.x && it.point2.x <= point.x)
}