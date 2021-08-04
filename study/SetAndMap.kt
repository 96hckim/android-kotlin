package com.vutech.kotlinpractice.day5

fun main() {
    // Set : 순서 X 중복 O, Set MutableSet, add remove contains
    val a = mutableSetOf("귤", "바나나", "키위")

    for (item in a) {
        println("${item}")
    }

    a.add("자몽")
    println(a)

    a.remove("바나나")
    println(a)

    println(a.contains("귤"))

    // Map : Key 와 value 쌍으로 이루어짐, Map MutableMap
    val b = mutableMapOf(
        "레드벨벳" to "음파음파",
        "트와이스" to "FANCY",
        "ITZY" to "ICY"
    )

    for (entry in b) {
        println("${entry.key} : ${entry.value}")
    }

    b["오마이걸"] = "번지" // b.put("오마이걸", "번지") 같다
    println(b)

    b.remove("ITZY")
    println(b)

    println(b["레드벨벳"])
}