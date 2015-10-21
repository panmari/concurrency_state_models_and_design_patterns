package diners;

import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;

public class Table implements Runnable{
	private Queue<RunnablePhilosopher> queue;
	private boolean isAlive = true;
	private int queueSize;
	private int areSittingDown = 0;
	public Table(int size)
	{
		this.queue = new ConcurrentLinkedQueue<RunnablePhilosopher>();
		this.queueSize = size;
	}
	
	public synchronized void waitInQueue(RunnablePhilosopher phil)
	{
		this.queue.add(phil);
	}

	public void kill()
	{
		isAlive = false;
	}
	
	public synchronized void leaveTable()
	{
		this.areSittingDown--;
	}
	
	private synchronized void philSitDown()
	{
		this.areSittingDown++;
	}
	
	@Override
	public void run() {
		while(isAlive)
		{
			if(areSittingDown<queueSize-1 && this.queue.size()>0)
			{
				this.queue.poll().sitDown();
				philSitDown();
			}
			try {
				Thread.sleep(1);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		}
	}
}
