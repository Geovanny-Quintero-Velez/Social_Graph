package comparator;

import java.util.Comparator;

import structures.Grafo.Node;

public class CompareToDistance implements Comparator<Node>{

	@Override
	public int compare(Node o1, Node o2) {
		return o1.getDistance()-o2.getDistance();
	}

	
}
