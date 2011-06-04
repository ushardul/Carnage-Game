/*
Bullet
Tom Cui, Shardul Upadhyay, Graham McColl
Ms.Reid
ICS4U1
June 14, 2010

Purpose: The purpose is to draw a bullet given its coordinates, its speed and its shape

Variable Dictionary:
bulletX = is starting x coordinate,
bulletY = is starting y coordinate
bulletLength = the length dimension of the bullet
bulletWidth = the width dimension of the bullet
bulletAngle = the trajectory of the bullet
bulletSpeed = how fast the bullet is going
bulletDX = the amount the bullets point changes horizontally
bulletDY = the amount the bullets point changes vertically
bulletDamage = the amount of damage a bullet does.
playerCreated = tells the program which player created the bullet
tempBounding = is the rectangle used for collision detecting
bulletRot = is used to rotate the bullet
g = stores the image of the bullet
*/
import java.awt.geom.*;
import java.awt.Graphics2D;
import java.awt.Color;
import java.awt.geom.AffineTransform;
import java.awt.geom.GeneralPath;
import java.awt.geom.Line2D;
public class Bullet
{
    double bulletX, bulletY, bulletDX, bulletDY, bulletLength, bulletWidth, bulletAngle, bulletSpeed, bulletDamage;
    int playerCreated;
    private GeneralPath tempBounding = new GeneralPath (); // declares tempBounding
    private AffineTransform bulletRot = new AffineTransform (); // declares bulletRot
    public Bullet (double bulletX, double bulletY, double bulletLength, double bulletWidth, double bulletAngle, double bulletSpeed, int playerCreated, double bulletDamage)  // bullet constructor
    {
	// Assigns parameter values onto object variables
	this.bulletX = bulletX;
	this.bulletY = bulletY;
	this.bulletDX = (Math.cos (bulletAngle) * bulletSpeed); // Performs trig in order to calculate a new component x
	this.bulletDY = (Math.sin (bulletAngle) * bulletSpeed); // Performs trig in order to calculate a new component y
	this.bulletLength = bulletLength;
	this.bulletWidth = bulletWidth;
	this.bulletAngle = bulletAngle;
	this.bulletSpeed = bulletSpeed;
	this.bulletDamage = bulletDamage;
	this.playerCreated = playerCreated;
	this.tempBounding.moveTo ((float) (bulletX), (float) (bulletY - bulletWidth)); // These four tempbounding commands are used to draw 4 lines depending on the length and width of bullet
	this.tempBounding.lineTo ((float) (bulletX + bulletLength), (float) (bulletY - bulletWidth));
	this.tempBounding.lineTo ((float) (bulletX + bulletLength), (float) (bulletY + bulletWidth));
	this.tempBounding.lineTo ((float) (bulletX), (float) (bulletY + bulletWidth));
	this.tempBounding.closePath (); // closes the rectangle
	this.bulletRot.rotate (bulletAngle, bulletX, bulletY); //rotates the rectangle based on the angle given on the x and y points
	this.tempBounding.transform (bulletRot); // performs the rotation
    }


    public void Calculate ()  //this is the calculate method that finds the new coordinates
    {
	this.bulletRot = new AffineTransform (); //creates the bulletRot
	this.bulletRot.translate (this.bulletDX, this.bulletDY); // moves the bullet to a coordinate
	this.tempBounding.transform (this.bulletRot); //rotates the bullet
	this.bulletX += this.bulletDX; //sets the new coordinate
	this.bulletY += this.bulletDY; //sets the new coordinate
    }


    public GeneralPath getBounding ()  //this method returns tempBounding
    {
	return tempBounding;
    }


    public int getPlayerCreated ()  //this method returns which player shot the bullet
    {
	return playerCreated;
    }


    public void drawBullet (Graphics2D g)  //this method actually draws the bullet and colours it red
    {
	g.setColor (Color.yellow); //sets the color to red
	g.fill (tempBounding); // draws the rectangle tempbounding
    }
}
