package src;

import java.util.ArrayList;
import java.util.List;

public class MovementInstruction {
	
	public enum Direction {
        NORTH, SOUTH, EAST, WEST
    }

    private int organismIndex;
    private Direction direction;

    public MovementInstruction(int organismIndex, Direction direction) {
        this.organismIndex = organismIndex;
        this.direction = direction;
    }

    public int getEntityIndex() {
        return organismIndex;
    }

    public Direction getDirection() {
        return direction;
    }
    
    public Environment applyAction(Environment environment,MovementInstruction action) {
        List<Coordinate> newOrganisms = new ArrayList<>(environment.getOrganisms());
        Coordinate currentPosition = newOrganisms.get(action.getEntityIndex());
        Coordinate newPosition = computeNewPosition(currentPosition,  environment);

        //System.out.println("Current Position: " + currentPosition + ", New Position: " + newPosition + ", Action: " + this);
        if (currentPosition.equals(newPosition)) {
            // System.out.println("No movement for position: " + currentPosition);
             return null;
         }
        if (newPosition == null) {
            return null;
        }
       
        if (!environment.isValidPosition(newPosition)) {
         //  System.out.println("Invalid position: " + newPosition);
            return null;
        }
        if (newOrganisms.contains(newPosition)) {
          //  System.out.println("Collision detected at position: " + newPosition);
            newOrganisms.remove(currentPosition);
            newOrganisms.remove(newPosition);
            newOrganisms.add(newPosition);
        } else {
            newOrganisms.set(action.getEntityIndex(), newPosition);
        }

        int cost = distanceFromTo(currentPosition, newPosition);
        int finalCost = cost * currentPosition.getSize();
       // System.out.println("Final cost: " + finalCost);

        Environment newEnvironment = new Environment(environment.getWidth(), environment.getHeight(), newOrganisms, environment.getObstacles());
        newEnvironment.setCost(finalCost);

      //  System.out.println("Generated new environment: " + newEnvironment);
        return newEnvironment;
    }


    
    
    public Coordinate computeNewPosition(Coordinate position, Environment environment) {
        int x = position.getX();
        int y = position.getY();

        switch (direction) {
            case NORTH: return moveNorth(x, y, position.getSize(), environment);
            case SOUTH: return moveSouth(x, y, position.getSize(), environment);
            case EAST:  return moveEast(x, y, position.getSize(), environment);
            case WEST:  return moveWest(x, y, position.getSize(), environment);
            default: return null;
        }
    }
    private Coordinate moveNorth(int x, int y, int size, Environment environment) {
        while (--y >= 0) {
            Coordinate newPosition = new Coordinate(x, y, size);
            if (handleCollision(newPosition, environment, y + 1)) return newPosition;
        }
        return null;
    }

    private Coordinate moveSouth(int x, int y, int size, Environment environment) {
        while (++y < environment.getHeight()) {
            Coordinate newPosition = new Coordinate(x, y, size);
            if (handleCollision(newPosition, environment, y - 1)) return newPosition;
        }
        return null;
    }

    private Coordinate moveEast(int x, int y, int size, Environment environment) {
        while (++x < environment.getWidth()) {
            Coordinate newPosition = new Coordinate(x, y, size);
            if (handleCollision(newPosition, environment, x - 1)) return newPosition;
        }
        return null;
    }

    private Coordinate moveWest(int x, int y, int size, Environment environment) {
        while (--x >= 0) {
            Coordinate newPosition = new Coordinate(x, y, size);
            if (handleCollision(newPosition, environment, x + 1)) return newPosition;
        }
        return null;
    }

    private boolean handleCollision(Coordinate newPosition, Environment environment, int backtrack) {
       // System.out.println("Checking collision for newPosition: " + newPosition);

        if (environment.getObstacles().contains(newPosition)) {
           // System.out.println("Collision with obstacle at: " + newPosition);
            newPosition.setSize(backtrack);
           // System.out.println("Backtracked to position with size: " + newPosition.getSize());
            return true;
        }

        for (Coordinate organism : environment.getOrganisms()) {
            if (organism.equals(newPosition)) {
               // System.out.println("Collision with organism at: " + newPosition);
                newPosition.setSize(organism.getSize() + newPosition.getSize());
               // System.out.println("New position size after merging: " + newPosition.getSize());
                return true;
            }
        }

       // System.out.println("No collision detected for position: " + newPosition);
        return false;
    }
    
	private int distanceFromTo(Coordinate currentPosition, Coordinate newPosition) {
		// TODO Auto-generated method stub
		int X=  newPosition.getX()-currentPosition.getX();
		int Y=  newPosition.getY()-currentPosition.getY();
		int distance=Math.abs(Y)+Math.abs(X);
		return distance;
	

	}


    @Override
    public String toString() {
        return "MovementAction{" +
                "organismIndex=" + organismIndex +
                ", direction=" + direction +
                '}';
    }
	}


