package data;

public class Address implements java.io.Serializable {

    private static final long serialVersionUID = 1L;

    private String street;
    private String country;
    private String city;
    private int stateID;
    private Integer addressID;
    private String state;
    private String zip;

    public Address(String street, String country, String city, int stateID, String zip) {
        setStreet(street);
        setCountry(country);
        setCity(city);
        setStateID(stateID);
        setState(State.getStateString(stateID));
        setZip(zip);
    }

    public static String stateNumberToString(int num) {
        String ans = " ";
        switch (num) {
            case 1:
                ans = "AL";
                break;
            case 2:
                ans = "AK";
                break;
            case 3:
                ans = "AZ";
                break;
            case 4:
                ans = "AR";
                break;
            case 5:
                ans = "CA";
                break;
            case 6:
                ans = "CO";
                break;
            case 7:
                ans = "CT";
                break;
            case 8:
                ans = "DE";
                break;
            case 9:
                ans = "FL";
                break;
            case 10:
                ans = "GA";
                break;
            case 11:
                ans = "HI";
                break;
            case 12:
                ans = "ID";
                break;
            case 13:
                ans = "IL";
                break;
            case 14:
                ans = "IN";
                break;
            case 15:
                ans = "IA";
                break;
            case 16:
                ans = "KS";
                break;
            case 17:
                ans = "KY";
                break;
            case 18:
                ans = "LA";
                break;
            case 19:
                ans = "ME";
                break;
            case 20:
                ans = "MD";
                break;
            case 21:
                ans = "MA";
                break;
            case 22:
                ans = "MI";
                break;
            case 23:
                ans = "MN";
                break;
            case 24:
                ans = "MS";
                break;
            case 25:
                ans = "MO";
                break;
            case 26:
                ans = "MT";
                break;
            case 27:
                ans = "NE";
                break;
            case 28:
                ans = "NV";
                break;
            case 29:
                ans = "NH";
                break;
            case 30:
                ans = "NJ";
                break;
            case 31:
                ans = "NM";
                break;
            case 32:
                ans = "NY";
                break;
            case 33:
                ans = "NC";
                break;
            case 34:
                ans = "ND";
                break;
            case 35:
                ans = "OH";
                break;
            case 36:
                ans = "OK";
                break;
            case 37:
                ans = "OR";
                break;
            case 38:
                ans = "PA";
                break;
            case 39:
                ans = "RI";
                break;
            case 40:
                ans = "SC";
                break;
            case 41:
                ans = "SD";
                break;
            case 42:
                ans = "TN";
                break;
            case 43:
                ans = "TX";
                break;
            case 44:
                ans = "UT";
                break;
            case 45:
                ans = "VT";
                break;
            case 46:
                ans = "VA";
                break;
            case 47:
                ans = "WA";
                break;
            case 48:
                ans = "WV";
                break;
            case 49:
                ans = "WI";
                break;
            case 50:
                ans = "WY";
                break;
            default:
                ans = "";
                break;
        }
        return ans;
    }

    public Integer getAddressID() {
        return addressID;
    }

    public void setAddressID(Integer id) {
        this.addressID = id;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public int getStateID() {
        return stateID;
    }

    public void setStateID(int stateID) {
        this.stateID = stateID;
    }

    public String getZip() {
        return zip;
    }

    public void setZip(String zip) {
        this.zip = zip;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

}
