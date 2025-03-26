package src;

public class NodeFactory {
	   public static Node createNode( Environment environment, Node parent, int cost) {
	        return new Node( environment, parent, cost);
	    }
}
