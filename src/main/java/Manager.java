/**
 * Created by PRASHANT on 4/14/2017.
 */
public class Manager {
    public static void main(String[] args) throws InterruptedException {
        Threads[] t=new Threads[20];
        for (int i=0;i<20;i++){
            t[i]=new Threads("localhost",100,i);
            System.out.println(i+" Created");
            t[i].start();
        }
    }
}
