/**
 * Created by PRASHANT on 4/14/2017.
 */
public class Manager {
    public static void main(String[] args) throws InterruptedException {
        ChatClient.checkInitialization();
        Threads[] t=new Threads[100];
        for (int i=0;i<100;i++){
            t[i]=new Threads("localhost",100,i);
            System.out.println(i+" Created");
        }

        for (int i=0;i<100;i++){
            t[i].start();
        }

    }
}
