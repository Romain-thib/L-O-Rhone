package fr.univartois.butinfo.sae.odf.controller;

import fr.univartois.butinfo.sae.odf.model.Adresse;
import fr.univartois.butinfo.sae.odf.model.Client;
import fr.univartois.butinfo.sae.odf.model.ClientEntreprise;
import fr.univartois.butinfo.sae.odf.model.ClientEtablissementPublic;
import fr.univartois.butinfo.sae.odf.model.ClientParticulier;
import fr.univartois.butinfo.sae.odf.model.Commune;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ListView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class AjoutModificationClientController {

    @FXML
    private ComboBox<String> typeClientComboBox;
    @FXML
    private TextField nomTextField;
    @FXML
    private TextField prenomTextField;
    @FXML
    private TextField emailTextField;
    @FXML
    private TextField telephoneTextField;
    @FXML
    private TextField numeroAdresseTextField;
    @FXML
    private TextField rueAdresseTextField;
    @FXML
    private TextField codePostalTextField;
    @FXML
    private TextField villeTextField;
    @FXML
    private TextField departementTextField;

    private Stage stage;
    private Scene scenePrecedente;
    private ObservableList<Client> clientList;
    private ListView<Client> listView;
    private Client clientAModifier; // null si ajout, objet existant si modification

    public void setStage(Stage stage) {
        this.stage = stage;
    }

    public void setScenePrecedente(Scene scene) {
        this.scenePrecedente = scene;
        System.out.println("Scène précédente définie: " + (scene != null));
    }

    public void setClientList(ObservableList<Client> clientList) {
        this.clientList = clientList;
    }

    public void setListView(ListView<Client> listView) {
        this.listView = listView;
    }

    public void setClientAModifier(Client client) {
        this.clientAModifier = client;
        System.out.println("Client à modifier défini: " + (client != null ? client.getEmail() : "null"));
        if (client != null) {
            preremplirChamps(client);
        }
    }

    @FXML
    public void initialize() {
        System.out.println("Initialisation AjoutModificationClientController");
        
        // Vérifier que la ComboBox est bien initialisée
        if (typeClientComboBox != null) {
            // Remplir la ComboBox avec les types de clients
            typeClientComboBox.setItems(FXCollections.observableArrayList(
                "Particulier", "Entreprise", "Établissement Public"
            ));

            // Listener pour adapter les champs selon le type de client
            typeClientComboBox.getSelectionModel().selectedItemProperty().addListener(
                (observable, oldValue, newValue) -> {
                    if (newValue != null) {
                        adapterChampsSelonType(newValue);
                    }
                }
            );

            // Sélectionner "Particulier" par défaut
            typeClientComboBox.getSelectionModel().select("Particulier");
        }
    }

    /**
     * Adapte la visibilité des champs selon le type de client
     */
    private void adapterChampsSelonType(String typeClient) {
    	boolean isParticulier = "Particulier".equals(typeClient);
    	boolean isEntreprise = "Entreprise".equals(typeClient);
    	boolean isEtablissement = "Établissement Public".equals(typeClient);

    	// Le champ nom est visible pour tous sauf particulier → il s'appelle "nom entreprise" ou "nom établissement"
    	if (nomTextField != null) {
    	    nomTextField.setVisible(isParticulier || isEntreprise || isEtablissement);
    	    nomTextField.setManaged(isParticulier || isEntreprise || isEtablissement);

    	    // Adapter le *prompt text* (facultatif, pour l’interface utilisateur)
    	    if (isEntreprise) {
    	        nomTextField.setPromptText("Nom de l'entreprise");
    	    } else if (isEtablissement) {
    	        nomTextField.setPromptText("Nom de l'établissement");
    	    } else {
    	        nomTextField.setPromptText("Nom");
    	    }
    	}

        if (prenomTextField != null) {
            prenomTextField.setVisible(isParticulier);
            prenomTextField.setManaged(isParticulier);
        }
    }

    /**
     * Prérempli les champs avec les données du client à modifier
     */
    private void preremplirChamps(Client client) {
        System.out.println("Préremplissage des champs pour: " + client.getEmail());
        
        if (emailTextField != null) emailTextField.setText(client.getEmail() != null ? client.getEmail() : "");
        if (telephoneTextField != null) telephoneTextField.setText(client.getTelephone() != null ? client.getTelephone() : "");

        // Définir le type de client
        if (client instanceof ClientParticulier) {
            if (typeClientComboBox != null) typeClientComboBox.getSelectionModel().select("Particulier");
            ClientParticulier cp = (ClientParticulier) client;
            if (nomTextField != null) nomTextField.setText(cp.getNom() != null ? cp.getNom() : "");
            if (prenomTextField != null) prenomTextField.setText(cp.getPrenom() != null ? cp.getPrenom() : "");
        } else if (client instanceof ClientEntreprise) {
            typeClientComboBox.getSelectionModel().select("Entreprise");
            nomTextField.setText(((ClientEntreprise) client).getNomEntreprise());
        } else if (client instanceof ClientEtablissementPublic) {
            typeClientComboBox.getSelectionModel().select("Établissement Public");
            nomTextField.setText(((ClientEtablissementPublic) client).getNom());
        }


        // Préremplir l'adresse si elle existe
        if (client.getAdresse() != null) {
            Adresse adresse = client.getAdresse();
            if (numeroAdresseTextField != null) numeroAdresseTextField.setText(String.valueOf(adresse.getNumero()));
            if (rueAdresseTextField != null) rueAdresseTextField.setText(adresse.getVoie() != null ? adresse.getVoie() : "");
            
            if (adresse.getCommune() != null) {
                Commune commune = adresse.getCommune();
                if (codePostalTextField != null) codePostalTextField.setText(commune.getCode() != null ? commune.getCode() : "");
                if (villeTextField != null) villeTextField.setText(commune.getNom() != null ? commune.getNom() : "");
                if (departementTextField != null) departementTextField.setText(commune.getDepartement() != null ? commune.getDepartement() : "");
            }
        }
    }

    @FXML
    private void onValider() {
        System.out.println("Bouton Valider cliqué");
        try {
            // Validation des champs obligatoires
            if (!validerChamps()) {
                return;
            }

            Client client;
            
            if (clientAModifier != null) {
                System.out.println("Mode modification");
                // Modification d'un client existant
                client = clientAModifier;
                modifierClient(client);
                
                // Forcer le rafraîchissement de la ListView
                if (listView != null) {
                    listView.refresh();
                }
            } else {
                System.out.println("Mode ajout");
                // Création d'un nouveau client
                client = creerNouveauClient();
                if (clientList != null) {
                    clientList.add(client);
                    System.out.println("Client ajouté à la liste");
                }
            }

            // Retourner à la vue précédente
            retournerVuePrecedente();

        } catch (Exception e) {
            System.err.println("Erreur lors de la validation: " + e.getMessage());
            e.printStackTrace();
            afficherErreur("Erreur lors de la validation", e.getMessage());
        }
    }

    @FXML
    private void onAnnuler() {
        System.out.println("Bouton Annuler cliqué");
        // Retourner à la vue précédente sans sauvegarder
        retournerVuePrecedente();
    }

    /**
     * Retourne à la vue précédente
     */
    private void retournerVuePrecedente() {
        if (stage != null && scenePrecedente != null) {
            System.out.println("Retour à la vue précédente");
            stage.setScene(scenePrecedente);
        } else {
            System.err.println("Impossible de retourner: stage=" + stage + ", scenePrecedente=" + scenePrecedente);
        }
    }

    /**
     * Valide les champs obligatoires
     */
    private boolean validerChamps() {
        String typeClient = typeClientComboBox != null ? typeClientComboBox.getSelectionModel().getSelectedItem() : null;
        
        if (typeClient == null) {
            afficherErreur("Erreur de validation", "Veuillez sélectionner un type de client.");
            return false;
        }

        if (emailTextField == null || emailTextField.getText().trim().isEmpty()) {
            afficherErreur("Erreur de validation", "L'email est obligatoire.");
            return false;
        }

        if (telephoneTextField == null || telephoneTextField.getText().trim().isEmpty()) {
            afficherErreur("Erreur de validation", "Le téléphone est obligatoire.");
            return false;
        }

        if ("Particulier".equals(typeClient)) {
            if (nomTextField == null || nomTextField.getText().trim().isEmpty()) {
                afficherErreur("Erreur de validation", "Le nom est obligatoire pour un particulier.");
                return false;
            }
            if (prenomTextField == null || prenomTextField.getText().trim().isEmpty()) {
                afficherErreur("Erreur de validation", "Le prénom est obligatoire pour un particulier.");
                return false;
            }
        }

        return true;
    }

    /**
     * Crée un nouveau client selon le type sélectionné
     */
    private Client creerNouveauClient() {
        String typeClient = typeClientComboBox.getSelectionModel().getSelectedItem();
        Client client = null;

        switch (typeClient) {
            case "Particulier":
                client = new ClientParticulier();
                ((ClientParticulier) client).setNom(nomTextField.getText().trim());
                ((ClientParticulier) client).setPrenom(prenomTextField.getText().trim());
                break;
            case "Entreprise":
                ClientEntreprise ce = new ClientEntreprise();
                ce.setNomEntreprise(nomTextField.getText().trim());
                client = ce;
                break;

            case "Établissement Public":
                ClientEtablissementPublic ep = new ClientEtablissementPublic();
                ep.setNom(nomTextField.getText().trim());
                client = ep;
                break;

        }

        if (client != null) {
            remplirDonneesCommunes(client);
        }

        return client;
    }

    /**
     * Modifie un client existant
     */
    private void modifierClient(Client client) {
        // Modifier les données communes
        remplirDonneesCommunes(client);

        // Modifier les données spécifiques selon le type
        if (client instanceof ClientParticulier) {
            ClientParticulier cp = (ClientParticulier) client;
            cp.setNom(nomTextField.getText().trim());
            cp.setPrenom(prenomTextField.getText().trim());
        }
        else if (client instanceof ClientEntreprise) {
            ((ClientEntreprise) client).setNomEntreprise(nomTextField.getText().trim());
        } else if (client instanceof ClientEtablissementPublic) {
            ((ClientEtablissementPublic) client).setNom(nomTextField.getText().trim());
        }

    }

    /**
     * Remplit les données communes à tous les types de clients
     */
    private void remplirDonneesCommunes(Client client) {
        client.setEmail(emailTextField.getText().trim());
        client.setTelephone(telephoneTextField.getText().trim());

        // Créer l'adresse si les champs sont remplis
        if (numeroAdresseTextField != null && rueAdresseTextField != null &&
            !numeroAdresseTextField.getText().trim().isEmpty() && 
            !rueAdresseTextField.getText().trim().isEmpty()) {
            
            try {
                int numero = Integer.parseInt(numeroAdresseTextField.getText().trim());
                String rue = rueAdresseTextField.getText().trim();
                
                String codePostal = codePostalTextField != null ? codePostalTextField.getText().trim() : "";
                String ville = villeTextField != null ? villeTextField.getText().trim() : "";
                String departement = departementTextField != null ? departementTextField.getText().trim() : "";
                
                Commune commune = new Commune(codePostal, ville, departement);
                Adresse adresse = new Adresse(numero, rue, commune);
                client.setAdresse(adresse);
            } catch (NumberFormatException e) {
                throw new RuntimeException("Le numéro d'adresse doit être un nombre valide.");
            }
        }
    }

    /**
     * Affiche une boîte de dialogue d'erreur
     */
    private void afficherErreur(String titre, String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(titre);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}