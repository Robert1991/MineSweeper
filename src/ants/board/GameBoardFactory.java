package ants.board;
import ants.board.cells.AntHillCell;
import ants.board.cells.Coordinates;
import ants.board.cells.GameCell;
import ants.board.dimension.GameObjectPositions;
import ants.runner.GameConfiguration;

public class GameBoardFactory {
	public static GameBoard createFor(GameConfiguration gameConfiguration) {
		GameBoard gameBoard = new GameBoard(gameConfiguration.dimension());
		int currentCellIndex = 1;
		GameObjectPositions objectPositions = gameConfiguration.createRandomGameBoardPositions();
		for (Coordinates next : gameConfiguration.createBoardCoordinates()) {
			if (objectPositions.isAntHillPosition(currentCellIndex)) {
				gameBoard.placeAntHillAt((AntHillCell)gameConfiguration.createAntHillAt(next), next);
			}
			else if (objectPositions.isFoodCellPosition(currentCellIndex))
				gameBoard.placeCell(gameConfiguration.nextFoodCell(), next);
			else
				gameBoard.placeCell(GameCell.empty(), next);
			currentCellIndex += 1;
		}
		return gameBoard;
	}
}
