package kz.pashim.trywithres

import java.io.ByteArrayOutputStream
import java.io.OutputStream

class TestKotlinTryWithResources {

    fun run() {

        val st: OutputStream = ByteArrayOutputStream()
        st.use {
            // as try with resources
            it.write(12)
        }
    }
}