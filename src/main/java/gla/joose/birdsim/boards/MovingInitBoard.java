package gla.joose.birdsim.boards;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.Random;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

import gla.joose.birdsim.pieces.Grain;

public class MovingInitBoard implements InitBoardBehaviour {
	final Board board;
	Random rand = new Random();
	public MovingInitBoard(Board board){
		this.board = board;
	}

	public void initBoard(final JFrame frame) {
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
            	board.runningthread.start();
        }}); 
        
        board.feedBirdButton = new JButton("feed birds");
        board.buttonPanel.add(board.feedBirdButton);
        board.feedBirdButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
            	board.starveBirds = false;

            	Grain grain = new Grain();
            	int randRow = rand.nextInt((board.getRows() - 3) + 1) + 0;
            	int randCol = rand.nextInt((board.getColumns() - 3) + 1) + 0;
            	board.place(grain,randRow, randCol);
        		grain.setDraggable(false);
        		
        		board.updateStockDisplay();
        }}); 

        board.starveBirdsButton = new JButton("starve birds");
        board.buttonPanel.add(board.starveBirdsButton);
        board.starveBirdsButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
            	board.starveBirds = true;

        }}); 
        
        board.scareBirdsButton = new JButton("scare birds");
        board.buttonPanel.add(board.scareBirdsButton);
        board.scareBirdsButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
            	board.scareBirds = true;

        }}); 
        board.noOfBirdsLabel = new JLabel();
        board.noOfBirdsLabel.setText("#birds: "+0);
        board.buttonPanel.add(board.noOfBirdsLabel);

        board.noOfGrainsLabel = new JLabel();
        board.noOfGrainsLabel.setText("#grains: "+0);
        board.buttonPanel.add(board.noOfGrainsLabel);

        // Implement window close box
        frame.addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
            	board.scareBirds = true;
            	if(board.runningthread !=null){
            		board.clear();
                    try {
                    	board.runningthread.join();
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
