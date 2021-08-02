package com.vutech.kotlinpractice.day4

fun main() {
    // 제네릭 : 클래스나 함수에서 사용하는 자료형을 외부에서 지정할 수 있는 기능 (캐스팅 연산없이 사용가능)
    // 타입 파라미터에 특정 자료형이 할당되면 제네릭을 사용하는 모든 코드는 할당된 자료형으로 대체되어 컴파일
    UsingGeneric(A()).doShouting()
    UsingGeneric(B()).doShouting()
    UsingGeneric(C()).doShouting()
    // class UsingGeneric(val t: T) 와 같지만 캐스팅을 방지하여 성능을 더 높일 수 있다

    doShouting(B())
}

// 함수에도 사용 가능
fun <T : A> doShouting(t: T) {
    t.shout()
}

open class A {
    open fun shout() {
        println("A가 소리칩니다")
    }
}

class B : A() {
    override fun shout() {
        println("B가 소리칩니다")
    }
}

class C : A() {
    override fun shout() {
        println("C가 소리칩니다")
    }
}

// 특정한 수퍼 클래스를 상속받은 클래스 타입으로만 제한하려면 <T : SuperClass>
class UsingGeneric<T : A>(val t: T) {
    fun doShouting() {
        t.shout()
    }
}