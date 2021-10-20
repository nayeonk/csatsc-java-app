package data;


public class Parent implements java.io.Serializable {

    public static final long serialVersionUID = 1;

    private int parentID = -1;
    private String firstName;
    private String lastName;
    private int emailID = -1;
    private String email;
    private int addressID;
    private String phone;
    private Address address;
    private String income;
    private boolean uscEmployee;
    private String uscID;
    private int incomeID = -1;

    private Parent(ParentBuilder builder) {

        if (builder.parentID != -1) {
            this.parentID = builder.parentID;
        }
        if (builder.firstName != null) {
            this.firstName = builder.firstName;
        }
        if (builder.lastName != null) {
            this.lastName = builder.lastName;
        }
        if (builder.lastName != null) {
            this.emailID = builder.emailID;
        }
        if (builder.lastName != null) {
            this.email = builder.email;
        }
        if (builder.lastName != null) {
            this.email = builder.email;
        }
        if (builder.lastName != null) {
            this.email = builder.email;
        }
        if (builder.addressID != -1) {
            this.addressID = builder.addressID;
        }
        if (builder.address != null) {
            this.address = builder.address;
        }
        if (builder.phone != null) {
            this.phone = builder.phone;
        }
        if (builder.income != null) {
            this.income = builder.income;
        }
        if (builder.incomeID != -1) {
            this.incomeID = builder.incomeID;
        }

        this.uscEmployee = builder.uscEmployee;
        this.uscID = builder.uscID;
    }

    public int getParentID() {
        return this.parentID;
    }

    public void setParentID(int parentID) {
        this.parentID = parentID;
    }

    public String getName() {
        return this.firstName + " " + this.lastName;
    }

    public int getIncomeID() {
        return incomeID;
    }

    public String getFirstName() {
        return this.firstName;
    }

    public String getLastName() {
        return this.lastName;
    }

    public String getPhone() {
        return this.phone;
    }

    public int getEmailID() {
        return this.emailID;
    }

    public void setEmailID(int emailID) {
        this.emailID = emailID;
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

    public String getIncome() {
        return this.income;
    }

    public Boolean getUSCEmployee() { return this.uscEmployee; }

    public String getUSCID() { return this.uscID; }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }


    public static class ParentBuilder {

        private int parentID = -1;
        private String firstName;
        private String lastName;
        private int emailID = -1;
        private String email;
        private int addressID = -1;
        private String phone;
        private Address address;
        private String income;
        private boolean uscEmployee;
        private String uscID;
        private int incomeID = -1;

        public ParentBuilder parentID(int parentID) {
            this.parentID = parentID;
            return this;
        }

        public ParentBuilder name(String firstName, String lastName) {
            this.firstName = firstName;
            this.lastName = lastName;
            return this;
        }

        public ParentBuilder emailID(int emailID) {
            this.emailID = emailID;
            return this;
        }

        public ParentBuilder incomeID(int incomeID) {
            this.incomeID = incomeID;
            return this;
        }

        public ParentBuilder email(String email) {
            this.email = email;
            return this;
        }

        public ParentBuilder phone(String phone) {
            this.phone = phone;
            return this;
        }

        public ParentBuilder addressID(int addressID) {
            this.addressID = addressID;
            return this;
        }

        public ParentBuilder address(Address address) {
            this.address = address;
            return this;
        }

        public ParentBuilder income(String income) {
            this.income = income;
            return this;
        }

        public ParentBuilder uscEmployee(boolean uscEmployee){
            this.uscEmployee = uscEmployee;
            return this;
        }

        public ParentBuilder uscID(String uscID){
            this.uscID = uscID;
            return this;
        }

        public Parent build() {
            return new Parent(this);
        }

    }
}


