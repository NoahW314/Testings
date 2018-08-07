package projects;

import java.util.ArrayList;

public class CardGame {
	static ArrayList<ArrayList<Integer>> piles = new ArrayList<ArrayList<Integer>>(13);
	static int numWon = 0;
	
	public static void main(String[] args) {
		traverseDeckCombs();
		/*for(int i = 0; i < 52; i++) {
			deck.add(i%13+1);
		}*/
	}
	
	public static void setUp(int deckComb) {
		ArrayList<Integer> deck = new ArrayList<Integer>(52);
		
		
		piles = new ArrayList<ArrayList<Integer>>(13);
		for(int i = 0; i < 13; i++) {
			piles.add(new ArrayList<Integer>(4));
			for(int j = 0; j < 4; j++) {
				piles.get(i).add(deck.get(i+j*13));
			}
		}
	}
	public static void play() {
		int lastI = 0;
		int lastPile = 12;
		for(int i = 0; i < 52; i++) {
			int card = piles.get(lastPile).remove(piles.get(lastPile).size()-1);
			piles.get(card-1).add(0, card);
			if(card == 13 && lastPile == 12 && allKings(piles.get(12))) {
				break;
			}
			lastPile = card-1;
			lastI = i;
		}
		if(lastI == 51) {
			numWon++;
		}
	}
	
	public static void traverseDeckCombs() {
		int deckCombNum = 10;
		for(int i = 0; i < deckCombNum; i++) {
			setUp(i);
			play();
		}
		System.out.println(numWon);
	}
	
	
	public static ArrayList<Integer> toArrayList(int[] intArr){
		ArrayList<Integer> list = new ArrayList<Integer>(intArr.length);
		for(int i: intArr) {
			list.add(i);
		}
		return list;
	}
	public static boolean allKings(ArrayList<Integer> pile) {
		for(int i = 0; i < pile.size(); i++) {
			if(pile.get(i) != 13) {return false;}
		}
		return true;
	}
}
