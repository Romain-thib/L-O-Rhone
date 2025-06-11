package fr.univartois.butinfo.sae.odf.model;

public enum TypeEtablissement {
    EPIC("EPIC"), EPA("EPA"), EPSCT("EPSCT");

    private final String val;


    TypeEtablissement(String val) {
        this.val = val;
    }

    @Override
    public String toString() {
        return val;
    }

}
