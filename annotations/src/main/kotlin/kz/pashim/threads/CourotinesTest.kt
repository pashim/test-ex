package kz.pashim.threads

import kotlinx.coroutines.*
import kotlin.concurrent.thread
import kotlin.coroutines.Continuation
import kotlin.coroutines.resume
import kotlin.coroutines.suspendCoroutine
import kotlin.system.measureTimeMillis

class CoroutinesTest {

    fun run() {
//        testLaunch()
//        testCoroutineScope()
//        test2()
//        testCancel()
//        testCancel2()
//        testCancel3()
//        testTimeout()
//        testAsync()
//        testAsyncCancel()
//        dispatcherTest()
//        debugCoroutines()
//        testCancelChildren()
//        testCoroutineVsThread()
        continuation()
//        sequence()
    }

    fun testLaunch() {
//        GlobalScope.launch { // launch a new coroutine in background and continue
//            delay(1000L) // non-blocking delay for 1 second (default time unit is ms)
//            println("World!") // print after delay
//        }
//        print("Hello,") // main thread continues while coroutine is delayed
//        Thread.sleep(2000L) // block main thread for 2 seconds to keep JVM alive
//
        runBlocking {
            val job = GlobalScope.launch {
                // launch a new coroutine in background and continue
                delay(1000L)
                println("World!")
                testSuspend()
            }
            println("Hello,") // main thread continues here immediately
//        runBlocking {     // but this expression blocks the main thread
////            delay(2000L)  // ... while we delay for 2 seconds to keep JVM alive
////        }
            job.join()

            val job2 = launch {
                delay(1000L)
                println("Im not daemon!")
            }
        }

    }

    suspend fun testSuspend() {
        delay(500)
        println("im suspend fun")
    }

    fun testCoroutineScope() = runBlocking { // this: CoroutineScope
        launch {
            delay(200L)
            println("Task from runBlocking")
        }

        coroutineScope { // Creates a coroutine scope
            launch {
                delay(500L)
                println("Task from nested launch")
            }

            delay(100L)
            println("Task from coroutine scope") // This line will be printed before the nested launch
        }

        println("Coroutine scope is over") // This line is not printed until the nested launch completes
    }

    fun test2() = runBlocking {
        repeat(100_000) { // launch a lot of coroutines
            launch {
                delay(1000L)
                print(".")
            }
        }
    }

    fun testCancel() = runBlocking {
        val job = launch {
            repeat(1000) { i ->
                println("job: I'm sleeping $i ...")
                delay(500L)
            }
        }
        delay(1300L) // delay a bit
        println("main: I'm tired of waiting!")
        job.cancel() // cancels the job
        job.join() // waits for job's completion
        println("main: Now I can quit.")
    }

    fun testCancel2() = runBlocking {
        val startTime = System.currentTimeMillis()
        val job = launch(Dispatchers.Default) {
            var nextPrintTime = startTime
            var i = 0
            while (isActive) { // cancellable computation loop
                // print a message twice a second
                if (System.currentTimeMillis() >= nextPrintTime) {
                    println("job: I'm sleeping ${i++} ...")
                    nextPrintTime += 500L
                }
            }
        }
        delay(1300L) // delay a bit
        println("main: I'm tired of waiting!")
        job.cancelAndJoin() // cancels the job and waits for its completion
        println("main: Now I can quit.")
    }

    fun testCancel3() = runBlocking {
        val job = launch {
            try {
                repeat(1000) { i ->
                    println("job: I'm sleeping $i ...")
                    delay(500L)
                }
            } finally {
                withContext(NonCancellable) {
                    println("job: I'm running finally")
                    delay(1000L)
                    println("job: And I've just delayed for 1 sec because I'm non-cancellable")
                }
            }
        }
        delay(1300L) // delay a bit
        println("main: I'm tired of waiting!")
        job.cancelAndJoin() // cancels the job and waits for its completion
        println("main: Now I can quit.")
    }

    fun testTimeout() = runBlocking {
        val result = withTimeoutOrNull(1300L) {
            repeat(1000) { i ->
                println("I'm sleeping $i ...")
                delay(500L)
            }
            "Done" // will get cancelled before it produces this result
        }
        println("Result is $result")

        withTimeout(1300L) {
            repeat(1000) { i ->
                println("I'm sleeping $i ...")
                delay(500L)
            }
        }
    }

    fun testAsync() = runBlocking {
        val time = measureTimeMillis {
            val one = async { doSomethingUsefulOne() }
            val two = async { doSomethingUsefulTwo() }
            println("The answer is ${one.await() + two.await()}")
        }
        println("Completed in $time ms")
    }

    suspend fun doSomethingUsefulOne(): Int {
        delay(1000L) // pretend we are doing something useful here
        return 13
    }

    suspend fun doSomethingUsefulTwo(): Int {
        delay(1000L) // pretend we are doing something useful here, too
        return 29
    }

    fun testAsyncCancel() = runBlocking<Unit> {
        try {
            failedConcurrentSum()
        } catch(e: ArithmeticException) {
            println("Computation failed with ArithmeticException")
        }
    }

    suspend fun failedConcurrentSum(): Int = coroutineScope {
        val one = async<Int> {
            try {
                delay(Long.MAX_VALUE) // Emulates very long computation
                42
            } finally {
                println("First child was cancelled")
            }
        }
        val two = async<Int> {
            println("Second child throws an exception")
            throw ArithmeticException()
        }
        one.await() + two.await()
    }

    fun dispatcherTest() = runBlocking {
        launch { // context of the parent, main runBlocking coroutine
            println("main runBlocking      : I'm working in thread ${Thread.currentThread().name}")
        }
        launch(Dispatchers.Unconfined) { // not confined -- will work with main thread
            println("Unconfined            : I'm working in thread ${Thread.currentThread().name}")
        }
        launch(Dispatchers.Default) { // will get dispatched to DefaultDispatcher
            println("Default               : I'm working in thread ${Thread.currentThread().name}")
        }
        launch(newSingleThreadContext("MyOwnThread")) { // will get its own new thread
            println("newSingleThreadContext: I'm working in thread ${Thread.currentThread().name}")
        }

        launch(Dispatchers.Unconfined) { // not confined -- will work with main thread
            println("Unconfined      : I'm working in thread ${Thread.currentThread().name}")
            println("Unconfined      : After delay in thread ${Thread.currentThread().name}")
            println("Unconfined      : After delay in thread ${Thread.currentThread().name}")
            println("Unconfined      : After delay in thread ${Thread.currentThread().name}")
            delay(100)
            println("Unconfined      : After delay in thread ${Thread.currentThread().name}")
        }
        launch { // context of the parent, main runBlocking coroutine
            println("main runBlocking: I'm working in thread ${Thread.currentThread().name}")
            println("main runBlocking: After delay in thread ${Thread.currentThread().name}")
            println("main runBlocking: After delay in thread ${Thread.currentThread().name}")
            println("main runBlocking: After delay in thread ${Thread.currentThread().name}")
            println("main runBlocking: After delay in thread ${Thread.currentThread().name}")
            delay(100)
            println("main runBlocking: After delay in thread ${Thread.currentThread().name}")
        }
    }

    fun debugCoroutines() {
        newSingleThreadContext("Ctx1").use { ctx1 ->
            newSingleThreadContext("Ctx2").use { ctx2 ->
                runBlocking(ctx1) {
                    println("Started in ctx1")
                    withContext(ctx2) {
                        println("Working in ctx2")
                    }
                    println("Back to ctx1")
                }
            }
        }
    }

    fun testCancelChildren() = runBlocking {
        // launch a coroutine to process some kind of incoming request
        val request = launch {
            // it spawns two other jobs, one with GlobalScope
            GlobalScope.launch {
                println("job1: I run in GlobalScope and execute independently! ${Thread.currentThread().name}")
                delay(1000)
                println("job1: I am not affected by cancellation of the request ${Thread.currentThread().name}")
            }
            // and the other inherits the parent context
            launch {
                delay(100)
                println("job2: I am a child of the request coroutine ${Thread.currentThread().name}")
                delay(1000)
                println("job2: I will not execute this line if my parent request is cancelled ${Thread.currentThread().name}")
            }
        }
        delay(500)
        request.cancel() // cancel processing of the request
        delay(1000) // delay a second to see what happens
        println("main: Who has survived request cancellation? ${Thread.currentThread().name}")
    }

    fun testCoroutineVsThread() {
        println("coroutine time: " + measureTimeMillis {
            runBlocking {
                val jobs = List(10_000) {
                    launch {
                        delay(1000)
                    }
                }
                jobs.forEach{ it.join() }
            }
        })

        println("thread time: " + measureTimeMillis {
            runBlocking {
                val jobs = List(10_000) {
                    thread {
                        Thread.sleep(1000)
                    }
                }
                jobs.forEach{ it.join() }
            }
        })


    }

    fun continuation() = runBlocking {
        var continuation: Continuation<Unit>? = null
        GlobalScope.launch(Dispatchers.Unconfined) {
            println("Suspending")
            suspendCoroutine<Unit> { cont ->
                println("in suspend block")
            }
            println("Resumed!")
        }
        println("After launch")
        println("After continuation.resume(Unit)")
    }

    fun sequence() {
        val sequence = sequence {
            println("One")
            yield(1)
            println("Two")
            yield(2)
            println("Three")
            yield(3)
        }

        sequence.take(2).forEach {
            println(it)
        }
    }

}