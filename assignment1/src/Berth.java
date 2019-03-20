/*
    Ships could only unloaded in the berth with assistance of tugs
 */
public class Berth {
    private String berthId;

    //  indicates whether the berth is been used
    private boolean beingUsed;

    public Berth(String berthId){
        this.berthId = berthId;
        this.beingUsed = false;
    }

    //  try to unload the ship
    public synchronized void unload(){

        // if the berth is under used, then let the ship wait
        if(beingUsed){
            try {
                wait();
            } catch (InterruptedException e){
            }
        }else{
            // if the berth could be used now, switch the state of berth and notify other pilot threads
            this.beingUsed = true;
            notify();
        }
    }

    //  To complete unloading, bring the berth back to usable
    public synchronized void finishUnLoading(){
        this.beingUsed = false;
        notify();
    }

}
