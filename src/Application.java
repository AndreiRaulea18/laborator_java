import org.w3c.dom.css.Counter;

import java.beans.ExceptionListener;
import java.beans.XMLDecoder;
import java.beans.XMLEncoder;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.*;

public class Application {
    private static Application single_instance = null;
    private List<User> userList = new ArrayList<>();

    public User currentUser = null;
    // adauga
    public ManagerCursuri managerCursuri = new ManagerCursuri(Arrays.asList(Settings.dataLoader.createCoursesData()));
    public List<Student> students = Arrays.asList(Settings.dataLoader.createStudentsData());
    public List<Profesor> profesors = Arrays.asList(Settings.dataLoader.createProfesorData());

    static Application getInstance() {
        if ( single_instance == null) {
            single_instance = new Application();
        }
        return  single_instance;
    }

    private Application() {
         /* HardcodatDataManager dataManager = new HardcodatDataManager();
        Random r = new Random();
        var studenti = dataManager.dataSetOfStudent;
        var profesori = dataManager.dataSetOfProfesor;
        this.userList.add(new User("aaa", "aaa", new StudentStrategy( studenti[r.nextInt(studenti.length)])));
        this.userList.add(new User("bbb", "aaa", new TeacherStrategy( profesori[r.nextInt(profesori.length)])));
        this.userList.add(new User("ccc", "ccc", new StudentStrategy( studenti[r.nextInt(studenti.length)])));
        this.userList.add(new User("ddd", "ddd", new TeacherStrategy( profesori[r.nextInt(profesori.length)])));
        this.userList.add(new User("eee", "eee", new StudentStrategy( studenti[r.nextInt(studenti.length)])));
        try {
            FileOutputStream fos = new FileOutputStream("users.xml");
            XMLEncoder encoder = new XMLEncoder(fos);
            encoder.setExceptionListener(new ExceptionListener() {
                @Override
                public void exceptionThrown(Exception e) {
                    System.out.println("Exception:" + e.toString());
                }
            });
            encoder.writeObject(userList);
            encoder.close();
            fos.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } */
        this.initUsers();
    }

    private void initUsers() {
        try (FileInputStream fis = new FileInputStream("users.xml")) {
            XMLDecoder decoder = new XMLDecoder(fis);
            this.userList = (ArrayList<User>)decoder.readObject();
            decoder.close();
            fis.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void login(User user) throws Exception {
        int index = userList.indexOf(user);
        if ( index != -1 ) {
            Application.getInstance().currentUser = userList.get(index);
        } else {
            throw new Exception("Username sau parola este gresita!");
        }
    }

    // verifica daca exista userul inregistrat cu username
    public boolean userExists(String username) {
        for (User user: userList) {
            if (username.equals(user.userName)) {
                return true;
            }
        }
        return false;
    }

    // cauta profesor dupa nume si prenume
    public Profesor getProfesor(String nume, String prenume) {
        for (Profesor profesor: profesors) {
            if (profesor.nume.equals(nume) && profesor.prenume.equals(prenume)) {
                return profesor;
            }
        }
        return null;
    }

    // cauta studentul dupa nume si prenume
    public Student getStudent(String nume, String prenume) {
        for (Student student: students) {
            if (student.nume.equals(nume) && student.prenume.equals(prenume)) {
                return student;
            }
        }
        return null;
    }

    public void saveUser(User user) {
        // salveaza doar daca nu exista
        if (!userExists(user.userName)) {
            userList.add(user);
            try(FileOutputStream fis = new FileOutputStream("users.xml")) {
                XMLEncoder encoder = new XMLEncoder(fis);
                encoder.writeObject(this.userList);
                encoder.close();
                fis.close();
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

}
