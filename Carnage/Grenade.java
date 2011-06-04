/*
Grenade
Tom Cui, Shardul Upadhyay, Graham McColl
Ms.Reid
ICS4U1
June 14, 2010

Purpose: The purpose of this class is to control the grenade, where it is, and when it explodes

Variable Dictionary:
grenadeX = original x coordinate of the grenade
grenadeY = original y coordinate of the grenade
grenadeDX = the amount of horizontal displacement from the original x point to the next
grenadeDY = the amount of vertical displacement from the original x point to the next
grenade = stores the picture of the grenade
gravity = stores the gravity of the grenade
explosionSize = stores the speed of the blast
player1Hit = stores whether or not player 1 was hit
player2Hit = stores wheter or not player 2 was hit
timer = stores how long until the grenade explodes
g = stores the image of the grenade
*/
import java.awt.*;
public class Grenade
{
    private double grenadeX, grenadeY, grenadeDX, grenadeDY; // declares variables
    private double gravity = 0.08; //sets graivty to 0.08
    private int timer = 600; //sets the timer until grenades explode
    private Image grenade = Toolkit.getDefaultToolkit ().getImage ("Grenade.gif"); //declares the image of a grenade and assigns it an image
    private double explosionSize = 2; // determines speed of the blast
    private boolean player1Hit = false, player2Hit = false; // sets the booleans of whether each player was hit to default false

    public Grenade (double x, double y, double direction)  //the grenade constructor
    {
	this.grenadeX = x; //assigns a value to grenadeX from parameters
	this.grenadeY = y; //assigns a value to grenadeY from parameters
	this.grenadeDX = Math.cos (direction) * 5; //creates the horizontal displacement of the grenade
	this.grenadeDY = Math.sin (direction) * 5; //creates the vertical displacement of the grenade
    }


    public void grenadeCalculate ()  //the calculate method do control detonation
    {
	if (this.timer != 0) // if the timer has not reached zero
	{
	    this.grenadeDY += gravity; //increase the grenade's vertical displacement
	    this.grenadeX += grenadeDX; // move the grenade horizontally
	    this.grenadeY += grenadeDY; // move the grenade vertically
	    this.timer--; // decrease the timer
	}
	else // if the timer is zero
	{
	    this.grenadeX -= 1; //move the grenade horizontally by 1
	    this.grenadeY -= 1; //move the grenade vertically by 1
	    this.explosionSize += 2; // increase the size of the explosion
	}
    }


    public void grenadeDraw (Graphics2D g)  //draws the grenade
    {
	if (this.timer != 0) //if the grenade had not exploded yet
	{
	    g.drawImage (this.grenade, (int) this.grenadeX, (int) this.grenadeY, null); //draw the grenade at a certain position
	}
	else //if the grenade has exploded
	{
	    g.setColor (Color.black); // sets explosion color to black
	    g.drawOval ((int) this.grenadeX, (int) this.grenadeY, (int) this.explosionSize, (int) this.explosionSize); //draw the explosion radius
	}
    }


    public double getGrenadeX ()  //this method returns the x coordinate of the grenade
    {
	return this.grenadeX; //returns the x coordinate
    }


    public double getGrenadeY ()  //this method returns the y coordinate of the grenade
    {
	return this.grenadeY; //returns the y coordinate
    }


    public void setGrenadeDX (double dx)  //this method sets the x displacement of the grenade
    {
	this.grenadeDX = dx; //sets the x displacement
    }


    public void setGrenadeDY (double dy)  //this method sets the y displacement of the grenade
    {
	this.grenadeDY = dy; //sets the y displacement
    }


    public void setGravity (double gravity)  //sets the gravity of the grenade
    {
	this.gravity = gravity; //transforms the gravity from the class into the gravity sent through the parameters
    }


    public double getExplosionSize ()  //this coordinate retuns the size of the explosion
    {
	return this.explosionSize; //returns the explosion size
    }


    public boolean getPlayer1Hit ()  // returns whether the player1 was hit
    {
	return this.player1Hit; //returns the boolean
    }


    public boolean getPlayer2Hit ()  // returns whether the player2 was hit
    {
	return this.player2Hit; //returns the boolean
    }


    public void setPlayer1Hit (boolean hit)  //sets whether the player1 was hit
    {
	this.player1Hit = hit; //sets the boolean
    }


    public void setPlayer2Hit (boolean hit)  //sets whether the player2 was hit
    {
	this.player2Hit = hit; //sets the boolean
    }
}
