import java.applet.*;
import java.awt.*;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.event.MouseEvent;



// Cookie Calculator by Neeilan Selvalingam and Aaruran Elamurugaiyan
// This is a simple and elegant game: you are given a set of 9 random numbers, and you are asked to select 
// all the numbers from this set which happen to be a multiple of another random number. After selecting all
// the correct multiples, press done to check your answer. If you select the numbers correctly, YOU GET A COOKIE!
// Else, YOU LOSE A COOKIE. Therefore, it is in your best interest to channel your mathematical talent and love for 
// cookies on this arduous test of mental, physical and intestinal fortitude. Watch out though; every now and then, 
// no numbers in the set of 9 may be a multiple of your given number! Good luck!

public class CookieCalculator extends Applet implements MouseListener, MouseMotionListener //CookieCalculator
{
    //***GLOBAL VARIABLES***
    int[] numbers = new int [9]; //array
    int clickedcircle = -1;
    int diameter = 100;
    int score = 0;
    int typeOfNum = (int) (Math.random () * 10 + 2); //Number whose multiple the user is asked to select
    int correct = 0; //0 for incomplete, 1 for complete and wrong, 2 for correct
    boolean[] HasItBeenClicked = new boolean [9]; //array to store the clicks on each circle
    //Colors and font specifications
    Color unclicked = new Color (185, 122, 87);
    Color clicked = new Color (136, 0, 21);
    Color text = new Color (255, 255, 255);
    Color background = Color.black;
    Font allText = new Font ("Arial", Font.BOLD, 24);
    Image img; //Background image. Source: http://www.aflockinthecity.com/wp-content/uploads/2012/05/italian-cookies-10-1024x768.jpg
    AudioClip audio; //Background audio. Source: Chorus from "Do the Cookie Dance" by Chip Chocolate)
    Button Submit = new Button ("Done?"); //Submit button
    int[] xCoor = new int[]
    {
	50, 200, 350, 50, 200, 350, 50, 200, 350
    }


    ; //Array to store X coordinates of the circles
    int[] yCoor = new int[]
    {
	100, 100, 100, 250, 250, 250, 400, 400, 400
    }


    ; //Array to store Y coordinates of the circles

    //***METHODS***
    
    public void setNumbers ()
    {
	for (int i = 0 ; i < 9 ; i++)
	    numbers [i] = (int) (Math.random () * 89) + 10;
	// This method places 9 random 3-digit numbers
	// in the numbers[] array
    }


    public void drawcircles (Graphics g)
    {
	g.setFont (new Font ("Arial", Font.BOLD, 16));
	for (int i = 0 ; i < 9 ; i++)
	{
	    g.setColor (Color.white);
	    g.fillOval (xCoor [i] - 2, yCoor [i] - 2, diameter + 4, diameter + 4);
	}
	for (int i = 0 ; i < 9 ; i++)
	{
	    if (HasItBeenClicked [i] == false)
		g.setColor (unclicked);
	    else
		g.setColor (clicked);
	    g.fillOval (xCoor [i], yCoor [i], diameter, diameter);
	    g.setColor (text);
	    g.drawString ("" + numbers [i], xCoor [i] + 37, yCoor [i] + 55);
	}
	// This method draws 9 circles and prints each of the numbers
	// from the numbers[] array inside each circle. The color of the circles depends on whether they have been clicked.
    }

    public int closestcircle (int x, int y)
    {
	for (int i = 0 ; i < 9 ; i++)
	{
	    if ((Math.pow (xCoor [i] + diameter / 2 - x, 2) + Math.pow (yCoor [i] + diameter / 2 - y, 2)) < 2500)
		return i;
	}
	return -1;
	// This method uses the distance formula to determine if a mouseclick
	// is inside one of the circles. It then returns the number of the circle.
    }

    public void init () //Initializing method
    {
	setSize (500, 650);
	setNumbers ();
	addMouseListener (this);
	setLayout (new BorderLayout ());
	Panel south = new Panel ();
	south.setLayout (new GridLayout (1, 2));
	south.add (Submit);
	add (south, BorderLayout.SOUTH);
	//Audio (song- cookie dance)
	audio = getAudioClip (getDocumentBase (), "cookiedance.au");
	audio.play ();
	//background image
	img = getImage (getCodeBase (), "cookiesbg.png");
    }


    public void mousePressed (MouseEvent e)
    { // called after a button is pressed down
	// "Consume" the event so it won't be processed in the
	// default manner by the source which generated it.
	//e.consume ();
	clickedcircle = closestcircle (e.getX (), e.getY ());
	if (clickedcircle != -1)
	{
	    if (HasItBeenClicked [clickedcircle] == false)
		HasItBeenClicked [clickedcircle] = true;
	    else
		HasItBeenClicked [clickedcircle] = false;
	}

	e.consume ();
    }


    public boolean action (Event e, Object o)
    {
	if (e.target == Submit)
	{
	    boolean isItRight = true;
	    for (int i = 0 ; i < 9 ; i++)
	    {
		if (HasItBeenClicked [i] == false && numbers [i] % typeOfNum == 0)
		{
		    isItRight = false;
		    break;
		}

		if (HasItBeenClicked [i] == true && numbers [i] % typeOfNum != 0)
		{
		    isItRight = false;
		    break;
		}
	    }

	    if (isItRight == true)
		correct = 2;
	    else
		correct = 1;
	    repaint ();
	}
	return true;
    }


    public void mouseReleased (MouseEvent e)
    { // called after a button is released
	repaint ();
    }


    public void paint (Graphics g)
    {
	g.setFont (allText);
	//background
	setBackground (background);
	g.drawImage (img, 0, 0, this);
	
	//drawcircles (g);
	g.setColor (text);


	if (correct != 0)
	{
	    typeOfNum = (int) (Math.random () * 10 + 2);
	    if (correct == 2)
	    {
		g.drawString ("You cleared that round!", 150, 580);
		score++;
	    }
	    else if (correct == 1)
	    {
		g.drawString ("You didn't clear that round :(", 150, 580);
		score--;
	    }

	    setNumbers ();
	    for (int i = 0 ; i < 9 ; i++)
		HasItBeenClicked [i] = false;

	    //typeOfNum = (int) (Math.random () * 10 + 2);
	    correct = 0;
	}
	g.drawString ("Thou shalt pick out all multiples of " + typeOfNum, xCoor [0] - 15, (yCoor [0]) / 2);
	g.drawString ("Cookies collected: " + score, 150, 610);
	drawcircles (g);

    }


    //UNUSED METHODS---------------------------------------------------------------
    public void mouseEntered (MouseEvent e)
    {
	// called when the pointer enters the applet's rectangular area
    }


    public void mouseExited (MouseEvent e)
    {
	// called when the pointer leaves the applet's rectangular area
    }


    public void mouseClicked (MouseEvent e)
    {
    }


    public void mouseDragged (MouseEvent e)
    { // called during motion with buttons down
    }


    public void mouseMoved (MouseEvent e)
    { // called during motion when no buttons are down
	// int mx = e.getX ();
	// int my = e.getY ();
	// showStatus ("Mouse at (" + mx + "," + my + ")");
	// repaint ();
	// e.consume ();
    }

} // CookieCalculator class


