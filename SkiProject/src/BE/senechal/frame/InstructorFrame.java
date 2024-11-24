package BE.senechal.frame;

import java.awt.*;
import java.awt.event.*;
import java.util.List;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;

import BE.senechal.DAO.InstructorDAO;
import BE.senechal.ski.Instructor;

public class InstructorFrame extends JFrame {
    
    private JTable instructorTable;
    private DefaultTableModel tableModel;
    private JTextField firstNameField;
    private JTextField lastNameField;
    private InstructorDAO instructorDAO;

    private static final long serialVersionUID = 1L;
    private JPanel contentPane;
    private JButton homeButton; 

    /**
     * Launch the application.
     */
    public static void main(String[] args) {
        EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    InstructorFrame frame = new InstructorFrame();
                    frame.setVisible(true);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }
    
    public InstructorFrame() {
        instructorDAO = new InstructorDAO();
        setTitle("Gestion des Instructeurs");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(600, 400);
        setLayout(new BorderLayout());
        tableModel = new DefaultTableModel(new Object[]{"ID", "Prénom", "Nom"}, 0);
        instructorTable = new JTable(tableModel);
        JScrollPane scrollPane = new JScrollPane(instructorTable);
        add(scrollPane, BorderLayout.CENTER);

        JPanel formPanel = new JPanel(new GridLayout(3, 2, 10, 10));
        formPanel.setBorder(BorderFactory.createTitledBorder("Ajouter un Instructeur"));

        formPanel.add(new JLabel("Prénom:"));
        firstNameField = new JTextField();
        formPanel.add(firstNameField);

        formPanel.add(new JLabel("Nom:"));
        lastNameField = new JTextField();
        formPanel.add(lastNameField);

        JButton addButton = new JButton("Ajouter");
        formPanel.add(addButton);

        JPanel buttonPanel = new JPanel();
        homeButton = new JButton("Accueil"); 
        buttonPanel.add(homeButton); 
        buttonPanel.add(addButton);
        formPanel.add(buttonPanel);

        add(formPanel, BorderLayout.SOUTH);

        loadInstructors();

        addButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                addInstructor();
            }
        });
        
        homeButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                navigateToHome();
            }
        });

        setVisible(true);
    }

    private void loadInstructors() {
        tableModel.setRowCount(0); 
        List<Instructor> instructors = instructorDAO.getAllInstructors();
        for (Instructor instructor : instructors) {
            tableModel.addRow(new Object[]{instructor.getId(), instructor.getFirstName(), instructor.getLastName()});
        }
    }

    private void addInstructor() {
        String firstName = firstNameField.getText();
        String lastName = lastNameField.getText();

        if (firstName.isEmpty() || lastName.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Veuillez remplir tous les champs.", "Erreur", JOptionPane.ERROR_MESSAGE);
            return;
        }

        Instructor newInstructor = new Instructor(0, firstName, lastName, null);
        boolean success = instructorDAO.addInstructor(newInstructor);

        if (success) {
            JOptionPane.showMessageDialog(this, "Instructeur ajouté avec succès.", "Succès", JOptionPane.INFORMATION_MESSAGE);
            loadInstructors(); 
            firstNameField.setText("");
            lastNameField.setText("");
        } else {
            JOptionPane.showMessageDialog(this, "Erreur lors de l'ajout de l'instructeur.", "Erreur", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void navigateToHome() {
        this.setVisible(false);
        this.dispose();       
        MainFrame mainFrame = new MainFrame();
        mainFrame.setVisible(true);
    }
}
