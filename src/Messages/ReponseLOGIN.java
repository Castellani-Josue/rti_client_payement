package Messages;


import Interface.Reponse;

import java.io.Serializable;

public class ReponseLOGIN implements Reponse {
    private boolean Valide;
    private int cas = -1;

    private String Errmessage;

    public ReponseLOGIN(boolean v)
    {
        Valide = v;
    }

    public void setCas(int i)
    {
        cas = i;
    }

    public String getValide()
    {
        if(Valide)
            return "LOGIN#OK";
        else
        {
            if(cas == 1)
            {
                Errmessage = "Mot de passe incorrecte.";
            }
            else if(cas == 2)
            {
                Errmessage = "Employé non trouvé dans la bd.";
            }
            else
            {
                Errmessage = "Erreur indéfinie.";
            }
            return "LOGIN#KO";
        }
    }

    public String getErrmessage()
    {
        return Errmessage;
    }
}
