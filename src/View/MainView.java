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
        recipePanel = new RecipePanel();
        storagePanel = new StoragePanel(controller);
        orderPanel = new OrderPanel();
        supplierPanel = new SupplierPanel();
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

}