import java.util.*;

/**
 * The Player class is used to create and modify players. Players can then be
 * added to the club
 * 
 * @author Gary Carr
 **/
public class Player
{
    private Scanner sc = new Scanner(System.in);
    private Calendar bd = Calendar.getInstance();
    private Calendar now = Calendar.getInstance();
    private String fullName;
    private String dob;
    private double height;
    private int goalTally;
    private int age;

    /**
     * The constructor for Player class takes a players full name and then
     * creates the player. The method setName then checks if the name entered is
     * valid.
     * 
     * @param name
     *            This param is user inputted, and takes the players full name
     */
    public Player(String fullName)
    {
	this.fullName = fullName;
	setName(fullName);
    }

    /**
     * This method checks the players full name. A while loop is used to check
     * that the players name is over 3 characters, and if not the user asked to
     * confirm if this is correct
     * 
     * @param fullName
     *            This is the players name, the field is created in the Player
     *            constructor and passed to this method.
     */
    public void setName(String fullName) {

	int input = 0;
	this.fullName = fullName;
	while (fullName.length() < 3 && input != 1)
	{
	    System.out.println("Short name entered - " + fullName);
	    System.out.println("1. Confirm");
	    System.out.println("2. Edit Name");
	    input = sc.nextInt();

	    if (input == 2)
	    {
		System.out.println("Enter new name");
		String force = sc.nextLine();
		fullName = sc.nextLine();
		setName(fullName);
	    }
	    this.fullName = fullName;
	}
    }

    /**
     * Method gets the players name
     * 
     * @return Returns the players full name
     */
    public String getName() {
	return fullName;
    }

    /**
     * This method allows a user to input a players height. While loop checks
     * the height must be between 50cm and 250cm.
     * 
     * @param h
     *            This takes the players height and updates the player object
     *            accordingly
     */
    public void setHeight(double h) {
	this.height = h;
	height = Math.round(height * 10.00) / 10.00;
	while (h < 0.5 || h > 2.5)// If height is below 50cm or above 250cm, the
	// while loop will request a new height
	{
	    System.out.println("Incorrect height entered for " + getName());
	    System.out
		    .println("Please re-enter height (in metre then centermetres (e.g. 1.8)");
	    System.out.println("Input Height");
	    h = sc.nextDouble();
	    height = Math.round(height * 10.00) / 10.00;
	    this.height = h;
	}
    }

    /**
     * Method gets the players height
     * 
     * @return Returns the players height
     */
    public double getHeight() {
	return height;
    }

    /**
     * This method allows the user to enter how many goals the player has
     * scored, and it updates their current goal tally.
     * 
     * @param g
     *            This takes the number of goals the player has scored and adds
     *            to goalTally
     */
    public void scoredXGoals(int g) {
	if (g >= 0)
	{
	    goalTally += g;
	}
	else
	{
	    System.out.println("Goals scored entered is " + g
		    + ". Negative numbers are not allowed");
	}
    }

    /**
     * Method gets the players goal tally
     * 
     * @return Returns the total number of goals the player has scored
     */
    public int getGoalTally() {
	return goalTally;
    }

    /**
     * This method sets the date of birth of the player, and sets against the bd
     * calendar. If a player is less than 16 or more than 45 the user is put
     * into the while loop and prompted to double check the date is correct. The
     * date of birth is then converted into a String DOB which uses format to
     * display correctly
     * 
     * @param p
     *            This is the player object which is being modified
     * @param date
     *            This is the day the player was born
     * @param month
     *            This is the month the player was born
     * @param year
     *            This is the year the player was born
     */
    public void setDOB(Player p, int date, int month, int year) {
	if (year < 100)
	{
	    year = year + 1900;
	}

	bd.set(Calendar.MONTH, month - 1);
	bd.set(Calendar.DAY_OF_MONTH, date);
	bd.set(Calendar.YEAR, year);

	dob = String.format("%1$te/%1$tm/%1$tY", bd);
	int option = 0;
	while (p.getAge() < 16 || (p.getAge() > 45))
	{
	    System.out.println("Unexpected age entered, " + getName() + " is "
		    + p.getAge() + " years old");
	    System.out
		    .println("Type 1 to edit, or 2 to confirm this is correct");
	    option = sc.nextInt();
	    if (option == 1)
	    {
		System.out.println("Input day of birth");
		int x = sc.nextInt();
		System.out.println("Input month of birth");
		int y = sc.nextInt();
		System.out.println("Input Year of birth");
		int z = sc.nextInt();
		if (z < 100)
		{
		    z = z + 1900;
		}

		date = x;
		month = y;
		year = z;
		bd.set(Calendar.MONTH, x);
		bd.set(Calendar.DAY_OF_MONTH, y);
		bd.set(Calendar.YEAR, z);
		dob = String.format("%1$te/%1$tm/%1$tY", bd);
	    }
	    else if (option == 2)
	    {
		break;
	    }
	}
    }

    /**
     * This method compares the current date (now) to the players date of birth
     * to get the current age of the player
     * 
     * @return The players current age
     */
    public int getAge() {
	age = (now.get(Calendar.YEAR)) - (bd.get(Calendar.YEAR));
	return age;
    }

    /**
     * Method gets the players date of birth
     * 
     * @return This returns the date of birth in a string format
     */
    public String getDOB() {
	return dob;
    }

    /*
     * String is overridden to give statistical analysis of the player
     */
    @Override
    public String toString() {
	return ("Name - " + fullName + "\n" + "Height - " + height + "\n"
		+ "Date of birth - " + dob + "\n" + "Goals Scored- " + goalTally);
    }

    /**
     * This is a testing method to test the above methods work. Each test gives
     * in text what the answer should be, a demonstration of the answer, and
     * then a coding test to ensure that Java confirms the answers are correct
     */
    public static void testPlayer() {
	Player P1 = new Player("DummyP1");
	Player P2 = new Player("DummyP2");
	P1.setName("James Burns");
	P2.setName("Alan Smith");
	P1.setDOB(P1, 15, 2, 1986);
	P2.setDOB(P2, 3, 1, 84);
	P1.setHeight(1.8);
	P2.setHeight(1.4);
	P1.scoredXGoals(15);
	P2.scoredXGoals(25);

	System.out.println("Player 1 should be called James Burns");
	System.out.println("Player 1s name is - " + P1.getName());
	System.out.println("Player 2 should be called Alan Smith");
	System.out.println("Player 2s name is - " + P2.getName());

	if (P1.getName().equals("James Burns")
		&& P2.getName().equals("Alan Smith"))
	{
	    System.out.println("Name is correct");
	}
	else
	{
	    System.out.println("Name is not correct");
	}
	System.out.println("");
	System.out.println("James should have the birthday 15/02/1986");
	System.out.println("James birthday is  - " + P1.getDOB());
	System.out.println("Alan should have the birthday 3/01/1984");
	System.out.println("Alans birthday is  - " + P2.getDOB());

	if (P1.getDOB().equals("15/02/1986") && P2.getDOB().equals("3/01/1984"))
	{
	    System.out.println("Birthday is correct");
	}
	else
	{
	    System.out.println("Birthday is not correct");
	}
	System.out.println("");
	System.out.println("James should be 1.8 metres");
	System.out.println("James height is  - " + P1.getHeight());
	System.out.println("Alan should be 1.4 metres");
	System.out.println("Alans height is  - " + P2.getHeight());

	if (P1.getHeight() == (1.8) && P2.getHeight() == (1.4))
	{
	    System.out.println("Height is correct");
	}
	else
	{
	    System.out.println("Height is not correct");
	}
	System.out.println("");
	System.out.println("James should have 15 goals");
	System.out.println("James goal tally is  - " + P1.getGoalTally());
	System.out.println("Alan should have 25 goals");
	System.out.println("Alans goal tally is  - " + P2.getGoalTally());

	if (P1.getGoalTally() == (15) && P2.getGoalTally() == (25))
	{
	    System.out.println("Goal tally is correct");
	}
	else
	{
	    System.out.println("Goal tally is not correct");
	}
    }

}
