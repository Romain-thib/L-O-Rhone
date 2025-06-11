package fr.univartois.butinfo.sae.odf.model;

import java.util.Comparator;
import java.util.Objects;

public class StockEau {
    private Categorie categorie;
    private Entrepot entrepot;
    /**
     * Le nombre de caisses de bouteilles.
     */
    private int quantite;

    public StockEau(Categorie categorie, Entrepot entrepot, int quantite) {
        this.categorie = categorie;
        this.entrepot = entrepot;
        this.quantite = quantite;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof StockEau stockEau)) return false;
        return categorie == stockEau.categorie && Objects.equals(entrepot, stockEau.entrepot);
    }

    @Override
    public int hashCode() {
        return Objects.hash(categorie, entrepot);
    }

    public Categorie getCategorie() {
        return categorie;
    }

    public Entrepot getEntrepot() {
        return entrepot;
    }

    public int getQuantite() {
        return quantite;
    }

    public void deltaQuantity(int quantite) {
        this.quantite += quantite;
    }


    public static Comparator<StockEau> QuantityComparator = new Comparator<StockEau>() {
        @Override
        public int compare(StockEau o1, StockEau o2) {
            return o1.quantite - o2.quantite;
        }
    };

    @Override
    public String toString() {
        return "StockEau{" +
                "categorie=" + categorie +
                ", entrepot=" + entrepot +
                ", quantite=" + quantite +
                '}';
    }
}
