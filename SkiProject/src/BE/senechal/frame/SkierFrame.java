package BE.senechal.frame;

import javax.swing.*;

import BE.senechal.DAO.SkierDAO;
import BE.senechal.ex1.Skier;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;

public class SkierFrame extends JFrame {
    private JTextField firstNameField;
    private JTextField lastNameField;
    private JButton addButton;
    private JButton listSkiersButton;
    private JTextArea skierListArea;
    private JButton homeButton;

    private SkierDAO skierDAO;

    public SkierFrame() {
        skierDAO = new SkierDAO();

        setTitle("Gestion des Skieurs");
        setSize(400, 300);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        JPanel inputPanel = new JPanel(new GridLayout(4, 2));
        inputPanel.add(new JLabel("Prénom :"));
        firstNameField = new JTextField();
        inputPanel.add(firstNameField);
        inputPanel.add(new JLabel("Nom :"));
        lastNameField = new JTextField();
        inputPanel.add(lastNameField);

        addButton = new JButton("Ajouter");
        inputPanel.add(addButton);

        listSkiersButton = new JButton("Liste des Skieurs");
        inputPanel.add(listSkiersButton);

        homeButton = new JButton("Accueil");
        inputPanel.add(homeButton);

        add(inputPanel, BorderLayout.NORTH);

        skierListArea = new JTextArea();
        skierListArea.setEditable(false);
        add(new JScrollPane(skierListArea), BorderLayout.CENTER);

        addButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                addSkier();
            }
        });

        listSkiersButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                displayAllSkiers();
            }
        });


        homeButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                navigateToHome(); 
            }
        });
    }

    private void addSkier() {
        String firstName = firstNameField.getText().trim();
        String lastName = lastNameField.getText().trim();

        if (firstName.isEmpty() || lastName.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Veuillez remplir tous les champs.", "Erreur", JOptionPane.ERROR_MESSAGE);
            return;
        }

        Skier skier = new Skier(0, firstName, lastName);
        try {
            skierDAO.addSkier(skier);
            JOptionPane.showMessageDialog(this, "Skieur ajouté avec succès !");
            firstNameField.setText("");
            lastNameField.setText("");
        } catch (SQLException ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(this, "Erreur lors de l'ajout du skieur : " + ex.getMessage(), "Erreur", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void displayAllSkiers() {
        try {
            java.util.List<Skier> skiers = skierDAO.getAllSkiers();
            skierListArea.setText("");
            for (Skier skier : skiers) {
                skierListArea.append(skier + "\n");
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(this, "Erreur lors de la récupération des skieurs : " + ex.getMessage(), "Erreur", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void navigateToHome() {
        this.setVisible(false);
        this.dispose();
        MainFrame mainFrame = new MainFrame();
        mainFrame.setVisible(true);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            new SkierFrame().setVisible(true);
        });
    }
}
