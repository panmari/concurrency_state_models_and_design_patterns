package serie07.ghosts;

import java.util.concurrent.ConcurrentLinkedQueue;

abstract public class RunnablePerson implements Runnable {

	protected int n;
	protected ConcurrentLinkedQueue<Ghost> ghosts;
	protected boolean alive;
	public void kill() {
		alive = false;
	}



}
