package fr.univartois.butinfo.sae.odf.model;

import java.util.Objects;

public class Entrepot {
    private int code;
    private String nom;
    private Adresse adresse;

    public Entrepot(int code, String nom, Adresse adresse) {
        this.code = code;
        this.nom = nom;
        this.adresse = adresse;
    }

    public int getCode() {
        return code;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public Adresse getAdresse() {
        return adresse;
    }

    public void setAdresse(Adresse adresse) {
        this.adresse = adresse;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Entrepot entrepot)) return false;
        return getCode() == entrepot.getCode();
    }

    @Override
    public int hashCode() {
        return Objects.hash(getCode());
    }

    @Override
    public String toString() {
        return "Entrepot{" +
                "code=" + code +
                ", nom='" + nom + '\'' +
                ", adresse=" + adresse +
                '}';
    }
}
