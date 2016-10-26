package shape;

import java.awt.Graphics2D;
import java.awt.Color;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Line2D;
import java.awt.geom.Rectangle2D;
import java.awt.geom.Point2D;

/**
 * A car that can be moved around a JFrame using the CarShape's
 * {@link #translate(int, int)} method.
 * This is a modified version of the books CarShape class.
 * @author Andrew Markley
 * @author Cory W. Cordell
 */
public class CarShape implements MoveableShape
{

	 /**
	  * Constructs a CarShape with a random color.
	  * @param x the left of the bounding rectangle
      *	@param y the top of the bounding rectangle
	  * @param width the width of the bounding rectangle
	  * @param speedBonus value to use as the velocity increment,
	  * calculated using a slope formula to normalize vehicle speed
	  * based on the lane the vehicle is driving on.
	  */
	 public CarShape( int x, int y, int width , int speedBonus )
	 {
		 this.x = x;
		 this.y = y;
		 this.width = width;
		 velocity = ( ( y - maxY ) / -20 ) + 5 + speedBonus;
		 newColor();
	 }

	 /**
	  * Moves the CarShape by a given amount.
	  * @param dx the amount to translate in x-direction
	  * @param dy the amount to translate in y-direction
	  */
	 public void translate(int dx, int dy)
	 {
		 x += dx;
		 y += dy;
	 }

	 /**
	  * Generates the CarShape.
	  * @param g2 the graphics context
	  */
	 public void draw(Graphics2D g2)
	 {
		 Rectangle2D.Double body
        	= new Rectangle2D.Double(x, y + width / 6, width - 1, width / 6);

		 Ellipse2D.Double frontTire
            = new Ellipse2D.Double(x + width / 6, y + width / 3,
            	width / 6, width / 6);

		 Ellipse2D.Double rearTire
            = new Ellipse2D.Double(x + width * 2 / 3, y + width / 3,
                width / 6, width / 6);

		 // The bottom of the front wind shield
		 Point2D.Double r1
        	= new Point2D.Double(x + width / 6, y + width / 6);

		 // The front of the roof
		 Point2D.Double r2 = new Point2D.Double(x + width / 3, y);

		 // The rear of the roof
		 Point2D.Double r3 = new Point2D.Double(x + width * 2 / 3, y);

		 // The bottom of the rear wind shield
		 Point2D.Double r4
            = new Point2D.Double(x + width * 5 / 6, y + width / 6);

		 Line2D.Double frontWindshield = new Line2D.Double(r1, r2);
		 Line2D.Double roofTop = new Line2D.Double(r2, r3);
		 Line2D.Double rearWindshield = new Line2D.Double(r3, r4);

		 g2.setPaint(color);
		 g2.fill(body);
		 g2.draw(body);

		 g2.setPaint(Color.BLACK);
		 g2.fill(frontTire);
		 g2.draw(frontTire);

		 g2.setPaint(Color.BLACK);
		 g2.fill(rearTire);
		 g2.draw(rearTire);

		 g2.draw(frontWindshield);
		 g2.draw(roofTop);
		 g2.draw(rearWindshield);
	 }

	/**
	 * The upper left corner's x-coordinate.
   	 * @return integer value of the x-coordinate
	 */
	public int getXPosition() { return x; }

	/**
	 * The upper left corner's y-coordinate.
	 * @return integer value of the y-coordinate
	 */
	public int getYPosition() { return y; }

	/**
	 * Get the width of the CarShape object.
	 * @return width of shape
	 */
	public int getWidth() { return width; }

	/**
	 * Get the height of the CarShape object.
	 * @return height of car
	 */
	public int getHeight() { return width * 2 / 3; }

	/**
	 * Gets this objects current velocity.
	 * @return integer value of the velocity field
	 */
	public int getVelocity() { return velocity; }

	/**
	 * Resets this objects velocity.
	 */
	public void resetVelocity() { velocity = ( ( y - maxY ) / -20 ) + 5; }

	/**
	 * Increment this objects velocity field by one.
	 */
	public void incVelocity()
	{
		if ( velocity > 0 )
			velocity++;
		else
			velocity--;
	}

	/**
	 * Increment this objects velocity field by a given amount.
	 * @param amt integer in which to increment the velocity
	 */
	public void incVelocity( int amt )
	{
		if ( velocity > 0 )
			velocity += amt;
		else
			velocity -= amt;
	}

	/**
	 * When called, causes the car object to travel in the opposite direction.
	 */
	public void reverseDirection() { velocity *= -1; }

	/**
	 * Setter for the x-coordinate of the this object.
	 * @param x integer value for the x-coordinate of the this object
	 */
	public void setXPosition( int x ) { this.x = x; }

	/**
	 * Sets a new random color for the object.
	 */
	public void newColor()
	{
		 color = new Color( (float) Math.random(),
				 (float) Math.random(), (float) Math.random() );
	}

	 private int x;
	 private int y;
	 private int width;
	 private Color color;
	 private int velocity;
	 private int maxY = 525;
}
