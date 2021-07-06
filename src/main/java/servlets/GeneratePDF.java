package servlets;

import data.*;
import database.DatabaseQueries;
import org.apache.commons.io.FilenameUtils;
import org.apache.pdfbox.multipdf.PDFMergerUtility;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.common.PDRectangle;
import org.apache.pdfbox.pdmodel.graphics.image.LosslessFactory;
import org.apache.pdfbox.pdmodel.graphics.image.PDImageXObject;
import org.apache.pdfbox.pdmodel.interactive.form.PDAcroForm;
import org.apache.pdfbox.pdmodel.interactive.form.PDCheckBox;
import util.FileUploadUtil;
import util.StorageDirectory;

import javax.imageio.ImageIO;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class GeneratePDF extends HttpServlet {

    private static final Map<String, String> ethnicityToAvroField;
    private static final Map<Integer, String> spelledInt;

    static {
        ethnicityToAvroField = new HashMap<>();
        spelledInt = new HashMap<>();
        spelledInt.put(1, "One");
        spelledInt.put(2, "Two");
        spelledInt.put(3, "Three");
        spelledInt.put(4, "Four");
        spelledInt.put(5, "Five");
        ethnicityToAvroField.put("caucasian", "whiteRace");
        ethnicityToAvroField.put("african-american", "blackRace");
        ethnicityToAvroField.put("asian", "asianRace");
        ethnicityToAvroField.put("hispanic", "hispanicRace");
        ethnicityToAvroField.put("american-indian", "nativeRace");
    }

    private PDAcroForm acroForm;

    protected void service(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        if (!request.getSession().getAttribute(StringConstants.ROLE).equals(StringConstants.ADMIN)) {
            String errorMessage = "Please login first.";
            request.setAttribute("errorMessage", errorMessage);
            request.getRequestDispatcher("/register.jsp").forward(request, response);
        } else {
            String medFormPath = StorageDirectory.getPdfTemplateDirectory(this.getServletContext());
            PDDocument pdfDocument = PDDocument.load(new File(medFormPath + "MedicalForm.pdf"));
            acroForm = pdfDocument.getDocumentCatalog().getAcroForm();
            Integer studentID = Integer.parseInt(request.getParameter("studentID"));
            Integer parentID = Integer.parseInt(request.getParameter("parentID"));
            Student student = DatabaseQueries.getStudent(studentID, true);
            MedicalForm medForm = student.getMedForm();
            Parent parent = DatabaseQueries.getParentByPID(parentID);
            Boolean haveMedForm = medForm != null;

            Address address = haveMedForm && (medForm.getAddress() != null) ? medForm.getAddress() : parent.getAddress();

            String studentDob = haveMedForm ? medForm.getDateOfBirth() : student.getDob();
            String ecName;
            String ecNumber;

            if (medForm == null || medForm.getEcName() == null) {
                EmergencyContact ec = DatabaseQueries.getEmergencyContact(parentID);
                ecName = ec.getFirstName() + " " + ec.getLastName();
                ecNumber = ec.getPhone();
            } else {
                ecName = medForm.getEcName();
                ecNumber = medForm.getEcPhone();
            }

            if (acroForm != null) {
                acroForm.getField("programName").setValue("SC@SC Summer Camp");
                acroForm.getField("camperAddress").setValue(address.getStreet() + " " + address.getCity() + " " + address.getState() + " " + address.getZip());
                acroForm.getField("ec1Name").setValue(ecName);
                acroForm.getField("ec1Number").setValue(ecNumber);
                acroForm.getField("ec1Relationship").setValue("Guardian");
                acroForm.getField("camperName").setValue((student.getLastName() + ", " + student.getFirstName()));

                for (Ethnicity e : student.getEthnicities()) {
                    PDCheckBox ethnicity = null;
                    if (ethnicityToAvroField.containsKey(e.getEthnicity().toLowerCase())) {
                        ethnicity = (PDCheckBox) acroForm.getField(ethnicityToAvroField.get(e.getEthnicity().toLowerCase()));
                    } else {
                        ethnicity = (PDCheckBox) acroForm.getField("unknownRace");
                    }
                    ethnicity.check();
                }

                LocalDateTime now = LocalDateTime.now();
                int dayOfWeek = now.getDayOfWeek().getValue();
                LocalDateTime startDate = now.minusDays(dayOfWeek - 1);
                LocalDateTime endDate = now.plusDays(4);
                acroForm.getField("campDateMonth").setValue(Integer.toString(startDate.getMonth().getValue()));
                acroForm.getField("campDateDay").setValue(Integer.toString(startDate.getDayOfMonth()));
                acroForm.getField("campDateYear").setValue(Integer.toString(startDate.getYear()));
                acroForm.getField("campDateEndMonth").setValue(Integer.toString(endDate.getMonth().getValue()));
                acroForm.getField("campDateEndDay").setValue(Integer.toString(endDate.getDayOfMonth()));
                acroForm.getField("campDateEndYear").setValue(Integer.toString(endDate.getDayOfYear()));

                if ((studentDob != null) && (!studentDob.isEmpty())) {

                    String[] tokens = studentDob.split("[-]+");
                    String day = tokens[0];
                    String month = tokens[1];
                    String year = tokens[2];

                    acroForm.getField("birthMonth").setValue(month);
                    acroForm.getField("birthDay").setValue(year);
                    acroForm.getField("birthYear").setValue(day);

                    String dateOfBirth = month + " / " + day + " / " + year;

                    acroForm.getField("dateOfBirth").setValue(dateOfBirth);
                }

                if (medForm == null) {
                    acroForm.getField("legalName").setValue(student.getLastName() + ", " + student.getFirstName());
                    acroForm.getField("parentPhone").setValue(parent.getPhone());

                    Gender g = student.getGender();

                    if (g.getGender().toLowerCase().matches("(female)|(male)")) {
                        setCheckbox(g.getGender().equalsIgnoreCase("female"), "female", "male");
                    }

                    acroForm.getField("parentName").setValue(parent.getFirstName() + " " + parent.getLastName());
                    pdfDocument.save(medFormPath + "FilledForm1.pdf");
                    pdfDocument.close();
                    FileUploadUtil.outputFile(medFormPath + "FilledForm1.pdf", response, getServletContext());
                } else {

                    acroForm.getField("parentPhone").setValue(medForm.getParentPhone());

                    setCheckbox(medForm.getParentPhoneCellOrHome(), "parentCell", "parentHome");

                    acroForm.getField("parentName").setValue(medForm.getLegalGuardianName());

                    Gender g = student.getGender();
                    if (g.getGender().toLowerCase().matches("(female)|(male)")) {
                        setCheckbox(g.getGender().equalsIgnoreCase("female"), "female", "male");
                    }

                    acroForm.getField("signature").setValue(medForm.getLegalGuardianName());
                    acroForm.getField("todayDateMonth").setValue(Integer.toString(now.getMonth().getValue()));
                    acroForm.getField("todayDateDay").setValue(Integer.toString(now.getDayOfMonth()));
                    acroForm.getField("todayDateYear").setValue(Integer.toString(now.getYear()));

                    setCheckbox(medForm.getEcPhoneCellOrHome(), "ecCell", "ecHome");

                    acroForm.getField("nameOfInsurance").setValue(medForm.getInsuranceCarrier());
                    acroForm.getField("nameOnInsurance").setValue(medForm.getNameOfInsured());
                    acroForm.getField("policyNumber").setValue(medForm.getPolicyNumber());
                    acroForm.getField("insuranceNumber").setValue(medForm.getPolicyPhone());
                    acroForm.getField("physicianName").setValue(medForm.getPhysicianName());
                    acroForm.getField("physicianNumber").setValue(medForm.getPhysicianPhone());

                    setCheckbox(medForm.getPhysicianPhoneCellOrWork(), "officeCell", "officeHome");

                    acroForm.getField("legalName").setValue(medForm.getStudentLName() + ", " + medForm.getStudentFName());


                    if (medForm.getTetanusShot() == null) {
                        PDCheckBox tet = (PDCheckBox) acroForm.getField("tetanusU");
                        tet.check();
                    } else {
                        setCheckbox(medForm.getTetanusShot(), "tetanusYes", "tetanusNo");
                    }

                    List<Allergy> allergies = medForm.getAllergies();
                    List<String> illnesses = medForm.getIllnessesInjuries();
                    List<Medication> meds = medForm.getPrescribedMeds();
                    List<Medication> npmeds = medForm.getNonPrescribedMeds();
                    List<HospitalVisit> hospitalVisits = medForm.getHospitalVisits();
                    //allergy
                    if (allergies != null) {
                        for (int i = 0; i < allergies.size(); i++) {
                            Allergy a = allergies.get(i);
                            String key = spelledInt.get(i + 1);
                            acroForm.getField("al" + key).setValue(a.getAllergy());
                            acroForm.getField("reaction" + key).setValue(a.getReaction());
                        }
                    }

                    if (meds != null) {
                        for (int i = 0; i < meds.size(); i++) {
                            Medication m = meds.get(i);
                            String key = spelledInt.get(i + 1);
                            acroForm.getField("med" + key).setValue(m.getMed());
                            acroForm.getField("dosage" + key).setValue(m.getDose());
                        }
                    }

                    if (npmeds != null) {
                        for (int i = 0; i < npmeds.size(); i++) {
                            Medication m = npmeds.get(i);
                            String key = spelledInt.get(i + 1);
                            acroForm.getField("medNP" + key).setValue(m.getMed());
                            acroForm.getField("dosageNP" + key).setValue(m.getDose());
                        }
                    }

                    if (hospitalVisits != null) {
                        for (int i = 0; i < hospitalVisits.size(); i++) {
                            HospitalVisit h = hospitalVisits.get(i);
                            String key = spelledInt.get(i + 1);
                            acroForm.getField("surg" + key).setValue(h.getYear());
                            acroForm.getField("reason" + key).setValue(h.getReason());
                        }
                    }

                    if (illnesses != null) {
                        for (int i = 0; i < illnesses.size(); i++) {
                            acroForm.getField("illness" + spelledInt.get(i + 1)).setValue(illnesses.get(i));
                        }
                    }

                    pdfDocument.save(medFormPath + "FilledForm1.pdf");
                    pdfDocument.close();
                    if (medForm.getInsuranceCard() != null) {
                        File i1 = new File(medForm.getInsuranceCard().getFrontFilePath());
                        File i2 = new File(medForm.getInsuranceCard().getBackFilePath());
                        File file = new File(medFormPath + "FilledForm1.pdf");

                        if (FilenameUtils.getExtension(i1.getName()).equalsIgnoreCase("pdf")) {
                            //Instantiating PDFMergerUtility class
                            PDFMergerUtility PDFmerger = new PDFMergerUtility();
                            //Setting the destination file
                            PDFmerger.setDestinationFileName(medFormPath + "filled.pdf");

                            //adding the source files
                            PDFmerger.addSource(file);
                            PDFmerger.addSource(i1);
                            PDFmerger.addSource(i2);

                            //Merging the two documents
                            PDFmerger.mergeDocuments();
                        } else {
                            PDDocument doc = PDDocument.load(file);
                            drawImage(doc, i1);
                            drawImage(doc, i2);
                            doc.save(medFormPath + "filled.pdf");
                            doc.close();
                        }

                        FileUploadUtil.outputFile(medFormPath + "filled.pdf", response, getServletContext());
                    } else {
                        FileUploadUtil.outputFile(medFormPath + "FilledForm1.pdf", response, getServletContext());
                    }
                }
            }
        }
    }

    private void setCheckbox(Boolean bool, String ifTrue, String ifFalse) throws IOException {
        String type = bool ? ifTrue : ifFalse;
        PDCheckBox box = (PDCheckBox) acroForm.getField(type);
        box.check();
    }

    private void drawImage(PDDocument doc, File file) throws IOException {

        PDRectangle docMB = doc.getPage(0).getMediaBox();
        float docWidth = docMB.getWidth();
        float docHeight = docMB.getHeight();

        BufferedImage image1 = ImageIO.read(file);
        PDPage page1 = new PDPage(new PDRectangle(docWidth, docHeight));
        PDImageXObject pdImageXObject1 = LosslessFactory.createFromImage(doc, image1);

        float imageWidth = pdImageXObject1.getWidth();
        float imageHeight = pdImageXObject1.getHeight();
        float scaleWidth = imageWidth;
        float scaleHeight = imageHeight;
        if ((imageWidth > imageHeight) && (imageWidth > docWidth)) {
            scaleWidth = docWidth - 40;
            scaleHeight = imageHeight * (docWidth / imageWidth);
        } else if (imageHeight > docHeight) {
            scaleHeight = docHeight - 40;
            scaleWidth = imageWidth * (docHeight / imageHeight);
        }

        PDPageContentStream contentStream = new PDPageContentStream(doc, page1, PDPageContentStream.AppendMode.OVERWRITE, true, false);
        doc.addPage(page1);
        contentStream.drawImage(pdImageXObject1, 20, 20, scaleWidth, scaleHeight);
        contentStream.close();
    }
}