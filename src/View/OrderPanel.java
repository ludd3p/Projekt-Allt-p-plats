package View;

import Model.Order;
import Model.OrderItem;

import javax.swing.*;
import javax.swing.border.TitledBorder;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class OrderPanel extends JPanel {
    private JPanel leftPanel; // Vänstra panelen
    private JPanel rightPanel; // höger panelen

    private JList<Order> orderJList; // Order history jlist
    private List<Order> orderList; // Order history list

    private JList<OrderItem> currentOrderList; // Info om vald order
    private JButton showOrder; // Visa info om vald order
    private JButton hasArrived; // Klicka när en order har kommit in
    private JButton remove; // Avbryt en order


    public OrderPanel() {
        setLayout(new BorderLayout(5, 5));
        orderList = new ArrayList<>();

        setupPanels();
    }

    public void setupPanels() {
        leftPanel = new JPanel();
        rightPanel = new JPanel();

        leftPanel.setBorder(new TitledBorder("Order history"));
        orderJList = new JList<>();
        orderJList.setPreferredSize(new Dimension(500, 600));
        leftPanel.add(orderJList);


        rightPanel.setBorder(new TitledBorder("Info"));
        rightPanel.setPreferredSize(new Dimension(400, 600));
        rightPanel.setLayout(new BorderLayout(5, 0));

        currentOrderList = new JList<>();
        currentOrderList.setPreferredSize(new Dimension(500, 450));
        rightPanel.add(currentOrderList, BorderLayout.NORTH);

        JPanel controlPanel = new JPanel();
        controlPanel.setBorder(new TitledBorder("Control panel"));
        showOrder = new JButton("Show order");
        hasArrived = new JButton("Has arrived");
        remove = new JButton("Cancel order");
        controlPanel.add(showOrder);
        controlPanel.add(hasArrived);
        controlPanel.add(remove);
        rightPanel.add(controlPanel, BorderLayout.SOUTH);

        this.add(leftPanel, BorderLayout.WEST);
        this.add(rightPanel, BorderLayout.EAST);


    }

    public void addOrderToList(Order order) {
        this.orderList.add(order);
        Order[] val = new Order[orderList.size()];
        orderList.toArray(val);
        this.orderJList.setListData(val);
    }
}
