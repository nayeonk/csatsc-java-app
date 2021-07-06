package data;

public class HospitalVisit {
    private final String reason;
    private final String year;

    public HospitalVisit(String year, String reason) {
        this.reason = reason;
        this.year = year;
    }


    public String getYear() {
        return year;
    }

    public String getReason() {
        return reason;
    }

}
