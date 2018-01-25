package exceptions;

public class OutZeroToTen extends Exception{

	public OutZeroToTen() {
		System.out.println("Erreur : octave non compris entre 0 et 10 !");
	}
}
