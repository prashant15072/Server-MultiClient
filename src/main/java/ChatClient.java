import io.netty.bootstrap.Bootstrap;
import io.netty.channel.Channel;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.util.internal.SystemPropertyUtil;

import java.io.*;

/**
 * Created by PRASHANT on 4/13/2017.
 */
public class ChatClient {
    private String host;
    private int port;
    private int id;

/*
    public static void main(String[] args) throws IOException, InterruptedException {
        //new ChatClient("localhost",810).run();
    }
*/

    public ChatClient(String host ,int port,int id){
        this.host=host;
        this.port=port;
        this.id=id;
    }

    public void run() throws InterruptedException, IOException {
        EventLoopGroup workerGroup=new NioEventLoopGroup();

        try{
            Bootstrap b=new Bootstrap()
                    .group(workerGroup)
                    .channel(NioSocketChannel.class)
                    .handler(new ChatClientInitializer());

            Channel c=b.connect(host,port).sync().channel();

            String str= "{\"_id\":\""+this.id+"\",\"Name\":\"ABC"+this.id+this.port+"\"}";
            InputStream is=new ByteArrayInputStream(str.getBytes());
            BufferedReader in =new BufferedReader(new InputStreamReader(is));
            c.write(in.readLine()+"\r\n");
        }
        finally {
            workerGroup.shutdownGracefully();
        }
    }
}
