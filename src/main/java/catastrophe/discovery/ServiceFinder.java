package catastrophe.discovery;

import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Set;

import com.ecwid.consul.transport.TransportException;
import com.ecwid.consul.v1.ConsistencyMode;
import com.ecwid.consul.v1.ConsulClient;
import com.ecwid.consul.v1.QueryParams;
import com.ecwid.consul.v1.Response;
import com.ecwid.consul.v1.catalog.model.CatalogService;

public class ServiceFinder {

	private static final QueryParams QUERY_PARAMS = new QueryParams(ConsistencyMode.DEFAULT);
	private static final Random RANDOM = new Random();
	private static final String CONSUL_HOST = System.getenv("CONSUL_HOST");
	private static final int CONSUL_PORT = Integer.valueOf(System.getenv("CONSUL_PORT"));
	Set<String> serviceIds = new HashSet<String>();
	final ConsulClient client;

	public ServiceFinder() {
		// This entry needs to be in /etc/hosts, pointing to the IP address
		// of the docker container
		client = new ConsulClient(CONSUL_HOST, CONSUL_PORT);
	}

	public String getHostAndPort(String serviceName) {

		serviceName = serviceName.replaceAll("/", "");

		// Check environment variables first, in case we have consul problems

		Map<String, String> env = System.getenv();

		// Prefer an environment variable if one is set

		String key = "SERVICE_" + serviceName.replace('.', '-');
		System.out.println("Checking environment for " + key);
		String value = env.get(key);
		if (value != null) {
			System.out.println("Using environment variable: " + value);
			return value;
		}

		try {

			Response<List<CatalogService>> r = client.getCatalogService(serviceName, QUERY_PARAMS);
			List<CatalogService> list = r.getValue();
			int numberOfServices = list.size();
			if (numberOfServices > 0) {
				// Do a simple random-robin :)
				int index = RANDOM.nextInt(numberOfServices);
				System.out.println("Choosing service " + index + " of " + numberOfServices);
				CatalogService service = list.get(index);
				// Massive temporary hack
				if (!service.getServiceAddress().startsWith("169.")) {
					return service.getServiceAddress() + ":" + service.getServicePort();
				} else {
					// Use hard-coded values to work out the secure gateway path
					// This is the opposite of service discovery, of course
					int port = service.getServicePort();
					if (port == 8085) { // users, pi2
						return "cap-sg-prd-5.integration.ibmcloud.com:17926";
					} else if (port == 8082) { // cats, red pi
						return "cap-sg-prd-5.integration.ibmcloud.com:16451";
					} else if (port == 8081) { // scoring, localhost
						return "cap-sg-prd-5.integration.ibmcloud.com:16193";
					} else {
						System.out.println("Could not unmap " + service.getServiceAddress());
						return "unknown:8080";
					}

				}
			} else {
				System.out.println("No services available with name " + serviceName);
			}
		} catch (

		TransportException e) {
			System.out.println(e + CONSUL_HOST);
		}
		return null;
	}
}
