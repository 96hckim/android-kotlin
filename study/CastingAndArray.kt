package com.vutech.kotlinpractice

fun main() {
    // 명시적 형변환 : toLong(), toByte(), toInt() 등
    // 암시적 형변환 지원 안한다.
    var a: Int = 54321
    var b: Long = a.toLong()

    // 배열 : arrayOf
    var intArr = arrayOf(1, 2, 3, 4, 5)
    // 특정한 크기를 가졌고 null 로 채워진 배열 : arrayOfNulls<Int>(5)
    var nullArr = arrayOfNulls<Int>(5)
    // 배열 사용
    intArr[2] = 8
    println(intArr[2])
    // * 배열은 처음 선언했을 때의 크기는 변경할 수 없지만 다른 자료구조보다 빠른 입출력 가능 *
}