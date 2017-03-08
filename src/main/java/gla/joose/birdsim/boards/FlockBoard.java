package gla.joose.birdsim.boards;

/**
 * A BirdSim board with generic bird flying behaviour.
 */
public class FlockBoard extends Board{
	public FlockBoard(int rows, int columns) {
		super(rows, columns);	
		setFlyBehaviour(new FlockFly(this));
		setInitBehaviour(new FlockInitBoard(this));
	}

	@Override
	public void updateStockDisplay(){
		updateStock();
		noOfBirdsLabel.setText("#birds: "+noofbirds);
	}
	
	
	

}
