package com.scms;

import com.formdev.flatlaf.FlatLightLaf;
import com.scms.gui.LoginFrame;
import com.scms.pattern.structural.CampusFacade;

import javax.swing.*;

/**
 * Smart Campus Management System (SCMS) - Main Application Entry Point
 *
 * Cardiff Metropolitan University Digital Transformation Prototype
 * Demonstrates OOP principles, design patterns, and software architecture.
 *
 * Design Patterns Used:
 * - Singleton (SystemManager) - Creational
 * - Factory (RoomFactory) - Creational
 * - Facade (CampusFacade) - Structural
 * - Observer (NotificationService) - Behavioural
 */
public class SmartCampusApp {

    public static void main(String[] args) {
        try {
            FlatLightLaf.setup();
            UIManager.put("Button.arc", 8);
            UIManager.put("TextComponent.arc", 8);
            UIManager.put("Component.arc", 8);
            UIManager.put("ScrollBar.width", 10);
            UIManager.put("ScrollBar.thumbArc", 999);
            UIManager.put("ScrollBar.trackArc", 999);
        } catch (Exception e) {
            try {
                UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
            } catch (Exception ignored) {}
        }

        SwingUtilities.invokeLater(() -> {
            CampusFacade facade = new CampusFacade();
            LoginFrame loginFrame = new LoginFrame(facade);

            loginFrame.setVisible(true);
        });
    }
}
