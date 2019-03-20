/*
Tugs will be used when the pilots are operating ships to dock or undock
 */
public class Tugs {
    private int tugNum;

    public Tugs(int tugNum){
        this.tugNum = tugNum;
    }

    // The pilot could acquire for some tugs, if the available tugs are not enough, pilots need to wait
    public synchronized void acquireTug(int num){
        if(tugNum<num){
            try{
                wait();
            } catch (InterruptedException e){
            }
        }else{
            tugNum -= num;
            notify();
        }
    }

    // After docking or undocking, the pilot could return tugs
    public synchronized void returnTug(int num){
        tugNum += num;
        notify();
    }
}
