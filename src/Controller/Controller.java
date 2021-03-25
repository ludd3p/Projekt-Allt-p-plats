package Controller;


import Model.Note;
import Model.WeekDays;
import View.MainView;

public class Controller {
    public static void main(String[] args) {
        new MainView();
    }

    public void connectToFirebase() {
        
    }

    public WeekDays[] getWeekDays(){
        return WeekDays.values();
    }
}
