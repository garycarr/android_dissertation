import java.util.*;

/**
 * The Match class is used to create and modify matches. Clubs objects build the
 * match and player objects are used to create match results. The match class
 * updates these two classes based on the results of a game.
 * 
 * @author Gary Carr
 */
public class Match
{
    private Scanner sc = new Scanner(System.in);
    private Club homeTeam;
    private Club awayTeam;
    private Calendar timeAndDate = Calendar.getInstance();
    private Calendar now = Calendar.getInstance();
    private ArrayList<Player> inHTeam;
    private ArrayList<Player> inATeam;
    HashMap<Player, Integer> scoredInMatch = new HashMap<Player, Integer>();
    private String finalResult;
    private String fixtureDate;
    private String location;
    private String finalScore;
    private boolean matchPlayed;
    private int totalGoalsHome;
    private int totalGoalsAway;

    /**
     * The constructor for Match class creates an empty match, and initializes
     * the scores at zero and sets match played to false
     * 
     * @param l
     *            This requires a league object, the match will then belong to
     *            that league
     */
    public Match(League l)
    {
	totalGoalsHome = 0;
	totalGoalsAway = 0;
	matchPlayed = false;
    }

    /**
     * Methods creates a fixture date for the match and sets it to Calender
     * timeAndDate. The date is then passed to the checkFixtureDate method and
     * checked the date is in the future. If not the user is asked to input once
     * more until the date is set. Format is then used to display the date
     * correctly as a string
     * 
     * @param date
     *            This takes the user inputted date of the match
     * @param month
     *            This takes the user inputted month of the match
     * @param year
     *            This takes the user inputted year of the match
     * @param minute
     *            This takes the user inputted minute the match starts
     * @param hour
     *            This takes the user inputted hour the match starts
     */
    public void setFixtureDate(int date, int month, int year, int hour,
	    int minute, League l, Match m) {
	timeAndDate.set(Calendar.MONTH, month - 1);// Matches user inputted
						   // month to the correct month
						   // for Calender format (i.e.
						   // 1st January 2013 is in
						   // Calender is 1/0/2013
	timeAndDate.set(Calendar.DAY_OF_MONTH, date);
	timeAndDate.set(Calendar.YEAR, year);
	timeAndDate.set(Calendar.MINUTE, minute);
	timeAndDate.set(Calendar.HOUR, hour);
	while (checkFixtureDate(timeAndDate) == true)
	{
	    System.out
		    .println("Invalid date entered.  Date is in the past. Please enter new date");
	    timeAndDate.clear();
	    System.out.println("Input day of match");
	    int date1 = sc.nextInt();
	    System.out.println("Input month of match");
	    int month1 = sc.nextInt();
	    System.out.println("Input Year of match");
	    int year1 = sc.nextInt();
	    System.out.println("Input hour of match");
	    int hour1 = sc.nextInt();
	    System.out.println("Input minute of match");
	    int minute1 = sc.nextInt();
	    if (year < 100)
	    {
		year = year + 2000;
	    }
	    timeAndDate.set(Calendar.MONTH, month1 - 1);
	    timeAndDate.set(Calendar.DAY_OF_MONTH, date1);
	    timeAndDate.set(Calendar.YEAR, year1);
	    timeAndDate.set(Calendar.MINUTE, minute1);
	    timeAndDate.set(Calendar.HOUR, hour1);
	}
	fixtureDate = String.format("%1$te/%1$tm/%1$tY - %1$tH:%1$tM",
		timeAndDate);
    }

    /**
     * Method returns a string of the fixture date
     * 
     * @return Returns the fixture date as a String
     */
    public String getFixtureDate() {
	return fixtureDate;
    }

    /**
     * This method tests whether the date is in the past or the future. This is
     * used to ensure that a match date cannot be set for a previous date
     * 
     * @param fixture
     *            This parameter takes in a date from setFixtureMethod
     * @return Returns boolean true if the date is in the past, and false if the
     *         date is in the future
     */
    public boolean checkFixtureDate(Calendar fixture) {
	boolean check = false;
	if (timeAndDate.compareTo(now) <= 0)
	{
	    check = true;
	    return check;
	}
	else
	    return check;
    }

    /**
     * Method sets the stadium location for the match
     * 
     * @param location
     *            This takes an inputted new stadium for the match to be played
     */
    public void setLocation(String location) {
	this.location = location;
    }

    /**
     * Method returns the stadium where the match is to be played
     * 
     * @return Returns the stadium name the match is to be played at
     */
    public String getLocation() {
	return location;
    }

    /**
     * This method takes 2 club objects and performs checks to ensure they are .
     * Then it is verified a user hasn't tried to enter the same club twice. If
     * both of these checks are okay then the teams are set, the home teams
     * stadium is assigned as the location. The match is added to the leagues
     * future fixture list and finally the away team is entered into the home
     * teams ArrayList of homeMatches
     * 
     * @param m
     *            This is the match that the user is currently modifying. It is
     *            a parameter itself so that it can be added to the leagues
     *            fixtures
     * @param homeTeam
     *            This is the club object selected to be the home team
     * @param awayTeam
     *            This is the club object selected to be the away team
     * @param l
     *            This is the league that the clubs belong to
     */
    public void setMatch(Match m, Club homeTeam, Club awayTeam, League l) {
	this.homeTeam = homeTeam;
	this.awayTeam = awayTeam;

	if (!(homeTeam.getHomeMatches().contains(awayTeam)))
	{
	    if ((!(homeTeam.equals(awayTeam)))
		    || (!(awayTeam.equals(homeTeam))))
	    {
		location = homeTeam.getStadiumName();
		inHTeam = new ArrayList<Player>();
		inATeam = new ArrayList<Player>();
		System.out.println(homeTeam.getClubName() + " vs "
			+ awayTeam.getClubName() + " created");
		homeTeam.addHomeMatch(awayTeam);
		l.addFixture(m, l);
		return;
	    }

	    System.out.println("Cannot create " + homeTeam.getClubName()
		    + " vs " + awayTeam.getClubName()
		    + " as both teams are the same club.");
	}
	System.out.println("This match has already been created.");
    }

    /**
     * This toString method is set to return 2 different strings depending on
     * whether the game has been played. An if statement tests and then makes
     * small changes in the formatting e.g. if the match has been displayed will
     * display result, if not will just state when it is to be played
     * 
     * @return Returns a string of text with the match details
     */
    public String toString() {
	String a;
	String b;
	String c;
	if (returnMatchPlayed() == false)// If match is still to be played
	// displays game as pending
	{
	    a = " ";
	    b = " ";
	    c = " - Match to be played";
	}
	else
	{// If the match has been played gives the full time score
	    a = " " + totalGoalsHome + " ";
	    b = " " + totalGoalsAway + " ";
	    c = " - Match played at";
	}
	return homeTeam.getClubName() + a + "vs " + awayTeam.getClubName() + b
		+ c + " at " + location + " - " + fixtureDate;
    }

    /**
     * Method returns the home club object
     * 
     * @return Returns the home club object
     */
    public Club getHomeTeam() {
	return homeTeam;
    }

    /**
     * Method returns the away club object
     * 
     * @return Returns the away club object
     */
    public Club getAwayTeam() {
	return awayTeam;
    }

    /**
     * The method selects a player for inclusion in the the home team, and
     * checks they are not a duplicated entry. If the checks are okay the player
     * is added to the home team
     * 
     * 
     * @param player
     *            This tests a player object
     */
    public void selectHomePlayerForTeam(Player player) {
	if (homeTeam != null)
	{
	    if (returnMatchPlayed() == false)// Checks match has not already
					     // been played
	    {
		if (inHTeam.size() < 11)
		{
		    if (!(isPlayerInHTeam(player)))// Checks players is not
						   // already in the team and
						   // then adds player
		    {
			inHTeam.add(player);
		    }
		    else
		    {
			System.out.println("Player is already in the squad");
		    }
		}
		else
		{
		    System.out.println("11 players already selected for "
			    + homeTeam.getClubName());
		}
	    }
	    else
	    {
		System.out.println("Game has already been played");
	    }
	}
	else
	{
	    System.out.println("Home Team needs to be set");
	}
    }

    /**
     * The method selects a player for inclusion in the the home team, and
     * checks they are not a duplicated entry. If the checks are okay the player
     * is added to the home team
     * 
     * 
     * @param player
     *            This tests a player object
     */
    public void selectAwayPlayerForTeam(Player player) {
	if (awayTeam != null)
	{
	    if (returnMatchPlayed() == false)// Checks match has not already
	    // been played
	    {
		if (inATeam.size() < 11)
		{
		    if (!(isPlayerInATeam(player)))// Checks players is not
		    // already in the team and
		    // then adds player
		    {

			inATeam.add(player);
		    }
		    else
		    {
			System.out.println("Player is already in the squad");
		    }
		}
		else
		{
		    System.out.println("11 players already selected for "
			    + awayTeam.getClubName());
		}
	    }
	    else
	    {
		System.out.println("Game has already been played");
	    }
	}
	else
	{
	    System.out.println("Away Team needs to be set");
	}
    }

    /**
     * Method adds the goals scored to the match. The goals are then added to
     * their total tally, to the clubs total tally, to the match tally for that
     * club, and stored in a hashmap so that they can be recorded for future
     * display.
     * 
     * @param player
     *            This takes a player object selected by the user
     * @param gscored
     *            User inputs how many goals the player scored in the game
     */
    public void selectGoalsScorers(Player player, int gscored) {
	if (returnMatchPlayed() == false)// Checks match has not already been
					 // played
	{
	    if (homeTeam != null && awayTeam != null)// Checks home and away
						     // teams have been set
	    {
		if (inHTeam.contains(player))// Checks if the player is in the
					     // home team
		{
		    player.scoredXGoals(gscored);
		    totalGoalsHome += gscored;
		    homeTeam.addGoalsScored(gscored);
		    scoredInMatch.put(player, gscored);
		}
		else if (inATeam.contains(player))// Checks if the player is in
						  // the away team
		{
		    player.scoredXGoals(gscored);
		    totalGoalsAway += gscored;
		    awayTeam.addGoalsScored(gscored);
		    scoredInMatch.put(player, gscored);
		}
		else
		{
		    System.out.println("This player is not in either team");
		}
	    }
	    else
	    {
		System.out.println("Home and Away Team need to be set");
	    }
	}
	else
	{
	    System.out.println("Game has already been played");
	}
    }

    /**
     * This method completes the game. An if statement compares the goal tally
     * of each team from the game and assigns the result accordingly. The points
     * tally of the teams and statistics are updated and the user is given a
     * text output of the result.
     * 
     * The match is moved from the fixtures to pastFixtures ArrayList in league
     * and finally the matchPlayed boolean is updated to show the match is
     * complete and cannot be edited further
     * 
     * 
     * @param m
     *            This match is taken as a parameter so that methods for the
     *            league fixtures can be updated
     * @param l
     *            This is the league the match belongs to
     */
    public void confirmMatchResult(Match m, League l) {

	if (matchPlayed == false)// Checks match has not already been played
	{

	    if ((inHTeam.size() == 11) && (inATeam.size() == 11))// Checks both
								 // teams have
								 // 11 players
								 // set
	    {

		if (totalGoalsHome > totalGoalsAway)// Tests to see if home team
						    // won
		{
		    homeTeam.addPointsTally(3);
		    System.out.println(homeTeam.getClubName()
			    + " won this game");
		    homeTeam.addMatchFigures(1, 0, 0);
		    awayTeam.addMatchFigures(0, 0, 1);

		}
		else if (totalGoalsHome == totalGoalsAway)// Tests to see if
							  // game was a draw
		{
		    homeTeam.addPointsTally(1);
		    awayTeam.addPointsTally(1);
		    System.out.println("Draw!");
		    finalResult = "This games was a draw";
		    homeTeam.addMatchFigures(0, 1, 0);
		    awayTeam.addMatchFigures(0, 1, 0);
		}
		else
		// Sets home team as winner
		{
		    awayTeam.addPointsTally(3);
		    System.out.println(awayTeam.getClubName() + " - win!");
		    finalResult = (awayTeam.getClubName() + " won this game");
		    homeTeam.addMatchFigures(0, 0, 1);
		    awayTeam.addMatchFigures(1, 0, 0);
		}
		awayTeam.addGoalsConceded(totalGoalsHome);
		homeTeam.addGoalsConceded(totalGoalsAway);
		homeTeam.getGoalDifference();// Updates clubs goal difference
					     // for league display
		awayTeam.getGoalDifference();
		l.updateFixture(m);
		finalScore = homeTeam.getClubName() + " " + totalGoalsHome
			+ " vs " + awayTeam.getClubName() + " "
			+ totalGoalsAway;
		matchPlayed = true;
	    }
	}
    }

    /**
     * Method returns the current size of the team
     * 
     * @return Returns an int of the current size of the home team
     */
    public int getHTeamSize() {
	return inHTeam.size();
    }

    /**
     * Method returns the current size of the team
     * 
     * @return Returns an int of the current size of the home team
     */
    public int getATeamSize() {
	return inATeam.size();
    }

    /**
     * Method determines if a match has already been played and returns true or
     * false
     * 
     * @return Returns a boolean stating if the match has been played yet
     */
    public boolean returnMatchPlayed() {
	return matchPlayed;
    }

    /**
     * Method prints out a display of the goal scorers from the match from the
     * scoredInMatch hashmap
     * 
     * @return Returns a list of the scorers from this match
     */
    public String displayScorersfromMatch() {
	String disp = "";

	for (Player player : scoredInMatch.keySet())
	{
	    Integer goalScored = scoredInMatch.get(player);
	    disp += player.getName() + " scored - " + goalScored + "\n";
	}
	return disp;
    }

    /**
     * Method lists the players who played in this match. The int variable comes
     * from the league IO where the user has selected if they want to view the
     * home or away team
     * 
     * @param x
     *            This int is inputted through the console IO
     * @param m
     *            This is the match that is being viewed
     * @param l
     *            This is the league the match belongs to
     * @return Returns a print out of the players who played in the match
     */
    public String showMatchPlayers(int x, Match m, League l) {
	int index = 1;
	String dispH = "";
	if (x == 1)
	{

	    dispH += homeTeam.getClubName() + "\n";

	    for (Player hPlayer : inHTeam)
	    {
		dispH += index + " - " + hPlayer.getName() + "\n";
		index++;
	    }
	    return dispH;
	}
	else if (x == 2)
	    index = 1;
	String dispA = "";

	dispA += awayTeam.getClubName() + "\n";

	for (Player hPlayer : inATeam)
	{
	    dispA += index + " - " + hPlayer.getName() + "\n";
	    index++;
	}
	return dispA;
    }

    /**
     * Method returns final score. The method is only used for unit testing and
     * not called upon by the console IO
     * 
     * @return Returns the final score of the game.
     */
    public String getFinalScore() {
	return finalScore;
    }

    /**
     * Method returns final score. The method is only used for unit testing and
     * not called upon by the console IO
     * 
     * @return Returns the result of the game (win/draw/lose).
     */
    public String getFinalResult() {
	return finalResult;
    }

    /**
     * Method tests if a player is already included in the home team for the
     * match.
     * 
     * @param player
     *            This tests a Player object
     * @return Boolean returns true if player is part of the team
     */
    public boolean isPlayerInHTeam(Player player) {
	return inHTeam.contains(player);
    }

    /**
     * Method tests if a player is already included in the away team for the
     * match.
     * 
     * @param player
     *            This tests a Player object
     * @return Boolean returns true if player is part of the team
     */
    public boolean isPlayerInATeam(Player player) {
	return inATeam.contains(player);
    }

    /**
     * Method is accessed from the League IO class. A user inputs the home team
     * player they wish to select and then this method maps it to the player in
     * the ArrayList and returns the player.
     * 
     * @param selectPlayer
     *            This selects a player object, based on a number selection from
     *            the console IO
     * @param m
     *            This is the match which is being modified
     * @return Returns the player selected from the arrayList
     */
    public Player getHTeamPlayerObject(int selectPlayer, Match m) {
	Player player = inHTeam.get(selectPlayer);
	return player;
    }

    /**
     * Method is accessed from the League IO class. A user inputs the away team
     * player they wish to select and then this method maps it to the player in
     * the ArrayList and returns the player.
     * 
     * @param selectPlayer
     *            This selects a player object, based on a number selection from
     *            the console IO
     * @param m
     *            This is the match which is being modified
     * @return Returns the player selected from the arrayList
     */
    public Player getATeamPlayerObject(int selectPlayer, Match m) {
	Player player = inATeam.get(selectPlayer);
	return player;
    }

    /**
     * This is a testing method to test the above methods work. Each test gives
     * in text what the answer should be, a demonstration of the answer, and
     * then a coding test to ensure that Java confirms the answers are correct
     */
    public static void testMatch() {
	Club C1 = new Club("Chelsea", "Stamford Bridge");
	Club C2 = new Club("Newcastle", "St James");

	Player P1 = new Player("Alan Smith");
	Player P2 = new Player("1P2");
	Player P3 = new Player("1P3");
	Player P4 = new Player("1P4");
	Player P5 = new Player("James Smith");
	Player P6 = new Player("1P6");
	Player P7 = new Player("Dave Smith");
	Player P8 = new Player("1P8");
	Player P9 = new Player("1P9");
	Player P10 = new Player("1P10");
	Player P11 = new Player("1P11");
	Player Q1 = new Player("Jonny Allen");
	Player Q2 = new Player("2Q2");
	Player Q3 = new Player("2Q3");
	Player Q4 = new Player("2Q4");
	Player Q5 = new Player("2Q5");
	Player Q6 = new Player("2Q6");
	Player Q7 = new Player("2Q7");
	Player Q8 = new Player("2Q8");
	Player Q9 = new Player("2Q9");
	Player Q10 = new Player("2Q10");
	Player Q11 = new Player("Peter Beardsley");

	C1.addClubPlayers(P1);
	C1.addClubPlayers(P2);
	C1.addClubPlayers(P3);
	C1.addClubPlayers(P4);
	C1.addClubPlayers(P5);
	C1.addClubPlayers(P6);
	C1.addClubPlayers(P7);
	C1.addClubPlayers(P8);
	C1.addClubPlayers(P9);
	C1.addClubPlayers(P10);
	C1.addClubPlayers(P11);
	C2.addClubPlayers(Q1);
	C2.addClubPlayers(Q2);
	C2.addClubPlayers(Q3);
	C2.addClubPlayers(Q4);
	C2.addClubPlayers(Q5);
	C2.addClubPlayers(Q6);
	C2.addClubPlayers(Q7);
	C2.addClubPlayers(Q8);
	C2.addClubPlayers(Q9);
	C2.addClubPlayers(Q10);
	C2.addClubPlayers(Q11);
	League L1 = new League("Premiership", 5);
	L1.addLeagueClub(C1);
	L1.addLeagueClub(C2);

	Match M1 = new Match(L1);
	M1.setMatch(M1, C1, C2, L1);

	M1.setLocation("Wembeley");
	M1.setFixtureDate(15, 5, 2013, 15, 0, L1, M1);

	System.out.println("Time of the match should be 15/05/2013 - 15:00");
	System.out.println("Time of the match is " +M1.fixtureDate);

	if (M1.fixtureDate.equals("15/05/2013 - 15:00"))
	{
	    System.out.println("Date is correct");
	}
	else
	{
	    System.out.println("Date is not correct");
	}
	System.out.println("");
	System.out.println("Location of the match should be - Wembeley");
	System.out.println("Location is " + M1.getLocation());
	if (M1.getLocation().equals("Wembeley"))
	{
	    System.out.println("Location is correct");
	}
	else
	{
	    System.out.println("Location is not correct");
	}
	System.out.println("");
	System.out
		.println("The clubs playing in the match should be Chelsea and Newcastle");
	System.out.println("The clubs playing in the match are "
		+ M1.homeTeam.getClubName() + " and "
		+ M1.awayTeam.getClubName());

	if (M1.homeTeam.getClubName().equals("Chelsea")
		&& M1.awayTeam.getClubName().equals("Newcastle"))
	{
	    System.out.println("Teams are correct");
	}
	else
	{
	    System.out.println("Teams are incorrect");
	}
	System.out.println("");
	M1.selectHomePlayerForTeam(P1);
	M1.selectHomePlayerForTeam(P2);
	M1.selectHomePlayerForTeam(P3);
	M1.selectHomePlayerForTeam(P4);
	M1.selectHomePlayerForTeam(P5);
	M1.selectHomePlayerForTeam(P6);
	M1.selectHomePlayerForTeam(P7);
	M1.selectHomePlayerForTeam(P8);
	M1.selectHomePlayerForTeam(P9);
	M1.selectHomePlayerForTeam(P10);
	M1.selectHomePlayerForTeam(P11);
	M1.selectAwayPlayerForTeam(Q1);
	M1.selectAwayPlayerForTeam(Q2);
	M1.selectAwayPlayerForTeam(Q3);
	M1.selectAwayPlayerForTeam(Q4);
	M1.selectAwayPlayerForTeam(Q5);
	M1.selectAwayPlayerForTeam(Q6);
	M1.selectAwayPlayerForTeam(Q7);
	M1.selectAwayPlayerForTeam(Q8);
	M1.selectAwayPlayerForTeam(Q9);
	M1.selectAwayPlayerForTeam(Q10);
	M1.selectAwayPlayerForTeam(Q11);

	M1.selectGoalsScorers(P5, 3);
	M1.selectGoalsScorers(Q1, 1);
	M1.selectGoalsScorers(Q11, 2);

	System.out.println("");
	System.out
		.println("Alan Smith, James Smith and Dave Smith should be part of the team Chelsea.  Jonny Allen should not");

	if (M1.isPlayerInHTeam(P1) == true && M1.isPlayerInHTeam(P5) == true
		&& M1.isPlayerInHTeam(P7) == true
		&& M1.isPlayerInHTeam(Q1) == false)
	{
	    System.out.println("Players have been checked correctly");
	}
	else
	{
	    System.out.println("Players have not been checked correctly");
	}

	M1.confirmMatchResult(M1, L1);
	System.out.println("");
	System.out
		.println("Jonny Allen should have 1 goal, Peter Beardsley should have 2 goals and James Smith 3 goals");

	System.out.println(M1.displayScorersfromMatch());

	System.out.println("");
	System.out.println("Score should be Chelsea 3 vs Newcastle 3");
	System.out.println("Score was - " + M1.getFinalScore());

	if (M1.getFinalScore().equals("Chelsea 3 vs Newcastle 3"))
	{
	    System.out.println("Score has been checked correctly");
	}
	else
	{
	    System.out.println("Score has not been checked correctly");
	}
	System.out.println("");
	System.out.println("Result should be - This games was a draw");
	System.out.println("Result was - " + M1.getFinalResult());

	if (M1.getFinalResult().equals("This games was a draw"))
	{
	    System.out.println("Result has been checked correctly");
	}
	else
	{
	    System.out.println("Result has not been checked correctly");
	}
    }
}
