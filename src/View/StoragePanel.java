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

    public StoragePanel() {
        setupPanel();
    }

    private void setupPanel(){
        setBorder(new EtchedBorder());
        setLayout(new BorderLayout());
        setupNorthPanel();
        setupCenterPanel();

        //productList.getColumnModel().getColumn(0).setHeaderValue("tas");
        //productList.getColumnModel().getColumn(1).setHeaderValue("Mängd");

        //addButtons();
    }

    private void setupNorthPanel(){
        pnlNorth = new JPanel();
        //pnlNorth.setLayout(new BorderLayout());
        pnlNorth.setBorder(new EtchedBorder(EtchedBorder.RAISED));
        addButtons();
        add(pnlNorth, BorderLayout.NORTH);
    }

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

    private void addButtons(){
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

    private class ProductWindow extends JFrame{
        private JPanel pnlProductWindowContainer;
        private JPanel pnlProductWindowCenter;
        private JPanel pnlProductWindowSouth;

        private JLabel lblProductName;
        private JTextField txfProductName;

        private JLabel lblMinAmount;
        private JTextField txfMinAmount;

        private JLabel lblMaxAmount;
        private JTextField txfMaxAmount;

        private JButton btnOk;
        private JButton btnCancel;

        //true = add, false = change
        private boolean addOrChange;

        /**
         * Constructor used when adding a new product.
         */
        public ProductWindow(){
            addOrChange = true;

            setupPanel();
        }

        /**
         * Constructor used when changing values for a product.
         * @param productName
         * @param minAmount
         * @param maxAmount
         */
        public ProductWindow(String productName, String minAmount, String maxAmount){
            addOrChange = false;

            setupPanel();

            txfProductName.setText(productName);
            txfMinAmount.setText(minAmount);
            txfMaxAmount.setText(maxAmount);
        }

        private void setupPanel(){
            pnlProductWindowContainer = new JPanel();
            pnlProductWindowContainer.setBorder(new EtchedBorder(EtchedBorder.RAISED));
            pnlProductWindowContainer.setLayout(new BorderLayout());

            setupCenterPanel();
            setupSouthPanel();

            setLocation(400, 300);
            setContentPane(pnlProductWindowContainer);
            setResizable(false);
            pack();
            setVisible(true);
        }

        private void setupCenterPanel(){
            pnlProductWindowCenter = new JPanel();
            pnlProductWindowCenter.setBorder(new EtchedBorder(EtchedBorder.RAISED));
            pnlProductWindowCenter.setLayout(new GridLayout(3,2));

            lblProductName = new JLabel("Product: ");
            pnlProductWindowCenter.add(lblProductName);
            txfProductName = new JTextField();
            txfProductName.setPreferredSize(new Dimension(100, txfProductName.getHeight()));
            pnlProductWindowCenter.add(txfProductName);

            lblMinAmount = new JLabel("Min");
            pnlProductWindowCenter.add(lblMinAmount);
            txfMinAmount = new JTextField();
            pnlProductWindowCenter.add(txfMinAmount);

            lblMaxAmount = new JLabel("Max");
            pnlProductWindowCenter.add(lblMaxAmount);
            txfMaxAmount = new JTextField();
            pnlProductWindowCenter.add(txfMaxAmount);

            pnlProductWindowContainer.add(pnlProductWindowCenter, BorderLayout.CENTER);
        }

        private void setupSouthPanel(){
            pnlProductWindowSouth = new JPanel();
            pnlProductWindowSouth.setLayout(new GridLayout(1,2));

            ActionListener listener = new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    if(e.getSource() == btnOk){
                        if(addOrChange) {
                            model.addElement(String.format("%s %s %s",
                                    "Product: " + txfProductName.getText(),
                                    "Min amount: " + txfMinAmount.getText(),
                                    "Max amount: " + txfMaxAmount.getText()));
                            dispose();
                        }
                        else if(!addOrChange){
                            model.setElementAt(String.format("%s %s %s",
                                    "Product: " + txfProductName.getText(),
                                    "Min amount: " + txfMinAmount.getText(),
                                    "Max amount: " + txfMaxAmount.getText()),
                                    productList.getSelectedIndex());
                            dispose();
                        }
                    }
                    else if(e.getSource() == btnCancel){
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

            pnlProductWindowContainer.add(pnlProductWindowSouth, BorderLayout.SOUTH);
        }

    }
}
