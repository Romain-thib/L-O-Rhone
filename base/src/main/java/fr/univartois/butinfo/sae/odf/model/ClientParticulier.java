package fr.univartois.butinfo.sae.odf.model;

import static fr.univartois.butinfo.sae.odf.model.TypeClient.PARTICULIER;

public class ClientParticulier extends Client {
    private String nom;
    private String prenom;

    private final TypeClient typeClient = PARTICULIER;

    @Override
    public void ajoutPointsFidelite(double achat) {
        int nbTranches = (int) (achat / 100);
        pointsFidelite += nbTranches * 10;
    }

    @Override
    public String getTypeClient() {
        return typeClient.toString();
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getPrenom() {
        return prenom;
    }

    public void setPrenom(String prenom) {
        this.prenom = prenom;
    }

    @Override
    public String toString() {
        return "ClientParticulier{" +
                "nom='" + nom + '\'' +
                ", prenom='" + prenom + '\'' +
                ", code=" + code +
                ", dateInscription=" + dateInscription +
                ", adresse=" + adresse +
                ", telephone='" + telephone + '\'' +
                ", email='" + email + '\'' +
                ", pointsFidelite=" + pointsFidelite +
                '}';
    }
}
