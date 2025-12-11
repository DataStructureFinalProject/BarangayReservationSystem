package barangayreservationsystem;

/**
 *
 * @author 900X3K (modified)
 */

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumnModel;
import java.awt.*;
import java.awt.event.*;
import java.io.*;

import java.util.List;
import java.util.ArrayList;
import java.util.Map;
import java.util.HashMap;
import java.util.Queue;
import java.util.LinkedList;
import java.util.Stack;
import java.util.Set;
import java.util.HashSet;

public class BarangayReservationSystem {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(LoginFrame::new);
    }
}

//LOGIN
class LoginFrame extends JFrame {

    private JTextField usernameField;
    private JPasswordField passwordField;
    private JButton loginButton;
    private JButton registerButton;

    private static final String FILE_PATH = "user.txt";

    public LoginFrame() {

        setTitle("Login");
        setExtendedState(JFrame.MAXIMIZED_BOTH);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // Use BorderLayout so we can center card and still have full-screen look
        setLayout(new BorderLayout());

        // SCREEN
        Dimension screen = Toolkit.getDefaultToolkit().getScreenSize();
        int cardWidth = (int) (screen.width * 0.80);
        int cardHeight = (int) (screen.height * 0.80);

        // FONTS
        Font labelFont = new Font("Arial", Font.BOLD, 28);
        Font fieldFont = new Font("Arial", Font.PLAIN, 24);
        Font buttonFont = new Font("Arial", Font.BOLD, 22);

        // FIELDS
        usernameField = new JTextField();
        passwordField = new JPasswordField();
        usernameField.setPreferredSize(new Dimension(420, 48));
        passwordField.setPreferredSize(new Dimension(420, 48));
        usernameField.setFont(fieldFont);
        passwordField.setFont(fieldFont);

        // BUTTONS
        loginButton = new JButton("LOGIN");
        registerButton = new JButton("SIGNUP");
        loginButton.setPreferredSize(new Dimension(200, 48));
        registerButton.setPreferredSize(new Dimension(200, 48));
        loginButton.setFont(buttonFont);
        registerButton.setFont(buttonFont);

        JLabel userLabel = new JLabel("Username:");
        JLabel passLabel = new JLabel("Password:");
        userLabel.setFont(labelFont);
        passLabel.setFont(labelFont);

        // -------------------------------
        // CARD PANEL (with big title INSIDE the card)
        // -------------------------------
        JPanel cardPanel = new JPanel();
        cardPanel.setLayout(new BoxLayout(cardPanel, BoxLayout.Y_AXIS));
        cardPanel.setPreferredSize(new Dimension(cardWidth, cardHeight));
        cardPanel.setBackground(new Color(245, 245, 245));
        cardPanel.setBorder(BorderFactory.createLineBorder(Color.GRAY, 5));

        // Big title inside card, centered
        JLabel titleLabel = new JLabel("Barangay Reservation System", SwingConstants.CENTER);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 80));
        titleLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        titleLabel.setBorder(BorderFactory.createEmptyBorder(30, 0, 30, 0));
        cardPanel.add(titleLabel);

        // Form panel (GridBag) added under the title
        JPanel formPanel = new JPanel(new GridBagLayout());
        formPanel.setOpaque(false); // allow card background to show
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(16, 16, 16, 16);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        gbc.gridx = 0; gbc.gridy = 0;
        formPanel.add(userLabel, gbc);

        gbc.gridx = 1; gbc.gridy = 0;
        formPanel.add(usernameField, gbc);

        gbc.gridx = 0; gbc.gridy = 1;
        formPanel.add(passLabel, gbc);

        gbc.gridx = 1; gbc.gridy = 1;
        formPanel.add(passwordField, gbc);

        gbc.gridx = 0; gbc.gridy = 2;
        formPanel.add(loginButton, gbc);

        gbc.gridx = 1; gbc.gridy = 2;
        formPanel.add(registerButton, gbc);

        cardPanel.add(formPanel);

        // Place cardPanel in center of frame inside a container so it's visually centered
        JPanel container = new JPanel(new GridBagLayout());
        container.setBackground(getBackground()); // same background as frame
        container.add(cardPanel);
        add(container, BorderLayout.CENTER);

        // Ensure user file exists
        UserUtil.ensureUserFileExists(FILE_PATH);

        // listeners
        loginButton.addActionListener(new LoginButtonListener());
        registerButton.addActionListener(new RegisterButtonListener());

        // show
        setVisible(true);
    }


    //LOGIN BUTTON
    private class LoginButtonListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {

            String username = usernameField.getText().trim();
            String password = new String(passwordField.getPassword());

            if (username.isEmpty() || password.isEmpty()) {
                JOptionPane.showMessageDialog(LoginFrame.this, "Enter username and password");
                return;
            }

            if (UserUtil.validateUser(username, password)) {
                JOptionPane.showMessageDialog(LoginFrame.this, "Login Successful!");
                dispose();
                // open HomePage (HomePage will use the singleton ReservationStore)
                new HomePage(username);
            } else {
                JOptionPane.showMessageDialog(LoginFrame.this, "Invalid Username or Password");
            }
        }
    }

    //REGISTER BUTTON 
    private class RegisterButtonListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            String username = usernameField.getText().trim();
            String password = new String(passwordField.getPassword());

            if (username.isEmpty() || password.isEmpty()) {
                JOptionPane.showMessageDialog(LoginFrame.this, "Enter username and password to register");
                return;
            }

            boolean ok = UserUtil.registerUser(username, password);
            if (ok) {
                JOptionPane.showMessageDialog(LoginFrame.this, "Registration Successful!");
            } else {
                JOptionPane.showMessageDialog(LoginFrame.this, "Registration failed (maybe user exists).");
            }
        }
    }

    private boolean validateLogin(String username, String password) {
        return UserUtil.validateUser(username, password);
    }

    private boolean registerUser(String username, String password) {
        return UserUtil.registerUser(username, password);
    }
}

//HomePage 
class HomePage extends JFrame {

    public HomePage(String username) {

        setTitle("Barangay Reservation System - Home");
        setExtendedState(JFrame.MAXIMIZED_BOTH);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        Dimension screen = Toolkit.getDefaultToolkit().getScreenSize();
        int cardWidth = (int) (screen.width * 0.80);   // 80% width
        int cardHeight = (int) (screen.height * 0.80); // 80% height

        JLabel welcomeLabel = new JLabel("Welcome, " + username + "!");
        welcomeLabel.setFont(new Font("Arial", Font.BOLD, 60));
        welcomeLabel.setHorizontalAlignment(SwingConstants.CENTER);

        JButton reserveBtn = new JButton("Make Reservation");
        JButton recordsBtn = new JButton("View Records");
        JButton logoutBtn = new JButton("Logout");

        Font buttonFont = new Font("Arial", Font.BOLD, 40);
        reserveBtn.setFont(buttonFont);
        recordsBtn.setFont(buttonFont);
        logoutBtn.setFont(buttonFont);

        reserveBtn.setPreferredSize(new Dimension(500, 120));
        recordsBtn.setPreferredSize(new Dimension(500, 120));
        logoutBtn.setPreferredSize(new Dimension(500, 120));

        JPanel cardPanel = new JPanel(new GridBagLayout());
        cardPanel.setPreferredSize(new Dimension(cardWidth, cardHeight));
        cardPanel.setBackground(new Color(245, 245, 245));
        cardPanel.setBorder(BorderFactory.createLineBorder(Color.GRAY, 6));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(40, 40, 40, 40);

        gbc.gridx = 0; gbc.gridy = 0;
        cardPanel.add(welcomeLabel, gbc);

        gbc.gridx = 0; gbc.gridy = 1;
        cardPanel.add(reserveBtn, gbc);

        gbc.gridx = 0; gbc.gridy = 2;
        cardPanel.add(recordsBtn, gbc);

        gbc.gridx = 0; gbc.gridy = 3;
        cardPanel.add(logoutBtn, gbc);

        JPanel container = new JPanel(new GridBagLayout());
        container.add(cardPanel);

        add(container);

        reserveBtn.addActionListener(e -> new ReservationFormDialog(this));
        recordsBtn.addActionListener(e -> new RecordsPage(this));

        logoutBtn.addActionListener(e -> {
            dispose();
            new LoginFrame();
        });

        setVisible(true);
    }
}

class Reservation {
    String id;
    String type;
    String name;
    String date;
    String time;
    String purpose;
    String returnDate; 
    String returnTime; 

    Reservation(String id, String type, String name, String date, String time, String purpose, String returnDate, String returnTime) {
        this.id = id;
        this.type = type;
        this.name = name;
        this.date = date;
        this.time = time;
        this.purpose = purpose;
        this.returnDate = returnDate;
        this.returnTime = returnTime;
    }

    Object[] toRow() {
        return new Object[]{id, type, name, date, time, purpose, returnDate, returnTime};
    }

    // serialize for file
    String toFileLine() {
        return esc(id) + ";" + esc(type) + ";" + esc(name) + ";" + esc(date) + ";" + esc(time) + ";" + esc(purpose) + ";" + esc(returnDate) + ";" + esc(returnTime);
    }

    static Reservation fromFileLine(String line) {
        
        String[] parts = line.split(";", -1); 
        
        String id = parts.length > 0 ? unesc(parts[0]) : "";
        String type = parts.length > 1 ? unesc(parts[1]) : "";
        String name = parts.length > 2 ? unesc(parts[2]) : "";
        String date = parts.length > 3 ? unesc(parts[3]) : "";
        String time = parts.length > 4 ? unesc(parts[4]) : "";
        String purpose = parts.length > 5 ? unesc(parts[5]) : "";
        String returnDate = parts.length > 6 ? unesc(parts[6]) : "";
        String returnTime = parts.length > 7 ? unesc(parts[7]) : "";
        return new Reservation(id, type, name, date, time, purpose, returnDate, returnTime);
    }

    private static String esc(String s) {
        if (s == null) return "";
        return s.replace(";", "&#59;");
    }

    private static String unesc(String s) {
        if (s == null) return "";
        return s.replace("&#59;", ";");
    }
}

class ReservationStore {
    // data structures
    final List<Reservation> list = new ArrayList<>();            // main list (ArrayList)
    final Map<String, Reservation> byId = new HashMap<>();       // map id -> reservation
    final Queue<Reservation> pending = new LinkedList<>();      // pending queue
    final Stack<Reservation> undoStack = new Stack<>();         // undo stack
    final Map<String, Integer> categoryCounts = new HashMap<>();// counts per type
    final Set<String> idSet = new HashSet<>();                  // unique ids

    private int counter = 1000;
    private boolean isSortedByName = false; 

    private static final String RES_FILE = "reservations.txt";

    private static ReservationStore INSTANCE = null;

    private ReservationStore() {
        loadFromFile();
    }

    public static synchronized ReservationStore getInstance() {
        if (INSTANCE == null) INSTANCE = new ReservationStore();
        return INSTANCE;
    }

    public synchronized String generateId() {
        String id;
        do {
            id = "R" + (counter++);
        } while (idSet.contains(id));
        idSet.add(id);
        return id;
    }

    public synchronized void addReservation(Reservation r) {
        list.add(r);
        byId.put(r.id, r);
        pending.offer(r);
        undoStack.push(r);
        idSet.add(r.id);
        categoryCounts.put(r.type, categoryCounts.getOrDefault(r.type, 0) + 1);
        isSortedByName = false;
        saveToFile();
    }

    public synchronized boolean removeReservation(String id) {
        Reservation r = byId.remove(id);
        if (r == null) return false;
        list.remove(r);
        pending.remove(r);
        idSet.remove(id);
       
        categoryCounts.put(r.type, Math.max(0, categoryCounts.getOrDefault(r.type, 1) - 1));
       
        if (!undoStack.isEmpty() && undoStack.peek().id.equals(id)) undoStack.pop();
        isSortedByName = false;
        saveToFile();
        return true;
    }

    public synchronized boolean undoLast() {
        if (undoStack.isEmpty()) return false;
        Reservation r = undoStack.pop();
        list.remove(r);
        byId.remove(r.id);
        pending.remove(r);
        idSet.remove(r.id);
        categoryCounts.put(r.type, Math.max(0, categoryCounts.getOrDefault(r.type, 1) - 1));
        isSortedByName = false;
        saveToFile();
        return true;
    }

    public synchronized void bubbleSort(String field) {
        int n = list.size();
        for (int i = 0; i < n - 1; i++) {
            boolean swapped = false;
            for (int j = 0; j < n - 1 - i; j++) {
                Reservation a = list.get(j);
                Reservation b = list.get(j + 1);
                int cmp = 0;
                if ("name".equalsIgnoreCase(field)) {
                    cmp = a.name.compareToIgnoreCase(b.name);
                } else if ("date".equalsIgnoreCase(field)) {
                    cmp = a.date.compareTo(b.date);
                }
                if (cmp > 0) {
                    list.set(j, b);
                    list.set(j + 1, a);
                    swapped = true;
                }
            }
            if (!swapped) break;
        }
        if ("name".equalsIgnoreCase(field)) isSortedByName = true;
        else isSortedByName = false;
        saveToFile();
    }

    public synchronized List<Reservation> snapshot() {
        return new ArrayList<>(list);
    }

    public synchronized Map<String, Integer> getCategoryCounts() {
        return new HashMap<>(categoryCounts);
    }

    private synchronized void loadFromFile() {
        list.clear();
        byId.clear();
        pending.clear();
        undoStack.clear();
        categoryCounts.clear();
        idSet.clear();
        counter = 1000;
        isSortedByName = false;

        File f = new File(RES_FILE);
        if (!f.exists()) return;
        try (BufferedReader br = new BufferedReader(new FileReader(f))) {
            String line;
            while ((line = br.readLine()) != null) {
                Reservation r = Reservation.fromFileLine(line);
                if (r != null) {
                    list.add(r);
                    byId.put(r.id, r);
                    idSet.add(r.id);
                    categoryCounts.put(r.type, categoryCounts.getOrDefault(r.type, 0) + 1);
                    
                    try {
                        if (r.id != null && r.id.startsWith("R")) {
                            int num = Integer.parseInt(r.id.substring(1));
                            if (num >= counter) counter = num + 1;
                        }
                    } catch (Exception ignored) {}
                }
            }
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    // Save list
    public synchronized void saveToFile() {
        File f = new File(RES_FILE);
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(f, false))) {
            for (Reservation r : list) {
                bw.write(r.toFileLine());
                bw.newLine();
            }
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }
}

class UserUtil {
    private static final String USER_FILE = "user.txt";
    private static final Map<String, String> users = new HashMap<>();

    static {
        loadUsers();
    }

    static void ensureUserFileExists(String filePath) {
        File f = new File(USER_FILE);
        if (!f.exists()) {
            // create default admin/admin
            users.put("admin", "admin");
            saveUsers();
        }
    }

    static synchronized void loadUsers() {
        users.clear();
        File f = new File(USER_FILE);
        if (!f.exists()) {
            // create default admin
            users.put("admin", "admin");
            saveUsers();
            return;
        }
        try (BufferedReader br = new BufferedReader(new FileReader(f))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] parts = line.split(";", 2);
                if (parts.length == 2) users.put(parts[0], parts[1]);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    static synchronized boolean validateUser(String user, String pass) {
        return users.containsKey(user) && users.get(user).equals(pass);
    }

    static synchronized boolean registerUser(String user, String pass) {
        if (users.containsKey(user)) return false;
        users.put(user, pass);
        saveUsers();
        return true;
    }

    static synchronized void saveUsers() {
        File f = new File(USER_FILE);
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(f, false))) {
            for (Map.Entry<String, String> e : users.entrySet()) {
                bw.write(e.getKey() + ";" + e.getValue());
                bw.newLine();
            }
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }
}

class ReservationFormDialog extends JDialog {

    private JComboBox<String> typeCombo;
    private JComboBox<String> detailsCombo;

    private JTextField nameField, dateField, timeField, purposeField;
    private JTextField returnDateField, returnTimeField; 

    private final ReservationStore store = ReservationStore.getInstance();

    public ReservationFormDialog(JFrame parent) {
        super(parent, "Make Reservation", true);

        //SCREEN SIZE
        Dimension screen = Toolkit.getDefaultToolkit().getScreenSize();
        int width = (int) (screen.width * 0.80);
        int height = (int) (screen.height * 0.80);
        setSize(width, height);
        setLocationRelativeTo(null);

        // FONTS 
        Font labelFont = new Font("Arial", Font.BOLD, 32);
        Font fieldFont = new Font("Arial", Font.PLAIN, 30);
        Font buttonFont = new Font("Arial", Font.BOLD, 32);

        typeCombo = new JComboBox<>(new String[]{"Covered Court", "Vehicle", "Others"});
        typeCombo.setFont(fieldFont);

        detailsCombo = new JComboBox<>();
        detailsCombo.setFont(fieldFont);
        detailsCombo.setVisible(false);

        nameField = new JTextField();
        dateField = new JTextField();
        timeField = new JTextField();
        purposeField = new JTextField();
        returnDateField = new JTextField();
        returnTimeField = new JTextField();

        nameField.setFont(fieldFont);
        dateField.setFont(fieldFont);
        timeField.setFont(fieldFont);
        purposeField.setFont(fieldFont);
        returnDateField.setFont(fieldFont);
        returnTimeField.setFont(fieldFont);

        //BUTTONS
        JButton submitBtn = new JButton("Submit Reservation");
        JButton cancelBtn = new JButton("Cancel");
        submitBtn.setFont(buttonFont);
        cancelBtn.setFont(buttonFont);

        submitBtn.setPreferredSize(new Dimension(420, 80));
        cancelBtn.setPreferredSize(new Dimension(300, 80));

        JPanel form = new JPanel(new GridBagLayout());
        form.setBorder(BorderFactory.createEmptyBorder(50, 50, 50, 50));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(20, 20, 20, 20);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        int row = 0;

        gbc.gridx = 0; gbc.gridy = row;
        JLabel typeLabel = new JLabel("Type:");
        typeLabel.setFont(labelFont);
        form.add(typeLabel, gbc);

        gbc.gridx = 1;
        form.add(typeCombo, gbc);

        gbc.gridx = 0; gbc.gridy = ++row;
        JLabel detailsLabel = new JLabel("Options:");
        detailsLabel.setFont(labelFont);
        form.add(detailsLabel, gbc);

        gbc.gridx = 1;
        form.add(detailsCombo, gbc);

        // NAME
        gbc.gridx = 0; gbc.gridy = ++row;
        JLabel nameLabel = new JLabel("Name:");
        nameLabel.setFont(labelFont);
        form.add(nameLabel, gbc);

        gbc.gridx = 1;
        form.add(nameField, gbc);

        // DATE
        gbc.gridx = 0; gbc.gridy = ++row;
        JLabel dateLabel = new JLabel("Date (YYYY-MM-DD):");
        dateLabel.setFont(labelFont);
        form.add(dateLabel, gbc);

        gbc.gridx = 1;
        form.add(dateField, gbc);

        // TIME
        gbc.gridx = 0; gbc.gridy = ++row;
        JLabel timeLabel = new JLabel("Time (HH:MM):");
        timeLabel.setFont(labelFont);
        form.add(timeLabel, gbc);

        gbc.gridx = 1;
        form.add(timeField, gbc);

        // PURPOSE
        gbc.gridx = 0; gbc.gridy = ++row;
        JLabel purposeLabel = new JLabel("Purpose:");
        purposeLabel.setFont(labelFont);
        form.add(purposeLabel, gbc);

        gbc.gridx = 1;
        form.add(purposeField, gbc);

        // RETURN DATE
        gbc.gridx = 0; gbc.gridy = ++row;
        JLabel retDateLabel = new JLabel("Return Date (YYYY-MM-DD):");
        retDateLabel.setFont(labelFont);
        form.add(retDateLabel, gbc);

        gbc.gridx = 1;
        form.add(returnDateField, gbc);

        // RETURN TIME
        gbc.gridx = 0; gbc.gridy = ++row;
        JLabel retTimeLabel = new JLabel("Return Time (HH:MM):");
        retTimeLabel.setFont(labelFont);
        form.add(retTimeLabel, gbc);

        gbc.gridx = 1;
        form.add(returnTimeField, gbc);

        // BUTTON PANEL
        JPanel btnPanel = new JPanel();
        btnPanel.setBorder(BorderFactory.createEmptyBorder(20,20,20,20));
        btnPanel.add(submitBtn);
        btnPanel.add(cancelBtn);

        setLayout(new BorderLayout());
        add(form, BorderLayout.CENTER);
        add(btnPanel, BorderLayout.SOUTH);

        typeCombo.addActionListener(e -> updateDetailsDropdown());

        updateDetailsDropdown();

        cancelBtn.addActionListener(e -> dispose());

        // create reservation 
        submitBtn.addActionListener(e -> {
            String type = (String) typeCombo.getSelectedItem();
            String detail = (detailsCombo.isVisible() && detailsCombo.getSelectedItem() != null)
                    ? (String) detailsCombo.getSelectedItem()
                    : null;
            if (detail != null && !detail.trim().isEmpty()) {
                type = type + " (" + detail + ")";
            }

            String name = nameField.getText().trim();
            String date = dateField.getText().trim();
            String time = timeField.getText().trim();
            String purpose = purposeField.getText().trim();
            String rDate = returnDateField.getText().trim();
            String rTime = returnTimeField.getText().trim();

            // basic validation
            if (name.isEmpty() || date.isEmpty() || time.isEmpty()) {
                JOptionPane.showMessageDialog(ReservationFormDialog.this, "Name, date, and time are required.");
                return;
            }

            // generate id and create reservation
            String id = store.generateId();
            Reservation r = new Reservation(id, type, name, date, time, purpose, rDate, rTime);
            store.addReservation(r);

            JOptionPane.showMessageDialog(ReservationFormDialog.this, "Reservation created. ID: " + id);
            dispose();
        });

        setVisible(true);
    }

    private void updateDetailsDropdown() {
        String selected = (String) typeCombo.getSelectedItem();

        detailsCombo.removeAllItems();

        if ("Vehicle".equals(selected)) {
            detailsCombo.addItem("Van");
            detailsCombo.addItem("Motorcycle");
            detailsCombo.addItem("BaoBao");
            detailsCombo.addItem("Bonggo");
            detailsCombo.setVisible(true);
        }
        else if ("Others".equals(selected)) {
            detailsCombo.addItem("Chairs");
            detailsCombo.addItem("Tables");
            detailsCombo.addItem("Sound System");
            detailsCombo.addItem("Sports Things");
            detailsCombo.setVisible(true);
        }
        else {
            detailsCombo.setVisible(false);
        }
    }
}

//  Records Page
class RecordsPage extends JFrame {

    private final ReservationStore store = ReservationStore.getInstance();
    private final DefaultTableModel model;
    private final JTable table;

    public RecordsPage(JFrame parent) {
        super("Reservation Records");

        // SCREEN 
        Dimension screen = Toolkit.getDefaultToolkit().getScreenSize();
        int width = (int) (screen.width * 0.80);
        int height = (int) (screen.height * 0.80);
        setSize(width, height);
        setLocationRelativeTo(parent);
        setLayout(new BorderLayout(20, 20));

        JPanel controls = new JPanel(new GridBagLayout());
        controls.setBorder(BorderFactory.createEmptyBorder(12, 12, 12, 12));
        GridBagConstraints c = new GridBagConstraints();
        c.insets = new Insets(10, 10, 10, 10);
        c.fill = GridBagConstraints.HORIZONTAL;

        // filter by type
        c.gridx = 0; c.gridy = 0; c.weightx = 0;
        JLabel filterLabel = new JLabel("Filter Type:");
        filterLabel.setFont(new Font("Arial", Font.PLAIN, 18));
        controls.add(filterLabel, c);
        JComboBox<String> typeFilter = new JComboBox<>(new String[] {"All", "Covered Court", "Vehicle", "Others"});
        typeFilter.setFont(new Font("Arial", Font.PLAIN, 18));
        c.gridx = 1; c.gridy = 0; c.weightx = 0.2;
        controls.add(typeFilter, c);

        // search
        c.gridx = 2; c.gridy = 0; c.weightx = 0;
        JLabel searchLabel = new JLabel("Search name:");
        searchLabel.setFont(new Font("Arial", Font.PLAIN, 18));
        controls.add(searchLabel, c);
        JTextField searchField = new JTextField();
        searchField.setFont(new Font("Arial", Font.PLAIN, 18));
        c.gridx = 3; c.gridy = 0; c.weightx = 0.4;
        controls.add(searchField, c);

        // Buttons row
        Font btnFont = new Font("Arial", Font.BOLD, 18);
        Dimension bigBtn = new Dimension(240, 56);

        JButton sortByNameBtn = new JButton("Sort by Name");
        sortByNameBtn.setFont(btnFont); sortByNameBtn.setPreferredSize(bigBtn);
        JButton sortByDateBtn = new JButton("Sort by Date");
        sortByDateBtn.setFont(btnFont); sortByDateBtn.setPreferredSize(bigBtn);

        JButton refreshBtn = new JButton("Refresh");
        refreshBtn.setFont(btnFont); refreshBtn.setPreferredSize(bigBtn);
        JButton viewDetailsBtn = new JButton("View Details");
        viewDetailsBtn.setFont(btnFont); viewDetailsBtn.setPreferredSize(bigBtn);
        JButton deleteBtn = new JButton("Delete Selected");
        deleteBtn.setFont(btnFont); deleteBtn.setPreferredSize(bigBtn);
        JButton undoBtn = new JButton("Undo Last");
        undoBtn.setFont(btnFont); undoBtn.setPreferredSize(bigBtn);
        JButton countsBtn = new JButton("Category Counts");
        countsBtn.setFont(btnFont); countsBtn.setPreferredSize(bigBtn);

        // ⚠️ CSV BUTTON REMOVED HERE

        JPanel btnPanel = new JPanel(new GridLayout(2, 4, 12, 12));

        btnPanel.add(sortByNameBtn);
        btnPanel.add(sortByDateBtn);
        btnPanel.add(refreshBtn);
        btnPanel.add(viewDetailsBtn);
        btnPanel.add(deleteBtn);
        btnPanel.add(undoBtn);
        btnPanel.add(countsBtn);
        // ⚠️ exportBtn removed — NO LONGER ADDED

        c.gridx = 0; c.gridy = 1; c.gridwidth = 4; c.weightx = 1;
        controls.add(btnPanel, c);

        add(controls, BorderLayout.NORTH);

        // TABLE 
        String[] cols = {"ID", "Type", "Name", "Date", "Time"};
        model = new DefaultTableModel(cols, 0) {
            @Override public boolean isCellEditable(int r, int c) { return false; }
        };
        table = new JTable(model);

        table.setFont(new Font("Arial", Font.PLAIN, 20));
        table.setRowHeight(42);
        table.setFillsViewportHeight(true);

        table.getTableHeader().setFont(new Font("Arial", Font.BOLD, 18));
        table.getTableHeader().setPreferredSize(new Dimension(table.getTableHeader().getWidth(), 38));

        JScrollPane scroll = new JScrollPane(table);
        scroll.setBorder(BorderFactory.createEmptyBorder(10, 12, 12, 12));
        add(scroll, BorderLayout.CENTER);

        refreshTable(store.snapshot());

        TableColumnModel colModel = table.getColumnModel();
        if (colModel.getColumnCount() >= 8) {
            colModel.getColumn(0).setPreferredWidth(120);
            colModel.getColumn(1).setPreferredWidth(260);
            colModel.getColumn(2).setPreferredWidth(260);
            colModel.getColumn(3).setPreferredWidth(140);
            colModel.getColumn(4).setPreferredWidth(110);
            colModel.getColumn(5).setPreferredWidth(320);
            colModel.getColumn(6).setPreferredWidth(140);
            colModel.getColumn(7).setPreferredWidth(120);
        }

        // filter by type
        typeFilter.addActionListener(e -> {
            String sel = (String) typeFilter.getSelectedItem();
            List<Reservation> s = store.snapshot();
            if (!"All".equals(sel)) {
                List<Reservation> filtered = new ArrayList<>();
                for (Reservation r : s) {
                    if (sel.equals(r.type) || r.type.startsWith(sel + " ")) filtered.add(r);
                }
                refreshTable(filtered);
            } else {
                refreshTable(s);
            }
        });

        // refresh
        refreshBtn.addActionListener(e -> {
            typeFilter.setSelectedIndex(0);
            searchField.setText("");
            refreshTable(store.snapshot());
        });

        // search
        searchField.addKeyListener(new KeyAdapter() {
            @Override
            public void keyReleased(KeyEvent e) {
                String q = searchField.getText().trim().toLowerCase();
                String filterType = (String) typeFilter.getSelectedItem();

                List<Reservation> all = store.snapshot();
                List<Reservation> filtered = new ArrayList<>();

                for (Reservation r : all) {
                    boolean matchType = filterType.equals("All") || r.type.equals(filterType) || r.type.startsWith(filterType + " ");
                    boolean matchName = r.name != null && r.name.toLowerCase().contains(q);

                    if (matchType && matchName) filtered.add(r);
                }

                refreshTable(filtered);
            }
        });

        // sort by name
        sortByNameBtn.addActionListener(e -> {
            store.bubbleSort("name");
            refreshTable(store.snapshot());
            JOptionPane.showMessageDialog(this, "Sorted by name.");
        });

        // sort by date
        sortByDateBtn.addActionListener(e -> {
            store.bubbleSort("date");
            refreshTable(store.snapshot());
            JOptionPane.showMessageDialog(this, "Sorted by date.");
        });

        // view details
        viewDetailsBtn.addActionListener(e -> {
            int row = table.getSelectedRow();
            if (row == -1) { JOptionPane.showMessageDialog(this, "Select a row to view details."); return; }
            String id = (String) model.getValueAt(row, 0);
            Reservation r = store.byId.get(id);
            if (r == null) { JOptionPane.showMessageDialog(this, "Reservation not found."); return; }

            StringBuilder sb = new StringBuilder();
            sb.append("ID: ").append(r.id).append("\n");
            sb.append("Type: ").append(r.type).append("\n");
            sb.append("Name: ").append(r.name).append("\n");
            sb.append("Date: ").append(r.date).append("\n");
            sb.append("Time: ").append(r.time).append("\n");
            sb.append("Purpose: ").append(r.purpose).append("\n");
            sb.append("Return Date: ").append(r.returnDate == null ? "" : r.returnDate).append("\n");
            sb.append("Return Time: ").append(r.returnTime == null ? "" : r.returnTime).append("\n");

            JTextArea ta = new JTextArea(sb.toString());
            ta.setFont(new Font("Arial", Font.PLAIN, 18));
            ta.setEditable(false);

            JOptionPane.showMessageDialog(this, new JScrollPane(ta), "Reservation Details", JOptionPane.INFORMATION_MESSAGE);
        });

        // delete
        deleteBtn.addActionListener(e -> {
            int row = table.getSelectedRow();
            if (row == -1) { JOptionPane.showMessageDialog(this, "Select a row to delete."); return; }
            String id = (String) model.getValueAt(row, 0);
            int ans = JOptionPane.showConfirmDialog(this, "Delete reservation " + id + "?", "Confirm", JOptionPane.YES_NO_OPTION);
            if (ans == JOptionPane.YES_OPTION) {
                boolean ok = store.removeReservation(id);
                JOptionPane.showMessageDialog(this, ok ? "Deleted." : "Delete failed.");
                refreshTable(store.snapshot());
            }
        });

        // undo
        undoBtn.addActionListener(e -> {
            boolean ok = store.undoLast();
            JOptionPane.showMessageDialog(this, ok ? "Undo successful." : "Nothing to undo.");
            refreshTable(store.snapshot());
        });

        // category counts
        countsBtn.addActionListener(e -> {
            Map<String, Integer> map = store.getCategoryCounts();
            StringBuilder sb = new StringBuilder();
            if (map.isEmpty()) sb.append("No reservations.");
            else map.forEach((k, v) -> sb.append(k).append(": ").append(v).append("\n"));
            JOptionPane.showMessageDialog(this, sb.toString(), "Category Counts", JOptionPane.INFORMATION_MESSAGE);
        });

        setVisible(true);
    }

    private void refreshTable(List<Reservation> list) {
        model.setRowCount(0);
        for (Reservation r : list) {
            model.addRow(new Object[]{
                r.id, r.type, r.name, r.date, r.time, r.purpose,
                r.returnDate, r.returnTime
            });
        }
    }
}

