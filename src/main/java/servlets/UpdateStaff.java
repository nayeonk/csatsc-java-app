package servlets;

import data.Email;
import data.Staff;
import data.StringConstants;
import database.DatabaseDeletions;
import database.DatabaseInserts;
import database.DatabaseQueries;
import database.DatabaseUpdates;
import util.FileUploadUtil;
import util.PasswordUtil;
import util.StorageDirectory;
import util.ValidateEmail;

import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;
import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;

@MultipartConfig
public class UpdateStaff extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private static final String contextPath = "/SummerCamp/staff/";

    public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        if (!request.getSession().getAttribute(StringConstants.ROLE).equals(StringConstants.ADMIN)) {
            String errorMessage = "Please login first.";
            request.setAttribute("errorMessage", errorMessage);
            request.getRequestDispatcher("/register.jsp").forward(request, response);
        } else {
            String staffIDAsString = request.getParameter("staffID");

            if (staffIDAsString == null || staffIDAsString.isEmpty()) {
                response.sendRedirect("/SummerCamp/editstaff");
                return;
            }

            int staffID = Integer.parseInt(staffIDAsString);
            Staff staff = DatabaseQueries.getStaff(staffID);

            if (staff.getStaffID() == 0) {
                response.sendRedirect("/SummerCamp/editstaff");
                return;
            }

            request.getSession().setAttribute("staff", staff);
            request.getRequestDispatcher("/WEB-INF/admin/updatestaff.jsp").forward(request, response);
        }
    }

    public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String errorMessage = "";

        if (!request.getSession().getAttribute(StringConstants.ROLE).equals(StringConstants.ADMIN)) {

            errorMessage = "Please login first.";
            request.setAttribute("errorMessage", errorMessage);
            request.getRequestDispatcher("/register.jsp").forward(request, response);
        } else {
            Staff staff = new Staff();
            int staffID = Integer.parseInt(request.getParameter("staffID"));
            staff.setStaffID(staffID);
            staff.setFirstName(request.getParameter("first-name"));
            staff.setLastName(request.getParameter("last-name"));
            staff.setTitle(request.getParameter("title"));
            staff.setCompany(request.getParameter("company"));
            staff.setDescription(request.getParameter("description"));

            String instructor = request.getParameter("instructor") == null ? "none" : request.getParameter("instructor");
            String admin = request.getParameter("admin") == null ? "none" : request.getParameter("admin");

            if (!instructor.equals("none")) {
                staff.setInstructor(true);
            }

            if (!admin.equals("none")) {
                staff.setAdmin(true);
            }

            String password = request.getParameter("password");
            String confirmPassword = request.getParameter("confirm-password");
            String emailAddress = request.getParameter("email-address");
            Part filePart = request.getPart("file");

            if (staff.getFirstName().isEmpty() || staff.getLastName().isEmpty() || staff.getTitle().isEmpty() ||
                    staff.getCompany().isEmpty() || staff.getDescription().isEmpty() || emailAddress.isEmpty() ||
                    filePart.getSize() == 0 || (instructor == null && admin == null) || (admin.equals("admin") && (password == null || confirmPassword == null))) {
                errorMessage += "All required fields must be completed.";
            } else if (admin.equals("admin") && !password.equals(confirmPassword)) {
                errorMessage += "Entered passwords don't match";
            } else if (admin.equals("admin") && (password.length() < 6 || !(password.matches(".*\\d+.*")))) {
                errorMessage += "Password has to be more than 6 letters and have at least one number";
            } else if (!ValidateEmail.isValidEmailAddress(emailAddress)) {
                errorMessage += "Valid email address required. ";
            } else {
                String fileExtension = filePart.getSubmittedFileName().substring(filePart.getSubmittedFileName().lastIndexOf('.') + 1);
                String fileName = staff.getFirstName() + "_" + staff.getLastName() + "." + fileExtension;

                if (!FileUploadUtil.isImage(fileExtension)) {
                    errorMessage += "Uploaded file must be .jpg, .gif, or .png. ";
                } else {
                    String fileLocation = StorageDirectory.STAFF + fileName;
                    errorMessage += FileUploadUtil.uploadFile(fileLocation, filePart);
                    fileLocation = contextPath + fileName;

                    if (errorMessage.length() == 0) {
                        int emailID = DatabaseQueries.doesEmailExist(emailAddress);

                        // email doesn't exist
                        if (emailID == -1) {
                            emailID = DatabaseInserts.insertEmail(emailAddress);

                            if (emailID == -1) {
                                errorMessage += "Couldn't insert email. ";
                            }
                        }

                        Email email = new Email(emailAddress);
                        email.setEmailID(emailID);
                        staff.setEmail(email);

                        //delete credentials before making new one
                        int updatedRows = DatabaseDeletions.deleteLogin(emailID);

                        // login credentials need to be created
                        if (admin.equals("admin")) {
                            int loginID = DatabaseQueries.getLogin(emailID).getLoginID();
                            // login doesn't exist yet
                            if (loginID == -1) {
                                loginID = createLogin(loginID, password, emailID);
                                if (loginID == -1) {
                                    errorMessage += "Couldn't insert login credentials. ";
                                }
                            }
                        }
                        if (emailID != -1 && (!admin.equals("admin") || DatabaseQueries.isAdmin(emailID))) {
                            updatedRows = DatabaseUpdates.updateStaff(staff, fileLocation);
                            if (updatedRows == 0) {
                                errorMessage += "Couldn't update staff. ";
                            }
                        } else {
                            errorMessage += "Couldn't insert staff. Check if email address is already in use. ";
                        }
                    }
                }
            }

            if (errorMessage.length() > 0) {
                request.setAttribute("errorMessage", errorMessage);
                request.getRequestDispatcher("WEB-INF/admin/updatestaff.jsp").forward(request, response);
            } else {
                response.sendRedirect("/SummerCamp/editstaff");
            }
        }
    }

    private int createLogin(int loginID, String password, int emailID) {
        try {
            String salt = PasswordUtil.createSalt();
            String hashedPassword = PasswordUtil.hashPassword(password, salt);
            loginID = DatabaseInserts.insertLogin(emailID, hashedPassword, salt, true);
        } catch (NoSuchAlgorithmException | NoSuchProviderException e) {
            System.out.println(e.getMessage());
            e.printStackTrace();
        }
        return loginID;
    }
}