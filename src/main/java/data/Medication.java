package data;

public class Medication {
    private final String med;
    private final String dose;

    public Medication(String med, String dose) {
        this.med = med;
        this.dose = dose;
    }


    public String getMed() {
        return med;
    }

    public String getDose() {
        return dose;
    }
}
