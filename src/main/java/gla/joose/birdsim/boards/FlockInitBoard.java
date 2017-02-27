package gla.joose.birdsim.boards;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class FlockInitBoard implements InitBoardBehaviour {

	public void initBoard(JFrame frame, Board board) {
		JPanel display = board.getJPanel();
        frame.getContentPane().add(display, BorderLayout.CENTER);
        
        // Install button panel
        board.buttonPanel = new JPanel();
        frame.getContentPane().add(board.buttonPanel, BorderLayout.SOUTH);
        
        board.hatchEggButton = new JButton("hatch egg");
        board.buttonPanel.add(board.hatchEggButton);
        board.hatchEggButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
            	board.scareBirds = false;
            	board.runningthread = new Thread(new Runnable(){
					public void run() {
						board.performFly();
					}            		
            	});
            	runningthread.start();
        }}); 

        board.scareBirdsButton = new JButton("scare birds");
        board.buttonPanel.add(board.scareBirdsButton);
        board.scareBirdsButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
            	scareBirds = true;
        }}); 
        
        board.noOfBirdsLabel = new JLabel();
        board.noOfBirdsLabel.setText("#birds: "+0);
        board.buttonPanel.add(board.noOfBirdsLabel);

        
        // Implement window close box
        frame.addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
            	//used to invoke birds removal from the board
            	scareBirds = true;
            	if(runningthread !=null){
                    clear();
                    try {
						runningthread.join();
					} catch (InterruptedException e1) {
						e1.printStackTrace();
					}
            	}
            	frame.dispose();
                System.exit(0);
        }});
        
        frame.pack();
        frame.setSize(650, 650);
        frame.setVisible(true);
	}
}
