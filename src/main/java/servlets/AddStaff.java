package servlets;

import data.Email;
import data.Staff;
import data.StringConstants;
import database.DatabaseInserts;
import database.DatabaseQueries;
import util.FileUploadUtil;
import util.PasswordUtil;
import util.StorageDirectory;

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
public class AddStaff extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private static final String contextPath = "/SummerCamp/staff/";

    public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //error here when refreshing the add staff page
        if (!request.getSession().getAttribute(StringConstants.ROLE).equals(StringConstants.ADMIN)) {
            String errorMessage = "Please login first.";
            request.setAttribute("errorMessage", errorMessage);
            request.getRequestDispatcher("/register.jsp").forward(request, response);
        } else {
            request.getRequestDispatcher("/WEB-INF/admin/addstaff.jsp").forward(request, response);
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
                    filePart.getSize() == 0 || (instructor == null && admin == null) || ((password == null || confirmPassword == null))) {
                errorMessage += "All required fields must be completed.";
            } else if (!password.equals(confirmPassword)) {
                errorMessage += "Entered passwords don't match";
            } else if ((password.length() < 6 || !(password.matches(".*\\d+.*")))) {
                errorMessage += "Password has to be more than 6 letters and have at least one number";
            } else if (!isValidEmailAddress(emailAddress)) {
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

                        // login credentials need to be created
                        int loginID = DatabaseQueries.getLogin(emailID).getLoginID();

                        // login doesn't exist yet
                        if (loginID == -1) {
                            loginID = createLogin(loginID, password, emailID);

                            if (loginID == -1) {
                                errorMessage += "Couldn't insert login credentials. ";
                            }
                        }

                        if (emailID != -1 && (!admin.equals("admin") || DatabaseQueries.isAdmin(emailID))) {
                            int staffID = DatabaseInserts.insertStaff(staff, fileLocation);
                            if (staffID == -1) {
                                errorMessage += "Couldn't insert staff. ";
                            }
                        } else {
                            errorMessage += "Couldn't insert staff. Check if email address is already in use. ";
                        }
                    }
                }
            }

            if (errorMessage.length() > 0) {
                request.setAttribute("errorMessage", errorMessage);
                request.getRequestDispatcher("WEB-INF/admin/addstaff.jsp").forward(request, response);
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

    public boolean isValidEmailAddress(String email) {
        String ePattern = "^[a-zA-Z0-9.!#$%&'*+/=?^_`{|}~-]+@((\\[[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\])|(([a-zA-Z\\-0-9]+\\.)+[a-zA-Z]{2,}))$";
        java.util.regex.Pattern p = java.util.regex.Pattern.compile(ePattern);
        java.util.regex.Matcher m = p.matcher(email);
        return m.matches();
    }
}
