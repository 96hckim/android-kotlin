package com.vutech.kotlinpractice.day6

fun main() {
    // << 2배, >> 1/2
    // 최상위 비트(맨 왼쪽) 0이면 양수, 1이면 음수
    // shl : 부호 비트 제외 왼쪽으로 밀어준다, shr : 부호 비트 제외 오른쪽으로 밀어준다
    // ushr : 부호 비트 포함 오른쪽으로 밀어준다
    // and : 비트를 확인하는 용도, 비트를 0으로 만드는 용도
    // or : 비트를 1로 set 하는 용도
    // xor(같으면 0, 다르면 1) : 비트들이 같은지 비교하는 용도
    // inv() : 비트 반전
    var bitData: Int = 0b10000

    bitData = bitData or (1 shl 2) // 1을 2번 왼쪽으로 shift
    println(bitData.toString(2)) // 정수형의 경우 toString 통해 진법변환 가능

    var result = bitData and (1 shl 4)
    println(result.toString(2))

    println(result shr 4)

    bitData = bitData and ((1 shl 4).inv())
    println(bitData.toString(2))

    println((bitData xor (0b10100)).toString(2))
}