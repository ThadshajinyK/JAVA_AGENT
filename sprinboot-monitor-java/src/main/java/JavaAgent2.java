import java.lang.management.ManagementFactory;
import java.lang.management.RuntimeMXBean;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Metrics for uptime, availability, response time, and request time.
 */
public class JavaAgent2 {
    public static void main(String[] args) throws Exception {
        // Get the runtime MXBean
        RuntimeMXBean runtimeMxBean = ManagementFactory.getRuntimeMXBean();

        // Get the start time of the JVM
        long jvmStartTime = runtimeMxBean.getStartTime(); // returns the VM start time in ms

        // Set the URL of the server endpoint to monitor
        String serverUrl = "http://localhost:8080/";

        while (true) {
            // Make a connection to the server endpoint
            long start = System.currentTimeMillis();
            try {
                URL url = new URL(serverUrl); // to set the url
                HttpURLConnection con = (HttpURLConnection) url.openConnection();
                con.setRequestMethod("GET");
                int status = con.getResponseCode();
                long end = System.currentTimeMillis(); // set the surrent time as end time,
                long responseTimeInMillis = end - start;// then current - start = response time
                long uptimeInMillis = runtimeMxBean.getUptime(); // directly retrun uptime from a function
                long requestTimeInMillis = start - jvmStartTime;
                System.out.println("Server is available");
                System.out.println("Uptime: " + uptimeInMillis + " ms");
                System.out.println("Request time: " + requestTimeInMillis + " milliseconds.");
                System.out.println("Response time: " + responseTimeInMillis + " milliseconds.");
            } catch (Exception e) {
                System.out.println("Server is not available");
            }

            // at 2 seconds gap interval.... run again and again
            try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}

/**
 * thread la ippa run aahuthu.
 * next atha apdiye naanka db ku connect pannanum. send panni paakanum
 * so athuku naanka backend sariya endu paathu rest api end points ellam create pannaum
 * postman la poddu check pannaum
 *
 */