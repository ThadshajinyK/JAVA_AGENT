/**
 * uptime , availabilty, response time, request time
 * return Metrics for uptime , availabilty, response time, request time.
 */

import java.lang.management.ManagementFactory;
import java.lang.management.RuntimeMXBean;
import java.net.HttpURLConnection;
import java.net.URL;


/**
 * Metrics for uptime , availabilty, response time, request time.
 */

public class JavaAgent {
    public static void main(String[] args) throws Exception{
        // Get the runtime MXBean
        RuntimeMXBean runtimeMxBean = ManagementFactory.getRuntimeMXBean();

        // Get the start time of the JVM
        long jvmStartTime = runtimeMxBean.getStartTime(); // returns the VM start time in ms

        // Calculate the uptime of the JVM
        long uptimeInMillis = runtimeMxBean.getUptime(); ///returns uptime in ms

        // Convert uptime to a human-readable format
        long uptimeInSeconds = uptimeInMillis / 1000;
        long uptimeInMinutes = uptimeInSeconds / 60;
        long uptimeInHours = uptimeInMinutes / 60;
        long uptimeInDays = uptimeInHours / 24;

        // Set the URL of the server endpoint to monitor
        String serverUrl = "http://localhost:8080/";

        // Make a connection to the server endpoint
        long start = System.currentTimeMillis();
        try {
            URL url = new URL(serverUrl);
            HttpURLConnection con = (HttpURLConnection) url.openConnection();
            con.setRequestMethod("GET");
            int status = con.getResponseCode();
            long end = System.currentTimeMillis();
            long responseTimeInMillis = end - start;
            long requestTimeInMillis = start - jvmStartTime;
            System.out.println("Server is available");
            //System.out.println("Uptime: " + uptimeInDays + " days, " + uptimeInHours % 24 + " hours, " + uptimeInMinutes % 60 + " minutes, " + uptimeInSeconds % 60 + " seconds.");
            System.out.println("Uptime: "+uptimeInMillis +" ms");
            System.out.println("Request time: " + requestTimeInMillis + " milliseconds.");
            System.out.println("Response time: " + responseTimeInMillis + " milliseconds.");
        } catch (Exception e) {
            System.out.println("Server is not available");
        }

    }

}
/**
 * Metrics for uptime and availability only
 */
/*import java.lang.management.ManagementFactory;
import java.lang.management.RuntimeMXBean;
import java.net.HttpURLConnection;
import java.net.URL;

public class JavaAgent {
    public static void main(String[] args) {
        // Get the runtime MXBean
        RuntimeMXBean runtimeMxBean = ManagementFactory.getRuntimeMXBean();

        // Get the start time of the JVM
        long jvmStartTime = runtimeMxBean.getStartTime(); // retruns the VM start time in ms

        // Calculate the uptime of the JVM
        long uptimeInMillis = runtimeMxBean.getUptime(); ///returns uptime in ms

        // Convert uptime to a human-readable format
        long uptimeInSeconds = uptimeInMillis / 1000;
        long uptimeInMinutes = uptimeInSeconds / 60;
        long uptimeInHours = uptimeInMinutes / 60;
        long uptimeInDays = uptimeInHours / 24;

        // Set the URL of the server endpoint to monitor
        String serverUrl = "http://localhost:8080/"; //server which you want to monitor

        // Make a connection to the server endpoint
        long start = System.currentTimeMillis();
        try {
            URL url = new URL(serverUrl);
            HttpURLConnection con = (HttpURLConnection) url.openConnection();
            con.setRequestMethod("GET");
            int status = con.getResponseCode();
            System.out.println("Server is available.");
            System.out.println("Uptime: "+uptimeInMillis +" ms");

        } catch (Exception e) {
            System.out.println("Server is not available.");
        }
    }

}*/

