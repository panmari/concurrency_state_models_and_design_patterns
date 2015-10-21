/*
 File: RunnablePhilosopher.java
 */

package diners; 

/**
 *  a runnable Philosopher
 * 
 *  Strategy: a philosoper thinks, picks right and left fork, eats 
 *  forever
 *
 *  This class runs each philosopher in its own thread and provides
 *  the eating, thinking strategy.
 */

public class RunnablePhilosopher extends Thread {
	protected final Philosopher phil; 
	protected final Diners controller; 
	boolean terminated = false; 
	private Table table;
	private boolean isAllowedToSitDown = false;
	public int nrOfTimesEaten = 0;
	
	public RunnablePhilosopher( Philosopher p, Diners controller, Table table ) {
		super(); 
		phil = p; 
		this.controller = controller;
		phil.init();
		this.table = table;
	}
	
	public void terminate() {
		interrupt();
	}
	
	public boolean isTerminated(){
		return terminated;
	}
	
	public void start() {
		super.start();
	}
	
	public void interrupt() {
		terminated = true;
		super.interrupt();
	}
	
	private void pause() throws InterruptedException {
		sleep( 500 );
	}
	
	private void think() throws InterruptedException {
		sleep( controller.getThinkTime() );
	}
	
	private void eat() throws InterruptedException {
		sleep( controller.getEatTime() );
		nrOfTimesEaten++;
	}
	
	public synchronized void sitDown()
	{
		this.isAllowedToSitDown = true;
		this.notifyAll();
	}
	
	public void run()  {
		try {
			while ( !isTerminated() ) {
				phil.setThinking();
				think();
				phil.setHungry();
				
				table.waitInQueue(this);
				while(!isAllowedToSitDown)
				{
//					Thread.sleep(1);
					synchronized(this)
					{
						this.wait();
					}
				}
				phil.pickRight(); 
				pause();
				phil.pickLeft(); 
				eat();
				standUp();
			}
		} catch (InterruptedException e) {
			return;
		}
	}
	
	private void standUp()
	{
		isAllowedToSitDown = false;
		table.leaveTable();
	}

	public synchronized void waitinQueue() {
		try {
			this.wait();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
}


