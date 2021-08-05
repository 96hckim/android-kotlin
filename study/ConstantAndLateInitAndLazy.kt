package com.vutech.kotlinpractice.day6

fun main() {
    // val : 할당된 객체를 바꿀 수 없을 뿐이지 객체 속성은 변경할 수 있다
    // 상수 : 절대 변경 불가, 기본 자료형만 사용 가능, 클래스나 지역 변수로 사용 불가(companion object)
    val foodCourt = FoodCourt()
    foodCourt.searchPrice(FoodCourt.FOOD_CREAM_PASTA)
    foodCourt.searchPrice(FoodCourt.FOOD_STEAK)
    foodCourt.searchPrice(FoodCourt.FOOD_PIZZA)

    // lateinit : 변수만 선언하고 초기값 설정은 나중에 할 수 있도록 도와주는 키워드
    // lateinit var : 초기값 할당 전까지 변수를 사용할 수 없다(에러 발생), 기본 자료형에 사용 불가
    // 초기화 여부 확인 방법 : ::a.isInitialized
    val a = LateInitSample()

    println(a.getLateInitText())
    a.text = "새로 할당한 값"
    println(a.getLateInitText())

    // lazy : 마지막으로 변수를 사용하는 시점까지 초기화를 자동으로 늦춰주는 속성 ex. val a: Int by lazy{7}
    val number: Int by lazy {
        println("초기화를 합니다")
        7
    }

    println("코드를 시작합니다")
    println(number)
    println(number)
}

class FoodCourt {
    fun searchPrice(foodName: String) {
        val price = when (foodName) {
            FOOD_CREAM_PASTA -> 13000
            FOOD_STEAK -> 25000
            FOOD_PIZZA -> 15000
            else -> 0
        }

        println("${foodName}의 가격은 ${price}원 입니다")
    }

    companion object {
        const val FOOD_CREAM_PASTA = "크림파스타"
        const val FOOD_STEAK = "스테이크"
        const val FOOD_PIZZA = "피자"
    }
}

class LateInitSample {
    lateinit var text: String

    fun getLateInitText(): String {
        return if (::text.isInitialized) return text else "기본값"
    }
}