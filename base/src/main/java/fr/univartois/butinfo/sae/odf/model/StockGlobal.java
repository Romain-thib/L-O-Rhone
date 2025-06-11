package fr.univartois.butinfo.sae.odf.model;

import java.util.Arrays;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.util.Comparator;

public class StockGlobal {
    private final ObservableList<StockEau> stocks;

    public StockGlobal() {
        stocks = FXCollections.observableArrayList();
    }

    public void add(StockEau stock) {
        int index = stocks.indexOf(stock);

        if (index >= 0) {
            stocks.get(index).deltaQuantity(stock.getQuantite());
        } else {
            stocks.add(stock);
        }
    }

    public void sub(int index, int quantite) {
        if (index < 0 || index >= stocks.size()) {
            throw new IndexOutOfBoundsException("Invalid index");
        }
        stocks.get(index).deltaQuantity(-quantite);
    }

    public void triQuantite() {
        FXCollections.sort(stocks, StockEau.QuantityComparator);
    }

    public ObservableList<StockEau> getStocks() {
        return stocks;
    }
}

