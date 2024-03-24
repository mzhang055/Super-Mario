/* Michelle Zhang
 * ICS3U1 - 05
 * 
 * This program  allows the player to control mario within a level. The game interface includes a 
 * panel where the level is displayed, with walls and coins represented by icons. The player 
 * can control Mario's movement using keyboard inputs, including moving left or right and jumping.
 * The game keeps track of the number of coins collected and displays it on the interface.
 * There is also a timer that measures the player's elapsed time. When all coins are collected, the 
 * game stops the timer, records the player's time as a high score, and saves it to a file. The program 
 * also includes buttons for controlling the background music, restarting the game, exiting the game, 
 * and pausing/resuming the game.
 */

//import
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.util.Scanner;
import javax.swing.ActionMap;
import javax.swing.ImageIcon;
import javax.swing.InputMap;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.KeyStroke;
import javax.swing.Timer;
import javax.sound.sampled.*;
import java.io.IOException;


public class LevelFrame extends JFrame implements KeyListener {

	//stores background music clip
    private Clip backgroundMusic;
    //this 2d array represents the game board
    public static JLabel[][] boardArray = new JLabel[20][25];
    //panel for the level
    private JPanel lvl1Panel = new JPanel();
    //this label displays the coin count
    private JLabel coinCountLabel;
    //instance of character
    private Character mario;
    //tracking elapsed time
    private Timer timer;
    //elapsed time in tenth of seconds
    private int elapsedTime;
    //this label displays the time
    private JLabel timerLabel;
    //create a variable to keep track of the game's paused state
    boolean isPaused = false;

    //constructor for level frame 
    public LevelFrame(int level) {
        //set the properties of the frame
    	setSize(25 * boardArray[0].length + 100, 25 * boardArray.length);
        setLayout(null);
        setResizable(false);

        //load the level
        loadLevel(level);
        createLvlPanel();
        setupKeyBindings();
        addKeyListener(this);
        setVisible(true);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
      
        //input the mario game music from a .wav file
        //--------------- SOURCE: https://www.geeksforgeeks.org/play-audio-file-using-java/
        try {
            AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(new File("music/marioMusic.wav").getAbsoluteFile());
            backgroundMusic = AudioSystem.getClip();
            backgroundMusic.open(audioInputStream);
        } catch (UnsupportedAudioFileException | IOException | LineUnavailableException e) {
            e.printStackTrace();
        }
        
        //---------------- END OF SOURCE
        
        //start playing game music
        startBackgroundMusic();
        
        //start the timer
        timer = new Timer(100, new TimerAction());
        timer.start();
    }

    //this method begins playing the background music
    private void startBackgroundMusic() {
        if (backgroundMusic != null) {
            backgroundMusic.loop(Clip.LOOP_CONTINUOUSLY);
        }
    }

    //this method stops playing the background music
    private void stopBackgroundMusic() {
        if (backgroundMusic != null && backgroundMusic.isRunning()) {
            backgroundMusic.stop();
            backgroundMusic.close();
        }
    }

    //this method is responsible for stopping and closing different features
    //of this frame when it is closed
    @Override
    public void dispose() {
        stopBackgroundMusic();
        timer.stop();
        super.dispose();
    }

    //this method records the high score
    //------------ SOURCE: https://www.programiz.com/java-programming/bufferedwriter
    private void recordHighScore(double time) {
        try {
            BufferedWriter writer = new BufferedWriter(new FileWriter("data/highscores.txt", true));
            //write the high score to the txt file with one decimal place
            writer.write(String.format("%.1f", time));
            writer.newLine();
            writer.close();
            System.out.println("High score saved: " + time);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    //------------- END SOURCE

    //this method loads the level from the level 1 map file (.txt)
    private void loadLevel(int levelNumber) {
        try {
            Scanner inputFile = new Scanner(new File("level" + levelNumber + ".txt"));
            //read the level data from the file and assign/populate the appropriate icons to each letter
            //for every letter in the 2D array
            for (int row = 0; row < boardArray.length; row++) {
                char[] lineArray = inputFile.next().toCharArray();
                for (int col = 0; col < lineArray.length; col++) {
                    //this B represents wall tiles
                	if (lineArray[col] == 'B') {
                        boardArray[row][col] = new JLabel(Icons.WALL);   
                    } //the C represents coin tiles
                	else if (lineArray[col] == 'C') {
                        boardArray[row][col] = new JLabel(Icons.COIN);
                    } else {
                        boardArray[row][col] = new JLabel();
                    }
                }
            }
            inputFile.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    //this method creates the level panel
    private void createLvlPanel() {
        lvl1Panel.setLayout(null);
        lvl1Panel.setSize(getSize());

        //create and position the character
        mario = new Character(Icons.MARIORIGHT, new String[]{"a", "d", " "}, this);
        mario.setBounds(25, 425, 25, 25);
        lvl1Panel.add(mario);

        //add the elements from the board array to the level panel
        for (int row = 0; row < boardArray.length; row++) {
            for (int col = 0; col < boardArray[0].length; col++) {
                if (boardArray[row][col].getIcon() == Icons.WALL) {
                    boardArray[row][col].setBounds(col * 25, row * 25, 25, 25);
                    lvl1Panel.add(boardArray[row][col]);
                } else if (boardArray[row][col].getIcon() == Icons.COIN) {
                    boardArray[row][col].setBounds(col * 25, row * 25, 25, 25);
                    lvl1Panel.add(boardArray[row][col]);
                } 
            }
        }
        
        //create and position the score (coin counter) label on the GUI
        coinCountLabel = new JLabel("Score: " + mario.getCoinsCollected());
        coinCountLabel.setBounds(getSize().width - 200, 10, 100, 100);
        lvl1Panel.add(coinCountLabel);
        
        //create a timer with tenths of seconds to keep track of user's time
        //------------- SOURCE: https://www.educba.com/timer-in-java-swing/
        timerLabel = new JLabel("Time: 0.0");
        timerLabel.setBounds(50, 10, 100, 100);
        lvl1Panel.add(timerLabel);
        //-------------- END SOURCE
        
        //set the bounds for the level panel
        lvl1Panel.setBounds(0, 0, 25 * boardArray[0].length, 25 * boardArray.length);
        add(lvl1Panel);

        //set the background for the image of the frame
        ImageIcon backgroundImage = new ImageIcon("images/lvl1Background.jpeg");
        JLabel backgroundLabel = new JLabel(backgroundImage);
        backgroundLabel.setBounds(0, 0, lvl1Panel.getWidth(), lvl1Panel.getHeight());
        lvl1Panel.add(backgroundLabel);
        
        //create a menu bar on the right side of the frame
        JPanel menuPanel = new JPanel();
        menuPanel.setLayout(null);
        menuPanel.setBounds(getSize().width - 100, 0, 100, getSize().height);
        add(menuPanel);
        
        //create a button to stop background music from the game
        ImageIcon buttonIcon1 = new ImageIcon("images/mute.png");
        JButton button1 = new JButton(buttonIcon1);
        button1.setBounds(10, 50, 80, 80);
        menuPanel.add(button1);
        button1.addActionListener(new ActionListener() {
            // use action listener to close music when button is clicked
            public void actionPerformed(ActionEvent e) {
                stopBackgroundMusic(); //stop the music
                lvl1Panel.requestFocusInWindow(); //focus program back to the game so user can continue
            }
        });
        
        //create button to restart the game
        ImageIcon buttonIcon2 = new ImageIcon("images/restart.png");
        JButton button2 = new JButton(buttonIcon2);
        button2.setBounds(10, 150, 80, 80);
        menuPanel.add(button2);
        button2.addActionListener(new ActionListener() {
            // use action listener to close music when button is clicked
            public void actionPerformed(ActionEvent e) {
            LevelFrame levels = new LevelFrame(1); //open new level frame when button is clicked
            dispose(); //close current frame
            	
            }
        });
        
        //create button to exit the game and return to home frame
        ImageIcon buttonIcon3 = new ImageIcon("images/close.png");
        JButton button3 = new JButton(buttonIcon3);
        button3.setBounds(10, 250, 80, 80);
        menuPanel.add(button3);
        button3.addActionListener(new ActionListener() {
            // use action listener to close music when button is clicked
            public void actionPerformed(ActionEvent e) {
            HomeFrame home = new HomeFrame(); //open home frame when button is clicked
            home.setVisible(true);
            dispose(); //close current frame
            	
            }
        });
        
        //create a button to pause/resume the game
        ImageIcon buttonIcon4 = new ImageIcon("images/pause.png");
        JButton button4 = new JButton(buttonIcon4);
        button4.setBounds(10, 350, 80, 80);
        menuPanel.add(button4);
        //use action listener to change icon of button and stop timer when button is clicked
        button4.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                //when game is not paused yet, stop timer and set paused to true
            	if (!isPaused) {
                    timer.stop();
                    isPaused = true;
                   
                    //assign an image of pause onto the button
                    button4.setIcon(new ImageIcon("images/pause.png")); //change button icon to represent resume action
                } 
            	//when game is unpaused, resume game
            	else {
                    timer.start(); //begin timer
                    isPaused = false; //set pause to false
                    button4.setIcon(new ImageIcon("images/unpause.png")); //change button icon to represent pause action
                    lvl1Panel.requestFocusInWindow(); //focus program back to the game so the user can continue
                }
            }
        }); 
    }

    //this method updates the number of coins collected by the user and stops the timer
    //once all coins are collected
    public void updateCoinCountLabel() {
        coinCountLabel.setText("Coins: " + mario.getCoinsCollected());

        //check if all coins are collected
        if (mario.getCoinsCollected() == 4) {
            double timeInSeconds = elapsedTime / 10.0; //convert elapsed time to seconds with tenths
            recordHighScore(timeInSeconds); //save the time to highscores.txt
            timer.stop(); //stop the timer
        }
    }


    //this method sets up the key bindings for the game controls
    private void setupKeyBindings() {
        ActionMap actionMap;
        InputMap inputMap;

        //get the input and action map for the level panel
        inputMap = lvl1Panel.getInputMap();
        actionMap = lvl1Panel.getActionMap();

        //add key bindings for character moving left
        //associates the action name "left" with a keyaction object that corresponds with 
        //an action once the key is pressed
        inputMap.put(KeyStroke.getKeyStroke(mario.getKey()[0].toCharArray()[0]), "left");
        actionMap.put("left", new KeyAction("left", mario));

        //add key bindings for character moving right
        //associates the action name "right" with key action obejct that corresponds with 
        //an action once key is pressed
        inputMap.put(KeyStroke.getKeyStroke(mario.getKey()[1].toCharArray()[0]), "right");
        actionMap.put("right", new KeyAction("right", mario));

        //add key bindings for character jumping
        //associates the action name "right" with key action obejct that corresponds with 
        //an action once key is pressed
        inputMap.put(KeyStroke.getKeyStroke(mario.getKey()[2].toCharArray()[0]), "jump");
        actionMap.put("jump", new KeyAction("jump", mario));
    }

    //although these methods are not used in the program,
    //they need to be implemented because they are required when using key events
    
    //this method is called when key is typed 
    public void keyTyped(KeyEvent e) {
    }
    //this method is called when key is pressed from keyboard
    public void keyPressed(KeyEvent e) {
    }
    //this method is called when key is released on keyboard
    public void keyReleased(KeyEvent e) {
        //check if the key is 'a' or 'd'
    	if (e.getKeyChar() == 'a' || e.getKeyChar() == 'd') {
            mario.setdX(0); //set horizontal velocity of character to 0
        }
    }
    
    //this class handles timer events and determines the events that happen
    //once the timer event is invoked
    class TimerAction implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            elapsedTime++; //increment the elapsed time
            updateTimerLabel(); //call method to updates timer label on GUI
        }
    }
    
    //this method updates the timer label on GUI
    private void updateTimerLabel() {
    	//calculate the elapsed seconds
        int seconds = elapsedTime / 10; 
        //calculate the tenths of a second
        int tenths = elapsedTime % 10; 
        //update the timer label with the new value
        timerLabel.setText(String.format("Time: %d.%d", seconds, tenths)); 
    }
}
