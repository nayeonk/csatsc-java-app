package data;

public class Income implements java.io.Serializable {
    public static final long serialVersionUID = 1;

    private int incomeID;
    private String income;
    private String timestamp;

    public Income(int incomeID, String income, String timestamp) {
        setIncomeID(incomeID);
        setIncome(income);
        setTimestamp(timestamp);
    }

    public Income(String income) {
        setIncome(income);
    }

    public Income(int incomeID) {
        setIncomeID(incomeID);
    }

    public int getIncomeID() {
        return this.incomeID;
    }

    public void setIncomeID(int incomeID) {
        this.incomeID = incomeID;
    }

    public String getIncome() {
        return this.income;
    }

    public void setIncome(String income) {
        this.income = income;
    }

    public String getTimestamp() {
        return this.timestamp;
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }
}

