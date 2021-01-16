/* BaseballElimination.java
   CSC 226 - Spring 2019
   Assignment 4 - Baseball Elimination Program

   This template includes some testing code to help verify the implementation.
   To interactively provide test inputs, run the program with
	java BaseballElimination

   To conveniently test the algorithm with a large input, create a text file
   containing one or more test divisions (in the format described below) and run
   the program with
	java -cp .;algs4.jar BaseballElimination file.txt (Windows)
   or
    java -cp .:algs4.jar BaseballElimination file.txt (Linux or Mac)
   where file.txt is replaced by the name of the text file.

   The input consists of an integer representing the number of teams in the division and then
   for each team, the team name (no whitespace), number of wins, number of losses, and a list
   of integers represnting the number of games remaining against each team (in order from the first
   team to the last). That is, the text file looks like:

	<number of teams in division>
	<team1_name wins losses games_vs_team1 games_vs_team2 ... games_vs_teamn>
	...
	<teamn_name wins losses games_vs_team1 games_vs_team2 ... games_vs_teamn>


   An input file can contain an unlimited number of divisions but all team names are unique, i.e.
   no team can be in more than one division.


   R. Little - 03/22/2019
*/

import edu.princeton.cs.algs4.*;
import java.util.*;
import java.io.File;

//Do not change the name of the BaseballElimination class
public class BaseballElimination{

	// We use an ArrayList to keep track of the eliminated teams.
	public ArrayList<String> eliminated = new ArrayList<String>();

	/* BaseballElimination(s)
		Given an input stream connected to a collection of baseball division
		standings we determine for each division which teams have been eliminated
		from the playoffs. For each team in each division we create a flow network
		and determine the maxflow in that network. If the maxflow exceeds the number
		of inter-divisional games between all other teams in the division, the current
		team is eliminated.
	*/
	public BaseballElimination(Scanner s){

		int teams = s.nextInt();
		int verticies = teams*teams+2;
		int sink = verticies-1;
		int start = 0;

		int[][] teamStats = new int[teams][2]; //stores teams wins and games left
		int[][] gamesLeft = new int[teams][teams]; //stores teams games left against another team
		String[] teamName = new String[teams]; //stores team names


/*storing the team stats so that they are easier to access for putting them into the flow network*/
		for(int i = 0; i < teams; i++) {
			teamName[i] = s.next();
			teamStats[i][0] = s.nextInt();
			teamStats[i][1] = s.nextInt();
			for(int j = 0; j < teams; j++) {
				gamesLeft[i][j] = s.nextInt();
			}
		}

		int top = 0;

		for(int i = 1; i < teams; i++) {
			if(teamStats[i][0] > teamStats[top][0]) {
				top = i;
			}
		}

		for(int x = 0; x < teams; x++) {
			/*if team x cannot possibly win as many games as the leading team, they are immediatly eliminated*/
				if(teamStats[top][0] > teamStats[x][0] + teamStats[x][1]) {
					eliminated.add(teamName[x]);
					continue;
				}

			/*create a new FlowNetwork*/
					FlowNetwork G = new FlowNetwork(verticies);
			/*max wins for team x*/
					double W = teamStats[x][0] + teamStats[x][1];

			/*Edges from teams to sink, excluding team x*/
					for(int i = 0; i < teams; i++) {
						if(i != x) {
							FlowEdge e = new FlowEdge(i+1, sink, W - teamStats[i][0]);
							G.addEdge(e);
						}
					}

					int pairVertex = teams+1;
					double infinity = Double.POSITIVE_INFINITY;
					double capacity = 0.0;

			/*Edges from start to pair verticies as well and edges from pair verticies to team verticies, excluding team x*/
					for(int i = 0; i < teams; i++) {
						for(int j = i; j < teams; j++) {
							if(i != x && j != x && i != j) {
								double weight = gamesLeft[i][j];
								FlowEdge e = new FlowEdge(start, pairVertex, weight);
								FlowEdge pairToi = new FlowEdge(pairVertex, i+1, infinity);
								FlowEdge pairToj = new FlowEdge(pairVertex, j+1, infinity);
								G.addEdge(e);
								G.addEdge(pairToi);
								G.addEdge(pairToj);
								capacity += weight;
								pairVertex++;
							}
						}
					}

					FordFulkerson flowG = new FordFulkerson(G, start, sink);
					double maxFlowG = flowG.value();
					if(maxFlowG != capacity) {
						eliminated.add(teamName[x]);
					}
		}
		int x = 1;
	}

	/* main()
	   Contains code to test the BaseballElimantion function. You may modify the
	   testing code if needed, but nothing in this function will be considered
	   during marking, and the testing process used for marking will not
	   execute any of the code below.
	*/
	public static void main(String[] args){
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

		BaseballElimination be = new BaseballElimination(s);

		if (be.eliminated.size() == 0)
			System.out.println("No teams have been eliminated.");
		else
			System.out.println("Teams eliminated: " + be.eliminated);
	}
}
