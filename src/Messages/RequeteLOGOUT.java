package Messages;

import Interface.Requete;

import java.io.Serializable;

public class RequeteLOGOUT implements Requete {
    private String Logout;
    public RequeteLOGOUT(String logout)
    {
        Logout = logout;
    }
    public RequeteLOGOUT(RequeteLOGOUT requeteLOGOUT)
    {
        this.Logout = requeteLOGOUT.Logout;
    }
    public String getLogout()
    {
        return Logout;
    }
}
