/*
Operator is the role who will control the shield of berth
 */
public class Operator extends Thread {
    private Berth berth;

    public Operator (Berth berth){
        this.berth = berth;
    }

    public void run(){
        while(!isInterrupted()){
            try{
                sleep(Params.debrisLapse());
            } catch (InterruptedException e ){

            }
            // If there comes a debris, the operator need to activate the shield
            berth.shieldControllerOn();

            try{
                sleep(Params.DEBRIS_TIME);
            } catch (InterruptedException e ){

            }

            // After the debris, the operator deactivate the shield
            berth.shieldControllerOff();
        }
    }
}
