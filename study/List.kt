package com.vutech.kotlinpractice.day4

fun main() {
    // 리스트 : 여러 개의 데이터를 원하는 순서로 넣어 관리하는 형태
    // List<out T> : 생성시에 넣은 객체를 대체, 추가, 삭제 할 수 없음 ex. listOf(1, 2, 3)
    // MutableList<T> : 대체, 추가, 삭제 가능 ex. mutableListOf("A", "B", "C")
    // 추가 : add(데이터), add(인덱스, 데이터)
    // 삭제 : remove(데이터), removeAt(인덱스)
    // 무작위 섞기 : shuffle()
    // 정렬 : sort()
    // 특정 요소 다른 데이터로 변경 가능 ex. list[인덱스] = 데이터
    var a = listOf("사과", "딸기", "배")
    println(a[1])

    for (fruit in a) {
        print("${fruit}:")
    }
    println()

    var b = mutableListOf(6, 3, 1)
    println(b)

    b.add(4)
    println(b)

    b.add(2, 8)
    println(b)

    b.removeAt(1)
    println(b)

    b.shuffle()
    println(b)

    b.sort()
    println(b)
}