package com.vutech.kotlinpractice.day5

fun main() {
    // null check 연산자

    var a: String? = null
    // ?. : null safe operator
    // 먼저 객체가 null 인지 확인부터하고 null 이면 뒷구문 실행 안한다 ex. sample?.toUpperCase()
    println(a?.toUpperCase())
    // ?: : elvis operator
    // null 이면 우측 객체로 대체 ex. sample?:"default
    println(a ?: "default".toUpperCase())
    // !!. : non-null assertion operator
    // null 여부 컴파일시 확인 안하는 연산자
    println(a!!.toUpperCase())

    // null 이면 실행안한다
    a?.run {
        println(toUpperCase())
        println(toLowerCase())
    }

    // 내용의 동일성 : 메모리 주소가 달라도 내용이 같다면 동일하다고 판단 ex. a==b
    // 객체의 동일성 : 서로 다른 변수가 메모리 상의 같은 객체만 가르키고 있을 때 동일하다고 판단 ex. a===b
    var b = Product("콜라", 1000)
    var c = Product("콜라", 1000)
    var d = b
    var e = Product("사이다", 1000)

    println(b == c) // true
    println(b === c) // false

    println(b == d) // true
    println(b === d) // true

    println(b == e) // false
    println(b === e) // false
}

class Product(val name: String, val price: Int) {
    override fun equals(other: Any?): Boolean {
        return if (other is Product) other.name == name && other.price == price else false
    }
}