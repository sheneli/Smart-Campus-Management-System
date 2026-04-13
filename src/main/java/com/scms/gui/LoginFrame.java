package com.scms.gui;

import com.scms.gui.components.StyledComponents;
import com.scms.model.User;
import com.scms.pattern.structural.CampusFacade;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.geom.*;
import java.awt.image.BufferedImage;
import java.io.InputStream;
import javax.imageio.ImageIO;

public class LoginFrame extends JFrame {

    private final CampusFacade facade;
    private JTextField emailField;
    private JPasswordField passwordField;
    private JLabel statusLabel;

    public LoginFrame(CampusFacade facade) {
        this.facade = facade;
        initializeUI();
    }

    private void initializeUI() {
        setTitle("Smart Campus Management System - Login");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(1050, 640);
        setLocationRelativeTo(null);
        setResizable(true);

        JPanel mainPanel = new JPanel(new GridLayout(1, 2));
        mainPanel.add(createBrandingPanel());
        mainPanel.add(createLoginPanel());
        setContentPane(mainPanel);

        setAlwaysOnTop(true);
        toFront();
        requestFocus();
        setAlwaysOnTop(false);
    }

    private JPanel createBrandingPanel() {
        JPanel panel = new JPanel() {
            // Explanation: Marks the following method as an override of inherited behavior.
            @Override
            // Explanation: Defines the `paintComponent` method.
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D g2d = (Graphics2D) g.create();
                g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

                GradientPaint gradient = new GradientPaint(
                        0, 0, new Color(19, 51, 80),
                        0, getHeight(), new Color(12, 36, 58));
                g2d.setPaint(gradient);
                g2d.fillRect(0, 0, getWidth(), getHeight());

                g2d.setColor(new Color(255, 255, 255, 6));
                g2d.fill(new Ellipse2D.Double(-100, getHeight() - 180, 350, 350));
                g2d.fill(new Ellipse2D.Double(getWidth() - 120, -80, 250, 250));

                g2d.dispose();
            }
        // Explanation: Closes the current block and ends the statement.
        };
        panel.setLayout(new GridBagLayout());

        JPanel centerWrapper = new JPanel();
        centerWrapper.setOpaque(false);
        centerWrapper.setLayout(new BoxLayout(centerWrapper, BoxLayout.Y_AXIS));

        JLabel logoLabel = loadLogoImage("/images/cardiffmet_logo_white.png", 320);
        if (logoLabel != null) {
            logoLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
            centerWrapper.add(logoLabel);
        }

        centerWrapper.add(Box.createVerticalStrut(35));

        JPanel divider = new JPanel();
        divider.setMaximumSize(new Dimension(250, 1));
        divider.setPreferredSize(new Dimension(250, 1));
        divider.setBackground(new Color(255, 255, 255, 35));
        divider.setAlignmentX(Component.CENTER_ALIGNMENT);
        centerWrapper.add(divider);

        centerWrapper.add(Box.createVerticalStrut(25));

        JLabel scmsTitle = new JLabel("Smart Campus Management System");
        scmsTitle.setFont(new Font("Segoe UI", Font.BOLD, 18));
        scmsTitle.setForeground(Color.WHITE);
        scmsTitle.setAlignmentX(Component.CENTER_ALIGNMENT);
        scmsTitle.setHorizontalAlignment(SwingConstants.CENTER);
        centerWrapper.add(scmsTitle);

        centerWrapper.add(Box.createVerticalStrut(15));

        JLabel descLabel = new JLabel("<html><div style='text-align:center;line-height:1.7'>"
                + "Streamline room bookings, manage maintenance<br>"
                + "requests, and stay connected with campus-wide<br>"
                + "notifications in one unified platform.</div></html>");
        descLabel.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        descLabel.setForeground(new Color(155, 170, 190));
        descLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        descLabel.setHorizontalAlignment(SwingConstants.CENTER);
        centerWrapper.add(descLabel);

        centerWrapper.add(Box.createVerticalStrut(25));

        JPanel features = new JPanel(new FlowLayout(FlowLayout.CENTER, 8, 6));
        features.setOpaque(false);
        features.setMaximumSize(new Dimension(400, 50));
        features.setAlignmentX(Component.CENTER_ALIGNMENT);
        features.add(createFeaturePill("Room Booking"));
        features.add(createFeaturePill("Maintenance"));
        features.add(createFeaturePill("Notifications"));
        features.add(createFeaturePill("Analytics"));
        centerWrapper.add(features);

        centerWrapper.add(Box.createVerticalStrut(30));

        JLabel versionLabel = new JLabel("CMP7001 - Advanced Programming Module");
        versionLabel.setFont(new Font("Segoe UI", Font.PLAIN, 10));
        versionLabel.setForeground(new Color(90, 110, 130));
        versionLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        centerWrapper.add(versionLabel);

        panel.add(centerWrapper);

        return panel;
    }

    private JLabel loadLogoImage(String resourcePath, int targetWidth) {
        try {
            InputStream is = getClass().getResourceAsStream(resourcePath);
            if (is == null) return null;
            BufferedImage img = ImageIO.read(is);
            if (img == null) return null;

            double scale = (double) targetWidth / img.getWidth();
            int targetHeight = (int) (img.getHeight() * scale);

            Image scaled = img.getScaledInstance(targetWidth, targetHeight, Image.SCALE_SMOOTH);
            JLabel label = new JLabel(new ImageIcon(scaled));
            label.setPreferredSize(new Dimension(targetWidth, targetHeight));
            return label;
        } catch (Exception e) {
            return null;
        }
    }

    private JPanel createFeaturePill(String text) {
        JPanel pill = new JPanel(new FlowLayout(FlowLayout.CENTER, 0, 0)) {
            @Override
            protected void paintComponent(Graphics g) {
                Graphics2D g2d = (Graphics2D) g.create();
                g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                g2d.setColor(new Color(255, 255, 255, 12));
                g2d.fill(new RoundRectangle2D.Double(0, 0, getWidth(), getHeight(), 20, 20));
                g2d.setColor(new Color(255, 255, 255, 30));
                g2d.setStroke(new BasicStroke(1f));
                g2d.draw(new RoundRectangle2D.Double(0, 0, getWidth() - 1, getHeight() - 1, 20, 20));
                g2d.dispose();
            }
        };
        pill.setOpaque(false);
        JLabel label = new JLabel(text);
        label.setFont(new Font("Segoe UI", Font.PLAIN, 11));
        label.setForeground(new Color(170, 190, 210));
        label.setBorder(new EmptyBorder(5, 14, 5, 14));
        pill.add(label);
        return pill;
    }

    private JPanel createLoginPanel() {
        JPanel panel = new JPanel() {
            // Explanation: Marks the following method as an override of inherited behavior.
            @Override
            // Explanation: Defines the `paintComponent` method.
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D g2d = (Graphics2D) g.create();
                g2d.setColor(new Color(250, 251, 253));
                g2d.fillRect(0, 0, getWidth(), getHeight());
                GradientPaint bar = new GradientPaint(0, 0, new Color(19, 51, 80), getWidth(), 0, new Color(0, 140, 190));
                g2d.setPaint(bar);
                g2d.fillRect(0, 0, getWidth(), 3);
                g2d.dispose();
            }
        // Explanation: Closes the current block and ends the statement.
        };
        panel.setLayout(new GridBagLayout());

        JPanel formContainer = new JPanel();
        formContainer.setOpaque(false);
        formContainer.setLayout(new BoxLayout(formContainer, BoxLayout.Y_AXIS));
        formContainer.setPreferredSize(new Dimension(360, 500));

        JLabel welcomeLabel = new JLabel("Welcome Back");
        welcomeLabel.setFont(new Font("Segoe UI", Font.BOLD, 28));
        welcomeLabel.setForeground(StyledComponents.PRIMARY);
        welcomeLabel.setAlignmentX(Component.LEFT_ALIGNMENT);

        JLabel instructionLabel = new JLabel("Sign in to access your campus portal");
        instructionLabel.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        instructionLabel.setForeground(StyledComponents.TEXT_SECONDARY);
        instructionLabel.setAlignmentX(Component.LEFT_ALIGNMENT);

        JLabel emailLabel = StyledComponents.createFormLabel("Email Address");
        emailLabel.setAlignmentX(Component.LEFT_ALIGNMENT);
        emailField = StyledComponents.createStyledTextField();
        emailField.setMaximumSize(new Dimension(Integer.MAX_VALUE, 42));
        emailField.setAlignmentX(Component.LEFT_ALIGNMENT);

        JLabel passLabel = StyledComponents.createFormLabel("Password");
        passLabel.setAlignmentX(Component.LEFT_ALIGNMENT);
        passwordField = StyledComponents.createStyledPasswordField();
        passwordField.setMaximumSize(new Dimension(Integer.MAX_VALUE, 42));
        passwordField.setAlignmentX(Component.LEFT_ALIGNMENT);

        JButton loginButton = new JButton("Sign In") {
            @Override
            protected void paintComponent(Graphics g) {
                Graphics2D g2d = (Graphics2D) g.create();
                g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                Color bg1 = getModel().isRollover() ? new Color(19, 51, 80) : new Color(0, 150, 200);
                Color bg2 = getModel().isRollover() ? new Color(0, 100, 160) : new Color(0, 120, 180);
                g2d.setPaint(new GradientPaint(0, 0, bg1, getWidth(), 0, bg2));
                g2d.fill(new RoundRectangle2D.Double(0, 0, getWidth(), getHeight(), 10, 10));
                g2d.setColor(Color.WHITE);
                g2d.setFont(new Font("Segoe UI", Font.BOLD, 15));
                FontMetrics fm = g2d.getFontMetrics();
                g2d.drawString(getText(), (getWidth() - fm.stringWidth(getText())) / 2,
                        (getHeight() + fm.getAscent() - fm.getDescent()) / 2);
                g2d.dispose();
            }
        };
        loginButton.setMaximumSize(new Dimension(Integer.MAX_VALUE, 46));
        loginButton.setAlignmentX(Component.LEFT_ALIGNMENT);
        loginButton.setFocusPainted(false);
        loginButton.setBorderPainted(false);
        loginButton.setContentAreaFilled(false);
        loginButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
        loginButton.addActionListener(e -> handleLogin());

        statusLabel = new JLabel(" ");
        statusLabel.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        statusLabel.setForeground(StyledComponents.DANGER);
        statusLabel.setAlignmentX(Component.LEFT_ALIGNMENT);

        JPanel hintCard = new JPanel();
        hintCard.setLayout(new BoxLayout(hintCard, BoxLayout.Y_AXIS));
        hintCard.setBackground(new Color(240, 248, 255));
        hintCard.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(19, 51, 80, 40), 1, true),
                new EmptyBorder(12, 15, 12, 15)));
        hintCard.setMaximumSize(new Dimension(Integer.MAX_VALUE, 110));
        hintCard.setAlignmentX(Component.LEFT_ALIGNMENT);

        JLabel hintTitle = new JLabel("Demo Credentials");
        hintTitle.setFont(new Font("Segoe UI", Font.BOLD, 11));
        hintTitle.setForeground(new Color(19, 51, 80));
        hintTitle.setAlignmentX(Component.LEFT_ALIGNMENT);

        JLabel hintContent = new JLabel("<html><table style='font-size:10px;color:#5a6d80;'>"
                + "<tr><td><b>Admin:</b></td><td>admin@cardiffmet.ac.uk / admin123</td></tr>"
                + "<tr><td><b>Staff:</b></td><td>emily.brown@cardiffmet.ac.uk / staff123</td></tr>"
                + "<tr><td><b>Student:</b></td><td>alex.thompson@student... / student123</td></tr>"
                + "</table></html>");
        hintContent.setAlignmentX(Component.LEFT_ALIGNMENT);

        hintCard.add(hintTitle);
        hintCard.add(Box.createVerticalStrut(6));
        hintCard.add(hintContent);

        formContainer.add(welcomeLabel);
        formContainer.add(Box.createVerticalStrut(6));
        formContainer.add(instructionLabel);
        formContainer.add(Box.createVerticalStrut(32));
        formContainer.add(emailLabel);
        formContainer.add(Box.createVerticalStrut(6));
        formContainer.add(emailField);
        formContainer.add(Box.createVerticalStrut(20));
        formContainer.add(passLabel);
        formContainer.add(Box.createVerticalStrut(6));
        formContainer.add(passwordField);
        formContainer.add(Box.createVerticalStrut(10));
        formContainer.add(statusLabel);
        formContainer.add(Box.createVerticalStrut(15));
        formContainer.add(loginButton);
        formContainer.add(Box.createVerticalStrut(25));
        formContainer.add(hintCard);

        panel.add(formContainer);

        passwordField.addActionListener(e -> handleLogin());
        emailField.addActionListener(e -> passwordField.requestFocus());

        return panel;
    }

    private void handleLogin() {
        String email = emailField.getText().trim();
        String password = new String(passwordField.getPassword());

        if (email.isEmpty() || password.isEmpty()) {
            statusLabel.setForeground(StyledComponents.DANGER);
            statusLabel.setText("Please enter both email and password.");
            return;
        }

        User user = facade.login(email, password);
        if (user != null) {
            statusLabel.setForeground(StyledComponents.SUCCESS);
            statusLabel.setText("Login successful! Welcome, " + user.getName());
            SwingUtilities.invokeLater(() -> {
                MainFrame mainFrame = new MainFrame(facade);
                mainFrame.setVisible(true);
                this.dispose();
            });
        } else {
            statusLabel.setForeground(StyledComponents.DANGER);
            statusLabel.setText("Invalid email or password. Please try again.");
            passwordField.setText("");
        }
    }
}
