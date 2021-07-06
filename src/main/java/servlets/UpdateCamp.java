package servlets;

import data.*;
import database.DatabaseInserts;
import database.DatabaseQueries;
import database.DatabaseUpdates;
import util.FileUploadUtil;
import util.StorageDirectory;

import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;
import java.io.IOException;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.TimeZone;

@MultipartConfig
public class UpdateCamp extends HttpServlet {
    private static final long serialVersionUID = 1L;

    public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        if (!request.getSession().getAttribute(StringConstants.ROLE).equals(StringConstants.ADMIN)) {
            String errorMessage = "Please login first.";
            request.setAttribute("errorMessage", errorMessage);
            request.getRequestDispatcher("/register.jsp").forward(request, response);
        } else {
            String campOfferedIDAsString = request.getParameter("campOfferedID");

            if (campOfferedIDAsString == null || campOfferedIDAsString.isEmpty()) {
                response.sendRedirect("/SummerCamp/admincontrolpanel");
                return;
            }

            List<CampTopic> campTopicList = DatabaseQueries.getCampTopics();
            List<CampLevel> campLevelList = DatabaseQueries.getCampLevels();
            List<Grade> gradeList = DatabaseQueries.getGrades();

            request.getSession().setAttribute("gradeList", gradeList);
            request.getSession().setAttribute("campTopicList", campTopicList);
            request.getSession().setAttribute("campLevelList", campLevelList);

            int campOfferedID = Integer.parseInt(request.getParameter("campOfferedID"));
            CampOffered campOffered = DatabaseQueries.getCampOffered(campOfferedID);

            if (campOffered.getCampOfferedID() == 0) {
                response.sendRedirect("/SummerCamp/admincontrolpanel");
                return;
            }

            request.getSession().setAttribute("campOffered", campOffered);

            String recommendedGradeStart = campOffered.getRecommendedGradeLow();
            String recommendedGradeEnd = campOffered.getRecommendedGradeHigh();

            request.getSession().setAttribute("recommendedGradeStart", recommendedGradeStart);
            request.getSession().setAttribute("recommendedGradeEnd", recommendedGradeEnd);

            // Check current days of the week
            String days = campOffered.getDays();
            request.getSession().setAttribute("dayM", days.contains("M"));
            request.getSession().setAttribute("dayT", days.contains("T"));
            request.getSession().setAttribute("dayW", days.contains("W"));
            request.getSession().setAttribute("dayH", days.contains("H"));
            request.getSession().setAttribute("dayF", days.contains("F"));
            request.getSession().setAttribute("daySa", days.contains("Sa"));
            request.getSession().setAttribute("daySu", days.contains("Su"));

            DateFormat format = new SimpleDateFormat("yyyy-MM-dd");
            String startDateString = format.format(campOffered.getStartDate());
            request.getSession().setAttribute("startDate", startDateString);
            String endDateString = format.format(campOffered.getEndDate());
            request.getSession().setAttribute("endDate", endDateString);

            Date startTimeToDate = null;
            Date endTimeToDate = null;
            try {
                DateFormat timeToDateFormat = new SimpleDateFormat("hh:mm:ss");
                startTimeToDate = timeToDateFormat.parse(campOffered.getStartTime().toString());
                endTimeToDate = timeToDateFormat.parse(campOffered.getEndTime().toString());
            } catch (ParseException e) {
                e.printStackTrace();
            }
            DateFormat timeFormat = new SimpleDateFormat("HH:mm");
            String startTimeString = timeFormat.format(startTimeToDate);
            request.getSession().setAttribute("startTime", startTimeString);
            String endTimeString = timeFormat.format(endTimeToDate);
            request.getSession().setAttribute("endTime", endTimeString);

            String imageLink = campOffered.getImageLink();
            request.getSession().setAttribute("link", imageLink);

            double cost = campOffered.getPrice() / 100;
            DecimalFormat df = new DecimalFormat("#.00");
            String costString = df.format(cost);
            request.getSession().setAttribute("cost", costString);
            boolean remote = campOffered.isRemote();
            request.getSession().setAttribute("remote", remote);

            request.getRequestDispatcher("/WEB-INF/admin/updatecamp.jsp").forward(request, response);
        }
    }

    public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String errorMessage = "";

        if (!request.getSession().getAttribute(StringConstants.ROLE).equals(StringConstants.ADMIN)) {
            errorMessage = "Please login first.";
            request.setAttribute("errorMessage", errorMessage);
            request.getRequestDispatcher("/register.jsp").forward(request, response);
        } else {
            int campOfferedID = Integer.parseInt(request.getParameter("campOfferedID"));
            CampOffered oldCamp = DatabaseQueries.getCampOffered(campOfferedID);
            List<CampTopic> campTopicList = DatabaseQueries.getCampTopics();
            List<CampLevel> campLevelList = DatabaseQueries.getCampLevels();
            List<CampDate> campDateList = DatabaseQueries.getDates();

            String campTopic;

            if (request.getParameter("camp-topic").equals("other")) {
                campTopic = request.getParameter("other-camp-topic");
            } else {
                campTopic = request.getParameter("camp-topic");
            }

            String campLevel = request.getParameter("camp-level");
            String campStartDate = request.getParameter("camp-start-date").replaceAll("-", "/");
            String campEndDate = request.getParameter("camp-end-date").replaceAll("-", "/");
            // Time zone conversion back to PST
            SimpleDateFormat parse = new SimpleDateFormat("HH:mm");
            SimpleDateFormat format = new SimpleDateFormat("HH:mm");
            format.setTimeZone(TimeZone.getTimeZone("America/Los_Angeles"));

            String campStartTime = null;
            String campEndTime = null;
            try {
                campStartTime = format.format(parse.parse(request.getParameter("camp-start-time")));
                campEndTime = format.format(parse.parse(request.getParameter("camp-end-time")));
            } catch (ParseException e) {
                e.printStackTrace();
            }

            // Create string for days of week
            String campDays = "";
            if (request.getParameter("dayM") != null) {
                campDays += "M ";
            }
            if (request.getParameter("dayT") != null) {
                campDays += "T ";
            }
            if (request.getParameter("dayW") != null) {
                campDays += "W ";
            }
            if (request.getParameter("dayH") != null) {
                campDays += "H ";
            }
            if (request.getParameter("dayF") != null) {
                campDays += "F ";
            }
            if (request.getParameter("daySa") != null) {
                campDays += "Sa ";
            }
            if (request.getParameter("daySu") != null) {
                campDays += "Su ";
            }
            campDays = campDays.trim();

            String campCapacity = request.getParameter("camp-capacity");
            String campDescription = request.getParameter("camp-description");

            String campRecommendedGradeStart = request.getParameter("camp-recommended-grade-start");
            String campRecommendedGradeEnd = request.getParameter("camp-recommended-grade-end");
            String campRecommendedGrade = campRecommendedGradeStart.equals(campRecommendedGradeEnd) ? campRecommendedGradeStart : campRecommendedGradeStart + "-" + campRecommendedGradeEnd;

            String campCost = request.getParameter("camp-cost");
            String campRemote = request.getParameter("camp-remote");

            //assignedTA update
            String campTA = request.getParameter("camp-ta");

            String newPhoto = request.getParameter("file");
            Part filePart = null;
            // Determine if new photo was uploaded and set filePart accordingly
            if (newPhoto != null) {
                filePart = request.getPart("file-input");
            }

            if (campTopic == null || campLevel == null || campStartDate == null || campEndDate == null || campStartTime == null || campEndTime == null ||
                    campDescription == null || campRecommendedGrade == null || campCapacity == null || campCost == null ||
                    campTopic.isEmpty() || campLevel.isEmpty() || campStartDate.isEmpty() || campEndDate.isEmpty() ||
                    campDescription.isEmpty() || campRecommendedGrade.isEmpty() || campCapacity.isEmpty() || campCost.isEmpty() || campDays.equals("")) {
                errorMessage = "All required fields must be completed.";
            } else if (newPhoto != null && filePart == null) {
                errorMessage = "All required fields must be completed. Please upload a new camp photo.";
            }


            //checks if campCapacity is a positive integer
            else if (!campCapacity.matches("^-?\\d+$") || Integer.parseInt(campCapacity) < 0) {
                errorMessage = "Capacity must be a positive integer.";
            }

            //checks if campCost is a valid dollar amount
            else if (!isValidDollarAmount(campCost)) {
                errorMessage = "Cost must be a valid dollar amount.";
            } else {
                String fileLocation = null;
                //Determine if file name has been changed
                if (filePart != null) {
                    String fileExtension;
                    String fileName;

                    fileExtension = filePart.getSubmittedFileName().substring(filePart.getSubmittedFileName().lastIndexOf('.') + 1);
                    fileName = campTopic + "_" + campLevel + "_" + campStartDate.replaceAll("/", "-") + "_" + campEndDate.replaceAll("/", "-") + "." + fileExtension;
                    fileLocation = StorageDirectory.CAMP_FILE + fileName;

                    if (!FileUploadUtil.isImage(fileExtension)) {
                        errorMessage += "Uploaded file must be .jpg, .gif, or .png. ";
                    } else {
                        errorMessage += FileUploadUtil.uploadFile(fileLocation, filePart);
                    }
                } else {
                    fileLocation = oldCamp.getImageLink();
                }

                //Update the rest of the attributes
                if (errorMessage.length() == 0) {
                    int campTopicID = getCampTopicID(campTopicList, campTopic);

                    if (campTopicID == -1) {
                        campTopicID = DatabaseInserts.insertCampTopic(campTopic);

                        if (campTopicID == -1) {
                            errorMessage += "Couldn't insert camp topic. ";
                        }
                    }

                    int campLevelID = getCampLevelID(campLevelList, campLevel);

                    if (campLevelID == -1) {
                        campLevelID = DatabaseInserts.insertCampLevel(campLevel);

                        if (campLevelID == -1) {
                            errorMessage += "Couldn't insert camp level. ";
                        }
                    }

                    int campStartDateID = -1;

                    try {
                        campStartDateID = getCampDateID(campDateList, campStartDate);
                        if (campStartDateID == -1) {
                            campStartDateID = DatabaseInserts.insertDate(campStartDate);
                            if (campStartDateID == -1) {
                                errorMessage += "Couldn't insert camp start date. ";
                            }
                        }
                    } catch (ParseException e) {
                        errorMessage = e.getMessage();
                        System.out.println(errorMessage);
                    }

                    int campEndDateID = -1;

                    try {
                        campEndDateID = getCampDateID(campDateList, campEndDate);
                        if (campEndDateID == -1) {
                            campEndDateID = DatabaseInserts.insertDate(campEndDate);

                            if (campEndDateID == -1) {
                                errorMessage += "Couldn't insert camp end date. ";
                            }
                        }
                    } catch (ParseException e) {
                        errorMessage = e.getMessage();
                        System.out.println(errorMessage);
                    }

                    boolean isRemote = false;
                    if (campRemote != null) {
                        isRemote = true;
                    }

                    if (errorMessage.length() == 0) {
                        // TODO
                        int updatedRows = DatabaseUpdates.updateCampOffered(campOfferedID, campTopicID, campLevelID,
                                campStartDateID, campEndDateID, Integer.parseInt(campCapacity), campDescription,
                                campRecommendedGrade, Integer.parseInt(campRecommendedGradeStart),
                                //aaron change
                                Integer.parseInt(campRecommendedGradeEnd), Double.parseDouble(campCost) * 100,
                                fileLocation, isRemote, campTA, campStartTime, campEndTime, campDays);

                    }
                }
            }

            if (errorMessage.length() > 0) {
                request.setAttribute("errorMessage", errorMessage);
                request.getRequestDispatcher("WEB-INF/admin/updatecamp.jsp").forward(request, response);
            } else {
                response.sendRedirect("/SummerCamp/admincontrolpanel");
            }
        }
    }

    private int getCampTopicID(List<CampTopic> campTopicList, String topic) {
        int campTopicID = -1;

        for (CampTopic campTopic : campTopicList) {
            if (campTopic.getTopic().equals(topic)) {
                campTopicID = campTopic.getCampTopicID();
            }
        }

        return campTopicID;
    }

    private int getCampLevelID(List<CampLevel> campLevelList, String level) {
        int campLevelID = -1;

        for (CampLevel campLevel : campLevelList) {
            if (campLevel.getCampLevelDescription().equals(level)) {
                campLevelID = campLevel.getCampLevelID();
            }
        }

        return campLevelID;
    }

    private int getCampDateID(List<CampDate> campDateList, String dateString) throws ParseException {
        int campDateID = -1;
        SimpleDateFormat format = new SimpleDateFormat("yyyy/MM/dd");
        Date date = format.parse(dateString);

        for (CampDate campWeek : campDateList) {
            if (campWeek.getDate().getTime() == date.getTime()) {
                campDateID = campWeek.getDateID();
            }
        }

        return campDateID;
    }

    private boolean isValidDollarAmount(String campCost) {
        String[] campCostParts = campCost.split("\\.");

        if (campCostParts.length != 2) {
            return false;
        } else {
            return campCostParts[1].length() == 2;
        }
    }

}
