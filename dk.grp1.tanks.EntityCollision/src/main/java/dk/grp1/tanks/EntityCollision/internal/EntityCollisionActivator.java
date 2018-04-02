package dk.grp1.tanks.EntityCollision.internal;

import dk.grp1.tanks.common.services.IPostEntityProcessingService;
import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;

/**
 * Extension of the default OSGi bundle activator
 */
public final class EntityCollisionActivator
    implements BundleActivator
{
    /**
     * Called whenever the OSGi framework starts our bundle
     */
    public void start( BundleContext bc )
        throws Exception
    {
        System.out.println( "STARTING dk.grp1.tanks.EntityCollision" );
        bc.registerService(IPostEntityProcessingService.class.getName(), new EntityCollisionPostProcessingSystem(), null);
    }

    /**
     * Called whenever the OSGi framework stops our bundle
     */
    public void stop( BundleContext bc )
        throws Exception
    {
        System.out.println( "STOPPING dk.grp1.tanks.EntityCollision" );

        // no need to unregister our service - the OSGi framework handles it for us
    }
}

