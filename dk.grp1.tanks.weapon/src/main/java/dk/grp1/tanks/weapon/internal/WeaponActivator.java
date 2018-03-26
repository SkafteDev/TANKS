package dk.grp1.tanks.weapon.internal;

import dk.grp1.tanks.common.services.IEntityProcessingService;
import dk.grp1.tanks.common.services.IGamePluginService;
import dk.grp1.tanks.common.services.IWeapon;
import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;
import org.osgi.framework.ServiceEvent;
import org.osgi.framework.ServiceListener;

import java.util.Dictionary;
import java.util.Properties;

public class WeaponActivator implements BundleActivator {

    @Override
    public void start(BundleContext bc) throws Exception {
        System.out.println( "STARTING dk.grp1.tanks.weapon" );

        bc.registerService(IEntityProcessingService.class.getName(), new WeaponProcessingSystem(),null);
        bc.registerService(IGamePluginService.class.getName(), new WeaponPlugin(),null);
    }

    @Override
    public void stop(BundleContext bundleContext) throws Exception {
        System.out.println( "STOPPING dk.grp1.tanks.weapon" );
    }

}
