package TGC;

class Dijkstra {

	private int graph[][];	//graph to be processed
	private int V;			//number of vertexes
	
	private static final int source = 0; //always compute path from first to last node
	
	public Dijkstra(int graph[][]) {	
		this.setGraph(graph); 		//not deep copy, just keeps its address
		this.V = graph.length;		//number of vertexes
	}
	
    int[][] dijkstra() {
        
    	int dist[] = new int[V];
        int pathGraph[][] = new int[V][V];
        Boolean sptSet[] = new Boolean[V];
        

        for (int i = 0; i < V; i++) {
        	for (int j = 0; j < V; j++)
        		pathGraph[i][j] = 0;
        	
            dist[i] = Integer.MAX_VALUE;
            sptSet[i] = false;
        }
 
        dist[source] = 0;

        int a[] = new int[V];
        for (int count = 0; count < V-1; count++) {
        	
            int u = minDistance(dist, sptSet);
            sptSet[u] = true;
 
            for (int v = 0; v < V; v++) {
                if (!sptSet[v] && graph[u][v]!=0 && dist[u] != Integer.MAX_VALUE && dist[u]+graph[u][v] < dist[v]) {	
                    dist[v] = dist[u] + graph[u][v];
                    a[v] = u;
                }
            }
        }
        
        int g = V-1;
        while (g != 0) {
        	pathGraph[a[g]][g] = graph[a[g]][g];
        	g = a[g];        	
        }
 
        return pathGraph;
    }
    
    //help function
    int minDistance(int dist[], Boolean sptSet[]) {

        int min = Integer.MAX_VALUE, min_index=-1;
 
        for (int v = 0; v < V; v++) {
            if (sptSet[v] == false && dist[v] <= min) {
                min = dist[v];
                min_index = v;
            }
        }
 
        return min_index;
    }

    //setters and getters
	public int[][] getGraph() {
		return graph;
	}

	public void setGraph(int graph[][]) {
		this.graph = graph;
	}
}
