package util;

public class EmailParse {

    private String toFind;
    private String toAppend;
    private int buff = 0;
    private Boolean endParagraph = false;

    public EmailParse(String toFind, String toAppend, int buffer) {
        setVals(toFind, toAppend);
        this.buff = buffer;
    }

    public EmailParse(String toFind, String toAppend, int buffer, Boolean endParagraph) {
        setVals(toFind, toAppend);
        this.buff = buffer;
        this.endParagraph = endParagraph;
    }

    public EmailParse(String toFind, String toAppend, Boolean endParagraph) {
        setVals(toFind, toAppend);
        this.endParagraph = endParagraph;
    }

    public EmailParse(String toFind, String toAppend) {
        setVals(toFind, toAppend);
    }

    private void setVals(String toFind, String toAppend) {
        this.toFind = toFind;
        this.toAppend = toAppend;
    }

    public String parse(String start) {
        int offset = start.indexOf(toFind);
        if (offset >= 0) {
            String toReturn = start.substring(0, offset) + toAppend + start.substring(offset + toFind.length() + buff);
            if (endParagraph) {
                toReturn += "</p>";
            }
            return toReturn;
        }
        return start;
    }

    public String parseWhile(String start) {
        String newStart = start;
        int offset = start.indexOf(toFind);
        while (offset >= 0) {
            newStart = start.substring(0, offset) + toAppend + start.substring(offset + toFind.length() + buff);
            if (endParagraph) {
                newStart += "</p>";
            }

            offset = newStart.indexOf(toFind);
        }
        return newStart;
    }
}
