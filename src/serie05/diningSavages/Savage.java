package serie05.diningSavages;

public class Savage extends ThreadPerson {

	private Pot pot;
	private int eatenPortions = 0;
	
	public Savage(Pot pot) {
		this.pot = pot;
	}
	
	@Override
	public void action() {
		try {
			pot.getServing();
			eatenPortions++;
		} catch (InterruptedException e) {
			System.err.println("Savage was interrupted!");
		}
	}
	
	@Override
	public String status() {
		return "Eaten portions: " + eatenPortions;
	}
}
