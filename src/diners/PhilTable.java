/*
 File: PhilTable.java
 */

package diners;

import java.awt.*;
import java.net.*; 

/** View for Philosopers and Forks.  
 *  Philosophers and Forks inform view about changes
 */

public class PhilTable extends Canvas {
	static final long serialVersionUID = 378395221617823321L;
	static String imageDir = "/resources/";
	static final int NUMPHILS = 5;
	
	static final int THINKING = 0;
	static final int HUNGRY = 1;
	static final int GOTRIGHT = 2;
	static final int EATING = 3;
	static final int GOTLEFT = 4;
	
	private Image[] imgs = new Image[5];
	
	private double [] philX  = new double[NUMPHILS];
	private double [] philY  = new double[NUMPHILS];
	
	private int [] philstate = new int [NUMPHILS];
	
	private double [] forkX  = new double[NUMPHILS];
	private double [] forkY  = new double[NUMPHILS];
	
	private boolean[] forkstate = new boolean[NUMPHILS];
	
	public PhilTable( ) {
		MediaTracker mt;
		setSize( 300, 320 ); // set initial size
		mt = new MediaTracker( this );
		
		imgs[THINKING] = getImage("thinking.gif");
		mt.addImage( imgs[THINKING], THINKING );
		imgs[HUNGRY] = getImage("hungry.gif");
		mt.addImage( imgs[HUNGRY], HUNGRY );
		imgs[GOTRIGHT] = getImage("gotright.gif");
		mt.addImage( imgs[GOTRIGHT], GOTRIGHT );
		imgs[EATING] = getImage("eating.gif");
		mt.addImage( imgs[EATING], EATING );
		imgs[GOTLEFT] = getImage("gotleft.gif");
		mt.addImage( imgs[GOTLEFT], GOTLEFT );
		
		try {
			mt.waitForAll();
		} catch ( InterruptedException e) {
			System.out.println( "Couldn't load one of the images" );
		}
		initPlacing();
	}
	
	private Image getImage(String imageFile) {
		URL url = getClass().getResource(imageDir + imageFile);
		Image image = Toolkit.getDefaultToolkit().createImage(url);
		assertTrue( image != null );
		return image;
	}
	
	// avoid dependency on Java 5
	private void assertTrue(boolean b) {
		if (!b) {
			throw new Error ("Assertion violation");
		}
	}
	
	public void start() {
		setBackground( Color.lightGray );
		repaint();
	}
	
	public void setFrozen() {
		setBackground( Color.red );
		repaint();
	}
	
	public synchronized void setForkState( Fork f ) {
		forkstate[f.getId()] = f.getState();
		// repaint not needed (protocol)
	}
	
	public synchronized void setPhilState( Philosopher p ) {
		philstate[p.getId()] = p.getState();
		repaint();
	}
	
	boolean canupdate = true;
	
	public void repaint() {
		if ( canupdate ) {
			super.repaint();
		}
	}
	
	public void enableUpdate() {
		canupdate = true;
		repaint();
	}
	
	public void disableUpdate() {
		canupdate = false;
	}
	
	/** place philosophers around the table
	 */
	private void initPlacing() {
		double radius = 105.0;
		double centerAdj = 100.0;
		double radians;
		
		for ( int i = 0; i < NUMPHILS; i++ ) {
			radians = i * (2.0 * Math.PI / (double)NUMPHILS);
			philX[i] = Math.sin(radians) * radius + centerAdj;
			philY[i] = Math.cos(radians) * radius + centerAdj;
		}
		
		radius = 35;
		centerAdj = 145;
		
		for ( int i = 0; i < NUMPHILS; i++ ) {
			radians = i * (2 * Math.PI / (double)NUMPHILS) + Math.PI / 5;
			forkX[i] = Math.sin(radians) * radius + centerAdj;
			forkY[i] = Math.cos(radians) * radius + centerAdj;
		}
	}
	
	private Image offscreen;
	private Dimension offscreensize;
	private Graphics offgraphics;
	
	private void prepare() {
		Dimension d = getSize();
		if ( (offscreen == null) || 
				(d.width != offscreensize.width) || 
				(d.height != offscreensize.height) ) 
		{
			offscreen = createImage( d.width, d.height );
			offscreensize = d;
			offgraphics = offscreen.getGraphics();
			offgraphics.setFont(new Font("Helvetica",Font.BOLD,18));
		}
	}
	
	public void paint( Graphics g ) {
		prepare();
		update( g );
	}
	
	private void drawTable() {
		offgraphics.setColor( Color.white );
		offgraphics.fillOval( 105, 105, 90, 90 );
		offgraphics.setColor( Color.black );
		for( int i = 0; i < NUMPHILS; i++ ) {
			if ( !forkstate[i] ) 
				offgraphics.fillOval( (int)forkX[i],
						(int)forkY[i],
						10,
						10 );
		}
	}
	
	private void drawPhils() {
		offgraphics.setColor( getBackground() );
		for ( int i = 0; i < NUMPHILS; i++ ) {
			offgraphics.fillRect( (int)philX[i], 
					(int)philY[i],
					imgs[0].getWidth( this ),
					imgs[0].getHeight( this ) );
			offgraphics.drawImage( imgs[philstate[i]], 
					(int)philX[i], 
					(int)philY[i], 
					this );
		}
	}
	
	public void update( Graphics g ) {
		if ( offscreen == null) {
			return; // something went wrong
		}
		
		boolean d = deadlocked();
		
		if ( d ) {
			setBackground( Color.red );
		}
		offgraphics.setColor( getBackground() );
		offgraphics.fillRect( 0, 0, getSize().width, getSize().height );
		
		drawTable();
		drawPhils();
		
		// check if deadlock has been reached
		if ( d ) {
			offgraphics.setColor( Color.black );
			offgraphics.drawString( "DEADLOCKED" , 90, 130 );
		}
		
		g.drawImage( offscreen, 0, 0, null );
	}
	
	private boolean deadlocked() {
		int i = 0;
		
		while ( i < NUMPHILS && 
				(philstate[i] == GOTRIGHT || philstate[i] == GOTLEFT) ) {
			i++;
		}
		return i == NUMPHILS;
	}
	
}
