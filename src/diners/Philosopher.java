/*
 File: Philosopher.java
 */

package diners;

/** represents a passive Philosopher.  
 *  provide a thread for each philosopher to make it an active. 
 *  philosopher can:
 *  pickLeft()   pick their left fork
 *  pickRight()  pick their right fork 
 *			   they start eating when they have both forks. 
 *  setThinking() drop their forks and think
 */
public final class Philosopher {
	private int id;
	private PhilTable view;   
	private int state;
	private Fork left;
	private Fork right;
	private boolean hasLeft; 
	private boolean hasRight;
	
	public Philosopher(PhilTable view, int id, Fork left, Fork right) {
		this.view = view;
		this.id = id; 
		this.left = left; 
		this.right = right;
		this.hasLeft = false; 
		this.hasRight = false;
		this.init();
	}
	
	public int getId() {
		return this.id; 
	}
	
	public int getState() {
		return this.state;
	}
	
	private void updateState(int state) {
		this.state = state;
		this.view.setPhilState(this);
	}
	
	public void init() {
		if (this.hasRight) {
			this.right.init(); 
			this.hasRight = false; 
		} 
		if (this.hasLeft) {
			this.left.init(); 
			this.hasLeft = false; 
		}
		this.updateState(PhilTable.THINKING);
	}
	
	
	/** drops both Forks and start thinking
	 */
	public void setThinking() {
		if (this.hasRight) {
			this.right.put(); 
			this.hasRight = false; 
		} 
		if (this.hasLeft) {
			this.left.put(); 
			this.hasLeft = false; 
		}
		this.updateState(PhilTable.THINKING );
	}
	
	public void setHungry() {
		this.updateState(PhilTable.HUNGRY );
	}
	
	/** tries to pick the left fork:  
	 *  nothing happens when the philo. already has the left fork. 
	 *  goes into eating state when he also has the right fork. 
	 */
	public void pickLeft() throws InterruptedException {
		if (this.hasLeft) {
			return;
		} else {
			if (this.hasRight) {
				this.left.get(); 
				this.hasLeft = true; 
				this.updateState(PhilTable.EATING);
			} else {
				this.left.get(); 
				this.hasLeft = true; 
				this.updateState(PhilTable.GOTLEFT);
			}
		}
	}
	
	/** tries to pick the right fork:  
	 *  nothing happens when the philo. already has the right fork. 
	 *  goes into eating state when he also has the left fork. 
	 */
	public void pickRight() throws InterruptedException {
		if (this.hasRight) {
			return;
		} else {
			if (this.hasLeft)  {
				this.right.get(); 
				this.hasRight = true; 
				this.updateState(PhilTable.EATING);
			} else {
				this.right.get(); 
				this.hasRight = true; 
				this.updateState(PhilTable.GOTRIGHT);
			}
		}
	}
	
}
