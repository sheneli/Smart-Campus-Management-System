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
// Explanation: Imports `DefaultTableModel` for use in this file.
import javax.swing.table.DefaultTableModel;
// Explanation: Imports all types from `java.awt` for use in this file.
import java.awt.*;
// Explanation: Imports `LocalDate` for use in this file.
import java.time.LocalDate;
// Explanation: Imports `LocalTime` for use in this file.
import java.time.LocalTime;
// Explanation: Imports `DateTimeFormatter` for use in this file.
import java.time.format.DateTimeFormatter;
// Explanation: Imports `DateTimeParseException` for use in this file.
import java.time.format.DateTimeParseException;
// Explanation: Imports `List` for use in this file.
import java.util.List;

// Explanation: Declares the `BookingPanel` class with its inheritance or interface information.
public class BookingPanel extends JPanel {

    // Explanation: Declares the private final field `facade` of type `CampusFacade`.
    private final CampusFacade facade;
    // Explanation: Declares the private field `bookingTable` of type `JTable`.
    private JTable bookingTable;
    // Explanation: Declares the private field `tableModel` of type `DefaultTableModel`.
    private DefaultTableModel tableModel;

    // Explanation: Defines the constructor for `BookingPanel`.
    public BookingPanel(CampusFacade facade) {
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
        JLabel title = new JLabel("Room Bookings");
        // Explanation: Calls `setFont` to configure this object.
        title.setFont(StyledComponents.FONT_TITLE);
        // Explanation: Calls `setForeground` to configure this object.
        title.setForeground(StyledComponents.PRIMARY);

        // Explanation: Declares and initializes `buttonPanel` as `JPanel`.
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT, 10, 0));
        // Explanation: Calls `setOpaque` to configure this object.
        buttonPanel.setOpaque(false);

        // Explanation: Declares and initializes `newBookingBtn` as `JButton`.
        JButton newBookingBtn = StyledComponents.createPrimaryButton("+ New Booking");
        // Explanation: Registers an action listener for this component.
        newBookingBtn.addActionListener(e -> showNewBookingDialog());

        // Explanation: Declares and initializes `cancelBtn` as `JButton`.
        JButton cancelBtn = StyledComponents.createDangerButton("Cancel Booking");
        // Explanation: Registers an action listener for this component.
        cancelBtn.addActionListener(e -> cancelSelectedBooking());

        // Explanation: Adds a component or value to its parent container or collection.
        buttonPanel.add(newBookingBtn);
        // Explanation: Adds a component or value to its parent container or collection.
        buttonPanel.add(cancelBtn);

        // Explanation: Adds a component or value to its parent container or collection.
        header.add(title, BorderLayout.WEST);
        // Explanation: Adds a component or value to its parent container or collection.
        header.add(buttonPanel, BorderLayout.EAST);

        // Table
        // Explanation: Declares and initializes `tableCard` as `JPanel`.
        JPanel tableCard = StyledComponents.createCard(null);
        // Explanation: Declares and initializes `columns` as `String[]`.
        String[] columns = {"Booking ID", "Room", "Booked By", "Date", "Time", "Purpose", "Status"};
        // Explanation: Creates an anonymous `DefaultTableModel` implementation.
        tableModel = new DefaultTableModel(columns, 0) {
            // Explanation: Marks the following method as an override of inherited behavior.
            @Override
            // Explanation: Defines the `isCellEditable` method.
            public boolean isCellEditable(int row, int column) { return false; }
        // Explanation: Closes the current block and ends the statement.
        };
        // Explanation: Updates `bookingTable` with a new value.
        bookingTable = new JTable(tableModel);
        // Explanation: Calls `setFont` to configure this object.
        bookingTable.setFont(StyledComponents.FONT_BODY);
        // Explanation: Calls `setRowHeight` to configure this object.
        bookingTable.setRowHeight(40);
        // Explanation: Calls `setSelectionMode` to configure this object.
        bookingTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        // Explanation: Calls `setShowGrid` to configure this object.
        bookingTable.setShowGrid(false);
        // Explanation: Calls `setShowHorizontalLines` to configure this object.
        bookingTable.setShowHorizontalLines(true);
        // Explanation: Calls `setGridColor` to configure this object.
        bookingTable.setGridColor(StyledComponents.BORDER_COLOR);
        // Explanation: Calls `getTableHeader` to configure this object.
        bookingTable.getTableHeader().setFont(new Font("Segoe UI", Font.BOLD, 13));
        // Explanation: Calls `getTableHeader` to configure this object.
        bookingTable.getTableHeader().setBackground(new Color(248, 249, 250));
        // Explanation: Calls `getTableHeader` to configure this object.
        bookingTable.getTableHeader().setPreferredSize(new Dimension(0, 44));
        // Explanation: Calls `setSelectionBackground` to configure this object.
        bookingTable.setSelectionBackground(new Color(0, 166, 214, 30));

        // Explanation: Calls `populateTable` to perform this step.
        populateTable();

        // Explanation: Declares and initializes `sp` as `JScrollPane`.
        JScrollPane sp = new JScrollPane(bookingTable);
        // Explanation: Calls `setBorder` to configure this object.
        sp.setBorder(null);
        // Explanation: Adds a component or value to its parent container or collection.
        tableCard.add(sp, BorderLayout.CENTER);

        // Explanation: Calls `add` to perform this step.
        add(header, BorderLayout.NORTH);
        // Explanation: Calls `add` to perform this step.
        add(tableCard, BorderLayout.CENTER);
    // Explanation: Closes the current block.
    }

    // Explanation: Defines the `populateTable` method.
    private void populateTable() {
        // Explanation: Calls `setRowCount` to configure this object.
        tableModel.setRowCount(0);
        // Explanation: Declares and initializes `currentUser` as `User`.
        User currentUser = facade.getCurrentUser();
        // Explanation: Declares `bookings` as `List<Booking>` for later use.
        List<Booking> bookings;

        // Explanation: Checks this condition before running the following block.
        if (currentUser != null && currentUser.getRole() == UserRole.ADMINISTRATOR) {
            // Explanation: Updates `bookings` with a new value.
            bookings = facade.getAllBookings();
        // Explanation: Checks another condition after closing the previous branch.
        } else if (currentUser != null) {
            // Explanation: Updates `bookings` with a new value.
            bookings = facade.getBookingsForUser(currentUser.getUserId());
        // Explanation: Runs this block when the earlier condition did not match.
        } else {
            // Explanation: Updates `bookings` with a new value.
            bookings = List.of();
        // Explanation: Closes the current block.
        }

        // Explanation: Declares and initializes `dateFmt` as `DateTimeFormatter`.
        DateTimeFormatter dateFmt = DateTimeFormatter.ofPattern("dd MMM yyyy");
        // Explanation: Declares and initializes `timeFmt` as `DateTimeFormatter`.
        DateTimeFormatter timeFmt = DateTimeFormatter.ofPattern("HH:mm");

        // Explanation: Declares and initializes `allUsers` as `List<User>`.
        List<User> allUsers = facade.getAllUsers();
        // Explanation: Declares and initializes `allRooms` as `List<Room>`.
        List<Room> allRooms = facade.getAllRooms();

        // Explanation: Starts a loop over the values defined in this header.
        for (Booking b : bookings) {
            // Explanation: Performs the operation written on this line.
            String roomName = allRooms.stream()
                    // Explanation: Applies a filter step to the current stream.
                    .filter(r -> r.getRoomId().equals(b.getRoomId()))
                    // Explanation: Takes the first matching value from the stream.
                    .findFirst().map(Room::getName).orElse(b.getRoomId());
            // Explanation: Performs the operation written on this line.
            String userName = allUsers.stream()
                    // Explanation: Applies a filter step to the current stream.
                    .filter(u -> u.getUserId().equals(b.getUserId()))
                    // Explanation: Takes the first matching value from the stream.
                    .findFirst().map(User::getName).orElse(b.getUserId());

            // Explanation: Defines the `addRow` method.
            tableModel.addRow(new Object[]{
                    // Explanation: Adds another value to the inline array or object initializer.
                    b.getBookingId(),
                    // Explanation: Adds another value to the inline array or object initializer.
                    roomName,
                    // Explanation: Adds another value to the inline array or object initializer.
                    userName,
                    // Explanation: Adds another value to the inline array or object initializer.
                    b.getDate().format(dateFmt),
                    // Explanation: Adds another value to the inline array or object initializer.
                    b.getStartTime().format(timeFmt) + " - " + b.getEndTime().format(timeFmt),
                    // Explanation: Adds another value to the inline array or object initializer.
                    b.getPurpose(),
                    // Explanation: Adds another value inside the inline array or object initializer.
                    b.getStatus().name()
            // Explanation: Closes the inline array or object initializer.
            });
        // Explanation: Closes the current block.
        }
    // Explanation: Closes the current block.
    }

    // Explanation: Defines the `showNewBookingDialog` method.
    private void showNewBookingDialog() {
        // Explanation: Declares and initializes `panel` as `JPanel`.
        JPanel panel = new JPanel(new GridLayout(5, 2, 10, 10));
        // Explanation: Calls `setBorder` to configure this object.
        panel.setBorder(new EmptyBorder(10, 10, 10, 10));

        // Room selector
        // Explanation: Declares and initializes `rooms` as `List<Room>`.
        List<Room> rooms = facade.getActiveRooms();
        // Explanation: Declares and initializes `roomNames` as `String[]`.
        String[] roomNames = rooms.stream().map(r -> r.getRoomId() + " - " + r.getName()).toArray(String[]::new);
        // Explanation: Declares and initializes `roomCombo` as `JComboBox<String>`.
        JComboBox<String> roomCombo = StyledComponents.createStyledComboBox(roomNames);

        // Date
        // Explanation: Declares and initializes `dateField` as `JTextField`.
        JTextField dateField = StyledComponents.createStyledTextField();
        // Explanation: Calls `setText` to configure this object.
        dateField.setText(LocalDate.now().plusDays(1).format(DateTimeFormatter.ofPattern("yyyy-MM-dd")));

        // Times
        // Explanation: Declares and initializes `startField` as `JTextField`.
        JTextField startField = StyledComponents.createStyledTextField();
        // Explanation: Calls `setText` to configure this object.
        startField.setText("09:00");
        // Explanation: Declares and initializes `endField` as `JTextField`.
        JTextField endField = StyledComponents.createStyledTextField();
        // Explanation: Calls `setText` to configure this object.
        endField.setText("11:00");

        // Purpose
        // Explanation: Declares and initializes `purposeField` as `JTextField`.
        JTextField purposeField = StyledComponents.createStyledTextField();

        // Explanation: Adds a component or value to its parent container or collection.
        panel.add(StyledComponents.createFormLabel("Room:"));
        // Explanation: Adds a component or value to its parent container or collection.
        panel.add(roomCombo);
        // Explanation: Adds a component or value to its parent container or collection.
        panel.add(StyledComponents.createFormLabel("Date (yyyy-MM-dd):"));
        // Explanation: Adds a component or value to its parent container or collection.
        panel.add(dateField);
        // Explanation: Adds a component or value to its parent container or collection.
        panel.add(StyledComponents.createFormLabel("Start Time (HH:mm):"));
        // Explanation: Adds a component or value to its parent container or collection.
        panel.add(startField);
        // Explanation: Adds a component or value to its parent container or collection.
        panel.add(StyledComponents.createFormLabel("End Time (HH:mm):"));
        // Explanation: Adds a component or value to its parent container or collection.
        panel.add(endField);
        // Explanation: Adds a component or value to its parent container or collection.
        panel.add(StyledComponents.createFormLabel("Purpose:"));
        // Explanation: Adds a component or value to its parent container or collection.
        panel.add(purposeField);

        // Explanation: Starts the multi-line call to `showConfirmDialog`.
        int result = JOptionPane.showConfirmDialog(this, panel, "Create New Booking",
                // Explanation: Supplies the remaining arguments and completes the call to `showConfirmDialog`.
                JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);

        // Explanation: Checks this condition before running the following block.
        if (result == JOptionPane.OK_OPTION) {
            // Explanation: Starts a try block for code that may throw an exception.
            try {
                // Explanation: Declares and initializes `selectedRoom` as `String`.
                String selectedRoom = (String) roomCombo.getSelectedItem();
                // Explanation: Checks this condition before running the following block.
                if (selectedRoom == null) {
                    // Explanation: Calls `showMessageDialog` to perform this step.
                    JOptionPane.showMessageDialog(this, "Please select a room.", "Error", JOptionPane.ERROR_MESSAGE);
                    // Explanation: Exits the current method without returning a value.
                    return;
                // Explanation: Closes the current block.
                }
                // Explanation: Declares and initializes `roomId` as `String`.
                String roomId = selectedRoom.split(" - ")[0];
                // Explanation: Declares and initializes `date` as `LocalDate`.
                LocalDate date = LocalDate.parse(dateField.getText().trim());
                // Explanation: Declares and initializes `start` as `LocalTime`.
                LocalTime start = LocalTime.parse(startField.getText().trim());
                // Explanation: Declares and initializes `end` as `LocalTime`.
                LocalTime end = LocalTime.parse(endField.getText().trim());
                // Explanation: Declares and initializes `purpose` as `String`.
                String purpose = purposeField.getText().trim();

                // Explanation: Checks this condition before running the following block.
                if (purpose.isEmpty()) {
                    // Explanation: Calls `showMessageDialog` to perform this step.
                    JOptionPane.showMessageDialog(this, "Purpose is required.", "Error", JOptionPane.ERROR_MESSAGE);
                    // Explanation: Exits the current method without returning a value.
                    return;
                // Explanation: Closes the current block.
                }

                // Explanation: Calls `createBooking` to perform this step.
                facade.createBooking(roomId, date, start, end, purpose);
                // Explanation: Calls `refresh` to perform this step.
                refresh();
                // Explanation: Calls `showMessageDialog` to perform this step.
                JOptionPane.showMessageDialog(this, "Booking created successfully!", "Success", JOptionPane.INFORMATION_MESSAGE);
            // Explanation: Defines the `catch` method.
            } catch (DateTimeParseException ex) {
                // Explanation: Calls `showMessageDialog` to perform this step.
                JOptionPane.showMessageDialog(this, "Invalid date or time format.", "Error", JOptionPane.ERROR_MESSAGE);
            // Explanation: Defines the `catch` method.
            } catch (Exception ex) {
                // Explanation: Calls `showMessageDialog` to perform this step.
                JOptionPane.showMessageDialog(this, ex.getMessage(), "Booking Error", JOptionPane.ERROR_MESSAGE);
            // Explanation: Closes the current block.
            }
        // Explanation: Closes the current block.
        }
    // Explanation: Closes the current block.
    }

    // Explanation: Defines the `cancelSelectedBooking` method.
    private void cancelSelectedBooking() {
        // Explanation: Declares and initializes `row` as `int`.
        int row = bookingTable.getSelectedRow();
        // Explanation: Checks this condition before running the following block.
        if (row < 0) {
            // Explanation: Calls `showMessageDialog` to perform this step.
            JOptionPane.showMessageDialog(this, "Please select a booking to cancel.", "Info", JOptionPane.INFORMATION_MESSAGE);
            // Explanation: Exits the current method without returning a value.
            return;
        // Explanation: Closes the current block.
        }

        // Explanation: Declares and initializes `bookingId` as `String`.
        String bookingId = (String) tableModel.getValueAt(row, 0);
        // Explanation: Declares and initializes `status` as `String`.
        String status = (String) tableModel.getValueAt(row, 6);

        // Explanation: Checks this condition before running the following block.
        if ("CANCELLED".equals(status)) {
            // Explanation: Calls `showMessageDialog` to perform this step.
            JOptionPane.showMessageDialog(this, "This booking is already cancelled.", "Info", JOptionPane.INFORMATION_MESSAGE);
            // Explanation: Exits the current method without returning a value.
            return;
        // Explanation: Closes the current block.
        }

        // Explanation: Starts the multi-line call to `showConfirmDialog`.
        int confirm = JOptionPane.showConfirmDialog(this,
                // Explanation: Continues the argument list for the call to `showConfirmDialog`.
                "Are you sure you want to cancel booking " + bookingId + "?",
                // Explanation: Supplies the remaining arguments and completes the call to `showConfirmDialog`.
                "Confirm Cancellation", JOptionPane.YES_NO_OPTION);

        // Explanation: Checks this condition before running the following block.
        if (confirm == JOptionPane.YES_OPTION) {
            // Explanation: Starts a try block for code that may throw an exception.
            try {
                // Explanation: Calls `cancelBooking` to perform this step.
                facade.cancelBooking(bookingId);
                // Explanation: Calls `refresh` to perform this step.
                refresh();
                // Explanation: Calls `showMessageDialog` to perform this step.
                JOptionPane.showMessageDialog(this, "Booking cancelled successfully.", "Success", JOptionPane.INFORMATION_MESSAGE);
            // Explanation: Defines the `catch` method.
            } catch (Exception ex) {
                // Explanation: Calls `showMessageDialog` to perform this step.
                JOptionPane.showMessageDialog(this, ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            // Explanation: Closes the current block.
            }
        // Explanation: Closes the current block.
        }
    // Explanation: Closes the current block.
    }

    // Explanation: Defines the `refresh` method.
    public void refresh() {
        // Explanation: Calls `populateTable` to perform this step.
        populateTable();
        // Explanation: Calls `revalidate` to perform this step.
        revalidate();
        // Explanation: Calls `repaint` to perform this step.
        repaint();
    // Explanation: Closes the current block.
    }
// Explanation: Closes the current block.
}
