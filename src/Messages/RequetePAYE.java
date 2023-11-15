package Messages;

import java.io.Serializable;

public class RequetePAYE implements Serializable {

    private int idFacture;
    private String nom_Client;
    private String visa;
    public RequetePAYE(int Id, String Nom_client, String Visa)
    {
        idFacture = Id;
        nom_Client = Nom_client;
        visa = Visa;
    }
    public RequetePAYE(RequetePAYE requetePAYE)
    {
        this.idFacture = requetePAYE.idFacture;
        this.nom_Client = requetePAYE.nom_Client;
        this.visa = requetePAYE.visa;
    }
    public int getIdFacture() {
        return idFacture;
    }
    public String getNom_Client() {
        return nom_Client;
    }
    public String getVisa() {
        return visa;
    }
}
