package com.vutech.kotlinpractice

// fun main() : 코드의 시작점
fun main() {
    // 변수에 무조건 값 할당해주어야 한다.(꼭 선언 시 할당 안해주어도 됨)
    // var : 가변
    // val : 불변
    var a: Int = 123

    // ? : null 허용
    var b: Int? = null

    // Long 일 경우 뒤에 L 붙여준다.
    var longValue: Long = 1234L
    // 16진수 일 경우 0x 붙여준다.(hexadecimal)
    var intValueByHex: Int = 0x1af
    // 2진수 일 경우 0b 붙여준다.(binary)
    var intValueByBin: Int = 0b10110110
    // 코틀린은 8진수 지원 안한다.

    // 실수는 기본이 Double 형이다. Float일 경우 뒤에 f or F 붙여준다.
    var doubleValue: Double = 123.5
    var floatValue: Float = 123.5f

    // char 문자 하나하나가 2bytes 의 메모리 공간 사용
    var charValue: Char = 'a'

    // boolean
    var booleanValue: Boolean = true

    // string 여러 줄일 경우 """
    val stringValue = "one line string test"
    val multiLineStringValue = """multiline
        string
        test
    """.trimMargin()
}