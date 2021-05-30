package view;

import controller.StorageController;
import model.ingredient.Ingredient;

import javax.swing.*;
import javax.swing.border.EtchedBorder;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.awt.event.*;

/**
 * Panel used to keep track of products in storage.
 *
 * @Author Jonathan Engström
 * @Version 3.0
 */

public class StoragePanel extends JPanel {
    private StorageController storageController;

    private JPanel pnlNorth;
    private JPanel pnlCenter;

    private JList<Ingredient> productList;
    private DefaultListModel<Ingredient> model;
    private JScrollPane scrollPane;

    private JLabel lblFilter;
    private JTextField txfFilter;
    private JButton btnUpdateList;
    private JButton btnAddProduct;
    private JButton btnChangeProduct;
    private JButton btnRemoveProduct;

    /**
     * Constructor.
     */
    public StoragePanel(StorageController storageController) {
        this.storageController = storageController;
        setupMainPanel();
        storageController.setUp(this);
    }


    /**
     * Setup for the main panel.
     */
    private void setupMainPanel() {
        setBorder(new EtchedBorder());
        setLayout(new BorderLayout());
        setupNorthPanel();
        setupCenterPanel();
    }

    /**
     * Adds components to and configure pnlNorth and adds it to the main panel.
     */
    private void setupNorthPanel() {
        pnlNorth = new JPanel();
        pnlNorth.setBorder(new EtchedBorder(EtchedBorder.RAISED));

        addComponentsNorthPanel();
        add(pnlNorth, BorderLayout.NORTH);
    }

    /**
     * Adds components to and configure pnlCenter and adds it to the main panel.
     */
    private void setupCenterPanel() {
        pnlCenter = new JPanel();
        pnlCenter.setLayout(new BorderLayout());
        pnlCenter.setBorder(new EtchedBorder(EtchedBorder.RAISED));
        productList = new JList<>();
        productList.setCellRenderer(new CellRenderer());
        model = new DefaultListModel<>();
        productList.setModel(model);
        scrollPane = new JScrollPane(productList);
        pnlCenter.add(scrollPane);
        add(pnlCenter, BorderLayout.CENTER);
    }

    /**
     * Filters productList based on user input in txfFilter.
     *
     * @param model
     * @param filter text in txfFilter
     */
    private void filterModel(DefaultListModel<Ingredient> model, String filter) {
        model.clear();

        for(Ingredient ingredient : storageController.getAllIngredients())
            if(ingredient.getName().toLowerCase().startsWith(filter.toLowerCase()))
                model.addElement(ingredient);
    }

    /**
     * Configures and adds components to pnlNorth.
     */
    private void addComponentsNorthPanel() {
        ActionListener actionListener = e -> {
            if (e.getSource() == btnAddProduct)
                new ProductWindow();

            else if (e.getSource() == btnChangeProduct) {
                if (productList.getSelectedValue() != null) {
                    String selected = productList.getSelectedValue().toString();
                    new ProductWindow(
                            selected.substring(selected.indexOf("Produkt: ") + "Produkt: ".length(), selected.indexOf("<br>Kostnad") - 1),
                            selected.substring(selected.indexOf("Kostnad: ") + "Kostnad: ".length(), selected.lastIndexOf(" ", selected.indexOf("<br>Leverantör:") - 2)),
                            selected.substring(selected.indexOf("<br>Nuvarande mängd: ") + "<br>Nuvarande mängd: ".length(), selected.lastIndexOf(" ", selected.indexOf("Minsta mängd:") - 2)),
                            selected.substring(selected.indexOf("Minsta mängd: ") + "Minsta mängd: ".length(), selected.lastIndexOf(" ", selected.indexOf("Rekommenderad mängd: ") - 2)),
                            selected.substring(selected.indexOf("Rekommenderad mängd: ") + "Rekommenderad mängd: ".length(), selected.lastIndexOf(" ", selected.indexOf("</html>") - 2)),
                            selected.substring(selected.indexOf("sek/") + "sek/".length(), selected.indexOf("<br>Leverantör") - 1),
                            selected.substring(selected.indexOf("<br>Leverantör: ") + "<br>Leverantör: ".length(), selected.indexOf("<br>Nuvarande mängd:") - 1));
                } else
                    JOptionPane.showMessageDialog(null, "Välj en produkt att ändra.");

            } else if (e.getSource() == btnRemoveProduct) {
                if (productList.getSelectedValue() != null && JOptionPane.showConfirmDialog(null, "Är du säker på att du vill ta bort produkten?", "Ta bort produkt", JOptionPane.YES_NO_OPTION) == 0) {
                        String selected = productList.getSelectedValue().toString();
                        model.removeElement(productList.getSelectedIndex());
                        storageController.removeIngredient(selected.substring(selected.indexOf("Produkt: ") + "Produkt: ".length(), selected.indexOf("<br>Kostnad") - 1));
                }
                else if(productList.getSelectedValue() == null)
                    JOptionPane.showMessageDialog(null, "Välj en produkt att ta bort först.");

            } else if (e.getSource() == btnUpdateList)
                storageController.getIngredientsFromDatabase();
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

        lblFilter = new JLabel("Sök:");

        txfFilter = new JTextField();
        txfFilter.setToolTipText("Skriv in namnet på den produkt du söker efter.");
        txfFilter.setPreferredSize(new Dimension(100, 27));
        txfFilter.addKeyListener(keyListener);

        btnAddProduct = new JButton("Ny produkt");
        btnAddProduct.setToolTipText("Klicka för att lägga till  en ny produkt.");
        btnAddProduct.addActionListener(actionListener);

        btnChangeProduct = new JButton("Ändra");
        btnChangeProduct.setToolTipText("Klicka för att ändra den valda produkten.");
        btnChangeProduct.addActionListener(actionListener);

        btnRemoveProduct = new JButton("Ta bort");
        btnRemoveProduct.setToolTipText("Klicka för att ta bort den valda produkten.");
        btnRemoveProduct.addActionListener(actionListener);

        btnUpdateList = new JButton("Uppdatera listan");
        btnUpdateList.setToolTipText("Klicka för att uppdatera listan av produkter.");
        btnUpdateList.addActionListener(actionListener);

        pnlNorth.add(lblFilter);
        pnlNorth.add(txfFilter);
        pnlNorth.add(btnAddProduct);
        pnlNorth.add(btnChangeProduct);
        pnlNorth.add(btnRemoveProduct);
        pnlNorth.add(btnUpdateList);
    }

    /**
     * Inner-class used to change the look of the JList
     */
    private class CellRenderer extends DefaultListCellRenderer {
        public Component getListCellRendererComponent(JList list, Object value, int index, boolean isSelected, boolean cellHasFocus) {
            setText(value.toString());
            setBorder(new LineBorder(new Color(0, 0, 0, 0)));

            if (index % 2 == 0)
                setBackground(Color.WHITE);
            else
                setBackground(new Color(220, 220, 220));

            if (isSelected) {
                setBackground(productList.getSelectionBackground());
                setBorder(new LineBorder(Color.BLUE));
            }

            return this;
        }
    }

    /**
     * Inner-class used to create a window for inserting and changing products.
     */
    private class ProductWindow extends JFrame {
        private JPanel pnlProductWindowMain;
        private JPanel pnlProductWindowCenter;
        private JPanel pnlProductWindowSouth;

        private JLabel lblProductName;
        private JTextField txfProductName;

        private JLabel lblCost;
        private JTextField txfCost;

        private JLabel lblCurrentAmount;
        private JTextField txfCurrentAmount;

        private JLabel lblMinAmount;
        private JTextField txfMinAmount;

        private JLabel lblMaxAmount;
        private JTextField txfMaxAmount;

        private JLabel lblUnit;
        private JComboBox<String> cbxUnit;

        private JLabel lblSupplier;
        private JComboBox<String> cbxSupplier;

        private JButton btnOk;
        private JButton btnCancel;

        //true = add, false = change
        private final boolean addOrChange;

        /**
         * Constructor used when adding a new product.
         */
        public ProductWindow() {
            addOrChange = true;

            setTitle("Lägg till ny produkt");
            setupProductWindow();
        }

        /**
         * Constructor used when changing values for a product.
         *
         * @param productName name of the product.
         * @param cost of the product per unit.
         * @param currentAmount of the product in storage.
         * @param minAmount of the product that should be in storage before warning.
         * @param maxAmount of the product that should be in storage when full.
         * @param unit associated with the product.
         * @param supplier associated with the product.
         */
        public ProductWindow(String productName, String cost, String currentAmount, String minAmount, String maxAmount, String unit, String supplier) {
            addOrChange = false;
            setTitle("Ändra produkten");
            setupProductWindow();

            txfProductName.setText(productName);
            txfCost.setText(cost);
            txfCurrentAmount.setText(currentAmount);
            txfMinAmount.setText(minAmount);
            txfMaxAmount.setText(maxAmount);
            cbxUnit.setSelectedItem(unit);
            cbxSupplier.setSelectedItem(supplier);
        }

        /**
         * Configures the frame and adds the main panel to it. Also calls methods to add components to the main panel.
         */
        private void setupProductWindow() {
            pnlProductWindowMain = new JPanel();
            pnlProductWindowMain.setBorder(new EtchedBorder(EtchedBorder.RAISED));
            pnlProductWindowMain.setLayout(new BorderLayout());

            setupProductWindowCenterPanel();
            setupProductWindowSouthPanel();

            setContentPane(pnlProductWindowMain);
            setResizable(false);
            pack();
            setLocationRelativeTo(productList);
            setVisible(true);
        }

        /**
         * Configures and adds components to pnlProductWindowCenter and adds it to the main panel.
         */
        private void setupProductWindowCenterPanel() {
            pnlProductWindowCenter = new JPanel();
            pnlProductWindowCenter.setBorder(BorderFactory.createEtchedBorder(0));
            pnlProductWindowCenter.setLayout(new GridLayout(7, 2, 10, 1));

            lblProductName = new JLabel(" Produktnamn: ");
            pnlProductWindowCenter.add(lblProductName);
            txfProductName = new JTextField();
            txfProductName.setPreferredSize(new Dimension(100, txfProductName.getHeight()));
            txfProductName.setToolTipText("Skriv in namnet på den produkt du vill lägga till.");
            pnlProductWindowCenter.add(txfProductName);

            lblCost = new JLabel(" Kostnad per enhet: ");
            pnlProductWindowCenter.add(lblCost);
            txfCost = new JTextField();
            txfCost.setToolTipText("Skriv in kostnaden per vald enhet för produkten.");
            pnlProductWindowCenter.add(txfCost);

            lblCurrentAmount = new JLabel(" Nuvarande mängd:");
            pnlProductWindowCenter.add(lblCurrentAmount);
            txfCurrentAmount = new JTextField();
            txfCurrentAmount.setToolTipText("Skriv in den nuvarande mängden av denna produkt i lagret.");
            pnlProductWindowCenter.add(txfCurrentAmount);

            lblMinAmount = new JLabel(" Minsta mängd:");
            pnlProductWindowCenter.add(lblMinAmount);
            txfMinAmount = new JTextField();
            txfMinAmount.setToolTipText("Skriv in den mängd av produkten då du vill bli varnad att det behövs köpas in mer.");
            pnlProductWindowCenter.add(txfMinAmount);

            lblMaxAmount = new JLabel(" Rekommenderad mängd:");
            pnlProductWindowCenter.add(lblMaxAmount);
            txfMaxAmount = new JTextField();
            txfMaxAmount.setToolTipText("Skriv in den mängd av produkten du vill ha i lagret.");
            pnlProductWindowCenter.add(txfMaxAmount);

            lblUnit = new JLabel(" Enhet:");
            pnlProductWindowCenter.add(lblUnit);
            cbxUnit = new JComboBox<>(storageController.getUnitsPrefixArray());
            cbxUnit.setToolTipText("Välj enhet för produkten.");
            pnlProductWindowCenter.add(cbxUnit);

            lblSupplier = new JLabel(" Leverantör:");
            pnlProductWindowCenter.add(lblSupplier);
            cbxSupplier = new JComboBox<>(storageController.getSupplierNames());
            cbxSupplier.setToolTipText("Välj leverantör för produkten.");
            cbxSupplier.setPreferredSize(new Dimension(100, txfProductName.getHeight()));
            pnlProductWindowCenter.add(cbxSupplier);

            pnlProductWindowMain.add(pnlProductWindowCenter, BorderLayout.CENTER);
        }

        /**
         * Configures and adds components to pnlProductWindowSouth and adds it to the main panel.
         */
        private void setupProductWindowSouthPanel() {
            pnlProductWindowSouth = new JPanel();
            pnlProductWindowSouth.setBorder(new EtchedBorder(EtchedBorder.RAISED));
            pnlProductWindowSouth.setLayout(new GridLayout(1, 2, 1, 1));

            ActionListener listener = e -> {
                if (e.getSource() == btnOk) {
                    boolean proceed = inputErrorCheck();

                    if (proceed) {
                        if (addOrChange)
                            storageController.addIngredientToDatabase(  txfProductName.getText(),
                                                                        Double.parseDouble(txfCost.getText()),
                                                                        Double.parseDouble(txfCurrentAmount.getText()),
                                                                        Double.parseDouble(txfMinAmount.getText()),
                                                                        Double.parseDouble(txfMaxAmount.getText()),
                                                                        (String) cbxUnit.getSelectedItem(),
                                                                        (String) cbxSupplier.getSelectedItem());
                        else {
                            String selected = productList.getSelectedValue().toString();
                            String oldName = selected.substring(selected.indexOf("Produkt: ") + "Produkt: ".length(), selected.indexOf("<br>Kostnad") - 1);
                            storageController.updateIngredient( oldName,
                                                                txfProductName.getText(),
                                                                Double.parseDouble(txfCost.getText()),
                                                                Double.parseDouble(txfCurrentAmount.getText()),
                                                                Double.parseDouble(txfMinAmount.getText()),
                                                                Double.parseDouble(txfMaxAmount.getText()),
                                                                (String) cbxUnit.getSelectedItem(),
                                                                (String) cbxSupplier.getSelectedItem());
                        }

                        storageController.getIngredientsFromDatabase();
                        dispose();
                    }
                } else if (e.getSource() == btnCancel)
                    dispose();
            };

            btnOk = new JButton();
            btnOk.addActionListener(listener);
            if (addOrChange) {
                btnOk.setText("Lägg till");
                btnOk.setToolTipText("Lägg till produkten i lagret.");
            } else {
                btnOk.setText("Spara");
                btnOk.setToolTipText("Slutför ändringarna för den valda produkten.");
            }
            pnlProductWindowSouth.add(btnOk);

            btnCancel = new JButton("Avbryt");

            if (addOrChange)
                btnCancel.setToolTipText("Avbryt tilläget av produkten i lagret.");
            else
                btnCancel.setToolTipText("Avbryt ändringarna för den valda produkten..");

            btnCancel.addActionListener(listener);
            pnlProductWindowSouth.add(btnCancel);

            pnlProductWindowMain.add(pnlProductWindowSouth, BorderLayout.SOUTH);
        }

        /**
         * Checks for input related errors when adding or changing a product.
         * @return false if there's any errors, otherwise true.
         */
        private boolean inputErrorCheck(){
            if(txfProductName.getText().toLowerCase().contains("Produkt:".toLowerCase()) ||
                    txfProductName.getText().toLowerCase().contains("Kostnad:".toLowerCase()) ||
                    txfProductName.getText().toLowerCase().contains("Leverantör:".toLowerCase()) ||
                    txfProductName.getText().toLowerCase().contains("Nuvarande mängd:".toLowerCase()) ||
                    txfProductName.getText().toLowerCase().contains("Minsta mängd:".toLowerCase()) ||
                    txfProductName.getText().toLowerCase().contains("Rekommenderad mängd:".toLowerCase()) ||
                    txfProductName.getText().toLowerCase().contains("</html>")) {
                JOptionPane.showMessageDialog(null, "Otillåtet produktnamn. \nVälj ett annat produktnamn", "Otillåtet produktnamn", JOptionPane.ERROR_MESSAGE);
                return false;
            }
            if(txfProductName.getText().equals("")){
                JOptionPane.showMessageDialog(null, "Produktnamn saknas. \nFyll i ett produktnamn till höger om \"Produktnamn:\"", "Produktnamn", JOptionPane.ERROR_MESSAGE);
                return false;
            } else{
                for (Ingredient ingredient : storageController.getAllIngredients())
                    if (addOrChange && ingredient.getName().toLowerCase().equals(txfProductName.getText().toLowerCase())) {
                        JOptionPane.showMessageDialog(null, "Denna produkt finns redan.", "Produkten finns redan", JOptionPane.ERROR_MESSAGE);
                        return false;
                    }
            }
            if(txfCost.getText().equals("")){
                JOptionPane.showMessageDialog(null, "Kostnad per enhet saknas. \nFyll i produktens kostnad per enhet till höger om \"Kostnad per enhet:\".", "Kostnad saknas.", JOptionPane.ERROR_MESSAGE);
                return false;
            } else{
                try{
                    Double.parseDouble(txfCost.getText());
                }catch (NumberFormatException nfe){
                    JOptionPane.showMessageDialog(null, "Kostnad per enhet kan endast vara ett numeriskt värde.",
                            "Kostnad per enhet", JOptionPane.ERROR_MESSAGE);
                    return false;
                }
            }
            if(txfCurrentAmount.getText().equals("")){
                JOptionPane.showMessageDialog(null, "Nuvarande mängd saknas. \nFyll i den mängd av produkten som finns i lagret till höger om \"Nuvarande mängd:\".",
                        "Nuvarande mängd", JOptionPane.ERROR_MESSAGE);
                return false;
            } else{
                try {
                    Double.parseDouble(txfCurrentAmount.getText());
                }catch (NumberFormatException nfe){
                    JOptionPane.showMessageDialog(null, "Nuvarande mängd kan endast vara ett numeriskt värde.",
                            "Nuvarande mängd", JOptionPane.ERROR_MESSAGE);
                    return false;
                }
            }
            if(txfMinAmount.getText().equals("")){
                JOptionPane.showMessageDialog(null, "Minsta mängd saknas. \nFyll i den minsta mängden av produkten som ska finnas i lagret till höger om \"Minsta mängd:\".",
                        "Minsta mängd saknas", JOptionPane.ERROR_MESSAGE);
                return false;
            } else{
                try{
                    Double.parseDouble(txfMinAmount.getText());
                }catch (NumberFormatException nfe){
                    JOptionPane.showMessageDialog(null, "Minsta mängd kan endast vara ett numeriskt värde.",
                            "Minsta mängd", JOptionPane.ERROR_MESSAGE);
                    return false;
                }
            }
            if(txfMaxAmount.getText().equals("")){
                JOptionPane.showMessageDialog(null, "Rekommenderad mängd saknas. \nFyll i den rekommenderade mängden av produkten som ska finnas i lagret till höger om \"Rekommenderad mängd:\".", "Rekommenderad mängd saknas.", JOptionPane.ERROR_MESSAGE);
                return false;
            } else{
                try{
                    Double.parseDouble(txfMaxAmount.getText());
                }catch (NumberFormatException nfe){
                    JOptionPane.showMessageDialog(null, "Rekommenderad mängd kan endast vara ett numeriskt värde.",
                            "Rekommenderad mängd", JOptionPane.ERROR_MESSAGE);
                    return false;
                }
            }
            if(cbxSupplier.getSelectedItem() == null){
                JOptionPane.showMessageDialog(null, "Leverantör saknas. Produkten måste ha en leverantör.",
                        "Leverantör", JOptionPane.ERROR_MESSAGE);
                return false;
            }
            return true;
        }

    }

    //<editor-fold desc = "Setters and getters">
    public StorageController getStorageController() {
        return storageController;
    }

    public void setStorageController(StorageController storageController) {
        this.storageController = storageController;
    }

    public JPanel getPnlNorth() {
        return pnlNorth;
    }

    public void setPnlNorth(JPanel pnlNorth) {
        this.pnlNorth = pnlNorth;
    }

    public JPanel getPnlCenter() {
        return pnlCenter;
    }

    public void setPnlCenter(JPanel pnlCenter) {
        this.pnlCenter = pnlCenter;
    }

    public JList<Ingredient> getProductList() {
        return productList;
    }

    public void setProductList(JList<Ingredient> productList) {
        this.productList = productList;
    }

    public JScrollPane getScrollPane() {
        return scrollPane;
    }

    public void setScrollPane(JScrollPane scrollPane) {
        this.scrollPane = scrollPane;
    }

    public JTextField getTxfFilter() {
        return txfFilter;
    }

    public void setTxfFilter(JTextField txfFilter) {
        this.txfFilter = txfFilter;
    }

    public JButton getBtnUpdateList() {
        return btnUpdateList;
    }

    public void setBtnUpdateList(JButton btnUpdateList) {
        this.btnUpdateList = btnUpdateList;
    }

    public JButton getBtnAddProduct() {
        return btnAddProduct;
    }

    public void setBtnAddProduct(JButton btnAddProduct) {
        this.btnAddProduct = btnAddProduct;
    }

    public JButton getBtnChangeProduct() {
        return btnChangeProduct;
    }

    public void setBtnChangeProduct(JButton btnChangeProduct) {
        this.btnChangeProduct = btnChangeProduct;
    }

    public JButton getBtnRemoveProduct() {
        return btnRemoveProduct;
    }

    public void setBtnRemoveProduct(JButton btnRemoveProduct) {
        this.btnRemoveProduct = btnRemoveProduct;
    }

    //</editor-fold>
}
