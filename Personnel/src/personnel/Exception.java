package personnel;

class ExceptionDarrive extends Exception
{
    public ExceptionDarrive()
    {
        System.out.println("L'exception a été levée");
    }
    
    public String toString()
    {
      return "Une erreur dans les dates !";
    }
}

class ExceptionDepart extends Exception
{
    public ExceptionDepart()
    {
        System.out.println("L'exception a été levée");
    }
    
    public String toString()
    {
      return "Une erreur dans les dates !";
    }
}