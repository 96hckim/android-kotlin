package com.vutech.kotlinpractice.day5

fun main() {
    // 중첩 클래스 : 클래스 안에 클래스를 중첩해서 사용 가능 (외부 클래스의 내용을 공유할 수 없다)
    // 내부 클래스 : 외부 클래스의 속성과 함수의 사용 가능
    Outer.Nested().introduce()

    val outer = Outer()
    val inner = outer.Inner()

    inner.introduceInner()
    inner.introduceOuter()

    outer.text = "Changed Outer Class"
    inner.introduceOuter()
}

class Outer {
    var text = "Outer Class"

    class Nested {
        fun introduce() {
            println("Nested Class")
        }
    }

    inner class Inner {
        var text = "Inner Class"

        fun introduceInner() {
            println(text)
        }

        fun introduceOuter() {
            println(this@Outer.text)
        }
    }
}