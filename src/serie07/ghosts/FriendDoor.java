package serie07.ghosts;

import java.util.concurrent.*;

public class FriendDoor extends RunnablePerson {

	private int ghostsLetThrough;
	private boolean shouldLetGhostsThrough;
	private ComAnswer comAnswer;
	private static int count = 0;
	private int id;

	public FriendDoor(ConcurrentLinkedQueue<Ghost> ghosts) {
		this.alive = true;
		this.ghosts = ghosts;
		this.ghostsLetThrough = 0;
		this.shouldLetGhostsThrough = true;
		this.id = ++count;
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
				if(comAnswer != null && rnd < 0.5) {
					// Answer com call.
					comAnswer.speak(ghostsLetThrough);
					// Set com answer as null, so we don't answer it multiple times.
					comAnswer = null;
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

	public ComAnswer tryToCallOnCom() {
		comAnswer = new ComAnswer(this);
		return comAnswer;
	}

	public void closeDoor() {
		shouldLetGhostsThrough = false;
	}

	public void openDoor() {
		shouldLetGhostsThrough = true;
	}

	public String toString() {
		return "Friend " + id;
	}

}
