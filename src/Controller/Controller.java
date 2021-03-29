package Controller;


import Model.Note;
import Model.Unit;
import Model.WeekDays;
import View.MainView;
import com.google.auth.oauth2.GoogleCredentials;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import com.google.firebase.database.*;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;

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
}
