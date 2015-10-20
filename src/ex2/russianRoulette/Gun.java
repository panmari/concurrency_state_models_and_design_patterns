package ex2.russianRoulette;

public class Gun {

	private boolean[] chambers;
	private int currentPosition;

	public Gun(int nr_chambers, int nr_bullets) {
		if (nr_chambers > nr_bullets) {
			throw new IllegalArgumentException("There need to be a less or equal amount of  bullets than chambers");
		}
		this.chambers = new boolean[nr_chambers];
		this.currentPosition = 0;
		
		// TODO: initialize chambers randomly.
		for (int i = 0; i < nr_chambers - nr_bullets; i++) {
			chambers[i] = true;
		}
	}
	
	public boolean pullTrigger() {
		boolean didShoot = chambers[currentPosition];
		currentPosition++;
		return didShoot;
	}
}
