package view;

import controller.Controller;
import controller.SupplierController;
import model.supplier.Supplier;

import javax.swing.*;
import javax.swing.border.TitledBorder;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.ArrayList;

/**
 * Class for the supplier panel
 *
 * @Author Alex Bergenholtz
 * @Version 1.2
 */


public class SupplierPanel extends JPanel implements PropertyChangeListener {

    private SupplierController supController;

    private JPanel northPanel;
    private JPanel centerPanel;
    private JPanel westPanel;
    private DefaultListModel<Supplier> supModel;
    private DefaultListModel<String> supNameModel;
    private JList<Supplier> supplierJList;;
    private JList<String> supplierNameJList;
    private JTextArea supplierInfoArea;
    private JButton addSupplier, removeSupplier, updateSupplier;
    private JComboBox cmbWeekDays;

    /**
     * Constructor to create supplier panel GUI
     */
    public SupplierPanel(SupplierController controller) {
        supController = controller;
        supController.setSupplierPanel(this);
        supController.registerPropertyChangeListener(this);
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

        supModel = new DefaultListModel<>();
        supplierJList = new JList<>();
        supplierJList.setModel(supModel);

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
        supNameModel = new DefaultListModel<>();
        supplierNameJList = new JList<>();
        supplierNameJList.setPreferredSize(new Dimension(200, 550));
        supplierNameJList.setModel(supNameModel);
        supplierNameJList.setEnabled(true);
        supplierNameJList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        westPanel.setLayout(new BorderLayout(5, 0));
        westPanel.add(supplierNameJList);

        centerPanel.setBorder(new TitledBorder("Info"));
        supplierInfoArea = new JTextArea();
        supplierInfoArea.setPreferredSize(new Dimension(700, 550));
        supplierInfoArea.setEnabled(false);
        centerPanel.setLayout(new BorderLayout(5, 0));
        centerPanel.add(supplierInfoArea);

        this.add(northPanel, BorderLayout.NORTH);
        this.add(westPanel, BorderLayout.WEST);
        this.add(centerPanel, BorderLayout.CENTER);

        addListeners();

        supplierNameJList.getSelectionModel().addListSelectionListener(new ListSelectionListener() {
            public void valueChanged(ListSelectionEvent evt) {
                listValueChanged(evt);
            }
        });
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
        cmbWeekDays = new JComboBox(supController.getWeekDays());

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
                    supController.addSupplierToDatabase(name,address,city,country, email, phone); // När det skall skickas till controller.
                    supController.getSuppliersFromDatabase();
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
        JTextField supName = new JTextField(supplierJList.getSelectedValue().getName(), 5);
        JTextField supPhone = new JTextField(supplierJList.getSelectedValue().getPhonenumber(), 5);
        JTextField supEmail = new JTextField(supplierJList.getSelectedValue().getEmail(), 5);
        JTextField supAddress = new JTextField(supplierJList.getSelectedValue().getAddress(), 5);
        JTextField supCity = new JTextField(supplierJList.getSelectedValue().getCity(), 5);
        JTextField supCountry = new JTextField(supplierJList.getSelectedValue().getCountrty(), 5);
        cmbWeekDays = new JComboBox(supController.getWeekDays());

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

    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        if (evt.getPropertyName().equals("Supplier list")) {
            System.out.println("hej");
            supModel.clear();
            supNameModel.clear();
            ArrayList<Supplier> tempList = (ArrayList<Supplier>) evt.getNewValue();
            for (Supplier sup : tempList){
                supModel.addElement(sup);
                supNameModel.addElement(sup.getName());
            }
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

    private void listValueChanged(ListSelectionEvent evt) {
        if (!supplierNameJList.getValueIsAdjusting()) {
            if (supplierNameJList.getSelectedIndex() >= 0) {
                Supplier tempSup = supplierJList.getSelectedValue();
                supplierInfoArea.append(tempSup.toString());
            }
        }
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
