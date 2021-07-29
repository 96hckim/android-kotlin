package com.vutech.kotlinpractice.day2

fun main() {
    // 생성자 : 새로운 인스턴스를 만들기 위해 호출하는 특수한 함수
    // 인스턴스의 속성을 초기화, 생성시 구문 수행
    // init : 파라미터나 반환형이 없는 특수한 함수(생성자를 통해 인스턴스 생성 시 호출되는 함수)
    var a = Person2("박보영", 1990)
    var b = Person2("전정국", 1997)
    var c = Person2("장원영", 2004)

    var d = Person2("이루다")
    var e = Person2("차은우")
    var f = Person2("류수정")
}

class Person2(var name: String, val birthYear: Int) {
    init {
        // this : 인스턴스 자신의 속성이나 함수를 호출하기 위해 클래스 내부에서 사용되는 키워드
        println("${this.birthYear}년생 ${this.name}님이 생성되었습니다.")
    }

    // 보조 생성자 constructor 사용 시 반드시 기본 생성자를 통해 속성을 초기화 해줘야 한다
    constructor(name: String) : this(name, 1997) {
        println("보조 생성자가 사용되었습니다.")
    }
}