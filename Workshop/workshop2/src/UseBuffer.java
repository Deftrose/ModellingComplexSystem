import java.util.List;
import java.util.ArrayList;
import java.util.Random;
import java.util.concurrent.Semaphore;

/**
 * A bounded buffer maintains a fixed number of "slots". Items can be
 * inserted into and removed from the buffer. The buffer has a maximum
 * size.
 */

class BoundedBuffer
{

  private Semaphore notFull ;
  private Semaphore notEmpty;
  // the maximum size of the bounded buffer
  final public static int MAXSIZE = 10;

  // the buffer
  private List<Integer> buffer;

  public BoundedBuffer()
  {
    buffer = new ArrayList<Integer>();
    notEmpty = new Semaphore(0);
    notFull = new Semaphore(MAXSIZE);
  }

  // add an element to the end of the buffer if it is not full
  public synchronized void put(int input)
    throws InterruptedException
  {
    if (buffer.size() < MAXSIZE) {
      buffer.add(input);
      notify();
    }
    else {
      try{
        wait();
      } catch (InterruptedException e ){
      }
    }
  }

  // take an element from the front of the buffer
  public synchronized int get()
    throws InterruptedException
  {
    int result = 0;
    if(buffer.size() > 0){
       result = buffer.remove(0);
       notify();
    }else{
      try{
        wait();
      }catch (InterruptedException e){
      }
    }
    return result;
  }

  public int size()
  {
    int result = buffer.size();
    return result;
  }
}

/**
 * An instance of the Producer class produces new integers at random
 * intervals, and inserts them into a bounded buffer.
 */

class Producer extends Thread
{
  // the buffer in which to insert new integers
  BoundedBuffer buffer;

  public Producer(BoundedBuffer buffer)
  {
    this.buffer = buffer;
  }

  public void run()
  {
    Random random = new Random();

    try {
      while (true) {

	//insert a random integer
	int next = random.nextInt();
	buffer.put(next);

	//sleep for a random period between
	//0 and 99 milliseconds
	int sleep = random.nextInt(10);
	Thread.sleep(sleep);

	System.err.println("b.size() = " + buffer.size());
      }
    }
    catch (InterruptedException e) {}
  }
}

/**
 * An instance of the Consumer class consumes integers from a bounded
 * buffer at random intervals.
 */

class Consumer extends Thread
{
  // the buffer in which to insert new integers
  BoundedBuffer buffer;

  public Consumer(BoundedBuffer buffer)
  {
    this.buffer = buffer;
  }

  public void run()
  {
    Random random = new Random();

    try {
      while (true) {

	//get the next integer from the buffer
	int next = buffer.get();

	System.err.println("next = " + next);

	//sleep for a random period between
	//0 and 49 milliseconds
	int sleep = random.nextInt(50);
	Thread.sleep(sleep);
      }
    }
    catch (InterruptedException e) {}
  }
}

public class UseBuffer
{
  public static void main(String [] args)
  {
    BoundedBuffer buffer = new BoundedBuffer();
    Producer p = new Producer(buffer);
    Consumer c = new Consumer(buffer);

    p.start();
    c.start();
  }
}
