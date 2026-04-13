package com.scms.gui.components;

import javax.swing.*;
import javax.swing.border.*;

import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.JTableHeader;
import java.awt.*;

public class StyledComponents {

    // Color Palette - Cardiff Met inspired
    public static final Color PRIMARY = new Color(19, 51, 80);        // Dark Navy
    public static final Color PRIMARY_LIGHT = new Color(41, 98, 155);  // Medium Blue
    public static final Color ACCENT = new Color(0, 166, 214);         // Bright Blue
    public static final Color SUCCESS = new Color(46, 139, 87);        // Green
    public static final Color WARNING = new Color(255, 165, 0);        // Orange
    public static final Color DANGER = new Color(220, 53, 69);         // Red
    public static final Color BG_MAIN = new Color(240, 243, 247);      // Light Gray BG
    public static final Color BG_CARD = Color.WHITE;
    public static final Color TEXT_PRIMARY = new Color(33, 37, 41);
    public static final Color TEXT_SECONDARY = new Color(108, 117, 125);
    public static final Color BORDER_COLOR = new Color(222, 226, 230);
    public static final Color SIDEBAR_BG = PRIMARY;
    public static final Color SIDEBAR_HOVER = PRIMARY_LIGHT;
    public static final Color SIDEBAR_TEXT = new Color(200, 210, 220);
    public static final Color SIDEBAR_ACTIVE = ACCENT;

    public static final Font FONT_TITLE = new Font("Segoe UI", Font.BOLD, 22);
    public static final Font FONT_SUBTITLE = new Font("Segoe UI", Font.BOLD, 16);
    public static final Font FONT_BODY = new Font("Segoe UI", Font.PLAIN, 14);
    public static final Font FONT_SMALL = new Font("Segoe UI", Font.PLAIN, 12);
    public static final Font FONT_BUTTON = new Font("Segoe UI", Font.BOLD, 13);
    public static final Font FONT_SIDEBAR = new Font("Segoe UI", Font.PLAIN, 14);
    public static final Font FONT_SIDEBAR_ACTIVE = new Font("Segoe UI", Font.BOLD, 14);

    public static JButton createPrimaryButton(String text) {
        JButton button = new JButton(text);
        button.setBackground(ACCENT);
        button.setForeground(Color.WHITE);
        button.setFont(FONT_BUTTON);
        button.setFocusPainted(false);
        button.setBorderPainted(false);
        button.setCursor(new Cursor(Cursor.HAND_CURSOR));
        button.setPreferredSize(new Dimension(140, 38));
        button.setOpaque(true);
        return button;
    }

    public static JButton createSuccessButton(String text) {
        JButton button = createPrimaryButton(text);
        button.setBackground(SUCCESS);
        return button;
    }

    public static JButton createDangerButton(String text) {
        JButton button = createPrimaryButton(text);
        button.setBackground(DANGER);
        return button;
    }

    public static JButton createSecondaryButton(String text) {
        JButton button = new JButton(text);
        button.setBackground(Color.WHITE);
        button.setForeground(PRIMARY);
        button.setFont(FONT_BUTTON);
        button.setFocusPainted(false);
        button.setBorder(BorderFactory.createLineBorder(BORDER_COLOR, 1));
        button.setCursor(new Cursor(Cursor.HAND_CURSOR));

        button.setPreferredSize(new Dimension(140, 38));
        button.setOpaque(true);
        return button;
    }

    public static JPanel createCard(String title) {
        JPanel card = new JPanel();
        card.setLayout(new BorderLayout(10, 10));
        card.setBackground(BG_CARD);
        card.setBorder(BorderFactory.createCompoundBorder(
                new LineBorder(BORDER_COLOR, 1, true),
                new EmptyBorder(20, 20, 20, 20)
        ));

        if (title != null && !title.isEmpty()) {
            JLabel titleLabel = new JLabel(title);
            titleLabel.setFont(FONT_SUBTITLE);
            titleLabel.setForeground(PRIMARY);
            titleLabel.setBorder(new EmptyBorder(0, 0, 10, 0));
            card.add(titleLabel, BorderLayout.NORTH);
        }

        return card;
    }

    public static JPanel createStatCard(String label, String value, Color accentColor) {
        JPanel card = new JPanel();
        card.setLayout(new BoxLayout(card, BoxLayout.Y_AXIS));
        card.setBackground(BG_CARD);
        card.setBorder(BorderFactory.createCompoundBorder(
                new LineBorder(BORDER_COLOR, 1, true),
                new EmptyBorder(20, 25, 20, 25)
        ));

        JPanel topBar = new JPanel();
        topBar.setMaximumSize(new Dimension(Integer.MAX_VALUE, 4));
        topBar.setPreferredSize(new Dimension(0, 4));
        topBar.setBackground(accentColor);
        topBar.setAlignmentX(Component.LEFT_ALIGNMENT);

        JLabel valueLabel = new JLabel(value);
        valueLabel.setFont(new Font("Segoe UI", Font.BOLD, 28));
        valueLabel.setForeground(PRIMARY);
        valueLabel.setAlignmentX(Component.LEFT_ALIGNMENT);

        JLabel nameLabel = new JLabel(label);
        nameLabel.setFont(FONT_SMALL);
        nameLabel.setForeground(TEXT_SECONDARY);
        nameLabel.setAlignmentX(Component.LEFT_ALIGNMENT);

        card.add(topBar);
        card.add(Box.createVerticalStrut(12));
        card.add(valueLabel);
        card.add(Box.createVerticalStrut(5));
        card.add(nameLabel);

        return card;
    }

    public static JTextField createStyledTextField() {
        JTextField field = new JTextField();
        field.setFont(FONT_BODY);
        field.setPreferredSize(new Dimension(250, 36));
        field.setBorder(BorderFactory.createCompoundBorder(
                new LineBorder(BORDER_COLOR, 1, true),
                new EmptyBorder(5, 10, 5, 10)
        ));
        return field;
    }

    public static JPasswordField createStyledPasswordField() {
        JPasswordField field = new JPasswordField();
        field.setFont(FONT_BODY);
        field.setPreferredSize(new Dimension(250, 36));
        field.setBorder(BorderFactory.createCompoundBorder(
                new LineBorder(BORDER_COLOR, 1, true),
                new EmptyBorder(5, 10, 5, 10)
        ));
        return field;
    }

    // Defines the `createStyledComboBox` method.
    public static <T> JComboBox<T> createStyledComboBox(T[] items) {
        JComboBox<T> combo = new JComboBox<>(items);
        combo.setFont(FONT_BODY);
        combo.setPreferredSize(new Dimension(250, 36));
        combo.setBackground(Color.WHITE);
        return combo;
    }

    public static JTable createStyledTable(Object[][] data, String[] columns) {
        JTable table = new JTable(data, columns) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        table.setFont(FONT_BODY);
        table.setRowHeight(40);
        table.setShowGrid(false);
        table.setIntercellSpacing(new Dimension(0, 0));
        table.setSelectionBackground(new Color(ACCENT.getRed(), ACCENT.getGreen(), ACCENT.getBlue(), 30));
        table.setSelectionForeground(TEXT_PRIMARY);
        table.setGridColor(BORDER_COLOR);
        table.setShowHorizontalLines(true);

        JTableHeader header = table.getTableHeader();
        header.setFont(new Font("Segoe UI", Font.BOLD, 13));
        header.setBackground(new Color(248, 249, 250));
        header.setForeground(TEXT_PRIMARY);
        header.setBorder(new LineBorder(BORDER_COLOR));
        header.setPreferredSize(new Dimension(0, 44));
        header.setReorderingAllowed(false);

        DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
        centerRenderer.setHorizontalAlignment(JLabel.CENTER);

        return table;
    }

    public static JLabel createFormLabel(String text) {
        JLabel label = new JLabel(text);
        label.setFont(new Font("Segoe UI", Font.BOLD, 13));
        label.setForeground(TEXT_PRIMARY);
        return label;
    }

    // Defines the `createStyledTextArea` method.
    public static JTextArea createStyledTextArea(int rows) {
        JTextArea area = new JTextArea(rows, 30);
        area.setFont(FONT_BODY);
        area.setLineWrap(true);
        area.setWrapStyleWord(true);
        area.setBorder(BorderFactory.createCompoundBorder(
                // Starts the signature for the `LineBorder` method.
                new LineBorder(BORDER_COLOR, 1, true),
                // Finishes the parameter list for the `LineBorder` method.
                new EmptyBorder(8, 10, 8, 10)
        // Closes the nested method calls in this statement.
        ));
        return area;
    }

    // Defines the `createBadge` method.
    public static JPanel createBadge(String text, Color bgColor) {
        JPanel badge = new JPanel(new FlowLayout(FlowLayout.CENTER, 0, 0));
        badge.setOpaque(true);
        badge.setBackground(bgColor);
        JLabel label = new JLabel(text);
        label.setFont(new Font("Segoe UI", Font.BOLD, 11));
        label.setForeground(Color.WHITE);
        label.setBorder(new EmptyBorder(3, 10, 3, 10));
        badge.add(label);
        return badge;
    }
}
