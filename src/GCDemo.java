import java.util.Random;

public class GCDemo {
    public static void main(String[] args) {
        System.out.println("GC Deno");
        try {
            String str = "Hello";
            while (true) {
                str += str + new Random().nextInt(7777777) + new Random().nextInt(8888888) + str.intern();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
