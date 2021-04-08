package controller;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;
import model.home.Holiday;
import model.home.Note;
import view.HomePanel;

import javax.swing.*;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class HomeController {
    private Controller controller;
    private PropertyChangeSupport propertyChangeSupport = new PropertyChangeSupport(this);
    private HomePanel homePanel;

    private JList<Note> notesJList;
    private JList<Holiday> holidaysJList;
    private List<Note> noteList = new ArrayList<>();
    private List<Holiday> holidayList = new ArrayList<>();

    public HomeController(Controller controller) {
        this.controller = controller;
        getNotesFromDatabase();
        getHolidaysFromDatabase();
    }

    /**
     * reads the data (all notes) from the database where its stored
     *
     * @return
     */
    public Note[] getNotesFromDatabase() {
        Controller.databaseReference.child("Notes").addListenerForSingleValueEvent(new ValueEventListener() {
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
                    //  Controller.getMainView().getHomePanel().addNote(note);
                    addNote(note);
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                System.out.println(databaseError.toString());
            }
        });
        return null;
    }


    /**
     * reads the data (all the holidays) from the database where its stored
     *
     * @return
     */
    public Holiday[] getHolidaysFromDatabase() {
        Controller.databaseReference.child("Holiday").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                List<Object> allHolidaysList = (List<Object>) dataSnapshot.getValue();
                for (Object objectMap : allHolidaysList) {
                    if (objectMap == null)
                        continue;
                    HashMap<String, Object> map = (HashMap<String, Object>) objectMap;
                    String title = map.get("title").toString();
                    String desc = map.get("description").toString();
                    int id = Integer.parseInt(map.get("id").toString());
                    Holiday holiday = new Holiday(title, desc, id);
                    //  Controller.getMainView().getHomePanel().addNote(note);
                    addHoliday(holiday);
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                System.out.println(databaseError.toString());
            }
        });
        return null;
    }

    public void registerPropertyChangeListener(PropertyChangeListener listener) {
        propertyChangeSupport.addPropertyChangeListener(listener);
    }

    public void addNote(Note note) {
        noteList.add(note);
        Note[] newVal = new Note[noteList.size()];
        noteList.toArray(newVal);
        notesJList.setListData(newVal);
        Controller.databaseReference.child("Notes").child(noteList.size() + "").setValueAsync(note); // Sätter in värder i databasen
    }

    public void addHoliday(Holiday holiday) {
        holidayList.add(holiday);
        Holiday[] newVal = new Holiday[holidayList.size()];
        holidayList.toArray(newVal);
        holidaysJList.setListData(newVal);
        Controller.databaseReference.child("Holiday").child(holidayList.size() + "").setValueAsync(holiday); // Sätter in värder i databasen
    }


    public void setHomePanel(HomePanel homePanel) {
        this.homePanel = homePanel;
    }

    /**
     * returns the main controller
     *
     * @return controller
     */

    public Controller getController() {
        return controller;
    }

    /**
     * sets a new main controller
     *
     * @param controller new controller
     */
    public void setController(Controller controller) {
        this.controller = controller;
    }

}
