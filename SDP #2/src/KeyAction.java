/* Michelle Zhang
 * ICS3U1 - 05
 * 
 * This program handles the movement of the character in the Super Mario Game. 
 * It ensures that the character moves only in the allowed directions, depending
 * on the keys the character presses.
 */

//import
import java.awt.event.ActionEvent;
import javax.swing.text.TextAction;

//this class is responsible for ensuring correct character movement
public class KeyAction extends TextAction {
	private String key;
	private Character mario;
	
	//this is the constructor the the class that stores the key value and character object
	public KeyAction(String key, Character mario) {
		super(key);
		this.key = key;
		this.mario = mario;
	}

	//this is called when a key is pressed by the user
	//gets a references from the main character object (mario)
	//checks whether the key press is left, right and space
	@Override
	public void actionPerformed(ActionEvent e) {

		//check if key press is moving to the left
		if (e.getActionCommand().equals(mario.getKey()[0]) &&
				LevelFrame.boardArray[mario.getRow()] [mario.getCol()].getIcon()
				!= Icons.WALL)
				//display image of mario moving left
				mario.moveLeft(Icons.MARIO);
		//check if key press is moving right
		else if (e.getActionCommand().equals(mario.getKey()[1]) &&
				LevelFrame.boardArray[mario.getRow()] [mario.getCol()].getIcon()
				!= Icons.WALL)
				//display image of mario moving right
				mario.moveRight(Icons.MARIO);
		
		//check if key press is jumping
		else if (e.getActionCommand().equals(mario.getKey()[2]) &&
		LevelFrame.boardArray[mario.getRow()-1][mario.getCol()].getIcon() != Icons.WALL)
			//check if character is not already jumping to prevent abusing the jump feature
			//if this is true, make the character jump
			if (!mario.isJumping()) {
				mario.setJumping(true);
				mario.jump(); 
		}
	}
}