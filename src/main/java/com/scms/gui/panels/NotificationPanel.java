// Explanation: Declares the `com.scms.gui.panels` package for this source file.
package com.scms.gui.panels;

// Explanation: Imports `StyledComponents` for use in this file.
import com.scms.gui.components.StyledComponents;
// Explanation: Imports `Notification` for use in this file.
import com.scms.model.Notification;
// Explanation: Imports `CampusFacade` for use in this file.
import com.scms.pattern.structural.CampusFacade;

// Explanation: Imports all types from `javax.swing` for use in this file.
import javax.swing.*;
// Explanation: Imports `EmptyBorder` for use in this file.
import javax.swing.border.EmptyBorder;
// Explanation: Imports all types from `java.awt` for use in this file.
import java.awt.*;
// Explanation: Imports `DateTimeFormatter` for use in this file.
import java.time.format.DateTimeFormatter;
// Explanation: Imports `List` for use in this file.
import java.util.List;

// Explanation: Declares the `NotificationPanel` class with its inheritance or interface information.
public class NotificationPanel extends JPanel {

    // Explanation: Declares the private final field `facade` of type `CampusFacade`.
    private final CampusFacade facade;
    // Explanation: Declares the private field `notificationList` of type `JPanel`.
    private JPanel notificationList;

    // Explanation: Defines the constructor for `NotificationPanel`.
    public NotificationPanel(CampusFacade facade) {
        // Explanation: Updates `this.facade` with a new value.
        this.facade = facade;
        // Explanation: Calls `setLayout` to configure this object.
        setLayout(new BorderLayout());
        // Explanation: Calls `setBackground` to configure this object.
        setBackground(StyledComponents.BG_MAIN);
        // Explanation: Calls `setBorder` to configure this object.
        setBorder(new EmptyBorder(25, 30, 25, 30));
        // Explanation: Calls `buildUI` to perform this step.
        buildUI();
    // Explanation: Closes the current block.
    }

    // Explanation: Defines the `buildUI` method.
    private void buildUI() {
        // Explanation: Calls `removeAll` to perform this step.
        removeAll();

        // Header
        // Explanation: Declares and initializes `header` as `JPanel`.
        JPanel header = new JPanel(new BorderLayout());
        // Explanation: Calls `setOpaque` to configure this object.
        header.setOpaque(false);
        // Explanation: Calls `setBorder` to configure this object.
        header.setBorder(new EmptyBorder(0, 0, 20, 0));

        // Explanation: Declares and initializes `title` as `JLabel`.
        JLabel title = new JLabel("Notifications");
        // Explanation: Calls `setFont` to configure this object.
        title.setFont(StyledComponents.FONT_TITLE);
        // Explanation: Calls `setForeground` to configure this object.
        title.setForeground(StyledComponents.PRIMARY);

        // Explanation: Declares and initializes `buttonPanel` as `JPanel`.
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT, 10, 0));
        // Explanation: Calls `setOpaque` to configure this object.
        buttonPanel.setOpaque(false);

        // Explanation: Declares and initializes `unread` as `int`.
        int unread = facade.getUnreadNotificationCount();
        // Explanation: Declares and initializes `countLabel` as `JLabel`.
        JLabel countLabel = new JLabel(unread + " unread");
        // Explanation: Calls `setFont` to configure this object.
        countLabel.setFont(StyledComponents.FONT_BODY);
        // Explanation: Calls `setForeground` to configure this object.
        countLabel.setForeground(unread > 0 ? StyledComponents.DANGER : StyledComponents.TEXT_SECONDARY);

        // Explanation: Declares and initializes `markAllBtn` as `JButton`.
        JButton markAllBtn = StyledComponents.createSecondaryButton("Mark All Read");
        // Explanation: Performs the operation written on this line.
        markAllBtn.addActionListener(e -> {
            // Explanation: Calls `markAllNotificationsRead` to perform this step.
            facade.markAllNotificationsRead();
            // Explanation: Calls `refresh` to perform this step.
            refresh();
        // Explanation: Closes the current block and completes the surrounding method call.
        });

        // Explanation: Adds a component or value to its parent container or collection.
        buttonPanel.add(countLabel);
        // Explanation: Adds a component or value to its parent container or collection.
        buttonPanel.add(markAllBtn);

        // Explanation: Adds a component or value to its parent container or collection.
        header.add(title, BorderLayout.WEST);
        // Explanation: Adds a component or value to its parent container or collection.
        header.add(buttonPanel, BorderLayout.EAST);

        // Notification list
        // Explanation: Updates `notificationList` with a new value.
        notificationList = new JPanel();
        // Explanation: Calls `setLayout` to configure this object.
        notificationList.setLayout(new BoxLayout(notificationList, BoxLayout.Y_AXIS));
        // Explanation: Calls `setOpaque` to configure this object.
        notificationList.setOpaque(false);

        // Explanation: Calls `populateNotifications` to perform this step.
        populateNotifications();

        // Explanation: Declares and initializes `sp` as `JScrollPane`.
        JScrollPane sp = new JScrollPane(notificationList);
        // Explanation: Calls `setBorder` to configure this object.
        sp.setBorder(null);
        // Explanation: Calls `setOpaque` to configure this object.
        sp.setOpaque(false);
        // Explanation: Calls `getViewport` to configure this object.
        sp.getViewport().setOpaque(false);
        // Explanation: Calls `getVerticalScrollBar` to configure this object.
        sp.getVerticalScrollBar().setUnitIncrement(16);

        // Explanation: Calls `add` to perform this step.
        add(header, BorderLayout.NORTH);
        // Explanation: Calls `add` to perform this step.
        add(sp, BorderLayout.CENTER);
    // Explanation: Closes the current block.
    }

    // Explanation: Defines the `populateNotifications` method.
    private void populateNotifications() {
        // Explanation: Calls `removeAll` to perform this step.
        notificationList.removeAll();

        // Explanation: Declares and initializes `notifications` as `List<Notification>`.
        List<Notification> notifications = facade.getNotificationsForCurrentUser();

        // Explanation: Checks this condition before running the following block.
        if (notifications.isEmpty()) {
            // Explanation: Declares and initializes `emptyPanel` as `JPanel`.
            JPanel emptyPanel = new JPanel(new BorderLayout());
            // Explanation: Calls `setOpaque` to configure this object.
            emptyPanel.setOpaque(false);
            // Explanation: Calls `setBorder` to configure this object.
            emptyPanel.setBorder(new EmptyBorder(60, 0, 0, 0));
            // Explanation: Declares and initializes `emptyLabel` as `JLabel`.
            JLabel emptyLabel = new JLabel("No notifications yet", SwingConstants.CENTER);
            // Explanation: Calls `setFont` to configure this object.
            emptyLabel.setFont(StyledComponents.FONT_BODY);
            // Explanation: Calls `setForeground` to configure this object.
            emptyLabel.setForeground(StyledComponents.TEXT_SECONDARY);
            // Explanation: Adds a component or value to its parent container or collection.
            emptyPanel.add(emptyLabel, BorderLayout.NORTH);
            // Explanation: Adds a component or value to its parent container or collection.
            notificationList.add(emptyPanel);
            // Explanation: Exits the current method without returning a value.
            return;
        // Explanation: Closes the current block.
        }

        // Explanation: Declares and initializes `fmt` as `DateTimeFormatter`.
        DateTimeFormatter fmt = DateTimeFormatter.ofPattern("dd MMM yyyy, HH:mm");

        // Show newest first
        // Explanation: Starts a loop over the values defined in this header.
        for (int i = notifications.size() - 1; i >= 0; i--) {
            // Explanation: Declares and initializes `notif` as `Notification`.
            Notification notif = notifications.get(i);
            // Explanation: Declares and initializes `card` as `JPanel`.
            JPanel card = createNotificationCard(notif, fmt);
            // Explanation: Calls `setMaximumSize` to configure this object.
            card.setMaximumSize(new Dimension(Integer.MAX_VALUE, 100));
            // Explanation: Calls `setAlignmentX` to configure this object.
            card.setAlignmentX(LEFT_ALIGNMENT);
            // Explanation: Adds a component or value to its parent container or collection.
            notificationList.add(card);
            // Explanation: Adds a component or value to its parent container or collection.
            notificationList.add(Box.createVerticalStrut(8));
        // Explanation: Closes the current block.
        }
    // Explanation: Closes the current block.
    }

    // Explanation: Defines the `createNotificationCard` method.
    private JPanel createNotificationCard(Notification notif, DateTimeFormatter fmt) {
        // Explanation: Declares and initializes `card` as `JPanel`.
        JPanel card = new JPanel(new BorderLayout(10, 5));
        // Explanation: Calls `setBackground` to configure this object.
        card.setBackground(notif.isRead() ? StyledComponents.BG_CARD : new Color(240, 248, 255));
        // Explanation: Starts the call to `setBorder`.
        card.setBorder(BorderFactory.createCompoundBorder(
                // Explanation: Starts the call to `createLineBorder`.
                BorderFactory.createLineBorder(
                        // Explanation: Starts the multi-line call to `isRead`.
                        notif.isRead() ? StyledComponents.BORDER_COLOR : StyledComponents.ACCENT, 1, true),
                // Explanation: Supplies the remaining arguments and completes the call to `isRead`.
                new EmptyBorder(15, 20, 15, 20)
        // Explanation: Closes the nested method calls in this statement.
        ));

        // Icon/type indicator
        // Explanation: Declares and initializes `leftPanel` as `JPanel`.
        JPanel leftPanel = new JPanel(new BorderLayout());
        // Explanation: Calls `setOpaque` to configure this object.
        leftPanel.setOpaque(false);
        // Explanation: Calls `setPreferredSize` to configure this object.
        leftPanel.setPreferredSize(new Dimension(10, 0));
        // Explanation: Declares and initializes `typeColor` as `Color`.
        Color typeColor = getTypeColor(notif.getType());
        // Explanation: Declares and initializes `indicator` as `JPanel`.
        JPanel indicator = new JPanel();
        // Explanation: Calls `setBackground` to configure this object.
        indicator.setBackground(typeColor);
        // Explanation: Calls `setPreferredSize` to configure this object.
        indicator.setPreferredSize(new Dimension(4, 0));
        // Explanation: Adds a component or value to its parent container or collection.
        leftPanel.add(indicator, BorderLayout.WEST);

        // Content
        // Explanation: Declares and initializes `contentPanel` as `JPanel`.
        JPanel contentPanel = new JPanel(new BorderLayout(5, 3));
        // Explanation: Calls `setOpaque` to configure this object.
        contentPanel.setOpaque(false);

        // Explanation: Declares and initializes `titleRow` as `JPanel`.
        JPanel titleRow = new JPanel(new BorderLayout());
        // Explanation: Calls `setOpaque` to configure this object.
        titleRow.setOpaque(false);

        // Explanation: Declares and initializes `titleLabel` as `JLabel`.
        JLabel titleLabel = new JLabel(notif.getTitle());
        // Explanation: Calls `setFont` to configure this object.
        titleLabel.setFont(new Font("Segoe UI", notif.isRead() ? Font.PLAIN : Font.BOLD, 14));
        // Explanation: Calls `setForeground` to configure this object.
        titleLabel.setForeground(StyledComponents.PRIMARY);

        // Explanation: Declares and initializes `timeLabel` as `JLabel`.
        JLabel timeLabel = new JLabel(notif.getTimestamp().format(fmt));
        // Explanation: Calls `setFont` to configure this object.
        timeLabel.setFont(StyledComponents.FONT_SMALL);
        // Explanation: Calls `setForeground` to configure this object.
        timeLabel.setForeground(StyledComponents.TEXT_SECONDARY);

        // Explanation: Adds a component or value to its parent container or collection.
        titleRow.add(titleLabel, BorderLayout.WEST);
        // Explanation: Adds a component or value to its parent container or collection.
        titleRow.add(timeLabel, BorderLayout.EAST);

        // Explanation: Declares and initializes `messageLabel` as `JLabel`.
        JLabel messageLabel = new JLabel(notif.getMessage());
        // Explanation: Calls `setFont` to configure this object.
        messageLabel.setFont(StyledComponents.FONT_BODY);
        // Explanation: Calls `setForeground` to configure this object.
        messageLabel.setForeground(StyledComponents.TEXT_SECONDARY);

        // Explanation: Declares and initializes `typeLabel` as `JLabel`.
        JLabel typeLabel = new JLabel(formatType(notif.getType()));
        // Explanation: Calls `setFont` to configure this object.
        typeLabel.setFont(new Font("Segoe UI", Font.BOLD, 10));
        // Explanation: Calls `setForeground` to configure this object.
        typeLabel.setForeground(typeColor);

        // Explanation: Adds a component or value to its parent container or collection.
        contentPanel.add(titleRow, BorderLayout.NORTH);
        // Explanation: Adds a component or value to its parent container or collection.
        contentPanel.add(messageLabel, BorderLayout.CENTER);
        // Explanation: Adds a component or value to its parent container or collection.
        contentPanel.add(typeLabel, BorderLayout.SOUTH);

        // Explanation: Adds a component or value to its parent container or collection.
        card.add(leftPanel, BorderLayout.WEST);
        // Explanation: Adds a component or value to its parent container or collection.
        card.add(contentPanel, BorderLayout.CENTER);

        // Click to mark as read
        // Explanation: Checks this condition before running the following block.
        if (!notif.isRead()) {
            // Explanation: Calls `setCursor` to configure this object.
            card.setCursor(new Cursor(Cursor.HAND_CURSOR));
            // Explanation: Defines the `addMouseListener` method.
            card.addMouseListener(new java.awt.event.MouseAdapter() {
                // Explanation: Marks the following method as an override of inherited behavior.
                @Override
                // Explanation: Defines the `mouseClicked` method.
                public void mouseClicked(java.awt.event.MouseEvent e) {
                    // Explanation: Calls `markAsRead` to perform this step.
                    notif.markAsRead();
                    // Explanation: Calls `refresh` to perform this step.
                    refresh();
                // Explanation: Closes the current block.
                }
            // Explanation: Closes the current block and completes the surrounding method call.
            });
        // Explanation: Closes the current block.
        }

        // Explanation: Returns this value from the current method.
        return card;
    // Explanation: Closes the current block.
    }

    // Explanation: Defines the `getTypeColor` method.
    private Color getTypeColor(Notification.Type type) {
        // Explanation: Returns this value from the current method.
        return switch (type) {
            // Explanation: Performs the operation written on this line.
            case BOOKING_CONFIRMED -> StyledComponents.SUCCESS;
            // Explanation: Performs the operation written on this line.
            case BOOKING_CANCELLED -> StyledComponents.DANGER;
            // Explanation: Performs the operation written on this line.
            case MAINTENANCE_UPDATE -> StyledComponents.WARNING;
            // Explanation: Performs the operation written on this line.
            case ANNOUNCEMENT -> StyledComponents.ACCENT;
        // Explanation: Closes the current block and ends the statement.
        };
    // Explanation: Closes the current block.
    }

    // Explanation: Defines the `formatType` method.
    private String formatType(Notification.Type type) {
        // Explanation: Returns this value from the current method.
        return switch (type) {
            // Explanation: Performs the operation written on this line.
            case BOOKING_CONFIRMED -> "BOOKING CONFIRMED";
            // Explanation: Performs the operation written on this line.
            case BOOKING_CANCELLED -> "BOOKING CANCELLED";
            // Explanation: Performs the operation written on this line.
            case MAINTENANCE_UPDATE -> "MAINTENANCE UPDATE";
            // Explanation: Performs the operation written on this line.
            case ANNOUNCEMENT -> "ANNOUNCEMENT";
        // Explanation: Closes the current block and ends the statement.
        };
    // Explanation: Closes the current block.
    }

    // Explanation: Defines the `refresh` method.
    public void refresh() {
        // Explanation: Calls `buildUI` to perform this step.
        buildUI();
        // Explanation: Calls `revalidate` to perform this step.
        revalidate();
        // Explanation: Calls `repaint` to perform this step.
        repaint();
    // Explanation: Closes the current block.
    }
// Explanation: Closes the current block.
}
