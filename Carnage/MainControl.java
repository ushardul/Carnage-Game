/*
MainControl
Tom Cui, Shardul Upadhyay, Graham McColl
Ms.Reid
ICS4U1
June 14, 2010

Purpose: The purpose of this class is to pull everything from other classes
together and contains a wide assortment of methods that call upon other classes as well
as draw obstacles on maps

Variable Dictionary:
t = a thread that does calculations without interupting the program
delay = the amount of time between actions
player1 = a object of player that stores player 1
player2 = a object of player that stores player 2
player1Lives = stores the amount of lives player1 have left
player2Lives = stores the amount of lives player2 have left
totalLives = stores the total amount of lives left
obstacles[] = an array of rectangle2d that stores the obstacles in the maps
bullets = a vector that stores all the bullets objects
grenades = a vector that stores bullet objects
input = an object for keyboard polling
endGameTimer = a timer for ending the game
player1Name = stores the name of player 1
player2Name = stores the name of player 2
map = stores the image of the current lap
ttfReal = a font object
level = identifies which map is being played
tm = a variable used to get current system time
enum = an enumeration of a vector
tempBullet = a temporary object of the bullet
temp = a temporary object used to temporarily store values for various purposes
b = a object used for various purposes
g = the graphics object gotten when the method is called to paint to screen
g2 = the Graphics2D object that is used for advanced drawing functions
*/
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.Image;
import java.awt.Font;
import java.awt.FontFormatException;
import java.awt.geom.Rectangle2D;
import java.awt.geom.Point2D;
import java.awt.geom.Ellipse2D;
import java.awt.geom.GeneralPath;
import java.util.Vector;
import java.util.Enumeration;
import javax.swing.JPanel;
import javax.swing.ImageIcon;
import java.io.InputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.BufferedReader;
import javax.swing.SwingUtilities;
import java.util.NoSuchElementException;


public class MainControl extends JPanel implements Runnable
{
    //private variable declaration
    private Thread t;
    private int delay;

    private Player player1;
    private Player player2;

    private int player1Lives = 0; //player 1 lives gets set to 0 by default
    private int player2Lives = 0; //player 2 lives gets set to 0 by default
    private int totalLives = 0; // total player lives gets set to 0 by default

    private Rectangle2D obstacles[];

    private Vector bullets = new Vector ();

    private Vector grenades = new Vector ();

    private InputManager input = new InputManager ();

    private int endGameTimer = 1000; //end game timer gets set to 1000

    private String player1Name;
    private String player2Name;

    private Image map;

    private Font ttfReal;

    public MainControl (String player1Name, String player2Name)  //MainControl Constructor that gets sent in 2 names from the parameters
    {
	//assigns values to the names in this class
	this.player1Name = player1Name;
	this.player2Name = player2Name;

	int level = 0; //presets the level of map to zero

	try //try method to avoid crashes
	{
	    BufferedReader br = new BufferedReader (new FileReader ("settings.txt")); //declares bufferedreader
	    level = Integer.parseInt (br.readLine ()); // reads in the level from the settings
	    ImageIcon mapLoad = new ImageIcon ("map" + level + ".jpg"); //loads the map depending on what the value of level was
	    map = mapLoad.getImage (); // map becomes the map that was loaded
	    this.totalLives = Integer.parseInt (br.readLine ()); //reads in the amount of lives each player starts off with
	    this.player1Lives = this.totalLives; // sets the player 1 life to total lives
	    this.player2Lives = this.totalLives; // sets the player 1 life to total lives
	    this.delay = 4 + (int) (Double.parseDouble (br.readLine ()) * -2);  // changes the speed of the game by the settings in the settings file

	    InputStream is = this.getClass ().getResourceAsStream ("Halo3.ttf"); // creates a new input stream
	    Font ttfBase = Font.createFont (Font.TRUETYPE_FONT, is); // gets new font from the input stream
	    this.ttfReal = ttfBase.deriveFont (Font.PLAIN, 48); // resizes the font

	    if (level == 1) //if it is map one
	    {
		this.obstacles = new Rectangle2D [9]; //declare an obstacles array based on the amount of obstacles
		//assigns objects into the array based on which map it is
		this.obstacles [0] = new Rectangle2D.Double (-50, 0, 48, 850);
		this.obstacles [1] = new Rectangle2D.Double (0, 705, 800, 95);
		this.obstacles [2] = new Rectangle2D.Double (801, -50, 50, 850);
		this.obstacles [3] = new Rectangle2D.Double (0, 0, 800, 62);

		this.obstacles [4] = new Rectangle2D.Double (0, 595, 129, 31);
		this.obstacles [5] = new Rectangle2D.Double (137, 485, 520, 31);
		this.obstacles [6] = new Rectangle2D.Double (671, 595, 129, 31);
		this.obstacles [7] = new Rectangle2D.Double (0, 186, 180, 29);
		this.obstacles [8] = new Rectangle2D.Double (620, 186, 180, 29);
	    }
	    else if (level == 2) //if it is map 2
	    {
		this.obstacles = new Rectangle2D [21]; //declare an obstacles array based on the amount of obstacles
		//assigns objects into the array based on which map it is
		this.obstacles [0] = new Rectangle2D.Double (-50, 0, 48, 850);
		this.obstacles [1] = new Rectangle2D.Double (801, -50, 50, 850);
		this.obstacles [2] = new Rectangle2D.Double (0, 0, 800, 46);

		this.obstacles [3] = new Rectangle2D.Double (0, 185, 255, 49);
		this.obstacles [4] = new Rectangle2D.Double (255, 234, 64, 49);
		this.obstacles [5] = new Rectangle2D.Double (294, 284, 64, 49);
		this.obstacles [6] = new Rectangle2D.Double (544, 185, 256, 49);
		this.obstacles [7] = new Rectangle2D.Double (480, 234, 64, 49);
		this.obstacles [8] = new Rectangle2D.Double (441, 284, 64, 49);
		this.obstacles [9] = new Rectangle2D.Double (245, 480, 315, 49);
		this.obstacles [10] = new Rectangle2D.Double (181, 505, 64, 49);
		this.obstacles [11] = new Rectangle2D.Double (562, 505, 64, 49);
		this.obstacles [12] = new Rectangle2D.Double (0, 560, 49, 49);
		this.obstacles [13] = new Rectangle2D.Double (49, 608, 49, 49);
		this.obstacles [14] = new Rectangle2D.Double (98, 656, 49, 49);
		this.obstacles [15] = new Rectangle2D.Double (148, 704, 97, 49);
		this.obstacles [16] = new Rectangle2D.Double (245, 753, 310, 47);
		this.obstacles [17] = new Rectangle2D.Double (556, 704, 97, 49);
		this.obstacles [18] = new Rectangle2D.Double (653, 656, 49, 49);
		this.obstacles [19] = new Rectangle2D.Double (702, 608, 49, 49);
		this.obstacles [20] = new Rectangle2D.Double (751, 560, 49, 49);
	    }
	    else if (level == 3) //if it is map 3
	    {
		this.obstacles = new Rectangle2D [8]; //declare an obstacles array based on the amount of obstacles
		//assigns objects into the array based on which map it is
		this.obstacles [0] = new Rectangle2D.Double (0, 30, 45, 744);
		this.obstacles [1] = new Rectangle2D.Double (0, 774, 800, 26);
		this.obstacles [2] = new Rectangle2D.Double (749, 30, 51, 744);
		this.obstacles [3] = new Rectangle2D.Double (0, 0, 800, 30);

		this.obstacles [4] = new Rectangle2D.Double (45, 151, 201, 30);
		this.obstacles [5] = new Rectangle2D.Double (561, 151, 193, 30);
		this.obstacles [6] = new Rectangle2D.Double (357, 0, 93, 413);
		this.obstacles [7] = new Rectangle2D.Double (356, 593, 94, 115);
	    }
	    else if (level == 4) //if it is map 4
	    {
		this.obstacles = new Rectangle2D [18]; //declare an obstacles array based on the amount of obstacles
		//assigns objects into the array based on which map it is
		this.obstacles [0] = new Rectangle2D.Double (-50, 0, 48, 850);
		this.obstacles [1] = new Rectangle2D.Double (0, 770, 800, 30);
		this.obstacles [2] = new Rectangle2D.Double (801, -50, 50, 850);
		this.obstacles [3] = new Rectangle2D.Double (0, -50, 800, 50);

		this.obstacles [4] = new Rectangle2D.Double (0, 310, 63, 139);
		this.obstacles [5] = new Rectangle2D.Double (62, 391, 80, 58);
		this.obstacles [6] = new Rectangle2D.Double (0, 685, 126, 87);
		this.obstacles [7] = new Rectangle2D.Double (126, 696, 76, 76);
		this.obstacles [8] = new Rectangle2D.Double (311, 540, 165, 243);
		this.obstacles [9] = new Rectangle2D.Double (589, 696, 89, 76);
		this.obstacles [10] = new Rectangle2D.Double (678, 680, 122, 87);
		this.obstacles [11] = new Rectangle2D.Double (151, 237, 73, 46);
		this.obstacles [12] = new Rectangle2D.Double (151, 237, 73, 46);
		this.obstacles [13] = new Rectangle2D.Double (224, 159, 361, 146);
		this.obstacles [14] = new Rectangle2D.Double (375, 304, 55, 54);
		this.obstacles [15] = new Rectangle2D.Double (585, 237, 75, 46);
		this.obstacles [16] = new Rectangle2D.Double (668, 395, 76, 58);
		this.obstacles [17] = new Rectangle2D.Double (746, 310, 54, 139);

	    }
	}
	catch (IOException e)  // if nothing is read, report an error
	{
	    this.ExitProgram ("Error in file. Exiting..."); //exits program
	}
	catch (FontFormatException e)  // if the font is inappropriate
	{
	    this.ExitProgram ("Font Format Error. Exiting..."); //exits program
	}

	this.setFocusable (true);  // makes so the window gets input
	addKeyListener (this.input); // registers a keylistener for this object

	this.t = new Thread (this); // assigns a new third object to this object
	this.t.start (); // starts execution the thread
    }


    public void run ()  // this is the run method
    {
	long tm = System.currentTimeMillis (); // gets current system time
	this.player1 = new Player (this.player1Name, 80, 70, 0, 1, this.obstacles, this.input, this); //initializes new player 1 object
	this.player2 = new Player (this.player2Name, 690, 70, Math.PI, 2, this.obstacles, this.input, this); //initializes new player 2 objects
	while (this.endGameTimer != 0) // perform while endGameTimer is not over
	{
	    Enumeration enum; //declares an enumeration
	    this.player1.Calculate (); //runs player1 method
	    this.player2.Calculate (); //runs player2 method

	    enum = this.bullets.elements (); // enumeration of the vector beings
	    while (enum.hasMoreElements ()) // while enum still has values
	    {
		Bullet tempBullet; // a temporary object that holds a bullet
		try
		{
		    tempBullet = (Bullet) enum.nextElement (); // assigns tempBullet an object in the enum
		}
		catch (NoSuchElementException e)  // if there is nothing next in the enumeration
		{
		    continue; //proceed forward
		}
		tempBullet.Calculate (); // calls the calculate method in the class
		GeneralPath tempBounding = tempBullet.getBounding (); // tempBounding becomes the bounding that is returned by the tempBullet class

		for (int i2 = 0 ; i2 < this.obstacles.length ; i2++) // foras long as the length of the obstacles
		{
		    if (this.obstacles [i2].intersects (tempBullet.getBounding ().getBounds2D ())) // simplifying general path to a rectangle and checking if it intersects
		    {
			if (tempBullet.getBounding ().intersects (this.obstacles [i2])) // taking actual general path and checking if it intersects
			{
			    this.bullets.remove (tempBullet); //removes the object from the vector
			    this.bullets.trimToSize (); //decreases the capacity of the vector
			    continue; //proceeds forward
			}
		    }
		}

		if (tempBullet.getPlayerCreated () == 2) //if player 2 created the bullet
		{
		    if (this.player1.getBounding ().getBounds2D ().intersects (tempBounding.getBounds2D ())) //getting the bounding of the player and seeing if the bullet intersects
		    {
			if (tempBounding.intersects (this.player1.getBounding ().getBounds2D ())) // checking if it is intersecting against the player
			{
			    this.player1.setHealth (tempBullet.bulletDamage); // runs the setHeath method to do damage

			    this.bullets.remove (tempBullet); //removes the bullet
			    this.bullets.trimToSize (); //trims the vector
			    continue; //proceeds forward
			}
		    }
		}
		else
		{
		    if (this.player2.getBounding ().getBounds2D ().intersects (tempBounding.getBounds2D ())) //getting the bounding of the player and seeing if the bullet intersects
		    {
			if (tempBounding.intersects (this.player2.getBounding ().getBounds2D ())) // checking if it is intersecting against the player
			{
			    this.player2.setHealth (tempBullet.bulletDamage); // runs the setHeath method to do damage

			    this.bullets.remove (tempBullet); //removes the bullet
			    this.bullets.trimToSize (); //trims the vector
			    continue; //proceeds forward
			}
		    }
		}
	    }

	    enum = this.grenades.elements (); //creates an enumeration of grenade vector
	    while (enum.hasMoreElements ()) //while there are still grenade objects
	    {
		Grenade temp; // declares temp
		try
		{
		    temp = (Grenade) enum.nextElement (); //temp becomes an element in the enumeration
		}
		catch (NoSuchElementException e)  //if there is no next element
		{
		    break; //end the loop
		}
		temp.grenadeCalculate (); // runs the calculate method
		for (int i2 = 0 ; i2 < this.obstacles.length ; i2++) // for the length of the obstacle
		{
		    if (this.obstacles [i2].intersects (new Rectangle2D.Double (temp.getGrenadeX (), temp.getGrenadeY (), 10, 10))) // checking if grenade intersects one of the paths
		    {
			temp.setGrenadeDX (0); //sets the value of the grenade displacement x to 0
			temp.setGrenadeDY (0); //sets the value of the grenade displacement y to 0
			temp.setGravity (0); //sets the value of the grenade gravity to 0
		    }
		}
		if (temp.getExplosionSize () < 200 && temp.getExplosionSize () > 2) // if the size of the explosion is less than 200 and more than 2
		{
		    Ellipse2D tempExplodeBounding = new Ellipse2D.Double (temp.getGrenadeX (), temp.getGrenadeY (), temp.getExplosionSize (), temp.getExplosionSize ()); //creates a new oval with the values returned by the method
		    Rectangle2D playerBounding = this.player1.getBounding ().getBounds2D (); // creates a new player Bounding
		    if (tempExplodeBounding.intersects (playerBounding) && temp.getPlayer1Hit () == false) //if the player has been hit by the grenade before is false
		    {
			this.player1.setHealth (75); //player loses 75 heath
			temp.setPlayer1Hit (true); // player has been hit before becomes true
		    }
		    playerBounding = this.player2.getBounding ().getBounds2D (); //creates a new player bounding
		    if (tempExplodeBounding.intersects (playerBounding) && temp.getPlayer2Hit () == false) //if the player has been hit by the grenade before is false
		    {
			this.player2.setHealth (75); //player loses 75 health
			temp.setPlayer2Hit (true); // player has been hit becomes true
		    }
		}
		else if (temp.getExplosionSize () > 200) //if the size of the explosion is more than 200
		{
		    this.grenades.remove (temp); //remove the grenade object
		    this.grenades.trimToSize (); //fix the size of the vector
		    continue; //proceed forward
		}
	    }

	    if (this.player1.getHealth () <= 0 && this.player1.getDeathTimer () < 0 && this.player1Lives > 1) //if the player died but still has more lives
	    {
		this.player1 = new Player (this.player1Name, 80, 70, 0, 1, this.obstacles, this.input, this); //draw the death of the player
		this.player1Lives--; //player loses one life
	    }
	    if (this.player2.getHealth () <= 0 && this.player2.getDeathTimer () < 0 && this.player2Lives > 1) //if the player died but still has more lives
	    {
		this.player2 = new Player (this.player2Name, 690, 70, Math.PI, 2, this.obstacles, this.input, this); //draw the death of the player
		this.player2Lives--; //player loses one life
	    }


	    if ((this.player1Lives == 1 && this.player1.getHealth () <= 0 && this.player1.getDeathTimer () < 0) || (this.player2Lives == 1 && this.player2.getHealth () <= 0 && this.player2.getDeathTimer () < 0)) //if either player only has 1 life left and they just died
	    {
		this.endGameTimer--; //end the game timer
	    }
	    if (this.endGameTimer != 1000) //if the timer is not 1000
	    {
		this.endGameTimer--; //subtract timer by 1
		if (player1Lives == 1)  //if player1 has 1 life
		{
		    player1Lives--; //player 1 loses a life
		}
		else if (player2Lives == 1) // if player 1 does not have 1 life, check if player 2 has 1 life
		{
		    player2Lives--; //player 2 loses a life
		}
	    }
	    if (this.endGameTimer == 0) // if the timer ends
	    {
		new GameOver (this.player1.getPlayerName (), -1 * (this.player2Lives - this.totalLives), player2.getPlayerName (), -1 * (this.player1Lives - this.totalLives)); // Creates new game over object passing the names given and the frags gotten by the player
		SwingUtilities.getWindowAncestor (this).dispose (); // Disposes the top level JFrame
	    }
	    // Calls method to repaint screen
	    repaint ();
	    // Tries to sleep the thread
	    try
	    {
		// delay is added to timeout
		tm += this.delay;
		// Thread sleeps for the maximum time between 0 or the difference between the timeout and current time
		Thread.sleep (Math.max (0, tm - System.currentTimeMillis ()));
	    }
	    catch (InterruptedException e)  // if the thread is interuppted
	    {
		this.ExitProgram ("Thread interrupted"); //display error message
	    }
	}
    }


    public void paintComponent (Graphics g)  //this method paints the players
    {
	super.paintComponent (g); // Calls paintComponent method in upper level class
	Graphics2D g2 = (Graphics2D) g; //g2 becomes g object

	// Sets rendering hints for the graphics context
	RenderingHints rh = new RenderingHints (RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
	g2.setRenderingHints (rh);

	g2.drawImage (map, 0, 0, null); //draws the map image
	g2.setColor (Color.black); //sets the colour in g2 to be black

	g2.drawRect (0, 0, 798, 798); //draw the black rectangle by running the drawRect method
	//runs the draw player methods to draw both players
	this.player1.drawPlayer (g2);
	this.player2.drawPlayer (g2);

	Enumeration enum = this.bullets.elements (); //creates an enumeration of the bullets vector

	while (enum.hasMoreElements ()) //while enum sill has more elements draw the bullets
	{
	    Bullet b; //creates a Bullet object b
	    try
	    {
		b = (Bullet) enum.nextElement (); //b becomes the next element
	    }
	    catch (NoSuchElementException e)  //if there is no more bullets left
	    {
		break; //exit the loop
	    }
	    b.drawBullet (g2); //draw the bullet
	}

	enum = this.grenades.elements (); // creates an enumeration of the grenades
	while (enum.hasMoreElements ()) //while there are still grenades
	{
	    Grenade b; //creates an object Grenade
	    try
	    {
		b = (Grenade) enum.nextElement (); //b becomes next object in vector
	    }
	    catch (NoSuchElementException e)  //if there are no grenades left
	    {
		break; //break the loop
	    }
	    b.grenadeDraw (g2); //draw the grenade
	}

	if (endGameTimer != 1000) //if the timer is not 1000
	{
	    g2.setFont (ttfReal); //set the font
	    g2.setColor (Color.blue); //make the word blue
	    g2.drawString ("GAME OVER", 175, 590); //write game over
	    // Checks if player 2 had more kills than player 1
	    if (this.player1Lives < this.player2Lives)
	    {
		// Draws victory message for player 2
		g2.drawString (player2.getPlayerName () + " WINS", 175, 620);
	    }
	    // Otherwise, if player 1 won
	    else
	    {
		// Draws victory message for player 1
		g2.drawString (player1.getPlayerName () + " WINS", 210, 620);
	    }
	}
    }


    public void createBullet (double x, double y, double length, double width, double angle, double speed, int playerCreated, double damage)  //this method creates a bullet
    {
	bullets.add (new Bullet (x, y, length, width, angle, speed, playerCreated, damage)); //adds a new object into the bullet with its parameters
    }


    public void createGrenade (double x, double y, double angle)  //this method creates a new grenade
    {
	grenades.add (new Grenade (x, y, angle)); //adds a new grenade with the vectors included
    }


    public int getPlayer1Lives ()  //this method returns the amount of lives player 1 has left
    {
	return player1Lives; //returns the amount of lives player 1 has left
    }


    public int getPlayer2Lives ()  //this method returns the amount of lives player 2 has left
    {
	return player2Lives; //returns the amount of lives player 2 has left
    }


    public int getTotalLives ()  //this method returns the total amount of lives left
    {
	return totalLives; //return the totalLives value
    }


    private void ExitProgram (String error)  //this method exits the program depending on the error
    {
	try
	{
	    System.out.println (error); //prints out the error message
	    Thread.sleep (2000); //gives a 2000 delay for the reader to finish reading it
	}
	catch (InterruptedException e)  //if there is an interruption
	{
	    System.exit (0); //exit the program
	}
    }
}


