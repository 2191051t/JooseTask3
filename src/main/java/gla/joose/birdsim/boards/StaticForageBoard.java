package gla.joose.birdsim.boards;

/**
 * A BirdSim board with where birds simultaneously fly and perch on  static grains.
 */
public class StaticForageBoard extends Board{


        
	public StaticForageBoard(int rows, int columns) {
		super(rows, columns);		
		flyBehaviour = new StaticForageFly(this);
		boardBehaviour = new StaticInitBoard(this);
	}
	@Override
	public void updateStockDisplay(){
		updateStock();
		noOfBirdsLabel.setText("#birds: "+noofbirds);
		noOfGrainsLabel.setText("#grains: "+noofgrains);
	}



}
