package frogger;

import javax.swing.ImageIcon;
import javax.swing.JOptionPane;

/**
 * Option menu the enables the user to choose the difficulty level of the game.
 * @author Cory W. Cordell
 * @author Andrew Markley
 */
public class Menu
{

	/**
	 * Constructor that generates an option dialog for the game.
	 * @param frogIcon ImageIcon to use for the dialog
	 */
	public Menu(ImageIcon frogIcon)
	{
		String message = "Choose your level:\n"
			+ "Tadpole:           1 lane     +1 initial car speed\n"
			+ "Pond Frog:        2 lanes    +2 initial car speed\n"
			+ "Bull Frog:          3 lanes    +3 initial car speed\n"
			+ "Toad:                4 lanes    +4 initial car speed\n"
			+ "Goliath Frog:     5 lanes    +5 initial car speed\n\n"
			+ "BE THE FROG!\n\n";

		String[] values = { "Tadpole", "Pond Frog", "Bull Frog", "Toad",
			"Goliath Frog" };

		reply = JOptionPane.showOptionDialog(
			null,
			message,
			"Frogger",
			JOptionPane.DEFAULT_OPTION,
			JOptionPane.INFORMATION_MESSAGE,
			frogIcon,
			values,
			1
		);

		if (reply == JOptionPane.CLOSED_OPTION)
			System.exit(0);
	}

	/**
	 * The reply from the user making a selection from the option dialog.
	 * @return integer value corresponding to the users selection plus one.
	 */
	public int getReply()
	{
		return reply + 1;
	}
	
	private int reply;
}
