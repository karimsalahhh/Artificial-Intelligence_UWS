package src;

import java.util.ArrayList;
import java.util.List;


public class Node {
	
	  public Node parent;
    public Environment environment;
    public MovementInstruction operator;
    public int cost;
    public int depth;
    public ArrayList<Integer> sizes = new ArrayList<>();
    
	public Node(Environment environment,Node parent,MovementInstruction operator, int cost,int depth) {
		// TODO Auto-generated constructor stub
		super();
		this.environment = environment;
		this.parent = parent;
		this.operator = operator;
		this.cost = cost;
		this.depth = depth;
		
	}
	public Environment getEnvironment() {
		return environment;
	}


	public void setEnvironmente(Environment environment) {
		this.environment = environment;
	}


	public Node getParent() {
		return parent;
	}


	public void setParent(Node parent) {
		this.parent = parent;
	}


	public MovementInstruction getMovementAction() {
		return operator;
	}


	public void setMovementAction(MovementInstruction operator) {
		this.operator = operator;
	}


	public int getCost() {
		return cost;
	}


	public void setCost(int cost) {
		this.cost = cost;
	}


	public int getDepth() {
		return depth;
	}


	public void setDepth(int depth) {
		this.depth = depth;
	}


	public ArrayList<Integer> getSizes() {
		return sizes;
	}


	public void setSizes(ArrayList<Integer> sizes) {
		this.sizes = sizes;
	}




	public Node(Environment environment, Node parent, int cost) {
		super();
		this.environment = environment;
		this.parent = parent;
		this.cost = cost;
	}
	public int getHeuristic1() {
		// TODO Auto-generated method stub
		return this.getEnvironment().getOrganisms().size();
	}
	public int getHeuristic2() {
		// TODO Auto-generated method stub
		int misplacedCount = 0;
	    List<Coordinate> goalPositions = getGoalPositions(); // Assume this method returns the target positions

	    // Assume that the organisms and goal positions are in the same order
	    for (int i = 0; i < getEnvironment().getOrganisms().size(); i++) {
	    	Coordinate organismPosition = getEnvironment().getOrganisms().get(i);
	    	Coordinate goalPosition = goalPositions.get(i);

	        if (!organismPosition.equals(goalPosition)) {
	            misplacedCount++;
	        }
	    }

	    return misplacedCount;
	}
	private List<Coordinate> getGoalPositions() {
        List<Coordinate> goalPositions = new ArrayList<>();
        List<Coordinate> obstaclePositions = environment.getObstacles();
        
        // Example strategy: Any position not occupied by obstacles could be a potential goal position
        for (int x = 0; x < 10; x++) { // Example grid dimensions
            for (int y = 0; y < 10; y++) {
            	Coordinate candidate = new Coordinate(x, y);
                if (!obstaclePositions.contains(candidate)) {
                    goalPositions.add(candidate);
                }
            }
        }

        return goalPositions;
    }





	



    
   

}
