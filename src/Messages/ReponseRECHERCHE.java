package Messages;

import Interface.Reponse;

import java.io.Serializable;

public class ReponseRECHERCHE implements Reponse {
    //Format reponse : GETFACTURE#ok#idFacture, idClient, date, montant, paye$idFacture, idClient...
    private String reponse;
    public ReponseRECHERCHE(String Reponse)
    {
        reponse = Reponse;
    }
    public String getReponse() {
        return reponse;
    }
}
