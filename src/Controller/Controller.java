package Controller;



import Model.*;
import View.MainView;
import View.RecipePanel;
import com.google.auth.oauth2.GoogleCredentials;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import com.google.firebase.database.*;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.io.FileInputStream;
import java.io.IOException;
import java.lang.reflect.Array;
import java.util.*;

public class Controller {
    private static FirebaseDatabase database;
    public static DatabaseReference databaseReference;
    private static MainView mainView;

    private ArrayList<Recipe> allRecipes = new ArrayList<>();
    private ArrayList<Ingredient> allIngredients = new ArrayList<>();
    private ArrayList<RecipeIngredient> recipeIngredient = new ArrayList<>();
    private ArrayList<String> ingredientNames = new ArrayList<>();
    private PropertyChangeSupport pcs = new PropertyChangeSupport(this);


    public static void main(String[] args) throws IOException, InterruptedException {
        Controller controller = new Controller();
    }

    public Controller() throws IOException {
        connectToFirebase();
        database = FirebaseDatabase.getInstance();
        databaseReference = database.getReference();

        getNotesFromDatabase();
        mainView = new MainView(this);
        getRecipesFromDatabase();
    }

    public void addPropertyChangeListener(PropertyChangeListener listener){
        pcs.addPropertyChangeListener(listener);
    }


    public static void connectToFirebase() throws IOException {
        FileInputStream serviceAccount = new FileInputStream("./ServiceAccount.json");
        FirebaseOptions options = new FirebaseOptions
                .Builder()
                .setCredentials(GoogleCredentials.fromStream(serviceAccount))
                .setDatabaseUrl("https://alltpp-default-rtdb.europe-west1.firebasedatabase.app/")
                .build();

        FirebaseApp.initializeApp(options);
    }

    public WeekDays[] getWeekDays() {
        return WeekDays.values();
    }

    public String[] getUnitsPrefixArray() {
        return Unit.getPrefixArray();
    }

    // läser data från databasen
    public static Note[] getNotesFromDatabase() {
        databaseReference.child("Notes").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                List<Object> allNotesList = (List<Object>) dataSnapshot.getValue();
                for (Object objectMap : allNotesList) {
                    if (objectMap == null)
                        continue;
                    HashMap<String, Object> map = (HashMap<String, Object>) objectMap;
                    String title = map.get("title").toString();
                    String desc = map.get("description").toString();
                    int id = Integer.parseInt(map.get("id").toString());
                    Note note = new Note(title, desc, id);
                    mainView.getHomePanel().addNote(note);
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                System.out.println(databaseError.toString());
            }
        });
        return null;
    }

    public void addIngredientToDatabase(String name, double cost, double currentAmount, double criticalAmount, double recommendedAmount, String unitPrefix){
        Ingredient ingredientToAddToDatabase = new Ingredient(name, cost, currentAmount, criticalAmount, recommendedAmount, Unit.getUnitBasedOnPrefix(unitPrefix));
        databaseReference.child("Ingredient").push().setValueAsync(ingredientToAddToDatabase);
    }

    public void changeProductInDatabase(String key, String name, double cost, double currentAmount, double criticalAmount, double recommendedAmount, String unitPrefix){
        Ingredient changedIngredient = new Ingredient(name, cost, currentAmount, criticalAmount, recommendedAmount, Unit.getUnitBasedOnPrefix(unitPrefix));
        databaseReference.child("Ingredient").child(key).setValueAsync(changedIngredient);
    }

    public void removeIngredientFromDatabase(String key){
        databaseReference.child("Ingredient").child(key).removeValueAsync();
    }

    public void getIngredientsFromDatabase(){
        ingredientNames.clear();
        allIngredients.clear();
        ArrayList<String> ingredientValueList = new ArrayList<>();


        databaseReference.child("Ingredient").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                HashMap<String, HashMap<String, Object>> ingredientMap = (HashMap<String, HashMap<String, Object>>) dataSnapshot.getValue();
                for (Map.Entry<String, HashMap<String, Object>> stringHashMapEntry : ingredientMap.entrySet()) {
                    Map.Entry mapElement = stringHashMapEntry;


                    // Testkod
                    String s = dataSnapshot.child((String) mapElement.getKey()).getValue(Ingredient.class).getName();
                    ingredientNames.add(s);
                    Ingredient ingredient = dataSnapshot.child((String) mapElement.getKey()).getValue(Ingredient.class);
                    allIngredients.add(ingredient);



                    ingredientValueList.add(dataSnapshot.child((String) mapElement.getKey()).getValue(Ingredient.class).toString()
                            + "<!--" + mapElement.getKey() + "-->" +"</html>");
                }

                mainView.getStoragePanel().updateList(ingredientValueList);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                System.out.println(databaseError.toString());
            }
        });
    }

    //<editor-fold desc="Functionality for recipes">
    public void getRecipesFromDatabase(){
        databaseReference.child("Recipes").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                allRecipes.clear();
                for (DataSnapshot recept : dataSnapshot.getChildren()) {
                    Recipe rec = recept.getValue(Recipe.class);
                    allRecipes.add(rec);
                }
                ArrayList<String> recNames = getRecipeNames();
                pcs.firePropertyChange("updRecipes", null, recNames);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                System.out.println("Fel i hämtning av recept");
            }
        });
    }

    public ArrayList<String> getRecipeNames(){
        ArrayList<String> recipeNames = new ArrayList<>();
        for (Recipe r : allRecipes){
            recipeNames.add(r.getName());
        }
        return recipeNames;
    }


    public void resetRecipeIngredients(){
        recipeIngredient.clear();
    }

    public void createRecipeIngredient(String ingredient, double amount){
        for (Ingredient i :allIngredients){
            if (i.getName().equals(ingredient)){
                recipeIngredient.add(new RecipeIngredient(i, amount));
                break;
            }
        }
    }

    public void removeRecipeIngredient(int i){
        System.out.println(recipeIngredient.get(i).toString());
        recipeIngredient.remove(i);
    }

    public ArrayList<String> getIngredientNames(){
        return ingredientNames;
    }

    public String getIngredientPrefix(String name){
        String prefix = "";
        for (Ingredient i : allIngredients){
            if (i.getName().equals(name)){
                prefix = i.getUnit().getPrefix();
                break;
            }
        }

        return prefix;
    }

    public void addRecipeToDatabase(String name, ArrayList<String> instructions){
        Recipe recipeToAddToDatabase = new Recipe(name, recipeIngredient, instructions);
        databaseReference.child("Recipes").push().setValueAsync(recipeToAddToDatabase);
    }

    public ArrayList<String> populateNewRecipeIngredients(RecipePanel.NewRecipeWindow newRecipeWindow){
        ArrayList<String> strings = new ArrayList<>();
        for (RecipeIngredient r : recipeIngredient){
            strings.add(r.toString());
        }
        return strings;
    }
    //</editor-fold>


}
