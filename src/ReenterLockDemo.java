import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

class phone implements Runnable{
    phone() {
    }

    synchronized void sendSMS(){
        System.out.print(Thread.currentThread().getName() + "invoked SMS.");
        sendEmail();
    }

    synchronized void sendEmail(){
        System.out.println(Thread.currentThread().getName() + "invoked email.");
    }

    private Lock lock = new ReentrantLock();
    @Override
    public void run() {
        get();
    }

    public void get(){
        lock.lock();
        try {
            System.out.println(Thread.currentThread().getName() + "Invoked get()");
            set();
        }catch (Exception e){
            System.out.println(e);
        }finally {
            lock.unlock();
        }

    }

    public void set(){
        lock.lock();
        try {
            System.out.println(Thread.currentThread().getName() + "Invoked set()");
        }catch (Exception e){
            System.out.println(e);
        }finally {
            lock.unlock();
        }
    }
}

public class ReenterLockDemo {

    public static void main(String[] args) throws InterruptedException {
        phone iphone = new phone();
        new Thread(iphone::sendSMS,"t1").start();

        new Thread(iphone::sendEmail, "t2").start();

        TimeUnit.SECONDS.sleep(2);

        Thread t1 = new Thread(iphone);
        Thread t2 = new Thread(iphone);

        t1.start();
        t2.start();

    }
}
