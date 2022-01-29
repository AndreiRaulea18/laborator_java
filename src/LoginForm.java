import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class LoginForm {

    public LoginForm(JFrame owner) {
        this.owner = owner;
        btnLogin.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.out.println("Am apasat butonul de login");
                try {
                    Application app = Application.getInstance();
                    app.login(new User(txtUsername.getText(), new String(txtPassword.getPassword())));
                    JOptionPane.showMessageDialog(null, "Login successfully!");
                    mainPanel.setVisible(false);
                    // cerinta 3: configurez meniu specific in functie de tipul de user (profesor/student)
                    if (app.currentUser.menuStrategy.getAccountType() == UserAccountType.STUDENT) {
                        StudentForm form = new StudentForm();
                        owner.setContentPane(form.getMainPanel());
                        form.populate();
                    } else {
                        TeacherForm form = new TeacherForm();
                        owner.setContentPane(form.getMainPanel());
                        form.populate();
                    }
                    // mareste si recentreaza forma
                    owner.setSize(1200, 600);
                    owner.setLocationRelativeTo(null);
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(null, ex.getMessage());
                }
            }
        });
        btnRegister.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JFrame frame = new JFrame("Register");
                RegisterForm registerForm = new RegisterForm(frame);
                frame.setContentPane(registerForm.getMainPanel());
                frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
                frame.setLocationRelativeTo(null);
                frame.pack();
                frame.setVisible(true);
            }
        });
    }

    public JPanel getMainPanel() {
        return mainPanel;
    }

    private JPanel mainPanel;
    private JLabel lblUsername;
    private JTextField txtUsername;
    private JPasswordField txtPassword;
    private JButton btnLogin;
    private JButton btnRegister;
    private JFrame owner;
}
