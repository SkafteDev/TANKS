package dk.grp1.tanks.weapon.gravitybomb.internal;

import dk.grp1.tanks.common.services.IGamePluginService;
import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;

import java.util.Dictionary;
import java.util.Properties;

/**
 * Extension of the default OSGi bundle activator
 */
public final class GravityBombActivator
    implements BundleActivator
{
    /**
     * Called whenever the OSGi framework starts our bundle
     */
    public void start( BundleContext bc ) throws Exception {
        System.out.println( "STARTING dk.grp1.tanks.weapon.gravitybomb" );
        bc.registerService(IGamePluginService.class.getName(), new GravityBombPlugin(), null);
    }

    /**
     * Called whenever the OSGi framework stops our bundle
     */
    public void stop( BundleContext bc ) throws Exception {
        System.out.println( "STOPPING dk.grp1.tanks.weapon.gravitybomb" );
    }
}

