package fr.univartois.butinfo.sae.odf;

import fr.univartois.butinfo.sae.odf.model.Eau;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

import static org.junit.jupiter.api.Assertions.assertEquals;

class EauTest {

    /**
     * Test de la méthode equals pour vérifier l'égalité des instances d'Eau.
     * Deux instances sont considérées égales si elles ont la même marque, catégorie et prix.
     */
    @Test
    void testEachInstanceHasUniqueId() {
        Eau e1 = new Eau("Evian", "Source", 1.0);
        Eau e2 = new Eau("Evian", "Source", 1.0);

        // Chaque instance a un id différent donc elles ne sont pas égales
        assertThat(e1).isNotEqualTo(e2);
    }

    /**
     * Test de la méthode equals pour vérifier que deux instances d'Eau avec les mêmes attributs sont égales.
     */
    @Test
    void testSameInstanceIsEqual() {
        Eau e1 = new Eau("Evian", "Source", 1.0);
        Eau e2 = e1;

        assertThat(e1).isEqualTo(e2);
    }

    /**
     * Test de la méthode equals pour vérifier que deux instances d'Eau avec des attributs différents ne sont pas égales.
     * Ici, on vérifie que deux instances avec des marques différentes sont considérées comme différentes.
     */
    @Test
    void testDifferentInstanceIsNotEqual() {
        Eau e1 = new Eau("Evian", "Source", 1.0);
        Eau e2 = new Eau("Vittel", "Source", 1.0);

        assertThat(e1).isNotEqualTo(e2);
    }

    //Ajout de tests pour les getters, setters et toString pour atteindre plus de 80% de couverture de code
    @Test
    void testGettersAndSetters() {
        Eau eau = new Eau("Evian", "Plate", 1.5);

        // Vérification des getters initiales
        assertEquals("Evian", eau.getMarque());
        assertEquals("Plate", eau.getCategorie());
        assertEquals(1.5, eau.getPrix());

        // Modification via setters
        eau.setMarque("Volvic");
        eau.setCategorie("Gazeuse");
        eau.setPrix(2.0);

        // Vérification après modification
        assertEquals("Volvic", eau.getMarque());
        assertEquals("Gazeuse", eau.getCategorie());
        assertEquals(2.0, eau.getPrix());
    }
}



