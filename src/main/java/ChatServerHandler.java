/**
 * Created by PRASHANT on 4/13/2017.
 */
import com.mongodb.*;
import com.mongodb.util.JSON;
import com.mongodb.util.JSONSerializers;
import com.sun.deploy.util.SyncAccess;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundMessageHandlerAdapter;
import io.netty.channel.group.ChannelGroup;
import io.netty.channel.group.DefaultChannelGroup;
import jdk.nashorn.internal.parser.JSONParser;
import org.json.simple.JSONObject;

import java.net.UnknownHostException;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class ChatServerHandler extends ChannelInboundMessageHandlerAdapter<String> {
    private static final ChannelGroup channels=new DefaultChannelGroup();
//    public static Lock lock=new ReentrantLock();

    @Override
    public void handlerAdded(ChannelHandlerContext ctx) throws Exception {
        Channel incoming=ctx.channel();
        for (Channel channel:channels){
            channel.write("[Server] - " +incoming.remoteAddress() + "has joined");
        }
        channels.add(ctx.channel());
    }

    @Override
    public void handlerRemoved(ChannelHandlerContext ctx) throws Exception {
        Channel incoming=ctx.channel();
        for (Channel channel:channels){
            channel.write("[Server] - " +incoming.remoteAddress() + "has left");
        }
        channels.remove(ctx.channel());
    }

    public void writeIntoDatabase(String s) throws UnknownHostException {
        MongoClient mongoclient=new MongoClient(new MongoClientURI("mongodb://localhost:27017"));
        DB database=mongoclient.getDB("N");
        DBObject a=(DBObject) JSON.parse(s);
        DBCollection collection=database.getCollection("device"+a.get("_id"));
        collection.insert(a);
    }

    public void messageReceived(ChannelHandlerContext channelHandlerContext, String s) throws Exception {
//            lock.lock();
//            writeIntoDatabase(s);
       /* Channel incoming=channelHandlerContext.channel();
        System.out.println(s);
        incoming.write(s);*/
//            lock.unlock();
        writeIntoDatabase(s);
        System.out.println(s);
        Channel incoming=channelHandlerContext.channel();
        for (Channel channel:channels){
            if (channel==incoming){
                channel.write("["+incoming.remoteAddress()+"]" + s +"\n");
            }
        }
    }
}
