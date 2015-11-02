package serie07.ghosts;

import java.util.concurrent.ConcurrentLinkedQueue;

public class FriendDoor extends RunnablePerson {

	private int ghostsLetThrough;
	private boolean shouldLetGhostsThrough;
	
	public FriendDoor(int n, ConcurrentLinkedQueue<Ghost> ghosts) {
		this.n = n;
		this.alive = true;
		this.ghosts = ghosts;
		this.ghostsLetThrough = 0;
		this.shouldLetGhostsThrough = true;
	}
	@Override
	public void run() {
		while(alive)
		{
			if(shouldLetGhostsThrough)
			{
				double rnd = Math.random();
				if(rnd<0.1)
				{
					letGhostEnter();
				}
			}
			try {
				Thread.sleep(10);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}
	private void letGhostEnter() {
		ghosts.add(new Ghost());
		ghostsLetThrough++;
	}
	public int getNrOfGhostsEntered() {
		//TODO return future
		return ghostsLetThrough;
	}
	public void closeDoor() {
		shouldLetGhostsThrough = false;
	}
	public void openDoor() {
		shouldLetGhostsThrough = true;
	}

}
