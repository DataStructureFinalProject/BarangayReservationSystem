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

public class BarangayReservationSystem {

    private static CardLayout cardLayout;
    private static JPanel mainPanel;

    public static void main(String[] args) {

        JFrame frame = new JFrame("Login / Sign Up App");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        frame.setSize(350, 250);      // NORMAL window size
        frame.setResizable(false);    // prevent huge resizing
        frame.setLocationRelativeTo(null);

        cardLayout = new CardLayout();
        mainPanel = new JPanel(cardLayout);

        mainPanel.add(loginPanel(), "login");
        mainPanel.add(signUpPanel(), "signup");
        mainPanel.add(homePanel(), "home");

        frame.add(mainPanel);
        frame.setVisible(true);
    }

    // ---------------- LOGIN PANEL ----------------
    private static JPanel loginPanel() {
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));

        JLabel title = new JLabel("Log In", SwingConstants.CENTER);
        title.setAlignmentX(Component.CENTER_ALIGNMENT);

        JTextField user = new JTextField(12);   // small size
        JPasswordField pass = new JPasswordField(12);

        JButton loginBtn = new JButton("Login");
        loginBtn.setAlignmentX(Component.CENTER_ALIGNMENT);

        JButton goSignUp = new JButton("Go to Sign Up");
        goSignUp.setAlignmentX(Component.CENTER_ALIGNMENT);

        panel.add(Box.createVerticalStrut(10));
        panel.add(title);
        panel.add(Box.createVerticalStrut(10));
        panel.add(user);
        panel.add(Box.createVerticalStrut(10));
        panel.add(pass);
        panel.add(Box.createVerticalStrut(15));
        panel.add(loginBtn);
        panel.add(Box.createVerticalStrut(5));
        panel.add(goSignUp);

        loginBtn.addActionListener(e -> cardLayout.show(mainPanel, "home"));
        goSignUp.addActionListener(e -> cardLayout.show(mainPanel, "signup"));

        return panel;
    }

    // ---------------- SIGN UP PANEL ----------------
    private static JPanel signUpPanel() {
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));

        JLabel title = new JLabel("Sign Up", SwingConstants.CENTER);
        title.setAlignmentX(Component.CENTER_ALIGNMENT);

        JTextField newUser = new JTextField(12);
        JPasswordField newPass = new JPasswordField(12);

        JButton signUpBtn = new JButton("Create Account");
        signUpBtn.setAlignmentX(Component.CENTER_ALIGNMENT);

        JButton backToLogin = new JButton("Back to Log In");
        backToLogin.setAlignmentX(Component.CENTER_ALIGNMENT);

        panel.add(Box.createVerticalStrut(10));
        panel.add(title);
        panel.add(Box.createVerticalStrut(10));
        panel.add(newUser);
        panel.add(Box.createVerticalStrut(10));
        panel.add(newPass);
        panel.add(Box.createVerticalStrut(15));
        panel.add(signUpBtn);
        panel.add(Box.createVerticalStrut(5));
        panel.add(backToLogin);

        signUpBtn.addActionListener(e -> cardLayout.show(mainPanel, "home"));
        backToLogin.addActionListener(e -> cardLayout.show(mainPanel, "login"));

        return panel;
    }

    // ---------------- HOME PANEL ----------------
    private static JPanel homePanel() {
        JPanel panel = new JPanel(new BorderLayout());

        JLabel homeText = new JLabel("Welcome to Home Page!", SwingConstants.CENTER);
        homeText.setFont(new Font("Arial", Font.BOLD, 16));

        panel.add(homeText, BorderLayout.CENTER);
        return panel;
    }
}
