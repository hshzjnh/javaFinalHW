package myorganism;

import static org.junit.Assert.assertEquals;
//import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.*;

class PositionTest {
	private static Position<Organism>[][] positions=new Position[5][16];
	@BeforeAll
	static void setUpBeforeClass() throws Exception {
		for(int i=0;i<5;i++)
			for(int j=0;j<16;j++)
			{
				positions[i][j]=new Position<Organism>(i,j);
			}
	}

	@AfterAll
	static void tearDownAfterClass() throws Exception {
	}

	@BeforeEach
	void setUp() throws Exception {
	}

	@AfterEach
	void tearDown() throws Exception {
	}

	@Test
	void testSet() {
		Organism organism=new Organism<>();
		positions[3][4].set(organism);
		assertEquals("set at wrong position.", 3, organism.mypos.getX());
		assertEquals(4,organism.mypos.getY());
	}

}
