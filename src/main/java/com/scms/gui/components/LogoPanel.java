package com.scms.gui.components;

import javax.swing.*;
import java.awt.*;
import java.awt.geom.*;

/**
 * Custom-drawn professional SCMS logo component.
 * A modern hexagonal shield with a smart campus icon inside.
 */
public class LogoPanel extends JPanel {

    private final int logoSize;
    private final boolean darkBackground;

    public LogoPanel(int logoSize, boolean darkBackground) {
        this.logoSize = logoSize;
        this.darkBackground = darkBackground;
        setOpaque(false);
        setPreferredSize(new Dimension(logoSize, logoSize));
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g.create();
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2d.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
        g2d.setRenderingHint(RenderingHints.KEY_STROKE_CONTROL, RenderingHints.VALUE_STROKE_PURE);

        int cx = getWidth() / 2;
        int cy = getHeight() / 2;
        float scale = logoSize / 120f;

        g2d.translate(cx, cy);
        g2d.scale(scale, scale);

        Path2D shield = new Path2D.Double();
        // Declares and initializes `sw` as `double`.
        double sw = 48, sh = 54;
        shield.moveTo(0, -sh);
        shield.lineTo(sw, -sh * 0.45);
        shield.lineTo(sw, sh * 0.3);
        shield.lineTo(0, sh);
        shield.lineTo(-sw, sh * 0.3);
        shield.lineTo(-sw, -sh * 0.45);
        shield.closePath();

        GradientPaint shieldGrad = new GradientPaint(
                0, (float) -sh, new Color(0, 180, 230),
                0, (float) sh, new Color(0, 100, 170));
        g2d.setPaint(shieldGrad);
        g2d.fill(shield);

        g2d.setColor(new Color(100, 210, 255, 60));
        g2d.setStroke(new BasicStroke(2.5f));
        g2d.draw(shield);

        AffineTransform savedTx = g2d.getTransform();
        g2d.scale(0.88, 0.88);
        g2d.setColor(new Color(255, 255, 255, 25));
        g2d.setStroke(new BasicStroke(1.2f));
        g2d.draw(shield);
        g2d.setTransform(savedTx);

        g2d.setColor(Color.WHITE);

        g2d.fill(new RoundRectangle2D.Double(-10, -30, 20, 42, 2, 2));

        g2d.fill(new RoundRectangle2D.Double(-30, -14, 18, 26, 2, 2));

        g2d.fill(new RoundRectangle2D.Double(12, -14, 18, 26, 2, 2));

        g2d.setColor(new Color(255, 220, 60));
        g2d.fill(new RoundRectangle2D.Double(-6, -34, 12, 4, 2, 2));

        g2d.setStroke(new BasicStroke(2f, BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND));
        g2d.draw(new Line2D.Double(0, -34, 0, -42));
        g2d.fill(new Ellipse2D.Double(-2.5, -44, 5, 5));

        // Declares and initializes `windowColor` as `Color`.
        Color windowColor = new Color(0, 90, 160);
        Color windowGlow = new Color(100, 200, 255, 50);

        // Main tower windows (3 rows x 2 cols)
        // Starts a loop over the values defined in this header.
        for (int row = 0; row < 3; row++) {
            // Explanation: Starts a loop over the values defined in this header.
            for (int col = 0; col < 2; col++) {
                double wx = -6 + col * 8;
                double wy = -25 + row * 12;
                g2d.setColor(windowGlow);
                g2d.fill(new RoundRectangle2D.Double(wx - 1, wy - 1, 6, 8, 1, 1));
                g2d.setColor(windowColor);
                g2d.fill(new RoundRectangle2D.Double(wx, wy, 4, 6, 1, 1));
            }
        }

        // Starts a loop over the values defined in this header.
        for (int row = 0; row < 2; row++) {
            for (int col = 0; col < 2; col++) {
                double wx = -27 + col * 8;
                double wy = -9 + row * 11;
                g2d.setColor(windowGlow);
                g2d.fill(new RoundRectangle2D.Double(wx - 0.5, wy - 0.5, 5, 6, 1, 1));
                g2d.setColor(windowColor);
                g2d.fill(new RoundRectangle2D.Double(wx, wy, 4, 5, 1, 1));
            }
        }

        // Right block windows
        // Starts a loop over the values defined in this header.
        for (int row = 0; row < 2; row++) {
            // Starts a loop over the values defined in this header.
            for (int col = 0; col < 2; col++) {
                double wx = 15 + col * 8;
                double wy = -9 + row * 11;
                g2d.setColor(windowGlow);
                g2d.fill(new RoundRectangle2D.Double(wx - 0.5, wy - 0.5, 5, 6, 1, 1));
                g2d.setColor(windowColor);
                g2d.fill(new RoundRectangle2D.Double(wx, wy, 4, 5, 1, 1));
            }

        }

        // Calls `setColor` to configure this object.
        g2d.setColor(new Color(255, 220, 60));
        g2d.fill(new RoundRectangle2D.Double(-3.5, 3, 7, 9, 3, 3));

        // Calls `setColor` to configure this object.
        g2d.setColor(Color.WHITE);
        g2d.setStroke(new BasicStroke(2.5f, BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND));
        g2d.draw(new Line2D.Double(-36, 12, 36, 12));

        // Calls `setStroke` to configure this object.
        g2d.setStroke(new BasicStroke(1.5f, BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND));
        g2d.setColor(new Color(255, 255, 255, 120));
        g2d.draw(new Line2D.Double(-34, 16, -20, 16));
        g2d.draw(new Line2D.Double(20, 16, 34, 16));

        g2d.setColor(new Color(255, 220, 60, 200));
        g2d.setStroke(new BasicStroke(1.8f, BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND));
        for (int i = 1; i <= 3; i++) {
            int arcSize = i * 7;
            g2d.draw(new Arc2D.Double(-arcSize / 2.0, -48 - arcSize / 2.0,
                    arcSize, arcSize, 20, 140, Arc2D.OPEN));
        }

        g2d.dispose();
    }

    /**
     * Creates a logo with text below it for the login page.
     */
    public static JPanel createLogoWithText(int logoSize, String title, String subtitle, boolean darkBg) {
        JPanel container = new JPanel();
        container.setLayout(new BoxLayout(container, BoxLayout.Y_AXIS));
        container.setOpaque(false);

        LogoPanel logo = new LogoPanel(logoSize, darkBg);
        logo.setAlignmentX(Component.CENTER_ALIGNMENT);
        logo.setMaximumSize(new Dimension(logoSize, logoSize));

        JLabel titleLabel = new JLabel(title);
        titleLabel.setFont(new Font("Segoe UI", Font.BOLD, (int) (logoSize * 0.28)));
        titleLabel.setForeground(darkBg ? Color.WHITE : StyledComponents.PRIMARY);
        titleLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        JLabel subtitleLabel = new JLabel(subtitle);
        subtitleLabel.setFont(new Font("Segoe UI", Font.PLAIN, (int) (logoSize * 0.15)));
        subtitleLabel.setForeground(darkBg ? new Color(180, 200, 220) : StyledComponents.TEXT_SECONDARY);
        subtitleLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        container.add(logo);
        container.add(Box.createVerticalStrut(14));
        container.add(titleLabel);
        container.add(Box.createVerticalStrut(4));
        container.add(subtitleLabel);

        return container;
    }
}
