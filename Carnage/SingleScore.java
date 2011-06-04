/*
MainMenu
Graham McColl, Tom Cui, Shardul Upadhyay
Ms.Reid
ICS4U1
Date: June 13, 2010
Due:  June 14, 2010

Purpose: The purpose is to store all values from a single player and calculate the KDR (Kill/Death Ratio) and Win/Loss ratio.

Variables Used:
kdr, wlr - stores the players KD and WL ratios.
    playerWin, playerLose, playerKill, playerDeath - stores the players win/lose/kill/death.
    name - players name.
bfr - buffered reader read in from the SingleScore method.

*/
import java.io.*;
class SingleScore
{
    //Variable Declaration.
    public double kdr, wlr;
    public String playerWin, playerLose, playerKill, playerDeath;
    public String name;

    //This constructor reads in values from text file.
    public SingleScore (BufferedReader bfr)
    {
	try
	{
	    //reads these values in order.
	    name = bfr.readLine ();
	    playerWin = bfr.readLine ();
	    playerLose = bfr.readLine ();
	    playerKill = bfr.readLine ();
	    playerDeath = bfr.readLine ();
	}
	//if the text file is empty.
	catch (IOException exc)
	{
	    //calls method to end the program.
	    this.ExitProgram ("Error in File. Exiting...");
	}
	//if player death is not 0, let the kdr be the player kills/deaths.
	if (!playerDeath.equals ("0"))
	{
	    this.kdr = Double.parseDouble (playerKill) / Double.parseDouble (playerDeath);
	}
	//else if the player death is zero, kdr becomes the player kills.
	else
	{
	    this.kdr = Double.parseDouble (playerKill);
	}
	//if the player does not lose 0 times, win/lose ratio equals wins divided by loses.
	if (!playerLose.equals ("0"))
	{
	    this.wlr = Double.parseDouble (playerWin) / Double.parseDouble (playerLose);
	}
	//else if the player did lose 0 times, win/lose ratio equals win.
	else
	{
	    this.wlr = Double.parseDouble (playerWin);
	}
    }


    //this is an overloaded constructor for singleScore and the player info is given from the game.
    public SingleScore (String name, int playerWin, int playerLose, int playerKill, int playerDeath)
    {
	//reads in the value from the parameters.
	this.name = name;
	this.playerWin = Integer.toString (playerWin);
	this.playerLose = Integer.toString (playerLose);
	this.playerKill = Integer.toString (playerKill);
	this.playerDeath = Integer.toString (playerDeath);
	//if player death is not 0, let the kdr be the player kills/deaths.
	if (!this.playerDeath.equals ("0"))
	{
	    this.kdr = Double.parseDouble (this.playerKill) / Double.parseDouble (this.playerDeath);
	}
	//if player death is not 0, let the kdr be the player kills/deaths.
	else
	{
	    this.kdr = Double.parseDouble (this.playerKill);
	}
	//if the player does not lose 0 times, win/lose ratio equals wins divided by loses.
	if (!this.playerLose.equals ("0"))
	{
	    this.wlr = Double.parseDouble (this.playerWin) / Double.parseDouble (this.playerLose);
	}
	//else if the player did lose 0 times, win/lose ratio equals win.
	else
	{
	    this.wlr = Double.parseDouble (this.playerWin);
	}
    }


    //this method creates an error statement and exits the program.
    private void ExitProgram (String error)
    {
	try
	{
	    //try to print out the error and has a 2000 delay for the player to read the message.
	    System.out.println (error);
	    Thread.sleep (2000);
	}
	//if there is an interuption, exit the program.
	catch (InterruptedException e)
	{
	    System.exit (0);
	}
    }


    //this method returns the kdr.
    public double getKdr ()
    {
	//return the kdr.
	return this.kdr;
    }


    //this method returns the wlr.
    public double getWlr ()
    {
	//return the wlr.
	return this.wlr;
    }


    //this method returns the win.
    public String getWin ()
    {
	//return the win.
	return this.playerWin;
    }


    //this method returns the loss.
    public String getLoss ()
    {
	//return the lose
	return this.playerLose;
    }


    //this method returns the kill.
    public String getKill ()
    {
	//return the kill.
	return this.playerKill;
    }


    //this method returns the death.
    public String getDeath ()
    {
	//return the death,
	return this.playerDeath;
    }


    //this method returns the name.
    public String getName ()
    {
	//return the name.
	return this.name;
    }


    //this method sets the kill.
    public void setKill (int kill)
    {
	//the kill becomes the current amount of kills plus the new amount of kill
	this.playerKill = Integer.toString (Integer.parseInt (this.playerKill) + kill);
	//run the calculate class to recalculate the values.
	this.CalculateStats ();
    }


    //this method sets the death.
    public void setDeath (int death)
    {
	//the death becomes the current amount of deaths plus the new amount of deaths.
	this.playerDeath = Integer.toString (Integer.parseInt (this.playerDeath) + death);
	//run the calculate class to recalculate the values.
	this.CalculateStats ();
    }


    //this method sets the win
    public void setWin (int win)
    {
	//the win becomes the current amount of wins plus the new amount of win.
	this.playerWin = Integer.toString (Integer.parseInt (this.playerWin) + win);
	//run the calculate class to recalculate the values.
	this.CalculateStats ();
    }


    //this method sets the loss
    public void setLoss (int loss)
    {
	//the loss becomes the current amount of loses plus the new amount of loss.
	this.playerLose = Integer.toString (Integer.parseInt (this.playerLose) + loss);
	//run the calculate class to recalculate the values.
	this.CalculateStats ();
    }


    //this method calculates the stats such as kdr and wlr.
    public void CalculateStats ()
    {
	//if the player does not die 0 times.
	if (!this.playerDeath.equals ("0"))
	{
	    //kdr becomes the amout of kills divided by amount of deaths rounded to 2 decimal places.
	    this.kdr = Double.parseDouble (this.playerKill) / Double.parseDouble (this.playerDeath) * 10 / 10;
	}
	//if the player did die zero times
	else
	{
	    //kdr becomes the amount of kills
	    this.kdr = Double.parseDouble (this.playerKill);
	}
	//if the player did not lose 0 times.
	if (!this.playerLose.equals ("0"))
	{
	    //wlr becomes the amout of wins divided by amount of deaths rounded to 2 decimal places.
	    this.wlr = Double.parseDouble (this.playerWin) / Double.parseDouble (this.playerLose) * 10 / 10;
	}
	//if the player did lose zero times
	else
	{
	    //wlr becomes the amount of wins.
	    this.wlr = Double.parseDouble (this.playerWin);
	}
    }
}
