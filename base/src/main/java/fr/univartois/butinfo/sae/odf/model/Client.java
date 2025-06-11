package fr.univartois.butinfo.sae.odf.model;

import java.util.Date;
import java.util.Objects;

public abstract class Client {
    protected static int compteurCode = 1; // Compteur statique pour générer des codes uniques
    protected int code;
    protected Date dateInscription;
    protected Adresse adresse;
    protected String telephone;
    protected String email;
    protected int pointsFidelite;

    // Constructeur
    public Client() {
        this.code = compteurCode++;
        this.dateInscription = new Date();
        this.pointsFidelite = 0;
    }

    public abstract void ajoutPointsFidelite(double achat);

    public abstract String getTypeClient();

    public int getCode() {
        return code;
    }

    public Date getDateInscription() {
        return dateInscription;
    }

    public Adresse getAdresse() {
        return adresse;
    }

    public String getTelephone() {
        return telephone;
    }

    public String getEmail() {
        return email;
    }

    public int getPointsFidelite() {
        return pointsFidelite;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public void setDateInscription(Date dateInscription) {
        this.dateInscription = dateInscription;
    }

    public void setAdresse(Adresse adresse) {
        this.adresse = adresse;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setPointsFidelite(int pointsFidelite) {
        this.pointsFidelite = pointsFidelite;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Client client)) return false;
        return getCode() == client.getCode();
    }

    @Override
    public int hashCode() {
        return Objects.hash(getCode());
    }

    @Override
    public String toString() {
        return "Client{" +
                "code=" + code +
                ", email='" + email + '\'' +
                ", telephone='" + telephone + '\'' +
                '}';
    }
}
