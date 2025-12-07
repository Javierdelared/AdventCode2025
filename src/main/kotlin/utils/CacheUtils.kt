package utils

fun <T, R> memorize(function: (T) -> R): (T) -> R {
    val cache = mutableMapOf<T, R>()
    return { input ->
        cache.getOrPut(input) { function(input) }
    }
}