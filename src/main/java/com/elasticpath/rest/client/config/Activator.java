package com.elasticpath.rest.client.config;

import com.google.inject.Guice;
import com.google.inject.Injector;

import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;

import com.elasticpath.rest.client.unmarshalling.JacksonProvider;
import com.elasticpath.rest.client.urlbuilding.CortexUrlFactory;
import com.elasticpath.rest.client.unmarshalling.JsonUnmarshallReaderInterceptor;

/**
 * This class implements a simple bundle that uses the bundle
 * context to register an English language dictionary service
 * with the OSGi framework. The dictionary service interface is
 * defined in a separate class file and is implemented by an
 * inner class.
 **/
public class Activator implements BundleActivator {
	/**
	 * Implements BundleActivator.start(). Registers an
	 * instance of a dictionary service using the bundle context;
	 * attaches properties to the service that can be queried
	 * when performing a service look-up.
	 * @param context the framework context for the bundle.
	 **/
	public void start(BundleContext context)
	{
		Injector injector = Guice.createInjector(
				new GuiceConfig()
		);
		CortexUrlFactory cortexUrlFactory = injector.getInstance(CortexUrlFactory.class);

		context.registerService(CortexUrlFactory.class.getName(), cortexUrlFactory, null);

		register(context, injector, JacksonProvider.class);
		register(context, injector, JsonUnmarshallReaderInterceptor.class);
	}

	private void register(BundleContext context,
						  Injector injector,
						  Class<?> clazz) {
		context.registerService(
				clazz.getName(),
				injector.getInstance(clazz),
				null
		);
	}

	/**
	 * Implements BundleActivator.stop(). Does nothing since
	 * the framework will automatically unregister any registered services.
	 * @param context the framework context for the bundle.
	 **/
	public void stop(BundleContext context)
	{
		// NOTE: The service is automatically unregistered.
	}

}