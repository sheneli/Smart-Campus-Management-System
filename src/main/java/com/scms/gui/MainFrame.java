package com.scms.gui;

import com.scms.gui.components.StyledComponents;
import com.scms.gui.panels.*;
import com.scms.model.User;
import com.scms.model.UserRole;
import com.scms.pattern.structural.CampusFacade;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

public class MainFrame extends JFrame {

    private final CampusFacade facade;
    private JPanel contentPanel;
    private CardLayout cardLayout;
    private final List<JPanel> sidebarButtons = new ArrayList<>();
    private JPanel activeButton;

    private DashboardPanel dashboardPanel;
    private RoomManagementPanel roomPanel;
    private BookingPanel bookingPanel;
    private MaintenancePanel maintenancePanel;
    private NotificationPanel notificationPanel;

    // Defines the constructor for `MainFrame`.
    public MainFrame(CampusFacade facade) {
        this.facade = facade;
        initializeUI();
    }

    // Defines the `initializeUI` method.
    private void initializeUI() {
        User user = facade.getCurrentUser();
        setTitle("SCMS - " + user.getName() + " (" + user.getRole().getDisplayName() + ")");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(1280, 780);
        setMinimumSize(new Dimension(1100, 680));
        setLocationRelativeTo(null);
        setExtendedState(JFrame.MAXIMIZED_BOTH);

        JPanel mainPanel = new JPanel(new BorderLayout());
        mainPanel.setBackground(StyledComponents.BG_MAIN);

        JPanel sidebar = createSidebar();
        mainPanel.add(sidebar, BorderLayout.WEST);

        cardLayout = new CardLayout();
        contentPanel = new JPanel(cardLayout);
        contentPanel.setBackground(StyledComponents.BG_MAIN);

        dashboardPanel = new DashboardPanel(facade);
        roomPanel = new RoomManagementPanel(facade);
        bookingPanel = new BookingPanel(facade);
        maintenancePanel = new MaintenancePanel(facade);
        notificationPanel = new NotificationPanel(facade);

        contentPanel.add(dashboardPanel, "dashboard");
        contentPanel.add(roomPanel, "rooms");
        contentPanel.add(bookingPanel, "bookings");
        contentPanel.add(maintenancePanel, "maintenance");
        contentPanel.add(notificationPanel, "notifications");

        mainPanel.add(contentPanel, BorderLayout.CENTER);
        setContentPane(mainPanel);

        showPanel("dashboard", sidebarButtons.get(0));
    }

    private JPanel createSidebar() {
        JPanel sidebar = new JPanel();
        sidebar.setPreferredSize(new Dimension(240, 0));
        sidebar.setBackground(StyledComponents.SIDEBAR_BG);
        sidebar.setLayout(new BoxLayout(sidebar, BoxLayout.Y_AXIS));

        JPanel brandPanel = new JPanel();
        brandPanel.setBackground(StyledComponents.SIDEBAR_BG);
        brandPanel.setLayout(new BoxLayout(brandPanel, BoxLayout.Y_AXIS));
        brandPanel.setBorder(new EmptyBorder(18, 18, 12, 18));
        brandPanel.setMaximumSize(new Dimension(240, 120));

        JLabel logoLabel = loadSidebarLogo("/images/cardiffmet_logo_white.png", 180);
        if (logoLabel != null) {
            logoLabel.setAlignmentX(Component.LEFT_ALIGNMENT);
            brandPanel.add(logoLabel);
            brandPanel.add(Box.createVerticalStrut(6));
        }

        JLabel brandSub = new JLabel("Smart Campus Management");
        brandSub.setFont(new Font("Segoe UI", Font.PLAIN, 10));
        brandSub.setForeground(new Color(0, 166, 214));
        brandSub.setAlignmentX(Component.LEFT_ALIGNMENT);
        brandPanel.add(brandSub);
        sidebar.add(brandPanel);

        JSeparator sep = new JSeparator();
        sep.setMaximumSize(new Dimension(200, 1));
        sep.setForeground(new Color(255, 255, 255, 30));
        sidebar.add(sep);
        sidebar.add(Box.createVerticalStrut(15));

        JLabel navLabel = new JLabel("  NAVIGATION");
        navLabel.setFont(new Font("Segoe UI", Font.BOLD, 10));
        navLabel.setForeground(new Color(150, 160, 170));
        navLabel.setAlignmentX(Component.LEFT_ALIGNMENT);
        navLabel.setBorder(new EmptyBorder(0, 20, 8, 0));
        navLabel.setMaximumSize(new Dimension(240, 25));
        sidebar.add(navLabel);

        addMenuItem(sidebar, "\u25A3  Dashboard", "dashboard");
        addMenuItem(sidebar, "\u25A1  Rooms", "rooms");
        addMenuItem(sidebar, "\u2637  Bookings", "bookings");
        addMenuItem(sidebar, "\u2692  Maintenance", "maintenance");
        addMenuItem(sidebar, "\u2709  Notifications", "notifications");

        sidebar.add(Box.createVerticalGlue());

        User user = facade.getCurrentUser();

        JSeparator userSep = new JSeparator();
        userSep.setMaximumSize(new Dimension(200, 1));
        userSep.setForeground(new Color(255, 255, 255, 20));
        sidebar.add(userSep);

        JPanel userPanel = new JPanel();
        userPanel.setBackground(new Color(8, 28, 50));
        userPanel.setLayout(new BorderLayout(0, 0));
        userPanel.setBorder(new EmptyBorder(14, 16, 14, 16));
        userPanel.setMaximumSize(new Dimension(240, 130));

        JPanel profileRow = new JPanel(new BorderLayout(12, 0));
        // Calls `setOpaque` to configure this object.
        profileRow.setOpaque(false);

        String initials = getInitials(user.getName());
        JPanel avatar = new JPanel() {
            // Explanation: Marks the following method as an override of inherited behavior.
            @Override
            // Explanation: Defines the `paintComponent` method.
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D g2d = (Graphics2D) g.create();
                g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                java.awt.GradientPaint gp = new java.awt.GradientPaint(
                        0, 0, StyledComponents.ACCENT,
                        getWidth(), getHeight(), new Color(0, 120, 180));
                g2d.setPaint(gp);
                g2d.fillOval(2, 2, getWidth() - 4, getHeight() - 4);
                g2d.setColor(Color.WHITE);
                g2d.setFont(new Font("Segoe UI", Font.BOLD, 15));
                FontMetrics fm = g2d.getFontMetrics();
                int tx = (getWidth() - fm.stringWidth(initials)) / 2;
                int ty = (getHeight() + fm.getAscent() - fm.getDescent()) / 2;
                g2d.drawString(initials, tx, ty);
                g2d.dispose();
            }
        // Explanation: Closes the current block and ends the statement.
        };
        avatar.setOpaque(false);
        avatar.setPreferredSize(new Dimension(42, 42));
        avatar.setMinimumSize(new Dimension(42, 42));
        avatar.setMaximumSize(new Dimension(42, 42));

        JPanel nameRolePanel = new JPanel();
        nameRolePanel.setOpaque(false);
        nameRolePanel.setLayout(new BoxLayout(nameRolePanel, BoxLayout.Y_AXIS));

        JLabel userName = new JLabel(user.getName());
        userName.setFont(new Font("Segoe UI", Font.BOLD, 13));
        userName.setForeground(Color.WHITE);
        userName.setAlignmentX(Component.LEFT_ALIGNMENT);

        JPanel roleBadge = new JPanel(new FlowLayout(FlowLayout.LEFT, 0, 0)) {
            @Override
            protected void paintComponent(Graphics g) {
                Graphics2D g2d = (Graphics2D) g.create();
                g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                Color badgeColor = switch (user.getRole()) {
                    case ADMINISTRATOR -> new Color(0, 166, 214);
                    case STAFF -> new Color(46, 139, 87);
                    case STUDENT -> new Color(255, 165, 0);
                };
                g2d.setColor(new Color(badgeColor.getRed(), badgeColor.getGreen(), badgeColor.getBlue(), 35));
                g2d.fillRoundRect(0, 0, getWidth(), getHeight(), 12, 12);
                g2d.dispose();
            }
        };
        roleBadge.setOpaque(false);
        roleBadge.setAlignmentX(Component.LEFT_ALIGNMENT);
        JLabel roleLabel = new JLabel(user.getRole().getDisplayName());
        roleLabel.setFont(new Font("Segoe UI", Font.BOLD, 10));
        Color roleColor = switch (user.getRole()) {
            case ADMINISTRATOR -> StyledComponents.ACCENT;
            case STAFF -> new Color(100, 200, 140);
            case STUDENT -> new Color(255, 200, 80);
        };
        roleLabel.setForeground(roleColor);
        roleLabel.setBorder(new EmptyBorder(2, 8, 2, 8));
        roleBadge.add(roleLabel);

        nameRolePanel.add(userName);
        nameRolePanel.add(Box.createVerticalStrut(4));
        nameRolePanel.add(roleBadge);

        profileRow.add(avatar, BorderLayout.WEST);
        profileRow.add(nameRolePanel, BorderLayout.CENTER);

        JButton logoutButton = new JButton("Sign Out") {
            @Override
            protected void paintComponent(Graphics g) {
                Graphics2D g2d = (Graphics2D) g.create();
                g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                if (getModel().isRollover()) {
                    g2d.setColor(new Color(220, 53, 69, 25));
                } else {
                    g2d.setColor(new Color(255, 255, 255, 8));
                }
                g2d.fillRoundRect(0, 0, getWidth(), getHeight(), 8, 8);
                g2d.setColor(new Color(255, 255, 255, 25));
                g2d.drawRoundRect(0, 0, getWidth() - 1, getHeight() - 1, 8, 8);
                g2d.setColor(getModel().isRollover() ? new Color(255, 120, 120) : new Color(180, 190, 200));
                g2d.setFont(new Font("Segoe UI", Font.PLAIN, 12));
                FontMetrics fm = g2d.getFontMetrics();
                String text = getText();
                int tx = (getWidth() - fm.stringWidth(text)) / 2;
                int ty = (getHeight() + fm.getAscent() - fm.getDescent()) / 2;
                g2d.drawString(text, tx, ty);

                g2d.dispose();
            }
        };
        logoutButton.setOpaque(false);
        logoutButton.setContentAreaFilled(false);
        logoutButton.setBorderPainted(false);
        logoutButton.setFocusPainted(false);
        logoutButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
        logoutButton.setPreferredSize(new Dimension(0, 32));
        logoutButton.addActionListener(e -> handleLogout());

        userPanel.add(profileRow, BorderLayout.NORTH);
        JPanel logoutWrapper = new JPanel(new BorderLayout());
        logoutWrapper.setOpaque(false);
        logoutWrapper.setBorder(new EmptyBorder(10, 0, 0, 0));

        logoutWrapper.add(logoutButton, BorderLayout.CENTER);
        userPanel.add(logoutWrapper, BorderLayout.SOUTH);
        sidebar.add(userPanel);

        return sidebar;
    }

    private void addMenuItem(JPanel sidebar, String text, String panelName) {
        JPanel item = new JPanel(new BorderLayout());
        item.setBackground(StyledComponents.SIDEBAR_BG);
        item.setMaximumSize(new Dimension(240, 44));
        item.setCursor(new Cursor(Cursor.HAND_CURSOR));
        item.setBorder(new EmptyBorder(10, 24, 10, 20));

        JLabel label = new JLabel(text);
        label.setFont(StyledComponents.FONT_SIDEBAR);
        label.setForeground(StyledComponents.SIDEBAR_TEXT);
        item.add(label, BorderLayout.CENTER);

        item.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                showPanel(panelName, item);
            }

            @Override
            public void mouseEntered(MouseEvent e) {
                if (item != activeButton) {
                    item.setBackground(StyledComponents.SIDEBAR_HOVER);
                }
            }

            @Override
            public void mouseExited(MouseEvent e) {
                if (item != activeButton) {
                    item.setBackground(StyledComponents.SIDEBAR_BG);
                }
            }
        });

        sidebarButtons.add(item);
        sidebar.add(item);
    }

    private void showPanel(String panelName, JPanel button) {
        if (activeButton != null) {
            activeButton.setBackground(StyledComponents.SIDEBAR_BG);
            Component label = ((BorderLayout) activeButton.getLayout()).getLayoutComponent(BorderLayout.CENTER);
            if (label instanceof JLabel) {
                ((JLabel) label).setForeground(StyledComponents.SIDEBAR_TEXT);
                ((JLabel) label).setFont(StyledComponents.FONT_SIDEBAR);
            }
        }

        activeButton = button;
        button.setBackground(StyledComponents.SIDEBAR_ACTIVE);
        Component label = ((BorderLayout) button.getLayout()).getLayoutComponent(BorderLayout.CENTER);
        if (label instanceof JLabel) {
            ((JLabel) label).setForeground(Color.WHITE);
            ((JLabel) label).setFont(StyledComponents.FONT_SIDEBAR_ACTIVE);
        }

        switch (panelName) {
            case "dashboard" -> dashboardPanel.refresh();
            case "rooms" -> roomPanel.refresh();
            case "bookings" -> bookingPanel.refresh();
            case "maintenance" -> maintenancePanel.refresh();
            case "notifications" -> notificationPanel.refresh();
        }

        cardLayout.show(contentPanel, panelName);
    }

    private JLabel loadSidebarLogo(String resourcePath, int targetWidth) {
        try {
            InputStream is = getClass().getResourceAsStream(resourcePath);
            if (is == null) return null;
            BufferedImage img = ImageIO.read(is);
            if (img == null) return null;
            double scale = (double) targetWidth / img.getWidth();
            int targetHeight = (int) (img.getHeight() * scale);
            Image scaled = img.getScaledInstance(targetWidth, targetHeight, Image.SCALE_SMOOTH);
            return new JLabel(new ImageIcon(scaled));
        } catch (Exception e) {
            return null;
        }
    }

    // Defines the `getInitials` method.
    private String getInitials(String fullName) {
        if (fullName == null || fullName.isBlank()) return "?";
        String[] parts = fullName.trim().split("\\s+");
        if (parts.length == 1) return parts[0].substring(0, 1).toUpperCase();
        int start = 0;
        if (parts[0].endsWith(".") && parts.length > 2) start = 1;
        String first = parts[start].substring(0, 1).toUpperCase();
        String last = parts[parts.length - 1].substring(0, 1).toUpperCase();
        return first + last;
    }

    // Defines the `handleLogout` method.
    private void handleLogout() {
        int confirm = JOptionPane.showConfirmDialog(this,
                "Are you sure you want to logout?", "Confirm Logout",
                JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
        if (confirm == JOptionPane.YES_OPTION) {
            facade.logout();
            LoginFrame loginFrame = new LoginFrame(facade);
            loginFrame.setVisible(true);
            this.dispose();
        }
    }
}
