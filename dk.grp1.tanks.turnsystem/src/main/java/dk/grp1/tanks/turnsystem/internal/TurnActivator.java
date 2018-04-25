package dk.grp1.tanks.turnsystem.internal;

import dk.grp1.tanks.common.services.IGamePluginService;
import dk.grp1.tanks.common.services.IPostEntityProcessingService;
import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;

public class TurnActivator implements BundleActivator {

    public static TurnManager turnManager;


    public static TurnManager getInstance(){
        if (turnManager == null) {
            turnManager = new TurnManager();
        }
        return turnManager;
    }

    @Override
    public void start(BundleContext bundleContext) throws Exception {
        System.out.println( "STARTING dk.grp1.tanks.TurnManager" );

        bundleContext.registerService(IPostEntityProcessingService.class.getName(), getInstance(),null);
        bundleContext.registerService(IGamePluginService.class.getName(), new TurnGamePlugin(),null);


    }

    @Override
    public void stop(BundleContext bundleContext) throws Exception {

    }
}
