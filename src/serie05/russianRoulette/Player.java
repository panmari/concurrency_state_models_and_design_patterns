package serie05.russianRoulette;

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
				System.out.println(name + " is trying to get the gun.");
				gun.getGun(this);
				System.out.println(name + " is holding gun.");
				boolean didShoot = gun.pullTrigger(this);
				if (didShoot) {
					alive = false;
					System.out.println(name + " -> BANG!");
				} else {
					System.out.println(name + " -> click");
				}
				System.out.println(name + " is placing the gun back on the table.");
				gun.placeGun();
			} catch (AllChambersTriedException e) {
				System.out.println(name + " -> stopped playing and survived.");
				break;
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
}
