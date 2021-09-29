package servlets;

import data.*;
import database.DatabaseInserts;
import database.DatabaseQueries;
import org.apache.commons.fileupload.FileItem;
import util.FileUploadUtil;
import util.MultiPartFormUtil;

import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.TimeZone;

@MultipartConfig
public class AddCamp extends HttpServlet {
    private static final long serialVersionUID = 1L;

    public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        if (!request.getSession().getAttribute(StringConstants.ROLE).equals(StringConstants.ADMIN)) {
            String errorMessage = "Please login first.";
            request.setAttribute("errorMessage", errorMessage);
            request.getRequestDispatcher("/register.jsp").forward(request, response);
        } else {
            CampOffered camp = DatabaseQueries.getLatestCampOffered();
            List<CampTopic> campTopicList = DatabaseQueries.getCampTopics();
            List<CampLevel> campLevelList = DatabaseQueries.getCampLevels();
            List<Grade> gradeList = DatabaseQueries.getGrades();

            request.getSession().setAttribute("gradeList", gradeList);
            Collections.sort(campTopicList, new Comparator<CampTopic>() {
                @Override
                public int compare(final CampTopic object1, final CampTopic object2) {
                    return object1.getTopic().compareTo(object2.getTopic());
                }
            });
            request.getSession().setAttribute("campTopicList", campTopicList);
            request.getSession().setAttribute("campLevelList", campLevelList);
            request.getSession().setAttribute("pastCamp", camp);

            request.getRequestDispatcher("/WEB-INF/admin/addcamp.jsp").forward(request, response);
        }
    }

    public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String errorMessage = "";
        request = MultiPartFormUtil.parseRequest(request);
        if (!request.getSession().getAttribute(StringConstants.ROLE).equals(StringConstants.ADMIN)) {
            errorMessage = "Please login first.";
            request.setAttribute("errorMessage", errorMessage);
            request.getRequestDispatcher("/register.jsp").forward(request, response);
        } else {
            CampOffered camp = DatabaseQueries.getLatestCampOffered();
            List<CampTopic> campTopicList = DatabaseQueries.getCampTopics();
            List<CampLevel> campLevelList = DatabaseQueries.getCampLevels();
            List<CampDate> campDateList = DatabaseQueries.getDates();

            String campTopic;

            if (request.getParameter("camp-topic").equals("other")) {
                campTopic = request.getParameter("other-camp-topic");
            } else {
                campTopic = request.getParameter("camp-topic");
            }

            String campLevel;

            if (request.getParameter("camp-level").equals("other")) {
                campLevel = request.getParameter("other-camp-level");
            } else {
                campLevel = request.getParameter("camp-level");
            }
            String campStartDate = request.getParameter("camp-start-date").replaceAll("-", "/");
            String campEndDate = request.getParameter("camp-end-date").replaceAll("-", "/");
            // Time zone conversion back to UTC
            SimpleDateFormat parse = new SimpleDateFormat("HH:mm");
            SimpleDateFormat format = new SimpleDateFormat("HH:mm");
            format.setTimeZone(TimeZone.getTimeZone("PST"));

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
                campDays += "Th ";
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

            //campTA is TBD if nothing specified
            String campTA = request.getParameter("camp-ta");
            if (campTA == null || campTA.length() == 0) {
                campTA = "TBD";
            }

            FileItem fileItem = (FileItem) request.getAttribute("file");
            if (campTopic == null || campLevel == null || campStartDate == null || campEndDate == null || campStartTime == null || campEndTime == null ||
                    campDescription == null || campRecommendedGrade == null || campCapacity == null || campCost == null ||
                    campTopic.isEmpty() || campLevel.isEmpty() || campStartDate.isEmpty() || campEndDate.isEmpty() ||
                    campDescription.isEmpty() || campRecommendedGrade.isEmpty() || campCapacity.isEmpty() || campCost.isEmpty() || campDays.equals("")) {
                errorMessage = "All required fields must be completed.";
            }


            //checks if campCapacity is a positive integer
            else if (!campCapacity.matches("^-?\\d+$") || Integer.parseInt(campCapacity) < 0) {
                errorMessage = "Capacity must be a positive integer.";
            }

            //checks if campCost is a valid dollar amount
            else if (!isValidDollarAmount(campCost)) {
                errorMessage = "Cost must be a valid dollar amount.";
            } else {
                String fileLocation = "";
                // optional file upload
                if (fileItem != null) {
                    try {
                        fileLocation = FileUploadUtil.uploadCampFile(campTopic, campLevel, campStartDate, campEndDate, fileItem);
                    } catch (Exception fileUploadException) {
                        // TODO Auto-generated catch block
                        fileUploadException.printStackTrace();
                        errorMessage = "could not upload file";
                    }
                }

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
                        // use checkbox get value of remote and replace the default true (true is camp offered is remote)
                        int campOfferedID = DatabaseInserts.insertCampOffered(campTopicID, campLevelID, campStartDateID, campEndDateID,
                                Integer.parseInt(campCapacity), campDescription, campRecommendedGrade,
                                Integer.parseInt(campRecommendedGradeStart), Integer.parseInt(campRecommendedGradeEnd),
                                Double.parseDouble(campCost) * 100, fileLocation, isRemote, campTA, campStartTime, campEndTime, campDays);

                    }
                }
            }
        }

        if (errorMessage.length() > 0) {
            request.setAttribute("errorMessage", errorMessage);
            request.getRequestDispatcher("WEB-INF/admin/addcamp.jsp").forward(request, response);
        } else {
            response.sendRedirect("/SummerCamp/admincontrolpanel");
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

        for (CampDate campDate : campDateList) {
            if (campDate.getDate().getTime() == date.getTime()) {
                campDateID = campDate.getDateID();
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
