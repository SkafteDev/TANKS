package dk.grp1.tanks.weapon.Flamethrower.internal;

import dk.grp1.tanks.common.services.IGamePluginService;
import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;

public class FlamethrowerActivator implements BundleActivator {

    @Override
    public void start(BundleContext bundleContext) throws Exception {
        System.out.println( "STARTING dk.grp1.tanks.weapon.Flamethrower" );
        bundleContext.registerService(IGamePluginService.class.getName(), new FlamethrowerPlugin(),  null);
    }

    @Override
    public void stop(BundleContext bundleContext) throws Exception {
        System.out.println( "STOPPING dk.grp1.tanks.weapon.Flamethrower" );
    }
}
