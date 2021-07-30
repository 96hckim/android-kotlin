package com.vutech.kotlinpractice.day3

fun main() {
    // object : 인스턴스를 생성하지 않고 그 자체로 객체(싱글턴)
    println(Counter.count)

    Counter.countUp()
    Counter.countUp()

    println(Counter.count)

    Counter.clear()

    println(Counter.count)

    // Companion Object 는 Static 멤버와 비슷하다
    var a = FoodPoll("짜장")
    var b = FoodPoll("짬뽕")

    a.vote()
    a.vote()

    b.vote()
    b.vote()
    b.vote()

    println("${a.name} : ${a.count}")
    println("${b.name} : ${b.count}")
    println("총계 : ${FoodPoll.total}")
}

object Counter {
    var count = 0

    fun countUp() {
        count++
    }

    fun clear() {
        count = 0
    }
}

class FoodPoll(val name: String) {
    companion object {
        var total = 0
    }

    var count = 0

    fun vote() {
        total++
        count++
    }
}