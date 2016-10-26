package shape;

import java.awt.Component;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.util.ArrayList;
import javax.swing.Icon;

/**
 * Icon that can contain more than one shape.
 * @author Andrew Markley
 * @author Cory W. Cordell
 */
public class MultiShapeIcon implements Icon
{

	/**
	 * Constructor that instantiates a new MultiShapeIcon object.
	 * @param list MoveableShape Arraylist of objects
	 * @param width integer width value
	 * @param height integer height value
	 */
	public MultiShapeIcon( ArrayList<MoveableShape> list , int width, int height)
	{
      icons = list;
      this.width = width;
      this.height = height;
	}

	/**
	 * @see javax.swing.Icon#getIconWidth()
	 */
	public int getIconWidth()
	{
		return width;
	}

	/**
	 * @see javax.swing.Icon#getIconHeight()
	 */
	public int getIconHeight()
	{
		return height;
	}

	/**
	 * @see javax.swing.Icon#paintIcon(java.awt.Component, java.awt.Graphics, int, int)
	 */
	@Override
	public void paintIcon(Component c, Graphics g, int x, int y)
	{
		Graphics2D g2 = (Graphics2D) g;
		for ( MoveableShape s : icons )
			s.draw(g2);
	}

	private int width;
	private int height;
	private ArrayList<MoveableShape> icons;

}
