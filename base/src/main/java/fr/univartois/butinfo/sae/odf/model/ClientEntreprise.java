package fr.univartois.butinfo.sae.odf.model;

import static fr.univartois.butinfo.sae.odf.model.TypeClient.ENTREPRISE;

public class ClientEntreprise extends Client{

    private String nom;
    private String prenom;

    private final TypeClient typeClient = ENTREPRISE;

    @Override
    public void ajoutPointsFidelite(double achat) {
        int nbTranches = (int) (achat / 1000);
        pointsFidelite += nbTranches * 10;
    }

    @Override
    public String getTypeClient() {
        return typeClient.toString();
    }

    @Override
    public String toString() {
        return "ClientEntreprise{" +
                "prenom='" + prenom + '\'' +
                ", code=" + code +
                ", dateInscription=" + dateInscription +
                ", adresse=" + adresse +
                ", telephone='" + telephone + '\'' +
                ", email='" + email + '\'' +
                ", pointsFidelite=" + pointsFidelite +
                '}';
    }
}
