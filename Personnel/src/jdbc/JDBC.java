package jdbc;

import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Date;
//import java.time.LocalDate;
import personnel.*;

public class JDBC implements Passerelle 
{
	Connection connection;

	public JDBC()
	{
		try
		{
			Class.forName(Credentials.getDriverClassName());
			connection = DriverManager.getConnection(Credentials.getUrl(), Credentials.getUser(), Credentials.getPassword());
		}
		catch (ClassNotFoundException e)
		{
			System.out.println("Pilote JDBC non installe.");
		}
		catch (SQLException e)
		{
			System.out.println(e);
		}
	}
	
	@Override
	public GestionPersonnel getGestionPersonnel() 
	{
		GestionPersonnel gestionPersonnel = new GestionPersonnel();
		try 
		{
			String requete = "select * from ligue";
			Statement instruction = connection.createStatement();
			ResultSet ligues = instruction.executeQuery(requete);
			while (ligues.next())
				gestionPersonnel.addLigue(ligues.getInt(1), ligues.getString(2));
		}
		catch (SQLException e)
		{
			System.out.println(e);
		}
		return gestionPersonnel;
	}

	@Override
	public void sauvegarderGestionPersonnel(GestionPersonnel gestionPersonnel) throws SauvegardeImpossible 
	{
		close();
	}
	
	public void close() throws SauvegardeImpossible
	{
		try
		{
			if (connection != null)
				connection.close();
		}
		catch (SQLException e)
		{
			throw new SauvegardeImpossible(e);
		}
	}
	
	
	//Ligue
	
	@Override
	public int insert(Ligue ligue) throws SauvegardeImpossible 
	{
		try 
		{
			PreparedStatement instruction;
			instruction = connection.prepareStatement("insert into ligue (nom_L) values(?)", Statement.RETURN_GENERATED_KEYS);
			instruction.setString(1, ligue.getNom());		
			instruction.executeUpdate();
			ResultSet Id_L = instruction.getGeneratedKeys();
			Id_L.next();
			return Id_L.getInt(1);
		} 
		catch (SQLException exception) 
		{
			exception.printStackTrace();
			throw new SauvegardeImpossible(exception);
		}		
	}

	@Override
	public void delete(Ligue ligue) throws SauvegardeImpossible 
	{
		try 
		{
			PreparedStatement instruction;
			instruction = connection.prepareStatement("delete from ligue where Nom_L = (?)", Statement.RETURN_GENERATED_KEYS);
			instruction.setString(1, ligue.getNom());		
			instruction.executeUpdate();
		} 
		catch (SQLException exception) 
		{
			exception.printStackTrace();
			throw new SauvegardeImpossible(exception);
		}		
	}
	@Override
	public void update(Ligue ligue) throws SauvegardeImpossible 
	{
		try 
		{
			PreparedStatement instruction;
			instruction = connection.prepareStatement("update ligue set nom_L=(?) where id_L = (?)", Statement.RETURN_GENERATED_KEYS);
			instruction.setString(1, ligue.getNom());
			instruction.setInt(2, ligue.getID());
			instruction.executeUpdate();
		} 
		catch (SQLException exception) 
		{
			exception.printStackTrace();
			throw new SauvegardeImpossible(exception);
		}		
	}
	
	
	//Employe
	
	@Override
	public int insert(Employe employe) throws SauvegardeImpossible 
	{
		try 
		{
			Date dateArriveeSQL = Date.valueOf(employe.getDateArrivee());
			Date dateDepartSQL = Date.valueOf(employe.getDateDepart());
			PreparedStatement instruction;
			if (employe.getLigue()!=null) {
				instruction = connection.prepareStatement("insert into employe (nom_E, prenom, mail, mdp, Date_Dep, Date_Arri, Id_L) values(?, ?, ?, ?, ?, ?, ?)", Statement.RETURN_GENERATED_KEYS);
				instruction.setString(1, employe.getNom());
				instruction.setString(2, employe.getPrenom());
				instruction.setString(3, employe.getMail());
				instruction.setString(4, employe.getPassword());
				instruction.setDate(5, dateArriveeSQL);
				instruction.setDate(6, dateDepartSQL);
				instruction.setInt(7, employe.getLigue().getID());
				}
			else{
				instruction = connection.prepareStatement("insert into employe (nom_E, prenom, mail, mdp, statut, Date_Dep, Date_Arri) values(?, ?, ?, ?, ?, ?, ?)", Statement.RETURN_GENERATED_KEYS);
				instruction.setString(1, employe.getNom());
				instruction.setString(2, employe.getPrenom());
				instruction.setString(3, employe.getMail());
				instruction.setString(4, employe.getPassword());
				instruction.setString(5, employe.getStatut());
				instruction.setDate(6, dateArriveeSQL);
				instruction.setDate(7, dateDepartSQL);
			}			
			instruction.executeUpdate();
			ResultSet Id_E = instruction.getGeneratedKeys();
			Id_E.next();
			return Id_E.getInt(1);

		} 
		catch (SQLException exception) 
		{
			exception.printStackTrace();
			throw new SauvegardeImpossible(exception);
		}		
	}
	
	@Override
	public void delete(Employe employe) throws SauvegardeImpossible 
	{
		try 
		{
			PreparedStatement instruction;
			instruction = connection.prepareStatement("delete from employe where id_E=  ?", Statement.RETURN_GENERATED_KEYS);
			instruction.setInt(1, employe.getID());
			instruction.executeUpdate();
		} 
		catch (SQLException exception) 
		{
			exception.printStackTrace();
			throw new SauvegardeImpossible(exception);
		}		
	}
	
	@Override
	public void update(Employe employe) throws SauvegardeImpossible 
	{
		try 
		{
			Date dateArriveeSQL = Date.valueOf(employe.getDateArrivee());
			Date dateDepartSQL = Date.valueOf(employe.getDateDepart());
			PreparedStatement instruction;
			instruction = connection.prepareStatement("update employe set nom_E=(?), prenom=(?), mail=(?), mdp=(?), date_dep=(?), date_arri=(?) where id_E = (?)", Statement.RETURN_GENERATED_KEYS);
			instruction.setString(1, employe.getNom());
			instruction.setString(2, employe.getPrenom());
			instruction.setString(3, employe.getMail());
			instruction.setString(4, employe.getPassword());
			//instruction.setString(5, employe.getStatut());
			instruction.setDate(5, dateArriveeSQL);
			instruction.setDate(6, dateDepartSQL);
			instruction.setInt(7, employe.getID());
			instruction.executeUpdate();
		} 
		catch (SQLException exception) 
		{
			exception.printStackTrace();
			throw new SauvegardeImpossible(exception);
		}		
	}
}

