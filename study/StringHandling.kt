package com.vutech.kotlinpractice.day4

fun main() {
    val test1 = "Test.Kotlin.String"

    println(test1.length) // 길이

    println(test1.toLowerCase()) // 소문자
    println(test1.toUpperCase()) // 대문자

    var test2 = test1.split(".") // 특정 문자 기준으로 배열로 나눈다
    println(test2)

    println(test2.joinToString()) // 나누어진 배열을 합쳐준다
    println(test2.joinToString("-")) // - 문자를 넣어 합쳐준다

    println(test1.substring(5..10)) // 5번 K부터 10번 n까지 문자열을 자른다

    println()

    val nullString: String? = null
    val emptyString = ""
    val blankString = " "
    val normalString = "A"

    // isNullOrEmpty() : null 이거나 empty 이면 true 반환
    println(nullString.isNullOrEmpty())
    println(emptyString.isNullOrEmpty())
    println(blankString.isNullOrEmpty())
    println(normalString.isNullOrEmpty())

    println()

    // isNullOrBlank() : null 이거나 blank 이면 true 반환 (공백 포함)
    println(nullString.isNullOrBlank())
    println(emptyString.isNullOrBlank())
    println(blankString.isNullOrBlank())
    println(normalString.isNullOrBlank())

    println()

    var test3 = "kotlin.kt"
    var test4 = "java.java"

    // java 로 시작하는 문자열 찾기 (boolean 반환)
    println(test3.startsWith("java"))
    println(test4.startsWith("java"))

    // .kr 로 끝나는 문자열 찾기 (boolean 반환)
    println(test3.endsWith(".kt"))
    println(test4.endsWith(".kt"))

    // lin 포함하는 문자열 찾기 (boolean 반환)
    println(test3.contains("lin"))
    println(test4.contains("lin"))
}