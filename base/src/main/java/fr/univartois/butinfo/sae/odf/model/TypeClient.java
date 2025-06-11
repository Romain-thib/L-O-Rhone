package fr.univartois.butinfo.sae.odf.model;

public enum TypeClient {

    PARTICULIER("Particulier"), ENTREPRISE("Entreprise"), ETABLISSEMENT_PUBLIC("Établissement public");
    private final String val;


    TypeClient(String val) {
        this.val = val;
    }

    @Override
    public String toString() {
        return val;
    }
}
