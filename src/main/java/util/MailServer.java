package util;

import data.Email;
import database.DatabaseQueries;

import enums.EmailType;
import model.AwsSecret;
import util.EmailConstants;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import javax.servlet.ServletContext;
import java.io.*;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.Properties;
import java.util.stream.Collectors;


public class MailServer {
    private static final String messageOpenTags = "<html><body><div style=\"font-family: 'Helvetica Neue', Helvetica, Arial, sans-serif; font-weight: 200; max-width:600px; padding:20px;\">";
    private static final String messageCloseTags = "<br><p style=\"font-size: 16px;\"><strong>Jeffrey Miller, Ph.D.</strong><br>Associate Professor of Engineering Practice<br>Department of Computer Science<br>CS@SC Summer Camps Director<br>http://summercamp.usc.edu<br><br>USC Viterbi School of Engineering<br>University of Southern California<br>941 Bloom Walk, SAL 342<br>Los Angeles, California 90089-0781<br>jeffrey.miller@usc.edu<br>213-740-7129 (office)</p></div></body></html>";

    private static Session session;

    public static String getPassword(ServletContext servletContext) {
        try {
            InputStream is = servletContext.getResourceAsStream("/WEB-INF/cssc-email-password.txt");
            BufferedReader br = new BufferedReader(new InputStreamReader(is));
            return br.readLine();

        } catch (IOException e) {
            e.printStackTrace();
        }

        return "";

    }

    private static Session getSession(ServletContext servletContext) {
        if (MailServer.session == null) {
            AwsSecret secret = AwsSecretManagerUtil.fetch();
            MailServer.session = Session.getInstance(defaultProperties(),
                    new javax.mail.Authenticator() {
                        protected PasswordAuthentication getPasswordAuthentication() {
                            return new PasswordAuthentication(secret.getEmailUsername(), secret.getDbPassword());
                        }
                    });
        }

        return MailServer.session;
    }

    private static Properties defaultProperties() {
        Properties props = new Properties();
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
        String hostname = "smtp.office365.com";
        props.put("mail.smtp.host", hostname);
        props.put("mail.smtp.port", "587");
        return props;
    }

    public static void sendEmail(String emailTo, String subject, String content, ServletContext servletContext) {
        try {
            Message message = buildEmail(emailTo, subject, content, servletContext);
            if (shouldTransportMessage(message)) {
                Transport.send(message);
            }
        } catch (MessagingException e) {
            e.printStackTrace();
        }
    }

    public static void sendEmail(String emailTo, String subject, String content, String attachment, ServletContext servletContext) {
        try {
            Message message = buildEmail(emailTo, subject, content,  servletContext);

            Multipart multipart = new MimeMultipart();
            MimeBodyPart attachPart = new MimeBodyPart();
            //MimeBodyPart messageBodyPart = new MimeBodyPart();

//            attachPart.attachFile(attachment);

            //multipart.addBodyPart(messageBodyPart);
            multipart.addBodyPart(attachPart);

            message.setContent(multipart);

            if (shouldTransportMessage(message)) {
                Transport.send(message);
            }
        } catch (MessagingException e) {
            e.printStackTrace();
        }
    }

    /**
     * Builds a message that sets the recipient, subject, content, and proper encoding headers
     *
     * @param emailTo Recipient
     * @param subject Email subject
     * @param content Email body
     * @return Message
     * @throws MessagingException if cannot build message
     */
    private static Message buildEmail(String emailTo, String subject, String content, ServletContext servletContext) throws MessagingException {
        Message message = buildMessage(emailTo, subject, servletContext);
        String bodyContent = messageOpenTags + content + messageCloseTags;
        message.setContent(bodyContent, "text/html; charset=utf-8");
        return message;
    }

    /**
     * Cross checks the message with the environment setting to determine if mail should be sent out
     * PRODUCTION:
     * Always true
     * DEVELOPMENT:
     * False if ANY of the recipients are not on the whitelist specified by
     * EnvironmentConstants.ALLOWED_DEV_EMAILS
     * <p>
     * Raises an error if environment variable is not set
     *
     * @param message Message
     * @return boolean if message should be transported
     * @throws MessagingException if the message is invalid
     */
    private static boolean shouldTransportMessage(Message message) throws MessagingException {
        if (System.getenv(EnvironmentConstants.ENV).equals(EnvironmentConstants.ENV_PRODUCTION)) {
            return true;
        } else if (System.getenv(EnvironmentConstants.ENV).equals(EnvironmentConstants.ENV_DEVELOPMENT)) {
            String[] allowedDevEmails = System.getenv(EnvironmentConstants.ALLOWED_DEV_EMAILS).split(",");
            List<String> recipientIntersection = Arrays
                    .stream(message.getAllRecipients())
                    .map(Objects::toString)
                    .filter(Arrays.asList(allowedDevEmails)::contains)
                    .collect(Collectors.toList());

            return recipientIntersection.size() > 0;
        } else {
            throw new Error(
                    String.format(
                            "Environment variable '%s' must be set to either '%s' or '%s'",
                            EnvironmentConstants.ENV,
                            EnvironmentConstants.ENV_PRODUCTION,
                            EnvironmentConstants.ENV_DEVELOPMENT
                    )
            );
        }
    }

    private static Message buildMessage(String emailTo, String subject, ServletContext servletContext) throws MessagingException {
        Message message = new MimeMessage(MailServer.getSession(servletContext ));
        AwsSecret secret = AwsSecretManagerUtil.fetch();
        InternetAddress fromAddr = new InternetAddress(secret.getEmailUsername());
        message.setFrom(fromAddr);
        message.setRecipient(Message.RecipientType.BCC, fromAddr);
        message.setRecipients(Message.RecipientType.TO,
                InternetAddress.parse(emailTo));
        message.setSubject(subject);
        return message;
    }

    public static String parseEmail(String contents) {
        StringBuilder contentsFinal = new StringBuilder(contents);
        contentsFinal = parseLinks(contentsFinal);

        int offset = contentsFinal.indexOf("!");
        int add = 1;
        if (!(offset < 30 && offset > 0)) {
            offset = contentsFinal.indexOf("CS@SC Summer Camps");
            add = "CS@SC Summer Camps".length();
        }
//		if(offset < 30 && offset > 0){
//			contentsFinal.insert(offset+1, "</h1><p style=\"font-size: 20px;\">");
//			contentsFinal.insert(0, "<h1 align=\"center\" style=\"font-weight:200; font-size: 30px;\">");
//		}
//		else{
//			offset = contentsFinal.indexOf("CS@SC Summer Camps");
        if (offset >= 0) {
            contentsFinal.insert(offset + add, "</h1><p style=\"font-size: 20px;\">");
            contentsFinal.insert(offset, "<h1 align=\"center\" style=\"font-weight:200; font-size: 30px;\">");
            //}
        }
        contentsFinal = parseLogIns(contentsFinal);

        offset = contentsFinal.indexOf("\n");
        while (offset >= 0) {
            contentsFinal.replace(offset, offset + 1, "<br>");
            offset = contentsFinal.indexOf("\n", offset + 4);
        }
        return contentsFinal.toString();
    }

    public static StringBuilder parseLogIns(StringBuilder contentsFinal) {
        String register = "<a href=\"http://summercamp.usc.edu:8080/SummerCamp/register.jsp\">";
        List<String> logIns = Arrays.asList("log in", "logging in", "Log in", "Logging in");
        for (String logIn : logIns) {
            int offset = contentsFinal.indexOf(logIn);

            while (offset >= 0) {
                contentsFinal.insert(offset + logIn.length(), "</a>");
                contentsFinal.insert(offset, register);
                offset = contentsFinal.indexOf(logIn.toLowerCase(), offset + logIn.length() + register.length());
            }
        }

        return contentsFinal;
    }

    public static StringBuilder parseLinks(StringBuilder contentsFinal) {
        int offset = contentsFinal.indexOf("instruction");
        if (offset >= 0) {
            int instIndex = offset;
            offset = contentsFinal.indexOf("Click here", instIndex - 20);
            if (!(offset >= 0 && offset > instIndex - 20)) {
                offset = contentsFinal.indexOf("click here", instIndex - 20);
            }
            String instructions = "<a href=\"http://summercamp.usc.edu/wordpress/wp-content/uploads/2016/03/Paid-Camp-Instructions\">";
//			if(offset >= 0 && offset > instIndex-20){
//				contentsFinal.insert(offset, instructions);
//				contentsFinal.insert(offset + instructions.length()+10, "</a>");
//			}
//			else{
//				offset = contentsFinal.indexOf("click here", instIndex - 20);
            if (offset >= 0 && offset > instIndex - 20) {
                contentsFinal.insert(offset, instructions);
                contentsFinal.insert(offset + instructions.length() + 10, "</a>");
            }
            //}
        }

        offset = contentsFinal.indexOf("schedule");
        if (offset >= 0) {
            int scheduleIndex = offset;
            offset = contentsFinal.indexOf("Click here", scheduleIndex - 50);
            if (!(offset >= 0 && offset > scheduleIndex - 50)) {
                offset = contentsFinal.indexOf("click here", scheduleIndex - 50);
            }
            String schedule = "<a href=\"http://summercamp.usc.edu/schedule/\">";
//			if(offset >= 0 && offset > scheduleIndex-50){
//				contentsFinal.insert(offset, schedule);
//				contentsFinal.insert(offset + schedule.length()+10, "</a>");
//			}
//			else{
//				offset = contentsFinal.indexOf("click here", scheduleIndex - 50);
            if (offset >= 0 && offset > scheduleIndex - 50) {
                contentsFinal.insert(offset, schedule);
                contentsFinal.insert(offset + schedule.length() + 10, "</a>");
            }
            //}
        }

        offset = contentsFinal.indexOf("parking");
        if (offset >= 0) {
            int parkingIndex = offset;
            offset = contentsFinal.indexOf("Click here", parkingIndex - 40);
            if (!(offset >= 0 && offset > parkingIndex - 40)) {
                offset = contentsFinal.indexOf("click here", parkingIndex - 40);
            }
            String parking = "<a href=\"http://summercamp.usc.edu/directions-parking/\">";
//			if(offset >= 0 && offset > parkingIndex-40){
//				contentsFinal.insert(offset, parking);
//				contentsFinal.insert(offset + parking.length()+10, "</a>");
//			}
//			else{
//				offset = contentsFinal.indexOf("click here", parkingIndex - 40);
            if (offset >= 0 && offset > parkingIndex - 40) {
                contentsFinal.insert(offset, parking);
                contentsFinal.insert(offset + parking.length() + 10, "</a>");
            }
            //}
        }
        return contentsFinal;
    }

    public static String emailWaitlistOrReject(EmailType emailType, String studentName, String campLevel, String campTopic, String campDates1) {
        String parseVariables = parseEmail(DatabaseQueries.getEmailContents(emailType));
        parseVariables = new EmailParse(EmailConstants.STUDENT_NAME, studentName).parseWhile(parseVariables);
        parseVariables = new EmailParse(EmailConstants.CAMP_LEVEL, campLevel).parseWhile(parseVariables);
        parseVariables = new EmailParse(EmailConstants.CAMP_TOPIC, campTopic).parseWhile(parseVariables);
        return new EmailParse(EmailConstants.CAMP_DATES_1, campDates1 + ").</p><p style=\"font-size: 18px;\">", 2, true).parse(parseVariables);
    }

    public static String parseSubject(String subject, String campLevel, String campTopic) {
        List<EmailParse> toParse = Arrays.asList(new EmailParse(EmailConstants.CAMP_LEVEL, campLevel), new EmailParse(EmailConstants.CAMP_TOPIC, campTopic));
        return parseVars(toParse, subject);

    }

    public static String parseVars(List<EmailParse> vars, String start) {
        String parsed = start;
        for (EmailParse var : vars) {
            parsed = var.parse(parsed);
        }
        return parsed;
    }
}
