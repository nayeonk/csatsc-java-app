package database;

import data.StringConstants;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SQLStatements {
    static final String getStudentsSQL = " s.*, gr.grade, g.gender, sch.school, rm.description as reducedMeals, oi.otherInfo "
            + "FROM Student s "
            + "INNER JOIN StudentParent sp ON  s.studentID = sp.studentID "
            + "LEFT JOIN Gender g ON s.genderID = g.genderID "
            + "LEFT JOIN School sch ON s.schoolID = sch.schoolID "
            + "LEFT JOIN ReducedMeals rm ON s.reducedMealsID = rm.reducedMealsID "
            + "LEFT JOIN OtherInfo oi ON s.studentID = oi.studentID "
            + "LEFT JOIN Grade gr ON s.gradeID = gr.gradeID ";

    static final String getStudentForMedFormSQL = " SELECT s.*, g.gender "
            + "FROM Student s "
            + "LEFT JOIN Gender g ON s.genderID = g.genderID "
            + "WHERE s.studentID = ?";

    static final String getStudentByPID = " SELECT studentID FROM studentparent WHERE parentID=?";

    static final String appendToSearchStudents = " LEFT JOIN ParentIncome pi ON sp.parentID = pi.parentID "
            + "LEFT JOIN Income i ON pi.incomeID = i.incomeID "
            + "INNER JOIN Parent p ON sp.parentID = p.parentID "
            + "INNER JOIN Email e ON p.emailID = e.emailID ";
    static final String appendToGetStudents = " WHERE sp.parentID = ?";

    static final String prependToSearchStudents = "SELECT DISTINCT p.parentID, p.fname as pfname, p.lname as plname, p.emailID, p.addressID, p.phone, i.income, ";
    static final String prependToGetStudents = "SELECT ";

    static final String databaseDriver = "com.mysql.cj.jdbc.Driver";

//    static final String databaseConnectionString = String.format(
//            "jdbc:mysql://%s:%s/summercamps?user=%s&password=%s&serverTimezone=America/Los_Angeles",
//            System.getenv("DB_HOST"),
//            System.getenv("DB_PORT"),
//            System.getenv("DB_USER"),
//            System.getenv("DB_PASSWORD")
//
//    );

    //static final String databaseConnectionString = "jdbc:mysql://localhost:3306/summercamps?user=csatsc_admin&password=aStA7kOO9@iVsZ0/&serverTimezone=America/Los_Angeles";
    static final String databaseConnectionString = "jdbc:mysql://localhost:3306/summercamps?user=root&password=password&serverTimezone=America/Los_Angeles";


    static final String getNamesFromClassID = "SELECT fname, lname, Student.studentID FROM Student "
            + "INNER JOIN StudentCamp ON Student.studentID = StudentCamp.studentID "
            + "INNER JOIN CampOffered ON StudentCamp.campofferedID = CampOffered.campofferedID "
            + "WHERE confirmed = 1 AND CampOffered.campofferedID = ";
    static final String baseGetClassIds = "SELECT DISTINCT StudentCamp.campOfferedID FROM StudentCamp "
            + "INNER JOIN CampOffered ON StudentCamp.campofferedID = campOffered.campofferedID "
            + "WHERE ";
    static final String topicID = "CampOffered.camptopicID = ";
    static final String difficultyID = "CampOffered.campLevelID = ";
    static final String gradeID = "";
    static final String classID = "CampOffered.campOfferedID = ";

    static final String selectStatisticsAll = "SELECT count(distinct(Student.studentID)) AS 'stat' FROM Student ";
    static final String selectStatisticsByEthnicity = "SELECT count(distinct(Student.studentID)) AS 'ethnicityStat', Ethnicity.ethnicity "
            + "FROM Student INNER JOIN StudentEthnicity ON Student.studentID = StudentEthnicity.studentID "
            + "INNER JOIN Ethnicity ON StudentEthnicity.ethnicityID = Ethnicity.ethnicityID ";
    static final String selectStatisticsByIncome = "SELECT count(distinct(Student.studentId)) AS 'incomeStat', Income.income "
            + "FROM Student INNER JOIN StudentParent ON StudentParent.studentID = Student.studentID "
            + "INNER JOIN ParentIncome ON ParentIncome.parentID = StudentParent.parentID "
            + "INNER JOIN Income ON ParentIncome.incomeID = Income.incomeID ";
    static final String selectStatisticsByGender = "SELECT count(distinct(Student.studentId)) AS 'genderStat', Gender.gender "
            + "FROM Student INNER JOIN Gender ON Student.genderID = Gender.genderID ";
    static final String selectStatisticsByTopic = "SELECT count(distinct(Student.studentId)) AS 'topicStat', CampTopic.topic "
            + "FROM Student INNER JOIN StudentCamp ON Student.studentID = StudentCamp.studentID "
            + "INNER JOIN CampOffered ON StudentCamp.campOfferedID = CampOffered.campOfferedID "
            + "INNER JOIN CampTopic ON CampOffered.campTopicID = CampTopic.campTopicID "
            + "INNER JOIN Date ON Date.dateID = CampOffered.campStartDateID ";
    static final String selectStatisticsBySchool = "SELECT count(distinct(Student.studentId)) AS 'schoolStat', School.school "
            + "FROM Student INNER JOIN School ON Student.schoolID = School.schoolID ";
    static final String selectStatisticsByGrade = "SELECT count(distinct(Student.studentId)) AS 'gradeStat', Grade.grade "
            + "FROM Student INNER JOIN Grade ON Student.gradeID = Grade.gradeID ";
    static final String selectStatisticsByReducedMeals = "SELECT count(distinct(Student.studentId)) AS 'reducedMealsStat', ReducedMeals.description "
            + "FROM Student INNER JOIN ReducedMeals ON Student.reducedMealsID = ReducedMeals.reducedMealsID ";

    static final String innerJoinStatisticsStudentCamp = "INNER JOIN StudentCamp ON Student.studentID = StudentCamp.studentID "
            + "INNER JOIN CampOffered ON StudentCamp.campOfferedID = CampOffered.campOfferedID "
            + "INNER JOIN Date ON Date.dateID = CampOffered.campStartDateID ";
    static final String innerJoinStatisticsStudentEthnicity = "INNER JOIN StudentEthnicity ON StudentEthnicity.studentID = Student.studentID ";
    static final String innerJoinStatisticsStudentIncome = "INNER JOIN StudentParent ON StudentParent.studentID = Student.studentID "
            + "INNER JOIN ParentIncome ON ParentIncome.parentID = StudentParent.parentID ";

    static final String where = "WHERE ";
    static final String and = " AND ";
    static final String addGender = "Student.genderID = ";
    static final String addGrade = "Student.gradeID = ";
    static final String addMealPlan = "Student.reducedMealsID = ";
    static final String addWeek = "CampOffered.campStartDateID = ";
    static final String addTopic = "CampOffered.campTopicID = ";
    static final String addYear = "Date.date LIKE ";
    static final String addEthnicity = "StudentEthnicity.ethnicityID = ";
    static final String addIncome = "ParentIncome.incomeID = ";
    static final String addStatusAccepted = "StudentCamp.accepted = ";
    static final String addStatusWaitlised = "StudentCamp.waitlisted = ";
    static final String addStatusRejected = "StudentCamp.rejected = ";
    static final String addStatusConfirmed = "StudentCamp.confirmed = ";
    static final String addStatusWithdrawn = "StudentCamp.withdrawn = ";

    static final String ethnicityGroupBy = " GROUP BY StudentEthnicity.ethnicityID "
            + "ORDER BY count(StudentEthnicity.studentId) DESC;";
    static final String incomeGroupBy = " GROUP BY ParentIncome.incomeID "
            + "ORDER BY count(ParentIncome.incomeID);";
    static final String genderGroupBy = " GROUP BY Student.genderID "
            + "ORDER BY count(student.genderID) DESC;";
    static final String topicGroupBy = "GROUP BY CampOffered.campTopicID " +
            "ORDER BY count(CampOffered.campTopicID) DESC;";
    static final String schoolGroupBy = " GROUP BY Student.schoolID "
            + "ORDER BY count(Student.schoolID) DESC;";
    static final String gradeGroupBy = " GROUP BY Student.gradeID "
            + "ORDER BY count(Student.gradeID) DESC;";
    static final String reducedMealsGroupBy = " GROUP BY Student.reducedMealsID "
            + "ORDER BY count(Student.reducedMealsID) DESC;";

    static final String insertEmailSQL = "INSERT IGNORE INTO Email (email) "
            + "VALUES (?)";
    static final String selectEmailSQL = "SELECT * FROM Email "
            + "WHERE email=?";
    static final String selectEmailContentSQL = "SELECT * FROM EmailContent "
            + "WHERE reason=?";
    static final String insertLoginSQL = "INSERT IGNORE INTO Login (emailID, password, salt, admin) "
            + "VALUES (?, ?, ?, ?)";
    static final String insertLoginAuditSQL = "INSERT INTO LoginAudit (usernameEntered, passwordEntered, authenticated, timestamp) "
            + "VALUES (?, ?, ?, ?)";
    static final String selectLoginAdminSQL = "SELECT Admin FROM Login "
            + "WHERE emailID=?";
    static final String selectLoginStaffAdminSQL = "SELECT admin FROM Staff "
            + "WHERE emailID=?";
    static final String selectLoginStaffInstructorSQL = "SELECT instructor FROM Staff "
            + "WHERE emailID=?";
    static final String selectLoginSQL = "SELECT * FROM Login "
            + "WHERE emailID=?";
    static final String deleteLoginSQL = "DELETE FROM Login "
            + "WHERE emailID=?";
    static final String selectStudentsSQL = "SELECT * FROM Student";
    static final String selectSpecificStudentSQL = "SELECT * FROM Student "
            + "WHERE studentID =?";
    static final String selectStudentParentSQL = "SELECT * FROM StudentParent";
    static final String selectGendersSQL = "SELECT * FROM Gender";
    static final String selectEthnicitySQL = "SELECT * FROM Ethnicity";
    static final String selectStateSQL = "SELECT * FROM State";
    static final String selectSchoolSQL = "SELECT * FROM School";
    static final String selectGradeSQL = "SELECT * FROM Grade";
    static final String selectReducedMealsSQL = "SELECT * FROM ReducedMeals";
    static final String selectStudentCampSQL = "SELECT * FROM StudentCamp";
    static final String selectStudentCampByStudentIDSQL = "SELECT * FROM StudentCamp "
            + "WHERE studentID=?";

    static final String selectIncomesSQL = "SELECT * FROM Income";

    static final String selectParentSQL = "SELECT p.*, e.email, i.income, i.incomeID, a.street, a.country, a.city, a.stateID, a.zip, s.state "
            + "FROM Parent p "
            + "LEFT JOIN Address a ON p.addressID = a.addressID "
            + "LEFT JOIN State s ON a.stateID = s.stateID "
            + "LEFT JOIN ParentIncome pi ON p.parentID = pi.parentID "
            + "LEFT JOIN Income i ON pi.incomeID = i.incomeID "
            + "LEFT JOIN Email e on p.emailID = e.emailID ";

    static final String selectParentByPIDWhere = "WHERE p.parentID = ?";
    static final String selectParentByEIDWhere = "WHERE p.emailID = ?";

    static final String selectEmergencyContactSQL = "SELECT ec.*, e.email FROM EmergencyContact ec "
            + "LEFT JOIN Email e ON ec.emailID = e.emailID "
            + "WHERE ec.parentID=?";
    static final String selectAddressSQL = "SELECT * FROM Address "
            + "WHERE addressID=?";

    static final String getGenderSQL = "SELECT * FROM Gender "
            + "WHERE gender=?";
    static final String getGradeSQL = "SELECT * FROM Grade "
            + "WHERE grade=?";

    static final String getEthnicitySQL = "SELECT * FROM Ethnicity "
            + "WHERE ethnicity=?";
    static final String getSchoolSQL = "SELECT * FROM School "
            + "WHERE school=?";
    static final String getReducedMealsSQL = "SELECT * FROM ReducedMeals "
            + "WHERE reducedMeals=?";
    static final String getOtherInfoSQL = "SELECT * FROM OtherInfo "
            + "WHERE studentID=?";
    static final String getStudentPickupCode = "SELECT pickupCode FROM Student "
            + "WHERE studentID=?";
    static final String getUniversalCodeSQL = "SELECT * FROM UniversalPickupCode "
            + "ORDER BY universalCodeID desc";

    static final String updateParentSQL = "UPDATE Parent SET fname=?, lname=?, phone=? "
            + "WHERE emailID=?";
    static final String updateParentIncomeSQL = "UPDATE ParentIncome SET incomeID=? "
            + "WHERE parentID=?";
    static final String updateEmergencyContactSQL = "UPDATE EmergencyContact SET fname=?, lname=?, phone=?, emailID=? "
            + "WHERE parentID=?";
    //	static final String updateEmergencyContactEmailSQL = "UPDATE EmergencyContact SET emailID=? "
//			+ "WHERE emergencyContactID=?";
//	static final String removeEmergencyContactEmailSQL = "UPDATE EmergencyContact SET emailID=null "
//			+ "WHERE emergencyContactID=?";
    static final String updateStudentSQL = "UPDATE Student "
            + "SET fname=?, lname=?, mname=?,pname=?,semail=?, genderID=?, schoolID=?, otherSchool=?, dateOfBirth=?, reducedMealsID=?, experience=?, diet=?, interestedGirlsCamp=?, transportationTo=?, transportationFrom=?, lastUpdatedYear=?, attended=? "
            + "WHERE studentID=?";
    static final String updateStudentProgress = "UPDATE Student SET progress=? WHERE studentID=?";
    static final String updateStudentCamperSchoolsSQL = "UPDATE CamperSchools SET schoolID = ? WHERE studentID = ? AND year = ?";
    static final String updateAddressSQL = "UPDATE Address SET street=?, country=?, city=?, stateID=?, zip=? "
            + "WHERE addressID=?";
    static final String modifyOtherInfoSQL = "UPDATE OtherInfo SET otherInfo=?, timestamp=? "
            + "WHERE studentID=?";
    static final String insertOtherInfoSQL = "INSERT INTO OtherInfo (otherInfo, timestamp, studentID) "
            + "VALUES(?, ?, ?)";
    static final String updatePickupCodeSQL = "UPDATE Student SET pickupCode=? "
            + "WHERE studentID=?";
    static final String updateUniversalCodeSQL = "INSERT INTO UniversalPickupCode (pickupCode) "
            + "VALUES (?)";

    static final String insertGenderSQL = "INSERT INTO Gender (gender) "
            + "VALUES (?)";
    static final String insertStudentParentSQL = "INSERT INTO StudentParent (studentID, parentID) "
            + "VALUES (?, ?)";

    static final String insertStudentSQL = "INSERT INTO Student (fname, lname, mname, pname, semail, genderID, schoolID, otherSchool, dateOfBirth, reducedMealsID, experience, diet, interestedGirlsCamp, transportationTo, transportationFrom, gradeID, legalAgreement, pickupCode, lastUpdatedYear, progress, attended) "
            + "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
    static final String insertStudentCamperSchoolsSQL = "INSERT INTO CamperSchools (studentID, fname, lname, schoolID, year)"
            + "VALUES (?,?,?,?,?)";
    static final String modifyStudentReducedMealsSQL = "UPDATE Student SET reducedmealsdirectory = ? "
            + "WHERE studentID=?";

    static final String insertStudentEthnicitiesSQL = "INSERT INTO StudentEthnicity (studentID, ethnicityID, valid) "
            + "VALUES (?, ?, ?)";
    static final String insertStudentEthnicitiesOtherSQL = "INSERT INTO StudentEthnicity (studentID, ethnicityID, valid, otherEthnicity) "
            + "VALUES (?, ?, ?, ?)";
    static final String invalidateStudentEthnicitiesSQL = "UPDATE StudentEthnicity SET valid=0 "
            + "WHERE studentID=?";
    static final String selectStudentCamperSQL = "SELECT * FROM CamperSchools WHERE studentID = ? AND year = ?";
    static final String selectStudentEthnicitiesSQL = "SELECT * FROM StudentEthnicity";
    static final String selectEthnicitiesByStudentIDSQL = "SELECT * FROM StudentEthnicity "
            + "INNER JOIN Student ON Student.studentID = StudentEthnicity.studentID "
            + "INNER JOIN Ethnicity ON Ethnicity.ethnicityID = StudentEthnicity.ethnicityID "
            + "WHERE StudentEthnicity.studentID=? and valid=1";

    static final String insertParentSQL = "INSERT INTO Parent (fname, lname, phone, emailID, addressID, uscemployee, uscID) "
            + "VALUES (?, ?, ?, ?, ?, ?, ?)";
    static final String insertAddressSQL = "INSERT INTO Address (street, country, city, stateID, zip) "
            + "VALUES (?, ?, ?, ?, ?)";
    static final String insertParentIncomeSQL = "INSERT INTO ParentIncome (incomeID, parentID, timestamp) "
            + "VALUES(?, ?, ?)";

    static final String getIncomeIDByParentIDSQL = "SELECT incomeID from ParentIncome "
            + "WHERE parentID=?";
    static final String getIncomeByIncomeIDSQL = "SELECT income from Income "
            + "WHERE incomeID=?";

//	static final String getEmergencyContactEmailIDSQL = "SELECT emailID from EmergencyContact "
//			+ "WHERE emergencyContactID=?";

    static final String insertEmergencyContactSQL = "INSERT INTO EmergencyContact (fname, lname, phone, emailID, parentID, addressID) "
            + "VALUES (?, ?, ?, ?, ?, ?)";

//	static final String insertECWithEmail = "(fname, lname, phone, addressID, parentID, ) "
//			+ "VALUES (?, ?, ?, ?, ?, ?)";

    static final String selectCampTopicsSQL = "SELECT * FROM CampTopic";
    static final String selectCurrentCampTopicsSQL = "SELECT CampTopic.* FROM CampTopic " +
            "INNER JOIN CampOffered c ON CampTopic.campTopicID=c.campTopicID " +
            "INNER JOIN Date d ON d.dateID = c.campEndDateID " +
            "WHERE d.date >= NOW() GROUP BY camptopicID;";
    static final String selectCampLevelsSQL = "SELECT * FROM CampLevel";
    static final String selectCurrentCampLevelsSQL = " SELECT CampLevel.* FROM CampLevel " +
            "INNER JOIN CampOffered c ON CampLevel.campLevelID=c.campLevelID " +
            "INNER JOIN Date d ON d.dateID = c.campEndDateID " +
            "WHERE d.date >= NOW() GROUP BY campLevelID;";
    static final String selectDatesSQL = "SELECT * FROM Date";

    static final String getDateByDateIDSQL = "SELECT date from Date"
            + " WHERE dateID=?";

    static final String insertGradeReportSQL = "INSERT INTO GradeReport (studentID, filePath, deleted)"
            + "VALUES (?, ?, 0)";

    static final String insertInsuranceCardSQL = "INSERT INTO InsuranceCard (medicalFormID, frontFilePath, backFilePath, deleted)"
            + "VALUES (?, ?, ?, 0)";

    static final String getAllInsuraneCardsByMedicalFormIDSQL = "SELECT * FROM InsuranceCard "
            + "WHERE medicalFormID=? ORDER BY insuranceCardID DESC";
    static final String getInsuranceCardsByMedicalFormIDSQL = "SELECT * FROM InsuranceCard "
            + "WHERE medicalFormID=? AND deleted=0 ORDER BY insuranceCardID DESC";
    static final String getInsuranceCardByInsuranceCardIDSQL = "SELECT * FROM InsuranceCard "
            + "WHERE insuranceCardID=?";
    static final String deleteInsuranceCardSQL = "UPDATE InsuranceCard SET deleted=1 "
            + "WHERE insuranceCardID=?";

    static final String getAllGradeReportsByStudentIDSQL = "SELECT * FROM GradeReport "
            + "WHERE studentID=? ORDER BY gradeReportID DESC";
    static final String getGradeReportsByStudentIDSQL = "SELECT * FROM GradeReport "
            + "WHERE studentID=? AND deleted=0 ORDER BY gradeReportID DESC";
    static final String getGradeReportByGradeReportIDSQL = "SELECT * FROM GradeReport "
            + "WHERE gradeReportID=?";
    static final String deleteGradeReportSQL = "UPDATE GradeReport SET deleted=1 "
            + "WHERE gradeReportID=?";

    static final String insertReducedMealsVerificationSQL = "INSERT INTO ReducedMealsVerification (studentID, filePath, deleted)"
            + "VALUES (?, ?, 0)";
    static final String getAllReducedMealsVerificationsByStudentIDSQL = "SELECT * FROM ReducedMealsVerification "
            + "WHERE studentID=? ORDER BY reducedMealsVerificationID DESC";
    static final String getReducedMealsVerificationsByStudentIDSQL = "SELECT * FROM ReducedMealsVerification "
            + "WHERE studentID=? AND deleted=0 ORDER BY reducedMealsVerificationID DESC";
    static final String deleteReducedMealsVerificationSQL = "UPDATE ReducedMealsVerification SET deleted=1 "
            + "WHERE reducedMealsVerificationID=?";
    static final String getReducedMealsVerificationByReducedMealVerificationIDSQL = "SELECT * FROM ReducedMealsVerification "
            + "WHERE reducedMealsVerificationID=?";
    static final String getReducedMealsID = "SELECT reducedMealsVerificationID FROM summercamps.ReducedMealsVerification";
    static final String selectCampStartDatesByCurrYearSQL = "SELECT DISTINCT Date.dateID, Date.date "
            + "FROM Date "
            + "INNER JOIN CampOffered "
            + "ON Date.dateID = CampOffered.campStartDateID "
            + "WHERE YEAR(Date.date) = YEAR(now()) "
            + "ORDER BY date ASC";

    static final String selectCampsByDateIDSQL = "SELECT *, "
            + "dateStart.date as startDate, dateEnd.date as endDate, "
            + "gradeLow.grade as  recommendedGradeLow, gradeHigh.grade as  recommendedGradeHigh "
            + "FROM CampOffered "
            + "INNER JOIN Date dateStart "
            + "ON dateStart.dateID = CampOffered.campStartDateID "
            + "INNER JOIN Date dateEnd "
            + "ON dateEnd.dateID = CampOffered.campEndDateID "
            + "INNER JOIN Grade gradeLow "
            + "ON gradeLow.gradeID = CampOffered.recommendedGradeLowID "
            + "INNER JOIN Grade gradeHigh "
            + "ON gradeHigh.gradeID = CampOffered.recommendedGradeHighID "
            + "INNER JOIN CampLevel ON CampLevel.campLevelID = CampOffered.campLevelID "
            + "INNER JOIN CampTopic ON CampTopic.campTopicID = CampOffered.campTopicID "
            + "WHERE campStartDateID=? "
            + "ORDER BY dateStart.date ASC";

    static final String selectStudentCampDatesSQL = "SELECT * "
            + "FROM StudentCamp "
            + "INNER JOIN CampOffered "
            + "ON CampOffered.campOfferedID = StudentCamp.campOfferedID "
            + "INNER JOIN Date ON Date.dateID = CampOffered.campStartDateID "
            + "WHERE studentID=?";
    static final String insertCampTopicSQL = "INSERT IGNORE INTO CampTopic (topic) "
            + "VALUES (?)";
    static final String insertCampLevelSQL = "INSERT IGNORE INTO CampLevel (campLevelDescription) "
            + "VALUES (?)";
    static final String insertDateSQL = "INSERT IGNORE INTO Date (date) "
            + "VALUES (?)";
    static final String insertCampOfferedSQL = "INSERT IGNORE INTO CampOffered (campTopicID, campLevelID, campStartDateID, campEndDateID, studentCapacity, description, recommendedGrade, recommendedGradeLowID, recommendedGradeHighID, cost, paid, imageLink, remote, offeredTA, startTime, endTime, days) "
            + "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
    static final String updateCampOfferedSQL = "UPDATE CampOffered "
            + "SET campTopicID=?, campLevelID=?, campStartDateID=?, campEndDateID=?, studentCapacity=?, description=?, recommendedGrade=?, recommendedGradeLowID=?, recommendedGradeHighID=?, cost=?, paid=?, imageLink=?, remote=?, offeredTA = ?, startTime=?, endTime=?, days=? "
            + "WHERE CampOfferedID=?";
    //aaron change
    static final String updateCampOfferedClosedSQL = "UPDATE CampOffered SET closed=1 "
            + "WHERE CampOfferedID=?";
    static final String updateCampOfferedOpenedSQL = "UPDATE CampOffered SET closed=0 "
            + "WHERE CampOfferedID=?";
    static final String updateCampOfferedDeletedSQL = "UPDATE CampOffered SET deleted=1 "
            + "WHERE CampOfferedID=?";
    static final String selectConfirmedStudentCountSQL = "SELECT COUNT(confirmed) AS confirmedStudentCount "
            + "FROM StudentCamp "
            + "WHERE confirmed=1 AND withdrawn=0 AND campOfferedID=?";
    static final String selectAppliedStudentCountSQL = "SELECT COUNT(campOfferedID) AS appliedStudentCount "
            + "FROM StudentCamp "
            + "WHERE accepted = 0 AND waitlisted = 0 AND rejected = 0 AND confirmed=0 AND withdrawn=0 AND campOfferedID=?";
    static final String selectAcceptedStudentCountSQL = "SELECT COUNT(accepted) AS acceptedStudentCount "
            + "FROM StudentCamp "
            + "WHERE accepted = 1 AND confirmed=0 AND withdrawn=0 AND campOfferedID=?";
    static final String selectRejectedStudentCountSQL = "SELECT COUNT(rejected) AS rejectedStudentCount "
            + "FROM StudentCamp "
            + "WHERE rejected=1 AND withdrawn=0 AND campOfferedID=?";
    static final String selectWithdrawnStudentCountSQL = "SELECT COUNT(withdrawn) AS withdrawnStudentCount "
            + "FROM StudentCamp "
            + "WHERE withdrawn=1 AND campOfferedID=?";
    static final String selectWaitlistedStudentCountSQL = "SELECT COUNT(waitlisted) AS waitlistedStudentCount "
            + "FROM StudentCamp "
            + "WHERE waitlisted=1 AND withdrawn=0 AND campOfferedID=?";
    static final String selectConfirmedStudentSQL = "SELECT confirmed FROM StudentCamp "
            + "WHERE withdrawn=0 AND studentID=? AND campOfferedID=?";
    static final String selectConfirmedStudentCampSQL = "SELECT confirmed FROM StudentCamp "
            + "WHERE withdrawn=0 AND studentCampID=?";
    static final String selectAcceptedStudentSQL = "SELECT accepted FROM StudentCamp "
            + "WHERE withdrawn=0 AND studentID=? AND campOfferedID=?";
    static final String getPriceSQL = "SELECT cost FROM StudentCamp "
            + "WHERE withdrawn=0 AND studentID=? AND campOfferedID=?";
    static final String selectWaitlistedStudentSQL = "SELECT waitlisted FROM StudentCamp "
            + "WHERE withdrawn=0 AND studentID=? AND campOfferedID=?";
    static final String selectRejectedStudentSQL = "SELECT rejected FROM StudentCamp "
            + "WHERE withdrawn=0 AND studentID=? AND campOfferedID=?";
    static final String selectWithdrawnStudentSQL = "SELECT withdrawn FROM StudentCamp "
            + "WHERE studentID=? AND campOfferedID=?";
    static final String selectWithdrawnStudentCampSQL = "SELECT withdrawn FROM StudentCamp "
            + "WHERE studentCampID=?";
    static final String getAllEmailsSQL = "SELECT email FROM Email";
    static final String updateStudentHistory = "INSERT INTO StudentCampHistory (campOfferedID, studentID, status) "
            + "VALUES (?, ?, ?)";
    static final String updatStudentCampCostSQL = "UPDATE StudentCamp SET cost=? "
            + "WHERE studentID=? AND campOfferedID=? AND withdrawn=0";
    static final String getParentID = "SELECT * FROM StudentParent "
            + "WHERE studentID =?";
    static final String getIncomeIDSQL = "SELECT incomeID FROM Income "
            + "WHERE income=?";
    static final String getParentName = "SELECT * FROM Parent "
            + "WHERE parentID = ?";
    static final String getEmailID = "SELECT * FROM Parent "
            + "WHERE parentID = ?";
    static final String getEmail = "SELECT * FROM Email "
            + "WHERE emailID = ?";
    static final String updateLoginSQL = "UPDATE Login SET password=?, salt=? "
            + "WHERE emailID=?";
    static final String selectStaffSQL = "SELECT * FROM Staff "
            + "INNER JOIN Email ON Email.emailID = Staff.emailID "
            + "WHERE deleted=0";
    static final String selectStaffByIDSQL = "SELECT * FROM Staff "
            + "INNER JOIN Email ON Email.emailID = Staff.emailID "
            + "WHERE staffID=?";
    static final String selectStaffByEmailIDSQL = "SELECT * FROM Staff "
            + "WHERE emailID=?";
    static final String insertStaffSQL = "INSERT INTO Staff (firstName, lastName, emailID, title, company, description, instructor, admin, photoUrl) "
            + "VALUES (?,?,?,?,?,?,?,?,?)";
    static final String updateStaffDeletedSQL = "UPDATE Staff SET deleted=1 WHERE StaffID=?";
    static final String updateStaffSQL = "UPDATE Staff SET firstName=?, lastName=?, emailID=?, title=?, company=?, description=?, instructor=?, admin=?, photoUrl=? WHERE staffID=?";
    static final String getCampStaffSQL = "SELECT * FROM Staff "
            + "INNER JOIN StaffAssignment ON StaffAssignment.staffID = Staff.staffID "
            + "WHERE StaffAssignment.campID=? AND StaffAssignment.deleted=0";
    static final String getAllCampStaffSQL = "SELECT * FROM Staff "
            + "WHERE instructor=1";
    static final String getStaffByName = "SELECT staffID FROM Staff "
            + "WHERE firstName=? AND lastName=?";
    static final String addCampStaffSQL = "INSERT INTO StaffAssignment (staffID, campID, deleted) "
            + "VALUES (?,?,0);";
    static final String deleteCampStaffSQL = "UPDATE StaffAssignment SET deleted=1 "
            + "WHERE staffID=? AND campID=?";
    static final String insertStudentLegalAgreeSQL = "UPDATE Student SET legalAgreement=? "
            + "WHERE studentID=?";
    static final String insertStudentCampSQL = "INSERT INTO StudentCamp (studentID, campOfferedID, accepted, waitlisted, rejected, confirmed, withdrawn, paid, requestedTimestamp) "
            + "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";
    static final String confirmStudentCampApplicationSQL = "UPDATE StudentCamp SET confirmed=?, confirmedTimestamp=? "
            + "WHERE campOfferedID=? AND studentID=?";
    static final String confirmStudentCampApplicationByStudentCampIDSQL = "UPDATE StudentCamp SET confirmed=?, confirmedTimestamp=? "
            + "WHERE studentCampID=?";
    static final String withdrawStudentCampApplicationSQL = "UPDATE StudentCamp SET withdrawn=?, withdrawnTimestamp=? "
            + "WHERE campOfferedID=? AND studentID=?";
    static final String withdrawStudentCampApplicationByStudentCampIDSQL = "UPDATE StudentCamp SET withdrawn=?, withdrawnTimestamp=? "
            + "WHERE studentCampID=?";
    static final String payForCampSQL = "UPDATE StudentCamp SET paid=?, paidTimestamp=? "
            + "WHERE campOfferedID=? AND studentID=?";
    static final String selectTokenSQL = "SELECT * FROM VerificationToken "
            + "WHERE tokenID=?";
    static final String verifyTokenSQL = "UPDATE VerificationToken SET verified=1 "
            + "WHERE tokenID=?";
    static final String insertTokenSQL = "INSERT INTO VerificationToken (tokenID, emailID, expiry, verified) "
            + "VALUES (?, ?, ?, 0)";
    static final String getEmailContentsSQL = "SELECT currentEmail FROM EmailContents "
            + "WHERE emailContentsID=?";
    static final String getEmailSubjectSQL = "SELECT currentSubject FROM EmailContents "
            + "WHERE emailContentsID=?";
    static final String updateEmailContentSQL = "UPDATE EmailContents SET currentEmail=? "
            + "WHERE emailContentsID=?";
    static final String updateEmailSubjectSQL = "UPDATE EmailContents SET currentSubject=? "
            + "WHERE emailContentsID=?";
    static final String selectStudentsByCamp = "SELECT * FROM Student "
            + "INNER JOIN StudentCamp ON StudentCamp.studentID = Student.studentID "
            + "WHERE StudentCamp.confirmed = 1 AND StudentCamp.campOfferedID=? AND StudentCamp.withdrawn = 0";
    static final String selectStudentsByLastName = selectStudentsByCamp + " AND Student.lname=?";
    static final String selectStudentsByGrade = "SELECT * FROM Student "
            + "INNER JOIN StudentCamp ON StudentCamp.studentID = Student.studentID "
            + "INNER JOIN Grade ON Grade.gradeID = Student.gradeID "
            + "WHERE StudentCamp.confirmed = 1 AND StudentCamp.campOfferedID=? AND Grade.grade=? AND StudentCamp.withdrawn = 0";
    static final String selectStudentsByGradeAndLastName = selectStudentsByGrade + " AND Student.lname=?";
    static final String insertPickupTimeSQL = "INSERT INTO CheckoutTimeStamps (studentID, checkoutTime) "
            + "VALUES(?, NOW())";
    static final String getMedicalForm = "SELECT mf.*, a.street, a.city, a.stateID, a.zip, s.state "
            + "FROM MedicalForm mf "
            + "LEFT JOIN Address a ON mf.addressID = a.addressID "
            + "LEFT JOIN State s ON a.stateID = s.stateID "
            + "WHERE mf.studentID = ? AND mf.timestamp BETWEEN timestamp(DATE_SUB(NOW(), INTERVAL 9 MONTH)) AND timestamp(NOW()) "
            + "ORDER BY mf.timestamp DESC";
    static final String insertMedicalForm = "INSERT INTO MedicalForm "
            + "(insuranceCarrier, nameOfInsured, policyNumber, policyPhone, physicianName, physicianPhone, "
            + "allergies, prescribedMeds, nonPrescribedMeds, illnessesOrInjuries, "
            + "surergiesOrHospital, studentFName, studentLName, parentPhone, legalGuardianName, legalGuardianAgree, dateOfBirth, ecName, ecRelationship, ecPhone, "
            + "tetanusShot, physicianPhoneCellOrWork, parentPhoneCellOrHome, "
            + "ecPhoneCellOrHome, genderID, addressID, studentID) "
            + "VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
    static final String updateMedicalForm = "UPDATE MedicalForm SET "
            + "timestamp=NOW(), insuranceCarrier=?, nameOfInsured=?, policyNumber=?, policyPhone=?, "
            + "physicianName=?, physicianPhone=?, "
            + "allergies=?, prescribedMeds=?, nonPrescribedMeds=?, illnessesOrInjuries=?, "
            + "surergiesOrHospital=?, studentFName=?, studentLName=?, parentPhone=?, legalGuardianName=?, "
            + "dateOfBirth=?, ecName=?, ecRelationship=?, ecPhone=?, "
            + "tetanusShot=?, physicianPhoneCellOrWork=?, parentPhoneCellOrHome=?, ecPhoneCellOrHome=?, genderID=? "
            + "WHERE medicalFormID=?";
    static final Map<String, String> searchCampersWhereMap = new HashMap<>();
    private static final String selectCampsHelper =
            "SELECT c.*, cl.campLevelDescription, ct.topic, "
                    + "d1.date as startDate, d2.date as endDate, "
                    + "g1.grade as recommendedGradeLow, g2.grade as recommendedGradeHigh "
                    + "FROM CampOffered c "
                    + "INNER JOIN CampLevel cl ON cl.campLevelID = c.campLevelID "
                    + "INNER JOIN Date d1 ON d1.dateID = c.campStartDateID "
                    + "INNER JOIN Date d2 ON d2.dateID = c.campEndDateID "
                    + "INNER JOIN CampTopic ct ON ct.campTopicID = c.campTopicID "
                    + "LEFT JOIN Grade g1 ON c.recommendedGradeLowID = g1.gradeID "
                    + "LEFT JOIN Grade g2 ON c.recommendedGradeHighID = g2.gradeID ";

    static final String selectCampsOfferedByIdSQL = selectCampsHelper
            + "WHERE c.campTopicID=? AND YEAR(d1.date) = YEAR(now()) "
            + "ORDER BY startDate ASC";
    static final String selectOpenCampsOfferedByIdSQL = selectCampsHelper
            + "WHERE c.campTopicID=? AND TIMESTAMP(now()) < TIMESTAMP(d2.date) AND c.closed=0 "
            + "ORDER BY startDate ASC";
    static final String selectAllCampsOfferedByIdSQL = selectCampsHelper
            + "WHERE c.campTopicID=? "
            + "ORDER BY startDate ASC";
    static final String selectCampsOfferedByCampLevelDescriptionSQL = selectCampsHelper
            + "WHERE cl.campLevelDescription=? AND YEAR(d1.date) = YEAR(now()) "
            + "ORDER BY startDate ASC";
    static final String selectCampsOfferedSQL = selectCampsHelper
            + "WHERE YEAR(d1.date) = YEAR(now()) "
            + "ORDER BY startDate ASC";
    static final String selectCampOfferedSQL = selectCampsHelper
            + "WHERE c.campOfferedID=?";
    static final String selectLatestCreatedCamp = selectCampsHelper
            + "ORDER BY campOfferedID DESC "
            + "LIMIT 1";
    private static final String updateStudentCampAcceptSQL = "UPDATE StudentCamp SET accepted=1, confirmed=0, withdrawn=0, paid=0, waitlisted=0, rejected=0, acceptedTimestamp=?"
            + " WHERE studentID=? AND campOfferedID=? AND withdrawn=0";
    private static final String updateStudentCampWaitlistSQL = "UPDATE StudentCamp SET accepted=0, confirmed=0, withdrawn=0, paid=0, waitlisted=1, rejected=0, waitlistedTimestamp=?"
            + " WHERE studentID=? AND campOfferedID=? AND withdrawn=0";
    private static final String updateStudentCampRejectSQL = "UPDATE StudentCamp SET accepted=0, confirmed=0, withdrawn=0, paid=0, waitlisted=0, rejected=1, rejectedTimestamp=?"
            + " WHERE studentID=? AND campOfferedID=? AND withdrawn=0";
    private static final String updateStudentCampPaidSQL = "UPDATE StudentCamp SET accepted=0, confirmed=0, withdrawn=0, paid=1, waitlisted=0, rejected=0, paidTimestamp=?"
            + " WHERE studentID=? AND campOfferedID=? AND withdrawn=0";
    public static final List<String> studentCampStatusList = Arrays.asList("applied", "confirmed", "accepted", "waitlisted", "rejected", "withdrawn");
    private static final String selectAppliedStudentCampsHelper = "SELECT * FROM StudentCamp "
            + "INNER JOIN Student ON Student.studentID = StudentCamp.studentID "
            + "INNER JOIN Gender ON Student.genderID = Gender.genderID "
            + "INNER JOIN Grade ON Student.gradeID = Grade.gradeID "
            + "INNER JOIN ReducedMeals ON Student.reducedMealsID = ReducedMeals.reducedMealsID ";
    private static final String selectAppliedStudentCampsSQL = selectAppliedStudentCampsHelper
            + "WHERE campOfferedID = ? AND accepted=0 AND confirmed=0 AND withdrawn=0 AND paid=0 AND waitlisted=0 AND rejected=0 "
            + "ORDER BY requestedTimestamp";
    private static final String selectConfirmedStudentCampsSQL = selectAppliedStudentCampsHelper
            + "WHERE campOfferedID = ? AND accepted=1 AND confirmed=1 AND withdrawn=0 "
            + "ORDER BY confirmedTimestamp";
    private static final String selectAcceptedStudentCampsSQL = selectAppliedStudentCampsHelper
            + "WHERE campOfferedID = ? AND accepted=1 AND confirmed=0 AND withdrawn=0 "
            + "ORDER BY acceptedTimestamp";
    private static final String selectWithdrawnStudentCampsSQL = selectAppliedStudentCampsHelper
            + "WHERE campOfferedID = ? AND withdrawn=1 "
            + "ORDER BY withdrawnTimestamp";
    private static final String selectWaitlistedStudentCampsSQL = selectAppliedStudentCampsHelper
            + "WHERE campOfferedID = ? AND accepted=0 AND waitlisted=1 AND withdrawn=0 "
            + "ORDER BY waitlistedTimestamp";
    private static final String selectRejectedStudentCampsSQL = selectAppliedStudentCampsHelper
            + "WHERE campOfferedID = ? AND accepted=0 AND rejected=1 AND withdrawn=0 "
            + "ORDER BY rejectedTimestamp";
    static final String selectStudentPastAttendedCampsSQL = "SELECT CampTopic.topic, CampLevel.campLevelDescription, startDate.date AS startDate, endDate.date AS endDate " +
            "FROM StudentCamp " +
            "INNER JOIN CampOffered ON CampOffered.campOfferedID=StudentCamp.campOfferedID " +
            "INNER JOIN CampTopic ON CampTopic.campTopicID=CampOffered.campTopicID " +
            "INNER JOIN CampLevel ON CampLevel.campLevelID=CampOffered.campLevelID " +
            "INNER JOIN Date AS startDate ON startDate.dateID=CampOffered.campStartDateID " +
            "INNER JOIN Date AS endDate ON endDate.dateID=CampOffered.campEndDateID " +
            "WHERE endDate.date <= NOW() AND studentID=? AND confirmed=1 AND withdrawn=0";

    static final String selectStudentCurrentCampsAppliedSQL = "SELECT c.*, sc.*, sc.cost AS studentCost, cl.campLevelDescription, ct.topic, "
            + "d1.date as startDate, d2.date as endDate, "
            + "g1.grade as recommendedGradeLow, g2.grade as recommendedGradeHigh "
            + "FROM StudentCamp sc "
            + "INNER JOIN CampOffered c ON c.campOfferedID=sc.campOfferedID "
            + "INNER JOIN CampLevel cl ON cl.campLevelID = c.campLevelID "
            + "INNER JOIN Date d1 ON d1.dateID = c.campStartDateID "
            + "INNER JOIN Date d2 ON d2.dateID = c.campEndDateID "
            + "INNER JOIN CampTopic ct ON ct.campTopicID = c.campTopicID "
            + "LEFT JOIN Grade g1 ON c.recommendedGradeLowID = g1.gradeID "
            + "LEFT JOIN Grade g2 ON c.recommendedGradeHighID = g2.gradeID "
            + "WHERE d2.date >= NOW() AND studentID=? AND withdrawn=0; ";

    //    Same as above, but includes withdrawn
    static final String selectAllStudentCurrentCampsAppliedSQL = "SELECT c.*, sc.*, sc.cost AS studentCost, cl.campLevelDescription, ct.topic, "
            + "d1.date as startDate, d2.date as endDate, "
            + "g1.grade as recommendedGradeLow, g2.grade as recommendedGradeHigh "
            + "FROM StudentCamp sc "
            + "INNER JOIN CampOffered c ON c.campOfferedID=sc.campOfferedID "
            + "INNER JOIN CampLevel cl ON cl.campLevelID = c.campLevelID "
            + "INNER JOIN Date d1 ON d1.dateID = c.campStartDateID "
            + "INNER JOIN Date d2 ON d2.dateID = c.campEndDateID "
            + "INNER JOIN CampTopic ct ON ct.campTopicID = c.campTopicID "
            + "LEFT JOIN Grade g1 ON c.recommendedGradeLowID = g1.gradeID "
            + "LEFT JOIN Grade g2 ON c.recommendedGradeHighID = g2.gradeID "
            + "WHERE d2.date >= NOW() AND studentID=?;";

    static final String selectStudentAcceptedCampsSQL = "SELECT c.*, sc.*, sc.cost AS studentCost, cl.campLevelDescription, ct.topic, "
            + "d1.date as startDate, d2.date as endDate, "
            + "g1.grade as recommendedGradeLow, g2.grade as recommendedGradeHigh "
            + "FROM StudentCamp sc "
            + "INNER JOIN CampOffered c ON c.campOfferedID=sc.campOfferedID "
            + "INNER JOIN CampLevel cl ON cl.campLevelID = c.campLevelID "
            + "INNER JOIN Date d1 ON d1.dateID = c.campStartDateID "
            + "INNER JOIN Date d2 ON d2.dateID = c.campEndDateID "
            + "INNER JOIN CampTopic ct ON ct.campTopicID = c.campTopicID "
            + "LEFT JOIN Grade g1 ON c.recommendedGradeLowID = g1.gradeID "
            + "LEFT JOIN Grade g2 ON c.recommendedGradeHighID = g2.gradeID "
            + "WHERE studentID=? AND confirmed = 0 AND accepted=1 AND withdrawn=0;";

    static Map<String, String> studentCampStatusToSQLStatementMap = new HashMap<>();
    static Map<String, String> studentCampStatusToUpdateMap = new HashMap<>();

    static {
        studentCampStatusToSQLStatementMap.put(studentCampStatusList.get(0), selectAppliedStudentCampsSQL);
        studentCampStatusToSQLStatementMap.put(studentCampStatusList.get(1), selectConfirmedStudentCampsSQL);
        studentCampStatusToSQLStatementMap.put(studentCampStatusList.get(2), selectAcceptedStudentCampsSQL);
        studentCampStatusToSQLStatementMap.put(studentCampStatusList.get(3), selectWaitlistedStudentCampsSQL);
        studentCampStatusToSQLStatementMap.put(studentCampStatusList.get(4), selectRejectedStudentCampsSQL);
        studentCampStatusToSQLStatementMap.put(studentCampStatusList.get(5), selectWithdrawnStudentCampsSQL);

        studentCampStatusToUpdateMap.put("accept", SQLStatements.updateStudentCampAcceptSQL);
        studentCampStatusToUpdateMap.put("waitlist", SQLStatements.updateStudentCampWaitlistSQL);
        studentCampStatusToUpdateMap.put("reject", SQLStatements.updateStudentCampRejectSQL);
        studentCampStatusToUpdateMap.put("paid", SQLStatements.updateStudentCampPaidSQL);

        searchCampersWhereMap.put(StringConstants.searchCampersEmail, "e.email");
        searchCampersWhereMap.put(StringConstants.searchCampersPFname, "p.fname");
        searchCampersWhereMap.put(StringConstants.searchCampersPLname, "p.lname");
        searchCampersWhereMap.put(StringConstants.searchCampersFname, "s.fname");
        searchCampersWhereMap.put(StringConstants.searchCampersLname, "s.lname");
    }


}
