package serie05.diningSavages;

public abstract class ThreadPerson extends Thread {
	
	public boolean alive = true;
	public abstract void action();
	public abstract String status();

	@Override
	public void run() {
		while (alive) {
			action();
		}
	}
}
