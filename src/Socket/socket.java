package Socket;


import java.io.*;
import java.net.Socket;

import Messages.*;

public final class socket
{

    public static final String SERVER_IP = "192.168.114.1";
    public static final int SERVER_PORT = 50001;
    public static Socket ClientSocket()
    {
        try
        {
            Socket socket = new Socket(SERVER_IP, SERVER_PORT);
            System.out.println("Connecte au serveur.");

            return socket;
        } catch (IOException e)
        {
            e.printStackTrace();
            return null;
        }
    }

    private socket()
    {
        //empêche l'instanciation de la classe
    }
    public static void Send(Object obj, ObjectOutputStream envoi) throws IOException
    {
        envoi.writeObject(obj);
        envoi.flush();
        System.out.println("Requete envoyée");
    }

    public static Object Receive(ObjectInputStream reception) throws IOException, ClassNotFoundException {

        Object requete = reception.readObject();

        System.out.println("Réponse reçue");

        return requete;
    }

}
