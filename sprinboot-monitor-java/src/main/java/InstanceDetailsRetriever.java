/**
 * tomcat instance details retriever
 * return how many instances are there
 * return list of instances details : hostname, name
 *
 * working properly but with null values in localhost:8080
 */

import javax.management.*;
import java.io.IOException;
import java.lang.management.ManagementFactory;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;
import java.util.stream.Collectors;

public class InstanceDetailsRetriever {
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

        //print the instances details
        String hostname = args.length > 0 ? args[0] : "http://localhost:8080/"; // Use "localhost" as default if no argument provided
        List<String> deployedInstances = instanceDetailsRetriever.getDeployedInstances(hostname);
        System.out.println("Deployed Instances: " + deployedInstances);

    /*
    temperary work
     */

    }
    public void setMBeanServerConnection(MBeanServerConnection mBeanServerConnection) {
        this.mBeanServerConnection = mBeanServerConnection;
    }

    // method to list out all the instances
    public List<String> getDeployedInstances(String hostname) throws MalformedObjectNameException, IOException {
        Set<ObjectName> deployedModules = new TreeSet<>(mBeanServerConnection.queryNames(ObjectName.getInstance("Catalina:j2eeType=WebModule,*"), null));
        List<String> deployedInstances = deployedModules
                .stream().map(deployedModule -> deployedModule.getKeyProperty("name").replace("//" + hostname + "/", ""))
                .collect(Collectors.toList());
        return deployedInstances;
    }

}




