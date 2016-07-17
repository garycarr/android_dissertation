import java.io.*;
import java.util.*;

/**
 * The LeagueIO class is the user interface for entering, updating and
 * displaying information regarding multiple leagues. Within the league IO the
 * users inputs are passed to the classes to create or modify objects. A static
 * Arraylist of leagues is held.
 * 
 * @author Gary Carr
 */
public class LeagueIO
{
    private static ArrayList<League> leagueList = new ArrayList<League>();
    private static Scanner sc = new Scanner(System.in);

    /**
     * Entry menu into League IO. Allows user to import a league from the test
     * date, add league from a file, or start with no leagues
     * 
     * @param args
     *            Program arguments
     * @throws IOException
     *             Throws exception if file not found
     */
    public static void main(String args[]) {
	System.out.println("Welcome to the League Editor!");
	System.out.println("1. Add league from the text file");
	System.out.println("2. Start with empty league");
	int option = 0;
	String input = sc.nextLine();
	option = new Integer(input);
	switch (option)
	{
	case 1:
	    System.out.println("Please type the name/path of the file");
	    String leagueFName = sc.nextLine();
	    inputLeague(leagueFName);
	    leagueSubMenu();
	    break;
	case 2:
	    leagueSubMenu();
	    break;
	default:
	    System.out.println("Wrong number entered");
	    leagueSubMenu();
	    break;
	}

    }

    /**
     * This menu directs a user to create a new league, select from a list of
     * existing leagues, or close the program
     */
    private static void leagueSubMenu() {
	System.out.println("");
	System.out.println("1. Create a new league");
	System.out.println("2. List available leagues for editing");
	System.out.println("3. Close League Editor");

	int option = 0;

	String input = sc.nextLine();
	option = new Integer(input);
	{
	    switch (option)
	    {
	    case 1:
		createLeague();
		break;
	    case 2:
		selectLeague();
		break;
	    case 3:
		System.exit(0);
		break;
	    default:
		System.out.println("Wrong number entered");
		leagueSubMenu();
		break;
	    }
	}
    }

    /**
     * Menu directs user to create a new league. This method requests the name
     * of the league, how many teams are allowed, and then passes to the league
     * class to create the league. The user is then given the option to create
     * new teams in the league
     * 
     */
    private static void createLeague() {

	System.out.println("");
	System.out.println("Please input the new leagues name");
	String leagueName = sc.nextLine();
	System.out
		.println("What is the maximum number of clubs in this league?");

	int maxNoOfTeams = sc.nextInt();
	League newLeague = new League(leagueName, maxNoOfTeams);
	leagueList.add(newLeague);
	String force = sc.nextLine();

	System.out.println("");
	System.out.println(newLeague.getLeagueName() + " has now been created");

	System.out.println("Do you now want to add new teams to the league?");
	System.out.println("1. Yes");
	System.out.println("2. Return to main menu");
	String input = sc.nextLine();

	int option = new Integer(input);
	{
	    switch (option)
	    {
	    case 1:
		createAClubInALeague(newLeague);
		break;
	    case 2:
		leagueSubMenu();
		break;
	    default:
		System.out.println("Wrong number entered");
		leagueSubMenu();
		break;
	    }
	}
    }

    /**
     * Method prints all the leagues contained within the ArrayList leagueList
     * 
     * @return Returns the list of the available leagues in ArrayList leaguelist
     */
    public static String printLeagues() {
	String print = "";
	int in = 1;
	print = "List of available leagues" + "\n";
	for (League leagues : leagueList)
	{
	    print += in + " - " + leagues.getLeagueName() + "\n";
	    in++;
	}
	return print;
    }

    /**
     * Menu calls on printLeagues method to display all available leagues and
     * the user can then select which league to access
     */
    private static void selectLeague() {

	if (leagueList.size() > 0)// If no league yet exists the user is brought
	// back to the leagueSubMenu
	{
	    System.out.println("");
	    System.out.println(printLeagues());

	    System.out.println("Which league do you want to choose?");
	    System.out.println("Type 0 to return");

	    String input = sc.nextLine();
	    int option = new Integer(input);
	    option--; // The users is displayed with a list beginning at 1,
	    // therefore 1 is minused to match the ArrayList
	    while (option < leagueList.size())// Tests that the user has
					      // selected an available
					      // league
	    {

		if (option == -1)
		{
		    leagueSubMenu();
		}
		else
		{
		    League l = leagueList.get(option);
		    modifyLeague(l);
		}
	    }
	    System.out.println("Wrong figure entered");
	    selectLeague();
	}
	else
	{
	    System.out.println("No leagues created yet");
	    leagueSubMenu();
	}
    }

    /**
     * Menu page allows user to select a club within the league for modifying
     * 
     * @param l
     *            Displays all clubs that have been added to this league
     */
    public static void selectClubsInLeague(League l) {
	if (l.getLeagueCurrentSize() > 0)
	{
	    System.out.println("");
	    System.out.println(l.showClubs());
	    System.out.println("Which club do you want to choose?");
	    System.out.println("Type 0 to return to league");

	    String input = sc.nextLine();
	    int option = new Integer(input);
	    option--;// The users is displayed with a list beginning at 1,
		     // therefore 1 is minused to match the ArrayList
	    while (option < l.getLeagueCurrentSize())// Tests that the user has
	    // selected an available
	    // league
	    {
		if (option == -1)
		{
		    modifyLeague(l);
		}
		else
		{
		    Club c = l.getLeagueItem(option, l);
		    modifyClubsWithinLeagueMenu(c, l);
		}
	    }
	    System.out.println("Wrong figure entered");
	    selectClubsInLeague(l);
	}
	else
	{
	    System.out.println("No clubs exist yet in league");
	    modifyLeague(l);
	}
    }

    /**
     * Method directs user to create a new match. A list of teams is displayed
     * by the showClubs method and the user selects 2 teams which are checked to
     * ensure they are unique, and then a match is created. The method setMatch
     * in Match class will verify the game can be added to the fixture list
     * 
     * @param l
     *            This is the league which the new match will belong to
     */
    private static void createMatchinLeague(League l) {
	System.out.println(l.showClubs());
	if (l.getLeagueCurrentSize() < 2)// Tests there are at least 2 clubs in
					 // the league to create a match
	{
	    System.out
		    .println("Not enough clubs in league yet, must be at least 2");
	    matchSubMenu(l);
	}
	System.out.println("Please select home team");
	String input = sc.nextLine();
	int home = new Integer(input);
	if ((home) > l.getLeagueCurrentSize())// User will be return to match
	// sub menu if they input wrong
	// figure
	{
	    System.out.println("Invalid number entered");
	    createMatchinLeague(l);
	}
	else
	{
	    System.out.println("Please select away team");
	    input = sc.nextLine();
	    int away = new Integer(input);
	    if (away > l.getLeagueCurrentSize())
	    {
		System.out.println("Invalid number entered");
		matchSubMenu(l);
	    }
	    else
	    {
		home -= 1;
		away -= 1;// The users is displayed with a list beginning
		// at 1, therefore 1 is minused to match the
		// ArrayList

		Club h = l.getLeagueItem(home, l);
		Club a = l.getLeagueItem(away, l);

		if (h.equals(a))// Tests both clubs are different
		{
		    System.out
			    .println(h.getClubName()
				    + " vs "
				    + a.getClubName()
				    + " - This match cannot be created as both teams picked are the same");
		    createMatchinLeague(l);
		}
		if (h.getHomeMatches().contains(a))// Tests if match has already
						   // been set
		{
		    System.out
			    .println("Cannot create this match as it has already been set");
		    matchSubMenu(l);
		}

		Match match = new Match(l);
		match.setMatch(match, h, a, l);
		matchSubMenu(l);
	    }

	    System.out.println("Wrong figure entered");
	    createMatchinLeague(l);
	}

    }

    /**
     * Menu page directs user to create a new match, pick a future fixture,
     * review the result of a completed game or input a result from a text file
     * 
     * @param l
     *            Checks for all matches that have been created for the given
     *            league
     */
    private static void matchSubMenu(League l) {
	System.out.println("");
	System.out.println("1. Create new match");
	System.out.println("2. List fixtures available for modifying");
	System.out.println("3. List results of previous fixtures");
	System.out.println("4. Input results from a text file");
	System.out.println("5. Return to league");

	int option = 0;

	String input = sc.nextLine();
	option = new Integer(input);
	{
	    switch (option)
	    {
	    case 1:
		createMatchinLeague(l);
		break;
	    case 2:
		selectMatch(l);
		break;
	    case 3:
		selectFinishedMatch(l);
		break;
	    case 4:
		System.out.println("Please type the name/path of the file");
		String resultFname = sc.nextLine();
		inputMatch(resultFname, l);
		matchSubMenu(l);
		break;
	    case 5:
		modifyLeague(l);
		break;
	    default:
		System.out.println("Wrong number entered");
		matchSubMenu(l);
		break;
	    }
	}
    }

    /**
     * Method displays all future matches and prompts the user to select one for
     * editing
     * 
     * @param l
     *            This is the league the matches belong to
     */
    private static void selectMatch(League l) {
	if (l.getFixtureSize() > 0)// If no matches exist the user is returned
				   // to
	// matchSubMenu
	{
	    System.out.println(l.showFutureMatches());
	    System.out.println("Which match do you want to choose?");
	    System.out.println("Type 0 to return");

	    String input = sc.nextLine();
	    int option = new Integer(input);
	    if (option > l.getFixtureSize())// Tests that not too high a number
					    // has been entered
	    {
		System.out.println("Wrong number entered");
		matchSubMenu(l);
	    }
	    while (option - 1 < l.getFixtureSize())
	    {
		if (option == 0)// If the user types 0 they are returned to
		// the matchSubMenu
		{
		    matchSubMenu(l);
		}
		else
		{
		    option -= 1;// The users is displayed with a list beginning
		    // at 1, therefore 1 is minused to match the
		    // ArrayList
		    Match m = l.getFixtureItem(option, l);
		    modifyMatch(m, l);
		}
	    }
	    System.out.println("Wrong figure entered");
	    matchSubMenu(l);
	}
	else
	{
	    System.out.println("No fixtures created yet");
	    matchSubMenu(l);
	}
    }

    /**
     * Method displays all future matches and prompts the user to select one for
     * editing
     * 
     * @param l
     *            This is the league the past games belong to
     */
    private static void selectFinishedMatch(League l) {
	if (l.getResultFixtureSize() > 0)// If no matches have yet been
	// completed the user is returned to
	// matchSubMenu
	{
	    System.out.println(l.showFinishedMatches());
	    System.out.println("Which match do you want to choose?");
	    System.out.println("Type 0 to return");

	    String input = sc.nextLine();
	    int option = new Integer(input);
	    if (option > l.getFixtureSize())// Tests that not too high a number
					    // has been entered
	    {
		System.out.println("Wrong number entered");
		matchSubMenu(l);
	    }
	    while (option - 1 < l.getResultFixtureSize())
	    {
		if (option == 0)// If the user types 0 they are returned to
		// the matchSubMenu
		{
		    matchSubMenu(l);
		}
		else
		{
		    option -= 1;// The users is displayed with a list beginning
		    // at 1, therefore 1 is minused to match the
		    // ArrayList
		    Match m = l.getPastFixtureItem(option, l);
		    viewFinishedMatch(m, l);
		}
	    }
	    System.out.println("Wrong figure entered");
	    matchSubMenu(l);
	}
	else
	{
	    System.out.println("No results created yet");
	    matchSubMenu(l);
	}
    }

    /**
     * * Menu allows a club to be created for the selected league. An if
     * statement ensures that the league does not already contain its maximum
     * number of clubs
     * 
     * @param l
     *            This is the league the new club will belong to
     */
    private static void createAClubInALeague(League l) {
	if (l.getLeagueCurrentSize() < l.getMaxTeams())
	{
	    System.out.println("Please input new clubs name");
	    String clubName = sc.nextLine();
	    System.out.println("Input stadium");
	    String stadium = sc.nextLine();
	    Club newClub = new Club(clubName, stadium);
	    l.addLeagueClub(newClub);

	    System.out.println(newClub.getClubName()
		    + " has now been created and entered into - "
		    + l.getLeagueName());
	    System.out.println(l.getLeagueCurrentSize()
		    + " clubs selected.  League maximum is " + l.getMaxTeams());
	    modifyLeague(l);
	}
	else
	{
	    System.out.println("There are already " + l.getLeagueCurrentSize()
		    + " teams in this league. No more teams can be added");
	    modifyLeague(l);
	}
    }

    /**
     * Menu page allows user to make changes to the details of the club or
     * access players within the team
     * 
     * @param c
     *            This is the club that has been selected to modify
     * @param l
     *            This is the league which the club belongs to
     */
    public static void modifyClubsWithinLeagueMenu(Club c, League l) {
	System.out.println("");
	System.out.println("Club selected - " + c.getClubName());
	System.out.println("1. Add new player to the squad");
	System.out.println("2. View details of club");
	System.out.println("3. View details of players");
	System.out.println("4. Return to league");

	String input = sc.nextLine();
	int option = new Integer(input);

	switch (option)
	{
	case 1:
	    createAPlayerInAClub(c, l);
	    break;
	case 2:
	    modifyClub(c, l);
	    break;
	case 3:
	    selectPlayerInClubMenu(c, l);
	    break;
	case 4:
	    modifyLeague(l);
	    break;
	default:
	    System.out.println("Wrong number entered");
	    modifyClubsWithinLeagueMenu(c, l);
	    break;
	}
    }

    /**
     * Method displays all players in the club and prompts the user to select
     * one for editing
     * 
     * @param c
     *            This is the club that the player belongs to
     * @param l
     *            This is the league that the club belongs to
     */
    public static void selectPlayerInClubMenu(Club c, League l) {

	if (c.squadSize() > 0)// If no players exists the user is brought back
	// to the modifyClubsWithinLeagueMenu
	{
	    System.out.println(c.showPlayers());
	    System.out.println("Which player do you want to choose?");
	    System.out.println("Type 0 to cancel");

	    String input = sc.nextLine();
	    int option = new Integer(input);

	    while (option - 1 < c.squadSize())
	    {
		if (option == 0)
		{
		    modifyClubsWithinLeagueMenu(c, l);
		}
		else
		{
		    option -= 1;
		    Player p = c.getPlayerItem(option, c, l);
		    modifyPlayer(p, c, l);

		}
	    }
	    System.out.println("Wrong figure entered");
	    selectPlayerInClubMenu(c, l);
	}
	else
	{
	    System.out.println("No players in squad");
	    modifyClubsWithinLeagueMenu(c, l);

	}
    }

    /**
     * Menu allows a new player to be created for the selected club. Name,
     * height and birthday are then passed to the player class for exception
     * checking
     * 
     * @param c
     *            This is the club the player will belong to
     * @param l
     *            This is the league the club belongs to
     */
    private static void createAPlayerInAClub(Club c, League l) {

	System.out.println("Please input players Name");
	String name = sc.nextLine();
	Player p = new Player(name);
	System.out.println("Input Height");
	double height = sc.nextDouble();
	p.setHeight(height);

	System.out.println("Input day of birth");
	int x = sc.nextInt();
	System.out.println("Input month of birth");
	int y = sc.nextInt();
	System.out.println("Input Year of birth");
	int z = sc.nextInt();
	p.setDOB(p, x, y, z);// Date is validated in Player Class

	c.addClubPlayers(p);
	String force = sc.nextLine();
    }

    /**
     * Menu page allows user to view or make changes to the details of the
     * players
     * 
     * @param p
     *            This is the player selected
     * @param c
     *            This is the club the player belongs to
     * @param l
     *            This is the league the club belongs to
     */
    private static void modifyPlayer(Player p, Club c, League l) {
	System.out.println("");
	System.out.println("Modifying - " + p.getName());
	System.out.println("1. Get personal details");
	System.out.println("2. Change Name");
	System.out.println("3. Change Height");
	System.out.println("4. Change birthday");
	System.out.println("5. Return to club");

	int option = 0;

	String input = sc.nextLine();
	option = new Integer(input);
	{
	    switch (option)
	    {
	    case 1:
		System.out.println(p);
		modifyPlayer(p, c, l);
		break;
	    case 2:
		setNewName(p, c, l);
		modifyPlayer(p, c, l);
		break;
	    case 3:
		setNewHeight(p, c, l);
		modifyPlayer(p, c, l);
		break;
	    case 4:
		setNewDateOfBirth(p, c, l);
		modifyPlayer(p, c, l);
		break;
	    case 5:
		modifyClubsWithinLeagueMenu(c, l);
		break;
	    default:
		System.out.println("Wrong number entered");
		modifyPlayer(p, c, l);
		break;
	    }
	}
    }

    /**
     * Method allows the user to assign a new name to the selected player. The
     * name is then passed to the player class for exception checking
     * 
     * @param p
     *            This is the player selected
     * @param c
     *            This is the club the player belongs to
     * @param l
     *            This is the league the club belongs to
     */
    public static void setNewName(Player p, Club c, League l) {
	System.out.println("Please input new name");
	String newName = sc.nextLine();
	p.setName(newName);
	modifyPlayer(p, c, l);
    }

    /**
     * Menu page allows the user to assign a new height to the player. The
     * height is then passed to the player class for exception checking
     * 
     * @param p
     *            This is the player selected
     * @param c
     *            This is the club the player belongs to
     * @param l
     *            This is the league the club belongs to
     */
    public static void setNewHeight(Player p, Club c, League l) {
	System.out.println("Input Height");
	double height = sc.nextDouble();
	p.setHeight(height);
	String force = sc.nextLine();
	modifyPlayer(p, c, l);
    }

    /**
     * Menu page directs the user to view or change the club details.
     * 
     * @param c
     *            This is the club which is being modified
     * @param l
     *            This is the league the club belongs to
     */
    private static void modifyClub(Club c, League l)

    {
	System.out.println("");
	System.out.println("Modifying - " + c.getClubName());
	System.out.println("1. Get club statistics");
	System.out.println("2. Change club name");
	System.out.println("3. Change Stadium");
	System.out.println("4. Return to club");

	int option = 0;

	String input = sc.nextLine();
	option = new Integer(input);
	{
	    switch (option)
	    {
	    case 1:
		if (c.squadSize() > 0)// If no player is in the squad yet then
				      // club details will not show
		{
		    System.out.println(c.clubDetails());
		}
		else
		{
		    System.out
			    .println("Unable to display statistics as no players in squad.  Please add new players first.");
		    modifyClub(c, l);
		}
		modifyClub(c, l);
		break;
	    case 2:
		setClubName(c, l);
		break;
	    case 3:
		setStadium(c, l);
		break;
	    case 4:
		modifyClubsWithinLeagueMenu(c, l);
		break;
	    default:
		System.out.println("Wrong number entered");
		modifyClub(c, l);
		break;
	    }
	}
    }

    /**
     * Method allows the user to change the clubs name and then passes it to the
     * club class for exception checking
     * 
     * @param c
     *            This is the club which is being modified
     * @param l
     *            This is the league the club belongs to
     */
    public static void setClubName(Club c, League l) {
	System.out.println("Enter new Club Name");
	String newClubName = sc.nextLine();
	c.setClubName(newClubName);
	System.out.println("Clubs new name is - " + c.getClubName());
	modifyClub(c, l);
    }

    /**
     * Method allows the user to change the stadiums name
     * 
     * @param c
     *            This is the club which is being modified
     * @param l
     *            This is the league the club belongs to
     */
    public static void setStadium(Club c, League l) {
	System.out.println("Enter new Stadium Name");
	String newStadiumName = sc.nextLine();
	c.setClubName(newStadiumName);
	modifyClub(c, l);
    }

    /**
     * Method allows the user to assign a new date of birth to the player then
     * passes it to the player class for exception checking
     * 
     * @param p
     *            This is the player selected
     * @param c
     *            This is the club the player belongs to
     * @param l
     *            This is the league the club belongs to
     */
    private static void setNewDateOfBirth(Player p, Club c, League l) {
	System.out.println("Input day of birth");
	int x = sc.nextInt();
	System.out.println("Input month of birth");
	int y = sc.nextInt();
	System.out.println("Input Year of birth");
	int z = sc.nextInt();
	p.setDOB(p, x, y, z);
	String force = sc.nextLine();
	modifyPlayer(p, c, l);
    }

    /**
     * Menu page allows a user to make changes to the match
     * 
     * @param m
     *            This is the match that has been selected
     * 
     * @param l
     *            This is the league the match belongs to
     */
    private static void modifyMatch(Match m, League l) {
	System.out.println("");
	System.out.println("Modifying - " + m);
	System.out.println("1. Pick team for match");
	System.out.println("2. See or change date and location");
	System.out.println("3. Confirm goal scorers and final result");
	System.out.println("4. Return to league");

	int option = 0;

	String input = sc.nextLine();
	option = new Integer(input);
	{
	    switch (option)
	    {

	    case 1:
		if (m.getHTeamSize() == 11 && m.getATeamSize() == 11)// If
								     // statement
								     // stops
								     // this
								     // menu
								     // being
								     // accessed
								     // if
								     // there
								     // are
								     // already
								     // 11
								     // teams
								     // in
								     // the
								     // league
		{
		    System.out
			    .println("Cannot add more players. 11 have already been selected");
		    modifyMatch(m, l);
		}
		else
		{
		    setTeam(m, l);
		}
		break;
	    case 2:// Calls on Match methods to create the new date and location
		System.out.println("Input day of match");
		int date = sc.nextInt();
		System.out.println("Input month of match");
		int month = sc.nextInt();
		System.out.println("Input Year of match");
		int year = sc.nextInt();
		System.out.println("Input hour of match");
		int hour = sc.nextInt();
		System.out.println("Input minute of match");
		int minute = sc.nextInt();
		if (year < 100)
		{
		    year = year + 2000;
		}
		m.setFixtureDate(date, month, year, hour, minute, l, m);
		System.out.println("Input new location");
		String force = sc.nextLine();
		String location = sc.nextLine();
		m.setLocation(location);
		System.out.println("Fixture is now at - " + m.getLocation());
		modifyMatch(m, l);
		break;
	    case 3:
		setGoalScorers(m, l);
		m.confirmMatchResult(m, l);
		break;
	    case 4:
		modifyLeague(l);
		break;
	    default:
		System.out.println("Wrong number entered");
		modifyMatch(m, l);
		break;
	    }
	}
    }

    /**
     * Menu page allows the user to view the details of a past fixture
     * 
     * @param m
     *            This is the match that has been selected
     * 
     * @param l
     *            This is the league the match belongs to
     */
    private static void viewFinishedMatch(Match m, League l) {
	System.out.println("");
	System.out.println("Viewing - " + m);
	System.out.println("1. See who played");
	System.out.println("2. See who scored");
	System.out.println("3. Return to match selection");

	int option = 0;

	String input = sc.nextLine();
	option = new Integer(input);
	{
	    switch (option)
	    {

	    case 1:
		System.out.println(m.showMatchPlayers(1, m, l));// Displaying
								// home team
		System.out.println(m.showMatchPlayers(2, m, l));// Displaying
								// away team
		viewFinishedMatch(m, l);
		break;
	    case 2:
		System.out.println(m.displayScorersfromMatch());
		viewFinishedMatch(m, l);
		break;
	    case 3:
		matchSubMenu(l);
		break;
	    default:
		System.out.println("Wrong number entered");
		viewFinishedMatch(m, l);
		break;
	    }
	}
    }

    /**
     * Menu allows user to bring up a list of home and away players and select
     * them for inclusion in the team for this match
     * 
     * @param m
     *            This is the match that has been selected
     * 
     * @param l
     *            This is the league the match belongs to
     */
    private static void setTeam(Match m, League l) {
	if (m.returnMatchPlayed() == false)
	{
	    Club home = m.getHomeTeam();
	    Club away = m.getAwayTeam();
	    System.out.println("");
	    System.out.println("1. Add player to " + home.getClubName()
		    + " team");
	    System.out.println("2. Add player to " + away.getClubName()
		    + " team");
	    System.out.println("3. Return");
	    int option = 0;

	    String input = sc.nextLine();
	    option = new Integer(input);
	    {
		switch (option)
		{
		case 1:
		    if (m.getHTeamSize() < 11)// Checks the team is not
					      // exceeding 11 players
		    {
			while (m.getHTeamSize() < 11)// loops until 11 players
						     // have been added
			{
			    System.out.println("List of selectable players");
			    System.out.println(m.getHTeamSize()
				    + " players already picked out of 11");
			    System.out.println(home.showPlayers());
			    System.out
				    .println("Type player number to select player");
			    System.out.println("Type 0 to return");
			    String input1 = sc.nextLine();
			    int option1 = new Integer(input1);
			    if (option1 > home.getSquadSize())// Checks user has
							      // not inputted
							      // too high a
							      // number
			    {
				System.out.println("Wrong number selected");
				setTeam(m, l);
			    }
			    option1--;// Minuses 1 to match the ArrayList
			    if (option1 == -1)// If user has typed 0 this
					      // takes them out the loop
			    {
				setTeam(m, l);
				break;

			    }
			    else if (option1 > -1)
			    {
				Player ph = home.getSquadPlayer(option1, m);// Selects
									    // player
									    // from
									    // squad
									    // ArrayList
									    // in
									    // Club
				m.selectHomePlayerForTeam(ph);

			    }
			}
			System.out.println("11 players selected for "
				+ home.getClubName());
			modifyMatch(m, l);
		    }
		    else
			System.out
				.println("Cannot add more players. 11 have already been selected");
		    modifyMatch(m, l);
		    break;

		case 2:
		    if (m.getATeamSize() < 11) // Checks the team is not
		    // exceeding 11 players
		    {
			while (m.getATeamSize() < 11)// loops until 11 players
			// have been added
			{
			    System.out.println("List of selectable players");
			    System.out.println(m.getATeamSize()
				    + " players already picked out of 11");
			    System.out.println(away.showPlayers());
			    System.out
				    .println("Type player number to select player");
			    System.out.println("Type 0 to return");
			    String input1 = sc.nextLine();
			    int option1 = new Integer(input1);
			    if (option1 > away.getSquadSize())// Checks user has
							      // not inputted
							      // too high a
							      // number
			    {
				System.out.println("Wrong number selected");
				setTeam(m, l);
			    }
			    option1--;// Minuses 1 to match the ArrayList
			    if (option1 == -1)// If user has typed 0 this
			    // takes them out the loop
			    {
				setTeam(m, l);
				break;
			    }
			    else if (option1 > -1)
			    {
				Player ph = away.getSquadPlayer(option1, m);// Selects
				// player
				// from
				// squad
				// ArrayList
				// in
				// Club
				m.selectAwayPlayerForTeam(ph);

			    }
			}
			System.out.println("11 players selected for "
				+ away.getClubName());
			modifyMatch(m, l);
		    }
		    else
			System.out
				.println("Cannot add more players. 11 have already been selected");
		    modifyMatch(m, l);
		    break;
		case 3:
		    modifyMatch(m, l);
		    break;
		default:
		    System.out.println("Wrong number entered");
		    setTeam(m, l);

		    break;
		}
	    }
	}
	else
	{
	    System.out
		    .println("Cannot modify.  This match has already been played.");
	}
    }

    /**
     * Menu allows user to bring up a list of home and away players and select
     * which of them scored in this match
     * 
     * @param m
     *            This is the match that has been selected
     * 
     * @param l
     *            This is the league the match belongs to
     */
    private static void setGoalScorers(Match m, League l) {
	if ((m.getHTeamSize() == 11) && (m.getATeamSize() == 11))
	{
	    Club home = m.getHomeTeam();
	    Club away = m.getAwayTeam();
	    System.out.println("");
	    System.out.println(m);
	    System.out.println("Pick goalscorers");
	    System.out.println("1. Goal scorer from " + home.getClubName());
	    System.out.println("2. Goal scorer from " + away.getClubName());
	    System.out.println("3. Confirm result");
	    int option = 0;

	    String input = sc.nextLine();
	    option = new Integer(input);
	    {
		switch (option)
		{
		case 1:
		    int option1 = 1;
		    while (option1 != 0)
		    {
			System.out.println("Team list");
			System.out.println(m.showMatchPlayers(1, m, l));// Displays
									// home
									// team
			System.out.println("Type player number of goal scorer");
			System.out.println("Type 0 to go back to Match menu");
			String input1 = sc.nextLine();
			option1 = new Integer(input1);
			if (option1 > 11)// Checks the user has not selected too
					 // high a number
			{
			    System.out.println("Wrong number selected");
			    setGoalScorers(m, l);
			}
			option1--;// Minuses 1 from the option so the users
				  // choice matches the ArrayList
			if (option1 > -1)
			{
			    Player ph = m.getHTeamPlayerObject(option1, m);
			    System.out.println("How many goals did "
				    + ph.getName() + " score?");
			    int gScored;
			    gScored = sc.nextInt();
			    m.selectGoalsScorers(ph, gScored);// Adds goals to
							      // player, match
							      // and club goal
							      // tally
			    String force = sc.nextLine();
			}
			else
			// If user typed 0 to return to previous menu
			{
			    setGoalScorers(m, l);
			    break;
			}
			setGoalScorers(m, l);
		    }
		    break;

		case 2:
		    int option2 = 1;
		    while (option2 != 0)
		    {
			System.out.println("Team list");
			System.out.println(m.showMatchPlayers(2, m, l));// Displays
									// away
									// team
			System.out.println("Type player number of goal scorer");
			System.out.println("Type 0 to go back to Match menu");
			String input1 = sc.nextLine();
			option1 = new Integer(input1);
			if (option1 > 11)// Checks the user has not selected too
					 // high a number
			{
			    System.out.println("Wrong number selected");
			    setGoalScorers(m, l);
			}
			option1--;
			if (option1 > -1)// Minuses 1 from the option so the
					 // users
			// choice matches the ArrayList
			{
			    Player ph = m.getATeamPlayerObject(option1, m);
			    System.out.println("How many goals did "
				    + ph.getName() + " score?");
			    int gScored;
			    gScored = sc.nextInt();
			    m.selectGoalsScorers(ph, gScored);// Adds goals to
							      // player, match
							      // and club goal
							      // tally
			    String force = sc.nextLine();
			}
			else
			{
			    setGoalScorers(m, l);
			}
			setGoalScorers(m, l);
		    }
		case 3:
		    m.confirmMatchResult(m, l);// After confirming match no more
					       // editing is possible
		    matchSubMenu(l);
		    break;

		default:
		    System.out.println("Wrong number entered");
		    setGoalScorers(m, l);
		    break;
		}
	    }
	}
	else
	{
	    System.out.println("");
	    System.out.println(m.getHomeTeam().getClubName() + " have "
		    + m.getHTeamSize() + " players");
	    System.out.println(m.getAwayTeam().getClubName() + " have "
		    + m.getATeamSize() + " players");
	    System.out.println("Please add more players first");
	    modifyMatch(m, l);
	}

    }

    /**
     * Menu page allows the user to modify or view options for the league. It
     * also lets the user write the details of the league to a text file
     * 
     * @param l
     *            This is the league that has been accessed
     */
    private static void modifyLeague(League l) {
	System.out.println("");
	System.out.println("Modifying - " + l.getLeagueName());
	System.out.println("1. View league details");
	System.out.println("2. Add clubs");
	System.out.println("3. View clubs");
	System.out.println("4. Fixtures/Results");
	System.out.println("5. Write league to file");
	System.out.println("6. Return to main menu");

	int option = 0;

	String input = sc.nextLine();
	option = new Integer(input);
	{
	    switch (option)
	    {
	    case 1:
		editLeagueDetails(l);
		break;
	    case 2:
		createAClubInALeague(l);
		break;
	    case 3:
		selectClubsInLeague(l);
		break;
	    case 4:
		matchSubMenu(l);
		break;
	    case 5:
		System.out
			.println("Please type name you wish the file to be called");
		String writeFName = sc.nextLine();

		try
		{
		    toFile(writeFName, l);
		    System.out.println("File created");
		    modifyLeague(l);
		}
		catch (IOException e)
		{
		    System.out.println("Unable to create file");
		    e.printStackTrace();
		}
		leagueSubMenu();
		break;
	    case 6:
		leagueSubMenu();
		break;
	    default:
		modifyLeague(l);
		System.out.println("Wrong number entered");

		break;
	    }
	}
    }

    /**
     * Method allows the user to change the name of the league
     * 
     * @param l
     *            This is the league that has been accessed
     */
    public static void setNewLeagueName(League l) {
	System.out.println("");
	System.out.println("Enter new League Name");
	String newLeagueName = sc.nextLine();
	l.setLeagueName(newLeagueName);
	System.out.println("Leagues new name is - " + l.getLeagueName());
	editLeagueDetails(l);
    }

    /**
     * Menu page allows the user view the statistics of the league
     * 
     * @param l
     *            This is the league that has been accessed
     */
    private static void editLeagueDetails(League l) {
	if (l.getLeagueCurrentSize() > 0)// Tests if clubs are actually
					 // contained within the league
	{
	    System.out.println("");
	    System.out.println("Modifying - " + l.getLeagueName());
	    System.out.println("1. View league table");
	    System.out.println("2. View top team in the league");
	    System.out.println("3. View top scorer");
	    System.out.println("4. Change league Name");
	    System.out.println("5. Return to league");

	    int option = 0;

	    String input = sc.nextLine();
	    option = new Integer(input);
	    {
		switch (option)
		{
		case 1:
		    System.out.println(l.getLeagueTable());
		    editLeagueDetails(l);
		    break;
		case 2:
		    System.out.println("Top team is "
			    + l.getTopTeam().getClubName());
		    editLeagueDetails(l);
		    break;
		case 3:
		    System.out.println(l.displayTopScorer());
		    editLeagueDetails(l);
		    break;
		case 4:
		    setNewLeagueName(l);
		    break;
		case 5:
		    modifyLeague(l);
		    break;
		default:
		    System.out.println("Wrong number entered");
		    editLeagueDetails(l);
		    break;
		}
	    }
	}
	else
	{
	    System.out
		    .println("No clubs yet added to league. Please create club first");
	    modifyLeague(l);
	}
    }

    /**
     * This method creates all possible fixtures for the league after it is
     * imported from the text file. A unique date is then applied to the match.
     * 
     * @param l
     *            This is the league the fixtures will be added to
     */
    public static void createFixtures(League l) {

	int home = 0;
	int away = 0;
	int date = 1;
	while (home < l.getLeagueCurrentSize())// While loop ensures that the
					       // first team is set against
					       // every other team at home, and
					       // then the next team is selected
					       // as the home team
	{
	    away = 0;
	    while (away < l.getLeagueCurrentSize())// While loop selects every
						   // team to play as away team
						   // against the home club
	    {
		if (home != away)
		{
		    Club h = l.getLeagueItem(home, l);
		    Club a = l.getLeagueItem(away, l);
		    Match M1 = new Match(l);
		    M1.setMatch(M1, h, a, l);
		    M1.setFixtureDate(date, 1, 2013, 15, 0, l, M1);
		    away++;
		    date++;
		}
		else
		{
		    away++;
		}
	    }
	    home++;
	}
    }

    /**
     * This method is called upon through the console IO from matchSubMenu to
     * allow a user to import multiple results from a text file
     * 
     * @param fname
     *            This is the path to the file
     * @param l
     *            This is the league the match belongs to
     * @throws IOException
     * @throws FileNotFoundException
     */
    public static void inputMatch(String fname, League l) {
	BufferedReader reader = null;
	try
	{

	    try
	    {
		reader = new BufferedReader(new FileReader(fname));
	    }
	    catch (FileNotFoundException e2)
	    {
		System.out.println("Incorrect fileName. File not found");
		matchSubMenu(l);
	    }

	    String readLine = null;
	    try
	    {
		readLine = reader.readLine();// Entering match number
	    }
	    catch (IOException e)
	    {
		System.out
			.println("Error occured processing match number, check file");
	    }
	    int minus = 1;// User will select the match based on its location on
			  // the
			  // printed fixture list (i.e. printed list will show
			  // match
			  // 1 but it is contained in the ArrayList at 0.
			  // Therefore
			  // minus ensures the correct match is selected
	    while (readLine != null)
	    {
		String[] matchNo = readLine.split("\\|");
		if (!(matchNo[0].trim().contains("MatchNo")))// Tests that user
							     // has
							     // formatted
							     // correctly
		{
		    System.out
			    .println("File has been structed incorrectly at line "
				    + readLine
				    + "\n"
				    + "This needs to display the text ''MatchNo', followed by the match number i.e. Match number|3");
		    matchSubMenu(l);
		}
		int matchChoice = Integer.parseInt(matchNo[1].trim());
		matchChoice -= minus;
		if (matchChoice > l.getFixtureSize() || matchChoice < 0)// Tests
									// that
									// the
									// number
									// of
									// the
									// match
									// selected
									// is
									// valid
		{
		    System.out
			    .println("File has been structured incorrectly at line -"
				    + readLine
				    + "\n"
				    + ". This line needs to be a valid match number - check select match");
		    matchSubMenu(l);
		}
		Match m = l.getFixtureItem(matchChoice, l);
		Club home = m.getHomeTeam();
		Club away = m.getAwayTeam();

		String readSet = null;
		try
		{
		    readSet = reader.readLine(); // Entering location and date
						 // details
		}
		catch (IOException e)
		{
		    System.out
			    .println("Error occured processing date and location following "
				    + readLine + ". Check file");
		}

		String[] set = readSet.split("\\|");
		if (set.length != 6)// Tests that user has formatted correctly
		{
		    System.out
			    .println("File has been structured incorrectly at line -"
				    + readSet
				    + "\n"
				    + ". This line needs to be location, date, month, year, hour, minute ie. St James|12|1|2013|15|00");
		    matchSubMenu(l);
		}
		String location = (set[0].trim());
		Integer date = new Integer(set[1].trim());
		Integer month = new Integer(set[2].trim());
		Integer year = new Integer(set[3].trim());
		Integer hour = new Integer(set[4].trim());
		Integer minute = new Integer(set[5].trim());

		m.setFixtureDate(date, month, year, hour, minute, l, m);
		m.setLocation(location);

		int hIndex = 0;
		while (hIndex < 11)
		{
		    String readChooseHPlayer = null;
		    try
		    {
			readChooseHPlayer = reader.readLine(); // Selecting home
							       // players
		    }
		    catch (IOException e)
		    {
			System.out
				.println("Error occured selecting home players for "
					+ home.getClubName() + ". Check file");
		    }
		    String[] chooseHPlayer = readChooseHPlayer.split("\\|");
		    if (!(chooseHPlayer[0].trim().contains("HomePlayer")))// Tests
									  // that
									  // user
									  // has
									  // formatted
									  // correctly
		    {
			System.out
				.println("File has been structed incorrectly during selecting players for club "
					+ home.getClubName()
					+ "\n"
					+ "This needs to display the text 'Home Player' (1-11) followed by their squad number i.e Home player 7|9");
			matchSubMenu(l);
		    }
		    Integer playerChoice = Integer.parseInt(chooseHPlayer[1]
			    .trim());
		    if (playerChoice > home.getSquadSize())// Tests that the
							   // number
							   // entered by the
							   // user is
							   // not higher than
							   // the
							   // size of the squad
		    {
			System.out
				.println("File has been structured incorrectly during selecting players for club "
					+ home.getClubName()
					+ "\n"
					+ ". This line needs to be a valid player number - check player list");
			matchSubMenu(l);
		    }
		    playerChoice--;// Matches user choice with arrayList
		    Player player = home.getSquadPlayer(playerChoice, m);
		    m.selectHomePlayerForTeam(player);
		    hIndex++;
		}

		int aIndex = 0;
		while (aIndex < 11)
		{
		    String readChooseAPlayer = null;
		    try
		    {
			readChooseAPlayer = reader.readLine(); // Selecting away
							       // players
		    }
		    catch (IOException e)
		    {
			{
			    System.out
				    .println("Error occured selecting away team players for"
					    + away.getClubName()
					    + ". Check file");
			}
		    }
		    String[] chooseAPlayer = readChooseAPlayer.split("\\|");
		    if (!(chooseAPlayer[0].trim().contains("AwayPlayer")))// Tests
		    // that
		    // user
		    // has
		    // formatted
		    // correctly
		    {
			System.out
				.println("File has been structed incorrectly during selecting players for club "
					+ away.getClubName()
					+ "\n"
					+ "This needs to display the text 'Away Player' (1-11) followed by their squad number i.e Home player 7|9");
			matchSubMenu(l);
		    }
		    Integer playerChoice = Integer.parseInt(chooseAPlayer[1]
			    .trim());
		    if (playerChoice > away.getSquadSize())// Tests that the
							   // number
		    // entered by the user is
		    // not higher than the
		    // size of the squad
		    {
			System.out
				.println("File has been structed incorrectly during selecting players for club "
					+ away.getClubName()
					+ "\n"
					+ "This needs to display the text 'Away Player' (1-11) followed by their squad number i.e Home player 7|9");
			matchSubMenu(l);
		    }
		    playerChoice--;// Matches user choice with arrayList
		    Player player = away.getSquadPlayer(playerChoice, m);
		    m.selectAwayPlayerForTeam(player);
		    aIndex++;
		}

		String readNOGH = null;
		try
		{
		    readNOGH = reader.readLine(); // Entering number of home
						  // team
						  // scorers
		}
		catch (IOException e)
		{
		    System.out
			    .println("Error occured selecting home team scorers for "
				    + home.getClubName() + ". Check file");
		}
		String[] numberOfGoalsHome = readNOGH.split("\\|");

		if (!(numberOfGoalsHome[0].contains("No of home team scorers")))// Tests
		// that
		// user
		// has
		// formatted
		// correctly
		{
		    System.out
			    .println("File has been structed incorrectly during setting the number of home goals for "
				    + home.getClubName()
				    + "\n"
				    + "This needs to display the text 'No of home team scorers' followed by the number i.e - Number of home team scorers|3");
		    matchSubMenu(l);
		}
		int goalsHome = Integer.parseInt(numberOfGoalsHome[1].trim());

		int indexH = 0;
		while (indexH < goalsHome)
		{
		    String readcHS = null;
		    try
		    {
			readcHS = reader.readLine(); // Selecting the home team
						     // scorers
		    }
		    catch (IOException e)
		    {

		    }
		    String[] cHS = readcHS.split("\\|");
		    Integer hWhoScored = new Integer(cHS[1].trim());
		    Integer hGoalsPScored = new Integer(cHS[2].trim());
		    if (hWhoScored > 11)// Tests that the user has not entered a
					// player number higher than 11
		    {
			System.out
				.println("File has been structured incorrectly during selecting goalscorers for -"
					+ home.getClubName()
					+ "\n"
					+ ". This line needs to be under 11 and define the player who scored");
			matchSubMenu(l);
		    }
		    hWhoScored--;// Matches user choice with arrayList
		    Player hPlayer = m.getHTeamPlayerObject(hWhoScored, m);
		    m.selectGoalsScorers(hPlayer, hGoalsPScored);
		    indexH++;
		}

		String readAOGH = null;
		try
		{
		    readAOGH = reader.readLine();// Entering number of home team
		    // scorers
		}
		catch (IOException e)
		{
		    System.out
			    .println("Error occured selecting away team scorers for"
				    + away.getClubName() + ". Check file");
		}
		String[] aGS = readAOGH.split("\\|");
		if (!(aGS[0].trim().contains("No of away team scorers")))// Tests
		// that
		// user
		// has
		// formatted
		// correctly
		{
		    {
			System.out
				.println("File has been structed incorrectly during setting the number of away goals for "
					+ away.getClubName()
					+ "\n"
					+ "This needs to display the text 'No of away team scorers' followed by the number i.e - Number of home team scorers|3");
			matchSubMenu(l);
		    }
		}
		int goalsAway = Integer.parseInt(aGS[1].trim());

		// Selecting away scorers

		int indexA = 0;

		while (indexA < goalsAway)
		{
		    String readCAS = null;
		    try
		    {
			readCAS = reader.readLine();// Selecting the away team
			// scorers
		    }
		    catch (IOException e)
		    {
			System.out
				.println("Error occured selecting away team scorers for "
					+ away.getClubName() + ". Check file");
			matchSubMenu(l);
		    }
		    String[] cAS = readCAS.split("\\|");
		    Integer aWhoScored = new Integer(cAS[1].trim());
		    Integer aGoalsPScored = new Integer(cAS[2].trim());
		    if (aWhoScored > 11)// Tests that the user has not entered a
		    // player number higher than 11
		    {
			System.out
				.println("File has been structured incorrectly during selecting goalscorers for -"
					+ away.getClubName()
					+ "\n"
					+ ". This line needs to be under 11 and define the player who scored");
			matchSubMenu(l);
		    }
		    aWhoScored--;
		    Player aPlayer = m.getATeamPlayerObject(aWhoScored, m);
		    m.selectGoalsScorers(aPlayer, aGoalsPScored);
		    indexA++;
		}
		m.confirmMatchResult(m, l);
		minus++;// Minus increase by one everytime because after a match
			// is
			// completed it is removed from the Arraylist, so the
			// minus
			// ensures the correct match is selected
		try
		{
		    readLine = reader.readLine();
		}
		catch (IOException e)
		{
		    System.out.println("Error occured after "
			    + away.getClubName() + " scorers. Check file");
		}
	    }
	}
	catch (NullPointerException e)
	{
	    System.out
		    .println("Null pointer exception found. Please check file");
	}

    }

    /**
     * This method is called upon through the console IO from Main to allow a
     * user to import a complete league of clubs and players from a text file
     * 
     * @param fname
     *            This is the path to the file
     * @throws IOException
     * @throws FileNotFoundException
     */
    public static void inputLeague(String fname) {
	ArrayList<League> leagues1 = new ArrayList<League>();
	try
	{
	    BufferedReader reader = null;
	    try
	    {
		reader = new BufferedReader(new FileReader(fname));
	    }
	    catch (FileNotFoundException e2)
	    {
		System.out.println("File not found. Check file name");
		leagueSubMenu();

	    }
	    String line = null;
	    try
	    {
		line = reader.readLine();// Entering league and size of league
					 // details
	    }
	    catch (IOException e)
	    {
		System.out
			.println("Error occured processing league name and max teams, check file");
		leagueSubMenu();
	    }
	    if (line.equals(null))// Checks that the file contains data
	    {
		System.out
			.println("No text found, please recheck file. No leagues created.");
		leagueSubMenu();
	    }
	    while (line != null)
	    {
		int index = 0;
		String[] leagues = line.split("\\|");
		int size = leagues.length;
		if (!(size == 2))// Checks the formatting of the line
		{
		    System.out
			    .println("File has been structured incorrectly at line - "
				    + line
				    + "."
				    + "\n"
				    + "Should be set out league name followed by the maximum teams allowed i.e - Premiership|20."
				    + "\n" + "League has not been created");
		    leagueSubMenu();
		}

		Integer max = new Integer(leagues[1].trim());
		League l = new League(leagues[0].trim(), max);

		l.setMaxNoOfTeams(max);
		leagueList.add(l);
		leagues1.add(l);
		String noOfClubs = null;
		try
		{
		    noOfClubs = reader.readLine();// Reads how many clubs are in
						  // the
						  // league
		}
		catch (IOException e)
		{
		    System.out.println("Error occured following line " + line
			    + ", check file");
		}
		String[] clubLine = noOfClubs.split("\\|");
		if (!(clubLine[0].trim().contains("Number of Clubs"))
			|| (clubLine.length != 2))// Checks the formatting of
						  // the
						  // line
		{
		    System.out
			    .println("File has been structed incorrectly at line - "
				    + line
				    + "\n"
				    + "This needs to display the text 'Number of clubs', followed by the number of clubs in the league i.e. Number of clubs|3");
		    leagueSubMenu();
		}
		int clubLineInt = 0;
		clubLineInt = Integer.parseInt(clubLine[1]);
		while (index != clubLineInt)
		{
		    String clubName = null;
		    try
		    {
			clubName = reader.readLine();// Reads the clubs name
		    }
		    catch (IOException e)
		    {
			System.out
				.println("Error occured processing number of clubs in the league, check file");
		    }
		    String[] clubs = clubName.split("\\|");// Checks the
							   // formatting
							   // of the line
		    if (clubs.length != 2)
		    {
			System.out
				.println("File has been structured incorrectly at line - "
					+ clubName
					+ ". Should be set out club name followed by stadium name ie. - Newcastle|St James."
					+ "\n"
					+ "League has been created, as have all clubs and players listed before this in the text file");
			leagueSubMenu();
		    }

		    Club c = new Club(clubs[0].trim(), clubs[1].trim());
		    l.addLeagueClub(c);
		    String noOfPlayers = null;
		    try
		    {
			noOfPlayers = reader.readLine();// Reads the number of
							// players in the squad
		    }
		    catch (IOException e)
		    {
			System.out
				.println("Error occured processing number of players in club "
					+ c.getClubName() + ", check file");
		    }
		    String[] playerLine = noOfPlayers.split("\\|");
		    if (!(playerLine[0].trim().contains("Number of Players"))
			    || (playerLine.length != 2))// Checks the formatting
							// of
							// the line
		    {
			System.out
				.println("File has been structed incorrectly at line - "
					+ noOfPlayers
					+ "\n"
					+ "This needs to display the text 'Number of players', followed by the number of players in the club i.e. Number of Players|14");
			leagueSubMenu();
		    }
		    int playerLineInt = 0;
		    playerLineInt = Integer.parseInt(playerLine[1]);

		    int index1 = 0;

		    while (index1 != playerLineInt)
		    {

			String playerName = null;
			try
			{
			    playerName = reader.readLine();// Reads the details
							   // of
							   // the players
			}
			catch (IOException e)
			{
			    System.out
				    .println("Error occured processing the players in club "
					    + c.getClubName() + ", check file");
			}
			String[] players = playerName.split("\\|");// Checks the
								   // formatting
								   // of
								   // the line
			size = players.length;
			if (!(size == 5))
			{
			    System.out
				    .println("File has been structured incorrectly at line - "
					    + playerName
					    + ". Should be set out player name followed by height, date, month, year  ie. - Johnny|2|12|7|1984. "
					    + "\n"
					    + " League has been created, as have all clubs and players listed before this in the text file");
			    leagueSubMenu();
			}

			Player p = new Player(players[0].trim());
			c.addClubPlayers(p);
			Double f = new Double(players[1].trim());

			Integer date = new Integer(players[2].trim());

			Integer month = new Integer(players[3].trim());

			Integer year = new Integer(players[4].trim());

			p.setDOB(p, date, month, year);

			p.setHeight(f);
			index1++;
		    }
		    index++;

		}
		createFixtures(l);// Creates the fixtures in the league
		leagueSubMenu();
	    }
	}

	catch (NullPointerException e)
	{
	    System.out
		    .println("Null pointer exception found. Please check file");
	}

    }

    /**
     * Method writes the league details, club details, players in the club, and
     * writes the league table to a text file
     * 
     * @param fname
     *            This is the filename the user types in. The program will then
     *            create a new file of this name
     * @throws IOException
     */
    public static void toFile(String fname, League l) throws IOException {
	FileWriter out = new FileWriter(fname, false);
	out.write(l.getLeagueName());
	out.write("|");
	out.write(l.getMaxTeams() + "");
	out.write("\n");
	out.write(l.getLeagueCurrentSize() + "");
	out.write("\n");
	if (l.getLeagueCurrentSize() > 0)
	{
	    for (Club c : l.getLeagueClub())
	    {
		out.write(c.getClubName());
		out.write("|");
		out.write(c.getStadiumName());
		out.write("\n");
		out.write(c.squadSize() + "");
		out.write("\n");
		if (c.squadSize() > 0)
		{
		    for (Player p : c.getSquad())
		    {
			out.write(p.getName());
			out.write("|");
			out.write(p.getHeight() + "");
			out.write("|");
			out.write(p.getDOB() + "");
			out.write("\n");
		    }
		}
	    }
	    out.write(l.getLeagueTable());
	    out.write("\n");

	}
	out.close();
    }
}
