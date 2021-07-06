package data;

public class StudentCampApplication implements java.io.Serializable {

    public static final long serialVersionUID = 1;

    private int studentCampID;
    private int studentID;
    private int campOfferedID;
    private int accepted;
    private int rejected;
    private int waitlisted;
    private int confirmed;
    private int withdrawn;
    private int paid;
    private double cost;
    private String status;

    public StudentCampApplication(int studentCampID, int studentID, int campOfferedID, int accepted, int rejected, int waitlisted, int confirmed, int withdrawn, int paid, double cost) {
        setStudentCampID(studentCampID);
        setStudentID(studentID);
        setCampOfferedID(campOfferedID);
        setAccepted(accepted);
        setRejected(rejected);
        setWaitlisted(waitlisted);
        setConfirmed(confirmed);
        setWithdrawn(withdrawn);
        setPaid(paid);
        setCost(cost);

        String status = "";

        if (withdrawn == 1) {
            status = "Withdrawn";
        } else {

            if (accepted == 1) {
                status = "Accepted";
            } else if (waitlisted == 1) {
                status = "Waitlisted";
            } else {

                if (rejected == 1) {
                    status = "Rejected";
                } else {
                    status = "Pending";
                }
            }

            if (paid == 1) {
                status += ", Paid";
            }

            if (confirmed == 1) {
                status += ", Confirmed";
            }

        }

        setStatus(status);
    }

    public int getStudentCampID() {
        return studentCampID;
    }

    public void setStudentCampID(int studentCampID) {
        this.studentCampID = studentCampID;
    }

    public int getStudentID() {
        return studentID;
    }

    public void setStudentID(int studentID) {
        this.studentID = studentID;
    }

    public int getCampOfferedID() {
        return campOfferedID;
    }

    public void setCampOfferedID(int campOfferedID) {
        this.campOfferedID = campOfferedID;
    }

    public int getAccepted() {
        return accepted;
    }

    public void setAccepted(int accepted) {
        this.accepted = accepted;
    }

    public int getRejected() {
        return rejected;
    }

    public void setRejected(int rejected) {
        this.rejected = rejected;
    }

    public int getWaitlisted() {
        return waitlisted;
    }

    public void setWaitlisted(int waitlisted) {
        this.waitlisted = waitlisted;
    }

    public int getConfirmed() {
        return confirmed;
    }

    public void setConfirmed(int confirmed) {
        this.confirmed = confirmed;
    }

    public int getWithdrawn() {
        return withdrawn;
    }

    public void setWithdrawn(int withdrawn) {
        this.withdrawn = withdrawn;
    }

    public int getPaid() {
        return paid;
    }

    public void setPaid(int paid) {
        this.paid = paid;
    }

    public double getCost() {
        return cost;
    }

    public void setCost(double cost) {
        this.cost = cost;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}