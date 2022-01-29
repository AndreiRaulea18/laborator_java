import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class RegisterForm {
    private JPanel mainPanel;
    private JLabel lblUsername;
    private JTextField txtUsername;
    private JPasswordField txtPassword;
    private JButton btnRegister;
    private JTextField txtName;
    private JTextField txtSurname;
    private JFrame owner;

    public RegisterForm(JFrame owner) {
        this.owner = owner;
        btnRegister.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Application app = Application.getInstance();
                // inregistreaza utlizator
                String username = txtUsername.getText();
                String password = txtPassword.getText();
                String nume = txtName.getText();
                String prenume = txtSurname.getText();
                User currentUser = app.currentUser;
                // cerinta 5: verifica ca userul nu este intrgistrat deja
                if (app.userExists(username)) {
                    JOptionPane.showMessageDialog(null, "Utilizator inregistrat!");
                } else {
                    // [1] cauta userul dupa nume + prenume in lista de studenti
                    User user;
                    Student student = app.getStudent(nume, prenume);
                    if (student != null) {
                        user = new User(username, password, new StudentStrategy(student));
                    } else {
                        // [2] cauta userul dupa nume + prenume in lista de profesori
                        Profesor profesor = app.getProfesor(nume, prenume);
                        if (profesor != null) {
                            user = new User(username, password, new TeacherStrategy(profesor));
                        } else {
                            JOptionPane.showMessageDialog(null, "Student sau profesor neinregistrat!");
                           return;
                        }
                    }
                    // adauga userul nou
                    app.saveUser(user);
                    // sterge campurile pentru o noua inregistrare
                    txtUsername.setText("");
                    txtPassword.setText("");
                    txtName.setText("");
                    txtSurname.setText("");
                    JOptionPane.showMessageDialog(null, "Utilizator nou salvat!");
                }
            }
        });
    }

    public JPanel getMainPanel() {
        return mainPanel;
    }
}
