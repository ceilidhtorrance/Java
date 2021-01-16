/*
 * Ceilidh Torrance
 * V00885432
*/

/*************************************************************************************************************
* Title: ProblemMap.java
* Author: Alex Thomo
* Date: 2020
* Code Version: 1.0
* Avalibility: https://connex.csc.uvic.ca/portal/site/892ec737-37e7-437e-99b8-5e80411b77e4/tool/19f873bd-85d8-4e75-9c9b-8568cf1e4242
*************************************************************************************************************/
//Code is similar to Problem.java so it was cited

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class WGCProblem extends Problem {
	Map<String, Map<String, Double>> map;
	Map<String, Double> sld;

	public Object goalState;

	public WGCProblem(String mapfilename) throws Exception {
		map = new HashMap<String, Map<String, Double>>();
		//read map from file of source-destination-cost triples (tab separated)
    	BufferedReader reader = new BufferedReader( new FileReader (mapfilename));
        String line;
        while( ( line = reader.readLine() ) != null ) {
        	String[] strA = line.split("\t");

        	String 	from_state = strA[0],
        			to_state   = strA[1];
        	Double 	cost 	  = Double.parseDouble(strA[2]);

        	if(!map.containsKey(from_state))
        		map.put(from_state, new HashMap<String, Double>());
        	map.get(from_state).put(to_state,cost);

        	//putting the reverse edge as well
        	if(!map.containsKey(to_state))
        		map.put(to_state, new HashMap<String, Double>());
        	map.get(to_state).put(from_state,cost);
        }
        reader.close();
	}

	public WGCProblem(String mapfilename, String heuristicfilename) throws Exception {
		this(mapfilename);

		sld = new HashMap<String, Double>();
    	BufferedReader reader = new BufferedReader( new FileReader (heuristicfilename));
        String line;
        while( ( line = reader.readLine() ) != null ) {
        	String[] strA = line.split("\t");

        	String 	state = strA[0];
        	double 	h 	 = Double.parseDouble(strA[1]);

        	sld.put(state, h);
        }
        reader.close();
	}

	boolean goal_test(Object state) {
		return state.equals(goalState);
	}

	Set<Object> getSuccessors(Object state) {
		Set<Object> result = new HashSet<Object>();
		for(Object successor_state : map.get(state).keySet())
			result.add(successor_state);
		return result;
	}

	double step_cost(Object fromState, Object toState) {
		return map.get(fromState).get(toState);
	}

	public double h(Object state) {
		return sld.get(state);
	}

	public static void main(String[] args) throws Exception {
		WGCProblem problem = new WGCProblem("WGC.txt","WGCdist.txt");
		problem.initialState = "WGCY";
		problem.goalState = "N";

		Search search  = new Search(problem);

    System.out.println("\n\nThe states are the items on the starting side of the river, so the goal state is N and the start state is WGCY.");
    System.out.println("Abreviations are as follows:\n W = Wolf \n G = Goat \n C = Cabbage \n Y = Driver \n N = None left");

		System.out.println("TreeSearch------------------------");
		System.out.println("BreadthFirstTreeSearch:\t\t" + search.BreadthFirstTreeSearch());
		System.out.println("UniformCostTreeSearch:\t\t" + search.UniformCostTreeSearch());
		System.out.println("DepthFirstTreeSearch:\t\t" + search.DepthFirstTreeSearch());
		System.out.println("GreedyBestFirstTreeSearch:\t" + search.GreedyBestFirstTreeSearch());
		System.out.println("AstarTreeSearch:\t\t" + search.AstarTreeSearch());

		System.out.println("\n\nGraphSearch----------------------");
		System.out.println("BreadthFirstGraphSearch:\t" + search.BreadthFirstGraphSearch());
		System.out.println("UniformCostGraphSearch:\t\t" + search.UniformCostGraphSearch());
		System.out.println("DepthFirstGraphSearch:\t\t" + search.DepthFirstGraphSearch());
		System.out.println("GreedyBestGraphSearch:\t\t" + search.GreedyBestFirstGraphSearch());
		System.out.println("AstarGraphSearch:\t\t" + search.AstarGraphSearch());

		System.out.println("\n\nIterativeDeepening----------------------");
		System.out.println("IterativeDeepeningTreeSearch:\t" + search.IterativeDeepeningTreeSearch());
		System.out.println("IterativeDeepeningGraphSearch:\t" + search.IterativeDeepeningGraphSearch()+"\n\n");
	}
}
