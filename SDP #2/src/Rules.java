/* Michelle Zhang
 * ICS3U1 - 05
 * 
 * This program displays a GUI with the rules and objective of the game. It also
 * prompts the user to start the game using a button that leads them to the game. 
 * The user also has the option to return back to the home frame.
 * 
 */

//import
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Rules extends JFrame {

	//this constructor method creates the GUI
    public Rules() {
        //set the frame size
        setSize(850, 580);
        //set the layout to null for absolute positioning
        setLayout(null);
        //allow frame to exit when closed by user
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        
        //create a panel for the background image
        JPanel backgroundPanel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                //input the background image
                ImageIcon backgroundImage = new ImageIcon("images/rulesFrame.png");
                //draw the image on the panel
                g.drawImage(backgroundImage.getImage(), 0, 0, getWidth(), getHeight(), null);
            }
        };
        
        //set the layout of the background panel to null for absolute positioning
        backgroundPanel.setLayout(null);
        //set the bounds of the background panel to cover the entire frame
        backgroundPanel.setBounds(0, 0, getWidth(), getHeight());
        //add the background panel to the frame
        add(backgroundPanel);
        
        //create a start game button with an image
        ImageIcon startGameImg2 = new ImageIcon("images/startGame.png");
        JButton startGameBtn2 = new JButton(startGameImg2);
        // Set the bounds of the button to position it at the bottom center
        int buttonWidth = startGameImg2.getIconWidth();
        int buttonHeight = startGameImg2.getIconHeight();
        startGameBtn2.setBounds(270, 350, buttonWidth, buttonHeight);
		startGameBtn2.addActionListener(new ActionListener() {
			//use action listener to open question frame when button is clicked
			public void actionPerformed(ActionEvent e) {
				LevelFrame level = new LevelFrame(1); // open level frame when button is clicked
				level.setVisible(true);
				dispose(); // close rules frame
			}
		});
        //add the button to the background panel
        backgroundPanel.add(startGameBtn2);

        //set the frame visible
        setVisible(true);
    }

}
