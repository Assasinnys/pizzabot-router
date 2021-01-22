package com.itechart.pizzabotrouter.util

fun Int.pow(n: Int): Int {
    val number: Int = this
    var result: Int = this
    repeat(n.minus(1)) {
        result = result.times(number)
    }
    return result
}

