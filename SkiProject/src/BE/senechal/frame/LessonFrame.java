package BE.senechal.frame;

import javax.swing.*;
import BE.senechal.DAO.AccreditationDAO;
import BE.senechal.DAO.InstructorDAO;
import BE.senechal.DAO.LessonDAO;
import BE.senechal.DAO.LessonTypeDAO;
import BE.senechal.ex1.Instructor;
import BE.senechal.ex1.Lesson;
import BE.senechal.ex1.LessonType;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class LessonFrame extends JFrame {
    private JComboBox<String> lessonTypeComboBox;
    private JComboBox<String> instructorComboBox;
    private JSpinner minBookingSpinner;
    private JSpinner maxBookingSpinner;
    private JSpinner dateSpinner;
    private JCheckBox morningCheckBox;
    private JButton addButton;
    private JButton homeButton;
    private JLabel accreditationStatusLabel;

    private LessonTypeDAO lessonTypeDAO;
    private InstructorDAO instructorDAO;
    private AccreditationDAO accreditationDAO;
    private LessonDAO lessonDAO;

    private List<LessonType> lessonTypes;
    private List<Integer> instructorIds;

    public LessonFrame() {
        lessonTypeDAO = new LessonTypeDAO();
        instructorDAO = new InstructorDAO();
        accreditationDAO = new AccreditationDAO();
        lessonDAO = new LessonDAO();

        setTitle("Ajouter une Leçon");
        setSize(500, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        getContentPane().setLayout(new GridLayout(10, 2));

        lessonTypeComboBox = new JComboBox<>();
        instructorComboBox = new JComboBox<>();
        minBookingSpinner = new JSpinner(new SpinnerNumberModel(5, 1, 10, 1));
        maxBookingSpinner = new JSpinner(new SpinnerNumberModel(8, 1, 20, 1));
        morningCheckBox = new JCheckBox("Matin", true);
        accreditationStatusLabel = new JLabel("Statut des accréditations : Non vérifié");
        accreditationStatusLabel.setForeground(Color.BLACK);

        SpinnerDateModel dateModel = new SpinnerDateModel();
        dateModel.setCalendarField(Calendar.DATE);
        dateSpinner = new JSpinner(dateModel);

        addButton = new JButton("Ajouter");
        homeButton = new JButton("Accueil");

        fillLessonTypeComboBox();
        fillInstructorComboBox();

        getContentPane().add(new JLabel("Type de leçon"));
        getContentPane().add(lessonTypeComboBox);
        getContentPane().add(new JLabel("Instructeur"));
        getContentPane().add(instructorComboBox);
        getContentPane().add(new JLabel("Nombre minimum de participants"));
        getContentPane().add(minBookingSpinner);
        getContentPane().add(new JLabel("Nombre maximum de participants"));
        getContentPane().add(maxBookingSpinner);
        getContentPane().add(new JLabel("Date de début"));
        getContentPane().add(dateSpinner);
        getContentPane().add(new JLabel("Horaires"));
        getContentPane().add(morningCheckBox);
        getContentPane().add(new JLabel("Statut des accréditations"));
        getContentPane().add(accreditationStatusLabel);

        JPanel buttonPanel = new JPanel(new FlowLayout());
        buttonPanel.add(homeButton);
        buttonPanel.add(addButton);
        getContentPane().add(buttonPanel);

        addButton.addActionListener(e -> addLesson());
        homeButton.addActionListener(e -> navigateToHome());
        instructorComboBox.addActionListener(e -> checkInstructorAccreditation());
    }

    private void fillLessonTypeComboBox() {
        try {
            lessonTypes = lessonTypeDAO.getAllLessonTypes();
            for (LessonType lessonType : lessonTypes) {
                lessonTypeComboBox.addItem(lessonType.getName() + " " + lessonType.getCategory() + " " + lessonType.getSportType());
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    private void fillInstructorComboBox() {
        instructorIds = new ArrayList<>();
        try {
            List<Instructor> instructors = instructorDAO.getAllInstructors();
            for (Instructor instructor : instructors) {
                instructorComboBox.addItem(instructor.getFirstName() + " " + instructor.getLastName());
                instructorIds.add(instructor.getId());
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    private void checkInstructorAccreditation() {
        int selectedIndex = instructorComboBox.getSelectedIndex();
        if (selectedIndex == -1) return;

        try {
            int instructorId = instructorIds.get(selectedIndex);
            LessonType selectedLessonType = lessonTypes.get(lessonTypeComboBox.getSelectedIndex());
            boolean hasAccreditation = accreditationDAO.hasAccreditation(instructorId, selectedLessonType.getAccreditationId());

            if (hasAccreditation) {
                accreditationStatusLabel.setText("Instructeur qualifié pour ce cours.");
                accreditationStatusLabel.setForeground(Color.GREEN);
            } else {
                accreditationStatusLabel.setText("Instructeur non qualifié pour ce cours.");
                accreditationStatusLabel.setForeground(Color.RED);
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    private void addLesson() {
        try {
            int lessonTypeId = lessonTypes.get(lessonTypeComboBox.getSelectedIndex()).getId();
            int instructorId = instructorIds.get(instructorComboBox.getSelectedIndex());
            int minBooking = (int) minBookingSpinner.getValue();
            int maxBooking = (int) maxBookingSpinner.getValue();
            Date startDate = (Date) dateSpinner.getValue();
            boolean isMorning = morningCheckBox.isSelected();

            Lesson lesson = new Lesson(0, lessonTypeId, instructorId, minBooking, maxBooking, isMorning, startDate);
            lessonDAO.addLesson(lesson);

            JOptionPane.showMessageDialog(this, "Leçon ajoutée avec succès!");
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Erreur lors de l'ajout de la leçon : " + ex.getMessage(), "Erreur", JOptionPane.ERROR_MESSAGE);
            ex.printStackTrace();
        }
    }

    private void navigateToHome() {
        this.dispose();
        new MainFrame().setVisible(true);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new LessonFrame().setVisible(true));
    }
}
