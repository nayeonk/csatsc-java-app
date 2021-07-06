package data;

import org.apache.commons.io.FilenameUtils;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;

public class InsuranceCard implements java.io.Serializable {

    public static final long serialVersionUID = 1;

    private int insuranceCardID;
    private final int medicalFormID;
    private final String frontFilePath;
    private final String backFilePath;
    private String readableTimestamp;
    private boolean deleted;

    public InsuranceCard(int medFormID, String frontPath, String backPath) {
        this.medicalFormID = medFormID;
        this.frontFilePath = frontPath;
        this.backFilePath = backPath;
        setDateAndTime(frontFilePath);
    }

    public InsuranceCard(int insuranceCardID, int medFormID, String frontPath, String backPath, boolean deleted) {
        this.medicalFormID = medFormID;
        this.frontFilePath = frontPath;
        this.backFilePath = backPath;
        this.deleted = deleted;
        this.insuranceCardID = insuranceCardID;
        setDateAndTime(frontFilePath);
    }


    public String getReadableTimestamp() {
        return this.readableTimestamp;
    }

    public void setDateAndTime(String filePath) {
        String baseName = FilenameUtils.getBaseName(filePath);
        baseName = baseName.replace('_', ' ');
        int indexAfterSpace = baseName.indexOf(' ') + 1;
        baseName = baseName.substring(0, indexAfterSpace) + baseName.substring(indexAfterSpace).replace('-', ':');
        Timestamp timestamp = Timestamp.valueOf(baseName);
        this.readableTimestamp = new SimpleDateFormat("EEE, MMM dd, yyyy 'at' hh:mm aaa").format(timestamp);
    }

    public boolean isDeleted() {
        return deleted;
    }

    public void setDeleted(boolean deleted) {
        this.deleted = deleted;
    }

    public int getInsuranceCardID() {
        return insuranceCardID;
    }

    public int getMedicalFormID() {
        return medicalFormID;
    }

    public String getFrontFilePath() {
        return frontFilePath;
    }

    public String getBackFilePath() {
        return backFilePath;
    }

}
