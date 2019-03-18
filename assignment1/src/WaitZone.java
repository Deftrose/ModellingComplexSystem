/*
*
* WaitZone includes Departure zone and Arrival zone
* Ships will stay in WaitZone before docking or after undocking
 */

public class WaitZone {

    private String zoneName;
    private Ship[] ships;

    public WaitZone(String newZoneName){
        this.zoneName = newZoneName;
        this.ships = new Ship[1];
    }

    public void arrive(Ship arrivalShip){
        ships[0] = arrivalShip;
        System.out.println(arrivalShip.toString()+" arrives at arrival zone");
    }

    public void depart(){
        System.out.println(ships[0].toString()+" departs departure zone");
        ships[0] = null;
    }

    public Ship getShip(){
        Ship ship = ships[0];
        ships[0] = null;
        return ship;
    }

    public boolean isFull(){
        if (ships.length==1)
            return true;
        else
            return false;
    }
}
