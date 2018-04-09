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

        ServiceReference[] refs = bc.getAllServiceReferences(IGamePluginService.class.getName(), null);

        if (refs == null){
            System.out.println("No matches on filter!");
            return;
        }

        for (ServiceReference ref : refs) {

            String category = (String) ref.getBundle().getHeaders().get("Bundle-Category");
            if(category!= null && category.equals("weapon")){
                ref.getBundle().stop();
            }
        }
    }

}
