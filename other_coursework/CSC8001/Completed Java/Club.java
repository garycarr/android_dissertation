import java.util.*;

/**
 * The Club class is used to create and modify club objects. Players can then be
 * added to the club
 * 
 * @author Gary Carr
 **/
public class Club implements Comparable<Club>
{
    private String clubName;
    private String stadium;
    private ArrayList<Player> squad;
    private Scanner sc = new Scanner(System.in);
    private ArrayList<Club> homeMatches;
    private int clubGoalsTotalScored, clubGoalsTotalConceded, goalDifference;
    private int totalMatchesPlayed, totalMatchesWon, totalMatchesDrawn,
	    totalMatchesLost, pointsTally;

    /**
     * The constructor for Club class takes the name and stadium name and then
     * checks that the club name is entered in a valid format. An ArrayList to
     * hold players is also initialized
     * 
     * @param clubName
     *            This is the user inputted name of the club
     * @param stadium
     *            This is the user inputted name of the stadium, required as all
     *            the clubs home matches will be set their by default
     */
    public Club(String clubName, String stadium)
    {
	this.clubName = clubName;
	this.stadium = stadium;
	squad = new ArrayList<Player>();
	homeMatches = new ArrayList<Club>();
	pointsTally = 0;
	clubGoalsTotalScored = 0;
	clubGoalsTotalConceded = 0;
	goalDifference = 0;
	setClubName(clubName);
    }

    /**
     * Method checks that the club name is between 2 and 15 characters, and if
     * correct sets the team name
     * 
     * @param clubName
     *            This is the user inputted name for the club
     */

    public void setClubName(String clubName) {
	this.clubName = clubName;
	while (clubName.length() < 2 || clubName.length() > 15)
	{
	    System.out.println("Invalid club name entered. " + getClubName()
		    + ". Must be between 2 and 15 characters");
	    System.out
		    .println("If the team has a long name it must be shortened");
	    System.out
		    .println("For example Dagenham and Redbridge shorted to Dagenham, or Manchester United to Man United");
	    System.out.println("Enter new club name");
	    clubName = sc.nextLine();
	    this.clubName = clubName;
	}
    }

    /**
     * Method returns the clubs name
     * 
     * @return Returns the name of the club
     */
    public String getClubName() {
	return clubName;
    }

    /**
     * Sets a new stadium name
     * 
     * @param stadium
     *            This is the user inputted new stadium name
     */
    public void setStadiumName(String stadium) {
	this.stadium = stadium;
    }

    /**
     * Mehod returns the clubs stadium name
     * 
     * @return Returns the name of the stadium
     */
    public String getStadiumName() {
	return stadium;
    }

    /**
     * Method adds points to the clubs total goals scored tally. Can be inputted
     * by user or is accessed from the Match class - confirmResults method.
     * 
     * @param gs
     *            This adds to the clubs current total goals tally
     */
    public void addGoalsScored(int gs) {
	if (gs >= 0)
	{
	    clubGoalsTotalScored += gs;
	}
	else
	{
	    System.out.println("Goals scored entered is " + gs
		    + ". Negative numbers are not allowed");
	}
    }

    /**
     * Method adds points to the clubs total goals conceded tally. Can be
     * inputted by user or is accessed from the Match class - confirmResults
     * method.
     * 
     * @param gc
     *            This adds to the clubs current total goals conceded tally
     */
    public void addGoalsConceded(int gc) {
	if (gc >= 0)
	{
	    clubGoalsTotalConceded += gc;
	}
	else
	{
	    System.out.println("Goals conceded entered is " + gc
		    + ". Negative numbers are not allowed");
	}
    }

    /**
     * Method returns the clubs goal difference
     * 
     * @return Returns clubs current goal difference
     */
    public int getGoalDifference() {
	goalDifference = clubGoalsTotalScored - clubGoalsTotalConceded;
	return goalDifference;
    }

    /**
     * Method adds points to the clubs total points tally. Can be inputted by
     * user or is updated by the confirmResults method in Match
     * 
     * @param p
     *            This adds to the clubs current total points tally
     */
    public void addPointsTally(int p) {
	if (p >= 0)
	{
	    pointsTally += p;
	}
	else
	{
	    System.out.println("Points tally entered is " + p
		    + ". This is not allowed and must be a positive number");
	}
    }

    /**
     * Method returns the clubs points tally
     * 
     * @return Returns the current points tally of the club
     */
    public int getPointsTally() {
	return pointsTally;
    }

    /**
     * Method takes a player and adds them to an ArrayList of the clubs current
     * squad
     * 
     * @param squadMember
     *            This takes a player object to add to an ArrayList of players
     */
    public void addClubPlayers(Player squadMember) {
	squad.add(squadMember);
    }

    /**
     * Method takes a player object and checks if that player is in the clubs
     * squad
     * 
     * @param player
     *            The user or method selects a Player object to check if they
     *            are in the squad
     * @return Returns a boolean after determining if player is in squad
     */
    public boolean isPlayerInTeam(Player player) {
	return squad.contains(player);
    }

    /**
     * Method returns the average height of the club by extracting the height of
     * each Player contained in squad, adding it together, then dividing by the
     * size of the squad
     * 
     * @return Returns the average height of the current squad
     */
    public double getAverageHeight() {
	double totalHeight = 0;
	for (Player player : squad)
	{
	    totalHeight += player.getHeight();
	}
	totalHeight = totalHeight / squad.size();
	totalHeight = Math.round(totalHeight * 100.0) / 100.0;
	return totalHeight;
    }

    /**
     * Method takes parameters from the Match class - confirmResults method.
     * When a final result is confirmed it updates matches played, won, lost and
     * drawn statistics. It is used for the visual display of the league table
     * 
     * @param w
     *            This increases the number of games won depending on the
     *            results of the match
     * @param d
     *            This increases the number of games drawn depending on the
     *            results of the match
     * @param l
     *            This increases the number of games lost depending on the
     *            results of the match
     */
    public void addMatchFigures(int w, int d, int l) {
	totalMatchesPlayed++;
	totalMatchesWon += w;
	totalMatchesDrawn += d;
	totalMatchesLost += l;
    }

    /**
     * Method returns the clubs current squad size
     * 
     * @return Returns how many players are in the current squad
     */
    public int squadSize() {
	return squad.size();
    }

    /**
     * Method returns an ArratList of the clubs squad
     * 
     * @return Returns an ArrayList of the existing squad
     */
    public ArrayList<Player> getSquad() {
	return squad;
    }

    /**
     * Method iterates through each player and checks if their goal tally
     * exceeds the highest amount found so far, and then returns a list of the
     * top scorers. Called by the League class to test each club as it builds a
     * list of all players in the league
     * 
     * @return Returns an arraylist containing the top scorer from the team
     */

    /**
     * Method adds up the age of all players in the squad and then divides by
     * the number of players to give an average
     * 
     * @return Returns the average age of the squad
     */
    public double getAverageAge() {
	double averageAge = 0;
	for (Player player : squad)
	{
	    averageAge += player.getAge();
	}
	averageAge = averageAge / squad.size();
	averageAge = Math.round(averageAge * 10.0) / 10.0;// Rounds the answer
	// to 1 decimal place
	return averageAge;
    }

    /**
     * Whenever a home match for this club is set the opposition club are added
     * to the homeMatches ArrayList. This is used by the Match class to ensure
     * that duplicate fixtures cannot occur during a season
     * 
     * @param c
     *            This is the opposition club
     */
    public void addHomeMatch(Club c) {
	homeMatches.add(c);
    }

    /**
     * Method checks if this club has already played another club. It is
     * accessed from the match class to check fixtures are not duplicated
     * 
     * @return Returns an ArrayList of the clubs home games so far
     */
    public ArrayList<Club> getHomeMatches() {
	return homeMatches;
    }

    /**
     * Method is accessed through the console IO when choosing a player to
     * modify within an squad
     * 
     * @param selectPlayer
     *            This is the player from the squad that has been chosen from
     *            the user through the IO
     * @param c
     *            This is the club the player belongs to
     * @param l
     *            This is the league the club belongs to
     * @return Returns the player selected
     */
    public Player getPlayerItem(int selectPlayer, Club c, League l) {
	Player player = squad.get(selectPlayer);
	return player;
    }

    /**
     * Method is accessed through the console IO when choosing a player to add
     * to the team for a match
     * 
     * @param selectPlayer
     *            This is the player from the squad that has been chosen from
     *            the user through the IO
     * @param m
     *            This is the match that the user has selected
     * @return Returns the player selected
     */
    public Player getSquadPlayer(int selectPlayer, Match m) {
	Player player = squad.get(selectPlayer);
	return player;
    }

    /**
     * Method prints a list of all the current players in the ArrayList squad
     * 
     * @return Returns a print out of all players in the squad
     */
    public String showPlayers() {

	int index = 1;
	String showPlayers = "\n";
	for (Player player : squad)
	{
	    showPlayers += index + " - " + player.getName() + "\n";
	    index++;
	}
	return showPlayers;
    }

    /**
     * Method returns squads current size
     * 
     * @return Returns the current size of the squad
     */
    public int getSquadSize() {
	return squad.size();
    }

    public String toString() {
	String a;
	if (clubName.length() <= 7)
	{
	    a = "\t";
	}
	else
	{
	    a = "";

	}
	return this.clubName + "\t" + "\t" + a + totalMatchesPlayed + "\t"
		+ totalMatchesWon + "\t" + totalMatchesDrawn + "\t"
		+ totalMatchesLost + "\t" + clubGoalsTotalScored + "\t"
		+ clubGoalsTotalConceded + "\t" + goalDifference + "\t"
		+ pointsTally;
    }

    /*
     * compareTo compares each club against each other and then orders them
     * based first on points tally, then goal difference, then goal scored, then
     * finally by alphabet.
     */
    public int compareTo(Club club) {
	int result = 0;

	if (this.pointsTally > club.pointsTally)
	{
	    result += -3;
	}
	else if (this.pointsTally < club.pointsTally)
	{
	    result += 3;

	}
	else if (this.goalDifference > club.goalDifference)
	{
	    result += -2;

	}
	else if (this.goalDifference < club.goalDifference)
	{
	    result += 2;

	}
	else if (this.clubGoalsTotalScored > club.clubGoalsTotalScored)
	{
	    result += -1;

	}
	else if (this.clubGoalsTotalScored < club.clubGoalsTotalScored)
	{
	    result += 1;

	}
	else
	{
	    result += this.getClubName().compareTo(club.getClubName());

	}
	return result;
    }

    /**
     * Method creates a short summary of the clubs statistics
     * 
     * @return Returns the statistics of the team as a block of text
     */
    public String clubDetails() {
	String results = "" + "\n";
	results += "Club Name - " + clubName + "\n";
	results += "Stadium - " + stadium + "\n";
	results += "Matches Played- " + totalMatchesPlayed + "\n";
	results += "Games won - " + totalMatchesWon + "\n";
	results += "Games drawn - " + totalMatchesDrawn + "\n";
	results += "Games lost - " + totalMatchesLost + "\n";
	results += "Goals Scored - " + clubGoalsTotalScored + "\n";
	results += "Goals conceded - " + clubGoalsTotalConceded + "\n";
	results += "Goals difference - " + getGoalDifference() + "\n";
	results += "Points Tally - " + pointsTally + "\n";
	results += "Average age of squad - " + getAverageAge() + "\n";
	results += "Average height of squad - " + getAverageHeight();
	return results;
    }

    /**
     * This is a testing method to test the above methods work. Each test gives
     * in text what the answer should be, a demonstration of the answer, and
     * then a coding test to ensure that Java confirms the answers are correct
     */
    public static void testClub() {
	Club C1 = new Club("Dummy Team", "Dummy Stadium");
	Club C2 = new Club("Dummy Team2", "Dummy Stadium2");

	Player P1 = new Player("Jonny");
	Player P2 = new Player("Jamie");
	Player P3 = new Player("Simon");
	Player Q1 = new Player("Wendall");
	Player Q2 = new Player("Tony");
	Player Q3 = new Player("Armen");
	C1.addClubPlayers(P1);
	C1.addClubPlayers(P2);
	C1.addClubPlayers(P3);
	C2.addClubPlayers(Q1);
	C2.addClubPlayers(Q2);
	C2.addClubPlayers(Q3);

	C1.setClubName("Chelsea");
	C2.setClubName("Newcastle");
	System.out.println("Club 1 should be Chelsea");
	System.out.println("Club 1s name is - " + C1.getClubName());
	System.out.println("Club 2 should be Newcastle");
	System.out.println("Club 1s name is - " + C2.getClubName());

	if (C1.getClubName().equals("Chelsea")
		&& C2.getClubName().equals("Newcastle"))
	{
	    System.out.println("Club name is correct");
	}
	else
	{
	    System.out.println("Name is not correct");
	}
	System.out.println("");
	C1.setStadiumName("Stamford Bridge");
	C2.setStadiumName("St James");
	System.out.println("Club 1 stadium should be Stamford Bridge");
	System.out.println("Club 1s name is - " + C1.getStadiumName());
	System.out.println("Club 2 should be St James");
	System.out.println("Club 1s name is - " + C2.getStadiumName());

	if (C1.getStadiumName().equals("Stamford Bridge")
		&& C2.getStadiumName().equals("St James"))
	{
	    System.out.println("Stadium name is correct");
	}
	else
	{
	    System.out.println("Stadium name is not correct");
	}

	C1.addGoalsScored(10);
	C1.addGoalsConceded(15);
	C2.addGoalsScored(12);
	C2.addGoalsConceded(11);
	System.out.println("");
	System.out
		.println("Club 1 should have scored 10 goals, conceded 15, with -5 goal difference");
	System.out.println("Club 1s have scored " + C1.clubGoalsTotalScored
		+ " and conceded " + C1.clubGoalsTotalConceded + " with "
		+ C1.getGoalDifference() + " goal difference");
	System.out
		.println("Club 2 should have scored 12 goals and conceded 11 goals with 1 goal difference");
	System.out.println("Club 1s have scored " + C2.clubGoalsTotalScored
		+ " and conceded " + C2.clubGoalsTotalConceded + " with "
		+ C2.getGoalDifference() + " goal difference");

	if (C1.clubGoalsTotalScored == 10 && (C1.clubGoalsTotalConceded == 15)
		&& (C1.getGoalDifference() == -5)
		&& (C2.clubGoalsTotalScored == 12)
		&& (C2.clubGoalsTotalConceded == 11)
		&& (C2.getGoalDifference() == 1))
	{
	    System.out.println("Goal details are correct");
	}
	else
	{
	    System.out.println("Goal details are not correct");
	}
	System.out.println("");
	C1.addPointsTally(15);
	C2.addPointsTally(11);
	System.out.println("Club 1 should have 15 points");
	System.out.println("Club 1 should have " + C1.getPointsTally()
		+ " points");
	System.out.println("Club 2 should have 11 points");
	System.out.println("Club 2 should have " + C2.getPointsTally()
		+ " points");
	if ((C1.getPointsTally() == 15) && (C2.getPointsTally() == 11))
	{
	    System.out.println("Point details are correct");
	}
	else
	{
	    System.out.println("Point details are not correct");
	}

	P1.setDOB(P1, 15, 2, 1986);
	P2.setDOB(P2, 3, 1, 84);
	P3.setDOB(P3, 15, 2, 1980);
	Q1.setDOB(Q1, 3, 1, 87);
	Q2.setDOB(Q2, 15, 2, 1985);
	Q3.setDOB(Q3, 3, 1, 90);
	P1.setHeight(1.8);
	P2.setHeight(1.4);
	P3.setHeight(1.7);
	Q1.setHeight(1.6);
	Q2.setHeight(1.7);
	Q3.setHeight(1.8);

	System.out.println("");
	System.out.println("Club 1 should contain Jonny");
	System.out.println("Does club 1 contain Jonny? - "
		+ C1.isPlayerInTeam(P1));
	System.out.println("Club 1 should not contain Wendall");
	System.out.println("Does club 1 contain Wendall? - "
		+ C1.isPlayerInTeam(Q1));

	if ((C1.isPlayerInTeam(P1) == true) && (C1.isPlayerInTeam(Q1) == false))
	{
	    System.out.println("Checking squad is correct");
	}
	else
	{
	    System.out.println("Checking squad is not correct");
	}
	System.out.println("");
	System.out
		.println("Club 1 average age should be 28.7 (as of the 2nd December!)");
	System.out.println("Club 1 average age is " + C1.getAverageAge());
	System.out
		.println("Club 2 average age should be 24.7 (as of the 2nd December!)");
	System.out.println("Club 2 average age is " + C2.getAverageAge());

	if (C1.getAverageAge() > 28.6 && C1.getAverageAge() < 28.8
		&& C2.getAverageAge() > 24.6 && C2.getAverageAge() < 24.9)
	{
	    System.out.println("Age is correct");
	}
	else
	{
	    System.out.println("Age is not correct, but may be out of date!");
	}
	System.out.println("");
	System.out.println("Club 1 average height should be 1.63");
	System.out.println("Club 1 average height is " + C1.getAverageHeight());
	System.out.println("Club 2 average height should be 1.7");
	System.out.println("Club 2 average height is " + C2.getAverageHeight());

	if ((C1.getAverageHeight() == 1.63) && (C2.getAverageHeight() == 1.7))
	{
	    System.out.println("Height is correct");
	}
	else
	{
	    System.out.println("Height is not correct");
	}

    }

}
