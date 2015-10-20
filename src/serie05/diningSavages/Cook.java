package serie05.diningSavages;

public class Cook extends ThreadPerson {

	private Pot pot;
	private int nrRefills = 0;
	
	public Cook(Pot pot) {
		this.pot = pot;
	}

	@Override
	public void action() {
		try {
			pot.fill();
			nrRefills++;
		} catch (InterruptedException e) {
			System.err.println("Cook was interrupted while filling!");
		}
	}

	
	@Override
	public String status() {
		return "Refills: " + nrRefills;
	}

}
