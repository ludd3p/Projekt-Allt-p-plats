package controller;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;
import model.ingredient.Ingredient;
import model.*;

import java.util.HashMap;
import java.util.Map;

public class StorageController {
    private Controller controller;

    public StorageController(Controller controller){
        this.controller = controller;
    }

    public String[] getUnitsPrefixArray() {
        return Unit.getPrefixArray();
    }

    /**
     * Adds an ingredient to the database.
     * @param name
     * @param cost
     * @param currentAmount
     * @param criticalAmount
     * @param recommendedAmount
     * @param unitPrefix
     */
    public void addIngredientToDatabase(String name, double cost, double currentAmount, double criticalAmount, double recommendedAmount, String unitPrefix) {
        Ingredient ingredientToAddToDatabase = new Ingredient(name, cost, currentAmount, criticalAmount, recommendedAmount, Unit.getUnitBasedOnPrefix(unitPrefix));
        Controller.getDatabaseReference().child("Ingredient").push().setValueAsync(ingredientToAddToDatabase);
    }

    /**
     * Updates an ingredient with new values.
     * @param key of the ingredient
     * @param name to update the ingredient with
     * @param cost to update the ingredient with
     * @param currentAmount to update the ingredient with
     * @param criticalAmount to update the ingredient with
     * @param recommendedAmount to update the ingredient with
     * @param unitPrefix to update the ingredient with
     */
    public void updateIngredient(String key, String name, double cost, double currentAmount, double criticalAmount, double recommendedAmount, String unitPrefix) {
        Controller.getDatabaseReference().child("Ingredient").child(key).setValueAsync(
                Ingredient.updateIngredient(key, new Ingredient(name, cost, currentAmount, criticalAmount, recommendedAmount, Unit.getUnitBasedOnPrefix(unitPrefix))));
    }

    /**
     * Updates the current quantity of an ingredient in both the database and the ingredient list.
     * @param ingredient to update the current quantity of.
     * @param quantityToAdd
     */
    public void updateQuantityOfIngredient(Ingredient ingredient, double quantityToAdd){
        Controller.getDatabaseReference().child("Ingredient").child(ingredient.getKey()).setValueAsync(
                Ingredient.updateIngredientCurrentQuantity(ingredient.getKey(), quantityToAdd));
        getIngredientsFromDatabase();
    }

    /**
     * Removes an ingredient.
     * @param key
     */
    public void removeIngredientFromDatabase(String key) {
        Ingredient.removeIngredientFromList(key);
        Controller.getDatabaseReference().child("Ingredient").child(key).removeValueAsync();
    }

    /**
     * Gets ingredients from the database.
     */
    public void getIngredientsFromDatabase() {
        Controller.getDatabaseReference().child("Ingredient").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                HashMap<String, HashMap<String, Object>> ingredientMap = (HashMap<String, HashMap<String, Object>>) dataSnapshot.getValue();
                for (Map.Entry<String, HashMap<String, Object>> stringHashMapEntry : ingredientMap.entrySet()) {
                    Map.Entry mapElement = stringHashMapEntry;

                    Ingredient.addIngredientToList((dataSnapshot.child((String) mapElement.getKey()).getValue(Ingredient.class)), (String)mapElement.getKey());
                }

                Controller.getMainView().getStoragePanel().updateList(Ingredient.getIngredientStringsForStorage());
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                System.out.println(databaseError.toString());
            }
        });
    }

    public void bla(Ingredient ingredient, double quantity){

        Controller.getDatabaseReference().child("Ingredient").child(ingredient.getKey()).addListenerForSingleValueEvent(
                new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        HashMap<String, Object> map = (HashMap<String, Object>)dataSnapshot.getValue();

                        for(Map.Entry<String, Object> mapEntry : map.entrySet()){
                            System.out.println();
                        }
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                }
        );
    }
}
