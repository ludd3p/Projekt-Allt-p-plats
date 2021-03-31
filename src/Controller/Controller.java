package Controller;



import Model.*;
import View.MainView;
import com.google.auth.oauth2.GoogleCredentials;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import com.google.firebase.database.*;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.*;

public class Controller {
    private static FirebaseDatabase database;
    public static DatabaseReference databaseReference;
    static MainView mainView;

    public static void main(String[] args) throws IOException, InterruptedException {
        connectToFirebase();
        database = FirebaseDatabase.getInstance();
        databaseReference = database.getReference();
        Controller controller = new Controller();
        getNotesFromDatabase();
        mainView = new MainView(controller);
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

    public void addIngredientToDatabase(String name, double cost, double criticalAmount, double recommendedAmount, String unitPrefix){
        Ingredient ingredientToAddToDatabase = new Ingredient(name, cost, criticalAmount, recommendedAmount, Unit.getUnitBasedOnPrefix(unitPrefix));
        databaseReference.child("Ingredient").push().setValueAsync(ingredientToAddToDatabase);
    }

    public void changeProductInDatabase(String key, String newName, double cost, double criticalAmount, double recommendedAmount, String unitPrefix){
        Ingredient changedIngredient = new Ingredient(newName, cost, criticalAmount, recommendedAmount, Unit.getUnitBasedOnPrefix(unitPrefix));
        databaseReference.child("Ingredient").child(key).setValueAsync(changedIngredient);
    }

    public void removeIngredientFromDatabase(String key){
        databaseReference.child("Ingredient").child(key).removeValueAsync();
    }

    public void getIngredientsFromDatabase(){
        ArrayList<String> ingredientValueList = new ArrayList<>();

        databaseReference.child("Ingredient").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                HashMap<String, HashMap<String, Object>> ingredientMap = (HashMap<String, HashMap<String, Object>>) dataSnapshot.getValue();

                for (Map.Entry<String, HashMap<String, Object>> stringHashMapEntry : ingredientMap.entrySet()) {
                    Map.Entry mapElement = (Map.Entry) stringHashMapEntry;
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

    public RecipeIngredient createRecipeIngredient(Ingredient ingredient, double amount){
        return new RecipeIngredient(ingredient, amount);
    }

    public void createRecipe(String name, ArrayList<RecipeIngredient> ingredients, ArrayList<String> instructions){
        Recipe recipe = new Recipe(name, ingredients, instructions);
    }
}
