package fr.univartois.butinfo.sae.odf;

import fr.univartois.butinfo.sae.odf.model.Adresse;
import fr.univartois.butinfo.sae.odf.model.ClientEntreprise;
import fr.univartois.butinfo.sae.odf.model.Commune;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class ClientEntrepriseTest {

    private ClientEntreprise clientEntreprise;

    /**
     * Initialisation du client entreprise avant chaque test.
     * Cette méthode est appelée avant chaque test pour s'assurer que
     * chaque test commence avec un nouvel objet ClientEntreprise.
     */
    @BeforeEach
    void setUp() {
        clientEntreprise = new ClientEntreprise();
    }

    /**
     * Test de la méthode ajoutPointsFidelite pour le client entreprise.
     * Les tranches sont de 1000 euros, avec 10 points par tranche.
     */
    @Test
    void testAjoutPointsFidelite() {
        clientEntreprise.ajoutPointsFidelite(100.0); // 0 tranche
        assertEquals(0, clientEntreprise.getPointsFidelite());

        clientEntreprise.ajoutPointsFidelite(1000.0); // 1 tranche
        assertEquals(10, clientEntreprise.getPointsFidelite());

        clientEntreprise.ajoutPointsFidelite(2500.0); // 2 tranches
        assertEquals(30, clientEntreprise.getPointsFidelite());
    }


    /**
     * Test des getters et setters pour les attributs spécifiques de ClientEntreprise.
     */
    @Test
    void testGetTypeClient() {
        assertEquals("Entreprise", clientEntreprise.getTypeClient());
    }

    /**
     * Test de la méthode toString() pour vérifier qu'elle contient les informations attendues.
     */
    @Test
    void testToStringContainsExpectedValues() {
        clientEntreprise.setNomEntreprise("Nom SARL");
        clientEntreprise.setSiret("123456789");

        String toStringResult = clientEntreprise.toString();

        assertTrue(toStringResult.contains("Nom SARL"));
        assertTrue(toStringResult.contains("123456789"));
        assertTrue(toStringResult.contains("Entreprise"));
    }
    
    /**
     * Test des getters et setters pour les attributs spécifiques de ClientEntreprise.
     */
    @Test
    void testGettersAndSetters() {
        String nomEntreprise = "Orange";
        String siret = "12345678900000";

        clientEntreprise.setNomEntreprise(nomEntreprise);
        clientEntreprise.setSiret(siret);

        assertEquals(nomEntreprise, clientEntreprise.getNomEntreprise());
        assertEquals(siret, clientEntreprise.getSiret());
    }

}

