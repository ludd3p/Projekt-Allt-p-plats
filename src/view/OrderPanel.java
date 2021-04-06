package view;

import controller.OrderController;
import model.order.Order;
import model.order.OrderItem;

import javax.swing.*;
import javax.swing.border.TitledBorder;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Panel used to keep track of order history and current orders.
 *
 * @Author Hazem Elkhalil
 */

public class OrderPanel extends JPanel {
    private JPanel leftPanel; // Vänstra panelen
    private JPanel rightPanel; // höger panelen

    private JList<Order> orderHistoryJList; // Order history jlist
    private List<Order> orderHistoryList; // Order history list

    private JList<OrderItem> currentOrderList; // Info om vald order
    private JButton showOrder; // Visa info om vald order
    private JButton hasArrived; // Klicka när en order har kommit in
    private JButton remove; // Avbryt en order
    private OrderController controller;


    /**
     * Constructor to initiate the panel with an OrderController
     *
     * @param controller which controller to use
     */
    public OrderPanel(OrderController controller) {
        setLayout(new BorderLayout(5, 5));
        orderHistoryList = new ArrayList<>();
        this.controller = controller;
        controller.setPanel(this);
        setupPanels();
        controller.setup(this);
    }

    /**
     * Function to setup all the components
     */
    public void setupPanels() {
        leftPanel = new JPanel();
        rightPanel = new JPanel();

        leftPanel.setBorder(new TitledBorder("Order Historik"));
        orderHistoryJList = new JList<>();
        orderHistoryJList.setPreferredSize(new Dimension(500, 600));
        leftPanel.add(orderHistoryJList);


        rightPanel.setBorder(new TitledBorder("Info"));
        rightPanel.setPreferredSize(new Dimension(400, 600));
        rightPanel.setLayout(new BorderLayout(5, 0));

        currentOrderList = new JList<>();
        currentOrderList.setPreferredSize(new Dimension(500, 450));
        rightPanel.add(currentOrderList, BorderLayout.NORTH);

        JPanel controlPanel = new JPanel();
        controlPanel.setBorder(new TitledBorder("Control panel"));
        showOrder = new JButton("Show order");
        showOrder.addActionListener((e) -> {
            if (orderHistoryJList.getSelectedValue() == null) {
                JOptionPane.showConfirmDialog(null, "Du måste välja en order!", "ERROR", JOptionPane.DEFAULT_OPTION);
                return;
            }
            this.controller.orderPreview(orderHistoryJList.getSelectedValue());
        });
        hasArrived = new JButton("Has arrived");
        hasArrived.addActionListener((e -> {
            if (orderHistoryJList.getSelectedValue() == null) {
                JOptionPane.showConfirmDialog(null, "Du måste välja en order!", "ERROR", JOptionPane.DEFAULT_OPTION);
                return;
            }
            if (orderHistoryJList.getSelectedIndex() == 0) {
                controller.saveCurrentOrder();
            }
            this.controller.orderHasArrived(orderHistoryJList.getSelectedValue());
        }));
        remove = new JButton("Cancel order");
        remove.addActionListener((e -> {
            if (orderHistoryJList.getSelectedValue() == null) {
                JOptionPane.showConfirmDialog(null, "Du måste välja en order att tabort!", "ERROR", JOptionPane.DEFAULT_OPTION);
                return;
            }
            this.controller.cancelOrder(orderHistoryJList.getSelectedValue());
        }));
        controlPanel.add(showOrder);
        controlPanel.add(hasArrived);
        controlPanel.add(remove);
        rightPanel.add(controlPanel, BorderLayout.SOUTH);

        this.add(leftPanel, BorderLayout.WEST);
        this.add(rightPanel, BorderLayout.EAST);
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
    public JList<Order> getOrderHistoryJList() {
        return orderHistoryJList;
    }

    /**
     * @param orderHistoryJList replaces the current order history
     */
    public void setOrderHistoryJList(JList<Order> orderHistoryJList) {
        this.orderHistoryJList = orderHistoryJList;
    }

    /**
     * @return orderHistoryList returns the current order history as an arraylist
     */
    public List<Order> getOrderHistoryList() {
        return orderHistoryList;
    }

    /**
     * @param orderHistoryList replaces the arraylist
     */
    public void setOrderHistoryList(List<Order> orderHistoryList) {
        this.orderHistoryList = orderHistoryList;
    }

    /**
     * @return current order view list
     */
    public JList<OrderItem> getCurrentOrderList() {
        return currentOrderList;
    }

    /**
     * @param currentOrderList replace the current order view list
     */
    public void setCurrentOrderList(JList<OrderItem> currentOrderList) {
        this.currentOrderList = currentOrderList;
    }
}
