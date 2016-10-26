package shape;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

/**
 * A frog that can be moved around a JFrame using the FrogShape's
 * {@link #translate(int, int)} method.
 * @author Andrew Markley
 * @author Cory W. Cordell
 */
public class FrogShape implements MoveableShape
{

	 /**
	  * Constructs a MoveableShape object with the image of a frog.
	  * @param x the left of the bounding rectangle
	  * @param y the top of the bounding rectangle
	  * @param frog image to be used as the frog image
	  */
	 public FrogShape( int x, int y, BufferedImage frog )
	 {
		 yStart = y;
		 this.x = x;
		 this.y = y;
		 this.frog = frog;

		 this.width = frog.getWidth();
		 this.height = frog.getHeight();
	 }

	 /**
	  * Moves the shape by a given amount.
	  * @param dx the amount to translate in x-direction
	  * @param dy the amount to translate in y-direction
	  */
	 public void translate( int dx, int dy )
	 {
		 x += dx;
		 y += dy;
	 }

	 /**
	  * Draws the FrogShape object in JFrame.
	  * @param g2 graphics2D object needed for rendering the image
	  */
	 public void draw( Graphics2D g2 )
	 {
	     g2.drawImage( frog , x , y , null );
	 }

   	 /**
   	  * The upper left corner's x-coordinate.
   	  * @return integer value of the x-coordinate
   	  */
	 public int getXPosition() {	return x; }

   	 /**
   	  * The upper left corner's y-coordinate.
   	  * @return integer value of the y-coordinate
   	  */
	 public int getYPosition() {	return y; }

	 /**
	  * Get the width of the FrogShape object.
	  * @return width of image
	  */
	 public int getWidth() { return width; }

	 /**
	  * Get the height of the FrogShape object.
	  * @return height of image
	  */
	 public int getHeight() { return height; }

	 /**
	 * Set this object's x and y coordinates to the start position.
	 */
	 public void reset() { x = START_POSITION; y = yStart; }

	 private int x = 0;
	 private int y = 0;
	 private final int yStart;
	 private int width;
	 private int height;
	 private final int START_POSITION = 350;
	 private BufferedImage frog;
}
