package personnel;

public class ExceptionDepart extends Exception{
     	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
		public ExceptionDepart()
	    {
	        System.out.println("Exception ExceptionDepart has been raised...");
	    }
     	@Override
     	public String toString()
        {
          return "La date de d�part ne peut pas etre avant la date d'arriv�e ";
        }
}