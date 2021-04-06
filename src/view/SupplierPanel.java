package view;

import controller.Controller;
import model.supplier.Supplier;

import javax.swing.*;
import javax.swing.border.TitledBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.PropertyChangeEvent;

/**
 * Class for the supplier panel
 *
 * @Author Alex Bergenholtz
 * @Version 1.2
 */


public class SupplierPanel extends JPanel {

    private Controller controller;

    private JPanel northPanel;
    private JPanel centerPanel;
    private JPanel westPanel;
    private JList<Supplier> supplierNameJList;
    private JList<String> supplierInfoJList;
    private JButton addSupplier, removeSupplier, updateSupplier;
    private JComboBox cmbWeekDays;

    /**
     * Constructor to create supplier panel GUI
     */
    public SupplierPanel(Controller controller) {
        this.controller = controller;

        setLayout(new BorderLayout());
        setupPanels();
    }

    /**
     * Supplier panel GUI
     */
    private void setupPanels() {
        northPanel = new JPanel();
        centerPanel = new JPanel();
        westPanel = new JPanel();

        addSupplier = new JButton("Add supplier");
        removeSupplier = new JButton("Remove supplier");
        updateSupplier = new JButton("Update supplier");
        addSupplier.setSize(new Dimension(100, 30));
        removeSupplier.setSize(new Dimension(100, 30));
        updateSupplier.setSize(new Dimension(100, 30));
        northPanel.add(addSupplier);
        northPanel.add(removeSupplier);
        northPanel.add(updateSupplier);

        westPanel.setBorder(new TitledBorder("Suppliers"));
        supplierNameJList = new JList<>();
        supplierNameJList.setPreferredSize(new Dimension(200, 550));
        supplierNameJList.setEnabled(true);
        supplierNameJList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        westPanel.setLayout(new BorderLayout(5, 0));
        westPanel.add(supplierNameJList);

        centerPanel.setBorder(new TitledBorder("Info"));
        supplierInfoJList = new JList<>();
        supplierInfoJList.setPreferredSize(new Dimension(700, 550));
        supplierInfoJList.setEnabled(true);
        supplierInfoJList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        centerPanel.setLayout(new BorderLayout(5, 0));
        centerPanel.add(supplierInfoJList);

        this.add(northPanel, BorderLayout.NORTH);
        this.add(westPanel, BorderLayout.WEST);
        this.add(centerPanel, BorderLayout.CENTER);

        addListeners();
    }

    /**
     * A panel which is opened in a JOptionPane for new supplier input
     */
    public void addSupplier() {
        JTextField supName = new JTextField(5);
        JTextField supPhone = new JTextField(5);
        JTextField supEmail = new JTextField(5);
        JTextField supAddress = new JTextField(5);
        JTextField supCity = new JTextField(5);
        JTextField supCountry = new JTextField(5);
        cmbWeekDays = new JComboBox(controller.getWeekDays());

        JPanel panel = new JPanel();
        panel.setPreferredSize(new Dimension(320, 133));
        panel.setLayout(new GridLayout(7, 2, 2, 2));
        panel.add(new JLabel("Supplier name:"));
        panel.add(supName);
        panel.add(new JLabel("Supplier phone:"));
        panel.add(supPhone);
        panel.add(new JLabel("Supplier email;"));
        panel.add(supEmail);
        panel.add(new JLabel("Supplier address:"));
        panel.add(supAddress);
        panel.add(new JLabel("Supplier city:"));
        panel.add(supCity);
        panel.add(new JLabel("Supplier country:"));
        panel.add(supCountry);
        panel.add(new JLabel("Day of delivery"));
        panel.add(cmbWeekDays);

        int result = JOptionPane.showConfirmDialog(null, panel,
                "Add supplier", JOptionPane.OK_CANCEL_OPTION);
        if (result == JOptionPane.OK_OPTION) {
            if ((supName.getText().isBlank()) || (supPhone.getText().isBlank()) || (supAddress.getText().isBlank()) || (supCity.getText().isBlank()) || (supCountry.getText().isBlank())) {
                JOptionPane.showMessageDialog(null, "Input missing");
            } else {
                try {
                    String name = (supName.getText().toUpperCase());
                    String phone = supPhone.getText();
                    String email = supEmail.getText();
                    String address = supAddress.getText();
                    String city = supCity.getText();
                    String country = supCountry.getText();
                    //controller.addNewSupplier(name,phone,email,address,city,country); // När det skall skickas till controller.
                    Supplier supplier = new Supplier(name, address, city, country, email, phone);
                    Controller.databaseReference.child("Suppliers").child(supplier.getName()).setValueAsync(supplier);
                } catch (NumberFormatException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    /**
     * A panel which is opened in a JOptionPane to update current supplier
     */
    public void updateSupplier() {
        JTextField supName = new JTextField(supplierNameJList.getSelectedValue().getName(), 5);
        JTextField supPhone = new JTextField(supplierNameJList.getSelectedValue().getPhonenumber(), 5);
        JTextField supEmail = new JTextField(supplierNameJList.getSelectedValue().getEmail(), 5);
        JTextField supAddress = new JTextField(supplierNameJList.getSelectedValue().getAddress(), 5);
        JTextField supCity = new JTextField(supplierNameJList.getSelectedValue().getCity(), 5);
        JTextField supCountry = new JTextField(supplierNameJList.getSelectedValue().getCountrty(), 5);
        cmbWeekDays = new JComboBox(controller.getWeekDays());

        JPanel panel = new JPanel();
        panel.setPreferredSize(new Dimension(320, 133));
        panel.setLayout(new GridLayout(7, 2, 2, 2));
        panel.add(new JLabel("Supplier name:"));
        panel.add(supName);
        panel.add(new JLabel("Supplier phone:"));
        panel.add(supPhone);
        panel.add(new JLabel("Supplier email;"));
        panel.add(supEmail);
        panel.add(new JLabel("Supplier address:"));
        panel.add(supAddress);
        panel.add(new JLabel("Supplier city:"));
        panel.add(supCity);
        panel.add(new JLabel("Supplier country:"));
        panel.add(supCountry);
        panel.add(new JLabel("Day of delivery"));
        panel.add(cmbWeekDays);

        int result = JOptionPane.showConfirmDialog(null, panel,
                "Update supplier", JOptionPane.OK_CANCEL_OPTION);
        if (result == JOptionPane.OK_OPTION) {
            if ((supName.getText().isBlank()) || (supPhone.getText().isBlank()) || (supAddress.getText().isBlank()) || (supCity.getText().isBlank()) || (supCountry.getText().isBlank())) {
                JOptionPane.showMessageDialog(null, "Input missing");
            } else {
                try {
                    String name = (supName.getText().toUpperCase());
                    String phone = supPhone.getText();
                    String email = supEmail.getText();
                    String address = supAddress.getText();
                    String city = supCity.getText();
                    String country = supCountry.getText();
                    //controller.updateCurrentSupplier(name,phone,email,address,city,country); // När det skall skickas till controller.
                } catch (NumberFormatException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public void propertyChangeSupplierList(PropertyChangeEvent evt){
        if (evt.getPropertyName().equals("supplier")){

        }
    }

    public void propertyChangeSupplierInfo(PropertyChangeEvent evt){
        if (evt.getPropertyName().equals("info")){

        }
    }

    /**
     * Add listeners to Swing componenets which needs it
     */
    private void addListeners() {
        ActionListener listener = new ButtonActionListeners();
        addSupplier.addActionListener(listener);
        removeSupplier.addActionListener(listener);
        updateSupplier.addActionListener(listener);
    }

    /**
     * Actionlistener for buttons.
     */
    class ButtonActionListeners implements ActionListener {

        public void actionPerformed(ActionEvent e) {
            if (e.getSource() == addSupplier) {
                addSupplier();
            }
            if (e.getSource() == removeSupplier) {
                if (supplierNameJList.getSelectedValue() != null) {

                } else {
                    JOptionPane.showMessageDialog(null, "Please select a supplier");
                }
            }
            if (e.getSource() == updateSupplier) {
                if (supplierNameJList.getSelectedValue() != null) {
                    updateSupplier();
                } else {
                    JOptionPane.showMessageDialog(null, "Please select a supplier");
                }
            }
        }
    }

}
