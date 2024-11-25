package BE.senechal.frame;

import javax.swing.*;

import BE.senechal.DAO.AccreditationDAO;
import BE.senechal.DAO.InstructorAccreditationDAO;
import BE.senechal.DAO.InstructorDAO;
import BE.senechal.ex1.Accreditation;
import BE.senechal.ex1.Instructor;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class AccreditationFrame extends JFrame {

    private JList<String> instructorList;
    private JList<String> accreditationList;
    private DefaultListModel<String> instructorModel;
    private DefaultListModel<String> accreditationModel;

    private InstructorDAO instructorDAO = new InstructorDAO();
    private AccreditationDAO accreditationDAO = new AccreditationDAO();
    private InstructorAccreditationDAO instructorAccreditationDAO = new InstructorAccreditationDAO();

    private Set<Integer> currentInstructorAccreditations = new HashSet<>();

    public AccreditationFrame() {
        setTitle("Gestion des Accréditations");
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        getContentPane().setLayout(new BorderLayout());

        instructorModel = new DefaultListModel<>();
        instructorList = new JList<>(instructorModel);
        JScrollPane instructorScrollPane = new JScrollPane(instructorList);

        List<Instructor> instructors = instructorDAO.getAllInstructors();
        for (Instructor instructor : instructors) {
            instructorModel.addElement(instructor.getFirstName() + " " + instructor.getLastName());
        }
        accreditationModel = new DefaultListModel<>();
        accreditationList = new JList<>(accreditationModel);
        JScrollPane accreditationScrollPane = new JScrollPane(accreditationList);


        List<Accreditation> accreditations = accreditationDAO.getAllAccreditations();
        for (Accreditation accreditation : accreditations) {
            accreditationModel.addElement(accreditation.getName());
        }

        accreditationList.setCellRenderer(new DefaultListCellRenderer() {
            @Override
            public Component getListCellRendererComponent(JList<?> list, Object value, int index, boolean isSelected, boolean cellHasFocus) {
                Component component = super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);

                if (currentInstructorAccreditations.contains(index + 1)) { 
                    setForeground(Color.GREEN); 
                } else {
                    setForeground(Color.BLACK);
                }

                return component;
            }
        });

        // Buttons for actions
        JButton addButton = new JButton("Ajouter");
        JButton removeButton = new JButton("Supprimer");
        JPanel buttonPanel = new JPanel();
        
        JButton btnHome = new JButton("Accueil");
        buttonPanel.add(btnHome);
        buttonPanel.add(addButton);
        buttonPanel.add(removeButton);

        getContentPane().add(instructorScrollPane, BorderLayout.WEST);
        getContentPane().add(accreditationScrollPane, BorderLayout.CENTER);
        getContentPane().add(buttonPanel, BorderLayout.SOUTH);

        instructorList.addListSelectionListener(e -> {
            int instructorIndex = instructorList.getSelectedIndex();
            if (instructorIndex != -1) {
                Instructor selectedInstructor = instructors.get(instructorIndex);
                currentInstructorAccreditations.clear();
                currentInstructorAccreditations.addAll(instructorAccreditationDAO.getInstructorAccreditations(selectedInstructor.getId()));
                accreditationList.repaint();
            }
        });

        addButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int instructorIndex = instructorList.getSelectedIndex();
                int accreditationIndex = accreditationList.getSelectedIndex();

                if (instructorIndex != -1 && accreditationIndex != -1) {
                    Instructor selectedInstructor = instructors.get(instructorIndex);
                    Accreditation selectedAccreditation = accreditations.get(accreditationIndex);

                    instructorAccreditationDAO.addAccreditationToInstructor(
                            selectedInstructor.getId(),
                            selectedAccreditation.getId()
                    );

                    currentInstructorAccreditations.add(selectedAccreditation.getId());
                    accreditationList.repaint(); // Update colors
                    JOptionPane.showMessageDialog(null, "Accréditation ajoutée !");
                } else {
                    JOptionPane.showMessageDialog(null, "Veuillez sélectionner un instructeur et une accréditation.");
                }
            }
        });

        removeButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int instructorIndex = instructorList.getSelectedIndex();
                int accreditationIndex = accreditationList.getSelectedIndex();

                if (instructorIndex != -1 && accreditationIndex != -1) {
                    Instructor selectedInstructor = instructors.get(instructorIndex);
                    Accreditation selectedAccreditation = accreditations.get(accreditationIndex);

                    instructorAccreditationDAO.removeAccreditationFromInstructor(
                            selectedInstructor.getId(),
                            selectedAccreditation.getId()
                    );

                    currentInstructorAccreditations.remove(selectedAccreditation.getId());
                    accreditationList.repaint();
                    JOptionPane.showMessageDialog(null, "Accréditation supprimée !");
                } else {
                    JOptionPane.showMessageDialog(null, "Veuillez sélectionner un instructeur et une accréditation.");
                }
            }
        });
        btnHome.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                MainFrame mainFrame = new MainFrame();
                mainFrame.setVisible(true);
                dispose();
            }
        });
    }
    
    

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            AccreditationFrame frame = new AccreditationFrame();
            frame.setVisible(true);
        });
    }
}
