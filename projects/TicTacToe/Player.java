package projects.TicTacToe;

public abstract class Player {
	public int playerNum;
	public Class<? extends Player> otherPlayerClass;
	
	public Player(int playerNum) {
		this.playerNum = playerNum;
	}
	
	public abstract void move(int[] board);
	
	public void end() {}
}
