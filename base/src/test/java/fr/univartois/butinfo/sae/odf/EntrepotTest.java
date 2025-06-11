package fr.univartois.butinfo.sae.odf;

import fr.univartois.butinfo.sae.odf.model.Adresse;
import fr.univartois.butinfo.sae.odf.model.Commune;
import fr.univartois.butinfo.sae.odf.model.Entrepot;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;

import static org.junit.jupiter.api.Assertions.assertEquals;

class EntrepotTest {

    /**
     * Test de la méthode equals pour vérifier que deux instances d'Entrepot avec les mêmes attributs sont égales.
     * Deux instances sont considérées égales si elles ont le même code, nom et adresse.
     */
    @Test
    void testEqualsWithSameCode() {
        Adresse adresse = new Adresse(1, "Rue A", new Commune("62", "Arras", "62"));
        Entrepot e1 = new Entrepot(1, "Entrepôt Nord", adresse);
        Entrepot e2 = new Entrepot(1, "Autre nom", adresse);

        assertThat(e1).isEqualTo(e2);
    }

    /**
     * Test de la méthode equals pour vérifier que deux instances d'Entrepot avec des attributs différents ne sont pas égales.
     * Ici, on vérifie que deux instances avec des codes différents sont considérées comme différentes.
     */
    @Test
    void testNotEqualsWithDifferentCode() {
        Adresse adresse = new Adresse(1, "Rue A", new Commune("62", "Arras", "62"));
        Entrepot e1 = new Entrepot(1, "Entrepôt Nord", adresse);
        Entrepot e2 = new Entrepot(2, "Entrepôt Sud", adresse);

        assertThat(e1).isNotEqualTo(e2);
    }

    //Ajout de tests pour les getters, setters et toString pour atteindre plus de 80% de couverture de code
    @Test
    void testGettersAndSetters() {
        Adresse adresse = new Adresse(1, "Rue A", new Commune("62", "Arras", "62"));
        Entrepot entrepot = new Entrepot(1, "Entrepôt Test", adresse);

        // Vérification des getters
        assertEquals(1, entrepot.getCode()); // code est final donc pas de setter
        assertEquals("Entrepôt Test", entrepot.getNom());
        assertEquals(adresse, entrepot.getAdresse());

        // Modification avec les setters
        entrepot.setNom("Nouveau Nom");
        Adresse nouvelleAdresse = new Adresse(2, "Rue B", new Commune("59", "Lille", "59"));
        entrepot.setAdresse(nouvelleAdresse);

        // Vérification des getters après modification
        assertEquals("Nouveau Nom", entrepot.getNom());
        assertEquals(nouvelleAdresse, entrepot.getAdresse());
    }




}

