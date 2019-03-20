/*
*
* WaitZone includes Departure zone and Arrival zone
* Ships will stay in WaitZone before docking or after undocking
 */

import java.util.ArrayList;
import java.util.List;

public class WaitZone {

    // CAPACITY indicates the max number of ships the waitzone could stay
    private static int CAPACITY = 1;

    private String zoneName;
    // The ships in this zone
    private List<Ship> ships;

    public WaitZone(String newZoneName){
        this.zoneName = newZoneName;
        this.ships = new ArrayList<Ship>(CAPACITY);
    }

    // A new ship arrive and wait for a pilot to operate it
    public synchronized void arrive(Ship arrivalShip){
        if(isFull()){
            try{
                wait();
            } catch (InterruptedException e){
            }
        }
        ships.add(arrivalShip);
        notify();
        System.out.println(arrivalShip.toString()+" arrives at "+zoneName+" zone");
    }

    // The ship in departzone will depart from this zone
    public synchronized void depart(){
        if(ships.size()!=0){
            System.out.println(ships.get(0).toString()+" departs departure zone");
            ships.clear();
            notify();
        }else{
            try{
                wait();
            } catch (InterruptedException e ){
            }
        }
    }

    // After arriving at arrivezone, the pilot begin to operate the ship
    public synchronized Ship getShip(){
       Ship ship = null;

       // when the arrivezone is empty, the pilot need to wait for next ship
        while(isEmpty()){
            try{
                wait();
            } catch (InterruptedException e){
            }
        }
        ship = ships.get(0);
        ships.clear();
        notify();
        return ship;
    }

    // to check if the waitzone is full
    public boolean isFull(){
        if (ships.size() == CAPACITY)
            return true;
        else
            return false;
    }

    // to check if the waitzone is empty
    public boolean isEmpty(){
        if(ships.size() == 0){
            return true;
        }else
            return false;
    }
}
