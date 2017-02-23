package gla.joose.birdsim.boards;

import java.util.Random;

import gla.joose.birdsim.pieces.Bird;

public class FlockFly implements FlyBehaviour {
	Random rand = new Random();
	public void fly(Board board) {
		
		Bird bird = new Bird();
		
		int randRow = rand.nextInt((board.getRows() - 3) + 1) + 0;
    	int randCol = rand.nextInt((board.getColumns() - 3) + 1) + 0;
    	
		board.place(bird,randRow, randCol);
		bird.setDraggable(false);
		bird.setSpeed(20);
		board.updateStockDisplay();
		
		while(!board.scareBirds){
			randRow = rand.nextInt((board.getRows() - 3) + 1) + 0;
        	randCol = rand.nextInt((board.getColumns() - 3) + 1) + 0; 
        	bird.moveTo(randRow, randCol);
    		bird.setSpeed(20);
			
		} 
		bird.remove();
		board.updateStockDisplay();
	}

}
