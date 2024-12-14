/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */

/**
 *
 * @author sluig
 */
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
public class MainFrame extends javax.swing.JFrame {
    private ArrayList<Student> students;
    private JTable studentTable;
    private StudentTableModel tableModel;

    public MainFrame() {
        super("EduGrade - Education meets Grading, Made Simple");
        students = new ArrayList<>();
        tableModel = new StudentTableModel(students);

        setLayout(new BorderLayout());

        JMenuBar menuBar = new JMenuBar();
        JMenu fileMenu = new JMenu("File");
        JMenuItem saveItem = new JMenuItem("Save");
        JMenuItem loadItem = new JMenuItem("Load");
        fileMenu.add(saveItem);
        fileMenu.add(loadItem);
        menuBar.add(fileMenu);

        JMenu helpMenu = new JMenu("Help");
        JMenuItem aboutItem = new JMenuItem("About");
        helpMenu.add(aboutItem);
        menuBar.add(helpMenu);

        setJMenuBar(menuBar);

        studentTable = new JTable(tableModel);
        JScrollPane scrollPane = new JScrollPane(studentTable);

        JPanel buttonPanel = new JPanel();
        JButton addStudentButton = new JButton("Add Student");
        JButton removeStudentButton = new JButton("Remove Student");
        JButton manageSubjectsButton = new JButton("Manage Subjects");
        buttonPanel.add(addStudentButton);
        buttonPanel.add(removeStudentButton);
        buttonPanel.add(manageSubjectsButton);

        add(scrollPane, BorderLayout.CENTER);
        add(buttonPanel, BorderLayout.SOUTH);

        addStudentButton.addActionListener(e -> addStudent());
        removeStudentButton.addActionListener(e -> removeStudent());
        manageSubjectsButton.addActionListener(e -> manageSubjects());

        saveItem.addActionListener(e -> FileHandler.saveStudents(students));
        loadItem.addActionListener(e -> {
            students = FileHandler.loadStudents();
            tableModel.setStudents(students);
        });

        aboutItem.addActionListener(e -> JOptionPane.showMessageDialog(this, "EduGrade v1.3\nNow with double grades and precise GWA!"));

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(800, 600);
        setVisible(true);
    }

    private void addStudent() {
        String name = JOptionPane.showInputDialog(this, "Enter student name:");
        if (name != null && !name.trim().isEmpty()) {
            students.add(new Student(name));
            tableModel.fireTableDataChanged();
        }
    }

    private void removeStudent() {
        int selectedRow = studentTable.getSelectedRow();
        if (selectedRow != -1) {
            students.remove(selectedRow);
            tableModel.fireTableDataChanged();
        } else {
            JOptionPane.showMessageDialog(this, "Please select a student to remove.");
        }
    }

    private void manageSubjects() {
        int selectedRow = studentTable.getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(this, "Please select a student to manage subjects.");
            return;
        }

        Student selectedStudent = students.get(selectedRow);

        String[] options = {"Add Subject", "View Subjects and GWA", "Remove Subject"};
        int choice = JOptionPane.showOptionDialog(
            this,
            "Manage subjects for: " + selectedStudent.getName(),
            "Subject Management",
            JOptionPane.DEFAULT_OPTION,
            JOptionPane.PLAIN_MESSAGE,
            null,
            options,
            options[0]
        );

        switch (choice) {
            case 0 -> addSubject(selectedStudent);
            case 1 -> viewSubjectsAndGWA(selectedStudent);
            case 2 -> removeSubject(selectedStudent);
        }
    }

    private void addSubject(Student student) {
        String subjectName = JOptionPane.showInputDialog(this, "Enter subject name:");
        if (subjectName == null || subjectName.trim().isEmpty()) return;

        String gradeInput = JOptionPane.showInputDialog(this, "Enter grade (1.0 - 5.0) for " + subjectName + ":");
        try {
            double grade = Double.parseDouble(gradeInput);
            if (grade >= 0.0 && grade <= 100.0) {
                student.addSubject(subjectName, grade);
                JOptionPane.showMessageDialog(this, "Subject and grade added successfully.");
            } else {
                JOptionPane.showMessageDialog(this, "Invalid grade. Enter a value between 0.0 and 100.0.");
            }
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Invalid input. Please enter a decimal number.");
        }
    }

    private void viewSubjectsAndGWA(Student student) {
        StringBuilder info = new StringBuilder("Subjects and Grades for " + student.getName() + ":\n");

        ArrayList<Subject> subjects = student.getSubjects();
        for (Subject subject : subjects) {
            info.append(subject.getName()).append(": ").append(String.format("%.2f", subject.getGrade())).append("\n");
        }

        info.append("\nGWA: ").append(String.format("%.2f", student.calculateGWA()));

        JOptionPane.showMessageDialog(this, info.toString());
    }

    private void removeSubject(Student student) {
        ArrayList<Subject> subjects = student.getSubjects();
        if (subjects.isEmpty()) {
            JOptionPane.showMessageDialog(this, "No subjects to remove.");
            return;
        }

        StringBuilder subjectList = new StringBuilder("Select a subject to remove:\n");
        for (int i = 0; i < subjects.size(); i++) {
            subjectList.append(i + 1).append(". ").append(subjects.get(i).getName()).append("\n");
        }

        String subjectIndexInput = JOptionPane.showInputDialog(this, subjectList.toString());
        try {
            int subjectIndex = Integer.parseInt(subjectIndexInput) - 1;
            student.removeSubject(subjectIndex);
            JOptionPane.showMessageDialog(this, "Subject removed successfully.");
        } catch (NumberFormatException | IndexOutOfBoundsException e) {
            JOptionPane.showMessageDialog(this, "Invalid input. Please enter a valid subject number.");
        }
    }
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 400, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 300, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        new MainFrame();
        
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    // End of variables declaration//GEN-END:variables
}
