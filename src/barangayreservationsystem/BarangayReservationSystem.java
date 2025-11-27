/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package barangayreservationsystem;

/**
 *
 * @author 900X3K
 */

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.*;

public class BarangayReservationSystem {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(LoginFrame::new);
    }
}

// LOGIN
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

        //SCREEN
        Dimension screen = Toolkit.getDefaultToolkit().getScreenSize();
        int cardWidth = (int) (screen.width * 0.80);
        int cardHeight = (int) (screen.height * 0.80);

        // FONTS
        Font labelFont = new Font("Arial", Font.BOLD, 35);
        Font fieldFont = new Font("Arial", Font.PLAIN, 32);
        Font buttonFont = new Font("Arial", Font.BOLD, 30);

        // FIELDS
        usernameField = new JTextField();
        passwordField = new JPasswordField();
        usernameField.setPreferredSize(new Dimension(450, 60));
        passwordField.setPreferredSize(new Dimension(450, 60));
        usernameField.setFont(fieldFont);
        passwordField.setFont(fieldFont);

        // BUTTONS
        loginButton = new JButton("LOGIN");
        registerButton = new JButton("SIGNUP");
        loginButton.setPreferredSize(new Dimension(230, 65));
        registerButton.setPreferredSize(new Dimension(230, 65));
        loginButton.setFont(buttonFont);
        registerButton.setFont(buttonFont);

        JLabel userLabel = new JLabel("Username:");
        JLabel passLabel = new JLabel("Password:");
        userLabel.setFont(labelFont);
        passLabel.setFont(labelFont);

        // CARD
        JPanel cardPanel = new JPanel(new GridBagLayout());
        cardPanel.setPreferredSize(new Dimension(cardWidth, cardHeight));
        cardPanel.setBackground(new Color(245, 245, 245));
        cardPanel.setBorder(BorderFactory.createLineBorder(Color.GRAY, 5));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(25, 25, 25, 25);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        gbc.gridx = 0; gbc.gridy = 0;
        cardPanel.add(userLabel, gbc);

        gbc.gridx = 1; gbc.gridy = 0;
        cardPanel.add(usernameField, gbc);

        gbc.gridx = 0; gbc.gridy = 1;
        cardPanel.add(passLabel, gbc);

        gbc.gridx = 1; gbc.gridy = 1;
        cardPanel.add(passwordField, gbc);

        gbc.gridx = 0; gbc.gridy = 2;
        cardPanel.add(loginButton, gbc);

        gbc.gridx = 1; gbc.gridy = 2;
        cardPanel.add(registerButton, gbc);

        JPanel container = new JPanel(new GridBagLayout());
        container.add(cardPanel);
        add(container);

        loginButton.addActionListener(new LoginButtonListener());
        registerButton.addActionListener(new RegisterButtonListener());

        setVisible(true);
    }

    //LOGIN BUTTON
    private class LoginButtonListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {

            String username = usernameField.getText();
            String password = new String(passwordField.getPassword());

            if (validateLogin(username, password)) {
                JOptionPane.showMessageDialog(LoginFrame.this, "Login Successful!");
                dispose();
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
            String username = usernameField.getText();
            String password = new String(passwordField.getPassword());

            if (registerUser(username, password)) {
                JOptionPane.showMessageDialog(LoginFrame.this, "Registration Successful!");
            } else {
                JOptionPane.showMessageDialog(LoginFrame.this, "Error: Could not register.");
            }
        }
    }

    private boolean validateLogin(String username, String password) {
        try (BufferedReader br = new BufferedReader(new FileReader(FILE_PATH))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] parts = line.split(";");
                if (parts.length == 2 && parts[0].equals(username) && parts[1].equals(password)) {
                    return true;
                }
            }
        } catch (IOException e) {
            System.out.println("No user file found. Register first.");
        }
        return false;
    }

    private boolean registerUser(String username, String password) {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(FILE_PATH, true))) {
            bw.write(username + ";" + password);
            bw.newLine();
            return true;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return false;
    }
}


//HOME PAGE
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

        logoutBtn.addActionListener(e -> {
            dispose();
            new LoginFrame();
        });

        setVisible(true);
    }
}
