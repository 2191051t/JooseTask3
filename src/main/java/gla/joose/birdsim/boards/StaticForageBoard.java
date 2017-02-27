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

import gla.joose.birdsim.pieces.Grain;

/**
 * A BirdSim board with where birds simultaneously fly and perch on  static grains.
 */
public class StaticForageBoard extends Board{

    JPanel buttonPanel;
    JButton hatchEggButton;
    JButton feedBirdButton;
    JButton scareBirdsButton;
    boolean scareBirds;
    JButton starveBirdsButton;
    
    JLabel noOfGrainsLabel;
    JLabel noOfBirdsLabel;
    
    Thread runningthread;
        
	public StaticForageBoard(int rows, int columns) {
		super(rows, columns);		
		flyBehaviour = new StaticForageFly();
	}

	@Override
	public void initBoard(final JFrame frame) {
		JPanel display = getJPanel();
        frame.getContentPane().add(display, BorderLayout.CENTER);
        
        // Install button panel
        buttonPanel = new JPanel();
        frame.getContentPane().add(buttonPanel, BorderLayout.SOUTH);
        
        hatchEggButton = new JButton("hatch egg");
        buttonPanel.add(hatchEggButton);
        hatchEggButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
            	scareBirds = false;
            	runningthread = new Thread(new Runnable(){
					public void run() {
						performFly();
					}            		
            	});
            	runningthread.start();
        }}); 
        
        feedBirdButton = new JButton("feed birds");
        buttonPanel.add(feedBirdButton);
        feedBirdButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
            	starveBirdspressed = false;

            	Grain grain = new Grain();
            	int randRow = rand.nextInt((getRows() - 3) + 1) + 0;
            	int randCol = rand.nextInt((getColumns() - 3) + 1) + 0;
            	place(grain,randRow, randCol);
        		grain.setDraggable(false);
        		
        		updateStockDisplay();
        }}); 

        starveBirdsButton = new JButton("starve birds");
        buttonPanel.add(starveBirdsButton);
        starveBirdsButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
            	starveBirdspressed = true;

        }}); 
        
        scareBirdsButton = new JButton("scare birds");
        buttonPanel.add(scareBirdsButton);
        scareBirdsButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
            	scareBirds = true;

        }}); 
        noOfBirdsLabel = new JLabel();
        noOfBirdsLabel.setText("#birds: "+0);
        buttonPanel.add(noOfBirdsLabel);

        noOfGrainsLabel = new JLabel();
        noOfGrainsLabel.setText("#grains: "+0);
        buttonPanel.add(noOfGrainsLabel);

        // Implement window close box
        frame.addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
            	scareBirds = true;            	
            	if(runningthread != null){
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
	
	
	@Override
	public void updateStockDisplay(){
		updateStock();
		noOfBirdsLabel.setText("#birds: "+noofbirds);
		noOfGrainsLabel.setText("#grains: "+noofgrains);
	}



}
