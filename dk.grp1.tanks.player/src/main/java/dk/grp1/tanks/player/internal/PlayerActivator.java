package dk.grp1.tanks.player.internal;

import java.util.Dictionary;
import java.util.Properties;

import dk.grp1.tanks.common.services.IEntityProcessingService;
import dk.grp1.tanks.common.services.IGamePluginService;
import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;


/**
 * Extension of the default OSGi bundle activator
 */
public final class PlayerActivator
    implements BundleActivator
{
    /**
     * Called whenever the OSGi framework starts our bundle
     */
    public void start( BundleContext bc )
        throws Exception
    {
        System.out.println( "STARTING dk.grp1.tanks.player" );

        bc.registerService(IEntityProcessingService.class.getName(), new PlayerProcessingSystem(),null);
        bc.registerService(IGamePluginService.class.getName(), new PlayerGamePlugin(),null);

    }

    /**
     * Called whenever the OSGi framework stops our bundle
     */
    public void stop( BundleContext bc )
        throws Exception
    {
        System.out.println( "STOPPING dk.grp1.tanks.player" );

        // no need to unregister our service - the OSGi framework handles it for us
    }
}

