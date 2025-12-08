package puzzle

import utils.Point3D
import utils.Point3DPair

class Day8: BaseDay(8) {

    private val junctions = junctions()
    private val junctionPairs = junctionPairsSorted()
    private val circuits = junctions.map { mutableSetOf(it) }.toMutableList()
    override fun solvePart1(): Long {
        (0..<1000).forEach { joinJunctionPair(junctionPairs[it]) }
        return circuits.map { it.size }.sortedDescending().subList(0, 3).fold(1L) { acc , n -> acc * n }
    }

    override fun solvePart2(): Long {
        var i = 0
        while(circuits.size > 1) joinJunctionPair(junctionPairs[i++])
        return junctionPairs[i - 1].run { (point1.x * point2.x).toLong() }
    }

    private fun junctions(): List<Point3D> =
        input
            .map { line ->
                line
                    .split(',')
                    .map { it.toDouble() }
                    .let { Point3D(it[0], it[1], it[2]) }
            }
    private fun junctionPairsSorted() =
        junctions
            .mapIndexed { i, point1 ->
                junctions
                    .subList(i + 1, junctions.size)
                    .map { point2 ->
                        Point3DPair(point1, point2)
                    }
            }.flatten().sortedBy { it.distance }

    private fun joinJunctionPair(junctionPair: Point3DPair) {
        var circuit1: MutableSet<Point3D>? = null
        var circuit2: MutableSet<Point3D>? = null
        for (circuit in circuits) {
            if (circuit.contains(junctionPair.point1)) circuit1 = circuit
            if (circuit.contains(junctionPair.point2)) circuit2 = circuit
            if (circuit1 != null && circuit2 != null) break
        }
        if (circuit1 != circuit2) {
            circuits.remove(circuit2)
            circuit1!!.addAll(circuit2!!)
        }
    }
}