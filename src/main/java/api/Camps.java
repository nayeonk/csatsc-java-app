package api;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import data.CampOffered;
import database.DatabaseQueries;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;


public class Camps extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void service( HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String campLevel = request.getParameter("campLevel");
		Gson gson = new Gson();
		PrintWriter printWriter = response.getWriter();
		List<CampOffered> fullCampOfferedList = DatabaseQueries.getCampsOfferedByCampLevelDescription(campLevel);
		JsonObject campsJSON = new JsonObject();
		campsJSON.add("camps", gson.toJsonTree(fullCampOfferedList));
		response.setContentType("application/json");
		printWriter.print(campsJSON.toString());
    }
}
