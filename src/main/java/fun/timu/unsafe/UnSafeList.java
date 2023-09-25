package fun.timu.unsafe;

import java.util.*;
import java.util.concurrent.CopyOnWriteArrayList;

public class UnSafeList {
    public static void main(String[] args) {

//        List<String> list = new ArrayList<>();// 不安全
//        List<String> list = new Vector<>();
//        List<String> list = Collections.synchronizedList(new ArrayList<>());
        List<String> list = new CopyOnWriteArrayList<>();

        for (int i = 1; i <= 30; i++) {
            new Thread(() -> {
                list.add(UUID.randomUUID().toString().substring(0, 3));
                System.out.println(list);
            }, String.valueOf(i)).start();
        }
    }
}
