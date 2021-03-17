package View;

import javax.swing.*;
import javax.swing.border.TitledBorder;
import java.awt.*;
import java.awt.event.ActionEvent;

public class RecipePanel extends JPanel {
    private JPanel northPanel;
    private JPanel leftPanel;
    private JPanel centerPanel;

    private JComboBox<String> recipes;
    private JTextArea abc;
    private JTextArea recipeInstructions;

    private JButton addRecipe;
    private JButton removeRecipe;

    private String[] menu = {"Recept1", "Recept2", "Recept3", "Recept4"};

    public RecipePanel(){
        setLayout(new BorderLayout());
        setupPanels();
    }

    public void setupPanels(){
        northPanel = new JPanel();
        leftPanel = new JPanel();
        centerPanel = new JPanel();

        //North panel
        recipes = new JComboBox<>(menu);
        recipes.setPreferredSize(new Dimension(200, 25));
        northPanel.add(recipes);

        addRecipe = new JButton("Lägg till recept");
        addRecipe.addActionListener(this::actionPerformed);
        northPanel.add(addRecipe);

        removeRecipe = new JButton("Ta bort recept");
        removeRecipe.addActionListener(this::actionPerformed);
        northPanel.add(removeRecipe);

        add(northPanel, BorderLayout.NORTH);

        //Left panel
        leftPanel.setLayout(new BorderLayout());
        abc = new JTextArea();
        abc.setEditable(false);
        abc.setPreferredSize(new Dimension(200, 800));
        abc.setBorder(new TitledBorder("Innehåll"));
        abc.append("2 ägg \n");
        abc.append("2 banan \n");
        abc.append("2 vatten \n");
        abc.append("2 salt \n");
        leftPanel.add(abc);
        leftPanel.setPreferredSize(new Dimension(200, 800));

        add(leftPanel, BorderLayout.WEST);

        //Center panel
        centerPanel.setLayout(new BorderLayout());
        recipeInstructions = new JTextArea();
        recipeInstructions.setEditable(false);
        recipeInstructions.setBorder(new TitledBorder("Instruktioner"));
        recipeInstructions.append("Vispa ägg med jord \n");
        recipeInstructions.append("rör ner hackad banan \n");
        recipeInstructions.append("Häll i form, salta på toppen och backa på 0 grader i 80 minuter \n");
        centerPanel.add(recipeInstructions, BorderLayout.CENTER);

        add(centerPanel, BorderLayout.CENTER);
    }

    public void actionPerformed(ActionEvent e){

    }
}
