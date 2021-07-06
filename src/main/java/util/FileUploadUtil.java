package util;

import database.DatabaseInserts;
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.io.FilenameUtils;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;
import java.io.*;
import java.sql.Timestamp;
import java.util.Arrays;
import java.util.List;

public class FileUploadUtil {
    private static final List<String> imageFileExtensions = Arrays.asList("jpg", "gif", "png");

    public static void uploadInsuranceCard(Integer medFormID, Integer parentID, Integer studentID, FileItem front, FileItem back) throws Exception {
        String frontLoc = uploadFile(StorageDirectory.INSURANCE_FRONT, parentID, studentID, front);
        String backLoc = uploadFile(StorageDirectory.INSURANCE_BACK, parentID, studentID, back);
        DatabaseInserts.insertInsuranceCard(medFormID, frontLoc, backLoc);
    }

    static String uploadFile(String dir, Integer parentID, Integer studentID, FileItem fileItem) throws Exception {
        Timestamp currentTime = new Timestamp(System.currentTimeMillis());
        String newFileName = currentTime.toString().substring(0, 20); // of the format yyyy-mm-dd hh:mm:ss.
        newFileName = newFileName.replace(':', '-');
        newFileName = newFileName.replace(' ', '_');
        return uploadFile(newFileName + FilenameUtils.getExtension(fileItem.getName()),
                dir,
                fileItem,
                parentID + File.separator + studentID + File.separator);
    }

    public static String uploadCampFile(
            String campTopic,
            String campLevel,
            String campStartDate,
            String campEndDate,
            FileItem fileItem) throws Exception {
        String ext = FilenameUtils.getExtension(fileItem.getName());
        String fileName = campTopic +
                "_" + campLevel + "_" + campStartDate.replaceAll("/", "-") +
                "_" +
                campEndDate.replaceAll("/", "-") + "." + ext;
        return uploadFile(fileName, StorageDirectory.CAMP_FILE, fileItem, null);
    }

    private static String uploadFile(String fileName, String dir, FileItem fileItem, String extraDir) throws Exception {
        String newDir = dir;

        String path = (extraDir != null) ? newDir + extraDir : newDir;
        File pathDir = new File(path);

        if (!pathDir.exists()) {
            pathDir.mkdirs();
        }

        File fileLocation = new File(pathDir.getAbsolutePath(), fileName);

        fileItem.write(fileLocation);

        //CC: Function To insert file directory into Student table. Requires us to pass a student's data as a session attribute.
        return fileLocation.getAbsolutePath();
    }

    public static void outputFile(String filePath, HttpServletResponse response, ServletContext context) {
        try {
            InputStream inputStream;
            inputStream = new File(filePath).toURI().toURL().openStream();
            ByteArrayOutputStream output = new ByteArrayOutputStream();
            byte[] buffer = new byte[1024];
            int n;
            while ((n = inputStream.read(buffer)) != -1) {
                output.write(buffer, 0, n);
            }
            inputStream.close();
            byte[] content = output.toByteArray();

            response.setContentType(context.getMimeType(filePath.substring(filePath.lastIndexOf(File.separator))));
            response.setContentLength(content.length);
            response.getOutputStream().write(content);
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }

    }

    public static String uploadFile(String filePath, Part filePart) throws IOException {
        String errorMessage = "";
        filePath = filePath.replaceFirst("^~", System.getProperty("user.home"));
        OutputStream out = null;
        InputStream filecontent = null;
        try {
            File file = new File(filePath);
            if (!file.exists()) {
                file.createNewFile();
            }
            out = new FileOutputStream(file);
            filecontent = filePart.getInputStream();

            int read;
            byte[] bytes = new byte[1024];

            while ((read = filecontent.read(bytes)) != -1) {
                out.write(bytes, 0, read);
            }
        } catch (IOException ioe) {
            System.out.println("ignoring the image path right now because no WEBSTORAGE  root yet");

        }

//        catch (FileNotFoundException fnfe) {
//            System.out.println("FileUploadUtil.uploadFile():" + fnfe.getMessage());
//            errorMessage += "Could not upload file. ";
//            return errorMessage;
//        }

        finally {
            if (out != null) {
                out.close();
            }

            if (filecontent != null) {
                filecontent.close();
            }
        }

        return errorMessage;
    }

    public static boolean isImage(String fileExtension) {
        fileExtension = fileExtension.toLowerCase();
        return imageFileExtensions.contains(fileExtension);
    }
}
