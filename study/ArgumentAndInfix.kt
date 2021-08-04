package com.vutech.kotlinpractice.day5

fun main() {
    read(7)
    read("감사합니다")

    deliveryItem("짬뽕")
    deliveryItem("책", 3)
    deliveryItem("노트북", 30, "학교")

    // named argument : 변수명 지정해서 넣을 수 있다
    deliveryItem("선물", destination = "친구집")

    sum(1, 2, 3, 4)
    sample("sample", 1, 2, 3, 4)

    // this : 6, x : 4
    println(6 multiply 4)
    // 위와 동일
    println(6.multiply(4))
}

// overloading
fun read(x: Int) {
    println("숫자 $x 입니다")
}

fun read(x: String) {
    println(x)
}

// default argument
fun deliveryItem(name: String, count: Int = 1, destination: String = "집") {
    println("${name}, ${count}개를 ${destination}에 배달하였습니다")
}

// vararg : 같은 자료형을 개수에 상관없이 파라미터로 받고 싶을 때 사용
fun sum(vararg numbers: Int) {
    var sum = 0

    // 배열처럼 for 문 참조 가능
    for (n in numbers) {
        sum += n
    }

    print(sum)
}

// vararg 는 다른 파라미터와 사용 시 맨 뒤에 입력해주어야 한다
fun sample(test: String, vararg x: Int) {}

// infix : 함수를 연산자처럼 쓸 수 있도록 해준다
// 함수 이름을 infix 함수가 적용될 자료형.이름으로 지정
infix fun Int.multiply(x: Int): Int = this * x