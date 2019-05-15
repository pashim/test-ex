package kz.pashim.stream

import java.util.ArrayList
import kotlin.streams.asSequence
import kotlin.system.measureTimeMillis

class TestKotlinStream {

    fun run() {
        val list = ArrayList<String>()
        for (i in 0..5) {
            list.add("String2s_$i")
        }
//        val stream = list.stream()
//                .filter {
//                    println("In list stream Filter")
//                    it.startsWith("String")
//                }
//                .peek {
//                    println("in peek stream filter")
//                    it + "2ads"
//                }
//
//        println("Starting iterate kotlin stream")
//        println("End iteration with time: " + measureTimeMillis {
//            stream.noneMatch{ it == "asd" }
//        })
//
//        println("Using inline operations in collection")
//        println("End iteration with time: " + measureTimeMillis {
//            list.filter {
//                println("In list filter")
//                it.startsWith("String")
//            }.forEach {
//                println("In list foreach")
//            }
//        })
//
//        val sequence = list.asSequence()
//                .filter {
//                    it.startsWith("String")
//                }
//                .filter {
//                    it.startsWith("St")
//                }
//        println("Starting iterate kotlin sequence")
//        println("End iteration with time: " + measureTimeMillis {
//            sequence.toList()
//        })

        println("----------------------------------")

        println("twoStepListProcessing: " + measureTimeMillis {
            list
                    .filter { println("in filter 1"); it.length < 20 }
                    .filter { println("in filter 2"); it.length > 2 }
                    .map { println("in filter 3");it + it.length }
        })

        println("twoStepSequenceProcessing: " + measureTimeMillis {
            list.asSequence()
                    .filter { println("in filter 1");it.length < 20 }
                    .filter { println("in filter 2");it.length > 2 }
                    .map { println("in filter 3");it + it.length }
                    .toList()
        })

        println("threeStepListProcessing: " + measureTimeMillis {
            list.filter { it.length < 20 }
                    .filter { it.length > 2 }
                    .map { it + it.length }
                    .sumBy { it.length }
        })

        println("threeStepSequenceProcessing: " + measureTimeMillis {
            list.asSequence().filter { it.length < 20 }
                    .filter { it.length > 2 }
                    .map { it + it.length }
                    .sumBy { it.length }
        })
    }

}