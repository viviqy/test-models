import com.fairychar.netty.server.handler.AsyncLongWaitHandler;
import com.fairychar.netty.server.handler.LongWaitHandler;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.string.StringDecoder;
import io.netty.handler.codec.string.StringEncoder;
import io.netty.handler.logging.LoggingHandler;
import org.junit.Test;

/**
 * Datetime: 2021/6/21 11:20 <br>
 *
 * @author chiyo <br>
 * @since 1.0
 */
public class TestMain {

    @Test
    public void testWorkerThreads() {
        NioEventLoopGroup boss = new NioEventLoopGroup(1);
        //当worker=1时,可以接收多个链接,但是会阻塞所有连接的请求,将会顺序执行
        //当worker=2时,可以接收多个链接,当2个连接时,A连接挂到t1,B连接挂到t2,AB之前相互不阻塞,但是AB内部会阻塞
        NioEventLoopGroup worker = new NioEventLoopGroup(1);
        try {
            ServerBootstrap serverBootstrap = new ServerBootstrap();
            serverBootstrap.group(boss, worker)
                    .channel(NioServerSocketChannel.class)
                    .option(ChannelOption.SO_BACKLOG, 1)
                    .handler(new LoggingHandler())
                    .childHandler(new ChannelInitializer<SocketChannel>() {
                        @Override
                        protected void initChannel(SocketChannel ch) throws Exception {
                            StringDecoder stringDecoder = new StringDecoder();
                            StringEncoder stringEncoder = new StringEncoder();
                            LongWaitHandler longWaitHandler = new LongWaitHandler(60);
                            AsyncLongWaitHandler asyncLongWaitHandler = new AsyncLongWaitHandler(1);
                            ch.pipeline().addLast(stringDecoder)
                                    .addLast(stringEncoder)
//                                    .addLast(longWaitHandler)
                                    .addLast(asyncLongWaitHandler)
                            ;
                        }
                    })
                    .bind(10000)
                    .channel().closeFuture().sync();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            boss.shutdownGracefully();
            worker.shutdownGracefully();
        }
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