package dk.grp1.tanks.core.internal;


import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;

import dk.grp1.tanks.common.data.Entity;
import dk.grp1.tanks.common.data.GameData;
import dk.grp1.tanks.common.data.parts.InventoryPart;
import dk.grp1.tanks.common.services.IGamePluginService;
import dk.grp1.tanks.common.services.IWeapon;
import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;
import org.osgi.framework.ServiceEvent;
import org.osgi.framework.ServiceListener;

import java.util.Arrays;


/**
 * Extension of the default OSGi bundle activator
 */
public final class ExampleActivator
        implements BundleActivator, ServiceListener {
    private final int WIDTH = 800;
    private final int HEIGHT = 600;

    private BundleContext bc;
    private Game game;
    LwjglApplication app;

    /**
     * Called whenever the OSGi framework starts our bundle
     */
    public void start(BundleContext bc)
            throws Exception {
        this.bc = bc;

        System.out.println("STARTING dk.grp1.tanks.core");
        ServiceLoader serviceLoader = new ServiceLoader(bc);
        GameData gameData = new GameData();
        gameData.setDisplayWidth(WIDTH);
        gameData.setDisplayHeight(HEIGHT);
        game = new Game(serviceLoader, gameData);


        LwjglApplicationConfiguration cfg = new LwjglApplicationConfiguration();
        cfg.title = "Tanks";
        cfg.width = WIDTH;
        cfg.height = HEIGHT;
        cfg.useGL30 = false;
        cfg.resizable = true;
        app = new LwjglApplication(game, cfg);
        bc.addServiceListener(this);
    }

    /**
     * Called whenever the OSGi framework stops our bundle
     */
    public void stop(BundleContext bc)
            throws Exception {
        bc.removeServiceListener(this);
        System.out.println("STOPPING dk.grp1.tanks.core");
        app.stop();
        app.exit();


        // no need to unregister our service - the OSGi framework handles it for us
    }

    /**
     * Detects changes in registered services
     *
     * @param serviceEvent
     */
    @Override
    public void serviceChanged(ServiceEvent serviceEvent) {
        String[] objectClass = (String[]) serviceEvent.getServiceReference().getProperty("objectClass");

        if (serviceEvent.getType() == ServiceEvent.REGISTERED) {
            if (objectClass[0].equalsIgnoreCase(IGamePluginService.class.getCanonicalName())) {
                IGamePluginService plugin = (IGamePluginService) bc.getService(serviceEvent.getServiceReference());
                plugin.start(game.getWorld(), game.getGameData());

            } else if (objectClass[0].equalsIgnoreCase(IWeapon.class.getCanonicalName())) {
                IWeapon weapon = (IWeapon) bc.getService(serviceEvent.getServiceReference());
                for (Entity e : game.getWorld().getEntities()) {
                    InventoryPart ip = e.getPart(InventoryPart.class);
                    if (ip != null) {
                        ip.addWeapon(weapon);
                    }
                }
            }
        }


        if (serviceEvent.getType() == ServiceEvent.UNREGISTERING)

        {
            if (objectClass[0].equalsIgnoreCase(IGamePluginService.class.getCanonicalName())) {
                IGamePluginService plugin = (IGamePluginService) bc.getService(serviceEvent.getServiceReference());
                plugin.stop(game.getWorld(), game.getGameData());

            }else if (objectClass[0].equalsIgnoreCase(IWeapon.class.getCanonicalName())) {
                IWeapon weapon = (IWeapon) bc.getService(serviceEvent.getServiceReference());
                for (Entity e : game.getWorld().getEntities()) {
                    InventoryPart ip = e.getPart(InventoryPart.class);
                    if (ip != null) {
                        ip.removeWeapon(weapon);
                    }
                }
            }
        }
    }
}

