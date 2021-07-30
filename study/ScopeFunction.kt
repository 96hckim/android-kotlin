package com.vutech.kotlinpractice.day3

fun main() {
    // 람다함수의 특별한 케이스
    // 1. 람다함수도 여러 구문의 사용이 가능
    val c: (String) -> Unit = { str ->
        println("$str 람다함수")
        println("여러 구문을")
        println("사용가능합니다")
    }
    // 반환값이 있을 경우 마지막 구문이 반환값
    val calculate: (Int, Int) -> Int = { a, b ->
        println(a)
        println(b)
        a + b
    }

    // 2. 파라미터가 없는 람다함수는 실행할 구문들만 나열하면 된다
    val a: () -> Unit = { println("파라미터가 없어요") }

    // 3. 파라미터가 하나뿐이라면 it 사용
    val c2: (String) -> Unit = { println("$it 람다함수") }

    // 스코프 함수 : 함수형 언어의 특징을을 좀 더 편리하게 사용할 수 있도록 기본 제공하는 함수들
    // apply : 인스턴스를 생성한 후 변수에 담기 전에 초기화 과정을 수행할 때 사용
    // run : 인스턴스 대신 마지막 구문에 결과값을 반환(인스턴스가 만들어진 후에 함수나 속성을 사용할 때)
    // with : run 과 동일한 기능을 가지지만 인스턴스를 참조연산자 대신 파라미터로 받는다
    // also : 처리가 끝나면 인스턴스를 반환(apply)
    // let : 처리가 끝나면 최종값을 반환(run)
    // also, let : 스코프 바깥에 중복되어 있는 경우 혼란 방지(it 사용)
    var b = Book("코틀린 기본서", 10000).apply {
        name = "[초특가]$name"
        discount()
    }

    b.run {
        println("상품명: ${name}, 가격: ${price}원")
    }

    with(b) {
        println("상품명: ${name}, 가격: ${price}원")
    }

    var b2 = Book("코틀린 응용서", 10000).also {
        it.name = "[초특가]$it.name"
        it.discount()
    }

    b.let {
        println("상품명: ${it.name}, 가격: ${it.price}원")
    }
}

class Book(var name: String, var price: Int) {
    fun discount() {
        price -= 2000
    }
}