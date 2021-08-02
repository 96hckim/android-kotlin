package com.vutech.kotlinpractice.day4

fun main() {
    /**
     * 다형성
     * 클래스의 상속관계에서 오는 인스턴스의 호환성을 활용할 수 있는 기능
     * 수퍼클래스가 같은 인스턴스를 한 번에 관리하거나 인터페이스를 구현하여 사용하는 코드에서도 사용
     */
    // Up-Casting : 상위 자료형인 수퍼클래스로 변환 ex. var a: Drink = Cola()
    // Down-Casting : Up-Casting 된 자료형을 하위 자료형으로 변환 (별도의 연산자 필요)
    // as : 호환되는 자료형으로 변환해주는 캐스팅 연산자 ex. a as Cola
    // is : 변수가 자료형에 호환되는지 체크 후 변환 (조건문 내에서만 사용 가능) if(a is Cola) { }
    var a = Drink()
    a.drink()

    var b: Drink = Cola()
    b.drink()
    // Up-Casting 된 객체이므로 b.washDishes() 함수 호출 불가

    if (b is Cola) { // is 는 조건문 안에서만 잠시 Down-Casting 된다
        b.washDishes()
    }

    var c = b as Cola
    c.washDishes()
    b.washDishes() // as 사용 시 반환값 뿐만아니라 변수 자체도 Down-Casting 되므로 사용 가능
}

open class Drink {
    var name = "음료"

    open fun drink() {
        println("${name}를 마십니다")
    }
}

class Cola : Drink() {
    var type = "콜라"

    override fun drink() {
        println("${name}중에 ${type}를 마십니다")
    }

    fun washDishes() {
        println("${type}로 설거지를 합니다")
    }
}