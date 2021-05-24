package view;

import controller.RecipeController;

import javax.swing.*;
import javax.swing.border.EtchedBorder;
import javax.swing.border.TitledBorder;
import javax.swing.event.ChangeEvent;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.ArrayList;

/**
 * Panel for managing recipes
 * @Author Ludvig Wedin Pettersson
 * @Version 1.2
 */

public class RecipePanel extends JPanel implements PropertyChangeListener {
    private RecipeController recipeController;
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


    /**
     * Constructor for the panel
     */
    public RecipePanel(RecipeController recipeController) {
        this.recipeController = recipeController;
        recipeController.registerPropertyChangeListener(this);
        setLayout(new BorderLayout());
        setupPanels();
        fetchRecipeNames();
    }

    /**
     * Method that sets everything up inside the panel
     */
    public void setupPanels() {
        northPanel = new JPanel();
        leftPanel = new JPanel();
        centerPanel = new JPanel();

        //North panel
        northPanel.setBorder(new EtchedBorder(EtchedBorder.RAISED));

        recipes = new JComboBox<String>();
        recipes.setPreferredSize(new Dimension(200, 40));
        recipes.addActionListener(this::actionPerformed);
        recipes.setBorder(new TitledBorder("Recept"));
        northPanel.add(recipes);

        spinnerModel = new SpinnerNumberModel(1, 1, 20, 1);
        spinner = new JSpinner(spinnerModel);
        spinner.addChangeListener(this::stateChanged);
        spinner.setPreferredSize(new Dimension(75, 40));
        spinner.setBorder(new TitledBorder("Satser"));
        northPanel.add(spinner);

        done = new JButton("Recept tillagat");
        done.addActionListener(this::actionPerformed);
        northPanel.add(done);

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
        leftPanel.add(ingredientsList);
        leftPanel.setPreferredSize(new Dimension(200, 800));

        add(leftPanel, BorderLayout.WEST);

        //Center panel
        centerPanel.setLayout(new BorderLayout());
        instructionsModel = new DefaultListModel<>();
        recipeInstructions = new JList<>();
        recipeInstructions.setModel(instructionsModel);
        recipeInstructions.setBorder(new TitledBorder("Instruktioner"));
        centerPanel.add(recipeInstructions, BorderLayout.CENTER);

        add(centerPanel, BorderLayout.CENTER);


    }

    /**
     * Used initially when panel is constructed, fetches all recipe names.
     */
    public void fetchRecipeNames(){
        ArrayList<String> recNames = recipeController.getAllRecipeNames();
        for (String s : recNames) {
            recipes.addItem(s);
        }
    }


    /**
     * Listener for the buttons
     * @param e Source of method call
     */
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == done) {
            if (JOptionPane.showConfirmDialog(null, "Är du säker? Lagersaldo kommer att dras av för: " + recipes.getItemAt(recipes.getSelectedIndex()) + ", " + spinner.getValue() + " satser", "Recept tillagat", JOptionPane.YES_NO_OPTION) == 0) {
                recipeController.updateAmountsIngredient(recipes.getSelectedIndex(), (int)spinner.getValue());
            } else {
                JOptionPane.showMessageDialog(null, "Inga varor dras av", "Meddelande", JOptionPane.PLAIN_MESSAGE);
            }
        }

        if (e.getSource() == recipes) {
            ingredientsModel.clear();
            instructionsModel.clear();
            ArrayList<String> recIngredients = recipeController.populateRecipeIngredients(recipes.getSelectedIndex(), (int) spinner.getValue());
            ArrayList<String> recInstructions = recipeController.getSelectedRecipeInstructions(recipes.getSelectedIndex());
            for (String s : recIngredients) {
                ingredientsModel.addElement(s);
            }
            if (recInstructions != null) {
                int i = 1;
                String formatted;
                for (String s : recInstructions) {
                    formatted = i + ". " + s;
                    instructionsModel.addElement(formatted);
                    i++;
                }
            }

        }

        if (e.getSource() == addRecipe) {
            if (newRecipeWindow != null) {
                newRecipeWindow.dispose();
            }
            newRecipeWindow = new NewRecipeWindow();
        }

        if (e.getSource() == removeRecipe) {
            if (JOptionPane.showConfirmDialog(null, "Är du säker på att du vill ta bort " + recipes.getItemAt(recipes.getSelectedIndex()) + "?", "Ta bort recept", JOptionPane.YES_NO_OPTION) == 0) {
                recipeController.removeRecipeFromDatabase(recipes.getItemAt(recipes.getSelectedIndex()));
            } else {
                JOptionPane.showMessageDialog(null, "Receptet tas inte bort", "Meddelande", JOptionPane.PLAIN_MESSAGE);
            }
        }

        if (e.getSource() == modifyRecipe) {
            if (recipes.getItemAt(recipes.getSelectedIndex()) != null && JOptionPane.showConfirmDialog(null, "Vill du ändra i " + recipes.getItemAt(recipes.getSelectedIndex()) + "?", "Ändra recept", JOptionPane.YES_NO_OPTION) == 0) {
                if (newRecipeWindow != null) {
                    newRecipeWindow.dispose();
                }

                newRecipeWindow = new NewRecipeWindow(recipes.getItemAt(recipes.getSelectedIndex()));
            } else
                JOptionPane.showMessageDialog(null, "Inget recept valt", "Meddelande", JOptionPane.PLAIN_MESSAGE);
        }

    }

    /**
     * Listens to changed states of registered components
     * @param e Source of call
     */
    public void stateChanged(ChangeEvent e) {
        if (e.getSource() == spinner) {
            ingredientsModel.clear();
            ArrayList<String> recIngredients = recipeController.populateRecipeIngredients(recipes.getSelectedIndex(), (int) spinner.getValue());
            for (String s : recIngredients) {
                ingredientsModel.addElement(s);
            }
        }
    }

    /**
     * Handles PropertyChange.
     * @param evt incoming message with the changed property
     */
    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        if (evt.getPropertyName().equals("RecipeNames")) {
            ArrayList<String> recNames = (ArrayList<String>) evt.getNewValue();

            try {   //Needed try/catch because for some reason removeAllElements produces IndexOutOfBoundsException
                recipes.removeAllItems();
            } catch (Exception e){
                e.printStackTrace();
            }

            for (String s : recNames) {
                recipes.addItem(s);
            }

        }
    }


    /**
     * NewRecipeWindow is a new frame used to create new, or edit existing recipes
     */
    public class NewRecipeWindow extends JFrame implements PropertyChangeListener {
        private ArrayList<String> instructionsArray;
        private ArrayList<String> ingredientsArray;
        private Boolean editRecipe;
        private String recName;

        //West
        private JPanel westPanel;
        private JPanel westNorth;
        private JPanel westSouth;
        private JComboBox<String> ingredientsMenu;
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
        private JButton editInstruction;
        private DefaultListModel<String> instructionListModel;
        private JList<String> instructionList;
        private ArrayList<String> ingredients;


        /**
         * Constructor used when creating new recipe
         */
        public NewRecipeWindow() {
            //Frame settings
            instructionsArray = new ArrayList<>();
            ingredientsArray = new ArrayList<>();
            editRecipe = false;
            setTitle("Nytt recept");
            setPreferredSize(new Dimension(1000, 600));
            setupNewRecipeFrame();
            recipeController.registerPropertyChangeListener(this);

        }

        /**
         * Constructor used when editing a recipe, receives a recicpe
         */
        public NewRecipeWindow(String recName) {
            //Frame settings

            //ingredientsArray = new ArrayList<>();
            editRecipe = true;
            this.recName = recName;
            setTitle("Ändra recept: " + recName);
            setPreferredSize(new Dimension(1000, 600));
            setupNewRecipeFrame();
            recipeName.setText(recName);
            ingredientsListModel.clear();
            instructionListModel.clear();
            ingredientsArray = recipeController.populateRecipeIngredients(recipes.getSelectedIndex(), 1);
            recipeController.registerPropertyChangeListener(this);

            if (recipeController.getSelectedRecipeInstructions(recipes.getSelectedIndex()) != null) {
                instructionsArray = recipeController.getSelectedRecipeInstructions(recipes.getSelectedIndex());
            } else {
                instructionsArray = new ArrayList<>();
            }

            for (String s : ingredientsArray) {
                ingredientsListModel.addElement(s);
            }
            if (instructionsArray != null) {
                for (String s : instructionsArray) {
                    instructionListModel.addElement(s);
                }
            }

        }

        /**
         * Sets up the frame
         */
        public void setupNewRecipeFrame() {
            ingredients = recipeController.getIngredientNames();
            recipeController.resetRecipeIngredients();
            //West panel
            westPanel = new JPanel();
            westPanel.setLayout(new BorderLayout());
            westPanel.setPreferredSize(new Dimension(200, 400));

            westNorth = new JPanel();
            westNorth.setBorder(new TitledBorder(""));

            ingredientsMenu = new JComboBox(ingredients.toArray());
            ingredientsMenu.setPreferredSize(new Dimension(100, 25));
            ingredientsMenu.addActionListener(this::actionPerformed);
            westNorth.add(ingredientsMenu);

            amountModel = new SpinnerNumberModel(0, 0, 10000, 0.1);
            amount = new JSpinner(amountModel);
            amount.setPreferredSize(new Dimension(50, 25));
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
            removeIngredient.addActionListener(this::actionPerformed);
            westSouth.add(removeIngredient);

            westPanel.add(westSouth, BorderLayout.SOUTH);

            //East panel
            eastPanel = new JPanel();
            eastPanel.setLayout(new BorderLayout());
            eastPanel.setPreferredSize(new Dimension(400, 400));

            eastNorth = new JPanel();
            eastNorth.setBorder(new TitledBorder(""));

            instructionInput = new JTextField("Lägg till instruktion");
            instructionInput.setPreferredSize(new Dimension(300, 25));
            eastNorth.add(instructionInput);

            addInstruction = new JButton("Lägg till");
            addInstruction.addActionListener(this::actionPerformed);
            eastNorth.add(addInstruction);

            removeInstruction = new JButton("Ta bort");
            removeInstruction.addActionListener(this::actionPerformed);
            eastNorth.add(removeInstruction);

            editInstruction = new JButton("Ändra instruktion");
            editInstruction.addActionListener(this::actionPerformed);
            eastNorth.add(editInstruction);

            instructionListModel = new DefaultListModel<>();
            instructionList = new JList<>(instructionListModel);
            instructionList.setBorder(new TitledBorder("Instruktioner"));
            eastPanel.add(instructionList, BorderLayout.CENTER);

            eastPanel.add(eastNorth, BorderLayout.NORTH);

            eastSouth = new JPanel();
            recipeName = new JTextField("Receptnamn...");
            recipeName.setPreferredSize(new Dimension(200, 25));
            eastSouth.add(recipeName);
            saveRecipe = new JButton("Spara recept");
            saveRecipe.addActionListener(this::actionPerformed);
            eastSouth.add(saveRecipe);
            eastPanel.add(eastSouth, BorderLayout.SOUTH);

            add(westPanel, BorderLayout.WEST);
            add(eastPanel, BorderLayout.CENTER);
            pack();
            setVisible(true);

            unit.setText(recipeController.getIngredientPrefix((String) ingredientsMenu.getSelectedItem()));
        }

        /**
         * Listener for buttons
         * @param e source of call
         */
        public void actionPerformed(ActionEvent e) {
            if (e.getSource() == addInstruction) {
                instructionsArray.add(instructionInput.getText());
                updateInstructions();
                instructionInput.setText("");
            }

            if (e.getSource() == removeInstruction) {
                if (instructionList.getSelectedIndex() != -1) {
                    instructionsArray.remove(instructionList.getSelectedIndex());
                    updateInstructions();
                }
            }

            if (e.getSource() == addIngredient) {
                if ((double) amountModel.getValue() > 0) { // Kan lägga till en JOptionpane för att bekräfta
                    recipeController.createRecipeIngredient((String) ingredientsMenu.getSelectedItem(), (double) amountModel.getValue());
                    updateIngredients();
                }
            }

            if (e.getSource() == removeIngredient) {
                if (!ingredientsList.isSelectionEmpty()) {

                    recipeController.removeRecipeIngredient(ingredientsList.getSelectedIndex());
                    updateIngredients();
                } else {
                    JOptionPane.showMessageDialog(null, "Inget markerat", "Fel", JOptionPane.ERROR_MESSAGE);
                }
            }

            if (e.getSource() == saveRecipe) {
                if (editRecipe) {
                    saveEditedRecipe();
                } else
                    saveNewRecipe();
            }

            if (e.getSource() == ingredientsMenu) {
                unit.setText(recipeController.getIngredientPrefix((String) ingredientsMenu.getSelectedItem()));
            }

            if (e.getSource() == editInstruction){
                if (!instructionList.isSelectionEmpty()){
                    int i = instructionList.getSelectedIndex();
                    String s = instructionsArray.get(i);
                    String st = JOptionPane.showInputDialog(null, "Ändra instruktion", s);
                    instructionsArray.set(i, st);
                    updateInstructions();
                }
            }
        }

        /**
         * Checks if recipe has name and ingredient
         * @return true if has name and ingredient.
         */
        public boolean hasNameInstruction(){
            String s = recipeName.getText();
            return !ingredientsListModel.isEmpty() && s.length() >= 1;
        }


        /**
         * Used when saving a fresh recipe
         */
        public void saveNewRecipe(){
            if (hasNameInstruction() &&
                    JOptionPane.showConfirmDialog(null, "Vill du spara receptet " + recipeName.getText() + "?", "Spara", JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION) {
                if (!recipeController.checkDuplicateRecipe(recipeName.getText())) {
                    recipeController.addRecipeToDatabase(recipeName.getText(), instructionsArray);
                    JOptionPane.showMessageDialog(null, "Receptet är sparat", "Sparat", JOptionPane.INFORMATION_MESSAGE);
                    dispose();
                } else {
                    JOptionPane.showMessageDialog(null, "Recept med samma namn existerar, testa annat namn", "Fel", JOptionPane.ERROR_MESSAGE);
                }
            } else {
                JOptionPane.showMessageDialog(null, "Det måste finnas ingredienser och namn tillagda för att spara receptet", "Fel", JOptionPane.ERROR_MESSAGE);
            }

        }

        /**
         * Used when saving edited recipe
         */
        public void saveEditedRecipe(){
            if (hasNameInstruction() &&
                    JOptionPane.showConfirmDialog(null, "Vill du spara receptet " + recipeName.getText() + "?", "Spara", JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION) {
                if (!recipeController.checkDuplicateRecipe(recipeName.getText())) {
                    recipeController.updateRecipeDatabase(recipeName.getText(), instructionsArray, recName);
                    JOptionPane.showMessageDialog(null, "Receptet är sparat", "Sparat", JOptionPane.INFORMATION_MESSAGE);
                    dispose();
                } else if (JOptionPane.showConfirmDialog(null, "Recept med samma namn existerar, vill du skriva över?", "Fel", JOptionPane.YES_NO_OPTION) == 0){
                    recipeController.updateRecipeDatabase(recipeName.getText(), instructionsArray, recName);
                    JOptionPane.showMessageDialog(null, "Receptet är sparat", "Sparat", JOptionPane.INFORMATION_MESSAGE);
                    dispose();
                }
            } else {
                JOptionPane.showMessageDialog(null, "Det måste finnas ingredienser och namn tillagda för att spara receptet", "Fel", JOptionPane.ERROR_MESSAGE);
            }

        }

        /**
         * Handles PropertyChangeListener
         * @param evt Incoming message with new object
         */
        @Override
        public void propertyChange(PropertyChangeEvent evt) {
            if (evt.getPropertyName().equals("IngredientNames")) {
                ingredientsMenu.removeAllItems();
                ArrayList<String> ingNames = (ArrayList<String>) evt.getNewValue();
                for (String s : ingNames) {
                    ingredientsMenu.addItem(s);
                }
            }
        }

        /**
         * Method for updating the recipe instructions in GUI
         */
        public void updateInstructions() {
            int i = 1;
            instructionListModel.clear();
            ingredientsModel.clear();
            for (String s : instructionsArray) {
                String formatted = (i) + ". " + s;
                instructionListModel.addElement(formatted);
                i++;
            }

        }

        /**
         * Method updating recipe ingredients in GUI
         */
        public void updateIngredients() {
            ingredientsListModel.clear();
            ArrayList<String> ingredientStrings = recipeController.populateNewRecipeIngredients();
            for (String s : ingredientStrings) {
                ingredientsListModel.addElement(s);
            }
        }




    }

    public RecipeController getController() {
        return recipeController;
    }

    public void setController(RecipeController recipeController) {
        this.recipeController = recipeController;
    }
}
