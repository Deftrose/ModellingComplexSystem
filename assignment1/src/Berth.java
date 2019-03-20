/*
    Ships could only unloaded in the berth with assistance of tugs
 */
public class Berth {
    private String berthId;

    //  indicates whether the berth is been used
    private boolean beingUsed;

    // is the shield activated
    private boolean isActivated;

    public Berth(String berthId){
        this.berthId = berthId;
        this.beingUsed = false;
        this.isActivated = false;
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

    //  Check the state of berth shield, if the shield is activated, the ship need to wait
    public synchronized void checkShield(){
        while(isActivated){
            try {
                wait();
            } catch(InterruptedException e ){
            }
        }
    }

    //  The operator use the shield controller to switch the shield on or off
    public synchronized void shieldControllerOn(){
        isActivated = true;
        notify();
        System.out.println("Shield is activated");
    }

    public synchronized void shieldControllerOff(){
        isActivated = false;
        notify();
        System.out.println("Shield is deactivated");
    }

}
