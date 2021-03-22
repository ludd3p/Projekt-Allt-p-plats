package View;

import Model.Supplier;

import javax.swing.*;
import javax.swing.border.TitledBorder;
import java.awt.*;
import java.awt.event.ActionEvent;


public class SupplierPanel extends JPanel {

    private JPanel northPanel;
    private JPanel centerPanel;
    private JList<Supplier> supplierJList;
    private JButton addSupplier, removeSupplier, alterSupplier;

    public SupplierPanel(){
        setLayout(new BorderLayout());
        setupPanels();
    }

    private void setupPanels() {
        northPanel = new JPanel();
        centerPanel = new JPanel();

        addSupplier = new JButton("Add supplier");
        removeSupplier = new JButton("Remove supplier");
        alterSupplier = new JButton("Alter supplier");
        addSupplier.setSize(new Dimension(100,30));
        removeSupplier.setSize(new Dimension(100,30));
        alterSupplier.setSize(new Dimension(100,30));
        northPanel.add(addSupplier);
        northPanel.add(removeSupplier);
        northPanel.add(alterSupplier);

        centerPanel.setBorder(new TitledBorder("Suppliers"));
        supplierJList = new JList<>();
        supplierJList.setPreferredSize(new Dimension(900, 550));
        supplierJList.setEnabled(true);
        supplierJList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        centerPanel.setLayout(new BorderLayout(5, 0));
        centerPanel.add(supplierJList);

        this.add(northPanel, BorderLayout.NORTH);
        this.add(centerPanel, BorderLayout.CENTER);
    }

    public void addSupplierToList(Supplier[] supplier){
        if (supplier != null){
            supplierJList.setListData(supplier);
        }
    }

    public void actionPerformed(ActionEvent e){
        if (e.getSource() == addSupplier){

        }
        if (e.getSource() == removeSupplier){

        }
        if (e.getSource() == alterSupplier){

        }
    }


}
