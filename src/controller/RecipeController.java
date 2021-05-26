package controller;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;
import model.home.Notifications;
import model.ingredient.Ingredient;
import model.recipe.Recipe;
import model.recipe.RecipeIngredient;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.util.ArrayList;
import java.util.Set;

/**
 * Controller class for handling everything related to recipes
 * @Author Ludvig Wedin Pettersson
 * @Version 2.0
 */
public class RecipeController {
    private DatabaseReference databaseReference;
    private PropertyChangeSupport pcs = new PropertyChangeSupport(this);
    private Controller controller;
    private ArrayList<Recipe> allRecipes = new ArrayList<>();
    private ArrayList<String> allRecipeNames = new ArrayList<>();
    private Set<Ingredient> allIngredients;
    private ArrayList<RecipeIngredient> recipeIngredient = new ArrayList<>();
    private ArrayList<String> ingredientNames = new ArrayList<>();

    /**
     * Constructor for the class
     * @param controller        Reference to main controller
     * @param databaseReference Reference to use database
     */
    public RecipeController(Controller controller, DatabaseReference databaseReference) {
        this.databaseReference = databaseReference;
        getRecipesFromDatabase();
        getIngredientsFromDatabase();
        this.controller = controller;
        allIngredients = controller.getStorageController().getAllIngredients();
    }

    /**
     * Used by other classes to register listeners
     * @param listener reference to the class that registers
     */
    public void registerPropertyChangeListener(PropertyChangeListener listener) {
        pcs.addPropertyChangeListener(listener);
    }

    /**
     * Registers a listener in the database. Everytime data changes under the tab "Recipes", the program will retrieve all recipes and store them.
     */
    public void getRecipesFromDatabase() {
        databaseReference.child("Recipes").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                allRecipes.clear();
                allRecipeNames.clear();
                for (DataSnapshot recept : dataSnapshot.getChildren()) {
                    Recipe rec = recept.getValue(Recipe.class);
                    allRecipes.add(rec);
                    allRecipeNames.add(rec.getName());
                }
                pcs.firePropertyChange("RecipeNames", null, allRecipeNames);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                System.out.println("Fel vid hämtning av recept");
            }
        });
    }

    /**
     * Registers a listener in the database. Everytime data changes under the tab "Ingredient", the onDataChange method will run and retrieve all ingredients.
     */
    public void getIngredientsFromDatabase() {
        databaseReference.child("Ingredient").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                ingredientNames.clear();
                allIngredients.clear();

                for (DataSnapshot d : dataSnapshot.getChildren()) {
                    Ingredient ingredient = d.getValue(Ingredient.class);
                    String s = ingredient.getName();

                    ingredientNames.add(s);

                    if (!controller.getStorageController().checkIfIngredientExists(ingredient.getName())) {
                        allIngredients.add(ingredient);
                    } else {
                        controller.getStorageController().updateIngredient(ingredient.getName(), ingredient);
                    }
                }
                pcs.firePropertyChange("IngredientNames", null, ingredientNames);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                System.out.println(databaseError.toString());
            }
        });
    }


    /**
     * Used when user is finished with a recipe and will deduct the correct amounts of each ingredient from the storage automatically.
     * Goes through all the ingredients in the specified recipe.
     * @param selectedItem Specifies which recipe was used.
     * @param batches      Specifies how many batches the user has made of the selected recipe and will multiply the amounts from the recipe.
     */
    public void updateAmountsIngredient(int selectedItem, int batches) {
        Recipe rec = allRecipes.get(selectedItem);
        for (RecipeIngredient ri : rec.getIngredients()) {
            for (Ingredient i : allIngredients) {
                if (ri.getIngredient().getName().equals(i.getName())) {
                    i.setCurrentAmount(i.getCurrentAmount() - (ri.getAmount() * batches));
                    databaseReference.child("Ingredient").child(i.getName()).setValueAsync(i);

                    if (i.getCurrentAmount() < i.getCriticalAmount()) {
                        controller.getOrderController().addOrderItemToSupplierOrder(i, (int) i.getRecommendedAmount());
                        controller.getHomeController().addNotification(new Notifications("Order", "Ingredient: " + i.getName() + " dropped below limit! Added to order list!"));
                    }
                    break;
                }
            }
        }
    }


    /**
     * Resets list of RecipeIngredients that wont be used.
     */
    public void resetRecipeIngredients() {
        recipeIngredient.clear();
    }

    /**
     * Creates a new RecipeIngredient object and saves it in a list.
     * @param ingredient The chosen ingredient
     * @param amount     The selected amount that should be used in recipe
     */
    public void createRecipeIngredient(String ingredient, double amount) {
        for (Ingredient i : allIngredients) {
            if (i.getName().equals(ingredient)) {
                recipeIngredient.add(new RecipeIngredient(i, amount));
                break;
            }
        }
    }

    /**
     * Removes a specific RecipeIngredient from list.
     * @param i index of RecipeIngredient
     */
    public void removeRecipeIngredient(int i) {
        recipeIngredient.remove(i);
    }

    /**
     * Gets a list of all registered ingredients names.
     * @return
     */
    public ArrayList<String> getIngredientNames() {
        return ingredientNames;
    }

    /**
     * Sets list with ingredient names.
     * @param ingredientNames
     */
    public void setIngredientNames(ArrayList<String> ingredientNames) {
        this.ingredientNames = ingredientNames;
    }

    /**
     * Gets the unit type of a specific ingredient.
     * @param name Name of the ingredient.
     * @return Returns the unit.
     */
    public String getIngredientPrefix(String name) {
        String prefix = "";
        for (Ingredient i : allIngredients) {
            if (i.getName().equals(name)) {
                prefix = i.getUnit().getPrefix();
                break;
            }
        }

        return prefix;
    }

    /**
     * Creates a new Recipe and saves it in the database
     * @param name         Name of the recipe
     * @param instructions List of instructions for recipe.
     */
    public void addRecipeToDatabase(String name, ArrayList<String> instructions) {
        if (name == null || name.equalsIgnoreCase("")) {
            return;
        }
        Recipe recipeToAddToDatabase = new Recipe(name, recipeIngredient, instructions);
        databaseReference.child("Recipes").child(name).setValueAsync(recipeToAddToDatabase);
    }

    /**
     * Used when trying to save an edited recipe.
     * Removes old recipe from database using old recipe name
     * Adds the edited recipe to the database.
     * @param name         Name of recipe.
     * @param instructions List of instructions for recipe.
     * @param recName      Old recipe name
     */
    public void updateRecipeDatabase(String name, ArrayList<String> instructions, String recName) {
        Recipe updatedRecipe = new Recipe(name, recipeIngredient, instructions);
        databaseReference.child("Recipes").child(recName).removeValueAsync();
        databaseReference.child("Recipes").child(name).setValueAsync(updatedRecipe);
    }

    /**
     * Removes a specified recipe from the database
     * @param name Name of the recipe.
     */
    public void removeRecipeFromDatabase(String name) {
        databaseReference.child("Recipes").child(name).removeValueAsync();
    }

    /**
     * Called by GUI when a new RecipeIngredient is created, creates a list of String containing ingredient names, and amounts.
     * @return String list.
     */
    public ArrayList<String> populateNewRecipeIngredients() {
        ArrayList<String> strings = new ArrayList<>();
        for (RecipeIngredient r : recipeIngredient) {
            strings.add(r.toString());
        }
        return strings;
    }

    /**
     * Called by GUI when user selects a recipe to view.
     * @param i     Index of selected recipe.
     * @param multi Int used to multiply ingredients amount when making bigger batches.
     * @return String list with ingredients and their amounts.
     */
    public ArrayList<String> populateRecipeIngredients(int i, int multi) {
        Recipe rec = allRecipes.get(i);
        ArrayList<String> al = new ArrayList<>();
        recipeIngredient.clear();
        for (RecipeIngredient ri : rec.getIngredients()) {
            recipeIngredient.add(ri);
            al.add(ri.toString2(multi));
        }
        return al;
    }

    /**
     * Gets the instructions for a specified recipe.
     * @param i Index of selected recipe.
     * @return String list of instructions.
     */
    public ArrayList<String> getSelectedRecipeInstructions(int i) {
        return allRecipes.get(i).getInstructions();
    }

    /**
     * Used when trying to save a recipe, checks if recipe already exists.
     * @param recName Name of recipe that should be checked.
     * @return Boolean, true if duplicate.
     */
    public Boolean checkDuplicateRecipe(String recName) {
        for (Recipe r : allRecipes) {
            if (r.getName().equals(recName)) {
                return true;
            }
        }
        return false;
    }

    //<editor-fold desc="Getters, setters">
    public ArrayList<Recipe> getAllRecipes() {
        return allRecipes;
    }

    public void setAllRecipes(ArrayList<Recipe> allRecipes) {
        this.allRecipes = allRecipes;
    }

    public Set<Ingredient> getAllIngredients() {
        return allIngredients;
    }

    public void setAllIngredients(Set<Ingredient> allIngredients) {
        this.allIngredients = allIngredients;
    }

    public ArrayList<RecipeIngredient> getRecipeIngredient() {
        return recipeIngredient;
    }

    public void setRecipeIngredient(ArrayList<RecipeIngredient> recipeIngredient) {
        this.recipeIngredient = recipeIngredient;
    }

    public ArrayList<String> getAllRecipeNames() {
        return allRecipeNames;
    }

    public void setAllRecipeNames(ArrayList<String> allRecipeNames) {
        this.allRecipeNames = allRecipeNames;
    }

    public DatabaseReference getDatabaseReference() {
        return databaseReference;
    }

    public void setDatabaseReference(DatabaseReference databaseReference) {
        this.databaseReference = databaseReference;
    }

    public PropertyChangeSupport getPcs() {
        return pcs;
    }

    public void setPcs(PropertyChangeSupport pcs) {
        this.pcs = pcs;
    }

    //</editor-fold>
}
