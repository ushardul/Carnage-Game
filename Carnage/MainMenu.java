/*
MainMenu
Graham McColl, Tom Cui, Shardul Upadhyay
Ms.Reid
ICS4U1
Date: June 13, 2010
Due:  June 14, 2010

Purpose: The purpose is to create a user friendly menu using swing.

Variables Used:
   fw - filewriter used to write setting info to file.
 contentPane - Main content panel.
 mainScreen = reads in "carnage2.jpg"
    myImage = assigns the "carnage2.jpg" image.
    instructionScreen = reads "instructions.jpg".
    instruc = assigns the "instructions.jpg" image.
   instructionScreen2 = reads in the "instructions2.jpg".
    instruc2 = assigns the "instructions2.jpg" image.
    creditScreen = reads in the "credits.jpg".
    credit = assigns the "credits.jpg" image.
   startScreen = reads in the "start.jpg".
    startsc = assigns the "start.jpg" image.
    settingScreen = reads in the "settings6.jpg".
    settingsc = assigns the "settings6.jpg" image.
   start, settings, credits, instructions, exit,  back, hs, toggle - JButtons used throughout the menu.
    check - int checker used to test for the action performed.
    checker - int checker used to set the visibility of buttons.
    instruccheck - int checker used to check what instruction screen is present.
    map - stores which map was selected.
    frag - stores number of frags in a game.
    speed - stores the speed of the game.
    textField, textField2 - two textfields used to read in the names from the user.
    groupmaps - button group used to group all the map JCheckBoxes.
       groupfrags - button group used to group all the frag JCheckBoxes.
   groupspeed - button group used to group all the speed JCheckBoxes.
    map1, map2, map3, map4, frag1, frag2, frag3, frag4, frag5, neg1, neg05, zero, pos05, pos1 - all checkboxes used to get map/frag/speed info from user.
    String player1, player2 - holds player1 and player 2's name.
    t - declares a thread.
    command - object that gets source of command pressed.
    stopThread - boolean to stop thread

    Methods Used:
    main - used to call "MainMenu" constructor.
    MainMenu - the main constructor used in all the initialize of the buttons, layout, textfields, etc.
    run - threading.
    paint - method used to paint all the imported jpg's.
    setVisible - sets the visibilty of JButtons, JCheckBoxes, and JTextFields.
    actionPerformed - checks if an action is performed.
*/

//imports all necessary packages.
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.io.*;
import java.awt.image.*;

class MainMenu extends JFrame implements ActionListener, Runnable, MouseListener

{
    //all global variable used through out the class.
    FileWriter fw = null;
    JPanel contentPane;
    ImageIcon mainScreen = new ImageIcon ("carnage2.jpg");
    Image myImage = mainScreen.getImage ();
    ImageIcon instructionScreen = new ImageIcon ("instructions.jpg");
    Image instruc = instructionScreen.getImage ();
    ImageIcon instructionScreen2 = new ImageIcon ("instructions2.jpg");
    Image instruc2 = instructionScreen2.getImage ();
    ImageIcon creditScreen = new ImageIcon ("credits.jpg");
    Image credit = creditScreen.getImage ();
    ImageIcon startScreen = new ImageIcon ("start.jpg");
    Image startsc = startScreen.getImage ();
    ImageIcon settingScreen = new ImageIcon ("settings6.jpg");
    Image settingsc = settingScreen.getImage ();
    JButton start, settings, credits, instructions, exit, back, hs, toggle;
    int check = 0;
    int checker = 0;
    int instruccheck = 0;
    int map = 1;
    int frag = 3;
    double speed = 0;
    JTextField textField, textField2;
    ButtonGroup groupmaps = new ButtonGroup ();
    ButtonGroup groupfrags = new ButtonGroup ();
    ButtonGroup groupspeed = new ButtonGroup ();
    JCheckBox map1, map2, map3, map4, frag1, frag2, frag3, frag4, frag5, neg1, neg05, zero, pos05, pos1;
    String player1, player2;
    Thread t;
    boolean stopThread = false;


    // Creates a new JFrame
    public static void main (String args[])
    {
	new MainMenu ();
    }


    // Constructor for the class
    public MainMenu ()
    {
	//sets panel size of 800 x 800.
	this.setSize (800, 800);
	// Sets if windows can be closed from x button or not
	this.setDefaultCloseOperation (EXIT_ON_CLOSE);
	// Centers windows
	this.setLocationRelativeTo (null);
	//unable to resize the window.
	this.setResizable (false);
	// Gets root pane
	contentPane = (JPanel) this.getContentPane ();
	//layout is set to null allowing a canvas style layout.
	contentPane.setLayout (null);

	//creates all the JButtons used.
	start = new JButton ("Start");
	settings = new JButton ("Settings");
	credits = new JButton ("Credits");
	instructions = new JButton ("Instructions");
	hs = new JButton ("High Scores");
	exit = new JButton ("Exit");
	back = new JButton ("Back");
	toggle = new JButton ("toggle");
	//creates all the map JCheckBoxes used.
	map1 = new JCheckBox ("map1");
	//has map1 as the default selected map.
	map1.setSelected (true);
	map2 = new JCheckBox ("map2");
	map3 = new JCheckBox ("map3");
	map4 = new JCheckBox ("map4");
	//creates all the frag JCheckBoxes used.
	frag1 = new JCheckBox ("3 frags");
	//has frag1 as the default selected frag number.
	frag1.setSelected (true);
	frag2 = new JCheckBox ("5 frags");
	frag3 = new JCheckBox ("7 frags");
	frag4 = new JCheckBox ("10 frags");
	frag5 = new JCheckBox ("15 frags");
	//creates all the speed JCheckBoxes used.
	neg1 = new JCheckBox ("Negative 1");
	neg05 = new JCheckBox ("Negative 0.5");
	zero = new JCheckBox ("Normal");
	//has zero as the default selected speed.
	zero.setSelected (true);
	pos05 = new JCheckBox ("Positive 0.5");
	pos1 = new JCheckBox ("Positive 1");

	//creates textfield, sets the bounds and sets the visibility.
	textField = new JTextField ("", 20);
	textField.setBounds (255, 64, 288, 66);
	textField.setVisible (false);

	//creates textfield2, sets the bounds and sets visibilty.
	textField2 = new JTextField ("", 20);
	textField2.setBounds (248, 303, 298, 63);
	textField2.setVisible (false);

	//adds textfield and textfield2 to the JPanel.
	contentPane.add (textField);
	contentPane.add (textField2);

	//sets the bounds for the start button, sets visibility and adds to the JPanel.
	start.setBounds (63, 165, 155, 18);
	start.setVisible (true);
	contentPane.add (start);

	//sets the bounds for the setting's button, sets visibility and adds to the JPanel.
	settings.setBounds (63, 200, 220, 18);
	settings.setVisible (true);
	contentPane.add (settings);

	//sets the bounds for the instructions button, sets visibility and adds to the JPanel.
	instructions.setBounds (63, 232, 300, 18);
	instructions.setVisible (true);
	contentPane.add (instructions);

	//sets the bounds for the credits button, sets visibility and adds to the JPanel.
	credits.setBounds (63, 267, 200, 18);
	credits.setVisible (true);
	contentPane.add (credits);

	//sets the bounds for the hs button, sets visibility and adds to the JPanel.
	hs.setBounds (63, 305, 300, 18);
	hs.setVisible (true);
	contentPane.add (hs);


	//sets the bounds for the hs button, sets visibility and adds to the JPanel.
	exit.setBounds (63, 333, 130, 18);
	exit.setVisible (true);
	contentPane.add (exit);

	//sets the bounds for the back button, sets visibility and adds to the JPanel.
	back.setBounds (24, 732, 100, 20);
	back.setVisible (true);
	contentPane.add (back);

	//sets the bounds for the toggle button, sets visibility and adds to the JPanel.
	toggle.setBounds (345, 732, 123, 20);
	toggle.setVisible (false);
	contentPane.add (toggle);

	//adds the JCheckBoxes map1, map2, map3, and map4 to groupmaps
	groupmaps.add (map1);
	groupmaps.add (map2);
	groupmaps.add (map3);
	groupmaps.add (map4);

	//sets the bounds for the map1 checkbox, sets visibility and adds to the JPanel.
	map1.setBounds (63, 251, 50, 20);
	map1.setVisible (false);
	contentPane.add (map1);

	//sets the bounds for the map2 checkbox, sets visibility and adds to the JPanel.
	map2.setBounds (144, 250, 50, 20);
	map2.setVisible (false);
	contentPane.add (map2);

	//sets the bounds for the map3 checkbox, sets visibility and adds to the JPanel.
	map3.setBounds (224, 250, 50, 20);
	map3.setVisible (false);
	contentPane.add (map3);

	//sets the bounds for the map4 checkbox, sets visibility and adds to the JPanel.
	map4.setBounds (305, 249, 50, 20);
	map4.setVisible (false);
	contentPane.add (map4);

	//adds the JCheckBoxes frag1, frag2, frag3, frag4 and frag5 to groupfrags.
	groupfrags.add (frag1);
	groupfrags.add (frag2);
	groupfrags.add (frag3);
	groupfrags.add (frag4);
	groupfrags.add (frag5);

	//sets the bounds for the frag1 checkbox, sets visibility and adds to the JPanel.
	frag1.setBounds (62, 432, 50, 20);
	frag1.setVisible (false);
	contentPane.add (frag1);

	//sets the bounds for the frag2 checkbox, sets visibility and adds to the JPanel.
	frag2.setBounds (142, 432, 50, 20);
	frag2.setVisible (false);
	contentPane.add (frag2);

	//sets the bounds for the frag3 checkbox, sets visibility and adds to the JPanel.
	frag3.setBounds (223, 430, 50, 20);
	frag3.setVisible (false);
	contentPane.add (frag3);

	//sets the bounds for the frag4 checkbox, sets visibility and adds to the JPanel.
	frag4.setBounds (303, 430, 50, 20);
	frag4.setVisible (false);
	contentPane.add (frag4);

	//sets the bounds for the frag5 checkbox, sets visibility and adds to the JPanel.
	frag5.setBounds (384, 430, 50, 20);
	frag5.setVisible (false);
	contentPane.add (frag5);

	//sets the bounds for the neg1 checkbox, sets visibility and adds to the JPanel.
	neg1.setBounds (64, 610, 50, 20);
	neg1.setVisible (false);
	contentPane.add (neg1);

	//sets the bounds for the neg05 checkbox, sets visibility and adds to the JPanel.
	neg05.setBounds (143, 610, 50, 20);
	neg05.setVisible (false);
	contentPane.add (neg05);

	//sets the bounds for the zero checkbox, sets visibility and adds to the JPanel.
	zero.setBounds (223, 610, 50, 20);
	zero.setVisible (false);
	contentPane.add (zero);

	//sets the bounds for the pos05 checkbox, sets visibility and adds to the JPanel.
	pos05.setBounds (303, 610, 50, 20);
	pos05.setVisible (false);
	contentPane.add (pos05);

	//sets the bounds for the pos01 checkbox, sets visibility and adds to the JPanel.
	pos1.setBounds (383, 610, 50, 20);
	pos1.setVisible (false);
	contentPane.add (pos1);

	//adds the JCheckBoxes neg1, neg05, zero, pos05, pos1 to groupspeed.
	groupspeed.add (neg1);
	groupspeed.add (neg05);
	groupspeed.add (zero);
	groupspeed.add (pos05);
	groupspeed.add (pos1);

	//creates all the action listeners for the JButtons.
	start.addActionListener (this);
	settings.addActionListener (this);
	credits.addActionListener (this);
	instructions.addActionListener (this);
	exit.addActionListener (this);
	back.addActionListener (this);

	hs.addActionListener (this);
	toggle.addActionListener (this);
	//creates all the action listeners for the JCheckBoxes.
	map1.addActionListener (this);
	map2.addActionListener (this);
	map3.addActionListener (this);
	map4.addActionListener (this);
	frag1.addActionListener (this);
	frag2.addActionListener (this);
	frag3.addActionListener (this);
	frag4.addActionListener (this);
	frag5.addActionListener (this);
	neg1.addActionListener (this);
	neg05.addActionListener (this);
	zero.addActionListener (this);
	pos05.addActionListener (this);
	pos1.addActionListener (this);
	// Gets input from mouse
	addMouseListener (this);
	//sets the visibility to true.
	this.setVisible (true);
	t = new Thread (this);
	t.start ();
    }


    // Called when thread is initialized
    public void run ()
    {
	// Keeps going until requested to stop
	while (stopThread == false)
	{
	    // If the start screen is not chosen, repaint the screen
	    if (check != 1)
	    {
		repaint ();
	    }
	    // Tries to sleep the thread
	    try
	    {
		// If the screen is not the settings screen
		if (check != 4)
		{
		    // Sleep the thread for that 1 ms
		    Thread.sleep (1);
		}
		else
		{
		    // Otherwise sleep thread for 60 ms
		    Thread.sleep (60);
		}
	    }
	    // Catch interruption in sleeping thread
	    catch (InterruptedException e)
	    {
	    }
	}
    }


    public void paint (Graphics g)
    {
	//if check equals 0, draw the first screen.
	if (check == 0)
	{
	    g.drawImage (myImage, 0, 0, null);
	}
	//if check equals 1, draw the start screen.
	else if (check == 1)
	{
	    g.drawImage (startsc, 0, 0, null);
	}
	//if check equals 2, draw the instructions screen.
	else if (check == 2)
	{
	    g.drawImage (instruc, 0, 0, null);
	}
	//if check equals 3, draw the credits screen.
	else if (check == 3)
	{
	    g.drawImage (credit, 0, 0, null);
	}
	//if check equals 4, draw the settings screen.
	else if (check == 4)
	{
	    g.drawImage (settingsc, 0, 0, null);
	    //if map1 is selected, draw a rectangle over top of it to show its selected.
	    if (map1.isSelected ())
	    {
		g.fillRect (78, 284, 17, 13);
	    }
	    //if map2 is selected, draw a rectangle over top of it to show its selected.
	    if (map2.isSelected ())
	    {
		g.fillRect (157, 283, 17, 13);
	    }
	    //if map3 is selected, draw a rectangle over top of it to show its selected.
	    if (map3.isSelected ())
	    {
		g.fillRect (233, 283, 17, 13);
	    }
	    //if map4 is selected, draw a rectangle over top of it to show its selected.
	    if (map4.isSelected ())
	    {
		g.fillRect (308, 283, 17, 13);
	    }
	    //if frag1 is selected, draw a rectangle over top of it to show its selected.
	    if (frag1.isSelected ())
	    {
		g.fillRect (76, 458, 17, 13);
	    }
	    //if frag2 is selected, draw a rectangle over top of it to show its selected.
	    if (frag2.isSelected ())
	    {
		g.fillRect (156, 459, 17, 13);
	    }
	    //if frag3 is selected, draw a rectangle over top of it to show its selected.
	    if (frag3.isSelected ())
	    {
		g.fillRect (230, 459, 17, 13);
	    }
	    //if frag4 is selected, draw a rectangle over top of it to show its selected.
	    if (frag4.isSelected ())
	    {
		g.fillRect (310, 458, 17, 13);
	    }
	    //if frag5 is selected, draw a rectangle over top of it to show its selected.
	    if (frag5.isSelected ())
	    {
		g.fillRect (397, 458, 17, 13);
	    }
	    //if neg1 is selected, draw a rectangle over top of it to show its selected.
	    if (neg1.isSelected ())
	    {
		g.fillRect (78, 643, 17, 13);
	    }
	    //if neg05 is selected, draw a rectangle over top of it to show its selected.
	    if (neg05.isSelected ())
	    {
		g.fillRect (146, 643, 17, 13);
	    }
	    //if zero is selected, draw a rectangle over top of it to show its selected.
	    if (zero.isSelected ())
	    {
		g.fillRect (231, 643, 17, 13);
	    }
	    //if pos05 is selected, draw a rectangle over top of it to show its selected.
	    if (pos05.isSelected ())
	    {
		g.fillRect (310, 644, 17, 13);
	    }
	    //if pos1 is selected, draw a rectangle over top of it to show its selected.
	    if (pos1.isSelected ())
	    {
		g.fillRect (391, 645, 17, 13);
	    }
	}
	//if check equals 4
	if (check == 5)
	{
	    //if the instruction checker equals 1, draw first instructions page.
	    if (instruccheck == 1)
	    {
		g.drawImage (instruc2, 0, 0, null);

	    }
	    //if the instruction checker equals 2, draw first instructions page.
	    else if (instruccheck == 2)
	    {
		g.drawImage (instruc, 0, 0, null);
		//resets the instructions checker back to zero.
		instruccheck = 0;
		check = 2;
		repaint ();
	    }
	}
    }





    public void setVisible ()
    {
	//if checker equals zero, set JButtons on loading screen to false.
	if (checker == 0)
	{
	    exit.setVisible (false);
	    credits.setVisible (false);
	    instructions.setVisible (false);
	    settings.setVisible (false);
	    hs.setVisible (false);
	    start.setVisible (false);
	    back.setVisible (true);
	}
	//if checker equals 1, set the JButtons on the loading screen to true, all JCheckBoxes to false, Textfields to false and toggle/ok to false.
	else if (checker == 1)
	{
	    exit.setVisible (true);
	    credits.setVisible (true);
	    instructions.setVisible (true);
	    settings.setVisible (true);
	    hs.setVisible (true);
	    back.setVisible (false);
	    start.setVisible (true);
	    map1.setVisible (false);
	    map2.setVisible (false);
	    map3.setVisible (false);
	    map4.setVisible (false);
	    frag1.setVisible (false);
	    frag2.setVisible (false);
	    frag3.setVisible (false);
	    frag4.setVisible (false);
	    frag5.setVisible (false);
	    neg1.setVisible (false);
	    neg05.setVisible (false);
	    zero.setVisible (false);
	    pos05.setVisible (false);
	    pos1.setVisible (false);
	    toggle.setVisible (false);
	    textField.setVisible (false);
	    textField2.setVisible (false);
	}
	//if checker equals 3, set the JCheckBoxes to true, toggle/ok to false.
	else if (checker == 3)
	{
	    map1.setVisible (true);
	    map2.setVisible (true);
	    map3.setVisible (true);
	    map4.setVisible (true);
	    frag1.setVisible (true);
	    frag2.setVisible (true);
	    frag3.setVisible (true);
	    frag4.setVisible (true);
	    frag5.setVisible (true);
	    neg1.setVisible (true);
	    neg05.setVisible (true);
	    zero.setVisible (true);
	    pos05.setVisible (true);
	    pos1.setVisible (true);
	    toggle.setVisible (false);
	    //resets checker to 0.
	    checker = 0;
	    //call on setVisible method.
	    setVisible ();
	}
    }


    public void actionPerformed (ActionEvent evt)
    {
	//declares command used in getting the action performed.
	Object command = evt.getSource ();
	//checks if "start" is pressed.
	if (command == start)
	{
	    //sets appropriate checkers.
	    check = 1;
	    checker = 0;
	    //calls on the setVisible method.
	    setVisible ();
	    back.setVisible (false);
	    //sets visibilty of textfields to true for user input.
	    textField.setVisible (true);
	    textField2.setVisible (true);
	    textField.setText ("");
	    textField2.setText ("");
	    //sets "ok" button to true.
	    //repaints screen.
	    repaint ();
	}
	//checks if "instructions" is pressed.
	else if (command == instructions)
	{
	    //sets appropriate checkers.
	    check = 2;
	    checker = 0;
	    //calls on the setVisible method.
	    setVisible ();
	    //sets the toggle button visibilty to true,
	    toggle.setVisible (true);
	}
	//checks if "credits" is pressed.
	else if (command == credits)
	{
	    //sets appropriate checkers.
	    check = 3;
	    checker = 0;
	    //calls on the setVisible method.
	    setVisible ();
	}
	//checks if "back" is pressed.
	else if (command == back)
	{
	    //sets appropriate checkers.
	    check = 0;
	    checker = 1;
	    //calls on the setVisible method.
	    setVisible ();
	}
	//checks if "exit" is pressed.
	else if (command == exit)
	{
	    //closes program.
	    System.exit (0);
	}
	//checks if "settings" is pressed.
	else if (command == settings)
	{
	    //sets appropriate checkers.
	    check = 4;
	    checker = 3;
	    //calls on the setVisible method.
	    setVisible ();
	}
	//checks if "toggle" is pressed.
	else if (command == toggle)
	{
	    //sets appropriate checkers.
	    check = 5;
	    checker = 0;
	    if (instruccheck == 1)
	    {
		instruccheck = 2;
	    }
	    else
	    {
		instruccheck = 1;
	    }
	    //sets the toggle visibilty to true.
	    toggle.setVisible (true);
	    //calls on the setVisible method.
	    setVisible ();
	    repaint ();
	}
	//if highscores is pressed, calls on GameOver method.
	else if (command == hs)
	{
	    new GameOver (this);
	    this.setVisible (false);
	}
	//if JCheckBox map1 is pressed, assign map equals 1.
	else if (command == map1)
	{
	    map = 1;
	}
	//if JCheckBox map2 is pressed, assign map equals 2.
	else if (command == map2)
	{
	    map = 2;
	}
	//if JCheckBox map3 is pressed, assign map equals 3.
	else if (command == map3)
	{
	    map = 3;
	}
	//if JCheckBox map4 is pressed, assign map equals 4.
	else if (command == map4)
	{
	    map = 4;
	}
	//if JCheckBox frag1 is pressed, assign frag equals 3.
	else if (command == frag1)
	{
	    frag = 3;
	}
	//if JCheckBox frag2 is pressed, assign frag equals 5.
	else if (command == frag2)
	{
	    frag = 5;
	}
	//if JCheckBox frag3 is pressed, assign frag equals 7.
	else if (command == frag3)
	{
	    frag = 7;
	}
	//if JCheckBox frag4 is pressed, assign frag equals 9.
	else if (command == frag4)
	{
	    frag = 9;
	}
	//if JCheckBox frag5 is pressed, assign frag equals 15.
	else if (command == frag5)
	{
	    frag = 15;
	}
	//if JCheckBox neg1 is pressed, assign speed equals -1.
	else if (command == neg1)
	{
	    speed = -1;
	}
	//if JCheckBox neg05 is pressed, assign speed equals -0.5.
	else if (command == neg05)
	{
	    speed = -0.5;
	}
	//if JCheckBox zero is pressed, assign speed equals 0.
	else if (command == zero)
	{
	    speed = 0;
	}
	//if JCheckBox npos05 is pressed, assign speed equals 0.5.
	else if (command == pos05)
	{
	    speed = 0.5;
	}
	//if JCheckBox pos1 is pressed, assign speed equals 1.
	else if (command == pos1)
	{
	    speed = 1;
	}
    }


    // Method required to be implemented
    public void mousePressed (MouseEvent e)
    {
    }


    // Checks for clicking on the back button, only used for Start menu
    public void mouseReleased (MouseEvent e)
    {
	// Checks upper and lower x and y coordinates of rectangle to see if click has occurred inside "Back" text
	if (e.getButton () == 1 && e.getX () > 15 && e.getX () < 100 && e.getY () > 755 && e.getY () < 775 && this.check == 1)
	{
	    //sets appropriate checkers.
	    check = 0;
	    checker = 1;
	    //calls on the setVisible method.
	    setVisible ();
	}
	// Checks upper and lower x and y coordinates of rectangle to see if click has occurred inside "Start the carnage" text
	else if (e.getButton () == 1 && e.getX () > 155 && e.getX () < 677 && e.getY () > 554 && e.getY () < 584 && this.check == 1)
	{
	    //gets user input from textfield and textfield2 and assigns it to player1 and player2 respectfully.
	    player1 = textField.getText ();
	    player2 = textField2.getText ();
	    //if player 1 enters no text, give them a default name.
	    if (player1.length () == 0)
	    {
		player1 = "Anon1";
	    }
	    //if player1's name is greater than 10 spots, cut off the name to 10 places.
	    else if (player1.length () > 10)
	    {
		player1 = player1.substring (0, 10);
	    }
	    //if player2's name is blank and player1's name is blankm give player2 default name.
	    if (player2.length () == 0 && player1.equals ("Anon1"))
	    {
		player2 = "Anon2";
	    }
	    //if player 1 enters no text, give them a default name.
	    else if (player2.length () == 0)
	    {
		player2 = "Anon1";
	    }
	    //if player2's name is greater than 10 spots, cut off the name to 10 places.
	    else if (player2.length () > 10)
	    {
		player2 = player2.substring (0, 10);
	    }
	    //checks if the two user names are equal.
	    if (player1.equals (player2))
	    {
		player2 += "2";
	    }
	    //writes the map, frag, and speed to file and check for possible errors.
	    try
	    {
		fw = new FileWriter ("settings.txt");
		fw.write (map + "\r\n");
		fw.write (frag + "\r\n");
		fw.write (speed + "\r\n");
		fw.close ();
	    }
	    catch (IOException exc)
	    {
		System.out.println ("Eror in reading from file. Exiting...");
		try
		{
		    Thread.sleep (2000);
		}
		catch (InterruptedException e2)
		{
		}
		System.exit (0);
	    }
	    // Sets stopThread to true so that thread stops running on next iteration
	    stopThread = true;
	    //hides the JPanel.
	    this.dispose ();
	    //calls on EntryPoint and passes the two players names.
	    new EntryPoint (player1, player2);
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
}


