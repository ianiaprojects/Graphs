package TGC;

public class Warshall {
	
	private static final int inf = 999;
	
	private int[][] graph;	//graph to be processed
	private int V; 			//number of vertexes

	public Warshall(int[][] graph) {
		this.setGraph(graph);		//not deep copy, just keeps the pointer
		this.setV(graph.length); 	//keeps number of vertexes
	}
	
	public int[][] floydWarshall() {

		int dist[][] = new int[V][V];

		for (int i = 0; i < V; i++)
			for (int j = 0; j < V; j++)
				if(graph[i][j]==0)
					dist[i][j]=inf;
				else
					dist[i][j] = graph[i][j];

		for (int k = 0; k < V; k++) {
			for (int i = 0; i < V; i++) {
				for (int j = 0; j < V; j++) {
					if (dist[i][k] + dist[k][j] < dist[i][j])
						dist[i][j] = dist[i][k] + dist[k][j];
				}
			}
		}
		
		return dist;
	}

	//setters and getters
	public int[][] getGraph() {
		return graph;
	}

	public void setGraph(int[][] graph) {
		this.graph = graph;
	}

	public int getV() {
		return V;
	}

	public void setV(int v) {
		V = v;
	}
	
}
