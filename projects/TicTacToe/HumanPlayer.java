package projects.TicTacToe;

import java.util.Scanner;

public class HumanPlayer extends Player {
	public Scanner scanner;
	
	public HumanPlayer(int playerNum, Scanner sc) {
		super(playerNum);
		this.scanner = sc;
	}

	@Override
	public void move(int[] board) {
		if(HumanPlayer.class.isAssignableFrom(otherPlayerClass)) {
			System.out.println("Player "+playerNum+" make your move");
		}
		else {
			System.out.println("Make your move");
		}
		int move = scanner.nextInt();
		board[keyBoardToBoard(move)] = playerNum;
	}
	
	private int keyBoardToBoard(int move) {
		switch(move) {
		case 1: return 6;
		case 2: return 7;
		case 3: return 8;
		case 4: return 3;
		case 5: return 4;
		case 6: return 5;
		case 7: return 0;
		case 8: return 1;
		case 9: return 2;
		default: throw new IllegalArgumentException("No 0 allowed!!");
		}
	} 

	@Override
	public void end() {
		if(playerNum == 1) {
			scanner.close();
		}
	}
}
