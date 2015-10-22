package drugware_v20;

import static org.junit.Assert.*;

import java.util.List;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

public class ClientTest {
	private Client client = null;
	
	@Before
	public void setUp() throws Exception {
		client = new Client("ABCD12345678", "nom1", "prenom");
	}

	@Test
	public void testClient() {
		assertNotNull(client);
	}

	@Test
	public void testGetNom() {
		assertEquals(client.getNom(),"nom1");
	}

	@Test
	public void testSetNom() {
		client.setNom("nom2");
		assertEquals(client.getNom(), "nom2");
	}

	@Test
	public void testGetPrenom() {
		assertEquals(client.getPrenom(),"prenom1");
	}

	@Test
	public void testSetPrenom() {
		client.setPrenom("prenom2");
		assertEquals(client.getPrenom(), "prenom2");
	}

	@Test
	public void testGetNAM() {
		assertEquals(client.getNAM(), "ABCD12345678");
	}

	@Test
	public void testSetNAM() {
		client.setNAM("ZYXW98765432");
		assertEquals(client.getNAM(), "ZYXW98765432");
	}

	@Test
	public void testGetPrescriptions() {
		fail("Not yet implemented");
	}

	@Test
	public void testSetPrescriptions() {
		Prescription prescription = new Prescription("NomDebileTest", 15.0, 2);
		List<Prescription> listePrescription = client.getPrescriptions();
		listePrescription.add(prescription);
		client.setPrescriptions(listePrescription);
		assertSame(client.getPrescriptions(), listePrescription);
	}

	@Test
	public void testAfficherClient() {
		assertEquals(client.afficherClient(),"ABCD12345678 nom1 prenom1");
	}

}
