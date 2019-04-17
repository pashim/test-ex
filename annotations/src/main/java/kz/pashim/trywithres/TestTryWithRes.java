package kz.pashim.trywithres;

import java.io.ByteArrayOutputStream;
import java.io.OutputStream;

public class TestTryWithRes {

    public void run() {

//        OutputStream stream = new ByteArrayOutputStream();
        try(OutputStream stream = new ByteArrayOutputStream()) {
            System.out.println("in try");
            int a = 2 / 0;
        } catch (Exception e) {
            System.out.println("in catch");
        }
    }
}
