import java.util.Scanner;

public class Runner {
	public static Scanner sc = new Scanner(System.in);
	public static void main(String[] args) {
		
		Manager m = new Manager();
		System.out.println("Please type a number of cars:");
		int cars = sc.nextInt();
		System.out.println("Please type a number of rinsing stations at the first wash station:");
		int N = sc.nextInt();
		System.out.println("Please type a number of rinsing stations at the second wash station:");
		int K = sc.nextInt();
		System.out.println("Average time between arrival of car at the first facility(Recommended 1.5 seconds):");
		long lamda1 = (long) (sc.nextDouble()*Math.pow(10, 9));//nano second
		System.out.println("Average time between arrival of car at the first facility(Recommended 3 seconds):");
		long lamda2 = (long)(sc.nextDouble()*Math.pow(10, 9));//nano second
		m.runCars(cars, N, K,lamda1,lamda2);
		
	}

}
