import com.sun.source.tree.SynchronizedTree;
import org.w3c.dom.ls.LSOutput;

import java.sql.Time;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicReference;

public class SpinLock {

    private AtomicReference<Thread> atomicReference = new AtomicReference<>();

    public void myLock() {
        Thread thread = Thread.currentThread();
        System.out.println(Thread.currentThread().getName() + "\t invoked myLock!");
        while (!atomicReference.compareAndSet(null, thread)) {

        }
    }

    public void unLock() {
        Thread thread = Thread.currentThread();
        System.out.println(Thread.currentThread().getName() + "\t invoked nulock！");
        atomicReference.compareAndSet(thread, null);
    }

    public static void main(String[] args) throws InterruptedException {
        SpinLock spinLock = new SpinLock();
        new Thread(() -> {
            spinLock.myLock();
            try {
                TimeUnit.SECONDS.sleep(5);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            spinLock.unLock();
        }, "t1").start();

        TimeUnit.SECONDS.sleep(1);

        new Thread(() -> {
            spinLock.myLock();
//            try {
//                TimeUnit.SECONDS.sleep(1);
//            } catch (InterruptedException e) {
//                e.printStackTrace();
//            }
            spinLock.unLock();
        }, "t2").start();


    }
}
