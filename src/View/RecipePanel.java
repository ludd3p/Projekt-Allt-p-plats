package View;

import Controller.Controller;
import Model.Recipe;

import javax.swing.*;
import javax.swing.border.EtchedBorder;
import javax.swing.border.TitledBorder;
import javax.swing.event.ChangeEvent;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.util.ArrayList;

/**
 * Panel for managing recipes
 * @Author Ludvig Wedin Pettersson
 * @Version 1.0
 */

public class RecipePanel extends JPanel {
    private Controller controller;
    private NewRecipeWindow newRecipeWindow;

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
    public RecipePanel(Controller controller){
        this.controller = controller;
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
            newRecipeWindow = new NewRecipeWindow();
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
                newRecipeWindow = new NewRecipeWindow();
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

    public NewRecipeWindow getNewRecipeWindow(){
        return newRecipeWindow;
    }

    /**
     * ´NewRecipeWindow is a new frame used to create new, or edit existing recipes
     */
    public class NewRecipeWindow extends JFrame{
        private ArrayList<String> instructionsArray;
        private ArrayList<String> ingredientsArray;

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
        private JTextField recipeName;
        private JTextField instructionInput;
        private JButton addInstruction;
        private JButton removeInstruction;
        private JButton saveRecipe;
        private DefaultListModel<String> instructionListModel;
        private JList<String> instructionList;
        private ArrayList<String> ingredients;

        /**
         * Constructor used when creating new recipe
         */
        public NewRecipeWindow(){
            //Frame settings
            instructionsArray = new ArrayList<>();
            ingredientsArray = new ArrayList<>();
            setTitle("Nytt recept");
            setPreferredSize(new Dimension(1000, 600));
            setupNewRecipeFrame();

        }

        /**
         * Constructor used when editing a recipe, receives a recicpe
         * @param recipe Received recipe
         */
        public NewRecipeWindow(Recipe recipe){
            //Frame settings
            instructionsArray = new ArrayList<>();
            ingredientsArray = new ArrayList<>();
            setTitle("Ändra recept");
            setPreferredSize(new Dimension(1000, 600));
            setupNewRecipeFrame();
        }

        /**
         * Sets up the frame
         */
        public void setupNewRecipeFrame(){
            ingredients = controller.getIngredientNames();
            controller.resetRecipeIngredients();
            //West panel
            westPanel = new JPanel();
            westPanel.setLayout(new BorderLayout());
            westPanel.setPreferredSize(new Dimension(200, 400));

            westNorth = new JPanel();
            westNorth.setBorder(new TitledBorder(""));

            ingredientsMenu = new JComboBox(ingredients.toArray());
            ingredientsMenu.addActionListener(this::actionPerformed);
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
            addIngredient.addActionListener(this::actionPerformed);
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
            addInstruction.addActionListener(this::actionPerformed);
            eastNorth.add(addInstruction);

            removeInstruction = new JButton("Ta bort");
            removeInstruction.addActionListener(this::actionPerformed);
            eastNorth.add(removeInstruction);

            instructionListModel = new DefaultListModel<>();
            instructionList = new JList<>(instructionListModel);
            instructionList.setBorder(new TitledBorder("Instruktioner"));
            eastPanel.add(instructionList, BorderLayout.CENTER);

            eastPanel.add(eastNorth, BorderLayout.NORTH);

            eastSouth = new JPanel();
            recipeName = new JTextField("Receptnamn...");
            recipeName.setPreferredSize(new Dimension(200,25));
            eastSouth.add(recipeName);
            saveRecipe = new JButton("Spara recept");
            saveRecipe.addActionListener(this::actionPerformed);
            eastSouth.add(saveRecipe);
            eastPanel.add(eastSouth, BorderLayout.SOUTH);

            add(westPanel, BorderLayout.WEST);
            add(eastPanel, BorderLayout.CENTER);
            pack();
            setVisible(true);

            unit.setText(controller.getIngredientPrefix((String) ingredientsMenu.getSelectedItem()));
        }

        /**
         * Listener for buttons
         * @param e
         */
        public void actionPerformed(ActionEvent e){
            if (e.getSource() == addInstruction){
                instructionsArray.add(instructionInput.getText());
                updateInstructions();
            }
            if (e.getSource() == removeInstruction){
                if (instructionList.getSelectedIndex() != -1){
                    instructionsArray.remove(instructionList.getSelectedIndex());
                    updateInstructions();
                }
            }
            if (e.getSource() == addIngredient){
                if ((double)amountModel.getValue() > 0) { // Kan lägga till en JOptionpane för att bekräfta
                    controller.createRecipeIngredient((String)ingredientsMenu.getSelectedItem(), (double) amountModel.getValue());
                    updateIngredients();
                }
            }
            if (e.getSource() == removeIngredient){
                if (!ingredientsList.isSelectionEmpty()){
                    controller.removeRecipeIngredient(ingredientsList.getSelectedIndex());
                    updateIngredients();
                } else {
                    JOptionPane.showMessageDialog(null, "Inget markerat", "Fel", JOptionPane.ERROR_MESSAGE);
                }

            }
            if (e.getSource() == saveRecipe){
                if (ingredientsArray.size() >= 1){ // Kan lägga till en JOptionpane för att bekräfta
                    controller.addRecipeToDatabase(recipeName.getText(), instructionsArray);
                } else  {
                    JOptionPane.showMessageDialog(null, "Det måste finnas ingredienser tillagda för att spara receptet", "Fel", JOptionPane.ERROR_MESSAGE);
                }
            }
            if (e.getSource() == ingredientsMenu){

                unit.setText(controller.getIngredientPrefix((String) ingredientsMenu.getSelectedItem()));
            }
        }

        /**
         * Method for updating the GUI
         */
        public void updateInstructions(){
            int i = 1;
            instructionListModel.clear();
            ingredientsModel.clear();
            for (String s : instructionsArray){
                String formatted = (i) + ". " + s;
                instructionListModel.addElement(formatted);
                i++;
            }

        }

        public void updateIngredients(){
            ingredientsListModel.clear();
            ArrayList<String> ingredientStrings = controller.populateNewRecipeIngredients(this);
            for (String s : ingredientStrings){
                ingredientsListModel.addElement(s);
            }
        }
    }
}
