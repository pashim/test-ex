package kz.pashim

import kz.pashim.stream.TestKotlinStream
import kz.pashim.stream.TestStream
import kz.pashim.threads.CoroutinesTest
import kz.pashim.threads.TestThreads
import kz.pashim.trywithres.TestTryWithRes

fun main(args: Array<String>) {
//    var obj: SomeObject = SomeObject("objw")
//    if (obj.javaClass.isAnnotationPresent(Experimentaaal::class.java)) {
//        val wd = obj.javaClass.getAnnotation(Experimentaaal::class.java).javaClass.getMethod("arg2")
//        println(wd.defaultValue)
//    }
//    print(obj.objectName)

//    ExperimentalClass().run()
//    KExperimentalClass().run()

//    ExperimentalClass().runLambda()
//    KotlinLambdaEx.test()
//    TestStream().run()
//    TestKotlinStream().run()
//    TestTryWithRes().run()
//    TestThreads().run()
    CoroutinesTest().run()
}

@Experimentaaal(arg1 = "dwdwdw", arg4 = ExperimentalClass::class)
@Deprecated("ds")
data class SomeObject(internal var objectName: String) : Esp()

sealed class Esp // extension of enum