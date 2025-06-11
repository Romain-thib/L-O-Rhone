package fr.univartois.butinfo.sae.odf.model;

import static fr.univartois.butinfo.sae.odf.model.TypeClient.ETABLISSEMENT_PUBLIC;

public class ClientEtablissementPublic extends Client {
    private String nom;
    private TypeEtablissement type;

    private final TypeClient typeClient = ETABLISSEMENT_PUBLIC;

    @Override
    public void ajoutPointsFidelite(double achat) {
        int nbTranches = Double.valueOf(achat).intValue() / 500;
        pointsFidelite += nbTranches * 10;
    }

    @Override
    public String getTypeClient() {
        return typeClient.toString();
    }

    @Override
    public String toString() {
        return "ClientEtablissementPublic{" +
                "type=" + type +
                ", code=" + code +
                ", dateInscription=" + dateInscription +
                ", adresse=" + adresse +
                ", telephone='" + telephone + '\'' +
                ", email='" + email + '\'' +
                ", pointsFidelite=" + pointsFidelite +
                '}';
    }
}
