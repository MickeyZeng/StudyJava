import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

class Chef{
    private int cake = 0;
    Lock lock = new ReentrantLock();
    Condition condition = lock.newCondition();

    public Chef() {
    }

    public void increment(){
        lock.lock();
        try{
            while (cake != 0){
                condition.await();
            }
            cake++;
            System.out.println(Thread.currentThread().getName() + "\t" + cake);
            condition.signalAll();
        }catch (Exception e){
            System.out.println(e);
        }finally {
            lock.unlock();
        }
    }

    public void decrement(){
        lock.lock();
        try{
            while (cake == 0){
                condition.await();
            }
            cake--;
            System.out.println(Thread.currentThread().getName() + "\t" + cake);
            condition.signalAll();
        }catch (Exception e){
            System.out.println(e);
        }finally {
            lock.unlock();
        }
    }
}

public class CustomerAndProvider {
    public static void main(String[] args) {
        Chef chef;
        chef = new Chef();
        for (int i = 0; i < 5; i++) {
            new Thread(chef::increment,"AAA").start();
        }

        for (int i = 0; i < 5; i++) {
            new Thread(chef::decrement,"BBB").start();
        }
    }
}
