package exceptions;

public class NegatifOrEqualException extends Exception{
	
	public NegatifOrEqualException() {
		super("Erreur : entier inférieur ou égale à 0 !");
	}
}
