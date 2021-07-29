package com.vutech.kotlinpractice.day2

fun main() {
    // 상속이 필요한 경우
    // 1. 이미 존재하는 클래스를 확장하여 새로운 속성이나 함수를 추가하는 클래스를 만들어야할 때
    // 2. 여러 개의 클래스를 만들었는데 클래스들의 공통점을 뽑아 코드 관리를 편하게할 때
    // 물려주는 쪽 : 수퍼 클래스, 물려받는 쪽 : 서브 클래스
    var a = Animal("별이", 5, "개")
    var b = Dog("별이", 5)

    a.introduce()
    b.introduce()

    b.bark()

    var c = Cat("루이", 1)

    c.introduce()
    c.meow()
}

// open 붙여야 상속 가능한 클래스로 변경된다
open class Animal(var name: String, var age: Int, var type: String) {
    fun introduce() {
        println("저는 ${type} ${name}이고, ${age}살 입니다.")
    }
}

// 서브 클래스 생성 시 반드시 수퍼클래스의 생성자까지 호출되어야 한다
// 수퍼 클래스에 존재하는 속성과 같은 이름의 속성을 가질 수 없다
class Dog(name: String, age: Int) : Animal(name, age, "개") {
    fun bark() {
        println("멍멍")
    }
}

class Cat(name: String, age: Int) : Animal(name, age, "고양이") {
    fun meow() {
        println("야옹")
    }
}