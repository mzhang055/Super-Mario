/* Michelle Zhang
 * ICS3U1 - 05
 * This class is responsible for building the GUI of the home page frame. 
 * It includes two buttons (start game and rules) that allow the user to open
 * the level frame class or open the rules class to get instructions before playing
 */

//import
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class HomeFrame extends JFrame {
	//this class is responsible for building the gui of the home page frame
	public HomeFrame() {

		//set the title
		setTitle("Super Mario 🍄");
		//frame will exit on close
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		//create a custom panel with a background image
		JPanel panel = new JPanel() {
			@Override
			//this is a protected method because it must follow the same accessibility
			//of the override JPanel class
			protected void paintComponent(Graphics g) {
				super.paintComponent(g);
				//get actual image of object
				ImageIcon backgroundImage = new ImageIcon("images/homepage.png");
				Image image = backgroundImage.getImage();
				//scale to make image fit panels width and height
				g.drawImage(image, 0, 0, getWidth(), getHeight(), null);
			}
		};

		panel.setLayout(null); //use null layout for absolute positioning

		//create the start game button with an image
		ImageIcon startGameImg = new ImageIcon("images/startGame.png");
		JButton startGameBtn = new JButton(startGameImg);
		startGameBtn.setBounds(306, 260, 310, 57); //set the bounds for start game button
		startGameBtn.addActionListener(new ActionListener() {
			//use action listener to open game when button is clicked
			public void actionPerformed(ActionEvent e) {
				LevelFrame level = new LevelFrame(1); 
				level.setVisible(true);
				dispose(); // close home frame application when level frame appears
			}
		});

		//create the rules button with an image
		ImageIcon rulesImg = new ImageIcon("images/rules.png");
		JButton rulesBtn = new JButton(rulesImg);
		rulesBtn.setBounds(340, 340, 220, 53); //set the bounds
		rulesBtn.addActionListener(new ActionListener() {
			//use action listener to open rules frame when button is clicked
			public void actionPerformed(ActionEvent e) {
				Rules rules = new Rules();
				rules.setVisible(true);
				dispose(); // close home frame application when level frame appears
			}
		});
		
		//create the leaderboard button with an image
		ImageIcon leaderboardImg = new ImageIcon("images/leaderboardBtn.png");
		JButton leaderboardBtn = new JButton(leaderboardImg);
		leaderboardBtn.setBounds(300, 410, 330, 60); //set the bounds
		leaderboardBtn.addActionListener(new ActionListener() {
			//use action listener to open leaderboard frame when button is clicked
			public void actionPerformed(ActionEvent e) {
				Leaderboard leaderboard = new Leaderboard(); 
				leaderboard.setVisible(true);
				dispose(); // close home frame  application when leaderboard appears
			}
		});
		
		//add the buttons to the panel
		panel.add(startGameBtn);
		panel.add(rulesBtn);
		panel.add(leaderboardBtn);
		//add the panel to the frame
		add(panel);
		//set size of the frame
		setSize(900, 550);
		//allow components to be centered
		setLocationRelativeTo(null);
	}
}
