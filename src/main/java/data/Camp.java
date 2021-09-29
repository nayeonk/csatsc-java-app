package data;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Camp implements java.io.Serializable {

    /* SERIAL ID */
    private static final long serialVersionUID = 1L;

    /* PRIVATE MEMBER VARIABLES */
    private int campOfferedID;
    private int capacity;
    private int confirmed;
    private Boolean isFull;
    private int campRegistration;
    private int paid;
    private int priceValue;
    private int selected;
    private String price;
    private Boolean remote;
    private String remoteString;
    private String campName;
    private String campLevel;
    private String campTopic;
    private String campStartWeek;
    private Date campStartDate;
    private String campEndWeek;
    private Date campEndDate;
    private String campDays;
    private String campTime;
    private String campDescription;
    private String campGradeFormatted;
    private String campRecommendedGrade;
    private String campRecommendedGradeLow;
    private String campRecommendedGradeHigh;
    private String campImageLink;
    private CampOffered m_offered;
    private boolean applied;

    /* CONSTRUCTORS */
    //@SuppressWarnings("deprecation")
    public Camp(CampOffered offered) {

        setOffered(offered);

        // Set basic data
        setCampOfferedID(offered.getCampOfferedID());
        setCampLevel(offered.getCampLevel());
        setCampTopic(offered.getCampTopic());
        setCampName(offered.getCampLevel() + " " + offered.getCampTopic());
        setCampConfirmed(offered.getConfirmed());
        setCampCapacity(offered.getCapacity());
        setFull(this.capacity == this.confirmed);
        setCampRegistration(offered.getRegistered());
        setCampDays(offered.getDays());

        String rs = offered.isRemote() ? "Online" : "On-Campus";
        setRemoteString(rs);
        setRemote(offered.isRemote());

        // Extract the start week
        DateFormat df = new SimpleDateFormat("MM/dd/yyyy");

        Date start = offered.getStartDate();
        Date end = offered.getEndDate();

        String startDate = df.format(start);
        String endDate = df.format(end);
        setStartWeek(formatWeek(startDate));
        setEndWeek(formatWeek(endDate));

        //setStartWeek (startWeek);
        setCampStartDate(offered.getStartDate());
        setCampEndDate(offered.getEndDate());

        Date startTimeToDate = null;
        Date endTimeToDate = null;
        try {
            DateFormat timeToDateFormat = new SimpleDateFormat("hh:mm:ss");
            startTimeToDate = timeToDateFormat.parse(offered.getStartTime().toString());
            endTimeToDate = timeToDateFormat.parse(offered.getEndTime().toString());
        } catch (ParseException e) {
            e.printStackTrace();
        }
        DateFormat timeFormat = new SimpleDateFormat("hh:mm a");
        String campTimes = timeFormat.format(startTimeToDate) + " - " + timeFormat.format(endTimeToDate);
        setCampTime(campTimes);

        setPaid(offered.getPaid());

        setPriceValue(offered.getPrice());

        double convertedPrice = offered.getPrice() / 100.0;

        String campPrice;

        if (convertedPrice == 0) {
            campPrice = "Free";
        } else {
            campPrice = "$";
            campPrice += Double.toString(convertedPrice);
            if (campPrice.endsWith(".0")
                    || campPrice.endsWith(".1")
                    || campPrice.endsWith(".2")
                    || campPrice.endsWith(".3")
                    || campPrice.endsWith(".4")
                    || campPrice.endsWith(".5")
                    || campPrice.endsWith(".6")
                    || campPrice.endsWith(".7")
                    || campPrice.endsWith(".8")
                    || campPrice.endsWith(".9")
            ) {
                campPrice += "0";
            }
        }

        setPrice(campPrice);

        // TODO: FIX THIS ONCE THE DATABASE HAS COLUMNS PROPERLY ADDED
        setCampDescription(offered.getDescription());
        setCampGradeFormatted(formatGrade(offered.getRecommendedGradeLow(), offered.getRecommendedGradeHigh()));
        setCampRecommendedGrade(offered.getRecommendedGrade());
        setCampRecommendedGradeLow(offered.getRecommendedGradeLow());
        setCampRecommendedGradeHigh(offered.getRecommendedGradeHigh());
        setCampImageLink(offered.getImageLink());

        setApplied(false);
    }

    private String formatGrade(String low, String high) {
        low = truncateGrade(low);
        high = truncateGrade(high);

        if (low == high) {
            return low;
        }
        return low + "-" + high;
    }

    private String truncateGrade(String grade) {
        if (grade == "Pre-Kindergarden") {
            return "Pre-K";
        } else if (grade == "Kindergarden") {
            return "K";
        }
        return grade;
    }

    private String formatWeek(String week) {
        return week.substring(0, week.length() - 4) + week.substring(week.length() - 2);
    }

    private String formatForFrontEnd(String str) {
        return str.replaceAll("\\s", "").replaceAll(".", "");
    }


    /* ACCESSORS AND MUTATORS */
    public int getCampOfferedID() {
        return campOfferedID;
    }

    public void setCampOfferedID(int campOfferedID) {
        this.campOfferedID = campOfferedID;
    }

    public int getCampCapacity() {
        return capacity;
    }

    public void setCampCapacity(int capacity) {
        this.capacity = capacity;
    }

    public int getCampConfirmed() {
        return confirmed;
    }

    public void setCampConfirmed(int confirmed) {
        this.confirmed = confirmed;
    }

    public int getCampRegistration() {
        return campRegistration;
    }

    public void setCampRegistration(int filled) {
        this.campRegistration = filled;
    }

    public int getPaid() {
        return paid;
    }

    public void setPaid(int paid) {
        this.paid = paid;
    }

    public int getSelected() {
        return selected;
    }

    public void setSelected(int selected) {
        this.selected = selected;
    }

    public String getCampLevel() {
        return campLevel;
    }

    public void setCampLevel(String campLevel) {
        this.campLevel = campLevel;
    }

    public String getCampTopic() {
        return campTopic;
    }

    public void setCampTopic(String campTopic) {
        this.campTopic = campTopic;
    }

    public String getCampName() {
        return campName;
    }

    public void setCampName(String campName) {
        this.campName = campName;
    }

    public String getCampStartWeek() {
        return campStartWeek;
    }

    public void setStartWeek(String endWeek) {
        this.campStartWeek = endWeek;
    }

    public Date getCampStartDate() {
        return campStartDate;
    }

    public void setCampStartDate(Date campStartDate) {
        this.campStartDate = campStartDate;
    }

    public String getCampDays() {
        return campDays;
    }

    public void setCampDays(String campDays) {
        this.campDays = campDays.replaceAll("H", "Th");
        // this.campDays = campDays;
    }

    public String getCampEndWeek() {
        return campEndWeek;
    }

    public void setEndWeek(String endWeek) {
        this.campEndWeek = endWeek;
    }

    public Date getCampEndDate() {
        return campEndDate;
    }

    public void setCampEndDate(Date campEndDate) {
        this.campEndDate = campEndDate;
    }

    public String getCampTime() {
        return campTime;
    }

    public void setCampTime(String campTime) {
        this.campTime = campTime;
    }

    public String getCampDescription() {
        return campDescription;
    }

    public void setCampDescription(String campDescription) {
        this.campDescription = campDescription;
    }

    public String getCampRecommendedGrade() {
        return campRecommendedGrade;
    }

    public void setCampRecommendedGrade(String campRecommendedGrade) {
        this.campRecommendedGrade = campRecommendedGrade;
    }

    public String getCampRecommendedGradeLow() {
        return campRecommendedGradeLow;
    }

    public void setCampRecommendedGradeLow(String campRecommendedGradeLow) {
        this.campRecommendedGradeLow = campRecommendedGradeLow;
    }

    public String getCampRecommendedGradeHigh() {
        return campRecommendedGradeHigh;
    }

    public void setCampRecommendedGradeHigh(String campRecommendedGradeHigh) {
        this.campRecommendedGradeHigh = campRecommendedGradeHigh;
    }

    public String getCampImageLink() {
        return campImageLink;
    }

    public void setCampImageLink(String campImageLink) {
        this.campImageLink = campImageLink;
    }

    public CampOffered getOffered() {
        return m_offered;
    }

    public void setOffered(CampOffered m_offered) {
        this.m_offered = m_offered;
    }

    public int getPriceValue() {
        return priceValue;
    }

    public void setPriceValue(int priceValue) {
        this.priceValue = priceValue;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public Boolean getRemote() {
        return remote;
    }

    public void setRemote(Boolean remote) {
        this.remote = remote;
    }

    public String getRemoteString() {
        return remoteString;
    }

    public void setRemoteString(String remoteString) {
        this.remoteString = remoteString;
    }


    public String getCampGradeFormatted() {
        return campGradeFormatted;
    }

    public void setCampGradeFormatted(String campGradeFormatted) {
        this.campGradeFormatted = campGradeFormatted;
    }


    public boolean isApplied() {
        return applied;
    }

    public void setApplied(boolean applied) {
        this.applied = applied;
    }

    public Boolean getFull() {
        return isFull;
    }

    public void setFull(Boolean full) {
        isFull = full;
    }



}
