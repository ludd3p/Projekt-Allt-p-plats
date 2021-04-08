package controller;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;
import model.home.Holiday;
import model.home.Note;
import model.home.Notifications;
import view.HomePanel;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class HomeController {
    private Controller controller;
    private PropertyChangeSupport propertyChangeSupport = new PropertyChangeSupport(this);
    private HomePanel homePanel;

    private List<Note> noteList = new ArrayList<>();
    private List<Holiday> holidayList = new ArrayList<>();
    private List<Notifications> notificationslist = new ArrayList<>();

    public HomeController(Controller controller) {
        this.controller = controller;
        getNotesFromDatabase();
        getHolidaysFromDatabase();
        getNotificationsFromDatabase();
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
                    if (objectMap != null) {
                        HashMap<String, Object> map = (HashMap<String, Object>) objectMap;
                        String title = map.get("title").toString();
                        String desc = map.get("description").toString();
                        int id = Integer.parseInt(map.get("id").toString());
                        Note note = new Note(title, desc, id);
                        addNote(note);
                    }
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
                    if (objectMap != null) {
                        HashMap<String, Object> map = (HashMap<String, Object>) objectMap;
                        String title = map.get("title").toString();
                        String desc = map.get("description").toString();
                        int id = Integer.parseInt(map.get("id").toString());
                        Holiday holiday = new Holiday(title, desc, id);
                        addHoliday(holiday);
                    }
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
    public Notifications[] getNotificationsFromDatabase() {
        Controller.databaseReference.child("Notifications").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                List<Object> allNotificationsList = (List<Object>) dataSnapshot.getValue();
                for (Object objectMap : allNotificationsList) {
                    if (objectMap != null) {
                        HashMap<String, Object> map = (HashMap<String, Object>) objectMap;
                        String title = map.get("title").toString();
                        String desc = map.get("description").toString();
                        int id = Integer.parseInt(map.get("id").toString());
                        Notifications notifications = new Notifications(id, title, desc);
                        addNotification(notifications);
                    }
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
        homePanel.getNotesJList().setListData(newVal);
        Controller.databaseReference.child("Notes").child(noteList.size() + "").setValueAsync(note); // Sätter in värder i databasen
    }

    public void addHoliday(Holiday holiday) {
        holidayList.add(holiday);
        Holiday[] newVal = new Holiday[holidayList.size()];
        holidayList.toArray(newVal);
        homePanel.getHolidaysJList().setListData(newVal);
        Controller.databaseReference.child("Holidays").child(holidayList.size() + "").setValueAsync(holiday); // Sätter in värder i databasen
    }

    public void addNotification(Notifications notifications) {
        notificationslist.add(notifications);
        Notifications[] newVal = new Notifications[notificationslist.size()];
        notificationslist.toArray(newVal);
        homePanel.getNotificationsJList().setListData(newVal);
        Controller.databaseReference.child("Notifications").child(notificationslist.size() + "").setValueAsync(notifications); // Sätter in värder i databasen
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

    public PropertyChangeSupport getPropertyChangeSupport() {
        return propertyChangeSupport;
    }

    public void setPropertyChangeSupport(PropertyChangeSupport propertyChangeSupport) {
        this.propertyChangeSupport = propertyChangeSupport;
    }

    public HomePanel getHomePanel() {
        return homePanel;
    }

    public List<Note> getNoteList() {
        return noteList;
    }

    public void setNoteList(List<Note> noteList) {
        this.noteList = noteList;
    }

    public List<Holiday> getHolidayList() {
        return holidayList;
    }

    public void setHolidayList(List<Holiday> holidayList) {
        this.holidayList = holidayList;
    }

    public List<Notifications> getNotificationslist() {
        return notificationslist;
    }

    public void setNotificationslist(List<Notifications> notificationslist) {
        this.notificationslist = notificationslist;
    }


}
