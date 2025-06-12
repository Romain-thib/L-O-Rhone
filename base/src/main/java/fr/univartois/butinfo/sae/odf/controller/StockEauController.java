package fr.univartois.butinfo.sae.odf.controller;

import fr.univartois.butinfo.sae.odf.model.Entrepot;
import fr.univartois.butinfo.sae.odf.model.StockEau;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;

public class StockEauController implements Initializable {

    @FXML
    private ListView<StockEau> stockEauListView;
    @FXML
    private TextField categorieTextField;
    @FXML
    private TextField entrepotTextField;
    @FXML
    private TextField quantiteTextField;
    @FXML
    private Button ajouterButton;
    @FXML
    private Button modifierButton;
    @FXML
    private Button supprimerButton;

    private ObservableList<StockEau> stockEauList;
    private ObservableList<Entrepot> entrepotsList;
    private Stage stage;
    private Scene sceneStockEauController;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        // Configuration de l'affichage des items dans la ListView
        stockEauListView.setCellFactory(list -> {
            return new ListCell<>() {
                @Override
                public void updateItem(StockEau stock, boolean empty) {
                    super.updateItem(stock, empty);
                    if (empty || (stock == null)) {
                        setText(null);
                    } else {
                        setText(stock.getCategorie() + " - " + stock.getEntrepot().getNom() + 
                               " (Qté: " + stock.getQuantite() + ")");
                    }
                }
            };
        });

        // Listener pour la sélection d'un item
        stockEauListView.getSelectionModel().selectedItemProperty().addListener(
            (observable, oldValue, newValue) -> {
                if (newValue != null) {
                    afficherDetailsStock(newValue);
                    // Activer les boutons modifier et supprimer
                    modifierButton.setDisable(false);
                    supprimerButton.setDisable(false);
                } else {
                    viderDetailsStock();
                    // Désactiver les boutons modifier et supprimer
                    modifierButton.setDisable(true);
                    supprimerButton.setDisable(true);
                }
            }
        );

        // Désactiver les boutons modifier et supprimer au démarrage
        modifierButton.setDisable(true);
        supprimerButton.setDisable(true);
    }

    public void setStage(Stage stage) {
        this.stage = stage;
    }

    public void setScene(Scene scene) {
        this.sceneStockEauController = scene;
    }

    public void setObservableList(ObservableList<StockEau> stockEauList) {
        this.stockEauList = stockEauList;
        stockEauListView.setItems(stockEauList);
    }

    public void setEntrepotsList(ObservableList<Entrepot> entrepotsList) {
        this.entrepotsList = entrepotsList;
    }

    /**
     * Affiche les détails du stock sélectionné
     */
    private void afficherDetailsStock(StockEau stock) {
        categorieTextField.setText(stock.getCategorie().toString());
        entrepotTextField.setText(stock.getEntrepot().getNom());
        quantiteTextField.setText(String.valueOf(stock.getQuantite()));
    }

    /**
     * Vide les champs de détails
     */
    private void viderDetailsStock() {
        categorieTextField.clear();
        entrepotTextField.clear();
        quantiteTextField.clear();
    }

    /**
     * Méthode pour ajouter un nouveau stock
     */
    @FXML
    private void ajouterStock() {
        try {
            ouvrirFenetreAjoutModification(null);
        } catch (IOException e) {
            afficherErreur("Erreur", "Impossible d'ouvrir la fenêtre d'ajout : " + e.getMessage());
        }
    }

    /**
     * Méthode pour modifier le stock sélectionné
     */
    @FXML
    private void modifierStock() {
        StockEau stockSelectionne = stockEauListView.getSelectionModel().getSelectedItem();
        if (stockSelectionne != null) {
            try {
                ouvrirFenetreAjoutModification(stockSelectionne);
            } catch (IOException e) {
                afficherErreur("Erreur", "Impossible d'ouvrir la fenêtre de modification : " + e.getMessage());
            }
        }
    }

    /**
     * Méthode pour supprimer le stock sélectionné
     */
    @FXML
    private void supprimerStock() {
        StockEau stockSelectionne = stockEauListView.getSelectionModel().getSelectedItem();
        if (stockSelectionne != null) {
            // Demander confirmation avant suppression
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Confirmation de suppression");
            alert.setHeaderText("Supprimer le stock");
            alert.setContentText("Êtes-vous sûr de vouloir supprimer ce stock ?\n" +
                                "Catégorie : " + stockSelectionne.getCategorie() + "\n" +
                                "Entrepôt : " + stockSelectionne.getEntrepot().getNom() + "\n" +
                                "Quantité : " + stockSelectionne.getQuantite());

            Optional<ButtonType> result = alert.showAndWait();
            if (result.isPresent() && result.get() == ButtonType.OK) {
                stockEauList.remove(stockSelectionne);
                viderDetailsStock();
            }
        }
    }

    /**
     * Ouvre la fenêtre d'ajout/modification
     */
    private void ouvrirFenetreAjoutModification(StockEau stockAModifier) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(
            getClass().getResource("/fr.univartois.butinfo.sae.odf.view/ajout-modification-stock-eau.fxml"));
        Parent viewContent = fxmlLoader.load();

        Scene ajoutModificationScene = new Scene(viewContent);
        stage.setScene(ajoutModificationScene);
        stage.setTitle("L'Ô Rhône - " + (stockAModifier == null ? "Ajouter" : "Modifier") + " Stock");

        // Configuration du contrôleur
        AjoutModificationStockEauController controller = fxmlLoader.getController();
        controller.setStage(stage);
        controller.setSceneRetour(sceneStockEauController);
        controller.setStockEauList(stockEauList);
        controller.setStockEauListView(stockEauListView);
        
        // Passer la liste des entrepôts si disponible
        if (entrepotsList != null) {
            controller.setEntrepotsList(entrepotsList);
        }
        
        // Si c'est une modification, passer le stock à modifier
        if (stockAModifier != null) {
            controller.setStockEnEdition(stockAModifier);
        }
    }

    @FXML
    private void retourAccueil() {
        if (stage != null && sceneStockEauController != null) {
            stage.setScene(sceneStockEauController);
            stage.setTitle("L'Ô Rhône - Accueil");
        }
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