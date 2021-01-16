/* PrimVsKruskal.java
   CSC 226 - Spring 2019
   Assignment 2 - Prim MST versus Kruskal MST Template

   The file includes the "import edu.princeton.cs.algs4.*;" so that yo can use
   any of the code in the algs4.jar file. You should be able to compile your program
   with the command

	javac -cp .;algs4.jar PrimVsKruskal.java

   To conveniently test the algorithm with a large input, create a text file
   containing a test graphs (in the format described below) and run
   the program with

	java -cp .;algs4.jar PrimVsKruskal file.txt

   where file.txt is replaced by the name of the text file.

   The input consists of a graph (as an adjacency matrix) in the following format:

    <number of vertices>
	<adjacency matrix row 1>
	...
	<adjacency matrix row n>

   Entry G[i][j] >= 0.0 of the adjacency matrix gives the weight (as type double) of the edge from
   vertex i to vertex j (if G[i][j] is 0.0, then the edge does not exist).
   Note that since the graph is undirected, it is assumed that G[i][j]
   is always equal to G[j][i].


   R. Little - 03/07/2019
*/

 import edu.princeton.cs.algs4.*;
 import java.util.Scanner;
 import java.io.File;

//Do not change the name of the PrimVsKruskal class
public class PVK{

	/* PrimVsKruskal(G)
		Given an adjacency matrix for connected graph G, with no self-loops or parallel edges,
		determine if the minimum spanning tree of G found by Prim's algorithm is equal to
		the minimum spanning tree of G found by Kruskal's algorithm.

		If G[i][j] == 0.0, there is no edge between vertex i and vertex j
		If G[i][j] > 0.0, there is an edge between vertices i and j, and the
		value of G[i][j] gives the weight of the edge.
		No entries of G will be negative.
	*/

  /******************************************************************************
   *  Compilation:  javac PrimMST.java
   *  Execution:    java PrimMST filename.txt
   *  Dependencies: EdgeWeightedGraph.java Edge.java Queue.java
   *                IndexMinPQ.java UF.java In.java StdOut.java
   *  Data files:   https://algs4.cs.princeton.edu/43mst/tinyEWG.txt
   *                https://algs4.cs.princeton.edu/43mst/mediumEWG.txt
   *                https://algs4.cs.princeton.edu/43mst/largeEWG.txt
   *
   *  Compute a minimum spanning forest using Prim's algorithm.
   *
   *  %  java PrimMST tinyEWG.txt
   *  1-7 0.19000
   *  0-2 0.26000
   *  2-3 0.17000
   *  4-5 0.35000
   *  5-7 0.28000
   *  6-2 0.40000
   *  0-7 0.16000
   *  1.81000
   *
   *  % java PrimMST mediumEWG.txt
   *  1-72   0.06506
   *  2-86   0.05980
   *  3-67   0.09725
   *  4-55   0.06425
   *  5-102  0.03834
   *  6-129  0.05363
   *  7-157  0.00516
   *  ...
   *  10.46351
   *
   *  % java PrimMST largeEWG.txt
   *  ...
   *  647.66307
   *
   ******************************************************************************/

  /**
   *  The {@code PrimMST} class represents a data type for computing a
   *  <em>minimum spanning tree</em> in an edge-weighted graph.
   *  The edge weights can be positive, zero, or negative and need not
   *  be distinct. If the graph is not connected, it computes a <em>minimum
   *  spanning forest</em>, which is the union of minimum spanning trees
   *  in each connected component. The {@code weight()} method returns the
   *  weight of a minimum spanning tree and the {@code edges()} method
   *  returns its edges.
   *  <p>
   *  This implementation uses <em>Prim's algorithm</em> with an indexed
   *  binary heap.
   *  The constructor takes time proportional to <em>E</em> log <em>V</em>
   *  and extra space (not including the graph) proportional to <em>V</em>,
   *  where <em>V</em> is the number of vertices and <em>E</em> is the number of edges.
   *  Afterwards, the {@code weight()} method takes constant time
   *  and the {@code edges()} method takes time proportional to <em>V</em>.
   *  <p>
   *  For additional documentation,
   *  see <a href="https://algs4.cs.princeton.edu/43mst">Section 4.3</a> of
   *  <i>Algorithms, 4th Edition</i> by Robert Sedgewick and Kevin Wayne.
   *  For alternate implementations, see {@link LazyPrimMST}, {@link KruskalMST},
   *  and {@link BoruvkaMST}.
   *
   *  @author Robert Sedgewick
   *  @author Kevin Wayne
   */
  

	static boolean PrimVsKruskal(double[][] G){
		int n = G.length;
    PrimMST prim = new PrimMST(G);
    /* Build the MST by Prim's and the MST by Kruskal's */
    /* (You may add extra methods if necessary) */

    /* create an edge weighted graph from G */


    /*EdgeWeightedGraph EWG = new EdgeWeightedGraph(n);
    for(int i = 0; i < n; i++) {
      for(int j = 0; j < n; j++) {
        if(G[i][j] != 0) {
          EWG.addEdge(new Edge(i, j, G[i][j])); //add
          G[j][i] = 0; //stops the edge from being added multiple times
        }
      }
    }*/

		/* Determine if the MST by Prim equals the MST by Kruskal */
		boolean pvk = true;

    /*System.out.printf("EWG before prims \n");
    System.out.printf(EWG.toString());

    PrimMST prim = new PrimMST(EWG);
    double p = prim.weight();

    System.out.printf("EWG after prims \n");
    System.out.printf(EWG.toString());

    KruskalMST kruskal = new KruskalMST(EWG);
    double k = kruskal.weight();

    if(k != p) {
      pvk = false;
    }*/

		return pvk;
	}

	/* main()
	   Contains code to test the PrimVsKruskal function. You may modify the
	   testing code if needed, but nothing in this function will be considered
	   during marking, and the testing process used for marking will not
	   execute any of the code below.
	*/
   public static void main(String[] args) {
		Scanner s;
		if (args.length > 0){
			try{
				s = new Scanner(new File(args[0]));
			} catch(java.io.FileNotFoundException e){
				System.out.printf("Unable to open %s\n",args[0]);
				return;
			}
			System.out.printf("Reading input values from %s.\n",args[0]);
		}else{
			s = new Scanner(System.in);
			System.out.printf("Reading input values from stdin.\n");
		}

		int n = s.nextInt();
		double[][] G = new double[n][n];
		int valuesRead = 0;
		for (int i = 0; i < n && s.hasNextDouble(); i++){
			for (int j = 0; j < n && s.hasNextDouble(); j++){
				G[i][j] = s.nextDouble();
				if (i == j && G[i][j] != 0.0) {
					System.out.printf("Adjacency matrix contains self-loops.\n");
					return;
				}
				if (G[i][j] < 0.0) {
					System.out.printf("Adjacency matrix contains negative values.\n");
					return;
				}
				if (j < i && G[i][j] != G[j][i]) {
					System.out.printf("Adjacency matrix is not symmetric.\n");
					return;
				}
				valuesRead++;
			}
		}

		if (valuesRead < n*n){
			System.out.printf("Adjacency matrix for the graph contains too few values.\n");
			return;
		}

        boolean pvk = PrimVsKruskal(G);
        System.out.printf("Does Prim MST = Kruskal MST? %b\n", pvk);
  }
}
