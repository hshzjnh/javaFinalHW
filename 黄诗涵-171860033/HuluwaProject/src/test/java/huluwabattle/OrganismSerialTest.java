package huluwabattle;

import static org.junit.Assert.assertEquals;
//import static org.junit.jupiter.api.Assertions.*;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import org.junit.*;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.Test;
import org.junit.runners.*;
/*
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runners.MethodSorters;*/

import myorganism.InitModule;
import myorganism.Organism;
import myorganism.Red;
import organismdata.OrganismData;

@FixMethodOrder(MethodSorters.DEFAULT)
class OrganismSerialTest {

	@BeforeAll
	static void setUpBeforeClass() throws Exception {
	}

	@AfterAll
	static void tearDownAfterClass() throws Exception {
		File file = new File("testFile.ser");
		if (!file.exists()) 
			return;
	    if (file.isFile()) 
            file.delete();
		         
	}

	@BeforeEach
	void setUp() throws Exception {
	}

	@AfterEach
	void tearDown() throws Exception {
	}
	@Test
	void testAOutput() throws IOException, ClassNotFoundException
	{
		Organism organism=new Organism();
		organism.getData().setAtk(999);
		FileOutputStream fs = new FileOutputStream("testFile.ser");//new FileOutputStream("file\\recordFile.ser");
        ObjectOutputStream os =  new ObjectOutputStream(fs);
        os.writeObject(organism.getData());
        os.close();
	}
	@Test
	void testBInput() throws IOException, ClassNotFoundException {

		Organism organism=new Organism();
		FileInputStream fs2 = new FileInputStream("testFile.ser");//new FileOutputStream("file\\recordFile.ser");
        ObjectInputStream os2 = new ObjectInputStream(fs2);
        organism.setData((OrganismData)os2.readObject());
        os2.close();
        assertEquals(999,organism.getData().getAtk());
	}
	
}
