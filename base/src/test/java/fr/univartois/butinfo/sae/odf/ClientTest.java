package fr.univartois.butinfo.sae.odf;

import fr.univartois.butinfo.sae.odf.model.Adresse;
import fr.univartois.butinfo.sae.odf.model.Client;
import fr.univartois.butinfo.sae.odf.model.Commune;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

//Classe ajoutée pour tester les getters, setters, equals et hashCode de Client pour atteindre plus de 80% de couverture de code
public class ClientTest {

    @Test
    void testGettersAndSetters() {
        // Sous-classe anonyme de Client pour tester sans modifier ClientEntreprise
        Client client = new Client() {
            @Override
            public void ajoutPointsFidelite(double achat) {
                // Méthode non implémentée volontairement
            }

            @Override
            public String getTypeClient() {
                return "TEST";
            }
        };

        // On simule un code et une date via accès direct (ou par constructeur s'il y en avait)
        // Sinon, on reste sur les setters/getters testables
        Adresse adresse = new Adresse(1, "Rue Exemple", new Commune("75", "Paris", "IDF"));
        client.setAdresse(adresse);
        client.setTelephone("0101010101");
        client.setEmail("test@example.com");

        // Vérification des setters/getters
        assertEquals(adresse, client.getAdresse());
        assertEquals("0101010101", client.getTelephone());
        assertEquals("test@example.com", client.getEmail());
        assertEquals(0, client.getPointsFidelite());
    }

    @Test
    void testEqualsAndHashCode() {
        // Création de deux clients avec même code
        Client c1 = new Client() {
            {
                code = 42;
            }
            @Override public void ajoutPointsFidelite(double achat) {
                // Méthode non implémentée volontairement
            }
            @Override public String getTypeClient() { return "TEST"; }
        };

        Client c2 = new Client() {
            {
                code = 42;
            }
            @Override public void ajoutPointsFidelite(double achat) {
                // Méthode non implémentée volontairement
            }
            @Override public String getTypeClient() { return "TEST"; }
        };

        Client c3 = new Client() {
            {
                code = 99;
            }
            @Override public void ajoutPointsFidelite(double achat) {
                // Méthode non implémentée volontairement
            }
            @Override public String getTypeClient() { return "TEST"; }
        };

        // Vérification de equals/hashCode
        assertEquals(c1, c2);
        assertEquals(c1.hashCode(), c2.hashCode());
        assertNotEquals(c1, c3);
    }
    
    @Test
    void testToStringClient() {
        Client client = new Client() {
            @Override public void ajoutPointsFidelite(double achat) {}
            @Override public String getTypeClient() { return "TEST"; }
        };
        client.setEmail("a@b.com");
        client.setTelephone("0123456789");

        String s = client.toString();
        assertTrue(s.contains("a@b.com"));
        assertTrue(s.contains("0123456789"));
        assertTrue(s.contains("code="));
    }

    
    
}
