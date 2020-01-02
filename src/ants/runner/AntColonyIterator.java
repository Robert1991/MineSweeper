package ants.runner;

import ants.board.GameBoard;

public class AntColonyIterator {
	private GameConfiguration gameConfiguration;
	private GameBoard gameBoard;

	public AntColonyIterator(GameConfiguration gameConfiguration) {
		this.gameConfiguration = gameConfiguration;
	}

	public void letAntsCrawl() {
		gameBoard = gameConfiguration.createBoard();
		
		while(gameBoard.stillFoodToCollect())
			gameBoard.iterate();
	}
	
	
	
}
