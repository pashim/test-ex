package kz.pashim.trywithres;

import java.io.ByteArrayOutputStream;
import java.io.OutputStream;

public class TestTryWithRes {

    public void run() {

//        OutputStream stream = new ByteArrayOutputStream();
//        try(OutputStream stream = new ByteArrayOutputStream()) {
//            System.out.println("in try");
//            int a = 2 / 0;
//        } catch (Exception e) {
//            System.out.println("in catch");
//        }
        int a =  0b1010_0001_1010_0001_1010_0001_1010_0001; // -95

        System.out.println(Integer.toBinaryString(a) + " " + a);
        System.out.println(Integer.toBinaryString(a>>>1) + " " + (a>>>1));
        System.out.println(Integer.toBinaryString(a>>1) + " " + (a>>1));

    }
}
