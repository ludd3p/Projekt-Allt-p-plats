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

    private  Set<Ingredient> allIngredients = new HashSet<>();
    private StoragePanel panel;

    public StorageController(Controller controller) {
        this.controller = controller;
        getIngredientsFromDatabase();
    }

    /**
     * Set-up for the storage-tab main panel.
     * @param panel storage-tab main panel.
     */
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
     * @param name              of the ingredient.
     * @param cost              of the ingredient.
     * @param currentAmount     of the ingredient in storage.
     * @param criticalAmount    amount at which there should be a warning about the volume of the ingredient in the storage is low.
     * @param recommendedAmount of the ingredient in the storage.
     * @param unitPrefix        unit related to the ingredient.
     * @param supplierName      of the supplier related to the ingredient.
     */
    public void addIngredientToDatabase(String name, double cost, double currentAmount, double criticalAmount, double recommendedAmount, String unitPrefix, String supplierName) {
        Controller.getDatabaseReference().child("Ingredient").child(name).
                setValueAsync(new Ingredient(   name,
                                                cost,
                                                currentAmount,
                                                criticalAmount,
                                                recommendedAmount,
                                                Unit.getUnitBasedOnPrefix(unitPrefix),
                                                controller.getSupplierController().getSupplierFromName(supplierName)));
    }

    /**
     * Updates an ingredient with new values.
     *
     * @param oldName           name of the product to update.
     * @param newName              to update the ingredient with.
     * @param cost              to update the ingredient with.
     * @param currentAmount     to update the ingredient with.
     * @param criticalAmount    to update the ingredient with.
     * @param recommendedAmount to update the ingredient with.
     * @param unitPrefix        to update the ingredient with.
     */
    public void updateIngredient(String oldName, String newName, double cost, double currentAmount, double criticalAmount, double recommendedAmount, String unitPrefix, String supplierName) {
        Controller.getDatabaseReference().child("Ingredient").child(newName).setValueAsync(
                updateIngredient(oldName, new Ingredient(   newName,
                                                            cost,
                                                            currentAmount,
                                                            criticalAmount,
                                                            recommendedAmount,
                                                            Unit.getUnitBasedOnPrefix(unitPrefix),
                                                            controller.getSupplierController().getSupplierFromName(supplierName))));

        if(!oldName.equals(newName))
            Controller.getDatabaseReference().child("Ingredient").child(oldName).removeValueAsync();
    }

    /**
     * Updates an ingredient in the ingredientList with new values.
     *
     * @param name of the product
     * @param ingredientNewValues Ingredient-object with values to be used to update old object.
     * @return the updated Ingredient-object.
     */
    public Ingredient updateIngredient(String name, Ingredient ingredientNewValues) {
        Ingredient updatedIngredient = null;

        for (Ingredient ingredient : allIngredients) {
            if (ingredient.getName().equals(name)) {
                ingredient.updateValues(ingredientNewValues);
                updatedIngredient = ingredient;
                break;
            }
        }

        return updatedIngredient;
    }

    /**
     * Updates the current quantity of an ingredient in both the database and the ingredient list.
     *
     * @param ingredientToUpdate    to update the current quantity of.
     * @param quantityToChangeBy    value to change the current amount of the ingredient by.
     */
    public void updateQuantityOfIngredient(Ingredient ingredientToUpdate, double quantityToChangeBy) {
        for (Ingredient ingredient : allIngredients) {
            if (ingredient.getName().equals(ingredientToUpdate.getName())) {
                ingredient.setCurrentAmount(ingredient.getCurrentAmount() + quantityToChangeBy);
                Controller.getDatabaseReference().child("Ingredient").child(ingredientToUpdate.getName()).setValueAsync(ingredient);
                break;
            }
        }

        updatePanel();
    }

    /**
     * Removes an ingredient.
     *
     * @param name of the ingredient to remove.
     */
    public void removeIngredient(String name) {
        allIngredients.removeIf(ingredient -> ingredient.getName().equals(name));
        Controller.getDatabaseReference().child("Ingredient").child(name).removeValueAsync();
        updatePanel();
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

        ((DefaultListModel<Ingredient>) panel.getProductList().getModel()).clear();
        ((DefaultListModel<Ingredient>) panel.getProductList().getModel()).addAll(allIngredients);
    }

    /**
     * Checks if an ingredient already exists in the list.
     *
     * @param name
     * @return
     */
    public boolean checkIfIngredientExists(String name) {
        for (Ingredient ingredient : allIngredients) {
            if (ingredient.getName().equals(name)) {
                return true;
            }
        }
        return false;
    }

    //<editor-fold desc = "Setters and getters">
    public String[] getSupplierNames() {
        return controller.getSupplierController().getSupplierNames();
    }


    public Controller getController() {
        return controller;
    }

    public void setController(Controller controller) {
        this.controller = controller;
    }

    public  Set<Ingredient> getAllIngredients() {
        return allIngredients;
    }

    public  void setAllIngredients(Set<Ingredient> allIngredients) {
        this.allIngredients = allIngredients;
    }

    public StoragePanel getPanel() {
        return panel;
    }

    public void setPanel(StoragePanel panel) {
        this.panel = panel;
    }
    //</editor-fold>
}
