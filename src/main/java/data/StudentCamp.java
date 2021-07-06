package data;

import java.sql.Time;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class StudentCamp implements java.io.Serializable {

    private static final long serialVersionUID = 1L;

    private int studentCampID;
    private int campOfferedID;
    private Student student;
    private String studentsParentsIncome;
    private boolean accepted;
    private boolean confirmed;
    private boolean withdrawn;
    private boolean paid;
    private boolean waitlisted;
    private boolean rejected;
    private Date requestedTimestamp;
    private Date acceptedTimestamp;
    private Date confirmedTimestamp;
    private Date withdrawnTimestamp;
    private Date paidTimestamp;
    private Date waitlistedTimestamp;
    private Date rejectedTimestamp;
    private double cost;
    // camp info below, added 11/1/2020
    private Date startDate;
    private Date endDate;
    private Time startTime;
    private Time endTime;
    private String campTopic;
    private String campLevel;
    private String description;
    private boolean closed;
    private boolean remote;
    private String recommendedGradeLow;
    private String recommendedGradeHigh;
    private String campDays;


    public int getStudentCampID() {
        return studentCampID;
    }

    public void setStudentCampID(int studentCampID) {
        this.studentCampID = studentCampID;
    }

    public double getCost() {
        return cost;
    }

    public void setCost(Double cost2) {
        this.cost = cost2;
    }

    public int getCampOfferedID() {
        return campOfferedID;
    }

    public void setCampOfferedID(int campOfferedID) {
        this.campOfferedID = campOfferedID;
    }

    public Student getStudent() {
        return student;
    }

    public void setStudent(Student student) {
        this.student = student;
    }

    public boolean isAccepted() {
        return accepted;
    }

    public void setAccepted(boolean accepted) {
        this.accepted = accepted;
    }

    public boolean isConfirmed() {
        return confirmed;
    }

    public void setConfirmed(boolean confirmed) {
        this.confirmed = confirmed;
    }

    public boolean isWithdrawn() {
        return withdrawn;
    }

    public void setWithdrawn(boolean withdrawn) {
        this.withdrawn = withdrawn;
    }

    public boolean isPaid() {
        return paid;
    }

    public void setPaid(boolean paid) {
        this.paid = paid;
    }

    public boolean isWaitlisted() {
        return waitlisted;
    }

    public void setWaitlisted(boolean waitlisted) {
        this.waitlisted = waitlisted;
    }

    public boolean isRejected() {
        return rejected;
    }

    public void setRejected(boolean rejected) {
        this.rejected = rejected;
    }

    public long getSerialVersionUID() {
        return serialVersionUID;
    }

    public Date getRequestedTimestamp() {
        return requestedTimestamp;
    }

    public void setRequestedTimestamp(Date requestedTimestamp) {
        this.requestedTimestamp = requestedTimestamp;
    }

    public Date getAcceptedTimestamp() {
        return acceptedTimestamp;
    }

    public void setAcceptedTimestamp(Date acceptedTimestamp) {
        this.acceptedTimestamp = acceptedTimestamp;
    }

    public Date getConfirmedTimestamp() {
        return confirmedTimestamp;
    }

    public void setConfirmedTimestamp(Date confirmedTimestamp) {
        this.confirmedTimestamp = confirmedTimestamp;
    }

    public Date getWithdrawnTimestamp() {
        return withdrawnTimestamp;
    }

    public void setWithdrawnTimestamp(Date withdrawnTimestamp) {
        this.withdrawnTimestamp = withdrawnTimestamp;
    }

    public Date getPaidTimestamp() {
        return paidTimestamp;
    }

    public void setPaidTimestamp(Date paidTimestamp) {
        this.paidTimestamp = paidTimestamp;
    }

    public Date getWaitlistedTimestamp() {
        return waitlistedTimestamp;
    }

    public void setWaitlistedTimestamp(Date waitlistedTimestamp) {
        this.waitlistedTimestamp = waitlistedTimestamp;
    }

    public Date getRejectedTimestamp() {
        return rejectedTimestamp;
    }

    public void setRejectedTimestamp(Date rejectedTimestamp) {
        this.rejectedTimestamp = rejectedTimestamp;
    }

    public String getStudentsParentsIncome() {
        return studentsParentsIncome;
    }

    public void setStudentsParentsIncome(String studentParentsIncome) {
        this.studentsParentsIncome = studentParentsIncome;
    }

    // camp info below, added 11/1/2020
    public String getStatus() {
//        if (paid) return "Paid";
        if (withdrawn) return "Withdrawn";
        if (confirmed) return "Confirmed";
        if (rejected) return "Rejected";
        if (accepted) return "Accepted";
        if (waitlisted) return "Waitlisted";
        return "Pending";
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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
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

    public void setRecommendedGradeLow(String recommendedGradeLow) {
        this.recommendedGradeLow = recommendedGradeLow;
    }

    public void setRecommendedGradeHigh(String recommendedGradeHigh) {
        this.recommendedGradeHigh = recommendedGradeHigh;
    }

    public String getRecommendedGradeLow() {
        return recommendedGradeLow;
    }

    public String getRecommendedGradeHigh() {
        return recommendedGradeHigh;
    }

    public void setCampDays(String days) {
        if (days == null) days = "";
        this.campDays = days;
    }
    public String getCampDays() {
        return campDays;
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

}
