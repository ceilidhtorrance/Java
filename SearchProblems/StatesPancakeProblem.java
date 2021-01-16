/*
 * Ceilidh Torrance
 * V00885432
*/

public class StatesPancakeProblem
{
	int N;
    int[] pancakeArray;

    public StatesPancakeProblem(int[] pancakeArray) {
    	this.pancakeArray = pancakeArray;
    	N = pancakeArray.length;
    }

    public StatesPancakeProblem(StatesPancakeProblem state) {
    	N = state.N;
    	pancakeArray = new int[N];

        for(int i=0; i<N; i++){
					pancakeArray[i] = state.pancakeArray[i];
				}
    }

    public boolean equals(Object o) {
        return java.util.Arrays.equals( pancakeArray, ((StatesPancakeProblem) o).pancakeArray );
    }

    public int hashCode() {
        return java.util.Arrays.hashCode( pancakeArray );
    }

    public String toString() {
    	return java.util.Arrays.toString( pancakeArray );
    }
}
