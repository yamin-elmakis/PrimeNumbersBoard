package com.yamin.primeboard.utils

import kotlin.math.sqrt

fun <T, K> Pair<T?, K?>.safeLet(action: (first: T, second: K) -> Unit) {
    if (this.first != null && this.second != null) {
        return action(this.first!!, this.second!!)
    }
}

fun Int.getPrimeFactors(): List<Int> {
    val res = mutableSetOf<Int>()
    var number = this
    var factor = 2
    while (factor <= number) {
        while (number % factor == 0) {
            if (factor != this)
                res.add(factor)
            number /= factor
        }
        factor++
    }
    return res.toList()
}

fun Int.isPrime(): Boolean {
    if (this < 2) return false
    if (this == 2 || this == 3) return true
    if (this % 2 == 0 || this % 3 == 0) return false
    val sqrtNum = sqrt(this.toDouble()).toInt() + 1
    var i = 6
    while (i <= sqrtNum) {
        if (this % (i - 1) == 0 || this % (i + 1) == 0) return false
        i += 6
    }
    return true
}

fun Int.hasGcd(other: Int): Boolean {
    if (this < 2 || other < 2) return false
    return gcd(other) > 1
}

private fun Int.gcd(other: Int): Int {
    if (other == 0) return this
    return other.gcd(this % other)
}