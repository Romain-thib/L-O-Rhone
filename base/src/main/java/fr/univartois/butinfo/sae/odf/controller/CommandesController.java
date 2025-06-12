package fr.univartois.butinfo.sae.odf.controller;

import fr.univartois.butinfo.sae.odf.model.Client;
import fr.univartois.butinfo.sae.odf.model.Commande;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.Optional;
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
    
    @FXML
    private Button ajouterButton;
    
    @FXML
    private Button modifierButton;
    
    @FXML
    private Button supprimerButton;

    private ObservableList<Commande> commandesList;
    private ObservableList<Client> clientsList;
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
                    // Activer les boutons modifier et supprimer
                    modifierButton.setDisable(false);
                    supprimerButton.setDisable(false);
                } else {
                    viderDetailsCommande();
                    // Désactiver les boutons modifier et supprimer
                    modifierButton.setDisable(true);
                    supprimerButton.setDisable(true);
                }
            }
        );
        
        // Initialiser les boutons comme désactivés
        modifierButton.setDisable(true);
        supprimerButton.setDisable(true);
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
    
    public void setClientsList(ObservableList<Client> clientsList) {
        this.clientsList = clientsList;
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
    private void onAjouter() {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(
                getClass().getResource("/fr.univartois.butinfo.sae.odf.view/ajout-modification-commandes.fxml"));
            Parent viewContent = fxmlLoader.load();

            Scene ajoutScene = new Scene(viewContent);
            stage.setScene(ajoutScene);
            stage.setTitle("O-de-France - Ajouter une commande");

            AjoutModificationCommandesController controller = fxmlLoader.getController();
            controller.setStage(stage);
            controller.setSceneRetour(sceneAccueil);
            controller.setCommandesList(commandesList);
            controller.setClientsList(clientsList);
            controller.setCommandesListView(commandesListView);
            
        } catch (IOException e) {
            showAlert("Erreur", "Impossible de charger la vue d'ajout : " + e.getMessage());
        }
    }

    @FXML
    private void onModifier() {
        Commande commandeSelectionnee = commandesListView.getSelectionModel().getSelectedItem();
        if (commandeSelectionnee == null) {
            showAlert("Erreur", "Veuillez sélectionner une commande à modifier.");
            return;
        }
        
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(
                getClass().getResource("/fr.univartois.butinfo.sae.odf.view/ajout-modification-commandes.fxml"));
            Parent viewContent = fxmlLoader.load();

            Scene modificationScene = new Scene(viewContent);
            stage.setScene(modificationScene);
            stage.setTitle("L'Ô Rhône - Modifier une commande");

            AjoutModificationCommandesController controller = fxmlLoader.getController();
            controller.setStage(stage);
            controller.setSceneRetour(sceneAccueil);
            controller.setCommandesList(commandesList);
            controller.setClientsList(clientsList);
            controller.setCommandesListView(commandesListView);
            controller.setCommandeAEditer(commandeSelectionnee);
            
        } catch (IOException e) {
            showAlert("Erreur", "Impossible de charger la vue de modification : " + e.getMessage());
        }
    }

    @FXML
    private void onSupprimer() {
        Commande commandeSelectionnee = commandesListView.getSelectionModel().getSelectedItem();
        if (commandeSelectionnee == null) {
            showAlert("Erreur", "Veuillez sélectionner une commande à supprimer.");
            return;
        }
        
        // Demander confirmation
        Alert confirmation = new Alert(Alert.AlertType.CONFIRMATION);
        confirmation.setTitle("Confirmation");
        confirmation.setHeaderText("Suppression de commande");
        confirmation.setContentText("Êtes-vous sûr de vouloir supprimer la commande #" + 
                                   commandeSelectionnee.getId() + " ?");
        
        Optional<ButtonType> result = confirmation.showAndWait();
        if (result.isPresent() && result.get() == ButtonType.OK) {
            commandesList.remove(commandeSelectionnee);
            viderDetailsCommande();
        }
    }

    @FXML
    private void retourAccueil() {
        if (stage != null && sceneAccueil != null) {
            stage.setScene(sceneAccueil);
            stage.setTitle("L'Ô Rhône - Accueil");
        }
    }
    
    private void showAlert(String titre, String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(titre);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}