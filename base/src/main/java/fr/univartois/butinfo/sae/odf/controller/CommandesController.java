package fr.univartois.butinfo.sae.odf.controller;

import fr.univartois.butinfo.sae.odf.model.Commande;
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

public class CommandesController implements Initializable {

    @FXML
    private ListView<Commande> commandesListView;
    
    @FXML
    private ListView<String> lignesCommandeListView;
    
    @FXML
    private TextField idCommandeTextField;
    
    @FXML
    private TextField clientTextField;

    private ObservableList<Commande> commandesList;
    private Stage stage;
    private Scene sceneAccueil;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        commandesListView.setCellFactory(list -> {
            return new ListCell<>() {
                @Override
                public void updateItem(Commande commande, boolean empty) {
                    super.updateItem(commande, empty);
                    if (empty || (commande == null)) {
                        setText(null);
                    } else {
                        setText("Commande #" + commande.getId() + " - Client " + commande.getClient().getCode());
                    }
                }
            };
        });

        // Listener pour la sélection d'un item
        commandesListView.getSelectionModel().selectedItemProperty().addListener(
            (observable, oldValue, newValue) -> {
                if (newValue != null) {
                    afficherDetailsCommande(newValue); 
                } else {
                    viderDetailsCommande(); 
                }
            }
        );
    }

    public void setStage(Stage stage) {
        this.stage = stage;
    }

    public void setScene(Scene scene) {
        this.sceneAccueil = scene;
    }

    public void setObservableList(ObservableList<Commande> commandesList) {
        this.commandesList = commandesList;
        commandesListView.setItems(commandesList);
    }

    /**
     * Affiche les détails de la commande sélectionnée
     */
    private void afficherDetailsCommande(Commande commande) {
        idCommandeTextField.setText(String.valueOf(commande.getId()));
        clientTextField.setText(String.valueOf(commande.getClient().getCode()));
        
        // Afficher les lignes de commande
        lignesCommandeListView.getItems().clear();
        if (commande.getLignesCommande() != null) {
            for (var ligne : commande.getLignesCommande()) {
                lignesCommandeListView.getItems().add(
                    ligne.getEau().toString() + " - Quantité: " + ligne.getQuantite()
                );
            }
        }
    }

    /**
     * Vide les champs de détails
     */
    private void viderDetailsCommande() {
        idCommandeTextField.clear();
        clientTextField.clear();
        lignesCommandeListView.getItems().clear();
    }

    @FXML
    private void retourAccueil() {
        if (stage != null && sceneAccueil != null) {
            stage.setScene(sceneAccueil);
            stage.setTitle("O-de-France - Accueil");
        }
    }
}