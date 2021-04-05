package controller;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;
import model.Recipe;
import model.RecipeIngredient;
import model.ingredient.Ingredient;
import view.RecipePanel;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.util.ArrayList;

public class RecipeController {
    private Controller controller;
    private RecipePanel recPanel;
    private DatabaseReference databaseReference;
    private PropertyChangeSupport pcs = new PropertyChangeSupport(this);

    private ArrayList<Recipe> allRecipes = new ArrayList<>();
    private ArrayList<String> allRecipeNames = new ArrayList<>();
    private ArrayList<Ingredient> allIngredients = new ArrayList<>();
    private ArrayList<RecipeIngredient> recipeIngredient = new ArrayList<>();
    private ArrayList<String> ingredientNames = new ArrayList<>();

    public RecipeController(Controller controller, DatabaseReference databaseReference){
        this.controller = controller;
        this.databaseReference = databaseReference;
        getRecipesFromDatabase();
        getIngredientsFromDatabase();
    }

    public void registerPropertyChangeListener(PropertyChangeListener listener){
        pcs.addPropertyChangeListener(listener);
    }

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
                    allIngredients.add(ingredient);
                }
                pcs.firePropertyChange("IngredientNames", null, ingredientNames);
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {
                System.out.println(databaseError.toString());
            }
        });
    }


    public void resetRecipeIngredients() {
        recipeIngredient.clear();
    }

    public void createRecipeIngredient(String ingredient, double amount) {
        for (Ingredient i : allIngredients) {
            if (i.getName().equals(ingredient)) {
                recipeIngredient.add(new RecipeIngredient(i, amount));
                break;
            }
        }
    }

    public void removeRecipeIngredient(int i) {
        System.out.println(recipeIngredient.get(i).toString());
        recipeIngredient.remove(i);
    }

    public ArrayList<String> getIngredientNames() {
        return ingredientNames;
    }

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

    public void addRecipeToDatabase(String name, ArrayList<String> instructions) {
        Recipe recipeToAddToDatabase = new Recipe(name, recipeIngredient, instructions);
        databaseReference.child("Recipes").push().setValueAsync(recipeToAddToDatabase);
    }

    public ArrayList<String> populateNewRecipeIngredients(RecipePanel.NewRecipeWindow newRecipeWindow) {
        ArrayList<String> strings = new ArrayList<>();
        for (RecipeIngredient r : recipeIngredient) {
            strings.add(r.toString());
        }
        return strings;
    }

    public ArrayList<String> populateRecipeIngredients(int i, int multi){
        Recipe rec = allRecipes.get(i);
        ArrayList<String> al = new ArrayList<>();
        for (RecipeIngredient ri : rec.getIngredients()){
            al.add(ri.toString2(multi));
        }
        return al;
    }

    //<editor-fold desc="Getters, setters">
    public ArrayList<Recipe> getAllRecipes() {
        return allRecipes;
    }

    public void setAllRecipes(ArrayList<Recipe> allRecipes) {
        this.allRecipes = allRecipes;
    }

    public ArrayList<Ingredient> getAllIngredients() {
        return allIngredients;
    }

    public void setAllIngredients(ArrayList<Ingredient> allIngredients) {
        this.allIngredients = allIngredients;
    }

    public ArrayList<RecipeIngredient> getRecipeIngredient() {
        return recipeIngredient;
    }

    public void setRecipeIngredient(ArrayList<RecipeIngredient> recipeIngredient) {
        this.recipeIngredient = recipeIngredient;
    }

    public void setIngredientNames(ArrayList<String> ingredientNames) {
        this.ingredientNames = ingredientNames;
    }

    public ArrayList<String> getAllRecipeNames() {
        return allRecipeNames;
    }

    public void setAllRecipeNames(ArrayList<String> allRecipeNames) {
        this.allRecipeNames = allRecipeNames;
    }

    public void setRecPanel(RecipePanel recPanel){
        this.recPanel = recPanel;
    }
    //</editor-fold>
}
