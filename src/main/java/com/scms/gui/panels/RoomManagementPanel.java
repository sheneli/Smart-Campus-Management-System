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
// Explanation: Imports `Arrays` for use in this file.
import java.util.Arrays;
// Explanation: Imports `List` for use in this file.
import java.util.List;

// Explanation: Declares the `RoomManagementPanel` class with its inheritance or interface information.
public class RoomManagementPanel extends JPanel {

    // Explanation: Declares the private final field `facade` of type `CampusFacade`.
    private final CampusFacade facade;
    // Explanation: Declares the private field `roomTable` of type `JTable`.
    private JTable roomTable;
    // Explanation: Declares the private field `tableModel` of type `DefaultTableModel`.
    private DefaultTableModel tableModel;

    // Explanation: Defines the constructor for `RoomManagementPanel`.
    public RoomManagementPanel(CampusFacade facade) {
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
        JLabel title = new JLabel("Room Management");
        // Explanation: Calls `setFont` to configure this object.
        title.setFont(StyledComponents.FONT_TITLE);
        // Explanation: Calls `setForeground` to configure this object.
        title.setForeground(StyledComponents.PRIMARY);

        // Explanation: Declares and initializes `buttonPanel` as `JPanel`.
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT, 10, 0));
        // Explanation: Calls `setOpaque` to configure this object.
        buttonPanel.setOpaque(false);

        // Explanation: Declares and initializes `user` as `User`.
        User user = facade.getCurrentUser();
        // Explanation: Checks this condition before running the following block.
        if (user != null && user.getRole() == UserRole.ADMINISTRATOR) {
            // Explanation: Declares and initializes `addBtn` as `JButton`.
            JButton addBtn = StyledComponents.createPrimaryButton("+ Add Room");
            // Explanation: Registers an action listener for this component.
            addBtn.addActionListener(e -> showAddRoomDialog());
            // Explanation: Declares and initializes `editBtn` as `JButton`.
            JButton editBtn = StyledComponents.createSecondaryButton("Edit Room");
            // Explanation: Registers an action listener for this component.
            editBtn.addActionListener(e -> showEditRoomDialog());
            // Explanation: Declares and initializes `toggleBtn` as `JButton`.
            JButton toggleBtn = StyledComponents.createDangerButton("Toggle Status");
            // Explanation: Registers an action listener for this component.
            toggleBtn.addActionListener(e -> toggleRoomStatus());
            // Explanation: Adds a component or value to its parent container or collection.
            buttonPanel.add(addBtn);
            // Explanation: Adds a component or value to its parent container or collection.
            buttonPanel.add(editBtn);
            // Explanation: Adds a component or value to its parent container or collection.
            buttonPanel.add(toggleBtn);
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
        String[] columns = {"Room ID", "Name", "Type", "Capacity", "Equipment", "Status"};
        // Explanation: Creates an anonymous `DefaultTableModel` implementation.
        tableModel = new DefaultTableModel(columns, 0) {
            // Explanation: Marks the following method as an override of inherited behavior.
            @Override
            // Explanation: Defines the `isCellEditable` method.
            public boolean isCellEditable(int row, int column) { return false; }
        // Explanation: Closes the current block and ends the statement.
        };
        // Explanation: Updates `roomTable` with a new value.
        roomTable = new JTable(tableModel);
        // Explanation: Calls `setFont` to configure this object.
        roomTable.setFont(StyledComponents.FONT_BODY);
        // Explanation: Calls `setRowHeight` to configure this object.
        roomTable.setRowHeight(40);
        // Explanation: Calls `setSelectionMode` to configure this object.
        roomTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        // Explanation: Calls `setShowGrid` to configure this object.
        roomTable.setShowGrid(false);
        // Explanation: Calls `setShowHorizontalLines` to configure this object.
        roomTable.setShowHorizontalLines(true);
        // Explanation: Calls `setGridColor` to configure this object.
        roomTable.setGridColor(StyledComponents.BORDER_COLOR);
        // Explanation: Calls `getTableHeader` to configure this object.
        roomTable.getTableHeader().setFont(new Font("Segoe UI", Font.BOLD, 13));
        // Explanation: Calls `getTableHeader` to configure this object.
        roomTable.getTableHeader().setBackground(new Color(248, 249, 250));
        // Explanation: Calls `getTableHeader` to configure this object.
        roomTable.getTableHeader().setPreferredSize(new Dimension(0, 44));
        // Explanation: Calls `setSelectionBackground` to configure this object.
        roomTable.setSelectionBackground(new Color(0, 166, 214, 30));

        // Explanation: Calls `populateTable` to perform this step.
        populateTable();

        // Explanation: Declares and initializes `sp` as `JScrollPane`.
        JScrollPane sp = new JScrollPane(roomTable);
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
        // Explanation: Declares and initializes `rooms` as `List<Room>`.
        List<Room> rooms = facade.getAllRooms();
        // Explanation: Starts a loop over the values defined in this header.
        for (Room room : rooms) {
            // Explanation: Defines the `addRow` method.
            tableModel.addRow(new Object[]{
                    // Explanation: Adds another value to the inline array or object initializer.
                    room.getRoomId(),
                    // Explanation: Adds another value to the inline array or object initializer.
                    room.getName(),
                    // Explanation: Adds another value to the inline array or object initializer.
                    room.getType().getDisplayName(),
                    // Explanation: Adds another value to the inline array or object initializer.
                    room.getCapacity(),
                    // Explanation: Adds another value to the inline array or object initializer.
                    String.join(", ", room.getEquipment()),
                    // Explanation: Adds another value inside the inline array or object initializer.
                    room.isActive() ? "Active" : "Inactive"
            // Explanation: Closes the inline array or object initializer.
            });
        // Explanation: Closes the current block.
        }
    // Explanation: Closes the current block.
    }

    // Explanation: Defines the `showAddRoomDialog` method.
    private void showAddRoomDialog() {
        // Explanation: Declares and initializes `panel` as `JPanel`.
        JPanel panel = new JPanel(new GridLayout(4, 2, 10, 10));
        // Explanation: Calls `setBorder` to configure this object.
        panel.setBorder(new EmptyBorder(10, 10, 10, 10));

        // Explanation: Declares and initializes `nameField` as `JTextField`.
        JTextField nameField = StyledComponents.createStyledTextField();
        // Explanation: Declares and initializes `typeCombo` as `JComboBox<RoomType>`.
        JComboBox<RoomType> typeCombo = StyledComponents.createStyledComboBox(RoomType.values());
        // Explanation: Declares and initializes `capacityField` as `JTextField`.
        JTextField capacityField = StyledComponents.createStyledTextField();
        // Explanation: Declares and initializes `equipmentField` as `JTextField`.
        JTextField equipmentField = StyledComponents.createStyledTextField();

        // Explanation: Adds a component or value to its parent container or collection.
        panel.add(StyledComponents.createFormLabel("Room Name:"));
        // Explanation: Adds a component or value to its parent container or collection.
        panel.add(nameField);
        // Explanation: Adds a component or value to its parent container or collection.
        panel.add(StyledComponents.createFormLabel("Room Type:"));
        // Explanation: Adds a component or value to its parent container or collection.
        panel.add(typeCombo);
        // Explanation: Adds a component or value to its parent container or collection.
        panel.add(StyledComponents.createFormLabel("Capacity:"));
        // Explanation: Adds a component or value to its parent container or collection.
        panel.add(capacityField);
        // Explanation: Adds a component or value to its parent container or collection.
        panel.add(StyledComponents.createFormLabel("Equipment (comma-separated):"));
        // Explanation: Adds a component or value to its parent container or collection.
        panel.add(equipmentField);

        // Explanation: Starts the multi-line call to `showConfirmDialog`.
        int result = JOptionPane.showConfirmDialog(this, panel, "Add New Room",
                // Explanation: Supplies the remaining arguments and completes the call to `showConfirmDialog`.
                JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);

        // Explanation: Checks this condition before running the following block.
        if (result == JOptionPane.OK_OPTION) {
            // Explanation: Starts a try block for code that may throw an exception.
            try {
                // Explanation: Declares and initializes `name` as `String`.
                String name = nameField.getText().trim();
                // Explanation: Declares and initializes `type` as `RoomType`.
                RoomType type = (RoomType) typeCombo.getSelectedItem();
                // Explanation: Declares and initializes `capacity` as `int`.
                int capacity = Integer.parseInt(capacityField.getText().trim());
                // Explanation: Declares and initializes `equipment` as `List<String>`.
                List<String> equipment = Arrays.asList(equipmentField.getText().split("\\s*,\\s*"));

                // Explanation: Checks this condition before running the following block.
                if (name.isEmpty()) {
                    // Explanation: Calls `showMessageDialog` to perform this step.
                    JOptionPane.showMessageDialog(this, "Room name is required.", "Error", JOptionPane.ERROR_MESSAGE);
                    // Explanation: Exits the current method without returning a value.
                    return;
                // Explanation: Closes the current block.
                }

                // Explanation: Calls `addRoom` to perform this step.
                facade.addRoom(name, type, capacity, equipment);
                // Explanation: Calls `refresh` to perform this step.
                refresh();
                // Explanation: Calls `showMessageDialog` to perform this step.
                JOptionPane.showMessageDialog(this, "Room added successfully!", "Success", JOptionPane.INFORMATION_MESSAGE);
            // Explanation: Defines the `catch` method.
            } catch (NumberFormatException ex) {
                // Explanation: Calls `showMessageDialog` to perform this step.
                JOptionPane.showMessageDialog(this, "Invalid capacity value.", "Error", JOptionPane.ERROR_MESSAGE);
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

    // Explanation: Defines the `showEditRoomDialog` method.
    private void showEditRoomDialog() {
        // Explanation: Declares and initializes `row` as `int`.
        int row = roomTable.getSelectedRow();
        // Explanation: Checks this condition before running the following block.
        if (row < 0) {
            // Explanation: Calls `showMessageDialog` to perform this step.
            JOptionPane.showMessageDialog(this, "Please select a room to edit.", "Info", JOptionPane.INFORMATION_MESSAGE);
            // Explanation: Exits the current method without returning a value.
            return;
        // Explanation: Closes the current block.
        }

        // Explanation: Declares and initializes `roomId` as `String`.
        String roomId = (String) tableModel.getValueAt(row, 0);
        // Explanation: Declares and initializes `room` as `Room`.
        Room room = facade.getAllRooms().stream().filter(r -> r.getRoomId().equals(roomId)).findFirst().orElse(null);
        // Explanation: Performs the operation written on this line.
        if (room == null) return;

        // Explanation: Declares and initializes `panel` as `JPanel`.
        JPanel panel = new JPanel(new GridLayout(4, 2, 10, 10));
        // Explanation: Calls `setBorder` to configure this object.
        panel.setBorder(new EmptyBorder(10, 10, 10, 10));

        // Explanation: Declares and initializes `nameField` as `JTextField`.
        JTextField nameField = StyledComponents.createStyledTextField();
        // Explanation: Calls `setText` to configure this object.
        nameField.setText(room.getName());
        // Explanation: Declares and initializes `typeCombo` as `JComboBox<RoomType>`.
        JComboBox<RoomType> typeCombo = StyledComponents.createStyledComboBox(RoomType.values());
        // Explanation: Calls `setSelectedItem` to configure this object.
        typeCombo.setSelectedItem(room.getType());
        // Explanation: Declares and initializes `capacityField` as `JTextField`.
        JTextField capacityField = StyledComponents.createStyledTextField();
        // Explanation: Calls `setText` to configure this object.
        capacityField.setText(String.valueOf(room.getCapacity()));
        // Explanation: Declares and initializes `equipmentField` as `JTextField`.
        JTextField equipmentField = StyledComponents.createStyledTextField();
        // Explanation: Calls `setText` to configure this object.
        equipmentField.setText(String.join(", ", room.getEquipment()));

        // Explanation: Adds a component or value to its parent container or collection.
        panel.add(StyledComponents.createFormLabel("Room Name:"));
        // Explanation: Adds a component or value to its parent container or collection.
        panel.add(nameField);
        // Explanation: Adds a component or value to its parent container or collection.
        panel.add(StyledComponents.createFormLabel("Room Type:"));
        // Explanation: Adds a component or value to its parent container or collection.
        panel.add(typeCombo);
        // Explanation: Adds a component or value to its parent container or collection.
        panel.add(StyledComponents.createFormLabel("Capacity:"));
        // Explanation: Adds a component or value to its parent container or collection.
        panel.add(capacityField);
        // Explanation: Adds a component or value to its parent container or collection.
        panel.add(StyledComponents.createFormLabel("Equipment (comma-separated):"));
        // Explanation: Adds a component or value to its parent container or collection.
        panel.add(equipmentField);

        // Explanation: Starts the multi-line call to `showConfirmDialog`.
        int result = JOptionPane.showConfirmDialog(this, panel, "Edit Room: " + room.getName(),
                // Explanation: Supplies the remaining arguments and completes the call to `showConfirmDialog`.
                JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);

        // Explanation: Checks this condition before running the following block.
        if (result == JOptionPane.OK_OPTION) {
            // Explanation: Starts a try block for code that may throw an exception.
            try {
                // Explanation: Starts the multi-line call to `updateRoom`.
                facade.updateRoom(roomId, nameField.getText().trim(),
                        // Explanation: Continues the argument list for the call to `updateRoom`.
                        (RoomType) typeCombo.getSelectedItem(),
                        // Explanation: Continues the argument list for the call to `updateRoom`.
                        Integer.parseInt(capacityField.getText().trim()),
                        // Explanation: Supplies the remaining arguments and completes the call to `updateRoom`.
                        Arrays.asList(equipmentField.getText().split("\\s*,\\s*")));
                // Explanation: Calls `refresh` to perform this step.
                refresh();
                // Explanation: Calls `showMessageDialog` to perform this step.
                JOptionPane.showMessageDialog(this, "Room updated successfully!", "Success", JOptionPane.INFORMATION_MESSAGE);
            // Explanation: Defines the `catch` method.
            } catch (NumberFormatException ex) {
                // Explanation: Calls `showMessageDialog` to perform this step.
                JOptionPane.showMessageDialog(this, "Invalid capacity value.", "Error", JOptionPane.ERROR_MESSAGE);
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

    // Explanation: Defines the `toggleRoomStatus` method.
    private void toggleRoomStatus() {
        // Explanation: Declares and initializes `row` as `int`.
        int row = roomTable.getSelectedRow();
        // Explanation: Checks this condition before running the following block.
        if (row < 0) {
            // Explanation: Calls `showMessageDialog` to perform this step.
            JOptionPane.showMessageDialog(this, "Please select a room.", "Info", JOptionPane.INFORMATION_MESSAGE);
            // Explanation: Exits the current method without returning a value.
            return;
        // Explanation: Closes the current block.
        }

        // Explanation: Declares and initializes `roomId` as `String`.
        String roomId = (String) tableModel.getValueAt(row, 0);
        // Explanation: Declares and initializes `status` as `String`.
        String status = (String) tableModel.getValueAt(row, 5);
        // Explanation: Starts a try block for code that may throw an exception.
        try {
            // Explanation: Checks this condition before running the following block.
            if ("Active".equals(status)) {
                // Explanation: Calls `deactivateRoom` to perform this step.
                facade.deactivateRoom(roomId);
            // Explanation: Runs this block when the earlier condition did not match.
            } else {
                // Explanation: Calls `activateRoom` to perform this step.
                facade.activateRoom(roomId);
            // Explanation: Closes the current block.
            }
            // Explanation: Calls `refresh` to perform this step.
            refresh();
        // Explanation: Defines the `catch` method.
        } catch (Exception ex) {
            // Explanation: Calls `showMessageDialog` to perform this step.
            JOptionPane.showMessageDialog(this, ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
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
