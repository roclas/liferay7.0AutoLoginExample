package exampleautologin;

import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;

public class ExampleAutoLoginActivator implements BundleActivator {

	@Override
	public void start(BundleContext bundleContext) throws Exception {
		System.out.println("\n\n\n\nAuto Loging is ON\n\n\n\n");
	}

	@Override
	public void stop(BundleContext bundleContext) throws Exception {
		System.out.println("\n\n\n\nAuto Loging is OFF\n\n\n\n");
	}

}