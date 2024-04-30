package personnel;

import java.io.Serializable;
import java.time.LocalDate;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * Employé d'une ligue hébergée par la M2L. Certains peuvent 
 * être administrateurs des employés de leur ligue.
 * Un seul employé, rattaché à aucune ligue, est le root.
 * Il est impossible d'instancier directement un employé, 
 * il faut passer la méthode {@link Ligue#addEmploye addEmploye}.
 */

public class Employe implements Serializable, Comparable<Employe>
{
	private static final long serialVersionUID = 4795721718037994734L;
	private String nom, prenom, password, mail ,statut;
	private Ligue ligue;
	private GestionPersonnel gestionPersonnel;
	private LocalDate dateArrivee = LocalDate.of(0000, 01, 01);
	private LocalDate dateDepart = LocalDate.of(0000, 01, 01);
	private int id;
	
	Employe(GestionPersonnel gestionPersonnel, Ligue ligue, String nom, String prenom, String mail, String statut, String password, LocalDate dateArrivee, LocalDate dateDepart)
	{
		this.gestionPersonnel = gestionPersonnel;
		this.nom = nom;
		this.prenom = prenom;
		this.password = (password != null) ? hashPassword(password) : "defaultPassword";
		this.mail = mail;
		this.statut = statut;
		this.ligue = ligue;
		
		try {
		this.id = gestionPersonnel.insert(this);
		}
		catch(SauvegardeImpossible e) 
		{
			e.printStackTrace();
		}
	}
	
	/**
	 * Retourne vrai ssi l'employé est administrateur de la ligue 
	 * passée en paramètre.
	 * @return vrai ssi l'employé est administrateur de la ligue 
	 * passée en paramètre.
	 * @param ligue la ligue pour laquelle on souhaite vérifier si this 
	 * est l'admininstrateur.
	 */
	
	public boolean estAdmin(Ligue ligue)
	{
		return ligue.getAdministrateur() == this;
	}
	
	/**
	 * Retourne vrai ssi l'employé est le root.
	 * @return vrai ssi l'employé est le root.
	 */
	
	public GestionPersonnel getGestionPersonnel()
	{
		return gestionPersonnel;
	}
	
	public boolean estRoot()
	{
		return gestionPersonnel.getRoot() == this;
	}
	
	/**
	 * Retourne le nom de l'employé.
	 * @return le nom de l'employé. 
	 */
	
	public int getID() {
		return id;
	}
	
	public String getNom()
	{
		return nom;
	}

	/**
	 * Change le nom de l'employé.
	 * @param nom le nouveau nom.
	 */
	
	public void setNom(String nom)
	{
		this.nom = nom;
		try {
			gestionPersonnel.update(this);
		}
		catch (SauvegardeImpossible e)
		{
			e.printStackTrace();
		}
	}

	/**
	 * Retourne le prénom de l'employé.
	 * @return le prénom de l'employé.
	 */
	
	public String getPrenom()
	{
		return prenom;
	}
	
	/**
	 * Change le prénom de l'employé.
	 * @param prenom le nouveau prénom de l'employé. 
	 */

	public void setPrenom(String prenom)
	{
		this.prenom = prenom;
		try {
			gestionPersonnel.update(this);
		}
		catch (SauvegardeImpossible e)
		{
			e.printStackTrace();
		}
	}

	/**
	 * Retourne le mail de l'employé.
	 * @return le mail de l'employé.
	 */
	
	public String getMail()
	{
		return mail;
	}
	
	/**
	 * Change le mail de l'employé.
	 * @param mail le nouveau mail de l'employé.
	 */

	public void setMail(String mail)
	{
		this.mail = mail;
		try {
			gestionPersonnel.update(this);
		}
		catch (SauvegardeImpossible e)
		{
			e.printStackTrace();
		}
	}

	public String getStatut()
	{
		return statut;
	}
	
	public void setStatut(String statut)
	{
		this.statut = statut;
		try {
			gestionPersonnel.update(this);
		}
		catch (SauvegardeImpossible e)
		{
			e.printStackTrace();
		}
	}
	
	
	public String getPassword()
	{
		return password;
	}
	
	/**
	 * Change le password de l'employé.
	 * @param password le nouveau password de l'employé. 
	 */
	
	public void setPassword(String password)
	{
		this.password = hashPassword(password);
		try {
			gestionPersonnel.update(this);
		}
		catch (SauvegardeImpossible e)
		{
			e.printStackTrace();
		}
	}
	
	
	/**
	 * Retourne vrai ssi le password passé en paramètre est bien celui
	 * de l'employé.
	 * @return vrai ssi le password passé en paramètre est bien celui
	 * de l'employé.
	 * @param password le password auquel comparer celui de l'employé.
	 */
	
	public boolean checkPassword(String Password)
	{
		//System.out.println(this.password); //pour afficher le mdp
	    return this.password.equals(hashPassword(Password));
	}
	
	
	private static String hashPassword(String password) {
        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            byte[] hashedBytes = digest.digest(password.getBytes());

            // Convertir les octets hash�s en format hexad�cimal
            StringBuilder hexString = new StringBuilder();
            for (byte hashedByte : hashedBytes) {
                String hex = Integer.toHexString(0xff & hashedByte);
                if (hex.length() == 1) hexString.append('0');
                hexString.append(hex);
            }
            return hexString.toString();
        } catch (NoSuchAlgorithmException e) {
            // G�rer l'exception (par exemple, en logguant l'erreur)
            e.printStackTrace();
            return null; // Ou lancer une exception appropri�e
        }
    }


	/**
	 * Retourne la ligue à laquelle l'employé est affecté.
	 * @return la ligue à laquelle l'employé est affecté.
	 */
	
	public Ligue getLigue()
	{
		return ligue;
	}

	/**
	 * Supprime l'employé. Si celui-ci est un administrateur, le root
	 * récupère les droits d'administration sur sa ligue.
	 */
	
	public void remove()
	{
		Employe root = gestionPersonnel.getRoot();
		if (this != root)
		{
			if (estAdmin(getLigue()))
				getLigue().setAdministrateur(root);
			getLigue().remove(this);
		}
		else
			throw new ImpossibleDeSupprimerRoot();
		try 
		{
			gestionPersonnel.delete(this);
		}
		catch(SauvegardeImpossible e) 
		{
			e.printStackTrace();
		}
	}

	public LocalDate getDateArrivee() {
        return dateArrivee;
    }
    public LocalDate getDateDepart() {
        return dateDepart;
    }

    public void setDateArrivee(LocalDate dateArrivee) throws ExceptionArrivee{
		if( (dateDepart != null) && (dateArrivee.isBefore(dateDepart) ) )
		{
			throw new ExceptionArrivee();
		}
		this.dateArrivee = dateArrivee;
		try {
			gestionPersonnel.update(this);
		}
		catch (SauvegardeImpossible e)
		{
			e.printStackTrace();
		}
		
}
    
    public void setDateDepart(LocalDate dateDepart) throws ExceptionDepart {
    	if( (dateArrivee != null) && (dateDepart.isAfter(dateArrivee) ) )
		{
			throw new ExceptionDepart();
		}
        this.dateDepart = dateDepart;
        try {
			gestionPersonnel.update(this);
		}
		catch (SauvegardeImpossible e)
		{
			e.printStackTrace();
		}
    }
    
	@Override
	public int compareTo(Employe autre)
	{
		int cmp = getNom().compareTo(autre.getNom());
		if (cmp != 0)
			return cmp;
		return getPrenom().compareTo(autre.getPrenom());
	}
	
	@Override
	public String toString()
	{
		String res = nom + " " + prenom + " " + mail + " (";
		if (estRoot())
			res += "super-utilisateur";
		else
			res += ligue.toString();
		return res + ")";
	}
}
