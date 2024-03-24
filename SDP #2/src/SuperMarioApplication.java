/* Michelle Zhang
 * Sunday May 28
 * ICS3U1 - 05
 * 
 * Software Development Project #2 - Super Mario Game
 * 
 * DESCRIPTION: This Super Mario Game prompts the user with two options on the home frame: start the game
 * or read the rules/instructions for the game. When the user begins the game, a timer and 
 * traditional mario background music will start. The objective is to collect all the coins
 * on the map in order to complete the game. The user also has access to a menubar that can mute
 * the background music, restart the game, close the game (return back to home frame), pause and
 * unpause the game, and leaderboard. When all coins are collected, the timer will stop and the user's time will be 
 * sent and read from the highscores.txt file when they exit. The top 5 scores are saved onto this
 * leaderboard that the player can view anytime during the game. 
 * 
 * MAJOR SKILLS: Arrays, Swing GUI components, Scanner, Java File Input/Output, action listener,
 * key action, Swing Timer, Audio Input Stream, Buffered Writer,
 * 
 * ADDED FEATURES: 
 * basic: 
 * #1: player faces respective direction depending on their direction of movement
 * #2: fixed movement of character when walking off blocks (no longer floating)
 * #3: added timing 
 * #4: adding timing with tenths of seconds (more accurate)
 * #5: pause and unpause game button on the menu bar. icons of button also change for this
 * #6: menubar with mute background music, restart game, close game, and pause
 * #7: separate opening screen before game starts (homeframe). there is also a rules frame
 * #8: added background music
 * #9: added sound effects for when coins are collected
 * (SCORE is the number of coins collected)
 * 
 * advanced:
 * #1: highscore table that saves top 5 scores to external file and can be viewed and updated
 * (this score is recorded and ordered from the time it took to collect all coins on the map)
 * 
 * AREAS OF CONCERN: n/a
 * 
 * CONTRIBUTION/SOURCES: 100% contribution to assignment with help of these sources
 * https://www.educba.com/timer-in-java-swing/
 * https://www.geeksforgeeks.org/play-audio-file-using-java/
 * https://www.programiz.com/java-programming/bufferedwriter
 */


//this class is responsible for running the entire super mario game
public class SuperMarioApplication {

	public static void main(String[] args) {
		//creates an instance of the home class
		//represents the game window
		HomeFrame home = new HomeFrame();
		home.setVisible(true); //set the home frame to be visible
	}
} 
