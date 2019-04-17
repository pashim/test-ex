package kz.pashim.stream;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

public class TestStream {

    public void run() {

        List<String> list = new ArrayList<>();
        for (int i = 0; i < 10000000; i++) {
            list.add(String.valueOf("String_" + i));
        }
        long startTime = System.currentTimeMillis();
        Stream<String> stream = list.stream()
                .filter(s -> {
                    return s.startsWith("String");
                })
                .peek(s -> {
                    s = s + "smt";
                });

        System.out.println("Starting iterate list stream...");
        System.out.println(stream.noneMatch(s -> s.equals("asd")));
        System.out.println("Stream end with time: " + (System.currentTimeMillis() - startTime));

        startTime = System.currentTimeMillis();
        Stream<String> parallelStream = list.parallelStream()
                .filter(s -> {
                    return s.startsWith("String");
                })
                .peek(s -> {
                    s = s + "smt";
                });
        System.out.println("Starting iterate list parallel stream...");
        System.out.println(parallelStream.noneMatch(s -> s.equals("asd")));
        System.out.println("Parallel Stream end with time: " + (System.currentTimeMillis() - startTime));

        startTime = System.currentTimeMillis();
        Stream<String> stream2 = list.stream()
                .filter(s -> {
                    Integer z = Integer.parseInt(s.substring("String_".length()));
                    return z % 2 == 0;
                })
                .map(s -> {
                    Integer z = Integer.parseInt(s.substring("String_".length()));
                    return s + z;
                });

        System.out.println("Starting iterate list stream2...");
        System.out.println(stream2.noneMatch(s -> s.equals("asd")));
        System.out.println("Stream2 end with time: " + (System.currentTimeMillis() - startTime));

        startTime = System.currentTimeMillis();
        Stream<String> parallelStream2 = list.parallelStream()
                .filter(s -> {
                    Integer z = Integer.parseInt(s.substring("String_".length()));
                    return z % 2 == 0;
                })
                .map(s -> {
                    Integer z = Integer.parseInt(s.substring("String_".length()));
                    return s + z;
                });
        System.out.println("Starting iterate list parallel stream2...");
        System.out.println(parallelStream2.noneMatch(s -> s.equals("asd")));
        System.out.println("Parallel Stream2 end with time: " + (System.currentTimeMillis() - startTime));

        System.out.println("----------------------------------------");
    }

}
