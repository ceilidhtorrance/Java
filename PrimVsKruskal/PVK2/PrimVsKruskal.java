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
public class PrimVsKruskal{

  static class KruskalMST {
      private static final double FLOATING_POINT_EPSILON = 1E-12;

      private double weight;                        // weight of MST
      private Queue<Edge> mst = new Queue<Edge>();  // edges in MST

      /**
       * Compute a minimum spanning tree (or forest) of an edge-weighted graph.
       * @param G the edge-weighted graph
       */
      public KruskalMST(double[][] G) {
          // more efficient to build heap by passing array of edges
          boolean[][] added = new boolean[G.length][G.length];
          MinPQ<Edge> pq = new MinPQ<Edge>();
          for(int i = 0; i < G.length; i++) {
            for(int j = 0; j < G.length; j++) {
              if(G[i][j] != 0 && !added[i][j]) {
                pq.insert(new Edge(i, j, G[i][j]));
                added[j][i] = true;
                added[i][j] = true;
              }
            }
          }

          /*for (Edge e : G.edges()) {
              pq.insert(e);
          } commented out original code for refrence*/

          // run greedy algorithm
          UF uf = new UF(G.length);
          while (!pq.isEmpty() && mst.size() < G.length - 1) {
              Edge e = pq.delMin();
              int v = e.either();
              int w = e.other(v);
              if (!uf.connected(v, w)) { // v-w does not create a cycle
                  uf.union(v, w);  // merge v and w components
                  mst.enqueue(e);  // add edge e to mst
                  weight += e.weight();
              }
          }

          // check optimality conditions
          assert check(G);
      }

      /**
       * Returns the edges in a minimum spanning tree (or forest).
       * @return the edges in a minimum spanning tree (or forest) as
       *    an iterable of edges
       */
      public Iterable<Edge> edges() {
          return mst;
      }

      /**
       * Returns the sum of the edge weights in a minimum spanning tree (or forest).
       * @return the sum of the edge weights in a minimum spanning tree (or forest)
       */
      public double weight() {
          return weight;
      }

      // check optimality conditions (takes time proportional to E V lg* V)
      private boolean check(double[][] G) {

          // check total weight
          double total = 0.0;
          for (Edge e : edges()) {
              total += e.weight();
          }
          if (Math.abs(total - weight()) > FLOATING_POINT_EPSILON) {
              System.err.printf("Weight of edges does not equal weight(): %f vs. %f\n", total, weight());
              return false;
          }

          // check that it is acyclic
          UF uf = new UF(G.length);
          for (Edge e : edges()) {
              int v = e.either(), w = e.other(v);
              if (uf.connected(v, w)) {
                  System.err.println("Not a forest");
                  return false;
              }
              uf.union(v, w);
          }

          // check that it is a spanning forest
          for(int i = 0; i < G.length; i++) {
            for(int j = 0; j < G.length; j++) {
              if(G[i][j] != 0) {
                if(!uf.connected(i, j)) {
                  System.err.println("Not a spanning forest");
                  return false;
                }
              }
            }
          }



          // check that it is a minimal spanning forest (cut optimality conditions)
          for(int i = 0; i < G.length; i++) {
            for(int j = 0; j < G.length; j++) {
              if(G[i][j] != 0) {
                Edge e = new Edge(i, j, G[i][j]);
                // all edges in MST except e
                uf = new UF(G.length);
                for (Edge f : mst) {
                    int x = f.either(), y = f.other(x);
                    if (f != e) uf.union(x, y);
                    if (!uf.connected(x, y)) {
                            if (f.weight() < e.weight()) {
                                System.err.println("Edge " + f + " violates cut optimality conditions");
                                return false;
                            }
                        }
                } // check that e is min weight edge in crossing cut
              }
            }
          }
          return true;
      }
    }

  static class PrimMST {
      private static final double FLOATING_POINT_EPSILON = 1E-12;

      private Edge[] edgeTo;        // edgeTo[v] = shortest edge from tree vertex to non-tree vertex
      private double[] distTo;      // distTo[v] = weight of shortest such edge
      private boolean[] marked;     // marked[v] = true if v on tree, false otherwise
      private IndexMinPQ<Double> pq;

      /**
       * Compute a minimum spanning tree (or forest) of an edge-weighted graph.
       * @param G the edge-weighted graph
       */
      public PrimMST(double[][] G) { //complete??
          int vertices = G.length;
          edgeTo = new Edge[vertices];
          distTo = new double[vertices];
          marked = new boolean[vertices];
          pq = new IndexMinPQ<Double>(vertices);
          for (int v = 0; v < vertices; v++)
              distTo[v] = Double.POSITIVE_INFINITY;

          for (int v = 0; v < vertices; v++)      // run from each vertex to find
              if (!marked[v]) prim(G, v);      // minimum spanning forest

          // check optimality conditions
          assert check(G);
      }

      // run Prim's algorithm in graph G, starting from vertex s
      private void prim(double[][] G, int s) { //complete??
          distTo[s] = 0.0;
          pq.insert(s, distTo[s]);
          while (!pq.isEmpty()) {
              int v = pq.delMin();
              scan(G, v);
          }
      }

      // scan vertex v
      private void scan(double[][] G, int v) { //complete??
          marked[v] = true;
          for(int w = 0; w < G.length; w++) {
            if(marked[w] || G[v][w] == 0) {
              continue;
            }
            if(G[v][w] < distTo[w]) {
              distTo[w] = G[v][w];
              edgeTo[w] = new Edge(v, w, G[v][w]);
              if(pq.contains(w)) pq.decreaseKey(w, distTo[w]);
              else               pq.insert(w, distTo[w]);
            }
          }
      }

      /**
       * Returns the edges in a minimum spanning tree (or forest).
       * @return the edges in a minimum spanning tree (or forest) as
       *    an iterable of edges
       */
      public Iterable<Edge> edges() { //no changes??
          Queue<Edge> mst = new Queue<Edge>();
          for (int v = 0; v < edgeTo.length; v++) {
              Edge e = edgeTo[v];
              if (e != null) {
                  mst.enqueue(e);
              }
          }
          return mst;
      }

      /**
       * Returns the sum of the edge weights in a minimum spanning tree (or forest).
       * @return the sum of the edge weights in a minimum spanning tree (or forest)
       */
      public double weight() { //no changes??
          double weight = 0.0;
          for (Edge e : edges())
              weight += e.weight();
          return weight;
      }


      // check optimality conditions (takes time proportional to E V lg* V)
      private boolean check(double[][] G) {

          // check weight
          double totalWeight = 0.0;
          for (Edge e : edges()) {
              totalWeight += e.weight();
          }
          if (Math.abs(totalWeight - weight()) > FLOATING_POINT_EPSILON) {
              System.err.printf("Weight of edges does not equal weight(): %f vs. %f\n", totalWeight, weight());
              return false;
          }

          // check that it is acyclic
          UF uf = new UF(G.length);
          for (Edge e : edges()) {
              int v = e.either(), w = e.other(v);
              if (uf.connected(v, w)) {
                  System.err.println("Not a forest");
                  return false;
              }
              uf.union(v, w);
          }

          // check that it is a spanning forest
          /*for (Edge e : G.edges()) {
              int v = e.either(), w = e.other(v);
              if (!uf.connected(v, w)) {
                  System.err.println("Not a spanning forest");
                  return false;
              }
          }*/

          // check that it is a minimal spanning forest (cut optimality conditions)
          for (Edge e : edges()) {

              // all edges in MST except e
              uf = new UF(G.length);
              for (Edge f : edges()) {
                  int x = f.either(), y = f.other(x);
                  if (f != e) uf.union(x, y);
              }

              // check that e is min weight edge in crossing cut
              /*for (Edge f : G.edges()) {
                  int x = f.either(), y = f.other(x);
                  if (!uf.connected(x, y)) {
                      if (f.weight() < e.weight()) {
                          System.err.println("Edge " + f + " violates cut optimality conditions");
                          return false;
                      }
                  }
              }*/

          }

          return true;
      }

      public String toString() {
          StringBuilder s = new StringBuilder();
          for (Edge e : edges()) {
              s.append(e + "\n");
          }
          return s.toString();
      }

  }


	/* PrimVsKruskal(G)
		Given an adjacency matrix for connected graph G, with no self-loops or parallel edges,
		determine if the minimum spanning tree of G found by Prim's algorithm is equal to
		the minimum spanning tree of G found by Kruskal's algorithm.

		If G[i][j] == 0.0, there is no edge between vertex i and vertex j
		If G[i][j] > 0.0, there is an edge between vertices i and j, and the
		value of G[i][j] gives the weight of the edge.
		No entries of G will be negative.
	*/
	static boolean PrimVsKruskal(double[][] G){
		int n = G.length;

		/* Build the MST by Prim's and the MST by Kruskal's */
		/* (You may add extra methods if necessary) */

		double[][] P = G;
    double[][] K = G;

    PrimMST prim = new PrimMST(P);
    KruskalMST kruskal = new KruskalMST(K);

		/* Determine if the MST by Prim equals the MST by Kruskal */
		boolean pvk = true;
    /*figure out if both trees contain the same edges*/
    for(Edge p: prim.edges()) {
      int p1 = p.either();
      int p2 = p.other(p1);
      boolean found = false;
      for(Edge k: kruskal.edges()) {
        int k1 = k.either();
        int k2 = k.other(k1);
        if((k1 == p1 && k2 == p2 )||(k1 == p2 && k2 == p1)) {
          found = true;
        }
      }
      if(found != true) return false;   //return false if no edge in kruskal to match an edge in prim
    }

		if(prim.weight() != kruskal.weight()) return false;   //return false if weights are different

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
