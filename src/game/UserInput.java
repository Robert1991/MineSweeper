package game;

import java.util.Scanner;

import board.BoardConfiguration;
import board.BoardDimension;
import board.BombCount;
import coordinates.FieldCoordinates;

public class UserInput {
	private static Scanner systemIn = new Scanner(System.in);
	
	public static FieldCoordinates promptCoordiantes() {
		System.out.println("Next coordinates: ");
		String coordiantes = systemIn.nextLine();
		int xCoordiante = Integer.parseInt(coordiantes.split(",")[0]);
		int yCoordiante = Integer.parseInt(coordiantes.split(",")[1]);
		return new FieldCoordinates(xCoordiante, yCoordiante);
	}
	
	public static BoardConfiguration configureBoard() {
		BombCount bombs = configureBombs();
		BoardDimension dimension = configureBoardDimension();
		return new BoardConfiguration(bombs, dimension);
	}
	
	private static BoardDimension configureBoardDimension() {
		int xDimension = promptDimension("x");
		int yDimension = promptDimension("x");
		return new BoardDimension(xDimension, yDimension);
	}
	
	private static BombCount configureBombs() {
		System.out.println("Bomb count on board: ");
		return BombCount.Of(Integer.parseInt(systemIn.nextLine()));
	}
	
	private static int promptDimension(String dimensionName) {
		System.out.println(dimensionPrompt(dimensionName));
		return Integer.parseInt(systemIn.nextLine());
	}

	private static String dimensionPrompt(String dimensionName) {
		return String.format("Board %s-dimension:", dimensionName);
	}
}
