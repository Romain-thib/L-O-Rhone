package fr.univartois.butinfo.sae.odf;

import fr.univartois.butinfo.sae.odf.model.Eau;
import fr.univartois.butinfo.sae.odf.model.LigneDeCommande;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class LigneDeCommandeTest {

    /**
     * Test de la création d'une ligne de commande.
     * Vérifie que l'objet LigneDeCommande est correctement initialisé avec une instance d'Eau et une quantité.
     */
    @Test
    public void testLigneDeCommandeCreation() {
        Eau eau = new Eau("Evian", "Plate", 1.5);
        LigneDeCommande ligne = new LigneDeCommande(eau, 10);

        assertEquals(eau, ligne.getEau());
        assertEquals(10, ligne.getQuantite());
    }

    /**
     * Test de la méthode equals pour vérifier l'égalité des instances de LigneDeCommande.
     * Deux lignes de commande sont considérées égales si elles ont la même eau et la même quantité.
     */
    @Test
    public void testToString() {
        Eau eau = new Eau("Cristaline", "Gazeuse", 1.0);
        LigneDeCommande ligne = new LigneDeCommande(eau, 5);

        String result = ligne.toString();
        assertTrue(result.contains("Cristaline"));
        assertTrue(result.contains("5"));
    }
}

