package Messages;

import Interface.Requete;

import java.io.Serializable;

public class RequeteLOGIN implements Requete {

    private String nom_employer;
    private String mdp_employer;
    public RequeteLOGIN(String log, String mdp)
    {
        nom_employer = log;
        mdp_employer = mdp;
    }
    public RequeteLOGIN(RequeteLOGIN requeteLOGIN)
    {
        this.nom_employer = requeteLOGIN.nom_employer;
        this.mdp_employer = requeteLOGIN.mdp_employer;
    }
    public String getLogin()
    {
        return nom_employer;
    }
    public String getPassWord()
    {
        return mdp_employer;
    }
}
