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
	 * @invar | 0 <= aantalRijen
	 * @invar | 0 <= aantalKolommen
	 * @invar | elementenColumnMajor != null
	 * @invar | elementenColumnMajor.length == aantalRijen * aantalKolommen 
	 */
	
	
	
	
	
	
	private int aantalRijen;
	private int aantalKolommen;
	
	/**
	 * @representationObject
	 */
	private double[] elementenColumnMajor;
	
	
	
	
	/**
	 * @post | 0 <= result
	 */
	public int getAantalRijen() {return aantalRijen;}
	
	/**
	 * @post | 0 <= result
	 */
	public int getAantalKolommen() {return aantalKolommen;}
	
	/**
	 * @post | result != null
	 * @post | result.length == getAantalRijen()
	 * @post | Arrays.stream(result).allMatch(rij ->
	 *       |     rij != null && rij.length == getAantalKolommen()
	 *       | )
	 * @creates | result, ...result			//Bij getRijen is niet alleen de return value een object maar ook de elementen van de return value zijn objecten
	 * 								// en die worden ook aangemaakt door de methode
	 */
	public double[][] getRijen() { 
		double [][] result = new double[aantalRijen][aantalKolommen];
		for (int rijIndex = 0; rijIndex < aantalRijen; rijIndex ++)
			for (int kolomIndex = 0; kolomIndex < aantalKolommen; kolomIndex ++)
				result[rijIndex][kolomIndex] = elementenColumnMajor[kolomIndex * aantalRijen + rijIndex];
		return result;
	
	
	} // [][] want array van arrays, waarbij elke rij een array is
	// deze inspector gaan we gebruiken als "BASISINSPECTOR" gebruiken om te vermijden dat we lussen krijgen
	
	/**
	 * @pre | 0 <= rijIndex && rijIndex < getAantalRijen()
	 * @pre | 0 <= kolomIndex && kolomIndex < getAantalKolommen()
	 * @post | result == getRijen()[rijIndex][kolomIndex]
	 */
	public double getElement(int rijIndex, int kolomIndex) { 
		// DE NIEUWE WISKUNDE: Spring per kolom in plaats van per rij
				return elementenColumnMajor[kolomIndex * aantalRijen + rijIndex];
	
	
	}
	
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
	public double[] getElementenRowMajor() { 
		// We moeten onze interne column-major data nu vertalen naar een row-major array voor de output
				double[] result = new double[aantalRijen * aantalKolommen];
				for (int rijIndex = 0; rijIndex < aantalRijen; rijIndex++) {
					for (int kolomIndex = 0; kolomIndex < aantalKolommen; kolomIndex++) {
						result[rijIndex * aantalKolommen + kolomIndex] = getElement(rijIndex, kolomIndex);
					}	//bovenstaande was de formule voor elementrowmajor
				}
				return result;	
	
	
	
	
	}
	
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
	public double[] getElementenColumnMajor() {
		// Omdat we intern ALLES al in column major hebben staan, is dit nu super simpel!
				return elementenColumnMajor.clone();
			}
	
	
	
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
		this.aantalRijen = aantalRijen;
		this.aantalKolommen = aantalKolommen;
		
		// De klant geeft row-major mee, maar wij vertalen het direct naar onze interne column-major opslag!
		this.elementenColumnMajor = new double[aantalRijen * aantalKolommen];
		for (int rijIndex = 0; rijIndex < aantalRijen; rijIndex++) {
			for (int kolomIndex = 0; kolomIndex < aantalKolommen; kolomIndex++) {
				this.elementenColumnMajor[kolomIndex * aantalRijen + rijIndex] = elementenRowMajor[rijIndex * aantalKolommen + kolomIndex];
			}
		}
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
	public Matrix scaled(double factor) {
		// Onze constructor verwacht een ROW-MAJOR array, dus we bouwen eerst een geschaalde row-major array op
				double[] scaledRowMajor = new double[aantalRijen * aantalKolommen];
				for (int rijIndex = 0; rijIndex < aantalRijen; rijIndex++) {
					for (int kolomIndex = 0; kolomIndex < aantalKolommen; kolomIndex++) {
						scaledRowMajor[rijIndex * aantalKolommen + kolomIndex] = factor * getElement(rijIndex, kolomIndex);
					}
				}
				return new Matrix(aantalRijen, aantalKolommen, scaledRowMajor);
	
	}
	
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
	public Matrix plus(Matrix other) { 
		// Onze constructor verwacht een ROW-MAJOR array, dus we bouwen eerst de som op als row-major array
				double[] sumRowMajor = new double[aantalRijen * aantalKolommen];
				for (int rijIndex = 0; rijIndex < aantalRijen; rijIndex++) {
					for (int kolomIndex = 0; kolomIndex < aantalKolommen; kolomIndex++) {
						sumRowMajor[rijIndex * aantalKolommen + kolomIndex] = this.getElement(rijIndex, kolomIndex) + other.getElement(rijIndex, kolomIndex);
					}
				}
				return new Matrix(aantalRijen, aantalKolommen, sumRowMajor);
	
	}
	
	
}
