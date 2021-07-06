package servlets;

import data.CampDate;
import data.StringConstants;
import data.Student;
import database.DatabaseQueries;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;


public class Statistics extends HttpServlet {
    private static final long serialVersionUID = 1L;

    private final Map<String, String> topicToID = new HashMap<String, String>() {{
        put("scratch", "1");
        put("java", "2");
        put("scratchjr", "3");
        put("python", "4");
        put("scratch_or_jr_java", "5");
        put("robotics_jr", "6");
        put("robotics", "7");
        put("web_dev", "8");
        put("hour_of_code", "9");
        put("before_camp_care", "10");
        put("after_camp_care", "11");
    }};

    private final Map<String, String> gradeToID = new HashMap<String, String>() {{
        put("Pre-K", "1");
        put("K", "2");
        put("1", "3");
        put("2", "4");
        put("3", "5");
        put("4", "6");
        put("5", "7");
        put("6", "8");
        put("7", "9");
        put("8", "10");
        put("9", "11");
        put("10", "12");
        put("11", "13");
        put("12", "14");
        put("college", "15");
        put("adults", "16");
        put("elementary_teachers", "17");
        put("ms_teachers", "18");
        put("hs_teachers", "19");
    }};

    private final Map<String, String> genderToID = new HashMap<String, String>() {{
        put("male", "1");
        put("female", "2");
        put("DidNotIdentify", "3");
    }};

    private final Map<String, String> ethnicityToID = new HashMap<String, String>() {{
        put("caucasian", "1");
        put("african_american", "2");
        put("asian", "3");
        put("hispanic", "4");
        put("american_indian", "5");
        put("other", "6");
    }};

    private final Map<String, String> incomeToID = new HashMap<String, String>() {{
        put("0_to_20", "1");
        put("20_to_40", "2");
        put("40_to_60", "3");
        put("60_to_80", "4");
        put("80_to_100", "5");
        put("over_100", "6");
        put("no_income_reported", "7");
    }};

    private final Map<String, String> appStatusToID = new HashMap<String, String>() {{
        put("accepted", "1");
        put("rejected", "2");
        put("confirmed", "3");
        put("waitlisted", "4");
        put("withdrawn", "5");
    }};

    private final Map<String, String> mealPlanToID = new HashMap<String, String>() {{
        put("free_meals", "1");
        put("reduced_meals", "2");
        put("none", "3");
        put("prefer_not_to_enter", "4");
    }};

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        Map<String, String> subcategory = new HashMap<>();

        // add subcategories to map for sub-queries
        if (request.getParameterMap().containsKey("year")) {
            subcategory.put("year", request.getParameter("year"));
        }

        if (request.getParameterMap().containsKey("gender")) {
            String gender = request.getParameter("gender");
            subcategory.put("gender", gender);
            subcategory.put("genderID", genderToID.getOrDefault(gender, "none"));
        }
        if (request.getParameterMap().containsKey("ethnicity")) {
            String ethnicity = request.getParameter("ethnicity");
            subcategory.put("ethnicity", ethnicity);
            subcategory.put("ethnicityID", ethnicityToID.getOrDefault(ethnicity, "none"));
        }
        if (request.getParameterMap().containsKey("grade")) {
            String grade = request.getParameter("grade");
            subcategory.put("grade", grade);
            subcategory.put("gradeID", gradeToID.getOrDefault(grade, "none"));
        }
        if (request.getParameterMap().containsKey("topic")) {
            String topic = request.getParameter("topic");
            subcategory.put("topic", topic);
            subcategory.put("topicID", topicToID.getOrDefault(topic, "none"));
        }
        if (request.getParameterMap().containsKey("ethnicity")) {
            String ethnicity = request.getParameter("ethnicity");
            subcategory.put("ethnicity", ethnicity);
            subcategory.put("ethnicityID", ethnicityToID.getOrDefault(ethnicity, "none"));
        }
        if (request.getParameterMap().containsKey("income")) {
            String income = request.getParameter("income");
            subcategory.put("income", income);
            subcategory.put("incomeID", incomeToID.getOrDefault(income, "none"));
        }
        if (request.getParameterMap().containsKey("applicationStatus")) {
            String appStatus = request.getParameter("applicationStatus");
            subcategory.put("applicationStatus", appStatus);
            subcategory.put("applicationStatusID", appStatusToID.getOrDefault(appStatus, "none"));
        }
        if (request.getParameterMap().containsKey("mealPlan")) {
            String mealPlan = request.getParameter("mealPlan");
            subcategory.put("mealPlan", mealPlan);
            subcategory.put("mealPlanID", mealPlanToID.getOrDefault(mealPlan, "none"));
        }

        Map<String, Integer> ethnicityStats = DatabaseQueries.getStatistics2("ethnicity", subcategory);
        request.setAttribute("ethnicityStats", ethnicityStats);

        Map<String, Integer> incomeStats = DatabaseQueries.getStatistics2("income", subcategory);
        request.setAttribute("incomeStats", incomeStats);

        Map<String, Integer> genderStats = DatabaseQueries.getStatistics2("gender", subcategory);
        request.setAttribute("genderStats", genderStats);

        Map<String, Integer> topicStats = DatabaseQueries.getStatistics2("topic", subcategory);
        request.setAttribute("topicStats", topicStats);

        Map<String, Integer> gradeStats = DatabaseQueries.getStatistics2("grade", subcategory);
        request.setAttribute("gradeStats", gradeStats);

        Map<String, Integer> mealPlanStats = DatabaseQueries.getStatistics2("mealPlan", subcategory);
        request.setAttribute("mealPlanStats", mealPlanStats);

        Map<String, Integer> schoolStats = DatabaseQueries.getStatistics2("school", subcategory);
        request.setAttribute("schoolStats", schoolStats);

        //        Below is so it doesn't break
        String category = "all";
        int total = 0;
        request.setAttribute("category", category);
        request.setAttribute("subcategory", subcategory);
        request.setAttribute("total", total);

        request.getRequestDispatcher("/WEB-INF/admin/statistics.jsp").forward(request, response);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        if (!request.getSession().getAttribute(StringConstants.ROLE).equals(StringConstants.ADMIN)) {
            String errorMessage = "Please login first.";
            request.setAttribute("errorMessage", errorMessage);
            request.getRequestDispatcher("/register.jsp").forward(request, response);
        } else {
            request.getRequestDispatcher("/WEB-INF/admin/statistics.jsp").forward(request, response);
        }
    }

    public void save(String button, int index, HttpServletRequest request, HttpServletResponse response)
            throws IOException {

        response.sendRedirect("/SummerCamp/statistics");
    }

}
