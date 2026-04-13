// Explanation: Declares the `com.scms.gui.panels` package for this source file.
package com.scms.gui.panels;

// Explanation: Imports `StyledComponents` for use in this file.
import com.scms.gui.components.StyledComponents;
// Explanation: Imports all types from `com.scms.model` for use in this file.
import com.scms.model.*;
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
// Explanation: Imports `Map` for use in this file.
import java.util.Map;

// Explanation: Declares the `DashboardPanel` class with its inheritance or interface information.
public class DashboardPanel extends JPanel {

    // Explanation: Declares the private final field `facade` of type `CampusFacade`.
    private final CampusFacade facade;
    // Explanation: Declares the private field `statsPanel` of type `JPanel`.
    private JPanel statsPanel;
    // Explanation: Declares the private field `recentBookingsPanel` of type `JPanel`.
    private JPanel recentBookingsPanel;
    // Explanation: Declares the private field `maintenanceOverviewPanel` of type `JPanel`.
    private JPanel maintenanceOverviewPanel;
    // Explanation: Declares the private field `analyticsPanel` of type `JPanel`.
    private JPanel analyticsPanel;

    // Explanation: Defines the constructor for `DashboardPanel`.
    public DashboardPanel(CampusFacade facade) {
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
        // Explanation: Declares and initializes `user` as `User`.
        User user = facade.getCurrentUser();
        // Explanation: Declares and initializes `title` as `JLabel`.
        JLabel title = new JLabel("Dashboard");
        // Explanation: Calls `setFont` to configure this object.
        title.setFont(StyledComponents.FONT_TITLE);
        // Explanation: Calls `setForeground` to configure this object.
        title.setForeground(StyledComponents.PRIMARY);
        // Explanation: Declares and initializes `welcome` as `JLabel`.
        JLabel welcome = new JLabel("Welcome back, " + (user != null ? user.getName() : "User"));
        // Explanation: Calls `setFont` to configure this object.
        welcome.setFont(StyledComponents.FONT_BODY);
        // Explanation: Calls `setForeground` to configure this object.
        welcome.setForeground(StyledComponents.TEXT_SECONDARY);
        // Explanation: Adds a component or value to its parent container or collection.
        header.add(title, BorderLayout.NORTH);
        // Explanation: Adds a component or value to its parent container or collection.
        header.add(welcome, BorderLayout.SOUTH);
        // Explanation: Calls `setBorder` to configure this object.
        header.setBorder(new EmptyBorder(0, 0, 20, 0));

        // Main scrollable content
        // Explanation: Declares and initializes `content` as `JPanel`.
        JPanel content = new JPanel();
        // Explanation: Calls `setLayout` to configure this object.
        content.setLayout(new BoxLayout(content, BoxLayout.Y_AXIS));
        // Explanation: Calls `setOpaque` to configure this object.
        content.setOpaque(false);

        // Stats row
        // Explanation: Updates `statsPanel` with a new value.
        statsPanel = new JPanel(new GridLayout(1, 4, 15, 0));
        // Explanation: Calls `setOpaque` to configure this object.
        statsPanel.setOpaque(false);
        // Explanation: Calls `setMaximumSize` to configure this object.
        statsPanel.setMaximumSize(new Dimension(Integer.MAX_VALUE, 120));
        // Explanation: Calls `setAlignmentX` to configure this object.
        statsPanel.setAlignmentX(LEFT_ALIGNMENT);
        // Explanation: Calls `populateStats` to perform this step.
        populateStats();
        // Explanation: Adds a component or value to its parent container or collection.
        content.add(statsPanel);
        // Explanation: Adds a component or value to its parent container or collection.
        content.add(Box.createVerticalStrut(20));

        // Two column layout
        // Explanation: Declares and initializes `columns` as `JPanel`.
        JPanel columns = new JPanel(new GridLayout(1, 2, 15, 0));
        // Explanation: Calls `setOpaque` to configure this object.
        columns.setOpaque(false);
        // Explanation: Calls `setMaximumSize` to configure this object.
        columns.setMaximumSize(new Dimension(Integer.MAX_VALUE, 350));
        // Explanation: Calls `setAlignmentX` to configure this object.
        columns.setAlignmentX(LEFT_ALIGNMENT);

        // Explanation: Updates `recentBookingsPanel` with a new value.
        recentBookingsPanel = createRecentBookingsCard();
        // Explanation: Updates `maintenanceOverviewPanel` with a new value.
        maintenanceOverviewPanel = createMaintenanceCard();
        // Explanation: Adds a component or value to its parent container or collection.
        columns.add(recentBookingsPanel);
        // Explanation: Adds a component or value to its parent container or collection.
        columns.add(maintenanceOverviewPanel);
        // Explanation: Adds a component or value to its parent container or collection.
        content.add(columns);
        // Explanation: Adds a component or value to its parent container or collection.
        content.add(Box.createVerticalStrut(20));

        // Analytics row (admin only)
        // Explanation: Checks this condition before running the following block.
        if (user != null && user.getRole() == UserRole.ADMINISTRATOR) {
            // Explanation: Updates `analyticsPanel` with a new value.
            analyticsPanel = createAnalyticsCard();
            // Explanation: Calls `setMaximumSize` to configure this object.
            analyticsPanel.setMaximumSize(new Dimension(Integer.MAX_VALUE, 250));
            // Explanation: Calls `setAlignmentX` to configure this object.
            analyticsPanel.setAlignmentX(LEFT_ALIGNMENT);
            // Explanation: Adds a component or value to its parent container or collection.
            content.add(analyticsPanel);
        // Explanation: Closes the current block.
        }

        // Explanation: Declares and initializes `scroll` as `JScrollPane`.
        JScrollPane scroll = new JScrollPane(content);
        // Explanation: Calls `setBorder` to configure this object.
        scroll.setBorder(null);
        // Explanation: Calls `setOpaque` to configure this object.
        scroll.setOpaque(false);
        // Explanation: Calls `getViewport` to configure this object.
        scroll.getViewport().setOpaque(false);
        // Explanation: Calls `getVerticalScrollBar` to configure this object.
        scroll.getVerticalScrollBar().setUnitIncrement(16);

        // Explanation: Calls `add` to perform this step.
        add(header, BorderLayout.NORTH);
        // Explanation: Calls `add` to perform this step.
        add(scroll, BorderLayout.CENTER);
    // Explanation: Closes the current block.
    }

    // Explanation: Defines the `populateStats` method.
    private void populateStats() {
        // Explanation: Calls `removeAll` to perform this step.
        statsPanel.removeAll();
        // Explanation: Declares and initializes `totalRooms` as `int`.
        int totalRooms = facade.getActiveRooms().size();
        // Explanation: Declares and initializes `totalBookings` as `int`.
        int totalBookings = facade.getTotalBookingsCount();
        // Explanation: Declares and initializes `activeMaintenance` as `int`.
        int activeMaintenance = facade.getActiveMaintenanceCount();
        // Explanation: Declares and initializes `notifications` as `int`.
        int notifications = facade.getUnreadNotificationCount();

        // Explanation: Adds a component or value to its parent container or collection.
        statsPanel.add(StyledComponents.createStatCard("Active Rooms", String.valueOf(totalRooms), StyledComponents.ACCENT));
        // Explanation: Adds a component or value to its parent container or collection.
        statsPanel.add(StyledComponents.createStatCard("Total Bookings", String.valueOf(totalBookings), StyledComponents.SUCCESS));
        // Explanation: Adds a component or value to its parent container or collection.
        statsPanel.add(StyledComponents.createStatCard("Open Maintenance", String.valueOf(activeMaintenance), StyledComponents.WARNING));
        // Explanation: Adds a component or value to its parent container or collection.
        statsPanel.add(StyledComponents.createStatCard("Unread Notifications", String.valueOf(notifications), StyledComponents.DANGER));
    // Explanation: Closes the current block.
    }

    // Explanation: Defines the `createRecentBookingsCard` method.
    private JPanel createRecentBookingsCard() {
        // Explanation: Declares and initializes `card` as `JPanel`.
        JPanel card = StyledComponents.createCard("Recent Bookings");

        // Explanation: Declares and initializes `bookings` as `List<Booking>`.
        List<Booking> bookings = facade.getAllBookings();
        // Explanation: Declares and initializes `display` as `int`.
        int display = Math.min(bookings.size(), 5);

        // Explanation: Declares and initializes `cols` as `String[]`.
        String[] cols = {"ID", "Room", "Date", "Time", "Status"};
        // Explanation: Declares and initializes `data` as `Object[][]`.
        Object[][] data = new Object[display][5];
        // Explanation: Declares and initializes `dateFmt` as `DateTimeFormatter`.
        DateTimeFormatter dateFmt = DateTimeFormatter.ofPattern("dd MMM yyyy");
        // Explanation: Declares and initializes `timeFmt` as `DateTimeFormatter`.
        DateTimeFormatter timeFmt = DateTimeFormatter.ofPattern("HH:mm");

        // Explanation: Starts a loop over the values defined in this header.
        for (int i = 0; i < display; i++) {
            // Explanation: Declares and initializes `b` as `Booking`.
            Booking b = bookings.get(bookings.size() - 1 - i);
            // Explanation: Performs the operation written on this line.
            Room room = facade.getActiveRooms().stream()
                    // Explanation: Applies a filter step to the current stream.
                    .filter(r -> r.getRoomId().equals(b.getRoomId())).findFirst()
                    // Explanation: Provides a fallback value when the stream result is empty.
                    .orElse(null);
            // Explanation: Checks this condition before running the following block.
            if (room == null) {
                // Check all rooms
                // Explanation: Performs the operation written on this line.
                room = facade.getAllRooms().stream()
                        // Explanation: Applies a filter step to the current stream.
                        .filter(r -> r.getRoomId().equals(b.getRoomId())).findFirst()
                        // Explanation: Provides a fallback value when the stream result is empty.
                        .orElse(null);
            // Explanation: Closes the current block.
            }
            // Explanation: Performs the operation written on this line.
            data[i] = new Object[]{
                    // Explanation: Adds another value to the inline array or object initializer.
                    b.getBookingId(),
                    // Explanation: Adds another value to the inline array or object initializer.
                    room != null ? room.getName() : b.getRoomId(),
                    // Explanation: Adds another value to the inline array or object initializer.
                    b.getDate().format(dateFmt),
                    // Explanation: Adds another value to the inline array or object initializer.
                    b.getStartTime().format(timeFmt) + "-" + b.getEndTime().format(timeFmt),
                    // Explanation: Adds another value inside the inline array or object initializer.
                    b.getStatus().name()
            // Explanation: Closes the inline array or object initializer.
            };
        // Explanation: Closes the current block.
        }

        // Explanation: Declares and initializes `table` as `JTable`.
        JTable table = StyledComponents.createStyledTable(data, cols);
        // Explanation: Declares and initializes `sp` as `JScrollPane`.
        JScrollPane sp = new JScrollPane(table);
        // Explanation: Calls `setBorder` to configure this object.
        sp.setBorder(null);
        // Explanation: Adds a component or value to its parent container or collection.
        card.add(sp, BorderLayout.CENTER);

        // Explanation: Returns this value from the current method.
        return card;
    // Explanation: Closes the current block.
    }

    // Explanation: Defines the `createMaintenanceCard` method.
    private JPanel createMaintenanceCard() {
        // Explanation: Declares and initializes `card` as `JPanel`.
        JPanel card = StyledComponents.createCard("Maintenance Overview");

        // Explanation: Declares and initializes `requests` as `List<MaintenanceRequest>`.
        List<MaintenanceRequest> requests = facade.getAllMaintenanceRequests();
        // Explanation: Declares and initializes `display` as `int`.
        int display = Math.min(requests.size(), 5);

        // Explanation: Declares and initializes `cols` as `String[]`.
        String[] cols = {"ID", "Room", "Urgency", "Status"};
        // Explanation: Declares and initializes `data` as `Object[][]`.
        Object[][] data = new Object[display][4];

        // Explanation: Starts a loop over the values defined in this header.
        for (int i = 0; i < display; i++) {
            // Explanation: Declares and initializes `r` as `MaintenanceRequest`.
            MaintenanceRequest r = requests.get(requests.size() - 1 - i);
            // Explanation: Performs the operation written on this line.
            data[i] = new Object[]{
                    // Explanation: Adds another value to the inline array or object initializer.
                    r.getRequestId(),
                    // Explanation: Adds another value to the inline array or object initializer.
                    r.getRoomId(),
                    // Explanation: Adds another value to the inline array or object initializer.
                    r.getUrgency().name(),
                    // Explanation: Adds another value inside the inline array or object initializer.
                    r.getStatus().name()
            // Explanation: Closes the inline array or object initializer.
            };
        // Explanation: Closes the current block.
        }

        // Explanation: Declares and initializes `table` as `JTable`.
        JTable table = StyledComponents.createStyledTable(data, cols);
        // Explanation: Declares and initializes `sp` as `JScrollPane`.
        JScrollPane sp = new JScrollPane(table);
        // Explanation: Calls `setBorder` to configure this object.
        sp.setBorder(null);
        // Explanation: Adds a component or value to its parent container or collection.
        card.add(sp, BorderLayout.CENTER);

        // Explanation: Returns this value from the current method.
        return card;
    // Explanation: Closes the current block.
    }

    // Explanation: Defines the `createAnalyticsCard` method.
    private JPanel createAnalyticsCard() {
        // Explanation: Declares and initializes `card` as `JPanel`.
        JPanel card = StyledComponents.createCard("Analytics Overview");

        // Explanation: Declares and initializes `analyticsContent` as `JPanel`.
        JPanel analyticsContent = new JPanel(new GridLayout(1, 3, 15, 0));
        // Explanation: Calls `setOpaque` to configure this object.
        analyticsContent.setOpaque(false);

        // Most booked room
        // Explanation: Declares and initializes `mostBooked` as `String`.
        String mostBooked = facade.getMostBookedRoom();
        // Explanation: Performs the operation written on this line.
        Room room = facade.getAllRooms().stream()
                // Explanation: Applies a filter step to the current stream.
                .filter(r -> r.getRoomId().equals(mostBooked)).findFirst().orElse(null);
        // Explanation: Starts the signature for the `createAnalyticsItem` method.
        JPanel mostBookedCard = createAnalyticsItem("Most Booked Room",
                // Explanation: Finishes the parameter list for the `createAnalyticsItem` method.
                room != null ? room.getName() : mostBooked, StyledComponents.ACCENT);

        // Users count
        // Explanation: Starts the signature for the `createAnalyticsItem` method.
        JPanel usersCard = createAnalyticsItem("Total Users",
                // Explanation: Finishes the parameter list for the `createAnalyticsItem` method.
                String.valueOf(facade.getAllUsers().size()), StyledComponents.SUCCESS);

        // Maintenance by urgency
        // Explanation: Performs the operation written on this line.
        long highUrgency = facade.getAllMaintenanceRequests().stream()
                // Explanation: Applies a filter step to the current stream.
                .filter(r -> r.getUrgency() == MaintenanceRequest.Urgency.HIGH ||
                        // Explanation: Performs the operation written on this line.
                        r.getUrgency() == MaintenanceRequest.Urgency.CRITICAL)
                // Explanation: Applies a filter step to the current stream.
                .filter(r -> r.getStatus() != MaintenanceRequest.Status.COMPLETED)
                // Explanation: Counts the remaining values in the stream.
                .count();
        // Explanation: Starts the signature for the `createAnalyticsItem` method.
        JPanel urgentCard = createAnalyticsItem("High Priority Issues",
                // Explanation: Finishes the parameter list for the `createAnalyticsItem` method.
                String.valueOf(highUrgency), StyledComponents.DANGER);

        // Explanation: Adds a component or value to its parent container or collection.
        analyticsContent.add(mostBookedCard);
        // Explanation: Adds a component or value to its parent container or collection.
        analyticsContent.add(usersCard);
        // Explanation: Adds a component or value to its parent container or collection.
        analyticsContent.add(urgentCard);

        // Explanation: Adds a component or value to its parent container or collection.
        card.add(analyticsContent, BorderLayout.CENTER);
        // Explanation: Returns this value from the current method.
        return card;
    // Explanation: Closes the current block.
    }

    // Explanation: Defines the `createAnalyticsItem` method.
    private JPanel createAnalyticsItem(String label, String value, Color color) {
        // Explanation: Declares and initializes `panel` as `JPanel`.
        JPanel panel = new JPanel();
        // Explanation: Calls `setLayout` to configure this object.
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        // Explanation: Calls `setBackground` to configure this object.
        panel.setBackground(new Color(color.getRed(), color.getGreen(), color.getBlue(), 15));
        // Explanation: Calls `setBorder` to configure this object.
        panel.setBorder(new EmptyBorder(15, 15, 15, 15));

        // Explanation: Declares and initializes `valLabel` as `JLabel`.
        JLabel valLabel = new JLabel(value);
        // Explanation: Calls `setFont` to configure this object.
        valLabel.setFont(new Font("Segoe UI", Font.BOLD, 18));
        // Explanation: Calls `setForeground` to configure this object.
        valLabel.setForeground(color);
        // Explanation: Calls `setAlignmentX` to configure this object.
        valLabel.setAlignmentX(LEFT_ALIGNMENT);

        // Explanation: Declares and initializes `nameLabel` as `JLabel`.
        JLabel nameLabel = new JLabel(label);
        // Explanation: Calls `setFont` to configure this object.
        nameLabel.setFont(StyledComponents.FONT_SMALL);
        // Explanation: Calls `setForeground` to configure this object.
        nameLabel.setForeground(StyledComponents.TEXT_SECONDARY);
        // Explanation: Calls `setAlignmentX` to configure this object.
        nameLabel.setAlignmentX(LEFT_ALIGNMENT);

        // Explanation: Adds a component or value to its parent container or collection.
        panel.add(valLabel);
        // Explanation: Adds a component or value to its parent container or collection.
        panel.add(Box.createVerticalStrut(5));
        // Explanation: Adds a component or value to its parent container or collection.
        panel.add(nameLabel);

        // Explanation: Returns this value from the current method.
        return panel;
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
