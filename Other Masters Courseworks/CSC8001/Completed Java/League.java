import java.io.*;
import java.util.*;

/**
 * The league class processes the data in the match, clubs and player classes
 * and holds the fixtures and results.
 * 
 * @author Gary Carr
 */
public class League
{
    private Scanner sc = new Scanner(System.in);
    private String leagueName;
    private ArrayList<Club> leagueClubs;
    private ArrayList<Player> topScorer;
    private ArrayList<Match> fixture;
    private ArrayList<Match> pastFixture;
    private int maxNoOfTeams;

    /**
     * The constructor for league creates an empty league and initializes
     * multiple ArrayLists - clubs in the league, a future fixture list, a past
     * fixtures list, and the top scorer in the league. The constructor then
     * sets the maximum number of teams in the league
     * 
     * @param leagueName
     *            This is the user inputted name for the league
     * @param maxNoOfTeams
     *            This is the user inputted maximum number of teams a league can
     *            contain
     */
    public League(String leagueName, int maxNoOfTeams)

    {
	this.leagueName = leagueName;
	leagueClubs = new ArrayList<Club>();
	fixture = new ArrayList<Match>();
	pastFixture = new ArrayList<Match>();
	topScorer = new ArrayList<Player>();
	this.maxNoOfTeams = maxNoOfTeams;
	setMaxNoOfTeams(maxNoOfTeams);
    }

    /**
     * Method changes the name of the league
     * 
     * @param leagueName
     *            This is inputted by the user and used as a new name for the
     *            league
     */
    public void setLeagueName(String leagueName) {
	this.leagueName = leagueName;
    }

    /**
     * Method returns the leagues name
     * 
     * @return Returns the leagues name
     */
    public String getLeagueName() {
	return leagueName;
    }

    /**
     * User inputs a maximum number of teams for the league. A while loops
     * ensures 2 or more teams must be entered
     * 
     * @param maxNoOfTeams
     *            Sets the maximum number of teams the league is allowed to have
     */
    public void setMaxNoOfTeams(int maxNoOfTeams) {
	while (maxNoOfTeams < 2)
	{
	    System.out.println("At least 2 teams must be included");
	    System.out
		    .println("What is the maximum number of clubs in this league?");
	    maxNoOfTeams = sc.nextInt();
	    this.maxNoOfTeams = maxNoOfTeams;
	    setMaxNoOfTeams(maxNoOfTeams);// Tests the max number of teams again
	}

	this.maxNoOfTeams = maxNoOfTeams;
    }

    /**
     * Method is used to display a message in the console IO to show user how
     * many more teams can be entered.
     * 
     * @return Returns the maximum number of teams a league is allowed to have
     */
    public int getMaxTeams() {
	return maxNoOfTeams;
    }

    /**
     * Method adds a club to the ArrayList league. It tests to ensure that the
     * club is not going to exceed the max teams allowed before adding
     */
    public void addLeagueClub(Club newLeagueClub) {
	if (leagueClubs.size() < maxNoOfTeams)// Checks that the league has not
	// exceeded the maximum size
	{
	    leagueClubs.add(newLeagueClub);
	}
	else
	{
	    System.out
		    .println("League has reached maximum size. Unable to add any more teams");
	}
    }

    /**
     * Method returns an ArrayList of all the leagues clubs for the LeagueIO to
     * get a team
     * 
     * @return Returns a club from the ArrayList
     */
    public ArrayList<Club> getLeagueClub() {
	return leagueClubs;
    }

    /**
     * This method is called from the Match class the newly created match to the
     * fixture ArrayList. There is a test to ensure the match does not already
     * exist in the fixtures and pastFixtures ArrayLists first to stop
     * duplication, and if not it is added
     * 
     * @param m
     *            This is the match object which will be added to the fixture
     *            list
     * @param l
     *            This is the league that the match will be part of
     */
    public void addFixture(Match m, League l) {
	if (!(fixture.contains(m)) || (!(pastFixture.contains(m))))// Checks if
								   // match
								   // already
								   // exists
	{
	    fixture.add(m);
	}
	else
	{
	    System.out
		    .println("Cannot add this fixture as the match as it has already been added");
	}
    }

    /**
     * Method is called when a match result is confirmed and removes a game from
     * the fixture ArrayList and adds it to the completed games once the match
     * has been complete
     * 
     * @param m
     *            This is the match which is being updated
     */
    public void updateFixture(Match m) {
	fixture.remove(m);
	pastFixture.add(m);
    }

    /**
     * Method returns the how many teams are in the league
     * 
     * @return Returns the current size of the league
     */
    public int getLeagueCurrentSize() {
	return leagueClubs.size();
    }

    /**
     * Method returns the number of future fixtures set in the league
     * 
     * @return Returns the amount of fixtures in the ArrayList fixtures
     */
    public int getFixtureSize() {
	return fixture.size();
    }

    /**
     * Method returns the number of finished matches in the league
     * 
     * @return Returns the amount of fixtures in the ArrayList pastFixtures
     */
    public int getResultFixtureSize() {
	return pastFixture.size();
    }

    /**
     * Method is used by the league IO to select a fixture after the user has
     * inputted a number into the console IO. Method then returns this match
     * 
     * @param selectMatch
     *            This is the user inputted match they have selected, and it is
     *            mapped to the matches position in the fixture array list
     * @param l
     *            This is the league that the matches belong to
     * @return Returns the match object that the user has picked
     */
    public Match getFixtureItem(int selectMatch, League l) {
	Match match = fixture.get(selectMatch);
	return match;
    }

    /**
     * Method is used by the league IO to select a past fixture after the user
     * has inputted a number into the console IO.
     * 
     * @param selectMatch
     *            This is the user inputted match they have selected, and it is
     *            mapped to the matches position in the pastFixture array list
     * @param l
     *            This is the league that the matches belong to
     * @return Returns the match object that the user has picked
     */
    public Match getPastFixtureItem(int selectMatch, League l) {
	Match match = pastFixture.get(selectMatch);
	return match;
    }

    /**
     * Method is used by the league IO to select a club after the user has
     * inputted a number into the console IO.
     * 
     * @param selectClub
     *            This is the user inputted club they have selected, and it is
     *            mapped to the matches position in the pastFixture array list
     * @param l
     *            This is the league that the club belong to
     * @return Returns the club object from the ArrayList
     */
    public Club getLeagueItem(int selectClub, League l) {
	Club club = leagueClubs.get(selectClub);
	return club;
    }

    /**
     * Method searches through each team and obtains the players goal tallys.
     * When they are found to be equal or higher to the highest amount they are
     * added to the ArrayList topScorer and the list is updated accordingly
     * 
     * @return Returns an arraylist containing the top scorers in the league
     */
    public ArrayList<Player> getGoldenBoot() {
	int goldenBoot = 1;// Set at one so that if no players have scored yet
			   // no results are found
	topScorer.clear();
	for (Club clubs : leagueClubs)// Searching each team
	{
	    for (Player player : clubs.getSquad())// Searching through each
	    // player in the squad
	    {
		if (player.getGoalTally() == goldenBoot)// When a player is
							// found with an equal
							// goal tally they are
							// added to the
							// ArrayList
		{
		    topScorer.add(player);
		}
		else if (player.getGoalTally() >= goldenBoot)// When a player is
							     // found with a
							     // goal tally
							     // higher then the
							     // current top,
							     // goldenboot is
							     // set at his
							     // current tally,
							     // the arrayList is
							     // cleared, and
							     // that player is
							     // entered into it
		{
		    topScorer.clear();
		    goldenBoot = player.getGoalTally();
		    topScorer.add(player);
		}
	    }
	}
	return topScorer;

    }

    /**
     * Prints the top goal scorers to the console
     * 
     * @return Returns a display of the top scorers
     */
    public String displayTopScorer() {
	String disp = "";
	getGoldenBoot();
	if (topScorer.size() > 0)
	{
	    for (Player p : topScorer)
	    {
		disp += p.getName() + " has " + p.getGoalTally() + " goals"
			+ "\n";
	    }
	    return disp;
	}
	else
	{
	    disp = "No goal scorers in league so far";
	    return disp;
	}
    }

    /**
     * Method prints out a list of the club names which have been added to the
     * league
     * 
     * @return Returns a print out of all the league clubs
     */
    public String showClubs() {
	String showClubs = "";
	int index = 1;
	for (Club clubs : leagueClubs)
	{
	    showClubs += index + " - " + clubs.getClubName() + "\n";
	    index++;
	}
	return showClubs;
    }

    /**
     * Method prints all matches in the future for the chosen league
     * 
     * @return Returns a print out of the future fixture list
     */
    public String showFutureMatches() {
	String fMatches = "";
	int index = 1;

	for (Match match : fixture)
	{
	    fMatches += index + " - " + match + "\n";
	    index++;
	}
	fMatches += ("\n");
	return fMatches;
    }

    /**
     * Method prints all the results in the league
     * 
     * @return Returns the printed list of the clubs
     */
    public String showFinishedMatches() {
	String pMatches = "";
	int index = 1;
	for (Match match : pastFixture)
	{
	    pMatches += index + " - " + match + "\n";
	    index++;
	}
	pMatches += ("\n");
	return pMatches;

    }

    /**
     * Method prints the league table after ranking the clubs in order based on
     * the compareTo method in club.
     * 
     * @return Returns the league table
     */
    public String getLeagueTable() {
	String leagueT = "";
	Collections.sort(leagueClubs);// Sorts the clubs by the Club compareTo
				      // method
	leagueT += "\n" + getLeagueName();
	leagueT += ("\n");
	leagueT += ("\n" + "Team" + "\t" + "\t" + "\t" + "GP" + "\t" + "W"
		+ "\t" + "D" + "\t" + "L" + "\t" + "GS" + "\t" + "GC" + "\t"
		+ "GD" + "\t" + "P");// Formatting for the league table
	leagueT += ("\n");
	for (Club club : leagueClubs)
	{
	    leagueT += club + "\n";

	}

	return leagueT;
    }

    /**
     * Method sorts all teams in the ArrayList leagueClubs then returns the
     * current top team in the league as defined by compareTo in the match class
     * 
     * @return Returns a club object
     */
    public Club getTopTeam() {
	Club topTeam = Collections.min(leagueClubs);// The top team in league
						    // is now first in the
						    // ArrayList, therefore .min
						    // finds this team
	return topTeam;
    }

    /**
     * Method tests if the selected club is part of the league. This method is
     * solely used for unit testing
     * 
     * @param club
     *            This takes a club object
     * @return Returns true if club is part of the team
     */
    public boolean isClubInLeague(Club clubs) {
	return leagueClubs.contains(clubs);
    }

    /**
     * This is a testing method to test the above methods work. Each test gives
     * in text what the answer should be, a demonstration of the answer, and
     * then a coding test to ensure that Java confirms the answers are correct
     */
    public static void testLeague() {
	Club C1 = new Club("Newcastle", "St James");
	Club C2 = new Club("Chelsea", "Stamford Bridge");
	Club C3 = new Club("Tottenham", "White Hart Lane");
	Club C4 = new Club("Brisbane", "Australia Park");

	Player P1 = new Player("1P1");
	Player P2 = new Player("1P2");
	Player P3 = new Player("1P3");
	Player P4 = new Player("1P4");
	Player P5 = new Player("1P5");
	Player P6 = new Player("1P6");
	Player P7 = new Player("1P7");
	Player P8 = new Player("1P8");
	Player P9 = new Player("Alan Shearer");
	Player P10 = new Player("1P10");
	Player P11 = new Player("1P11");
	Player Q1 = new Player("2Q1");
	Player Q2 = new Player("2Q2");
	Player Q3 = new Player("2Q3");
	Player Q4 = new Player("2Q4");
	Player Q5 = new Player("2Q5");
	Player Q6 = new Player("2Q6");
	Player Q7 = new Player("2Q7");
	Player Q8 = new Player("2Q8");
	Player Q9 = new Player("Drogba");
	Player Q10 = new Player("2Q10");
	Player Q11 = new Player("2Q11");
	Player J1 = new Player("3J1");
	Player J2 = new Player("3J2");
	Player J3 = new Player("3J3");
	Player J4 = new Player("3J4");
	Player J5 = new Player("3J5");
	Player J6 = new Player("3J6");
	Player J7 = new Player("3J7");
	Player J8 = new Player("3J8");
	Player J9 = new Player("3J9");
	Player J10 = new Player("Defoe");
	Player J11 = new Player("3J11");
	Player A1 = new Player("3A1");
	Player A2 = new Player("3A2");
	Player A3 = new Player("3A3");
	Player A4 = new Player("3A4");
	Player A5 = new Player("3A5");
	Player A6 = new Player("3A6");
	Player A7 = new Player("3A7");
	Player A8 = new Player("3A8");
	Player A9 = new Player("3A9");
	Player A10 = new Player("Defoe");
	Player A11 = new Player("3A11");

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
	C3.addClubPlayers(J1);
	C3.addClubPlayers(J2);
	C3.addClubPlayers(J3);
	C3.addClubPlayers(J4);
	C3.addClubPlayers(J5);
	C3.addClubPlayers(J6);
	C3.addClubPlayers(J7);
	C3.addClubPlayers(J8);
	C3.addClubPlayers(J9);
	C3.addClubPlayers(J10);
	C3.addClubPlayers(J11);
	C2.addClubPlayers(Q11);
	C4.addClubPlayers(A1);
	C4.addClubPlayers(A2);
	C4.addClubPlayers(A3);
	C4.addClubPlayers(A4);
	C4.addClubPlayers(A5);
	C4.addClubPlayers(A6);
	C4.addClubPlayers(A7);
	C4.addClubPlayers(A8);
	C4.addClubPlayers(A9);
	C4.addClubPlayers(A10);
	C4.addClubPlayers(A11);
	League L1 = new League("Dummy League", 5);
	L1.addLeagueClub(C1);
	L1.addLeagueClub(C2);
	L1.addLeagueClub(C3);
	L1.addLeagueClub(C4);

	Match M1 = new Match(L1);
	M1.setMatch(M1, C1, C2, L1);
	Match M2 = new Match(L1);
	M2.setMatch(M2, C1, C3, L1);
	Match M3 = new Match(L1);
	M3.setMatch(M3, C2, C1, L1);
	Match M4 = new Match(L1);
	M4.setMatch(M4, C2, C3, L1);
	Match M5 = new Match(L1);
	M5.setMatch(M5, C3, C1, L1);
	Match M6 = new Match(L1);
	M6.setMatch(M6, C3, C2, L1);
	Match M7 = new Match(L1);
	M7.setMatch(M7, C4, C2, L1);
	Match M8 = new Match(L1);
	M8.setMatch(M8, C3, C4, L1);
	M1.setLocation("St James");
	M1.setFixtureDate(15, 5, 2013, 15, 0, L1, M1);
	M2.setLocation("St James");
	M2.setFixtureDate(16, 5, 2013, 15, 0, L1, M2);
	M3.setLocation("Stamford Bridge");
	M3.setFixtureDate(17, 5, 2013, 15, 0, L1, M3);
	M4.setLocation("Stamford Bridge");
	M4.setFixtureDate(18, 5, 2013, 15, 0, L1, M4);
	M5.setLocation("White Hart Lane");
	M5.setFixtureDate(19, 5, 2013, 15, 0, L1, M5);
	M6.setLocation("White Hart Lane");
	M6.setFixtureDate(20, 5, 2013, 15, 0, L1, M6);
	M7.setLocation("Brisbane");
	M7.setFixtureDate(20, 5, 2013, 15, 0, L1, M7);
	M8.setLocation("Chester");
	M8.setFixtureDate(20, 5, 2013, 15, 0, L1, M8);

	L1.setLeagueName("Premiership");

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
	M2.selectHomePlayerForTeam(P1);
	M2.selectHomePlayerForTeam(P2);
	M2.selectHomePlayerForTeam(P3);
	M2.selectHomePlayerForTeam(P4);
	M2.selectHomePlayerForTeam(P5);
	M2.selectHomePlayerForTeam(P6);
	M2.selectHomePlayerForTeam(P7);
	M2.selectHomePlayerForTeam(P8);
	M2.selectHomePlayerForTeam(P9);
	M2.selectHomePlayerForTeam(P10);
	M2.selectHomePlayerForTeam(P11);
	M2.selectAwayPlayerForTeam(J1);
	M2.selectAwayPlayerForTeam(J2);
	M2.selectAwayPlayerForTeam(J3);
	M2.selectAwayPlayerForTeam(J4);
	M2.selectAwayPlayerForTeam(J5);
	M2.selectAwayPlayerForTeam(J6);
	M2.selectAwayPlayerForTeam(J7);
	M2.selectAwayPlayerForTeam(J8);
	M2.selectAwayPlayerForTeam(J9);
	M2.selectAwayPlayerForTeam(J10);
	M2.selectAwayPlayerForTeam(J11);

	M1.selectGoalsScorers(P9, 5);
	M1.selectGoalsScorers(Q2, 2);
	M2.selectGoalsScorers(P9, 1);
	M2.selectGoalsScorers(J9, 1);
	M2.selectGoalsScorers(P6, 2);

	M1.confirmMatchResult(M1, L1);

	M2.confirmMatchResult(M2, L1);
	System.out.println("");
	System.out.println("The league should be called Premiership");
	System.out.println("The league is called - " + L1.getLeagueName());

	if (L1.getLeagueName().equals("Premiership"))
	{
	    System.out.println("League name is correct");
	}
	else
	{
	    System.out.println("League name is incorrect");
	    System.out.println("");
	    System.out
		    .println("The clubs Chelsea and Newcastle, Tottenham and Brisbane should be in the league");
	    System.out.println("These clubs are in the league - ");
	    L1.showClubs();

	    if (L1.isClubInLeague(C1) == true && L1.isClubInLeague(C2) == true
		    && L1.isClubInLeague(C3) == true
		    && L1.isClubInLeague(C4) == true)
	    {
		System.out.println("Clubs have been added correctly");
	    }
	    else
	    {
		System.out.println("Clubs have not been added correctly");
	    }
	}

	System.out.println("");
	System.out.println("League should contain 4 clubs");
	System.out.println("League contains " + L1.getLeagueCurrentSize());

	if (L1.getLeagueCurrentSize() == 4)
	{
	    System.out.println("League size is correct");
	}
	else
	{
	    System.out.println("League size is not correct");
	}

	System.out.println("");
	System.out
		.println("League should contain 2 results, Newcastle vs Chelsea and Newcastle vs Tottenham");
	L1.showFinishedMatches();
	if (L1.getResultFixtureSize() == 2)
	{
	    System.out.println("Results list is correct");
	}
	else
	{
	    System.out.println("Results list is not correct");
	}

	System.out.println("");
	System.out.println("League should contain 6 fixtures");
	L1.showFutureMatches();

	if (L1.getFixtureSize() == 6)
	{
	    System.out.println("Fixture list is correct");
	}
	else
	{
	    System.out.println("Fixture list is not correct");
	}
	System.out.println("");
	System.out
		.println("League should contain 2 results, Newcastle vs Chelsea and Newcastle vs Tottenham");
	L1.showFinishedMatches();

	if (L1.getResultFixtureSize() == 2)
	{
	    System.out.println("Results list is correct");
	}
	else
	{
	    System.out.println("Results list is not correct");
	}
	System.out.println("");
	System.out.println("Top team should be Newcastle");
	System.out.println("Top team is " + L1.getTopTeam().getClubName());

	if (L1.getTopTeam().getClubName().equals("Newcastle"))
	{
	    System.out.println("Top team is correct");
	}
	else
	{
	    System.out.println("Top team is not correct");
	}
	System.out.println("");
	System.out.println("Top scorer should be Alan Shearer");
	L1.getGoldenBoot();
	if (L1.topScorer.contains(P9))
	{
	    System.out.println("Top scorer is correct");
	}
	else
	{
	    System.out.println("Top scorer is not correct");
	}
	System.out.println("");
	System.out
		.println("The league should print Newcastle top with 2 wins, then the remaining teams sorted by goal difference");
	System.out.println(L1.getLeagueTable());
    }
}
