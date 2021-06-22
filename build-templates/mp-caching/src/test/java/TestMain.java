import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.fairychar.caching.entity.Customer;
import com.fairychar.caching.pojo.dto.CustomerDTO;
import com.google.common.collect.Lists;
import org.junit.Test;

import java.util.stream.Collectors;

/**
 * Datetime: 2021/6/21 16:43 <br>
 *
 * @author chiyo <br>
 * @since 1.0
 */
public class TestMain {

    @Test
    public void testPage(){
        Page page = new Page<>();
        page.setRecords(Lists.asList(new Customer().setId(1),new Customer().setId(2),new Customer[]{}));

        System.out.println(page);
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