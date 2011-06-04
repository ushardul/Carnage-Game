/*
Head
Tom Cui, Shardul Upadhyay, Graham McColl
Ms.Reid
ICS4U1
June 14, 2010

Purpose: The purpose is to rotate a player's head based on the angle his gun is pointing and the direction his body is pointing.

Variable Dictionary:
headLeft = an image of the head facing left
headRight = an image of the head facing right
color = stores the type of color used for the head
angle = stores the angle of the head
g = stores the image of the head
rot = stores how much the head of will rotated
*/
import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;
import java.awt.Image;
import java.awt.Toolkit;
public class Head
{
    private Image headRight, headLeft; // image declaration

    public Head (int player) // the Head constructor
    {
	this.setImages (player); //gets the image based on which player this is for
	this.headRight = headRight; // assigns head left and right
	this.headLeft = headLeft;
    }


    private void setImages (int player) //sets the images based on which player it is for
    {
	String color = "Green"; //the color is green assuming that it is player 1
	if (player == 2) //if it is player 2 and not player 1
	{
	    color = "Blue"; //change the colour to blue instead
	}
	this.headRight = Toolkit.getDefaultToolkit ().getImage (color + "HeadRight.gif"); //assigns an image on to headRight
	this.headLeft = Toolkit.getDefaultToolkit ().getImage (color + "HeadLeft.gif"); //assigns an image on to headLeft
    }


    public void HeadDraw (Graphics2D g, double x, double y, double angle) //this method draws the heads based on java's angles
    {
	AffineTransform rot = new AffineTransform (); // declares affinetransform rot
	if (angle < (Math.PI / 4) && angle > (Math.PI / -4)) // if the angle is inbetween 45 degrees and - 45 degrees
	{
	    rot.rotate (angle, x + 14, y + 13); // rotate the head based on the angle and location
	    rot.translate (x + 7, y + 2); //move the head
	    g.drawImage (this.headRight, rot, null); //draw the new head
	}
	else if (angle < (Math.PI / -4)) // if the angle is only under -45 degrees
	{
	    rot.rotate ((Math.PI / -4), x + 14, y + 13);// rotate the head based on the angle and location
	    rot.translate (x + 7, y + 2);//move the head
	    g.drawImage (this.headRight, rot, null);//draw the new head
	}
	else if (angle > (Math.PI / 4) && angle < (Math.PI / 2)) //if the angle is more than 45 degrees and less than 90 degrees
	{
	    rot.rotate ((Math.PI / 4), x + 14, y + 13);// rotate the head based on the angle and location
	    rot.translate (x + 7, y + 2);//move the head
	    g.drawImage (this.headRight, rot, null);//draw the new head
	}
	else if (angle > (0.75 * Math.PI) && angle < (Math.PI * 1.25)) //if the angle is more than 135 degrees but less than 225 degrees
	{
	    angle = angle - Math.PI; //angle becomes angle - 180 degrees
	    rot.rotate (angle, x + 16, y + 13);// rotate the head based on the angle and location
	    rot.translate (x + 8, y + 2);//move the head
	    g.drawImage (this.headLeft, rot, null);//draw the new head
	}
	else if (angle < (0.75 * Math.PI) && angle > (Math.PI / 2)) //if the angle is less than 135 degrees but more than 90 degrees
	{
	    rot.rotate ((Math.PI * -0.25), x + 16, y + 13);// rotate the head based on the angle and location
	    rot.translate (x + 8, y + 2);//move the head
	    g.drawImage (this.headLeft, rot, null);//draw the new head
	}
	else if (angle < (1.5 * Math.PI) && angle > (Math.PI * 1.25)) // if the angle is less than 270 degrees but more than 225
	{
	    rot.rotate ((Math.PI * 0.25), x + 16, y + 13);// rotate the head based on the angle and location
	    rot.translate (x + 8, y + 2);//move the head
	    g.drawImage (this.headLeft, rot, null);//draw the new head
	}
    }
}


