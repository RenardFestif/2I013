package exceptions;

public class NegatifException extends Exception {
	
	public NegatifException() {
		System.out.println("Erreur : entier inférieur à 0 !");
	}
}
