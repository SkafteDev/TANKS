package dk.grp1.tanks.weapon.internal;

import dk.grp1.tanks.common.services.IEntityProcessingService;
import dk.grp1.tanks.common.services.IGamePluginService;
import dk.grp1.tanks.common.services.IWeapon;
import org.osgi.framework.*;

import java.util.Dictionary;
import java.util.Properties;

public class WeaponActivator implements BundleActivator {

    @Override
    public void start(BundleContext bc) throws Exception {
        System.out.println("STARTING dk.grp1.tanks.weapon");

        bc.registerService(IEntityProcessingService.class.getName(), new WeaponProcessingSystem(), null);
        bc.registerService(IGamePluginService.class.getName(), new WeaponPlugin(), null);

    }

    @Override
    public void stop(BundleContext bc) throws Exception {
        System.out.println("STOPPING dk.grp1.tanks.weapon");

        // Stops all bundles within the "weapon" category
        for (Bundle bundle : bc.getBundles()) {
            String bundleCat = (String) bundle.getHeaders().get("Bundle-Category");

            if(bundleCat!= null && bundleCat.equals("weapon") && bundle.getState() == Bundle.ACTIVE){
                bundle.stop();
            }
        }
    }

}
