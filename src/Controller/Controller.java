package Controller;


import Model.Note;
import Model.*;
import View.MainView;

public class Controller {
    public static void main(String[] args) {
        new MainView(new Controller());
    }

    public void connectToFirebase() {
        
    }

    public WeekDays[] getWeekDays(){
        return WeekDays.values();
    }

    public String[] getUnitsPrefixArray(){
        return Unit.getPrefixArray();
    }
}
