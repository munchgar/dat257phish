package org.phish.classes;

import javafx.beans.property.SimpleIntegerProperty;

import java.text.SimpleDateFormat;
import java.util.Calendar;

public class TransportActivity {
    private SimpleIntegerProperty activityId;
    private SimpleIntegerProperty userId;
    private Calendar date;

    public TransportActivity(int activityId, int userId, Calendar date) {
        this.activityId = new SimpleIntegerProperty(activityId);
        this.userId = new SimpleIntegerProperty(userId);
        this.date = date;
    }

    public int getActivityId() {
        return activityId.get();
    }

    public SimpleIntegerProperty activityIdProperty() {
        return activityId;
    }

    public int getUserId() {
        return userId.get();
    }

    public SimpleIntegerProperty userIdProperty() {
        return userId;
    }

    public Calendar getDate() {
        return date;
    }
}
