/*
*
* WaitZone includes Departure zone and Arrival zone
* Ships will stay in WaitZone before docking or after undocking
 */

public class WaitZone {

    // CAPACITY indicates the max number of ships the waitzone could stay
    private static int CAPACITY = 1;

    private String zoneName;
    private Ship[] ships;

    public WaitZone(String newZoneName){
        this.zoneName = newZoneName;
        this.ships = new Ship[1];
    }

    public synchronized void arrive(Ship arrivalShip){
        if(isFull()){
            try{
                wait();
            } catch (InterruptedException e){
            }
        }
        ships[0] = arrivalShip;
        notify();
        System.out.println(arrivalShip.toString()+" arrives at arrival zone");
    }

    public synchronized void depart(){
        System.out.println(ships[0].toString()+" departs departure zone");
        ships[0] = null;
        notify();
    }

    public synchronized Ship getShip(){
       Ship ship = null;
        if(isEmpty()){
            try{
                wait();
            } catch (InterruptedException e ){

            }
        }else{
            ship = ships[0];
        }
        ships[0] = null;
        notify();
        return ship;
    }

    // to check if the waitzone is full
    public boolean isFull(){
        if (ships.length== CAPACITY)
            return true;
        else
            return false;
    }

    // to check if the waitzone is empty
    public boolean isEmpty(){
        if(ships.length == 0){
            return true;
        }else
            return false;
    }
}
