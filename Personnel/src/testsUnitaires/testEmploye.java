package testsUnitaires;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

import personnel.*;

public class testEmploye {
	
	GestionPersonnel gestionPersonnel = GestionPersonnel.getGestionPersonnel();
	
	@Test
	void createEmploye() throws SauvegardeImpossible
	{
		Ligue ligue = gestionPersonnel.addLigue("Fl�chettes");
		Employe employe = ligue.addEmploye("Bouchard", "G�rard", "g.bouchard@gmail.com", "azerty");
		assertEquals("Bouchard", employe.getNom());
		assertEquals("G�rard", employe.getPrenom());
		assertEquals("g.bouchard@gmail.com", employe.getMail());
		assertEquals(1, employe.checkPassword("azerty"));
	}
	
	@Test
	void NomEmploye() throws SauvegardeImpossible
	{
		Ligue ligue = gestionPersonnel.addLigue("Fl�chettes");
		Employe employe = ligue.addEmploye("Bouchard", "G�rard", "g.bouchard@gmail.com", "azerty");
		assertEquals("Bouchard", employe.getNom());
	}
	
	@Test
	void PrenomEmploye() throws SauvegardeImpossible
	{
		Ligue ligue = gestionPersonnel.addLigue("Fl�chettes");
		Employe employe = ligue.addEmploye("Bouchard", "G�rard", "g.bouchard@gmail.com", "azerty");
		assertEquals("G�rard", employe.getPrenom());
	}
	
	@Test
	void MailEmploye() throws SauvegardeImpossible
	{
		Ligue ligue = gestionPersonnel.addLigue("Fl�chettes");
		Employe employe = ligue.addEmploye("Bouchard", "G�rard", "g.bouchard@gmail.com", "azerty");
		assertEquals("g.bouchard@gmail.com", employe.getMail());
	}
}