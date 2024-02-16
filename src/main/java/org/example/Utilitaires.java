package org.example;

import org.hsqldb.Column;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.*;
import java.io.*;
import java.io.IOException;
import javax.imageio.ImageIO;



public class Utilitaires {
    public static String ajouterCaractereGauche(char car, int longueur, String donnee) {
        String newCaractere = "";
        int nbCaracteres = longueur - donnee.length();
        for (int i = 0; i < nbCaracteres; i++) {
            newCaractere += car;
        }
        return newCaractere + donnee;
    }

    public static String ajouterEspacesFin(int tailleColonne, String donnee) {
        String donneeAvecEspaces;
        int nbEspaces = tailleColonne - donnee.length();
        donneeAvecEspaces = donnee;

        for (int i = 0; i < nbEspaces; i++) {
            donneeAvecEspaces += " ";
        }
        return donneeAvecEspaces;
        //or?-> return String.format("%-" + tailleColonne + "s", donnee);
    }

    public static void message(String message, String titre)  {
        JOptionPane.showMessageDialog(null, message, titre, JOptionPane.PLAIN_MESSAGE);
    }

    public static void texteMessage(String message) {
        JTextArea textArea = new JTextArea(message);
        textArea.setEditable(false);
        JScrollPane scrollPane = new JScrollPane(textArea);
        textArea.setLineWrap(true);
        textArea.setWrapStyleWord(true);
        scrollPane.setPreferredSize(new Dimension(1000, 500));
        JOptionPane.showMessageDialog(null, scrollPane, "Jtext", JOptionPane.PLAIN_MESSAGE);
    }

    private boolean changeStringInputintoBool(String message) { //CHANGER UN STRING EN BOOLEAN
        String reponse = inputMessage(message, "Modifier");
        return reponse.equalsIgnoreCase("O");
    }
    public static void errorMessage(String message) {
        JOptionPane.showMessageDialog(null, message, "Erreur", JOptionPane.ERROR_MESSAGE);
    }


public static String inputMessageOld(String message, String titre) {
    String input = null;

    while (input == null || input.isEmpty()) {
        try {
            input = JOptionPane.showInputDialog(null, message, titre, JOptionPane.PLAIN_MESSAGE);

            if (input == null) {
                // L'utilisateur a appuyé sur "Cancel", revenir au menu général
                Compagnie.menuGeneral();
                return null;  // Sortir de la méthode si l'utilisateur a appuyé sur "Cancel"
            }

            if (input.isEmpty()) {
                // Afficher un message indiquant que la saisie est vide
                errorMessage("Veuillez entrer une valeur.");
            }
        } catch (Exception e) {
            // Gérer d'autres exceptions si nécessaire
            throw new RuntimeException(e);
        }
    }

    return input;
}
    public static Integer inputInteger(String message, String title) {
        String input = inputMessage(message, title);

        if (input == null) {
            // L'utilisateur a annulé ou fermé la fenêtre de dialogue
            return null;
        }

        try {
            return Integer.parseInt(input);
        } catch (NumberFormatException e) {
            // Gérer le cas où la saisie n'est pas un nombre entier valide
            JOptionPane.showMessageDialog(null, "Veuillez entrer un nombre entier valide.", "Erreur", JOptionPane.ERROR_MESSAGE);
            return inputInteger(message, title); // Rappeler la méthode pour demander à nouveau la saisie
        }
    }





    public static boolean yesNoInputMessage(String message) {
        int optionType = JOptionPane.YES_NO_OPTION;
        int result = JOptionPane.showConfirmDialog(null, message, "Confirmation", optionType);
        return result == JOptionPane.YES_OPTION;
    }

    public static Map<String, Boolean> optionsRadioInputMessage(String opt1Txt, String opt2Txt, String opt3Txt, String opt4Txt) {
        // Création d'un dialogue modal au lieu d'une JFrame
        JDialog dialog = new JDialog();
        dialog.setModal(true);
        dialog.setLayout(new FlowLayout());

        JLabel label = new JLabel("Choisissez les options souhaitées:");
        dialog.add(label);

        JCheckBox opt1 = new JCheckBox(opt1Txt);
        JCheckBox opt2 = new JCheckBox(opt2Txt);
        JCheckBox opt3 = new JCheckBox(opt3Txt);
        JCheckBox opt4 = new JCheckBox(opt4Txt);

        dialog.add(opt1);
        dialog.add(opt2);
        dialog.add(opt3);
        dialog.add(opt4);

        JButton button = new JButton("Submit");
        dialog.add(button);

        Map<String, Boolean> options = new HashMap<>();
        button.addActionListener(e -> {
            options.put("opt1", opt1.isSelected());
            options.put("opt2", opt2.isSelected());
            options.put("opt3", opt3.isSelected());
            options.put("opt4", opt4.isSelected());
            dialog.dispose(); // Fermer le dialogue au lieu de la JFrame
        });

        dialog.pack();
        dialog.setLocationRelativeTo(null); // Centrer le dialogue
        dialog.setVisible(true); // Afficher le dialogue (bloquant jusqu'à la fermeture)

        return options;
    }

    //pour un bouton de retour au menu principal, par exemple à partir d'un listage
    public static JButton returnToMenuButton() {
        JButton retourButton = new JButton("Retour au Menu Principal (en conservant cette fenêtre)");
        retourButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try { //attention, menuGeneral() vient de la classe Compagnie!
                    Compagnie.menuGeneral();
                } catch (Exception ex) {
                    throw new RuntimeException(ex);
                }
            }
        });
        return retourButton;
    }

    public static JPanel okButton(JDialog dialog) { //pour un bouton de fermeture de fenêtre et de retour au menu precedent a l'intérieur d'une fenêtre JDIALOG
        JPanel okButtonPanel = new JPanel();
        okButtonPanel.setOpaque(false);
        JButton okButton = new JButton("OK");
        okButton.addActionListener(e ->
                dialog.dispose()); // fermé la fenêtre
        okButtonPanel.add(okButton);
        return okButtonPanel;
    }
    //cancel button:
    public static JPanel okANDCancelButton(JDialog dialog) {
        JPanel okCancelButtonPanel = new JPanel();
        okCancelButtonPanel.setOpaque(false);

        JButton okButton = new JButton("OK");
        okButton.addActionListener(e -> dialog.dispose()); //ferme la fenêtre
        okCancelButtonPanel.add(okButton); //ajout du bouton ok au panel

        JButton cancelButton = new JButton("Cancel");
        cancelButton.addActionListener(e -> {
            dialog.dispose();
            try { //attention, menuGeneral() vient de la classe Compagnie!
                Compagnie.menuGeneral();
            } catch (Exception ex) { //s'assurer que cette méthode ne bloque pas ou ne lance pas une nouvelle instance de GUI
                throw new RuntimeException(ex);
            }
        });
        okCancelButtonPanel.add(cancelButton);
        okCancelButtonPanel.setOpaque(false);
        return okCancelButtonPanel;
    }



    //grid method:
    public static GridBagConstraints gridConstraints(int x, int y, int fill, double weightx, double weighty) {
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = x;
        gbc.gridy = y;
        gbc.fill = fill;
        gbc.weightx = weightx; // Controls how space is distributed among columns
        gbc.weighty = weighty; // Controls how space is distributed among rows
        gbc.anchor = GridBagConstraints.NORTH; // If you want components to be aligned to the top
        gbc.gridwidth = 1;
        return gbc;
    }
    public static GridBagConstraints gridConstraintsForMenu(int x, int y, int fill, double weightx, double weighty, int anchor) {
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = x;
        gbc.gridy = y;
        gbc.fill = fill;
        gbc.weightx = weightx;
        gbc.weighty = weighty;
        gbc.anchor = anchor;
        gbc.gridwidth = 1;
        gbc.insets = new Insets(10, 10, 10, 10); // ajoutez des marges si nécessaire
        return gbc;
    }

    public static String inputMessage(String message, String titre) {
        JDialog dialog = new JDialog();
        dialog.setModal(true);
        dialog.setTitle(titre);
        dialog.setLayout(new BorderLayout());
        dialog.setSize(1300, 600);
        dialog.setLocationRelativeTo(null);

        // Background panel with image
        JPanel backgroundPanel = new BackgroundPanel("/space.jpeg");
        backgroundPanel.setLayout(new GridBagLayout());

        // Panel for components using GridBagLayout
        JPanel inputPanel = new JPanel(new GridBagLayout());
        inputPanel.setOpaque(false);

        String[] messageLines = message.split("\n");

        // Ajouter des étiquettes pour chaque ligne de message
        for (int i = 0; i < messageLines.length; i++) {
            JLabel label = new JLabel(messageLines[i], SwingConstants.LEFT);
            label.setForeground(Color.WHITE);
            GridBagConstraints gbcLabel = gridConstraintsForMenu(0, i, GridBagConstraints.NONE, 0, 1.0, GridBagConstraints.CENTER);
            inputPanel.add(label, gbcLabel);
        }

        // Champ de texte pour l'entrée avec une taille préférée pour contrôler la largeur
        JTextField textField = new JTextField();
        textField.setPreferredSize(new Dimension(200, 24)); // ajustez la largeur et la hauteur comme nécessaire
        GridBagConstraints gbcTextField = gridConstraintsForMenu(0, messageLines.length, GridBagConstraints.NONE, 0, 0, GridBagConstraints.CENTER);
        gbcTextField.ipadx = 200; // contrôle la largeur supplémentaire du champ
        gbcTextField.gridwidth = GridBagConstraints.REMAINDER; // fin de ligne
        inputPanel.add(textField, gbcTextField);


        // Button panel with OK button
        JPanel buttonPanel = okButton(dialog);
        GridBagConstraints gbcButtonPanel = gridConstraints(0, messageLines.length + 1, GridBagConstraints.HORIZONTAL, 1.0, 0);
        inputPanel.add(buttonPanel, gbcButtonPanel);

        // Ajoute inputPanel au center of backgroundPanel
        GridBagConstraints gbcInputPanel = gridConstraints(0, 0, GridBagConstraints.BOTH, 1.0, 1.0);
        backgroundPanel.add(inputPanel, gbcInputPanel);

        dialog.add(backgroundPanel);
        dialog.setVisible(true);

        return textField.getText(); // Return l'input after the dialog is closed
    }
    public static void displayVolsPanel(String resultat) {
        JDialog dialog = new JDialog();
        dialog.setTitle("Cie Air Relax");
        dialog.setModal(true);
        dialog.setLayout(new BorderLayout());
        dialog.setSize(1300, 600);
        dialog.setLocationRelativeTo(null);

        BackgroundPanel backgroundPanel = new BackgroundPanel("/test5.jpeg");
        backgroundPanel.setLayout(new GridBagLayout());

        JPanel textPanel = new JPanel(new GridBagLayout());
        textPanel.setOpaque(false);

        JTextArea textArea = new JTextArea(resultat);
        textArea.setFont(new Font(Font.MONOSPACED, Font.PLAIN, 12));
        textArea.setEditable(false);
        textArea.setBackground(Color.BLACK);
        textArea.setForeground(Color.WHITE);

        JScrollPane scrollPane = new JScrollPane(textArea);
        scrollPane.setOpaque(false);
        scrollPane.getViewport().setOpaque(false);


        //les grids serviront de coordonnées pour placer les composants
        GridBagConstraints gbc = new GridBagConstraints(); //pour placer le scrollPane
        gbc.gridx = 0; // l'axe des x (colonne) (0,0) est en haut à gauche, 0 est au centre? À verifier
        gbc.gridy = 0; // l'axe des y
        gbc.fill = GridBagConstraints.BOTH; //pour remplir l'espace disponible


        textPanel.add(scrollPane, gbc);

        GridBagConstraints gbc2 = new GridBagConstraints(); //pour placer le textPanel
        gbc2.gridx = 0;
        gbc2.gridy = 0; //on place le textArea en haut à gauche (0,0)
        gbc2.anchor = GridBagConstraints.CENTER;
        gbc2.gridwidth = 1;
        backgroundPanel.add(textPanel, gbc2);

        JPanel okButtonPanel = okButton(dialog); //param = la fenêtre à fermer

        GridBagConstraints gbc3 = new GridBagConstraints(); //pour placer le bouton ok
        gbc3.gridx = 0;
        gbc3.gridy = 1; //on place le bouton en dessous du textArea (0,1)
        gbc3.anchor = GridBagConstraints.CENTER;
        backgroundPanel.add(okButtonPanel, gbc3);

        //ICI ON DOIT APPELER LA METH GRID A LA PLACE!! A UPDATE
//        // For placing the scrollPane
//        GridBagConstraints gbcScrollPane = gridConstraints(0, 0, GridBagConstraints.BOTH, 1.0, 1.0);
//        textPanel.add(scrollPane, gbcScrollPane);
//
//// For placing the textPanel
//        GridBagConstraints gbcTextPanel = gridConstraints(0, 1, GridBagConstraints.HORIZONTAL, 1.0, 0);
//        backgroundPanel.add(textPanel, gbcTextPanel);
//
//// For placing the okButtonPanel
//        GridBagConstraints gbcOkButtonPanel = gridConstraints(0, 2, GridBagConstraints.HORIZONTAL, 1.0, 0);
//        backgroundPanel.add(okButtonPanel, gbcOkButtonPanel);


        dialog.add(backgroundPanel);
        dialog.setVisible(true);
    }


    // Classe interne pour le panneau de fond avec image
    static class BackgroundPanel extends JPanel { //pour la creation d'un panneau avec image de fond
        private Image backgroundImage;

        public BackgroundPanel(String pathToImage) { //constructeur
            try {
                backgroundImage = ImageIO.read(getClass().getResource(pathToImage));
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);  // dessine le panneau
            g.drawImage(backgroundImage, 0, 0, this.getWidth(), this.getHeight(), this); // dessine l'image

            //rendre le panneau moins opaque
            Graphics2D g2d = (Graphics2D) g.create();
            g2d.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.5f)); // Adjust transparency level as needed
            g2d.setColor(Color.BLACK); // Or any other color that works with your text
            g2d.fillRect(0, 0, this.getWidth(), this.getHeight()); // Adjust the rectangle size as needed
            g2d.dispose();
        }
    }


}



