package game;

import board.BoardConfiguration;

public class MineSweeper {
	private GameBoard board;

	public MineSweeper(GameBoard board) {
		this.board = board;
	}

	public static void run(BoardConfiguration configureBoard) {
		GameBoard board = GameBoard.createFrom(configureBoard);
		new MineSweeper(board).startGame();
	}

	private void startGame() {
		GameStatus current = GameStatus.OnGoing; 
		board.putBoardToConsole();
			
		while(current == GameStatus.OnGoing) {
			current = board.reveal(UserInput.promptCoordiantes());
			board.putBoardToConsole();
		}
	}
}
