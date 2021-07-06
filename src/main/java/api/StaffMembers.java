package api;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import data.Staff;
import database.DatabaseQueries;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

public class StaffMembers extends HttpServlet {
    private static final long serialVersionUID = 1L;

    protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Gson gson = new Gson();
        PrintWriter printWriter = response.getWriter();
        List<Staff> staffList = DatabaseQueries.getStaffList();
        JsonObject staffMembersJSON = new JsonObject();
        staffMembersJSON.add("staffMembers", gson.toJsonTree(staffList));
        response.setContentType("application/json");
        printWriter.print(staffMembersJSON.toString());
    }
}
