package serie05.diningSavages;

public class Pot {

	private int maxServings;
	private int servings = 0;
	
	public Pot(int maxServings) {
		this.maxServings = maxServings;
	}
	
	public synchronized void fill() throws InterruptedException {
		while (!isEmpty()) {
			wait();
		}
		this.servings = maxServings;
		// Since there is only one cook, notifying any other waiting thread will be ok.
		// THIS WILL BREAK IF THERE ARE MULTIPLE COOKS.
		notify();
	}

	public synchronized void getServing() throws InterruptedException {
		while (isEmpty()) {
			notifyAll();
			wait();
		}
		servings--;
	}
	
	public boolean isEmpty() {
		return servings == 0;
	}
}
