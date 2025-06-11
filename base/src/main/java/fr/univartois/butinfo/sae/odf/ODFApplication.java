package fr.univartois.butinfo.sae.odf;

import java.io.IOException;

import fr.univartois.butinfo.sae.odf.controller.AccueilController;
import fr.univartois.butinfo.sae.odf.model.Adresse;
import fr.univartois.butinfo.sae.odf.model.Client;
import fr.univartois.butinfo.sae.odf.model.ClientEntreprise;
import fr.univartois.butinfo.sae.odf.model.ClientEtablissementPublic;
import fr.univartois.butinfo.sae.odf.model.ClientParticulier;
import fr.univartois.butinfo.sae.odf.model.Commune;
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class ODFApplication extends Application {

	
	
	@Override
	public void start(Stage stage) throws IOException {
		
		// Création de la liste des clients avec des données de test
		ObservableList<Client> clientList = creerListeClientsTest();
				
		FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/fr.univartois.butinfo.sae.odf.view/accueil-view.fxml"));
		Parent viewContent = fxmlLoader.load();

		Scene mainScene = new Scene(viewContent, 800, 200);
		
		stage.setScene(mainScene);
		stage.setTitle("O-de-France - Accueil");

		AccueilController accueilController = fxmlLoader.getController();
		
		accueilController.setPrimaryStage(stage);
		accueilController.setScene(mainScene);
		
		// Passer la liste des clients au contrôleur d'accueil
		accueilController.setClientList(clientList);
		
		
		stage.show();


	}
	
	/**
	 * Crée une liste de clients de test
	 */
	private ObservableList<Client> creerListeClientsTest() {
		ObservableList<Client> clients = FXCollections.observableArrayList();
		
		// Création d'une adresse de test
		Adresse adresse1 = new Adresse(5, "Rue des Lilas", new Commune("62", "Arras", "Pas-de-Calais"));
		
		// Client Particulier 1
		ClientParticulier client1 = new ClientParticulier();
		client1.setNom("Dupont");
		client1.setPrenom("Jean");
		client1.setEmail("jean.dupont@email.com");
		client1.setTelephone("0123456789");
		client1.setAdresse(adresse1);
		clients.add(client1);
		
		// Client Particulier 2
		ClientParticulier client2 = new ClientParticulier();
		client2.setNom("Martin");
		client2.setPrenom("Marie");
		client2.setEmail("marie.martin@email.com");
		client2.setTelephone("0987654321");
		client2.setAdresse(adresse1);
		clients.add(client2);
		
		// Client Entreprise
		ClientEntreprise client3 = new ClientEntreprise();
		client3.setEmail("contact@entreprise.com");
		client3.setTelephone("0147258369");
		client3.setAdresse(adresse1);
		clients.add(client3);
		
		// Client Établissement Public
		ClientEtablissementPublic client4 = new ClientEtablissementPublic();
		client4.setEmail("contact@mairie.fr");
		client4.setTelephone("0369258147");
		client4.setAdresse(adresse1);
		clients.add(client4);
		
		return clients;
	}


	public static void main(String[] args) {
		launch();
	}
}
