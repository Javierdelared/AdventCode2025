package utils

import java.io.File

private const val RESOURCE_PATH_TEMPLATE = "src/main/resources/input/day%02d.txt"

fun getLines(day: Int) = File(
    RESOURCE_PATH_TEMPLATE.format(day)
).useLines { lns -> lns.toList() }

