package fr.univartois.butinfo.sae.odf.controller;

import fr.univartois.butinfo.sae.odf.model.StockEau;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.net.URL;
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

    private ObservableList<StockEau> stockEauList;
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
                        setText(stock.getCategorie() + " - " + stock.getEntrepot() + 
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
                } else {
                    viderDetailsStock();
                }
            }
        );
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

    /**
     * Affiche les détails du stock sélectionné
     */
    private void afficherDetailsStock(StockEau stock) {
        categorieTextField.setText(stock.getCategorie().toString());
        entrepotTextField.setText(stock.getEntrepot().toString());
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

    @FXML
    private void retourAccueil() {
        if (stage != null && sceneStockEauController != null) {
            stage.setScene(sceneStockEauController);
            stage.setTitle("L'Ô Rhône - Accueil");
        }
    }
}