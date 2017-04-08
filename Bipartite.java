import java.util.*;

public class Bipartite {

	public static void main(String[] args) {
		Bipartite bipartiteTester = new Bipartite();

		//structure to hold adjacency list. Indexes from 1 (ignoring 0)
		ArrayList<LinkedList<Integer>> graphAdjacencyList = new ArrayList<LinkedList<Integer>>();
		graphAdjacencyList.add( new LinkedList<Integer>() );		//ignore 0th index to make future access more understandable
		graphAdjacencyList.add( new LinkedList<Integer>( Arrays.asList(5, 6, 7) ) );
		graphAdjacencyList.add( new LinkedList<Integer>( Arrays.asList(5, 6) ) );
		graphAdjacencyList.add( new LinkedList<Integer>( Arrays.asList(5, 6, 7) ) );
		graphAdjacencyList.add( new LinkedList<Integer>( Arrays.asList(6, 7) ) );
		graphAdjacencyList.add( new LinkedList<Integer>( Arrays.asList(1, 2, 3) ) );
		graphAdjacencyList.add( new LinkedList<Integer>( Arrays.asList(1, 2, 3, 4) ) );
		graphAdjacencyList.add( new LinkedList<Integer>( Arrays.asList(1, 4) ) );

		int startingNode = 1;		//starting node is arbitrary, can be any vertex in the graph
		System.out.println("1st graph\n");
		System.out.println("\nIs the graph bipartite? "+ bipartiteTester.isBipartite(graphAdjacencyList, startingNode));

		ArrayList<LinkedList<Integer>> graphAdjacencyList2 = new ArrayList<LinkedList<Integer>>();
		graphAdjacencyList2.add( new LinkedList<Integer>() );		//ignore 0th index to make future access more understandable
		graphAdjacencyList2.add( new LinkedList<Integer>( Arrays.asList(2, 3) ) );
		graphAdjacencyList2.add( new LinkedList<Integer>( Arrays.asList(1, 3) ) );
		graphAdjacencyList2.add( new LinkedList<Integer>( Arrays.asList(1, 2) ) );

		System.out.println("\n-------------------------------------------------------- \n2nd graph\n");
		System.out.println("\nIs the graph bipartite? "+ bipartiteTester.isBipartite(graphAdjacencyList2, startingNode));
	}

	public boolean isBipartite(ArrayList<LinkedList<Integer>> graph, int startingNode){
		//Array saying if a node has been visited. (INDEXED FROM 1, IGNORING 0). All future accesses must use an offset of +1 for "normal" arrays
		boolean[] visitedNodes = new boolean[ graph.size() +1];
		String[] color = new String[ graph.size() +1];	//boolean array. Red=true, Blue=false
		color[startingNode]="Red";

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
						if((layerCounter+1)%2==0){	//Check next layer, Even layers get Red
							color[v]="Red";
						}else{			//odd layers get Blue
							color[v]="Blue";
						}
					}
				}
			}
			layerCounter++;
		}
		System.out.println("Breadth first search Layers");
		printBfsLayers(layers);

		boolean bipartite = true;		//assume it's bipartite, check if it's not
		System.out.println("\nAll edges with colors");
		for(int u=1; u<graph.size(); u++){		//start from 1 NOT 0
			for(int v : graph.get(u)){
				System.out.print(u+"-->"+v+"  ("+color[u]+"-->"+color[v]+")");
				if(color[u].equals(color[v])){
					bipartite=false;
					System.out.print("  vertex colors match, cannot be bipartite");
				}
				System.out.println();
			}
		}

		return bipartite;
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