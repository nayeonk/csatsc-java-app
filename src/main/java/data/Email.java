package data;

public class Email implements java.io.Serializable {

    public static final long serialVersionUID = 1;

    private int emailID;
    private String email;
    private String timestamp;

    public Email(int emailID, String email, String timestamp) {
        setEmailID(emailID);
        setEmail(email);
        setTimestamp(timestamp);
    }

    public Email(String email) {
        setEmail(email);
    }

    public int getEmailID() {
        return this.emailID;
    }

    public void setEmailID(int emailID) {
        this.emailID = emailID;
    }

    public String getEmail() {
        return this.email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getTimestamp() {
        return this.timestamp;
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }
}