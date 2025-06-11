package fr.univartois.butinfo.sae.odf;

import fr.univartois.butinfo.sae.odf.model.*;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;


class StockEauTest {

    /**
     * Test de la méthode equals pour vérifier que deux instances de StockEau avec des données différentes ne sont pas égales.
     * Deux instances sont considérées égales si elles ont la même catégorie, le même entrepôt et la même quantité.
     */
    @Test
    void testEqualsWithSameData() {
        Categorie cat = mock(Categorie.class);
        Entrepot entrepot = new Entrepot(1, "Depot", new Adresse(1, "Rue", new Commune("62", "Arras", "62")));

        StockEau s1 = new StockEau(cat, entrepot, 10);
        StockEau s2 = new StockEau(cat, entrepot, 10);

        assertThat(s1).isEqualTo(s2);
    }

    /**
     * Test de la méthode equals pour vérifier que deux instances de StockEau avec des quantités différentes ne sont pas égales.
     * Deux instances sont considérées égales si elles ont la même catégorie, le même entrepôt et la même quantité.
     */
    @Test
    void testNotEqualsWithDifferentCategorieOrEntrepot() {
        Categorie cat1 = mock(Categorie.class);
        Categorie cat2 = mock(Categorie.class);

        Entrepot entrepot1 = new Entrepot(1, "Depot1", new Adresse(1, "Rue", new Commune("62", "Arras", "62")));
        Entrepot entrepot2 = new Entrepot(2, "Depot2", new Adresse(2, "Rue", new Commune("62", "Lens", "62")));

        StockEau s1 = new StockEau(cat1, entrepot1, 10);
        StockEau s2 = new StockEau(cat2, entrepot2, 10);

        assertThat(s1).isNotEqualTo(s2);
    }
}

