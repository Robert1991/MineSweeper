import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.hasItems;
import static org.junit.Assert.assertThat;

import java.util.List;

import org.junit.Test;

import board.BoardDimension;
import coordinates.FieldCoordinates;

public class BoardDimensionTests {
	@Test
	public void testCreatingTheFieldCoordinatesFromABoardDimension() {
		List<FieldCoordinates> dimensionCoordiantes = new BoardDimension(2, 2).dimensionCoordinates();
		assertThat(dimensionCoordiantes , hasItems(FieldCoordinates.createFor(1, 1),
 										           FieldCoordinates.createFor(2, 1),
							                       FieldCoordinates.createFor(1, 2),
								                   FieldCoordinates.createFor(2, 2)));
		assertThat(dimensionCoordiantes.size(), equalTo(4));
	}
	
	@Test
	public void testCreatingTheFieldCoordinatesFromABoardDimension3X2() {
		List<FieldCoordinates> dimensionCoordiantes = new BoardDimension(3, 2).dimensionCoordinates();
		assertThat(dimensionCoordiantes , hasItems(FieldCoordinates.createFor(1, 1),
 										           FieldCoordinates.createFor(2, 1),
							                       FieldCoordinates.createFor(3, 1),
							                       FieldCoordinates.createFor(1, 2),
								                   FieldCoordinates.createFor(2, 2),
								                   FieldCoordinates.createFor(3, 2)));
		assertThat(dimensionCoordiantes.size(), equalTo(6));
	}
}
