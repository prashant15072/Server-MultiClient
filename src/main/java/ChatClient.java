import io.netty.bootstrap.Bootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.util.internal.SystemPropertyUtil;

import java.io.*;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * Created by PRASHANT on 4/13/2017.
 */
public class ChatClient {
    private String host;
    private int port;
    private int id;
    public static boolean[] check=new boolean[100];

//    public static Lock lock=new ReentrantLock();
   /* public static void main(String[] args) throws Exception {
        new ChatClient("localhost",100,4).run();
    }
*/
    public static void checkInitialization(){
        for (int i=0;i<100;i++){
            check[i]=false;
        }
    }

    public ChatClient(String host ,int port,int id){
        this.host=host;
        this.port=port;
        this.id=id;
    }

    public void run() {
//        lock.lock();
        EventLoopGroup workerGroup=new NioEventLoopGroup();

        try{
            Bootstrap b=new Bootstrap()
                    .group(workerGroup)
                    .channel(NioSocketChannel.class)
                    .handler(new ChatClientInitializer(this.id,workerGroup));

            ChannelFuture d=b.connect(host,port).await();
            System.out.println("i'm trying to connect"+ id);
            if (!d.isSuccess()){
                System.out.println("i'm not connected"+ id);
                throw new Exception(String.format("Fail to bind on [host = %s , port = %d].", host, port, d.cause()));
            }
            Channel c=d.sync().channel();
            String str= "{\"_id\":\""+this.id+"\",\"Name\":\"ABC"+this.id+this.port+"\"}";
            InputStream is=new ByteArrayInputStream(str.getBytes());
            BufferedReader in =new BufferedReader(new InputStreamReader(is));
            System.out.println("i'm sending data"+ id);
            c.write(in.readLine()+"\r\n");
            while (!check[id]) {
                System.out.println("Stucked" + id);
            }

            workerGroup.shutdownGracefully();

        }
        catch (Exception e){
            e.printStackTrace();
        }
        finally {
            workerGroup.shutdownGracefully();
//            lock.unlock();
        }
    }
}
