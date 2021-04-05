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

	//Preconditions: Mock a Cell object, and return true when call getAlive().
    //Execution Steps: Call c_mock.toString();
    //Postconditions: Return value is "X"
	@Test
	public void testCellToStringX()
	{
		Cell c_spy = Mockito.spy(cell);
		Mockito.when(c_spy.getAlive()).thenReturn(true);
		assertEquals("X", c_spy.toString());
	}

	//Preconditions: Mock a Cell object, and return false when call getAlive().
    //Execution Steps: Call c_mock.toString();
    //Postconditions: Return value is "."
	@Test
	public void testCellToStringNotX()
	{
		Cell c_spy = Mockito.spy(cell);
		Mockito.when(c_spy.getAlive()).thenReturn(false);
		assertEquals(".", c_spy.toString());
	}

	//Preconditions: Have the 5x5 cell board set up
				//spy mainPanel for stubbing its getNumNeighbors() method
				//mock cell class for stubbing its getAlive() method
				//return 3 when call mainPanelSpy.getNumNeighbors(2,3)
				//return true when call cMock.getAlive()
    //Execution Steps: call mainPanelSpy.iterateCell(2,3)
    //Postconditions: the return value should be true
	@Test
	public void testIterateCellAlive()
	{
		mainPanel = new MainPanel(5);
		MainPanel mainPanelSpy = Mockito.spy(mainPanel);
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
		Mockito.when(mainPanelSpy.getNumNeighbors(2,3)).thenReturn(3);
		assertEquals(true, mainPanelSpy.iterateCell(2,3));
		

	}

	//Preconditions: Have the 5x5 cell board set up
				//spy mainPanel for stubbing its getNumNeighbors() method
				//mock cell class for stubbing its getAlive() method
				//return 4 when call mainPanelSpy.getNumNeighbors(1,1)
				//return false when call cMock.getAlive()
    //Execution Steps: call mainPanelSpy.iterateCell(2,3)
    //Postconditions: the return value should be false
	@Test
	public void testIterateCellNotAlive()
	{
		mainPanel = new MainPanel(5); 
		MainPanel mainPanelSpy = Mockito.spy(mainPanel);
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
		Mockito.when(mainPanelSpy.getNumNeighbors(1,1)).thenReturn(4);
		assertEquals(false, mainPanelSpy.iterateCell(1,1));
	}

	//Preconditions: Have the three cells of the center of the Cell board set up vertically to alive and the rest are dead
    //Execution Steps: call calculateNextIteration()
    //Postconditions: The three alive cells aligned vertically in the center of board should be converted to be aligned horizontally in the center
	@Test
	public void testCalculateNextIteration()
	{
		mainPanel = new MainPanel(5);
        Cell[][] cell = new Cell[5][5];
		for(int i = 0; i < 5; i++)
		{
			for(int j = 0; j < 5; j++)
			{
				if(i==0 || i==4)
					cell[i][j] = new Cell(false);
				else
				{
					if(j==2)
						cell[i][j] = new Cell(true);
					else
						cell[i][j] = new Cell(false);
				}
			}
		}
		mainPanel.setCells(cell);
		mainPanel.calculateNextIteration();
		cell = mainPanel.getCells();
		for(int i = 0; i < 5; i++)
		{
			for(int j = 0; j < 5; j++)
			{
				if(i!=2)
					assertEquals(".", cell[i][j].toString());
				else
				{
					if(j==0 || j==4)
						assertEquals(".", cell[i][j].toString());
					else
						assertEquals("X", cell[i][j].toString());
				}
			}
		}

	}
}
