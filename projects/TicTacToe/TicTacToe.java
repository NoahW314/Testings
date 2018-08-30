package projects.TicTacToe;

import java.util.Scanner;

public class TicTacToe {
	public static Player createPlayer1(Scanner sc) {
		return new HumanPlayer(1, sc);
	}
	public static Player createPlayer2(Scanner sc) {
		return new HumanPlayer(2, sc);
	}
	
	public static void main(String[] args) {
		Scanner sc = new Scanner(System.in);
		
		int[] board = new int[9];
		Player[] players = new Player[] {createPlayer1(sc), createPlayer2(sc)};
		players[0].otherPlayerClass = players[1].getClass();
		players[1].otherPlayerClass = players[0].getClass();
		
		printBlankBoard();
		
		for(int i = 0; i < 9; i++) {
			players[i%2].move(board);
			printBoard(board);
			
			int winnerNum = winner(board);
			if(winnerNum != 0) {
				endGame(winnerNum);
				break;
			}
		}
		
		if(winner(board) == 0) {
			System.out.println("Cat's Game");
		}
		players[0].end();
		players[1].end();
	}
	
	public static String[] letters = new String[] {" ", "O", "X"};
	
	public static int winner(int[] board) {
		for(int i = 0; i < 3; i++) {
			if(board[i] != 0 && board[i] == board[i+3] && board[i] == board[i+6]) {return board[i];}
			if(board[i*3] != 0 && board[i*3] == board[i*3+1] && board[i*3] == board[i*3+2]) {return board[i*3];}
		}
		print(board);
		for(int i = 0; i < 2; i++) {
			System.out.println(board[i*2]+""+board[4]+""+board[i*2+6]);
			if(board[i*2] != 0 && board[i*2] == board[4] && board[i*2] ==  board[i*2+6]) {return board[i*2];}
		}
		return 0;
	}
	public static void endGame(int winner) {
		System.out.println();
		System.out.println("Player "+winner+" won!");
	}

	public static void print(int[] board) {
		String str = "";
		for(int i = 0; i < board.length; i++) {
			str+=board[i];
		}
		System.out.println(str);
	}
	public static void printBoard(int[] board) {
		System.out.println(letters[board[0]]+" |"+letters[board[1]]+" |"+letters[board[2]]+" ");
		System.out.println("--|--|--");
		System.out.println(letters[board[3]]+" |"+letters[board[4]]+" |"+letters[board[5]]+" ");
		System.out.println("--|--|--");
		System.out.println(letters[board[6]]+" |"+letters[board[7]]+" |"+letters[board[8]]+" ");
	}
	public static void printBlankBoard() {
		System.out.println("  |  |  ");
		System.out.println("--|--|--");
		System.out.println("  |  |  ");
		System.out.println("--|--|--");
		System.out.println("  |  |  ");
	}
}
