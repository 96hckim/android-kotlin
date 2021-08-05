package com.vutech.kotlinpractice.day6

import kotlinx.coroutines.*

fun main() {
    // Coroutine : 비동기로 여러 개의 루틴을 동시에 처리할 수 있는 방법
    // implementation 'org.jetbrains.kotlinx:kotlinx-coroutines-android:1.3.9'
    // import kotlinx.coroutines.*

    // GlobalScope : 프로그램 어디서나 제어, 동작이 가능한 기본 범위

    // CoroutineScope : 특정한 목적의 Dispatcher 를 지정하여 제어 및 동작이 가능한 범위
    // Dispatchers.Default : 기본적인 백그라운드 동작
    // Dispatchers.IO : I/O 에 최적화 된 동작
    // Dispatchers.Main : 메인(UI) 스레드에서 동작

    // launch : 반환값이 없는 Job 객체
    // async : 반환값이 있는 Deffered 객체

    // 제어되는 스코프 또는 프로그램 전체가 종료되면 함께 종료된다
    // runBlocking 사용하면 메인 스레드가 기다려준다 하지만 안드로이드에서는 ANR 에러 발생

    // delay : ms 단위로 루틴을 잠시 대기시키는 함수
    // Job.join() : Job 의 실행이 끝날때까지 대기하는 함수
    // Deffered.await() : Deffered 의 실행이 끝날때까지 대기하는 함수
    // 세 함수는 코루틴 내부 또는 runBlocking 같은 루틴의 대기가 가능한 구문 안에서만 사용 가능

    // cancel() :
    // 1. delay 또는 yield 함수가 사용된 위치까지 수행된 뒤 종료
    // 2. cancel() 로 인해 속성인 isActive 가 false 가 되므로 수동으로 종료할 수 있다.

    val scope = GlobalScope

    runBlocking {
        val a = scope.launch {
            for (i in 1..5) {
                println(i)
                delay(10)
            }
        }

        val b = async {
            "async 종료"
        }

        println("async 대기")
        println(b.await())

//        println("launch 대기")
//        a.join()
        println("launch 취소")
        a.cancel()
        println("launch 종료")
    }

    println()

    // withTimeoutOrNull() : 제한시간 내에 수행되면 결과값을 아닌 경우 null 을 반환하는 함수
    runBlocking {
        var result = withTimeoutOrNull(50) {
            for (i in 1..10) {
                println(i)
                delay(10)
            }
            "Finish"
        }

        println(result)
    }

}