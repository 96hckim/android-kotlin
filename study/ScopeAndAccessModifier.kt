package com.vutech.kotlinpractice.day3

fun main() {
    // scope : 변수나 함수, 클래스 같은 멤버들을 서로 공유하여 사용할 수 있는 범위를 지정해 둔 단위
    // 패키지, 클래스, 함수로 나뉜다
    // 규칙1. 스코프 외부에서는 스코프 내부의 멤버를 참조연산자로만 참조 가능하다 ex) a.eat() 패키지는 import
    // 규칙2. 동일 스코프 내에서는 멤버들을 공유할 수 있다
    println(a)
    B().print()
    // 규칙3. 하위 스코프에서는 상위 스코프의 멤버를 재정의 할 수 있다
    val a = "함수 스코프"
    println(a)
    B().print()

    // 접근제한자 : 스코프 외부에서 스코프 내부에 접근할 때 그 권한을 개발자가 제어할 수 있는 기능

    // 패키지 스코프에서는
    // public : 어떤 패키지에서도 접근 가능(default)
    // internal : 같은 모듈 내에서만 접근 가능
    // private : 같은 파일 내에서만 접근 가능

    // 클래스 스코프에서는
    // public : 클래스 외부에서 늘 접근 가능(default)
    // private : 클래스 내부에서만 접근 가능
    // protected : 클래스 자신과 상속받은 클래스에서 접근 가능
}

val a = "패키지 스코프"

class B {
    fun print() {
        val a = "클래스 스코프"
        println(a)
    }
}