package dk.grp1.tanks.gamemap.internal;



import dk.grp1.tanks.common.services.IGamePluginService;
import dk.grp1.tanks.common.services.INonEntityProcessingService;
import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;

/**
 * Extension of the default OSGi bundle activator
 */
public final class GameMapActivator
    implements BundleActivator
{
    /**
     * Called whenever the OSGi framework starts our bundle
     */
    public void start( BundleContext bc )
        throws Exception
    {
        System.out.println( "STARTING dk.grp1.tanks.gamemap" );
        bc.registerService(IGamePluginService.class.getName(),new GameMapPlugin(),null);
        bc.registerService(INonEntityProcessingService.class.getName(), new GameMapProcessing(),null);

    }

    /**
     * Called whenever the OSGi framework stops our bundle
     */
    public void stop( BundleContext bc )
        throws Exception
    {
        System.out.println( "STOPPING dk.grp1.tanks.gamemap" );

        // no need to unregister our service - the OSGi framework handles it for us
    }
}

