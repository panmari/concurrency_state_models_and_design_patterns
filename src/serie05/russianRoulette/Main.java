package serie05.russianRoulette;

public class Main {

	public static void main(String[] args) {

		// The classic case, 1 bullet and 6 chambers.
		int nrChambers = 6;
		int nrBullets = 1;
		Gun gun = new Gun(nrChambers, nrBullets);
		Table table = new Table(gun);

		Player Michael = new Player("Michael", table);
		Player Charlton = new Player("Charlton", table);
		
		Thread MichaelThread = new Thread(Michael);
		Thread CharltonThread = new Thread(Charlton);
		
		MichaelThread.start();
		CharltonThread.start();
	}

}
