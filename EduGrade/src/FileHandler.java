/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

/**
 *
 * @author sluig
 */
import java.io.*;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JOptionPane; 

public class FileHandler {
    public static void saveStudents(List<Student> students) {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream("students.dat"))) {
            oos.writeObject(students);
            JOptionPane.showMessageDialog(null, "Students saved successfully.");
        } catch (IOException e) {
            JOptionPane.showMessageDialog(null, "Error saving students: " + e.getMessage());
        }
    }

    public static ArrayList<Student> loadStudents() {
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream("students.dat"))) {
            return (ArrayList<Student>) ois.readObject();
        } catch (IOException | ClassNotFoundException e) {
            JOptionPane.showMessageDialog(null, "Error loading students: " + e.getMessage());
        }
        return new ArrayList<>();
    }
}
