package data;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

public class Student implements java.io.Serializable {

    public static final long serialVersionUID = 1;

    private int lastUpdatedYear;
    private int studentID;
    private Integer genderID;
    private Integer schoolID;
    private Integer gradeID;
    private String dob;
    private Integer reducedMealsID = 3;
    private String firstName;
    private String middleName;
    private String lastName;
    private String preferredName;
    private String emailAdress;
    private String otherSchool;
    private List<Ethnicity> ethnicities;
    private Gender gender;
    private Grade grade;
    private School school;
    private String experience;
    private String diet;
    //private String birthString;
    private ReducedMeals reducedMeals;
    private Boolean transportTo;
    private Boolean transportFrom;
    private Boolean attended;
    private Boolean legalAgree;
    private Boolean interestedGirlsCamp;
    private Boolean OnCampus;
    private String otherInfo;
    private List<GradeReport> gradeReports;
    private List<ReducedMealsVerification> reducedMealsVerifications;
    private String pickupCode;
    private MedicalForm medForm;
    //used just for in rendering JSP forms
    //STUPID JSTL doesn't have a contains function so I have to use a map (see studentform.jsp for usage)
    private final Map<Integer, String> ethnicityIDSet = new HashMap<>();
    private String[] ethnicityIDs;
    private Boolean isCaucasian;
    private Boolean isAfricanAmerican;
    private Boolean isAsian;
    private Boolean isHispanic;
    private Boolean isAmericanIndian;
    private Boolean isOther;
    private int progress;

    public Student(Integer studentID, String fname, String mname, String lname, String pname) {
        this.studentID = studentID;
        this.firstName = fname;
        this.middleName = mname;
        this.lastName = lname;
        this.preferredName = pname;
    }

    private Student(StudentBuilder builder) {

        this.studentID = builder.studentID;
        this.firstName = builder.firstName;
        //THESE IDS ARE STRING OF THE ETHINICTY NOT A NUMBER!!!!
        this.ethnicityIDs = builder.ethinictyIDs;
        //check which ethnicities are checked
        this.isCaucasian = false;
        this.isAfricanAmerican = false;
        this.isAsian = false;
        this.isHispanic = false;
        this.isAmericanIndian = false;
        this.isOther = false;

        if (this.ethnicityIDs != null) {

            int elength = this.ethnicityIDs.length;
            for (int i = 0; i < elength; ++i) {
                if (this.ethnicityIDs[i].equalsIgnoreCase("caucasian")) {
                    this.isCaucasian = true;
                } else if (this.ethnicityIDs[i].equalsIgnoreCase("african-american")) {
                    this.isAfricanAmerican = true;
                } else if (this.ethnicityIDs[i].equalsIgnoreCase("asian")) {
                    this.isAsian = true;
                } else if (this.ethnicityIDs[i].equalsIgnoreCase("hispanic")) {
                    this.isHispanic = true;
                } else if (this.ethnicityIDs[i].equalsIgnoreCase("american-indian")) {
                    this.isAmericanIndian = true;
                } else if (this.ethnicityIDs[i].equalsIgnoreCase("other")) {
                    this.isOther = true;
                }
            }
        }
        this.lastName = builder.lastName;
        this.preferredName = builder.preferredName;
        this.middleName = builder.middleName;
        this.emailAdress = builder.emailAddress;
        this.otherSchool = builder.otherSchool;
        this.ethnicities = builder.ethnicities;
        if ((ethnicities != null) && (ethnicities.size() > 0)) {
            ethnicities.stream().forEach(e -> {
                ethnicityIDSet.put(e.getEthnicityID(), "trivial");
            });
        }
        this.gender = builder.gender;
        this.grade = builder.grade;
        this.dob = builder.dob;
        this.school = builder.school;
        this.experience = builder.experience;
        this.diet = builder.diet;
        this.reducedMeals = builder.reducedMeals;
        this.transportTo = builder.transportTo;
        this.transportFrom = builder.transportFrom;
        this.attended = builder.attended;
        this.legalAgree = builder.legalAgree;
        this.interestedGirlsCamp = builder.interestedGirlsCamp;
        this.otherInfo = builder.otherInfo;
        this.gradeReports = builder.gradeReports;
        this.reducedMealsVerifications = builder.reducedMealsVerifications;
        this.pickupCode = builder.pickupCode;
        this.lastUpdatedYear = builder.lastUpdatedYear;
        this.progress = builder.progress;
        this.OnCampus = builder.OnCampus;

        if (gender != null) {
            this.genderID = gender.getGenderID();
        }
        if (grade != null) {
            this.gradeID = grade.getGradeID();
        }

        if (school != null) {
            this.schoolID = school.getSchoolID();
        }

        if (reducedMeals != null) {
            this.reducedMealsID = reducedMeals.getReducedMealsID();
        }
    }

    public Student() {
        // TODO Auto-generated constructor stub
    }

    public static String generatePickupCode() {
        String SALTCHARS = "abcdefghijklmnopqrstuvwxyz1234567890";
        StringBuilder salt = new StringBuilder();
        Random rnd = new Random();
        while (salt.length() < 4) { // length of the random string.
            int index = (int) (rnd.nextFloat() * SALTCHARS.length());
            salt.append(SALTCHARS.charAt(index));
        }
        String saltStr = salt.toString();
        return saltStr;
    }

    public int getLastUpdatedYear() {
        return lastUpdatedYear;
    }

    public MedicalForm getMedForm() {
        return medForm;
    }

    public void setMedForm(MedicalForm medForm) {
        this.medForm = medForm;
    }

    public int getStudentID() {
        return this.studentID;
    }

    public void setStudentID(int studentID) {
        this.studentID = studentID;
    }

    public String[] getEthnicityIDS() {
        return ethnicityIDs;
    }

    public Boolean getIsAfricanAmerican() {
        return isAfricanAmerican;
    }

    public Boolean getIsAmericanIndian() {
        return isAmericanIndian;
    }

    public Boolean getIsAsian() {
        return isAsian;
    }

    public Boolean getIsCaucasian() {
        return isCaucasian;
    }

    public Boolean getIsHispanic() {
        return isHispanic;
    }

    public Boolean getIsOther() {
        return isOther;
    }

    public void setAfricanAmerican(Boolean africanAmerican) {
        isAfricanAmerican = africanAmerican;
    }

    public void setAmericanIndian(Boolean americanIndian) {
        isAmericanIndian = americanIndian;
    }

    public void setAsian(Boolean asian) {
        isAsian = asian;
    }

    public void setCaucasian(Boolean caucasian) {
        isCaucasian = caucasian;
    }

    public void setHispanic(Boolean hispanic) {
        isHispanic = hispanic;
    }

    public void setOtherEthnicity(Boolean other) {
        isOther = other;
    }

    public Map<Integer, String> getEthnicityIDSet() {
        return ethnicityIDSet;
    }

    public String getName() {
        return this.firstName + " " + this.lastName;
    }

    public String getFirstName() {
        return this.firstName;
    }

    public String getMiddleName() {
        return this.middleName;
    }

    public String getLastName() {
        return this.lastName;
    }

    public String getPreferredName() {
        return this.preferredName;
    }

    public String getEmailAddress() {
        return this.emailAdress;
    }

    public List<Ethnicity> getEthnicities() {
        return this.ethnicities;
    }

    public void setEthnicities(List<Ethnicity> studentEthnicities) {
        this.ethnicities = studentEthnicities;
    }

    public Gender getGender() {
        return this.gender;
    }

    public Integer getGenderID() {
        return this.genderID;
    }

    public Grade getGrade() {
        return this.grade;
    }

    public Integer getGradeID() {
        return this.gradeID;
    }

    public String getDob() {
        if (dob == null) return "";
        return this.dob;
    }

    public void setDob(String string) {
        this.dob = string;
    }

    public School getSchool() {
        return this.school;
    }

    public Integer getSchoolID() {
        return this.schoolID;
    }

    public String getExperience() {
        return this.experience;
    }

    public String getDiet() {
        return this.diet;
    }

    public void setReducedMeals(ReducedMeals reducedMeals) {
        this.reducedMeals = reducedMeals;
        this.reducedMealsID = reducedMeals.getReducedMealsID();
    }

    public ReducedMeals getReducedMeals() {
        return this.reducedMeals;
    }

    public Integer getReducedMealsID() {
        return this.reducedMealsID;
    }

    public void setPickUpCode(String pickupCode) {
        this.pickupCode = pickupCode;
    }

    public String getOtherSchool() {
        return otherSchool;
    }

    public void setOtherSchool(String otherSchool) {
        this.otherSchool = otherSchool;
    }

    public void setGradeReports(List<GradeReport> gradeReports) {
        this.gradeReports = gradeReports;
    }

    public List<GradeReport> getGradeReports() {
        return gradeReports;
    }

    public void setReducedMealsVerifications(List<ReducedMealsVerification> reducedMealsVerifications) {
        this.reducedMealsVerifications = reducedMealsVerifications;
    }

    public List<ReducedMealsVerification> getReducedMealsVerifications() {
        return reducedMealsVerifications;
    }

    public Boolean getTransportTo() {
        return transportTo;
    }

    public Boolean getTransportFrom() {
        return transportFrom;
    }

    public Boolean getAttended() {
        return this.attended;
    }
//	
//	public String getDob() {
//		return dob;
//	}

    public Boolean getLegalAgree() {
        return legalAgree;
    }

    public void setLegalAgree(Boolean bool) {
        this.legalAgree = bool;
    }

    public Boolean getInterestedGirlsCamp() {
        return interestedGirlsCamp;
    }

    public String getOtherInfo() {
        return otherInfo;
    }

    public String getPickupCode() {
        return pickupCode;
    }

    public int getProgress() {
        return progress;
    }

    public void setProgress(int progress) {
        this.progress = progress;
    }

    public Boolean getOnCampus() { return this.OnCampus; }

    public void setOnCampus(Boolean OnCampus) { this.OnCampus = OnCampus; }

    public static class StudentBuilder {

        // Initial completion of camper profile
        private Boolean OnCampus;
        private String firstName;
        private String middleName;
        private String lastName;
        private String preferredName;
        private Gender gender;
        private String dob;
        private List<Ethnicity> ethnicities;
        private String emailAddress;
        private School school;
        private String otherSchool;
        private Boolean transportTo;
        private Boolean transportFrom;
        private Boolean attended;
        private String experience;
        private String otherInfo;
        private String medicalIssues;
        private int studentID;
        private int lastUpdatedYear;
        private int progress;

        // After thought stuff
        public Grade grade;
        private String diet;
        private ReducedMeals reducedMeals;
        private Boolean legalAgree;
        private Boolean interestedGirlsCamp;
        private List<GradeReport> gradeReports;
        private List<ReducedMealsVerification> reducedMealsVerifications;
        private String pickupCode;
        private String[] ethinictyIDs;

        public StudentBuilder name(String firstName, String middleName, String lastName, String preferredName) {
            this.firstName = firstName;
            this.middleName = middleName;
            this.lastName = lastName;
            this.preferredName = preferredName;
            return this;
        }

        public StudentBuilder OnCampus(Boolean OnCampus) {
            this.OnCampus = OnCampus;
            return this;
        }

        public StudentBuilder gender(Gender gender) {
            this.gender = gender;
            return this;
        }

        public StudentBuilder dob(String date) {
            this.dob = date;
            return this;
        }

        public StudentBuilder ethinictyIDs(String[] parameterValues) {
            this.ethinictyIDs = parameterValues;
            return this;
        }

        public StudentBuilder emailAddress(String emailAddress) {
            this.emailAddress = emailAddress;
            return this;
        }

        public StudentBuilder school(School school) {
            this.school = school;
            return this;
        }

        public StudentBuilder otherSchool(String otherSchool) {
            this.otherSchool = otherSchool;
            return this;
        }

        public StudentBuilder transportTo(Boolean transportTo) {
            this.transportTo = transportTo;
            return this;
        }

        public StudentBuilder transportFrom(Boolean transportFrom) {
            this.transportFrom = transportFrom;
            return this;
        }

        public StudentBuilder attended(Boolean attended) {
            this.attended = attended;
            return this;
        }

        public StudentBuilder experience(String experience) {
            this.experience = experience;
            return this;
        }

        public StudentBuilder otherInfo(String otherInfo) {
            this.otherInfo = otherInfo;
            return this;
        }

        public StudentBuilder studentID(int studentID) {
            this.setStudentID(studentID);
            return this;
        }

        public StudentBuilder lastUpdatedYear(int lastUpdatedYear) {
            this.lastUpdatedYear = lastUpdatedYear;
            return this;
        }

        public StudentBuilder diet(String diet) {
            this.diet = diet;
            return this;
        }

        //        public StudentBuilder medicalIssues(String medicalIssues) {
//            this.medicalIssues = medicalIssues;
//            return this;
//        }
        public StudentBuilder progress(int progress) {
            this.progress = progress;
            return this;
        }

        public StudentBuilder grade(Grade studentGrade) {
            this.grade = studentGrade;
            return this;
        }

        public StudentBuilder reducedMeals(ReducedMeals reducedMeals) {
            this.reducedMeals = reducedMeals;
            return this;
        }

        public StudentBuilder legalAgree(Boolean legalAgree) {
            this.legalAgree = legalAgree;
            return this;
        }

        public StudentBuilder interestedGirlsCamp(Boolean interestedGirlsCamp) {
            this.interestedGirlsCamp = interestedGirlsCamp;
            return this;
        }

        public StudentBuilder gradeReports(List<GradeReport> gradeReports) {
            this.gradeReports = gradeReports;
            return this;
        }

        public StudentBuilder reducedMealsVerification(List<ReducedMealsVerification> reducedMealsVerifications) {
            this.reducedMealsVerifications = reducedMealsVerifications;
            return this;
        }

        public Student build() {
            return new Student(this);
        }

        public int getStudentID() {
            return studentID;
        }

        public Boolean getOnCampus() { return OnCampus; }

        public void setStudentID(int studentID) {
            this.studentID = studentID;
        }

        public StudentBuilder pickupCode(String pCode) {
            this.pickupCode = pCode;
            return this;
        }

        public StudentBuilder ethnicity(List<Ethnicity> currentStudentEthnicities) {
            this.ethnicities = currentStudentEthnicities;
            return this;
        }
    }
}


