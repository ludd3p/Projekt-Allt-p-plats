package view;

import controller.Controller;

import javax.swing.*;
import java.awt.*;

/**
 * Main GUI component that holds each separate panel using JTabbedPane
 *
 * @Author Ludvig Wedin Pettersson
 * @Version 3.0
 */

public class MainView extends JFrame {
    private JTabbedPane tp;
    private StoragePanel storagePanel;
    private RecipePanel recipePanel;
    private OrderPanel orderPanel;
    private SupplierPanel supplierPanel;
    private HomePanel homePanel;


    /**
     * Constructor setting up the frame and its components.
     *
     * @param controller Reference to the main controller.
     */
    public MainView(Controller controller) {
        setTitle("Allt på plats");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setPreferredSize(new Dimension(1000, 600));
        setResizable(false);
        setBounds(400, 300, 800, 500);
        recipePanel = new RecipePanel(controller.getRecipeController());
        storagePanel = new StoragePanel(controller.getStorageController());
        supplierPanel = new SupplierPanel(controller.getSupplierController());
        homePanel = new HomePanel(controller.getHomeController());


        tp = new JTabbedPane();
        tp.add("Startsida", homePanel);
        tp.add("Recept", recipePanel);
        tp.add("Lager", storagePanel);
        tp.add("Ordrar", new OrderPanel(controller.getOrderController()));
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