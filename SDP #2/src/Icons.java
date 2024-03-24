/* Michelle Zhang
 * ICS3U1 - 05
 * 
 * This program is responsible for defining and creating the icons used in the game. 
 * It represents the various objects and characters present in the game map. The class 
 * includes icons for walls, coins, ground tiles, and different images of Mario for 
 * different movements and directions.
 */

//import
import javax.swing.ImageIcon;

/* Michelle Zhang
 * ICS3U1 - 05
 * 
 * This class defines and creates the icons (character, blocks, objects)
 * used in the game.
 */

//create icons in the game
public class Icons {
	
	//create the wall
	public static final ImageIcon WALL = new ImageIcon("images/brick.png");
	
	//create the coins
	public static final ImageIcon COIN = new ImageIcon("images/coin.gif");
	
	//create a mario image moving right
	public static final ImageIcon MARIORIGHT = new ImageIcon("images/marioRunRight.gif");
	
	//add a mario image moving/facing left 
	public static final ImageIcon MARIOLEFT  = new ImageIcon("images/marioRunLeft.gif");
	
	//create an array to store these images. this is used for the movement of the character
	//so it can face different directions depending on how they are moving
	public static final ImageIcon[] MARIO = {MARIORIGHT, MARIOLEFT};
    
	//create an instance of Character with the iconArray
	private String[] key;
    Character character = new Character(Icons.MARIO,key);

}