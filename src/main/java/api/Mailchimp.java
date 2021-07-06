package api;

import com.google.gson.JsonObject;
import org.apache.commons.codec.binary.Base64;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;


public class Mailchimp {

    private static final String URL = "https://us20.api.mailchimp.com/3.0/";
    private static final String APIKEY = "14510b99139ecb8c08ae53a2dd42d1aa-us20";
    private static final String ListID = "900d012c92";
    private static final String AddListURL = "lists/" + ListID + "/members/";
    private static final String searchEmails = "search-members?query=";

    /*
     *  return: Authorization for API call
     */
    public static String getAuth() {
        String username = "anystring";
        String password = APIKEY;
        String authstring = username + ":" + password;

        byte[] encoding = Base64.encodeBase64(authstring.getBytes());
        String auth = new String(encoding);
        return auth;
    }

    /*
     * CHECK IF EMAIL IS IN THE MAILING LIST, IF NOT ADD TO LIST USING addEmail();
     */
    public static boolean subscribe(String email) {
        boolean added = false;
        if (emailSubscribed(email)) {
            return true;
        } else {
            added = addEmail(email);
        }
        return added;
    }


    public static boolean emailSubscribed(String email) {
        String auth = getAuth();

        try {
            URL urlConnector = new URL(URL + searchEmails + email);
            HttpURLConnection http = (HttpURLConnection) urlConnector.openConnection();
            http.setRequestMethod("GET");
            http.setDoOutput(true);
            http.setDoInput(true);
            http.setRequestProperty("Content-Type", "application/json; charset=UTF-8");
            http.setRequestProperty("Accept", "application/json");
            http.setRequestProperty("Authorization", "Basic " + auth);

            InputStream is = http.getInputStream();
            StringBuilder sb = new StringBuilder();
            BufferedReader br = new BufferedReader(new InputStreamReader(is, StandardCharsets.UTF_8));

            String line = null;
            while ((line = br.readLine()) != null) {
                System.out.println(line);
                sb.append(line + "\n");

            }
            br.close();

            String result = sb.toString();
            if (result.contains(email)) {
                return true;
            }

        } catch (IOException e) {
            System.out.println(e.getMessage());
            e.printStackTrace();
        }

        return false;
    }

    public static boolean addEmail(String email) {
        JsonObject info = new JsonObject();
        info.addProperty("email_address", email);
        info.addProperty("status", "subscribed");

        String json = info.toString();
        System.out.println(json);
        String auth = getAuth();

        try {
            URL urlConnector = new URL(URL + AddListURL);
            HttpURLConnection http = (HttpURLConnection) urlConnector.openConnection();
            http.setRequestMethod("POST");
            http.setDoOutput(true);
            http.setDoInput(true);
            http.setRequestProperty("Content-Type", "application/json; charset=UTF-8");
            http.setRequestProperty("Accept", "application/json");
            http.setRequestProperty("Authorization", "Basic " + auth);

            OutputStreamWriter w = new OutputStreamWriter(http.getOutputStream());
            w.write(json);
            w.flush();

            InputStream is = http.getInputStream();
            StringBuilder sb = new StringBuilder();
            BufferedReader br = new BufferedReader(new InputStreamReader(is, StandardCharsets.UTF_8));

            String line = null;
            while ((line = br.readLine()) != null) {
                System.out.println(line);
                sb.append(line + "\n");

            }
            br.close();

            String result = sb.toString();
            System.out.println(result);
            if (result.contains("id")) {
                return true;
            }

        } catch (IOException e) {
            System.out.println(e.getMessage());
            e.printStackTrace();
        }

        return false;
    }

    public static void main(String[] args) {
        //boolean result = Mailchimp.addEmail("test6@usc.edu");
        boolean result = Mailchimp.subscribe("abc567@gmail.edu");
        System.out.println(result);
    }
}
