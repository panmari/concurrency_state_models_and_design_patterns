package serie05.russianRoulette;

public class Gun {

	private boolean[] chambers;
	private int currentPosition;
	private boolean isTaken;
	private Player gunHolder;

	public Gun(int nr_chambers, int nr_bullets) {
		if (nr_bullets > nr_chambers) {
			throw new IllegalArgumentException("There need to be a less or equal amount of  bullets than chambers");
		}
		this.chambers = new boolean[nr_chambers];
		this.currentPosition = 0;
		this.isTaken = false;
		
		// TODO: initialize chambers randomly.
		for (int i = 0; i < nr_bullets; i++) {
			chambers[nr_chambers - 1 - i] = true;
		}
	}
	
	public synchronized void getGun(Player player) throws InterruptedException
	{
		while(isTaken == true)
		{
			this.wait();
		}
		isTaken = true;
		gunHolder = player;
	}
	
	public synchronized void placeGun()
	{
		isTaken = false;
		gunHolder = null;
		this.notifyAll();
	}
	
	public synchronized boolean pullTrigger(Player player) throws AllChambersTriedException {
		if (currentPosition >= chambers.length)
			throw new AllChambersTriedException();
		boolean didShoot = chambers[currentPosition];
		// Provokes a race condition if not synchronized.
		Thread.yield();
		currentPosition++;
		return didShoot;
	}
}
