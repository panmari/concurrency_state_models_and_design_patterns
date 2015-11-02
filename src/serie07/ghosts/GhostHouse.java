package serie07.ghosts;

import java.util.LinkedList;
import java.util.concurrent.ConcurrentLinkedQueue;

public class GhostHouse {

	public static void main(String[] args) {
		LinkedList<FriendDoor> friends = new LinkedList<FriendDoor>();
		ConcurrentLinkedQueue<Ghost> ghosts = new ConcurrentLinkedQueue<Ghost>();
		LinkedList<Thread> threads = new LinkedList<Thread>();
		int n = 10;//TODO get from args
		for(int i=0;i<4;i++)
		{
			FriendDoor friend = new FriendDoor(n, ghosts);
			Thread thread = new Thread(friend);
			threads.add(thread);
			friends.add(friend);
		}
		RunnablePerson center = new Center(n, ghosts,friends);
		Thread centerthread = new Thread(center);
		threads.add(centerthread);
		for(Thread thread:threads)
		{
			thread.start();
		}
		System.out.println("started");
		try {
			Thread.sleep(10000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		for(FriendDoor thread:friends)
		{
			thread.kill();
		}
		center.kill();
		System.out.println("finished");
	}

}
