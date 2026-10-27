
package ui;

import dao.StudentDAO;
import model.Student;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableRowSorter;
import java.awt.*;
import java.sql.SQLException;
import java.util.List;

public class StudentManagementGUI extends JFrame {

    private final StudentDAO dao = new StudentDAO();

    private final Color navy = new Color(25, 45, 85);
    private final Color blue = new Color(49, 95, 210);
    private final Color green = new Color(22, 145, 100);
    private final Color red = new Color(210, 65, 75);
    private final Color background = new Color(246, 248, 252);
    private final Color muted = new Color(105, 116, 135);

    private final JTextField nameField = new JTextField(18);
    private final JTextField courseField = new JTextField(18);
    private final JTextField emailField = new JTextField(18);
    private final JTextField searchField = new JTextField(22);
    private final JComboBox<String> courseFilter =new JComboBox<>(new String[]{"All Courses"});

    private final JLabel totalLabel = new JLabel("0");
    private final JLabel courseTotalLabel = new JLabel("0");
    private final JLabel statusLabel = new JLabel("Ready");
    private final DefaultTableModel model =
            new DefaultTableModel(
                    new String[]{"ID", "Name", "Course", "Email"}, 0) {
                @Override
                public boolean isCellEditable(int row, int column) {
                    return false;
                }
            };

    private final JTable table = new JTable(model);
    private final TableRowSorter<DefaultTableModel> sorter =
            new TableRowSorter<>(model);

    public StudentManagementGUI() {
        setTitle("Student Management System");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(1120, 720);
        setMinimumSize(new Dimension(900, 600));
        setLocationRelativeTo(null);

        buildUI();
        loadStudents();
    }

    private void buildUI() {
        JPanel root = new JPanel(new BorderLayout(0, 0));
        root.setBackground(background);
        setContentPane(root);

        // Header
        JPanel header = new JPanel(new BorderLayout());
        header.setBackground(navy);
        header.setBorder(new EmptyBorder(24, 30, 24, 30));

        JPanel heading = new JPanel();
        heading.setLayout(new BoxLayout(heading, BoxLayout.Y_AXIS));
        heading.setOpaque(false);

        JLabel title = new JLabel("Student Management");
        title.setFont(new Font("SansSerif", Font.BOLD, 27));
        title.setForeground(Color.WHITE);

        JLabel subtitle = new JLabel(
                "Manage student records in one place");
        subtitle.setFont(new Font("SansSerif", Font.PLAIN, 14));
        subtitle.setForeground(new Color(205, 217, 240));

        heading.add(title);
        heading.add(Box.createVerticalStrut(7));
        heading.add(subtitle);

        JLabel badge = new JLabel("  STUDENT PORTAL  ");
        badge.setOpaque(true);
        badge.setBackground(new Color(48, 69, 113));
        badge.setForeground(Color.WHITE);
        badge.setFont(new Font("SansSerif", Font.BOLD, 12));

        header.add(heading, BorderLayout.WEST);
        header.add(badge, BorderLayout.EAST);
        root.add(header, BorderLayout.NORTH);

        // Main content
        JPanel content = new JPanel(new BorderLayout(18, 18));
        content.setBackground(background);
        content.setBorder(new EmptyBorder(22, 25, 18, 25));
        root.add(content, BorderLayout.CENTER);

        // Summary card
        JPanel summary = new JPanel(new BorderLayout(15, 0));
        summary.setBackground(Color.WHITE);
        summary.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(226, 231, 240)),
                new EmptyBorder(17, 20, 17, 20)));

        JPanel summaryText = new JPanel();
        summaryText.setLayout(new BoxLayout(summaryText, BoxLayout.Y_AXIS));
        summaryText.setOpaque(false);

        JLabel summaryTitle = new JLabel("TOTAL STUDENTS");
        summaryTitle.setFont(new Font("SansSerif", Font.BOLD, 12));
        summaryTitle.setForeground(muted);

        totalLabel.setFont(new Font("SansSerif", Font.BOLD, 30));
        totalLabel.setForeground(navy);

        summaryText.add(summaryTitle);
        summaryText.add(Box.createVerticalStrut(5));
        summaryText.add(totalLabel);

        JLabel summaryIcon = new JLabel("USERS");
        summaryIcon.setFont(new Font("SansSerif", Font.BOLD, 13));
        summaryIcon.setForeground(blue);

        summary.add(summaryText, BorderLayout.WEST);
        summary.add(summaryIcon, BorderLayout.EAST);
JPanel summaryCards = new JPanel(new GridLayout(1, 2, 14, 0));
summaryCards.setOpaque(false);
// Total courses card
JPanel courseSummary = new JPanel(new BorderLayout(10, 0));
courseSummary.setBackground(new Color(245, 248, 255));
courseSummary.setBorder(new EmptyBorder(16, 20, 16, 20));

JPanel courseSummaryText = new JPanel();
courseSummaryText.setOpaque(false);
courseSummaryText.setLayout(
        new BoxLayout(courseSummaryText, BoxLayout.Y_AXIS));

JLabel courseSummaryTitle = new JLabel("TOTAL COURSES");
courseSummaryTitle.setForeground(muted);
courseSummaryTitle.setFont(
        new Font("SansSerif", Font.BOLD, 12));

courseTotalLabel.setFont(new Font("SansSerif", Font.BOLD, 30));
courseTotalLabel.setForeground(navy);

courseSummaryText.add(courseSummaryTitle);
courseSummaryText.add(Box.createVerticalStrut(5));
courseSummaryText.add(courseTotalLabel);

JLabel courseSummaryIcon = new JLabel("COURSES");
courseSummaryIcon.setFont(new Font("SansSerif", Font.BOLD, 13));
courseSummaryIcon.setForeground(green);

courseSummary.add(courseSummaryText, BorderLayout.WEST);
courseSummary.add(courseSummaryIcon, BorderLayout.EAST);
summaryCards.add(summary);
summaryCards.add(courseSummary);

        // Search area and student table
        JPanel records = new JPanel(new BorderLayout(0, 14));
        records.setBackground(Color.WHITE);
        records.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(226, 231, 240)),
                new EmptyBorder(20, 20, 20, 20)));

        JPanel recordsHeader = new JPanel(new BorderLayout(12, 10));
        recordsHeader.setOpaque(false);

        JLabel recordsTitle = new JLabel("Student Records");
        recordsTitle.setFont(new Font("SansSerif", Font.BOLD, 19));
        recordsTitle.setForeground(navy);

        searchField.setFont(new Font("SansSerif", Font.PLAIN, 14));
        searchField.setToolTipText("Search name, course, or email");
        searchField.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(218, 225, 236)),
                new EmptyBorder(9, 10, 9, 10)));

       JPanel searchPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT, 8, 0));
       searchPanel.setOpaque(false);

        courseFilter.setFont(new Font("SansSerif", Font.PLAIN, 13));
        searchField.setPreferredSize(new Dimension(180, 36));

         searchPanel.add(new JLabel("Course:"));
         searchPanel.add(courseFilter);
         searchPanel.add(new JLabel("Search:"));
         searchPanel.add(searchField);

        recordsHeader.add(recordsTitle, BorderLayout.WEST);
        recordsHeader.add(searchPanel, BorderLayout.EAST);

        table.setRowHeight(36);
        table.setFont(new Font("SansSerif", Font.PLAIN, 13));
        table.setForeground(new Color(42, 52, 70));
        table.setBackground(Color.WHITE);
        table.setSelectionBackground(new Color(225, 235, 255));
        table.setSelectionForeground(navy);
        table.setShowVerticalLines(false);
        table.setGridColor(new Color(238, 241, 246));
        table.setIntercellSpacing(new Dimension(0, 1));
        table.setFillsViewportHeight(true);
        table.setRowSorter(sorter);

        table.getTableHeader().setFont(
                new Font("SansSerif", Font.BOLD, 13));
        table.getTableHeader().setBackground(
                new Color(242, 245, 250));
        table.getTableHeader().setForeground(navy);
        table.getTableHeader().setPreferredSize(new Dimension(0, 38));
        table.getTableHeader().setReorderingAllowed(false);

        table.getColumnModel().getColumn(0).setPreferredWidth(45);
        table.getColumnModel().getColumn(1).setPreferredWidth(150);
        table.getColumnModel().getColumn(2).setPreferredWidth(145);
        table.getColumnModel().getColumn(3).setPreferredWidth(210);

        JScrollPane scroll = new JScrollPane(table);
        scroll.setBorder(BorderFactory.createLineBorder(
                new Color(231, 235, 242)));
        scroll.getViewport().setBackground(Color.WHITE);

        records.add(recordsHeader, BorderLayout.NORTH);
        records.add(scroll, BorderLayout.CENTER);

        JPanel center = new JPanel(new BorderLayout(0, 18));
center.setOpaque(false);

center.add(summaryCards, BorderLayout.NORTH);
center.add(records, BorderLayout.CENTER);
        content.add(center, BorderLayout.CENTER);
        content.add(buildForm(), BorderLayout.WEST);

        // Footer
        statusLabel.setFont(new Font("SansSerif", Font.PLAIN, 12));
        statusLabel.setForeground(muted);
        statusLabel.setBorder(new EmptyBorder(0, 28, 15, 28));
        root.add(statusLabel, BorderLayout.SOUTH);

        // Search across all table columns
       
        // Search across all table columns
        searchField.getDocument().addDocumentListener(
                new javax.swing.event.DocumentListener() {
                    private void filter() {
                        applyFilters();
                    }

                    @Override
                    public void insertUpdate(
                            javax.swing.event.DocumentEvent e) {
                        filter();
                    }

                    @Override
                    public void removeUpdate(
                            javax.swing.event.DocumentEvent e) {
                        filter();
                    }

                    @Override
                    public void changedUpdate(
                            javax.swing.event.DocumentEvent e) {
                        filter();
                    }
                });

        courseFilter.addActionListener(e -> applyFilters());

    }

    private JPanel buildForm() {
        JPanel panel = new JPanel(new GridBagLayout());
        panel.setBackground(Color.WHITE);
        panel.setPreferredSize(new Dimension(285, 0));
        panel.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(226, 231, 240)),
                new EmptyBorder(20, 17, 20, 17)));

        GridBagConstraints c = new GridBagConstraints();
        c.gridx = 0;
        c.weightx = 1;
        c.fill = GridBagConstraints.HORIZONTAL;
        c.anchor = GridBagConstraints.WEST;
        c.insets = new Insets(0, 0, 7, 0);

        JLabel title = new JLabel("Student Details");
        title.setFont(new Font("SansSerif", Font.BOLD, 19));
        title.setForeground(navy);

        c.gridy = 0;
        panel.add(title, c);

        c.gridy++;
        c.insets = new Insets(5, 0, 16, 0);
        JLabel description = new JLabel("Add or edit a student");
        description.setForeground(muted);
        description.setFont(new Font("SansSerif", Font.PLAIN, 12));
        panel.add(description, c);

        addField(panel, c, "Full name", nameField);
        addField(panel, c, "Course", courseField);
        addField(panel, c, "Email address", emailField);

        c.insets = new Insets(15, 0, 8, 0);
        c.gridy++;
        JButton add = button("Add Student", blue);
        panel.add(add, c);

        c.gridy++;
        c.insets = new Insets(0, 0, 8, 0);
        JButton update = button("Update Selected", green);
        panel.add(update, c);

        c.gridy++;
        JButton delete = button("Delete Selected", red);
        panel.add(delete, c);

        c.gridy++;
        JButton clear = button("Clear Form", new Color(93, 106, 128));
        panel.add(clear, c);

        c.gridy++;
        JButton refresh = button("Refresh Records", navy);
        panel.add(refresh, c);

        c.gridy++;
        JButton export = button("Export CSV", new Color(22, 101, 52));
        panel.add(export, c); 
        c.gridy++;
        c.weighty = 1;
        panel.add(Box.createVerticalGlue(), c);

        add.addActionListener(e -> addStudent());
        update.addActionListener(e -> updateStudent());
        delete.addActionListener(e -> deleteStudent());
        clear.addActionListener(e -> clearForm());
        refresh.addActionListener(e -> loadStudents());
        export.addActionListener(e -> exportStudentsToCSV());

        return panel;
    }

    private void addField(
            JPanel panel, GridBagConstraints c,
            String label, JTextField field) {

        c.gridy++;
        c.insets = new Insets(5, 0, 7, 0);

        JLabel text = new JLabel(label);
        text.setForeground(new Color(67, 78, 98));
        text.setFont(new Font("SansSerif", Font.BOLD, 12));
        panel.add(text, c);

        c.gridy++;
        c.insets = new Insets(0, 0, 8, 0);
        field.setFont(new Font("SansSerif", Font.PLAIN, 13));
        field.setPreferredSize(new Dimension(220, 36));
        field.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(218, 225, 236)),
                new EmptyBorder(7, 9, 7, 9)));
        panel.add(field, c);
    }

    private JButton button(String text, Color color) {
        JButton button = new JButton(text);
        button.setFont(new Font("SansSerif", Font.BOLD, 12));
        button.setForeground(Color.WHITE);
        button.setBackground(color);
        button.setOpaque(true);
        button.setFocusPainted(false);
        button.setBorderPainted(false);
        button.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        button.setPreferredSize(new Dimension(225, 38));
        return button;
    }

    private void loadStudents() {
        try {
            List<Student> students = dao.getAllStudents();
            model.setRowCount(0);

            for (Student s : students) {
                model.addRow(new Object[]{
                        s.getId(), s.getName(), s.getCourse(),
                        s.getEmail() == null ? "" : s.getEmail()
                });
            }

            totalLabel.setText(String.valueOf(students.size()));
java.util.Set<String> uniqueCourses =
        new java.util.TreeSet<>(String.CASE_INSENSITIVE_ORDER);

for (Student student : students) {
    if (student.getCourse() != null
            && !student.getCourse().isBlank()) {
        uniqueCourses.add(student.getCourse().trim());
    }
}

courseTotalLabel.setText(String.valueOf(uniqueCourses.size()));
            statusLabel.setText(
                    "  Loaded " + students.size() + " student record(s)");
        } catch (SQLException ex) {
            showError(ex);
        }
}

    private void applyFilters() {
        java.util.List<RowFilter<DefaultTableModel, Integer>> filters =
                new java.util.ArrayList<>();

        String query = searchField.getText().trim();

        if (!query.isEmpty()) {
            filters.add(RowFilter.regexFilter(
                    "(?i)" + java.util.regex.Pattern.quote(query)));
        }

        Object selected = courseFilter.getSelectedItem();

        if (selected != null && !selected.equals("All Courses")) {
            filters.add(RowFilter.regexFilter(
                    "^" + java.util.regex.Pattern.quote(
                            selected.toString()) + "$", 2));
        }

        if (filters.isEmpty()) {
            sorter.setRowFilter(null);
        } else {
            sorter.setRowFilter(RowFilter.andFilter(filters));
        }
    }


  
private String[] readForm() {
    String name = nameField.getText().trim();
    String course = courseField.getText().trim();
    String email = emailField.getText().trim();

    // Check required fields
    if (name.isEmpty() || course.isEmpty() || email.isEmpty()) {
        message("Please fill in all fields.");
        return null;
    }

    // Check name length
    if (name.length() > 100) {
        message("Name must be 100 characters or fewer.");
        return null;
    }

    // Check course length
    if (course.length() > 100) {
        message("Course must be 100 characters or fewer.");
        return null;
    }

    // Check email length
    if (email.length() > 150) {
        message("Email must be 150 characters or fewer.");
        return null;
    }

    // Check email format
    if (!email.matches("^[^\\s@]+@[^\\s@]+\\.[^\\s@]+$")) {
        message("Please enter a valid email address.");
        return null;
    }

    return new String[]{name, course, email};
}

    private void addStudent() {
        String[] d = readForm();
        if (d == null) return;

        try {
            if (dao.addStudent(new Student(d[0], d[1], d[2]))) {
                message("Student added successfully.");
                loadStudents();
                clearForm();
            }
       } catch (SQLException ex) {
    if ("23000".equals(ex.getSQLState())
            && ex.getMessage() != null
            && ex.getMessage().toLowerCase().contains("email")) {
        message("This email address is already registered.");
    } else {
        showError(ex);
    }
}
    }

    private void updateStudent() {
        int row = table.getSelectedRow();
        if (row < 0) {
            message("Select a student from the table first.");
            return;
        }

        String[] d = readForm();
        if (d == null) return;

        int modelRow = table.convertRowIndexToModel(row);
        int id = (int) model.getValueAt(modelRow, 0);

        try {
            if (dao.updateStudent(
                    new Student(id, d[0], d[1], d[2]))) {
                message("Student updated successfully.");
                loadStudents();
                clearForm();
            } else {
                message("Student not found.");
            }
       } catch (SQLException ex) {
    if ("23000".equals(ex.getSQLState())
            && ex.getMessage() != null
            && ex.getMessage().toLowerCase().contains("email")) {
        message("This email address is already registered.");
    } else {
        showError(ex);
    }
}
    }

    private void deleteStudent() {
        int row = table.getSelectedRow();
        if (row < 0) {
            message("Select a student from the table first.");
            return;
        }

        int modelRow = table.convertRowIndexToModel(row);
        int id = (int) model.getValueAt(modelRow, 0);
        String name = String.valueOf(model.getValueAt(modelRow, 1));

        int confirm = JOptionPane.showConfirmDialog(
                this, "Delete " + name + " (ID " + id + ")?",
                "Confirm deletion",
                JOptionPane.YES_NO_OPTION,
                JOptionPane.WARNING_MESSAGE);

        if (confirm != JOptionPane.YES_OPTION) return;

        try {
            if (dao.deleteStudent(id)) {
                message("Student deleted successfully.");
                loadStudents();
                clearForm();
            } else {
                message("Student not found.");
            }
        } catch (SQLException ex) {
            showError(ex);
        }
    }

    private void fillForm() {
        int row = table.getSelectedRow();
        if (row < 0) return;

        int modelRow = table.convertRowIndexToModel(row);

        nameField.setText(String.valueOf(model.getValueAt(modelRow, 1)));
        courseField.setText(String.valueOf(model.getValueAt(modelRow, 2)));

        Object email = model.getValueAt(modelRow, 3);
        emailField.setText(email == null ? "" : email.toString());
    }

    private void clearForm() {
        table.clearSelection();
        nameField.setText("");
        courseField.setText("");
        emailField.setText("");
        statusLabel.setText("  Form cleared");
    }

    private void message(String text) {
        JOptionPane.showMessageDialog(this, text);
    }

    private void showError(SQLException ex) {
        JOptionPane.showMessageDialog(
                this,
                "Database operation failed.\n" + ex.getMessage(),
                "Database Error",
                JOptionPane.ERROR_MESSAGE);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            try {
                UIManager.setLookAndFeel(
                        UIManager.getSystemLookAndFeelClassName());
            } catch (Exception ignored) {
                // Use the default Swing appearance.
            }

            new StudentManagementGUI().setVisible(true);
        });
    }

private void exportStudentsToCSV() {
    javax.swing.JFileChooser chooser =
            new javax.swing.JFileChooser();

    chooser.setDialogTitle("Export Student Records");
    chooser.setSelectedFile(
            new java.io.File("students.csv"));

    int result = chooser.showSaveDialog(this);

    if (result != javax.swing.JFileChooser.APPROVE_OPTION) {
        return;
    }

    java.io.File file = chooser.getSelectedFile();

    if (!file.getName().toLowerCase().endsWith(".csv")) {
        file = new java.io.File(file.getAbsolutePath() + ".csv");
    }

    try (java.io.PrintWriter writer =
                 new java.io.PrintWriter(
                         new java.io.OutputStreamWriter(
                                 new java.io.FileOutputStream(file),
                                 java.nio.charset.StandardCharsets.UTF_8))) {

        writer.println("ID,Name,Course,Email");

        for (int row = 0; row < model.getRowCount(); row++) {
            for (int col = 0; col < model.getColumnCount(); col++) {
                if (col > 0) {
                    writer.print(",");
                }

                Object value = model.getValueAt(row, col);
                String text = value == null ? "" : value.toString();

                // Escape CSV values safely
                text = text.replace("\"", "\"\"");
                writer.print("\"" + text + "\"");
            }

            writer.println();
        }

        JOptionPane.showMessageDialog(
                this,
                "Student records exported successfully!\n"
                        + file.getAbsolutePath(),
                "Export Complete",
                JOptionPane.INFORMATION_MESSAGE);
} catch (java.io.IOException ex) {
    JOptionPane.showMessageDialog(
            this,
            "Could not export the file: " + ex.getMessage(),
            "Export Error",
            JOptionPane.ERROR_MESSAGE);
}


}
}
