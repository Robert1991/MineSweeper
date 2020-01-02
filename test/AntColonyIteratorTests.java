import org.junit.Test;
import static org.junit.Assert.assertThat;

import org.junit.Before;

import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.times;
import static org.hamcrest.Matchers.*;

import ants.board.GameBoard;
import ants.board.dimension.BoardDimension;
import ants.runner.AntColonyIterator;
import ants.runner.GameConfiguration;

public class AntColonyIteratorTests {
	private GameBoard gameBoardMock;

	@Before
	public void resetGameBoardMock() {
		gameBoardMock = mock(GameBoard.class);
	}
	
	@Test
	public void testCreatingTheAntColonyIteratorFromAValidGameConfiguration() {
		assertThat(new AntColonyIterator(new GameConfiguration(new BoardDimension(3,4), 4, 2, 3)), notNullValue());
	}
	
	@Test
	public void testThatTheAntColonyIteratorIteratesTheColonyUntilAllFoodWasConsumedFromTheFoodCells() {
		AntColonyIterator iterator = new AntColonyIterator(new GameConfiguration(new BoardDimension(3,4), 4, 2, 3)
										.with(gameBoardMock));
		when(gameBoardMock.stillFoodToCollect()).thenReturn(true)
												.thenReturn(true)
												.thenReturn(false);
		iterator.letAntsCrawl();
		verify(gameBoardMock, times(2)).iterate();;
	}
}
