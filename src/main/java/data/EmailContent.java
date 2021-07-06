package data;

public class EmailContent implements java.io.Serializable {

    public static final long serialVersionUID = 1;
    private int emailContentID;
    private String body;
    private String subject;
    private String reason;

    public static long getSerialversionuid() {
        return serialVersionUID;
    }

    public int getEmailContentID() {
        return emailContentID;
    }

    public void setEmailContentID(int emailContentID) {
        this.emailContentID = emailContentID;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }


}
