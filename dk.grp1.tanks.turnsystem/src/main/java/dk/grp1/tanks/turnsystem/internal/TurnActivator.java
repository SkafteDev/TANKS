package dk.grp1.tanks.turnsystem.internal;

import dk.grp1.tanks.common.services.IGamePluginService;
import dk.grp1.tanks.common.services.IPostEntityProcessingService;
import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;

public class TurnActivator implements BundleActivator {
    @Override
    public void start(BundleContext bundleContext) throws Exception {
        System.out.println( "STARTING dk.grp1.tanks.player" );
        TurnManager turnManager = new TurnManager();
        bundleContext.registerService(IPostEntityProcessingService.class.getName(), turnManager,null);
        bundleContext.registerService(IGamePluginService.class.getName(), new TurnGamePlugin(turnManager),null);


    }

    @Override
    public void stop(BundleContext bundleContext) throws Exception {

    }
}
