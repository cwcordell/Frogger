package shape;

import java.awt.Graphics2D;


/**
 * Provides an interface for shapes and images that can be moved around in a
 * JFrame.
 * This is a modified version of the book's MoveableShape class.
 * * @author Andrew Markley
 * * @author Cory W. Cordell
 */
public interface MoveableShape
{
   /**
    * Displays the MoveableShape.
    * @param g2 the graphics context
    */
   void draw(Graphics2D g2);

   /**
    * Moves the shape by a given amount.
    * @param dx the amount to translate in x-direction
    * @param dy the amount to translate in y-direction
    */
   void translate(int dx, int dy);

   	/**
   	 * The upper left corner's x-coordinate.
   	 * @return integer value of the x-coordinate
   	 */
   	int getXPosition();

   	/**
   	 * The upper left corner's y-coordinate.
   	 * @return integer value of the y-coordinate
   	 */
   	int getYPosition();

	/**
	 * Get the width of the MoveableShape object.
	 * @return width of shape
	 */
   	int getWidth();

	/**
	 * Get the height of the MoveableShape object.
	 * @return height of shape
	 */
   	int getHeight();
}
