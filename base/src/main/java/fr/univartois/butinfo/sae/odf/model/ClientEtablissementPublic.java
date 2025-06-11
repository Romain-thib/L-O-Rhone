package fr.univartois.butinfo.sae.odf.model;

import static fr.univartois.butinfo.sae.odf.model.TypeClient.ETABLISSEMENT_PUBLIC;

public class ClientEtablissementPublic extends Client {
    private String nom;
    private TypeEtablissement type;

    private final TypeClient typeClient = ETABLISSEMENT_PUBLIC;
    
    public ClientEtablissementPublic() {
        super();
    }

    public ClientEtablissementPublic(String nom, TypeEtablissement type) {
        super();
        this.nom = nom;
        this.type = type;
    }

    /**
	 * @return the nom
	 */
	public String getNom() {
		return nom;
	}

	/**
	 * @param nom the nom to set
	 */
	public void setNom(String nom) {
		this.nom = nom;
	}

	/**
	 * @return the type
	 */
	public TypeEtablissement getType() {
		return type;
	}

	/**
	 * @param type the type to set
	 */
	public void setType(TypeEtablissement type) {
		this.type = type;
	}

	@Override
    public void ajoutPointsFidelite(double achat) {
        int nbTranches = (int) (achat / 500);
        pointsFidelite += nbTranches * 10;
    }

    @Override
    public String getTypeClient() {
        return typeClient.toString();
    }

    @Override
	public String toString() {
		return "ClientEtablissementPublic [nom=" + nom + ", type=" + type + ", typeClient=" + typeClient + "]";
	}
    
    
}
