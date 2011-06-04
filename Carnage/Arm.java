/*
    Arm
    Graham McColl, Tom Cui, Shardul Upadhyay
    Mrs.Reid
    ICS4U1
    Date: June 13, 2010
    Due:  June 14, 2010

    The purpose of this class is to create an arm for the player given certain images and hold all the data for shooting and weapons

    Variable dictionary:
    int weaponChosen: the weapon that is chosen (check in class comment codes for more information)
    int player: the player that created this object (check in class comment codes for more information)
    int weaponData [] []: the 2D array holding data for each weapon in the game (check in class comment codes for more information)
    double armX: the x value of the arm
    double armY: the y value of the arm
    Images: images for each arm with appropriate weapons
    AffineTransform rot: for rotating images
    double xComponent: the x component of where the bullet should be created
    double yComponent: the y component of where the bullet should be created
    String color: holds which color of image to import based on player value
*/
import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.Graphics2D;
public class Arm
{
    // Declarations of global variables
    // 1 = Pistol, 2 = Rifle, 3 = Shotgun, 4 = Grenade, 5 = Sniper, 6 = Machinegun
    int weaponChosen = 1;
    // if player == 1, then green player, if player == 2, then blue player
    int player;
    // Weapon data = {ammo current, ammo reserve, reload time current, reload time, reloaded (1 = yes, 0 = no), current clip reload timer, clip reload timer, clip size, damage}
    int weaponData[] [] = {{12, 48, 82, 82, 1, 150, 150, 12, 8}, {8, 24, 50, 50, 1, 100, 100, 8, 7}, {5, 20, 150, 150, 1, 250, 250, 5, 4}, {1, 3, 500, 500, 1, 500, 500, 1, 50}, {2, 10, 180, 180, 1, 700, 700, 2, 35}, {100, 200, 20, 20, 1, 120, 120, 100, 3}};
    double armX, armY;

    Image pistolRight;
    Image pistolLeft;
    Image rifleRight;
    Image rifleLeft;
    Image shotgunRight;
    Image shotgunLeft;
    Image sniperRight;
    Image sniperLeft;
    Image chainRight;
    Image chainLeft;
    Image grenadeRight;
    Image grenadeLeft;

    // Constructor for initializing variables
    public Arm (double x, double y, int player)
    {
	// Assigns x and y values for the arm
	this.armX = x;
	this.armY = y;
	// Assigns which player created this arm
	this.player = player;
	// Calls method to assign proper images
	this.setImages ();
    }


    // Method for settings images for the player
    private void setImages ()
    {
	// Color to add in front of string, by default green
	String color = "Green";
	// Check if the player that created it was blue player
	if (this.player == 2)
	{
	    // Sets string to get blue player images
	    color = "Blue";
	}
	// Assigns images to object variables by using a toolkit to get images in root directory
	this.pistolRight = Toolkit.getDefaultToolkit ().getImage (color + "PistolRight.gif");
	this.pistolLeft = Toolkit.getDefaultToolkit ().getImage (color + "PistolLeft.gif");
	this.rifleRight = Toolkit.getDefaultToolkit ().getImage (color + "RifleRight.gif");
	this.rifleLeft = Toolkit.getDefaultToolkit ().getImage (color + "RifleLeft.gif");
	this.shotgunRight = Toolkit.getDefaultToolkit ().getImage (color + "ShotgunRight.gif");
	this.shotgunLeft = Toolkit.getDefaultToolkit ().getImage (color + "ShotgunLeft.gif");
	this.sniperRight = Toolkit.getDefaultToolkit ().getImage (color + "SniperRight.gif");
	this.sniperLeft = Toolkit.getDefaultToolkit ().getImage (color + "SniperLeft.gif");
	this.chainRight = Toolkit.getDefaultToolkit ().getImage (color + "ChainRight.gif");
	this.chainLeft = Toolkit.getDefaultToolkit ().getImage (color + "ChainLeft.gif");
	this.grenadeRight = Toolkit.getDefaultToolkit ().getImage (color + "GrenadeRight.gif");
	this.grenadeLeft = Toolkit.getDefaultToolkit ().getImage (color + "GrenadeLeft.gif");
    }


    // Method to calculate where to move arm and if to change weapons or not
    public void armCalculate (double x, double y, boolean cycleForward, boolean cycleBackward)
    {
	// If the button has been pressed (sent in from Player class) and weapon chosen is not the last one
	if (cycleForward == true && weaponChosen < 6)
	{
	    // Change weapon that is chosen by 1 positive
	    weaponChosen++;
	}
	// If the button has been pressed (sent in from Player class) and weapon chosen is the last one
	else if (cycleForward == true && weaponChosen == 6)
	{
	    // Set to first weapon
	    weaponChosen = 1;
	}
	// If the button has been pressed (sent in from Player class) and weapon chosen is not the first one
	else if (cycleBackward == true && weaponChosen > 1)
	{
	    // Change weapon that is chosen by 1 negative
	    weaponChosen--;
	}
	// If the button has been pressed (sent in from Player class) and weapon chosen is the first one
	else if (cycleBackward == true && weaponChosen == 1)
	{
	    // Set to last weapon
	    weaponChosen = 6;
	}
	// Assign new arm values based on values gotten
	this.armX = x;
	this.armY = y;
	// If current reload time is equal to zero
	if (weaponData [weaponChosen - 1] [2] == 0)
	{
	    // Set reloaded to true
	    weaponData [weaponChosen - 1] [4] = 1;
	    // Set current reload time to end reload time
	    weaponData [weaponChosen - 1] [2] = weaponData [weaponChosen - 1] [3];
	}
	// If reloaded is false
	else if (weaponData [weaponChosen - 1] [4] == 0)
	{
	    // Subtract one from current reload time
	    weaponData [weaponChosen - 1] [2]--;
	}
	// If ammo is zero
	if (weaponData [weaponChosen - 1] [0] == 0 && weaponData [weaponChosen - 1] [1] > 0)
	{
	    // Start ammo reload timer
	    weaponData [weaponChosen - 1] [5]--;
	    // If ammo reload timer reaches zero
	    if (weaponData [weaponChosen - 1] [5] == 0)
	    {
		// Reset ammo reload timer
		weaponData [weaponChosen - 1] [5] = weaponData [weaponChosen - 1] [6];
		// If ammo in reserve is more than clip size
		if (weaponData [weaponChosen - 1] [1] > weaponData [weaponChosen - 1] [7])
		{
		    // Reloads gun with clip size stored
		    weaponData [weaponChosen - 1] [0] = weaponData [weaponChosen - 1] [7];
		    // Decreases values in reserve by clip size
		    weaponData [weaponChosen - 1] [1] -= weaponData [weaponChosen - 1] [7];
		}
		else
		{
		    // If this is clip's last reload
		    // Reloads gun with clip size stored
		    weaponData [weaponChosen - 1] [0] = weaponData [weaponChosen - 1] [1];
		    // Sets clip size to zero
		    weaponData [weaponChosen - 1] [1] = 0;
		}
		// Sets the reload timer to stored reload timer
		weaponData [weaponChosen - 1] [5] = weaponData [weaponChosen - 1] [6];
	    }
	}
    }


    // Method that is called that checks conditions for shooting and creates bullets
    public void armShoot (double x, double y, double angle, MainControl m)
    {
	// If reloaded is true
	if (weaponData [weaponChosen - 1] [4] == 1 && weaponData [weaponChosen - 1] [0] > 0)
	{
	    // Subtract ammo
	    weaponData [weaponChosen - 1] [0]--;
	    // Set reloaded to false
	    weaponData [weaponChosen - 1] [4] = 0;
	    // If pistol is chosen
	    if (weaponChosen == 1)
	    {
		// Does some trig. calculations to find place to create bullet
		double xComponent = Math.cos (angle + 0.0313) * 32.02;
		double yComponent = Math.sin (angle + 0.0313) * 32.02;
		// Check if player is facing left by looking at angle
		if (angle > (Math.PI / 2) && angle < (Math.PI * 1.5))
		{
		    // Create bullet using the parameters before calculated and other values determined by us
		    m.createBullet (this.armX + xComponent + 25, this.armY + yComponent + 5, 2, 1, angle, 3, player, weaponData [weaponChosen - 1] [8]);
		}
		// If player is facing right
		else
		{
		    // Create bullet using the parameters before calculated and other values determined by us
		    m.createBullet (this.armX + xComponent + 3, this.armY + yComponent + 3, 2, 1, angle, 3, player, weaponData [weaponChosen - 1] [8]);
		}
	    }
	    // If rifle is chosen
	    else if (weaponChosen == 2)
	    {
		// Check if player is facing left by looking at angle
		if (angle > (Math.PI / 2) && angle < (Math.PI * 1.5))
		{
		    // Does some trig. calculations to find place to create bullet
		    double xComponent = Math.cos (angle - 0.2268) * 40.02;
		    double yComponent = Math.sin (angle - 0.2268) * 40.02;
		    // Create bullet using the parameters before calculated and other values determined by us
		    m.createBullet ((this.armX + 23) + xComponent, (this.armY + 3) + yComponent, 2, 1, angle, 5, player, weaponData [weaponChosen - 1] [8]);
		}
		// If player is facing right
		else
		{
		    // Does some trig. calculations to find place to create bullet
		    double xComponent = Math.cos (angle + 0.2075) * 38.83;
		    double yComponent = Math.sin (angle + 0.2075) * 38.83;
		    // Create bullet using the parameters before calculated and other values determined by us
		    m.createBullet (this.armX + xComponent + 5, this.armY + yComponent + 3, 2, 1, angle, 5, player, weaponData [weaponChosen - 1] [8]);
		}
	    }
	    // If shotgun is chosen
	    else if (weaponChosen == 3)
	    {
		// Check if player is facing left by looking at angle
		if (angle > (Math.PI / 2) && angle < (Math.PI * 1.5))
		{
		    // Does some trig. calculations to find place to create bullet
		    double xComponent = Math.cos (angle - 0.0977) * 51.24;
		    double yComponent = Math.sin (angle - 0.0977) * 51.24;
		    // For creating 7 bullets
		    for (int i = 0 ; i < 7 ; i++)
		    {
			// Create bullet using the parameters before calculated and other values determined by us
			m.createBullet (this.armX + xComponent + 24, this.armY + yComponent + 3, 2, 1, angle + ((Math.random () * 0.2) - 0.1), 3 - Math.random (), player, weaponData [weaponChosen - 1] [8]);
		    }
		}
		// If player is facing right
		else
		{
		    // Does some trig. calculations to find place to create bullet
		    double xComponent = Math.cos (angle + 0.1038) * 48.26;
		    double yComponent = Math.sin (angle + 0.1038) * 48.26;
		    // For creating 7 bullets
		    for (int i = 0 ; i < 7 ; i++)
		    {
			// Create bullet using the parameters before calculated and other values determined by us
			m.createBullet (this.armX + xComponent + 5, this.armY + yComponent + 3, 2, 1, angle + ((Math.random () * 0.2) - 0.1), 3 - Math.random (), player, weaponData [weaponChosen - 1] [8]);
		    }
		}
	    }
	    // If grenade is chosen
	    else if (weaponChosen == 4)
	    {
		// Check if player is facing left by looking at angle
		if (angle > (Math.PI / 2) && angle < (Math.PI * 1.5))
		{
		    // Does some trig. calculations to find place to create bullet
		    double xComponent = Math.cos (angle - 0.245) * 37.11;
		    double yComponent = Math.sin (angle - 0.245) * 37.11;
		    // Create bullet using the parameters before calculated and other values determined by us
		    m.createGrenade (this.armX + 24 + xComponent, (this.armY + 3) + yComponent, angle);
		}
		// If player is facing right
		else
		{
		    // Does some trig. calculations to find place to create bullet
		    double xComponent = Math.cos (angle + 0.245) * 37.11;
		    double yComponent = Math.sin (angle + 0.245) * 37.11;
		    // Create bullet using the parameters before calculated and other values determined by us
		    m.createGrenade (this.armX + xComponent + 3, this.armY + yComponent + 3, angle);
		}
	    }
	    // If sniper is chosen
	    else if (weaponChosen == 5)
	    {
		// Check if player is facing left by looking at angle
		if (angle > (Math.PI / 2) && angle < (Math.PI * 1.5))
		{
		    // Does some trig. calculations to find place to create bullet
		    double xComponent = Math.cos (angle - 0.03076) * 65.03;
		    double yComponent = Math.sin (angle - 0.03076) * 65.03;
		    // Create bullet using the parameters before calculated and other values determined by us
		    m.createBullet ((this.armX + 23) + xComponent, (this.armY + 3) + yComponent, 100, 0.5, angle, 7, player, weaponData [weaponChosen - 1] [8]);
		}
		// If player is facing right
		else
		{
		    // Does some trig. calculations to find place to create bullet
		    double xComponent = Math.cos (angle + 0.03124) * 64.03;
		    double yComponent = Math.sin (angle + 0.03124) * 64.03;
		    // Create bullet using the parameters before calculated and other values determined by us
		    m.createBullet (this.armX + xComponent + 5, this.armY + yComponent + 3, 100, 0.5, angle, 7, player, weaponData [weaponChosen - 1] [8]);
		}
	    }
	    // If machine gun is chosen
	    else if (weaponChosen == 6)
	    {
		// Check if player is facing left by looking at angle
		if (angle > (Math.PI / 2) && angle < (Math.PI * 1.5))
		{
		    // Does some trig. calculations to find place to create bullet
		    double xComponent = Math.cos (angle - 0.1289) * 54.45;
		    double yComponent = Math.sin (angle - 0.1289) * 54.45;
		    // Create bullet using the parameters before calculated and other values determined by us
		    m.createBullet ((this.armX + 25) + xComponent, (this.armY + 3) + yComponent, 50, 0.5, angle + ((Math.random () * 0.2) - 0.1), 6 - Math.random (), player, weaponData [weaponChosen - 1] [8]);
		}
		// If player is facing right
		else
		{
		    // Does some trig. calculations to find place to create bullet
		    double xComponent = Math.cos (angle + 0.1364) * 51.48;
		    double yComponent = Math.sin (angle + 0.1364) * 51.48;
		    // Create bullet using the parameters before calculated and other values determined by us
		    m.createBullet (this.armX + xComponent + 3, this.armY + yComponent + 3, 50, 0.5, angle + ((Math.random () * 0.2) - 0.1), 6 - Math.random (), player, weaponData [weaponChosen - 1] [8]);
		}
	    }
	}
    }


    // Method that returns how much the weapon that is being used is reloaded
    public int getWeaponReloadCurrent ()
    {
	// Returns value stated above
	return weaponData [weaponChosen - 1] [2];
    }


    // Method that returns how much the weapon SHOULD be reloaded for the weapon being used
    public int getWeaponReload ()
    {
	// Returns value stated above
	return weaponData [weaponChosen - 1] [3];
    }


    // Method that returns how much ammo there is currently in the clip for the weapon being used
    public int getAmmoCurrent ()
    {
	// Returns value stated above
	return weaponData [weaponChosen - 1] [0];
    }


    // Method that returns how much ammo is in reserve clips for the weapon being used
    public int getAmmoReserve ()
    {
	// Returns value stated above
	return weaponData [weaponChosen - 1] [1];
    }


    // Method that returns how much the clip is reloaded for the weapon being used
    public int getClipReloadCurrent ()
    {
	// Returns value stated above
	return weaponData [weaponChosen - 1] [5];
    }


    // Method that returns how much the clip should be reloaded for the weapon being used
    public int getClipReload ()
    {
	// Returns value stated above
	return weaponData [weaponChosen - 1] [6];
    }


    // Draws the arm using the direction given and a Graphics2D object
    public void armDraw (double armDirection, Graphics2D g)
    {
	// Creates new rotation object for rotating arm
	AffineTransform rot = new AffineTransform ();
	// If pistol is chosen
	if (weaponChosen == 1)
	{
	    // Checks if arm is right facing
	    if (armDirection < (Math.PI / 2) && armDirection > (Math.PI / -2))
	    {
		// Rotates the AffineTransform around a center point given the direction
		rot.rotate (armDirection, this.armX + 3, this.armY + 3);
		// Translates the AffineTransform to the point required
		rot.translate (this.armX - 1, this.armY - 1);
		// draws image with transformations applied to it
		g.drawImage (this.pistolRight, rot, null);
	    }
	    else if (armDirection > (Math.PI / 2) && armDirection < (Math.PI * 1.5))
	    {
		// Changes direction for proper rotation
		armDirection = armDirection - (Math.PI);
		// Rotates the AffineTransform around a center point given the direction
		rot.rotate (armDirection, this.armX + 25, this.armY + 3);
		// Translates the AffineTransform to the point required
		rot.translate (this.armX - 7.5, this.armY - 1);
		// draws image with transformations applied to it
		g.drawImage (this.pistolLeft, rot, null);
	    }
	}
	// If rifle is chosen
	else if (weaponChosen == 2)
	{
	    // Checks if arm is right facing
	    if (armDirection < (Math.PI / 2) && armDirection > (Math.PI / -2))
	    {
		// Rotates the AffineTransform around a center point given the direction
		rot.rotate (armDirection, this.armX + 5, this.armY + 3);
		// Translates the AffineTransform to the point required
		rot.translate (this.armX, this.armY);
		// draws image with transformations applied to it
		g.drawImage (this.rifleRight, rot, null);
	    }
	    else if (armDirection > (Math.PI / 2) && armDirection < (Math.PI * 1.5))
	    {
		// Changes direction for proper rotation
		armDirection = armDirection - (Math.PI);
		// Rotates the AffineTransform around a center point given the direction
		rot.rotate (armDirection, this.armX + 23, this.armY + 3);
		// Translates the AffineTransform to the point required
		rot.translate (this.armX - 16, this.armY);
		// draws image with transformations applied to it
		g.drawImage (this.rifleLeft, rot, null);
	    }
	}
	// If shotgun is chosen
	else if (weaponChosen == 3)
	{
	    // Checks if arm is right facing
	    if (armDirection < (Math.PI / 2) && armDirection > (Math.PI / -2))
	    {
		// Rotates the AffineTransform around a center point given the direction
		rot.rotate (armDirection, this.armX + 5, this.armY + 3);
		// Translates the AffineTransform to the point required
		rot.translate (this.armX, this.armY);
		// draws image with transformations applied to it
		g.drawImage (this.shotgunRight, rot, null);
	    }
	    else if (armDirection > (Math.PI / 2) && armDirection < (Math.PI * 1.5))
	    {
		// Changes direction for proper rotation
		armDirection = armDirection - (Math.PI);
		// Rotates the AffineTransform around a center point given the direction
		rot.rotate (armDirection, this.armX + 24, this.armY + 3);
		// Translates the AffineTransform to the point required
		rot.translate (this.armX - 26, this.armY);
		// draws image with transformations applied to it
		g.drawImage (this.shotgunLeft, rot, null);
	    }
	}
	// If grenade is chosen
	else if (weaponChosen == 4)
	{
	    // Checks if arm is right facing
	    if (armDirection < (Math.PI / 2) && armDirection > (Math.PI / -2))
	    {
		// Rotates the AffineTransform around a center point given the direction
		rot.rotate (armDirection, this.armX + 3, this.armY + 3);
		// Translates the AffineTransform to the point required
		rot.translate (this.armX - 1, this.armY - 1);
		// draws image with transformations applied to it
		g.drawImage (this.grenadeRight, rot, null);
	    }
	    else if (armDirection > (Math.PI / 2) && armDirection < (Math.PI * 1.5))
	    {
		// Changes direction for proper rotation
		armDirection = armDirection - (Math.PI);
		// Rotates the AffineTransform around a center point given the direction
		rot.rotate (armDirection, this.armX + 24, this.armY + 3);
		// Translates the AffineTransform to the point required
		rot.translate (this.armX - 14, this.armY - 1);
		// draws image with transformations applied to it
		g.drawImage (this.grenadeLeft, rot, null);
	    }
	}
	// If sniper rifle is chosen
	else if (weaponChosen == 5)
	{
	    // Checks if arm is right facing
	    if (armDirection < (Math.PI / 2) && armDirection > (Math.PI / -2))
	    {
		// Rotates the AffineTransform around a center point given the direction
		rot.rotate (armDirection, this.armX + 5, this.armY + 3);
		// Translates the AffineTransform to the point required
		rot.translate (this.armX, this.armY - 1);
		// draws image with transformations applied to it
		g.drawImage (this.sniperRight, rot, null);
	    }
	    else if (armDirection > (Math.PI / 2) && armDirection < (Math.PI * 1.5))
	    {
		// Changes direction for proper rotation
		armDirection = armDirection - (Math.PI);
		// Rotates the AffineTransform around a center point given the direction
		rot.rotate (armDirection, this.armX + 23, this.armY + 3);
		// Translates the AffineTransform to the point required
		rot.translate (this.armX - 45, this.armY - 1);
		// draws image with transformations applied to it
		g.drawImage (this.sniperLeft, rot, null);
	    }
	}
	// If machine gun is chosen
	else if (weaponChosen == 6)
	{
	    // Checks if arm is right facing
	    if (armDirection < (Math.PI / 2) && armDirection > (Math.PI / -2))
	    {
		// Rotates the AffineTransform around a center point given the direction
		rot.rotate (armDirection, this.armX + 3, this.armY + 3);
		// Translates the AffineTransform to the point required
		rot.translate (this.armX - 1, this.armY - 1);
		// draws image with transformations applied to it
		g.drawImage (this.chainRight, rot, null);

	    }
	    else if (armDirection > (Math.PI / 2) && armDirection < (Math.PI * 1.5))
	    {
		// Changes direction for proper rotation
		armDirection = armDirection - (Math.PI);
		// Rotates the AffineTransform around a center point given the direction
		rot.rotate (armDirection, this.armX + 25, this.armY + 3);
		// Translates the AffineTransform to the point required
		rot.translate (this.armX - 29, this.armY - 1);
		// draws image with transformations applied to it
		g.drawImage (this.chainLeft, rot, null);
	    }
	}
    }
}


