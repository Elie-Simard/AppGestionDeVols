package org.example;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.*;
import java.io.*;


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
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));

        JTextArea textArea = new JTextArea();
        textArea.setFont(new Font(Font.MONOSPACED, Font.PLAIN, 12));
        textArea.setEditable(false);

        JPanel buttonPanel = new JPanel();
        buttonPanel.add(returnToMenuButton());

        textArea.setText(resultat);
        panel.add(textArea);
        panel.add(buttonPanel);
        JOptionPane.showMessageDialog(null, panel, "AirLine Co", JOptionPane.PLAIN_MESSAGE);
    }






}


