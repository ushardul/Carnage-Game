//file format should be:
// 1 //number of people in the database
// and then each player and their respective data in respective order
/*      GameOver
	Tom Cui, Graham McColl, Shardul Upadhyay
	Mrs. Reid
	ICS4U1
	Date: June 14th, 2010
	Due: June 14th, 2010

	The purpose of this class is to provide methods for drawing the highscores in file and making changes if neccessary

	Variable Dictionary:
	Vector v: the vector that holds all SingleScore objects
	SingleScore backup []: the backup of the vector of SingleScore objects
	SingleScore sortedData []: the sorted vector as an array with the top ten scores only]
	int p1Win, p1Loss, p2Win, p2Loss: the number of wins and losses that should be awarded to the player
	Image imgBack: the background image for the screen
	BufferedReader br: used to get input from file
	FileWriter fw: the filewriter to write info to screen
	Font ttfReal: the font for drawing text
	int totalPlayers: the total number of players
	MainMenu goBack: the reference to go back to the menu (only used in the first constructor)
	// Variables not listed here are method specific and purpose is explained above the variables
*/
import java.io.*;
import java.util.*;
import java.awt.event.*;
import javax.swing.*;
import java.awt.*;
import java.awt.Image;
import javax.swing.ImageIcon;
class GameOver extends JFrame implements MouseListener
{
    // Declares global variables
    private Vector v = new Vector ();
    private SingleScore backup[];
    private SingleScore sortedData[] = new SingleScore [10];
    private int p1Win = 0, p1Loss = 0, p2Win = 0, p2Loss = 0;
    private Image imgBack = new ImageIcon ("highscores.jpg").getImage ();
    private BufferedReader br;
    private FileWriter fw;
    private Font ttfReal;
    private int totalPlayers;
    private MainMenu goBack;

    // Constructor that is called from main menu
    public GameOver (MainMenu goBack)
    {
	// Assigns variable
	this.goBack = goBack;
	// Tries to set input to come from file
	try
	{
	    br = new BufferedReader (new FileReader ("highscores.txt"));
	}
	catch (IOException e)
	{
	    // Catches IOException and call method
	    this.ExitProgram ("Problems with file found. Exiting...");
	}
	// Reads in while a null pointer is found
	do
	{
	    // Tries to add an element to the vector with a given BufferedReader source
	    try
	    {
		v.addElement (new SingleScore (br));
	    }
	    // Catches exception which indicates the end of the file
	    catch (NullPointerException exc)
	    {
		break;
	    }
	}
	while (true);
	// Gets total size of vector
	this.totalPlayers = this.v.size ();
	// Tries to close input stream
	try
	{
	    br.close ();
	}
	catch (IOException e)
	{
	    // Catches IOException and call method
	    this.ExitProgram ("Problems with file found. Exiting...");
	}
	// Calls method to find highest values
	this.findHighest ();
	// Sets frame to be visible
	this.setVisible (true);
	// Sets size of frame
	this.setSize (800, 800);
	// Makes the frame not resizable
	this.setResizable (false);
	// Centers on screen
	this.setLocationRelativeTo (null);
	// Tries to read in the font
	try
	{
	    // Creates a new input stream
	    InputStream is = this.getClass ().getResourceAsStream ("Halo3.ttf");
	    // Gets new font from the input stream
	    Font ttfBase = Font.createFont (Font.TRUETYPE_FONT, is);
	    // Resizes font
	    this.ttfReal = ttfBase.deriveFont (Font.PLAIN, 20);
	}
	catch (FontFormatException e)
	{
	    // Catches font format exception and exits program
	    this.ExitProgram ("Font Problem. Exiting...");
	}
	catch (IOException e)
	{
	    // Catches IOException and call method
	    this.ExitProgram ("Error in input stream. Exiting...");
	}
	// Registers a mouse listener for this object
	addMouseListener (this);
    }


    // Constructor that is called when the player just finished a game and highscores need to be displayed AND added to
    public GameOver (String p1Name, int p1Kill, String p2Name, int p2Kill)
    {
	// Tries to set input to come from file
	try
	{
	    br = new BufferedReader (new FileReader ("highscores.txt"));
	}
	catch (IOException e)
	{
	    // Catches IOException and call method
	    this.ExitProgram ("Problems with file found. Exiting...");
	}
	// Reads in while a null pointer is found
	do
	{
	    // Tries to add an element to the vector with a given BufferedReader source
	    try
	    {
		v.addElement (new SingleScore (br));
	    }
	    // Catches exception which indicates the end of the file
	    catch (NullPointerException exc)
	    {
		break;
	    }
	}
	while (true);
	// Creates new booleans to see if players exist already in file or not, set to false by default
	boolean player1Found = false, player2Found = false;
	// Checks to see which players should get the win and loss
	if (p1Kill > p2Kill)
	{
	    p1Win++;
	    p2Loss++;
	}
	else if (p1Kill < p2Kill)
	{
	    p1Loss++;
	    p2Win++;
	}
	// Starts a for loop looking for the player that were playing in the game
	for (int i = 0 ; i < v.size () ; i++)
	{
	    // Declares a new SingleScore variable temporariliy
	    SingleScore temp = (SingleScore) v.elementAt (i);
	    // Checks if the object's name is the same as the one entered
	    if (temp.getName ().equals (p1Name) && player1Found == false)
	    {
		// Sets kills
		temp.setKill (p1Kill);
		// Sets deaths based on player 2 kills
		temp.setDeath (p2Kill);
		// Sets wins
		temp.setWin (p1Win);
		// Sets losses
		temp.setLoss (p1Loss);
		// Sets playerFound to be true so that two players can not receive changes if names are equal
		player1Found = true;
	    }
	    // Checks if the object's name is the same as the one entered
	    else if (temp.getName ().equals (p2Name) && player2Found == false)
	    {
		// Sets kills
		temp.setKill (p2Kill);
		// Sets deaths based on player 1 kills
		temp.setDeath (p1Kill);
		// Sets wins
		temp.setWin (p2Win);
		// Sets losses
		temp.setLoss (p2Loss);
		// Sets playerFound to be true so that two players can not receive changes if names are equal
		player2Found = true;
	    }
	}
	// If no player 1 is found, create a new player with following parameters
	if (player1Found == false)
	{
	    v.add (new SingleScore (p1Name, p1Win, p1Loss, p1Kill, p2Kill));
	}
	// If no player 2 is found, create a new player with following parameters
	if (player2Found == false)
	{
	    v.add (new SingleScore (p2Name, p2Win, p2Loss, p2Kill, p1Kill));
	}
	// Create a backup of the vector since it will be sorted
	backup = new SingleScore [v.size ()];
	v.copyInto (backup);
	// Sets total number of players
	totalPlayers = backup.length;
	// Tries to close input stream
	try
	{
	    br.close ();
	    fw = new FileWriter ("highscores.txt");
	}
	catch (IOException e)
	{
	    // Catches IOException and call method
	    this.ExitProgram ("Problems with file found. Exiting...");
	}
	// Finds highest of the vector -> for sorting
	this.findHighest ();
	// Tries to write to file
	try
	{
	    // Starts a for loop for writing all objects to file
	    for (int i = 0 ; i < backup.length ; i++)
	    {
		// Writes information to file
		fw.write (backup [i].getName () + "\r\n");
		fw.write (backup [i].getWin () + "\r\n");
		fw.write (backup [i].getLoss () + "\r\n");
		fw.write (backup [i].getKill () + "\r\n");
		fw.write (backup [i].getDeath () + "\r\n");
	    }
	    // Closes input
	    fw.close ();
	}
	// Catch IOException and exit program
	catch (IOException e)
	{
	    this.ExitProgram ("Problems with file found. Exiting...");
	}
	// Sets visibility to true
	this.setVisible (true);
	// Sets size of JFrame
	this.setSize (800, 800);
	// Stops resizing from frame
	this.setResizable (false);
	// Centers it on screen
	this.setLocationRelativeTo (null);
	try
	{
	    // Creates a new input stream
	    InputStream is = this.getClass ().getResourceAsStream ("Halo3.ttf");
	    // Gets new font from the input stream
	    Font ttfBase = Font.createFont (Font.TRUETYPE_FONT, is);
	    // Resizes font
	    this.ttfReal = ttfBase.deriveFont (Font.PLAIN, 20);
	}
	catch (FontFormatException e)
	{
	    // Catches font format exception and exits program
	    this.ExitProgram ("Font Problem. Exiting...");
	}
	catch (IOException e)
	{
	    // Catches IOException and call method
	    this.ExitProgram ("Error in input stream. Exiting...");
	}

	// Registers a mouse listener for this frame
	addMouseListener (this);
    }


    // Method for finding the highest
    private void findHighest ()
    {
	// Initializes the highest location to zero
	int highestLocation = 0;
	// Starts for loop to fill up every position in the top 10 array
	for (int p = 0 ; p < 10 ; p++)
	{
	    // Sets highestLocation to be at the 0 place in the vector
	    highestLocation = 0;
	    // Starts a for loop looking for the highest location
	    for (int i = 0 ; i < this.v.size () ; i++)
	    {
		// Gets object at specific vector place
		SingleScore temp = (SingleScore) v.elementAt (i);
		// Gets object at highestLocation
		SingleScore highest = (SingleScore) v.elementAt (highestLocation);
		// Checks if the current Wlr is greater than the already greatest Wlr
		if (temp.getWlr () > highest.getWlr ())
		{
		    // Sets highest location to current place
		    highestLocation = i;
		}
	    }
	    // Adds the highest found to the array
	    this.sortedData [p] = (SingleScore) this.v.elementAt (highestLocation);
	    // Removes the element so it is no longer the largest
	    this.v.removeElementAt (highestLocation);
	}
    }


    // Method for displaying error and leaving the program
    private void ExitProgram (String error)
    {
	// Tries to display the error message and exits
	try
	{
	    System.out.println (error);
	    Thread.sleep (2000);
	}
	catch (InterruptedException e)
	{
	    // Catches an interruption in the sleeping thread and exits
	    System.exit (0);
	}
    }


    // Method required to be implemented
    public void mousePressed (MouseEvent e)
    {
    }


    // Checks for clicking on the back button
    public void mouseReleased (MouseEvent e)
    {
	// Checks if mouse button has been pressed and mouse is in correct position
	if (e.getButton () == 1 && e.getX () > 15 && e.getX () < 100 && e.getY () > 755 && e.getY () < 775)
	{
	    // If first constructor was run
	    if (backup == null)
	    {
		// sets the main menu to be visible
		goBack.setVisible (true);
	    }
	    // Otherwise it will create a new main menu
	    else
	    {
		new MainMenu ();
	    }
	    // Destroys this JFrame
	    this.dispose ();
	}
    }


    // Method required to be implemented
    public void mouseClicked (MouseEvent e)
    {
    }

    // Method required to be implemented
    public void mouseEntered (MouseEvent e)
    {
    }

    // Method required to be implemented
    public void mouseExited (MouseEvent e)
    {
    }

    // Method that paints to screen
    public void paint (Graphics g)
    {
	// Calls method inherited from Graphics object
	super.paint (g);
	// Makes a new Graphics2D object
	Graphics2D g2 = (Graphics2D) g;
	// Draws background
	g.drawImage (imgBack, 3, 27, null);
	// Sets font and color
	g.setFont (ttfReal);
	g.setColor (Color.RED);
	// Draws headings for various player data
	g.drawString ("Rank", 25, 150);
	g.drawString ("Player name", 150, 150);
	g.drawString ("Win/Loss Ratio", 350, 150);
	g.drawString ("Kill/Death Ratio", 550, 150);
	// Starts for loop for displaying the 10 highest scores
	for (int i = 0 ; i < 10 ; i++)
	{
	    // Sets color
	    g.setColor (Color.WHITE);
	    // Draws the data by getting it from the array
	    g.drawString ("" + (i + 1), 47, (55 * i) + 200);
	    g.drawString (sortedData [i].getName (), 150, (55 * i) + 200);
	    g.drawString (this.Spaces (sortedData [i].getWlr (), 6, 2), 350, (55 * i) + 200);
	    g.drawString (this.Spaces (sortedData [i].getKdr (), 6, 2), 550, (55 * i) + 200);
	}
	// Sets color and outputs the total number of players
	g.setColor (Color.RED);
	g.drawString ("Total Players: " + totalPlayers, 25, 730);
	g.setColor (Color.blue);
	g.drawString ("*Sorted by Win/Loss Ratio", 400, 730);
    }


    // Method to round a decimal number and properly space it
    String Spaces (double num, int size, int decimal)
    {
	// Rounded value
	num = Math.round (num * Math.pow (10, decimal)) / Math.pow (10, decimal);
	// Returns the string with the adjustments requested
	String edited = "";
	// For loop for the number of spaces to be added
	for (int i = 0 ; i < (size - Double.toString (num).length ()) ; i++)
	{
	    // Adds a space in front of the string
	    edited += " ";
	}
	// Returns the edited string
	return edited + num;
    }
}


