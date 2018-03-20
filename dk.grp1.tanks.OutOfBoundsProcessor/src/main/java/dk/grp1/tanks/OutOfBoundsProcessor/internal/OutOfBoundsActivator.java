package dk.grp1.tanks.OutOfBoundsProcessor.internal;

import java.util.Dictionary;
import java.util.Properties;

import dk.grp1.tanks.common.services.IPostEntityProcessingService;
import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;

/**
 * Extension of the default OSGi bundle activator
 */
public final class OutOfBoundsActivator
    implements BundleActivator
{
    /**
     * Called whenever the OSGi framework starts our bundle
     */
    public void start( BundleContext bc )
        throws Exception
    {
        System.out.println( "STARTING dk.grp1.tanks.OutOfBoundsProcessor" );

        bc.registerService(IPostEntityProcessingService.class.getName(), new OutOfBoundsProcessingSystem() ,null);

    }

    /**
     * Called whenever the OSGi framework stops our bundle
     */
    public void stop( BundleContext bc )
        throws Exception
    {
        System.out.println( "STOPPING dk.grp1.tanks.OutOfBoundsProcessor" );

        // no need to unregister our service - the OSGi framework handles it for us
    }
}

