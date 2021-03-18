package View;

import javax.swing.*;
import java.awt.*;

public class StoragePanel extends JPanel {
    private JTable table;
    private JScrollPane scrollPane;

    public StoragePanel(){
        setLayout(new BorderLayout());
        table = new JTable(1000,2);
        table.getColumnModel().getColumn(0).setHeaderValue("test");
        table.getColumnModel().getColumn(1).setHeaderValue("Mängd");
        scrollPane = new JScrollPane(table);
        add(scrollPane);
    }
}
