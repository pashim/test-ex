package kz.pashim

fun main(args: Array<String>) {
    var obj: SomeObject = SomeObject("objw")
    if (obj.javaClass.isAnnotationPresent(Experimentaaal::class.java)) {
        val wd = obj.javaClass.getAnnotation(Experimentaaal::class.java).javaClass.getMethod("arg2")
        println(wd.defaultValue)
    }
    print(obj.objectName)
}

@Experimentaaal(arg1 = "dwdwdw", arg4 = ExperimentalClass::class)
@Deprecated("ds")
data class SomeObject(internal var objectName: String)