package controller;


import com.google.auth.oauth2.GoogleCredentials;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import com.google.firebase.database.*;
import model.Daily.DailyEvent;
import model.*;
import view.MainView;
import view.RecipePanel;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Controller {
    private static FirebaseDatabase database;
    public static DatabaseReference databaseReference;
    private static MainView mainView;
    private OrderController orderController;
    private DailyEvent dailyEvent;

    private ArrayList<Recipe> allRecipes = new ArrayList<>();
    private ArrayList<Ingredient> allIngredients = new ArrayList<>();
    private ArrayList<RecipeIngredient> recipeIngredient = new ArrayList<>();
    private ArrayList<String> ingredientNames = new ArrayList<>();


    public Controller() throws IOException {
        connectToFirebase();
        this.dailyEvent = new DailyEvent();
        mainView = new MainView(this);
        orderController = new OrderController(this);

        getNotesFromDatabase();
        getRecipesFromDatabase();
    }


    public static void connectToFirebase() throws IOException {
        FileInputStream serviceAccount = new FileInputStream("./ServiceAccount.json");
        FirebaseOptions options = new FirebaseOptions
                .Builder()
                .setCredentials(GoogleCredentials.fromStream(serviceAccount))
                .setDatabaseUrl("https://alltpp-default-rtdb.europe-west1.firebasedatabase.app/")
                .build();

        FirebaseApp.initializeApp(options);
        database = FirebaseDatabase.getInstance();
        databaseReference = database.getReference();
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

    public void addIngredientToDatabase(String name, double cost, double currentAmount, double criticalAmount, double recommendedAmount, String unitPrefix) {
        Ingredient ingredientToAddToDatabase = new Ingredient(name, cost, currentAmount, criticalAmount, recommendedAmount, Unit.getUnitBasedOnPrefix(unitPrefix));
        databaseReference.child("Ingredient").push().setValueAsync(ingredientToAddToDatabase);
    }

    public void changeProductInDatabase(String key, String name, double cost, double currentAmount, double criticalAmount, double recommendedAmount, String unitPrefix) {
        Ingredient changedIngredient = new Ingredient(name, cost, currentAmount, criticalAmount, recommendedAmount, Unit.getUnitBasedOnPrefix(unitPrefix));
        databaseReference.child("Ingredient").child(key).setValueAsync(changedIngredient);
    }

    public void removeIngredientFromDatabase(String key) {
        databaseReference.child("Ingredient").child(key).removeValueAsync();
    }

    public void getIngredientsFromDatabase() {
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


                    ingredientValueList.add(dataSnapshot.child((String) mapElement.getKey()).getValue(Ingredient.class).toString((String) mapElement.getKey()));
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
    public void getRecipesFromDatabase() {
        databaseReference.child("Recipes").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                allRecipes.clear();
                for (DataSnapshot recept : dataSnapshot.getChildren()) {
                    Recipe rec = recept.getValue(Recipe.class);
                    allRecipes.add(rec);
                }

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                System.out.println("Fel i hämtning av recept");
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
    //</editor-fold>


    public static FirebaseDatabase getDatabase() {
        return database;
    }

    public static void setDatabase(FirebaseDatabase database) {
        Controller.database = database;
    }

    public static DatabaseReference getDatabaseReference() {
        return databaseReference;
    }

    public static void setDatabaseReference(DatabaseReference databaseReference) {
        Controller.databaseReference = databaseReference;
    }

    public static MainView getMainView() {
        return mainView;
    }

    public static void setMainView(MainView mainView) {
        Controller.mainView = mainView;
    }

    public OrderController getOrderController() {
        return orderController;
    }

    public void setOrderController(OrderController orderController) {
        this.orderController = orderController;
    }

    public DailyEvent getDailyEvent() {
        return dailyEvent;
    }

    public void setDailyEvent(DailyEvent dailyEvent) {
        this.dailyEvent = dailyEvent;
    }

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


}