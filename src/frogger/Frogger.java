package frogger;

// imports
import java.io.IOException;
import java.util.ArrayList;
import javax.imageio.ImageIO;
// awt components
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.image.BufferedImage;
// swing components
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.Timer;
// shapes
import shape.CarShape;
import shape.FrogShape;
import shape.MoveableShape;
import shape.MultiShapeIcon;

/**
 * Main object class that controls the game.
 * @author Andrew Markley
 * @author Cory W. Cordell
 */
public class Frogger
{

	/**
	  * Frogger constructor that instantiates a
	  * {@link #BufferedImage} and {@link #ImageIcon} sets the difficulty of
	  * the game based on a user's choice from a menu, and initiates the game.
	  */
	public Frogger()
	{
		try
		{
			BufferedImage frogImage = ImageIO.read(
				Frogger.class.getResourceAsStream( "/resources/frog.png" ) );
			frogIcon = new ImageIcon( frogImage );

			dificulty = new Menu( frogIcon ).getReply();
			generateBoard( dificulty, frogImage );
		}
		catch (IOException | IllegalArgumentException e)
		{
			JOptionPane.showMessageDialog(null,
				"Error loading the frog image file!");
			System.exit(0);
		}
	}

	private boolean carBoundsCheck( CarShape shape )
	{
		if (shape.getXPosition() < 0 || shape.getXPosition() + shape.getWidth()
			>= FRAME_WIDTH)
				return true;
		return false;
	}

	private boolean collision( ArrayList<MoveableShape> list )
	{
		FrogShape frog = (FrogShape) list.get( 0 );

		boolean collision = false;
		int lane = getLane( frog );

		if ( lane < list.size() && lane != 0 )
			collision = checkLane( (CarShape) list.get( lane ) , frog );

		return collision;
	}

	private int getLane( MoveableShape frog )
	{
		return frog.getYPosition() / FROG_VELOCITY;
	}

	private boolean checkLane( MoveableShape car, MoveableShape frog )
	{
		boolean left = car.getXPosition() < frog.getXPosition()
				+ FROG_BOUNDRY_OFFSET_LEFT && car.getXPosition()
				+ car.getWidth() > frog.getXPosition()
				+ FROG_BOUNDRY_OFFSET_LEFT;

		boolean right = car.getXPosition() < frog.getXPosition()
				+ frog.getWidth() - FROG_BOUNDRY_OFFSET_RIGHT
				&& car.getXPosition() + car.getWidth()
				> frog.getXPosition() + frog.getWidth()
				- FROG_BOUNDRY_OFFSET_RIGHT;

		if ( left || right )
			return true;

		return false;
	}

	private void gameOver( ArrayList<MoveableShape> list )
	{
		FrogShape frog = (FrogShape) list.get( 0 );
		timer.stop();

		if ( highScore < score)
			highScore = score;

		if ( mostFrogsSaved < frogsSaved)
			mostFrogsSaved = frogsSaved;

		int reply = JOptionPane.showConfirmDialog(
				null,
				"Score: " + score + "\nPlay Again?",
				"Game Over!",
				JOptionPane.YES_NO_OPTION,
				0,
				frogIcon
		);

		if (reply == JOptionPane.YES_OPTION)
		{
			for ( int i = 1; i < list.size(); i++ )
			{
				CarShape car = (CarShape) list.get( i );
				car.setXPosition( FRAME_SPAWN_BOUNDARY / i );
				car.resetVelocity();
			}

			score = 0;
			frogsSaved = 0;
			frog.reset();
			timer.start();
		}
		else
			System.exit(0);
	}

	private void levelUp( ArrayList<MoveableShape> list )
	{
		FrogShape frog = (FrogShape) list.get( 0 );
		frogsSaved++;
		score += dificulty + frogsSaved;
		frog.reset();

		for ( int i = 1; i < list.size(); i++ )
			((CarShape) list.get(i)).incVelocity();
	}

	private String updateScore()
	{
		String spaces = "";
		while ( spaces.length() < 25 )
		{
			spaces += " ";
		}
		
		return  "Most Frogs Saved: " + mostFrogsSaved + spaces +
			"Frogs Saved: " + frogsSaved + spaces + "Score: " + score +
			spaces + "High Score: " + highScore;
	}

	private void generateBoard( int lanes, BufferedImage frogImage )
	{
		final JFrame frame = new JFrame( "Frogger" );
		final ArrayList<MoveableShape> shapeList =
			new ArrayList<MoveableShape>();
		final int frameHeight = BASE_FRAME_HEIGHT + FRAME_HEIGHT_MULT * lanes;

		//Create frog
		final MoveableShape frog = new FrogShape( FROG_X_POSITION ,
			FROG_BASE_POSITION + FROG_BASE_MULT * lanes, frogImage);
		shapeList.add( frog );

		//Create appropriate number of vehicles
		for ( int i = 1; i <= lanes; i++ )
		{
			shapeList.add( new CarShape( FRAME_SPAWN_BOUNDARY / i ,
				25 + ( CAR_WIDTH * i ) , CAR_WIDTH, lanes ) );
		}

		MultiShapeIcon msi = new MultiShapeIcon( shapeList,
			FRAME_WIDTH, frameHeight );

		final JLabel gameLabel = new JLabel( msi );
		final JLabel statusLabel = new JLabel( updateScore() );

		gameLabel.addKeyListener( new KeyListener()
		{
			private boolean keyReleased = true;
			public void keyPressed( KeyEvent e )
			{
				if (frog.getYPosition() != 0)
				{
					switch ( e.getKeyCode() )
					{
						case KeyEvent.VK_UP:
							if ((frog.getYPosition() - FROG_VELOCITY >= 0)
									&& keyReleased )
							{
								gameLabel.repaint();
								frog.translate(   0 , -FROG_VELOCITY );
							}

							break;

						case KeyEvent.VK_DOWN:
							if ( (frog.getYPosition() + frog.getHeight() 
									+ FROG_VELOCITY < frameHeight) && keyReleased )
								frog.translate(   0 ,  FROG_VELOCITY );
							break;

						case KeyEvent.VK_LEFT:
							if ( (frog.getXPosition() - FROG_VELOCITY > 0)
									&& keyReleased )
								frog.translate( -FROG_VELOCITY ,   0 );
							break;

						case KeyEvent.VK_RIGHT:
							if ( (frog.getXPosition() + frog.getWidth() +
									FROG_VELOCITY < FRAME_WIDTH) && keyReleased)
								frog.translate(  FROG_VELOCITY ,   0 );
							break;

						default:
							Toolkit.getDefaultToolkit().beep();
							break;
					}

					keyReleased = false;
				}
			}

			public void keyReleased( KeyEvent e ) { keyReleased = true; }
			public void keyTyped( KeyEvent e ) { }
		});

		frame.setLayout( new FlowLayout() );
		frame.setSize( FRAME_WIDTH, frameHeight );
		frame.getContentPane().setBackground( Color.white );
		frame.add( statusLabel );
		frame.add( gameLabel );
		frame.setLocationRelativeTo(null);
		frame.setDefaultCloseOperation( JFrame.EXIT_ON_CLOSE );
		frame.setVisible(true);
		frame.requestFocus();
		gameLabel.requestFocusInWindow();

		// Milliseconds between timer ticks
		timer = new Timer(TIMER_DELAY, new ActionListener()
		{
			public void actionPerformed(ActionEvent event)
			{
				for ( int i = 1; i < shapeList.size(); i++ )
				{
					CarShape car = (CarShape) shapeList.get( i );
					if ( carBoundsCheck( car ) )
					{
						car.reverseDirection();
						car.newColor();
					}
					car.translate( car.getVelocity(), 0 );
				}

				if ( frog.getYPosition() != 0 )
				{
					if ( collision( shapeList ) )
						gameOver( shapeList );
				}
				else
				{
					frog.translate( -50,  0 );
					frame.getContentPane().setBackground( Color.GREEN );

					if (frog.getXPosition() <= 0)
					{
						frame.getContentPane().setBackground( Color.WHITE );
						levelUp( shapeList );
					}
				}

				gameLabel.repaint();
				statusLabel.setText( updateScore() );
			}
		});
		timer.start();
	}

	// CAR SIZES
	private static final int CAR_WIDTH = 100;

	// FROG IMAGE
	private static final int FROG_X_POSITION = 350;
	private static final int FROG_BASE_POSITION = 100;
	private static final int FROG_BASE_MULT = 100;
	private static final int FROG_VELOCITY = 100;
	// USED IN COLLISION DETECTION
	private static final int FROG_BOUNDRY_OFFSET_RIGHT = 25;
	private static final int FROG_BOUNDRY_OFFSET_LEFT = 30;
	// FROG ICON INSTANTIATION
	private ImageIcon frogIcon;

	// GAME BOARD
	private static final int FRAME_WIDTH = 800;
	private static final int FRAME_SPAWN_BOUNDARY = 650;
	private static final int BASE_FRAME_HEIGHT = 250;
	private static final int FRAME_HEIGHT_MULT = 100;
	private static final int TIMER_DELAY = 100;
	private Timer timer;
	private int dificulty;

	// STATUS BAR VARIABLES
	private int highScore = 0;
	private int score = 0;
	private int frogsSaved = 0;
	private int mostFrogsSaved = 0;
}
