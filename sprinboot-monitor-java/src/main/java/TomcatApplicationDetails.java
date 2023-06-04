/**
 * temporary class file for gathering application details
 *
 * errors!!!
 */

import javax.management.*;
import javax.management.remote.JMXConnector;
import javax.management.remote.JMXConnectorFactory;
import javax.management.remote.JMXServiceURL;
import java.io.IOException;
import java.util.*;

public class TomcatApplicationDetails {

    public static void main(String[] args) throws Exception {
        // Create a JMX connector to connect to the Tomcat MBean server
        JMXServiceURL url = new JMXServiceURL("service:jmx:rmi:///jndi/rmi://localhost:9010/jmxrmi");
        JMXConnector jmxc = JMXConnectorFactory.connect(url, null);

        // Get an MBeanServerConnection to the Tomcat MBean server
        MBeanServerConnection connection = jmxc.getMBeanServerConnection();

        // Get the ObjectNames of the deployed web applications
        Set<ObjectName> appObjNames = connection.queryNames(new ObjectName("Catalina:type=WebModule,*"), null);
        for (ObjectName appObjName : appObjNames) {
            String appPath = (String) connection.getAttribute(appObjName, "path");
            System.out.println("Application path: " + appPath);
        }

        // Close the JMX connector
        jmxc.close();
    }
}

