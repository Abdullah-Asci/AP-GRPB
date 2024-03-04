package personnel;

class ExceptionDate extends Exception
{
    public ExceptionDate()
    {
        System.out.println("L'exception a été levée");
    }
    
    public String toString()
    {
      return "Une erreur dans les dates !";
    }
}

