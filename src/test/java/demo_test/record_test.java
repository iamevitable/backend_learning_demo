package demo_test;

/**
 * @Desc * @param
 * @return
 * @date:
 * @Author
 */
public class record_test {
    public static void main(String[] args) {
        final record User(String name, int age) {

        }
        User user = new User("han", 10);
        System.out.println(user.age);
    }
}
