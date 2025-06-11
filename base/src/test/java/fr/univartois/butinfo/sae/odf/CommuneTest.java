package fr.univartois.butinfo.sae.odf;

import fr.univartois.butinfo.sae.odf.model.Commune;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * Classe de test pour Commune.
 * Teste les méthodes equals, getters, setters et toString.
 */
class CommuneTest {


    @Test
    void testEqualsWithSameData() {
        Commune c1 = new Commune("62", "Arras", "Pas-de-Calais");
        Commune c2 = new Commune("62", "Arras", "Pas-de-Calais");

        assertThat(c1).isEqualTo(c2);
    }

    @Test
    void testNotEqualsWithDifferentData() {
        Commune c1 = new Commune("62", "Arras", "Pas-de-Calais");
        Commune c2 = new Commune("59", "Lille", "Nord");

        assertThat(c1).isNotEqualTo(c2);
    }

    //Ajout de tests pour les getters, setters et toString pour atteindre plus de 80% de couverture de code
    @Test
    void testGettersAndSetters() {
        Commune commune = new Commune("62", "Arras", "Pas-de-Calais");

        // Vérification des getters initiales
        assertEquals("62", commune.getCode());
        assertEquals("Arras", commune.getNom());
        assertEquals("Pas-de-Calais", commune.getDepartement());

        // Modification via setters
        commune.setCode("59");
        commune.setNom("Lille");
        commune.setDepartement("Nord");

        // Vérification après modification
        assertEquals("59", commune.getCode());
        assertEquals("Lille", commune.getNom());
        assertEquals("Nord", commune.getDepartement());
    }
}

