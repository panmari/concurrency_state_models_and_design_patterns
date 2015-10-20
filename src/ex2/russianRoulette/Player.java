package ex2.russianRoulette;

public class Player implements Runnable {
	
	private String name;
	private Gun gun;
	private boolean alive = true;

	public Player(String name, Gun gun) {
		this.name = name;
		this.gun = gun;
	}

	@Override
	public void run() {
		while (alive) {
			try {
				boolean didShoot = gun.pullTrigger();
				if (didShoot) {
					alive = false;
					System.out.println(name + " -> BANG!");
				} else {
					System.out.println(name + " -> click");
				}
			} catch (AllChambersTriedException e) {
				System.out.println(name + " -> stopped playing and survived.");
				break;
			}
		}
	}
}
