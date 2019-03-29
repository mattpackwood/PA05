/* Matt Packwood, Orchard Ridge Campus, Monday Evening Class, Fall Semester 2003
 * 
 * PA05: Modify PA04 with selection logic and ActionListener event handling
 * from C7 to control location and appearance of the computer on screen
 *
 * Use Dimension class and getSize method to adjust applet to screen
 * size parameters from HTML applet tag per C6/7 demos; 
 * use selection logic to keep figure visible on screen.
 * use events and selection logic to allow user to apply ZIP slot and
 *   screen balls to the computer; 0-6 balls and 0-1 ZIP slot;
 * set screen size to ~486x486
 * set initial height to 1/16 screen ht.
 */
import java.awt.*;
import java.applet.*;
import java.awt.event.*; // event lib
 
public class CmptrL5 extends Applet
		implements ActionListener { // action event handling
	TextField inTF; // text widget
	Button htUpB, htDwnB, ballUpB, ballDwnB, zipB; // buttons
	boolean zipF= false; // logic switch
	int bX, bY, bS; // base vars
	int sW, sH; // applet screen width/Ht
	int wH= 54; // space for widgets at top of screen...
	int qS, hS, dS; // work ratio vars 
	int ballCt= 0, maxBall= 6; // # balls on screen, max screen balls 
	int minH, maxH, incrH= 9, maxIncrH; // ht cntrls; 1/2 inch default incr
	Graphics g; // global ref for Graphic obj

public void init ( ) {
	Dimension size= getSize ( ); // get screen size
	sW= size.width; sH= size.height; // set screen constraints
	bS= sH/16; // initial CPU ht 1/16 screen ht
	bX= 18; // 1/4 inch (18 pixels) in from left edge
	bY= sH-bS-72; // KB bot 1/2 inch above screen bot
	minH= bS/2; // 1/2 initial ht
	maxH= (sH - wH -36 ) / 5; // ((screen ht - widget ht- 36)/ 5
	maxIncrH= bS / 4; // 1/4 ht 
	setBackground (Color.white); // BG color
	//define widgets per stickfig demo and Lab5 model applet
   add (new Label ("+/1 ht adjusts sz; +/- ball add/remove balls; +/-ZIP add/remove slot; txt entry mods ht incr"));  
	htUpB= new Button ("+CPU ht"); // widget catechism.....
	add (htUpB);
	htUpB.addActionListener (this);
	htDwnB= new Button ("-CPU ht");
	add (htDwnB);
	htDwnB.addActionListener (this);
	ballUpB= new Button ("+ball");
	add (ballUpB);
	ballUpB.addActionListener (this);
	ballDwnB= new Button ("-ball");
	add (ballDwnB);
	ballDwnB.addActionListener (this);
	zipB= new Button ("+ZIP");
	add (zipB);
	zipB.addActionListener (this);
	add (new Label ("CPU ht incr>>>"));
	inTF= new TextField (3);
	add (inTF);
	inTF.addActionListener (this);
	}
public void paint (Graphics s) {
	g= s; // set Graphic obj ref global
	showStatus ("Lab5: height="+ bS+ ", ht incr="+incrH+ ", ball#="+ ballCt+ ", ZIP= "+zipF); 
// display per specs
	dsplyFig ( );
	}
private void dsplyFig ( ) { // PEx4 + conditional ZIP slot
	calcRatios ( );
	// same as PEx4 +
	dsplyMonitor ( ); 
	g.setColor (Color.yellow);
	g.fillRect (bX, bY-bS, dS+dS+bS, bS); // CPU
	g.fillRect (bX, bY, dS+dS, bS); // Keyboard
	g.fillOval (bX+dS+dS+qS, bY+qS, hS, hS); // Mouse
	g.setColor (Color.black);
	g.drawRect (bX, bY-bS, dS+dS+bS, bS); // CPU outline
	g.drawRect (bX, bY, dS+dS, bS); // Keyboard outline
	g.drawOval (bX+dS+dS+qS, bY+qS, hS, hS); // Mouse outline
	g.drawLine (bX+dS+dS, bY+hS, bX+dS+dS+qS, bY+hS); // Mouse cord
	g.fillRect (bX+dS+hS, bY-hS-qS, dS, qS); // DVD slot
	g.fillRect (bX+qS, bY+qS, dS+bS, hS); // Keys
	g.fillArc (bX+dS+dS+qS, bY+qS, hS, hS, 45, 90); // Mouse 1
	g.fillArc (bX+dS+dS+qS, bY+qS, hS, hS, 225, 90); // Mouse 2
	// conditional ZIP slot display; see hat dsply technique in SF demo
	if (zipF) {
		g.fillRect (bX+hS, bY-bS+qS, bS, qS);
	}
	}
private void calcRatios ( ) {
	// same as PEx4 
	qS= Math.round (bS/4.0f); // calc ratio vals
	hS= Math.round (bS/2.0f); // calc ratio vals
	dS= Math.round (bS*2.0f); // calc ratio vals
	}
private void dsplyMonitor ( ) { // ref from dsplyFig
	// same as PEx4 + 
	g.setColor (Color.yellow);
	g.fillRect (bX+hS, bY-dS-dS, dS+dS, dS+bS); // Monitor
	g.setColor (Color.black);
	g.drawRect (bX+hS, bY-dS-dS, dS+dS, dS+bS); // Monitor Outline
	g.setColor (Color.lightGray);
	g.fillRect (bX+hS+qS, bY-dS-bS-hS-qS, dS+bS+hS, dS+hS); // Screen
	// conditional screen ball display; see hair display technique in SF demo
	switch (ballCt) { // no breaks == fall-thru
		case 6: 	g.setColor (Color.black);
				g.fillOval (bX+bS+dS-(((ballCt-3)%3)*bS), bY-dS-hS, bS, bS);
		case 5: 	g.setColor (Color.red);
				g.fillOval (bX+bS+dS-(((ballCt-2)%3)*bS), bY-dS-hS, bS, bS);   
		case 4: 	g.setColor (Color.yellow);
				g.fillOval (bX+bS+dS-(((ballCt-1)%3)*bS), bY-dS-hS, bS, bS);
		case 3: 	g.setColor (Color.green);
				g.fillOval (bX+bS+dS-(((ballCt-3)%3)*bS), bY-dS-hS-((ballCt <=5 ? 0 : 1)*bS), bS, bS);
		case 2: 	g.setColor (Color.blue);
				g.fillOval (bX+bS+dS-(((ballCt-2)%3)*bS), bY-dS-hS-((ballCt <=4 ? 0 : 1)*bS), bS, bS);
		case 1: 	g.setColor (Color.white);
				g.fillOval (bX+bS+dS-(((ballCt-1)%3)*bS), bY-dS-hS-((ballCt <=3 ? 0 : 1)*bS), bS, bS);
		} // end switch; minus values ignored
	}
public void actionPerformed (ActionEvent e) {
	// see SF demo for technique and lab5 model applet for spec behavior
	if (e.getSource ( ) == htUpB)
		bS= fitToLmts (bS, incrH, minH, maxH); // incr bS 
	else if (e.getSource ( ) == htDwnB)
		bS= fitToLmts (bS, -incrH, minH, maxH); // decr bS
	else if (e.getSource ( ) == ballUpB)
		ballCt= fitToLmts (ballCt, 1, 0, maxBall); // incr balls
	else if (e.getSource ( ) == ballDwnB)
		ballCt= fitToLmts (ballCt, -1, 0, maxBall); // decr balls
	else if (e.getSource ( ) == zipB) {
		zipF= ! zipF; // toggle Zip flag
		if (zipF) // update Zip button text based on Zip staus
			zipB.setLabel ("-ZIP");
		else
			zipB.setLabel ("+ZIP");
		}
	else if (e.getSource ( ) instanceof TextField) {
		int inNum= Integer.parseInt (inTF.getText ( ) ); // stmt scope
		incrH= fitToLmts (inNum, 0, 1, maxIncrH); // note 0 incr
		inTF.setText (""); // reset text field for user
		}
repaint ( );
	}
private int fitToLmts (int target, int incr, int min, int max) {
//
// fitToLmts is a tool or utility method which demos the usefulness of passing
// parameters and returning a value in order to reduce repetetive code; ie.
// generic code can be used when needed by method call w/specific parameters;
// the alternative is similar operations on specific data for each need; code
// once, call many is more efficient than code many.....
//
	target+= incr; // note: target reduced if incr negative; unchanged if incr 0  
	if (target > max)
		target= max; // target > max reduced to max
	else if (target < min)
		target= min; // target < min raised to min
	return target; // incremented target lies within min/max range 
	}
}

	
