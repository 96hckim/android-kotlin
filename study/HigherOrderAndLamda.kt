package com.vutech.kotlinpractice.day3

fun main() {
    // 고차 함수 : 함수를 마치 클래스에서 만들어 낸 인스턴스 처럼 취급하는 방법
    // 함수를 파라미터로 넘겨줄 수도 있고 결과값으로 반환 받을 수도 있는 방법
    a("일반")
    // 일반 함수를 고차 함수로 변경해 주는 연산자 ::
    b(::a)

    // 람다함수는 일반함수와 달리 그 자체가 이미 고차함수이므로 연산자가 필요 없다
    val c: (String) -> Unit = { str -> println("$str 람다함수") }
//    val c = { str: String -> println("$str 람다함수") }
    b(c)
}

fun a(str: String) {
    println("$str 함수 a")
}

// (자료형, 자료형, ...) -> 자료형
//      파라미터            반환형
fun b(function: (String) -> Unit) {
    function("b가 호출한")
}