package kz.pashim;

public class SadChild extends Sad implements Devil {
    public void saySamsa() {
        System.out.println("Samsa");
    }

    @Override
    public void sayDevil() {
        System.out.println("Devil from sad child");
    }
}
