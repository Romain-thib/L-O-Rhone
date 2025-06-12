package fr.univartois.butinfo.sae.odf.controller;

import fr.univartois.butinfo.sae.odf.model.Client;
import fr.univartois.butinfo.sae.odf.model.Commande;
import fr.univartois.butinfo.sae.odf.model.Eau;
import fr.univartois.butinfo.sae.odf.model.LigneDeCommande;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;

public class AjoutModificationCommandesController {

    @FXML
    private ComboBox<Client> clientComboBox;
    
    @FXML
    private ComboBox<Eau> eauComboBox;
    
    @FXML
    private TextField quantiteTextField;
    
    @FXML
    private ListView<LigneDeCommande> lignesListView;
    
    @FXML
    private Button ajouterLigneButton;
    
    @FXML
    private Button supprimerLigneButton;
    
    @FXML
    private Button validerButton;
    
    @FXML
    private Button annulerButton;

    private Stage stage;
    private Scene sceneRetour;
    private ObservableList<Commande> commandesList;
    private ObservableList<Client> clientsList;
    private ListView<Commande> commandesListView;
    private Commande commandeEnCours;
    private ObservableList<LigneDeCommande> lignesTemporaires;

    @FXML
    void initialize() {
        // Initialisation de la liste temporaire des lignes de commande
        lignesTemporaires = FXCollections.observableArrayList();
        lignesListView.setItems(lignesTemporaires);
        
        // Configuration de l'affichage des lignes de commande
        lignesListView.setCellFactory(list -> {
            return new ListCell<LigneDeCommande>() {
                @Override
                protected void updateItem(LigneDeCommande ligne, boolean empty) {
                    super.updateItem(ligne, empty);
                    if (empty || ligne == null) {
                        setText(null);
                    } else {
                        setText(ligne.getEau().toString() + " - Quantité: " + ligne.getQuantite());
                    }
                }
            };
        });
        
        // Initialisation des eaux disponibles (vous pouvez adapter selon vos données)
        ObservableList<Eau> eauxDisponibles = FXCollections.observableArrayList();
        eauxDisponibles.add(new Eau("Evian", "Plate", 1.50));
        eauxDisponibles.add(new Eau("Perrier", "Gazeuse", 1.80));
        eauxDisponibles.add(new Eau("Vittel", "Plate", 1.30));
        eauxDisponibles.add(new Eau("Badoit", "Gazeuse", 1.90));
        eauComboBox.setItems(eauxDisponibles);

        
        // Listener pour activer/désactiver le bouton de suppression de ligne
        lignesListView.getSelectionModel().selectedItemProperty().addListener(
            (observable, oldValue, newValue) -> {
                supprimerLigneButton.setDisable(newValue == null);
            }
        );
        
        // Désactiver le bouton de suppression au départ
        supprimerLigneButton.setDisable(true);
    }

    public void setStage(Stage stage) {
        this.stage = stage;
    }

    public void setSceneRetour(Scene scene) {
        this.sceneRetour = scene;
    }

    public void setCommandesList(ObservableList<Commande> commandesList) {
        this.commandesList = commandesList;
    }

    public void setClientsList(ObservableList<Client> clientsList) {
        this.clientsList = clientsList;
        if (clientComboBox != null && clientsList != null) {
            clientComboBox.setItems(clientsList);
        }
    }

    public void setCommandesListView(ListView<Commande> commandesListView) {
        this.commandesListView = commandesListView;
    }

    /**
     * Méthode pour préremplir les champs lors de l'édition d'une commande
     */
    public void setCommandeAEditer(Commande commande) {
        this.commandeEnCours = commande;
        if (commande != null) {
            // Sélectionner le client
            clientComboBox.getSelectionModel().select(commande.getClient());
            clientComboBox.setDisable(true); // Empêcher la modification du client
            
            // Copier les lignes de commande existantes
            lignesTemporaires.clear();
            for (LigneDeCommande ligne : commande.getLignesCommande()) {
                lignesTemporaires.add(new LigneDeCommande(ligne.getEau(), ligne.getQuantite()));
            }
        }
    }

    @FXML
    private void onAjouterLigne() {
        Eau eauSelectionnee = eauComboBox.getSelectionModel().getSelectedItem();
        String quantiteText = quantiteTextField.getText().trim();
        
        if (eauSelectionnee == null) {
            showAlert("Erreur", "Veuillez sélectionner une eau.");
            return;
        }
        
        if (quantiteText.isEmpty()) {
            showAlert("Erreur", "Veuillez saisir une quantité.");
            return;
        }
        
        try {
            int quantite = Integer.parseInt(quantiteText);
            if (quantite <= 0) {
                showAlert("Erreur", "La quantité doit être positive.");
                return;
            }
            
            // Vérifier si cette eau existe déjà dans la commande
            boolean eauExiste = false;
            for (int i = 0; i < lignesTemporaires.size(); i++) {
                LigneDeCommande ligne = lignesTemporaires.get(i);
                if (ligne.getEau().equals(eauSelectionnee)) {
                    // Remplacer la ligne existante
                    lignesTemporaires.set(i, new LigneDeCommande(eauSelectionnee, quantite));
                    eauExiste = true;
                    break;
                }
            }
            
            if (!eauExiste) {
                // Ajouter une nouvelle ligne
                lignesTemporaires.add(new LigneDeCommande(eauSelectionnee, quantite));
            }
            
            // Réinitialiser les champs
            eauComboBox.getSelectionModel().clearSelection();
            quantiteTextField.clear();
            
        } catch (NumberFormatException e) {
            showAlert("Erreur", "La quantité doit être un nombre entier.");
        }
    }

    @FXML
    private void onSupprimerLigne() {
        LigneDeCommande ligneSelectionnee = lignesListView.getSelectionModel().getSelectedItem();
        if (ligneSelectionnee != null) {
            lignesTemporaires.remove(ligneSelectionnee);
        }
    }

    @FXML
    private void onValider() {
        Client clientSelectionne = clientComboBox.getSelectionModel().getSelectedItem();
        
        if (clientSelectionne == null) {
            showAlert("Erreur", "Veuillez sélectionner un client.");
            return;
        }
        
        if (lignesTemporaires.isEmpty()) {
            showAlert("Erreur", "Veuillez ajouter au moins une ligne de commande.");
            return;
        }
        
        if (commandeEnCours == null) {
            // Création d'une nouvelle commande
            Commande nouvelleCommande = new Commande(clientSelectionne);
            for (LigneDeCommande ligne : lignesTemporaires) {
                nouvelleCommande.addLigneCommande(ligne.getEau(), ligne.getQuantite());
            }
            commandesList.add(nouvelleCommande);
        } else {
            // Modification d'une commande existante
            // Vider les anciennes lignes
            commandeEnCours.getLignesCommande().clear();
            
            // Ajouter les nouvelles lignes
            for (LigneDeCommande ligne : lignesTemporaires) {
                commandeEnCours.addLigneCommande(ligne.getEau(), ligne.getQuantite());
            }
            
            // Forcer le rafraîchissement de la ListView
            if (commandesListView != null) {
                commandesListView.refresh();
            }
        }
        
        // Retourner à la vue précédente
        retourVuePrecedente();
    }

    @FXML
    private void onAnnuler() {
        retourVuePrecedente();
    }

    private void retourVuePrecedente() {
        if (stage != null && sceneRetour != null) {
            stage.setScene(sceneRetour);
            stage.setTitle("O-de-France - Commandes");
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