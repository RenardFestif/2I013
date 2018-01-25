package exceptions;

public class OutZeroToTen extends Exception{

	public OutZeroToTen() {
		super("Erreur : octave non compris entre 0 et 10 !");
	}
}
