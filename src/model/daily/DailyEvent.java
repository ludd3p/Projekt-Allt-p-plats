package model.daily;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class DailyEvent extends Thread {
    private List<DailyAction> actions;

    public DailyEvent() {
        actions = new ArrayList<>();
        this.start();
    }

    @Override
    public void run() {
        super.run();
        Calendar calendar = Calendar.getInstance();

        while (!this.isInterrupted()) {
            if (calendar.get(Calendar.HOUR_OF_DAY) == 0) {
                for (DailyAction action : actions) {
                    action.run();
                }
            }
            try {
                Thread.sleep((24 - calendar.get(Calendar.HOUR_OF_DAY)) * 3600 * 1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public void addAction(DailyAction action) {
        this.actions.add(action);
    }
}
