package view;

import controller.StorageController;
import controller.SupplierController;

import javax.swing.*;
import javax.swing.border.EtchedBorder;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;

/**
 * Panel used to keep track of products.
 *
 * @Author Jonathan Engström
 * @Version 1.0
 */

public class StoragePanel extends JPanel {
    private StorageController storageController;

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
    public StoragePanel(StorageController storageController) {
        this.storageController = storageController;
        setupMainPanel();
        //storageController.getIngredientsFromDatabase();
    }

    /**
     * Updates/Adds products on/to the list.
     *
     * @param values
     */
    public void updateList(ArrayList<String> values) {
        this.values = values;
        model.clear();
        model.addAll(this.values);
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
        //pnlNorth.setLayout(new BorderLayout());
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
        model = new DefaultListModel();
        productList = new JList(model);
        productList.setCellRenderer(new CellRenderer());
        scrollPane = new JScrollPane(productList);
        pnlCenter.add(scrollPane);
        add(pnlCenter, BorderLayout.CENTER);
    }

    /**
     * Method to filter productList based on user input in txfFilter.
     *
     * @param model
     * @param filter text in txfFilter
     */
    private void filterModel(DefaultListModel<String> model, String filter) {
        model.clear();
        for (String s : values) {
            if (s.substring(s.indexOf("Produkt: ") + "Produkt: ".length(), s.indexOf("<br>Nuvarande mängd:") - 1).toLowerCase().startsWith(filter.toLowerCase())) {
                model.addElement(s);
            }
        }
    }

    /**
     * Configures and adds components to pnlNorth.
     */
    private void addComponentsNorthPanel() {
        ActionListener actionListener = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (e.getSource() == btnAddProduct) {
                    JFrame productWindow = new ProductWindow();
                } else if (e.getSource() == btnChangeProduct) {
                    if (productList.getSelectedValue() != null) {
                        String selected = (String) productList.getSelectedValue();
                        JFrame productWindow = new ProductWindow(
                                selected.substring(selected.indexOf("Produkt: ") + "Produkt: ".length(), selected.indexOf("<br>Kostnad") - 1),
                                selected.substring(selected.indexOf("Kostnad: ") + "Kostnad: ".length(), selected.lastIndexOf(" ", selected.indexOf("<br>Leverantör:") - 2)),
                                selected.substring(selected.indexOf("<br>Nuvarande mängd: ") + "<br>Nuvarande mängd: ".length(), selected.lastIndexOf(" ", selected.indexOf("Minsta mängd:") - 2)),
                                selected.substring(selected.indexOf("Minsta mängd: ") + "Minsta mängd: ".length(), selected.lastIndexOf(" ", selected.indexOf("Rekommenderad mängd: ") - 2)),
                                selected.substring(selected.indexOf("Rekommenderad mängd: ") + "Rekommenderad mängd: ".length(), selected.lastIndexOf(" ", selected.indexOf("<!---") - 2)),
                                selected.substring(selected.indexOf("sek/") + "sek/".length(), selected.indexOf("<br>Nuvarande") - 1),
                                selected.substring(selected.indexOf("<br>Leverantör: ") + "<br>Leverantör: ".length(), selected.indexOf("<br>Nuvarande mängd:") - 1));
                    } else {
                        JOptionPane.showMessageDialog(null, "Välj en produkt att ändra.");
                    }
                } else if (e.getSource() == btnRemoveProduct) {
                    int answer = -1;

                    if (productList.getSelectedValue() != null) {
                        answer = JOptionPane.showConfirmDialog(null, "Är du säker?", "Ta bort produkt", JOptionPane.YES_NO_OPTION);
                    } else {
                        JOptionPane.showMessageDialog(null, "Välj en produkt att ta bort först.");
                    }

                    if (answer == 0) {
                        String selected = (String) productList.getSelectedValue();
                        String key = selected.substring(selected.indexOf("<!--") + "<!--".length(), selected.indexOf("-->"));
                        storageController.removeIngredientFromDatabase(key);
                        storageController.getIngredientsFromDatabase();
                    }
                } else if (e.getSource() == btnUpdateList) {
                    storageController.getIngredientsFromDatabase();
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
                if (txfFilter.getText().equals("")) {
                    txfFilter.setText("Sök");
                }
            }
        };

        txfFilter = new JTextField("Sök");
        txfFilter.setToolTipText("Skriv in namnet på den produkt du söker efter.");
        txfFilter.setPreferredSize(new Dimension(100, 27));
        txfFilter.addFocusListener(focusListener);
        txfFilter.addKeyListener(keyListener);
        pnlNorth.add(txfFilter);

        btnAddProduct = new JButton("Lägg till");
        btnAddProduct.setToolTipText("Klicka för att lägga till  en ny produkt.");
        btnAddProduct.addActionListener(actionListener);

        btnChangeProduct = new JButton("Ändra");
        btnChangeProduct.setToolTipText("Klicka för att ändra den valda produkten.");
        btnChangeProduct.addActionListener(actionListener);

        btnRemoveProduct = new JButton("Ta bort");
        btnRemoveProduct.setToolTipText("Klicka för att ta bort den valda produkten.");
        btnRemoveProduct.addActionListener(actionListener);

        btnUpdateList = new JButton("Uppdatera");
        btnUpdateList.setToolTipText("Klicka för att uppdatera listan av produkter.");
        btnUpdateList.addActionListener(actionListener);

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


            if (index % 2 == 0) {
                setBackground(Color.WHITE);
                setBorder(new LineBorder(new Color(0, 0, 0, 0)));
            } else {
                setBackground(new Color(220, 220, 220));
                setBorder(new LineBorder(new Color(0, 0, 0, 0)));
            }

            if (isSelected) {
                setBackground(productList.getSelectionBackground());
                setForeground(productList.getForeground());
                setBorder(new LineBorder(Color.BLUE));
            } else {
                setBorder(new LineBorder(new Color(0, 0, 0, 0)));
            }

            return this;
        }
    }

    /**
     * Inner-class used to create a window for inserting and changing products.
     */
    private class ProductWindow extends JFrame {
        private JPanel pnlProductWindowMainPanel;
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
        private JComboBox cbxUnit;

        private JLabel lblSupplier;
        private JComboBox cbxSupplier;

        private JButton btnOk;
        private JButton btnCancel;

        //true = add, false = change
        private boolean addOrChange;

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
         * @param productName
         * @param minAmount
         * @param maxAmount
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
        private void setupProductWindowCenterPanel() {
            pnlProductWindowCenter = new JPanel();
            pnlProductWindowCenter.setBorder(BorderFactory.createEtchedBorder(0));
            pnlProductWindowCenter.setLayout(new GridLayout(7, 2, 10, 1));

            lblProductName = new JLabel(" Produkt: ");
            pnlProductWindowCenter.add(lblProductName);
            txfProductName = new JTextField();
            txfProductName.setPreferredSize(new Dimension(100, txfProductName.getHeight()));
            txfProductName.setToolTipText("Skriv in namnet på den produkt du vill lägga till.");
            pnlProductWindowCenter.add(txfProductName);

            lblCost = new JLabel(" Kostnad: ");
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
            txfMinAmount.setToolTipText("Skriv in den mängd av produkten du vill ha i lagret.");
            pnlProductWindowCenter.add(txfMaxAmount);

            lblUnit = new JLabel(" Enhet:");
            pnlProductWindowCenter.add(lblUnit);
            cbxUnit = new JComboBox(storageController.getUnitsPrefixArray());
            cbxUnit.setToolTipText("Välj enhet för produkten.");
            pnlProductWindowCenter.add(cbxUnit);

            lblSupplier = new JLabel(" Leverantör:");
            pnlProductWindowCenter.add(lblSupplier);
            cbxSupplier = new JComboBox(storageController.getSupplierNames());
            cbxUnit.setToolTipText("Välj leverantör för produkten.");
            pnlProductWindowCenter.add(cbxSupplier);

            pnlProductWindowMainPanel.add(pnlProductWindowCenter, BorderLayout.CENTER);
        }

        /**
         * Configures and adds components to pnlProductWindowSouth and adds it to the main panel.
         */
        private void setupProductWindowSouthPanel() {
            pnlProductWindowSouth = new JPanel();
            pnlProductWindowSouth.setBorder(new EtchedBorder(EtchedBorder.RAISED));
            pnlProductWindowSouth.setLayout(new GridLayout(1, 2, 1, 1));

            ActionListener listener = new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    if (e.getSource() == btnOk) {
                        try {
                            boolean proceed = true;
                            if (    txfProductName.getText().toLowerCase().contains("Produkt:".toLowerCase()) ||
                                    txfProductName.getText().toLowerCase().contains("Kostnad:".toLowerCase()) ||
                                    txfProductName.getText().toLowerCase().contains("Leverantör:".toLowerCase())||
                                    txfProductName.getText().toLowerCase().contains("Nuvarande mängd:".toLowerCase()) ||
                                    txfProductName.getText().toLowerCase().contains("Minsta mängd:".toLowerCase()) ||
                                    txfProductName.getText().toLowerCase().contains("Rekommenderad mängd:".toLowerCase()) ||
                                    txfProductName.getText().toLowerCase().contains("<!---")) {
                                JOptionPane.showMessageDialog(null, "Error: Otillåtet produktnamn.", "Felaktig inmatning", JOptionPane.PLAIN_MESSAGE);
                                proceed = false;
                            }
                            if (addOrChange && proceed) {
                                for (String value : values) {
                                    if (value.substring(value.indexOf("Produkt: ") + "Produkt: ".length(), value.indexOf("<br>Kostnad") - 1).toLowerCase().equals(txfProductName.getText().toLowerCase())) {
                                        JOptionPane.showMessageDialog(null, "Denna produkt finns redan.", "Produkten finns redan", JOptionPane.PLAIN_MESSAGE);
                                        proceed = false;
                                    }
                                }
                            }
                            if (proceed) {
                                if (addOrChange) {
                                    storageController.addIngredientToDatabase(
                                            txfProductName.getText(),
                                            Double.parseDouble(txfCost.getText()),
                                            Double.parseDouble(txfCurrentAmount.getText()),
                                            Double.parseDouble(txfMinAmount.getText()),
                                            Double.parseDouble(txfMaxAmount.getText()),
                                            (String) cbxUnit.getSelectedItem(),
                                            (String) cbxSupplier.getSelectedItem());
                                } else if(!addOrChange){
                                    String selected = (String) productList.getSelectedValue();
                                    String key = selected.substring(selected.indexOf("<!--") + "<!--".length(), selected.indexOf("-->"));
                                    storageController.updateIngredient(
                                            key,
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
                        } catch (NumberFormatException nfe) {
                            JOptionPane.showMessageDialog(null, "Error: Felaktig inmatning.");
                        }
                    } else if (e.getSource() == btnCancel) {
                        dispose();
                    }
                }
            };

            btnOk = new JButton();
            btnOk.addActionListener(listener);
            if(addOrChange){
                btnOk.setText("Lägg till");
                btnOk.setToolTipText("Lägg till produkten i lagret.");
            }
            else if(!addOrChange){
                btnOk.setText("Ändra");
                btnOk.setToolTipText("Slutför ändringarna för den valda produkten.");
            }
            pnlProductWindowSouth.add(btnOk);

            btnCancel = new JButton("Avbryt");
            if(addOrChange){
                btnCancel.setToolTipText("Avbryt tilläget av produkten i lagret.");
            }
            else if(!addOrChange){
                btnCancel.setToolTipText("Avbryt ändringarna för den valda produkten..");
            }
            btnCancel.addActionListener(listener);
            pnlProductWindowSouth.add(btnCancel);

            pnlProductWindowMainPanel.add(pnlProductWindowSouth, BorderLayout.SOUTH);
        }

    }
}
