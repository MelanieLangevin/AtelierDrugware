// auteurs: Maud El-Hachem
// 2015
package drugware_v20;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class Pharmacie {
	private List<Client> lesClients;
	private List<Medicament> lesMedicaments;

	public Pharmacie() {
		this.lesMedicaments = new ArrayList<>();
		this.lesClients = new ArrayList<>();
	}

	/**
	 * @return the lesClients
	 */
	public List<Client> getLesClients() {
		return lesClients;
	}

	/**
	 * @param lesClients
	 *            the lesClients to set
	 */
	public void setLesClients(List<Client> lesClients) {
		this.lesClients = lesClients;
	}

	/**
	 * @return the lesMedicaments
	 */
	public List<Medicament> getLesMedicaments() {
		return lesMedicaments;
	}

	/**
	 * @param lesMedicaments
	 *            the lesMedicaments to set
	 */
	public void setLesMedicaments(List<Medicament> lesMedicaments) {
		this.lesMedicaments = lesMedicaments;
	}

	public void lireClients() {
		Fichiers fichier = new Fichiers();
		fichier.lireClients(lesClients);
	}

	public void lireMedicaments() {
		Fichiers fichier = new Fichiers();
		fichier.lireMedicaments(lesMedicaments);
	}

	public void lirePrescriptions() {
		Fichiers fichier = new Fichiers();
		fichier.lirePrescriptions(lesClients);
	}

	public boolean siClientExiste(String NAM) {
		for (Iterator<Client> it = lesClients.iterator(); it.hasNext();) {
			Client itClient = it.next();
			if (itClient.getNAM().equalsIgnoreCase(NAM)) {
				return true;
			}
		}
		return false;
	}

	public void ajouterClient(String NAM, String nom, String prenom) {
		this.lesClients.add(new Client(NAM, nom, prenom));
	}

	public List<Prescription> getPrescriptionsClient(String NAM) {
		for (Iterator<Client> it = lesClients.iterator(); it.hasNext();) {
			Client itClient = it.next();
			if (itClient.getNAM().equalsIgnoreCase(NAM)) {
				return itClient.getPrescriptions();
			}
		}
		return null;
	}

	public ArrayList<String> servirPrescription(String NAM, String nomMedic) {
		ArrayList<String> suggestionsDoses = null;
		Medicament medicament = new Medicament();
		int index = -1;
		if (NAM != null && nomMedic != null) {
			for (Iterator<Medicament> it = lesMedicaments.iterator(); it
					.hasNext();) {
				Medicament itMedicament = it.next();
				if (nomMedic.equalsIgnoreCase(itMedicament.getNomMarque())) {
					index = lesMedicaments.indexOf(itMedicament);
					medicament = lesMedicaments.get(index);
				}
			}
			for (Iterator<Client> it = lesClients.iterator(); it.hasNext();) {
				Client itClient = it.next();
				if (itClient.getNAM().equalsIgnoreCase(NAM)) {
					for (Iterator<Prescription> it2 = itClient
							.getPrescriptions().iterator(); it2.hasNext();) {
						Prescription courante = it2.next();
						if (courante.getMedicamentAPrendre().equalsIgnoreCase(
								nomMedic))
							if (courante.getRenouvellements() >= 1) {
								courante.setRenouvellements(courante
										.getRenouvellements() - 1);
								suggestionsDoses = new ArrayList<String>();
								double restant = courante.getDose();
								int quantite = 0;
								for (int i = medicament.getDosesPossibles().length - 1; i >= 0; i--) {
									if (medicament.getDosesPossibles()[i] != 0) {
										while (restant >= medicament
												.getDosesPossibles()[i]) {
											restant -= medicament
													.getDosesPossibles()[i];
											quantite++;
										}
										if(quantite != 0){
											suggestionsDoses.add(quantite
													+ " de doses de "
													+ medicament
															.getDosesPossibles()[i]
													+ " " + medicament.getUnite());
											quantite = 0;
										}
									}

								}
							}
					}
				}
			}

		}

		return suggestionsDoses;
	}

	public void ajouterPrescription(String NAM, String medicament, double dose,
			int renouvellement) {
		boolean prescTrouvee = false;
		boolean medicExiste = false;

		for (Iterator<Client> it = lesClients.iterator(); it.hasNext();) {
			Client itClient = it.next();
			if (itClient.getNAM().equalsIgnoreCase(NAM)) {
				for(Iterator<Medicament> it2 = lesMedicaments.iterator(); it2.hasNext();){
					Medicament itMedicament = it2.next();
					if(itMedicament.getNomMarque().equalsIgnoreCase(medicament)){
						medicExiste = true;
					}
				}
				if(medicExiste){
					for (Iterator<Prescription> it3 = itClient.getPrescriptions()
						.iterator(); it3.hasNext();) {
					Prescription itPresc = it3.next();
					if (itPresc.getMedicamentAPrendre().equalsIgnoreCase(
							medicament)) {
						int index = itClient.getPrescriptions()
								.indexOf(itPresc);
						itClient.getPrescriptions().get(index).setDose(dose);
						itClient.getPrescriptions().get(index)
								.setRenouvellements(renouvellement);
						prescTrouvee = true;
					}
				}
				if (!prescTrouvee) {
					Prescription prescription = new Prescription(medicament,
							dose, renouvellement);
					itClient.getPrescriptions().add(prescription);
				}
				}
			}
		}
	}

	public boolean trouverInteraction(String medicament1, String medicament2) {
		Medicament medic1 = new Medicament();
		Medicament medic2 = new Medicament();

		for (Iterator<Medicament> it = lesMedicaments.iterator(); it.hasNext();) {
			Medicament courant = it.next();
			if (courant.getNomMolecule().equalsIgnoreCase(medicament1)
					|| courant.getNomMarque().equalsIgnoreCase(medicament1))
				medic1 = courant;
			if (courant.getNomMolecule().equalsIgnoreCase(medicament2)
					|| courant.getNomMarque().equalsIgnoreCase(medicament2))
				medic2 = courant;
		}
		if (medic1.getNomMolecule() != "" && medic2.getNomMolecule() != "") {
			for (int i = 0; i < medic1.getInteractions().length; i++) {
				if (medic1.getInteractions()[i].equalsIgnoreCase(medic2
						.getNomMolecule()))
					return true;
			}

			for (int i = 0; i < medic2.getInteractions().length; i++) {
				if (medic2.getInteractions()[i].equalsIgnoreCase(medic1
						.getNomMolecule()))
					return true;

			}
		}

		return false;
	}

	public void ecrireClients() {
		Fichiers fichier = new Fichiers();
		fichier.ecrireClients(lesClients);
	}

	public void ecrirePrescriptions() {
		Fichiers fichier = new Fichiers();
		fichier.lirePrescriptions(lesClients);
	}
}
