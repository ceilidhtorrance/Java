/*
 * Ceilidh Torrance
 * V00885432
*/

/*************************************************************************************************************
* Title: CSPZebra.java
* Author: Alex Thomo
* Date: 2020
* Code Version: 1.0
* Avalibility: https://connex.csc.uvic.ca/portal/site/892ec737-37e7-437e-99b8-5e80411b77e4/tool/19f873bd-85d8-4e75-9c9b-8568cf1e4242?panel=Main
*************************************************************************************************************/
//Code is similar to CSPZebra.java so it was cited

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

public class CSPBikeRidingPuzzle extends CSP {

	static Set<Object> varBike = new HashSet<Object>(Arrays.asList(new String[] {"black", "blue", "green", "red", "white"}));
	static Set<Object> varName = new HashSet<Object>(Arrays.asList(new String[] {"Adrian", "Charles", "Henry", "Joel", "Richard"}));
	static Set<Object> varSan = new HashSet<Object>(Arrays.asList(new String[] {"bacon", "chicken", "cheese", "pepperoni", "tuna"}));
	static Set<Object> varJui = new HashSet<Object>(Arrays.asList(new String[] {"apple", "cranberry", "grapefruit", "orange", "pineapple"}));
	static Set<Object> varAge = new HashSet<Object>(Arrays.asList(new String[] {"12", "13", "14", "15", "16"}));
	static Set<Object> varSpt = new HashSet<Object>(Arrays.asList(new String[] {"baseball", "basketball", "hockey", "soccer", "swimming"}));


	public boolean isGood(Object X, Object Y, Object x, Object y) {
		if(!C.containsKey(X))
			return true;

		if(!C.get(X).contains(Y))
			return true;

		//the 16-year-old brought cheese sandwhich
		if(X.equals("16") && Y.equals("cheese") && !x.equals(y))
			return false;

		//the boy who likes the sport played on ice is going to eat pepperoni sandwhich
		if(X.equals("hockey") && Y.equals("pepperoni") && !x.equals(y))
			return false;

		/* BESIDE */
		//Henry is exactly to the left of the soccer fan
		if(X.equals("Henry") && Y.equals("soccer") && (Integer)x-(Integer)y!=-1)
			return false;

		//the one who likes swimming is next to the friend who likes baseball
		if(X.equals("swimming") && Y.equals("baseball") && Math.abs((Integer)x-(Integer)y)!=1)
			return false;

		//the baseball fan is next to the boy who is going to drink apple juice
		if(X.equals("baseball") && Y.equals("apple") && Math.abs((Integer)x-(Integer)y)!=1)
			return false;

		//Adrian is exactly to teh left of the boy who is going to eat pepperoni sandwhich
		if(X.equals("Adrian") && Y.equals("pepperoni") && (Integer)x-(Integer)y!=-1)
			return false;

		//joel is next to the 16-year-old cyclist
		if(X.equals("Joel") && Y.equals("16") && Math.abs((Integer)x-(Integer)y)!=1)
			return false;

		/* SOMEWHERE TO THE SIDE */
		//the boy who is going to eat bacon sandwhich is somewhere to the right of the owner of the white bicycle
		if(X.equals("bacon") && Y.equals("white") && ((Integer)x-(Integer)y) < 1)
			return false;

		/* BETWEEN */
		//The owner of the white bike is somewhere between the 15-year-old boy and the youngest boy, in that order
		if(X.equals("white") && Y.equals("15") && (Integer)x-(Integer)y < 1)
			return false;

		if(X.equals("white") && Y.equals("12") && (Integer)x-(Integer)y > -1)
			return false;

		//the boy who is going to drink grapefruit juice is somewhere between who brought tuna sandwhich and who brought pineapple juice, in that order
		if(X.equals("grapefruit") && Y.equals("tuna") && (Integer)x-(Integer)y < 1)
			return false;

		if(X.equals("grapefruit") && Y.equals("pineapple") && (Integer)x-(Integer)y > -1)
			return false;

		//charles is somewhere between Richard and Adrian, in that order
		if(X.equals("Charles") && Y.equals("Richard") && (Integer)x-(Integer)y < 1)
			return false;

		if(X.equals("Charles") && Y.equals("Adrian") && (Integer)x-(Integer)y > -1)
			return false;

		//the cyclist riding the white bike is somewhere between richar and the boy riding the red bike, in that order
		if(X.equals("white") && Y.equals("Richard") && (Integer)x-(Integer)y < 1)
			return false;

		if(X.equals("white") && Y.equals("red") && (Integer)x-(Integer)y > -1)
			return false;

		//the 12-year-old is somewhere between the 14-year-old and the oldest boy, in that order
		if(X.equals("12") && Y.equals("14") && (Integer)x-(Integer)y < 1)
			return false;

		if(X.equals("white") && Y.equals("16") && (Integer)x-(Integer)y > -1)
			return false;

		//the boy riding the white bike is somewhere between the boys riding the blue and the black bicycles, in that order
		if(X.equals("white") && Y.equals("blue") && (Integer)x-(Integer)y < 1)
			return false;

		if(X.equals("white") && Y.equals("black") && (Integer)x-(Integer)y > -1)
			return false;

		//the cyclist that brought pineapple juice is somewhere between the 14-year-old and the boy that brought orange juice, in that order
		if(X.equals("pineapple") && Y.equals("14") && (Integer)x-(Integer)y < 1)
			return false;

		if(X.equals("pinapple") && Y.equals("orange") && (Integer)x-(Integer)y > -1)
			return false;

		//Uniqueness Constraints
		if(varBike.contains(X) && varBike.contains(Y) && !X.equals(Y) && x.equals(y))
			return false;

		if(varName.contains(X) && varName.contains(Y) && !X.equals(Y) && x.equals(y))
			return false;

		if(varSan.contains(X) && varSan.contains(Y) && !X.equals(Y) && x.equals(y))
			return false;

		if(varJui.contains(X) && varJui.contains(Y) && !X.equals(Y) && x.equals(y))
			return false;

		if(varAge.contains(X) && varAge.contains(Y) && !X.equals(Y) && x.equals(y))
			return false;

		if(varSpt.contains(X) && varSpt.contains(Y) && !X.equals(Y) && x.equals(y))
			return false;

		return true;
	}

	public static void main(String[] args) throws Exception {
		CSPBikeRidingPuzzle csp = new CSPBikeRidingPuzzle();

		Integer[] dom = {1, 2, 3, 4, 5};

		for(Object X: varBike)
			csp.addDomain(X, dom);

		for(Object X: varName)
			csp.addDomain(X, dom);

		for(Object X: varSan)
			csp.addDomain(X, dom);

		for(Object X: varJui)
			csp.addDomain(X, dom);

		for(Object X: varAge)
			csp.addDomain(X, dom);

		for(Object X: varSpt)
			csp.addDomain(X, dom);

		//unary constraints: remove values from domains

		//in the middle is the boy that likes basketball
		//The boy riding the black bike is at the third position
		for(int i = 1; i <= 5; i++) {
			if(i != 3) {
				csp.D.get("baseball").remove(i);
				csp.D.get("black").remove(i);
			}
		}

		//The boy that is going to drink pineapple juice is at the fourth position
		for(int i = 1; i <= 5; i++) {
			if(i != 4) {
				csp.D.get("pineapple").remove(i);
			}
		}

		//the boy who likes hockey is at the fift position
		//in the fifth position is the 13-year-old boy
		for(int i = 1; i <=5; i++) {
			if(i != 5) {
				csp.D.get("hockey").remove(i);
				csp.D.get("13").remove(i);
			}
		}

		//in one of the ends is the boy riding the green bicycle
		//the cyclist who is going to eat tuna sandwich is at one of the ends
		for(int i = 1; i <= 5; i++) {
			if(i != 1 && i != 5) {
				csp.D.get("green").remove(i);
				csp.D.get("tuna").remove(i);
			}
		}

		//the 16-year-old brought cheese sandwhich
		csp.addBidirectionalArc("16","cheese");

		//the boy who likes the sport played on ice is going to eat pepperoni sandwhich
		csp.addBidirectionalArc("hockey","pepperoni");

		//Henry is exactly to the left of the soccer fan
		csp.addBidirectionalArc("Henry","soccer");

		//the one who likes swimming is next to the friend who likes baseball
		csp.addBidirectionalArc("swimming","baseball");

		//the baseball fan is next to the boy who is going to drink apple juice
		csp.addBidirectionalArc("baseball","apple");

		//Adrian is exactly to teh left of the boy who is going to eat pepperoni sandwhich
		csp.addBidirectionalArc("Adrian","pepperoni");

		//joel is next to the 16-year-old cyclist
		csp.addBidirectionalArc("Joel","16");

		//the boy who is going to eat bacon sandwhich is somewhere to the right of the owner of the white bicycle
		csp.addBidirectionalArc("bacon","white");

		//The owner of the white bike is somewhere between the 15-year-old boy and the youngest boy, in that order
		csp.addBidirectionalArc("white","15");
		csp.addBidirectionalArc("white","12");

		//the boy who is going to drink grapefruit juice is somewhere between who brought tuna sandwhich and who brought pineapple juice, in that order
		csp.addBidirectionalArc("grapefruit","tuna");
		csp.addBidirectionalArc("grapefruit","pineapple");

		//charles is somewhere between Richard and Adrian, in that order
		csp.addBidirectionalArc("Charles","Richard");
		csp.addBidirectionalArc("Charles","Adrian");

		//the cyclist riding the white bike is somewhere between richar and the boy riding the red bike, in that order
		csp.addBidirectionalArc("white","Richard");
		csp.addBidirectionalArc("white","red");

		//the 12-year-old is somewhere between the 14-year-old and the oldest boy, in that order
		csp.addBidirectionalArc("12","14");
		csp.addBidirectionalArc("white","16");

		//the boy riding the white bike is somewhere between the boys riding the blue and the black bicycles, in that order
		csp.addBidirectionalArc("white","blue");
		csp.addBidirectionalArc("white","black");

		//the cyclist that brought pineapple juice is somewhere between the 14-year-old and the boy that brought orange juice, in that order
		csp.addBidirectionalArc("pineapple","14");
		csp.addBidirectionalArc("pineapple","orange");

		for(Object X : varBike)
			for(Object Y : varBike)
				csp.addBidirectionalArc(X,Y);

		for(Object X : varName)
			for(Object Y : varName)
				csp.addBidirectionalArc(X,Y);

		for(Object X : varSan)
			for(Object Y : varSan)
				csp.addBidirectionalArc(X,Y);

		for(Object X : varJui)
			for(Object Y : varJui)
				csp.addBidirectionalArc(X,Y);

		for(Object X : varAge)
			for(Object Y : varAge)
				csp.addBidirectionalArc(X,Y);

		for(Object X : varSpt)
			for(Object Y : varSpt)
				csp.addBidirectionalArc(X,Y);

		Search search = new Search(csp);
		System.out.println(search.BacktrackingSearch());
	}
}
