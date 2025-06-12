package fr.univartois.butinfo.sae.odf.controller;

import java.io.IOException;

import fr.univartois.butinfo.sae.odf.model.Client;
import fr.univartois.butinfo.sae.odf.model.Commande;
import fr.univartois.butinfo.sae.odf.model.Entrepot;
import fr.univartois.butinfo.sae.odf.model.StockEau;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class AccueilController {

	private Stage primaryStage;
	private Scene sceneAccueilController;
	private ObservableList<Client> clientList;
	private ObservableList<Commande> commandesList;
	private ObservableList<StockEau> stockEauList;
	private ObservableList<Entrepot> entrepotsList;
    
	public void setPrimaryStage(Stage stage) {
		this.primaryStage = stage;
	}
	
	public void setScene(Scene scene) {
        this.sceneAccueilController = scene;
    }
    
    public void setClientList(ObservableList<Client> clientList) {
        this.clientList = clientList;
    }
    
    public void setCommandesList(ObservableList<Commande> commandesList) {
        this.commandesList = commandesList;
    }
    
    public void setStockEauList(ObservableList<StockEau> stockEauList) {
        this.stockEauList = stockEauList;
    }
    
    public void setEntrepotsList(ObservableList<Entrepot> entrepotsList) {
        this.entrepotsList = entrepotsList;
    }

	@FXML
	private void onClients() throws IOException {
		// On récupère la description de la nouvelle vue.
	    FXMLLoader fxmlLoader = new FXMLLoader(
	        getClass().getResource("/fr.univartois.butinfo.sae.odf.view/affichage-client.fxml"));
	    Parent viewContent = fxmlLoader.load();

	    // Ensuite, on la place dans une nouvelle Scene.
	    Scene clientScene = new Scene(viewContent);
	    primaryStage.setScene(clientScene);

	    // On lie le modèle au nouveau contrôleur.
	    ClientController controller = fxmlLoader.getController();
	    controller.setStage(this.primaryStage);
	    controller.setScene(this.sceneAccueilController);
	    
	    // On passe la liste des clients au contrôleur
	    if (clientList != null) {
	        controller.setObservableList(clientList);
	    }
    }

	@FXML
	private void onStockEau() throws IOException {
		// On récupère la description de la nouvelle vue.
	    FXMLLoader fxmlLoader = new FXMLLoader(
	        getClass().getResource("/fr.univartois.butinfo.sae.odf.view/affichage-stock-eau.fxml"));
	    Parent viewContent = fxmlLoader.load();

	    // Ensuite, on la place dans une nouvelle Scene.
	    Scene stockEauScene = new Scene(viewContent);
	    primaryStage.setScene(stockEauScene);

	    // On lie le modèle au nouveau contrôleur.
	    StockEauController controller = fxmlLoader.getController();
	    controller.setStage(this.primaryStage);
	    controller.setScene(this.sceneAccueilController);
	    
	    if (stockEauList != null) {
	        controller.setObservableList(stockEauList);
	    }
	    
	    // Passer aussi la liste des entrepôts pour les formulaires d'ajout/modification
	    if (entrepotsList != null) {
	        controller.setEntrepotsList(entrepotsList);
	    }
    }

	@FXML
	private void onCommandes() throws IOException {
		// On récupère la description de la nouvelle vue.
	    FXMLLoader fxmlLoader = new FXMLLoader(
	        getClass().getResource("/fr.univartois.butinfo.sae.odf.view/affichage-commandes.fxml"));
	    Parent viewContent = fxmlLoader.load();

	    // Ensuite, on la place dans une nouvelle Scene.
	    Scene commandesScene = new Scene(viewContent);
	    primaryStage.setScene(commandesScene);

	    // On lie le modèle au nouveau contrôleur.
	    CommandesController controller = fxmlLoader.getController();
	    controller.setStage(this.primaryStage);
	    controller.setScene(this.sceneAccueilController);
	    
	    // Passer les listes nécessaires au contrôleur
	    if (commandesList != null) {
	        controller.setObservableList(commandesList);
	    }
	    if (clientList != null) {
	        controller.setClientsList(clientList);
	    }
    }
}