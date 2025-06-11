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
import javafx.scene.control.Button;
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
		// Configuration de l'affichage des cellules dans la ListView
		clientListView.setCellFactory(list -> {
			return new ListCell<>() {
				@Override
				public void updateItem(Client client, boolean empty) {
					super.updateItem(client, empty);
					if (empty || (client == null)) {
						setText(null);
					} else {
						// Affichage selon le type de client
						if (client instanceof ClientParticulier) {
							ClientParticulier cp = (ClientParticulier) client;
							setText(cp.getNom() + " " + cp.getPrenom());
						} else if (client instanceof ClientEntreprise) {
							ClientEntreprise ce = (ClientEntreprise) client;
							setText("Entreprise - Code: " + ce.getCode());
						} else if (client instanceof ClientEtablissementPublic) {
							ClientEtablissementPublic cep = (ClientEtablissementPublic) client;
							setText("Établissement Public - Code: " + cep.getCode());
						} else {
							setText("Client - Code: " + client.getCode());
						}
					}
				}
			};
		});

		// Listener pour la sélection d'un élément dans la ListView
		clientListView.getSelectionModel().selectedItemProperty().addListener(
			new ChangeListener<Client>() {
				@Override
				public void changed(ObservableValue<? extends Client> observable, 
								  Client oldValue, Client newValue) {
					if (newValue != null) {
						afficherDetailsClient(newValue);
					}
				}
			}
		);
	}

	/**
	 * Affiche les détails du client sélectionné dans les champs de droite
	 */
	private void afficherDetailsClient(Client client) {
		idTextField.setText(String.valueOf(client.getCode()));
		emailTextField.setText(client.getEmail());
		telephoneTextField.setText(client.getTelephone());
		typeClientTextField.setText(client.getTypeClient());
		
		// Affichage de l'adresse si elle existe
		if (client.getAdresse() != null) {
			adresseTextArea.setText(client.getAdresse().toString());
		} else {
			adresseTextArea.setText("");
		}
		
		// Affichage du nom selon le type de client
		if (client instanceof ClientParticulier) {
			ClientParticulier cp = (ClientParticulier) client;
			nomTextField.setText(cp.getNom() + " " + cp.getPrenom());
		} else {
			nomTextField.setText("Code: " + client.getCode());
		}
	}

	@FXML
	private void onRetour() throws IOException {
		// Retour à la vue d'accueil
		if (stage != null && sceneAccueil != null) {
			stage.setScene(sceneAccueil);
		}
	}
}
