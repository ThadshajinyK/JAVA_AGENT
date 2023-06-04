/**
 * Tomcat server logs monitoring
 * returns log details
 * but can't get
 *
 * through manager-tomcat only get logs
 * but it has some errors
 */

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class TomcatLogRetriever {
    public static void main(String[] args) {
        String tomcatUrl = "http://localhost:8080/";
        String username = "admin";
        String password = "fairy26";

        try {
            // Connect to the Tomcat Manager API to retrieve logs
            String logUrl = tomcatUrl + "manager/text/log?path=/&tail=100";
            URL url = new URL(logUrl);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");
            connection.setRequestProperty("Authorization", getBasicAuthHeader(username, password));

            int responseCode = connection.getResponseCode();
            if (responseCode == HttpURLConnection.HTTP_OK) {
                // Read the log data from the response
                BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
                String line;
                while ((line = reader.readLine()) != null) {
                    System.out.println(line);
                }
                reader.close();
            } else {
                System.out.println("Failed to retrieve logs (Response Code: " + responseCode + ")");
            }
            connection.disconnect();
        } catch (IOException e) {
            System.out.println("Error occurred while retrieving logs: " + e.getMessage());
        }
    }

    private static String getBasicAuthHeader(String username, String password) {
        String credentials = username + ":" + password;
        String encodedCredentials = java.util.Base64.getEncoder().encodeToString(credentials.getBytes());
        return "Basic " + encodedCredentials;
    }
}
