package com.vutech.kotlinpractice.day6

fun main() {
    // 컬렉션 함수 : list 나 set, map 과 같은 컬렉션 또는 배열을 원하는 방식으로 조작할 수 있는 함수
    // forEach : 모든 객체를 it 으로 참조 가능
    // filter : 특정한 조건을 걸어 조건에 맞는 객체로만 컬렉션을 다시 만든다
    // map : it 을 통해 값 변경
    // any : 하나라도 조건에 맞으면 true
    // all : 모두 조건에 맞으면 true
    // none : 하나도 조건에 맞지 않으면 true
    // first : 첫번째 객체 반환 (조건에 따라 다르게 가능, 조건에 안맞으면 Exception, find 대체 가능)
    // last : 마지막 객체 반환 (findLast 대체 가능)
    // firstOrNull, lastOrNull 로 Exception 이 아닌 null 반환 가능
    // count : 아이템 개수 반환 (조건에 맞는 아이템 개수 가능)
    val nameList = listOf("박수영", "김지수", "김다현", "신유나", "김지우")

    nameList.forEach { print("$it ") }
    println()

    // [김지수, 김다현, 김지우]
    println(nameList.filter { it.startsWith("김") })

    // [이름 : 박수영, 이름 : 김지수, 이름 : 김다현, 이름 : 신유나, 이름 : 김지우]
    println(nameList.map { "이름 : $it" })

    println(nameList.any { it == "김지연" })
    println(nameList.all { it.length == 3 })
    println(nameList.none { it.startsWith("김") })

    println(nameList.first { it.startsWith("김") })
    println(nameList.last { it.startsWith("김") })
    println(nameList.count { it.contains("지") })
}