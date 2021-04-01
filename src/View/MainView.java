package View;

import Controller.Controller;

import javax.swing.*;
import java.awt.*;

public class MainView extends JFrame {
    private JTabbedPane tp;
    private StoragePanel storagePanel;
    private RecipePanel recipePanel;
    private OrderPanel orderPanel;
    private SupplierPanel supplierPanel;
    private HomePanel homePanel;

    public MainView(Controller controller) {
        setTitle("Allt på plats");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setPreferredSize(new Dimension(1000, 600));
        setBounds(400, 300, 500, 300);
        recipePanel = new RecipePanel(controller);
        storagePanel = new StoragePanel(controller);
        orderPanel = new OrderPanel();
        supplierPanel = new SupplierPanel(controller);
        homePanel = new HomePanel();


        tp = new JTabbedPane();
        tp.add("Statsida", homePanel);
        tp.add("Recept", recipePanel);
        tp.add("Lager", storagePanel);
        tp.add("Order history", orderPanel);
        tp.add("Leverantörer", supplierPanel);

        add(tp);
        pack();
        setVisible(true);
    }

    public JTabbedPane getTp() {
        return tp;
    }

    public void setTp(JTabbedPane tp) {
        this.tp = tp;
    }

    public StoragePanel getStoragePanel() {
        return storagePanel;
    }

    public void setStoragePanel(StoragePanel storagePanel) {
        this.storagePanel = storagePanel;
    }

    public RecipePanel getRecipePanel() {
        return recipePanel;
    }

    public void setRecipePanel(RecipePanel recipePanel) {
        this.recipePanel = recipePanel;
    }

    public OrderPanel getOrderPanel() {
        return orderPanel;
    }

    public void setOrderPanel(OrderPanel orderPanel) {
        this.orderPanel = orderPanel;
    }

    public SupplierPanel getSupplierPanel() {
        return supplierPanel;
    }

    public void setSupplierPanel(SupplierPanel supplierPanel) {
        this.supplierPanel = supplierPanel;
    }

    public HomePanel getHomePanel() {
        return homePanel;
    }

    public void setHomePanel(HomePanel homePanel) {
        this.homePanel = homePanel;
    }
}