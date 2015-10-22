package drugware_v20;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TestName;
import org.junit.rules.TestWatcher;
import org.junit.runner.Description;

public class PharmacieTest {
	private static String watchedLog;
	@Rule 
	public TestName nomMethode = new TestName();
	  @Rule
	  public TestWatcher watchman= new TestWatcher() {
	      @Override
	      protected void failed(Throwable e, Description description) {
	          System.out.println("ERREUR: Ce test a �chou�.\n");
	      }

	      @Override
	      protected void succeeded(Description description) {
	    	  System.out.println();
	         }
	     };
	private Pharmacie pharmacie = null;

	@Before
	public void setUp() throws Exception {
		System.out.println("D�but du test  - Pharmacie : "+ nomMethode.getMethodName());
		pharmacie = new Pharmacie();
	}
	@After
	public void tearDown() throws Exception{
		System.out.println("Fin du test - Pharmacie : " + nomMethode.getMethodName());
	}

	@Test
	public void testPharmacie() {
		System.out.println("test le constructeur");
		assertNotNull(pharmacie);
	}

	@Test
	public void testGetLesClients() {
		System.out.println("test le getter de la liste des clients");
		assertNotNull(pharmacie.getLesClients());
	}

	@Test
	public void testSetLesClients() {
		System.out.println("test le setter de la liste des clients");
		List<Client> listeClient = new ArrayList<Client>();
		pharmacie.setLesClients(listeClient);
		assertSame(pharmacie.getLesClients(), listeClient);
	}

	@Test
	public void testGetLesMedicaments() {
		System.out.println("test le getter de la liste de m�dicament");
		assertNotNull(pharmacie.getLesMedicaments());
	}

	@Test
	public void testSetLesMedicaments() {
		System.out.println("test le setter de la liste de m�dicament");
		List<Medicament> listeMedicament = new ArrayList<Medicament>();
		pharmacie.setLesMedicaments(listeMedicament);
		assertSame(pharmacie.getLesMedicaments(), listeMedicament);
	}

	@Test
	public void testSiClientExisteListeClientVide() {
		System.out.println("test la m�thode siClientExiste dans le cas que la liste de client est vide");
		assertFalse(pharmacie.siClientExiste("ABCD12345678"));
	}

	@Test
	public void testSiClientExisteClientInexistant() {
		System.out.println("test la m�thode siClientExiste avec un NAM qui n'est pas pr�sent dans la liste de client et la liste de client est initialis�e");
		pharmacie.ajouterClient("ZYXW98765432", "nom1", "prenom");
		assertFalse(pharmacie.siClientExiste("ABCD12345678"));
	}

	@Test
	public void testSiClientExisteClientExistant() {
		System.out.println("test la m�thode siClientExiste avec un NAM d'un client qui se trouve dans la liste de client et la liste de client est initialis�e");
		pharmacie.ajouterClient("ABCD12345678", "nom1", "prenom");
		assertTrue(pharmacie.siClientExiste("ABCD12345678"));
	}

	@Test
	public void testSiClientExisteClientExistantMinuscule() {
		System.out.println("test la m�thode siClientExiste qui a re�u en argument le NAM d'un client qui se trouve dans la liste, les lettres sont en minuscule. La liste de client est initialis�e");
		pharmacie.ajouterClient("ABCD12345678", "nom1", "prenom");
		assertTrue(pharmacie.siClientExiste("abcd12345678"));
	}

	@Test
	public void testAjouterClient() {
		System.out.println("test la m�thode ajouterClient");
		pharmacie.ajouterClient("ABCD12345678", "nom1", "prenom");
		assertNotNull(pharmacie.getLesClients().get(0));

	}

	@Test
	public void testGetPrescriptionsClientClientInexistant() {
		System.out.println("test le getter pour avoir la prescription d'un client d'un client qui n'existe pas");
		assertNull(pharmacie.getPrescriptionsClient("ZYXW98765432"));
	}
	@Test
	public void testGetPrescriptionsClientClientExistantAucunePrescription() {
		System.out.println("test le getter pour avoir la prescription d'un client d'un client qui existe, mais qui n'a pas de prescription");
		pharmacie.ajouterClient("ABCD12345678", "nom1", "prenom");
		assertEquals(pharmacie.getPrescriptionsClient("ABCD12345678").size(), 0);
	}
	@Test
	public void testGetPrescriptionsClientClientExistantPrescriptionZero() {
		System.out.println("test le getter pour avoir la prescription d'un client. Le client existe et poss�de des prescriptions, mais un renouvellement de z�ro.");
		pharmacie.ajouterClient("ABCD12345678", "nom1", "prenom");
		Prescription prescription = new Prescription("NomDebileTest", 15.0, 0);
		List<Prescription> listePrescription = new ArrayList<Prescription>();
		listePrescription.add(prescription);
		pharmacie.getLesClients().get(0).setPrescriptions(listePrescription);
		assertEquals(pharmacie.getPrescriptionsClient("ABCD12345678"),
				listePrescription);
	}
	@Test
	public void testGetPrescriptionsClientClientMinusculePrescriptionValide() {
		System.out.println("test le getter pour avoir la prescription d'un client d'un client qui existe avec une prescription valide, mais les lettre du NAM de la requ�te sont minuscule");
		pharmacie.ajouterClient("ABCD12345678", "nom1", "prenom");
		Prescription prescription = new Prescription("NomDebileTest", 15.0, 2);
		List<Prescription> listePrescription = new ArrayList<Prescription>();
		listePrescription.add(prescription);
		pharmacie.getLesClients().get(0).setPrescriptions(listePrescription);
		assertSame(pharmacie.getPrescriptionsClient("abcd12345678"), listePrescription);
	}

	@Test
	public void testGetPrescriptionsClientClientExistantAvecPrescription() {
		System.out.println("test le getter pour avoir la prescription d'un client. Le client existe et poss�de des prescriptions et des renouvellements valide (plus grand que z�ro).");
		pharmacie.ajouterClient("ABCD12345678", "nom1", "prenom");
		Prescription prescription = new Prescription("NomDebileTest", 15.0, 2);
		List<Prescription> listePrescription = new ArrayList<Prescription>();
		listePrescription.add(prescription);
		pharmacie.getLesClients().get(0).setPrescriptions(listePrescription);
		assertEquals(pharmacie.getPrescriptionsClient("ABCD12345678"),
				listePrescription);
	}

	@Test
	public void testServirPrescriptionListeMedicamentVideClientVide() {
		System.out.println("test la m�thode ServirPrescription Si la liste de m�dicament est vide et la liste de client est vide");
		assertNull(pharmacie.servirPrescription("ABCD12345678",
				"NomDebileTest"));
	}
	
	@Test
	public void testServirPrescriptionListeMedicamentInexistantClientVide(){
		System.out.println("Test si la liste de client est vide, mais la liste de m�dicament ne contient pas le m�dic de la prescription");
		lireMedicaments();
		assertNull(pharmacie.servirPrescription("ABCD12345678", "Polynectar"));
		
	}
	
	@Test
	public void testServirPrescriptionListeClientInexistantMedicVide() {
		System.out.println("test si la liste de m�dic est vide, mais le client ne se trouve pas dans la liste de client existant");
		lireClients();
		assertNull(pharmacie.servirPrescription("ABCD12345678",
				"NomDebileTest"));
	}
	@Test
	public void testServirPrescriptionListeClientInexistantMedicInexistant() {
		System.out.println("test si le client ne se trouve pas dans la liste de client existant et la liste de m�dicament ne contient pas le m�dic de la prescription");
		lireClients();
		lireMedicaments();
		assertNull(pharmacie.servirPrescription("ABCD12345678",
				"Polynectar"));
	}
	@Test
	public void testServirPrescriptionListeClientValideMedicInexistant() {
		System.out.println("test la m�thode servirPrescription, mais il n'y a pas de client");
		lireClients();
		lireMedicaments();
		assertNull(pharmacie.servirPrescription("RACJ12345678",
				"Polynectar"));
	}
	@Test
	public void testServirPrescriptionListeClientValideMedicVide() {
		System.out.println("test la m�thode servirPrescription, mais il n'y a pas de client");
		lireClients();
		assertNull(pharmacie.servirPrescription("RACJ12345678",
				"Polynectar"));
	}
	
	@Test
	public void testServirPrescriptionListeClientInexistantMedicValide() {
		System.out.println("test la m�thode servirPrescription, mais il n'y a pas de client");
		lireClients();
		lireMedicaments();
		assertNull(pharmacie.servirPrescription("ABCD12345678",
				"NomDebileTest"));
	}
	@Test
	public void testServirPrescriptionListeClientVideMedicValide() {
		System.out.println("test la m�thode servirPrescription, mais il n'y a pas de client");
		lireMedicaments();
		assertNull(pharmacie.servirPrescription("ABCD12345678",
				"NomDebileTest"));
	}
	@Test
	public void testServirPrescriptionListeClientNull() {
		System.out.println("test la m�thode servirPrescription, mais il n'y a pas de client");
		lireMedicaments();
		assertNull(pharmacie.servirPrescription(null,
				"NomDebileTest"));
	}
	@Test
	public void testServirPrescriptionListeMedicNull() {
		System.out.println("test la m�thode servirPrescription, mais il n'y a pas de client");
		assertNull(pharmacie.servirPrescription("ABCD12345678",
				null));
	}
	@Test
	public void testServirPrescriptionListeMedicNullClientNull() {
		System.out.println("test la m�thode servirPrescription, mais il n'y a pas de client");
		assertNull(pharmacie.servirPrescription(null,
				null));
	}
	
	@Test
	public void testServirPrescriptionExistantPresMinuscule() {
		System.out.println("test la m�thode servirPrescription, le nom de la "
				+ "prescription re�u en argument accepte les minuscules � la "
				+ "place des majuscules et les majuscules � la place des "
				+ "minuscules");
		
		lireClients();
		
		Medicament medic1 = new Medicament();
		medic1.setNomMarque("NomDebileTest");
		double[] dosePossibles1 = {1.0,10.0,15.0, 0, 0,0,0,0};
		medic1.setDosesPossibles(dosePossibles1);
		medic1.setUnite("g");
		pharmacie.getLesMedicaments().add(medic1);
		
		Prescription prescription = new Prescription("NomDebileTest", 30.0, 2);
		List<Prescription> listePrescription = new ArrayList<Prescription>();
		listePrescription.add(prescription);
		pharmacie.getLesClients().get(0).setPrescriptions(listePrescription);
		
		ArrayList<String> message = new ArrayList<String>();
		message.add("2 de doses de 15.0 g");
		assertEquals(message, pharmacie.servirPrescription("RACJ12345678", "noMdEbiletEst"));
	}

	@Test
	public void testServirPrescriptionClientExistantMinuscule() {
		System.out.println("test la m�thode servirPrescription, mais les lettres du NAM re�u en argument sont en minuscule");
		pharmacie.ajouterClient("ABCD12345678", "nom1", "prenom");
		assertNull(pharmacie.servirPrescription("abcd12345678",
				"NomDebileTest"));
	}
	@Test
	public void testServirPrescriptionClientExistantMaisAutre() {
		System.out.println("test la m�thode servirPrescription, la liste de client n'est pas vide, mais le client recherch� n'existe pas");
		pharmacie.ajouterClient("ABCD12345678", "nom1", "prenom");
		assertNull(pharmacie.servirPrescription("ZYXW12345678",
				"NomDebileTest"));
	}

	@Test
	public void testServirPrescriptionListePrescVide() {
		System.out.println("test la m�thode servirPrescription, un client existe, mais ne poss�de aucune prescription");
		pharmacie.ajouterClient("ABCD12345678", "nom1", "prenom");
		assertNull(pharmacie.servirPrescription("ABCD12345678",
				"NomDebileTest"));
	}

	@Test
	public void testServirPrescriptionAutrePres() {
		System.out.println("test la m�thode servirPrescription, un client existe et poss�de une prescription, mais pas une des prescriptions qui n'est pas la liste");
		pharmacie.ajouterClient("ABCD12345678", "nom1", "prenom");
		Prescription prescription = new Prescription("NomDebileTest", 15.0, 2);
		List<Prescription> listePrescription = new ArrayList<Prescription>();
		listePrescription.add(prescription);
		pharmacie.getLesClients().get(0).setPrescriptions(listePrescription);
		assertNull(pharmacie.servirPrescription("ABCD12345678", "NomDebile2"));
	}

	@Test
	public void testServirPrescriptionExistantPresRenouvellementPlusOuEqual1() {
		System.out.println("la prescription existe et est plus grand ou �qual � 1");
		
		lireClients();
		
		Medicament medic1 = new Medicament();
		medic1.setNomMarque("NomDebileTest");
		double[] dosePossibles1 = {1.0,10.0,15.0, 0, 0,0,0,0};
		medic1.setDosesPossibles(dosePossibles1);
		medic1.setUnite("g");
		pharmacie.getLesMedicaments().add(medic1);
		
		Prescription prescription = new Prescription("NomDebileTest", 30.0, 2);
		List<Prescription> listePrescription = new ArrayList<Prescription>();
		listePrescription.add(prescription);
		pharmacie.getLesClients().get(0).setPrescriptions(listePrescription);
		
		ArrayList<String> message = new ArrayList<String>();
		message.add("2 de doses de 15.0 g");
		assertEquals(message, pharmacie.servirPrescription("RACJ12345678", "NomDebileTest"));
	}

	@Test
	public void testServirPrescriptionExistantPresRenouvellementZero() {
		System.out.println("test la m�thode servirPrescription, avec une prescription valide, mais un renouvellement de z�ro");
		pharmacie.ajouterClient("ABCD12345678", "nom1", "prenom");
		Prescription prescription = new Prescription("NomDebileTest", 15.0, 0);
		List<Prescription> listePrescription = new ArrayList<Prescription>();
		listePrescription.add(prescription);
		pharmacie.getLesClients().get(0).setPrescriptions(listePrescription);
		assertNull(pharmacie.servirPrescription("ABCD12345678",
				"NomDebileTest"));
	}
	
	@Test
	public void testTrouverInteractionAucunMedicament() {
		System.out.println("test la m�thode trouverInteraction, mais la liste de m�dicament est vide");
		assertFalse(pharmacie.trouverInteraction("NomDebileTest", "AutreNomDebile"));
	}
	
	@Test
	public void testTrouverInteractionUnMedicament() {
		System.out.println("test la m�thode trouverInteraction, mais la liste de m�dicament poss�de un seul m�dicament");
		Medicament medic1 = new Medicament();
		medic1.setNomMarque("NomDebileTest");
		medic1.setNomMolecule("prout");
		String[] Listeinterac = {};
		medic1.setInteractions(Listeinterac);
		pharmacie.getLesMedicaments().add(medic1);
		assertFalse(pharmacie.trouverInteraction("NomDebileTest", "AutreNomDebile"));
	}
	
	@Test
	public void testTrouverInteractionDeuxMedicamentInteractionVide() {
		System.out.println("test la m�thode trouverInteraction, avec deux m�dicaments, mais leur liste d'interaction est vide");
		Medicament medic1 = new Medicament();
		medic1.setNomMarque("NomDebileTest");
		medic1.setNomMolecule("prout");
		String[] Listeinterac = {};
		medic1.setInteractions(Listeinterac);
		pharmacie.getLesMedicaments().add(medic1);
		
		Medicament medic2 = new Medicament();
		medic2.setNomMarque("AutreNomDebile");
		medic2.setNomMolecule("rhalala");
		medic2.setInteractions(Listeinterac);
		pharmacie.getLesMedicaments().add(medic2);
		assertFalse(pharmacie.trouverInteraction("NomDebileTest", "AutreNomDebile"));
	}
	
	@Test
	public void testTrouverInteractionMedic1InteractionPasMedic2() {
		System.out.println("test la m�thode trouverInteraction, le 1er m�dicament poss�de un m�dicament avec qui il int�ragie, mais qui n'est pas m�dicament 2");
		Medicament medic1 = new Medicament();
		medic1.setNomMarque("NomDebileTest");
		medic1.setNomMolecule("prout");
		String[] Listeinterac1 = {"ohlalala"};
		medic1.setInteractions(Listeinterac1);
		pharmacie.getLesMedicaments().add(medic1);
		
		Medicament medic2 = new Medicament();
		medic2.setNomMarque("AutreNomDebile");
		medic2.setNomMolecule("rhalala");
		String[] Listeinterac2 = {};
		medic2.setInteractions(Listeinterac2);
		pharmacie.getLesMedicaments().add(medic2);
		assertFalse(pharmacie.trouverInteraction("NomDebileTest", "AutreNomDebile"));
	}
	
	@Test
	public void testTrouverInteractionPossedeMaisInteragiePas() {
		System.out.println("test la m�thode trouverInteraction. les deux m�thode poss�de des m�dicaments avec lequels il int�ragie, mais n'est pas �gal � l'autre m�dicament donn� en argument.");
		Medicament medic1 = new Medicament();
		medic1.setNomMarque("NomDebileTest");
		medic1.setNomMolecule("prout");
		String[] Listeinterac1 = {"ohlalala"};
		medic1.setInteractions(Listeinterac1);
		pharmacie.getLesMedicaments().add(medic1);
		
		Medicament medic2 = new Medicament();
		medic2.setNomMarque("AutreNomDebile");
		medic2.setNomMolecule("rhalala");
		String[] Listeinterac2 = {"ohlalala"};
		medic2.setInteractions(Listeinterac2);
		pharmacie.getLesMedicaments().add(medic2);
		assertFalse(pharmacie.trouverInteraction("NomDebileTest", "AutreNomDebile"));
	}
	@Test
	public void testTrouverInteractionMedic2InteractionPasMedic1() {
		System.out.println("test de la m�thode trouverInteraction. le 2e m�dicament poss�de un liste d'interaction, mais aucun n'est le m�dicament 1");
		Medicament medic1 = new Medicament();
		medic1.setNomMarque("NomDebileTest");
		medic1.setNomMolecule("prout");
		String[] Listeinterac1 = {};
		medic1.setInteractions(Listeinterac1);
		pharmacie.getLesMedicaments().add(medic1);
		Medicament medic2 = new Medicament();
		medic2.setNomMarque("AutreNomDebile");
		medic2.setNomMolecule("rhalala");
		String[] Listeinterac2 = {"ohlalala"};
		medic2.setInteractions(Listeinterac2);
		pharmacie.getLesMedicaments().add(medic2);
		assertFalse(pharmacie.trouverInteraction("NomDebileTest", "AutreNomDebile"));
	}
	
	@Test
	public void testTrouverInteractionMedic1TrouveInteraction() {
		System.out.println("test de la m�thode trouverInteraction. Le m�dicament 1 devrait r�agir avec le m�dicament 2");
		Medicament medic1 = new Medicament();
		medic1.setNomMarque("NomDebileTest");
		medic1.setNomMolecule("prout");
		String[] Listeinterac1 = {"rhalala"};
		medic1.setInteractions(Listeinterac1);
		pharmacie.getLesMedicaments().add(medic1);
		Medicament medic2 = new Medicament();
		medic2.setNomMarque("AutreNomDebile");
		medic2.setNomMolecule("rhalala");
		String[] Listeinterac2 = {};
		medic2.setInteractions(Listeinterac2);
		pharmacie.getLesMedicaments().add(medic2);
		assertTrue(pharmacie.trouverInteraction("NomDebileTest", "AutreNomDebile"));
	}
	@Test
	public void testTrouverInteractionMedic2TrouveInteraction() {
		System.out.println("test de la m�thode trouverInteraction, le m�dicament 2 int�ragie avec le m�dicament 1");
		Medicament medic1 = new Medicament();
		medic1.setNomMarque("NomDebileTest");
		medic1.setNomMolecule("prout");
		String[] Listeinterac1 = {};
		medic1.setInteractions(Listeinterac1);
		pharmacie.getLesMedicaments().add(medic1);
		Medicament medic2 = new Medicament();
		medic2.setNomMarque("AutreNomDebile");
		medic2.setNomMolecule("rhalala");
		String[] Listeinterac2 = {"prout"};
		medic2.setInteractions(Listeinterac2);
		pharmacie.getLesMedicaments().add(medic2);
		assertTrue(pharmacie.trouverInteraction("NomDebileTest", "AutreNomDebile"));
	}
	
	@Test
	public void testTrouverInteractionMedic2EtMedic1TrouveInteraction() {
		System.out.println("test de la m�thode trouverInteraction, les deux m�dicament int�ragie avec l'autre. m�dic 1 =  nom de Marque, m�dic2 = Nom de marque");
		Medicament medic1 = new Medicament();
		medic1.setNomMarque("NomDebileTest");
		medic1.setNomMolecule("prout");
		String[] Listeinterac1 = {"rhalala"};
		medic1.setInteractions(Listeinterac1);
		pharmacie.getLesMedicaments().add(medic1);
		Medicament medic2 = new Medicament();
		medic2.setNomMarque("AutreNomDebile");
		medic2.setNomMolecule("rhalala");
		String[] Listeinterac2 = {"prout"};
		medic2.setInteractions(Listeinterac2);
		pharmacie.getLesMedicaments().add(medic2);
		assertTrue(pharmacie.trouverInteraction("NomDebileTest", "AutreNomDebile"));
	}
	@Test
	public void testTrouverInteractionMedic1NomMolecule() {
		System.out.println("test de la m�thode trouverInteraction. Interaction. 1er = nom Mol�cule. 2e = nom Marque");
		Medicament medic1 = new Medicament();
		medic1.setNomMarque("NomDebileTest");
		medic1.setNomMolecule("prout");
		String[] Listeinterac1 = {"rhalala"};
		medic1.setInteractions(Listeinterac1);
		pharmacie.getLesMedicaments().add(medic1);
		Medicament medic2 = new Medicament();
		medic2.setNomMarque("AutreNomDebile");
		medic2.setNomMolecule("rhalala");
		String[] Listeinterac2 = {"prout"};
		medic2.setInteractions(Listeinterac2);
		pharmacie.getLesMedicaments().add(medic2);
		assertTrue(pharmacie.trouverInteraction("prout", "AutreNomDebile"));
	}
	@Test
	public void testTrouverInteractionMedic2NomMolecule() {
		System.out.println("test de la m�thode trouverInteraction. Interaction. 1er = nom Marque, 2e = nom mol�cule");
		Medicament medic1 = new Medicament();
		medic1.setNomMarque("NomDebileTest");
		medic1.setNomMolecule("prout");
		String[] Listeinterac1 = {"rhalala"};
		medic1.setInteractions(Listeinterac1);
		pharmacie.getLesMedicaments().add(medic1);
		Medicament medic2 = new Medicament();
		medic2.setNomMarque("AutreNomDebile");
		medic2.setNomMolecule("rhalala");
		String[] Listeinterac2 = {"prout"};
		medic2.setInteractions(Listeinterac2);
		pharmacie.getLesMedicaments().add(medic2);
		assertTrue(pharmacie.trouverInteraction("NomDebileTest", "rhalala"));
	}
	@Test
	public void testTrouverInteractionMedic2EtMedic1NomMolecule() {
		System.out.println("test de la m�thode trouverInteraction. Interaction. 1er = nom Mol�cule. 2e = nom mol�cule");
		Medicament medic1 = new Medicament();
		medic1.setNomMarque("NomDebileTest");
		medic1.setNomMolecule("prout");
		String[] Listeinterac1 = {"rhalala"};
		medic1.setInteractions(Listeinterac1);
		pharmacie.getLesMedicaments().add(medic1);
		Medicament medic2 = new Medicament();
		medic2.setNomMarque("AutreNomDebile");
		medic2.setNomMolecule("rhalala");
		String[] Listeinterac2 = {"prout"};
		medic2.setInteractions(Listeinterac2);
		pharmacie.getLesMedicaments().add(medic2);
		assertTrue(pharmacie.trouverInteraction("prout", "rhalala"));
	}
	@Test
	public void testTrouverInteractionMedicAvecLuiMeme() {
		System.out.println("test de la m�thode trouverInteraction. Ne devrait pas int�ragir lorsque medic1 et medic2 est la m�me chose");
		Medicament medic1 = new Medicament();
		medic1.setNomMarque("NomDebileTest");
		medic1.setNomMolecule("prout");
		String[] Listeinterac1 = {"rhalala"};
		medic1.setInteractions(Listeinterac1);
		pharmacie.getLesMedicaments().add(medic1);
		assertFalse(pharmacie.trouverInteraction("NomDebileTest", "NomDebileTest"));
	}
		
	@Test
	public void testAjouterPrescriptionListeClientVide(){
		System.out.println("test si la liste de client est vide");
		pharmacie.ajouterPrescription("ABCD12345678", "Polynectar", 15.0, 1);
		assertEquals(0, pharmacie.getLesClients().size());
	}
	
	@Test
	public void testAjouterPrescriptionClientInexistant(){
		System.out.println("test si la m�thode si la liste de client ne contient pas le client recherch�");
		pharmacie.ajouterClient("RACJ12345678", "Racine", "Jean");
		pharmacie.ajouterPrescription("ABCD12345678", "Polynectar", 15.0, 1);
		assertNotEquals("ABCD12345678",pharmacie.getLesClients().get(0).getNAM());
		
	}
	
	@Test
	public void testAjouterPrescriptionMedicamentInexistant(){
		System.out.println("test la m�thode si on essaye d'ajouter la prescription d'un m�dicament qui n'existe pas");
		lireClients();
		
		Medicament medic1 = new Medicament();
		medic1.setNomMarque("NomDebileTest");
		medic1.setNomMolecule("prout");
		pharmacie.getLesMedicaments().add(medic1);
		
		pharmacie.ajouterPrescription("RACJ12345678", "Polynectar", 15.0, 1);
		assertEquals(0,pharmacie.getLesClients().get(0).getPrescriptions().size());
	}
	
	@Test
	public void testAjouterPrescriptionMedicamentVide(){
		System.out.println("test la m�thode si on essaye d'ajouter la prescription alors que la liste des m�dicaments est vide");
		lireClients();
		
		pharmacie.ajouterPrescription("RACJ12345678", "Polynectar", 15.0, 1);
		assertEquals(0,pharmacie.getLesClients().get(0).getPrescriptions().size());
	}
	
	@Test
	public void testAjouterPrescriptionMedicamentExistantNouvellePrescription(){
		System.out.println("test la m�thode si le client existe et le m�dicament de la prescription existe aussi. alors qu'il n'y avait aucune prescription pour ce m�dicament");
		lireClients();
		
		Medicament medic1 = new Medicament();
		medic1.setNomMarque("NomDebileTest");
		medic1.setNomMolecule("prout");
		pharmacie.getLesMedicaments().add(medic1);
		
		pharmacie.ajouterPrescription("RACJ12345678", "NomDebileTest", 15.0, 1);
		assertEquals("NomDebileTest",pharmacie.getLesClients().get(0).getPrescriptions().get(0).getMedicamentAPrendre());
	}
	@Test
	public void testAjouterPrescriptionMedicamentExistantPrescriptionExistante(){
		System.out.println("test la m�thode si le client existe et avait d�j� une prescription pour le m�dicament.");
		lireClients();
		
		Medicament medic1 = new Medicament();
		medic1.setNomMarque("NomDebileTest");
		medic1.setNomMolecule("prout");
		pharmacie.getLesMedicaments().add(medic1);	
		
		Prescription prescription = new Prescription("NomDebileTest", 15.0, 1);
		List<Prescription> listePrescription = new ArrayList<Prescription>();
		listePrescription.add(prescription);
		pharmacie.getLesClients().get(0).setPrescriptions(listePrescription);
		
		pharmacie.ajouterPrescription("RACJ12345678", "NomDebileTest", 15.0, 15);
		assertEquals(15,pharmacie.getLesClients().get(0).getPrescriptions().get(0).getRenouvellements());
	}
	
	@Test
	public void testAjouterPrescriptionClientMinuscule(){
		System.out.println("test la m�thode le nam du client est inscrit en minuscule et le client existe");
		lireClients();
		Medicament medic1 = new Medicament();
		medic1.setNomMarque("NomDebileTest");
		medic1.setNomMolecule("prout");
		pharmacie.getLesMedicaments().add(medic1);
		
		pharmacie.ajouterPrescription("racJ12345678", "NomDebileTest", 15.0, 1);
		assertEquals("NomDebileTest",pharmacie.getLesClients().get(0).getPrescriptions().get(0).getMedicamentAPrendre());
	}
	
	public void lireClients() {
		Client client1 = new Client("RACJ12345678","Racine","Jean");
		Client client2 = new Client("SARJ12345678","Sartre", "Jean-Paul");
		Client client3 = new Client("JOLE12345678","Joly","Estelle");
		
		List<Client> listeClients = new ArrayList<Client>();
		listeClients.add(client1);
		listeClients.add(client2);
		listeClients.add(client3);
		
		pharmacie.setLesClients(listeClients);
	}

	public void lireMedicaments() {
		Medicament medic1 = new Medicament();
		medic1.setNomMarque("NomDebileTest");
		medic1.setNomMolecule("prout");
		String[] Listeinterac1 = {"rhalala"};
		double[] dosePossibles1 = {1.0,10.0,15.0, 0, 0,0,0,0};
		String[] usage1 = {"maux de t�te", "maux de dos"};
		medic1.setInteractions(Listeinterac1);
		medic1.setDosesPossibles(dosePossibles1);
		medic1.setUnite("g");
		medic1.setUsages(usage1);
		
		Medicament medic2 = new Medicament();
		medic1.setNomMarque("AutreNomDebile");
		medic1.setNomMolecule("rhalala");
		String[] Listeinterac2 = {"pouet"};
		double[] dosePossibles2 = {5.0,30.0,50.0};
		String[] usage2 = {"vomissement", "mal aux pieds", "otites"};
		medic1.setInteractions(Listeinterac2);
		medic1.setDosesPossibles(dosePossibles2);
		medic1.setUnite("mg");
		medic1.setUsages(usage2);
		
		Medicament medic3 = new Medicament();
		medic1.setNomMarque("JeConnaisLaBrumeGrele");
		medic1.setNomMolecule("MatinDHiver");
		String[] Listeinterac3 = {"lievreBlanc"};
		double[] dosePossibles3 = {1.0,10.0,15.0};
		String[] usage3 = {"d�s�quilibre �motif", "trouble de vue", "d�pression", "perte d'audition"};
		medic1.setInteractions(Listeinterac3);
		medic1.setDosesPossibles(dosePossibles3);
		medic1.setUnite("ml");
		medic1.setUsages(usage3);
		
		List<Medicament> listeMedicaments = new ArrayList<Medicament>();
		listeMedicaments.add(medic1);
		listeMedicaments.add(medic2);
		listeMedicaments.add(medic3);
		
		pharmacie.setLesMedicaments(listeMedicaments);
	}
	
	public void lirePrescriptions() {
		
		Client client1 = new Client("RACJ12345678","Racine","Jean");
		Client client2 = new Client("SARJ12345678","Sartre", "Jean-Paul");
		
		List<Client> listeClients = new ArrayList<Client>();
		listeClients.add(client1);
		listeClients.add(client2);
		
		pharmacie.setLesClients(listeClients);
		
		Prescription prescription1 = new Prescription("VDM", 50.0, 3 );
		Prescription prescription2 = new Prescription("RTFM", 15.0 , 1);
		Prescription prescription3 = new Prescription("FGSFDS",30.0 , 2);
		
		List<Prescription> listePrescriptions1 = new ArrayList<Prescription>();
		listePrescriptions1.add(prescription1);
		listePrescriptions1.add(prescription2);
		List<Prescription> listePrescriptions2 = new ArrayList<Prescription>();
		listePrescriptions2.add(prescription3);
		
		pharmacie.getLesClients().get(0).setPrescriptions(listePrescriptions1);
		pharmacie.getLesClients().get(1).setPrescriptions(listePrescriptions2);
		
	}
	

}
