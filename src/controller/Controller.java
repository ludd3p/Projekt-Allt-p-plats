package controller;


import com.google.auth.oauth2.GoogleCredentials;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import view.MainView;

import java.io.FileInputStream;
import java.io.IOException;

/**
 * Controller class for handling everything about the home page like
 * (creating notes, creating holidays or showing all notifications of the system)
 *
 * @Author Qassem Aburas / Hazem Elkhalil / Alex Bergenholtz  / Jonathan Engström / Ludvig Wedin Pettersson
 * @Version 3
 */

public class Controller {
    public static DatabaseReference databaseReference;
    private static FirebaseDatabase database;
    private MainView mainView;
    private OrderController orderController;
    private StorageController storageController;
    private RecipeController recipeController;
    private SupplierController supplierController;
    private HomeController homeController;


    public Controller() throws IOException, InterruptedException {
        Controller controller = this;
        System.out.println("Kopplar till firebase!");
        connectToFirebase();
        System.out.println("Kopplat till firebase!");
        System.out.println("Hämtar data från firebase!");
        new Thread() {
            @Override
            public void run() {
                super.run();
                supplierController = new SupplierController(controller, databaseReference);
                storageController = new StorageController(controller);
                recipeController = new RecipeController(controller, databaseReference);
                homeController = new HomeController(controller);
                orderController = new OrderController(controller);
            }
        }.start();
        Thread.sleep(5000);
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
