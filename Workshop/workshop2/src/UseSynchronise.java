class P extends Thread
{
  //the shared synch instance
  protected Synchronise s;

  public P(Synchronise s)
  {
    this.s = s;
  }

  public void run()
  {
    while (true) {
      task1p();
      s.synch();
      task2p();
        try{
            sleep(800);
        }catch (InterruptedException e){

        }
    }
  }

  private void task1p()
  {
    System.out.println("1p");
    s.setpIsReady();
  }

  private void task2p()
  {
    System.out.println("2p");
    s.setpIsReady();
  }
}

class Q extends Thread
{
  //the shared synch instance
  protected Synchronise s;

  public Q(Synchronise s)
  {
    this.s = s;
  }

  public void run()
  {
    while (true) {
      task1q();
      s.synch();
      task2q();
      try{
          sleep(800);
      }catch (InterruptedException e){

      }
    }
  }

  private void task1q()
  {
    System.out.println("1q");
    s.setqIsReady();
  }

  private void task2q()
  {
    System.out.println("2q");
    s.setqIsReady();
  }
}

class Synchronise
{
  // any useful variables go here
    private boolean pIsReady = false;
    private boolean qIsReady = false;

  public synchronized void synch()
  {
      if(!(pIsReady&&qIsReady)){
          try{
              wait();
          }catch (InterruptedException e){

          }
      }
  }

  public  synchronized void setpIsReady(){
      if(pIsReady)
          pIsReady = false;
      else
          pIsReady = true;
      notify();
  }

  public  synchronized void setqIsReady(){
      if(qIsReady)
          qIsReady = false;
      else
          qIsReady = true;
      notify();
  }
}


public class UseSynchronise
{
  public static void main(String [] args)
  {
    Synchronise s = new Synchronise();
    Thread p = new P(s);
    Thread q = new Q(s);
    p.start();
    q.start();
  }
}
