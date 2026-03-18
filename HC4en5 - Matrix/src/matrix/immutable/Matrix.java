package matrix.immutable;

import java.util.Arrays;
import java.util.stream.IntStream;

/**
 * Elke instantie van deze klasse stelt een matrix van `double`-waarden voor.. //(we zeggen hier "stelt voor" want het is immutable,
 * 										// dus je kan over elk object nadenken alsof het een specifieke matrix voorstelt.
 * 
 * @immutable	//vandaar dat er geen mutatoren zijn!
 */
public class Matrix {
	
	/**
	 * @post | 0 <= result
	 */
	public int getAantalRijen() { throw new RuntimeException("Not yet implemented"); }
	
	/**
	 * @post | 0 <= result
	 */
	public int getAantalKolommen() { throw new RuntimeException("Not yet implemented"); }
	
	/**
	 * @post | result != null
	 * @post | result.length == getAantalRijen()
	 * @post | Arrays.stream(result).allMatch(rij ->
	 *       |     rij != null && rij.length == getAantalKolommen()
	 *       | )
	 * @creates | result, ...result			//Bij getRijen is niet alleen de return value een object maar ook de elementen van de return value zijn objecten
	 * 								// en die worden ook aangemaakt door de methode
	 */
	public double[][] getRijen() { throw new RuntimeException("Not yet implemented"); }
	
	/**
	 * @pre | 0 <= rijIndex && rijIndex < getAantalRijen()
	 * @pre | 0 <= kolomIndex && kolomIndex < getAantalKolommen()
	 * @post | result == getRijen()[rijIndex][kolomIndex]
	 */
	public double getElement(int rijIndex, int kolomIndex) { throw new RuntimeException("Not yet implemented"); }
	
	/**
	 * @creates | result
	 * @post | result != null
	 * @post | result.length == getAantalRijen() * getAantalKolommen()
	 * @post | IntStream.range(0, getAantalRijen()).allMatch(rijIndex ->
	 *       |     IntStream.range(0, getAantalKolommen()).allMatch(kolomIndex ->
	 *       |         result[rijIndex * getAantalKolommen() + kolomIndex] == getElement(rijIndex, kolomIndex)
	 *       |     )
	 *       | )
	 */
	public double[] getElementenRowMajor() { throw new RuntimeException("Not yet implemented"); }
	
	/**
	 * @creates | result
	 * @post | result != null
	 * @post | result.length == getAantalRijen() * getAantalKolommen()
	 * @post | IntStream.range(0, getAantalRijen()).allMatch(rijIndex ->
	 *       |     IntStream.range(0, getAantalKolommen()).allMatch(kolomIndex ->
	 *       |         result[kolomIndex * getAantalRijen() + rijIndex] == getElement(rijIndex, kolomIndex)
	 *       |     )
	 *       | )
	 */
	public double[] getElementenColumnMajor() { throw new RuntimeException("Not yet implemented"); }
	
	/**
	 * @pre | 0 <= aantalRijen
	 * @pre | 0 <= aantalKolommen
	 * @pre | elementenRowMajor != null
	 * @pre | elementenRowMajor.length == aantalRijen * aantalKolommen
	 * @inspects | elementenRowMajor
	 * @post | getAantalRijen() == aantalRijen
	 * @post | getAantalKolommen() == aantalKolommen
	 * @post | Arrays.equals(getElementenRowMajor(), elementenRowMajor)
	 */
	public Matrix(int aantalRijen, int aantalKolommen, double[] elementenRowMajor) {
		throw new RuntimeException("Not yet implemented");
	}
	
	/**
	 * 
	 * 	// neemt geen preconditie; mag je voor eender welke factor oproepen
	 * @post | result != null
	 * @post | result.getAantalRijen() == getAantalRijen()
	 * @post | result.getAantalKolommen() == getAantalKolommen()
	 * @post | IntStream.range(0, getAantalRijen()).allMatch(rijIndex ->
	 *       |     IntStream.range(0, getAantalKolommen()).allMatch(kolomIndex ->
	 *       |         result.getElement(rijIndex, kolomIndex) == factor * getElement(rijIndex, kolomIndex)
	 *       |     )
	 *       | )
	 */
	public Matrix scaled(double factor) { throw new RuntimeException("Not yet implemented"); }
	
	/**
	 * @pre | other != null
	 * @pre | other.getAantalRijen() == getAantalRijen()
	 * @pre | other.getAantalKolommen() == getAantalKolommen()
	 * 
	 * @post | result != null
	 * @post | result.getAantalRijen() == getAantalRijen()
	 * @post | result.getAantalKolommen() == getAantalKolommen()
	 * @post | IntStream.range(0, getAantalRijen()).allMatch(rijIndex ->
	 *       |     IntStream.range(0, getAantalKolommen()).allMatch(kolomIndex ->
	 *       |         result.getElement(rijIndex, kolomIndex) == getElement(rijIndex, kolomIndex) +
	 *       |             other.getElement(rijIndex, kolomIndex)
	 *       |     )
	 *       | )
	 */
	public Matrix plus(Matrix other) { throw new RuntimeException("Not yet implemented"); }
	
	
}
