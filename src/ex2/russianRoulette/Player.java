package ex2.russianRoulette;

public class Player implements Runnable {
	
	private Gun gun;

	public Player(Gun gun) {
		this.gun = gun;
	}

	@Override
	public void run() {
		gun.pullTrigger();
	}

}
