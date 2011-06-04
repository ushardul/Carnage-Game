/*
EntryPoint
Graham McColl, Tom Cui, Shardul Upadhyay
Mrs.Reid
ICS4U1
Date: June 13, 2010
Due:  June 14, 2010

The purpose is to start the JFrame and call the MainControl class. 

*/

import javax.swing.*;

public class EntryPoint extends JFrame
{
    //EntryPoint method that is passed both players names.
    public EntryPoint (String player1, String player2)
    {
    //adds the MainControl to the ContentPane panel.
	getContentPane ().add (new MainControl (player1, player2));
	//sets panel size of 807 x 831.
	setSize (807, 831);
	//sets visibility to true.
	setVisible (true);
	// Sets if windows can be closed from x button or not
	setDefaultCloseOperation (EXIT_ON_CLOSE);
	// Centers windows
	setLocationRelativeTo (null);
	//makes it so the window can not be resized.
	setResizable (false);
    }
}

