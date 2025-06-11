package fr.univartois.butinfo.sae.odf.model;

public interface TraitementPaiement {
    void executePaiement(Paiement p);
    void remboursement(Paiement p);
}
