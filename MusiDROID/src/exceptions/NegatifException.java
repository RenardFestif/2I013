package exceptions;

public class NegatifException extends Exception {
	
	public NegatifException() {
		super("Erreur : entier inférieur à 0 !");
	}
}
