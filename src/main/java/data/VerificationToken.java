package data;

import java.sql.Timestamp;
import java.util.Calendar;
import java.util.UUID;


public class VerificationToken {

    private static final int EXPIRY_TIME_IN_MINS = 30;
    private final String token;
    private Timestamp expiryDate;
    private int emailID;
    private boolean verified;

    public VerificationToken(int email) {
        this.emailID = email;
        this.token = UUID.randomUUID().toString();
        this.expiryDate = calculateExpiryDate(EXPIRY_TIME_IN_MINS);
        this.verified = false;
    }

    public VerificationToken(int email, String token, Timestamp expiry, boolean verified) {
        this.emailID = email;
        this.token = token;
        this.expiryDate = expiry;
        this.verified = verified;
    }

    public boolean isVerified() {
        return verified;
    }

    public void setVerified(boolean verified) {
        this.verified = verified;
    }

    public int getEmailID() {
        return emailID;
    }

    public void setEmailID(int email) {
        this.emailID = email;
    }

    public Timestamp getExpiryDate() {
        return expiryDate;
    }

    public String getToken() {
        return token;
    }

    private Timestamp calculateExpiryDate(int expiryTimeInMinutes) {
        Calendar cal = Calendar.getInstance();
        cal.add(Calendar.MINUTE, expiryTimeInMinutes);
        expiryDate = new Timestamp(cal.getTimeInMillis());
//    	Timestamp expiryDate = Timestamp.valueOf(LocalDateTime.now().plusMinutes(expiryTimeInMinutes));
        return expiryDate;
    }

    public boolean hasExpired() {
//        return expiryDate.before(Timestamp.valueOf(LocalDateTime.now()));
        Calendar cal = Calendar.getInstance();
        return expiryDate.before(new Timestamp(cal.getTimeInMillis()));
    }
}
