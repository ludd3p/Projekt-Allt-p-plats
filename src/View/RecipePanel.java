package View;

import Model.Recipe;

import javax.swing.*;
import javax.swing.border.EtchedBorder;
import javax.swing.border.TitledBorder;
import javax.swing.event.ChangeEvent;
import java.awt.*;
import java.awt.event.ActionEvent;

/**
 * Panel for managing recipes
 * @Author Ludvig Wedin Pettersson
 * @Version 1.0
 */

public class RecipePanel extends JPanel {
    private newRecipeWindow newRecipeWindow;

    private JPanel northPanel; //Topp panelen
    private JPanel leftPanel; // Vänstra panelen
    private JPanel centerPanel; // Center panelen

    private SpinnerModel spinnerModel; // Bestämmer hur spinner fungerar
    private JSpinner spinner; // För att välja antal
    private JComboBox<String> recipes; // För att välja recept
    private DefaultListModel<String> ingredientsModel;
    private DefaultListModel<String> instructionsModel;
    private JList<String> ingredientsList; // Visar ingredienser för recept. Byt namn.
    private JList<String> recipeInstructions; // Visar instruktioner för recept.

    private JButton done; // Knapp för när man tillagat recept.
    private JButton addRecipe; // Lägga till recept
    private JButton removeRecipe; // Ta bort ett recept
    private JButton modifyRecipe; // Ändra i ett recept

    private String[] menu = {"Recept1", "Recept2", "Recept3", "Recept4"}; //Bara test för meny

    /**
     * Constructor for the panel
     */
    public RecipePanel(){
        setLayout(new BorderLayout());
        setupPanels();
    }

    /**
     * Method that sets everything up inside the panel
     */
    public void setupPanels(){
        northPanel = new JPanel();
        leftPanel = new JPanel();
        centerPanel = new JPanel();

        //North panel
        northPanel.setBorder(new EtchedBorder(EtchedBorder.RAISED));

        done = new JButton("Klar");
        done.addActionListener(this::actionPerformed);
        northPanel.add(done);

        spinnerModel = new SpinnerNumberModel(1, 1, 20, 1);
        spinner = new JSpinner(spinnerModel);
        spinner.addChangeListener(this::stateChanged);
        northPanel.add(spinner);

        recipes = new JComboBox<>(menu);
        recipes.setPreferredSize(new Dimension(200, 25));
        recipes.addActionListener(this::actionPerformed);
        northPanel.add(recipes);

        addRecipe = new JButton("Lägg till nytt recept");
        addRecipe.addActionListener(this::actionPerformed);
        northPanel.add(addRecipe);

        removeRecipe = new JButton("Ta bort valt recept");
        removeRecipe.addActionListener(this::actionPerformed);
        northPanel.add(removeRecipe);

        modifyRecipe = new JButton("Ändra i recept");
        modifyRecipe.addActionListener(this::actionPerformed);
        northPanel.add(modifyRecipe);

        add(northPanel, BorderLayout.NORTH);

        //Left panel
        leftPanel.setLayout(new BorderLayout());
        ingredientsModel = new DefaultListModel<>();
        ingredientsList = new JList<>();
        ingredientsList.setPreferredSize(new Dimension(200, 800));
        ingredientsList.setBorder(new TitledBorder("Innehåll"));
        ingredientsList.setModel(ingredientsModel);
        ingredientsModel.addElement("ÄGGGGGGGGGGGGG");
        leftPanel.add(ingredientsList);
        leftPanel.setPreferredSize(new Dimension(200, 800));

        add(leftPanel, BorderLayout.WEST);

        //Center panel
        centerPanel.setLayout(new BorderLayout());
        instructionsModel = new DefaultListModel<>();
        recipeInstructions = new JList<>();
        recipeInstructions.setModel(instructionsModel);
        recipeInstructions.setBorder(new TitledBorder("Instruktioner"));
        instructionsModel.addElement("Vispa ägg med jord \n");
        instructionsModel.addElement("rör ner hackad banan \n");
        instructionsModel.addElement("Häll i form, salta på toppen och backa på 0 grader i 80 minuter \n");
        centerPanel.add(recipeInstructions, BorderLayout.CENTER);

        add(centerPanel, BorderLayout.CENTER);
    }


    /**
     * Listener for the buttons
     * @param e Source of method call
     */
    public void actionPerformed(ActionEvent e){
        if (e.getSource() == done){
            if (JOptionPane.showConfirmDialog(null, "Är du säker på att du tillaggat receptet? Lagersaldo kommer att dras av", "asd", JOptionPane.YES_NO_OPTION) == 0){
                // Recept tillagat och saldo ska dras av
            } else {
                JOptionPane.showMessageDialog(null, "Inga varor dras av", "Meddelande", JOptionPane.PLAIN_MESSAGE);
            }
        }

        if (e.getSource() == recipes){
            //Byt recept som visas
            System.out.println(menu[recipes.getSelectedIndex()]);
        }

        if (e.getSource() == addRecipe){
            if (newRecipeWindow != null) {
                newRecipeWindow.dispose();
            }
            newRecipeWindow = new newRecipeWindow();
        }

        if (e.getSource() == removeRecipe){
            if (JOptionPane.showConfirmDialog(null, "Är du säker på att du vill ta bort " + recipes.getItemAt(recipes.getSelectedIndex()) + "?", "Ta bort recept", JOptionPane.YES_NO_OPTION) == 0){
                //Ta bort valt recept
            } else {
                JOptionPane.showMessageDialog(null, "Receptet tas inte bort", "Meddelande", JOptionPane.PLAIN_MESSAGE);
            }
        }

        if (e.getSource() == modifyRecipe){
            if (JOptionPane.showConfirmDialog(null, "Vill du ändra i " + recipes.getItemAt(recipes.getSelectedIndex()) + "?", "Ändra recept", JOptionPane.YES_NO_OPTION) == 0){
                if (newRecipeWindow != null) {
                    newRecipeWindow.dispose();
                }
                newRecipeWindow = new newRecipeWindow();
            }
        }

    }

    /**
     * Listens to changed states of registered components
     * @param e Source of call
     */
    public void stateChanged(ChangeEvent e){
        if (e.getSource() == spinner){
            //Metod för att multiplicera recept
            System.out.println(spinner.getValue());
        }
    }

    private class newRecipeWindow extends JFrame{
        //West
        private JPanel westPanel;
        private JPanel westNorth;
        private JPanel westSouth;
        private JComboBox ingredientsMenu;
        private JButton addIngredient;
        private JButton removeIngredient;
        private JSpinner amount;
        private SpinnerModel amountModel;
        private JLabel unit;
        private DefaultListModel<String> ingredientsListModel;
        private JList<String> ingredientsList;

        //East
        private JPanel eastPanel;
        private JPanel eastNorth;
        private JPanel eastSouth;
        private JTextField instructionInput;
        private JButton addInstruction;
        private JButton removeInstruction;
        private JButton saveRecipe;
        private DefaultListModel<String> instructionListModel;
        private JList<String> instructionList;

        /**
         * Constructor used when creating new recipe
         */
        public newRecipeWindow(){
            //Frame settings
            setTitle("Nytt recept");
            setPreferredSize(new Dimension(1000, 600));
            setupNewRecipeFrame();

        }

        /**
         * Construcotr used when editing a recipe, receives a recicpe
         * @param recipe Received recipe
         */
        public newRecipeWindow(Recipe recipe){
            //Frame settings
            setTitle("Ändra recept");
            setPreferredSize(new Dimension(1000, 600));
            setupNewRecipeFrame();
        }

        public void setupNewRecipeFrame(){
            //West panel
            westPanel = new JPanel();
            westPanel.setLayout(new BorderLayout());
            westPanel.setPreferredSize(new Dimension(200, 400));

            westNorth = new JPanel();
            westNorth.setBorder(new TitledBorder(""));

            String[] ingredients = {"Ägg", "Vatten", "Salt"};
            ingredientsMenu = new JComboBox(ingredients);
            westNorth.add(ingredientsMenu);

            amountModel = new SpinnerNumberModel(0, 0, 10000, 0.1);
            amount = new JSpinner(amountModel);
            westNorth.add(amount);

            unit = new JLabel("G");
            westNorth.add(unit);



            ingredientsListModel = new DefaultListModel<>();
            ingredientsList = new JList<>(ingredientsListModel);
            ingredientsList.setBorder(new TitledBorder("Ingredienser"));
            westPanel.add(ingredientsList, BorderLayout.CENTER);

            westPanel.add(westNorth, BorderLayout.NORTH);

            westSouth = new JPanel();

            addIngredient = new JButton("Lägg till");
            westSouth.add(addIngredient);

            removeIngredient = new JButton("Ta bort");
            westSouth.add(removeIngredient);

            westPanel.add(westSouth, BorderLayout.SOUTH);

            //East panel
            eastPanel = new JPanel();
            eastPanel.setLayout(new BorderLayout());
            eastPanel.setPreferredSize(new Dimension(400, 400));

            eastNorth = new JPanel();
            eastNorth.setBorder(new TitledBorder(""));

            instructionInput = new JTextField("Lägg till beskrivning");
            instructionInput.setPreferredSize(new Dimension(300, 25));
            eastNorth.add(instructionInput);

            addInstruction = new JButton("Lägg till");
            eastNorth.add(addInstruction);

            removeInstruction = new JButton("Ta bort");
            eastNorth.add(removeInstruction);

            instructionListModel = new DefaultListModel<>();
            instructionList = new JList<>(instructionListModel);
            instructionList.setBorder(new TitledBorder("Instruktioner"));
            eastPanel.add(instructionList, BorderLayout.CENTER);

            eastPanel.add(eastNorth, BorderLayout.NORTH);

            eastSouth = new JPanel();
            saveRecipe = new JButton("Spara recept");
            eastSouth.add(saveRecipe);
            eastPanel.add(eastSouth, BorderLayout.SOUTH);

            add(westPanel, BorderLayout.WEST);
            add(eastPanel, BorderLayout.CENTER);
            pack();
            setVisible(true);
        }

    }

    // Kommentar
}
