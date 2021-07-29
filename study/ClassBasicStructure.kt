package com.vutech.kotlinpractice.day2

fun main() {
    // 클래스는 값과 그 값을 사용하는 기능들을 묶어놓은 것
    // 고유의 특징값(속성) + 기능의 구현(함수)
    var a = Person("박보영", 1990)
    var b = Person("전정국", 1997)
    var c = Person("장원영", 2004)

    // 사용법 : 변수명.속성명
    a.introduce()
    b.introduce()
    c.introduce()
}

class Person(var name: String, val birthYear: Int) {
    fun introduce() {
        println("안녕하세요, ${birthYear}년생 ${name}입니다")
    }
}