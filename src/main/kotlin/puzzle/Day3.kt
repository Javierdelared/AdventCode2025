package puzzle

class Day3: BaseDay(3) {

    private val batteryBankList: List<List<Int>> = input
        .map { batteryBank ->
            batteryBank.map { it.code - '0'.code }
        }
    override fun solvePart1() =
        batteryBankList
            .map { findMaxBatteries(it, 2) }
            .sumOf { calculateTotal(it) }

    override fun solvePart2() =
        batteryBankList
            .map { findMaxBatteries(it, 12) }
            .sumOf { calculateTotal(it) }

    private fun findMaxBatteries(
        batteries: List<Int>,
        batteryNumber: Int,
    ) = findMaxBatteries(batteries, 0, batteryNumber, mutableListOf())

    private fun findMaxBatteries(
        batteries: List<Int>,
        lastIndex: Int,
        batteryNumber: Int,
        maxBatteryList: List<Int>
    ): List<Int> {
        if (batteryNumber == 0) return maxBatteryList
        val validRange = batteries.subList(lastIndex, batteries.size - batteryNumber + 1)
        val maxBattery = validRange.max()
        maxBatteryList.addLast(maxBattery)
        val maxBatteryIndex = validRange.indexOf(maxBattery) + lastIndex + 1
        return findMaxBatteries(batteries, maxBatteryIndex, batteryNumber - 1, maxBatteryList)
    }

    private fun calculateTotal(batteries: List<Int>) =
        batteries.fold(0L) { acc, battery -> acc * 10 + battery }

}