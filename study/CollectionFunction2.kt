package com.vutech.kotlinpractice.day6

fun main() {
    // associateBy : 아이템에서 key 를 추출하여 map 으로 변환하는 함수
    // groupBy : key 가 같은 아이템 끼리 배열로 묶어 map 으로 만드는 함수
    // partition : 아이템에 조건을 걸어 두개의 컬렉션으로 나누어 준다
    data class Person(val name: String, val birthYear: Int)

    val personList = listOf(
        Person("유나", 1992),
        Person("조이", 1996),
        Person("츄", 1999),
        Person("유나", 2003)
    )

    println(personList.associateBy { it.birthYear })
    println(personList.groupBy { it.name })

    val (over98, under98) = personList.partition { it.birthYear > 1998 }
    println(over98)
    println(under98)

    // flatMap : 아이템마다 만들어진 컬렉션을 합쳐서 반환하는 함수
    // getOrElse : 인덱스 위치에 아이템이 있으면 아이템을 반환, 아닌 경우 기본값 지정하여 반환
    // zip : 컬렉션 두 개의 아이템을 1:1로 매칭하여 새 컬렉션을 만들어 준다
    val numbers = listOf(-3, 7, 2, -10, 1)

    println(numbers.flatMap { listOf(it * 10, it + 10) })

    println(numbers.getOrElse(1) { 50 })
    println(numbers.getOrElse(10) { 50 })

    val names = listOf("A", "B", "C", "D")

    println(names zip numbers) // 1은 짝이 맞지 않으므로 제외된다
}