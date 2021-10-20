package data;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Comparator;
import java.sql.Time;
import java.util.Date;
import java.util.List;

public class CampOffered implements java.io.Serializable {

    public static final long serialVersionUID = 1;
    private int campOfferedID;
    private String campTopic;
    private String campLevel;
    private String description;
    private String recommendedGrade;
    private String recommendedGradeLow;
    private String recommendedGradeHigh;
    private String imageLink;
    //assigned TA added 10/13/2020
    private String assignedTA;
    private Date startDate;
    private Date endDate;
    private String days;
    private Time startTime;
    private Time endTime;
    private int capacity;
    private int confirmed;
    private int accepted;
    private int registered;
    private int waitlisted;
    private int rejected;
    private int withdrawn;
    private int applied;
    private int paid;
    private int price;
    private boolean closed;
    private boolean remote;
    private List<Staff> campTAs;
    private String status;

    public int getCampOfferedID() {
        return campOfferedID;
    }

    public void setCampOfferedID(int campOfferedID) {
        this.campOfferedID = campOfferedID;
    }

    public String getCampTopic() {
        return campTopic;
    }

    public void setCampTopic(String campTopic) {
        this.campTopic = campTopic;
    }

    public String getCampLevel() {
        return campLevel;
    }

    public void setCampLevel(String campLevel) {
        this.campLevel = campLevel;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    public String getDays() {
        return days;
    }

    public void setDays(String days) {
        this.days = days;
    }

    public Time getStartTime() {
        return startTime;
    }

    public void setStartTime(Time startTime) {
        this.startTime = startTime;
    }

    public Time getEndTime() {
        return endTime;
    }

    public void setEndTime(Time endTime) {
        this.endTime = endTime;
    }

    public String getTimeString() {
        Date startTimeToDate = null;
        Date endTimeToDate = null;
        try {
            DateFormat timeToDateFormat = new SimpleDateFormat("hh:mm:ss");
            startTimeToDate = timeToDateFormat.parse(startTime.toString());
            endTimeToDate = timeToDateFormat.parse(endTime.toString());
        } catch (ParseException e) {
            e.printStackTrace();
        }
        DateFormat timeFormat = new SimpleDateFormat("hh:mm a");
        String campTimes = timeFormat.format(startTimeToDate) + " - " + timeFormat.format(endTimeToDate);
        return campTimes;
    }

    public String getDateString() {
        DateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy");
        String campDates = dateFormat.format(startDate) + " - " + dateFormat.format(endDate);
        return campDates;
    }

    public int getConfirmed() {
        return confirmed;
    }

    public void setConfirmed(int confirmed) {
        this.confirmed = confirmed;
        this.status = confirmed == 1 ? "Confirmed" : status;
    }

    public int getApplied() {
        return applied;
    }

    public void setApplied(int applied) {
        this.applied = applied;
        this.status = applied == 1 ? "Applied" : status;
    }

    public int getAccepted() {
        return accepted;
    }

    public void setAccepted(int accepted) {
        this.accepted = accepted;
        this.status = accepted == 1 ? "Accepted" : status;
    }

    public int getCapacity() {
        return capacity;
    }

    public void setCapacity(int capacity) {
        this.capacity = capacity;
    }

    public int getRegistered() {
        return registered;
    }

    public void setRegistered(int registered) {
        this.registered = registered;
        this.status = registered == 1 ? "Registered" : status;
    }

    public int getRejected() {
        return rejected;
    }

    public void setRejected(int rejected) {
        this.rejected = rejected;
        this.status = rejected == 1 ? "Rejected" : status;
    }

    public int getWithdrawn() {
        return withdrawn;
    }

    public void setWithdrawn(int withdrawn) {
        this.withdrawn = withdrawn;
        this.status = withdrawn == 1 ? "Withdrawn" : status;
    }

    public int getWaitlisted() {
        return waitlisted;
    }

    public void setWaitlisted(int waitlisted) {
        this.waitlisted = waitlisted;
        this.status = waitlisted == 1 ? "Waitlisted" : status;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getRecommendedGrade() {
        return recommendedGrade;
    }

    public void setRecommendedGrade(String recommendedGrade) {
        this.recommendedGrade = recommendedGrade;
    }

    public String getRecommendedGradeLow() {
        return recommendedGradeLow;
    }

    public void setRecommendedGradeLow(String recommendedGradeLow) {
        this.recommendedGradeLow = recommendedGradeLow;
    }

    public String getRecommendedGradeHigh() {
        return recommendedGradeHigh;
    }

    public void setRecommendedGradeHigh(String recommendedGradeHigh) {
        this.recommendedGradeHigh = recommendedGradeHigh;
    }

    public String getImageLink() {
        return imageLink;
    }

    public void setImageLink(String imageLink) {
        this.imageLink = imageLink;
    }

    public int getPaid() {
        return paid;
    }

    public void setPaid(int paid) {
        this.paid = paid;
        this.status = paid == 1 ? "Paid" : status;
    }

    public int getPrice() {
        return price;
    }

    public int getEmployeePrice() { return price - 2500;}

    public void setPrice(int price) {
        this.price = price;
    }

    public boolean isClosed() {
        return closed;
    }

    public void setClosed(boolean closed) {
        this.closed = closed;
    }

    public boolean isRemote() {
        return remote;
    }

    public void setRemote(boolean remote) {
        this.remote = remote;
    }

    public List<Staff> getCampTAs() {
        return campTAs;
    }

    public void setCampTAs(List<Staff> campTAs) {
        this.campTAs = campTAs;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getStatus() {
        return status;
    }

    public String getAssignedTA() {
        return assignedTA;
    }

    public void setAssignedTA(String assignedTA){
        this.assignedTA = assignedTA;
    }

    public static class SortLevel implements Comparator<CampOffered> {
        @Override
        public int compare(final CampOffered object1, final CampOffered object2) {
            return object1.getCampLevel().compareTo(object2.getCampLevel());
        }
    }

}
