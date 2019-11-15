import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import board.BombCount;
import cells.BombCell;
import cells.RegularCell;

import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.junit.Assert.*;
import java.util.Random;

public class BombCountTests {
	private Random randomMock;
	
	@Rule
	public ExpectedException thrown = ExpectedException.none();
	
	@Before
	public void ResetMocks() {
		randomMock = mock(Random.class);
	}
	
	@Test
	public void testThatItIsNotPossibleToCreateTheBombCountWithABombCountOfZero() {
		thrown.expect(IllegalArgumentException.class);
		thrown.expectMessage(equalTo("bomb count has to be greater or equal to 1"));
		BombCount.Of(0);
	}
	
	@Test
	public void testThatItIsNotPossibleToCreateTheBombCountWithANegativeBombCount() {
		thrown.expect(IllegalArgumentException.class);
		thrown.expectMessage(equalTo("bomb count has to be greater or equal to 1"));
		BombCount.Of(-1);
	}
	
	@Test
	public void testThatTheBombCountCreatesABombFieldWhenTheRandomBoolReturnsTrue() {
		when(randomMock.nextBoolean()).thenReturn(true);
		assertThat(createBombCountWith(2).nextCell(() -> new BombCell(),
												   () -> new RegularCell()),
				instanceOf(BombCell.class));
	}
	
	@Test
	public void testThatTheBombCountOnlyCreatesRegularFieldsAfterTheBombCountWasMet() {
		when(randomMock.nextBoolean()).thenReturn(true);
		BombCount bombCount = createBombCountWith(2);
		assertThat(bombCount.nextCell(() -> new BombCell(),
								      () -> new RegularCell()),
				instanceOf(BombCell.class));
		assertThat(bombCount.nextCell(() -> new BombCell(),
			      				      () -> new RegularCell()),
				instanceOf(BombCell.class));
		assertThat(bombCount.nextCell(() -> new BombCell(),
			      					  () -> new RegularCell()),
				instanceOf(RegularCell.class));
	}

	private BombCount createBombCountWith(int count) {
		BombCount bombCount = BombCount.Of(count);
		bombCount.random = randomMock;
		return bombCount;
	}
}
