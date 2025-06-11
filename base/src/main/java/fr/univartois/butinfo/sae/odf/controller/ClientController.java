package fr.univartois.butinfo.sae.odf.controller;

import java.io.IOException;

import fr.univartois.butinfo.sae.odf.model.Client;
import fr.univartois.butinfo.sae.odf.model.ClientParticulier;
import fr.univartois.butinfo.sae.odf.model.ClientEntreprise;
import fr.univartois.butinfo.sae.odf.model.ClientEtablissementPublic;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.control.TextArea;
import javafx.stage.Stage;

public class ClientController {
	@FXML
	private ListView<Client> clientListView;
	@FXML
	private TextField idTextField;
	@FXML
	private TextField nomTextField;
	@FXML
	private TextField emailTextField;
	@FXML
	private TextField telephoneTextField;
	@FXML
	private TextField typeClientTextField;
	@FXML
	private TextArea adresseTextArea;
	@FXML
	private Button retourButton;
	@FXML
	private Button ajouterButton;
	@FXML
	private Button modifierButton;
	@FXML
	private Button supprimerButton;

	private ObservableList<Client> clientList;
	private Stage stage;
	private Scene sceneAccueil;

	public void setStage(Stage stage) {
		this.stage = stage;
	}

	public void setScene(Scene scene){
		this.sceneAccueil = scene;
	}

	public void setObservableList(ObservableList<Client> clientList) {
		this.clientList = clientList;
		// Associer la liste à la ListView
		if (clientListView != null) {
			clientListView.setItems(this.clientList);
		}
	}

	@FXML
	public void initialize() {
		// S'assurer que tous les composants sont initialisés
		if (modifierButton != null) {
			modifierButton.setDisable(true);
		}
		if (supprimerButton != null) {
			supprimerButton.setDisable(true);
		}

		// Configuration de l'affichage des cellules dans la ListView
		if (clientListView != null) {
			clientListView.setCellFactory(list -> {
				return new ListCell<>() {
					@Override
					protected void updateItem(Client client, boolean empty) {
						super.updateItem(client, empty);
						if (empty || client == null) {
							setText(null);
						} else {
							// Affichage selon le type de client
							if (client instanceof ClientParticulier) {
								ClientParticulier cp = (ClientParticulier) client;
								setText(cp.getNom() + " " + cp.getPrenom());
							} else if (client instanceof ClientEntreprise) {
								ClientEntreprise ce = (ClientEntreprise) client;
								setText("Entreprise :" + ce.getNomEntreprise());
							} else if (client instanceof ClientEtablissementPublic) {
								ClientEtablissementPublic cep = (ClientEtablissementPublic) client;
								setText("Établissement Public : " + cep.getNom());
							} else {
								setText("Client - Code: " + client.getCode());
							}
						}
					}
				};
			});

			// Listener pour la sélection d'un élément dans la ListView
			clientListView.getSelectionModel().selectedItemProperty().addListener(
				(ObservableValue<? extends Client> observable, Client oldValue, Client newValue) -> {
					System.out.println("Sélection changée: " + (newValue != null ? newValue.getEmail() : "null"));
					if (newValue != null) {
						afficherDetailsClient(newValue);
						// Activer les boutons modifier et supprimer
						if (modifierButton != null) modifierButton.setDisable(false);
						if (supprimerButton != null) supprimerButton.setDisable(false);
					} else {
						// Réinitialiser les champs si aucun client n'est sélectionné
						viderChamps();
						// Désactiver les boutons modifier et supprimer
						if (modifierButton != null) modifierButton.setDisable(true);
						if (supprimerButton != null) supprimerButton.setDisable(true);
					}
				}
			);
		}
	}

	/**
	 * Vide tous les champs de détails
	 */
	private void viderChamps() {
		if (idTextField != null) idTextField.setText("");
		if (nomTextField != null) nomTextField.setText("");
		if (emailTextField != null) emailTextField.setText("");
		if (telephoneTextField != null) telephoneTextField.setText("");
		if (typeClientTextField != null) typeClientTextField.setText("");
		if (adresseTextArea != null) adresseTextArea.setText("");
	}

	/**
	 * Affiche les détails du client sélectionné dans les champs de droite
	 */
	private void afficherDetailsClient(Client client) {
		System.out.println("Affichage des détails pour: " + client.getEmail());
		
		if (idTextField != null) idTextField.setText(String.valueOf(client.getCode()));
		if (emailTextField != null) emailTextField.setText(client.getEmail() != null ? client.getEmail() : "");
		if (telephoneTextField != null) telephoneTextField.setText(client.getTelephone() != null ? client.getTelephone() : "");
		if (typeClientTextField != null) typeClientTextField.setText(client.getTypeClient());
		
		// Affichage de l'adresse si elle existe
		if (adresseTextArea != null) {
			if (client.getAdresse() != null) {
				adresseTextArea.setText(client.getAdresse().toString());
			} else {
				adresseTextArea.setText("");
			}
		}
		
		// Affichage du nom selon le type de client
		if (nomTextField != null) {
			if (client instanceof ClientParticulier) {
				ClientParticulier cp = (ClientParticulier) client;
				nomTextField.setText(cp.getNom() + " " + cp.getPrenom());
			}
			else if (client instanceof ClientEntreprise) {
				ClientEntreprise ce = (ClientEntreprise) client;
				nomTextField.setText(ce.getNomEntreprise());
			}
			else if (client instanceof ClientEtablissementPublic) {
				ClientEtablissementPublic cep = (ClientEtablissementPublic) client;
				nomTextField.setText(cep.getNom());
			}
			else {
				nomTextField.setText("Code : " + client.getCode());
			}
		}
	}

	@FXML
	private void onAjouter() throws IOException {
		System.out.println("Bouton Ajouter cliqué");
		ouvrirVueAjoutModification(null); // null = ajout
	}

	@FXML
	private void onModifier() throws IOException {
		System.out.println("Bouton Modifier cliqué");
		Client clientSelectionne = clientListView.getSelectionModel().getSelectedItem();
		if (clientSelectionne != null) {
			System.out.println("Client sélectionné pour modification: " + clientSelectionne.getEmail());
			ouvrirVueAjoutModification(clientSelectionne); // client existant = modification
		} else {
			System.out.println("Aucun client sélectionné");
		}
	}

	@FXML
	private void onSupprimer() {
		Client clientSelectionne = clientListView.getSelectionModel().getSelectedItem();
		if (clientSelectionne != null) {
			// Demander confirmation
			Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
			alert.setTitle("Confirmation de suppression");
			alert.setHeaderText("Supprimer le client");
			alert.setContentText("Êtes-vous sûr de vouloir supprimer ce client ?");

			alert.showAndWait().ifPresent(response -> {
				if (response == ButtonType.OK) {
					// Supprimer le client de la liste
					clientList.remove(clientSelectionne);
					// Vider les champs de détails
					viderChamps();
					// Désactiver les boutons
					if (modifierButton != null) modifierButton.setDisable(true);
					if (supprimerButton != null) supprimerButton.setDisable(true);
				}
			});
		}
	}

	/**
	 * Ouvre la vue d'ajout/modification
	 */
	private void ouvrirVueAjoutModification(Client clientAModifier) throws IOException {
		try {
			System.out.println("Ouverture de la vue ajout/modification");
			FXMLLoader fxmlLoader = new FXMLLoader(
				getClass().getResource("/fr.univartois.butinfo.sae.odf.view/ajout-modification-client.fxml"));
			Parent viewContent = fxmlLoader.load();

			Scene ajoutModifScene = new Scene(viewContent);
			
			// Configurer le contrôleur AVANT de changer la scène
			AjoutModificationClientController controller = fxmlLoader.getController();
			controller.setStage(stage);
			controller.setScenePrecedente(stage.getScene()); // Passer la scène ACTUELLE
			controller.setClientList(clientList);
			controller.setListView(clientListView);

			// Si c'est une modification, passer le client à modifier
			if (clientAModifier != null) {
				System.out.println("Mode modification pour: " + clientAModifier.getEmail());
				controller.setClientAModifier(clientAModifier);
			} else {
				System.out.println("Mode ajout");
			}

			// Changer la scène APRÈS avoir configuré le contrôleur
			stage.setScene(ajoutModifScene);
			
		} catch (Exception e) {
			System.err.println("Erreur lors de l'ouverture de la vue: " + e.getMessage());
			e.printStackTrace();
		}
	}

	@FXML
	private void onRetour() {
		// Retour à la vue d'accueil
		if (stage != null && sceneAccueil != null) {
			stage.setScene(sceneAccueil);
		}
	}
}
