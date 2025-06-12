package fr.univartois.butinfo.sae.odf;

import fr.univartois.butinfo.sae.odf.model.Adresse;
import fr.univartois.butinfo.sae.odf.model.ClientEtablissementPublic;
import fr.univartois.butinfo.sae.odf.model.Commune;
import fr.univartois.butinfo.sae.odf.model.TypeEtablissement;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * Classe de test pour ClientEtablissementPublic.
 * Cette classe teste les fonctionnalités spécifiques à un client établissement public,
 * notamment l'ajout de points de fidélité et les getters/setters.
 */
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class ClientEtablissementPublicTest {

    private ClientEtablissementPublic client;

    @BeforeEach
    public void setUp() {
        client = new ClientEtablissementPublic();
        client.setNom("Lycée National");
        client.setType(TypeEtablissement.EPIC);
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
     * Vérifie que le type de client est bien "Établissement public".
     */
    @Test
    void testGetTypeClient() {
        assertEquals("Établissement public", client.getTypeClient());
    }

    /**
     * Test de la méthode toString() pour vérifier qu'elle contient les informations attendues.
     */
    @Test
    void testToStringContainsExpectedValues() {
        client.setNom("Lycée National");
        client.setType(TypeEtablissement.EPIC);

        String result = client.toString();

        assertTrue(result.contains("ClientEtablissementPublic"));
        assertTrue(result.contains("Lycée National"));              // Vérifie le nom
        assertTrue(result.contains("EPIC"));                        // Vérifie le type (via .toString() de l'enum)
        assertTrue(result.contains("Établissement public"));        // Vérifie typeClient (via .toString())
    }



    /**
     * Test des getters et setters personnalisés.
     */
    @Test
    void testGettersAndSetters() {
        String nomEtablissement = "Établissement Industriel";
        TypeEtablissement typeEtablissement = TypeEtablissement.EPIC;

        client.setNom(nomEtablissement);
        client.setType(typeEtablissement);

        assertEquals(nomEtablissement, client.getNom());
        assertEquals(typeEtablissement, client.getType());
    }
}

