package kz.pashim;

import java.util.ArrayList;
import java.util.List;

public class ExperimentalClass {

    public void run() {
//        Madness madness = new Madness<>();
//        Madness.hello(new SadChild());
//        Madness<? super SadChild> madness1 = new Madness<Sad>();
//        madness1.getMadness();
//
//        List<Number> strs = new ArrayList<>();
//        strs.add(Integer.valueOf(2));
    }

    public void runLambda() {
        Devill dev = () -> {
            return "im devil";
        };
        System.out.println(dev.sayDevil());
        System.out.println(isDevil(() -> "im devill"));

        Devil3 dev2 = ExperimentalClass::printDevil;
        dev2.sayDevil("devil3");
    }

    private Boolean isDevil(Devill dev) {
        if(dev.sayDevil().contains("devil")) {
            return true;
        } else {
            return false;
        }
    }

    private static void printDevil(String str) {
        System.out.println(str);
    }
}