package testsUnitaires;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

import personnel.*;

class testLigue 
{
	GestionPersonnel gestionPersonnel = GestionPersonnel.getGestionPersonnel();
	
	@Test
	void createLigue() throws SauvegardeImpossible
	{
		Ligue ligue = gestionPersonnel.addLigue("Fl�chettes");
		assertEquals("Flechettes", ligue.getNom());
	}

	@Test
	void addEmploye() throws SauvegardeImpossible
	{
		Ligue ligue = gestionPersonnel.addLigue("Fl�chettes");
		Employe employe = ligue.addEmploye("Bouchard", "G�rard", "g.bouchard@gmail.com", "azerty"); 
		assertEquals(employe, ligue.getEmployes().first());
	}
	@Test
	void getNom() throws SauvegardeImpossible
	{
		Ligue ligue = gestionPersonnel.addLigue("Fl�chettes");
		assertEquals("Fl�chettes", ligue.getNom());
	}
	
	@Test
	void setNom() throws SauvegardeImpossible
	{
		Ligue ligue = gestionPersonnel.addLigue("Fl�chettes");
		ligue.setNom("Billard");
		assertEquals("Billard", ligue.getNom());
	}
	
	@Test
	void getAdministrateur() throws SauvegardeImpossible
	{
		Ligue ligue = gestionPersonnel.addLigue("Fl�chettes");
		Employe employe = ligue.addEmploye("Bouchard", "Gerard", "g.bouchard@gmail.com", "azerty"); 
		ligue.setAdministrateur(employe);
		assertEquals("root", employe.getNom());
	}

}