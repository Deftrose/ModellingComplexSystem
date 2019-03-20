/*
Operator is the role who will control the shield of berth
 */
public class Operator extends Thread {
    private Berth berth;

    public Operator (Berth berth){
        this.berth = berth;
    }

    public void run(){

    }
}
