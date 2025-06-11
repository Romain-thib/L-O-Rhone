package fr.univartois.butinfo.sae.odf;

import fr.univartois.butinfo.sae.odf.model.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.Mockito.*;

import javafx.collections.ObservableList;

class StockGlobalTest {

    private StockGlobal stockGlobal;
    private Categorie cat;
    private Entrepot entrepot;

    /**
     * Classe de test pour StockGlobal.
     * Teste les méthodes add, sub et triQuantite.
     */
    @BeforeEach
    void setUp() {
        stockGlobal = new StockGlobal();
        cat = mock(Categorie.class);
        entrepot = new Entrepot(1, "Entrepôt A", new Adresse(1, "Rue", new Commune("62", "Arras", "62")));
    }

    /**
     * Test de la méthode add pour vérifier qu'elle ajoute un stock correctement.
     * Vérifie que la quantité est bien ajoutée et que le stock est présent dans la liste.
     */
    @Test
    void testAddModifiesQuantityIfStockExists() {
        StockEau s1 = new StockEau(cat, entrepot, 5);
        StockEau s2 = new StockEau(cat, entrepot, 3); // même stock (equals doit retourner true)

        stockGlobal.add(s1);
        stockGlobal.add(s2);

        // Vérifie que la quantité a été mise à jour dans le premier objet
        assertThat(s1.getQuantite()).isEqualTo(8);
        assertThat(stockGlobal.getStocks()).hasSize(1);
    }

    /**
     * Test de la méthode add pour vérifier qu'elle ajoute un nouveau stock si celui-ci n'existe pas.
     * Vérifie que la taille de la liste augmente et que le stock est bien ajouté.
     */
    @Test
    void testAddNewStockIncreasesSize() {
        StockEau s1 = new StockEau(cat, entrepot, 7);
        stockGlobal.add(s1);

        assertThat(stockGlobal.getStocks()).hasSize(1);
        assertThat(stockGlobal.getStocks().get(0)).isEqualTo(s1);
    }

    /**
     * Test de la méthode sub pour vérifier qu'elle diminue la quantité d'un stock existant.
     * Vérifie que la quantité est bien diminuée et que le stock est toujours présent dans la liste.
     */
    @Test
    void testSubDecreasesQuantity() {
        StockEau s = new StockEau(cat, entrepot, 10);
        stockGlobal.add(s);

        stockGlobal.sub(0, 4); // diminue de 4

        assertThat(s.getQuantite()).isEqualTo(6);
    }

    /**
     * Test de la méthode sub pour vérifier qu'elle lance une exception si l'index est invalide.
     * Vérifie que l'exception est bien lancée avec le message approprié.
     */
    @Test
    void testSubThrowsIndexExceptionIfInvalidIndex() {
        assertThatThrownBy(() -> stockGlobal.sub(1, 2))
                .isInstanceOf(IndexOutOfBoundsException.class)
                .hasMessageContaining("Invalid index");
    }

    /**
     * * Test de la méthode triQuantite pour vérifier qu'elle trie les stocks par quantité.
     */
    @Test
    void testTriQuantiteSortsByQuantity() {
        // Trois stocks avec catégories ou entrepôts différents
        StockEau s1 = new StockEau(mock(Categorie.class), new Entrepot(1, "Entrepôt A", new Adresse(1, "Rue", new Commune("62", "Arras", "62"))), 20);
        StockEau s2 = new StockEau(mock(Categorie.class), new Entrepot(2, "Entrepôt B", new Adresse(2, "Avenue", new Commune("75", "Paris", "75"))), 5);
        StockEau s3 = new StockEau(mock(Categorie.class), new Entrepot(3, "Entrepôt C", new Adresse(3, "Boulevard", new Commune("13", "Marseille", "13"))), 10);

        stockGlobal.add(s1);
        stockGlobal.add(s2);
        stockGlobal.add(s3);

        stockGlobal.triQuantite();

        ObservableList<StockEau> sortedStocks = stockGlobal.getStocks();

        assertThat(sortedStocks).hasSize(3);
        assertThat(sortedStocks.get(0)).isEqualTo(s2); // 5
        assertThat(sortedStocks.get(1)).isEqualTo(s3); // 10
        assertThat(sortedStocks.get(2)).isEqualTo(s1); // 20
    }

}

