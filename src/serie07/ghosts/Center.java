package serie07.ghosts;

import java.util.Date;
import java.util.LinkedList;
import java.util.concurrent.ConcurrentLinkedQueue;

public class Center extends RunnablePerson {

	private int killedGhosts;
	private Date timeAtLastCheck;
	private LinkedList<FriendDoor> friends;
	private boolean doorsClosed;
	
	
	public Center(int n, ConcurrentLinkedQueue<Ghost> ghosts, LinkedList<FriendDoor> friends)
	{
		killedGhosts = 0;
		this.n = n;
		this.ghosts = ghosts;
		alive = true;
		timeAtLastCheck = new Date();
		this.friends = friends;
		this.doorsClosed = false;
	}
	
	@Override
	public void run() {
		while(alive)
		{
			double rnd = Math.random();
			if(rnd < 0.4)
			{
				killGhost();
			}
			if((System.currentTimeMillis() - timeAtLastCheck.getTime())/1000 > 2)
			{
				checkNrOfGhosts();
				timeAtLastCheck.setTime(System.currentTimeMillis());
			}
			try {
				Thread.sleep(10);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}

	private void checkNrOfGhosts() {
		System.out.println("GhostsKilled: "+killedGhosts);
		int sumOfGhosts = 0;
		for(FriendDoor friend:friends)
		{
			sumOfGhosts += friend.getNrOfGhostsEntered();
		}
		System.out.println("SumOfGhosts: "+sumOfGhosts);
		int currentlyLivingGhosts = sumOfGhosts - killedGhosts;
		System.out.println("difference=living ghosts: " + currentlyLivingGhosts);
		if(currentlyLivingGhosts > n)
		{
			for(FriendDoor friend: friends)
			{
				friend.closeDoor();
			}
			doorsClosed = true;
		}
		else if(currentlyLivingGhosts < n/2 && doorsClosed)
		{
			for(FriendDoor friend: friends)
			{
				friend.openDoor();
			}
			doorsClosed = false;
		}
	}

	private void killGhost() {
		if(ghosts.size() > 0)
		{
			ghosts.poll();
			killedGhosts++;
		}
	}

}
