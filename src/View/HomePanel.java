package View;

import javax.swing.*;
import javax.swing.border.TitledBorder;
import javax.swing.text.DefaultCaret;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.security.PublicKey;

public class HomePanel extends JPanel implements ActionListener {
    private JPanel leftPanel;
    private JPanel rightPanel;
    private JTextPane notifications;
    private JTextPane notes;

    private JScrollPane listPane;
    private JScrollPane mainPane;
    private JButton submit;
    private JButton showSavedNotes;

    public HomePanel() {
        setLayout(new BorderLayout());
        createPanels();
    }

    public void createPanels() {
        rightPanel = new JPanel();
        leftPanel = new JPanel();

        //left panel
        leftPanel = new JPanel(new BorderLayout());
        leftPanel.setBorder(BorderFactory.createTitledBorder("Anteckningar"));

        notes = new JTextPane();
        notes.setPreferredSize(new Dimension(550, 600));
        notes.setFont(new Font("Times New Roman", Font.PLAIN, 14));
        notes.setEditable(true);
        ((DefaultCaret) notes.getCaret()).setUpdatePolicy(DefaultCaret.ALWAYS_UPDATE);

        mainPane = new JScrollPane(notes);
        leftPanel.add(mainPane, BorderLayout.CENTER);
        add(leftPanel, BorderLayout.CENTER);


        //right panel

        rightPanel = new JPanel(new BorderLayout());
        notifications = new JTextPane();
        rightPanel.setBorder(BorderFactory.createTitledBorder("Notifikationer"));
        // notifications.append("här visas alla notifikationer...");
        notifications.setPreferredSize(new Dimension(300, 600));
        notifications.setFont(new Font("Courier New", Font.PLAIN, 12));
        notifications.setEditable(false);


        listPane = new JScrollPane(notifications);
        listPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        listPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED);

        rightPanel.add(listPane, BorderLayout.EAST);
        add(rightPanel, BorderLayout.EAST);


        JPanel southPanel = new JPanel();

        submit = new JButton("Lägg till anteckningar");
        submit.addActionListener(this);
        southPanel.add(submit);

        add(southPanel, BorderLayout.SOUTH);


        showSavedNotes = new JButton("Visa alla anteckningar");
        showSavedNotes.addActionListener(this);
        southPanel.add(showSavedNotes);

        add(southPanel, BorderLayout.SOUTH);
    }

    // if the button is pressed
    public void actionPerformed(ActionEvent e) {

        if (e.getSource() == submit) {
            // TODO: fix the button
            JOptionPane.showMessageDialog(null,"Nya anteckningar har sparats");

        }
        if (e.getSource()==showSavedNotes){
            // FIXME:
            JOptionPane.showMessageDialog(null, "Dina anteckningar visas här...", "Alla anteckningar", JOptionPane.PLAIN_MESSAGE);

        }

    }
}
