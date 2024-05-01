package jdbc;

import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Date;
import java.time.LocalDate;

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
	    boolean rootExiste = false;
	    
		try 
		{
	        // Vérifier si l'administrateur existe déjà dans la base de données
	        String requete = "SELECT * FROM employe WHERE nom_E = 'root'";
	        Statement instruction = connection.createStatement();
	        ResultSet result = instruction.executeQuery(requete);

	        // Si un employé avec le nom "root" existe, mettre rootExiste à true
	        if (result.next()) {
	            rootExiste = true;
	        }
	        
	        if (!rootExiste) {
	            PreparedStatement insertInstruction = connection.prepareStatement("INSERT INTO employe (nom_E, prenom, mail, mdp, statut, Date_Dep, Date_Arri) VALUES (?, ?, ?, ?, ?, ?, ?)");
	            insertInstruction.setString(1, "root");
	            insertInstruction.setString(2, "");
	            insertInstruction.setString(3, "");
	            insertInstruction.setString(4, "toor");
	            insertInstruction.setString(5, "Admin");
	            insertInstruction.setDate(6, Date.valueOf(LocalDate.of(0000, 01, 01)));
	            insertInstruction.setDate(7, Date.valueOf(LocalDate.of(0000, 01, 01)));
	            insertInstruction.executeUpdate();
	        }
	        else {
	        	instruction.executeUpdate("DELETE FROM employe WHERE nom_E = 'root' AND id_E != 1");
	        }
	        
	        // Récupérer les ligues
			requete = "select * from ligue";
			ResultSet liguesResult  = instruction.executeQuery(requete);
			while (liguesResult .next()) {
	            int idLigue = liguesResult.getInt("id_L");
	            String nomLigue = liguesResult.getString("nom_L");
	            Ligue ligue = gestionPersonnel.addLigue(idLigue, nomLigue);
	            
	            // Récupérer les employés de chaque ligue
	            requete = "SELECT * FROM employe WHERE Id_L = ?";
	            PreparedStatement employesStatement = connection.prepareStatement(requete);
	            employesStatement.setInt(1, idLigue);
	            ResultSet employesResult = employesStatement.executeQuery();
	            while (employesResult.next()) {
	                int idEmploye = employesResult.getInt("id_E");
	                String nomEmploye = employesResult.getString("nom_E");
	                String prenomEmploye = employesResult.getString("prenom");
	                String mailEmploye = employesResult.getString("mail");
	                String passwordEmploye = employesResult.getString("mdp");
	                String statutEmploye = employesResult.getString("statut");
	                LocalDate dateArrivee = employesResult.getDate("Date_Arri").toLocalDate();
	                LocalDate dateDepart = employesResult.getDate("Date_Dep").toLocalDate();
	                if (dateArrivee != null && dateDepart != null) {
	                	dateArrivee = LocalDate.of(2024, 1, 1);
	                	dateDepart = LocalDate.of(2024, 1, 1);
	                	
                    Employe employe = gestionPersonnel.addEmploye(gestionPersonnel, ligue, nomEmploye, prenomEmploye, mailEmploye, passwordEmploye, statutEmploye, dateArrivee, dateDepart);
	                employe.setID(idEmploye); // Définir l'ID de l'employé
	                ligue.addEmploye(employe); // Ajouter l'employé à la ligue
	                
	                }
	            }
			}
		}
		catch (SQLException e)
		{
			System.out.println(e);
		}
		
	    // Si root n'existe pas, le créer
	    if (!rootExiste) {
	        try {
	            PreparedStatement instruction = connection.prepareStatement("INSERT INTO employe (nom_E, prenom, mail, mdp, statut, Date_Dep, Date_Arri) VALUES (?, ?, ?, ?, ?, ?, ?)");
	            instruction.setString(1, "root");
	            instruction.setString(2, "");
	            instruction.setString(3, "");
	            instruction.setString(4, "toor");
	            instruction.setString(5, "Admin");
	            instruction.setDate(6, Date.valueOf(LocalDate.of(0000, 01, 01)));
	            instruction.setDate(7, Date.valueOf(LocalDate.of(0000, 01, 01)));
	            instruction.executeUpdate();
	        } catch (SQLException exception) {
	            exception.printStackTrace();
	        }
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
			instruction = connection.prepareStatement("update employe set nom_E=(?), prenom=(?), mail=(?), mdp=(?), statut=(?), date_dep=(?), date_arri=(?) where id_E = (?)", Statement.RETURN_GENERATED_KEYS);
			instruction.setString(1, employe.getNom());
			instruction.setString(2, employe.getPrenom());
			instruction.setString(3, employe.getMail());
			instruction.setString(4, employe.getPassword());
			instruction.setString(5, employe.getStatut());
			instruction.setDate(6, dateArriveeSQL);
			instruction.setDate(7, dateDepartSQL);
			instruction.setInt(8, employe.getID());
			instruction.executeUpdate();
		} 
		catch (SQLException exception) 
		{
			exception.printStackTrace();
			throw new SauvegardeImpossible(exception);
		}		
	}
}

