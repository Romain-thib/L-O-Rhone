package fr.univartois.butinfo.sae.odf.controller;

import fr.univartois.butinfo.sae.odf.model.Categorie;
import fr.univartois.butinfo.sae.odf.model.Entrepot;
import fr.univartois.butinfo.sae.odf.model.StockEau;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class AjoutModificationStockEauController {

    @FXML
    private ComboBox<Categorie> categorieComboBox;
    
    @FXML
    private ComboBox<Entrepot> entrepotComboBox;
    
    @FXML
    private TextField quantiteTextField;

    private ObservableList<StockEau> stockEauList;
    private ObservableList<Entrepot> entrepotsList;
    private ListView<StockEau> stockEauListView;
    private StockEau stockEnEdition; // Stock à modifier (null si ajout)
    private Stage stage;
    private Scene sceneRetour;

    /**
     * Initialise les ComboBox avec leurs valeurs
     */
    @FXML
    void initialize() {
        // Remplir la ComboBox des catégories avec toutes les valeurs de l'enum
        categorieComboBox.setItems(FXCollections.observableArrayList(Categorie.values()));
        
        // Configuration de l'affichage des catégories
        categorieComboBox.setCellFactory(listView -> new javafx.scene.control.ListCell<Categorie>() {
            @Override
            protected void updateItem(Categorie item, boolean empty) {
                super.updateItem(item, empty);
                if (empty || item == null) {
                    setText(null);
                } else {
                    setText(item.toString());
                }
            }
        });
        
        categorieComboBox.setButtonCell(new javafx.scene.control.ListCell<Categorie>() {
            @Override
            protected void updateItem(Categorie item, boolean empty) {
                super.updateItem(item, empty);
                if (empty || item == null) {
                    setText(null);
                } else {
                    setText(item.toString());
                }
            }
        });
        
        // Configuration de l'affichage des entrepôts
        entrepotComboBox.setCellFactory(listView -> new javafx.scene.control.ListCell<Entrepot>() {
            @Override
            protected void updateItem(Entrepot item, boolean empty) {
                super.updateItem(item, empty);
                if (empty || item == null) {
                    setText(null);
                } else {
                    setText(item.getNom());
                }
            }
        });
        
        entrepotComboBox.setButtonCell(new javafx.scene.control.ListCell<Entrepot>() {
            @Override
            protected void updateItem(Entrepot item, boolean empty) {
                super.updateItem(item, empty);
                if (empty || item == null) {
                    setText(null);
                } else {
                    setText(item.getNom());
                }
            }
        });
    }

    /**
     * Définit la liste des stocks d'eau
     */
    public void setStockEauList(ObservableList<StockEau> stockEauList) {
        this.stockEauList = stockEauList;
    }

    /**
     * Définit la liste des entrepôts disponibles
     */
    public void setEntrepotsList(ObservableList<Entrepot> entrepotsList) {
        this.entrepotsList = entrepotsList;
        if (entrepotComboBox != null) {
            entrepotComboBox.setItems(entrepotsList);
        }
    }

    /**
     * Définit la ListView pour le rafraîchissement
     */
    public void setStockEauListView(ListView<StockEau> stockEauListView) {
        this.stockEauListView = stockEauListView;
    }

    /**
     * Définit le stock à éditer (null pour un ajout)
     */
    public void setStockEnEdition(StockEau stock) {
        this.stockEnEdition = stock;
        if (stock != null) {
            // Pré-remplir les champs pour l'édition
            categorieComboBox.getSelectionModel().select(stock.getCategorie());
            entrepotComboBox.getSelectionModel().select(stock.getEntrepot());
            quantiteTextField.setText(String.valueOf(stock.getQuantite()));
        } else {
            // Vider les champs pour un ajout
            viderChamps();
        }
    }

    /**
     * Définit la fenêtre et la scène de retour
     */
    public void setStage(Stage stage) {
        this.stage = stage;
    }

    public void setSceneRetour(Scene sceneRetour) {
        this.sceneRetour = sceneRetour;
    }

    /**
     * Méthode appelée lors du clic sur le bouton Valider
     */
    @FXML
    private void valider() {
        try {
            // Validation des champs
            if (!validerSaisie()) {
                return;
            }

            Categorie categorieSelectionnee = categorieComboBox.getSelectionModel().getSelectedItem();
            Entrepot entrepotSelectionne = entrepotComboBox.getSelectionModel().getSelectedItem();
            int quantite = Integer.parseInt(quantiteTextField.getText().trim());

            if (stockEnEdition == null) {
                // Mode ajout
                StockEau nouveauStock = new StockEau(categorieSelectionnee, entrepotSelectionne, quantite);
                
                // Vérifier si un stock existe déjà pour cette combinaison catégorie/entrepôt
                StockEau stockExistant = trouverStockExistant(categorieSelectionnee, entrepotSelectionne);
                if (stockExistant != null) {
                    // Mettre à jour la quantité du stock existant
                    stockExistant.deltaQuantity(quantite);
                    if (stockEauListView != null) {
                        stockEauListView.refresh();
                    }
                } else {
                    // Ajouter le nouveau stock
                    stockEauList.add(nouveauStock);
                }
            } else {
                // Mode édition - vérifier si la combinaison catégorie/entrepôt a changé
                if (!stockEnEdition.getCategorie().equals(categorieSelectionnee) || 
                    !stockEnEdition.getEntrepot().equals(entrepotSelectionne)) {
                    
                    // La combinaison a changé, supprimer l'ancien et créer un nouveau
                    stockEauList.remove(stockEnEdition);
                    StockEau nouveauStock = new StockEau(categorieSelectionnee, entrepotSelectionne, quantite);
                    
                    // Vérifier si un stock existe déjà pour la nouvelle combinaison
                    StockEau stockExistant = trouverStockExistant(categorieSelectionnee, entrepotSelectionne);
                    if (stockExistant != null) {
                        stockExistant.deltaQuantity(quantite);
                    } else {
                        stockEauList.add(nouveauStock);
                    }
                } else {
                    // Seule la quantité a changé, mettre à jour directement
                    int index = stockEauList.indexOf(stockEnEdition);
                    if (index >= 0) {
                        StockEau stockMisAJour = new StockEau(categorieSelectionnee, entrepotSelectionne, quantite);
                        stockEauList.set(index, stockMisAJour);
                    }
                }
                
                if (stockEauListView != null) {
                    stockEauListView.refresh();
                }
            }

            // Fermer la fenêtre
            annuler();

        } catch (NumberFormatException e) {
            afficherErreur("Erreur de saisie", "La quantité doit être un nombre entier valide.");
        } catch (Exception e) {
            afficherErreur("Erreur", "Une erreur s'est produite : " + e.getMessage());
        }
    }

    /**
     * Trouve un stock existant pour une combinaison catégorie/entrepôt donnée
     */
    private StockEau trouverStockExistant(Categorie categorie, Entrepot entrepot) {
        for (StockEau stock : stockEauList) {
            if (stock.getCategorie().equals(categorie) && stock.getEntrepot().equals(entrepot)) {
                return stock;
            }
        }
        return null;
    }

    /**
     * Valide la saisie utilisateur
     */
    private boolean validerSaisie() {
        if (categorieComboBox.getSelectionModel().getSelectedItem() == null) {
            afficherErreur("Erreur de saisie", "Veuillez sélectionner une catégorie.");
            return false;
        }

        if (entrepotComboBox.getSelectionModel().getSelectedItem() == null) {
            afficherErreur("Erreur de saisie", "Veuillez sélectionner un entrepôt.");
            return false;
        }

        String quantiteText = quantiteTextField.getText().trim();
        if (quantiteText.isEmpty()) {
            afficherErreur("Erreur de saisie", "Veuillez saisir une quantité.");
            return false;
        }

        try {
            int quantite = Integer.parseInt(quantiteText);
            if (quantite < 0) {
                afficherErreur("Erreur de saisie", "La quantité ne peut pas être négative.");
                return false;
            }
        } catch (NumberFormatException e) {
            afficherErreur("Erreur de saisie", "La quantité doit être un nombre entier valide.");
            return false;
        }

        return true;
    }

    /**
     * Méthode appelée lors du clic sur le bouton Annuler
     */
    @FXML
    private void annuler() {
        if (stage != null && sceneRetour != null) {
            stage.setScene(sceneRetour);
            stage.setTitle("L'Ô Rhône - Gestion des Stocks");
        }
    }

    /**
     * Vide tous les champs de saisie
     */
    private void viderChamps() {
        categorieComboBox.getSelectionModel().clearSelection();
        entrepotComboBox.getSelectionModel().clearSelection();
        quantiteTextField.clear();
    }

    /**
     * Affiche une boîte de dialogue d'erreur
     */
    private void afficherErreur(String titre, String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(titre);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}