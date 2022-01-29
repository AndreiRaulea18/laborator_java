import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

public class StudentForm {
    private JTable table;
    private JPanel mainPanel;
    private JLabel status;

    public Container getMainPanel() {
        return mainPanel;
    }

    public void populate() {
        // arata userul curent
        Application app = Application.getInstance();
        Student student = ((StudentStrategy) app.currentUser.menuStrategy).student;
        status.setText("Student: " + student.nume + " " + student.prenume);
        // populeaza cursuri
        String[] cols = new String[]{"Nume", "Descriere", "Profesor", "Nr.Studenti"};
        List<Curs> cursuri = app.managerCursuri.cursuri;
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

}
