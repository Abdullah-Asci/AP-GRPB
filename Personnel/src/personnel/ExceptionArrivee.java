package personnel;

public class ExceptionArrivee extends Exception {
     	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
		public ExceptionArrivee()
	    {
	        System.out.println("Exception ExceptionArrivee has been raised...");
	    }
     	@Override
     	public String toString()
        {
          return "La date d'arriv�e ne peut pas etre avant la date de d�part ";
        }
}