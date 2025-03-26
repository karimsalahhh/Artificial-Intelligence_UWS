package src;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.PriorityQueue;
import java.util.Queue;
import java.util.Random;
import java.util.Set;
import java.util.Stack;




public class UnitedWeStandSearch extends GenericSearch {
	static Node initialNode;

	
	@Override
	public Node getInitialState() {
		return initialNode; 
	}

	@Override
	public boolean goalTest(Node node) {
		// TODO Auto-generated method stub
		return node.getEnvironment().getOrganisms().size() == 1;
	}

   public UnitedWeStandSearch() {
	   
   }
   public static String solve(String grid, String strategy, boolean visualize) {
	   Environment initialState = ParsingGrid(grid); // Ensure ParsingGrid is updated
//	    Node initialNode = getInitialState();
	   
	   // Create an instance of UnitedWeStandSearch to perform the search
	    UnitedWeStandSearch search = new UnitedWeStandSearch();
	    
	    Node solutionNode = null;

	    // Choose the search strategy using a switch-case statement
	    switch (strategy) {
	        case "BF":
	            solutionNode = search.breadthFirstSearch(initialNode);
	            break;
	        case "DF":
	            solutionNode = search.depthFirstSearch(initialNode);
	            break;
	        case "UC":
	            solutionNode = search.uniformCostSearch(initialNode);
	            break;
	        case "ID":
	            solutionNode = search.iterativeDeepeningSearch(initialNode);
	            break;
	        case "GR1":
	            solutionNode = search.greedySearch1(initialNode);
	            break;
	        case "GR2":
	            solutionNode = search.greedySearch2(initialNode);
	            break;
	        case "AS1":
	            solutionNode = search.aStarSearch1(initialNode);
	            break;
	        case "AS2":
	            solutionNode = search.aStarSearch2(initialNode);
	            break;
	      
	        default:
	            System.err.println("Strategy Not Found" + strategy);
	            return "";
	    }
	    System.out.println("Solution  "+solutionNode );
	    // Process the solution node
	    if (solutionNode != null) {
	        // Generate solution string format: "action_organismX_organismY;cost;expandedNodes"
	        StringBuilder solution = new StringBuilder();
	        List<Node> path = constructPath(solutionNode);
	        List<Coordinate> originalPositions = initialState.getOrganisms(); // Get the original positions of organisms
	       // System.out.print(path.size() + " <<<<<<<<<<<");
	        for (int i = 0; i < path.size(); i++) {
	            Node node = path.get(i);
	            MovementInstruction action = node.getMovementAction();
	            
	            if (action != null) {
	                int organismIndex = action.getEntityIndex();
	                
	                // Use the original positions of the organisms
	                if (organismIndex >= 0 && organismIndex < originalPositions.size()) {
	                	Coordinate originalPosition = node.getParent().getEnvironment().getOrganisms().get(organismIndex);
	                    solution.append(action.getDirection()).append("_")
	                            .append(originalPosition.getX()).append("_")
	                            .append(originalPosition.getY());
	                    if (i < path.size() - 1) {
	                        solution.append(",");
	                    }
	                }
	            }
	        }
	        solution.append(";").append(solutionNode.getCost()).append(";")
	                .append(path.size()); // Number of expanded nodes

	        if (visualize) {
	            visualizeGrid(solutionNode);
	        }

	        return solution.toString();
	    } else {
	        return "No solution";
	    }
	   
   }
   
   private static void visualizeGrid(Node solutionNode) {
	
	
}

private static List<Node> constructPath(Node node) {
	List<Node> path = new ArrayList<>();
    while (node != null) {
        path.add(node);
        node = node.getParent();
    }
    Collections.reverse(path);
    return path;
}



private Node aStarSearch2(Node initialNode2) {
	// Priority queue with nodes ordered by the sum of path cost and heuristic
    PriorityQueue<Node> queue = new PriorityQueue<>(Comparator.comparingInt(node -> node.getCost() + node.getHeuristic2()));
    // Set to track visited states
    Set<Environment> visitedStates = new HashSet<>();
    // Map to track the cost of reaching each node
    Map<Environment, Integer> costMap = new HashMap<>();
    
    // Initialize the priority queue and cost map
    queue.add(initialNode);
    costMap.put(initialNode.getEnvironment(), initialNode.getCost());
    
    System.out.println("Starting A* with initial node: " + initialNode);
    
    while (!queue.isEmpty()) {
        Node currentNode = queue.poll();
        
        // Check if goal state is reached
        if (goalTest(currentNode)) {
            System.out.println("Goal state reached: " + currentNode);
            return currentNode;
        }
        
        // Explore successor nodes
        List<Node> childNodes = getChild(currentNode);
        
        for (Node successor : childNodes) {
            if (successor != null) {
                Environment state = successor.getEnvironment();
                int newCost = currentNode.getCost() + successor.getCost(); // Path cost to the successor
                
                // Check if the successor is visited or if a lower cost is found
                if (!visitedStates.contains(state) || newCost < costMap.getOrDefault(state, Integer.MAX_VALUE)) {
                    costMap.put(state, newCost);
                    // Add successor to the queue with updated priority
                    queue.add(successor);
                    visitedStates.add(state);
                }
            }
        }
    }
    
    return null; // Return null if no solution is found
}

private Node aStarSearch1(Node initialNode2) {
	// Priority queue with nodes ordered by the sum of path cost and heuristic
    PriorityQueue<Node> queue = new PriorityQueue<>(Comparator.comparingInt(node -> node.getCost() + node.getHeuristic1()));
    // Set to track visited states
    Set<Environment> visitedStates = new HashSet<>();
    // Map to track the cost of reaching each node
    Map<Environment, Integer> costMap = new HashMap<>();
    
    // Initialize the priority queue and cost map
    queue.add(initialNode);
    costMap.put(initialNode.getEnvironment(), initialNode.getCost());
    
    System.out.println("Starting A* with initial node: " + initialNode);
    
    while (!queue.isEmpty()) {
        Node currentNode = queue.poll();
        
        // Check if goal state is reached
        if (goalTest(currentNode)) {
            System.out.println("Goal state reached: " + currentNode);
            return currentNode;
        }
        
        // Explore successor nodes
        List<Node> childNodes = getChild(currentNode);
        
        for (Node successor : childNodes) {
            if (successor != null) {
                Environment state = successor.getEnvironment();
                int newCost = currentNode.getCost() + successor.getCost(); // Path cost to the successor
                
                // Check if the successor is visited or if a lower cost is found
                if (!visitedStates.contains(state) || newCost < costMap.getOrDefault(state, Integer.MAX_VALUE)) {
                    costMap.put(state, newCost);
                    // Add successor to the queue with updated priority
                    queue.add(successor);
                    visitedStates.add(state);
                }
            }
        }
    }
    
    return null; // Return null if no solution is found
}

private Node greedySearch2(Node initialNode2) {
	PriorityQueue<Node> queue = new PriorityQueue<>(Comparator.comparingInt(node -> node.getHeuristic2()));
    Set<Environment> visitedStates = new HashSet<>();
    
    queue.add(initialNode2);
  //  visitedStates.add(initialNode2.getEnvironment());
    System.out.println("Starting GR2 with initial node: " + initialNode2);
    
    while (!queue.isEmpty()) {
        Node currentNode = queue.remove();
       // System.out.println("Dequeued node: " + currentNode);
        // Check if goal state is reached
        if (goalTest(currentNode)) {
         //   System.out.println("Goal state reached: " + currentNode);
            return currentNode;
        }
        // Explore successor nodes
        List<Node> childNodes = getChild(currentNode);
       // System.out.println("Current node's successors: " + childNodes);

        for (Node successor : childNodes) {
            if (successor != null) {
               // System.out.println("Checking successor: " + successor);
                if (!visitedStates.contains(successor.getEnvironment())) {
                  //  System.out.println("Adding successor to queue: " + successor);
                    queue.add(successor);
                    visitedStates.add(successor.getEnvironment());
                } else {
                   // System.out.println("Successor already visited: " + successor);
                }
            } else {
              //  System.out.println("Encountered null successor.");
            }
        }
	
}
    return null;
}

private Node greedySearch1(Node initialNode2) {
	PriorityQueue<Node> queue = new PriorityQueue<>(Comparator.comparingInt(node -> node.getHeuristic1()));
    Set<Environment> visitedStates = new HashSet<>();
    
    queue.add(initialNode2);
  //  visitedStates.add(initialNode2.getEnvironment());
    System.out.println("Starting GR1 with initial node: " + initialNode2);
    
    while (!queue.isEmpty()) {
        Node currentNode = queue.remove();
       // System.out.println("Dequeued node: " + currentNode);
        // Check if goal state is reached
        if (goalTest(currentNode)) {
         //   System.out.println("Goal state reached: " + currentNode);
            return currentNode;
        }
        // Explore successor nodes
        List<Node> childNodes = getChild(currentNode);
       // System.out.println("Current node's successors: " + childNodes);

        for (Node successor : childNodes) {
            if (successor != null) {
               // System.out.println("Checking successor: " + successor);
                if (!visitedStates.contains(successor.getEnvironment())) {
                  //  System.out.println("Adding successor to queue: " + successor);
                    queue.add(successor);
                    visitedStates.add(successor.getEnvironment());
                } else {
                   // System.out.println("Successor already visited: " + successor);
                }
            } else {
              //  System.out.println("Encountered null successor.");
            }
        }
	
}
    return null;
}


public Node iterativeDeepeningSearch(Node initialNode) {
    int depthLimit = 0;
    Map<Node, Integer> nodeDepth = new HashMap<>();
    Queue<Node> queue = new LinkedList<>();

    queue.add(initialNode);
    nodeDepth.put(initialNode, 0);
    
    while (true) {
        Set<Environment> visitedStates = new HashSet<>();
        visitedStates.add(initialNode.getEnvironment());
        
        int lastDepth = -1;
        boolean depthIncreased = false;

        while (!queue.isEmpty()) {
            Node currentNode = queue.remove();
            int currentDepth = nodeDepth.get(currentNode); // Get depth from map

            // Print or use the current depth
            if (currentDepth != lastDepth) {
                System.out.println("Visiting Node at depth: " + currentDepth);
                lastDepth = currentDepth;
                depthIncreased = true;
            }

            // Check if goal state is reached
            if (goalTest(currentNode)) {
                return currentNode;
            }

            // Explore successor nodes
            List<Node> childNodes = getChild(currentNode);
            for (Node successor : childNodes) {
                if (successor != null && !visitedStates.contains(successor.getEnvironment())) {
                    if (!nodeDepth.containsKey(successor)) { // Check if the node is already visited
                        queue.add(successor);
                        nodeDepth.put(successor, currentDepth + 1); // Store depth of successor
                    }
                    visitedStates.add(successor.getEnvironment());
                }
            }
        }

        // Check if depth has not increased; if so, stop the search
        if (!depthIncreased) {
            System.out.println("Stopping search as no new depths are being explored.");
            break;
        }

        depthLimit++;
        System.out.println("Depth limit: " + depthLimit);
    }

    return null; // Return null if no solution is found
}



private Node uniformCostSearch(Node initialNode2) {
	PriorityQueue<Node> queue = new PriorityQueue<>(Comparator.comparingInt(Node::getCost));
    Set<Environment> visitedStates = new HashSet<>();
    
    queue.add(initialNode2);
  //  visitedStates.add(initialNode2.getEnvironment());
    System.out.println("Starting UC with initial node: " + initialNode2);
    
    while (!queue.isEmpty()) {
        Node currentNode = queue.remove();
       // System.out.println("Dequeued node: " + currentNode);
        // Check if goal state is reached
        if (goalTest(currentNode)) {
         //   System.out.println("Goal state reached: " + currentNode);
            return currentNode;
        }
        // Explore successor nodes
        List<Node> childNodes = getChild(currentNode);
       // System.out.println("Current node's successors: " + childNodes);

        for (Node successor : childNodes) {
            if (successor != null) {
               // System.out.println("Checking successor: " + successor);
                if (!visitedStates.contains(successor.getEnvironment())) {
                  //  System.out.println("Adding successor to queue: " + successor);
                    queue.add(successor);
                    visitedStates.add(successor.getEnvironment());
                } else {
                   // System.out.println("Successor already visited: " + successor);
                }
            } else {
              //  System.out.println("Encountered null successor.");
            }
        }
	
}
    return null;
    }

private Node depthFirstSearch(Node initialNode2) {
	Stack<Node> stack = new Stack<>();
    Set<Environment> visitedStates = new HashSet<>();
    
    stack.add(initialNode2);
  //  visitedStates.add(initialNode2.getEnvironment());
    System.out.println("Starting DFS with initial node: " + initialNode2);
    
    while (!stack.isEmpty()) {
        Node currentNode = stack.pop();
       // System.out.println("Dequeued node: " + currentNode);
        // Check if goal state is reached
        if (goalTest(currentNode)) {
         //   System.out.println("Goal state reached: " + currentNode);
            return currentNode;
        }
        // Explore successor nodes
        List<Node> childNodes = getChild(currentNode);
       // System.out.println("Current node's successors: " + childNodes);

        for (Node successor : childNodes) {
            if (successor != null) {
               // System.out.println("Checking successor: " + successor);
                if (!visitedStates.contains(successor.getEnvironment())) {
                  //  System.out.println("Adding successor to queue: " + successor);
                	stack.add(successor);
                    visitedStates.add(successor.getEnvironment());
                } else {
                   // System.out.println("Successor already visited: " + successor);
                }
            } else {
              //  System.out.println("Encountered null successor.");
            }
        }
    }   
    System.out.println("No solution found.");
    return null; // Return null if no solution is found
}

//BFS
private Node breadthFirstSearch(Node initialNode2) {
    // Initialize data structures for search
    Queue<Node> queue = new LinkedList<>();
    Set<Environment> visitedStates = new HashSet<>();
    
    queue.add(initialNode2);
  //  visitedStates.add(initialNode2.getEnvironment());
    System.out.println("Starting BFS with initial node: " + initialNode2);
    
    while (!queue.isEmpty()) {
        Node currentNode = queue.remove();
       // System.out.println("Dequeued node: " + currentNode);
        // Check if goal state is reached
        if (goalTest(currentNode)) {
         //   System.out.println("Goal state reached: " + currentNode);
            return currentNode;
        }
        // Explore successor nodes
        List<Node> childNodes = getChild(currentNode);
       // System.out.println("Current node's successors: " + childNodes);

        for (Node successor : childNodes) {
            if (successor != null) {
               // System.out.println("Checking successor: " + successor);
                if (!visitedStates.contains(successor.getEnvironment())) {
                  //  System.out.println("Adding successor to queue: " + successor);
                    queue.add(successor);
                    visitedStates.add(successor.getEnvironment());
                } else {
                   // System.out.println("Successor already visited: " + successor);
                }
            } else {
              //  System.out.println("Encountered null successor.");
            }
        }
    }   
    System.out.println("No solution found.");
    return null; // Return null if no solution is found
}

   
   
//   public static boolean goalTest(Node node) {
//       return node.getEnvironment().getOrganisms().size() == 1;
//   }

public List<Node> getChild(Node currentNode) {
    List<Node> childs = new ArrayList<>();
   // System.out.println("Generating child nodes for current node: " + currentNode);

    for (int organismIndex = 0; organismIndex < currentNode.getEnvironment().getOrganisms().size(); organismIndex++) {
        for (MovementInstruction.Direction direction : MovementInstruction.Direction.values()) {
            MovementInstruction action = new MovementInstruction(organismIndex, direction);
           // System.out.println("Applying action: " + action + " for organism index: " + organismIndex);

            Environment newState = action.applyAction(currentNode.getEnvironment(),action);
           // System.out.println("Action Direction: " + action + " for organism index: " + organismIndex);
            if (newState != null) {
                Node successor = new Node(newState, currentNode, action, currentNode.getCost() + newState.getCost(), currentNode.getDepth() + 1);
               // System.out.println("Generated successor node: " + successor);
                childs.add(successor);
            } else {
              //  System.out.println("Action resulted in a null state.");
            }
        }
    }
   //System.out.println("Generated child nodes: " + childs);
    return childs;
}



private static Environment ParsingGrid(String grid) {
	    String[] parts = grid.split(";");
	    int width = Integer.parseInt(parts[0]);
	    int height = Integer.parseInt(parts[1]);

	    List<Coordinate> organisms = new ArrayList<>();
	    List<Coordinate> obstacles = new ArrayList<>();

	    // Parse organisms positions
	    String[] organismPositions = parts[2].split(",");
	    for (int i = 0; i < organismPositions.length; i += 2) {
	        int x = Integer.parseInt(organismPositions[i]);
	        int y = Integer.parseInt(organismPositions[i + 1]);
	        organisms.add(new Coordinate(x, y));
	    }

	    // Parse obstacles positions (if any)
	    if (parts.length > 3) {
	        String[] obstaclePositions = parts[3].split(",");
	        for (int i = 0; i < obstaclePositions.length; i += 2) {
	            int x = Integer.parseInt(obstaclePositions[i]);
	            int y = Integer.parseInt(obstaclePositions[i + 1]);
	            obstacles.add(new Coordinate(x, y));
	        }
	    }
	    
	    Environment env=new Environment(width, height, organisms, obstacles);
	    initialNode = new Node(env, null,0);	
		initialNode.setCost(0);
		
	    return env;
	}

   public static String genGrid() {
       Random rand = new Random();
       int width = rand.nextInt(10) + 1;  // Random width between 1 and 10
       int height = rand.nextInt(10) + 1;  // Random height between 1 and 10

       // Generate random number of organisms (up to 20)
       int numOrganisms = rand.nextInt(20) + 1;  // At least one organism

       List<Coordinate> organisms = new ArrayList<>();
       for (int i = 0; i < numOrganisms; i++) {
           int x = rand.nextInt(width);  // Random x coordinate within width
           int y = rand.nextInt(height);  // Random y coordinate within height
           organisms.add(new Coordinate(x, y));
       }

       // Generate random number of obstacles
       int numObstacles = rand.nextInt(Math.min(width * height, 20 - numOrganisms)) + 1;  // Up to remaining spaces or 20 total

       List<Coordinate> obstacles = new ArrayList<>();
       for (int i = 0; i < numObstacles; i++) {
           int x = rand.nextInt(width);  // Random x coordinate within width
           int y = rand.nextInt(height);  // Random y coordinate within height
           obstacles.add(new Coordinate(x, y));
       }

       StringBuilder sb = new StringBuilder();
       sb.append(width).append(";").append(height).append(";");
       
       // Add organisms to the string
       for (Coordinate c : organisms) {
           sb.append(c.getX()).append(",").append(c.getY()).append(",");
       }
       if (!organisms.isEmpty()) {
           sb.deleteCharAt(sb.length() - 1); // Remove the trailing comma
       }
       sb.append(";");
       
       // Add obstacles to the string
       for (Coordinate c : obstacles) {
           sb.append(c.getX()).append(",").append(c.getY()).append(",");
       }
       if (!obstacles.isEmpty()) {
           sb.deleteCharAt(sb.length() - 1); // Remove the trailing comma
       }

       return sb.toString();
   }




   
   }
