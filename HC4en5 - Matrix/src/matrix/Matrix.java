package matrix;

import java.util.Arrays;
import java.util.stream.IntStream;

/**
 * Elke instantie van deze klasse SLAAT een matrix van 'double'-waarden OP. //(we zeggen hier "stelt voor" want het is immutable,
 * 										// dus je kan over elk object nadenken alsof het een specifieke matrix voorstelt.
 * 
 * 
 */
public class Matrix {
	
	/**
	 * @invar | 0 <= aantalRijen
	 * @invar | 0 <= aantalKolommen
	 * 
	 * @invar | rijen != null
	 * @invar | Arrays.stream(rijen).allMatch(rij -> rij != null && rij.length == aantalKolommen)
	 */
	
	private int aantalRijen;
	
	
	private int aantalKolommen;
	
	/**
	 * @representationObject
	 */
	private double[][] rijen;	//dit is de nieuwe interne structuur !
	
	
	
	
	/**
	 * 
	 * @post | 0 <= result
	 * @immutable
	 */
	public int getAantalRijen() {return aantalRijen;}
	
	/**
	 * 
	 * @post | 0 <= result
	 * @immutable 		//het is niet omdat het een mutable versie is, dat je de inspectoren niet immutable mag maken
	 */
	public int getAantalKolommen() {return aantalKolommen;}
	
	
	/**
	 * @post | result != null
	 * @post | result.length == getAantalRijen()
	 * @post | Arrays.stream(result).allMatch(rij -> rij != null && rij.length == getAantalKolommen())
	 * 
	 * 
	 * 
	 * 
	 * 
	 * @creates | result,...result	//Bij getRijen is niet alleen de return value een object maar ook de elementen van de return value zijn objecten
	 * 								// en die worden ook aangemaakt door de methode
	 */
	public double [][] getRijen() {
		double [][] result = new double[aantalRijen][aantalKolommen];
		
		// DEEP COPY: We moeten zowel de buitenste als de binnenste arrays nieuw maken!
		for (int r = 0; r < aantalRijen; r++) {
			result[r] = this.rijen[r].clone(); // Clone per rij, voorkomt Representation Exposure!
		}
		return result;
	
	
	} // [][] want array van arrays, waarbij elke rij een array is
	// deze inspector gaan we gebruiken als "BASISINSPECTOR" gebruiken om te vermijden dat we lussen krijgen
	
	
	
	/**
	 * @pre | 0 <= rijIndex && rijIndex < getAantalRijen()
	 * @pre | 0 <= kolomIndex && kolomIndex < getAantalKolommen()
	 * @post | result == getRijen()[rijIndex][kolomIndex]
	 */
	public double getElement(int rijIndex, int kolomIndex) {
		return rijen[rijIndex][kolomIndex];	//is simpel nu we een 2D array hebben
		
	}
	
	
	/**
	 * 
	 * @creates | result
	 * 
	 * @post | result != null
	 * @post | result.length == getAantalRijen() * getAantalKolommen()
	 * @post | IntStream.range(0, getAantalRijen()).allMatch(rijIndex -> IntStream.range(0, getAantalKolommen()).allMatch(kolomIndex -> 
	 * 		 |		result[rijIndex * getAantalKolommen() + kolomIndex] == getElement(rijIndex,kolomIndex)))
	 */
	public double[] getElementenRowMajor() {
		double[] result = new double[aantalRijen * aantalKolommen];
		for (int r = 0; r < aantalRijen; r++) {
			for (int c = 0; c < aantalKolommen; c++) {
				result[r * aantalKolommen + c] = rijen[r][c];
			}
		}
		return result;
	}
	
	/**
	 * 
	 * @creates | result
	 * 
	 * @post | result != null
	 * @post | result.length == getAantalRijen() * getAantalKolommen()
	 * @post | IntStream.range(0, getAantalRijen()).allMatch(rijIndex -> IntStream.range(0, getAantalKolommen()).allMatch(kolomIndex -> 
	 * 		 |		result[kolomIndex * getAantalRijen() + rijIndex] == getElement(rijIndex,kolomIndex)))
	 */
	public double[] getElementenColumnMajor() {
		double[] result = new double[aantalRijen * aantalKolommen];
		for (int r = 0; r < aantalRijen; r++) {
			for (int c = 0; c < aantalKolommen; c++) {
				result[c * aantalRijen + r] = rijen[r][c];
			}
		}
		return result;
	}
	
	
	
	/**
	 * 
	 * @pre | 0 <= aantalRijen 
	 * @pre | 0 <= aantalKolommen
	 * @pre | elementenRowMajor != null
	 * @pre | elementenRowMajor.length == aantalRijen * aantalKolommen
	 * 
	 * @inspects | elementenRowMajor
	 * @post | getAantalRijen() == aantalRijen
	 * @post | getAantalKolommen() == aantalKolommen
	 * @post | Arrays.equals(getElementenRowMajor(), elementenRowMajor)
	 *
	 * 
	 */
	public Matrix (int aantalRijen, int aantalKolommen, double[] elementenRowMajor) {
		this.aantalRijen = aantalRijen;
		this.aantalKolommen = aantalKolommen;
		
		// Vertaal de 1D input array naar onze interne 2D array!
		this.rijen = new double[aantalRijen][aantalKolommen];
		for(int r = 0; r < aantalRijen; r++) {
			for(int c = 0; c < aantalKolommen; c++) {
				this.rijen[r][c] = elementenRowMajor[r * aantalKolommen + c];
			}
		}
		
		
		
		
		
	}
	
	
	
	
	
	/**
	 * 
	 *		// neemt geen preconditie; mag je voor eender welke factor oproepen
	 *
	 * @mutates | this 
	 * 
	 *    		//post | result.getAantalRijen() == old(getAantalRijen())
	 * 			//post | result.getAantalKolommen() == old(getAantalKolommen())
	 * 			//dees halen we weg omdat we nu de inspectoren immutable hebben gemaakt waardoor dit er impliciet uit volgt 
	 * 
	 * 
	 * @post | IntStream.range(0, getAantalRijen()).allMatch(rijIndex -> IntStream.range(0, getAantalKolommen()).allMatch(kolomIndex -> 
	 * 		 |		getElement(rijIndex, kolomIndex) == factor * old(getRijen())[rijIndex][kolomIndex])) //je kunt old() niet op getElement oproepen dus je doet het op getRijen en dan buiten de old expressie indexeren!!!
	 * 
	 */
	public void  scale(double factor) {
		for (int r = 0; r < aantalRijen; r++) {
			for (int c = 0; c < aantalKolommen; c++) {
				rijen[r][c] *= factor;
			}
			
		}
		
	}
	
	
	
	/**
	 * 
	 *		// neemt geen preconditie; mag je voor eender welke factor oproepen
	 * @inspects | other //nu moet ik dit wel zeggen, vermits de matrixen nu wijzigbare objecten zijn
	 * @mutates | this
	 * @post | IntStream.range(0, getAantalRijen()).allMatch(rijIndex -> IntStream.range(0, getAantalKolommen()).allMatch(kolomIndex -> 
	 * 		 |		getElement(rijIndex, kolomIndex) == old(getRijen())[rijIndex][kolomIndex] + old(other.getRijen())[rijIndex][kolomIndex]))
	 * 
	 */
	public void add(Matrix other) {
	
		for (int r = 0; r < aantalRijen; r++) {
			for (int c = 0; c < aantalKolommen; c++) {
				rijen[r][c] += other.rijen[r][c];
			}
			
		}
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	

}
