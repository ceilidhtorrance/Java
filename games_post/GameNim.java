/*
 * Ceilidh Torrance
 * V00885432
*/

/*************************************************************************************************************
* Title: GameTicTacToe.java
* Author: Alex Thomo
* Date: 2020
* Code Version: 1.0
* Avalibility: https://connex.csc.uvic.ca/portal/site/892ec737-37e7-437e-99b8-5e80411b77e4/tool/19f873bd-85d8-4e75-9c9b-8568cf1e4242?panel=Main
*************************************************************************************************************/
//Code is similar to GameTicTacToe.java so it was cited

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.HashSet;
import java.util.Set;

public class GameNim extends Game {

    int WinningScore = 10;
    int LosingScore = -10;
    int NeutralScore = 0;

    public GameNim() {
    	currentState = new StateNim();
    }

    //checks to see if the state is a winning state
    public boolean isWinState(State state)
    {
      StateNim tstate = (StateNim) state;
      for(int i = 0; i < 13; i++) {
        if(tstate.board[i] != 'X') {
          return false;
        }
      }
      return true;
    }

    //there are no stuck states since the players can't tie
    public boolean isStuckState(State state) {
      return false;
    }


	public Set<State> getSuccessors(State state)
    {
		if(isWinState(state) || isStuckState(state)) {
      return null;
    }

		Set<State> successors = new HashSet<State>();
    StateNim tstate = (StateNim) state;

    StateNim successor_state;

    char mark = 'X';
    int i = 0;

    while(tstate.board[i] != 'O' && i < 13) {
      i++;
    }

    successor_state = new StateNim(tstate);
    for(int j = 0; j < 3; j++) {
      if(i+j < 13) {
        successor_state.board[i+j] = mark;
        successor_state.player = (state.player==0 ? 1 : 0);

        successors.add(successor_state);
      }
    }
      return successors;
    }

    public double eval(State state)
    {
      if(isWinState(state)) {
    		//player who made last move
    		int previous_player = (state.player==0 ? 1 : 0);

	    	if (previous_player==0) //computer wins
	            return WinningScore;
	    	else //human wins
	            return LosingScore;
    	}

        return NeutralScore;
    }


    public static void main(String[] args) throws Exception {

        Game game = new GameNim();
        Search search = new Search(game);
        int depth = 12;

        System.out.println("O's are coins, X's are removed coins ");
        //needed to get human's move
        BufferedReader in = new BufferedReader(new InputStreamReader(System.in));

        while (true) {

        	StateNim 	nextState = null;

            switch ( game.currentState.player ) {
              case 1: //Human
            	  //get human's move
                  System.out.print("How many objects would you like to take? (enter 1, 2, or 3) ");
                  int pos = Integer.parseInt( in.readLine() );

                  nextState = new StateNim((StateNim)game.currentState);
                  nextState.player = 1;
                  int track = 0;
                  while(nextState.board[track] != 'O'){
                    track++;
                  }

                  for(int i = track; i < (pos+track); i++) {
                    if(i < 13) {
                      nextState.board[i] = 'X';
                    }
                  }
                  track+=pos;
                  System.out.println("Human: \n" + nextState);
                  break;

              case 0: //Computer
            	  nextState = (StateNim)search.bestSuccessorState(depth);
            	  nextState.player = 0;
            	  System.out.println("Computer: \n" + nextState);
                  break;
            }

            game.currentState = nextState;
            //change player
            game.currentState.player = (game.currentState.player==0 ? 1 : 0);

            //Who wins?
            if ( game.isWinState(game.currentState) ) {

            	if (game.currentState.player == 0) //i.e. last move was by the human
            		System.out.println("Computer wins!");
            	else
            		System.out.println("You win!");

            	break;
            }
        }
    }
}
