package fr.univartois.butinfo.sae.odf.model;

import java.util.Objects;

public class Adresse {
    private int numero;
    private String voie;
    private Commune commune;

    public Adresse(int numero, String voie, Commune commune) {
        this.numero = numero;
        this.voie = voie;
        this.commune = commune;
    }

    public int getNumero() {
        return numero;
    }

    public void setNumero(int numero) {
        this.numero = numero;
    }

    public String getVoie() {
        return voie;
    }

    public void setVoie(String voie) {
        this.voie = voie;
    }

    public Commune getCommune() {
        return commune;
    }

    public void setCommune(Commune commune) {
        this.commune = commune;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Adresse adresse)) return false;
        return getNumero() == adresse.getNumero() && Objects.equals(getVoie(), adresse.getVoie()) && Objects.equals(getCommune(), adresse.getCommune());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getNumero(), getVoie(), getCommune());
    }

    @Override
    public String toString() {
        return "Adresse{" +
                "numero=" + numero +
                ", voie='" + voie + '\'' +
                ", commune=" + commune +
                '}';
    }
}
