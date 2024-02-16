package org.example;

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
        JOptionPane.showMessageDialog(null, new JLabel(message) {
            @Override
            public void paintComponent(Graphics g) {
                Image image = null;
                try {
                    image = ImageIO.read(getClass().getResource("/space.jpeg"));
                } catch (IOException e) {
                    e.printStackTrace();
                }
                g.drawImage(image, 0, 0, this.getWidth(), this.getHeight(), null);
                super.paintComponent(g);
            }
        }, "Erreur", JOptionPane.ERROR_MESSAGE);
    }

    public static String inputMessage(String message, String titre) {
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


    public static void displayVolsPanel(String resultat) {
        JDialog dialog = new JDialog(); // Jdialog permet de personnaliser la fenêtre, comparé à JOptionPane
        dialog.setTitle("Cie Air Relax");
        dialog.setModal(true); // Pour bloquer les autres fenêtres tant que celle-ci est ouverte
        dialog.setLayout(new BorderLayout()); // BorderLayout pour l'ajustabilité
        dialog.setSize(1300, 600);
        dialog.setLocationRelativeTo(null); // Centrer la fenêtre

        JPanel backgroundPanel = new JPanel() { // Panneau pour le fond d'écran
            Image image = new ImageIcon(this.getClass().getResource("/space.jpeg")).getImage();

            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                g.drawImage(image, 0, 0, getWidth(), getHeight(), this); // Ajuste l'image pour qu'elle remplisse le JPanel
            } //on peint l'image dans le panneau
        };
        backgroundPanel.setLayout(new GridBagLayout());

        // Panneau pour le JTextArea -> pour la liste, on doit d'abord créer un panneau pour le texte, un texteArea pour le contenu,
        // puis un JScrollPane pour le panneau (pour pouvoir scroll) ->
        JPanel textPanel = new JPanel(new GridBagLayout());
        textPanel.setOpaque(false); // Rendre le panneau transparent puisque il ne sert qu'à afficher le texte qui lui n'est pas transparent

        // JTextArea pour le contenu
        JTextArea textArea = new JTextArea(resultat); //on met le resultat dans le JTextArea
        textArea.setFont(new Font(Font.MONOSPACED, Font.PLAIN, 12));
        textArea.setEditable(false);
        textArea.setBackground(Color.BLACK);
        textArea.setForeground(Color.WHITE);

        // JScrollPane pour la JTextArea
        JScrollPane scrollPane = new JScrollPane(textArea);
        scrollPane.setOpaque(false);
        scrollPane.getViewport().setOpaque(false); //


        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.weightx = 1.0;
        gbc.weighty = 1.0;
        gbc.fill = GridBagConstraints.BOTH;
        // Ajouter le JScrollPane au textPanel et le gridBagLayout pour le positionner
        textPanel.add(scrollPane, gbc);

        // Ajouter le textPanel au backgroundPanel
        GridBagConstraints gbc2 = new GridBagConstraints();
        gbc2.gridx = 0;
        gbc2.gridy = 0;
        gbc2.weightx = 1.0;
        gbc2.weighty = 1.0;
        gbc2.anchor = GridBagConstraints.CENTER;
        backgroundPanel.add(textPanel, gbc2);

        // Panneau pour le bouton OK
        JPanel okButtonPanel = new JPanel();
        okButtonPanel.setOpaque(false);
        JButton okButton = new JButton("OK");
        okButton.addActionListener(e -> {
            // Fermer la fenêtre
            SwingUtilities.getWindowAncestor(okButton).dispose();

        });
        okButtonPanel.add(okButton);

        // Ajouter le okButtonPanel au backgroundPanel
        GridBagConstraints gbc3 = new GridBagConstraints();
        gbc3.gridx = 0;
        gbc3.gridy = 1;
        gbc3.anchor = GridBagConstraints.CENTER;
        backgroundPanel.add(okButtonPanel, gbc3);

        dialog.add(backgroundPanel);
        dialog.setVisible(true);
    }


}



