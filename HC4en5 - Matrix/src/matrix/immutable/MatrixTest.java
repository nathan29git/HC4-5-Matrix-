package matrix.immutable;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

class MatrixTest {

	
	Matrix myMatrix = new Matrix(2,3, new double[] {10, 20, 30, 40, 50, 60});
	
	
	@Test
	void testConstructor() {
		assertEquals(2, myMatrix.getAantalRijen());
		assertEquals(3, myMatrix.getAantalKolommen());
		assertArrayEquals( new double [] {10, 20, 30, 40, 50, 60}, myMatrix.getElementenRowMajor());
		assertArrayEquals( new double [] {10, 40, 20, 50, 30, 60}, myMatrix.getElementenColumnMajor());
		assertArrayEquals( new double [][] {{10, 20, 30}, {40, 50, 60}}, myMatrix.getRijen());
		assertEquals(20, myMatrix.getElement(1,  2));
		
	}
	
	
	@Test
	void testScaled() {
		Matrix scaledMatrix = myMatrix.scaled(10);
		assertEquals(2, scaledMatrix.getAantalRijen());
		assertEquals(3, scaledMatrix.getAantalKolommen());
		assertArrayEquals(new double[] {100, 200, 300, 400, 500, 600}, scaledMatrix.getElementenRowMajor());
		assertArrayEquals(new double[] {100, 400, 200, 500, 300, 600}, scaledMatrix.getElementenColumnMajor());
	
	}

}
