package utils

import kotlin.math.pow

data class Map2D(val input: List<String>) {

    val map: MutableMap<Point2D, Char> =
        input.mapIndexed { y, l ->
            l.mapIndexed { x, c ->
                Point2D(x, y) to c
            }
        }.flatten().toMap().toMutableMap()

    private val minX = 0
    private val minY = 0
    private val maxX
        get() = input[0].length - 1
    private val maxY
        get() = input.size - 1

    fun findAdjacentPoints(point: Point2D) =
        (-1..1).map { x ->
            (-1..1)
                .filter { y -> x != 0 || y != 0 }
                .map { y -> Point2D(point.x + x, point.y + y) }
                .filter { isValidPoint(it) }
        }.flatten().toList()

    private fun isValidPoint(point: Point2D) =
        point.x in minX..maxX && point.y in minY..maxY

}

data class Point2D(val x: Int, val y: Int)

data class Point3D(val x: Double, val y: Double, val z: Double)


data class Point3DPair(val point1: Point3D, val point2: Point3D) {
    val distance
        get() = (point1.x - point2.x).pow(2) + (point1.y - point2.y).pow(2) + (point1.z - point2.z).pow(2)
}