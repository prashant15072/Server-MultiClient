/**
 * Created by PRASHANT on 4/14/2017.
 */
public class Manager {
    public static void main(String[] args) throws InterruptedException {
        Threads one=new Threads("localhost",100,6);
        one.start();
    }
}
