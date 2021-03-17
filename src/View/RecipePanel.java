package View;

import javax.swing.*;
import javax.swing.border.EtchedBorder;
import javax.swing.border.LineBorder;
import javax.swing.border.MatteBorder;
import javax.swing.border.TitledBorder;
import javax.swing.event.ChangeEvent;
import java.awt.*;
import java.awt.event.ActionEvent;

public class RecipePanel extends JPanel {
    private JPanel northPanel;
    private JPanel leftPanel;
    private JPanel centerPanel;

    private SpinnerModel spinnerModel;
    private JSpinner spinner;
    private JComboBox<String> recipes;
    private JTextArea abc; // Byt namn. Ska det vara textarea?
    private JTextArea recipeInstructions; //Ska det vara textarea?

    private JButton done;
    private JButton addRecipe;
    private JButton removeRecipe;
    private JButton modifyRecipe;

    private String[] menu = {"Recept1", "Recept2", "Recept3", "Recept4"}; //Bara test för meny

    public RecipePanel(){
        setLayout(new BorderLayout());
        setupPanels();
    }

    public void setupPanels(){
        northPanel = new JPanel();
        leftPanel = new JPanel();
        centerPanel = new JPanel();

        //North panel
        northPanel.setBorder(new EtchedBorder(EtchedBorder.RAISED));

        done = new JButton("Klar");
        done.addActionListener(this::actionPerformed);
        northPanel.add(done);

        spinnerModel = new SpinnerNumberModel(1, 1, 20, 1);
        spinner = new JSpinner(spinnerModel);
        spinner.addChangeListener(this::stateChanged);
        northPanel.add(spinner);

        recipes = new JComboBox<>(menu);
        recipes.setPreferredSize(new Dimension(200, 25));
        recipes.addActionListener(this::actionPerformed);
        northPanel.add(recipes);

        addRecipe = new JButton("Lägg till nytt recept");
        addRecipe.addActionListener(this::actionPerformed);
        northPanel.add(addRecipe);

        removeRecipe = new JButton("Ta bort valt recept");
        removeRecipe.addActionListener(this::actionPerformed);
        northPanel.add(removeRecipe);

        modifyRecipe = new JButton("Ändra i recept");
        modifyRecipe.addActionListener(this::actionPerformed);
        northPanel.add(modifyRecipe);

        add(northPanel, BorderLayout.NORTH);

        //Left panel
        leftPanel.setLayout(new BorderLayout());
        abc = new JTextArea();
        abc.setEditable(false);
        abc.setPreferredSize(new Dimension(200, 800));
        abc.setBorder(new TitledBorder("Innehåll"));
        abc.append("2 ägg \n");
        abc.append("2 banan \n");
        abc.append("2 vatten \n");
        abc.append("2 salt \n");
        leftPanel.add(abc);
        leftPanel.setPreferredSize(new Dimension(200, 800));

        add(leftPanel, BorderLayout.WEST);

        //Center panel
        centerPanel.setLayout(new BorderLayout());
        recipeInstructions = new JTextArea();
        recipeInstructions.setEditable(false);
        recipeInstructions.setBorder(new TitledBorder("Instruktioner"));
        recipeInstructions.append("Vispa ägg med jord \n");
        recipeInstructions.append("rör ner hackad banan \n");
        recipeInstructions.append("Häll i form, salta på toppen och backa på 0 grader i 80 minuter \n");
        centerPanel.add(recipeInstructions, BorderLayout.CENTER);

        add(centerPanel, BorderLayout.CENTER);
    }

    public void actionPerformed(ActionEvent e){
        if (e.getSource() == done){
            if (JOptionPane.showConfirmDialog(null, "Är du säker på att du tillaggat receptet? Lagersaldo kommer att dras av", "asd", JOptionPane.YES_NO_OPTION) == 0){
                // Recept tillagat och saldo ska dras av
            } else {
                JOptionPane.showMessageDialog(null, "Inga varor dras av", "Meddelande", JOptionPane.PLAIN_MESSAGE);
            }
        }
        if (e.getSource() == recipes){
            //Byt recept som visas
            System.out.println(menu[recipes.getSelectedIndex()]);
        }
        if (e.getSource() == addRecipe){
            //Metod för att lägga till recept
            System.out.println("Lägg till recept");
        }
        if (e.getSource() == removeRecipe){
            //Antingen ta bort valt recept, eller ny ruta där man väljer vilket recept som ska tas bort
            //Varningsruta  typ JOptionpane eller liknande som man måste acceptera för att ta bort receptet
            System.out.println("Ta bort recept");
        }
        if (e.getSource() == modifyRecipe){
            //Funktion för att ändra i valt recept?
            System.out.println("Ändra i recept");
        }
    }

    public void stateChanged(ChangeEvent e){
        if (e.getSource() == spinner){
            //Metod för att multiplicera recept
            System.out.println(spinner.getValue());
        }
    }
}
