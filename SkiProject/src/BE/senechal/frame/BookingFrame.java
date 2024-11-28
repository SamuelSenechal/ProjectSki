package BE.senechal.frame;
import java.awt.*;
import java.awt.event.*;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.List;
import javax.swing.*;
import BE.senechal.DAO.*;
import BE.senechal.ski.*;
public class BookingFrame extends JFrame {
    private JComboBox<Skier> skierComboBox;
    private JComboBox<Lesson> lessonComboBox;
    private JCheckBox insuranceCheckBox;
    private JLabel priceLabel;
    private JButton bookButton;
    private JButton homeButton;

    private SkierDAO skierDAO;
    private LessonDAO lessonDAO;
    private LessonTypeDAO lessonTypeDAO;
    private BookingDAO bookingDAO;

    public BookingFrame() {
        skierDAO = new SkierDAO();
        lessonDAO = new LessonDAO();
        lessonTypeDAO = new LessonTypeDAO();
        bookingDAO = new BookingDAO();

        setTitle("Réservation");
        setSize(600, 337);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        getContentPane().setLayout(new BorderLayout());

        JPanel topPanel = new JPanel(new GridLayout(4, 2, 5, 5)); 
        topPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        topPanel.add(new JLabel("Skieur :"));
        skierComboBox = new JComboBox<>();
        topPanel.add(skierComboBox);

        topPanel.add(new JLabel("Leçon :"));
        lessonComboBox = new JComboBox<>();
        topPanel.add(lessonComboBox);

        insuranceCheckBox = new JCheckBox("Ajouter une assurance (+20 €)");
        topPanel.add(insuranceCheckBox);

        priceLabel = new JLabel("Prix total : 0 €");
        topPanel.add(priceLabel);

        getContentPane().add(topPanel, BorderLayout.CENTER);

        JPanel buttonPanel = new JPanel();
        bookButton = new JButton("Réserver");
        buttonPanel.add(bookButton);

        homeButton = new JButton("Accueil");
        buttonPanel.add(homeButton);
        getContentPane().add(buttonPanel, BorderLayout.SOUTH);

        loadSkiers();
        loadLessons();

        insuranceCheckBox.addActionListener(e -> updatePrice());
        lessonComboBox.addActionListener(e -> updatePrice());

        bookButton.addActionListener(e -> makeBooking());

        homeButton.addActionListener(e -> navigateToHome());
    }

    private void loadSkiers() {
        try {
            List<Skier> skiers = skierDAO.getAllSkiers();
            for (Skier skier : skiers) {
                skierComboBox.addItem(skier);
            }
            skierComboBox.setRenderer(new DefaultListCellRenderer() {
                @Override
                public Component getListCellRendererComponent(JList<?> list, Object value, int index, boolean isSelected, boolean cellHasFocus) {
                    Skier skier = (Skier) value;
                    String displayText = skier != null ? skier.getFirstName() + " " + skier.getLastName() : "";
                    return super.getListCellRendererComponent(list, displayText, index, isSelected, cellHasFocus);
                }
            });
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "Erreur lors du chargement des skieurs : " + e.getMessage(), "Erreur", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void loadLessons() {
        try {
            List<Lesson> lessons = lessonDAO.getAllLessons();
            for (Lesson lesson : lessons) {
                lessonComboBox.addItem(lesson);
            }
            lessonComboBox.setRenderer(new DefaultListCellRenderer() {
                @Override
                public Component getListCellRendererComponent(JList<?> list, Object value, int index, boolean isSelected, boolean cellHasFocus) {
                    Lesson lesson = (Lesson) value;
                    if (lesson != null) {
                        LessonType lessonType = lessonTypeDAO.getLessonTypeById(lesson.getLessonTypeId());
                        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
                        String startDate = dateFormat.format(lesson.getStartDate());
                        String endDate = dateFormat.format(new java.util.Date(lesson.getStartDate().getTime() + (6 * 24 * 60 * 60 * 1000)));
                        String timeOfDay = lesson.isMorning() ? "matin" : "après-midi";
                        String displayText = String.format("%s, %s, %s, %s - %s, %s",
                                lessonType.getSportType(),
                                lessonType.getCategory(),
                                lessonType.getName(),
                                startDate,
                                endDate,
                                timeOfDay);
                        return super.getListCellRendererComponent(list, displayText, index, isSelected, cellHasFocus);
                    }
                    return super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);
                }
            });
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Erreur lors du chargement des leçons : " + e.getMessage(), "Erreur", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void updatePrice() {
        Lesson selectedLesson = (Lesson) lessonComboBox.getSelectedItem();
        if (selectedLesson != null) {
            int lessontypeid = selectedLesson.getLessonTypeId();
            LessonType lessonType = lessonTypeDAO.getLessonTypeById(lessontypeid);

            double price = lessonType.getPrice();
            if (insuranceCheckBox.isSelected()) {
                price += 20;
            }
            priceLabel.setText("Prix total : " + price + " €");
        }
    }

    private void makeBooking() {
        Skier selectedSkier = (Skier) skierComboBox.getSelectedItem();
        Lesson selectedLesson = (Lesson) lessonComboBox.getSelectedItem();
        boolean insurance = insuranceCheckBox.isSelected();

        if (selectedSkier == null || selectedLesson == null) {
            JOptionPane.showMessageDialog(this, "Veuillez sélectionner un skieur et une leçon.", "Erreur", JOptionPane.ERROR_MESSAGE);
            return;
        }

        Booking booking = new Booking(selectedSkier, selectedLesson, insurance);

        try {
            bookingDAO.saveBooking(booking);
            JOptionPane.showMessageDialog(this, "Réservation effectuée avec succès !");
            updatePrice();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Erreur lors de la réservation : " + e.getMessage(), "Erreur", JOptionPane.ERROR_MESSAGE);
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
            new BookingFrame().setVisible(true);
        });
    }
}
