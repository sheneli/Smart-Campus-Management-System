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
// Explanation: Imports `DefaultTableCellRenderer` for use in this file.
import javax.swing.table.DefaultTableCellRenderer;
// Explanation: Imports all types from `java.awt` for use in this file.
import java.awt.*;
// Explanation: Imports `DateTimeFormatter` for use in this file.
import java.time.format.DateTimeFormatter;
// Explanation: Imports `List` for use in this file.
import java.util.List;

// Explanation: Declares the `MaintenancePanel` class with its inheritance or interface information.
public class MaintenancePanel extends JPanel {

    // Explanation: Declares the private final field `facade` of type `CampusFacade`.
    private final CampusFacade facade;
    // Explanation: Declares the private field `maintenanceTable` of type `JTable`.
    private JTable maintenanceTable;
    // Explanation: Declares the private field `tableModel` of type `DefaultTableModel`.
    private DefaultTableModel tableModel;

    // Explanation: Defines the constructor for `MaintenancePanel`.
    public MaintenancePanel(CampusFacade facade) {
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
        JLabel title = new JLabel("Maintenance Requests");
        // Explanation: Calls `setFont` to configure this object.
        title.setFont(StyledComponents.FONT_TITLE);
        // Explanation: Calls `setForeground` to configure this object.
        title.setForeground(StyledComponents.PRIMARY);

        // Explanation: Declares and initializes `buttonPanel` as `JPanel`.
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT, 10, 0));
        // Explanation: Calls `setOpaque` to configure this object.
        buttonPanel.setOpaque(false);

        // Explanation: Declares and initializes `reportBtn` as `JButton`.
        JButton reportBtn = StyledComponents.createPrimaryButton("+ Report Issue");
        // Explanation: Registers an action listener for this component.
        reportBtn.addActionListener(e -> showReportDialog());
        // Explanation: Adds a component or value to its parent container or collection.
        buttonPanel.add(reportBtn);

        // Explanation: Declares and initializes `user` as `User`.
        User user = facade.getCurrentUser();
        // Explanation: Checks this condition before running the following block.
        if (user != null && user.getRole() == UserRole.ADMINISTRATOR) {
            // Explanation: Declares and initializes `assignBtn` as `JButton`.
            JButton assignBtn = StyledComponents.createSecondaryButton("Assign Task");
            // Explanation: Registers an action listener for this component.
            assignBtn.addActionListener(e -> showAssignDialog());
            // Explanation: Declares and initializes `statusBtn` as `JButton`.
            JButton statusBtn = StyledComponents.createSuccessButton("Update Status");
            // Explanation: Registers an action listener for this component.
            statusBtn.addActionListener(e -> showUpdateStatusDialog());
            // Explanation: Adds a component or value to its parent container or collection.
            buttonPanel.add(assignBtn);
            // Explanation: Adds a component or value to its parent container or collection.
            buttonPanel.add(statusBtn);
        // Explanation: Closes the current block.
        }

        // Explanation: Adds a component or value to its parent container or collection.
        header.add(title, BorderLayout.WEST);
        // Explanation: Adds a component or value to its parent container or collection.
        header.add(buttonPanel, BorderLayout.EAST);

        // Table
        // Explanation: Declares and initializes `tableCard` as `JPanel`.
        JPanel tableCard = StyledComponents.createCard(null);
        // Explanation: Declares and initializes `columns` as `String[]`.
        String[] columns = {"Request ID", "Room", "Reported By", "Description", "Urgency", "Status", "Assigned To", "Created"};
        // Explanation: Creates an anonymous `DefaultTableModel` implementation.
        tableModel = new DefaultTableModel(columns, 0) {
            // Explanation: Marks the following method as an override of inherited behavior.
            @Override
            // Explanation: Defines the `isCellEditable` method.
            public boolean isCellEditable(int row, int column) { return false; }
        // Explanation: Closes the current block and ends the statement.
        };
        // Explanation: Updates `maintenanceTable` with a new value.
        maintenanceTable = new JTable(tableModel);
        // Explanation: Calls `setFont` to configure this object.
        maintenanceTable.setFont(StyledComponents.FONT_BODY);
        // Explanation: Calls `setRowHeight` to configure this object.
        maintenanceTable.setRowHeight(40);
        // Explanation: Calls `setSelectionMode` to configure this object.
        maintenanceTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        // Explanation: Calls `setShowGrid` to configure this object.
        maintenanceTable.setShowGrid(false);
        // Explanation: Calls `setShowHorizontalLines` to configure this object.
        maintenanceTable.setShowHorizontalLines(true);
        // Explanation: Calls `setGridColor` to configure this object.
        maintenanceTable.setGridColor(StyledComponents.BORDER_COLOR);
        // Explanation: Calls `getTableHeader` to configure this object.
        maintenanceTable.getTableHeader().setFont(new Font("Segoe UI", Font.BOLD, 13));
        // Explanation: Calls `getTableHeader` to configure this object.
        maintenanceTable.getTableHeader().setBackground(new Color(248, 249, 250));
        // Explanation: Calls `getTableHeader` to configure this object.
        maintenanceTable.getTableHeader().setPreferredSize(new Dimension(0, 44));
        // Explanation: Calls `setSelectionBackground` to configure this object.
        maintenanceTable.setSelectionBackground(new Color(0, 166, 214, 30));

        // Custom renderer for urgency column
        // Explanation: Creates an anonymous `DefaultTableCellRenderer` implementation.
        maintenanceTable.getColumnModel().getColumn(4).setCellRenderer(new DefaultTableCellRenderer() {
            // Explanation: Marks the following method as an override of inherited behavior.
            @Override
            // Explanation: Starts the signature for the `getTableCellRendererComponent` method.
            public Component getTableCellRendererComponent(JTable table, Object value,
                                                           // Explanation: Finishes the parameter list and opens the `getTableCellRendererComponent` method body.
                                                           boolean isSelected, boolean hasFocus, int row, int column) {
                // Explanation: Declares and initializes `c` as `Component`.
                Component c = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
                // Explanation: Declares and initializes `urgency` as `String`.
                String urgency = value != null ? value.toString() : "";
                // Explanation: Starts a switch statement for the selected value.
                switch (urgency) {
                    // Explanation: Calls `setForeground` to perform this step.
                    case "CRITICAL" -> setForeground(StyledComponents.DANGER);
                    // Explanation: Calls `setForeground` to perform this step.
                    case "HIGH" -> setForeground(new Color(255, 100, 0));
                    // Explanation: Calls `setForeground` to perform this step.
                    case "MEDIUM" -> setForeground(StyledComponents.WARNING);
                    // Explanation: Calls `setForeground` to perform this step.
                    case "LOW" -> setForeground(StyledComponents.SUCCESS);
                    // Explanation: Calls `setForeground` to perform this step.
                    default -> setForeground(StyledComponents.TEXT_PRIMARY);
                // Explanation: Closes the current block.
                }
                // Explanation: Calls `setFont` to configure this object.
                setFont(new Font("Segoe UI", Font.BOLD, 13));
                // Explanation: Calls `setHorizontalAlignment` to configure this object.
                setHorizontalAlignment(CENTER);
                // Explanation: Returns this value from the current method.
                return c;
            // Explanation: Closes the current block.
            }
        // Explanation: Closes the current block and completes the surrounding method call.
        });

        // Explanation: Calls `populateTable` to perform this step.
        populateTable();

        // Explanation: Declares and initializes `sp` as `JScrollPane`.
        JScrollPane sp = new JScrollPane(maintenanceTable);
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
        // Explanation: Declares `requests` as `List<MaintenanceRequest>` for later use.
        List<MaintenanceRequest> requests;

        // Explanation: Checks this condition before running the following block.
        if (currentUser != null && currentUser.getRole() == UserRole.ADMINISTRATOR) {
            // Explanation: Updates `requests` with a new value.
            requests = facade.getAllMaintenanceRequests();
        // Explanation: Checks another condition after closing the previous branch.
        } else if (currentUser != null) {
            // Explanation: Updates `requests` with a new value.
            requests = facade.getMaintenanceRequestsByUser(currentUser.getUserId());
        // Explanation: Runs this block when the earlier condition did not match.
        } else {
            // Explanation: Updates `requests` with a new value.
            requests = List.of();
        // Explanation: Closes the current block.
        }

        // Explanation: Declares and initializes `fmt` as `DateTimeFormatter`.
        DateTimeFormatter fmt = DateTimeFormatter.ofPattern("dd MMM yyyy HH:mm");
        // Explanation: Declares and initializes `allUsers` as `List<User>`.
        List<User> allUsers = facade.getAllUsers();

        // Explanation: Starts a loop over the values defined in this header.
        for (MaintenanceRequest r : requests) {
            // Explanation: Performs the operation written on this line.
            String reporterName = allUsers.stream()
                    // Explanation: Applies a filter step to the current stream.
                    .filter(u -> u.getUserId().equals(r.getReportedBy()))
                    // Explanation: Takes the first matching value from the stream.
                    .findFirst().map(User::getName).orElse(r.getReportedBy());

            // Explanation: Defines the `addRow` method.
            tableModel.addRow(new Object[]{
                    // Explanation: Adds another value to the inline array or object initializer.
                    r.getRequestId(),
                    // Explanation: Adds another value to the inline array or object initializer.
                    r.getRoomId(),
                    // Explanation: Adds another value to the inline array or object initializer.
                    reporterName,
                    // Explanation: Adds another value to the inline array or object initializer.
                    r.getDescription(),
                    // Explanation: Adds another value to the inline array or object initializer.
                    r.getUrgency().name(),
                    // Explanation: Adds another value to the inline array or object initializer.
                    r.getStatus().name(),
                    // Explanation: Adds another value to the inline array or object initializer.
                    r.getAssignedTo() != null ? r.getAssignedTo() : "Unassigned",
                    // Explanation: Adds another value inside the inline array or object initializer.
                    r.getCreatedAt().format(fmt)
            // Explanation: Closes the inline array or object initializer.
            });
        // Explanation: Closes the current block.
        }
    // Explanation: Closes the current block.
    }

    // Explanation: Defines the `showReportDialog` method.
    private void showReportDialog() {
        // Explanation: Declares and initializes `panel` as `JPanel`.
        JPanel panel = new JPanel(new GridLayout(3, 2, 10, 10));
        // Explanation: Calls `setBorder` to configure this object.
        panel.setBorder(new EmptyBorder(10, 10, 10, 10));

        // Explanation: Declares and initializes `rooms` as `List<Room>`.
        List<Room> rooms = facade.getAllRooms();
        // Explanation: Declares and initializes `roomNames` as `String[]`.
        String[] roomNames = rooms.stream().map(r -> r.getRoomId() + " - " + r.getName()).toArray(String[]::new);
        // Explanation: Declares and initializes `roomCombo` as `JComboBox<String>`.
        JComboBox<String> roomCombo = StyledComponents.createStyledComboBox(roomNames);

        // Explanation: Declares and initializes `descArea` as `JTextArea`.
        JTextArea descArea = StyledComponents.createStyledTextArea(3);
        // Explanation: Performs the operation written on this line.
        JComboBox<MaintenanceRequest.Urgency> urgencyCombo =
                // Explanation: Calls `createStyledComboBox` to perform this step.
                StyledComponents.createStyledComboBox(MaintenanceRequest.Urgency.values());

        // Explanation: Adds a component or value to its parent container or collection.
        panel.add(StyledComponents.createFormLabel("Room:"));
        // Explanation: Adds a component or value to its parent container or collection.
        panel.add(roomCombo);
        // Explanation: Adds a component or value to its parent container or collection.
        panel.add(StyledComponents.createFormLabel("Description:"));
        // Explanation: Adds a component or value to its parent container or collection.
        panel.add(new JScrollPane(descArea));
        // Explanation: Adds a component or value to its parent container or collection.
        panel.add(StyledComponents.createFormLabel("Urgency:"));
        // Explanation: Adds a component or value to its parent container or collection.
        panel.add(urgencyCombo);

        // Explanation: Starts the multi-line call to `showConfirmDialog`.
        int result = JOptionPane.showConfirmDialog(this, panel, "Report Maintenance Issue",
                // Explanation: Supplies the remaining arguments and completes the call to `showConfirmDialog`.
                JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);

        // Explanation: Checks this condition before running the following block.
        if (result == JOptionPane.OK_OPTION) {
            // Explanation: Starts a try block for code that may throw an exception.
            try {
                // Explanation: Declares and initializes `selectedRoom` as `String`.
                String selectedRoom = (String) roomCombo.getSelectedItem();
                // Explanation: Performs the operation written on this line.
                if (selectedRoom == null) return;
                // Explanation: Declares and initializes `roomId` as `String`.
                String roomId = selectedRoom.split(" - ")[0];
                // Explanation: Declares and initializes `desc` as `String`.
                String desc = descArea.getText().trim();
                // Explanation: Declares and initializes `urgency` as `MaintenanceRequest.Urgency`.
                MaintenanceRequest.Urgency urgency = (MaintenanceRequest.Urgency) urgencyCombo.getSelectedItem();

                // Explanation: Checks this condition before running the following block.
                if (desc.isEmpty()) {
                    // Explanation: Calls `showMessageDialog` to perform this step.
                    JOptionPane.showMessageDialog(this, "Description is required.", "Error", JOptionPane.ERROR_MESSAGE);
                    // Explanation: Exits the current method without returning a value.
                    return;
                // Explanation: Closes the current block.
                }

                // Explanation: Calls `createMaintenanceRequest` to perform this step.
                facade.createMaintenanceRequest(roomId, desc, urgency);
                // Explanation: Calls `refresh` to perform this step.
                refresh();
                // Explanation: Calls `showMessageDialog` to perform this step.
                JOptionPane.showMessageDialog(this, "Maintenance request submitted successfully!", "Success", JOptionPane.INFORMATION_MESSAGE);
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

    // Explanation: Defines the `showAssignDialog` method.
    private void showAssignDialog() {
        // Explanation: Declares and initializes `row` as `int`.
        int row = maintenanceTable.getSelectedRow();
        // Explanation: Checks this condition before running the following block.
        if (row < 0) {
            // Explanation: Calls `showMessageDialog` to perform this step.
            JOptionPane.showMessageDialog(this, "Please select a request to assign.", "Info", JOptionPane.INFORMATION_MESSAGE);
            // Explanation: Exits the current method without returning a value.
            return;
        // Explanation: Closes the current block.
        }

        // Explanation: Declares and initializes `requestId` as `String`.
        String requestId = (String) tableModel.getValueAt(row, 0);
        // Explanation: Declares and initializes `assignTo` as `String`.
        String assignTo = JOptionPane.showInputDialog(this, "Assign to (team/person name):", "Assign Maintenance Task", JOptionPane.PLAIN_MESSAGE);

        // Explanation: Checks this condition before running the following block.
        if (assignTo != null && !assignTo.trim().isEmpty()) {
            // Explanation: Starts a try block for code that may throw an exception.
            try {
                // Explanation: Calls `assignMaintenanceRequest` to perform this step.
                facade.assignMaintenanceRequest(requestId, assignTo.trim());
                // Explanation: Calls `refresh` to perform this step.
                refresh();
                // Explanation: Calls `showMessageDialog` to perform this step.
                JOptionPane.showMessageDialog(this, "Task assigned successfully!", "Success", JOptionPane.INFORMATION_MESSAGE);
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

    // Explanation: Defines the `showUpdateStatusDialog` method.
    private void showUpdateStatusDialog() {
        // Explanation: Declares and initializes `row` as `int`.
        int row = maintenanceTable.getSelectedRow();
        // Explanation: Checks this condition before running the following block.
        if (row < 0) {
            // Explanation: Calls `showMessageDialog` to perform this step.
            JOptionPane.showMessageDialog(this, "Please select a request.", "Info", JOptionPane.INFORMATION_MESSAGE);
            // Explanation: Exits the current method without returning a value.
            return;
        // Explanation: Closes the current block.
        }

        // Explanation: Declares and initializes `requestId` as `String`.
        String requestId = (String) tableModel.getValueAt(row, 0);
        // Explanation: Declares and initializes `statuses` as `MaintenanceRequest.Status[]`.
        MaintenanceRequest.Status[] statuses = MaintenanceRequest.Status.values();
        // Explanation: Starts the call to `showInputDialog`.
        MaintenanceRequest.Status selected = (MaintenanceRequest.Status) JOptionPane.showInputDialog(
                // Explanation: Continues the current argument, parameter, or element list.
                this, "Select new status:", "Update Status",
                // Explanation: Performs the operation written on this line.
                JOptionPane.PLAIN_MESSAGE, null, statuses, statuses[0]);

        // Explanation: Checks this condition before running the following block.
        if (selected != null) {
            // Explanation: Starts a try block for code that may throw an exception.
            try {
                // Explanation: Calls `updateMaintenanceStatus` to perform this step.
                facade.updateMaintenanceStatus(requestId, selected);
                // Explanation: Calls `refresh` to perform this step.
                refresh();
                // Explanation: Calls `showMessageDialog` to perform this step.
                JOptionPane.showMessageDialog(this, "Status updated successfully!", "Success", JOptionPane.INFORMATION_MESSAGE);
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
