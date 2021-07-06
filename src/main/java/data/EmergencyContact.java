package data;

public class EmergencyContact implements java.io.Serializable {

    public static final long serialVersionUID = 1;

    private int emergencyContactID;
    private final String firstName;
    private final String lastName;
    private final int parentID;
    private int addressID;
    private final String phone;
    private final Address address;
    private String email;
    private int emailID;

    private EmergencyContact(EmergencyContactBuilder builder) {

        this.emergencyContactID = builder.emergencyContactID;
        this.firstName = builder.firstName;
        this.lastName = builder.lastName;
        this.parentID = builder.parentID;
        this.addressID = builder.addressID;
        this.address = builder.address;
        this.phone = builder.phone;
        this.emailID = builder.emailID;
        //this.address = builder.address;
        this.email = builder.email;
    }

    public int getEmailID() {
        return this.emailID;
    }

    public void setEmailID(int emailID) {
        this.emailID = emailID;
    }

    public int getEmergencyContactID() {
        return this.emergencyContactID;
    }

    public void setEmergencyContactID(int emergencyContactID) {
        this.emergencyContactID = emergencyContactID;
    }

    public String getName() {
        if (firstName == null || lastName == null)
            return "";
        return this.firstName + " " + this.lastName;
    }

    public String getFirstName() {
        return this.firstName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getLastName() {
        return this.lastName;
    }

    public String getPhone() {
        return this.phone;
    }

    public int getParentID() {
        return this.parentID;
    }

    public int getAddressID() {
        return this.addressID;
    }

    public void setAddressID(int addressID) {
        this.addressID = addressID;
    }

    public Address getAddress() {
        return this.address;
    }

    public static class EmergencyContactBuilder {

        private int emergencyContactID;
        private String firstName;
        private String lastName;
        private int parentID;
        private int addressID;
        private String phone;
        private Address address;
        private String email;
        private int emailID;

        public EmergencyContactBuilder emergencyContactID(int emergencyContactID) {
            this.emergencyContactID = emergencyContactID;
            return this;
        }

        public EmergencyContactBuilder name(String firstName, String lastName) {
            this.firstName = firstName;
            this.lastName = lastName;
            return this;
        }

        public EmergencyContactBuilder parentID(int parentID) {
            this.parentID = parentID;
            return this;
        }

        public EmergencyContactBuilder emailID(int emailID) {
            this.emailID = emailID;
            return this;
        }

        public EmergencyContactBuilder phone(String phone) {
            this.phone = phone;
            return this;
        }

        public EmergencyContactBuilder addressID(int addressID) {
            this.addressID = addressID;
            return this;
        }

        public EmergencyContactBuilder address(Address address) {
            this.address = address;
            return this;
        }

        public EmergencyContactBuilder email(String email) {
            this.email = email;
            return this;
        }

        public EmergencyContact build() {
            return new EmergencyContact(this);
        }
    }
}


