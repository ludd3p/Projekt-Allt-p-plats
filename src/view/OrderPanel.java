package view;

import controller.OrderController;
import model.ingredient.Ingredient;
import model.order.OrderItem;
import model.order.SupplierOrder;

import javax.swing.*;
import javax.swing.border.TitledBorder;
import java.awt.*;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.util.List;

/**
 * Panel used to keep track of order history and current orders.
 *
 * @Author Hazem Elkhalil
 * @Version 3.0
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

    private JComboBox<String> ingredientToAdd;
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
        this.supplierList = controller.getOrderHistoryList();
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
        leftPanel.setBorder(new TitledBorder("Ordrar"));
        setUpLeftPanel();

        centerPanel = new JPanel(null);
        centerPanel.setBackground(Color.white);
        centerPanel.setBounds(300, 0, 400, 600);
        centerPanel.setBorder(new TitledBorder(""));
        setUpCenterPanel();

        rightPanel = new JPanel();
        rightPanel.setBackground(Color.white);
        rightPanel.setBorder(new TitledBorder("Order Information"));
        rightPanel.setToolTipText("Order information");
        rightPanel.setBounds(700, 0, 300, 600);
        rightPanel.setLayout(new BorderLayout(5, 0));
        setUpRightPanel();

    }

    /**
     * Function to setup left side of the panel
     */
    public void setUpLeftPanel() {
        supplierJList = new JList<>(supplierList.toArray(new SupplierOrder[0]));
        JScrollPane jScrollPane = new JScrollPane(supplierJList);
        jScrollPane.setPreferredSize(new Dimension(280, 500));
        jScrollPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        jScrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED);
        jScrollPane.setBorder(null);
        supplierJList.addListSelectionListener(l -> {
            controller.previewSupplierOrder(supplierJList.getSelectedValue());

        });
        leftPanel.add(jScrollPane);

        add(leftPanel);
    }

    /**
     * Function to setup center side of the panel
     */
    public void setUpCenterPanel() {
        JPanel orderControlPanel = new JPanel(null);
        orderControlPanel.setBounds(25, 30, 350, 205);

        orderControlPanel.setBorder(new TitledBorder("Order kontrollpanel"));
        orderControlPanel.setToolTipText("Klicka en order för att förhandsgranska den!");
        orderControlPanel.setBackground(Color.white);

//        showOrder = new JButton("Visa order");
//        showOrder.setToolTipText("Visar varor i ordern");
//        showOrder.setBounds(25, 30, 300, 65);
//        showOrder.addActionListener(l -> {
//            if (supplierJList.getSelectedValue() == null) {
//                JOptionPane.showConfirmDialog(null, "Vänligen välj en order först", "ERROR", JOptionPane.OK_CANCEL_OPTION);
//                return;
//            }
//            controller.previewSupplierOrder(supplierJList.getSelectedValue());
//        });

        hasArrived = new JButton("Har kommit");
        hasArrived.setToolTipText("Markera en order som kommit");
        hasArrived.setBounds(25, 30, 300, 65);
        hasArrived.addActionListener(a -> {
            SupplierOrder supplierOrder = getSupplierJList().getSelectedValue();
            if (supplierOrder == null) {
                JOptionPane.showConfirmDialog(null, "Vänligen välj en order först", "ERROR", JOptionPane.OK_CANCEL_OPTION);
                return;
            }

            controller.orderHasArrived(supplierOrder);
        });

        remove = new JButton("Ta bort");
        remove.setToolTipText("Rensar en order helt.");
        remove.setBounds(25, 110, 300, 65);
        remove.addActionListener(l -> {
            if (controller.getCurrentSelectedSupplierOrder() == null) {
                JOptionPane.showConfirmDialog(null, "Vänligen välj en order först", "ERROR", JOptionPane.OK_CANCEL_OPTION);
                return;
            }
            controller.removeOrder();
        });

        //orderControlPanel.add(showOrder);
        orderControlPanel.add(hasArrived);
        orderControlPanel.add(remove);
        centerPanel.add(orderControlPanel);

        JPanel addItemToOrderPanel = new JPanel(null);
        addItemToOrderPanel.setBounds(25, 300, 350, 300);
        addItemToOrderPanel.setBorder(new TitledBorder("Ingredienser kontrollpanel"));
        addItemToOrderPanel.setBackground(null);

        JLabel ingredientLabel = new JLabel("Ingredienser");
        ingredientLabel.setBounds(10, 20, 250, 20);

        ingredientToAdd = new JComboBox<>(ingredientList());
        ingredientToAdd.setBounds(10, 40, 250, 30);
        ingredientToAdd.addFocusListener(new FocusListener() {
            @Override
            public void focusGained(FocusEvent e) {
                ingredientToAdd.setModel(new DefaultComboBoxModel<>(ingredientList()));
            }

            @Override
            public void focusLost(FocusEvent e) {
            }
        });
        ingredientToAdd.setBackground(null);

        JLabel quantityLabel = new JLabel("Kvantitet");
        quantityLabel.setBounds(290, 20, 250, 20);
        quantityLabel.setToolTipText("Mängden av ingridenten du vill lägga till");
        quantitySelector = new JSpinner();
        quantitySelector.setBounds(290, 40, 50, 30);
        SpinnerNumberModel spinnerNumberModel = new SpinnerNumberModel();
        spinnerNumberModel.setStepSize(1);
        spinnerNumberModel.setValue(1);
        spinnerNumberModel.setMinimum(1);
        quantitySelector.setModel(spinnerNumberModel);

        addOrderItem = new JButton("Lägg till ingredient");
        addOrderItem.setToolTipText("Lägg till ingredient manuellt till en order");
        addOrderItem.setBounds(10, 160, 150, 50);
        addOrderItem.addActionListener(e -> {
            if (controller.getCurrentSelectedSupplierOrder() == null) {
                JOptionPane.showConfirmDialog(null, "Vänligen välj en order först", "ERROR", JOptionPane.OK_CANCEL_OPTION);
                return;
            }
            Ingredient ingredient = getIngredientFromString((String) ingredientToAdd.getSelectedItem());
            int amount = (int) quantitySelector.getValue();
            OrderItem orderItem = new OrderItem(ingredient, amount);
            controller.addOrderItemToSelectedOrder(orderItem);
        });


        removeOrderItem = new JButton("Ta bort ingredient");
        removeOrderItem.setBounds(190, 160, 150, 50);
        addOrderItem.setToolTipText("Tar bort ingredient manuellt från en order");

        removeOrderItem.addActionListener(p -> {
            if (controller.getCurrentSelectedSupplierOrder() == null || getCurrentSupplier().getSelectedValue() == null) {
                JOptionPane.showConfirmDialog(null, "Vänligen välj en ingredient först", "ERROR", JOptionPane.OK_CANCEL_OPTION);
                return;
            }
            controller.removeOrderItemFromCurrentOrder(getCurrentSupplier().getSelectedValue());
        });

        addItemToOrderPanel.add(ingredientLabel);
        addItemToOrderPanel.add(quantityLabel);
        addItemToOrderPanel.add(ingredientToAdd);
        addItemToOrderPanel.add(quantitySelector);
        addItemToOrderPanel.add(addOrderItem);
        addItemToOrderPanel.add(removeOrderItem);

        centerPanel.add(addItemToOrderPanel);
        add(centerPanel);
    }

    /**
     * Function to setup right side of the panel
     */
    public void setUpRightPanel() {
        currentSupplier = new JList<>();
        JScrollPane jScrollPane = new JScrollPane(currentSupplier);
        jScrollPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        jScrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED);
        jScrollPane.setBorder(null);
        rightPanel.add(jScrollPane);

        add(rightPanel);
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

    public JPanel getLeftPanel() {
        return leftPanel;
    }


    public void setLeftPanel(JPanel leftPanel) {
        leftPanel = leftPanel;
    }

    public JPanel getRightPanel() {
        return rightPanel;
    }


    public void setRightPanel(JPanel rightPanel) {
        rightPanel = rightPanel;
    }


    public JList<SupplierOrder> getSupplierJList() {
        return supplierJList;
    }


    public void setSupplierJList(JList<SupplierOrder> supplierJList) {
        supplierJList = supplierJList;
    }

    public JPanel getCenterPanel() {
        return centerPanel;
    }

    public void setCenterPanel(JPanel centerPanel) {
        this.centerPanel = centerPanel;
    }

    public List<SupplierOrder> getSupplierList() {
        return supplierList;
    }

    public void setSupplierList(List<SupplierOrder> supplierList) {
        this.supplierList = supplierList;
    }

    public JButton getShowOrder() {
        return showOrder;
    }

    public void setShowOrder(JButton showOrder) {
        this.showOrder = showOrder;
    }

    public JButton getHasArrived() {
        return hasArrived;
    }

    public void setHasArrived(JButton hasArrived) {
        this.hasArrived = hasArrived;
    }

    public JButton getRemove() {
        return remove;
    }

    public void setRemove(JButton remove) {
        this.remove = remove;
    }

    public JComboBox<String> getIngredientToAdd() {
        return ingredientToAdd;
    }

    public void setIngredientToAdd(JComboBox<String> ingredientToAdd) {
        this.ingredientToAdd = ingredientToAdd;
    }

    public JSpinner getQuantitySelector() {
        return quantitySelector;
    }

    public void setQuantitySelector(JSpinner quantitySelector) {
        this.quantitySelector = quantitySelector;
    }

    public JButton getAddOrderItem() {
        return addOrderItem;
    }

    public void setAddOrderItem(JButton addOrderItem) {
        this.addOrderItem = addOrderItem;
    }

    public JButton getRemoveOrderItem() {
        return removeOrderItem;
    }

    public void setRemoveOrderItem(JButton removeOrderItem) {
        this.removeOrderItem = removeOrderItem;
    }

    public OrderController getController() {
        return controller;
    }

    public void setController(OrderController controller) {
        this.controller = controller;
    }


    public void setOrderHistoryList(List<SupplierOrder> supplierOrderHistoryList) {
        supplierList = supplierOrderHistoryList;
    }


    public JList<OrderItem> getCurrentSupplier() {
        return currentSupplier;
    }

    public void setCurrentSupplier(JList<OrderItem> currentSupplier) {
        currentSupplier = currentSupplier;
    }
}
