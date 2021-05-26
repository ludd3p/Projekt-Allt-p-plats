package controller;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;
import model.home.Holiday;
import model.home.Note;
import model.home.Notifications;
import view.HomePanel;

import java.util.ArrayList;
import java.util.List;

/**
 * Controller class for handling everything about the home page like
 * (creating notes, creating holidays or showing all notifications of the system)
 *
 * @Author Qassem Aburas
 * @Version 1.2
 */
public class HomeController {
    private Controller controller;
    private HomePanel homePanel;
    private List<Note> noteList = new ArrayList<>();
    private List<Holiday> holidayList = new ArrayList<>();
    private List<Notifications> notificationslist = new ArrayList<>();

    /**
     * constructor the takes a controller object as a parameter and
     * gets the data from the database whenever the tab opens or the system starts.
     *
     * @param controller
     */
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
                    System.out.println(d.getValue());
                    Notifications notifications = d.getValue(Notifications.class);
                    System.out.println("HERE2");

                    getNotificationsList().add(notifications);
                    if (homePanel != null)
                        updateHolidayViewer();
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
        if (note == null)
            return;
        noteList.add(note); // adds to list
        Controller.databaseReference.child("Notes").child(noteList.size() + "").setValueAsync(note); // adds to the database
        updateNoteViewer();
    }

    /**
     * Updates the notes in the database and UI
     */
    public void updateNoteViewer() {
        homePanel.getNotesJList().setListData(noteList.toArray(new Note[0]));
    }

    /**
     * adds the holidays to the database and the ui list
     *
     * @param holiday
     */
    public void addHoliday(Holiday holiday) {
        if (holiday == null) // to avoid null data
            return;
        holidayList.add(holiday); //add to the list
        Controller.databaseReference.child("Holidays").child(holidayList.size() + "").setValueAsync(holiday); //add to the database
        updateHolidayViewer();
    }

    /**
     * Updates the holidays in the database and the list UI
     */
    public void updateHolidayViewer() {
        homePanel.getHolidaysJList().setListData(holidayList.toArray(new Holiday[0]));
    }

    /**
     * adds the notifications to the database and the list (UI)
     *
     * @param notifications
     */
    public void addNotification(Notifications notifications) {
        if (notifications == null)
            return;
        notificationslist.add(notifications);// add to the list
        Controller.databaseReference.child("Notifications").child(notifications.getId() + "").setValueAsync(notifications); //add to the database
        getHomePanel().getNotificationsJList().setListData(notificationslist.toArray(new Notifications[0]));
        updateNotificationsViewer();

    }

    /**
     * Updates the notifications in the database and UI
     */
    private void updateNotificationsViewer() {
        homePanel.getNotificationsJList().setListData(notificationslist.toArray(new Notifications[0]));
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

    public List<Notifications> getNotificationslist() {
        return notificationslist;
    }

    public void setNotificationslist(List<Notifications> notificationslist) {
        this.notificationslist = notificationslist;
    }

}
