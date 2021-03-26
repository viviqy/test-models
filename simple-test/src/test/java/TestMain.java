import com.fairychar.test.simple.pojo.Customer;
import org.junit.Test;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.atomic.AtomicBoolean;

/**
 * Datetime: 2020/12/15 11:48 <br>
 *
 * @author chiyo <br>
 * @since 1.0
 */
public class TestMain {

    @Test
    public void testLockSequence(){
        final Object lock=new Object();
        Thread[] threads = new Thread[10];
        for (int i = 0; i < 10; i++) {
            threads[i]=new Thread(()->{
                System.out.println(Thread.currentThread()+" in");
                synchronized (lock) {
                    try {
                        Thread.sleep(200);
                    } catch (InterruptedException e) {
                    }
                    System.out.println(Thread.currentThread()+" out");
                }
            });
        }
        synchronized (lock){

        }
    }

    @Test
    public void testLock() throws InterruptedException {
        final Object LOCK = new Object();
        int N = 10;
        Thread[] threads = new Thread[10];
        for (int i = 0; i < N; i++) {
            threads[i] = new Thread(() -> {
                System.out.println(Thread.currentThread().getName() + " begin...");
                synchronized (LOCK) {
                    System.out.println(Thread.currentThread().getName() + "get sync lock!");
                    try {
                        Thread.sleep(100);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }, "thread [" + i + "]");
        }
        synchronized (LOCK) {
            for (int i = 0; i < N; i++) {
                threads[i].start();
                System.out.println(threads[i].getName() + " start!");
                Thread.sleep(200);
            }
            Thread.sleep(200);
        }
        System.out.println("all started");
        Thread.currentThread().join();
    }

    @Test
    public void testClone() throws Exception {
        Customer c1 = new Customer();
        Customer clone = (Customer) c1.getClass().getDeclaredMethod("clone").invoke(c1, null);
        System.out.println(c1);
        System.out.println(clone);
        System.out.println(c1);
        Customer c2 = new Customer();
        System.out.println(c2);


        Customer c3 = c2;
        System.out.println(c3);
        c1.id = 1;
        clone.id = 2;
        System.out.println(c1.id);
        System.out.println(clone.id);
    }

    @Test
    public void test1() {
        AtomicBoolean nothingCanChangeMyLove = new AtomicBoolean();
        CompletableFuture.supplyAsync(() -> {
            System.out.println("when i first see u");
            nothingCanChangeMyLove.set(true);
            return "i want to meet u again";
        }).thenCombine(CompletableFuture.allOf(
                CompletableFuture.runAsync(() -> learnMuchMoreClasses())
                        .thenRun(() -> learnHowToNiceToPeople())
                        .thenRun(() -> improveMyself())
                , CompletableFuture.runAsync(() -> doSomethingToKnowYou())
                        .thenRun(() -> makePlanToMeetYou())
                        .thenRun(() -> doMyPlan())
        ), (t1, t2) -> t1).exceptionally(t -> "best wishes to u")
                .whenComplete((r, t) -> System.out.println("i love u forever,i promise"));
    }

    private void doMyPlan() {
    }

    private void makePlanToMeetYou() {
    }

    private void improveMyself() {
    }

    private void learnHowToNiceToPeople() {
    }

    private void learnMuchMoreClasses() {

    }

    private void doSomethingToKnowYou() {

    }
}
/*
                                      /[-])//  ___        
                                 __ --\ `_/~--|  / \      
                               /_-/~~--~~ /~~~\\_\ /\     
                               |  |___|===|_-- | \ \ \    
____________ _/~~~~~~~~|~~\,   ---|---\___/----|  \/\-\   
____________ ~\________|__/   / // \__ |  ||  / | |   | | 
                      ,~-|~~~~~\--, | \|--|/~|||  |   | | 
                      [3-|____---~~ _--'==;/ _,   |   |_| 
                                  /   /\__|_/  \  \__/--/ 
                                 /---/_\  -___/ |  /,--|  
                                 /  /\/~--|   | |  \///   
                                /  / |-__ \    |/         
                               |--/ /      |-- | \        
                              \^~~\\/\      \   \/- _     
                               \    |  \     |~~\~~| \    
                                \    \  \     \   \  | \  
                                  \    \ |     \   \    \ 
                                   |~~|\/\|     \   \   | 
                                  |   |/         \_--_- |\
                                  |  /            /   |/\/
                                   ~~             /  /    
                                                 |__/   W<

*/