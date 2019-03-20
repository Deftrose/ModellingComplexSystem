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
        this.ship = null;
    }

    public void run(){
        while(!isInterrupted()){
            try{

                // The pilot need to go to arriveZone to fetch a ship first
                // then with the help of 3 tugs the ship will arrive the berth to unload
                ship = arriveZone.getShip();
                System.out.println("Pilot "+pilotID+" acquires "+ship.toString());
                tugs.acquireTug(Params.DOCKING_TUGS);
                System.out.println("Pilot "+pilotID+" acquires "+Params.DOCKING_TUGS +" tugs");

                //  Go from waitzone to the berth
                sleep(Params.TRAVEL_TIME);

                //  Before docking, the pilot need to check if the shield is on
                berth.checkShield();
                sleep(Params.DOCKING_TIME);
                tugs.returnTug(Params.DOCKING_TUGS);
                System.out.println("Pilot "+pilotID+" releases "+Params.DOCKING_TUGS +" tugs");

                // After docking, the pilot need to require to use the berth to unload
                berth.unload();
                System.out.println(ship.toString()+" being unloaded");
                sleep(Params.UNLOADING_TIME);
                berth.finishUnLoading();

                // After unloading, the ship will go to departure zone with 2 tugs then departs again
                // Before undocking, the pilot need to check if the shield is on
                berth.checkShield();
                tugs.acquireTug(Params.UNDOCKING_TUGS);
                System.out.println("Pilot "+pilotID+" acquires "+Params.UNDOCKING_TUGS +" tugs");
                sleep(Params.UNDOCKING_TIME);

                //  Go from the berth to depart waitzone
                sleep(Params.TRAVEL_TIME);

                tugs.returnTug(Params.UNDOCKING_TUGS);
                System.out.println("Pilot "+pilotID+" releases "+Params.UNDOCKING_TUGS +" tugs");
                departureZone.arrive(ship);

            } catch (InterruptedException e){
                this.interrupt();
            }
        }
    }
}
