package com.vutech.kotlinpractice.day4

fun main() {
    // 이벤트 : 함수로 직접 요청하지 않았지만 시스템 또는 루틴에 의해서 발생하게 되는 동작들
    // 옵저버 패턴 : 이벤트가 발생할 때마다 즉각적으로 처리할 수 있도록 만드는 프로그래밍 패턴
    // 이벤트를 넘겨주는 행위를 callback
    EventPrinter().start()
    println()
    EventPrinter2().start()
}

// observer 혹은 listener 라고 부른다
interface EventListener {
    fun onEvent(count: Int)
}

class Counter(var listener: EventListener) {
    fun count() {
        for (i in 1..100) {
            if (i % 5 == 0) listener.onEvent(i)
        }
    }
}

class EventPrinter : EventListener {
    override fun onEvent(count: Int) {
        print("${count}-")
    }

    fun start() {
        val counter = Counter(this)
        counter.count()
    }
}

// 익명객체 : 이름이 없는 객체
class EventPrinter2 {
    fun start() {
        val counter = Counter(object : EventListener {
            override fun onEvent(count: Int) {
                print("${count}..")
            }
        })
        counter.count()
    }
}