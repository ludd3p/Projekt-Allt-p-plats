package View;

import javax.swing.*;
import javax.swing.border.EtchedBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class StoragePanel extends JPanel {
    private JPanel pnlNorth;
    private JPanel pnlCenter;

    private JList productList;
    private DefaultListModel model;
    private JScrollPane scrollPane;

    private JButton btnUpdateList;
    private JButton btnAddProduct;
    private JButton btnChangeProduct;
    private JButton btnRemoveProduct;

    /**
     * Constructor.
     */
    public StoragePanel() {
        setupMainPanel();
    }

    /**
     * Setup for the main panel.
     */
    private void setupMainPanel(){
        setBorder(new EtchedBorder());
        setLayout(new BorderLayout());
        setupNorthPanel();
        setupCenterPanel();
    }

    /**
     * Adds components to and configure pnlNorth and adds it to the main panel.
     */
    private void setupNorthPanel(){
        pnlNorth = new JPanel();
        //pnlNorth.setLayout(new BorderLayout());
        pnlNorth.setBorder(new EtchedBorder(EtchedBorder.RAISED));
        addButtonsNorthPanel();
        add(pnlNorth, BorderLayout.NORTH);
    }

    /**
     * Adds components to and configure pnlCenter and adds it to the main panel.
     */
    private void setupCenterPanel(){
        pnlCenter = new JPanel();
        pnlCenter.setLayout(new BorderLayout());
        pnlCenter.setBorder(new EtchedBorder(EtchedBorder.RAISED));
        model = new DefaultListModel();
        productList = new JList(model);
        scrollPane = new JScrollPane(productList);
        pnlCenter.add(scrollPane);
        add(pnlCenter, BorderLayout.CENTER);
        test();
    }

    /**
     * Configures and adds buttons to pnlNorth.
     */
    private void addButtonsNorthPanel(){
        ActionListener listener = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(e.getSource() == btnAddProduct){
                    JFrame productWindow = new ProductWindow();
                }
                else if(e.getSource() == btnChangeProduct){
                    String selected = (String)productList.getSelectedValue();
                    JFrame productWindow = new ProductWindow(
                            selected.substring(selected.indexOf("Product: ") + "Product: ".length(), selected.indexOf("Min") - 1),
                            selected.substring(selected.indexOf("Min amount: ") + "Min amount: ".length(), selected.indexOf("Max") - 1),
                            selected.substring(selected.indexOf("Max amount: ") + "Max amount: ".length()));
                }
                else if(e.getSource() == btnRemoveProduct){
                    model.remove(productList.getSelectedIndex());
                }
                else if(e.getSource() == btnUpdateList){

                }
            }
        };

        btnAddProduct = new JButton("Add");
        btnAddProduct.addActionListener(listener);

        btnChangeProduct = new JButton("Change");
        btnChangeProduct.addActionListener(listener);

        btnRemoveProduct = new JButton("Remove");
        btnRemoveProduct.addActionListener(listener);

        btnUpdateList = new JButton("Update");
        btnUpdateList.addActionListener(listener);

        pnlNorth.add(btnAddProduct);
        pnlNorth.add(btnChangeProduct);
        pnlNorth.add(btnRemoveProduct);
        pnlNorth.add(btnUpdateList);
    }

    /**
     * Inserts test-values to productList.
     */
    private void test(){
        for(int i = 0; i < 10;  i++) {
            model.addElement(String.format("%s %s %s",
                    "Product: " + "Potatis",
                    "Min amount: " + "10",
                    "Max amount: " + "100"));
            model.addElement(String.format("%s %s %s",
                    "Product: " + "Mjölk",
                    "Min amount: " + "10",
                    "Max amount: " + "100"));
        }
    }

    /**
     * Inner-class used to create a window for inserting and changing products.
     */
    private class ProductWindow extends JFrame{
        private JPanel pnlProductWindowMainPanel;
        private JPanel pnlProductWindowCenter;
        private JPanel pnlProductWindowSouth;

        private JLabel lblProductName;
        private JTextField txfProductName;

        private JLabel lblMinAmount;
        private JTextField txfMinAmount;
        private JComboBox cbxMinAmountUnit;

        private JLabel lblMaxAmount;
        private JTextField txfMaxAmount;
        private JComboBox cbxMaxAmountUnit;

        private JButton btnOk;
        private JButton btnCancel;

        //true = add, false = change
        private boolean addOrChange;

        /**
         * Constructor used when adding a new product.
         */
        public ProductWindow(){
            addOrChange = true;

            setupProductWindow();
        }

        /**
         * Constructor used when changing values for a product.
         * @param productName
         * @param minAmount
         * @param maxAmount
         */
        public ProductWindow(String productName, String minAmount, String maxAmount){
            addOrChange = false;

            setupProductWindow();

            txfProductName.setText(productName);
            txfMinAmount.setText(minAmount);
            txfMaxAmount.setText(maxAmount);
        }

        /**
         * Configures the frame and adds the main panel to it. Also calls methods to add components to the main panel.
         */
        private void setupProductWindow(){
            pnlProductWindowMainPanel = new JPanel();
            pnlProductWindowMainPanel.setBorder(new EtchedBorder(EtchedBorder.RAISED));
            pnlProductWindowMainPanel.setLayout(new BorderLayout());

            setupProductWindowCenterPanel();
            setupProductWindowSouthPanel();

            setLocation(400, 300);
            setContentPane(pnlProductWindowMainPanel);
            setResizable(false);
            pack();
            setVisible(true);
        }

        /**
         * Configures and adds components to pnlProductWindowCenter and adds it to the main panel.
         */
        private void setupProductWindowCenterPanel(){
            pnlProductWindowCenter = new JPanel();
            pnlProductWindowCenter.setBorder(new EtchedBorder(EtchedBorder.RAISED));
            pnlProductWindowCenter.setLayout(new GridLayout(3,3));

            lblProductName = new JLabel("Product: ");
            pnlProductWindowCenter.add(lblProductName);
            txfProductName = new JTextField();
            txfProductName.setPreferredSize(new Dimension(100, txfProductName.getHeight()));
            pnlProductWindowCenter.add(txfProductName);
            JPanel pnlPadding = new JPanel();
            pnlProductWindowCenter.add(pnlPadding);

            lblMinAmount = new JLabel("Min");
            pnlProductWindowCenter.add(lblMinAmount);
            txfMinAmount = new JTextField();
            pnlProductWindowCenter.add(txfMinAmount);
            cbxMinAmountUnit = new JComboBox();
            pnlProductWindowCenter.add(cbxMinAmountUnit);

            lblMaxAmount = new JLabel("Max");
            pnlProductWindowCenter.add(lblMaxAmount);
            txfMaxAmount = new JTextField();
            pnlProductWindowCenter.add(txfMaxAmount);
            cbxMaxAmountUnit = new JComboBox();
            pnlProductWindowCenter.add(cbxMaxAmountUnit);

            pnlProductWindowMainPanel.add(pnlProductWindowCenter, BorderLayout.CENTER);
        }

        /**
         * Configures and adds components to pnlProductWindowSouth and adds it to the main panel.
         */
        private void setupProductWindowSouthPanel(){
            pnlProductWindowSouth = new JPanel();
            pnlProductWindowSouth.setLayout(new GridLayout(1,2));

            ActionListener listener = new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    if(e.getSource() == btnOk) {
                        try {
                            if (addOrChange) {
                                model.addElement(String.format("%s %s %s",
                                        "Product: " + txfProductName.getText(),
                                        "Min amount: " + Double.parseDouble(txfMinAmount.getText()),
                                        "Max amount: " + Double.parseDouble(txfMaxAmount.getText())));
                                dispose();

                            } else if (!addOrChange) {
                                model.setElementAt(String.format("%s %s %s",
                                        "Product: " + txfProductName.getText(),
                                        "Min amount: " + Double.parseDouble(txfMinAmount.getText()),
                                        "Max amount: " + Double.parseDouble(txfMaxAmount.getText())),
                                        productList.getSelectedIndex());
                                dispose();
                            }
                        } catch (NumberFormatException nfe) {
                            JOptionPane.showMessageDialog(null, "Error: Invalid input.");
                        }
                    }
                    else if (e.getSource() == btnCancel) {
                            dispose();
                    }
                }
            };

            btnOk = new JButton("Ok");
            btnOk.addActionListener(listener);
            pnlProductWindowSouth.add(btnOk);

            btnCancel = new JButton("Cancel");
            btnCancel.addActionListener(listener);
            pnlProductWindowSouth.add(btnCancel);

            pnlProductWindowMainPanel.add(pnlProductWindowSouth, BorderLayout.SOUTH);
        }

    }
}
