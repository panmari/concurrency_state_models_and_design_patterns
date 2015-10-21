/*
 File: Diners.java
 */

package diners;

import java.awt.*;
import java.awt.event.*;

public class Diners extends Frame {
	static final long serialVersionUID = 6084647440606041325L;
	private PhilTable table;
	private Scrollbar slider;
	private Philosopher phils[] = new Philosopher[PhilTable.NUMPHILS];
	private RunnablePhilosopher rphils[] = new RunnablePhilosopher[PhilTable.NUMPHILS];
	private Fork forks[] = new Fork[PhilTable.NUMPHILS];
	private Table table2;
	
	public static void main(String[] args) {
		new Diners();
	}
	
	public Diners() {
		super("Diners");
		this.addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent e) {
				dispose();
				stop();
			}
		});
		this.setLayout(new BorderLayout());
		
		this.table = new PhilTable();
		this.table.disableUpdate();
		this.add(this.table, BorderLayout.CENTER);
		
		Panel panel = new Panel(new BorderLayout());
		this.slider = new Scrollbar(Scrollbar.HORIZONTAL, 50, 5, 0, 100);
		panel.add(this.slider, BorderLayout.CENTER);
		
		Button restart = new Button("Restart");
		restart.addActionListener( 
				new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						restart(); 
					}
				} ); 
		panel.add(restart, BorderLayout.EAST);
		
		Button stop = new Button("Stop");
		stop.addActionListener(
				new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						freeze(); 
					}
				} ); 
		panel.add(stop, BorderLayout.WEST);
		add(panel, BorderLayout.SOUTH);
		
		this.start();
		
		this.setSize(300,350);
		this.setResizable(false);        
		this.setVisible(true);
	}
	
	private void setupPhilosophers() {
		table2 = new Table(PhilTable.NUMPHILS);
		new Thread(table2).start();
		for (int i = 0; i < PhilTable.NUMPHILS; ++i) {
			Fork left = this.forks[(i-1+PhilTable.NUMPHILS)%PhilTable.NUMPHILS];
			Fork right = this.forks[i];
			
			this.phils[i] = new Philosopher(this.table, i, left, right);
			this.rphils[i] = new RunnablePhilosopher(this.phils[i], this,table2);
		}
	}
	
	private void setupForks() {
		for ( int i = 0; i < PhilTable.NUMPHILS; ++i)
			this.forks[i] = new Fork(this.table, i);
	}
	
	public int getThinkTime() {
		return (this.slider.getValue() * (int)(100 * Math.random()));
	}
	
	public int getEatTime() {
		return (this.slider.getValue() * (int)(50 * Math.random()));
	}
	
	public PhilTable getView(){
		return this.table;
	}
	
	public void start() {
		this.setupForks();
		this.setupPhilosophers();
		this.table.start();
		this.table.enableUpdate();
		for (int i = 0; i < PhilTable.NUMPHILS; ++i) {
			this.rphils[i].start();
		}
	}
	
	public synchronized void stop() {
		// terminate threads
		for (int i = 0; i < PhilTable.NUMPHILS; ++i){
			if (this.rphils[i] != null)
				if (this.rphils[i].isAlive() ) {
					this.rphils[i].terminate();
					System.out.println("phil "+i+" has eaten "+rphils[i].nrOfTimesEaten);
					this.rphils[i] = null;
				}
		}
		this.table2.kill();
		this.notifyAll();
	}
	
	void restart() {
		this.stop();
		this.slider.setValue(50);
		this.table.disableUpdate();
		this.start();
	}
	
	void freeze() {
		this.stop();
		this.table.setFrozen();
	}
	
}
