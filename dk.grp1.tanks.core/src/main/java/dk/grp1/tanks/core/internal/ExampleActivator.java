package dk.grp1.tanks.core.internal;


import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import dk.grp1.tanks.common.ExampleService;
import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;

import java.util.Dictionary;
import java.util.Properties;

/**
 * Extension of the default OSGi bundle activator
 */
public final class ExampleActivator
    implements BundleActivator
{
    LwjglApplication app;
    /**
     * Called whenever the OSGi framework starts our bundle
     */
    public void start( BundleContext bc )
        throws Exception
    {
        System.out.println( "STARTING dk.grp1.tanks.core" );

        Dictionary props = new Properties();
        // add specific service properties here...

        System.out.println( "REGISTER dk.grp1.tanks.core.ExampleService" );

        // Register our example service implementation in the OSGi service registry
        bc.registerService( ExampleService.class.getName(), new ExampleServiceImpl(), props );

        LwjglApplicationConfiguration cfg = new LwjglApplicationConfiguration();
        cfg.title = "Tanks";
        cfg.width = 1800;
        cfg.height = 1000;
        cfg.useGL30 = false;
        cfg.resizable = false;
        app = new LwjglApplication(new Game(), cfg);

    }

    /**
     * Called whenever the OSGi framework stops our bundle
     */
    public void stop( BundleContext bc )
        throws Exception
    {
        System.out.println( "STOPPING dk.grp1.tanks.core" );
        app.stop();
        app.exit();


        // no need to unregister our service - the OSGi framework handles it for us
    }
}

