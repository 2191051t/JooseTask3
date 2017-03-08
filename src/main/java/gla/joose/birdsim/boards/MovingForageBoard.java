package gla.joose.birdsim.boards;

/**
 * A BirdSim board with where birds simultaneously fly and perch on  moving grains.
 */
public class MovingForageBoard extends Board{

        
	public MovingForageBoard(int rows, int columns) {
		super(rows, columns);
		flyBehaviour = new MovingForageFly(this);
		boardBehaviour = new MovingInitBoard(this);
	}	
	@Override
	public void updateStockDisplay(){
		updateStock();
		noOfBirdsLabel.setText("#birds: "+noofbirds);
		noOfGrainsLabel.setText("#grains: "+noofgrains);
	}
		

}
