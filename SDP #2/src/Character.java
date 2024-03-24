/* Michelle Zhang
 * ICS3U1 - 05
 * 
 * This class is responsible for representing a character in a game. 
 * It extends the JLabel class and implements the ActionListener interface. 
 * The class handles various aspects of character movement, including facing 
 * different directions, jumping, falling, and collecting coins. 
 * 
 * It stores the character's icon, movement keys, and position on the game board. The class 
 * includes utility methods such as jump(), fall(), moveRight(), and moveLeft() 
 * to handle specific movement actions.
 * 
 * It also checks for collisions with walls and handles the collection of coins, updating 
 * the character's position accordingly. The class includes getter and setter methods for accessing 
 * and modifying its attributes. Additionally, it interacts with the LevelFrame 
 * class to update the coin count label and play sound effects when coins are collected.
 */

//import
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.Timer;

//this class is responsible for the character movement in the game
public class Character extends JLabel implements ActionListener {
	//this deals with the character facing different directions in the game
	private ImageIcon[] iconArray;
	//this deals with the character facing only one direction (static)
	private ImageIcon icon;
	//stores keys used for character movement
	private String[] key;
	//this tracks movement/how many pixels character is moving on the game board
	private int dX, dY; 
	//this controls how long the character is in the jump for
	private Timer jumpTimer = new Timer(25, this);
	//this counts how long the character is jumping
	private int jumpCounter;
	//represents if character is jumping
	private boolean jumping = false;
	//we must create a fall timer because every jump needs a fall
	private Timer fallTimer = new Timer(25, this);
	//holds how long character is falling for
	private int fallCounter;
	//represents if character is falling
	private boolean falling = false;
	//variable holds the number of coins the user collected
	private int CoinsCollected = 0;
	//create an instance of the levelframe class
	private LevelFrame levelFrame;
	
	//constructor for ImageIcon character
	public Character(ImageIcon icon, String[] key, LevelFrame levelFrame) {
		super();
		setIcon(icon); //set icon field
		this.key = key; //set the key field
		this.levelFrame = levelFrame;
	}
	
	//constructor for ImageIcon[] array character (dynamic image)
	public Character(ImageIcon[] iconArray, String[] key) {
		super();
		setIconArray(Icons.MARIO);
		this.key = key;

	}

	//create setters and getters for each field
	public ImageIcon[] getIconArray() {
		return iconArray;
	}

	public void setIconArray(ImageIcon[] iconArray) {
		this.iconArray = iconArray;
	}

	public ImageIcon getIcon() {
		return icon;
	}

	public void setIcon(ImageIcon icon) {
		this.icon = icon;
	}

	public String[] getKey() {
		return key;
	}

	public void setKey(String[] key) {
		this.key = key;
	}

	public int getdX() {
		return dX;
	}

	public void setdX(int dX) {
		this.dX = dX;
	}

	public int getdY() {
		return dY;
	}

	public void setdY(int dY) {
		this.dY = dY;
	}

	public Timer getJumpTimer() {
		return jumpTimer;
	}

	public void setJumpTimer(Timer jumpTimer) {
		this.jumpTimer = jumpTimer;
	}

	public int getJumpCounter() {
		return jumpCounter;
	}

	public void setJumpCounter(int jumpCounter) {
		this.jumpCounter = jumpCounter;
	}

	public boolean isJumping() {
		return jumping;
	}

	public void setJumping(boolean jumping) {
		this.jumping = jumping;
	}

	public Timer getFallTimer() {
		return fallTimer;
	}

	public void setFallTimer(Timer fallTimer) {
		this.fallTimer = fallTimer;
	}

	public int getFallCounter() {
		return fallCounter;
	}

	public void setFallCounter(int fallCounter) {
		this.fallCounter = fallCounter;
	}

	public boolean isFalling() {
		return falling;
	}

	public void setFalling(boolean falling) {
		this.falling = falling;
	}
	
	public int getCoinsCollected() {
		return CoinsCollected;
	}
	
	//utility methods
	// ---------------------------
	//make my character jump
	public void jump() {
		jumping = true;
		jumpCounter = 0; 
		jumpTimer.start(); //begin timing the jump
	} 
		
	//for every jump, there must be a fall
	public void fall() {
		falling = true;
		fallCounter = 0;
		fallTimer.start(); //begin timing the fall
	}
	
	//when the character moves in the right direction, update their position
	public void moveRight(ImageIcon[] MARIO) {
		//x - coordinate to change 5 units right at a time
		dX = 5;
		//changes the icon to mario facing right
		setIcon(MARIO[0]);
		//add this change to the x coordinate of the character
		//remember, character is saved as a 25,25 pixel file
		setBounds(getX() + dX, getY(), 25,25);	
		//check if character is off a block. if so, start the fall process
		if (!jumping && !falling && isOffBlock()) {
	        fall();
	    }
	}
	
	//when the character moves in the left direction, update their position
	public void moveLeft(ImageIcon[] MARIO) {
		//x-coordinate to change 5 units left at a time
		dX = -5;
		//changes the icon to mario facing left
		setIcon(MARIO[1]);
		setBounds(getX() + dX, getY(), 25,25);
		//check if character is off a block. if so, start the fall process
		if (!jumping && !falling && isOffBlock()) {
	        fall();
	    }
	}
	
	// check if the character is off a block
	private boolean isOffBlock() {
	    int nextRow = getRow() + 1;
	    // check if the next block below the character is empty or not a wall
	    return nextRow < LevelFrame.boardArray.length &&
	            (LevelFrame.boardArray[nextRow][getCol()].getIcon() == null ||
	            LevelFrame.boardArray[nextRow][getCol()].getIcon() != Icons.WALL);
	}
	
	//return the row to get the size of each pixel as an integer
	//there are 25 pixels to divide by 25
	public int getRow() {
		return (int)getY()/25;
	}
	
	//return the width to get the size of each pixel as an integer
	//there are 25 pixels to divide by 25
	public int getCol() {
		return (int)getX()/25;
		
	}
	
	//this method makes the coins disappear when the chracter comes in contact with it
	public void collectCoin() {
		int row = getRow();
		int col = getCol();
		if (LevelFrame.boardArray[row][col].getIcon() == Icons.COIN) {
			//remove the coin from the board when the character collects it
			//set icon is null so the coin will disappear
			LevelFrame.boardArray[row][col].setIcon(null);	
			CoinsCollected++; //increment number of coins collected
			levelFrame.updateCoinCountLabel(); //update the label 
			
			//when a coin is collected, play the collect coin sound effect
			//------------- SOURCE https://www.geeksforgeeks.org/play-audio-file-using-java/
			try {
	            //implement the audio file for the coin collection sound effect
	            AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(new File("music/collectCoin.wav").getAbsoluteFile());
	            Clip coinSound = AudioSystem.getClip();
	            coinSound.open(audioInputStream);
	            coinSound.start(); //begin actually playing the sound effect
	        } catch (Exception e) {
	            e.printStackTrace();
	        }
			//--------------- END SOURCE
		}
	}

	//this method resets the number of coins collected
	public void resetCoinsCollected() {
	    CoinsCollected = 0;
	}
	
	//this method is responsible for checking for collisions with the ground when jumping
	//and falling down
	@Override
	public void actionPerformed(ActionEvent e) {
	    //check if the character is walking off a block 
		//if they are, make the character return back to the ground once they are off the block
	    int nextRow = getRow() + 1;
	    //this condition checks if the next block below the character is empty or not a wall in order
	    //to start the falling process
	    //check if the character is walking off a block
	    boolean nextBlockEmpty = nextRow < LevelFrame.boardArray.length &&
	            (LevelFrame.boardArray[nextRow][getCol()].getIcon() == null ||
	            LevelFrame.boardArray[nextRow][getCol()].getIcon() != Icons.WALL);
	    //if the character is not jumping or falling and the next block is empty, begin falling
	    //this fixes their movement so they are not floating when not on a block
	    if (!jumping && !falling) {
	        if (nextBlockEmpty) {
	            //start falling
	            falling = true;
	            fallCounter = 0;
	            fallTimer.start();
	            return;
	          //return to the ground if not on the block anymore
	        } else {
	            int currentRow = getRow();
	            if (currentRow < LevelFrame.boardArray.length - 5 &&
	                    LevelFrame.boardArray[currentRow + 1][getCol()].getIcon() == null) {
	                setBounds(getX(), getY() + 1, 25, 25); //move character down by 1 unit
	                return;
	            }
	        }
	    }

		//check for when character is jumping up and there is a tile above them/hit a wall
		//-1 is used to make room for errors for the bounds of each label (coins, character, and tiles are all labels)
		if(jumping && dY < 0 && LevelFrame.boardArray[getRow() - 1] [getCol()].getIcon() == Icons.WALL) {
			//when this happens, we need to set jumping to false, set vertical speed to 0 to stop jumping,
			//stop the jump timer, start the fall timer 
			jumping = false;
			dY = 0;
			jumpTimer.stop();
			fall();
			return;
		}
		//this is for when character is jumping off something/falling and should hit the ground
		if(falling && dY > 0) {
			//when character is jumping up and there is a collision with the wall
			if (nextRow >= LevelFrame.boardArray.length || 
					LevelFrame.boardArray[nextRow][getCol()].getIcon() == Icons.WALL) {
				//stop the falling motion and set direction Y to zero
				falling = false;
				dY = 0;
				fallTimer.stop();
				return;	
			}
		}
		
		//when character is jumping, increment the jump counter (when it starts)
		if(jumping) {
			jumpCounter++;
			if(jumpCounter <= 10) //going up
				dY = -6;
			else if (jumpCounter <= 20) //going down
				dY = 6;
			//once it reaches the max height, stop the jump and start the fall method
			else {
				jumping = false;
				dY = 0;
				jumpTimer.stop();
				fall();
			}
		}
		
		//when character is falling, increment the fall counter 
		else if(falling) {
			fallCounter++;
			//move character down 5 units at a time
			dY = 5;
			//update location of the character
			setBounds(getX(), getY() + dY, 25,25);
			
			//
			if(LevelFrame.boardArray[getRow() + 1] [getCol()].getIcon() == Icons.WALL) {
				falling = false;
				dY = 0;
				fallTimer .stop();
			}
			
			//stop falling after it has reached the ground level 
			else if(fallCounter >= 20) { 
				falling = false;
				dY = 0;
				fallTimer.stop();
			}
			
			return; //exit method since the character is falling
		}
		
		//make the character jump in a parabola instead up and then straight down
		//chekc if there is a wall to the right of the character and if the character is moving right
		if(LevelFrame.boardArray[getRow()][getCol() + 1].getIcon() == Icons.WALL && dX > 0) {
			dX = 0; //set horizontal velocity to 0
		}
		//check if there is a wall to the left of the character and if character is moving left
		else if (LevelFrame.boardArray[getRow()][getCol()].getIcon() == Icons.WALL && dX < 0) {
			dX = 0; //set horizontal velocity to 0
		}
		//checks if character is touching the ceiling or moving upwards
		else if (LevelFrame.boardArray[getRow()][getCol()].getIcon() == Icons.WALL && dY < 0) {
			dY = 0; //set vertical velocity to 0
		}
		//character touching the ground and moving downwards
		else if (LevelFrame.boardArray[getRow() + 1][getCol()].getIcon() == Icons.WALL && dY > 0) {
			dY = 0; //set vertical velocity to 0
		}
		//make the coin disappear as if it is collected by calling the collectCoin method
		collectCoin();
		
		//update position of character for every collision 
		setBounds(getX() + dX, getY() + dY, 25,25);
	}
}