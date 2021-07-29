package com.vutech.kotlinpractice.day2

fun main() {
    // 기존 흐름제어
    // return : 반환
    // break : 중단
    // continue : 다음
    for (i in 1..10) {
        if (i == 3) break
        println(i)
    }

    // 코틀린에서 추가된 흐름제어 label
    // LabelName@
    loop@ for (i in 1..10) {
        for (j in 1..10) {
            if (i == 1 && j == 2) continue@loop
            println("i : $i, k: $j")
        }
    }

    // 논리 연산자
    // && : and
    // || : or
    // ! : not
    println(true && false) // false
    println(true || false) // true
    println(!true) // false
    println(!false) // true

    var a = 6
    var b = 4
    println(a > 5 && b > 5) // false
}