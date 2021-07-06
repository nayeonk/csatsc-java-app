package data;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.List;

public class MedicalForm {
    private Integer studentID;
    private Integer medicalFormID = -1;
    private String insuranceCarrier;
    private String nameOfInsured;
    private String policyNumber;
    private String policyPhone;
    private Address address;
    private Integer addressID;
    private String parentPhone;
    private String legalGuardianName;
    private String legalGuardianAgree;
    private String dateOfBirth;
    private String ecName;
    private String ecRelationship;
    private String ecPhone;
    private Boolean physicianPhoneCellOrWork;
    private Boolean parentPhoneCellOrHome;
    private Boolean ecPhoneCellOrHome;
    private Integer genderID;
    private String physicianName;
    private String physicianPhone;
    private Boolean tetanusShot;
    private List<Allergy> allergies;
    private List<Medication> prescribedMeds;
    private List<Medication> nonPrescribedMeds;
    private List<String> illnessesInjuries;
    private List<HospitalVisit> hospitalVisits;
    private String allergiesJSON;
    private String pMedsJSON;
    private String nonPMedsJSON;
    private String illnessJSON;
    private String hVisitsJSON;
    private String studentFName;
    private String studentLName;
    private InsuranceCard insuranceCard;
    private Gson gson;
    private final Type allergiesType = new TypeToken<List<Allergy>>() {
    }.getType();
    private final Type medsType = new TypeToken<List<Medication>>() {
    }.getType();
    private final Type hospitalType = new TypeToken<List<HospitalVisit>>() {
    }.getType();
    private final Type illnessType = new TypeToken<List<String>>() {
    }.getType();


    private MedicalForm(MedicalFormBuilder builder) {
        if (builder.studentID != null) {
            this.studentID = builder.studentID;
        }
        if (builder.medicalFormID != null) {
            this.medicalFormID = builder.medicalFormID;
        }
        if (builder.studentFName != null) {
            this.studentFName = builder.studentFName;
        }
        if (builder.studentLName != null) {
            this.studentLName = builder.studentLName;
        }
        if (builder.address != null) {
            this.address = builder.address;
        }
        if (address != null) {
            this.addressID = address.getAddressID();
        }

        if (builder.dateOfBirth != null) {
            this.dateOfBirth = builder.dateOfBirth;
        }
        if (builder.ecName != null) {
            this.ecName = builder.ecName;
        }
        if (builder.ecPhone != null) {
            this.ecPhone = builder.ecPhone;
        }
        if (builder.ecRelationship != null) {
            this.ecRelationship = builder.ecRelationship;
        }
        if (builder.parentPhone != null) {
            this.parentPhone = builder.parentPhone;
        }
        if (builder.legalGuardianName != null) {
            this.legalGuardianName = builder.legalGuardianName;
        }
        if (builder.legalGuardianAgree != null) {
            this.legalGuardianAgree = builder.legalGuardianAgree;
        }
        if (builder.physicianPhoneCellOrWork != null) {
            this.physicianPhoneCellOrWork = builder.physicianPhoneCellOrWork;
        }
        if (builder.parentPhoneCellOrHome != null) {
            this.parentPhoneCellOrHome = builder.parentPhoneCellOrHome;
        }
        if (builder.ecPhoneCellOrHome != null) {
            this.ecPhoneCellOrHome = builder.ecPhoneCellOrHome;
        }
        if (builder.genderID != null) {
            this.genderID = builder.genderID;
        }

        this.gson = new Gson();

        if (builder.insuranceCarrier != null) {
            this.insuranceCarrier = builder.insuranceCarrier;
        }

        if (builder.nameOfInsured != null) {
            this.nameOfInsured = builder.nameOfInsured;
        }

        if (builder.policyNumber != null) {
            this.policyNumber = builder.policyNumber;
        }

        if (builder.policyPhone != null) {
            this.policyPhone = builder.policyPhone;
        }

        if (builder.physicianName != null) {
            this.physicianName = builder.physicianName;
        }

        if (builder.physicianPhone != null) {
            this.physicianPhone = builder.physicianPhone;
        }

        if (builder.tetanusShot != null) {
            this.tetanusShot = builder.tetanusShot;
        }

        if ((builder.allergiesJSON != null) && (!builder.allergiesJSON.isEmpty())) {
            this.allergiesJSON = builder.allergiesJSON;
            this.allergies = gson.fromJson(allergiesJSON, allergiesType);
        }

        if ((builder.hVisitsJSON != null) && (!builder.hVisitsJSON.isEmpty())) {
            this.hVisitsJSON = builder.hVisitsJSON;
            this.hospitalVisits = gson.fromJson(hVisitsJSON, hospitalType);
        }

        if ((builder.illnessJSON != null) && (!builder.illnessJSON.isEmpty())) {
            this.illnessJSON = builder.illnessJSON;
            this.illnessesInjuries = gson.fromJson(illnessJSON, illnessType);
        }

        if ((builder.nonPMedsJSON != null) && (!builder.nonPMedsJSON.isEmpty())) {
            this.nonPMedsJSON = builder.nonPMedsJSON;
            this.nonPrescribedMeds = gson.fromJson(nonPMedsJSON, medsType);
        }

        if ((builder.pMedsJSON != null) && (!builder.pMedsJSON.isEmpty())) {
            this.pMedsJSON = builder.pMedsJSON;
            this.prescribedMeds = gson.fromJson(pMedsJSON, medsType);
        }


        if ((builder.allergies != null) && (!builder.allergies.isEmpty())) {
            this.allergies = builder.allergies;
            this.allergiesJSON = gson.toJson(allergies, allergiesType);
        }

        if ((builder.hospitalVisits != null) && (!builder.hospitalVisits.isEmpty())) {
            this.hospitalVisits = builder.hospitalVisits;
            this.hVisitsJSON = gson.toJson(hospitalVisits, hospitalType);
        }

        if ((builder.illnessesInjuries != null) && (!builder.illnessesInjuries.isEmpty())) {
            this.illnessesInjuries = builder.illnessesInjuries;
            this.illnessJSON = gson.toJson(illnessesInjuries, illnessType);
        }

        if ((builder.prescribedMeds != null) && (!builder.prescribedMeds.isEmpty())) {
            this.prescribedMeds = builder.prescribedMeds;
            this.pMedsJSON = gson.toJson(prescribedMeds, medsType);
        }

        if ((builder.nonPrescribedMeds != null) && (!builder.nonPrescribedMeds.isEmpty())) {
            this.nonPrescribedMeds = builder.nonPrescribedMeds;
            this.nonPMedsJSON = gson.toJson(nonPrescribedMeds, medsType);
        }
    }

    public MedicalForm() {
        // TODO Auto-generated constructor stub
    }

    public Address getAddress() {
        return address;
    }

    public Integer getAddressID() {
        return addressID;
    }

    public String getParentPhone() {
        return parentPhone;
    }

    public String getLegalGuardianName() {
        return legalGuardianName;
    }

    public String getLegalGuardianAgree() {
        return legalGuardianAgree;
    }

    public String getDateOfBirth() {
        return dateOfBirth;
    }

    public String getEcName() {
        return ecName;
    }

    public String getEcRelationship() {
        return ecRelationship;
    }

    public String getEcPhone() {
        return ecPhone;
    }

    public Boolean getPhysicianPhoneCellOrWork() {
        return physicianPhoneCellOrWork;
    }

    public void setPhysicianPhoneCellOrWork(Boolean physicianPhoneCellOrWork) {
        this.physicianPhoneCellOrWork = physicianPhoneCellOrWork;
    }

    public Boolean getParentPhoneCellOrHome() {
        return parentPhoneCellOrHome;
    }

    public Boolean getEcPhoneCellOrHome() {
        return ecPhoneCellOrHome;
    }

    public Integer getGenderID() {
        return genderID;
    }

    public InsuranceCard getInsuranceCard() {
        return insuranceCard;
    }

    public void setInsuranceCard(InsuranceCard insuranceCard) {
        this.insuranceCard = insuranceCard;
    }

    public Integer getStudentID() {
        return studentID;
    }

    public Integer getMedicalFormID() {
        return medicalFormID;
    }

    public void setMedicalFormID(Integer medFormID) {
        this.medicalFormID = medFormID;
    }

    public String getInsuranceCarrier() {
        return insuranceCarrier;
    }

    public void setInsuranceCarrier(String insuranceCarrier) {
        this.insuranceCarrier = insuranceCarrier;
    }

    public String getNameOfInsured() {
        return nameOfInsured;
    }

    public void setNameOfInsured(String nameOfInsured) {
        this.nameOfInsured = nameOfInsured;
    }

    public String getStudentFName() {
        return studentFName;
    }

    public String getStudentLName() {
        return studentLName;
    }

    public String getPolicyNumber() {
        return policyNumber;
    }

    public void setPolicyNumber(String policyNumber) {
        this.policyNumber = policyNumber;
    }

    public String getPolicyPhone() {
        return policyPhone;
    }

    public void setPolicyPhone(String policyPhone) {
        this.policyPhone = policyPhone;
    }

    public String getPhysicianName() {
        return physicianName;
    }

    public void setPhysicianName(String physicianName) {
        this.physicianName = physicianName;
    }

    public String getPhysicianPhone() {
        return physicianPhone;
    }

    public void setPhysicianPhone(String physicianPhone) {
        this.physicianPhone = physicianPhone;
    }

    public Boolean getTetanusShot() {
        return tetanusShot;
    }

    public List<Allergy> getAllergies() {
        return allergies;
    }

    public List<Medication> getPrescribedMeds() {
        return prescribedMeds;
    }

    public List<Medication> getNonPrescribedMeds() {
        return nonPrescribedMeds;
    }

    public List<String> getIllnessesInjuries() {
        return illnessesInjuries;
    }

    public List<HospitalVisit> getHospitalVisits() {
        return hospitalVisits;
    }

    public String getAllergiesJSON() {
        return allergiesJSON;
    }

    public String getpMedsJSON() {
        return pMedsJSON;
    }

    public String getNonPMedsJSON() {
        return nonPMedsJSON;
    }

    public String getIllnessJSON() {
        return illnessJSON;
    }

    public String gethVisitsJSON() {
        return hVisitsJSON;
    }

    public void setTetanus(boolean boolean1) {
        this.tetanusShot = boolean1;
    }

    public static class MedicalFormBuilder {

        private Integer studentID;
        private Integer medicalFormID;
        private String insuranceCarrier;
        private String nameOfInsured;
        private String policyNumber;
        private String policyPhone;
        private String physicianName;
        private String physicianPhone;
        private Boolean tetanusShot;
        private String allergiesJSON;
        private String pMedsJSON;
        private String nonPMedsJSON;
        private String illnessJSON;
        private String hVisitsJSON;
        private List<Allergy> allergies;
        private List<Medication> prescribedMeds;
        private List<Medication> nonPrescribedMeds;
        private List<String> illnessesInjuries;
        private List<HospitalVisit> hospitalVisits;
        private String studentFName;
        private String studentLName;
        private Address address;
        private String parentPhone;
        private String legalGuardianName;
        private String legalGuardianAgree;
        private String dateOfBirth;
        private String ecName;
        private String ecRelationship;
        private String ecPhone;
        private Boolean physicianPhoneCellOrWork;
        private Boolean parentPhoneCellOrHome;
        private Boolean ecPhoneCellOrHome;
        private Integer genderID;

        public MedicalFormBuilder ecRelationship(String relationship) {
            this.ecRelationship = relationship;
            return this;
        }

        public MedicalFormBuilder ecPhone(String phone) {
            this.ecPhone = phone;
            return this;
        }

        public MedicalFormBuilder physicianPhoneCellOrWork(Boolean b) {
            this.physicianPhoneCellOrWork = b;
            return this;
        }

        public MedicalFormBuilder parentPhoneCellOrHome(Boolean b) {
            this.parentPhoneCellOrHome = b;
            return this;
        }

        public MedicalFormBuilder ecPhoneCellOrHome(Boolean b) {
            this.ecPhoneCellOrHome = b;
            return this;
        }

        public MedicalFormBuilder genderID(Integer b) {
            this.genderID = b;
            return this;
        }

        public MedicalFormBuilder address(Address address) {
            this.address = address;
            return this;
        }

        public MedicalFormBuilder parentPhone(String parentPhone) {
            this.parentPhone = parentPhone;
            return this;
        }

        public MedicalFormBuilder legalGuardianName(String name) {
            this.legalGuardianName = name;
            return this;
        }

        public MedicalFormBuilder legalGuardianAgree(String legalGuardianAgree) {
            this.legalGuardianAgree = legalGuardianAgree;
            return this;
        }

        public MedicalFormBuilder dateOfBirth(String dob) {
            this.dateOfBirth = dob;
            return this;
        }

        public MedicalFormBuilder ecName(String name) {
            this.ecName = name;
            return this;
        }

        public MedicalFormBuilder studentID(int studentID) {
            this.setStudentID(studentID);
            return this;
        }

        public MedicalFormBuilder studentFName(String fname) {
            this.studentFName = fname;
            return this;
        }


        public MedicalFormBuilder studentLName(String lname) {
            this.studentLName = lname;
            return this;
        }

        public MedicalFormBuilder medicalFormID(int medicalFormID) {
            this.setMedicalFormID(medicalFormID);
            return this;
        }

        public MedicalFormBuilder insuranceCarrier(String insuranceCarrier) {
            this.setInsuranceCarrier(insuranceCarrier);
            return this;
        }

        public MedicalFormBuilder nameOfInsured(String nameOfInsured) {
            this.setNameOfInsured(nameOfInsured);
            return this;
        }

        public MedicalFormBuilder policyNumber(String policyNumber) {
            this.setPolicyNumber(policyNumber);
            return this;
        }

        public MedicalFormBuilder policyPhone(String policyPhone) {
            this.setPolicyPhone(policyPhone);
            return this;
        }

        public MedicalFormBuilder physicianName(String physicianName) {
            this.setPhysicianName(physicianName);
            return this;
        }

        public MedicalFormBuilder physicianPhone(String physicianPhone) {
            this.setPhysicianPhone(physicianPhone);
            return this;
        }

        public MedicalFormBuilder tetanusShot(Boolean tetanusShot) {
            this.setTetanusShot(tetanusShot);
            return this;
        }

        public MedicalFormBuilder allergiesJSON(String allergiesJSON) {
            this.setAllergiesJSON(allergiesJSON);
            return this;
        }

        public MedicalFormBuilder pMedsJSON(String pMedsJSON) {
            this.setpMedsJSON(pMedsJSON);
            return this;
        }

        public MedicalFormBuilder nonPMedsJSON(String nonPMedsJSON) {
            this.setNonPMedsJSON(nonPMedsJSON);
            return this;
        }

        public MedicalFormBuilder illnessJSON(String illnessJSON) {
            this.setIllnessJSON(illnessJSON);
            return this;
        }

        public MedicalFormBuilder hVisitsJSON(String hVisitsJSON) {
            this.sethVisitsJSON(hVisitsJSON);
            return this;
        }

        public MedicalFormBuilder allergies(List<Allergy> allergies) {
            this.setAllergies(allergies);
            return this;
        }

        public MedicalFormBuilder prescribedMeds(List<Medication> prescribedMeds) {
            this.setPrescribedMeds(prescribedMeds);
            return this;
        }

        public MedicalFormBuilder nonPrescribedMeds(List<Medication> nonPrescribedMeds) {
            this.setNonPrescribedMeds(nonPrescribedMeds);
            return this;
        }

        public MedicalFormBuilder illnessesInjuries(List<String> illnessesInjuries) {
            this.setIllnessesInjuries(illnessesInjuries);
            return this;
        }

        public MedicalFormBuilder hospitalVisits(List<HospitalVisit> hospitalVisits) {
            this.setHospitalVisits(hospitalVisits);
            return this;
        }

        public MedicalForm build() {
            return new MedicalForm(this);
        }

        private void setStudentID(Integer studentID) {
            this.studentID = studentID;
        }

        private void setMedicalFormID(Integer medicalFormID) {
            this.medicalFormID = medicalFormID;
        }

        private void setInsuranceCarrier(String insuranceCarrier) {
            this.insuranceCarrier = insuranceCarrier;
        }

        private void setNameOfInsured(String nameOfInsured) {
            this.nameOfInsured = nameOfInsured;
        }

        private void setPolicyNumber(String policyNumber) {
            this.policyNumber = policyNumber;
        }

        private void setPolicyPhone(String policyPhone) {
            this.policyPhone = policyPhone;
        }

        private void setPhysicianName(String physicianName) {
            this.physicianName = physicianName;
        }

        private void setPhysicianPhone(String physicianPhone) {
            this.physicianPhone = physicianPhone;
        }

        private void setTetanusShot(Boolean tetanusShot) {
            this.tetanusShot = tetanusShot;
        }

        private void setAllergiesJSON(String allergiesJSON) {
            this.allergiesJSON = allergiesJSON;
        }

        private void setpMedsJSON(String pMedsJSON) {
            this.pMedsJSON = pMedsJSON;
        }

        private void setNonPMedsJSON(String nonPMedsJSON) {
            this.nonPMedsJSON = nonPMedsJSON;
        }

        private void setIllnessJSON(String illnessJSON) {
            this.illnessJSON = illnessJSON;
        }

        private void sethVisitsJSON(String hVisitsJSON) {
            this.hVisitsJSON = hVisitsJSON;
        }

        private void setAllergies(List<Allergy> allergies) {
            this.allergies = allergies;
        }

        private void setPrescribedMeds(List<Medication> prescribedMeds) {
            this.prescribedMeds = prescribedMeds;
        }

        private void setNonPrescribedMeds(List<Medication> nonPrescribedMeds) {
            this.nonPrescribedMeds = nonPrescribedMeds;
        }

        private void setIllnessesInjuries(List<String> illnessesInjuries) {
            this.illnessesInjuries = illnessesInjuries;
        }

        private void setHospitalVisits(List<HospitalVisit> hospitalVisits) {
            this.hospitalVisits = hospitalVisits;
        }

    }
}