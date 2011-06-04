/*
    Player
    Graham McColl, Tom Cui, Shardul Upadhyay
    Mrs.Reid
    ICS4U1
    Date: June 13, 2010
    Due:  June 14, 2010

    The purpose of this class is to create a player that will create its own arm and head objects and be able to perform calculations if methods are called
    to move the player and calculate collisions

    Variable dictionary:
    double playerX: x values of player
    double playerY: y value of player
    double playerDX: the change in the x velocity of the player
    double playerDY: the change in the y velocity of the player
    double playerDirection: the angle that the player is aiming at
    String playerName: the name of the player
    boolean left, right, etc..: checking whether a certain key has been pressed or not
    int leftKey, rightKey, etc..: value to check for key presses
    int changeDelay: the delay in changing weapons
    double gravity: the amount of gravity that affects the player
    boolean facingRight: whether the player is facing right or not
    int deathTimer: the amount to count down before indicating to MainControl to destroy player
    int player: whether the player is 1 - green, or 2 - blue
    GeneralPath futureBounding: calculates the bounding but always one step into future for possible collisions
    double playerHealth: the health of the player
    Rectangle2D obstacles []: the obstacles that will impede the player
    Images: images need to be displayed
    Arm playerArm: object of Arm class that will do calculations for the arm
    Head playerHead: object of Head class that will do calculations for the head
    InputManager input: object used for keyboard polling
    Color backGUI: color to use to draw background behind info - readability
    MainControl m: reference to MainControl class used to access variable info
    String importColor: the color of image to import
    GeneralPath temp: temporary general path that is used to store the general path until it is returned
    AffineTransform reflect: used to reflect the general path if player is facing left
    boolean buffer: buffer that holds the previous value of facingRight
    boolean moveableX: holds whether the collision checks returned collisions or not in the x axis
    boolean moveableY: holds whether the collision checks returned collisions or not in the y axis
    AffineTransform shift: used to calculate collision checks
*/
import java.awt.event.KeyEvent;
import java.awt.Graphics2D;
import java.awt.Color;
import java.awt.Toolkit;
import java.awt.geom.GeneralPath;
import java.awt.geom.Rectangle2D;
import java.awt.geom.AffineTransform;
import java.awt.geom.RoundRectangle2D;
import java.util.Vector;
import java.awt.Image;

public class Player
{
    // Global variable initialization
    private double playerX, playerY;
    public double playerDX = 0, playerDY = 0;
    private double playerDirection;
    private boolean playerGrounded = false;
    private String playerName;
    private boolean left = false, right = false, up = false, forwardTurn = false, backwardTurn = false, shot = false, cycleForward = false, cycleBackward = false;
    private int leftKey, rightKey, upKey, shotKey, forwardTurnKey, backwardTurnKey, cycleBackwardKey, cycleForwardKey;
    private int changeDelay = 0;
    private final double gravity = 0.03;
    private boolean facingRight = true;
    private int deathTimer = 500;
    private int player = 0;
    private GeneralPath futureBounding;
    private double playerHealth = 100;
    private Rectangle2D obstacles[];
    private Image playerStandRight, playerRunRight, playerStandLeft, playerRunLeft, playerJumpLeft, playerJumpRight, playerDieRight, playerDieLeft;
    private Arm playerArm;
    private Head playerHead;
    private InputManager input;
    private Color backGUI = new Color (255, 255, 255, 150);
    private MainControl m;

    // Constructor that creates an object based on the parameters passed
    public Player (String name, int x, int y, double direction, int player, Rectangle2D obstacles[], InputManager input, MainControl m)
    {
	// Sets this object's name
	this.playerName = name;
	// Sets x and y value of this object
	this.playerX = x;
	this.playerY = y;
	// Sets angle/direction for the arm
	this.playerDirection = direction;
	// Checks whether the direction given is 180 degrees, i.e. facing left
	if (this.playerDirection == Math.PI)
	{
	    // Sets boolean to false
	    this.facingRight = false;
	}
	// Assigns player to this method's copy
	this.player = player;
	// Calls method to initialize images with values
	this.setImages ();
	// Calls method that initializes keys to check against
	this.setKeys ();
	// Creates a new arm for the player
	this.playerArm = new Arm (this.playerX + 16, this.playerY + 16, player);
	// Creates a new head for the player
	this.playerHead = new Head (this.player);
	// Assigns input object
	this.input = input;
	// Assigns obstacles object
	this.obstacles = obstacles;
	// Assigns main control object
	this.m = m;
    }


    // Method to set images based on player value
    private void setImages ()
    {
	// Initializes default color to green
	String importColor = "Green";
	// Checks if player is 2
	if (this.player == 2)
	{
	    // Sets color to blue
	    importColor = "Blue";
	}
	// Imports images based on the color + whatever image is required
	this.playerJumpLeft = Toolkit.getDefaultToolkit ().getImage (importColor + "JumpLeft.gif");
	this.playerJumpRight = Toolkit.getDefaultToolkit ().getImage (importColor + "JumpRight.gif");
	this.playerStandRight = Toolkit.getDefaultToolkit ().getImage (importColor + "StandRight.gif");
	this.playerStandLeft = Toolkit.getDefaultToolkit ().getImage (importColor + "StandLeft.gif");
	this.playerRunRight = Toolkit.getDefaultToolkit ().createImage (importColor + "RunRight.gif");
	this.playerRunLeft = Toolkit.getDefaultToolkit ().createImage (importColor + "RunLeft.gif");
	this.playerDieRight = Toolkit.getDefaultToolkit ().createImage (importColor + "DeathRight.gif");
	this.playerDieLeft = Toolkit.getDefaultToolkit ().createImage (importColor + "DeathLeft.gif");
    }


    // Sets keys to check against based on player values
    private void setKeys ()
    {
	// Checks if player is 1
	if (this.player == 1)
	{
	    // Key values that player 1 presses
	    this.leftKey = KeyEvent.VK_A;
	    this.rightKey = KeyEvent.VK_D;
	    this.upKey = KeyEvent.VK_W;
	    this.shotKey = KeyEvent.VK_K;
	    this.forwardTurnKey = KeyEvent.VK_G;
	    this.backwardTurnKey = KeyEvent.VK_H;
	    this.cycleBackwardKey = KeyEvent.VK_T;
	    this.cycleForwardKey = KeyEvent.VK_Y;
	}
	// If player is 2
	else
	{
	    // Key values that player 2 presses
	    this.leftKey = KeyEvent.VK_LEFT;
	    this.rightKey = KeyEvent.VK_RIGHT;
	    this.upKey = KeyEvent.VK_UP;
	    this.shotKey = KeyEvent.VK_NUMPAD0;
	    this.forwardTurnKey = KeyEvent.VK_NUMPAD1;
	    this.backwardTurnKey = KeyEvent.VK_NUMPAD2;
	    this.cycleBackwardKey = KeyEvent.VK_NUMPAD4;
	    this.cycleForwardKey = KeyEvent.VK_NUMPAD5;
	}
    }


    // Method to calculate the bounding using x and y values given to it
    private GeneralPath calculateBounding (float x, float y)
    {
	// Declares object
	GeneralPath temp = new GeneralPath ();
	// If player is not dead
	if (this.playerHealth > 0)
	{
	    // Creates a bounding area based on coordinates
	    temp.moveTo (x + 5, y);
	    temp.lineTo (x + 8, y + 9);
	    temp.lineTo (x + 1, y + 13);
	    temp.lineTo (x + 6, y + 21);
	    temp.lineTo (x + 6, y + 28);
	    temp.lineTo (x + 1, y + 33);
	    temp.lineTo (x + 3, y + 39);
	    temp.lineTo (x + 1, y + 61);
	    temp.lineTo (x - 1, y + 74);
	    temp.lineTo (x + 35, y + 74);
	    temp.lineTo (x + 28, y + 54);
	    temp.lineTo (x + 23, y + 33);
	    temp.lineTo (x + 24, y + 16);
	    temp.lineTo (x + 23, y + 4);
	    temp.lineTo (x + 19, y);
	    // Ends bounding area
	    temp.closePath ();
	    // If player is facing left
	    if (this.facingRight == false)
	    {
		// Creates new transformation object
		AffineTransform reflect = new AffineTransform ();
		// Reflect transformation applied to AffineTransform
		reflect.scale (-1, 1);
		// Translates AffineTransform to a certain coordinate
		reflect.translate ((-2 * this.playerX) - 26, 0);
		// Applies the transformations to the bounding area
		temp.transform (reflect);
	    }
	    return temp;
	}
	// If player is dead
	else
	{
	    // If he is in the air when killed
	    if (this.playerGrounded == false && this.deathTimer == 500)
	    {
		// Shift y value a little down
		y += 10;
	    }
	    // If player is facing right
	    if (facingRight == true)
	    {
		// Create rectangle based on coordinates given
		temp.moveTo (x - 4, y + 60);
		temp.lineTo (x + 91, y + 60);
		temp.lineTo (x + 91, y + 74);
		temp.lineTo (x - 4, y + 74);
		// close the path
		temp.closePath ();
	    }
	    else
	    {
		// Create rectangle based on coordinates given
		temp.moveTo (x - 4, y + 60);
		temp.lineTo (x + 91, y + 60);
		temp.lineTo (x + 91, y + 74);
		temp.lineTo (x - 4, y + 74);
		// close the path
		temp.closePath ();
	    }
	    // Returns bounding area
	    return temp;
	}
    }


    // Method used to return the bounding area of this player
    public GeneralPath getBounding ()
    {
	// Returns value mentioned above
	return this.futureBounding;
    }


    // Method to make calculations such as collisions, rotation, calling appropriate methods for keyboard polling, etc.
    public void Calculate ()
    {
	// If player is not dead
	if (this.playerHealth > 0)
	{
	    // Allow keyPressed and keyReleased methods to update key states
	    this.keyPressed ();
	    this.keyReleased ();
	}
	// If player is dead
	else
	{
	    // Set important key states to false (no further input allowed)
	    this.left = false;
	    this.right = false;
	    this.up = false;
	    this.shot = false;
	    // If player just died and he was facing left
	    if (this.deathTimer == 500 && this.facingRight == false)
	    {
		// Set speed to 0
		this.playerDY = 0;
		//  Set x value
		this.playerX -= 50;
		// Makes sure that death animation does not go offscreen on left side
		if (playerX < 5)
		{
		    playerX = -1 * this.playerX;
		}
	    }
	    // If player just died and he was facing right
	    else if (this.deathTimer == 500 && this.facingRight == true)
	    {
		// Set speed to 0
		this.playerDY = 0;
		// Makes sure that death animation does not go offscreen on right side
		if (this.playerX + 95 > 800)
		{
		    playerX -= this.playerX - 700;
		}
	    }
	    // Subtracts deathTimer so MainControl knows when to kill object
	    deathTimer--;
	}
	// ---------------------------------------------------------------------
	// Turret movement code
	// Checks if player is facing right
	if (this.facingRight == true)
	{
	    // If the forward turn button has been pressed and a subtract of the current direction by 1 degree will still be greater than -90 degrees
	    if (this.forwardTurn == true && (this.playerDirection - (Math.PI / 180)) > (Math.PI / -2))
	    {
		// Subtracts 1 degree to current direction
		this.playerDirection -= Math.PI / 180;
	    }
	    // If the backward turn button has been pressed and an addition of the current direction by 1 degree will still be less than 90 degrees
	    else if (this.backwardTurn == true && (this.playerDirection + (Math.PI / 180)) < (Math.PI / 2))
	    {
		// Adds 1 degree from to direction
		this.playerDirection += Math.PI / 180;
	    }
	}
	else
	{
	    // If the backward turn button has been pressed and a subtraction of the current direction by 1 degree will still be greater than 90 degrees
	    if (this.backwardTurn == true && (this.playerDirection - (Math.PI / 180)) > (Math.PI / 2))
	    {
		// Subtracts 1 degree to current direction
		this.playerDirection -= Math.PI / 180;
	    }
	    // If the forward turn button has been pressed and an addition of the current direction by 1 degree will still be less than 270 degrees
	    else if (this.forwardTurn == true && (this.playerDirection + (Math.PI / 180)) < (Math.PI * 1.5))
	    {
		// Adds 1 degree to current direction
		this.playerDirection += Math.PI / 180;
	    }
	}
	// -----------------------------------------------------------------------------
	// X Axis Movement Code
	// Initialization of variables
	boolean buffer = this.facingRight;
	boolean moveableX = true;
	// Checks if left key has been pressed
	if (this.left == true)
	{
	    // Moves player left temporarily
	    this.playerDX = -1.5;
	    // Changes states of facing right
	    this.facingRight = false;
	}
	// Checks if right key has been pressed
	else if (this.right == true)
	{
	    // Moves player right temporarily
	    this.playerDX = 1.5;
	    // Changes states of facing right
	    this.facingRight = true;
	}
	// Checks whether no button in x direction was pressed
	else if (this.right == false && this.left == false)
	{
	    // Sets speed to zero in x axis
	    this.playerDX = 0;
	}
	// Declares variable
	AffineTransform shift = new AffineTransform ();
	// If player was facing right
	if (this.facingRight == true)
	{
	    // Move transformation a little ahead of where they will actually move to
	    shift.translate (this.playerDX + 0.5, 0);
	}
	// If player was facing left
	else
	{
	    // Move transformation a little ahead of where they will actually move to
	    shift.translate (this.playerDX, 0);
	}
	// Gets a bounding using the method
	this.futureBounding = calculateBounding ((float) this.playerX, (float) this.playerY);
	// Applies transformation to the bounding area
	this.futureBounding.transform (shift);
	// For loop for checking each obstacle
	for (int i = 0 ; i < obstacles.length ; i++)
	{
	    // Rough check to see if obstacles collide with player
	    if (this.futureBounding.getBounds2D ().intersects (this.obstacles [i]))
	    {
		// More precise check to see if obstacles collide with player
		if (this.futureBounding.intersects (this.obstacles [i]))
		{
		    // Does not let it move in x direction
		    moveableX = false;
		    // Sets it to no motion
		    this.playerDX = 0;
		    // no need for more collision checking, so for loop exits
		    break;
		}
	    }
	}
	// Checks if moveableX is still true
	if (moveableX == true)
	{
	    // If it is, go ahead with the changes in the player's x value
	    this.playerX += this.playerDX;
	}
	// If facingRight is not the same as before
	if (this.facingRight != buffer)
	{
	    // Change the direction of the player to the exact opposite direction
	    this.playerDirection = Math.PI - this.playerDirection;
	}
	//----------------------------------------------------
	// Y Axis Movement Code
	// Variable declaration
	boolean moveableY = true;
	// Checks if up key has been pressed and player is on ground
	if (this.up == true && this.playerGrounded == true)
	{
	    // Sets its y velocity to -3
	    this.playerDY = -3;
	}
	// Adds gravity onto y velocity
	this.playerDY += gravity;
	// resets shift transform
	shift = new AffineTransform ();
	// Translates shift transform upward
	shift.translate (0, this.playerDY + 0.5);
	// Calculates a new bounding area using the method calculateBounding
	futureBounding = calculateBounding ((float) this.playerX, (float) this.playerY);
	// Applies the transformation to the bounding area
	futureBounding.transform (shift);
	// For loop for checking collisions against every block
	for (int i = 0 ; i < obstacles.length ; i++)
	{
	    // Rough check for collision between obstacle and player
	    if (futureBounding.getBounds2D ().intersects (obstacles [i]))
	    {
		// Precise check for collision between obstacle and player
		if (futureBounding.intersects (obstacles [i]) && this.playerDY > 0.01)
		{
		    // Does not let it move in y direction and sets y velocity to zero
		    moveableY = false;
		    this.playerDY = 0;
		}
		else if (futureBounding.intersects (obstacles [i]) && this.playerDY < 0)
		{
		    // Does not let it move in y direction and sets y velocity to slightly positive so it can go back down and x velocity to zero
		    this.playerDX = 0;
		    this.playerDY = 0.01;
		    moveableY = false;
		}
	    }
	}
	// Checks if player can move in y direction
	if (moveableY == true)
	{
	    // If they can, go ahead with the changes in the player's y value
	    this.playerY += this.playerDY;
	}
	// Checks if the y velocity is zero
	if (this.playerDY == 0)
	{
	    // allows player to jump again
	    this.playerGrounded = true;
	}
	// Otherwise
	else
	{
	    // keep player not grounded
	    this.playerGrounded = false;
	}
	// ----------------------------------------------------------------------
	// Shooting Code
	// Checks if player is not moving at all
	if (this.playerDX == 0 && this.playerDY == 0)
	{
	    // Calls calculations on arm based on x and y coordinates for the situation
	    this.playerArm.armCalculate (this.playerX, this.playerY + 15, this.cycleForward, this.cycleBackward);
	}
	// If player is moving right but not in the air
	else if (this.playerDX > 0 && this.playerDY == 0)
	{
	    // Calls calculations on arm based on x and y coordinates for the situation
	    this.playerArm.armCalculate (this.playerX + 4, this.playerY + 17, this.cycleForward, this.cycleBackward);
	}
	// If player is moving left but not in the air
	else if (this.playerDX < 0 && this.playerDY == 0)
	{
	    // Calls calculations on arm based on x and y coordinates for the situation
	    this.playerArm.armCalculate (this.playerX - 2, this.playerY + 17, this.cycleForward, this.cycleBackward);
	}
	// If player is in air and looking right
	else if (this.playerDY != 0 && this.facingRight == true)
	{
	    // Calls calculations on arm based on x and y coordinates for the situation
	    this.playerArm.armCalculate (this.playerX - 6, this.playerY + 17, this.cycleForward, this.cycleBackward);
	}
	// If player is in air and looking left
	else if (this.playerDY != 0 && this.facingRight == false)
	{
	    // Calls calculations on arm based on x and y coordinates for the situation
	    this.playerArm.armCalculate (this.playerX - 5, this.playerY + 17.5, this.cycleForward, this.cycleBackward);
	}
	// Checks if shooting button has been pressed
	if (this.shot == true)
	{
	    // Calls method in playerArm to shoot a bullet (if possible)
	    playerArm.armShoot (this.playerX, this.playerY, this.playerDirection, m);
	}
	// ----------------------------------------------------------------------
	// Weapon change delay
	// Checks if weapon is still delayed at 100
	if (this.changeDelay == 100)
	{
	    // Do not let the weapon cycle back or forward
	    this.cycleForward = false;
	    this.cycleBackward = false;
	}
	// As long as changeDelay != 0
	if (this.changeDelay != 0)
	{
	    // Countdown so that wepaon can be changed
	    this.changeDelay--;
	}
	// -----------------------------------------------------------------------
    }


    // Method called for drawing player to screen
    public void drawPlayer (Graphics2D g)
    {
	// If player is not dead
	if (this.playerHealth > 0)
	{
	    // -------------------------------------------------------------------------------------------------
	    // Draw GUI above player based on x and y information determined if it is going of screen
	    if (playerX + 100 < 800)
	    {
		this.drawInfo ((int) this.playerX, (int) this.playerY, g);
	    }
	    // If it is not going off screen
	    else
	    {
		// draw the info based on these coordinates determined
		this.drawInfo ((int) (this.playerX - (this.playerX - 690)), (int) this.playerY, g);
	    }
	    // -----------------------------------------------------------------------------------------------
	    // If player is not moving and looking right and not jumping
	    if (this.playerDX == 0 && this.playerDirection > (Math.PI / -2) && this.playerDirection < (Math.PI / 2) && this.playerDY == 0)
	    {
		// Draws standing right image at coordinates determined
		g.drawImage (this.playerStandRight, (int) this.playerX - 4, (int) this.playerY + 11, null);
	    }
	    // If player is moving and looking right and not jumping
	    else if (this.playerDX != 0 && this.playerDirection > (Math.PI / -2) && this.playerDirection < (Math.PI / 2) && this.playerDY == 0)
	    {
		// Draws running right image at coordinates determined
		g.drawImage (this.playerRunRight, (int) this.playerX - 4, (int) this.playerY + 13, null);
	    }
	    // If player is not moving and looking left and not jumping
	    else if (this.playerDX == 0 && this.playerDirection > (Math.PI / 2) && this.playerDirection < (Math.PI * 1.5) && this.playerDY == 0)
	    {
		// Draws standing left image at coordinates determined
		g.drawImage (this.playerStandLeft, (int) this.playerX - 7, (int) this.playerY + 11, null);
	    }
	    // If player is moving and looking left and not jumping
	    else if (this.playerDX != 0 && this.playerDirection > (Math.PI / 2) && this.playerDirection < (Math.PI * 1.5) && this.playerDY == 0)
	    {
		// Draws running left image at coordinates determined
		g.drawImage (this.playerRunLeft, (int) this.playerX - 7, (int) this.playerY + 13, null);
	    }
	    // If player is moving left and in the air
	    else if (this.playerDirection > (Math.PI / 2) && this.playerDirection < (Math.PI * 1.5) && this.playerDY != 0)
	    {
		// Draws jumping left image at coordinates determined
		g.drawImage (this.playerJumpLeft, (int) this.playerX - 7, (int) this.playerY + 13, null);
	    }
	    // If player is moving right and in the air
	    else if (this.playerDirection > (Math.PI / -2) && this.playerDirection < (Math.PI / 2) && this.playerDY != 0)
	    {
		// Draws jumping right image at coordinates determined
		g.drawImage (this.playerJumpRight, (int) this.playerX - 4, (int) this.playerY + 13, null);
	    }
	    // ----------------------------------------------------------------------------------------------
	    // If player is not moving at all
	    if (this.playerDX == 0 && this.playerDY == 0)
	    {
		// Draws head at this x and y value, with direction given
		this.playerHead.HeadDraw (g, this.playerX, this.playerY, this.playerDirection);
	    }
	    // If player is moving right but not jumping
	    else if (this.playerDX > 0 && this.playerDY == 0)
	    {
		// Draws head at this x and y value, with direction given
		this.playerHead.HeadDraw (g, this.playerX + 2, this.playerY + 2, this.playerDirection);
	    }
	    // If player is moving left but not jumping
	    else if (this.playerDX < 0 && this.playerDY == 0)
	    {
		// Draws head at this x and y value, with direction given
		this.playerHead.HeadDraw (g, this.playerX - 2, this.playerY + 2, this.playerDirection);
	    }
	    // If player is jumping and looking right
	    else if (this.playerDY != 0 && this.facingRight == true)
	    {
		// Draws head at this x and y value, with direction given
		this.playerHead.HeadDraw (g, this.playerX - 7, this.playerY + 2, this.playerDirection);
	    }
	    // If player is jumping and looking left
	    else if (this.playerDY != 0 && this.facingRight == false)
	    {
		// Draws head at this x and y value, with direction given
		this.playerHead.HeadDraw (g, this.playerX - 5, this.playerY + 3, this.playerDirection);
	    }
	    // Draws arm
	    this.playerArm.armDraw (this.playerDirection, g);
	}
	// If player is dead
	else
	{
	    // If player is facing left
	    if (this.facingRight == false)
	    {
		// Draw image of player dying left, at coordinates determined
		g.drawImage (playerDieLeft, (int) this.playerX - 4, (int) this.playerY + 13, null);
	    }
	    // If player is facing right
	    else
	    {
		// Draw image of player dying right, at coordinates determined
		g.drawImage (playerDieRight, (int) this.playerX - 4, (int) this.playerY + 13, null);
	    }
	}
    }


    // Method for drawing information above player
    public void drawInfo (int x, int y, Graphics2D g)
    {
	// Sets color for rectangle and draws rectangle
	g.setColor (backGUI);
	g.fill (new RoundRectangle2D.Double (x - 3, y - 57, 115, 63, 10, 10));
	// Sets black color and draw player's name
	g.setColor (Color.black);
	g.drawString (this.playerName, x, y - 45);
	// Gets total lives from main control class
	int totalLives = m.getTotalLives ();
	// If player is first player
	if (player == 1)
	{
	    // Draw string showing the number of frags player 1 got as a function of the number of lives player 2 have left
	    g.drawString ("Frags: " + (-1 * (m.getPlayer2Lives () - totalLives)) + " /" + totalLives, x, y - 35);
	}
	else
	{
	    // Draw string showing the number of frags player 2 got as a function of the number of lives player 1 have left
	    g.drawString ("Frags: " + (-1 * (m.getPlayer1Lives () - totalLives)) + " /" + totalLives, x, y - 35);
	}
	// Draws background rectangle for health
	g.drawRect (x, y - 33, 81, 6);
	// Sets color to green and draws changing player health rectangle
	g.setColor (Color.green);
	g.fillRect (x + 1, y - 32, (int) (this.playerHealth / 1.25), 5);
	// Sets color to black and draws background rectangle for shooting reload
	g.setColor (Color.black);
	g.drawRect (x, y - 25, 51, 2);
	// Sets color to blue and draws changing shooting reload bar
	g.setColor (Color.blue);
	g.fillRect (x + 1, y - 24, 50 * this.playerArm.getWeaponReloadCurrent () / this.playerArm.getWeaponReload (), 1);
	// Sets color to black and draws background rectangle for clip reload
	g.setColor (Color.black);
	g.drawRect (x, y - 21, 51, 2);
	// Sets the color to yellow and draws changing clip reload bar
	g.setColor (Color.yellow);
	g.fillRect (x + 1, y - 20, 50 * this.playerArm.getClipReloadCurrent () / this.playerArm.getClipReload (), 1);
	// Sets color to black and draws information about ammo and reserve ammo for the gun chosen
	g.setColor (Color.black);
	g.drawString ("Ammo Current: " + this.playerArm.getAmmoCurrent (), (float) x, (float) y - 8);
	g.drawString ("AmmoReserve: " + this.playerArm.getAmmoReserve (), (float) x, (float) y + 2);
    }


    // Method that is called to check key states
    public void keyPressed ()
    {
	// Basic workings:
	// Calls InputManager object to check the array if the keyCode is down
	if (this.input.isKeyDown (this.leftKey))
	{
	    // If it is, then the boolean is set to true
	    this.left = true;
	}
	// etc...
	if (this.input.isKeyDown (this.rightKey))
	{
	    this.right = true;
	}
	if (this.input.isKeyDown (this.upKey))
	{
	    this.up = true;
	}
	if (this.input.isKeyDown (this.forwardTurnKey))
	{
	    this.forwardTurn = true;
	}
	if (this.input.isKeyDown (this.backwardTurnKey))
	{
	    this.backwardTurn = true;
	}
	if (this.input.isKeyDown (this.shotKey))
	{
	    this.shot = true;
	}
	// Same method, but this time change delay is checked so that changing does not go too fast
	if (this.input.isKeyDown (this.cycleBackwardKey) && this.changeDelay == 0)
	{
	    this.cycleBackward = true;
	    // Change delay is set to 100 so player has to wait inbetween changes
	    this.changeDelay = 100;
	}
	// Same method, but this time change delay is checked so that changing does not go too fast
	if (this.input.isKeyDown (this.cycleForwardKey) && this.changeDelay == 0)
	{
	    this.cycleForward = true;
	    // Change delay is set to 100 so player has to wait inbetween changes
	    this.changeDelay = 100;
	}
    }


    public void keyReleased ()
    {
	// Basic workings:
	// Calls InputManager object to check the array if the keyCode is up
	if (input.isKeyUp (leftKey))
	{
	    // If it is, then the boolean is set to false
	    this.left = false;
	}
	// etc...
	if (input.isKeyUp (rightKey))
	{
	    this.right = false;
	}
	if (input.isKeyUp (upKey))
	{
	    this.up = false;
	}
	if (input.isKeyUp (forwardTurnKey))
	{
	    this.forwardTurn = false;
	}
	if (input.isKeyUp (backwardTurnKey))
	{
	    this.backwardTurn = false;
	}
	if (input.isKeyUp (shotKey))
	{
	    this.shot = false;
	}
	if (input.isKeyUp (cycleBackwardKey))
	{
	    this.cycleBackward = false;
	}
	if (input.isKeyUp (cycleForwardKey))
	{
	    this.cycleForward = false;
	}
    }


    // Method to return the timer for death so main control knows when to destroy this player
    public int getDeathTimer ()
    {
	// Returns variable mentioned above
	return this.deathTimer;
    }


    // Method to set the health of the player when he gets damaged by bullet
    public void setHealth (double changeInHealth)
    {
	// Decreases the health of the player by the number given
	this.playerHealth -= changeInHealth;
    }


    // Method to return the health of the player
    public double getHealth ()
    {
	// Returns variable mentioned above
	return this.playerHealth;
    }


    // Method to return the x value of the player
    public double getPlayerX ()
    {
	// Returns variable mentioned above
	return this.playerX;
    }


    // Method to return the y value of the player
    public double getPlayerY ()
    {
	// Returns variable mentioned above
	return this.playerY;
    }


    // Method to return the name of the player
    public String getPlayerName ()
    {
	// Returns variable mentioned above
	return this.playerName;
    }
}
