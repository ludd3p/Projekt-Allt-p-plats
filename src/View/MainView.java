package View;

import javax.swing.*;
import java.awt.*;

public class MainView extends JFrame {
    private JTabbedPane tp;
    private StoragePanel storagePanel;
    private RecipePanel recipePanel;
    private OrderPanel orderPanel;

    public MainView() {
        setTitle("Allt på plats");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setPreferredSize(new Dimension(1000, 600));
        setBounds(400, 300, 500, 300);
        recipePanel = new RecipePanel();
        storagePanel = new StoragePanel();
        orderPanel = new OrderPanel();


        tp = new JTabbedPane();
        tp.add("Statsida", new JButton("Hej"));
        tp.add("Recept", recipePanel);
        tp.add("Lager", storagePanel);
        tp.add("Order history", orderPanel);
        tp.add("Leverantörer", null);

        add(tp);
        pack();
        setVisible(true);
    }

    public static void main(String[] args) {
        new MainView();
    }
}
