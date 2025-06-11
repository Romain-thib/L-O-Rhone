package fr.univartois.butinfo.sae.odf;

import java.io.IOException;

import fr.univartois.butinfo.sae.odf.controller.AccueilController;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class ODFApplication extends Application {

	@Override
	public void start(Stage stage) throws IOException {
		FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/fr.univartois.butinfo.sae.odf.view/accueil-view.fxml"));
		Parent viewContent = fxmlLoader.load();

		Scene mainScene = new Scene(viewContent, 800, 200);
		
		stage.setScene(mainScene);
		stage.setTitle("O-de-France - Accueil");

		AccueilController accueilController = fxmlLoader.getController();
		
		accueilController.setPrimaryStage(stage);

		
		
		
		stage.show();


	}


	public static void main(String[] args) {
		launch();
	}
}
