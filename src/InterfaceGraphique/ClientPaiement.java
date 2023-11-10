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


    public ClientPaiement()
    {


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
        model.addColumn("Facture ID");
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


        /*buttonOK.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {

            }
        });*/


        Rchbutton.addActionListener(new ActionListener()
        {
            @Override
            public void actionPerformed(ActionEvent e)
            {

            }
        });



    }




    public static void main(String[] args)
    {
        FlatDarculaLaf.install(new FlatDarculaLaf());
        dialog = new ClientPaiement();
        dialog.setVisible(true);
    }
}
