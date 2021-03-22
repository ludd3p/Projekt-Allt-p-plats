package View;

import Model.Supplier;

import javax.swing.*;
import javax.swing.border.TitledBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.net.UnknownHostException;
import java.sql.SQLException;


public class SupplierPanel extends JPanel {

    private JPanel northPanel;
    private JPanel centerPanel;
    private JList<Supplier> supplierJList;
    private JButton addSupplier, removeSupplier, updateSupplier;

    public SupplierPanel(){
        setLayout(new BorderLayout());
        setupPanels();
    }

    private void setupPanels() {
        northPanel = new JPanel();
        centerPanel = new JPanel();

        addSupplier = new JButton("Add supplier");
        removeSupplier = new JButton("Remove supplier");
        updateSupplier = new JButton("Update supplier");
        addSupplier.setSize(new Dimension(100,30));
        removeSupplier.setSize(new Dimension(100,30));
        updateSupplier.setSize(new Dimension(100,30));
        northPanel.add(addSupplier);
        northPanel.add(removeSupplier);
        northPanel.add(updateSupplier);

        centerPanel.setBorder(new TitledBorder("Suppliers"));
        supplierJList = new JList<>();
        supplierJList.setPreferredSize(new Dimension(900, 550));
        supplierJList.setEnabled(true);
        supplierJList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        centerPanel.setLayout(new BorderLayout(5, 0));
        centerPanel.add(supplierJList);

        this.add(northPanel, BorderLayout.NORTH);
        this.add(centerPanel, BorderLayout.CENTER);

        addListeners();
    }

    public void addSupplierToList(Supplier[] supplier){
        if (supplier != null){
            supplierJList.setListData(supplier);
        }
    }

    public void addSupplier(){
        JTextField supName = new JTextField(5);
        JTextField supPhone = new JTextField(5);
        JTextField supEmail = new JTextField(5);
        JTextField supAddress = new JTextField(5);
        JTextField supCity = new JTextField(5);
        JTextField supCountry = new JTextField(5);

        JPanel panel = new JPanel();
        panel.setPreferredSize(new Dimension(320,133));
        panel.setLayout(new GridLayout(6,2,2,2));
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

        int result = JOptionPane.showConfirmDialog(null, panel,
                "Add supplier", JOptionPane.OK_CANCEL_OPTION);
        if (result == JOptionPane.OK_OPTION) {
            if ((supName.getText().isBlank()) || (supPhone.getText().isBlank()) || (supAddress.getText().isBlank()) || (supCity.getText().isBlank()) || (supCountry.getText().isBlank())) {
                JOptionPane.showMessageDialog(null, "Input missing");
            } else {
                try{
                    String name = (supName.getText().toUpperCase());
                    String phone = supPhone.getText();
                    String email = supEmail.getText();
                    String address = supAddress.getText();
                    String city = supCity.getText();
                    String country = supCountry.getText();
                    //controller.addNewSupplier(name,phone,email,address,city,country); // När det skall skickas till controller.
                } catch(NumberFormatException e){
                    e.printStackTrace();
                }
            }
        }
    }

    public void updateSupplier(){
        JTextField supName = new JTextField(supplierJList.getSelectedValue().getName(), 5);
        JTextField supPhone = new JTextField(Integer.toString(supplierJList.getSelectedValue().getPhonenumber()), 5);
        JTextField supEmail = new JTextField(supplierJList.getSelectedValue().getEmail(), 5);
        JTextField supAddress = new JTextField(supplierJList.getSelectedValue().getAddress(), 5);
        JTextField supCity = new JTextField(supplierJList.getSelectedValue().getCity(), 5);
        JTextField supCountry = new JTextField(supplierJList.getSelectedValue().getCountrty(), 5);

        JPanel panel = new JPanel();
        panel.setPreferredSize(new Dimension(320,133));
        panel.setLayout(new GridLayout(6,2,2,2));
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

        int result = JOptionPane.showConfirmDialog(null, panel,
                "Update supplier", JOptionPane.OK_CANCEL_OPTION);
        if (result == JOptionPane.OK_OPTION) {
            if ((supName.getText().isBlank()) || (supPhone.getText().isBlank()) || (supAddress.getText().isBlank()) || (supCity.getText().isBlank()) || (supCountry.getText().isBlank())) {
                JOptionPane.showMessageDialog(null, "Input missing");
            } else {
                try{
                    String name = (supName.getText().toUpperCase());
                    String phone = supPhone.getText();
                    String email = supEmail.getText();
                    String address = supAddress.getText();
                    String city = supCity.getText();
                    String country = supCountry.getText();
                    //controller.updateCurrentSupplier(name,phone,email,address,city,country); // När det skall skickas till controller.
                } catch(NumberFormatException e){
                    e.printStackTrace();
                }
            }
        }
    }

    private void addListeners()
    {
        ActionListener listener = new ButtonActionListeners();
        addSupplier.addActionListener(listener);
        removeSupplier.addActionListener(listener);
        updateSupplier.addActionListener(listener);
    }

    class ButtonActionListeners implements ActionListener {

        public void actionPerformed(ActionEvent e) {
            if (e.getSource() == addSupplier) {
                addSupplier();
            }
            if (e.getSource() == removeSupplier) {

            }
            if (e.getSource() == updateSupplier) {
                if (supplierJList.getSelectedValue() != null) {
                    updateSupplier();
                } else {
                    JOptionPane.showMessageDialog(null, "Please select a supplier");
                }
            }
        }
    }

}
