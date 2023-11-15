package Messages;

import Interface.Reponse;

import java.io.Serializable;

public class ReponsePAYE implements Reponse {

    private boolean valide;
    public ReponsePAYE(boolean Valide) {
        valide = Valide;
    }
    public String getValide() {
        if(valide)
            return "PAYFACTURE#ok";
        else
            return "PAYFACTURE#ko";
    }
}
