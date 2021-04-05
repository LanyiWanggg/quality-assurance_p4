import org.junit.*;
import org.junit.runners.MethodSorters;
import org.mockito.Mockito;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

public class GameOfLifePinningTest {
	/*
	 * READ ME: You may need to write pinning tests for methods from multiple
	 * classes, if you decide to refactor methods from multiple classes.
	 * 
	 * In general, a pinning test doesn't necessarily have to be a unit test; it can
	 * be an end-to-end test that spans multiple classes that you slap on quickly
	 * for the purposes of refactoring. The end-to-end pinning test is gradually
	 * refined into more high quality unit tests. Sometimes this is necessary
	 * because writing unit tests itself requires refactoring to make the code more
	 * testable (e.g. dependency injection), and you need a temporary end-to-end
	 * pinning test to protect the code base meanwhile.
	 * 
	 * For this deliverable, there is no reason you cannot write unit tests for
	 * pinning tests as the dependency injection(s) has already been done for you.
	 * You are required to localize each pinning unit test within the tested class
	 * as we did for Deliverable 2 (meaning it should not exercise any code from
	 * external classes). You will have to use Mockito mock objects to achieve this.
	 * 
	 * Also, you may have to use behavior verification instead of state verification
	 * to test some methods because the state change happens within a mocked
	 * external object. Remember that you can use behavior verification only on
	 * mocked objects (technically, you can use Mockito.verify on real objects too
	 * using something called a Spy, but you wouldn't need to go to that length for
	 * this deliverable).
	 */

	/* TODO: Declare all variables required for the test fixture. */
	MainPanel mainPanel;
	Cell cell;

	@Before
	public void setUp() {
		/*
		 * TODO: initialize the text fixture. For the initial pattern, use the "blinker"
		 * pattern shown in:
		 * https://en.wikipedia.org/wiki/Conway%27s_Game_of_Life#Examples_of_patterns
		 * The actual pattern GIF is at:
		 * https://en.wikipedia.org/wiki/Conway%27s_Game_of_Life#/media/File:Game_of_life_blinker.gif
		 * Start from the vertical bar on a 5X5 matrix as shown in the GIF.
		 */
		mainPanel = new MainPanel(5);
		cell = new Cell();
	}

	@After
    public void tearDown() throws Exception {
    }

	/* TODO: Write the three pinning unit tests for the three optimized methods */

	@Test
	public void testCellToStringX()
	{
		cell = new Cell(true);
		assertEquals("X", cell.toString());
	}  

	@Test
	public void testCellToStringNotX()
	{
		cell = new Cell(false);
		assertEquals(".", cell.toString());
	}

	@Test
	public void testIterateCellAlive()
	{
		mainPanel = new MainPanel(5);
		Cell cell[][] = new Cell[5][5];
		Cell cMock = Mockito.mock(Cell.class);
		Mockito.when(cMock.getAlive()).thenReturn(true);
		for(int i = 0; i < 5; i++)
		{
			for(int j = 0; j < 5; j++)
			{
				cell[i][j] = cMock;
			}
		}
		mainPanel.setCells(cell);
		assertEquals(false, mainPanel.iterateCell(2,3));
		

	}

	@Test
	public void testIterateCellNotAlive()
	{
		mainPanel = new MainPanel(5); 
		Cell cell[][] = new Cell[5][5];
		Cell cMock = Mockito.mock(Cell.class);
		Mockito.when(cMock.getAlive()).thenReturn(false);
		for(int i = 0; i < 5; i++)
		{
			for(int j = 0; j < 5; j++)
			{
				cell[i][j] = cMock;
			}
		}
		mainPanel.setCells(cell);
		assertEquals(false, mainPanel.iterateCell(2,2));
	}

	@Test
	public void testCalculateNextIteration()
	{
		mainPanel = new MainPanel(5);
        Cell[][] cell = new Cell[5][5];
		for(int i = 0; i < 5; i++)
		{
			for(int j = 0; j < cell[i].length; j++)
			{
				if(i==1 && j==2)
					cell[i][j] = new Cell(true);
				else if(i==2 && j==2)
					cell[i][j] = new Cell(true);
				else if(i==3 && j==2)
					cell[i][j] = new Cell(true);
				else
					cell[i][j] = new Cell(false);
			}
		}
		mainPanel.setCells(cell);
		mainPanel.calculateNextIteration();
		cell = mainPanel.getCells();
		for(int i = 0; i < 5; i++)
		{
			for(int j = 0; j < cell[i].length; j++)
			{
				if(i==2 && j==1)
					assertEquals("X",cell[i][j].toString());
				else if(i==2 && j==2)
					assertEquals("X",cell[i][j].toString());
				else if(i==2 && j==3)
					assertEquals("X",cell[i][j].toString());
				else
					assertEquals(".",cell[i][j].toString());
			}
		}

	}
}
