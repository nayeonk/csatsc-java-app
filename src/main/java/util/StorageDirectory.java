package util;

import javax.servlet.ServletContext;

public class StorageDirectory {
    public static final String ROOT = System.getenv("WEB_STORAGE_ROOT"); /* Original ="/opt/summercamp/" */
    public static final String CAMP_FILE = ROOT + "/camps/";
    public static final String REDUCED_MEALS = ROOT + "/reduced_meals/";
    public static final String STAFF = ROOT + "/staff/";
    static final String GRADE_REPORTS = ROOT + "/grade_reports/";
    static final String INSURANCE_BACK = ROOT + "/insurance_cards/back/";
    static final String INSURANCE_FRONT = ROOT + "/insurance_cards/front/";
    private static final String PDF_TEMPLATES = "/WEB-INF/PDFTemplates/";

    public static String getPdfTemplateDirectory(ServletContext servletContext) {
        return servletContext.getRealPath(StorageDirectory.PDF_TEMPLATES);
    }
}
