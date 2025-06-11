package fr.univartois.butinfo.sae.odf.model;

import java.util.Arrays;

public class StockGlobal {
    private final StockEau[] stocks;
    private int last = 0;

    public StockGlobal() {
        stocks = new StockEau[50];
    }

    public void add(StockEau stock) {
        int index;

        if ((index = Arrays.stream(stocks).toList().indexOf(stock)) >= 0) {
            stocks[index].deltaQuantity(stock.getQuantite());
        } else {
            stocks[last++] = stock;
        }
    }

    public void sub(int index, int quantite) {
        if (index < 0 || index >= last) {
            throw new IndexOutOfBoundsException("Invalid index");
        }
        stocks[index].deltaQuantity(-quantite);
    }

    public void triQuantite() {
        Arrays.sort(stocks, StockEau.QuantityComparator);
    }

}
