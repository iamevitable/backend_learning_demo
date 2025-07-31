package demo_test;

import java.util.HashMap;

public class string_test {
    public static void main(String[] args) {
        String a = new String("han");
        String b = new String("han");
        HashMap<String, Integer> hashMap = new HashMap<>();
        hashMap.put(a, 1);
        System.out.println(hashMap.get("han"));
        hashMap.put("han", 2);
        System.out.println(hashMap.get("han"));
        System.out.println(a);
        System.out.println(b);
        System.out.println(a == b);
        System.out.println(a.hashCode());
        System.out.println(b.hashCode());
    }
}
