import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

class HoldResource implements Runnable{
    private String lockA;
    private String lockB;

    public HoldResource(String lockA, String lockB) {
        this.lockA = lockA;
        this.lockB = lockB;
    }


    @Override
    public void run() {
        synchronized (lockA){
            System.out.println(Thread.currentThread().getName() + "拿着" + lockA +  "想" + lockB);

            try {
                TimeUnit.SECONDS.sleep(2);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            synchronized (lockB){
                System.out.println(Thread.currentThread().getName() + "拿着" + lockB + "想" + lockA);
            }
        }

    }
}

public class DeadLockDemo {
    public static void main(String[] args) {
        String lockA = "lockA";
        String lockB = "lockB";

        new Thread(new HoldResource(lockA,lockB), "AAA").start();
        new Thread(new HoldResource(lockB,lockA), "BBB").start();

    }
}
