package org.Compagnie.util;

import org.Compagnie.model.Date;
import org.Compagnie.vue.VueVol;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class UtilVue { // ici sera les methodes dinput et de display pour ce quon affiche et demande à l'utilisateur

    public static String ajouterCaractereGauche(char car, int longueur, String donnee) { // pour le formatage du listage
        String newCaractere = "";
        int nbCaracteres = longueur - donnee.length();
        for (int i = 0; i < nbCaracteres; i++) {
            newCaractere += car;
        }
        return newCaractere + donnee;
    }

    public static String ajouterEspacesFin(int tailleColonne, String donnee) { // same
        String donneeAvecEspaces;
        int nbEspaces = tailleColonne - donnee.length();
        donneeAvecEspaces = donnee;

        for (int i = 0; i < nbEspaces; i++) {
            donneeAvecEspaces += " ";
        }
        return donneeAvecEspaces;
        // or?-> return String.format("%-" + tailleColonne + "s", donnee);
    }

    public static JDialog createBasicDialog(String message, String titre, Boolean addOkButton) {
        JDialog dialog = new JDialog();
        dialog.setModal(true);
        dialog.setTitle(titre);
        dialog.setLayout(new BorderLayout());
        dialog.setSize(1000, 600);
        dialog.setLocationRelativeTo(null);

        // Panel pour l'image de fond
        JPanel backgroundPanel = new BackgroundPanel("/space.jpeg");
        backgroundPanel.setLayout(new GridBagLayout());

        // Configuration pour ajouter le message
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridwidth = GridBagConstraints.REMAINDER;
        gbc.anchor = GridBagConstraints.CENTER;

        // Ajout du message
        JLabel label = new JLabel(message, SwingConstants.CENTER);
        label.setForeground(Color.WHITE); // Assurez-vous que cette couleur fonctionne avec votre image de fond
        label.setFont(new Font("Arial", Font.PLAIN, 28));
        backgroundPanel.add(label, gbc);

        //ici ou pourrait ajouter le inputPanel pour les champs de texte dans inputMessage

        //ajoutons le ok (pt a enlever pour ajouter seulement dans les methodes qui en ont besoin?)
        if (addOkButton) {
            JPanel okButtonPanel = okButton(dialog);
            GridBagConstraints gbc2 = new GridBagConstraints();
            gbc2.gridx = 0;
            gbc2.gridy = 1;
            gbc2.anchor = GridBagConstraints.CENTER;
            backgroundPanel.add(okButtonPanel, gbc2);
        }


        dialog.add(backgroundPanel);

        return dialog;
    }

    public static String inputMessage(String message, String titre) {
        JDialog dialog = new JDialog();
        dialog.setModal(true);
        dialog.setTitle(titre);
        dialog.setLayout(new BorderLayout());
        dialog.setSize(1000, 600);
        dialog.setLocationRelativeTo(null);

        // Background image panel
        JPanel backgroundPanel = new BackgroundPanel("/space.jpeg");
        backgroundPanel.setLayout(new GridBagLayout());

        // Panel pour le message et le champ de texte
        JPanel inputPanel = new JPanel(new GridBagLayout());
        inputPanel.setOpaque(false);

        String[] messageLines = message.split("\n"); // Séparer les lignes de message

        // Ajouter des étiquettes pour chaque ligne de message
        for (int i = 0; i < messageLines.length; i++) {
            JLabel label = new JLabel(messageLines[i], SwingConstants.LEFT);
            label.setForeground(Color.WHITE);
            label.setFont(new Font("Arial", Font.PLAIN, 28)); // la police et la taille du texte
            label.setHorizontalAlignment(SwingConstants.LEFT); // alignement du texte
            GridBagConstraints gbcLabel = gridConstraintsForMenu(0, i, GridBagConstraints.NONE, 0, 1.0,
                    GridBagConstraints.CENTER);
            inputPanel.add(label, gbcLabel); //on ajoute la ligne au panel d'input
        }

        // Champ de texte pour l'entrée avec une taille préférée pour contrôler la largeur
        JTextField textField = new JTextField();
        textField.setPreferredSize(new Dimension(10, 24));
        GridBagConstraints gbcTextField = gridConstraintsForMenu(0, messageLines.length, GridBagConstraints.NONE, 0, 0,
                GridBagConstraints.CENTER);
        gbcTextField.ipadx = 60; // contrôle la largeur supplémentaire du champ
        gbcTextField.gridwidth = GridBagConstraints.REMAINDER; // fin de ligne
        inputPanel.add(textField, gbcTextField);

        // Button panel with OK button
        JPanel buttonPanel = okButton(dialog);
        GridBagConstraints gbcButtonPanel = gridConstraints(0, messageLines.length + 1, GridBagConstraints.HORIZONTAL,
                1.0, 0);
        inputPanel.add(buttonPanel, gbcButtonPanel);

        // Ajoute inputPanel au center of backgroundPanel
        GridBagConstraints gbcInputPanel = gridConstraints(0, 0, GridBagConstraints.BOTH, 1.0, 1.0);
        backgroundPanel.add(inputPanel, gbcInputPanel);

        dialog.add(backgroundPanel);
        dialog.setVisible(true);

        return textField.getText(); // Return l'input after the dialog is closed
    }

    public static String inputMessageWithDefaultInput(String message, String titre, String defaultValue) {
        // Appel de la méthode inputMessage originale pour obtenir la boîte de dialogue
        String userInput = inputMessage(message, titre);

        // Vérifier si l'input de l'utilisateur est vide et utiliser la valeur par défaut dans ce cas
        if (userInput.trim().isEmpty()) {
            return defaultValue;
        } else {
            return userInput;
        }
    }


    public static void message(String message, String titre) {
        JDialog dialog = createBasicDialog(message, "infos", true);

        dialog.setVisible(true);
    }

    public static void errorMessage(String message) {
        JDialog dialog = createBasicDialog(message, "Erreur", true);
        dialog.setVisible(true);
        System.out.println(message);
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
            errorMessage("Veuillez entrer un nombre entier valide.");
            // JOptionPane.showMessageDialog(null, "Veuillez entrer un nombre entier
            // valide.", "Erreur", JOptionPane.ERROR_MESSAGE);
            return inputInteger(message, title); // Rappeler la méthode pour demander à nouveau la saisie
        }
    }

    public static Date inputDate() {
        Date dateDepart = null;
        while (dateDepart == null) {
            String dateStr = inputMessageWithDefaultInput("Entrez la date (format jj/mm/yyyy) (enter pour default)", "Date", "01/01/2025");
            try {
                String[] parts = dateStr.split("/");
                int jour = Integer.parseInt(parts[0]);
                int mois = Integer.parseInt(parts[1]);
                int an = Integer.parseInt(parts[2]);
                dateDepart = new Date(); // Initialize dateDepart to avoid null pointer
                if (!dateDepart.initFromStringAndValidation(dateStr)) { // if date is valid
                    dateDepart = new Date(jour, mois, an); // Assign only if date is valid
                } else {
                    dateDepart = null; // Reset to null if date is invalid
                }
            } catch (Exception e) {
                errorMessage("Date invalide ou format incorrect. Veuillez réessayer.");
            }
        }
        return dateDepart;
    }


    public static boolean yesNoInputMessage(String message) {
        JDialog dialog = createBasicDialog(message, "Confirmation", false);

        // Panel pour les boutons
        JPanel buttonPanel = new JPanel(new FlowLayout());
        JButton yesButton = new JButton("Yes");
        JButton noButton = new JButton("No");
        buttonPanel.add(yesButton);
        buttonPanel.add(noButton);

        final boolean[] result = {false}; // Pour stocker le résultat du choix

        yesButton.addActionListener(e -> {
            result[0] = true;
            dialog.dispose();
        });

        noButton.addActionListener(e -> {
            result[0] = false;
            dialog.dispose();
        });

        dialog.add(buttonPanel, BorderLayout.SOUTH);
        dialog.setVisible(true);

        return result[0];
    }

    public static Map<String, Boolean> optionsRadioInputMessage(String opt1Txt, String opt2Txt, String opt3Txt, String opt4Txt) {
        JDialog dialog = createBasicDialog("Choisissez les options souhaitées:", "Options", false);

        // Panel pour les options, avec un BoxLayout pour aligner les composants verticalement
        JPanel optionsPanel = new JPanel();
        optionsPanel.setLayout(new BoxLayout(optionsPanel, BoxLayout.Y_AXIS));
        optionsPanel.setOpaque(false); // Rendre le panel transparent

        JCheckBox opt1 = new JCheckBox(opt1Txt);
        JCheckBox opt2 = new JCheckBox(opt2Txt);
        JCheckBox opt3 = new JCheckBox(opt3Txt);
        JCheckBox opt4 = new JCheckBox(opt4Txt);

        opt1.setOpaque(false); // Rendre les checkboxes transparents
        opt2.setOpaque(false);
        opt3.setOpaque(false);
        opt4.setOpaque(false);

        // Ajout des checkboxes au panel des options
        optionsPanel.add(opt1);
        optionsPanel.add(opt2);
        optionsPanel.add(opt3);
        optionsPanel.add(opt4);

        // Ajout du panel des options au centre du dialogue
        dialog.add(optionsPanel, BorderLayout.CENTER);

        // Panel pour le bouton au bas, avec FlowLayout pour centrer le bouton
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        buttonPanel.setOpaque(false); // Rendre le panel transparent
        JButton button = new JButton("Submit");
        buttonPanel.add(button);

        // Ajout du panel du bouton au sud du dialogue
        dialog.add(buttonPanel, BorderLayout.SOUTH);

        Map<String, Boolean> options = new HashMap<>();
        button.addActionListener(e -> {
            options.put("opt1", opt1.isSelected());
            options.put("opt2", opt2.isSelected());
            options.put("opt3", opt3.isSelected());
            options.put("opt4", opt4.isSelected());
            dialog.dispose();
        });

        dialog.pack();
        dialog.setLocationRelativeTo(null);
        dialog.setVisible(true);

        return options;
    }

    public static void displayVolsPanel(String resultat) {
        JDialog dialog = new JDialog();
        dialog.setTitle("Cie Air Relax");
        dialog.setModal(true);
        dialog.setLayout(new BorderLayout());
        dialog.setSize(1600, 1000);
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

        // les grids serviront de coordonnées pour placer les composants
        GridBagConstraints gbc = new GridBagConstraints(); // pour placer le scrollPane
        gbc.gridx = 0; // l'axe des x (colonne) (0,0) est en haut à gauche, 0 est au centre? À verifier
        gbc.gridy = 0; // l'axe des y
        gbc.fill = GridBagConstraints.BOTH; // pour remplir l'espace disponible

        textPanel.add(scrollPane, gbc);

        GridBagConstraints gbc2 = new GridBagConstraints(); // pour placer le textPanel
        gbc2.gridx = 0;
        gbc2.gridy = 0; // on place le textArea en haut à gauche (0,0)
        gbc2.anchor = GridBagConstraints.CENTER;
        gbc2.gridwidth = 1;
        backgroundPanel.add(textPanel, gbc2);

        JPanel okButtonPanel = okButton(dialog); // param = la fenêtre à fermer

        GridBagConstraints gbc3 = new GridBagConstraints(); // pour placer le bouton ok
        gbc3.gridx = 0;
        gbc3.gridy = 1; // on place le bouton en dessous du textArea (0,1)
        gbc3.anchor = GridBagConstraints.CENTER;
        backgroundPanel.add(okButtonPanel, gbc3);


        dialog.add(backgroundPanel);
        dialog.setVisible(true);

    }


    // **********INSIDE PANEL SETTING****************->


    // cancel button:
    public static JPanel okANDCancelButton(JDialog dialog) {
        JPanel okCancelButtonPanel = new JPanel();
        okCancelButtonPanel.setOpaque(false);

        JButton okButton = new JButton("OK");
        okButton.addActionListener(e -> dialog.dispose()); // ferme la fenêtre
        okCancelButtonPanel.add(okButton); // ajout du bouton ok au panel

        JButton cancelButton = new JButton("Cancel");
        cancelButton.addActionListener(e -> {
            dialog.dispose();
            try { // attention, menuGeneral() vient de la classe Compagnie!
                VueVol.menuGeneral();
            } catch (Exception ex) { // s'assurer que cette méthode ne bloque pas ou ne lance pas une nouvelle
                // instance de GUI
                throw new RuntimeException(ex);
            }
        });
        okCancelButtonPanel.add(cancelButton);
        okCancelButtonPanel.setOpaque(false);
        return okCancelButtonPanel;
    }

    public static JPanel okButton(JDialog dialog) { // pour un bouton de fermeture de fenêtre et de retour au menu
        // precedent a l'intérieur d'une fenêtre JDIALOG
        JPanel okButtonPanel = new JPanel();
        okButtonPanel.setOpaque(false);
        JButton okButton = new JButton("OK");
        okButton.addActionListener(e -> dialog.dispose()); // fermé la fenêtre
        // affilier enter au bouton:
        dialog.getRootPane().setDefaultButton(okButton);
        okButtonPanel.add(okButton);
        return okButtonPanel;
    }

    public static GridBagConstraints gridConstraints(int x, int y, int fill, double weightx, double weighty) {
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = x;
        gbc.gridy = y;
        gbc.fill = fill;
        gbc.weightx = weightx; // Controls how space is distributed among columns
        gbc.weighty = weighty; // "....." rows
        gbc.anchor = GridBagConstraints.NORTH; // Si tu veux que le composant soit centré dans l'espace qui lui est
        // alloué
        gbc.gridwidth = 1;
        return gbc;
    }

    public static GridBagConstraints gridConstraintsForMenu(int x, int y, int fill, double weightx, double weighty,
                                                            int anchor) {
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

    // --------------------------------------------------------------------------------
    // //
    // Classe interne pour le panneau de fond avec image
    static class BackgroundPanel extends JPanel { // pour la creation d'un panneau avec image de fond
        private Image backgroundImage;

        public BackgroundPanel(String pathToImage) { // constructeur
            try {
                backgroundImage = ImageIO.read(getClass().getResource(pathToImage));
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g); // dessine le panneau
            g.drawImage(backgroundImage, 0, 0, this.getWidth(), this.getHeight(), this); // dessine l'image

            // rendre le panneau moins opaque
            Graphics2D g2d = (Graphics2D) g.create();
            g2d.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.5f)); // Adjust transparency level as
            // needed
            g2d.setColor(Color.BLACK); // Or any other color that works with your text
            g2d.fillRect(0, 0, this.getWidth(), this.getHeight()); // Adjust the rectangle size as needed
            g2d.dispose();
        }
    }

}
