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
        throw new RuntimeException();
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