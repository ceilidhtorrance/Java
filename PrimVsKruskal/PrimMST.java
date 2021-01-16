import edu.princeton.cs.algs4.*;
import java.util.Scanner;
import java.io.File;

public class PrimMST {
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

/*original code for refrence
        for (Edge e : G.adj(V)) {}
            int w = e.other(v);
            if (marked[w]) continue;         // v-w is obsolete edge
            if (e.weight() < distTo[w]) {
                distTo[w] = e.weight();
                edgeTo[w] = e;
                if (pq.contains(w)) pq.decreaseKey(w, distTo[w]);
                else                pq.insert(w, distTo[w]);
            }
        }*/
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

      PrimMST prim = new PrimMST(G);
      double weight = prim.weight();
      System.out.println(weight);

      //System.out.printf("weight: %d", prim.weight());
      /*for(Edge e : prim.edges()) {
        System.out.printf("print f works in loop");
        System.out.println("println works in loop");
        System.out.printf("%s", e.toString());
      }*/

        /*In in = new In(args[0]);
        EdgeWeightedGraph G = new EdgeWeightedGraph(in);
        PrimMST mst = new PrimMST(G);
        for (Edge e : mst.edges()) {
            StdOut.println(e);
        }
        StdOut.printf("%.5f\n", mst.weight());*/
    }
}
