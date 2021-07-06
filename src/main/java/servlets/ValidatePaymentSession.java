package servlets;

import com.google.gson.Gson;
import contracts.ResponseMessage;
import contracts.ValidatePaymentSessionBody;
import database.DatabaseQueries;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class ValidatePaymentSession extends HttpServlet {
    public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        ValidatePaymentSessionBody body = new Gson().fromJson(
            request.getReader(), ValidatePaymentSessionBody.class
        );

        String errorMessage;
        if (!request.getSession().getAttribute("tokenStr").equals(body.token)) {
            errorMessage = "Session token expired. Please refresh the page.";
        } else if (!DatabaseQueries.isAccepted(body.studentId, body.campOfferedId)) {
            errorMessage = "Student hasn't been accepted. Please email us";
        } else {
            errorMessage = "";
        }

        if (errorMessage.length() > 0) {
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            response.getWriter().print(
                new Gson().toJson(
                    new ResponseMessage(errorMessage)
                )
            );
        } else {
            response.setStatus(HttpServletResponse.SC_OK);
        }
    }
}
