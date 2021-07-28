package com.vutech.kotlinpractice

fun main() {
    // 조건문 if
    var a = 11
    if (a > 10) {
        println("a는 10보다 크다")
    } else {
        println("a는 10보다 작거나 같다")
    }

    // 비교연산자
    // 부등호 < <= > >= !=
    // 등호 ==
    // is 연산자 : 자료형이 맞는지 체크
    // a is Int : a 가 Int 와 '호환되는지 여부를 체크하고 형변환까지 한번에 진행
    // !is 연산자 : 자료형이 틀린지 체크

    // 다중 조건문 when
    doWhen(1)
    doWhen("test")
    doWhen(12L)
    doWhen(3.14159)
    doWhen("Kotlin")

    doWhen2(3.14f)
}

// Any : 어떤 자료형이든 상관없이 호환되는 코틀린 최상위 자료형
fun doWhen(a: Any) {
    when (a) {
        1 -> println("정수 1입니다")
        "test" -> println(a)
        is Long -> println("Long 타입입니다")
        !is String -> println("String 타입이 아닙니다")
        else -> println("어떤 조건도 만족하지 않습니다")
        // * 등호나 부등호 사용 불가 *
        // 여러개 조건 부합 시 먼저 부합하는 조건 실행
    }
}

// 표현식 Expression
fun doWhen2(a: Any) {
    var result = when (a) {
        1 -> "정수 1입니다"
        "test" -> a
        is Long -> "Long 타입입니다"
        !is String -> "String 타입이 아닙니다"
        else -> "어떤 조건도 만족하지 않습니다"
    }

    println(result)
}