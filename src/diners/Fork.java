/*
 File: Fork.java
 */

package diners;

public class Fork {
	private boolean taken = false;
	private PhilTable view;
	private int id;
	
	Fork( PhilTable view, int id ) { 
		this.view = view; 
		this.id = id;
		view.setForkState( this );
	}
	
	public int getId() {
		return id;
	}
	
	public boolean getState() {
		return taken;
	}
	
	public void init() {
		taken = false;
		view.setForkState( this );
	}
	
	synchronized void put() {
		taken = false;
		view.setForkState( this );
		notify();
	}
	
	synchronized void get() throws InterruptedException  {
		while ( taken ) {
			wait();
		}
		if ( !((RunnablePhilosopher)Thread.currentThread()).isTerminated() ) {
			taken = true;
			view.setForkState( this );
		}
		else {
			throw new InterruptedException( "thread terminated" );
		}
	}
	
}
