package fr.univartois.butinfo.sae.odf.model;

import java.util.Objects;

public class Commune {
    private String code;
    private String nom;
    private String departement;

    public Commune(String code, String nom, String departement) {
        this.code = code;
        this.nom = nom;
        this.departement = departement;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getDepartement() {
        return departement;
    }

    public void setDepartement(String departement) {
        this.departement = departement;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Commune commune)) return false;
        return Objects.equals(getCode(), commune.getCode()) && Objects.equals(getNom(), commune.getNom()) && Objects.equals(getDepartement(), commune.getDepartement());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getCode(), getNom(), getDepartement());
    }

    @Override
    public String toString() {
        return "Commune{" +
                "code='" + code + '\'' +
                ", nom='" + nom + '\'' +
                ", departement='" + departement + '\'' +
                '}';
    }
}
