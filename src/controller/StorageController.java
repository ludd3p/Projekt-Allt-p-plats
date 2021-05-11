package controller;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;
import model.Unit;
import model.ingredient.Ingredient;
import view.StoragePanel;

import javax.swing.*;
import java.util.HashSet;
import java.util.Set;

public class StorageController {
    private Controller controller;

    public static Set<Ingredient> allIngredients = new HashSet<>();
    public StoragePanel panel;

    public StorageController(Controller controller) {
        this.controller = controller;
        getIngredientsFromDatabase();
    }

    public void setUp(StoragePanel panel) {
        this.panel = panel;
        updatePanel();
    }

    /**
     * Gets and array of units.
     * @return array of units.
     */
    public String[] getUnitsPrefixArray() {
        return Unit.getPrefixArray();
    }

    /**
     * Adds an ingredient to the database.
     *
     * @param name of the ingredient.
     * @param cost of the ingredient.
     * @param currentAmount of the ingredient in storage.
     * @param criticalAmount amount at which there should be a warning about the volume of the ingredient in the storage
     *                       is low.
     * @param recommendedAmount of the ingredient in the storage.
     * @param unitPrefix unit related to the ingredient.
     */
    public void addIngredientToDatabase(String name, double cost, double currentAmount, double criticalAmount, double recommendedAmount, String unitPrefix, String supplierName) {
        Ingredient ingredientToAddToDatabase = new Ingredient(name,
                cost,
                currentAmount,
                criticalAmount,
                recommendedAmount,
                Unit.getUnitBasedOnPrefix(unitPrefix),
                controller.getSupplierController().getSupplierFromName(supplierName));

        Controller.getDatabaseReference().child("Ingredient").child(ingredientToAddToDatabase.getName()).setValueAsync(ingredientToAddToDatabase);
    }

    /**
     * Updates an ingredient with new values.
     *
     * @param key               of the ingredient
     * @param name              to update the ingredient with
     * @param cost              to update the ingredient with
     * @param currentAmount     to update the ingredient with
     * @param criticalAmount    to update the ingredient with
     * @param recommendedAmount to update the ingredient with
     * @param unitPrefix        to update the ingredient with
     */
    public void updateIngredient(String key, String name, double cost, double currentAmount, double criticalAmount, double recommendedAmount, String unitPrefix, String supplierName) {
        Controller.getDatabaseReference().child("Ingredient").child(key).setValueAsync(
                Ingredient.updateIngredient(key, new Ingredient(name,
                        cost,
                        currentAmount,
                        criticalAmount,
                        recommendedAmount,
                        Unit.getUnitBasedOnPrefix(unitPrefix),
                        controller.getSupplierController().getSupplierFromName(supplierName))));
    }

    /**
     * Updates the current quantity of an ingredient in both the database and the ingredient list.
     *
     * @param ingredient    to update the current quantity of.
     * @param quantityToAdd
     */
    public void updateQuantityOfIngredient(Ingredient ingredient, double quantityToAdd) {
        Controller.getDatabaseReference().child("Ingredient").child(ingredient.getName()).setValueAsync(
                Ingredient.updateIngredientCurrentQuantity(ingredient.getName(), quantityToAdd));

        updatePanel();
    }

    /**
     * Removes an ingredient.
     *
     * @param key of the ingredient to remove.
     */
    public void removeIngredientFromDatabase(String key) {
        Ingredient.removeIngredientFromList(key);
        Controller.getDatabaseReference().child("Ingredient").child(key).removeValueAsync();
        updatePanel();

    }

    /**
     * Removes an ingredient.
     *
     * @param ingredient to remove.
     */
    public void removeIngredientFromDatabase(Ingredient ingredient) {
        allIngredients.remove(ingredient);
        Controller.getDatabaseReference().child("Ingredient").child(ingredient.getName()).removeValueAsync();

    }

    /**
     * Gets ingredients from the database.
     */
    public void getIngredientsFromDatabase() {
        Controller.getDatabaseReference().child("Ingredient").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                allIngredients.clear();
                for (DataSnapshot d : dataSnapshot.getChildren()) {
                    Ingredient ingredient = d.getValue(Ingredient.class);
                    allIngredients.add(ingredient);
                }

                if (panel != null)
                    updatePanel();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                System.out.println(databaseError.toString());
            }
        });
    }

    /**
     * Updates the product list in the storage panel.
     */
    public void updatePanel() {
        if (panel == null)
            return;

        ((DefaultListModel) getPanel().getProductList().getModel()).clear();
        ((DefaultListModel<Ingredient>) getPanel().getProductList().getModel()).addAll(allIngredients);
    }

    public String[] getSupplierNames() {
        return controller.getSupplierController().getSupplierNames();
    }


    public Controller getController() {
        return controller;
    }

    public void setController(Controller controller) {
        this.controller = controller;
    }

    public static Set<Ingredient> getAllIngredients() {
        return allIngredients;
    }

    public static void setAllIngredients(Set<Ingredient> allIngredients) {
        StorageController.allIngredients = allIngredients;
    }

    public StoragePanel getPanel() {
        return panel;
    }

    public void setPanel(StoragePanel panel) {
        this.panel = panel;
    }

}
