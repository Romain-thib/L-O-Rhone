package fr.univartois.butinfo.sae.odf.model;

public abstract class MethodePaiement {
    abstract void payer(double montant);
    abstract void rembourser(double montant);
}
