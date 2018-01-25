package exceptions;

public class NegatifOrEqualException extends Exception{
	
	public NegatifOrEqualException() {
		System.out.println("Erreur : entier inférieur ou égale à 0 !");
	}
}
