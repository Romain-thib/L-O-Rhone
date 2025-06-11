package fr.univartois.butinfo.sae.odf.model;


import javafx.collections.ObservableList;
import javafx.collections.FXCollections;


public class Commande implements RemisesPossibles {
    private final int id;

    private static int nextId = 0;
    private final ObservableList<LigneDeCommande> lignesDeCommande;
    private final Client client;

    public Commande(Client client) {
        this.id = nextId++;
        this.client = client;
        lignesDeCommande = FXCollections.observableArrayList();
    }

    @Override
    public double montantDeLaRemise() {
        int reduce = 0;
        if ("Particulier".equals(client.getTypeClient())) {
            reduce = Math.min(client.getPointsFidelite() / 100, 10);
        }
        return montantTotal() * reduce;
    }


    private double montantTotal() {
        //double montant = 0;
        return lignesDeCommande.stream()
                .mapToDouble(ligne -> ligne.getEau().getPrix() * ligne.getQuantite())
                .sum();
    }


    public double montant() {
        return montantTotal() - montantDeLaRemise();
    }

    @Override
    public int nombreBouteillesGratuites() {
        if ("Particulier".equals(client.getTypeClient()))
            return nombreBouteillesGratuites(12);
        if ("Établissement public".equals(client.getTypeClient()))
            return nombreBouteillesGratuites(60);
        if ("Entreprise".equals(client.getTypeClient()))
            return nombreBouteillesGratuites(120);
        return 0;
    }

    private int nombreBouteillesGratuites(int bloc) {
        return lignesDeCommande.stream().reduce(0, (nb, ligne) -> nb + (ligne.getQuantite() / bloc), Integer::sum);
    }

    public void addLigneCommande(Eau eau, int quantite) {
        lignesDeCommande.add(new LigneDeCommande(eau, quantite));
    }

    public void updateLigneCommande(int index, int quantite) {
    	LigneDeCommande nouvelleLigne = new LigneDeCommande(lignesDeCommande.get(index).getEau(), quantite);
        lignesDeCommande.set(index, nouvelleLigne);
    }

    public Client getClient() {
        return client;
    }

    public int getId() {
        return id;
    }
    
    public ObservableList<LigneDeCommande> getlignesDeCommande(){
		return lignesDeCommande;
	}
}
