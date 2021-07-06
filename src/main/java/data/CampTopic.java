package data;

import java.util.List;

public class CampTopic implements java.io.Serializable {

    public static final long serialVersionUID = 1;

    private int campTopicID;
    private String topic;
    private List<CampOffered> campOfferedList;
    private List<CampOffered> allCampOfferedList;

    public CampTopic(int campTopicID, String topic) {
        this.campTopicID = campTopicID;
        this.topic = topic;
    }

    public int getCampTopicID() {
        return campTopicID;
    }

    public void setCampTopicID(int campTopicID) {
        this.campTopicID = campTopicID;
    }

    public String getTopic() {
        return topic;
    }

    public void setTopic(String topic) {
        this.topic = topic;
    }

    public List<CampOffered> getCampOfferedList() {
        return campOfferedList;
    }

    public void setCampOfferedList(List<CampOffered> campOfferedList) {
        this.campOfferedList = campOfferedList;
    }

    public List<CampOffered> getAllCampOfferedList() {
        return allCampOfferedList;
    }

    public void setAllCampOfferedList(List<CampOffered> allCampOfferedList) {
        this.allCampOfferedList = allCampOfferedList;
    }
}
