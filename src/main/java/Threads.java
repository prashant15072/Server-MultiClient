import java.io.IOException;

/**
 * Created by PRASHANT on 4/14/2017.
 */
public class Threads implements Runnable {
    private Thread t;
    private int id;
    private int port;
    private String host;

    Threads(String host ,int port,int id){
        this.id=id;
        this.host=host;
        this.port=port;
    }

    public void run() {
        ChatClient a=new ChatClient(this.host,this.port,this.id);
        try {
            a.run();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void start() throws InterruptedException {
        if (t==null){
            t=new Thread(this,"t"+Integer.toString(id));
            t.start();
        }
    }

}
