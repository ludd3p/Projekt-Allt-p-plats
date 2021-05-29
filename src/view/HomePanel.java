package view;

import controller.HomeController;
import model.home.Holiday;
import model.home.Note;
import model.home.Notifications;
import org.jdesktop.swingx.JXDatePicker;

import javax.swing.*;
import javax.swing.border.TitledBorder;
import java.awt.*;
import java.awt.event.*;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

/**
 * Panel to handle and manage the notes and notifications in the homepage
 *
 * @author Qassem Aburas
 * @version 1.1
 */

public class HomePanel extends JPanel implements ActionListener {
    private JPanel leftPanel;
    private JPanel rightPanel;
    private JPanel centerPanel;
    private JList<Note> notesJList;
    private JList<Holiday> holidaysJList;
    private JList<Notifications> notificationsJList;
    private JScrollPane listPane;
    private JButton addNewNote;
    private JButton showSavedNotes;
    private JButton deleteNote;
    private JButton addNewHoliday;
    private JButton deleteHoliday;
    private JButton showSavedHolidays;
    private HomeController homeController;
    private NewHolidayJFrame currentHJ;
    private NewNoteJFrame currentNJ;

    public HomePanel(HomeController homeController) {
        setLayout(new BorderLayout());
        this.homeController = homeController;
        homeController.setHomePanel(this);
        createPanels();
    }

    /**
     * This method creates all the panels for the homepage and inside the panels there is the buttons
     * and text area to write text in it.
     */
    public void createPanels() {
        leftPanel = new JPanel();
        centerPanel = new JPanel();
        rightPanel = new JPanel();

        //left panel
        leftPanel = new JPanel(new BorderLayout());
        leftPanel.setBorder(BorderFactory.createTitledBorder("Anteckningar"));

        notesJList = new JList<>(homeController.getNoteList().toArray(new Note[0]));
        notesJList.setPreferredSize(new Dimension(200, 600));
        notesJList.setFont(new Font("Times New Roman", Font.BOLD, 20));
        notesJList.addMouseListener(new MouseListener() {
            int index = -1;

            @Override
            public void mouseClicked(MouseEvent e) {
                if (currentNJ != null)
                    return;
                if (notesJList.getSelectedIndex() == index) {
                    new NewNoteJFrame(notesJList.getSelectedValue());
                }
                index = notesJList.getSelectedIndex();
            }

            @Override
            public void mousePressed(MouseEvent e) {

            }

            @Override
            public void mouseReleased(MouseEvent e) {

            }

            @Override
            public void mouseEntered(MouseEvent e) {

            }

            @Override
            public void mouseExited(MouseEvent e) {

            }
        });

        listPane = new JScrollPane(notesJList);
        listPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        listPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED);

        leftPanel.add(listPane, BorderLayout.WEST);
        add(leftPanel, BorderLayout.WEST);

        // center panel
        centerPanel = new JPanel(new BorderLayout());
        centerPanel.setBorder(BorderFactory.createTitledBorder("Högtider"));

        holidaysJList = new JList<>(homeController.getHolidayList().toArray(new Holiday[0]));
        holidaysJList.setPreferredSize(new Dimension(200, 600));
        holidaysJList.setFont(new Font("Times New Roman", Font.BOLD, 20));
        holidaysJList.addMouseListener(new MouseListener() {
            int index = -1;

            @Override
            public void mouseClicked(MouseEvent e) {
                if (currentHJ != null)
                    return;
                if (holidaysJList.getSelectedIndex() == index) {
                    new NewHolidayJFrame(holidaysJList.getSelectedValue());
                }
                index = holidaysJList.getSelectedIndex();

            }

            @Override
            public void mousePressed(MouseEvent e) {

            }

            @Override
            public void mouseReleased(MouseEvent e) {

            }

            @Override
            public void mouseEntered(MouseEvent e) {

            }

            @Override
            public void mouseExited(MouseEvent e) {

            }
        });

        listPane = new JScrollPane(holidaysJList);
        listPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        listPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED);


        centerPanel.add(listPane, BorderLayout.CENTER);
        add(centerPanel, BorderLayout.CENTER);

        //right panel
        rightPanel = new JPanel(new BorderLayout());
        notificationsJList = new JList<>(homeController.getNotificationsList().toArray(new Notifications[0]));
        rightPanel.setBorder(BorderFactory.createTitledBorder("Notifikationer"));
        notificationsJList.setPreferredSize(new Dimension(200, 500));
        ((DefaultListCellRenderer) notificationsJList.getCellRenderer()).setHorizontalAlignment(JLabel.CENTER);
        notificationsJList.setFont(new Font("Times New Roman", Font.PLAIN, 12));
        notificationsJList.addKeyListener(new KeyListener() {
            @Override
            public void keyTyped(KeyEvent e) {

            }

            @Override
            public void keyPressed(KeyEvent e) {
                if (notificationsJList.getSelectedValue() == null)
                    return;
                if (e.getKeyCode() == 10 || e.getKeyCode() == 127)
                    homeController.removeNotification(notificationsJList.getSelectedValue());

            }

            @Override
            public void keyReleased(KeyEvent e) {

            }
        });
        listPane = new JScrollPane(notificationsJList);
        listPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        listPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED);

        rightPanel.add(listPane, BorderLayout.EAST);
        add(rightPanel, BorderLayout.EAST);

        // south panel
        JPanel southPanel = new JPanel();

        addNewNote =new JButton("Skapa en ny anteckning");
        addNewNote.addActionListener(this);
        southPanel.add(addNewNote);
        add(southPanel, BorderLayout.SOUTH);


        add(southPanel, BorderLayout.SOUTH);


        add(southPanel, BorderLayout.SOUTH);

        addNewHoliday = new JButton("Lägg till en högtid");
        addNewHoliday.addActionListener(this);
        southPanel.add(addNewHoliday);
        add(southPanel, BorderLayout.SOUTH);


        add(southPanel, BorderLayout.SOUTH);

        add(southPanel, BorderLayout.SOUTH);

    }

    /**
     * Whenever a button is pressed it will take action and listen
     *
     * @param e Source
     */
    public void actionPerformed(ActionEvent e) {

        if (e.getSource() == addNewNote)
            new NewNoteJFrame();

        if (e.getSource() == showSavedNotes) {
            if (notesJList.getSelectedValue() == null) {
                JOptionPane.showMessageDialog(null, "Du måste först välja en anteckning!!", "Error", JOptionPane.PLAIN_MESSAGE);
                return;
            }
            new NewNoteJFrame(notesJList.getSelectedValue());
        }

        if (e.getSource() == deleteNote) {
            if (notesJList.getSelectedValue() == null) {
                JOptionPane.showMessageDialog(null, "Du måste först välja en anteckning!!", "Error", JOptionPane.PLAIN_MESSAGE);
                return;
            }
            if (JOptionPane.showConfirmDialog(null, "Är du säker på att du vill ta bort den anteckning?", "Bekräfta borttagningen",
                    JOptionPane.YES_NO_OPTION) == 0)
                homeController.removeNote(notesJList.getSelectedValue());
            else
                JOptionPane.showMessageDialog(null, "Borttagningen genömfördes inte", "Meddelande", JOptionPane.PLAIN_MESSAGE);
        }

        if (e.getSource() == addNewHoliday)
            new NewHolidayJFrame();

        if (e.getSource() == deleteHoliday) {
            if (holidaysJList.getSelectedValue() == null) {
                JOptionPane.showMessageDialog(null, "Du måste först välja en högtid!!", "Error", JOptionPane.PLAIN_MESSAGE);
                return;
            }
            if (JOptionPane.showConfirmDialog(null, "Är du säker på att du vill ta bort den Högtiden?", "Bekräfta borttagningen",
                    JOptionPane.YES_NO_OPTION) == 0) {
                homeController.removeHoliday(holidaysJList.getSelectedValue());
                System.out.println("HERE");

            } else
                JOptionPane.showMessageDialog(null, "Borttagningen genömfördes inte", "Meddelande", JOptionPane.PLAIN_MESSAGE);
        }


        if (e.getSource() == showSavedHolidays) {
            if (holidaysJList.getSelectedValue() == null) {
                JOptionPane.showMessageDialog(null, "Du måste först välja en högtid!", "Error", JOptionPane.PLAIN_MESSAGE);
                return;
            }
            new NewHolidayJFrame(holidaysJList.getSelectedValue());
        }
    }

    class NewNoteJFrame {
        public NewNoteJFrame() {
            currentNJ = this;
            JFrame noteFrame = new JFrame();
            JPanel panel = new JPanel();
            noteFrame.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);

            noteFrame.setTitle("Ny anteckning");

            noteFrame.setSize(new Dimension(500, 500));
            panel.setLayout(new BorderLayout(10, 10));

            JPanel noteArea = new JPanel();
            noteArea.setBorder(new TitledBorder("Dina anteckningar"));
            JTextArea note = new JTextArea();
            note.setPreferredSize(new Dimension(480, 300));
            note.setEditable(true);
            note.setFont(new Font("Times New Roman", Font.BOLD, 20));

            noteArea.add(note);

            JPanel controlArea = new JPanel();
            controlArea.setBorder(new TitledBorder("Control panel"));

            JTextField title = new JTextField();
            title.setPreferredSize(new Dimension(340, 80));
            title.setFont(new Font("Times New Roman", Font.BOLD, 20));
            title.setBorder(new TitledBorder("Title"));

            JButton button = new JButton("Spara");
            button.setPreferredSize(new Dimension(120, 80));
            button.addActionListener(e -> {
                if (note.getText() == null || note.getText().equals("")) {
                    JOptionPane.showMessageDialog(null, "Du måste skriva något i anteckningar!", "Error", JOptionPane.PLAIN_MESSAGE);
                    return;
                }
                if (title.getText() == null || title.getText().equals("")) {
                    JOptionPane.showMessageDialog(null, "Du måste skriva en title!!", "Error", JOptionPane.PLAIN_MESSAGE);
                    return;
                }
                Note noteO = new Note(title.getText(), note.getText(), homeController.getNoteList().size() + 1);
                homeController.addNote(noteO);
                currentNJ = null;
                noteFrame.setVisible(false);
            });
            controlArea.add(title);
            controlArea.add(button);
            panel.add(noteArea, BorderLayout.NORTH);
            panel.add(controlArea, BorderLayout.SOUTH);

            noteFrame.setContentPane(panel);
            noteFrame.setResizable(false);
            noteFrame.setVisible(true);
        }

        public NewNoteJFrame(Note noteToShow) {
            currentNJ = this;
            JFrame noteFrame = new JFrame();
            JPanel panel = new JPanel();

            noteFrame.setTitle("Ändra | Visa anteckningen: " + noteToShow.getTitle());

            noteFrame.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);


            noteFrame.setSize(new Dimension(500, 500));
            panel.setLayout(new BorderLayout(10, 10));

            JPanel noteArea = new JPanel();
            noteArea.setBorder(new TitledBorder("Dina anteckningar"));
            JTextArea note = new JTextArea(noteToShow.getDescription());
            note.setPreferredSize(new Dimension(480, 300));
            note.setEditable(true);
            note.setFont(new Font("Times New Roman", Font.BOLD, 20));

            noteArea.add(note);

            JPanel controlArea = new JPanel();
            controlArea.setBorder(new TitledBorder("Control panel"));

            JTextField title = new JTextField(noteToShow.getTitle());
            title.setPreferredSize(new Dimension(210, 60));
            title.setFont(new Font("Times New Roman", Font.BOLD, 20));
            title.setBorder(new TitledBorder("Titel"));
            title.setBackground(null);

            JButton button = new JButton("Spara");
            button.setPreferredSize(new Dimension(120, 60));
            button.addActionListener(e -> {
                if (note.getText() == null || note.getText().equals("")) {
                    JOptionPane.showMessageDialog(null, "Du måste skriva något i anteckningar!", "Error", JOptionPane.PLAIN_MESSAGE);
                    return;
                }
                if (title.getText() == null || title.getText().equals("")) {
                    JOptionPane.showMessageDialog(null, "Du måste skriva en title till den anteckning !!", "Error", JOptionPane.PLAIN_MESSAGE);
                    return;
                }
                currentNJ = null;
                Note n = new Note(title.getText(), note.getText(), noteToShow.getId());
                homeController.removeNote(noteToShow);
                homeController.addNote(n);
                homeController.updateNoteViewer();
                noteFrame.setVisible(false);
            });
            JButton remove = new JButton("Ta bort");
            remove.setPreferredSize(new Dimension(120, 60));
            remove.addActionListener(e -> {
                if (note.getText() == null || note.getText().equals("")) {
                    JOptionPane.showMessageDialog(null, "Du måste skriva något i anteckningar!", "Error", JOptionPane.PLAIN_MESSAGE);
                    return;
                }
                if (title.getText() == null || title.getText().equals("")) {
                    JOptionPane.showMessageDialog(null, "Du måste skriva en title till den anteckning !!", "Error", JOptionPane.PLAIN_MESSAGE);
                    return;
                }
                currentNJ = null;
                if (JOptionPane.showConfirmDialog(null, "Är du säker på att du vill ta bort den?", "Bekräfta borttagningen",
                        JOptionPane.YES_NO_OPTION) == 0)
                    homeController.getNoteList().remove(noteToShow);
                else
                    JOptionPane.showMessageDialog(null, "Borttagningen genömfördes inte", "Meddelande", JOptionPane.PLAIN_MESSAGE);
                homeController.removeNote(noteToShow);
                homeController.updateNoteViewer();
                noteFrame.setVisible(false);
            });
            controlArea.add(title);
            controlArea.add(button);
            controlArea.add(remove);
            panel.add(noteArea, BorderLayout.NORTH);
            panel.add(controlArea, BorderLayout.SOUTH);

            noteFrame.setContentPane(panel);
            noteFrame.setResizable(false);
            noteFrame.setVisible(true);
        }
    }

    class NewHolidayJFrame {
        public NewHolidayJFrame() {

            currentHJ = this;
            JFrame HolidayFrame = new JFrame();
            JPanel panel = new JPanel();


            HolidayFrame.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);

            HolidayFrame.setTitle("Ny högtid");
            HolidayFrame.setSize(new Dimension(300, 200));
            panel.setLayout(new BorderLayout(10, 10));

            JPanel HolidayArea = new JPanel();
            HolidayArea.setBorder(new TitledBorder("Titel"));
            JTextArea title = new JTextArea();
            title.setPreferredSize(new Dimension(287, 65));
            title.setEditable(true);
            title.setFont(new Font("Times New Roman", Font.BOLD, 20));
            HolidayArea.add(title);
            JPanel controlArea = new JPanel();
            controlArea.setBorder(new TitledBorder("Välj datum"));

            JXDatePicker picker = new JXDatePicker();
            picker.setDate(Calendar.getInstance().getTime());
            picker.setPreferredSize(new Dimension(120, 35));
            picker.setFormats(new SimpleDateFormat("dd.MM.yyyy"));

            JButton button = new JButton("Spara");
            button.setPreferredSize(new Dimension(90, 35));
            button.addActionListener(e -> {
                if (title.getText() == null || title.getText().equals("")) {
                    JOptionPane.showMessageDialog(null, "Du måste skriva en titel!", "Error", JOptionPane.PLAIN_MESSAGE);
                    return;
                }
                Holiday title1 = new Holiday(title.getText(), picker.getDate(), homeController.getHolidayList().size() + 1);
                currentHJ = null;
                homeController.addHoliday(title1);
                HolidayFrame.setVisible(false);
            });
            controlArea.add(picker);
            controlArea.add(button);
            panel.add(HolidayArea, BorderLayout.NORTH);
            panel.add(controlArea, BorderLayout.SOUTH);

            HolidayFrame.setContentPane(panel);
            HolidayFrame.setResizable(false);
            HolidayFrame.setVisible(true);
        }

        public NewHolidayJFrame(Holiday showHoliday) {
            currentHJ = this;
            JFrame titleFrame = new JFrame();
            titleFrame.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
            JPanel panel = new JPanel();


            titleFrame.setTitle("Ändra | Visa högtiden: " + showHoliday.getName());

            titleFrame.setSize(new Dimension(400, 200));
            panel.setLayout(new BorderLayout(10, 10));

            JPanel titleArea = new JPanel();
            titleArea.setBorder(new TitledBorder("Högtider"));
            JTextArea title = new JTextArea(showHoliday.getName());
            title.setPreferredSize(new Dimension(300, 30));
            title.setEditable(true);
            title.setFont(new Font("Times New Roman", Font.BOLD, 20));

            titleArea.add(title);

            JPanel controlArea = new JPanel();
            controlArea.setBorder(new TitledBorder("Control panel"));

            JXDatePicker datePicker = new JXDatePicker(Locale.forLanguageTag(showHoliday.getDate()));
            datePicker.setPreferredSize(new Dimension(165, 55));
            datePicker.setFont(new Font("Times New Roman", Font.BOLD, 16));
            datePicker.setBorder(new TitledBorder("Datum"));

            JButton button = new JButton("Spara");
            button.setPreferredSize(new Dimension(90, 35));
            button.addActionListener(e -> {
                if (title.getText() == null || title.getText().equals("") || datePicker.getDate() == null) {
                    JOptionPane.showMessageDialog(null, "Högtiden måste ha ett namn!", "Error", JOptionPane.PLAIN_MESSAGE);
                    return;
                }
                homeController.removeHoliday(showHoliday);
                Holiday title1 = new Holiday(title.getText(), datePicker.getDate(), showHoliday.getId());
                homeController.addHoliday(title1);
                homeController.updateHolidayViewer();
                currentHJ = null;
                titleFrame.setVisible(false);
            });
            JButton remove = new JButton("Ta bort");
            remove.setPreferredSize(new Dimension(90, 35));
            remove.addActionListener(e -> {
                if (title.getText() == null || title.getText().equals("") || datePicker.getDate() == null) {
                    JOptionPane.showMessageDialog(null, "Högtiden måste ha ett namn!", "Error", JOptionPane.PLAIN_MESSAGE);
                    return;
                }
                if (JOptionPane.showConfirmDialog(null, "Är du säker på att du vill ta bort den?", "Bekräfta borttagningen",
                        JOptionPane.YES_NO_OPTION) == 0)
                    homeController.getHolidayList().remove(showHoliday);
                else
                    JOptionPane.showMessageDialog(null, "Borttagningen genömfördes inte", "Meddelande", JOptionPane.PLAIN_MESSAGE);
                homeController.removeHoliday(showHoliday);
                homeController.updateHolidayViewer();
                currentHJ = null;
                titleFrame.setVisible(false);
            });
            controlArea.add(datePicker);
            controlArea.add(button);
            controlArea.add(remove);
            panel.add(titleArea, BorderLayout.NORTH);
            panel.add(controlArea, BorderLayout.SOUTH);

            titleFrame.setContentPane(panel);
            titleFrame.setResizable(false);
            titleFrame.setVisible(true);
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

    public JButton getaddNewNote() {
        return addNewNote;
    }

    public void setaddNewNote(JButton addNewNote) {
        this.addNewNote = addNewNote;
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
