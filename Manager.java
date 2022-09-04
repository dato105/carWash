
import java.util.Scanner;
import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.Semaphore;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class Manager implements CarListener {

	private static int carId = 0;
	private Semaphore sem1;
	private Semaphore sem2;
	private Semaphore sem3;
	private Semaphore sem4;
	private long startTime;
	private int numCar;
	public static long sumT1 = 0;
	public static long sumT2 = 0;
	public static long sumT3 = 0;
	private long lamda1;
	private long lamda2;

	//Contractor
	public Manager() { }



	//Activate the program
	public void runCars(int numCar,int N,int K,long lamda1,long lamda2) {
		
		sem1 = new Semaphore(N);//first station
		sem2 = new Semaphore(K);//second station
		sem3 = new Semaphore(1 - numCar);//third station
		sem4 = new Semaphore(1- numCar);//all the cars are finish
		this.numCar = numCar;
		this.lamda1 = lamda1;
		this.lamda2 = lamda2;

		
		ExecutorService pool = Executors.newCachedThreadPool();//create the Thread Pool
		Car[] car = new Car[numCar];
		this.startTime = System.nanoTime();
		
		//Cars are the missions and within each mission there is a transition with semaphores between stations
		for (int i = 0; i < car.length; i++) {
			synchronized(this.getClass()) {
				carId++;
			}
			car[i] = new Car(carId,sem1,sem2,sem3,sem4 ,startTime,this,this.lamda1,this.lamda2);
			car[i].addListener(this);//manager listener
			pool.execute(car[i]);//start running
		}
		pool.shutdown();//terminate



	}


	//When all vehicles are finished and the program is over
	public void workComplete(CarEvent event) {

		long avg1 = sumT1/numCar;
		long avg2 = sumT2/numCar;
		long avg3 = sumT3/numCar;
		System.out.println("\t=================finish===================");
		System.out.println("For the first facility the average waiting time is: " + avg1+ " nanoS" );
		System.out.println("For the second facility the average waiting time is: " + avg2 + " nanoS");
		System.out.println("For the theard facility the average waiting time is: " + avg3 + " nanoS");


	}


	// during the program
	public void wash(CarEvent event) {
		synchronized(this) {
			long currentTime = System.nanoTime();
			int id = event.getCarId();

			System.out.println("Time elapsed from the start of the program: "+ (currentTime-startTime) + " nanoS");
			System.out.println(event.getData().getMessage());
			System.out.println("car number: "+ id);
		}
	}

}