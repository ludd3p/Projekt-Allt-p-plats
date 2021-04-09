package controller;


import com.google.auth.oauth2.GoogleCredentials;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import com.google.firebase.database.*;
import model.Unit;
import model.daily.DailyEvent;
import model.ingredient.Ingredient;
import view.MainView;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Controller {
    public static DatabaseReference databaseReference;
    private static FirebaseDatabase database;
    private MainView mainView;
    private OrderController orderController;
    private StorageController storageController;
    private RecipeController recipeController;
    private SupplierController supplierController;
    private DailyEvent dailyEvent;
    private HomeController homeController;


    public Controller() throws IOException, InterruptedException {
        connectToFirebase();
        dailyEvent = new DailyEvent();
        recipeController = new RecipeController(this, databaseReference);
        storageController = new StorageController(this);
        orderController = new OrderController(this);
        supplierController = new SupplierController(this, databaseReference);
        homeController = new HomeController(this);
        System.out.println("Hämtar data från firebase!");
        Thread.sleep(5000);
        System.out.println("Data hämtad!");
        mainView = new MainView(this);
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

    public MainView getMainView() {
        return mainView;
    }

    public void setMainView(MainView mainView) {
        this.mainView = mainView;
    }

    public RecipeController getRecipeController() {
        return recipeController;
    }

    public void setRecipeController(RecipeController recipeController) {
        this.recipeController = recipeController;
    }

    public OrderController getOrderController() {
        return orderController;
    }

    public void setOrderController(OrderController orderController) {
        this.orderController = orderController;
    }

    public StorageController getStorageController() {
        return storageController;
    }

    public SupplierController getSupplierController() {
        return supplierController;
    }

    public void setSupplierController(SupplierController supplierController) {
        this.supplierController = supplierController;
    }

    public DailyEvent getDailyEvent() {
        return dailyEvent;
    }

    public void setDailyEvent(DailyEvent dailyEvent) {
        this.dailyEvent = dailyEvent;
    }


    public void setStorageController(StorageController storageController) {
        this.storageController = storageController;
    }

    public HomeController getHomeController() {
        return homeController;
    }

    public void setHomeController(HomeController homeController) {
        this.homeController = homeController;
    }


}
