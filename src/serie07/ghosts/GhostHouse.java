package serie07.ghosts;

import java.util.LinkedList;
import java.util.concurrent.ConcurrentLinkedQueue;

public class GhostHouse {

	public static void main(String[] args) throws InterruptedException {
		LinkedList<FriendDoor> friends = new LinkedList<FriendDoor>();
		ConcurrentLinkedQueue<Ghost> ghosts = new ConcurrentLinkedQueue<Ghost>();
		LinkedList<Thread> threads = new LinkedList<Thread>();
		int n;
		if (args.length > 0) {
			n = Integer.parseInt(args[0]);
		} else {
			n = 10;
		}
		System.out.println("Using n=" + n);
		for(int i=0; i < 4; i++)
		{
			FriendDoor friend = new FriendDoor(ghosts);
			Thread thread = new Thread(friend);
			threads.add(thread);
			friends.add(friend);
		}
		RunnablePerson center = new Center(n, ghosts, friends);
		threads.add(new Thread(center));
		for(Thread thread:threads)
		{
			thread.start();
		}
		System.out.println("started");
		Thread.sleep(10000);
		System.out.println("Enough! Tearing everything down...");
		for(FriendDoor thread:friends)
		{
			thread.kill();
		}
		center.kill();
		System.out.println("finished");
	}

}
