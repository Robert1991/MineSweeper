package board;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import coordinates.FieldCoordinates;

public class BoardDimension {
	private int xDimension;
	private int yDimension;
	
	public BoardDimension(int xDimension, int yDimension) {
		this.xDimension = xDimension;
		this.yDimension = yDimension;
	}
	
	public FieldCoordinates xScale() {
		return new FieldCoordinates(xDimension, 0);
	}
	
	public List<FieldCoordinates> dimensionCoordinates() {
		List<FieldCoordinates> dimensionCoordinates = new ArrayList<FieldCoordinates>();
		for (int yLevel = 1; yLevel <= yDimension; yLevel++) {
			List<FieldCoordinates> levelCoordinates = fieldCoordiantesFor(yLevel);
			dimensionCoordinates.addAll(levelCoordinates);
		}
		Collections.sort(dimensionCoordinates);
		return dimensionCoordinates;
	}

	private List<FieldCoordinates> fieldCoordiantesFor(int yLevel) {
		List<FieldCoordinates> levelCoordinates = new ArrayList<FieldCoordinates>();
		for (int xLevel = 1; xLevel <= xDimension; xLevel++) {				
			int fieldXPosition = calculateX(xLevel);
			FieldCoordinates coordinates = FieldCoordinates.createFor(fieldXPosition, yLevel);
			levelCoordinates.add(coordinates);
		}
		return levelCoordinates;
	}

	private int calculateX(int xLevel) {
		if (xLevel % xDimension == 0) {
			return xDimension;
		}
		return xLevel % xDimension;
	}
}
