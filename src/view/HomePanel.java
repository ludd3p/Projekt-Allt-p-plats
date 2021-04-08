package view;

/**
 * Panel to handle and manage the notes and notifications in the homepage
 * @author Qassem Aburas
 * @version 1.1
 *
 */


import controller.Controller;
import controller.HomeController;
import model.home.Holiday;
import model.home.Note;
import model.home.Notifications;

import javax.swing.*;
import javax.swing.border.TitledBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class HomePanel extends JPanel implements ActionListener {
    private JPanel leftPanel;
    private JPanel rightPanel;
    private JPanel centerPanel;

    private JTextPane notifications;
    private JList<Note> notesJList;
    private JList<Holiday> holidaysJList;
    private JList<Notifications> notificationsJList;
    private JScrollPane listPane;
    private JButton submit;
    private JButton showSavedNotes;
    private JButton deleteNote;
    private JButton addNewHoliday;
    private JButton deleteHoliday;
    private JButton showSavedHolidays;
    private HomeController homeController;


    public HomePanel(HomeController homeController) {
        setLayout(new BorderLayout());
        this.homeController = homeController;
        homeController.setHomePanel(this);
        createPanels();
    }

    /**
     * This method creates all the panels for the homepage and inside the panels there is the buttons
     * and text area to write text in it.
     *
     */
    public void createPanels() {
        leftPanel = new JPanel();
        rightPanel = new JPanel();
        centerPanel = new JPanel();

        //left panel
        leftPanel = new JPanel(new BorderLayout());
        leftPanel.setBorder(BorderFactory.createTitledBorder("Anteckningar"));

        notesJList = new JList<>();
        notesJList.setPreferredSize(new Dimension(300, 600));
        notesJList.setFont(new Font("Times New Roman", Font.BOLD, 20));

        listPane = new JScrollPane(notesJList);
        listPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        listPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED);

        leftPanel.add(listPane, BorderLayout.WEST);
        add(leftPanel, BorderLayout.WEST);

        // center panel
        centerPanel = new JPanel(new BorderLayout());
        centerPanel.setBorder(BorderFactory.createTitledBorder("Högtider"));

        holidaysJList = new JList<>();
        holidaysJList.setPreferredSize(new Dimension(300, 600));
        holidaysJList.setFont(new Font("Times New Roman", Font.BOLD, 30));

        listPane = new JScrollPane(holidaysJList);
        listPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        listPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED);

        centerPanel.add(listPane, BorderLayout.CENTER);
        add(centerPanel, BorderLayout.CENTER);

        //right panel
        rightPanel = new JPanel(new BorderLayout());
        notifications = new JTextPane();
        rightPanel.setBorder(BorderFactory.createTitledBorder("Notifikationer"));
        notifications.setPreferredSize(new Dimension(300, 600));
        notifications.setFont(new Font("Courier New", Font.PLAIN, 12));
        notifications.setEditable(false);

        listPane = new JScrollPane(notifications);
        listPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        listPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED);

        rightPanel.add(listPane, BorderLayout.EAST);
        add(rightPanel, BorderLayout.EAST);

        // south panel
        JPanel southPanel = new JPanel();

        submit = new JButton("Skapa en ny anteckning");
        submit.addActionListener(this);
        southPanel.add(submit);
        add(southPanel, BorderLayout.SOUTH);

        showSavedNotes = new JButton("Visa/ändra en anteckning");
        showSavedNotes.addActionListener(this);
        southPanel.add(showSavedNotes);
        add(southPanel, BorderLayout.SOUTH);

        deleteNote = new JButton("ta bort en anteckning");
        deleteNote.addActionListener(this);
        southPanel.add(deleteNote);
        add(southPanel, BorderLayout.SOUTH);

        addNewHoliday = new JButton("Lägg till en högtid");
        addNewHoliday.addActionListener(this);
        southPanel.add(addNewHoliday);
        add(southPanel, BorderLayout.SOUTH);

        deleteHoliday = new JButton("ta bort en högtid");
        deleteHoliday.addActionListener(this);
        southPanel.add(deleteHoliday);
        add(southPanel, BorderLayout.SOUTH);

        showSavedHolidays = new JButton("visa/ändra högtid");
        showSavedHolidays.addActionListener(this);
        southPanel.add(showSavedHolidays);
        add(southPanel, BorderLayout.SOUTH);

    }

    /**
     * Whenever a button is pressed it will take action and listen
     * @param e Source
     */
    public void actionPerformed(ActionEvent e) {

        if (e.getSource() == submit) {
            new NewNoteJFrame();
        }
        if (e.getSource() == showSavedNotes) {
            if (notesJList.getSelectedValue() == null) {
                JOptionPane.showMessageDialog(null, "Du måste först välja en anteckning!!", "Error", JOptionPane.PLAIN_MESSAGE);
                return;
            }
            new NewNoteJFrame(notesJList.getSelectedValue());
        }
        if (e.getSource() == deleteNote) {
            // TODO: 2021-04-01
            homeController.getNoteList().remove(notesJList.getSelectedIndex());
        }
        if (e.getSource() == addNewHoliday) {
            new NewHolidayJFrame();

        }
        if (e.getSource() == deleteHoliday) {
            homeController.getHolidayList().remove(holidaysJList.getSelectedIndex());
        }
        if (e.getSource() == showSavedHolidays) {
            if (holidaysJList.getSelectedValue() == null) {
                JOptionPane.showMessageDialog(null, "Du måste först välja en högtid!", "Error", JOptionPane.PLAIN_MESSAGE);
                return;
            }
            new NewHolidayJFrame(holidaysJList.getSelectedValue());
        }

        // buttons for notifications???
    }



    class NewNoteJFrame {
        public NewNoteJFrame() {
            JFrame noteFrame = new JFrame();
            JPanel panel = new JPanel();

            noteFrame.setTitle("Ny anteckning");

            noteFrame.setSize(new Dimension(500, 500));
            panel.setLayout(new BorderLayout(10, 10));

            JPanel noteArea = new JPanel();
            noteArea.setBorder(new TitledBorder("Dina anteckningar"));
            JTextArea note = new JTextArea();
            note.setPreferredSize(new Dimension(480, 300));
            note.setEditable(true);
            note.setFont(new Font("Arial", Font.BOLD, 15));

            noteArea.add(note);

            JPanel controllArea = new JPanel();
            controllArea.setBorder(new TitledBorder("Control panel"));

            JTextField title = new JTextField();
            title.setPreferredSize(new Dimension(340, 80));
            title.setFont(new Font("Arial", Font.BOLD, 20));
            title.setBorder(new TitledBorder("Title"));

            JButton button = new JButton("Spara");
            button.setPreferredSize(new Dimension(120, 80));
            button.addActionListener(e -> {
                if (note.getText() == null || note.getText().equals("")) {
                    JOptionPane.showMessageDialog(null, "Du måste skriva en något i notes!", "Error", JOptionPane.PLAIN_MESSAGE);
                    return;
                }
                if (title.getText() == null || title.getText().equals("")) {
                    JOptionPane.showMessageDialog(null, "Du måste skriva en title!!", "Error", JOptionPane.PLAIN_MESSAGE);
                    return;
                }
                Note noteO = new Note(title.getText(), note.getText(), homeController.getNoteList().size() + 1);
                homeController.addNote(noteO);
                noteFrame.setVisible(false);
            });
            controllArea.add(title);
            controllArea.add(button);
            panel.add(noteArea, BorderLayout.NORTH);
            panel.add(controllArea, BorderLayout.SOUTH);

            noteFrame.setContentPane(panel);
            noteFrame.setResizable(false);
            noteFrame.setVisible(true);
        }

        public NewNoteJFrame(Note noteToShow) {
            JFrame noteFrame = new JFrame();
            JPanel panel = new JPanel();

            noteFrame.setTitle("Edit | Show Note: " + noteToShow.getTitle());

            noteFrame.setSize(new Dimension(500, 500));
            panel.setLayout(new BorderLayout(10, 10));

            JPanel noteArea = new JPanel();
            noteArea.setBorder(new TitledBorder("Your note"));
            JTextArea note = new JTextArea(noteToShow.getDescription());
            note.setPreferredSize(new Dimension(480, 300));
            note.setEditable(true);
            note.setFont(new Font("Arial", Font.BOLD, 15));

            noteArea.add(note);

            JPanel controllArea = new JPanel();
            controllArea.setBorder(new TitledBorder("Control panel"));

            JTextField title = new JTextField(noteToShow.getTitle());
            title.setPreferredSize(new Dimension(340, 80));
            title.setFont(new Font("Arial", Font.BOLD, 20));
            title.setBorder(new TitledBorder("Title"));

            JButton button = new JButton("Save");
            button.setPreferredSize(new Dimension(120, 80));
            button.addActionListener(e -> {
                if (note.getText() == null || note.getText().equals("")) {
                    JOptionPane.showMessageDialog(null, "Du måste skriva en något i notes!", "Error", JOptionPane.PLAIN_MESSAGE);
                    return;
                }
                if (title.getText() == null || title.getText().equals("")) {
                    JOptionPane.showMessageDialog(null, "Du måste skriva en title!!", "Error", JOptionPane.PLAIN_MESSAGE);
                    return;
                }
                homeController.getNoteList().remove(noteToShow);
                Note noteO = new Note(title.getText(), note.getText(), homeController.getNoteList().size() + 1);
                homeController.addNote(noteO);
                noteFrame.setVisible(false);
            });
            controllArea.add(title);
            controllArea.add(button);
            panel.add(noteArea, BorderLayout.NORTH);
            panel.add(controllArea, BorderLayout.SOUTH);

            noteFrame.setContentPane(panel);
            noteFrame.setResizable(false);
            noteFrame.setVisible(true);
        }

    }

    class NewHolidayJFrame {
        public NewHolidayJFrame() {
            JFrame HolidayFrame = new JFrame();
            JPanel panel = new JPanel();

            HolidayFrame.setTitle("Lägg ny högtid");

            HolidayFrame.setSize(new Dimension(500, 500));
            panel.setLayout(new BorderLayout(10, 10));

            JPanel HolidayArea = new JPanel();
            HolidayArea.setBorder(new TitledBorder("Högtider"));
            JTextArea holiday = new JTextArea();
            holiday.setPreferredSize(new Dimension(480, 300));
            holiday.setEditable(true);
            holiday.setFont(new Font("Arial", Font.BOLD, 15));

            HolidayArea.add(holiday);

            JPanel controllArea = new JPanel();
            controllArea.setBorder(new TitledBorder("Control panel"));

            JTextField title = new JTextField();
            title.setPreferredSize(new Dimension(340, 80));
            title.setFont(new Font("Arial", Font.BOLD, 20));
            title.setBorder(new TitledBorder("namn och datum"));

            JButton button = new JButton("Spara");
            button.setPreferredSize(new Dimension(120, 80));
            button.addActionListener(e -> {
                if (holiday.getText() == null || holiday.getText().equals("")) {
                    JOptionPane.showMessageDialog(null, "Du måste skriva om en högtid!", "Error", JOptionPane.PLAIN_MESSAGE);
                    return;
                }
                if (title.getText() == null || title.getText().equals("")) {
                    JOptionPane.showMessageDialog(null, "Du måste skriva ett namn och datum!!", "Error", JOptionPane.PLAIN_MESSAGE);
                    return;
                }
                Holiday holiday1 = new Holiday(title.getText(), holiday.getText(), homeController.getHolidayList().size() + 1);
                homeController.addHoliday(holiday1);
                HolidayFrame.setVisible(false);
            });
            controllArea.add(title);
            controllArea.add(button);
            panel.add(HolidayArea, BorderLayout.NORTH);
            panel.add(controllArea, BorderLayout.SOUTH);

            HolidayFrame.setContentPane(panel);
            HolidayFrame.setResizable(false);
            HolidayFrame.setVisible(true);
        }


        public NewHolidayJFrame(Holiday showHoliday) {
            JFrame holidayFrame = new JFrame();
            JPanel panel = new JPanel();

            holidayFrame.setTitle("Ändra | Visa högtiden: " + showHoliday.getName());

            holidayFrame.setSize(new Dimension(500, 500));
            panel.setLayout(new BorderLayout(10, 10));

            JPanel holidayArea = new JPanel();
            holidayArea.setBorder(new TitledBorder("Högtider"));
            JTextArea holiday = new JTextArea(showHoliday.getDescription());
            holiday.setPreferredSize(new Dimension(480, 300));
            holiday.setEditable(true);
            holiday.setFont(new Font("Arial", Font.BOLD, 15));

            holidayArea.add(holiday);

            JPanel controllArea = new JPanel();
            controllArea.setBorder(new TitledBorder("Control panel"));

            JTextField title = new JTextField(showHoliday.getName());
            title.setPreferredSize(new Dimension(340, 80));
            title.setFont(new Font("Arial", Font.BOLD, 20));
            title.setBorder(new TitledBorder("Namn"));

            JButton button = new JButton("Spara");
            button.setPreferredSize(new Dimension(120, 80));
            button.addActionListener(e -> {
                if (holiday.getText() == null || holiday.getText().equals("")) {
                    JOptionPane.showMessageDialog(null, "Du måste skriva någon högtid!", "Error", JOptionPane.PLAIN_MESSAGE);
                    return;
                }
                if (title.getText() == null || title.getText().equals("")) {
                    JOptionPane.showMessageDialog(null, "Högtiden måste ha ett namn!", "Error", JOptionPane.PLAIN_MESSAGE);
                    return;
                }
                homeController.getNoteList().remove(showHoliday);
                Holiday holiday1 = new Holiday(title.getText(), holiday.getText(), homeController.getHolidayList().size() + 1);
                homeController.addHoliday(holiday1);
                holidayFrame.setVisible(false);
            });
            controllArea.add(title);
            controllArea.add(button);
            panel.add(holidayArea, BorderLayout.NORTH);
            panel.add(controllArea, BorderLayout.SOUTH);

            holidayFrame.setContentPane(panel);
            holidayFrame.setResizable(false);
            holidayFrame.setVisible(true);
        }
    }


    public JPanel getLeftPanel() {
        return leftPanel;
    }

    public void setLeftPanel(JPanel leftPanel) {
        this.leftPanel = leftPanel;
    }

    public JPanel getRightPanel() {
        return rightPanel;
    }

    public void setRightPanel(JPanel rightPanel) {
        this.rightPanel = rightPanel;
    }

    public JPanel getCenterPanel() {
        return centerPanel;
    }

    public void setCenterPanel(JPanel centerPanel) {
        this.centerPanel = centerPanel;
    }

    public JTextPane getNotifications() {
        return notifications;
    }

    public void setNotifications(JTextPane notifications) {
        this.notifications = notifications;
    }

    public JList<Note> getNotesJList() {
        return notesJList;
    }

    public void setNotesJList(JList<Note> notesJList) {
        this.notesJList = notesJList;
    }

    public JList<Holiday> getHolidaysJList() {
        return holidaysJList;
    }

    public void setHolidaysJList(JList<Holiday> holidaysJList) {
        this.holidaysJList = holidaysJList;
    }

    public JScrollPane getListPane() {
        return listPane;
    }

    public void setListPane(JScrollPane listPane) {
        this.listPane = listPane;
    }

    public JButton getSubmit() {
        return submit;
    }

    public void setSubmit(JButton submit) {
        this.submit = submit;
    }

    public JButton getShowSavedNotes() {
        return showSavedNotes;
    }

    public void setShowSavedNotes(JButton showSavedNotes) {
        this.showSavedNotes = showSavedNotes;
    }

    public JButton getDeleteNote() {
        return deleteNote;
    }

    public void setDeleteNote(JButton deleteNote) {
        this.deleteNote = deleteNote;
    }

    public JButton getAddNewHoliday() {
        return addNewHoliday;
    }

    public void setAddNewHoliday(JButton addNewHoliday) {
        this.addNewHoliday = addNewHoliday;
    }

    public JButton getDeleteHoliday() {
        return deleteHoliday;
    }

    public void setDeleteHoliday(JButton deleteHoliday) {
        this.deleteHoliday = deleteHoliday;
    }

    public JButton getShowSavedHolidays() {
        return showSavedHolidays;
    }

    public void setShowSavedHolidays(JButton showSavedHolidays) {
        this.showSavedHolidays = showSavedHolidays;
    }

    public HomeController getHomeController() {
        return homeController;
    }

    public void setHomeController(HomeController homeController) {
        this.homeController = homeController;
    }

    public JList<Notifications> getNotificationsJList() {
        return notificationsJList;
    }

    public void setNotificationsJList(JList<Notifications> notificationsJList) {
        this.notificationsJList = notificationsJList;
    }


}
