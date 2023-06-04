import javax.management.MBeanServerConnection;
import javax.management.ObjectName;
import javax.management.remote.JMXConnector;
import javax.management.remote.JMXConnectorFactory;
import javax.management.remote.JMXServiceURL;

public class JavaForDetails {
    public static void main(String[] args) throws Exception {
        // Connect to the JMX connector on the Tomcat server
        String host = "<Tomcat server hostname or IP address>";
        int port = 8080;
        JMXServiceURL url = new JMXServiceURL("service:jmx:rmi:///jndi/rmi://" + host + ":" + port + "/jmxrmi");
        JMXConnector connector = JMXConnectorFactory.connect(url);
        MBeanServerConnection connection = connector.getMBeanServerConnection();

        // Retrieve metrics and data using JMX API
        ObjectName serverObjName = new ObjectName("Catalina:type=Server");
        String serverInfo = (String) connection.getAttribute(serverObjName, "serverInfo");
        System.out.println("Tomcat server info: " + serverInfo);

        ObjectName[] appObjNames = connection.queryNames(new ObjectName("Catalina:type=WebModule,*"), null).toArray(new ObjectName[0]);
        for (ObjectName appObjName : appObjNames) {
            String appName = (String) connection.getAttribute(appObjName, "name");
            String appContextPath = (String) connection.getAttribute(appObjName, "contextPath");
            System.out.println("Tomcat application: " + appName + " at " + appContextPath);
        }

        // Close the JMX connector
        connector.close();
    }
}
