package dk.grp1.tanks.core.internal;


import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;

import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;
import org.osgi.framework.ServiceEvent;
import org.osgi.framework.ServiceListener;

import java.util.Arrays;


/**
 * Extension of the default OSGi bundle activator
 */
public final class ExampleActivator
    implements BundleActivator//,ServiceListener
{
    LwjglApplication app;
    /**
     * Called whenever the OSGi framework starts our bundle
     */
    public void start( BundleContext bc )
        throws Exception
    {
       // bc.addServiceListener(this);
        System.out.println( "STARTING dk.grp1.tanks.core" );
        ServiceLoader serviceLoader = new ServiceLoader(bc);
        Game game = new Game(serviceLoader);

        LwjglApplicationConfiguration cfg = new LwjglApplicationConfiguration();
        cfg.title = "Tanks";
        cfg.width = 800;
        cfg.height = 600;
        cfg.useGL30 = false;
        cfg.resizable = false;
        app = new LwjglApplication(game, cfg);

    }

    /**
     * Called whenever the OSGi framework stops our bundle
     */
    public void stop( BundleContext bc )
        throws Exception
    {
    //    bc.removeServiceListener(this);
        System.out.println( "STOPPING dk.grp1.tanks.core" );
        app.stop();
        app.exit();


        // no need to unregister our service - the OSGi framework handles it for us
    }

/*    @Override
    public void serviceChanged(ServiceEvent serviceEvent) {
        String[] objectClass = (String[]) serviceEvent.getServiceReference().getProperty("objectClass");
        System.out.println(Arrays.toString(objectClass));

        if(serviceEvent.getType() == ServiceEvent.REGISTERED){
            //objectClass[0]
        }
    }*/
}

