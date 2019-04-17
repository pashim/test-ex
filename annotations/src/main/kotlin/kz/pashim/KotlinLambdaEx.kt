package kz.pashim

object KotlinLambdaEx {

    fun test() {
        println(Execute().exec(2.0, 4.3, ::plus))
        println(Execute().exec(1.0, 5.2, { a, b -> a - b }))
        println(Execute().exec(1.0, 5.2) { a, b -> a * b })
        println({ print("i can fly")})

        val flyable = { println("flykot") }
//        val flyable2: FlyableWithArg = { println("asd") }
        flyable.invoke()

        val lamb : (a: String, b: Int) -> String = { a,b -> "$b $a"}
        println(lamb.invoke("colors", 3))

    }

    fun plus(a: Double, b: Double): Double = a + b
}

class Execute {

    fun exec(a: Double, b: Double, ex: (a: Double, b: Double) -> Double): Double {
        return ex.invoke(a, b)
    }

    fun exec2(flyable: Flyable) {
        flyable.fly()
    }


}

interface Flyable {
    fun fly()
}

interface FlyableWithArg {
    fun fly(a: String)
}