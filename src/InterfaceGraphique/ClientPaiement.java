package InterfaceGraphique;

import com.formdev.flatlaf.FlatDarculaLaf;

import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.border.LineBorder;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.*;
import java.net.Socket;
import java.text.DecimalFormat;


import static Socket.socket.Receive;
import static Socket.socket.Send;
import static Socket.socket.ClientSocket;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ClientPaiement extends JFrame
{

    private static ClientPaiement dialog;
    private JPanel MainPanel;
    private JPanel RecherchePanel;
    private JPanel FacturePanel;
    private JLabel NumClientLabel;
    private JTextField NumCliTxt;
    private JLabel ResultatLabel;
    private JTable ResultatTable;
    private JScrollPane ResultatScroll;
    private JPanel PaiementPanel;
    private JButton SelectionnerButton;
    private JButton payerButton;
    private JLabel NomCliLabel;
    private JLabel VisaLabel;
    private JTextField NomCliTxt;
    private JTextField VisaTxt;
    private JButton Rchbutton;
    private JTextField NomTxt;
    private JTextField MDPtxt;
    private JButton LoginButton;
    private JButton LogoutButton;
    private JLabel LoginLabel;
    private JLabel PasswordLabel;
    private JPanel LoginPanel;

    private Socket sClient;

    private boolean logged = false;

    private void loginOK()
    {
        LoginButton.setEnabled(false);
        LogoutButton.setEnabled(true);
        NumCliTxt.setEnabled(true);
        VisaTxt.setEnabled(true);
        Rchbutton.setEnabled(true);
        payerButton.setEnabled(true);
        ResultatTable.setEnabled(true);

        logged = true;
    }
    private void logoutOK()
    {
        LoginButton.setEnabled(true);
        LogoutButton.setEnabled(false);
        NumCliTxt.setEnabled(false);
        VisaTxt.setEnabled(false);
        Rchbutton.setEnabled(false);
        payerButton.setEnabled(false);
        ResultatTable.setEnabled(false);

        logged = false;
    }

    private void ajouteFactureTable(int idFacture, int idClient, String date, float montant)
    {
        DefaultTableModel model = (DefaultTableModel) ResultatTable.getModel();

        DecimalFormat decimalFormat = new DecimalFormat("#.##");
        String Montant = decimalFormat.format(montant);
        model.addRow(new Object[] { "" + idFacture, "" + idClient, date, Montant });

        ResultatTable.setModel(model);
    }

    private void viderTableFacture()
    {
        DefaultTableModel model = new DefaultTableModel();
        model.addColumn("Id Facture");
        model.addColumn("Id Client");
        model.addColumn("Date");
        model.addColumn("Montant (€)");

        ResultatTable.setModel(model);
        DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
        centerRenderer.setHorizontalAlignment(JLabel.CENTER);

        // Supposons que "columnIndex" est l'index de la colonne pour laquelle vous souhaitez définir l'alignement.
        ResultatTable.getColumnModel().getColumn(0).setCellRenderer(centerRenderer);
        ResultatTable.getColumnModel().getColumn(1).setCellRenderer(centerRenderer);
        ResultatTable.getColumnModel().getColumn(2).setCellRenderer(centerRenderer);

        ResultatTable.getTableHeader().setReorderingAllowed(false);
    }

    public boolean Logout()
    {
        String requete = "LOGOUT";

        //---------------------ENVOIE---------------------------//

        try
        {
            DataOutputStream fluxSortie = new DataOutputStream(new BufferedOutputStream(sClient.getOutputStream()));
            Send(requete,fluxSortie);
        }
        catch (IOException e1)
        {
            System.err.println("Erreur de SEND !" + e1.getMessage());
            try {
                sClient.close();
            } catch (IOException e)
            {
                throw new RuntimeException(e);
            }
            return false;
        }

        JOptionPane.showMessageDialog(null, "Logout réussi.", "LOGOUT", JOptionPane.INFORMATION_MESSAGE);
        logoutOK();
        return true;
    }

    public ClientPaiement() {


        //Dimension num = new Dimension(50,20);
        //NumCliTxt.setPreferredSize(num);
        Border border = new LineBorder(Color.BLACK, 1);
        NumCliTxt.setBorder(border);
        NomCliTxt.setBorder(border);
        VisaTxt.setBorder(border);
        MDPtxt.setBorder(border);
        NomTxt.setBorder(border);

        /*int width = ResultatTable.getWidth();
        int newHeight = 300;
        Dimension panier = new Dimension(width,newHeight);

        ResultatScroll.setPreferredSize(panier);*/

        Dimension nomCli = new Dimension(160,20);
        NomCliTxt.setPreferredSize(nomCli);
        VisaTxt.setPreferredSize(nomCli);

        DefaultTableModel model = new DefaultTableModel();
        model.addColumn("Id Facture");
        model.addColumn("Id Client");
        model.addColumn("Date");
        model.addColumn("Montant (€)");

        ResultatTable.setModel(model);
        DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
        centerRenderer.setHorizontalAlignment(JLabel.CENTER);

        // Supposons que "columnIndex" est l'index de la colonne pour laquelle vous souhaitez définir l'alignement.
        ResultatTable.getColumnModel().getColumn(0).setCellRenderer(centerRenderer);
        ResultatTable.getColumnModel().getColumn(1).setCellRenderer(centerRenderer);
        ResultatTable.getColumnModel().getColumn(2).setCellRenderer(centerRenderer);

        ResultatTable.getTableHeader().setReorderingAllowed(false);

        setTitle("Client Paiement");
        setContentPane(MainPanel);
        setLocationRelativeTo(null);
        pack();
        Dimension screen = Toolkit.getDefaultToolkit().getScreenSize();
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setSize(1000,700);
        setLocation((screen.width - this.getSize().width)/2,(screen.height - this.getSize().height)/2);
        setResizable(false);

        logoutOK();

        LoginButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String nomEmp = NomTxt.getText();
                String mdpEmp = MDPtxt.getText();

                if(nomEmp.equals("") || nomEmp.equals(" ") || mdpEmp.equals("") || mdpEmp.equals(" "))
                {
                    if(nomEmp.equals("") || nomEmp.equals(" "))
                    {
                        JOptionPane.showMessageDialog(null, "Veuillez entrer un nom d'employé adéquat !", "Erreur", JOptionPane.ERROR_MESSAGE);
                    }
                    if(mdpEmp.equals("") || mdpEmp.equals(" "))
                    {
                        JOptionPane.showMessageDialog(null, "Veuillez entrer un mot de passe d'employé adéquat !", "Erreur", JOptionPane.ERROR_MESSAGE);
                    }
                }
                else
                {
                    sClient = ClientSocket();

                    String requete = "LOGIN#" + nomEmp + "#" + mdpEmp;
                    String reponse = "";


                    //-------------------Envoie-------------------------
                    try
                    {
                        DataOutputStream fluxSortie = new DataOutputStream(new BufferedOutputStream(sClient.getOutputStream()));
                        Send(requete,fluxSortie);
                    }
                    catch (IOException e1)
                    {
                        System.err.println("Erreur de SEND !" + e1.getMessage());
                        try {
                            sClient.close();
                        } catch (IOException e2)
                        {
                            throw new RuntimeException(e2);
                        }
                    }

                    //--------------------Recepetion------------------------
                    try
                    {
                        DataInputStream fluxEntree = new DataInputStream(new BufferedInputStream(sClient.getInputStream()));
                        reponse = Receive(fluxEntree);

                        String[] data = reponse.split("#");
                        if(data[1].equals("ok"))
                        {
                            JOptionPane.showMessageDialog(null, "Login réussi.", "LOGIN", JOptionPane.INFORMATION_MESSAGE);
                            loginOK();
                        }
                        else if (data[1].equals("ko"))
                        {
                            //affichage de l'erreur
                            JOptionPane.showMessageDialog(null, data[2], "Erreur", JOptionPane.ERROR_MESSAGE);
                        }
                    }
                    catch (IOException e1)
                    {
                        System.err.println("Erreur de RECEIVE !" + e1.getMessage());
                        try {
                            sClient.close();
                        } catch (IOException e2)
                        {
                            throw new RuntimeException(e2);
                        }
                    }
                }
            }
        });

        LogoutButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(logged == true)
                {
                    if(!Logout())
                    {
                        System.err.println("Erreur dans le logout");
                        System.exit(1);
                    }
                }
                else
                {
                    JOptionPane.showMessageDialog(null, "Impossible de se logout (pas log)", "Erreur", JOptionPane.ERROR_MESSAGE);
                }

                logoutOK();
            }
        });

        Rchbutton.addActionListener(new ActionListener()
        {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                String stridClient = NumCliTxt.getText();
                if(!estChiffresPositifs(stridClient))
                {
                    JOptionPane.showMessageDialog(null, "Veuillez entrer un id de client adéquat (chiffre positif) !", "Erreur", JOptionPane.ERROR_MESSAGE);
                }
                else
                {
                    String requete = "GETFACTURE#" + stridClient;

                    //----------------Envoie----------------------

                    try
                    {
                        DataOutputStream fluxSortie = new DataOutputStream(new BufferedOutputStream(sClient.getOutputStream()));
                        Send(requete,fluxSortie);
                    }
                    catch (IOException e1)
                    {
                        System.err.println("Erreur de SEND !" + e1.getMessage());
                        try {
                            sClient.close();
                        } catch (IOException e2)
                        {
                            throw new RuntimeException(e2);
                        }
                    }

                    //------------------Reception-------------------
                    String reponse = "";

                    //Format reponse : GETFACTURE#ok#idFacture, idClient, date, montant, paye$idFacture, idClient...

                    int idFacture = 0;
                    int idClient = 0;
                    String date = "";
                    float montant = 0.0F;

                    try
                    {
                        DataInputStream fluxEntree = new DataInputStream(new BufferedInputStream(sClient.getInputStream()));
                        reponse = Receive(fluxEntree);

                        String[] testOK = reponse.split("#");
                        if(testOK[1].equals("ko"))
                        {
                            JOptionPane.showMessageDialog(null, testOK[2], "Erreur", JOptionPane.ERROR_MESSAGE);
                            System.exit(1);
                        }

                        viderTableFacture();

                        //Format reponse : GETFACTURE#ok#idFacture, idClient, date, montant, paye
                        String[] ElemCaddie = testOK[2].split("\\$");
                        for (String elem : ElemCaddie)
                        {
                            elem = elem.replace("$", "");
                            String[] UnElemCaddie = elem.split(",");

                            if (UnElemCaddie.length >= 5)
                            {

                                idFacture = Integer.parseInt(UnElemCaddie[0]);
                                idClient = Integer.parseInt(UnElemCaddie[1]);
                                date = UnElemCaddie[2];
                                montant = Float.parseFloat(UnElemCaddie[3]);

                                ajouteFactureTable(idFacture, idClient, date, montant);
                            }
                        }
                    }
                    catch (IOException e1)
                    {
                        System.err.println("Erreur de RECEIVE !" + e1.getMessage());
                        try {
                            sClient.close();
                        } catch (IOException e2)
                        {
                            throw new RuntimeException(e2);
                        }
                    }
                }
            }
        });

        payerButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                int idFacture = -1;
                int factureSelectionne = ResultatTable.getSelectedRow();
                if(factureSelectionne == -1)
                {
                    JOptionPane.showMessageDialog(null, "Veuillez sélectionner une ligne", "Erreur", JOptionPane.ERROR_MESSAGE);
                }
                else
                {
                    String nomClient = NomCliTxt.getText();
                    String visa = VisaTxt.getText();
                    if(nomClient.equals("") || nomClient.equals(" "))
                    {
                        JOptionPane.showMessageDialog(null, "Nom client invalide !", "Erreur", JOptionPane.ERROR_MESSAGE);
                        return;
                    }
                    if(visa.equals("") || visa.equals(" "))
                    {
                        JOptionPane.showMessageDialog(null, "Visa vide !", "Erreur", JOptionPane.ERROR_MESSAGE);
                        return;
                    }

                    //----------------Envoie----------------------
                    idFacture = (int) ResultatTable.getValueAt(factureSelectionne, 0);

                    String requete = "PAYFACTURE#" + idFacture + "#" + nomClient + "#" + visa;

                    try
                    {
                        DataOutputStream fluxSortie = new DataOutputStream(new BufferedOutputStream(sClient.getOutputStream()));
                        Send(requete,fluxSortie);
                    }
                    catch (IOException e1)
                    {
                        System.err.println("Erreur de SEND !" + e1.getMessage());
                        try {
                            sClient.close();
                        } catch (IOException e2)
                        {
                            throw new RuntimeException(e2);
                        }
                    }

                    //-----------------reponse---------------------
                    String reponse = "";
                    try
                    {
                        DataInputStream fluxEntree = new DataInputStream(new BufferedInputStream(sClient.getInputStream()));
                        reponse = Receive(fluxEntree);

                        //Format reponse = PAYFACTURE#ok

                        String[] visaok = reponse.split("#");
                        if(visaok[1].equals("ok"))
                        {
                            JOptionPane.showMessageDialog(null, "Payement effectuer", "Payement", JOptionPane.INFORMATION_MESSAGE);
                        }
                        else
                        {
                            JOptionPane.showMessageDialog(null, "Visa invalide !", "Erreur", JOptionPane.ERROR_MESSAGE);
                        }
                    }
                    catch (IOException e1)
                    {
                        System.err.println("Erreur de RECEIVE !" + e1.getMessage());
                        try {
                            sClient.close();
                        } catch (IOException e2)
                        {
                            throw new RuntimeException(e2);
                        }
                    }
                }
            }
        });

        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                if(logged == true)
                {
                    if(!Logout())
                    {
                        System.err.println("Erreur dans le logout");
                        System.exit(1);
                    }
                }

                //fermeture de la fenêtre
                dialog.dispose();
                System.exit(0);
            }
        });
    }
    /*public static boolean TestVisa (String numeroCarte) {
        String visaCardRegex = "^4[0-9]{12}(?:[0-9]{3})?$";

        Pattern pattern = Pattern.compile(visaCardRegex);
        Matcher matcher = pattern.matcher(numeroCarte);

        return matcher.matches();
    }*/
    public static boolean estChiffresPositifs(String tmp)
    {
        // Utilisation d'une expression régulière pour vérifier si la chaîne est composée uniquement de chiffres positifs
        if(tmp.matches("\\d+") && Integer.parseInt(tmp) > 0)
            return true;
        else
            return false;
    }


    public static void main(String[] args)
    {
        FlatDarculaLaf.install(new FlatDarculaLaf());
        dialog = new ClientPaiement();
        dialog.setVisible(true);
    }
}
