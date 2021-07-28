package com.vutech.kotlinpractice

fun main() {
    // 조건형 반복문 : 조건이 참인 경우 반복을 유지 (while, do-while)
    var a = 0
    while (a < 5) {
        println(a++)
    }
    // 반드시 한번 실행 do-while
    do {
        println(a++)
    } while (a < 5)

    // 범위형 반복문 : 반복 범위를 정해 반복을 수행 (for)
    for (i in 0..9) {
        print(i) // 0~9까지 줄을 떼지 않고 출력
    }
    println()
    // 증가값 3
    for (i in 0..9 step 3) {
        print(i) // 0369 출력
    }
    println()
    // 감소
    for (i in 9 downTo 0) {
        print(i)
    }
    println()
    // char 자료형
    for (i in 'a'..'e') {
        print(i)
    }
}