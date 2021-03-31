package View;

import Controller.Controller;

import javax.swing.*;
import javax.swing.border.EtchedBorder;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;

/**
 * Panel used to keep track of products.
 * @Author Jonathan Engström
 * @Version 1.0
 */

public class StoragePanel extends JPanel {
    private Controller controller;

    private JPanel pnlNorth;
    private JPanel pnlCenter;

    private JList productList;
    private DefaultListModel model;
    private JScrollPane scrollPane;

    private ArrayList<String> values = new ArrayList<>();
    private JTextField txfFilter;
    private JButton btnUpdateList;
    private JButton btnAddProduct;
    private JButton btnChangeProduct;
    private JButton btnRemoveProduct;

    /**
     * Constructor.
     */
    public StoragePanel(Controller controller) {
        this.controller = controller;
        setupMainPanel();
        controller.getIngredientsFromDatabase();
    }

    /**
     * Updates/Adds products on/to the list.
     * @param values
     */
    public void updateList(ArrayList<String> values){
        this.values = values;
        model.clear();
        model.addAll(this.values);
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

        addComponentsNorthPanel();
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
        productList.setCellRenderer(new CellRenderer());
        scrollPane = new JScrollPane(productList);
        pnlCenter.add(scrollPane);
        add(pnlCenter, BorderLayout.CENTER);
    }

    /**
     * Inner-class used to change the look of the JList
     */
    private class CellRenderer extends DefaultListCellRenderer{
        public Component getListCellRendererComponent(JList list, Object value, int index, boolean isSelected, boolean cellHasFocus){
            setText(value.toString());

            if(index % 2 == 0){
                setBackground(Color.WHITE);
            }
            else{
                setBackground(Color.LIGHT_GRAY);
            }

            if(isSelected){
                setBackground(productList.getSelectionBackground());
                setForeground(productList.getForeground());
                setBorder(new LineBorder(Color.BLUE));
            }
            else{
                setBorder(null);
            }

            return this;
        }
    }

    /**
     * Method to filter productList based on user input in txfFilter.
     * @param model
     * @param filter text in txfFilter
     */
    private void filterModel(DefaultListModel<String> model, String filter){
        model.clear();
        for(String s : values){
            if(s.substring(s.indexOf("Product: ") + "Product: ".length(), s.indexOf("<br>Min") - 1).toLowerCase().startsWith(filter.toLowerCase())){
                model.addElement(s);
            }
        }
    }

    /**
     * Configures and adds components to pnlNorth.
     */
    private void addComponentsNorthPanel(){
        ActionListener actionListener = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(e.getSource() == btnAddProduct){
                    JFrame productWindow = new ProductWindow();
                }
                else if(e.getSource() == btnChangeProduct){
                    if(productList.getSelectedValue() != null) {
                        String selected = (String) productList.getSelectedValue();
                        JFrame productWindow = new ProductWindow(
                                selected.substring(selected.indexOf("Product: ") + "Product: ".length(), selected.indexOf("<br>Cost") - 1),
                                selected.substring(selected.indexOf("Cost: ") + "Cost: ".length(), selected.lastIndexOf(" ", selected.indexOf("<br>Min") - 2)),
                                selected.substring(selected.indexOf("Min amount: ") + "Min amount: ".length(), selected.lastIndexOf(" ", selected.indexOf("Max") - 2)),
                                selected.substring(selected.indexOf("Max amount: ") + "Max amount: ".length(), selected.lastIndexOf(" ", selected.indexOf("</html>") - 2)));
                    }
                    else{
                        JOptionPane.showMessageDialog(null, "Select a product to change.");
                    }
                }
                else if(e.getSource() == btnRemoveProduct){
                    int answer = -1;

                    if(productList.getSelectedValue() != null){
                       answer = JOptionPane.showConfirmDialog(null, "Are you sure?", "Remove product",  JOptionPane.YES_NO_OPTION);
                    }
                    else{
                        JOptionPane.showMessageDialog(null, "Select a product to remove first.");
                    }

                    if(answer == 0) {
                        String selected = (String) productList.getSelectedValue();
                        controller.removeIngredientFromDatabase(selected.substring(selected.indexOf("Product: ") + "Product: ".length(), selected.indexOf("<br>Cost") - 1));
                        controller.getIngredientsFromDatabase();
                    }
                }
                else if(e.getSource() == btnUpdateList){
                    controller.getIngredientsFromDatabase();
                }
            }
        };

        KeyListener keyListener = new KeyListener() {
            @Override
            public void keyTyped(KeyEvent e) {

            }

            @Override
            public void keyPressed(KeyEvent e) {

            }

            @Override
            public void keyReleased(KeyEvent e) {
                filterModel(model, txfFilter.getText());
            }
        };

        FocusListener focusListener = new FocusListener() {
            @Override
            public void focusGained(FocusEvent e) {
                txfFilter.selectAll();
            }

            @Override
            public void focusLost(FocusEvent e) {
                if(txfFilter.getText().equals("")){
                    txfFilter.setText("Search");
                }
            }
        };

        txfFilter = new JTextField("Search");
        txfFilter.setPreferredSize(new Dimension(100, 27));
        txfFilter.addFocusListener(focusListener);
        txfFilter.addKeyListener(keyListener);
        pnlNorth.add(txfFilter);

        btnAddProduct = new JButton("Add");
        btnAddProduct.addActionListener(actionListener);

        btnChangeProduct = new JButton("Change");
        btnChangeProduct.addActionListener(actionListener);

        btnRemoveProduct = new JButton("Remove");
        btnRemoveProduct.addActionListener(actionListener);

        btnUpdateList = new JButton("Update");
        btnUpdateList.addActionListener(actionListener);

        pnlNorth.add(btnAddProduct);
        pnlNorth.add(btnChangeProduct);
        pnlNorth.add(btnRemoveProduct);
        pnlNorth.add(btnUpdateList);
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

        private JLabel lblCost;
        private JTextField txfCost;

        private JLabel lblMinAmount;
        private JTextField txfMinAmount;

        private JLabel lblMaxAmount;
        private JTextField txfMaxAmount;

        private JLabel lblUnit;
        private JComboBox cbxUnit;

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
        public ProductWindow(String productName, String cost ,String minAmount, String maxAmount){
            addOrChange = false;
            setupProductWindow();

            txfProductName.setText(productName);
            txfCost.setText(cost);
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
            pnlProductWindowCenter.setLayout(new GridLayout(5,2,1 ,1));

            lblProductName = new JLabel("Product: ");
            pnlProductWindowCenter.add(lblProductName);
            txfProductName = new JTextField();
            txfProductName.setPreferredSize(new Dimension(100, txfProductName.getHeight()));
            pnlProductWindowCenter.add(txfProductName);

            lblCost = new JLabel("Cost: ");
            pnlProductWindowCenter.add(lblCost);
            txfCost = new JTextField();
            pnlProductWindowCenter.add(txfCost);

            lblMinAmount = new JLabel("Min:");
            pnlProductWindowCenter.add(lblMinAmount);
            txfMinAmount = new JTextField();
            pnlProductWindowCenter.add(txfMinAmount);

            lblMaxAmount = new JLabel("Max:");
            pnlProductWindowCenter.add(lblMaxAmount);
            txfMaxAmount = new JTextField();
            pnlProductWindowCenter.add(txfMaxAmount);

            lblUnit = new JLabel("Unit:");
            pnlProductWindowCenter.add(lblUnit);
            cbxUnit = new JComboBox(controller.getUnitsPrefixArray());
            pnlProductWindowCenter.add(cbxUnit);

            pnlProductWindowMainPanel.add(pnlProductWindowCenter, BorderLayout.CENTER);
        }

        /**
         * Configures and adds components to pnlProductWindowSouth and adds it to the main panel.
         */
        private void setupProductWindowSouthPanel(){
            pnlProductWindowSouth = new JPanel();
            pnlProductWindowSouth.setBorder(new EtchedBorder(EtchedBorder.RAISED));
            pnlProductWindowSouth.setLayout(new GridLayout(1,2, 1, 1));

            ActionListener listener = new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    if(e.getSource() == btnOk) {
                        try {
                            if(addOrChange) {
                                boolean proceed = true;
                                for(String value : values){
                                    if(value.substring(value.indexOf("Product: ") + "Product: ".length(), value.indexOf("<br>Cost") - 1).toLowerCase().equals(txfProductName.getText().toLowerCase())){
                                        JOptionPane.showMessageDialog(null, "That product already exists.", "Product already exists", JOptionPane.PLAIN_MESSAGE);
                                        proceed = false;
                                        System.out.println(value + " : " + txfProductName.getText());
                                    }
                                }
                                if(proceed) {
                                    controller.addIngredientToDatabase(
                                            txfProductName.getText(),
                                            Double.parseDouble(txfCost.getText()),
                                            Double.parseDouble(txfMinAmount.getText()),
                                            Double.parseDouble(txfMaxAmount.getText()),
                                            (String) cbxUnit.getSelectedItem());
                                    controller.getIngredientsFromDatabase();
                                    dispose();
                                }
                            }
                            else {
                                String selected = (String)productList.getSelectedValue();
                                String key = selected.substring(selected.indexOf("<!--") + "<!--".length(), selected.indexOf("-->"));
                                controller.changeProductInDatabase(
                                        key,
                                        txfProductName.getText(),
                                        Double.parseDouble(txfCost.getText()),
                                        Double.parseDouble(txfMinAmount.getText()),
                                        Double.parseDouble(txfMaxAmount.getText()),
                                        (String) cbxUnit.getSelectedItem());
                                controller.getIngredientsFromDatabase();
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
