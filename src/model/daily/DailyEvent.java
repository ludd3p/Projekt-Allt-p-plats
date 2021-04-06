package model.daily;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

/**
 * A daily event trigger that triggers all actions in a list once a day.
 *
 * @Author Hazem Elkhalil
 */

public class DailyEvent extends Thread {
    private List<DailyAction> actions;

    /**
     * Initiates the object to the basic values
     */
    public DailyEvent() {
        actions = new ArrayList<>();
        this.start();
    }

    /**
     * Ran every 24 hours and executes all the daily actions.
     */
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

    /**
     * @param action adds an action to the daily action list
     */

    public void addAction(DailyAction action) {
        this.actions.add(action);
    }
}
