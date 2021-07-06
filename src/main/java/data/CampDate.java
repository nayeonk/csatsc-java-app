package data;

import java.util.Calendar;
import java.util.Date;

public class CampDate implements java.io.Serializable {

    public static final long serialVersionUID = 1;

    private int dateID;
    private Date date;

    public int getDateID() {
        return dateID;
    }

    public void setDateID(int dateID) {
        this.dateID = dateID;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getPrettyDate() {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        int year = cal.get(Calendar.YEAR);
        int month = cal.get(Calendar.MONTH) + 1;
        int day = cal.get(Calendar.DAY_OF_MONTH);
        String currDate = month + "-" + day + "-" + year;
        return currDate;
    }
}
