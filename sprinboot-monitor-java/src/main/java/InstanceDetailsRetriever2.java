
/**
 * Tomcat instance details retriever
 * Returns the number of instances and a list of instance details (hostname, name)
 */
import javax.management.*;
        import java.io.IOException;
        import java.lang.management.ManagementFactory;
        import java.util.List;
        import java.util.Set;
        import java.util.TreeSet;
        import java.util.stream.Collectors;

public class InstanceDetailsRetriever2 {
    private MBeanServerConnection mBeanServerConnection;

    public static void main(String[] args) throws Exception {
        InstanceDetailsRetriever instanceDetailsRetriever = new InstanceDetailsRetriever();

        // Get the MBean server
        MBeanServer mBeanServer = ManagementFactory.getPlatformMBeanServer();
        instanceDetailsRetriever.setMBeanServerConnection(mBeanServer);

        // Create the ObjectName for the Manager MBean
        ObjectName objectName = new ObjectName("Catalina:type=Manager,*");

        // Query the MBean server to retrieve all Manager MBeans
        Set<ObjectName> managerMBeans = mBeanServer.queryNames(objectName, null);

        // Count the number of Manager MBeans (representing instances)
        int instanceCount = managerMBeans.size();

        // Print the instance count
        System.out.println("Number of Instances: " + instanceCount);

        // Print the instances details
        String hostname = "http://localhost:8080/"; // Update the hostname to "localhost"
        List<String> deployedInstances = instanceDetailsRetriever.getDeployedInstances(hostname);
        System.out.println("Deployed Instances: " + deployedInstances);
    }

    public void setMBeanServerConnection(MBeanServerConnection mBeanServerConnection) {
        this.mBeanServerConnection = mBeanServerConnection;
    }

    // Method to list all the instances
    public List<String> getDeployedInstances(String hostname) throws MalformedObjectNameException, IOException {
        String objectNamePattern = "Catalina:j2eeType=WebModule,*";
        Set<ObjectName> deployedModules = new TreeSet<>(mBeanServerConnection.queryNames(ObjectName.getInstance(objectNamePattern), null));
        List<String> deployedInstances = deployedModules
                .stream().map(deployedModule -> deployedModule.getKeyProperty("name").replace("//" + hostname + "/", ""))
                .collect(Collectors.toList());
        return deployedInstances;
    }
}
