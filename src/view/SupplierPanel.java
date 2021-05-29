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
 * @Version 3.0
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
    private JLabel nameLabel, phoneLabel, emailLabel, addressLabel, cityLabel, zipLabel, countryLabel, dodLabel;

    /**
     * Constructor to create supplier panel GUI
     */
    public SupplierPanel(SupplierController controller) {
        supController = controller;
        supController.setSupplierPanel(this);
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

        addSupplier = new JButton("Lägg till leverantör");
        removeSupplier = new JButton("Ta bort leverantör");
        updateSupplier = new JButton("Uppdatera leverantör");
        addSupplier.setSize(new Dimension(120, 30));
        removeSupplier.setSize(new Dimension(100, 30));
        updateSupplier.setSize(new Dimension(100, 30));
        northPanel.add(addSupplier);
        northPanel.add(removeSupplier);
        northPanel.add(updateSupplier);

        westPanel.setBorder(new TitledBorder("Leverantörer"));
        supplierJList = new JList<>(supController.getSupplierList().toArray(new Supplier[0]));
        supplierJList.setPreferredSize(new Dimension(200, 550));
        supplierJList.setEnabled(true);
        supplierJList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        westPanel.add(supplierJList);

        centerPanel.setBorder(new TitledBorder("Info"));
        Font font = new Font("Times New Roman", Font.BOLD, 18);
        supplierInfoArea = new JPanel(null);
        nameLabel = new JLabel("");
        nameLabel.setBounds(20, 20, 300, 40);
        nameLabel.setFont(new Font("Time New Roman", Font.BOLD, 20));
        phoneLabel = new JLabel("");
        phoneLabel.setBounds(20, 70, 300, 40);
        phoneLabel.setFont(font);
        emailLabel = new JLabel("");
        emailLabel.setBounds(20, 120, 300, 40);
        emailLabel.setFont(font);
        addressLabel = new JLabel("");
        addressLabel.setBounds(20, 170, 300, 40);
        addressLabel.setFont(font);
        cityLabel = new JLabel("");
        cityLabel.setBounds(20, 220, 300, 40);
        cityLabel.setFont(font);
        zipLabel = new JLabel("");
        zipLabel.setBounds(20, 270, 300, 40);
        zipLabel.setFont(font);
        countryLabel = new JLabel("");
        countryLabel.setBounds(20, 320, 300, 40);
        countryLabel.setFont(font);
        dodLabel = new JLabel("");
        dodLabel.setBounds(20, 370, 300, 40);
        dodLabel.setFont(font);
        supplierInfoArea.add(nameLabel);
        supplierInfoArea.add(phoneLabel);
        supplierInfoArea.add(emailLabel);
        supplierInfoArea.add(addressLabel);
        supplierInfoArea.add(cityLabel);
        supplierInfoArea.add(zipLabel);
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
    public int addSupplier(Supplier supplier) {
        JTextField supName = new JTextField(5);
        JTextField supPhone = new JTextField(5);
        JTextField supEmail = new JTextField(5);
        JTextField supAddress = new JTextField(5);
        JTextField supCity = new JTextField(5);
        JTextField supZip = new JTextField(5);
        JTextField supCountry = new JTextField(5);
        cmbWeekDays = new JComboBox(supController.getWeekDays());

        if (supplier != null) {
            supName.setText(supplier.getName());
            supPhone.setText(supplier.getPhonenumber());
            supEmail.setText(supplier.getEmail());
            supAddress.setText(supplier.getAddress());
            supCity.setText(supplier.getCity());
            supZip.setText(supplier.getZip());
            supCountry.setText(supplier.getCountry());
            cmbWeekDays.setSelectedItem(supplier.getDayOfDelivery());
        }

        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(9, 2, 2, 2));
        panel.add(new JLabel("Alla fält är obligatioriska"));
        panel.add(new JLabel(""));
        panel.add(new JLabel("Namn: "));
        panel.add(supName);
        panel.add(new JLabel("Telefonnummer: "));
        panel.add(supPhone);
        panel.add(new JLabel("Emailadress: "));
        panel.add(supEmail);
        panel.add(new JLabel("Adress: "));
        panel.add(supAddress);
        panel.add(new JLabel("Stad: "));
        panel.add(supCity);
        panel.add(new JLabel("Postkod: "));
        panel.add(supZip);
        panel.add(new JLabel("Land: "));
        panel.add(supCountry);
        panel.add(new JLabel("Leveransdag: "));
        panel.add(cmbWeekDays);

        int result = JOptionPane.showConfirmDialog(null, panel,
                "Lägg till leverantör", JOptionPane.OK_CANCEL_OPTION);

        if (result == JOptionPane.OK_OPTION) {
            if ((supName.getText().isBlank()) || (supPhone.getText().isBlank()) || (supEmail.getText().isBlank()) || (supAddress.getText().isBlank()) || (supCity.getText().isBlank()) || (supZip.getText().isBlank()) || (supCountry.getText().isBlank())) {
                JOptionPane.showMessageDialog(null, "Info saknas");
                return 3;
            } else {
                String name = (supName.getText().toUpperCase());
                String phone = supPhone.getText();
                String email = supEmail.getText();
                String address = supAddress.getText();
                String city = supCity.getText();
                String zip = supZip.getText();
                String country = supCountry.getText();
                supController.createNewSupplier(name, address, city, zip, country, email, phone, (WeekDay) getCmbWeekDays().getSelectedItem()); // När det skall skickas till controller.
            }
        }
        return result;
    }

    /**
     * Update shown supplier info
     *
     * @param evt
     */
    private void listValueChanged(ListSelectionEvent evt) {
        if (!supplierJList.getValueIsAdjusting())
            if (supplierJList.getSelectedIndex() >= 0) {
                Supplier tempSup = supplierJList.getSelectedValue();
                nameLabel.setText(tempSup.getName() + " kontaktuppgifter:");
                phoneLabel.setText("Telefon: " + tempSup.getPhonenumber());
                emailLabel.setText("Email: " + tempSup.getEmail());
                addressLabel.setText("Adress: " + tempSup.getAddress());
                cityLabel.setText("Stad: " + tempSup.getCity());
                zipLabel.setText("Zip-kod: " + tempSup.getZip());
                countryLabel.setText("Land: " + tempSup.getCountry());
                dodLabel.setText("Leveransdag: " + tempSup.getDayOfDelivery().name());
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
            if (e.getSource() == addSupplier)
                addSupplier(null);

            if (e.getSource() == removeSupplier) {
                if (supplierJList.getSelectedValue() != null) {
                    int confirmation = JOptionPane.showConfirmDialog(null, "Är du säker på att du vill ta bort denna leverantör?", "Varning", JOptionPane.OK_CANCEL_OPTION);
                    if (confirmation == JOptionPane.OK_OPTION) {
                        supController.removeSupplier(supplierJList.getSelectedValue());
                    }
                } else JOptionPane.showMessageDialog(null, "Vänligen välj en leverantör från listan");
            }
            if (e.getSource() == updateSupplier) {
                if (supplierJList.getSelectedValue() != null) {
                    Supplier s = supplierJList.getSelectedValue();
                    int updateSuccess = addSupplier(s);
                    if (updateSuccess == 0) {
                        supController.removeSupplier(s);
                    }
                } else JOptionPane.showMessageDialog(null, "Vänligen välj en leverantör från listan");

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

    public void setSupplierJList(Supplier[] supplierJList) {
        this.supplierJList.setListData(supplierJList);
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


    public void setSupplierJList(JList<Supplier> supplierJList) {
        this.supplierJList = supplierJList;
    }

    public JPanel getSupplierInfoArea() {
        return supplierInfoArea;
    }

    public void setSupplierInfoArea(JPanel supplierInfoArea) {
        this.supplierInfoArea = supplierInfoArea;
    }

    public JLabel getNameLabel() {
        return nameLabel;
    }

    public void setNameLabel(JLabel nameLabel) {
        this.nameLabel = nameLabel;
    }

    public JLabel getPhoneLabel() {
        return phoneLabel;
    }

    public void setPhoneLabel(JLabel phoneLabel) {
        this.phoneLabel = phoneLabel;
    }

    public JLabel getEmailLabel() {
        return emailLabel;
    }

    public void setEmailLabel(JLabel emailLabel) {
        this.emailLabel = emailLabel;
    }

    public JLabel getAddressLabel() {
        return addressLabel;
    }

    public void setAddressLabel(JLabel addressLabel) {
        this.addressLabel = addressLabel;
    }

    public JLabel getCityLabel() {
        return cityLabel;
    }

    public void setCityLabel(JLabel cityLabel) {
        this.cityLabel = cityLabel;
    }

    public JLabel getZipLabel() {
        return zipLabel;
    }

    public void setZipLabel(JLabel zipLabel) {
        this.zipLabel = zipLabel;
    }

    public JLabel getCountryLabel() {
        return countryLabel;
    }

    public void setCountryLabel(JLabel countryLabel) {
        this.countryLabel = countryLabel;
    }

    public JLabel getDodLabel() {
        return dodLabel;
    }

    public void setDodLabel(JLabel dodLabel) {
        this.dodLabel = dodLabel;
    }
}
