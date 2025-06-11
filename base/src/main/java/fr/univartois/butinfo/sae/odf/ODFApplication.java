package fr.univartois.butinfo.sae.odf;

import java.io.IOException;

import fr.univartois.butinfo.sae.odf.controller.AccueilController;
import fr.univartois.butinfo.sae.odf.model.Adresse;
import fr.univartois.butinfo.sae.odf.model.Categorie;
import fr.univartois.butinfo.sae.odf.model.Client;
import fr.univartois.butinfo.sae.odf.model.ClientEntreprise;
import fr.univartois.butinfo.sae.odf.model.ClientEtablissementPublic;
import fr.univartois.butinfo.sae.odf.model.ClientParticulier;
import fr.univartois.butinfo.sae.odf.model.Commande;
import fr.univartois.butinfo.sae.odf.model.Commune;
import fr.univartois.butinfo.sae.odf.model.Eau;
import fr.univartois.butinfo.sae.odf.model.Entrepot;
import fr.univartois.butinfo.sae.odf.model.StockEau;
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
		
		// Création de la liste des commandes avec des données de test
		ObservableList<Commande> commandesList = creerListeCommandesTest(clientList);
		
		// Création de la liste des stocks avec des données de test
		ObservableList<StockEau> stockEauList = creerListeStockEauTest();
				
		FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/fr.univartois.butinfo.sae.odf.view/accueil-view.fxml"));
		Parent viewContent = fxmlLoader.load();

		Scene mainScene = new Scene(viewContent);
		
		stage.setScene(mainScene);
		stage.setTitle("O-de-France - Accueil");

		AccueilController accueilController = fxmlLoader.getController();
		
		accueilController.setPrimaryStage(stage);
		accueilController.setScene(mainScene);
		
		// Passer la liste des clients au contrôleur d'accueil et la liste des commande puis des stock 
		accueilController.setClientList(clientList);
		accueilController.setCommandesList(commandesList);
		accueilController.setStockEauList(stockEauList);
		
		
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
		client3.setNomEntreprise("Orange");
		client3.setEmail("contact@entreprise.com");
		client3.setTelephone("0147258369");
		client3.setAdresse(adresse1);
		clients.add(client3);
		
		// Client Établissement Public
		ClientEtablissementPublic client4 = new ClientEtablissementPublic();
		client4.setNom("College Saint venant");
		client4.setEmail("contact@mairie.fr");
		client4.setTelephone("0369258147");
		client4.setAdresse(adresse1);
		clients.add(client4);
		
		return clients;
	}
	
	/**
	 * Crée une liste de commandes de test
	 */
	private ObservableList<Commande> creerListeCommandesTest(ObservableList<Client> clients) {
		ObservableList<Commande> commandes = FXCollections.observableArrayList();
		
		// Créer quelques eaux de test 
		Eau evian = new Eau("Evian","Plate", 1.50);
		Eau perrier = new Eau("Perrier", "Gazeuse", 1.80);
		Eau vittel = new Eau("Vittel", "Plate", 1.30);
		
		// Commande 1 - Client Particulier (Jean Dupont)
		if (clients.size() > 0) {
			Commande commande1 = new Commande(clients.get(0));
			commande1.addLigneCommande(evian, 12);
			commande1.addLigneCommande(perrier, 6);
			commandes.add(commande1);
		}
		
		// Commande 2 - Client Particulier (Marie Martin)
		if (clients.size() > 1) {
			Commande commande2 = new Commande(clients.get(1));
			commande2.addLigneCommande(vittel, 24);
			commande2.addLigneCommande(evian, 12);
			commandes.add(commande2);
		}
		
		// Commande 3 - Client Entreprise (Orange)
		if (clients.size() > 2) {
			Commande commande3 = new Commande(clients.get(2));
			commande3.addLigneCommande(evian, 120);
			commande3.addLigneCommande(perrier, 60);
			commande3.addLigneCommande(vittel, 90);
			commandes.add(commande3);
		}
		
		// Commande 4 - Client Établissement Public
		if (clients.size() > 3) {
			Commande commande4 = new Commande(clients.get(3));
			commande4.addLigneCommande(vittel, 60);
			commande4.addLigneCommande(evian, 48);
			commandes.add(commande4);
		}
		
		return commandes;
	}
	
	/**
	 * Crée une liste de stocks d'eau de test
	 */
	private ObservableList<StockEau> creerListeStockEauTest() {
		ObservableList<StockEau> stocks = FXCollections.observableArrayList();
		
		// Création des catégories de test 
		Categorie plateCategory = Categorie.EAU_PLATE; 
		Categorie gazeuseCategory = Categorie.EAU_GAZEUSE; 
		
		// Création d'une adresse de test
		Adresse adresse1 = new Adresse(5, "Rue des Lilas", new Commune("62", "Arras", "Pas-de-Calais"));
		
		// Création des entrepôts de test (adaptez selon votre classe Entrepot)
		Entrepot entrepotNord = new Entrepot(1,"Entrepôt Nord", adresse1);
		Entrepot entrepotSud = new Entrepot(2,"Entrepôt Sud", adresse1);
		Entrepot entrepotEst = new Entrepot(3,"Entrepôt Est", adresse1);
		Entrepot entrepotOuest = new Entrepot(4,"Entrepôt Ouest", adresse1);
		
		// Création des stocks
		stocks.add(new StockEau(plateCategory, entrepotNord, 150));
		stocks.add(new StockEau(gazeuseCategory, entrepotNord, 200));
		stocks.add(new StockEau(plateCategory, entrepotSud, 180));
		stocks.add(new StockEau(gazeuseCategory, entrepotSud, 120));
		stocks.add(new StockEau(plateCategory, entrepotEst, 90));
		stocks.add(new StockEau(gazeuseCategory, entrepotEst, 160));
		stocks.add(new StockEau(plateCategory, entrepotOuest, 220));
		stocks.add(new StockEau(gazeuseCategory, entrepotOuest, 85));
		
		return stocks;
	}


	public static void main(String[] args) {
		launch();
	}
}
