package Messages;

import Interface.Requete;

import java.io.Serializable;

public class RequeteRECHERCHE implements Requete {
    private int idClient;
    public RequeteRECHERCHE(int id)
    {
        idClient = id;
    }
    public RequeteRECHERCHE(RequeteRECHERCHE requeteRECHERCHE)
    {
        this.idClient = requeteRECHERCHE.idClient;
    }
    public int getIdClient() {
        return idClient;
    }
}
