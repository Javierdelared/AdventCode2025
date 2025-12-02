package utils

fun <T, R> memoize(function: (T) -> R): (T) -> R {
    val cache = mutableMapOf<T, R>()
    return { input ->
        cache.getOrPut(input) { function(input) }
    }
}