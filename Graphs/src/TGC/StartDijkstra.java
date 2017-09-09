package TGC;

import GUI.GraphGUI;

public class StartDijkstra {

	// Driver method
	public static void main (String[] args)
	{
		//Dijkstra example
		int graphExample1[][] = new int[][]{
			{0,  4,  0,  0,  0,  0,  0,  8,  0},
			{4,  0,  8,  0,  0,  0,  0, 11,  0},
			{0,  8,  0,  7,  0,  4,  0,  0,  2},
			{0,  0,  7,  0,  9, 14,  0,  0,  0},
			{0,  0,  0,  9,  0, 10,  0,  0,  0},
			{0,  0,  4, 14, 10,  0,  2,  0,  0},
			{0,  0,  0,  0,  0,  2,  0,  1,  6},
			{8, 11,  0,  0,  0,  0,  1,  0,  7},
			{0,  0,  2,  0,  0,  0,  6,  7,  0}};

		Dijkstra exampleDijkstra = new Dijkstra(graphExample1);
		
		int graphSolution1[][] = exampleDijkstra.dijkstra();
		
		GraphGUI dijkstraGUI = new GraphGUI(graphExample1, graphSolution1, true, "Dijkstra (shortest path algorithm)");
		dijkstraGUI.startGUI();
			
		//Warshall example
		int[][] graphExample2 = new int[][]{
			{0, 0, 0, 0, 0},
			{1, 0, 0, 0, 0},
			{0, 1, 0, 1, 0},
			{0, 0, 0, 0, 0},
			{1, 0, 0, 0, 0}};
		
		Warshall exampleWarshall = new Warshall(graphExample2);

		int graphSolution2[][] = exampleWarshall.floydWarshall();
		
		GraphGUI warshallGUI = new GraphGUI(graphSolution2, graphExample2, false, "Floyd Warshall (complete the graph)");
		warshallGUI.startGUI();
	}
}
