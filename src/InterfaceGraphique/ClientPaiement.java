package InterfaceGraphique;

import javax.swing.*;
import java.awt.event.*;

public class ClientPaiement extends JFrame {
    private JPanel contentPane;



    public ClientPaiement()
    {
        setContentPane(contentPane);


        /*buttonOK.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {

            }
        });*/

    }




    public static void main(String[] args) {
        ClientPaiement dialog = new ClientPaiement();
        dialog.pack();
        dialog.setVisible(true);
        System.exit(0);
    }
}
