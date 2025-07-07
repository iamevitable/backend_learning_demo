package demo_test;

public class string_test {
    public static void main(String[] args) {
        String a = new String("han");
        String b = new String("han");
        System.out.println(a == b);
        System.out.println(a.hashCode());
        System.out.println(b.hashCode());
    }
}
