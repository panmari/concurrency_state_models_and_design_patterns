package ex2.russianRoulette;

public class Main {

	/**
	 * @param args
	 */
	public static void main(String[] args) {

		// The classic case, 1 bullet and 6 chambers.
		int nrChambers = 6;
		int nrBullets = 1;
		Gun gun = new Gun(nrChambers, nrBullets);
		
		Player Michael = new Player("Michael", gun);
		Player Charlton = new Player("Charlton", gun);
		
		Thread MichaelThread = new Thread(Michael);
		Thread CharltonThread = new Thread(Charlton);
		
		MichaelThread.start();
		CharltonThread.start();
	}

}
