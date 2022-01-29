import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

public class TeacherForm {

    public TeacherForm() {
        btnStudents.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                showStudents();
            }
        });
        btnCourses.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                showCourses();
            }
        });
    }

    private void showCourses() {
        // populeaza cursuri
        String[] cols = new String[]{"Nume", "Descriere", "Profesor", "Nr.Studenti"};
        List<Curs> cursuri = Application.getInstance().managerCursuri.cursuri;
        Object[][] data = new Object[cursuri.size()][cols.length];
        int index = 0;
        for (Curs curs: cursuri) {
            data[index][0] = curs.nume;
            data[index][1] = curs.descriere;
            data[index][2] = curs.profu.nume + " " + curs.profu.prenume;
            data[index][3] = curs.studenti.size();
            index++;
        }
        DefaultTableModel model = new DefaultTableModel(data, cols);
        table.setModel(model);
    }

    private void showStudents() {
        // populeaza studenti
        String[] cols = new String[]{"Nume", "Prenume", "Grupa"};
        List<Student> students = Application.getInstance().students;
        Object[][] data = new Object[students.size()][cols.length];
        int index = 0;
        for (Student student: students) {
            data[index][0] = student.nume;
            data[index][1] = student.prenume;
            data[index][2] = student.grupa;
            index++;
        }
        DefaultTableModel model = new DefaultTableModel(data, cols);
        table.setModel(model);
    }

    public JPanel getMainPanel() {
        return mainPanel;
    }

    public void populate() {
        // arata userul curent
        Profesor profesor = ((TeacherStrategy) Application.getInstance().currentUser.menuStrategy).profesor;
        status.setText("Profesor: " + profesor.nume + " " + profesor.prenume);
        // arata cursuri
        showCourses();
    }

    private JPanel mainPanel;
    private JButton btnStudents;
    private JButton btnCourses;
    private JTable table;
    private JLabel status;
}
