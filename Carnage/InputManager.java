/*
InputManager
Graham McColl, Tom Cui, Shardul Upadhyay
Ms.Reid
ICS4U1
Date: June 13, 2010
Due:  June 14, 2010

The purpose is to get keys pressed from the keyboard from the user.

Variables Used:
key_state_up - array that holds the key states that are up.
key_state_down - array that holds the key states that are down.
keyPressed - booblean value to check when a key is pressed.
keyReleased - boolean value to check when a key is released.
   */

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public final class InputManager implements KeyListener
{

    //variable initilization.
    private boolean[] key_state_up = new boolean [256];
    private boolean[] key_state_down = new boolean [256];
    private boolean keyPressed = false;
    private boolean keyReleased = false;

    //Keypressed method that check for when a key is pressed.
    public void keyPressed (KeyEvent e)
    {
	//if the key is in range of 0 and 256
	if (e.getKeyCode () >= 0 && e.getKeyCode () < 256)
	{
	    //sets the key_state_down state to true in the array
	    key_state_down [e.getKeyCode ()] = true;
	    //sets the  key_state_up to false because key_state_down is now true in array
	    key_state_up [e.getKeyCode ()] = false;
	    //the keypressed is now true.
	    keyPressed = true;
	    //the key has yet to be released
	    keyReleased = false;
	}
    }


    //Keyreleased method that check for when a key is released.
    public void keyReleased (KeyEvent e)
    {
	//if the key is in range of 0 and 256
	if (e.getKeyCode () >= 0 && e.getKeyCode () < 256)
	{
	    //sets the  key_state_up to true.
	    key_state_up [e.getKeyCode ()] = true;
	    //sets the key_state_down state to false because key_state_up is now true.
	    key_state_down [e.getKeyCode ()] = false;
	    //the key is not currently pressed down.
	    keyPressed = false;
	    //the key has been released.
	    keyReleased = true;
	}
    }


    //Required to be implemented
    public void keyTyped (KeyEvent e)
    {
    }


    //boolean method to check if the key is down.
    public boolean isKeyDown (int key)
    {
	//returns the current state of the key.
	return key_state_down [key];
    }


    //boolean method to check if the key is up.
    public boolean isKeyUp (int key)
    {
	//returns the current state of key.
	return key_state_up [key];
    }
}
