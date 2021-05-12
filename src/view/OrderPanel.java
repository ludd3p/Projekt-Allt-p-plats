package view;

import controller.OrderController;
import model.ingredient.Ingredient;
import model.order.OrderItem;
import model.order.SupplierOrder;

import javax.swing.*;
import javax.swing.border.TitledBorder;
import java.awt.*;
import java.util.List;

/**
 * Panel used to keep track of order history and current orders.
 *
 * @Author Hazem Elkhalil
 */

public class OrderPanel extends JPanel {
    private JPanel leftPanel; // Vänstra panelen
    private JPanel rightPanel; // höger panelen
    private JPanel centerPanel;

    private JList<SupplierOrder> supplierJList; // Order history jlist
    private List<SupplierOrder> supplierList; // Order history list

    private JList<OrderItem> currentSupplier; // Info om vald supplier

    private JButton showOrder; // Visa info om vald order
    private JButton hasArrived; // Klicka när en order har kommit in
    private JButton remove; // Avbryt en order

    private JComboBox ingredientToAdd;
    private JSpinner quantitySelector;
    private JButton addOrderItem;
    private JButton removeOrderItem;

    private OrderController controller;

    /**
     * Constructor to initiate the panel with an OrderController
     *
     * @param controller which controller to use
     */
    public OrderPanel(OrderController controller) {
        this.controller = controller;
        setLayout(null);
        supplierList = controller.getOrderHistoryList();
        controller.setup(this);
        setupPanels();
    }

    /**
     * Function to setup all the components
     */
    public void setupPanels() {
        leftPanel = new JPanel();
        leftPanel.setBackground(Color.white);
        leftPanel.setBounds(0, 0, 300, 600);
        leftPanel.setBorder(new TitledBorder("Order Historik"));
        setUpLeftPanel();

        centerPanel = new JPanel(null);
        centerPanel.setBackground(Color.white);
        centerPanel.setBounds(300, 0, 400, 600);
        centerPanel.setBorder(new TitledBorder("Control Panel"));
        setUpCenterPanel();

        rightPanel = new JPanel();
        rightPanel.setBackground(Color.white);
        rightPanel.setBorder(new TitledBorder("Order Info"));
        rightPanel.setBounds(700, 0, 300, 600);
        rightPanel.setLayout(new BorderLayout(5, 0));
        setUpRightPanel();

    }

    public void setUpLeftPanel() {
        supplierJList = new JList(supplierList.toArray(new SupplierOrder[0]));
        JScrollPane jScrollPane = new JScrollPane(supplierJList);
        jScrollPane.setPreferredSize(new Dimension(280, 500));
        jScrollPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        jScrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED);
        jScrollPane.setBorder(null);
        leftPanel.add(jScrollPane);

        this.add(leftPanel);
    }

    public void setUpCenterPanel() {
        JPanel orderControlPanel = new JPanel(null);
        orderControlPanel.setBounds(25, 30, 350, 270);

        orderControlPanel.setBorder(new TitledBorder("Order control"));
        orderControlPanel.setBackground(Color.white);

        this.showOrder = new JButton("Visa order");
        this.showOrder.setToolTipText("Visar varor i ordern");
        showOrder.setBounds(25, 30, 300, 65);
        showOrder.addActionListener(l -> {
            if (supplierJList.getSelectedValue() == null) {
                JOptionPane.showConfirmDialog(null, "FEL!! \n Var snäll och välj en order först!", "ERROR", JOptionPane.OK_CANCEL_OPTION);
                return;
            }
            System.out.println(supplierJList.getSelectedValue().getOrderItems().size());
            controller.previewSupplierOrder(supplierJList.getSelectedValue());
        });

        this.hasArrived = new JButton("Har kommit");
        this.hasArrived.setToolTipText("Markera en order som kommit");
        this.hasArrived.setBounds(25, 110, 300, 65);
        this.hasArrived.addActionListener(a -> {
            SupplierOrder supplierOrder = this.getSupplierJList().getSelectedValue();
            if (supplierOrder == null) {
                JOptionPane.showConfirmDialog(null, "FEL!! \n Var snäll och välj en order först!", "ERROR", JOptionPane.OK_CANCEL_OPTION);
                return;
            }

            controller.orderHasArrived(supplierOrder);
        });

        this.remove = new JButton("Ta bort");
        this.remove.setToolTipText("Removes an order from history");
        this.remove.setBounds(25, 190, 300, 65);
        this.remove.addActionListener(l -> {
            if (controller.getCurrentSelectedSupplierOrder() == null) {
                JOptionPane.showConfirmDialog(null, "FEL!! \n Var snäll och välj en order först!", "ERROR", JOptionPane.OK_CANCEL_OPTION);
                return;
            }
            controller.removeOrder();
        });

        orderControlPanel.add(showOrder);
        orderControlPanel.add(hasArrived);
        orderControlPanel.add(remove);
        centerPanel.add(orderControlPanel);

        JPanel addItemToOrderPanel = new JPanel(null);
        addItemToOrderPanel.setBounds(25, 300, 350, 300);
        addItemToOrderPanel.setBorder(new TitledBorder("Order Item Control"));
        addItemToOrderPanel.setBackground(null);

        JLabel ingredientLabel = new JLabel("Ingredient");
        ingredientLabel.setBounds(10, 20, 250, 20);

        ingredientToAdd = new JComboBox(ingredientList());
        ingredientToAdd.setBounds(10, 40, 250, 30);
        ingredientToAdd.setBackground(null);

        JLabel quantityLabel = new JLabel("Quantity");
        quantityLabel.setBounds(290, 20, 250, 20);
        quantitySelector = new JSpinner();
        quantitySelector.setBounds(290, 40, 50, 30);
        SpinnerNumberModel spinnerNumberModel = new SpinnerNumberModel();
        spinnerNumberModel.setStepSize(1);
        spinnerNumberModel.setValue(1);
        spinnerNumberModel.setMinimum(1);
        quantitySelector.setModel(spinnerNumberModel);

        addOrderItem = new JButton("Lägg till ingredient");
        addOrderItem.setBounds(10, 160, 150, 50);
        addOrderItem.addActionListener(e -> {
            if (controller.getCurrentSelectedSupplierOrder() == null) {
                JOptionPane.showConfirmDialog(null, "FEL!! \n Var snäll och välj en order först!", "ERROR", JOptionPane.OK_CANCEL_OPTION);
                return;
            }
            Ingredient ingredient = getIngredientFromString((String) ingredientToAdd.getSelectedItem());
            int amount = (int) quantitySelector.getValue();
            OrderItem orderItem = new OrderItem(ingredient, amount);
            controller.addOrderItemToSelectedOrder(orderItem);
        });


        removeOrderItem = new JButton("Ta bort ingredient");
        removeOrderItem.setBounds(190, 160, 150, 50);
        removeOrderItem.addActionListener(p -> {
            if (controller.getCurrentSelectedSupplierOrder() == null || this.getCurrentSupplier().getSelectedValue() == null) {
                JOptionPane.showConfirmDialog(null, "FEL!! \n Var snäll och välj en order först!", "ERROR", JOptionPane.OK_CANCEL_OPTION);
                return;
            }
            controller.removeOrderItemFromCurrentOrder(this.getCurrentSupplier().getSelectedValue());
        });

        addItemToOrderPanel.add(ingredientLabel);
        addItemToOrderPanel.add(quantityLabel);
        addItemToOrderPanel.add(ingredientToAdd);
        addItemToOrderPanel.add(quantitySelector);
        addItemToOrderPanel.add(addOrderItem);
        addItemToOrderPanel.add(removeOrderItem);

        centerPanel.add(addItemToOrderPanel);
        this.add(centerPanel);
    }

    public void setUpRightPanel() {
        currentSupplier = new JList<>();
        JScrollPane jScrollPane = new JScrollPane(currentSupplier);
        jScrollPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        jScrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED);
        jScrollPane.setBorder(null);
        rightPanel.add(jScrollPane);

        this.add(rightPanel);
    }

    public Ingredient getIngredientFromString(String name) {
        for (Ingredient ingredient : controller.getController().getStorageController().getAllIngredients()) {
            if (ingredient.getName().equals(name))
                return ingredient;
        }
        return null;
    }

    public String[] ingredientList() {
        String[] arr = new String[controller.getController().getStorageController().getAllIngredients().size()];
        int x = 0;
        for (Ingredient ingredient : controller.getController().getStorageController().getAllIngredients()) {
            arr[x] = ingredient.getName();
            x++;
        }
        return arr;
    }

    /**
     * @return panel on the west side (Order history)
     */
    public JPanel getLeftPanel() {
        return leftPanel;
    }

    /**
     * @param leftPanel replaces the left panel
     */
    public void setLeftPanel(JPanel leftPanel) {
        this.leftPanel = leftPanel;
    }

    /**
     * @return the east side of the panel (control panel)
     */
    public JPanel getRightPanel() {
        return rightPanel;
    }

    /**
     * @param rightPanel replaces the right panel
     */

    public void setRightPanel(JPanel rightPanel) {
        this.rightPanel = rightPanel;
    }

    /**
     * @return returns the current orderhistory as a JList
     */
    public JList<SupplierOrder> getSupplierJList() {
        return supplierJList;
    }

    /**
     * @param supplierJList replaces the current order history
     */
    public void setSupplierJList(JList<SupplierOrder> supplierJList) {
        this.supplierJList = supplierJList;
    }

    /**
     * @return orderHistoryList returns the current order history as an arraylist
     */
    public List<SupplierOrder> getOrderHistoryList() {
        return supplierList;
    }

    /**
     * @param supplierOrderHistoryList replaces the arraylist
     */
    public void setOrderHistoryList(List<SupplierOrder> supplierOrderHistoryList) {
        this.supplierList = supplierOrderHistoryList;
    }

    /**
     * @return current order view list
     */
    public JList<OrderItem> getCurrentSupplier() {
        return currentSupplier;
    }

    /**
     * @param currentSupplier replace the current order view list
     */
    public void setCurrentSupplier(JList<OrderItem> currentSupplier) {
        this.currentSupplier = currentSupplier;
    }
}
