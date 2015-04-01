package Lab3Help;

// implementation of BusMapInf interface

import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Vector;
import javax.swing.JFrame;

/**
 * An implementation of the interface BusMapInf. This implementation
 * puts up a window where the buslines and busstops can be displayed.
 * The window has coordinates 0-1000 with (0,0) in the lower left
 * corner.
 *
 * @author <a href="mailto:kentp@cs.chalmers.se">Kent Petersson</a>
 * @version 1.0
 * @since 1.0
 * @see JFrame
 * @see BusMapInf
 */
public class BusMapFrame extends JFrame implements BusMapInf {

    static int frameNr = 0;

    /**
     * Class to represent line segments
     * @author <a href="mailto:kentp@cs.chalmers.se">Kent Petersson</a>
     * @version 1.0
     * @since 1.0
     */
    class Line {
	public Line(int fx, int fy, int tx, int ty, Color c) {
	    fromX = fx; fromY = fy;
	    toX = tx; toY = ty;
	    lineColor = c;
	}
	int fromX, fromY, toX, toY;
	Color lineColor;
    }
    

    /**
     * Class to represent stops
     *
     * @author <a href="mailto:kentp@cs.chalmers.se">Kent Petersson</a>
     * @version 1.0
     * @since 1.0
     */
    class Stop {
	public Stop(int px, int py, String name) {
	    posX = px; posY = py;
	    this.name = name;
	}
	int posX, posY;
	String name;
    }

    Vector lines = new Vector();
    Vector stops = new Vector();
    Vector shortest = new Vector();

    boolean isShortest = false;

    private int currColIx = 0;
    private Color[] colors 
	= {Color.black, Color.blue, Color.green, Color.white,
	   Color.yellow, Color.magenta, Color.orange};
    private Color currColor() {
	return colors[currColIx];
    }

    public BusMapFrame() {
	frameNr++;
	setTitle("BusMap Frame " + frameNr);
	setSize(600,625);
	getContentPane().setBackground(Color.white);
	

	addWindowListener(new WindowAdapter() {
	   
	    public void windowClosing(WindowEvent e) {
		System.exit(0);
	    }
	   
	});
    }
    
    /**
     * Initiates the object
     */
    public void initMap() {
	setVisible(false);
	lines.clear(); stops.clear(); shortest.clear();
	isShortest = false; currColIx = 0;
    }
    
    /**
     * Makes the following edges be drawn thick and in red. Could be 
     * used to distinguish the shortest path.
     */
    public void initShortestPath() {
	isShortest = true;	
    }
    
    /**
     * Makes the following edges in a "new" color.
     */
    public void nextColor() {
	currColIx++;
	if (currColIx >= colors.length) currColIx = 1;
    }
    
    /**
     * Draw a line between the points (fromX,fromY) and (toX,toY).
     */
    public void drawEdge(int fromX, int fromY, int toX, int toY) {
	(isShortest?shortest:lines)
	    .add(new Line(fromX,fromY,toX,toY,currColor()));
    }
    
    /**
     * Draw a stop (small bullet) at position (x,y) and print the
     * name of the stop immediately to the right of the stop.
     */
    public void drawStop(int x, int y, String name) {
	stops.add(new Stop(x,y,name));
    }
    
    /**
     * Shows the map.
     */
    public void finalMap() {
	this.setVisible(true);
    }

    /**
     * Translates between the external coordinates and the internal
     * coordintes in the X-direction (width).
     */
    private int transformX (int pos) {
	double width = this.getSize().getWidth()-40;
	return (int)((pos*width)/1000)+20;
    }

    /**
     * Translates between the external coordinates and the internal
     * coordintes in the Y-direction (height).
     */
    private int transformY (int pos) {
	double height = this.getSize().getHeight()-60;
	return (int)(height-(pos*height)/1000)+40;
    }

    private static final int bullSize = 2;
    public void paint(Graphics g) {
	super.paint(g);
	g.setColor(Color.lightGray);
	g.fillRect(20,40,
		   (int)(this.getSize().getWidth()-40),
		   (int)(this.getSize().getHeight()-60));
	Stop currStop;
	g.setColor(Color.black);
	for(Iterator i = stops.iterator(); i.hasNext();) {
	    currStop = (Stop) i.next();
	    g.fillOval(transformX(currStop.posX) - bullSize,
		       transformY(currStop.posY) - bullSize,
		       bullSize*2, bullSize*2);
	    g.drawString(currStop.name,
			 transformX(currStop.posX) + (int)(1.5*bullSize),
			 transformY(currStop.posY) + (int)(1.9*bullSize));
	}

	Line currLine;
	for(Iterator i = lines.iterator(); i.hasNext();) {
	    currLine = (Line) i.next();
	    g.setColor(currLine.lineColor);
	    g.drawLine(transformX(currLine.fromX), transformY(currLine.fromY),
		       transformX(currLine.toX), transformY(currLine.toY));
	}
	g.setColor(Color.red);
	for(Iterator i = shortest.iterator(); i.hasNext();) {
	    currLine = (Line) i.next();
	    g.drawLine(transformX(currLine.fromX)+1, transformY(currLine.fromY),
		       transformX(currLine.toX)+1, transformY(currLine.toY));
	    g.drawLine(transformX(currLine.fromX), transformY(currLine.fromY)+1,
		       transformX(currLine.toX), transformY(currLine.toY)+1);
	    g.drawLine(transformX(currLine.fromX)-1, transformY(currLine.fromY),
		       transformX(currLine.toX)-1, transformY(currLine.toY));
	    g.drawLine(transformX(currLine.fromX), transformY(currLine.fromY)-1,
		       transformX(currLine.toX), transformY(currLine.toY)-1);
	    g.drawLine(transformX(currLine.fromX), transformY(currLine.fromY),
		       transformX(currLine.toX), transformY(currLine.toY));
	}
    }
}
