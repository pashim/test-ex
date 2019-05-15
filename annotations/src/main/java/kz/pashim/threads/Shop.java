package kz.pashim.threads;

import java.util.ArrayList;
import java.util.List;

public class Shop {

    private List<String> iphones = new ArrayList<>();

    public Shop() {
        iphones.add("Iphone1");
        iphones.add("Iphone2");
        iphones.add("Iphone3");
        iphones.add("Iphone4");
    }

    synchronized public void addIPhone() {
        System.out.println("Added new Iphone to list");
        iphones.add("IPhone" + iphones.size());
        notify();
    }

    synchronized public void buyPhone(String name) {
        if (iphones.size() <= 0) {
            try { wait(); } catch (Exception e) {}
        }
        System.out.println(name + " bought " + iphones.get(iphones.size() - 1));
        iphones.remove(iphones.size() - 1);
        System.out.println(iphones.size() + " Iphones left");
    }
}
