package fr.univartois.butinfo.sae.odf.controller;

import fr.univartois.butinfo.sae.odf.model.Client;
import fr.univartois.butinfo.sae.odf.model.Commande;
import fr.univartois.butinfo.sae.odf.model.LigneDeCommande;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

public class CommandesController {

    @FXML
    private ListView<Commande> commandesListView;
    @FXML
    private TextField idCommandeTextField;
    @FXML
    private TextField clientTextField;
    @FXML
    private ListView<LigneDeCommande> lignesCommandeListView;

    private ObservableList<Commande> commandesList;
    
    private Stage stage;
    
    private Scene sceneCommandesController;
    
	
	public void setStage(Stage stage) {
    	this.stage = stage;
    }
    
    public void setScene(Scene scene){
    	this.sceneCommandesController = scene;
    }
    
    public void setObservableList(ObservableList<Commande> commandesList) {
    	this.commandesList = commandesList;    	
    }

    
}
