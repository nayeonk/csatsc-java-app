package data;

public class State implements java.io.Serializable {

    public static final long serialVersionUID = 1;
    private static final String[] stateList = {"ZERO", "AL", "AK", "AZ", "AR", "CA", "CO", "CT", "DE", "FL", "GA", "HI", "ID", "IL", "IN", "IA", "KS", "KY", "LA", "ME", "MD", "MA", "MI", "MN", "MS", "MO", "MT", "NE", "NV", "NH", "NJ", "NM", "NY", "NC", "ND", "OH", "OK", "OR", "PA", "RI", "SC", "SD", "TN", "TX", "UT", "VT", "VA", "WA", "WV", "WI", "WY"};
    private int stateID;
    private String state;
    private String timestamp;

    public State(int stateID, String state, String timestamp) {
        setStateID(stateID);
        setState(state);
        setTimestamp(timestamp);
    }

    public State(String state) {
        setState(state);
    }

    public State(int stateID) {
        setStateID(stateID);
    }

    public static String getStateString(int stateID) {
        return stateList[stateID];
    }

    public int getStateID() {
        return this.stateID;
    }

    public void setStateID(int stateID) {
        this.stateID = stateID;
    }

    public String getState() {
        return this.state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getTimestamp() {
        return this.timestamp;
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }
}