package com.vutech.kotlinpractice.day5

fun main() {
    // data class : equals(), hashCode(), toString(), copy(), componentX() 지원
    val a = General("보영", 212)

    println(a == General("보영", 212)) // false
    println(a.hashCode())
    println(a)

    val b = Data("루다", 306)

    println(b == Data("루다", 306)) // true
    println(b.hashCode())

//    Data(name=루다, id=306)
//    Data(name=루다, id=306)
//    Data(name=아린, id=306)
//    Data(name=루다, id=618)
    println(b)
    println(b.copy())
    println(b.copy("아린"))
    println(b.copy(id = 618))

    val list = listOf(
        Data("보영", 212),
        Data("루다", 306),
        Data("아린", 618)
    )

    // (component1(), component2())
    for ((c, d) in list) {
        println("${c}, ${d}")
    }

    // enum class
    var state = State.SING
    println(state) // SING

    state = State.SLEEP
    println(state.isSleeping()) // true

    state = State.EAT
    println(state.message) // 밥을 먹습니다
}

class General(val name: String, val id: Int)

data class Data(val name: String, val id: Int)

enum class State(val message: String) {
    SING("노래를 부릅니다"),
    EAT("밥을 먹습니다"),
    SLEEP("잠을 잡니다");

    fun isSleeping() = this == State.SLEEP
}