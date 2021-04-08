package view;

import controller.SupplierController;
import model.supplier.Supplier;
import model.supplier.WeekDay;

import javax.swing.*;
import javax.swing.border.TitledBorder;
import javax.swing.event.ListSelectionEvent;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Class for the supplier panel
 *
 * @Author Alex Bergenholtz
 * @Version 1.2
 */


public class SupplierPanel extends JPanel {

    private SupplierController supController;

    private JPanel northPanel;
    private JPanel centerPanel;
    private JPanel westPanel;
    private JList<Supplier> supplierJList;
    private JPanel supplierInfoArea;
    private JButton addSupplier, removeSupplier, updateSupplier;
    private JComboBox cmbWeekDays;
    private JLabel nameLabel, phoneLabel, emailLabel, addressLabel, cityLabel, countryLabel, dodLabel;

    /**
     * Constructor to create supplier panel GUI
     */
    public SupplierPanel(SupplierController controller) {
        supController = controller;
        supController.setUp(this);
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
        supplierJList = new JList<>();
        supplierJList.setPreferredSize(new Dimension(200, 550));
        supplierJList.setEnabled(true);
        supplierJList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        westPanel.add(supplierJList);
        centerPanel.setBorder(new TitledBorder("Info"));
        supplierInfoArea = new JPanel(null);
        nameLabel = new JLabel("");
        nameLabel.setBounds(300, 20, 300, 40);
        nameLabel.setFont(new Font("Time New Roman", Font.BOLD, 20));
        phoneLabel = new JLabel("");
        phoneLabel.setBounds(10, 70, 300, 40);
        emailLabel = new JLabel("");
        emailLabel.setBounds(680, 70, 300, 40);
        addressLabel = new JLabel("");
        addressLabel.setBounds(10, 170, 300, 40);
        cityLabel = new JLabel("");
        cityLabel.setBounds(300, 170, 300, 40);
        countryLabel = new JLabel("");
        countryLabel.setBounds(700, 170, 300, 40);
        dodLabel = new JLabel("");
        dodLabel.setBounds(10, 320, 300, 40);
        supplierInfoArea.add(nameLabel);
        supplierInfoArea.add(phoneLabel);
        supplierInfoArea.add(emailLabel);
        supplierInfoArea.add(addressLabel);
        supplierInfoArea.add(cityLabel);
        supplierInfoArea.add(countryLabel);
        supplierInfoArea.add(dodLabel);
        centerPanel.setLayout(new BorderLayout(5, 0));
        centerPanel.add(supplierInfoArea);

        this.add(northPanel, BorderLayout.NORTH);
        this.add(westPanel, BorderLayout.WEST);
        this.add(centerPanel, BorderLayout.CENTER);

        addListeners();

        supplierJList.getSelectionModel().addListSelectionListener(evt -> listValueChanged(evt));
    }

    /**
     * A panel which is opened in a JOptionPane for new supplier input
     */
    public void addSupplier(Supplier supplier) {
        JTextField supName = new JTextField(5);
        JTextField supPhone = new JTextField(5);
        JTextField supEmail = new JTextField(5);
        JTextField supAddress = new JTextField(5);
        JTextField supCity = new JTextField(5);
        JTextField supCountry = new JTextField(5);
        cmbWeekDays = new JComboBox(supController.getWeekDays());

        if (supplier != null) {
            supName.setText(supplier.getName());
            supPhone.setText(supplier.getPhonenumber());
            supEmail.setText(supplier.getEmail());
            supAddress.setText(supplier.getAddress());
            supCity.setText(supplier.getCity());
            supCountry.setText(supplier.getCountrty());
            cmbWeekDays.setSelectedItem(supplier.getDayOfDelivery());
        }

        JPanel panel = new JPanel();
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
                    supController.createNewSupplier(name, address, city, country, email, phone, (WeekDay) getCmbWeekDays().getSelectedItem()); // När det skall skickas till controller.
                } catch (NumberFormatException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public SupplierController getSupController() {
        return supController;
    }

    public void setSupController(SupplierController supController) {
        this.supController = supController;
    }

    public JPanel getNorthPanel() {
        return northPanel;
    }

    public void setNorthPanel(JPanel northPanel) {
        this.northPanel = northPanel;
    }

    public JPanel getCenterPanel() {
        return centerPanel;
    }

    public void setCenterPanel(JPanel centerPanel) {
        this.centerPanel = centerPanel;
    }

    public JPanel getWestPanel() {
        return westPanel;
    }

    public void setWestPanel(JPanel westPanel) {
        this.westPanel = westPanel;
    }

    public JList<Supplier> getSupplierJList() {
        return supplierJList;
    }

    public void setSupplierJList(JList<Supplier> supplierJList) {
        this.supplierJList = supplierJList;
    }


    public JButton getAddSupplier() {
        return addSupplier;
    }

    public void setAddSupplier(JButton addSupplier) {
        this.addSupplier = addSupplier;
    }

    public JButton getRemoveSupplier() {
        return removeSupplier;
    }

    public void setRemoveSupplier(JButton removeSupplier) {
        this.removeSupplier = removeSupplier;
    }

    public JButton getUpdateSupplier() {
        return updateSupplier;
    }

    public void setUpdateSupplier(JButton updateSupplier) {
        this.updateSupplier = updateSupplier;
    }

    public JComboBox getCmbWeekDays() {
        return cmbWeekDays;
    }

    public void setCmbWeekDays(JComboBox cmbWeekDays) {
        this.cmbWeekDays = cmbWeekDays;
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
        if (!supplierJList.getValueIsAdjusting()) {
            if (supplierJList.getSelectedIndex() >= 0) {
                Supplier tempSup = supplierJList.getSelectedValue();
                nameLabel.setText(tempSup.getName());
                phoneLabel.setText(tempSup.getPhonenumber());
                emailLabel.setText(tempSup.getEmail());
                addressLabel.setText(tempSup.getAddress());
                cityLabel.setText(tempSup.getCity());
                countryLabel.setText(tempSup.getCountrty());
                dodLabel.setText("Day of delivery: " + tempSup.getDayOfDelivery().name().toLowerCase());
            }
        }
    }

    /**
     * Actionlistener for buttons.
     */
    class ButtonActionListeners implements ActionListener {

        public void actionPerformed(ActionEvent e) {
            if (e.getSource() == addSupplier) {
                addSupplier(null);
            }
            if (e.getSource() == removeSupplier) {
                if (supplierJList.getSelectedValue() != null) {

                } else {
                    JOptionPane.showMessageDialog(null, "Please select a supplier");
                }
            }
            if (e.getSource() == updateSupplier) {
                if (supplierJList.getSelectedValue() != null) {
                    Supplier s = supplierJList.getSelectedValue();
                    supController.removeSupplier(s);
                    addSupplier(s);
                } else {
                    JOptionPane.showMessageDialog(null, "Please select a supplier");
                }
            }
        }
    }


}
