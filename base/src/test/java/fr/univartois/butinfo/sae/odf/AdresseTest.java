package fr.univartois.butinfo.sae.odf;

import fr.univartois.butinfo.sae.odf.model.Adresse;
import fr.univartois.butinfo.sae.odf.model.Commune;
import org.junit.jupiter.api.Test;


import static org.assertj.core.api.Assertions.assertThat;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * Classe de test pour la classe Adresse.
 * Cette classe vérifie l'égalité des instances d'Adresse, les getters, setters et la méthode toString.
 */
class AdresseTest {

    @Test
    void testEqualsWithSameData() {
        // Création d'une commune avec un code postal, un nom et un département
        Commune c = new Commune("62", "Arras", "Pas-de-Calais");
        // Création d'une adresse avec un numéro, une rue et la commune créée ci-dessus
        Adresse a1 = new Adresse(5, "Rue des Lilas", c);
        // Création d'une autre adresse identique mais avec une nouvelle instance Commune
        Adresse a2 = new Adresse(5, "Rue des Lilas", new Commune("62", "Arras", "Pas-de-Calais"));

        // Vérifie que les deux adresses sont considérées égales car leurs données sont identiques,
        // même si la référence de la commune est différente.
        assertThat(a1).isEqualTo(a2);
    }

    @Test
    void testNotEqualsWithDifferentData() {
        // Création d'une première adresse avec numéro 5, rue "Rue A" et commune Arras
        Adresse a1 = new Adresse(5, "Rue A", new Commune("62", "Arras", "62"));
        // Création d'une deuxième adresse avec numéro 10, rue "Rue B" et commune Lens
        Adresse a2 = new Adresse(10, "Rue B", new Commune("62", "Lens", "62"));

        // Vérifie que les deux adresses ne sont pas égales car elles diffèrent sur plusieurs champs :
        // numéro, rue et commune (nom et code postal différents).
        assertThat(a1).isNotEqualTo(a2);
    }


    //Ajout de tests pour les getters, setters et toString pour atteindre plus de 80% de couverture de code
    @Test
    void testGettersAndSetters() {
        Commune communeInitiale = new Commune("62", "Arras", "Pas-de-Calais");
        Adresse adresse = new Adresse(5, "Rue des Lilas", communeInitiale);

        // Vérifier getters initiaux
        assertEquals(5, adresse.getNumero());
        assertEquals("Rue des Lilas", adresse.getVoie());
        assertEquals(communeInitiale, adresse.getCommune());

        // Modifier avec setters
        adresse.setNumero(12);
        adresse.setVoie("Rue de la Paix");

        Commune nouvelleCommune = new Commune("75", "Paris", "Île-de-France");
        adresse.setCommune(nouvelleCommune);

        // Vérifier getters après modification
        assertEquals(12, adresse.getNumero());
        assertEquals("Rue de la Paix", adresse.getVoie());
        assertEquals(nouvelleCommune, adresse.getCommune());
    }

    /**
     * Test de la méthode toString pour vérifier que la représentation textuelle de l'adresse est correcte.
     * On vérifie que le toString contient les valeurs importantes de l'adresse.
     */
    @Test
    void testToString() {
        Commune c = new Commune("62", "Arras", "Pas-de-Calais");
        Adresse a = new Adresse(5, "Rue des Lilas", c);

        String str = a.toString();

        // Vérifier que le toString contient les valeurs importantes
        assertTrue(str.contains("numero=5"));
        assertTrue(str.contains("voie='Rue des Lilas'"));
        assertTrue(str.contains("commune=" + c.toString())); // suppose que Commune a un toString pertinent
    }
}


