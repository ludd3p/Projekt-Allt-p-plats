package View;

import Model.Ingredient;
import Model.Order;
import Model.OrderItem;
import Model.Unit;

import javax.swing.*;
import javax.swing.border.TitledBorder;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class OrderPanel extends JPanel {
    private JPanel leftPanel; // Vänstra panelen
    private JPanel rightPanel; // höger panelen

    private JList<Order> orderHistoryJList; // Order history jlist
    private List<Order> orderHistoryList; // Order history list

    private JList<OrderItem> currentOrderList; // Info om vald order
    private JButton showOrder; // Visa info om vald order
    private JButton hasArrived; // Klicka när en order har kommit in
    private JButton remove; // Avbryt en order


    public OrderPanel() {
        setLayout(new BorderLayout(5, 5));
        orderHistoryList = new ArrayList<>();

        setupPanels();
    }

    public void setupPanels() {
        leftPanel = new JPanel();
        rightPanel = new JPanel();

        leftPanel.setBorder(new TitledBorder("Order history"));
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
            orderPreview(orderHistoryJList.getSelectedValue());
        });
        hasArrived = new JButton("Has arrived");
        hasArrived.addActionListener((e -> {
            if (orderHistoryJList.getSelectedValue() == null) {
                JOptionPane.showConfirmDialog(null, "Du måste välja en order!", "ERROR", JOptionPane.DEFAULT_OPTION);
                return;
            }
            orderHasArrived(orderHistoryJList.getSelectedValue());
        }));
        remove = new JButton("Cancel order");
        remove.addActionListener((e -> {
            if (orderHistoryJList.getSelectedValue() == null) {
                JOptionPane.showConfirmDialog(null, "Du måste välja en order att tabort!", "ERROR", JOptionPane.DEFAULT_OPTION);
                return;
            }
            cancelOrder(orderHistoryJList.getSelectedValue());
        }));
        controlPanel.add(showOrder);
        controlPanel.add(hasArrived);
        controlPanel.add(remove);
        rightPanel.add(controlPanel, BorderLayout.SOUTH);

        this.add(leftPanel, BorderLayout.WEST);
        this.add(rightPanel, BorderLayout.EAST);


        Order order = new Order();
        order.getOrderItems().add(new OrderItem(new Ingredient("test", Unit.AMOUNT), 10));
        order.getOrderItems().add(new OrderItem(new Ingredient("test", Unit.AMOUNT), 10));
        order.getOrderItems().add(new OrderItem(new Ingredient("test", Unit.AMOUNT), 10));
        addOrderToHistory(order);
        addOrderToHistory(order);
        addOrderToHistory(order);
        addOrderToHistory(order);

    }

    public void addOrderToHistory(Order order) {
        this.orderHistoryList.add(order);
        Order[] val = new Order[orderHistoryList.size()];
        orderHistoryList.toArray(val);
        this.orderHistoryJList.setListData(val);
    }

    public void orderPreview(Order order) {
        if (order == null)
            order = new Order();
        OrderItem[] items = new OrderItem[order.getOrderItems().size()];
        order.getOrderItems().toArray(items);
        currentOrderList.setListData(items);
    }

    public void orderHasArrived(Order order) {
        //TODO Fyll på lagret
        this.orderHistoryList.remove(order);
        Order[] val = new Order[orderHistoryList.size()];
        orderHistoryList.toArray(val);
        this.orderHistoryJList.setListData(val);
        orderPreview(null);
    }

    public void cancelOrder(Order order) {
        orderPreview(null);
        this.orderHistoryList.remove(order);
        Order[] val = new Order[orderHistoryList.size()];
        orderHistoryList.toArray(val);
        this.orderHistoryJList.setListData(val);
    }
}
