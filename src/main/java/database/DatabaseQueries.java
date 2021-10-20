package database;

import com.google.gson.JsonSyntaxException;
import data.*;
import data.EmergencyContact.EmergencyContactBuilder;
import data.Parent.ParentBuilder;

import enums.EmailType;
import servlets.Medical;

import java.sql.*;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.Date;

// TESTING DEVELOP SERVER
public class DatabaseQueries {
    private static final CheckedFunction<ResultSet, CampDate> getDatesLambda = rs -> {
        CampDate campDate = new CampDate();
        campDate.setDateID(rs.getInt("dateID"));
        campDate.setDate(new Date(rs.getDate("date").getTime()));
        return campDate;
    };
    private static final CheckedFunction<ResultSet, Student> getStudentSimpleLambda = rs -> {
//        return new Student(rs.getInt("studentID"), rs.getString("fname"), rs.getString("lname"));
        return new Student(rs.getInt("studentID"), rs.getString("fname"), "mname", rs.getString("lname"), "pname");
    };
    private static final CheckedFunction<ResultSet, Student> getStudentLambda = rs -> {
        return DatabaseConnection.processStudent(rs);
    };
    private static final CheckedFunction<ResultSet, Staff> getStaffSimpleLambda = rs -> {
        Staff ta = new Staff();
        ta.setStaffID(rs.getInt("staffID"));
        ta.setFirstName(rs.getString("firstName"));
        ta.setLastName(rs.getString("lastName"));
        return ta;
    };
    private static final CheckedFunction<ResultSet, State> getStateLambda = rs -> {
        return new State(rs.getInt("stateID"), rs.getString("state"), rs.getString("timestamp"));
    };
    private static final CheckedFunction<ResultSet, Gender> getGenderLambda = rs -> {
        return new Gender(rs.getInt("genderID"), rs.getString("gender"), rs.getString("timestamp"));
    };
    private static final CheckedFunction<ResultSet, StudentEthnicity> getStudentEthnicityLambda = rs -> {
        int studentEthnicityID = rs.getInt("studentEthnicityID");
        int studentID = rs.getInt("studentID");
        int ethnicityID = rs.getInt("ethnicityID");
        int valid = rs.getInt("valid");
        String otherEthnicity = rs.getString("otherEthnicity");

        boolean validBool = valid == 1;
        return new StudentEthnicity(studentEthnicityID, studentID, ethnicityID, validBool, otherEthnicity);
    };
    private static final CheckedFunction<ResultSet, GradeReport> getGradeReportLambda = rs -> {
        return processGradeReport(rs);
    };
    private static final CheckedFunction<ResultSet, InsuranceCard> getInsuranceCardLambda = rs -> {
        return processInsuranceCard(rs);
    };
    private static final CheckedFunction<ResultSet, Student> getStudentLambdaMedical = rs -> {
        Student student = DatabaseConnection.processStudent(rs);
        student.setMedForm(getMedicalForm(student.getStudentID()));
        return student;
    };
    private static final CheckedFunction<ResultSet, ReducedMealsVerification> getReducedMealsVLambda = rs -> {
        return new ReducedMealsVerification(
                rs.getInt("reducedMealsVerificationID"),
                rs.getInt("studentID"),
                rs.getString("filePath"),
                rs.getBoolean("deleted"));
    };
    private static final CheckedFunction<ResultSet, CampTopic> getCampTopicLambda = rs -> {
        return new CampTopic(rs.getInt("campTopicID"), rs.getString("topic"));
    };
    private static final CheckedFunction<ResultSet, CampLevel> getCampLevelLambda = rs -> {
        CampLevel campLevel = new CampLevel();
        campLevel.setCampLevelID(rs.getInt("campLevelID"));
        campLevel.setCampLevelDescription(rs.getString("campLevelDescription"));
        return campLevel;
    };
    private static final CheckedFunction<ResultSet, CampOffered> getCampLambda = rs -> {
        return processCamp(rs);
    };
    private static final CheckedFunction<ResultSet, Staff> getStaffLambda = rs -> {
        return processStaff(rs);
    };
    private static final CheckedFunction<ResultSet, Ethnicity> getEthnicityLambda = rs -> {
        return new Ethnicity(rs.getInt("ethnicityID"), rs.getString("ethnicity"), rs.getString("timestamp"));
    };
    private static final CheckedFunction<ResultSet, School> getSchoolLambda = rs -> {
        return new School(rs.getInt("schoolID"), rs.getString("school"), rs.getString("timestamp"));
    };
    private static final CheckedFunction<ResultSet, Grade> getGradeLambda = rs -> {
        return new Grade(rs.getInt("gradeID"), rs.getString("grade"), rs.getString("timestamp"));
    };
    private static final CheckedFunction<ResultSet, ReducedMeals> getReducedMealsLambda = rs -> {
        return new ReducedMeals(rs.getInt("reducedMealsID"), rs.getString("description"));
    };
    private static final CheckedFunction<ResultSet, Income> getIncomeLambda = rs -> {
        return new Income(rs.getInt("incomeID"), DatabaseConnection.processIncomeString(rs.getString("income")), rs.getString("timestamp"));
    };
    private static final CheckedFunction<ResultSet, java.sql.Date> getBirthsLambda = rs -> {
        return rs.getDate("dateOfBirth");
    };

    private static GradeReport processGradeReport(ResultSet rs) throws SQLException {
        return new GradeReport(
                rs.getInt("gradeReportID"),
                rs.getInt("studentID"),
                rs.getString("filePath"),
                rs.getBoolean("deleted"));
    }

    private static <T> List<T> getList(String sql, Integer id, CheckedFunction<ResultSet, T> f) {
        List<T> l = new ArrayList<>();
        try {
            PreparedStatement ps = DatabaseConnection.getPreparedStatement(sql);
            if (id != null) {
                ps.setInt(1, id);
            }
            return getList(ps, f);

        } catch (SQLException sqle) {
            System.out.println("sqle:CampOfferedDatabaseConnection.getCampStartDatesByYear(): " + sqle.getMessage());
        }

        return l;
    }

    private static <T> List<T> getList(PreparedStatement ps, CheckedFunction<ResultSet, T> f) {
        List<T> l = new ArrayList<>();
        try {
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                l.add(f.apply(rs));
            }
            rs.close();
            ps.close();
        } catch (SQLException sqle) {
            System.out.println("sqle:CampOfferedDatabaseConnection.getCampStartDatesByYear(): " + sqle.getMessage());
        }

        return l;
    }

    private static CampOffered processCamp(ResultSet rs) throws SQLException {
        CampOffered campOffered = new CampOffered();
        campOffered.setCampOfferedID(rs.getInt("campOfferedID"));
        int campOfferedID = campOffered.getCampOfferedID();
        campOffered.setCampTopic(rs.getString("topic"));
        campOffered.setCampLevel(rs.getString("campLevelDescription"));

        campOffered.setCapacity(rs.getInt("studentCapacity"));
        campOffered.setDescription(rs.getString("description"));
        campOffered.setRecommendedGrade(rs.getString("recommendedGrade"));

        campOffered.setImageLink(rs.getString("imageLink"));
        campOffered.setPaid(rs.getInt("paid"));
        campOffered.setPrice(rs.getInt("cost"));
        campOffered.setClosed(rs.getBoolean("closed"));
        campOffered.setRemote(rs.getBoolean("remote"));
        campOffered.setConfirmed(DatabaseQueries.getConfirmedStudentCount(campOfferedID));
        campOffered.setApplied(DatabaseQueries.getAppliedStudentCount(campOfferedID));
        campOffered.setAccepted(DatabaseQueries.getAcceptedStudentCount(campOfferedID));
        campOffered.setRejected(DatabaseQueries.getRejectedStudentCount(campOfferedID));
        campOffered.setWithdrawn(DatabaseQueries.getWithdrawnStudentCount(campOfferedID));
        campOffered.setWaitlisted(DatabaseQueries.getWaitlistedStudentCount(campOfferedID));
        campOffered.setStartDate(new Date(rs.getDate("startDate").getTime()));
        campOffered.setEndDate(new Date(rs.getDate("endDate").getTime()));
        campOffered.setDays(rs.getString("days"));
        campOffered.setStartTime(rs.getTime("startTime"));
        campOffered.setEndTime(rs.getTime("endTime"));
        campOffered.setRecommendedGradeLow(rs.getString("recommendedGradeLow"));
        campOffered.setRecommendedGradeHigh(rs.getString("recommendedGradeHigh"));
        campOffered.setAssignedTA(rs.getString("offeredTA"));

        return campOffered;
    }

    private static StudentCamp processStudentCampWithCampDetails(ResultSet rs) throws SQLException {
        StudentCamp studentCamp = new StudentCamp();
        studentCamp.setStudentCampID(rs.getInt("studentCampID"));
        studentCamp.setCampOfferedID(rs.getInt("campOfferedID"));
        studentCamp.setCampTopic(rs.getString("topic"));
        studentCamp.setCampLevel(rs.getString("campLevelDescription"));
        studentCamp.setDescription(rs.getString("description"));
        studentCamp.setConfirmed(rs.getBoolean("confirmed"));
        studentCamp.setAccepted(rs.getBoolean("accepted"));
        studentCamp.setRejected(rs.getBoolean("rejected"));
        studentCamp.setWithdrawn(rs.getBoolean("withdrawn"));
        studentCamp.setWaitlisted(rs.getBoolean("waitlisted"));
        studentCamp.setStartDate(new Date(rs.getDate("startDate").getTime()));
        studentCamp.setEndDate(new Date(rs.getDate("endDate").getTime()));
        studentCamp.setStartTime(rs.getTime("startTime"));
        studentCamp.setEndTime(rs.getTime("endTime"));
        studentCamp.setCampDays(rs.getString("days"));

        // If accepted, set cost as studentCost
        if (studentCamp.isAccepted()) {
            studentCamp.setCost((double) rs.getInt("studentCost"));
        } else {
            studentCamp.setCost((double) rs.getInt("cost"));
        }

        studentCamp.setRecommendedGradeLow(rs.getString("recommendedGradeLow"));
        studentCamp.setRecommendedGradeHigh(rs.getString("recommendedGradeHigh"));

        studentCamp.setRequestedTimestamp(rs.getTimestamp("requestedTimestamp"));
        studentCamp.setAcceptedTimestamp(rs.getTimestamp("acceptedTimestamp"));
        studentCamp.setConfirmedTimestamp(rs.getTimestamp("confirmedTimestamp"));
        studentCamp.setWithdrawnTimestamp(rs.getTimestamp("withdrawnTimestamp"));
        studentCamp.setPaidTimestamp(rs.getTimestamp("paidTimestamp"));
        studentCamp.setWaitlistedTimestamp(rs.getTimestamp("waitlistedTimestamp"));
        studentCamp.setRejectedTimestamp(rs.getTimestamp("rejectedTimestamp"));

        return studentCamp;
    }

    private static List<CampDate> getCampDates(String sql) {
        return getList(sql, null, getDatesLambda);
    }

    private static void processStudentsInCamp(PreparedStatement ps, List<Student> resultStudents) throws SQLException {
        resultStudents.addAll(getList(ps, getStudentSimpleLambda));

    }

    private static StudentCampApplication processStudentCamp(ResultSet rs, int studentID) throws SQLException {

        return new StudentCampApplication(rs.getInt("studentCampID"), studentID,
                rs.getInt("campOfferedID"), rs.getInt("accepted"), rs.getInt("rejected"), rs.getInt("waitlisted"),
                rs.getInt("confirmed"), rs.getInt("withdrawn"), rs.getInt("paid"), (double) rs.getInt("cost") / 100.0);
    }

    private static int queryInt(int studentID, int classID, String sql, String column) {
        int boolVal = 0;
        try {
            PreparedStatement ps = DatabaseConnection.getPreparedStatement(sql);
            ps.setInt(1, studentID);
            ps.setInt(2, classID);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                boolVal = rs.getInt(column);
            }
            ps.close();
        } catch (SQLException sqle) {
            System.out.println("sqle:DatabaseConnection.isWithdrawn(" + studentID + "): " + " " + classID + sqle.getMessage());
        }
        return boolVal;
    }

    //private helper methods
    private static boolean queryBool(int studentID, int classID, String sql, String column) {
        return queryInt(studentID, classID, sql, column) == 1;
    }

    // If -1 is returned, the parent does not exist in the table.
    private static Parent getParent(int ID, String where) {

        // Construct parent using the parent builder class.
        Parent.ParentBuilder builder = new ParentBuilder();

        System.out.println(SQLStatements.selectParentSQL + where);
        System.out.println("--------");

        try {
            PreparedStatement ps = DatabaseConnection.getPreparedStatement(SQLStatements.selectParentSQL + where);
            ps.setInt(1, ID);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                Address address = new Address(rs.getString("street"), rs.getString("country"), rs.getString("city"),
                        rs.getInt("stateID"), rs.getString("zip"));
                address.setState(rs.getString("state"));
                builder.parentID(rs.getInt("parentID"));
                builder.name(rs.getString("fname"), rs.getString("lname"));
                builder.phone(rs.getString("phone"));
                builder.income(rs.getString("income"));
                builder.incomeID(rs.getInt("incomeID"));
                builder.emailID(rs.getInt("emailID"));
                builder.email(rs.getString("email"));
                builder.addressID(rs.getInt("addressID"));
                builder.address(address);
                builder.uscEmployee(rs.getBoolean("uscemployee"));
                builder.uscID(rs.getString("uscID"));
            }

        } catch (SQLException sqle) {
            System.out.println("sqle:DatabaseConnection.doesLoginExist(" + "): " + sqle.getMessage());
        }

        return builder.build();
        //return parent;
    }

    private static int getID(int id, String sql, String column) {
        int queriedID = -1;
        try {
            PreparedStatement ps = DatabaseConnection.getPreparedStatement(sql);
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                queriedID = rs.getInt(column);
            }
            rs.close();
            ps.close();
        } catch (SQLException sqle) {
            System.out.println("sqle:DatabaseConnection.getParentID(): " + sqle.getMessage());
        }

        return queriedID;
    }

    private static int getStudentCount(String sql, String column, int campOfferedID) {
        return getID(campOfferedID, sql, column);
    }

    private static Staff processStaff(ResultSet rs) throws SQLException {
        Staff staff = new Staff();
        staff.setStaffID(rs.getInt("staffID"));
        staff.setFirstName(rs.getString("firstName"));
        staff.setLastName(rs.getString("lastName"));
        Email email = new Email(rs.getString("email"));
        email.setEmailID(Integer.parseInt(rs.getString("emailID")));
        staff.setEmail(email);
        staff.setTitle(rs.getString("title"));
        staff.setCompany(rs.getString("company"));
        staff.setDescription(rs.getString("description"));
        staff.setPhotoUrl(rs.getString("photoUrl"));
        staff.setInstructor(rs.getBoolean("instructor"));
        staff.setAdmin(rs.getBoolean("admin"));
        return staff;
    }

    private static List<Staff> processStaffList(PreparedStatement ps) throws SQLException {
        return getList(ps, getStaffSimpleLambda);
    }

    // if -1 is returned, the email address was not in the table already
    public static Login getLogin(int emailID) {
        int loginID = -1;
        Login login = new Login(loginID);
        try {
            PreparedStatement ps = DatabaseConnection.getPreparedStatement(SQLStatements.selectLoginSQL);
            ps.setInt(1, emailID);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                login.setEmailID(emailID);
                login.setLoginID(rs.getInt("loginID"));
                login.setPassword(rs.getString("password"));
                login.setSalt(rs.getString("salt"));
            }
            rs.close();
            ps.close();
        } catch (SQLException sqle) {
            System.out.println("sqle:DatabaseConnection.doesLoginExist(" + emailID + "): " + sqle.getMessage());
        }
        return login;
    }

    public static boolean isConfirmed(int studentID, int classID) {
        return queryBool(studentID, classID, SQLStatements.selectConfirmedStudentSQL, "confirmed");
    }

    public static boolean isConfirmed(int studentCampID) {
        try {
            PreparedStatement ps = DatabaseConnection.getPreparedStatement(SQLStatements.selectConfirmedStudentCampSQL);
            ps.setInt(1, studentCampID);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return rs.getBoolean("confirmed");
            }
            rs.close();
            ps.close();
        } catch (SQLException sqle) {
            System.out.println("sqle:DatabaseConnection.isConfirmed(" + studentCampID + "): " + sqle.getMessage());
        }
        return false;
    }

    public static boolean isAccepted(int studentID, int campOfferedID) {
        return queryBool(studentID, campOfferedID, SQLStatements.selectAcceptedStudentSQL, "accepted");
    }

    public static Double getPriceDouble(int studentID, int campOfferedID) {
        Integer cost = queryInt(studentID, campOfferedID, SQLStatements.getPriceSQL, "cost");

        return (cost == 0) ? 0.00 : cost.doubleValue() / 100.0;
    }

//		public static String getPrice(int studentID, int campOfferedID) {
//			return String.valueOf(getPriceDouble(studentID, campOfferedID));
//		}

    public static boolean isWaitlisted(int studentID, int classID) {
        return queryBool(studentID, classID, SQLStatements.selectWaitlistedStudentSQL, "waitlisted");
    }

    public static boolean isRejected(int studentID, int classID) {
        return queryBool(studentID, classID, SQLStatements.selectRejectedStudentSQL, "rejected");
    }

    public static boolean isWithdrawn(int studentID, int classID) {
        return queryBool(studentID, classID, SQLStatements.selectWithdrawnStudentSQL, "withdrawn");
    }

    public static boolean isWithdrawn(int studentCampID) {
        try {
            PreparedStatement ps = DatabaseConnection.getPreparedStatement(SQLStatements.selectWithdrawnStudentCampSQL);
            ps.setInt(1, studentCampID);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return rs.getBoolean("withdrawn");
            }
            rs.close();
            ps.close();
        } catch (SQLException sqle) {
            System.out.println("sqle:DatabaseConnection.isWithdrawn(" + studentCampID + "): " + sqle.getMessage());
        }
        return false;
    }

    public static Parent getParentByEID(int emailID) {
        return getParent(emailID, SQLStatements.selectParentByEIDWhere);
    }

    public static Parent getParentByPID(int parentID) {
        return getParent(parentID, SQLStatements.selectParentByPIDWhere);
    }

    public static int getParentID(int studentID) {
        return getID(studentID, SQLStatements.getParentID, "parentID");
    }

    public static int getEmailID(int parentID) {
        return getID(parentID, SQLStatements.getEmailID, "emailID");
    }

    public static int getEmailIDAssociatedWithStudent(int studentID) {
        int emailID = -1;
        int parentID = getParentID(studentID);

        if (parentID != -1) {
            emailID = getEmailID(parentID);
        }
        return emailID;
    }

    public static String getStudentParentName(int studentID) {
        String parentsName = "";
        int parentId = getParentID(studentID);

        try {
            PreparedStatement ps = DatabaseConnection.getPreparedStatement(SQLStatements.getParentName);
            ps.setInt(1, parentId);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                parentsName = rs.getString("fname") + " " + rs.getString("lname");
            }
        } catch (SQLException sqle) {
            System.out.println("sqle:DatabaseConnection.getStudentParentName(): " + sqle.getMessage());
        }

        return parentsName;
    }

    public static List<StudentSearchResult> searchCampers(Map<String, String> searchParams) {
        StringBuilder sb = new StringBuilder(SQLStatements.prependToSearchStudents + SQLStatements.getStudentsSQL + SQLStatements.appendToSearchStudents);
        List<StudentSearchResult> results = new ArrayList<>();

        List<String> paramValuesInOrder = new ArrayList<>();
        for (Map.Entry<String, String> param : searchParams.entrySet()) {
            paramValuesInOrder.add(param.getValue());
            sb.append(" AND ");
            sb.append(SQLStatements.searchCampersWhereMap.get(param.getKey()));
            sb.append(" = ?");
        }

        try {
            PreparedStatement ps = DatabaseConnection.getPreparedStatement(sb.toString());

            for (int i1 = 0; i1 < paramValuesInOrder.size(); i1++) {
                ps.setString(i1 + 1, paramValuesInOrder.get(i1));
            }
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Student s = DatabaseConnection.processStudent(rs);
                s.setMedForm(DatabaseQueries.getMedicalForm(s.getStudentID()));
                String processedIncome = DatabaseConnection.processIncomeString(rs.getString("income"));

                Parent parent = new Parent.ParentBuilder().parentID(rs.getInt("parentID"))
                        .name(rs.getString("pfname"), rs.getString("plname"))
                        .phone(rs.getString("phone"))
                        .emailID(rs.getInt("emailID"))
                        .email(getEmail(rs.getInt("emailID")))
                        .income(processedIncome)
                        .addressID(rs.getInt("addressID")).build();

                StudentSearchResult ssr = new StudentSearchResult(s, parent);
                results.add(ssr);

            }
            ps.close();
            rs.close();
        } catch (SQLException sqle) {
            System.out.println("sqle:DatabaseConnection.searchCampers(): " + sqle.getMessage());
        }

        return results;
    }

    public static MedicalForm getMedicalForm(int studentID) {
        MedicalForm medForm = null;

        try {
            PreparedStatement ps = DatabaseConnection.getPreparedStatement(SQLStatements.getMedicalForm);
            ps.setInt(1, studentID);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                medForm = new MedicalForm.MedicalFormBuilder()
                        .medicalFormID(rs.getInt("medicalFormID"))
                        .allergiesJSON(rs.getString("allergies"))
                        .hVisitsJSON(rs.getString("surergiesOrHospital"))
                        .illnessJSON(rs.getString("illnessesOrInjuries"))
                        .nonPMedsJSON(rs.getString("nonPrescribedMeds"))
                        .pMedsJSON(rs.getString("prescribedMeds"))
                        .insuranceCarrier(rs.getString("insuranceCarrier"))
                        .medicalFormID(rs.getInt("medicalFormID"))
                        .studentID(rs.getInt("studentID"))
                        .nameOfInsured(rs.getString("nameOfInsured"))
                        .physicianName(rs.getString("physicianName"))
                        .physicianPhone(rs.getString("physicianPhone"))
                        .policyNumber(rs.getString("policyNumber"))
                        .policyPhone(rs.getString("policyPhone"))
                        .studentFName(rs.getString("studentFName"))
                        .studentLName(rs.getString("studentLName"))
                        .parentPhone(rs.getString("parentPhone"))
                        .legalGuardianName(rs.getString("legalGuardianName"))
                        .legalGuardianAgree(rs.getString("legalGuardianAgree"))
                        .dateOfBirth(rs.getString("dateOfBirth"))
                        .ecName(rs.getString("ecName"))
                        .ecRelationship(rs.getString("ecRelationship"))
                        .ecPhone(rs.getString("ecPhone"))
                        .physicianPhoneCellOrWork(rs.getBoolean("physicianPhoneCellOrWork"))
                        .parentPhoneCellOrHome(rs.getBoolean("parentPhoneCellOrHome"))
                        .ecPhoneCellOrHome(rs.getBoolean("ecPhoneCellOrHome"))
                        .genderID(rs.getInt("genderID"))
                        .address(getAddress(rs.getInt("addressID")))
                        .build();
                if (rs.getObject("tetanusShot") != null) {
                    medForm.setTetanus(rs.getBoolean("tetanusShot"));
                }
                List<InsuranceCard> insuranceCard = getInsuranceCards(medForm.getMedicalFormID());
                if (!insuranceCard.isEmpty()) {
                    medForm.setInsuranceCard(insuranceCard.get(0));
                }
            }

        } catch (JsonSyntaxException je) {
            System.out.println("jsonException in getMedicalForm " + je.getMessage());
        } catch (SQLException se) {
            System.out.println("sqle:DatabaseQueries.getMedicalForm(): " + se.getMessage());
        }
        return medForm;
    }

    public static String getEmail(int emailID) {
        String email = "";
        try {
            PreparedStatement ps = DatabaseConnection.getPreparedStatement(SQLStatements.getEmail);
            ps.setInt(1, emailID);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                email = rs.getString("email");
            }
        } catch (SQLException sqle) {
            System.out.println("sqle:DatabaseConnection.getEmail(): " + sqle.getMessage());
        }
        return email;
    }

    public static EmergencyContact getEmergencyContact(int parentID) {

        EmergencyContact.EmergencyContactBuilder builder = new EmergencyContactBuilder();

        try {
            PreparedStatement ps = DatabaseConnection.getPreparedStatement(SQLStatements.selectEmergencyContactSQL);
            ps.setInt(1, parentID);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {

                int emailID = rs.getInt("emailID");
                String email = null;
                if (emailID != 0) {
                    email = rs.getString("email");
                }
                //String phone = rs.getString("phone");
                int addressID = rs.getInt("addressID");

                builder.emergencyContactID(rs.getInt("emergencyContactID"));
                builder.name(rs.getString("fname"), rs.getString("lname"));
                builder.phone(rs.getString("phone"));
                builder.parentID(parentID);
                builder.addressID(addressID);
                builder.address(DatabaseQueries.getAddress(addressID));
                builder.emailID(emailID);
                builder.email(email);
            }

        } catch (SQLException sqle) {
            System.out.println("sqle:DatabaseConnection.getEmergencyContact(" + parentID + "): " + sqle.getMessage());
        }

        return builder.build();
    }

    public static Address getAddress(int addressID) {

        Address address = null;
        try {
            PreparedStatement ps = DatabaseConnection.getPreparedStatement(SQLStatements.selectAddressSQL);
            ps.setInt(1, addressID);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {

                address = new Address(rs.getString("street"), rs.getString("country"), rs.getString("city"), rs.getInt("stateID"), rs.getString("zip"));
                address.setAddressID(addressID);
            }

        } catch (SQLException sqle) {
            System.out.println("sqle:DatabaseConnection.getAddress(" + addressID + "): " + sqle.getMessage());
        }

        return address;
    }

    public static Student getStudent(int studentID, boolean includeMedical) {
        Student student = null;
        try {
            PreparedStatement ps = DatabaseConnection.getPreparedStatement(SQLStatements.prependToGetStudents + SQLStatements.getStudentsSQL + " WHERE s.studentID=?");
            ps.setInt(1, studentID);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                student = DatabaseConnection.processStudent(rs);
                if (includeMedical) {
                    student.setMedForm(DatabaseQueries.getMedicalForm(studentID));
                }
            }

        } catch (SQLException sqle) {
            System.out.println("sqle:DatabaseConnection.getStudent(" + studentID + "): " + sqle.getMessage());
        }

        return student;
    }

    public static List<Student> getStudents(int parentID, boolean includeMedical) {
        if (includeMedical) {
            return getList(SQLStatements.prependToGetStudents + SQLStatements.getStudentsSQL + SQLStatements.appendToGetStudents,
                    parentID, getStudentLambdaMedical);
        } else {
            return getList(SQLStatements.prependToGetStudents + SQLStatements.getStudentsSQL + SQLStatements.appendToGetStudents,
                    parentID, getStudentLambda);
        }
    }

    public static int getReducedMealsIdFromStudentId(int studentID) {
        System.out.println("inside of getReducedMealsFromStudentId");

        try {
            PreparedStatement ps = DatabaseConnection.getPreparedStatement(SQLStatements.getReducedMealsID + " WHERE studentID=?");
            ps.setInt(1, studentID);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                int num = rs.getInt(1);
                System.out.println("num = " + num);
                return num;
//					try{
//						  int num = Integer.parseInt(rs);
//						  // is an integer!
//						} catch (NumberFormatException e) {
//						  // not an integer!
//						}
            } else {
                return -1;
            }

        } catch (SQLException sqle) {
            System.out.println("sqle:DatabaseConnection.getStudent(" + studentID + "): " + sqle.getMessage());
            return -1;
        }

    }

    public static String getStudentName(int studentID) {
        String firstName = "";
        String lastName = "";
        try {
            PreparedStatement ps = DatabaseConnection.getPreparedStatement(SQLStatements.selectSpecificStudentSQL);
            ps.setInt(1, studentID);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                firstName = rs.getString("fname");
                lastName = rs.getString("lname");
            }
        } catch (SQLException sqle) {
            System.out.println("sqle:DatabaseConnection.getStudent(" + studentID + "): " + sqle.getMessage());
        }
        return firstName + " " + lastName;
    }

    public static List<Gender> getGenders() {
        return getList(SQLStatements.selectGendersSQL, null, getGenderLambda);
    }

    public static List<Ethnicity> getEthnicities() {
        return getList(SQLStatements.selectEthnicitySQL, null, getEthnicityLambda);
    }

    public static List<State> getStates() {
        return getList(SQLStatements.selectStateSQL, null, getStateLambda);
    }

    public static List<School> getSchools() {
        return getList(SQLStatements.selectSchoolSQL, null, getSchoolLambda);
    }

    public static List<Grade> getGrades() {
        return getList(SQLStatements.selectGradeSQL, null, getGradeLambda);
    }

    public static ArrayList<java.sql.Date> getBirths() {
        ArrayList<java.sql.Date> birthArray = new ArrayList<java.sql.Date>();
        try {
            PreparedStatement ps = DatabaseConnection.getPreparedStatement(SQLStatements.selectGradeSQL);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                birthArray.add(rs.getDate("dateOfBirth"));
            }
            rs.close();
            ps.close();
        } catch (SQLException sqle) {
            System.out.println("sqle:DatabaseConnection.getGrades(): " + sqle.getMessage());
        }
        return birthArray;
    }

    public static List<ReducedMeals> getReducedMeals() {
        return getList(SQLStatements.selectReducedMealsSQL, null, getReducedMealsLambda);
    }

    public static List<Income> getIncomes() {
        return getList(SQLStatements.selectIncomesSQL, null, getIncomeLambda);
    }

    // if -1 is returned, the email address was not in the table already
    public static int doesEmailExist(String email) {
        int emailID = -1;
        try {
            PreparedStatement ps = DatabaseConnection.getPreparedStatement(SQLStatements.selectEmailSQL);
            ps.setString(1, email);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                emailID = rs.getInt("emailID");
            }
            rs.close();
            ps.close();
        } catch (SQLException sqle) {
            sqle.printStackTrace();
            System.out.println("sqle:DatabaseConnection.doesEmailExist(" + email + "): " + sqle.getMessage());
        }
        return emailID;
    }

    public static boolean isAdmin(int emailID) {
        boolean admin = false;
        try {
            PreparedStatement ps = DatabaseConnection.getPreparedStatement(SQLStatements.selectLoginAdminSQL);
            ps.setInt(1, emailID);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                admin = rs.getBoolean("admin");
            }
            rs.close();
            ps.close();
            if (admin) {
                ps = DatabaseConnection.getPreparedStatement(SQLStatements.selectLoginStaffAdminSQL);
                ps.setInt(1, emailID);
                rs = ps.executeQuery();
                if (rs.next()) {
                    admin = rs.getBoolean("admin");
                }
                rs.close();
                ps.close();
            }
        } catch (SQLException sqle) {
            System.out.println("sqle:DatabaseConnection.isAdmin(" + emailID + "): " + sqle.getMessage());
        }

        return admin;
    }

    public static List<StudentCamp> getStudentCamps(String studentCampStatus, int campOfferedID) {
        List<StudentCamp> studentCampList = new ArrayList<StudentCamp>();
        try {
            PreparedStatement ps = DatabaseConnection.getPreparedStatement(SQLStatements.studentCampStatusToSQLStatementMap.get(studentCampStatus));
            ps.setInt(1, campOfferedID);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                StudentCamp studentCamp = new StudentCamp();
                studentCamp.setStudentCampID(rs.getInt("studentCampID"));
                studentCamp.setCampOfferedID(rs.getInt("campOfferedID"));
                studentCamp.setPaid(rs.getBoolean("paid"));
                studentCamp.setRequestedTimestamp(rs.getTimestamp("requestedTimestamp"));
                studentCamp.setAcceptedTimestamp(rs.getTimestamp("acceptedTimestamp"));
                studentCamp.setConfirmedTimestamp(rs.getTimestamp("confirmedTimestamp"));
                studentCamp.setWithdrawnTimestamp(rs.getTimestamp("withdrawnTimestamp"));
                studentCamp.setPaidTimestamp(rs.getTimestamp("paidTimestamp"));
                studentCamp.setWaitlistedTimestamp(rs.getTimestamp("waitlistedTimestamp"));
                studentCamp.setRejectedTimestamp(rs.getTimestamp("rejectedTimestamp"));

                List<School> schoolList = DatabaseQueries.getSchools();
                boolean schoolExists = false;
                String schoolName = "";
                for (School school : schoolList) {
                    if (school.getSchoolID() == rs.getInt("schoolID")) {
                        schoolName = school.getSchool();
                        schoolExists = true;
                    }
                }

                if (!schoolExists) {
                    schoolName = rs.getString("otherSchool");
                }

                PreparedStatement ps2 = DatabaseConnection.getPreparedStatement(SQLStatements.getOtherInfoSQL);
                ps2.setInt(1, rs.getInt("studentID"));
                ResultSet rs2 = ps2.executeQuery();
                String otherInfo = "";
                if (rs2.next()) {
                    otherInfo = rs2.getString("otherInfo");
                }
                //Get grade reports of student
                PreparedStatement ps3 = DatabaseConnection.getPreparedStatement(SQLStatements.getAllGradeReportsByStudentIDSQL);
                ps3.setInt(1, rs.getInt("studentID"));
                ResultSet rs3 = ps3.executeQuery();
                ArrayList<GradeReport> gradeReports = new ArrayList<GradeReport>();
                while (rs3.next()) {
                    gradeReports.add(new GradeReport(rs3.getInt("gradeReportID"), rs3.getInt("studentID"), rs3.getString("filePath"), (rs3.getInt("deleted") != 0)));
                    //System.out.println("camp data: "+rs3.getString("filePath"));
                }
                //Get reduced meal verifications of student
                PreparedStatement ps6 = DatabaseConnection.getPreparedStatement(SQLStatements.getReducedMealsVerificationsByStudentIDSQL);
                ps6.setInt(1, rs.getInt("studentID"));
                ResultSet rs6 = ps6.executeQuery();
                ArrayList<ReducedMealsVerification> reducedMealsVerifications = new ArrayList<ReducedMealsVerification>();
                while (rs6.next()) {
                    reducedMealsVerifications.add(new ReducedMealsVerification(rs6.getInt("reducedMealsVerificationID"), rs6.getInt("studentID"), rs6.getString("filePath"), (rs6.getInt("deleted") != 0)));
//							System.out.println(rs6.getString("filePath"));
                }
                Student student = new Student.StudentBuilder()
                        .studentID(rs.getInt("studentID"))
                        .name(rs.getString("fname"), rs.getString("mname"), rs.getString("lname"), rs.getString("pname"))
                        .gender(new Gender(rs.getString("gender")))
                        .school(new School(schoolName))
                        .grade(new Grade(rs.getString("grade")))
                        .dob(rs.getString("dateOfBirth"))
                        .reducedMeals(new ReducedMeals(rs.getString("description")))
                        .gradeReports(gradeReports)
                        .reducedMealsVerification(reducedMealsVerifications)
                        .transportTo(rs.getBoolean("transportationTo"))
                        .transportFrom(rs.getBoolean("transportationFrom"))
                        //added experience for admin side
                        .experience(rs.getString("experience"))
                        .diet(rs.getString("diet"))
                        .ethnicity(DatabaseQueries.getEthnicitiesByStudentID(rs.getInt("studentID")))
                        .otherInfo(otherInfo)
                        .pickupCode(rs.getString("pickupCode"))
                        .build();
                studentCamp.setStudent(student);

                int parentID = DatabaseQueries.getParentID(student.getStudentID());
                int incomeID = DatabaseQueries.getIncomeIDByParentID(parentID);
                String income = DatabaseQueries.getIncomeByIncomeID(incomeID);
                studentCamp.setStudentsParentsIncome(income);

                studentCampList.add(studentCamp);
            }
            rs.close();
            ps.close();
        } catch (SQLException sqle) {
            System.out.println("sqle:StudentCampDatabaseConnection.getStudentCamps(): " + sqle.getMessage());
        }
        return studentCampList;
    }

    // NN
    public static ArrayList<StudentCamp> getCurrentCampsAppliedByStudentID(int studentID) {
        ArrayList<StudentCamp> camps = new ArrayList<StudentCamp>();
        try {
            PreparedStatement ps = DatabaseConnection.getPreparedStatement(SQLStatements.selectStudentCurrentCampsAppliedSQL);
            ps.setInt(1, studentID);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                StudentCamp camp = processStudentCampWithCampDetails(rs);
                camps.add(camp);
            }
            rs.close();
            ps.close();
        } catch (SQLException sqle) {
            System.out.println("sqle:DatabaseQueries.getCurrentCampsAppliedByStudentID(): " + sqle.getMessage());
        }
        return camps;
    }

    public static ArrayList<StudentCamp> getAllCurrentCampsAppliedByStudentID(int studentID) {
        ArrayList<StudentCamp> camps = new ArrayList<StudentCamp>();
        try {
            PreparedStatement ps = DatabaseConnection.getPreparedStatement(SQLStatements.selectAllStudentCurrentCampsAppliedSQL);
            ps.setInt(1, studentID);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                StudentCamp camp = processStudentCampWithCampDetails(rs);
                camps.add(camp);
            }
            rs.close();
            ps.close();
        } catch (SQLException sqle) {
            System.out.println("sqle:DatabaseQueries.getCurrentCampsAppliedByStudentID(): " + sqle.getMessage());
        }
        return camps;
    }

    public static ArrayList<StudentCamp> getStudentAcceptedCampsSQL(int studentID) {
        ArrayList<StudentCamp> camps = new ArrayList<StudentCamp>();
        try {
            PreparedStatement ps = DatabaseConnection.getPreparedStatement(SQLStatements.selectStudentAcceptedCampsSQL);
            ps.setInt(1, studentID);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                StudentCamp camp = processStudentCampWithCampDetails(rs);
                camps.add(camp);
            }

            rs.close();
            ps.close();
        } catch (SQLException sqle) {
            System.out.println("sqle:DatabaseQueries.getStudentApprovedCampsSQL(): " + sqle.getMessage());
        }
        return camps;
    }

    public static ArrayList<CampOffered> getPastCampsAttendedByStudentID(int studentID) {
        ArrayList<CampOffered> camps = new ArrayList<CampOffered>();
        try {
            PreparedStatement ps = DatabaseConnection.getPreparedStatement(SQLStatements.selectStudentPastAttendedCampsSQL);
            ps.setInt(1, studentID);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                CampOffered camp = new CampOffered();
                camp.setCampTopic(rs.getString("topic"));
                camp.setCampLevel(rs.getString("campLevelDescription"));
                camp.setStartDate(rs.getDate("startDate"));
                camp.setEndDate(rs.getDate("endDate"));
                camps.add(camp);
            }
            rs.close();
            ps.close();
        } catch (SQLException sqle) {
            System.out.println("sqle:DatabaseQueries.getPastCampsAttendedByStudentID(): " + sqle.getMessage());
        }
        return camps;
    }

    public static int getConfirmedStudentCount(int campOfferedID) {
        return getStudentCount(SQLStatements.selectConfirmedStudentCountSQL, "confirmedStudentCount", campOfferedID);
    }

    public static int getAppliedStudentCount(int campOfferedID) {
        return getStudentCount(SQLStatements.selectAppliedStudentCountSQL, "appliedStudentCount", campOfferedID);
    }

    public static int getAcceptedStudentCount(int campOfferedID) {
        return getStudentCount(SQLStatements.selectAcceptedStudentCountSQL, "acceptedStudentCount", campOfferedID);
    }

    public static int getWithdrawnStudentCount(int campOfferedID) {
        return getStudentCount(SQLStatements.selectWithdrawnStudentCountSQL, "withdrawnStudentCount", campOfferedID);
    }

    public static int getWaitlistedStudentCount(int campOfferedID) {
        return getStudentCount(SQLStatements.selectWaitlistedStudentCountSQL, "waitlistedStudentCount", campOfferedID);
    }

    public static int getRejectedStudentCount(int campOfferedID) {
        return getStudentCount(SQLStatements.selectRejectedStudentCountSQL, "rejectedStudentCount", campOfferedID);
    }

    public static EmailContent getEmailContent(String reason) {
        EmailContent emailContent = new EmailContent();
        try {
            PreparedStatement ps = DatabaseConnection.getPreparedStatement(SQLStatements.selectEmailContentSQL);
            ps.setString(1, reason);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                emailContent.setSubject(rs.getString("subject"));
                emailContent.setBody(rs.getString("body"));
                emailContent.setReason(rs.getString("reason"));
            }
        } catch (SQLException sqle) {
            System.out.println("sqle:StudentCampDatabaseConnection.getEmailContent(" + reason + "): " + sqle.getMessage());
        }

        return emailContent;
    }

    public static List<Staff> getStaffList() {
        return getList(SQLStatements.selectStaffSQL, null, getStaffLambda);
    }

    public static Staff getStaff(int staffID) {
        Staff staff = new Staff();
        try {
            PreparedStatement ps = DatabaseConnection.getPreparedStatement(SQLStatements.selectStaffByIDSQL);
            ps.setInt(1, staffID);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                staff = processStaff(rs);
            }
            rs.close();
            ps.close();
        } catch (SQLException sqle) {
            System.out.println("sqle:StaffDatabaseConnection.getStaff(" + staffID + "):" + sqle.getMessage());
        }

        return staff;
    }

    public static List<Staff> getCampStaff(int campID) {
        List<Staff> campStaff = new ArrayList<Staff>();
        try {
            PreparedStatement ps = DatabaseConnection.getPreparedStatement(SQLStatements.getCampStaffSQL);
            ps.setInt(1, campID);
            campStaff = processStaffList(ps);
            ps.close();
        } catch (SQLException sqle) {
            System.out.println("sqle:StaffDatabaseConnection:getCampStaff(campID = " + campID + "): " + sqle.getMessage());
        }

        return campStaff;
    }

    public static List<Staff> getAllCampStaffNames() {
        List<Staff> allCampStaff = new ArrayList<Staff>();
        try {
            PreparedStatement ps = DatabaseConnection.getPreparedStatement(SQLStatements.getAllCampStaffSQL);
            allCampStaff = processStaffList(ps);
            ps.close();
        } catch (SQLException sqle) {
            System.out.println("sqle:DatabaseQueries:getAllCampStaffNames()" + sqle.getMessage());
        }
        return allCampStaff;
    }

    public static int getStaffByName(String firstName, String lastName) {
        int staffID = -1;
        try {
            PreparedStatement ps = DatabaseConnection.getPreparedStatement(SQLStatements.getStaffByName);
            ps.setString(1, firstName);
            ps.setString(2, lastName);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                staffID = rs.getInt("staffID");
            }
            rs.close();
            ps.close();
        } catch (SQLException sqle) {
            System.out.println("sqle:StaffDatabaseConnection:getStaffByName(" + firstName + " " + lastName + "): " + sqle.getMessage());
        }

        return staffID;
    }

    public static List<CampTopic> getCampTopics() {
        return getList(SQLStatements.selectCampTopicsSQL, null, getCampTopicLambda);
    }

    public static List<CampTopic> getCurrentCampTopics() {
        return getList(SQLStatements.selectCurrentCampTopicsSQL, null, getCampTopicLambda);
    }

    public static List<CampDate> getDates() {
        return getCampDates(SQLStatements.selectDatesSQL);
    }

    public static List<CampLevel> getCampLevels() {
        return getList(SQLStatements.selectCampLevelsSQL, null, getCampLevelLambda);
    }

    public static List<CampLevel> getCurrentCampLevels() {
        return getList(SQLStatements.selectCurrentCampLevelsSQL, null, getCampLevelLambda);
    }

    // If closed boolean is true, gets all camps offered, including those that are closed. This only happens for the admin control panel.
    public static List<CampOffered> getCampsOffered(CampTopic campTopic, boolean closed) {
        return getCampsOffered(campTopic, closed, false);
    }

    public static List<CampOffered> getCampsOffered(CampTopic campTopic, boolean closed, boolean deleted) {
        List<CampOffered> campOfferedList = new ArrayList<CampOffered>();

        try {
            PreparedStatement ps;
            if (deleted) {
                ps = DatabaseConnection.getPreparedStatement(SQLStatements.selectAllCampsOfferedByIdSQL);
            } else if (closed) {
                ps = DatabaseConnection.getPreparedStatement(SQLStatements.selectCampsOfferedByIdSQL);
            } else {
                ps = DatabaseConnection.getPreparedStatement(SQLStatements.selectOpenCampsOfferedByIdSQL);
            }
            ps.setInt(1, campTopic.getCampTopicID());

            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                CampOffered campOffered = processCamp(rs);
                campOffered.setCampTAs(DatabaseQueries.getCampStaff(campOffered.getCampOfferedID())); //not fully functional
                campOfferedList.add(campOffered);
            }
            rs.close();
            ps.close();
        } catch (SQLException sqle) {
            System.out.println("sqle:CampOfferedDatabaseConnection.getCampsOffered(" + campTopic.getCampTopicID() + "):" + sqle.getMessage());
        }

        return campOfferedList;
    }

    public static List<CampOffered> getCampsOfferedByCampLevelDescription(String campLevelDescription) {
        List<CampOffered> campOfferedList = new ArrayList<CampOffered>();

        try {
            PreparedStatement ps;

            if ((null == campLevelDescription) || campLevelDescription.isEmpty()) {
                ps = DatabaseConnection.getPreparedStatement(SQLStatements.selectCampsOfferedSQL);
            } else {
                ps = DatabaseConnection.getPreparedStatement(SQLStatements.selectCampsOfferedByCampLevelDescriptionSQL);
                ps.setString(1, campLevelDescription);
            }
            return getList(ps, getCampLambda);
        } catch (SQLException sqle) {
            System.out.println("sqle:CampOfferedDatabaseConnection.getCampsOfferedByCampLevelDescription(" + campLevelDescription + "):" + sqle.getMessage());
        }

        return campOfferedList;
    }

    public static CampOffered getCampOffered(int campOfferedID) {
        CampOffered campOffered = new CampOffered();

        try {
            PreparedStatement ps = DatabaseConnection.getPreparedStatement(SQLStatements.selectCampOfferedSQL);
            ps.setInt(1, campOfferedID);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                campOffered = processCamp(rs);
            }
            rs.close();
            ps.close();

        } catch (SQLException sqle) {
            System.out.println("sqle:CampOfferedDatabaseConnection.getCampOffered(" + campOfferedID + "): " + sqle.getMessage());
        }

        return campOffered;
    }

    public static CampOffered getLatestCampOffered() {
        CampOffered campOffered = new CampOffered();

        try {
            PreparedStatement ps = DatabaseConnection.getPreparedStatement(SQLStatements.selectLatestCreatedCamp);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                campOffered = processCamp(rs);
            }
            rs.close();
            ps.close();

        } catch (SQLException sqle) {
            System.out.println("Problem with latest camp creation");
        }

        return campOffered;
    }

    public static List<CampDate> getCampStartDatesByYear() {
        return getCampDates(SQLStatements.selectCampStartDatesByCurrYearSQL);
    }

    public static List<CampOffered> getCampsOfferedByDateID(int dateID) {
        return getList(SQLStatements.selectCampsByDateIDSQL, dateID, getCampLambda);
    }

    public static List<Student> getCampsByCurrYear(String name, String topic, String difficulty, String grade) {
        //System.out.println("In the database file- topic: " + topic + " level: " + difficulty);
        List<Integer> campIDs = new ArrayList<Integer>();
        List<Student> resultStudents = new ArrayList<Student>();

        Date cDate = new Date();
        Calendar cal3 = Calendar.getInstance();
        cal3.setTime(cDate);

        Date cTestDate = cal3.getTime();

        try {
            PreparedStatement ps;
            ps = DatabaseConnection.getPreparedStatement(SQLStatements.selectCampsOfferedSQL);
            ResultSet rs = ps.executeQuery();
            int count = 0;

            while (rs.next()) {
                Date startD = rs.getDate("startDate");
                Calendar cal = Calendar.getInstance();
                cal.setTime(startD);
                cal.add(Calendar.DATE, -1);
                Date newStartD = cal.getTime();
                int campOfferedID = rs.getInt("campOfferedID");

                Date endD = rs.getDate("endDate");
                Calendar cal2 = Calendar.getInstance();
                cal2.setTime(endD);
                cal2.add(Calendar.DATE, 1);
                Date newEndD = cal2.getTime();

                if (cTestDate.after(newStartD) && cTestDate.before(newEndD)) {

                    count++;
                    if (rs.getString("campLevelDescription").equals(difficulty) || difficulty.equals("difficultyAll")) {
                        if (rs.getString("topic").equals(topic) || topic.equals("topicAll")) {
                            campIDs.add(campOfferedID);
                        }
                    }
                }

            }
            rs.close();
            ps.close();

            if (name.equals("") && grade.equals("gradeAll")) {
                //System.out.println("name and grade empty so getting all students");
                for (int i = 0; i < campIDs.size(); i++) {
                    PreparedStatement ps3;
                    ps3 = DatabaseConnection.getPreparedStatement(SQLStatements.selectStudentsByCamp);
                    ps3.setInt(1, campIDs.get(i));

                    processStudentsInCamp(ps3, resultStudents);
                    ps3.close();
                }
            } else if (name.equals("") && !grade.equals("gradeAll")) {
                //System.out.println("getting all students in grade " + grade);
                for (int i = 0; i < campIDs.size(); i++) {
                    PreparedStatement ps3;
                    ps3 = DatabaseConnection.getPreparedStatement(SQLStatements.selectStudentsByGrade);
                    ps3.setInt(1, campIDs.get(i));
                    ps3.setString(2, grade);
                    processStudentsInCamp(ps3, resultStudents);
                    ps3.close();
                }
            } else if (!name.equals("") && grade.equals("gradeAll")) {
                //	System.out.println("getting all students with last name " + name);
                for (int i = 0; i < campIDs.size(); i++) {
                    PreparedStatement ps3;
                    ps3 = DatabaseConnection.getPreparedStatement(SQLStatements.selectStudentsByLastName);
                    ps3.setInt(1, campIDs.get(i));
                    ps3.setString(2, name);

                    processStudentsInCamp(ps3, resultStudents);
                    ps3.close();
                }
            } else {
                //System.out.println("getting all students with last name " + name + " in grade " + grade);
                for (int i = 0; i < campIDs.size(); i++) {
                    PreparedStatement ps3;
                    ps3 = DatabaseConnection.getPreparedStatement(SQLStatements.selectStudentsByGradeAndLastName);
                    ps3.setInt(1, campIDs.get(i));
                    ps3.setString(2, grade);
                    ps3.setString(3, name);

                    processStudentsInCamp(ps3, resultStudents);
                    ps3.close();
                }
            }

        } catch (SQLException sqle) {
            System.out.println("sqle:CampOfferedDatabaseConnection.getCampsOfferedByDateID(" + "):" + sqle.getMessage());
        }
        System.out.print("number of students found for check out = " + resultStudents.size());
        return resultStudents;
    }

    public static boolean isInstructor(int emailID) {
        boolean admin = false;
        try {
            PreparedStatement ps = DatabaseConnection.getPreparedStatement(SQLStatements.selectLoginAdminSQL);
            ps.setInt(1, emailID);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                admin = rs.getBoolean("admin");
            }
            rs.close();
            ps.close();
            if (admin) {
                ps = DatabaseConnection.getPreparedStatement(SQLStatements.selectLoginStaffInstructorSQL);
                ps.setInt(1, emailID);
                rs = ps.executeQuery();
                if (rs.next()) {
                    admin = rs.getBoolean("instructor");
                }
                rs.close();
                ps.close();
            }
        } catch (SQLException sqle) {
            System.out.println("sqle:DatabaseConnection.isAdmin(" + emailID + "): " + sqle.getMessage());
        }

        return admin;
    }

    public static int getIncomeIDByParentID(int parentID) {
        int incomeID = -1;

        try {
            PreparedStatement ps = DatabaseConnection.getPreparedStatement(SQLStatements.getIncomeIDByParentIDSQL);
            ps.setInt(1, parentID);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                incomeID = rs.getInt("incomeID");
            }
        } catch (SQLException sqle) {
            System.out.println("sqle:DatabaseConnection.getIncomeIDByParentID(" + parentID + "): " + sqle.getMessage());
        }
        return incomeID;
    }

    public static String getIncomeByIncomeID(int incomeID) {
        String income = "";

        try {
            PreparedStatement ps = DatabaseConnection.getPreparedStatement(SQLStatements.getIncomeByIncomeIDSQL);
            ps.setInt(1, incomeID);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                income = rs.getString("income");
            }
        } catch (SQLException sqle) {
            System.out.println("sqle:DatabaseConnection.getIncomeByIncomeID(" + incomeID + "): " + sqle.getMessage());
        }
        return income;
    }

    public static List<StudentEthnicity> getStudentEthnicities() {
        return getList(SQLStatements.selectStudentEthnicitiesSQL, null, getStudentEthnicityLambda);
    }

    public static List<Ethnicity> getEthnicitiesByStudentID(int studentID) {
        List<Ethnicity> ethnicityList = new ArrayList<Ethnicity>();

        try {
            PreparedStatement ps = DatabaseConnection.getPreparedStatement(SQLStatements.selectEthnicitiesByStudentIDSQL);
            ps.setInt(1, studentID);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                Ethnicity ethnicity = new Ethnicity(rs.getString("ethnicity"));

                if (ethnicity.getEthnicity().equals("Other")) {
                    ethnicity.setEthnicity(rs.getString("otherEthnicity"));
                }

                ethnicity.setEthnicityID(rs.getInt("ethnicityID"));
                ethnicityList.add(ethnicity);
            }

        } catch (SQLException sqle) {
            System.out.println("sqle:DatabaseConnection.getStudentEthnicitiesByStudentID(" + studentID + "): " + sqle.getMessage());
        }

        return ethnicityList;
    }

    public static ArrayList<Integer> getStudentIdsByPID(int parentID) {
        ArrayList<Integer> studentIDs = new ArrayList<Integer>();

        try {
            PreparedStatement ps = DatabaseConnection.getPreparedStatement(SQLStatements.getStudentByPID);
            ps.setInt(1, parentID);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                studentIDs.add(rs.getInt("studentID"));
            }
        } catch (SQLException sqle) {
            System.out.println("sqle:DatabaseConnection.getStudentIdsbyPID(): " + sqle.getMessage());
        }
        return studentIDs;

    }

    // CC: Get applications from the database
    public static ArrayList<StudentCampApplication> getStudentCamps() {

        ArrayList<StudentCampApplication> studentCamps = new ArrayList<StudentCampApplication>();

        try {
            PreparedStatement ps = DatabaseConnection.getPreparedStatement(SQLStatements.selectStudentCampSQL);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                int studentID = rs.getInt("studentID");
                studentCamps.add(processStudentCamp(rs, studentID));

            }
            rs.close();
            ps.close();
        } catch (SQLException sqle) {
            System.out.println("sqle:DatabaseConnection.getStudentCamps(): " + sqle.getMessage());
        }

        return studentCamps;
    }

    public static ArrayList<StudentCampApplication> getStudentCampsByStudentID(int studentID) {

        ArrayList<StudentCampApplication> studentCamps = new ArrayList<StudentCampApplication>();

        try {
            PreparedStatement ps = DatabaseConnection.getPreparedStatement(SQLStatements.selectStudentCampByStudentIDSQL);
            ps.setInt(1, studentID);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                studentCamps.add(processStudentCamp(rs, studentID));
            }
            rs.close();
            ps.close();
        } catch (SQLException sqle) {
            System.out.println("sqle:DatabaseConnection.getStudentCampsByStudentID(): " + sqle.getMessage());
        }

        return studentCamps;
    }

    public static VerificationToken findToken(String token) {
        int emailID = -1;
        Timestamp expiry = null;
        boolean verified = false;
        VerificationToken verificationToken;
        try {
            PreparedStatement ps = DatabaseConnection.getPreparedStatement(SQLStatements.selectTokenSQL);
            ps.setString(1, token);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                emailID = rs.getInt("emailID");
                expiry = rs.getTimestamp("expiry");
                verified = rs.getBoolean("verified");
            }
            rs.close();
            ps.close();
        } catch (SQLException sqle) {
            System.out.println("sqle:DatabaseConnection.findToken(" + token + "): " + sqle.getMessage());
        }
        if (emailID == -1) {
            verificationToken = null;
        } else {
            verificationToken = new VerificationToken(emailID, token, expiry, verified);
        }
        return verificationToken;
    }

    public static List<String> getAllEmails() {
        List<String> allEmails = new ArrayList<>();
        try {
            PreparedStatement ps = DatabaseConnection.getPreparedStatement(SQLStatements.getAllEmailsSQL);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                String email = rs.getString("email");
                if (!allEmails.contains(email) && email.indexOf('@') > 0) {
                    allEmails.add(email);
                }
            }
            ps.close();
        } catch (SQLException sqle) {
            System.out.println("sqle:DatabaseConnection.allEmails " + sqle.getMessage());
        }
        return allEmails;
    }

    private static String getEmailPart(String sql, String column, int token) {
        String part = "";
        try {
            PreparedStatement ps = DatabaseConnection.getPreparedStatement(sql);
            ps.setInt(1, token);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                part = rs.getString(column);
            }
            ps.close();
        } catch (SQLException sqle) {
            System.out.println("sqle:DatabaseConnection.getEmail " + sqle.getMessage());
        }
        return part;
    }

    public static String getEmailContents(EmailType emailType) {
        return getEmailPart(SQLStatements.getEmailContentsSQL, "currentEmail", emailType.ordinal());
    }

    public static String getEmailSubject(EmailType emailType) {
        return getEmailPart(SQLStatements.getEmailSubjectSQL, "currentSubject", emailType.ordinal());
    }

    public static List<GradeReport> getGradeReports(Student student, boolean deletedIncluded) {
        String sql = deletedIncluded ? SQLStatements.getAllGradeReportsByStudentIDSQL : SQLStatements.getGradeReportsByStudentIDSQL;
        return getList(sql, student.getStudentID(), getGradeReportLambda);
    }

    public static List<InsuranceCard> getInsuranceCards(int medFormID, boolean deletedIncluded) {
        String sql = deletedIncluded ? SQLStatements.getAllInsuraneCardsByMedicalFormIDSQL : SQLStatements.getInsuranceCardsByMedicalFormIDSQL;
        return getList(sql, medFormID, getInsuranceCardLambda);
    }

    public static GradeReport getGradeReportByGradeReportID(int gradeReportID) {

        GradeReport gr = null;
        try {
            PreparedStatement ps = DatabaseConnection.getPreparedStatement(SQLStatements.getGradeReportByGradeReportIDSQL);
            ps.setInt(1, gradeReportID);
            ResultSet rs = ps.executeQuery();
            rs.next();
            gr = processGradeReport(rs);

        } catch (SQLException sqle) {
            System.out.println("sqle:DatabaseConnection.getGradeReportByGradeReportID(" + gradeReportID + "): " + sqle.getMessage());
        }
        return gr;
    }

    public static ReducedMealsVerification getReducedMealsVerificationByReducedMealVerificationID(int reducedMealsVerificationID) {

        ReducedMealsVerification rmv = null;
        try {
            PreparedStatement ps = DatabaseConnection.getPreparedStatement(SQLStatements.getReducedMealsVerificationByReducedMealVerificationIDSQL);
            ps.setInt(1, reducedMealsVerificationID);
            ResultSet rs = ps.executeQuery();
            rs.next();
            rmv = new ReducedMealsVerification(rs.getInt("reducedMealsVerificationID"), rs.getInt("studentID"), rs.getString("filePath"), (rs.getInt("deleted") != 0));
        } catch (SQLException sqle) {
            System.out.println("sqle:DatabaseConnection.getReducedMealsVerificationByReducedMealVerificationID(" + reducedMealsVerificationID + "): " + sqle.getMessage());
        }
        return rmv;
    }

    private static InsuranceCard processInsuranceCard(ResultSet rs) throws SQLException {
        return new InsuranceCard(rs.getInt("insuranceCardID"), rs.getInt("medicalFormID"), rs.getString("frontFilePath"), rs.getString("backFilePath"), (rs.getInt("deleted") != 0));
    }

    public static InsuranceCard getInsuranceCardByInsuranceCardID(int ID) {

        InsuranceCard rmv = null;
        try {
            PreparedStatement ps = DatabaseConnection.getPreparedStatement(SQLStatements.getInsuranceCardByInsuranceCardIDSQL);
            ps.setInt(1, ID);
            ResultSet rs = ps.executeQuery();
            rs.next();
            rmv = processInsuranceCard(rs);
        } catch (SQLException sqle) {
            System.out.println("sqle:DatabaseConnection.getInsuranceCardByInsuranceCardID(" + ID + "): " + sqle.getMessage());
        }
        return rmv;
    }

    public static List<InsuranceCard> getInsuranceCards(int medicalFormID) {
        return getInsuranceCards(medicalFormID, false);
    }

    public static List<GradeReport> getGradeReports(Student student) {
        return getGradeReports(student, false);
    }

    public static List<ReducedMealsVerification> getReducedMealsVerifications(Student student, boolean deletedIncluded) {
        String sql = deletedIncluded ? SQLStatements.getAllReducedMealsVerificationsByStudentIDSQL : SQLStatements.getReducedMealsVerificationsByStudentIDSQL;
        return getList(sql, student.getStudentID(), getReducedMealsVLambda);
    }

    public static List<ReducedMealsVerification> getReducedMealsVerifications(Student student) {
        return getReducedMealsVerifications(student, false);
    }

    public static Map<String, Integer> getStatistics2(String category, Map<String, String> subcategory) {
        Map<String, Integer> statisticsList = new TreeMap<>();
        String sql = "";
        boolean innerJoinCamp = false, innerJoinEthnicity = false, innerJoinIncome = false;

        try {
            // main selection part of query for radio button filters
            if (category.equals("all")) {
                sql += SQLStatements.selectStatisticsAll;
            } else if (category.equals("ethnicity")) {
                innerJoinEthnicity = true;
                sql += SQLStatements.selectStatisticsByEthnicity;
            } else if (category.equals("income")) {
                innerJoinIncome = true;
                sql += SQLStatements.selectStatisticsByIncome;
            } else if (category.equals("gender")) {
                sql += SQLStatements.selectStatisticsByGender;
            } else if (category.equals("topic")) {
                innerJoinCamp = true;
                sql += SQLStatements.selectStatisticsByTopic;
            } else if (category.equals("school")) {
                sql += SQLStatements.selectStatisticsBySchool;
            } else if (category.equals("mealPlan")) {
                sql += SQLStatements.selectStatisticsByReducedMeals;
            } else if (category.equals("grade")) {
                sql += SQLStatements.selectStatisticsByGrade;
            }

            // list of values to the where clause
            List<String> psValues = new ArrayList<>();
            String whereSQL = SQLStatements.where;

            // year needs student camp info
            if (subcategory.containsKey("year") && !subcategory.get("year").equals("none")) {
                // check if it already has the required table
                if (!innerJoinCamp) {
                    sql += SQLStatements.innerJoinStatisticsStudentCamp;
                    innerJoinCamp = true;
                }
                whereSQL = addFilterToQuery(whereSQL, SQLStatements.addYear, psValues, subcategory.get("year") + "%");
            }
            // gender works
            if (subcategory.containsKey("genderID") && !subcategory.get("genderID").equals("none")) {
                whereSQL = addFilterToQuery(whereSQL, SQLStatements.addGender, psValues, subcategory.get("genderID"));
            }
            // grade works
            if (subcategory.containsKey("gradeID") && !subcategory.get("gradeID").equals("none")) {
                whereSQL = addFilterToQuery(whereSQL, SQLStatements.addGrade, psValues, subcategory.get("gradeID"));
            }
            // topic needs student camp info
            if (subcategory.containsKey("topicID") && !subcategory.get("topicID").equals("none")) {
                // check if it already has the required table
                if (!innerJoinCamp) {
                    sql += SQLStatements.innerJoinStatisticsStudentCamp;
                    innerJoinCamp = true;
                }
                whereSQL = addFilterToQuery(whereSQL, SQLStatements.addTopic, psValues, subcategory.get("topicID"));
            }
            // ethnicity needs Student Ethnicity info
            if (subcategory.containsKey("ethnicityID") && !subcategory.get("ethnicityID").equals("none")) {
                // check if it already has the required table
                if (!innerJoinEthnicity) {
                    sql += SQLStatements.innerJoinStatisticsStudentEthnicity;
                    innerJoinEthnicity = true;
                }
                whereSQL = addFilterToQuery(whereSQL, SQLStatements.addEthnicity, psValues, subcategory.get("ethnicityID"));
            }
            // income needs ParentIncome and StudentParent info
            if (subcategory.containsKey("incomeID") && !subcategory.get("incomeID").equals("none")) {
                // check if it already has the required table
                if (!innerJoinIncome) {
                    sql += SQLStatements.innerJoinStatisticsStudentIncome;
                    innerJoinIncome = true;
                }
                whereSQL = addFilterToQuery(whereSQL, SQLStatements.addIncome, psValues, subcategory.get("incomeID"));
            }
            // application status - works
            if (subcategory.containsKey("applicationStatusID") && !subcategory.get("applicationStatusID").equals("none")) {
                String statusID = subcategory.get("applicationStatusID");
                String statusSQL = SQLStatements.addStatusAccepted; // default accepted - status 1
                if (statusID.equals("2")) {
                    statusSQL = SQLStatements.addStatusRejected;
                } else if (statusID.equals("3")) {
                    statusSQL = SQLStatements.addStatusConfirmed;
                } else if (statusID.equals("4")) {
                    statusSQL = SQLStatements.addStatusWaitlised;
                } else if (statusID.equals("5")) {
                    statusSQL = SQLStatements.addStatusWithdrawn;
                }

                // check if it already has the required table
                if (!innerJoinCamp) {
                    sql += SQLStatements.innerJoinStatisticsStudentCamp;
                    innerJoinCamp = true;
                }
                whereSQL = addFilterToQuery(whereSQL, statusSQL, psValues, "1");
            }
            // meal plan works
            if (subcategory.containsKey("mealPlanID") && !subcategory.get("mealPlanID").equals("none")) {
                whereSQL = addFilterToQuery(whereSQL, SQLStatements.addMealPlan, psValues, subcategory.get("mealPlanID"));
            }

            // append the where clause if filters were applied
            if (!psValues.isEmpty()) {
                sql += whereSQL;
            }
            // group by clauses
            if (category.equals("ethnicity")) {
                sql += SQLStatements.ethnicityGroupBy;
            } else if (category.equals("income")) {
                sql += SQLStatements.incomeGroupBy;
            } else if (category.equals("gender")) {
                sql += SQLStatements.genderGroupBy;
            } else if (category.equals("topic")) {
                sql += SQLStatements.topicGroupBy;
            } else if (category.equals("school")) {
                sql += SQLStatements.schoolGroupBy;
            } else if (category.equals("mealPlan")) {
                sql += SQLStatements.reducedMealsGroupBy;
            } else if (category.equals("grade")) {
                sql += SQLStatements.gradeGroupBy;
            }

            PreparedStatement ps = DatabaseConnection.getPreparedStatement(sql);

            for (int psIndex = 0; psIndex < psValues.size(); psIndex++) {
                ps.setString(psIndex + 1, psValues.get(psIndex));
            }

            ResultSet rs = ps.executeQuery();

            // Checking any data in result set
            if (!rs.isBeforeFirst()) {
                System.out.println("NO DATA");
            }

            // RADIO BUTTON FILTERS
            if (category.equals("ethnicity")) {
                while (rs.next()) {
                    int ethnicityStat = rs.getInt("ethnicityStat");
                    String ethnicity = rs.getString("ethnicity");
                    statisticsList.put(ethnicity, ethnicityStat);
                }
            } else if (category.equals("income")) {
                while (rs.next()) {
                    int incomeStat = rs.getInt("incomeStat");
                    String income = rs.getString("income");
                    statisticsList.put(income, incomeStat);
                }
            } else if (category.equals("gender")) {
                while (rs.next()) {
                    int genderStat = rs.getInt("genderStat");
                    String gender = rs.getString("gender");
                    statisticsList.put(gender, genderStat);
                }
            } else if (category.equals("topic")) {
                while (rs.next()) {
                    int topicStat = rs.getInt("topicStat");
                    String topic = rs.getString("topic");
                    statisticsList.put(topic, topicStat);
                }
            } else if (category.equals("school")) {
                while (rs.next()) {
                    int schoolStat = rs.getInt("schoolStat");
                    String school = rs.getString("school");
                    statisticsList.put(school, schoolStat);
                }
            } else if (category.equals("mealPlan")) {
                while (rs.next()) {
                    int reducedMealsStat = rs.getInt("reducedMealsStat");
                    String reducedMeals = rs.getString("description");
                    statisticsList.put(reducedMeals, reducedMealsStat);
                }
            } else if (category.equals("grade")) {
                while (rs.next()) {
                    int gradeStat = rs.getInt("gradeStat");
                    String grade = rs.getString("grade");
                    statisticsList.put(grade, gradeStat);
                }
            } else {
                while (rs.next()) {
                    int stat = rs.getInt("stat");
                    statisticsList.put("All", stat);
                }
            }
        } catch (SQLException sqle) {
            System.out.println("sqle:DatabaseConnection " + sqle.getMessage());
            System.out.println("sql " + sql);
        }

        return statisticsList;
    }

    public static Map<String, Integer> getStatistics2_Ethnicity(Map<String, String> subcategory) {
        Map<String, Integer> stats = new HashMap<>();
        stats.put("Test", 5);

        String sql = "";
        String sqlWHERE = SQLStatements.where;
        String sqlGROUPBY = "";

        try {
            // list of values to the where clause
            List<String> psValues = new ArrayList<>();

            boolean innerJoinEthnicity = false;
            boolean innerJoinIncome = false;

            // For YEAR / TOPIC / APP STATUS
            boolean innerJoinCamp = false;

            sql += "SELECT count(distinct(Student.studentID)) AS ethnicityStat, Ethnicity.ethnicity "
                    + "FROM Student INNER JOIN StudentEthnicity ON Student.studentID = StudentEthnicity.studentID "
                    + "INNER JOIN Ethnicity ON StudentEthnicity.ethnicityID = Ethnicity.ethnicityID ";

            sqlGROUPBY += " GROUP BY StudentEthnicity.ethnicityID "
                    + "ORDER BY ethnicityStat DESC;";

            // Check YEAR
            if (subcategory.containsKey("year") && !subcategory.get("year").equals("none")) {
                innerJoinCamp = true;

                sql += SQLStatements.innerJoinStatisticsStudentCamp;

                sqlWHERE = addFilterToQuery(sqlWHERE, SQLStatements.addYear, psValues, subcategory.get("year") + "%");
            }

            // Check GENDER
            if (subcategory.containsKey("genderID") && !subcategory.get("genderID").equals("none")) {
                sqlWHERE = addFilterToQuery(sqlWHERE, SQLStatements.addGender, psValues, subcategory.get("genderID"));
            }

            // Check GRADE
            if (subcategory.containsKey("gradeID") && !subcategory.get("gradeID").equals("none")) {
                sqlWHERE = addFilterToQuery(sqlWHERE, SQLStatements.addGrade, psValues, subcategory.get("gradeID"));
            }

            // Check TOPIC
            if (subcategory.containsKey("topicID") && !subcategory.get("topicID").equals("none")) {
                if (!innerJoinCamp) {
                    sql += SQLStatements.innerJoinStatisticsStudentCamp;
                    innerJoinCamp = true;
                }
                sqlWHERE = addFilterToQuery(sqlWHERE, SQLStatements.addTopic, psValues, subcategory.get("topicID"));
            }

            //
            // If filters are applied
            if (!psValues.isEmpty()) {
                sql += sqlWHERE;
            }

            sql += sqlGROUPBY;

            // Generate the prepared statement
            PreparedStatement ps = DatabaseConnection.getPreparedStatement(sql);

            // Add the PS values
            for (int psIndex = 1; psIndex <= psValues.size(); psIndex++) {
                ps.setString(psIndex, psValues.get(psIndex));
            }
            ResultSet rs = ps.executeQuery();

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
//
//        String sql = "SELECT ethnicity.ethnicity, COUNT(DISTINCT(student.studentID))\n" +
//                "FROM student\n" +
//                "INNER JOIN studentethnicity ON studentethnicity.studentID = student.studentID\n" +
//                "INNER JOIN ethnicity ON ethnicity.ethnicityID = studentethnicity.ethnicityID\n";
//        ;
//        String sqlEnd = "GROUP BY studentethnicity.ethnicityID";
//
//        // list of values to the where clause
//        List<String> psValues = new ArrayList<>();
//        String whereSQL = SQLStatements.where;
//
//        // year needs student camp info
//        if (subcategory.containsKey("year") && !subcategory.get("year").equals("none")) {
//            sql += SQLStatements.innerJoinStatisticsStudentCamp;
//            whereSQL = addFilterToQuery(whereSQL, SQLStatements.addYear, psValues, subcategory.get("year") + "%");
//        }
//
//        if (subcategory.containsKey("genderID") && !subcategory.get("genderID").equals("none")) {
//            whereSQL = addFilterToQuery(whereSQL, SQLStatements.addGender, psValues, subcategory.get("genderID"));
//        }
//
//        if (!psValues.isEmpty()) {
//            sql += whereSQL;
//        }
//
//        sql += sqlEnd;
//
//        try {
//            PreparedStatement ps = DatabaseConnection.getPreparedStatement(sql);
//
//            for (int psIndex = 0; psIndex < psValues.size(); psIndex++) {
//                ps.setString(psIndex + 1, psValues.get(psIndex));
//            }
//
//            ResultSet rs = ps.executeQuery();
//            while (rs.next()) {
//                stats.put(rs.getString(1), rs.getInt(2));
//            }
//        } catch (SQLException sqle) {
//            System.out.println("sqle:DatabaseConnection " + sqle.getMessage());
//            System.out.println("sql " + sql);
//        }

        return stats;
    }

    public static Map<String, Integer> getStatistics2_Income(Map<String, String> subcategory) {
        Map<String, Integer> incomeStats = new HashMap<>();
        String sql = "SELECT income.income, COUNT(parentincome.incomeID)\n" +
                "FROM student\n" +
                "INNER JOIN studentparent ON studentparent.studentID = student.studentID\n" +
                "INNER JOIN parentincome ON parentincome.parentID = studentparent.parentID\n" +
                "INNER JOIN income ON income.incomeID = parentincome.incomeID\n";
        String sql2 = " GROUP BY parentincome.incomeID ORDER BY income.income DESC";

        // list of values to the where clause
        List<String> psValues = new ArrayList<>();
        String whereSQL = SQLStatements.where;

        // year needs student camp info
        if (subcategory.containsKey("year") && !subcategory.get("year").equals("none")) {
            sql += SQLStatements.innerJoinStatisticsStudentCamp;
            whereSQL = addFilterToQuery(whereSQL, SQLStatements.addYear, psValues, subcategory.get("year") + "%");
        }

        if (subcategory.containsKey("genderID") && !subcategory.get("genderID").equals("none")) {
            whereSQL = addFilterToQuery(whereSQL, SQLStatements.addGender, psValues, subcategory.get("genderID"));
        }

        if (!psValues.isEmpty()) {
            sql += whereSQL;
        }

        sql += sql2;

        try {
            PreparedStatement ps = DatabaseConnection.getPreparedStatement(sql);

            for (int psIndex = 0; psIndex < psValues.size(); psIndex++) {
                ps.setString(psIndex + 1, psValues.get(psIndex));
            }

            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                incomeStats.put(rs.getString(1), rs.getInt(2));
            }
        } catch (SQLException sqle) {
            System.out.println("sqle:DatabaseConnection " + sqle.getMessage());
            System.out.println("sql " + sql);
        }

        return incomeStats;
    }

    public static Map<String, Integer> getStatistics2_Gender(Map<String, String> subcategory) {
        Map<String, Integer> genderStats = new HashMap<>();
        String sql = "SELECT gender.gender, COUNT(student.genderID)\n" +
                "FROM student\n" +
                "INNER JOIN gender ON gender.genderID = student.genderID\n";
        String sqlEnd = "GROUP BY gender.genderID";

        // list of values to the where clause
        List<String> psValues = new ArrayList<>();
        String whereSQL = SQLStatements.where;

        // year needs student camp info
        if (subcategory.containsKey("year") && !subcategory.get("year").equals("none")) {
            sql += SQLStatements.innerJoinStatisticsStudentCamp;
            whereSQL = addFilterToQuery(whereSQL, SQLStatements.addYear, psValues, subcategory.get("year") + "%");
        }

        if (subcategory.containsKey("genderID") && !subcategory.get("genderID").equals("none")) {
            whereSQL = addFilterToQuery(whereSQL, SQLStatements.addGender, psValues, subcategory.get("genderID"));
        }

        if (!psValues.isEmpty()) {
            sql += whereSQL;
        }

        sql += sqlEnd;

        try {
            PreparedStatement ps = DatabaseConnection.getPreparedStatement(sql);

            for (int psIndex = 0; psIndex < psValues.size(); psIndex++) {
                ps.setString(psIndex + 1, psValues.get(psIndex));
            }

            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                genderStats.put(rs.getString(1), rs.getInt(2));
            }
        } catch (SQLException sqle) {
            System.out.println("sqle:DatabaseConnection " + sqle.getMessage());
            System.out.println("sql " + sql);
        }

        return genderStats;
    }

    public static Map<String, Integer> getStatistics2_Topics(Map<String, String> subcategory) {
        Map<String, Integer> topicStats = new HashMap<>();
        String sql = "SELECT CampTopic.topic, count(distinct(Student.studentId)) "
                + "FROM Student INNER JOIN StudentCamp ON Student.studentID = StudentCamp.studentID "
                + "INNER JOIN CampOffered ON StudentCamp.campOfferedID = CampOffered.campOfferedID "
                + "INNER JOIN CampTopic ON CampOffered.campTopicID = CampTopic.campTopicID "
                + "INNER JOIN Date ON Date.dateID = CampOffered.campStartDateID ";

//        "FROM Student INNER JOIN StudentCamp ON Student.studentID = StudentCamp.studentID "
//        + "INNER JOIN CampOffered ON StudentCamp.campOfferedID = CampOffered.campOfferedID "
//        + "INNER JOIN CampTopic ON CampOffered.campTopicID = CampTopic.campTopicID "
//        + "INNER JOIN Date ON Date.dateID = CampOffered.campStartDateID "
        String sqlEnd = "GROUP BY campoffered.campTopicID";

        // list of values to the where clause
        List<String> psValues = new ArrayList<>();
        String whereSQL = SQLStatements.where;

        // year needs student camp info
        if (subcategory.containsKey("year") && !subcategory.get("year").equals("none")) {
//            sql += SQLStatements.innerJoinStatisticsStudentCamp;
            whereSQL = addFilterToQuery(whereSQL, SQLStatements.addYear, psValues, subcategory.get("year") + "%");
        }

        if (subcategory.containsKey("genderID") && !subcategory.get("genderID").equals("none")) {
            whereSQL = addFilterToQuery(whereSQL, SQLStatements.addGender, psValues, subcategory.get("genderID"));
        }

        if (!psValues.isEmpty()) {
            sql += whereSQL;
        }

        sql += sqlEnd;

        try {
            PreparedStatement ps = DatabaseConnection.getPreparedStatement(sql);

            for (int psIndex = 0; psIndex < psValues.size(); psIndex++) {
                ps.setString(psIndex + 1, psValues.get(psIndex));
            }

            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                topicStats.put(rs.getString(1), rs.getInt(2));
            }
        } catch (SQLException sqle) {
            System.out.println("sqle:DatabaseConnection " + sqle.getMessage());
            System.out.println("sql " + sql);
        }

        return topicStats;
    }

    public static Map<String, Integer> getStatistics2_Schools(Map<String, String> subcategory) {
        Map<String, Integer> schoolStats = new HashMap<>();
        String sql = "SELECT school.school, COUNT(student.schoolID)\n" +
                "FROM student\n" +
                "INNER JOIN school ON school.schoolID = student.schoolID\n";
        String sqlEnd = "GROUP BY student.schoolID\n" +
                "ORDER BY COUNT(student.schoolID) DESC\n" +
                "LIMIT 20";

        // list of values to the where clause
        List<String> psValues = new ArrayList<>();
        String whereSQL = SQLStatements.where;

        // year needs student camp info
        if (subcategory.containsKey("year") && !subcategory.get("year").equals("none")) {
            sql += SQLStatements.innerJoinStatisticsStudentCamp;
            whereSQL = addFilterToQuery(whereSQL, SQLStatements.addYear, psValues, subcategory.get("year") + "%");
        }

        if (subcategory.containsKey("genderID") && !subcategory.get("genderID").equals("none")) {
            whereSQL = addFilterToQuery(whereSQL, SQLStatements.addGender, psValues, subcategory.get("genderID"));
        }

        if (!psValues.isEmpty()) {
            sql += whereSQL;
        }

        sql += sqlEnd;

        try {
            PreparedStatement ps = DatabaseConnection.getPreparedStatement(sql);

            for (int psIndex = 0; psIndex < psValues.size(); psIndex++) {
                ps.setString(psIndex + 1, psValues.get(psIndex));
            }

            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                schoolStats.put(rs.getString(1), rs.getInt(2));
            }
        } catch (SQLException sqle) {
            System.out.println("sqle:DatabaseConnection " + sqle.getMessage());
            System.out.println("sql " + sql);
        }

        return schoolStats;
    }

    public static Map<String, Integer> getStatistics2_Grades(Map<String, String> subcategory) {
        Map<String, Integer> gradeStats = new HashMap<>();
        String sql = "SELECT grade.grade, COUNT(student.gradeID)\n" +
                "FROM student\n" +
                "INNER JOIN grade ON grade.gradeID = student.gradeID\n";
        String sqlEnd = "GROUP BY student.gradeID";

// list of values to the where clause
        List<String> psValues = new ArrayList<>();
        String whereSQL = SQLStatements.where;

        // year needs student camp info
        if (subcategory.containsKey("year") && !subcategory.get("year").equals("none")) {
            sql += SQLStatements.innerJoinStatisticsStudentCamp;
            whereSQL = addFilterToQuery(whereSQL, SQLStatements.addYear, psValues, subcategory.get("year") + "%");
        }

        if (subcategory.containsKey("genderID") && !subcategory.get("genderID").equals("none")) {
            whereSQL = addFilterToQuery(whereSQL, SQLStatements.addGender, psValues, subcategory.get("genderID"));
        }

        if (!psValues.isEmpty()) {
            sql += whereSQL;
        }

        sql += sqlEnd;

        try {
            PreparedStatement ps = DatabaseConnection.getPreparedStatement(sql);

            for (int psIndex = 0; psIndex < psValues.size(); psIndex++) {
                ps.setString(psIndex + 1, psValues.get(psIndex));
            }

            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                gradeStats.put(rs.getString(1), rs.getInt(2));
            }
        } catch (SQLException sqle) {
            System.out.println("sqle:DatabaseConnection " + sqle.getMessage());
            System.out.println("sql " + sql);
        }

        return gradeStats;
    }

    public static Map<String, Integer> getStatistics2_MealPlans(Map<String, String> subcategory) {
        Map<String, Integer> mealPlanStats = new HashMap<>();
        String sql = "SELECT\n" +
                "\treducedMeals.description,\n" +
                "\tCOUNT(student.reducedMealsID)\n" +
                "FROM student\n" +
                "INNER JOIN reducedMeals ON reducedMeals.reducedMealsID = student.reducedMealsID\n";
        String sqlEnd = "GROUP BY student.reducedMealsID";

        // list of values to the where clause
        List<String> psValues = new ArrayList<>();
        String whereSQL = SQLStatements.where;

        // year needs student camp info
        if (subcategory.containsKey("year") && !subcategory.get("year").equals("none")) {
            sql += SQLStatements.innerJoinStatisticsStudentCamp;
            whereSQL = addFilterToQuery(whereSQL, SQLStatements.addYear, psValues, subcategory.get("year") + "%");
        }

        if (subcategory.containsKey("genderID") && !subcategory.get("genderID").equals("none")) {
            whereSQL = addFilterToQuery(whereSQL, SQLStatements.addGender, psValues, subcategory.get("genderID"));
        }

        if (!psValues.isEmpty()) {
            sql += whereSQL;
        }

        sql += sqlEnd;

        try {
            PreparedStatement ps = DatabaseConnection.getPreparedStatement(sql);

            for (int psIndex = 0; psIndex < psValues.size(); psIndex++) {
                ps.setString(psIndex + 1, psValues.get(psIndex));
            }

            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                mealPlanStats.put(rs.getString(1), rs.getInt(2));
            }
        } catch (SQLException sqle) {
            System.out.println("sqle:DatabaseConnection " + sqle.getMessage());
            System.out.println("sql " + sql);
        }

        return mealPlanStats;
    }

    public static Map<String, Integer> getStatistics(String category, Map<String, String> subcategory) {
        Map<String, Integer> statisticsList = new TreeMap<>();
        String sql = "";
        boolean innerJoinCamp = false, innerJoinEthnicity = false, innerJoinIncome = false;

        try {
            // main selection part of query for radio button filters
            if (category.equals("all")) {
                sql += SQLStatements.selectStatisticsAll;
            } else if (category.equals("ethnicity")) {
                innerJoinEthnicity = true;
                sql += SQLStatements.selectStatisticsByEthnicity;
            } else if (category.equals("income")) {
                innerJoinIncome = true;
                sql += SQLStatements.selectStatisticsByIncome;
            } else if (category.equals("gender")) {
                sql += SQLStatements.selectStatisticsByGender;
            } else if (category.equals("topic")) {
                innerJoinCamp = true;
                sql += SQLStatements.selectStatisticsByTopic;
            } else if (category.equals("school")) {
                sql += SQLStatements.selectStatisticsBySchool;
            } else if (category.equals("mealPlan")) {
                sql += SQLStatements.selectStatisticsByReducedMeals;
            } else if (category.equals("grade")) {
                sql += SQLStatements.selectStatisticsByGrade;
            }

            // list of values to the where clause
            List<String> psValues = new ArrayList<>();
            String whereSQL = SQLStatements.where;

            // year needs student camp info
            if (subcategory.containsKey("year") && !subcategory.get("year").equals("none")) {
                // check if it already has the required table
                if (!innerJoinCamp) {
                    sql += SQLStatements.innerJoinStatisticsStudentCamp;
                    innerJoinCamp = true;
                }
                whereSQL = addFilterToQuery(whereSQL, SQLStatements.addYear, psValues, subcategory.get("year") + "%");
            }
            // gender works
            if (subcategory.containsKey("genderID") && !subcategory.get("genderID").equals("none")) {
                whereSQL = addFilterToQuery(whereSQL, SQLStatements.addGender, psValues, subcategory.get("genderID"));
            }
            // grade works
            if (subcategory.containsKey("gradeID") && !subcategory.get("gradeID").equals("none")) {
                whereSQL = addFilterToQuery(whereSQL, SQLStatements.addGrade, psValues, subcategory.get("gradeID"));
            }
            // topic needs student camp info
            if (subcategory.containsKey("topicID") && !subcategory.get("topicID").equals("none")) {
                // check if it already has the required table
                if (!innerJoinCamp) {
                    sql += SQLStatements.innerJoinStatisticsStudentCamp;
                    innerJoinCamp = true;
                }
                whereSQL = addFilterToQuery(whereSQL, SQLStatements.addTopic, psValues, subcategory.get("topicID"));
            }
            // ethnicity needs Student Ethnicity info
            if (subcategory.containsKey("ethnicityID") && !subcategory.get("ethnicityID").equals("none")) {
                // check if it already has the required table
                if (!innerJoinEthnicity) {
                    sql += SQLStatements.innerJoinStatisticsStudentEthnicity;
                    innerJoinEthnicity = true;
                }
                whereSQL = addFilterToQuery(whereSQL, SQLStatements.addEthnicity, psValues, subcategory.get("ethnicityID"));
            }
            // income needs ParentIncome and StudentParent info
            if (subcategory.containsKey("incomeID") && !subcategory.get("incomeID").equals("none")) {
                // check if it already has the required table
                if (!innerJoinIncome) {
                    sql += SQLStatements.innerJoinStatisticsStudentIncome;
                    innerJoinIncome = true;
                }
                whereSQL = addFilterToQuery(whereSQL, SQLStatements.addIncome, psValues, subcategory.get("incomeID"));
            }
            // application status - works
            if (subcategory.containsKey("applicationStatusID") && !subcategory.get("applicationStatusID").equals("none")) {
                String statusID = subcategory.get("applicationStatusID");
                String statusSQL = SQLStatements.addStatusAccepted; // default accepted - status 1
                if (statusID.equals("2")) {
                    statusSQL = SQLStatements.addStatusRejected;
                } else if (statusID.equals("3")) {
                    statusSQL = SQLStatements.addStatusConfirmed;
                } else if (statusID.equals("4")) {
                    statusSQL = SQLStatements.addStatusWaitlised;
                } else if (statusID.equals("5")) {
                    statusSQL = SQLStatements.addStatusWithdrawn;
                }

                // check if it already has the required table
                if (!innerJoinCamp) {
                    sql += SQLStatements.innerJoinStatisticsStudentCamp;
                    innerJoinCamp = true;
                }
                whereSQL = addFilterToQuery(whereSQL, statusSQL, psValues, "1");
            }
            // meal plan works
            if (subcategory.containsKey("mealPlanID") && !subcategory.get("mealPlanID").equals("none")) {
                whereSQL = addFilterToQuery(whereSQL, SQLStatements.addMealPlan, psValues, subcategory.get("mealPlanID"));
            }

            // append the where clause if filters were applied
            if (!psValues.isEmpty()) {
                sql += whereSQL;
            }
            // group by clauses
            if (category.equals("ethnicity")) {
                sql += SQLStatements.ethnicityGroupBy;
            } else if (category.equals("income")) {
                sql += SQLStatements.incomeGroupBy;
            } else if (category.equals("gender")) {
                sql += SQLStatements.genderGroupBy;
            } else if (category.equals("topic")) {
                sql += SQLStatements.topicGroupBy;
            } else if (category.equals("school")) {
                sql += SQLStatements.schoolGroupBy;
            } else if (category.equals("mealPlan")) {
                sql += SQLStatements.reducedMealsGroupBy;
            } else if (category.equals("grade")) {
                sql += SQLStatements.gradeGroupBy;
            }

            PreparedStatement ps = DatabaseConnection.getPreparedStatement(sql);

            for (int psIndex = 0; psIndex < psValues.size(); psIndex++) {
                ps.setString(psIndex + 1, psValues.get(psIndex));
            }

            ResultSet rs = ps.executeQuery();

            // Checking any data in result set
            if (!rs.isBeforeFirst()) {
                System.out.println("NO DATA");
            }

            // RADIO BUTTON FILTERS
            if (category.equals("ethnicity")) {
                while (rs.next()) {
                    int ethnicityStat = rs.getInt("ethnicityStat");
                    String ethnicity = rs.getString("ethnicity");
                    statisticsList.put(ethnicity, ethnicityStat);
                }
            } else if (category.equals("income")) {
                while (rs.next()) {
                    int incomeStat = rs.getInt("incomeStat");
                    String income = rs.getString("income");
                    statisticsList.put(income, incomeStat);
                }
            } else if (category.equals("gender")) {
                while (rs.next()) {
                    int genderStat = rs.getInt("genderStat");
                    String gender = rs.getString("gender");
                    statisticsList.put(gender, genderStat);
                }
            } else if (category.equals("topic")) {
                while (rs.next()) {
                    int topicStat = rs.getInt("topicStat");
                    String topic = rs.getString("topic");
                    statisticsList.put(topic, topicStat);
                }
            } else if (category.equals("school")) {
                while (rs.next()) {
                    int schoolStat = rs.getInt("schoolStat");
                    String school = rs.getString("school");
                    statisticsList.put(school, schoolStat);
                }
            } else if (category.equals("mealPlan")) {
                while (rs.next()) {
                    int reducedMealsStat = rs.getInt("reducedMealsStat");
                    String reducedMeals = rs.getString("description");
                    statisticsList.put(reducedMeals, reducedMealsStat);
                }
            } else if (category.equals("grade")) {
                while (rs.next()) {
                    int gradeStat = rs.getInt("gradeStat");
                    String grade = rs.getString("grade");
                    statisticsList.put(grade, gradeStat);
                }
            } else {
                while (rs.next()) {
                    int stat = rs.getInt("stat");
                    statisticsList.put("All", stat);
                }
            }
        } catch (SQLException sqle) {
            System.out.println("sqle:DatabaseConnection " + sqle.getMessage());
            System.out.println("sql " + sql);
        }

        return statisticsList;
    }

//    public static Map<String, Integer> getStatistics2_Ethnicity(Map<String, String> subcategory) {
//        Map<String, Integer> stats = new HashMap<>();
//        stats.put("Test", 5);
//
//        String sql = "";
//        String sqlWHERE = SQLStatements.where;
//        String sqlGROUPBY = "";
//
//        try {
//            // list of values to the where clause
//            List<String> psValues = new ArrayList<>();
//
//            boolean innerJoinEthnicity = false;
//            boolean innerJoinIncome = false;
//
//            // For YEAR / TOPIC / APP STATUS
//            boolean innerJoinCamp = false;
//
//            sql += "SELECT count(distinct(Student.studentID)) AS ethnicityStat, Ethnicity.ethnicity "
//                    + "FROM Student INNER JOIN StudentEthnicity ON Student.studentID = StudentEthnicity.studentID "
//                    + "INNER JOIN Ethnicity ON StudentEthnicity.ethnicityID = Ethnicity.ethnicityID ";
//
//            sqlGROUPBY += " GROUP BY StudentEthnicity.ethnicityID "
//                    + "ORDER BY ethnicityStat DESC;";
//
//            // Check YEAR
//            if (subcategory.containsKey("year") && !subcategory.get("year").equals("none")) {
//                innerJoinCamp = true;
//
//                sql += SQLStatements.innerJoinStatisticsStudentCamp;
//
//                sqlWHERE = addFilterToQuery(sqlWHERE, SQLStatements.addYear, psValues, subcategory.get("year") + "%");
//            }
//
//            // Check GENDER
//            if (subcategory.containsKey("genderID") && !subcategory.get("genderID").equals("none")) {
//                sqlWHERE = addFilterToQuery(sqlWHERE, SQLStatements.addGender, psValues, subcategory.get("genderID"));
//            }
//
//            // Check GRADE
//            if (subcategory.containsKey("gradeID") && !subcategory.get("gradeID").equals("none")) {
//                sqlWHERE = addFilterToQuery(sqlWHERE, SQLStatements.addGrade, psValues, subcategory.get("gradeID"));
//            }
//
//            // Check TOPIC
//            if (subcategory.containsKey("topicID") && !subcategory.get("topicID").equals("none")) {
//                if (!innerJoinCamp) {
//                    sql += SQLStatements.innerJoinStatisticsStudentCamp;
//                    innerJoinCamp = true;
//                }
//                sqlWHERE = addFilterToQuery(sqlWHERE, SQLStatements.addTopic, psValues, subcategory.get("topicID"));
//            }
//
//            //
//            // If filters are applied
//            if (!psValues.isEmpty()) {
//                sql += sqlWHERE;
//            }
//
//            sql += sqlGROUPBY;
//
//            // Generate the prepared statement
//            PreparedStatement ps = DatabaseConnection.getPreparedStatement(sql);
//
//            // Add the PS values
//            for (int psIndex = 1; psIndex <= psValues.size(); psIndex++) {
//                ps.setString(psIndex, psValues.get(psIndex));
//            }
//
//            System.out.println(ps);
//
//            ResultSet rs = ps.executeQuery();
//
//        } catch (SQLException throwables) {
//            throwables.printStackTrace();
//        }
////
////        String sql = "SELECT ethnicity.ethnicity, COUNT(DISTINCT(student.studentID))\n" +
////                "FROM student\n" +
////                "INNER JOIN studentethnicity ON studentethnicity.studentID = student.studentID\n" +
////                "INNER JOIN ethnicity ON ethnicity.ethnicityID = studentethnicity.ethnicityID\n";
////        ;
////        String sqlEnd = "GROUP BY studentethnicity.ethnicityID";
////
////        // list of values to the where clause
////        List<String> psValues = new ArrayList<>();
////        String whereSQL = SQLStatements.where;
////
////        // year needs student camp info
////        if (subcategory.containsKey("year") && !subcategory.get("year").equals("none")) {
////            sql += SQLStatements.innerJoinStatisticsStudentCamp;
////            whereSQL = addFilterToQuery(whereSQL, SQLStatements.addYear, psValues, subcategory.get("year") + "%");
////        }
////
////        if (subcategory.containsKey("genderID") && !subcategory.get("genderID").equals("none")) {
////            whereSQL = addFilterToQuery(whereSQL, SQLStatements.addGender, psValues, subcategory.get("genderID"));
////        }
////
////        if (!psValues.isEmpty()) {
////            sql += whereSQL;
////        }
////
////        sql += sqlEnd;
////
////        try {
////            PreparedStatement ps = DatabaseConnection.getPreparedStatement(sql);
////
////            for (int psIndex = 0; psIndex < psValues.size(); psIndex++) {
////                ps.setString(psIndex + 1, psValues.get(psIndex));
////            }
////
////            ResultSet rs = ps.executeQuery();
////            while (rs.next()) {
////                stats.put(rs.getString(1), rs.getInt(2));
////            }
////        } catch (SQLException sqle) {
////            System.out.println("sqle:DatabaseConnection " + sqle.getMessage());
////            System.out.println("sql " + sql);
////        }
//
//        return stats;
//    }
//
//    public static Map<String, Integer> getStatistics2_Income(Map<String, String> subcategory) {
//        Map<String, Integer> incomeStats = new HashMap<>();
//        String sql = "SELECT income.income, COUNT(parentincome.incomeID)\n" +
//                "FROM student\n" +
//                "INNER JOIN studentparent ON studentparent.studentID = student.studentID\n" +
//                "INNER JOIN parentincome ON parentincome.parentID = studentparent.parentID\n" +
//                "INNER JOIN income ON income.incomeID = parentincome.incomeID\n";
//        String sql2 = " GROUP BY parentincome.incomeID ORDER BY income.income DESC";
//
//        // list of values to the where clause
//        List<String> psValues = new ArrayList<>();
//        String whereSQL = SQLStatements.where;
//
//        // year needs student camp info
//        if (subcategory.containsKey("year") && !subcategory.get("year").equals("none")) {
//            sql += SQLStatements.innerJoinStatisticsStudentCamp;
//            whereSQL = addFilterToQuery(whereSQL, SQLStatements.addYear, psValues, subcategory.get("year") + "%");
//        }
//
//        if (subcategory.containsKey("genderID") && !subcategory.get("genderID").equals("none")) {
//            whereSQL = addFilterToQuery(whereSQL, SQLStatements.addGender, psValues, subcategory.get("genderID"));
//        }
//
//        if (!psValues.isEmpty()) {
//            sql += whereSQL;
//        }
//
//        sql += sql2;
//
//        try {
//            PreparedStatement ps = DatabaseConnection.getPreparedStatement(sql);
//
//            for (int psIndex = 0; psIndex < psValues.size(); psIndex++) {
//                ps.setString(psIndex + 1, psValues.get(psIndex));
//            }
//
//            ResultSet rs = ps.executeQuery();
//            while (rs.next()) {
//                incomeStats.put(rs.getString(1), rs.getInt(2));
//            }
//        } catch (SQLException sqle) {
//            System.out.println("sqle:DatabaseConnection " + sqle.getMessage());
//            System.out.println("sql " + sql);
//        }
//
//        return incomeStats;
//    }
//
//    public static Map<String, Integer> getStatistics2_Gender(Map<String, String> subcategory) {
//        Map<String, Integer> genderStats = new HashMap<>();
//        String sql = "SELECT gender.gender, COUNT(student.genderID)\n" +
//                "FROM student\n" +
//                "INNER JOIN gender ON gender.genderID = student.genderID\n";
//        String sqlEnd = "GROUP BY gender.genderID";
//
//        // list of values to the where clause
//        List<String> psValues = new ArrayList<>();
//        String whereSQL = SQLStatements.where;
//
//        // year needs student camp info
//        if (subcategory.containsKey("year") && !subcategory.get("year").equals("none")) {
//            sql += SQLStatements.innerJoinStatisticsStudentCamp;
//            whereSQL = addFilterToQuery(whereSQL, SQLStatements.addYear, psValues, subcategory.get("year") + "%");
//        }
//
//        if (subcategory.containsKey("genderID") && !subcategory.get("genderID").equals("none")) {
//            whereSQL = addFilterToQuery(whereSQL, SQLStatements.addGender, psValues, subcategory.get("genderID"));
//        }
//
//        if (!psValues.isEmpty()) {
//            sql += whereSQL;
//        }
//
//        sql += sqlEnd;
//
//        try {
//            PreparedStatement ps = DatabaseConnection.getPreparedStatement(sql);
//
//            for (int psIndex = 0; psIndex < psValues.size(); psIndex++) {
//                ps.setString(psIndex + 1, psValues.get(psIndex));
//            }
//
//            ResultSet rs = ps.executeQuery();
//            while (rs.next()) {
//                genderStats.put(rs.getString(1), rs.getInt(2));
//            }
//        } catch (SQLException sqle) {
//            System.out.println("sqle:DatabaseConnection " + sqle.getMessage());
//            System.out.println("sql " + sql);
//        }
//
//        return genderStats;
//    }

//    public static Map<String, Integer> getStatistics2_Topics(Map<String, String> subcategory) {
//        Map<String, Integer> topicStats = new HashMap<>();
//        String sql = "SELECT CampTopic.topic, count(distinct(Student.studentId)) "
//                + "FROM Student INNER JOIN StudentCamp ON Student.studentID = StudentCamp.studentID "
//                + "INNER JOIN CampOffered ON StudentCamp.campOfferedID = CampOffered.campOfferedID "
//                + "INNER JOIN CampTopic ON CampOffered.campTopicID = CampTopic.campTopicID "
//                + "INNER JOIN Date ON Date.dateID = CampOffered.campStartDateID ";
//
////        "FROM Student INNER JOIN StudentCamp ON Student.studentID = StudentCamp.studentID "
////        + "INNER JOIN CampOffered ON StudentCamp.campOfferedID = CampOffered.campOfferedID "
////        + "INNER JOIN CampTopic ON CampOffered.campTopicID = CampTopic.campTopicID "
////        + "INNER JOIN Date ON Date.dateID = CampOffered.campStartDateID "
//        String sqlEnd = "GROUP BY campoffered.campTopicID";
//
//        // list of values to the where clause
//        List<String> psValues = new ArrayList<>();
//        String whereSQL = SQLStatements.where;
//
//        // year needs student camp info
//        if (subcategory.containsKey("year") && !subcategory.get("year").equals("none")) {
////            sql += SQLStatements.innerJoinStatisticsStudentCamp;
//            whereSQL = addFilterToQuery(whereSQL, SQLStatements.addYear, psValues, subcategory.get("year") + "%");
//        }
//
//        if (subcategory.containsKey("genderID") && !subcategory.get("genderID").equals("none")) {
//            whereSQL = addFilterToQuery(whereSQL, SQLStatements.addGender, psValues, subcategory.get("genderID"));
//        }
//
//        if (!psValues.isEmpty()) {
//            sql += whereSQL;
//        }
//
//        sql += sqlEnd;
//
//        try {
//            PreparedStatement ps = DatabaseConnection.getPreparedStatement(sql);
//
//            for (int psIndex = 0; psIndex < psValues.size(); psIndex++) {
//                ps.setString(psIndex + 1, psValues.get(psIndex));
//            }
//
//            ResultSet rs = ps.executeQuery();
//            while (rs.next()) {
//                topicStats.put(rs.getString(1), rs.getInt(2));
//            }
//        } catch (SQLException sqle) {
//            System.out.println("sqle:DatabaseConnection " + sqle.getMessage());
//            System.out.println("sql " + sql);
//        }
//
//        return topicStats;
//    }

//    public static Map<String, Integer> getStatistics2_Schools(Map<String, String> subcategory) {
//        Map<String, Integer> schoolStats = new HashMap<>();
//        String sql = "SELECT school.school, COUNT(student.schoolID)\n" +
//                "FROM student\n" +
//                "INNER JOIN school ON school.schoolID = student.schoolID\n";
//        String sqlEnd = "GROUP BY student.schoolID\n" +
//                "ORDER BY COUNT(student.schoolID) DESC\n" +
//                "LIMIT 20";
//
//        // list of values to the where clause
//        List<String> psValues = new ArrayList<>();
//        String whereSQL = SQLStatements.where;
//
//        // year needs student camp info
//        if (subcategory.containsKey("year") && !subcategory.get("year").equals("none")) {
//            sql += SQLStatements.innerJoinStatisticsStudentCamp;
//            whereSQL = addFilterToQuery(whereSQL, SQLStatements.addYear, psValues, subcategory.get("year") + "%");
//        }
//
//        if (subcategory.containsKey("genderID") && !subcategory.get("genderID").equals("none")) {
//            whereSQL = addFilterToQuery(whereSQL, SQLStatements.addGender, psValues, subcategory.get("genderID"));
//        }
//
//        if (!psValues.isEmpty()) {
//            sql += whereSQL;
//        }
//
//        sql += sqlEnd;
//
//        try {
//            PreparedStatement ps = DatabaseConnection.getPreparedStatement(sql);
//
//            for (int psIndex = 0; psIndex < psValues.size(); psIndex++) {
//                ps.setString(psIndex + 1, psValues.get(psIndex));
//            }
//
//            ResultSet rs = ps.executeQuery();
//            while (rs.next()) {
//                schoolStats.put(rs.getString(1), rs.getInt(2));
//            }
//        } catch (SQLException sqle) {
//            System.out.println("sqle:DatabaseConnection " + sqle.getMessage());
//            System.out.println("sql " + sql);
//        }
//
//        return schoolStats;
//    }

//    public static Map<String, Integer> getStatistics2_Grades(Map<String, String> subcategory) {
//        Map<String, Integer> gradeStats = new HashMap<>();
//        String sql = "SELECT grade.grade, COUNT(student.gradeID)\n" +
//                "FROM student\n" +
//                "INNER JOIN grade ON grade.gradeID = student.gradeID\n";
//        String sqlEnd = "GROUP BY student.gradeID";
//
//// list of values to the where clause
//        List<String> psValues = new ArrayList<>();
//        String whereSQL = SQLStatements.where;
//
//        // year needs student camp info
//        if (subcategory.containsKey("year") && !subcategory.get("year").equals("none")) {
//            sql += SQLStatements.innerJoinStatisticsStudentCamp;
//            whereSQL = addFilterToQuery(whereSQL, SQLStatements.addYear, psValues, subcategory.get("year") + "%");
//        }
//
//        if (subcategory.containsKey("genderID") && !subcategory.get("genderID").equals("none")) {
//            whereSQL = addFilterToQuery(whereSQL, SQLStatements.addGender, psValues, subcategory.get("genderID"));
//        }
//
//        if (!psValues.isEmpty()) {
//            sql += whereSQL;
//        }
//
//        sql += sqlEnd;
//
//        try {
//            PreparedStatement ps = DatabaseConnection.getPreparedStatement(sql);
//
//            for (int psIndex = 0; psIndex < psValues.size(); psIndex++) {
//                ps.setString(psIndex + 1, psValues.get(psIndex));
//            }
//
//            ResultSet rs = ps.executeQuery();
//            while (rs.next()) {
//                gradeStats.put(rs.getString(1), rs.getInt(2));
//            }
//        } catch (SQLException sqle) {
//            System.out.println("sqle:DatabaseConnection " + sqle.getMessage());
//            System.out.println("sql " + sql);
//        }
//
//        return gradeStats;
//    }

//public static Map<String, Integer> getStatistics2_MealPlans(Map<String, String> subcategory){
//        Map<String, Integer> mealPlanStats=new HashMap<>();
//        String sql="SELECT\n"+
//        "\treducedMeals.description,\n"+
//        "\tCOUNT(student.reducedMealsID)\n"+
//        "FROM student\n"+
//        "INNER JOIN reducedMeals ON reducedMeals.reducedMealsID = student.reducedMealsID\n";
//        String sqlEnd="GROUP BY student.reducedMealsID";
//
//        // list of values to the where clause
//        List<String> psValues=new ArrayList<>();
//        String whereSQL=SQLStatements.where;
//
//        // year needs student camp info
//        if(subcategory.containsKey("year")&&!subcategory.get("year").equals("none")){
//        sql+=SQLStatements.innerJoinStatisticsStudentCamp;
//        whereSQL=addFilterToQuery(whereSQL,SQLStatements.addYear,psValues,subcategory.get("year")+"%");
//        }
//
//        if(subcategory.containsKey("genderID")&&!subcategory.get("genderID").equals("none")){
//        whereSQL=addFilterToQuery(whereSQL,SQLStatements.addGender,psValues,subcategory.get("genderID"));
//        }
//
//        if(!psValues.isEmpty()){
//        sql+=whereSQL;
//        }
//
//        sql+=sqlEnd;
//
//        try{
//        PreparedStatement ps=DatabaseConnection.getPreparedStatement(sql);
//
//        for(int psIndex=0;psIndex<psValues.size();psIndex++){
//        ps.setString(psIndex+1,psValues.get(psIndex));
//        }
//
//        ResultSet rs=ps.executeQuery();
//        while(rs.next()){
//        mealPlanStats.put(rs.getString(1),rs.getInt(2));
//        }
//        }catch(SQLException sqle){
//        System.out.println("sqle:DatabaseConnection "+sqle.getMessage());
//        System.out.println("sql "+sql);
//        }
//
//        return mealPlanStats;
//        }
//
//public static Map<String, Integer> getStatistics(String category,Map<String, String> subcategory){
//        Map<String, Integer> statisticsList=new TreeMap<>();
//        String sql="";
//        boolean innerJoinCamp=false,innerJoinEthnicity=false,innerJoinIncome=false;
//
//        try{
//        // main selection part of query for radio button filters
//        if(category.equals("all")){
//        sql+=SQLStatements.selectStatisticsAll;
//        }else if(category.equals("ethnicity")){
//        innerJoinEthnicity=true;
//        sql+=SQLStatements.selectStatisticsByEthnicity;
//        }else if(category.equals("income")){
//        innerJoinIncome=true;
//        sql+=SQLStatements.selectStatisticsByIncome;
//        }else if(category.equals("gender")){
//        sql+=SQLStatements.selectStatisticsByGender;
//        }else if(category.equals("topic")){
//        innerJoinCamp=true;
//        sql+=SQLStatements.selectStatisticsByTopic;
//        }else if(category.equals("school")){
//        sql+=SQLStatements.selectStatisticsBySchool;
//        }else if(category.equals("mealPlan")){
//        sql+=SQLStatements.selectStatisticsByReducedMeals;
//        }else if(category.equals("grade")){
//        sql+=SQLStatements.selectStatisticsByGrade;
//        }
//
//        // list of values to the where clause
//        List<String> psValues=new ArrayList<>();
//        String whereSQL=SQLStatements.where;
//
//        // year needs student camp info
//        if(subcategory.containsKey("year")&&!subcategory.get("year").equals("none")){
//        // check if it already has the required table
//        if(!innerJoinCamp){
//        sql+=SQLStatements.innerJoinStatisticsStudentCamp;
//        innerJoinCamp=true;
//        }
//        whereSQL=addFilterToQuery(whereSQL,SQLStatements.addYear,psValues,subcategory.get("year")+"%");
//        }
//        // gender works
//        if(subcategory.containsKey("genderID")&&!subcategory.get("genderID").equals("none")){
//        whereSQL=addFilterToQuery(whereSQL,SQLStatements.addGender,psValues,subcategory.get("genderID"));
//        }
//        // grade works
//        if(subcategory.containsKey("gradeID")&&!subcategory.get("gradeID").equals("none")){
//        whereSQL=addFilterToQuery(whereSQL,SQLStatements.addGrade,psValues,subcategory.get("gradeID"));
//        }
//        // topic needs student camp info
//        if(subcategory.containsKey("topicID")&&!subcategory.get("topicID").equals("none")){
//        // check if it already has the required table
//        if(!innerJoinCamp){
//        sql+=SQLStatements.innerJoinStatisticsStudentCamp;
//        innerJoinCamp=true;
//        }
//        whereSQL=addFilterToQuery(whereSQL,SQLStatements.addTopic,psValues,subcategory.get("topicID"));
//        }
//        // ethnicity needs Student Ethnicity info
//        if(subcategory.containsKey("ethnicityID")&&!subcategory.get("ethnicityID").equals("none")){
//        // check if it already has the required table
//        if(!innerJoinEthnicity){
//        sql+=SQLStatements.innerJoinStatisticsStudentEthnicity;
//        innerJoinEthnicity=true;
//        }
//        whereSQL=addFilterToQuery(whereSQL,SQLStatements.addEthnicity,psValues,subcategory.get("ethnicityID"));
//        }
//        // income needs ParentIncome and StudentParent info
//        if(subcategory.containsKey("incomeID")&&!subcategory.get("incomeID").equals("none")){
//        // check if it already has the required table
//        if(!innerJoinIncome){
//        sql+=SQLStatements.innerJoinStatisticsStudentIncome;
//        innerJoinIncome=true;
//        }
//        whereSQL=addFilterToQuery(whereSQL,SQLStatements.addIncome,psValues,subcategory.get("incomeID"));
//        }
//        // application status - works
//        if(subcategory.containsKey("applicationStatusID")&&!subcategory.get("applicationStatusID").equals("none")){
//        String statusID=subcategory.get("applicationStatusID");
//        String statusSQL=SQLStatements.addStatusAccepted; // default accepted - status 1
//        if(statusID.equals("2")){
//        statusSQL=SQLStatements.addStatusRejected;
//        }else if(statusID.equals("3")){
//        statusSQL=SQLStatements.addStatusConfirmed;
//        }else if(statusID.equals("4")){
//        statusSQL=SQLStatements.addStatusWaitlised;
//        }else if(statusID.equals("5")){
//        statusSQL=SQLStatements.addStatusWithdrawn;
//        }
//
//        // check if it already has the required table
//        if(!innerJoinCamp){
//        sql+=SQLStatements.innerJoinStatisticsStudentCamp;
//        innerJoinCamp=true;
//        }
//        whereSQL=addFilterToQuery(whereSQL,statusSQL,psValues,"1");
//        }
//        // meal plan works
//        if(subcategory.containsKey("mealPlanID")&&!subcategory.get("mealPlanID").equals("none")){
//        whereSQL=addFilterToQuery(whereSQL,SQLStatements.addMealPlan,psValues,subcategory.get("mealPlanID"));
//        }
//
//        // append the where clause if filters were applied
//        if(!psValues.isEmpty()){
//        sql+=whereSQL;
//        }
//        // group by clauses
//        if(category.equals("ethnicity")){
//        sql+=SQLStatements.ethnicityGroupBy;
//        }else if(category.equals("income")){
//        sql+=SQLStatements.incomeGroupBy;
//        }else if(category.equals("gender")){
//        sql+=SQLStatements.genderGroupBy;
//        }else if(category.equals("topic")){
//        sql+=SQLStatements.topicGroupBy;
//        }else if(category.equals("school")){
//        sql+=SQLStatements.schoolGroupBy;
//        }else if(category.equals("mealPlan")){
//        sql+=SQLStatements.reducedMealsGroupBy;
//        }else if(category.equals("grade")){
//        sql+=SQLStatements.gradeGroupBy;
//        }
//
//        PreparedStatement ps=DatabaseConnection.getPreparedStatement(sql);
//
//        for(int psIndex=0;psIndex<psValues.size();psIndex++){
//        ps.setString(psIndex+1,psValues.get(psIndex));
//        }
//
//        ResultSet rs=ps.executeQuery();
//
//        // Checking any data in result set
//        if(!rs.isBeforeFirst()){
//        System.out.println("NO DATA");
//        }
//
//        // RADIO BUTTON FILTERS
//        if(category.equals("ethnicity")){
//        while(rs.next()){
//        int ethnicityStat=rs.getInt("ethnicityStat");
//        String ethnicity=rs.getString("ethnicity");
//        statisticsList.put(ethnicity,ethnicityStat);
//        }
//        }else if(category.equals("income")){
//        while(rs.next()){
//        int incomeStat=rs.getInt("incomeStat");
//        String income=rs.getString("income");
//        statisticsList.put(income,incomeStat);
//        }
//        }else if(category.equals("gender")){
//        while(rs.next()){
//        int genderStat=rs.getInt("genderStat");
//        String gender=rs.getString("gender");
//        statisticsList.put(gender,genderStat);
//        }
//        }else if(category.equals("topic")){
//        while(rs.next()){
//        int topicStat=rs.getInt("topicStat");
//        String topic=rs.getString("topic");
//        statisticsList.put(topic,topicStat);
//        }
//        }else if(category.equals("school")){
//        while(rs.next()){
//        int schoolStat=rs.getInt("schoolStat");
//        String school=rs.getString("school");
//        statisticsList.put(school,schoolStat);
//        }
//        }else if(category.equals("mealPlan")){
//        while(rs.next()){
//        int reducedMealsStat=rs.getInt("reducedMealsStat");
//        String reducedMeals=rs.getString("description");
//        statisticsList.put(reducedMeals,reducedMealsStat);
//        }
//        }else if(category.equals("grade")){
//        while(rs.next()){
//        int gradeStat=rs.getInt("gradeStat");
//        String grade=rs.getString("grade");
//        statisticsList.put(grade,gradeStat);
//        }
//        }else{
//        while(rs.next()){
//        int stat=rs.getInt("stat");
//        statisticsList.put("All",stat);
//        }
//        }
//        }catch(SQLException sqle){
//        System.out.println("sqle:DatabaseConnection "+sqle.getMessage());
//        System.out.println("sql "+sql);
//        }
//
//        return statisticsList;
//        }

    public static String addFilterToQuery(String sql, String SQLFilterStatement, List<String> psValues, String filter) {
        if (!psValues.isEmpty()) {
            sql += SQLStatements.and;
        }
        sql += SQLFilterStatement + "? ";
        psValues.add(filter);
        return sql;
    }

    public static boolean getCheckIfSameWeek(int startDateID) {
        boolean editable = false;
        try {
            String sql = SQLStatements.getDateByDateIDSQL;
            PreparedStatement ps = DatabaseConnection.getPreparedStatement(sql);
            ps.setInt(1, startDateID);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Calendar currentCalendar = Calendar.getInstance();
                int week = currentCalendar.get(Calendar.WEEK_OF_YEAR);
                int year = currentCalendar.get(Calendar.YEAR);
                Calendar targetCalendar = Calendar.getInstance();
                targetCalendar.setTime(rs.getDate("date"));
                int targetWeek = targetCalendar.get(Calendar.WEEK_OF_YEAR);
                int targetYear = targetCalendar.get(Calendar.YEAR);
                return week == targetWeek && year == targetYear;
            }
        } catch (SQLException sqle) {
            System.out.println("sqle:DatabaseConnection.getCheckIfSameWeek: " + sqle.getMessage());
        }

        return editable;
    }

    public static int getStartDateIdFomClassId(int classID) {
        int startDateid = 0;
        try {
            String sql = "SELECT campStartDateID FROM CampOffered where campOfferedID = ";
            sql += classID;
            PreparedStatement ps = DatabaseConnection.getPreparedStatement(sql);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                startDateid = rs.getInt("campStartDateID");
            }
        } catch (SQLException sqle) {
            System.out.println("sqle:DatabaseConnection.getStudentsByLastName: " + sqle.getMessage());
        }
        return startDateid;
    }

    public static List<Student> getNamesFromClassID(int classID) {
        List<Student> studentNames = new ArrayList<>();

        try {

            String sql = SQLStatements.getNamesFromClassID;
            sql += " " + classID;
            sql += " and StudentCamp.withdrawn = 0 and StudentCamp.confirmed = 1";

            PreparedStatement ps = DatabaseConnection.getPreparedStatement(sql);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                int studentID = rs.getInt("studentID");
                String fname = rs.getString("fname");
                fname = fname.replaceAll("\\s", "|");
                String mname = "mname";
                mname = mname.replaceAll("\\s", "|");
                String lname = rs.getString("lname");
                lname = lname.replaceAll("\\s", "|");
                String pname = "pname";
                pname = pname.replaceAll("\\s", "|");
//                Student student = new Student.StudentBuilder().studentID(studentID).name(fname, /*mname,*/ lname/*, pname*/).build();
                Student student = new Student.StudentBuilder().studentID(studentID).name(fname, mname, lname, pname).build();
                studentNames.add(student);
            }
            rs.close();
            ps.close();
        } catch (SQLException sqle) {
            System.out.println("sqle:DatabaseConnection.getStudentsByLastName: " + sqle.getMessage());
        }
        return studentNames;
    }

    public static List<Integer> getClassIds(String topic, String diff, String grade, String id) {
        List<Integer> classIds = new ArrayList<Integer>();

        String sql = SQLStatements.baseGetClassIds;
        boolean first = true;
        if (!topic.equals("topicAll")) {
            sql += SQLStatements.topicID;
            sql += topic;
            first = false;
        }
        if (!diff.equals("difficultyAll")) {
            if (!first) {
                sql += " and ";
            }
            sql += SQLStatements.difficultyID;
            sql += diff;
            first = false;
        }
        if (!grade.equals("gradeAll")) {
            if (!first) {
                sql += " and ";
            }
            sql += SQLStatements.gradeID;
            sql += grade;
            first = false;
        }
        if (!id.equals("idAll")) {
            if (!first) {
                sql += " and ";
            }
            sql += SQLStatements.classID;
            sql += id;
            first = false;
        }
        System.out.println("sql: " + sql);
        Date date = new Date();
        int currYear = date.getYear();
        PreparedStatement ps = DatabaseConnection.getPreparedStatement(sql);
        try {
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                classIds.add(rs.getInt("campOfferedID"));
            }
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        List<Integer> newClassIds = new ArrayList<Integer>();
        for (int i = 0; i < classIds.size(); i++) {
            sql = "SELECT date FROM Date inner join CampOffered on Date.DateID = CampOffered.campStartDateID where CampOffered.campOfferedID = ";
            sql += classIds.get(i);
            PreparedStatement ps1 = DatabaseConnection.getPreparedStatement(sql);
            ResultSet rs1;
            try {
                rs1 = ps1.executeQuery();
                while (rs1.next()) {
                    if (rs1.getDate("date").getYear() == currYear) {
                        newClassIds.add(classIds.get(i));
                    }
                }
            } catch (SQLException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }

        }
        return newClassIds;
    }

    public static List<Student> getStudentsByLastName(String name) {
        List<Student> studentList = new ArrayList<Student>();
        try {
            PreparedStatement ps = DatabaseConnection.getPreparedStatement(SQLStatements.selectStudentsByLastName);
            ps.setString(1, name);
            studentList = getList(ps, getStudentSimpleLambda);

        } catch (SQLException sqle) {
            System.out.println("sqle:DatabaseConnection.getStudentsByLastName: " + sqle.getMessage());
        }
        return studentList;
    }

    public static boolean verifyStudentParentName(String pickupCode, int studentId) {
        String parentName = getStudentParentName(studentId);

        return pickupCode.equalsIgnoreCase(parentName);
    }

    public static boolean verifyPickupCode(String pickupCode, int studentId) {
        boolean isCorrect = false;
        try {
            PreparedStatement ps = DatabaseConnection.getPreparedStatement(SQLStatements.getStudentPickupCode);
            ps.setInt(1, studentId);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                String cCode = rs.getString("pickupCode");
                isCorrect = pickupCode.equals(cCode);
            }

        } catch (SQLException sqle) {
            System.out.println("sqle:DatabaseConnection.getStudentPickupCode: " + sqle.getMessage());
        }

        return isCorrect;
    }

    public static String getPickupCode(String sID) {
        String cCode = "";
        try {
            PreparedStatement ps = DatabaseConnection.getPreparedStatement(SQLStatements.getStudentPickupCode);
            ps.setString(1, sID);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                cCode = rs.getString("pickupCode");
            }
        } catch (SQLException sqle) {
            System.out.println("sqle:DatabaseConnection.getStudentPickupCode: " + sqle.getMessage());
        }

        return cCode;
    }

    public static boolean verifyUniversalCode(String pCode) {

        boolean isUniversal = false;
        try {
            PreparedStatement ps = DatabaseConnection.getPreparedStatement(SQLStatements.getUniversalCodeSQL);

            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                String cUniversal = rs.getString("pickupCode");
                if (cUniversal != null) { //string is not null
                    if (cUniversal.equals(pCode)) {
                        isUniversal = true;
                        break;
                    }
                }
            }
            ps.close();
            rs.close();
        } catch (SQLException sqle) {
            System.out.println("sqle:DatabaseConnection.verifyUniversalCode: " + sqle.getMessage());
        }

        return isUniversal;
    }

    public static Map<Integer, Ethnicity> getMappedEthnicities() {
        Map<Integer, Ethnicity> map = new HashMap<>();
        //List<T> l = new ArrayList<>();
        try {
            PreparedStatement ps = DatabaseConnection.getPreparedStatement(SQLStatements.selectEthnicitySQL);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                map.put(rs.getInt("ethnicityID"), new Ethnicity(rs.getInt("ethnicityID"), rs.getString("ethnicity"), rs.getString("timestamp")));
            }
            rs.close();
            ps.close();
        } catch (SQLException sqle) {
            System.out.println("sqle:CampOfferedDatabaseConnection.getCampStartDatesByYear(): " + sqle.getMessage());
        }

        return map;
    }


    public static Student getStudentForMedForm(int studentID) {
        Student student = new Student();
        try {
            PreparedStatement ps = DatabaseConnection.getPreparedStatement(SQLStatements.getStudentForMedFormSQL);
            ps.setInt(1, studentID);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {

                String gender = rs.getString("gender");
                Gender genderObj = new Gender(rs.getInt("genderID"),
                        ((gender == null) || (gender.isEmpty()) ? "Other" : gender));
                student = new Student.StudentBuilder().name(rs.getString("fname"), rs.getString("mname"), rs.getString("lname"), rs.getString("pname"))
                        .gender(genderObj)
                        .dob(rs.getString("dateOfBirth"))
                        .diet(rs.getString("diet"))
                        .experience(rs.getString("experience"))
                        .transportTo(rs.getBoolean("transportationTo"))
                        .transportFrom(rs.getBoolean("transportationFrom"))
                        .interestedGirlsCamp(rs.getBoolean("interestedGirlsCamp"))
                        .ethnicity(DatabaseQueries.getEthnicitiesByStudentID(studentID))
                        .build();
                student.setStudentID(studentID);
            }
            rs.close();
            ps.close();
        } catch (SQLException sqle) {
            System.out.println("sqle:DatabaseQueries.getStudentForMedForm(): " + sqle.getMessage());
        }
        return student;
    }

    public static Staff getStaffByEmailID(int emailID) {
        Staff staff = new Staff();
        try {
            PreparedStatement ps = DatabaseConnection.getPreparedStatement(SQLStatements.selectStaffByEmailIDSQL);
            ps.setInt(1, emailID);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {

                staff.setStaffID(rs.getInt("staffID"));
                staff.setFirstName(rs.getString("firstName"));
                staff.setLastName(rs.getString("lastName"));
            }
            rs.close();
            ps.close();
        } catch (SQLException sqle) {
            System.out.println("sqle:DatabaseQueries.getStudentForMedForm(): " + sqle.getMessage());
        }
        return staff;
    }

    @FunctionalInterface
    public interface CheckedFunction<T, R> {
        R apply(T t) throws SQLException;
    }

}
