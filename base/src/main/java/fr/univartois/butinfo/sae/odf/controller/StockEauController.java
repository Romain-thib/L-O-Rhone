package fr.univartois.butinfo.sae.odf.controller;

import fr.univartois.butinfo.sae.odf.model.Commande;
import fr.univartois.butinfo.sae.odf.model.StockEau;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

public class StockEauController {

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
	
	public void setStage(Stage stage) {
    	this.stage = stage;
    }
    
    public void setScene(Scene scene){
    	this.sceneStockEauController = scene;
    }
    
    public void setObservableList(ObservableList<StockEau> stockEauList) {
    	this.stockEauList = stockEauList;    	
    }
    
}
