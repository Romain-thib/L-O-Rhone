
// Voici le code compléte mais par manque de temps nous l'avons mis en commentaire car il aurait fallu modifier les test qui ont été réaliser avant

//package fr.univartois.butinfo.sae.odf.model;
//
//public class PaiementSimple extends Paiement implements TraitementPaiement{
//	
//	public PaiementSimple(double montant, MethodePaiement methodePaiement) {
//        super(montant, methodePaiement);
//    }
//
//    @Override
//    public void executePaiement(Paiement p) {
//        p.getMethodePaiement().payer(p.getMontant());
//    }
//
//    @Override
//    public void remboursement(Paiement p) {
//        p.getMethodePaiement().rembourser(p.getMontant());
//    }
//}

package fr.univartois.butinfo.sae.odf.model;

public class PaiementSimple extends Paiement implements TraitementPaiement{
    @Override
    public void executePaiement(Paiement p) {

    }

    @Override
    public void remboursement(Paiement p) {

    }
}