package src;

import java.util.List;



public class Environment {
    private List<Coordinate> obstacles;
    private List<Coordinate> organisms;
    private int width;
    private int height;
    private int cost=0;

    public Environment( int width, int height,List<Coordinate> organisms,List<Coordinate> obstacles) {
        this.obstacles = obstacles;
        this.organisms = organisms;
        this.width = width;
        this.height = height;
    }

    public List<Coordinate> getObstacles() {
        return obstacles;
    }

    public List<Coordinate> getOrganisms() {
        return organisms;
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public boolean isValidPosition(Coordinate position) {
        // Check if the position is within bounds and not an obstacle
        return position.getX() >= 0 && position.getX() < width &&
               position.getY() >= 0 && position.getY() < height &&
               !obstacles.contains(position);
    }

	public int getCost() {
		return cost;
	}

	public void setCost(int cost) {
		this.cost = cost;
		
	}


}
