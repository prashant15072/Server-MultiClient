import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundMessageHandlerAdapter;
import io.netty.channel.EventLoopGroup;

/**
 * Created by PRASHANT on 4/13/2017.
 */
public class ChatClientHandler extends ChannelInboundMessageHandlerAdapter<String>{

    private int id;
    EventLoopGroup workerGroup;

    ChatClientHandler(int id,EventLoopGroup workerGroup){
        this.id=id;
        this.workerGroup=workerGroup;
    }

    public void messageReceived(ChannelHandlerContext channelHandlerContext, String s) throws Exception {
        System.out.println(s);
        System.out.println(id);
        ChatClient.check[id]=true;
        workerGroup.shutdownGracefully();
        System.out.println("Nahi");
    }
}
