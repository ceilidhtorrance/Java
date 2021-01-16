/*
 * Ceilidh Torrance
 * V00885432
*/

import java.util.HashSet;
import java.util.Set;

public class PancakeProblem extends Problem {

	//goal test checks to see if pancakes are in order from smallest to largest given the current state of the array of pancakes
	boolean goal_test(Object state) {
        StatesPancakeProblem puzzle_state = (StatesPancakeProblem) state;

				//smallest pancake is 1 so starts there and works its way up
        int k=1;
        for(int i=0; i<puzzle_state.N; i++) {
					if(puzzle_state.pancakeArray[i] != k) {
						return false;
					}
					k++;
				}
        return true;
	}

		//getSuccessors finds all the possible next states for the pancakeArray
    Set<Object> getSuccessors(Object state) {

        Set<Object> set = new HashSet<Object>();
        StatesPancakeProblem s = (StatesPancakeProblem) state;

        StatesPancakeProblem ss; //successor state

				//finds each possible switch that can be made, creates the new array, then adds it to the list
				for(int i = 1; i < s.N; i++) {
					ss = new StatesPancakeProblem(s);
					int flip = 0;
					for(int j = i; j > i/2; j--) {
						ss.pancakeArray[flip] =  s.pancakeArray[j];
						ss.pancakeArray[j] = s.pancakeArray[flip];
						flip++;
					}
					set.add(ss);
				}
        return set;
    }

	//cost is 1 because it takes 1 flip
	double step_cost(Object fromState, Object toState) { return 1; }

	public double h(Object state) { return 0; }


	public static void main(String[] args) throws Exception {
		PancakeProblem problem = new PancakeProblem();
		int[] pancakeArray = {4, 1, 8, 6, 5, 2, 3, 7};
		problem.initialState = new StatesPancakeProblem(pancakeArray);

		Search search  = new Search(problem);

		System.out.println("\n\nTEST 1 WITH STACK: {4, 1, 8, 6, 5, 2, 3, 7}\n");
		System.out.println("\n\nGraphSearch----------------------");
		System.out.println("BreadthFirstGraphSearch:\t" + search.BreadthFirstGraphSearch());
		System.out.println("UniformCostGraphSearch:\t\t" + search.UniformCostGraphSearch());
		System.out.println("AstarGraphSearch:\t\t" + search.AstarGraphSearch());

		System.out.println("\n\nIterativeDeepening----------------------");
		System.out.println("IterativeDeepeningGraphSearch:\t" + search.IterativeDeepeningGraphSearch());

	 	PancakeProblem problem2 = new PancakeProblem();
		int[] pancakeArray2 = {1, 5, 7, 2, 6, 3, 4};
		problem2.initialState = new StatesPancakeProblem(pancakeArray2);

		Search search2  = new Search(problem2);

		System.out.println("\nTEST 2 WITH STACK: {1, 5, 7, 2, 6, 3, 4}\n");
		System.out.println("\n\nGraphSearch----------------------");
		//System.out.println("BreadthFirstGraphSearch:\t" + search2.BreadthFirstGraphSearch());
		System.out.println("UniformCostGraphSearch:\t\t" + search2.UniformCostGraphSearch());
		System.out.println("AstarGraphSearch:\t\t" + search2.AstarGraphSearch());

		System.out.println("\n\nIterativeDeepening----------------------");
		System.out.println("IterativeDeepeningGraphSearch:\t" + search2.IterativeDeepeningGraphSearch() + "\n\n");
	}

}
