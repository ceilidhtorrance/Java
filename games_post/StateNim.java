/*
 * Ceilidh Torrance
 * V00885432
*/

/*************************************************************************************************************
* Title: StateTicTacToe.java
* Author: Alex Thomo
* Date: 2020
* Code Version: 1.0
* Avalibility: https://connex.csc.uvic.ca/portal/site/892ec737-37e7-437e-99b8-5e80411b77e4/tool/19f873bd-85d8-4e75-9c9b-8568cf1e4242?panel=Main
*************************************************************************************************************/
//Code is similar to StateTicTacToe.java so it was cited

public class StateNim extends State {

    public char board[] = new char [13];

    public StateNim() {

        for (int i=0; i<13; i++)
            board[i] = 'O';

        player = 1;
    }

    public StateNim(StateNim state) {

        for(int i=0; i<13; i++)
            this.board[i] = state.board[i];

        player = state.player;
    }

    public String toString() {

    	String ret = "";

    	for(int i=0; i<13; i++) {
        ret+=board[i] + ", ";
    	}
    	return ret;
    }
}
