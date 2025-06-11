package fr.univartois.butinfo.sae.odf;

import fr.univartois.butinfo.sae.odf.model.Adresse;
import fr.univartois.butinfo.sae.odf.model.ClientEtablissementPublic;
import fr.univartois.butinfo.sae.odf.model.Commune;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * Classe de test pour ClientEtablissementPublic.
 * Cette classe teste les fonctionnalités spécifiques à un client établissement public,
 * notamment l'ajout de points de fidélité et les getters/setters.
 */
public class ClientEtablissementPublicTest {

    private ClientEtablissementPublic client;

    @BeforeEach
    public void setUp() {
        client = new ClientEtablissementPublic();
    }

    /**
     * Test de la méthode ajoutPointsFidelite pour le client établissement public.
     * Les tranches sont de 500 euros, avec 10 points par tranche.
     */
    @Test
    public void testAjoutPointsFidelite() {
        client.ajoutPointsFidelite(100.0); // 0 tranche
        assertEquals(0, client.getPointsFidelite());

        client.ajoutPointsFidelite(500.0); // 1 tranche
        assertEquals(10, client.getPointsFidelite());

        client.ajoutPointsFidelite(1200.0); // 2 tranches
        assertEquals(30, client.getPointsFidelite());
    }

    /**
     * Test des getters et setters pour les attributs spécifiques de ClientEtablissementPublic.
     * On vérifie que le type de client est bien "ETABLISSEMENT_PUBLIC".
     */
    @Test
    void testGetTypeClient() {
        assertEquals("Établissement public", client.getTypeClient());
    }

    /**
     * Test de la méthode toString() pour vérifier qu'elle contient les informations attendues.
     * On vérifie que les champs spécifiques à ClientEtablissementPublic sont présents.
     */
    @Test
    void testToStringContainsExpectedValues() {
        client.setTelephone("0606060606");
        client.setEmail("public@etab.fr");
        client.setAdresse(new Adresse(10, "Place de la République", new Commune("33", "Bordeaux", "Gironde")));

        String result = client.toString();

        assertTrue(result.contains("ClientEtablissementPublic"));
        assertTrue(result.contains("0606060606"));
        assertTrue(result.contains("public@etab.fr"));
        assertTrue(result.contains("Place de la République"));
        assertTrue(result.contains("Bordeaux"));
    }
}

