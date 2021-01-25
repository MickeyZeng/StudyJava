import org.junit.Test;

import java.util.concurrent.TimeUnit;

public class volatileTest {

    /**
     *
     * Volatile的可见性
     *
     * */
    @Test
    public void test1(){
        StudyVolatile studyVolatile = new StudyVolatile();

        new Thread(()->{
            System.out.println(Thread.currentThread().getName() + "is doing a jobs");
            try {
                TimeUnit.SECONDS.sleep(3);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            studyVolatile.addTO60();

            System.out.println(Thread.currentThread().getName() + "update the number " + studyVolatile.test);
        }, "AAA").start();

        while(studyVolatile.test == 0){

        }

        System.out.println(Thread.currentThread().getName() + "Mission is over!");
    }

    /**
     *
     * Volatile的不保证原子性
     *
     * */
    @Test
    public void test2(){
        StudyVolatile studyVolatile = new StudyVolatile();

        for (int i = 0; i < 20; i++) {
            new Thread(()->{
                for (int j = 0; j < 1000; j++) {
                    studyVolatile.addPlus();
                }
            },String.valueOf(i)).start();
        }

        try {
            TimeUnit.SECONDS.sleep(5);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        while (Thread.activeCount() > 2){
            Thread.yield();
        }

        System.out.println(Thread.currentThread().getName() + " Mission Complete" + studyVolatile.test);
    }

    public class StudyVolatile{
        volatile int test = 0;

        public void addTO60(){
            this.test = 60;
        }

        public void addPlus(){
            this.test++;
        }
    }


}
