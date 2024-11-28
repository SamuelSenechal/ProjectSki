package BE.senechal.frame;

import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

public class MainFrame extends JFrame {

    private static final long serialVersionUID = 1L;
    private JPanel contentPane;

    public static void main(String[] args) {
        EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    MainFrame frame = new MainFrame();
                    frame.setVisible(true);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    public MainFrame() {
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBounds(100, 100, 345, 399);
        contentPane = new JPanel();
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
        setContentPane(contentPane);
        contentPane.setLayout(null); 

        // Create the buttons
        JButton btnGoToInstructor = new JButton("Instructeur");
        btnGoToInstructor.setBounds(50, 50, 200, 30); 
        contentPane.add(btnGoToInstructor);

        JButton btnGoToAccreditation = new JButton("Ajouter une accreditation");
        btnGoToAccreditation.setBounds(50, 100, 200, 30);
        contentPane.add(btnGoToAccreditation);

        JButton btnGoToLesson = new JButton("Créer une date de lesson");
        btnGoToLesson.setBounds(50, 150, 200, 30); 
        contentPane.add(btnGoToLesson);

        JButton btnGoToSkier = new JButton("Créer un nouveau skieur");
        btnGoToSkier.setBounds(50, 200, 200, 30); 
        contentPane.add(btnGoToSkier);

        JButton btnGoToBooking = new JButton("Créer une nouvelle reservation");
        btnGoToBooking.setBounds(50, 250, 200, 30); 
        contentPane.add(btnGoToBooking);

        btnGoToInstructor.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                InstructorFrame instructorFrame = new InstructorFrame();
                instructorFrame.setVisible(true);
                dispose();
            }
        });

        btnGoToAccreditation.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                AccreditationFrame accreditationFrame = new AccreditationFrame();
                accreditationFrame.setVisible(true);
                dispose();
            }
        });

        btnGoToLesson.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                LessonFrame lessonFrame = new LessonFrame();
                lessonFrame.setVisible(true);
                dispose();
            }
        });

        btnGoToSkier.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                SkierFrame skierFrame = new SkierFrame();
                skierFrame.setVisible(true);
                dispose();
            }
        });

        btnGoToBooking.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                BookingFrame bookingFrame = new BookingFrame();
                bookingFrame.setVisible(true);
                dispose();
            }
        });
    }
}
