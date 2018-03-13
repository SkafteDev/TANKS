package dk.grp1.tanks.core.internal;


import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;

import dk.grp1.tanks.common.services.IGamePluginService;
import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;
import org.osgi.framework.ServiceEvent;
import org.osgi.framework.ServiceListener;

import java.util.Arrays;


/**
 * Extension of the default OSGi bundle activator
 */
public final class ExampleActivator
    implements BundleActivator,ServiceListener
{
    private BundleContext bc;
    private Game game;
    LwjglApplication app;
    /**
     * Called whenever the OSGi framework starts our bundle
     */
    public void start( BundleContext bc )
        throws Exception
    {
        this.bc = bc;
        bc.addServiceListener(this);
        System.out.println( "STARTING dk.grp1.tanks.core" );
        ServiceLoader serviceLoader = new ServiceLoader(bc);
        game = new Game(serviceLoader);

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
        bc.removeServiceListener(this);
        System.out.println( "STOPPING dk.grp1.tanks.core" );
        app.stop();
        app.exit();


        // no need to unregister our service - the OSGi framework handles it for us
    }

    /**
     * Detects changes in registered services
     * @param serviceEvent
     */
    @Override
    public void serviceChanged(ServiceEvent serviceEvent) {
        String[] objectClass = (String[]) serviceEvent.getServiceReference().getProperty("objectClass");

        if(serviceEvent.getType() == ServiceEvent.REGISTERED){
            if (objectClass[0].equalsIgnoreCase(IGamePluginService.class.getCanonicalName())){
                IGamePluginService plugin = (IGamePluginService)bc.getService(serviceEvent.getServiceReference());
                plugin.start(game.getWorld(), game.getGameData());
            }
        }
    }
}

