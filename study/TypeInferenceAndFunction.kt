package com.vutech.kotlinpractice

fun main() {
    // 타입추론 : 변수나 함수들을 선언할 때나 연산할 때 자료형을 명시하지 않아도 자동으로 추론해주는 기능
    var a = 1234 // Int
    var b = 1234L // Long

    var c = 12.45 // Double
    var d = 12.45f // Float

    var e = 0xABCD // Int
    var f = 0b0101010 // Int

    var g = true // Boolean
    var h = 'c' // Char

    println(add(5, 6, 7))
    println(add2(5, 6, 7))
}

// 함수 : 특정한 동작을 하거나 원하는 결과값을 연산하는데 사용
// fun 함수명(파라미터): 반환형(없을 때 생략)
fun add(a: Int, b: Int, c: Int): Int {
    return a + b + c
}

// 단일 표현식 함수
fun add2(a: Int, b: Int, c: Int) = a + b + c