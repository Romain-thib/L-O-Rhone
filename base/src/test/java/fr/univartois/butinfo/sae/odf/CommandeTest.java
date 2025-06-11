package fr.univartois.butinfo.sae.odf;

import static org.junit.jupiter.api.Assertions.*;

import fr.univartois.butinfo.sae.odf.model.*;
import javafx.collections.ObservableList;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import org.junit.jupiter.api.extension.ExtendWith;
import static org.mockito.Mockito.*;
import static org.assertj.core.api.Assertions.*;

import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

/**
 * Classe de test pour Commande.
 * Teste les méthodes addLigneCommande, updateLigneCommande, montant, montantDeLaRemise et nombreBouteillesGratuites.
 */
public class CommandeTest {

    private Client clientMock;
    private Commande commande;


    @BeforeEach
    public void setUp() {
        clientMock = mock(Client.class);
        commande = new Commande(clientMock);
    }

    @Test
    public void testAddLigneCommande() {
        Eau eau = new Eau("Evian", "Plate", 2.0);
        commande.addLigneCommande(eau, 6);

        ObservableList<LigneDeCommande> lignes = commande.getlignesDeCommande();
        assertEquals(1, lignes.size());
        assertEquals(eau, lignes.get(0).getEau());
        assertEquals(6, lignes.get(0).getQuantite());
    }

    /**
     * Test de la méthode removeLigneCommande pour vérifier la suppression d'une ligne de commande.
     * Vérifie que la ligne est bien supprimée de la liste des lignes de commande.
     */
    @Test
    public void testUpdateLigneCommande() {
        Eau eau = new Eau("Evian", "Plate", 2.0);
        commande.addLigneCommande(eau, 6);
        commande.updateLigneCommande(0, 10);

        LigneDeCommande updated = commande.getlignesDeCommande().get(0);
        assertEquals(10, updated.getQuantite());
        assertEquals(eau, updated.getEau());
    }

    /**
     * Test de la méthode getMontantTotal pour vérifier le montant total de la commande.
     * Vérifie que le montant est calculé correctement en fonction des lignes de commande.
     */
    @Test
    public void testMontantCommandeSansRemise() {
        when(clientMock.getTypeClient()).thenReturn("Entreprise");

        Eau eau = new Eau("Evian", "Plate", 2.0);
        commande.addLigneCommande(eau, 5);

        double expected = 10.0;
        assertEquals(expected, commande.montant(), 0.01);
    }

    @Test
    public void testMontantCommandeAvecRemise_Particulier() {
        when(clientMock.getTypeClient()).thenReturn("Particulier");
        when(clientMock.getPointsFidelite()).thenReturn(300); // 3% remise

        Eau eau = new Eau("Evian", "Plate", 2.0);
        commande.addLigneCommande(eau, 5); // Total brut : 10 €

        double remise = 10.0 * 3; // 30 €
        assertEquals(remise, commande.montantDeLaRemise(), 0.01);
        assertEquals(-20.0, commande.montant(), 0.01); // 10 - 30 = -20
    }

    @Test
    public void testNombreBouteillesGratuites_Particulier() {
        when(clientMock.getTypeClient()).thenReturn("Particulier");

        Eau eau = new Eau("Evian", "Plate", 2.0);
        commande.addLigneCommande(eau, 24); // 2 gratuites (12 par bloc)

        assertEquals(2, commande.nombreBouteillesGratuites());
    }

    @Test
    public void testNombreBouteillesGratuites_Etablissement() {
        when(clientMock.getTypeClient()).thenReturn("Établissement public");

        Eau eau = new Eau("Volvic", "Plate", 1.5);
        commande.addLigneCommande(eau, 120); // 2 gratuites (60 par bloc)

        assertEquals(2, commande.nombreBouteillesGratuites());
    }

    @Test
    public void testNombreBouteillesGratuites_Entreprise() {
        when(clientMock.getTypeClient()).thenReturn("Entreprise");

        Eau eau = new Eau("Cristaline", "Gazeuse", 1.0);
        commande.addLigneCommande(eau, 360); // 3 gratuites (120 par bloc)

        assertEquals(3, commande.nombreBouteillesGratuites());
    }
}


