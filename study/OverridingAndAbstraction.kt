package com.vutech.kotlinpractice.day2

fun main() {
    // 오버라이딩 : 서브클래스에서 같은 이름과 형태로 된 함수의 내용을 다시 구현
    var t = Tiger()

    t.eat()

    // 추상화 : 선언부만 있고 기능이 구현되지 않은 추상함수, 추상함수를 포함하는 추상클래스 요소로 구성
    var r = Rabbit()

    r.eat()
    r.sniff()

    // 인터페이스
    var d = Dog2()

    d.run()
    d.eat()

    /**
     * 오버라이딩 : 이미 구현이 끝난 함수의 기능을 서브클래스에서 변경해야 할 때
     * 추상화 : 형식만 선언하고 실제 구현은 서브클래스에 일임할 때
     * 인터페이스 : 서로 다른 기능들을 여러개 물려주어야 할 때
     */
}

// 상속
open class Animal2 {
    open fun eat() { // open 붙여 오버라이딩 가능한 함수 선언
        println("음식을 먹습니다")
    }
}

class Tiger : Animal2() {
    override fun eat() { // override 사용하여 재구현
        println("고기를 먹습니다")
    }
}

// 추상화
abstract class Animal3 {
    abstract fun eat()
    fun sniff() {
        println("킁킁")
    }
}

class Rabbit : Animal3() {
    override fun eat() {
        println("당근을 먹습니다")
    }
}

// 인터페이스(인터페이스에서는 open 안써도 된다)
interface Runner {
    fun run()
}

interface Eater {
    fun eat() {
        println("음식을 먹습니다.")
    }
}

class Dog2 : Runner, Eater {
    override fun run() {
        println("우다다다 뜁니다")
    }

    override fun eat() {
        println("허겁지겁 먹습니다")
    }
}