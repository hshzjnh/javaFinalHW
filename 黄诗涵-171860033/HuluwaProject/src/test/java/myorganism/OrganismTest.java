package myorganism;

import static org.junit.Assert.assertEquals;
//import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.sun.javafx.scene.traversal.Direction;

import myorganism.Organism.DIRECTION;

class OrganismTest {
	private static Position<Organism>[][] positions=new Position[5][16];
	@BeforeAll
	static void setUpBeforeClass() throws Exception {
		for(int i=0;i<5;i++)
			for(int j=0;j<16;j++)
				positions[i][j]=new Position<Organism>(i,j);
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
	void testMove() {
		Organism organism=new Organism();
		positions[1][2].set(organism);
		organism.position=positions;
		organism.direction=DIRECTION.RIGHT;
		Position<Organism> p=organism.getNextPos();
		assertEquals(3,p.getY());
		assertEquals(1,p.getX());
		organism.mypos.set(null);
	}
	@Test
	void testDeductHP(){
		Organism organism=new Organism();
		organism.getData().setHp(1000);
		organism.getData().setDef(0);
		organism.getData().setDodge(0);
		organism.deductHP(700);
		assertEquals(300,organism.getData().getHp());
	}
	
}
