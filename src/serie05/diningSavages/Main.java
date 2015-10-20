package serie05.diningSavages;

import java.util.ArrayList;

public class Main {

	public static void main(String[] args) throws InterruptedException {
		ArrayList<ThreadPerson> list = new ArrayList<ThreadPerson>();
		
		int maxServings = 10;
		int nrSavages = 5;
		
		// Create all objects necessary.
		Pot pot = new Pot(maxServings);
		list.add(new Cook(pot));
		for (int i = 0; i < nrSavages; i++) {
			list.add(new Savage(pot));
		}
		
		for (ThreadPerson tp: list)
			tp.start();
		
		// Let them eat/fill for some time.
		Thread.sleep(1000);
		
		// Signal threads to die soonish.
		for (ThreadPerson tp: list)
			tp.alive = false;
		
		// Wait until all threads are finished.
		for (ThreadPerson tp: list)
			tp.join();
		
		// Some evaluation:
		for (ThreadPerson tp: list)
			System.out.println(tp.status());
	}

}
