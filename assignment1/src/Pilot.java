import java.util.concurrent.Semaphore;

/*
Pilots are assigned to operate the ship during the processes of docking, unloading, undocking
 */
public class Pilot extends Thread{

    //These properties are the zones, tugs and berth engaged with the pilot

    private int pilotID;
    private WaitZone arriveZone;
    private WaitZone departureZone;
    private Tugs tugs;
    private Berth berth;

    // the ship which this pilot is operating on
    private Ship ship;

    public Pilot(int pilotID, WaitZone arriveZone, WaitZone departureZone, Tugs tugs, Berth berth){
        this.pilotID = pilotID;
        this.arriveZone = arriveZone;
        this.departureZone = departureZone;
        this.tugs = tugs;
        this.berth = berth;
    }

    public void run(){
        while(!isInterrupted()){
            try{
                sleep(Params.DOCKING_TIME);
            } catch (InterruptedException e){
                this.interrupt();
            }
        }
    }
}
