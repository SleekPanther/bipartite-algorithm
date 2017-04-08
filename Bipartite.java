import java.util.*;

public class Bipartite {

	public static void main(String[] args) {
		//structure to hold adjacency list. Indexes from 1 (ignoring 0)
		ArrayList<LinkedList<Integer>> graphAdjacencyList = new ArrayList<LinkedList<Integer>>();
		graphAdjacencyList.add( new LinkedList<Integer>() );		//ignore 0th index to make future access more understandable
		graphAdjacencyList.add( new LinkedList<Integer>( Arrays.asList(5, 6, 7) ) );
		graphAdjacencyList.add( new LinkedList<Integer>( Arrays.asList(5, 6) ) );
		graphAdjacencyList.add( new LinkedList<Integer>( Arrays.asList(5, 6, 7) ) );
		graphAdjacencyList.add( new LinkedList<Integer>( Arrays.asList(4, 6) ) );
		graphAdjacencyList.add( new LinkedList<Integer>( Arrays.asList(1, 2, 3) ) );
		graphAdjacencyList.add( new LinkedList<Integer>( Arrays.asList(1, 2, 3, 4) ) );
		graphAdjacencyList.add( new LinkedList<Integer>( Arrays.asList(1, 7) ) );

		Bipartite bipartiteTester = new Bipartite();

		int startingNode = 1;
		System.out.println("Is the graph bipartite? "+ bipartiteTester.isBipartite(graphAdjacencyList, startingNode));
	}

	public boolean isBipartite(ArrayList<LinkedList<Integer>> graph, int startingNode){
		//Array saying if a node has been visited. (INDEXED FROM 1, IGNORING 0). All future accesses must use an offset of +1 for "normal" arrays
		boolean[] visitedNodes = new boolean[ graph.size() +1];		

		int layerCounter = 0;		//how many layers we find. Used to name the layers. Starts @ 0, but there is actually 1 more layer than this number since L0 counts as the 1st.
		ArrayList<ArrayList<Integer>> layers = new ArrayList<ArrayList<Integer>>();
		layers.add(new ArrayList<Integer>());		//add the 1st layer
		layers.get(0).add(startingNode);			//add Starting node to the 1st layer
		visitedNodes[startingNode]=true;			//visit that node so it won't be explored again
		
		while( !layers.get(layerCounter).isEmpty()){	//repeat until no more layers exist
			layers.add(new ArrayList<Integer>());		//create empty layer (makes an extra layer when algorithm terminate, but ignored when printing)

			for(int u : layers.get(layerCounter)){		//for each node in the current layer
				for(int v : graph.get(u)){		//for each node with an edge to the node u
					if(!visitedNodes[v]){
						visitedNodes[v]=true;		//If not visited, visit
						layers.get(layerCounter+1).add(v);	// add to the NEXT layer
					}
				}
			}
			layerCounter++;
		}
		System.out.println("Breadth first search Layers");
		printBfsLayers(layers);

		return false;
	}

	private void printBfsLayers(ArrayList<ArrayList<Integer>> layers){
		for(int i=0; i<(layers.size()-1); i++){		//size()-1 Skip the last empty layer
			System.out.print("Layer "+i+":  ");
			for(int vertex : layers.get(i)){
				System.out.print(vertex+ "  ");		//print actual vertex in layer separated by spaces
			}
			System.out.println();
		}
	}

}