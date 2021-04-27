/**
 * @Author Qassem Aburas
 * @Version 1.1
 */
package controller;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;
import model.home.Holiday;
import model.home.Note;
import model.home.Notifications;
import view.HomePanel;
import java.beans.PropertyChangeSupport;
import java.util.ArrayList;
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
     */
    public void getNotesFromDatabase() {
        Controller.databaseReference.child("Notes").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot d : dataSnapshot.getChildren()) {
                    Note note = d.getValue(Note.class);
                    getNoteList().add(note);
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                System.out.println(databaseError.toString());
            }
        });
    }


    /**
     * reads the data (all the holidays) from the database where its stored
     */
    public void getHolidaysFromDatabase() {
        Controller.databaseReference.child("Holidays").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot d : dataSnapshot.getChildren()) {
                    Holiday holiday = d.getValue(Holiday.class);
                    getHolidayList().add(holiday);
                }
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {
                System.out.println(databaseError.toString());
            }
        });
    }

    /**
     * reads the data (all the holidays) from the database where its stored
     */
    public void getNotificationsFromDatabase() {
        Controller.databaseReference.child("Notifications").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot d : dataSnapshot.getChildren()) {
                    Notifications notifications = d.getValue(Notifications.class);
                    getNotificationsList().add(notifications);
                }
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {
                System.out.println(databaseError.toString());
            }
        });
    }

    /**
     * adds the notes to the database and to the notes list in (UI)
     *
     * @param note
     */

    public void addNote(Note note) {
        noteList.add(note); // adds to list
        Controller.databaseReference.child("Notes").child(noteList.size() + "").setValueAsync(note); // adds to the database
        updateNoteViewer();
    }

    /**
     * Updates the notes in the database and UI
     */
    public void updateNoteViewer() {
        Note[] newVal = new Note[noteList.size()];
        noteList.toArray(newVal);
        homePanel.getNotesJList().setListData(newVal);
    }

    /**
     * adds the holidays to the database and the ui list
     *
     * @param holiday
     */
    public void addHoliday(Holiday holiday) {
        holidayList.add(holiday); //add to the list
        Controller.databaseReference.child("Holidays").child(holidayList.size() + "").setValueAsync(holiday); //add to the database
        updateHolidayViewer();
    }

    /**
     * Updates the holidays in the database and the list UI
     */
    public void updateHolidayViewer() {
        Holiday[] newVal = new Holiday[holidayList.size()];
        holidayList.toArray(newVal);
        homePanel.getHolidaysJList().setListData(newVal);
    }

    /**
     * adds the notifications to the database and the list (UI)
     *
     * @param notifications
     */
    public void addNotification(Notifications notifications) {
        notificationslist.add(notifications);// add to the list
        Controller.databaseReference.child("Notifications").child(notificationslist.size() + "").setValueAsync(notifications); //add to the database
        updateNotificationsViewer();
    }

    /**
     * Updates the notifications in the database and UI
     */
    private void updateNotificationsViewer() {
        Notifications[] newVal = new Notifications[notificationslist.size()];
        notificationslist.toArray(newVal);
        homePanel.getNotificationsJList().setListData(newVal);
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

    public List<Notifications> getNotificationsList() {
        return notificationslist;
    }

    public void setNotificationsList(List<Notifications> notificationslist) {
        this.notificationslist = notificationslist;
    }


    /**
     * This method is to delete the note from the list and the database
     *
     * @param selectedValue
     */
    public void removeNote(Note selectedValue) {
        Controller.getDatabaseReference().child("Notes").child(selectedValue.getId() + "").setValueAsync(null); // remove from the database
        getNoteList().remove(selectedValue); // remove from the list
        updateNoteViewer();// and then update the list
    }

    /**
     * This method is to delete the holidays from the list and the database
     *
     * @param selectedValue
     */
    public void removeHoliday(Holiday selectedValue) {
        Controller.getDatabaseReference().child("Holidays").child(selectedValue.getId() + "").setValueAsync(null); // remove from the database
        getHolidayList().remove(selectedValue); // remove from the list
        updateHolidayViewer(); // and then update the list
    }

    /**
     * This method is to delete the Notifications from the list and the database
     *
     * @param selectedValue
     */
    public void removeNotification(Notifications selectedValue) {
        Controller.getDatabaseReference().child("Notifications").child(selectedValue.getId() + "").setValueAsync(null); // remove from the database
        getNotificationsList().remove(selectedValue); // remove from the list
        updateNotificationsViewer();// and then update the list
    }

    public List<Notifications> getNotificationslist() {
        return notificationslist;
    }

    public void setNotificationslist(List<Notifications> notificationslist) {
        this.notificationslist = notificationslist;
    }
}
