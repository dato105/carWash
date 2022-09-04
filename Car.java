import java.util.concurrent.Semaphore;

import javax.swing.event.EventListenerList;
/*Tom Kondat 318275591
David Sharabi 315313981
*/
public class Car extends Thread {


	private Semaphore sem1;
	private Semaphore sem2;
	private Semaphore sem3;
	private Semaphore sem4;
	private  EventListenerList list;
	private int id;
	private long startTime;
	private long finishT = 0;
	private Manager manager;
	private long lamda1;
	private long lamda2;


	//Contractor
	public Car(int id,Semaphore sem1,Semaphore sem2,Semaphore sem3,Semaphore sem4,long startT,Manager manager,long lamda1,long lamda2) {
		this.list = new EventListenerList();
		this.id = id;
		this.sem1 = sem1 ;
		this.sem2 = sem2;
		this.sem3 = sem3;
		this.sem4 = sem4;
		this.startTime = startT;
		this.manager = manager;
		this.lamda1 = lamda1;
		this.lamda2 = lamda2;
	}

	public void addListener(CarListener listener) {
		this.list.add(CarListener.class, listener);	
	}

	public void removeListener(CarListener listener)
	{
		this.list.remove(CarListener.class, listener);
	}

	public void run() {
		try {

			CarEvent event = new CarEvent(this,this.id);
			sem1.acquire();
			sem3.release();
			this.sleep((long) nextTime1());//waiting time before enter wash1

			synchronized(manager.getClass()) {
				manager.sumT1 += System.nanoTime() - startTime;//Summarize the time elapsed from the beginning to make an average wait
				enterWash(event);
			}
			inWash(event);
			synchronized(this) {//Sync for time update
				exitWash(event);
				finishT = System.nanoTime();//update finish time
			}

			sem1.release();
			event.setId(event.getId()+1);//Updates the station type


			sem2.acquire();

			synchronized(manager.getClass()) {
				manager.sumT2 += System.nanoTime() - finishT;//The time has been summed up since the car left the first stop for the average wait
			}
			enterWash(event);
			inWash(event);

			//Sync for time update
			synchronized(this) {
				exitWash(event);
				finishT = System.nanoTime();//update finish time
			}
			sem2.release();
			event.setId(event.getId()+1);//Updates the station type


			
			synchronized(manager.getClass()) {
				if(sem3.tryAcquire())//All the cars left the first station
					manager.sumT3 += System.nanoTime() - finishT;//The time has been summed up since the car left the second stop for the average wait
				exitStation(event);
			}
			this.sem4.release();
			if(sem4.tryAcquire()) {//when finish
				for ( CarListener w: list.getListeners(CarListener.class) )
				{
					w.workComplete(event);
				}
			}




		}
		catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	public double nextTime1() {

		return -(Math.log(Math.random())/lamda1);

	}
	public double nextTime2() {

		return -(Math.log(Math.random())/lamda2);

	}

	/*I did the enter, wash and exit functions synchronized so 
	 * that the print would be in one block
	 */
	public synchronized void enterWash(CarEvent event) {


		String msg = "The car enters a washing station "+ event.getId();
		Data data = new Data(msg);


		event.setData(data);

		//for each listener type send the event
		for ( CarListener w: list.getListeners(CarListener.class) )
		{
			w.wash(event);
		}
	}

	public synchronized void inWash(CarEvent event) {

		try {
			this.sleep((long) nextTime2());
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		String msg = "The car is in  washing station "+ event.getId();
		Data data = new Data(msg);


		event.setData(data);

		//for each listener type send the event
		for ( CarListener w: list.getListeners(CarListener.class) )
		{
			w.wash(event);
		}
	}
	public synchronized void exitWash(CarEvent event) {
		String msg = "The car exit from washing station "+ event.getId();
		Data data = new Data(msg);

		event.setData(data);

		//for each listener type send the event
		for ( CarListener w: list.getListeners(CarListener.class) )
		{
			w.wash(event);
		}
	}
	public synchronized void exitStation(CarEvent event) {
		String msg = "The car exit from the facility";
		Data data = new Data(msg);
		event.setData(data);

		//for each listener type send the event
		for ( CarListener w: list.getListeners(CarListener.class) )
		{
			w.wash(event);
		}
	}

}
