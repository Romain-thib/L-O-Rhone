package fr.univartois.butinfo.sae.odf;

import fr.univartois.butinfo.sae.odf.model.ClientParticulier;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * Classe de test pour ClientParticulier.
 * Teste les méthodes ajoutPointsFidelite, getters, setters et toString.
 */
public class ClientParticulierTest {

    private ClientParticulier client;

    @BeforeEach
    void setUp() {
        client = new ClientParticulier();
    }

    @Test
    void testAjoutPointsFidelite() {
        client.ajoutPointsFidelite(50.0); // 0 tranche
        assertEquals(0, client.getPointsFidelite());

        client.ajoutPointsFidelite(100.0); // 1 tranche
        assertEquals(10, client.getPointsFidelite());

        client.ajoutPointsFidelite(350.0); // 3 tranches
        assertEquals(40, client.getPointsFidelite());
    }

    @Test
    void testGettersSetters() {
        client.setNom("Dupont");
        assertEquals("Dupont", client.getNom());

        client.setPrenom("Jean");
        assertEquals("Jean", client.getPrenom());
    }

    @Test
    void testToStringContainsFields() {
        client.setNom("Dupont");
        client.setPrenom("Jean");

        String str = client.toString();

        // Vérifie que les champs formatés dans toString sont bien présents
        assertTrue(str.contains("ClientParticulier"));
        assertTrue(str.contains("nom=Dupont"));
        assertTrue(str.contains("prenom=Jean"));
        assertTrue(str.contains("typeClient=Particulier")); // correspond à toString() de TypeClient.PARTICULIER
    }


}
