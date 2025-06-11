package fr.univartois.butinfo.sae.odf.model;

import static fr.univartois.butinfo.sae.odf.model.TypeClient.ENTREPRISE;

public class ClientEntreprise extends Client{

	private String nomEntreprise;
    private String siret;

    private final TypeClient typeClient = ENTREPRISE;
    
    public ClientEntreprise() {
        super();
    }

    public ClientEntreprise(String nomEntreprise, String siret) {
        super();
        this.nomEntreprise = nomEntreprise;
        this.siret = siret;
    }

    /**
	 * @return the nom
	 */
	public String getNomEntreprise() {
		return nomEntreprise;
	}

	/**
	 * @param nom the nom to set
	 */
	public void setNomEntreprise(String nomEntreprise) {
		this.nomEntreprise = nomEntreprise;
	}

	/**
	 * @return the prenom
	 */
	public String getSiret() {
		return siret;
	}

	/**
	 * @param prenom the prenom to set
	 */
	public void setSiret(String siret) {
		this.siret = siret;
	}

	@Override
    public void ajoutPointsFidelite(double achat) {
        int nbTranches = (int) (achat / 1000);
        pointsFidelite += nbTranches * 10;
    }

    @Override
    public String getTypeClient() {
        return typeClient.toString();
    }

    @Override
	public String toString() {
		return "ClientEntreprise [nomEntreprise=" + nomEntreprise + ", siret=" + siret + ", typeClient=" + typeClient
				+ "]";
	}
}
