package kz.pashim

import java.util.function.Predicate

class KMadness<T1>(madness: T1){

    var madness: T1 = madness

    fun hello2(t: Predicate<in T1>) {
        t.negate()
    }

    companion object {

        fun <U> hello(t: U) where U : Sad, U : Devil {
            t.sayDevil()
        }
    }
}

open class KSad {
    fun saySad() {
        println("Sad")
    }
}

class KSadChild : KSad(), Devil {
    fun saySamsa() {
        println("Samsa")
    }

    override fun sayDevil() {
        println("Devil from sad child")
    }
}

class KExperimentalClass {
    fun run() {
        copy(KMadness(KSadChild()), KMadness(KSad()))
    }

    private fun copy(from: KMadness<KSad>, to: KMadness<KSad>) {
        to.madness = from.madness
        from.madness = to.madness
    }
}
