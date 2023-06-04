/**
 * changed code of JAVA_AGENT
 * returns all metrics
 */

import javax.management.*;
import javax.management.remote.JMXConnector;
import javax.management.remote.JMXConnectorFactory;
import javax.management.remote.JMXServiceURL;
import java.lang.management.ManagementFactory;
        import java.util.Set;

public class ApplicationMetricsRetriever {
    private MBeanServerConnection mBeanServerConnection;

    public static void main(String[] args) throws Exception {
        ApplicationMetricsRetriever metricsRetriever = new ApplicationMetricsRetriever();

        // Connect to the local Tomcat server
        String host = "localhost";
        int port = 8080;
        String urlString = "service:jmx:rmi:///jndi/rmi://" + host + ":" + port + "/jmxrmi";
        JMXServiceURL url = new JMXServiceURL(urlString);
        JMXConnector connector = JMXConnectorFactory.connect(url);
        MBeanServerConnection mBeanServer = connector.getMBeanServerConnection();
        metricsRetriever.setMBeanServerConnection(mBeanServer);

        // Create the ObjectName for the Catalina Engine MBean
        ObjectName engineObjName = new ObjectName("Catalina:type=Engine,*");

        // Query the MBean server to retrieve all Catalina Engine MBeans
        Set<ObjectName> engineObjNames = mBeanServer.queryNames(engineObjName, null);

        // Iterate over the engine MBeans
        for (ObjectName engineObjectName : engineObjNames) {
            // Create the ObjectName for the application MBeans
            String domain = engineObjectName.getDomain();
            ObjectName applicationObjectName = new ObjectName(domain + ":type=Host,host=" + host + ",*");

            // Query the MBean server to retrieve all application MBeans for the current engine
            Set<ObjectName> applicationObjectNames = mBeanServer.queryNames(applicationObjectName, null);

            // Iterate over the application MBeans
            for (ObjectName appObjectName : applicationObjectNames) {
                // Retrieve the metrics attributes
                String name = (String) mBeanServer.getAttribute(appObjectName, "name");
                int appPort = (int) mBeanServer.getAttribute(appObjectName, "port");
                double cpuUsage = (double) mBeanServer.getAttribute(appObjectName, "cpuUsage");
                long memoryCapacity = (long) mBeanServer.getAttribute(appObjectName, "memoryCapacity");
                long storageCapacity = (long) mBeanServer.getAttribute(appObjectName, "storageCapacity");

                // Print the metrics details
                System.out.println("Application Name: " + name);
                System.out.println("Port Number: " + appPort);
                System.out.println("CPU Usage: " + cpuUsage);
                System.out.println("Memory Capacity: " + memoryCapacity);
                System.out.println("Storage Capacity: " + storageCapacity);
                System.out.println();
            }
        }

        // Close the JMX connector
        connector.close();
    }

    public void setMBeanServerConnection(MBeanServerConnection mBeanServerConnection) {
        this.mBeanServerConnection = mBeanServerConnection;
    }
}
