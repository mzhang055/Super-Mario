/* Michelle Zhang
 * ICS3U1 - 05
 * 
 * This program is responsible for the leaderboard frame. It reads the
 * scores from an external text file, sorts them in ascending order, and
 * updates the leaderboard GUI with the top 5 scores. The GUI includes 
 * score labels to display the scores and a button to return back to the
 * home frame.
 */

//import
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Scanner;

public class Leaderboard extends JFrame {

	//variable that holds the top 5 scores we are going to display
    private static final int NUM_TOP_SCORES = 5; 
    private JLabel[] scoreLabels; //array to hold score labels

    //this method takes the array of the scorelabels as input,
    //reads scores from the external .txt file, and objects the JLabel array
    //with the top 5 scores
    public static void fillLeaderboard(JLabel[] scoreLabels) {
        //determine file where the scores are stored
    	File file = new File("data/highscores.txt");
        try {
        	//read contents in the file using scanner
            Scanner scanner = new Scanner(file);

            //use an array list of double values to store the scores read from the file
            //use an array list instead of a regular array because it is a dynamic size 
            //to accomodate all values in the highscores file
            ArrayList<Double> scores = new ArrayList<>();

            //iterate through every line in the file
            while (scanner.hasNextLine()) {
            	//read the next line and assign to line variable
                String line = scanner.nextLine();
                try {
                	//parse the line as a double and assign to score
                    double score = Double.parseDouble(line);
                    //if parsing is successful, add score to array list
                    scores.add(score);
                    //if parsing fails, display an error message
                } catch (NumberFormatException e) {
                    System.out.println("Invalid score format: " + line);
                }
            }

            //close the scanner object after loop finishes
            scanner.close();

            //sort the scores in ascending order
            Collections.sort(scores);

            //display the top scores on the GUI
            int numScoresToDisplay = Math.min(NUM_TOP_SCORES, scores.size());
            //iterates through the numScoresToDisplay to get score from the arry list at 
            //the i-th index
            for (int i = 0; i < numScoresToDisplay; i++) {
                String scoreText = String.format("%.1f", scores.get(i));
                //gets the score 
                scoreLabels[i].setText(scoreText);
                scoreLabels[i].setForeground(Color.WHITE); //set text color to white
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    //this method constructs the leaderboard object, creating the necessary components
    //and displays leaderboard
    public Leaderboard() {
    	//set properties of frame
        setTitle("Leaderboard");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setResizable(false);
        setSize(850, 580);
        setLocationRelativeTo(null);

        setLayout(null); //use absolute positioning

        //create a panel to hold the score labels
        JPanel scorePanel = new JPanel();
        scorePanel.setBounds(0, 0, 850, 580);
        scorePanel.setOpaque(false); //make the panel transparent
        scorePanel.setLayout(null); //use absolute positioning
        
        //create the score labels
        scoreLabels = new JLabel[NUM_TOP_SCORES];
        //iterate through the top 5 scores and display on the GUI
        for (int i = 0; i < NUM_TOP_SCORES; i++) {
            scoreLabels[i] = new JLabel();
            scoreLabels[i].setBounds(450, 140 + i * 83, 200, 30);
            scoreLabels[i].setHorizontalAlignment(SwingConstants.RIGHT);
            scoreLabels[i].setFont(scoreLabels[i].getFont().deriveFont(Font.BOLD, 25f)); 
            scorePanel.add(scoreLabels[i]); //add to panel
        }
                
        //set the background image
        ImageIcon backgroundImageIcon = new ImageIcon("images/leaderboard.png");
        JLabel backgroundLabel = new JLabel(backgroundImageIcon);
        backgroundLabel.setBounds(0, 0, 850, 580);
        
        //create a button to return back to home frame
        ImageIcon arrowImg = new ImageIcon("images/arrow.png");
        JButton arrowBtn = new JButton(arrowImg);
        //set the bounds of the button to position it at the top left
        int btnWidth = arrowImg.getIconWidth();
        int btnHeight = arrowImg.getIconHeight();
        arrowBtn.setBounds(20, 20, btnWidth, btnHeight);
        arrowBtn.addActionListener(new ActionListener() {
			//use action listener to open question frame when button is clicked
			public void actionPerformed(ActionEvent e) {
				HomeFrame home = new HomeFrame(); //open home frame when button is clicked
				home.setVisible(true);
				dispose(); //close current frame
			}
		});
        
        //add button to frame
        scorePanel.add(arrowBtn);
        
        //add the components to the scorePanel in the desired order
        scorePanel.add(backgroundLabel);
        
        //add score panel to teh main content pane
        getContentPane().add(scorePanel);

        //fill the leaderboard with score labels
        fillLeaderboard(scoreLabels);

        //set the frame to be visible
        setVisible(true);
    }

}
