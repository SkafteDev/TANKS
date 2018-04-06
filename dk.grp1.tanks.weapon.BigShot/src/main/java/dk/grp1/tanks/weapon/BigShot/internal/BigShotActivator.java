<<<<<<< HEAD:dk.grp1.tanks.weapon.BigShot/src/main/java/dk/grp1/tanks/weapon/BigShot/internal/BigShotActivator.java
package dk.grp1.tanks.BigShot.internal;

import dk.grp1.tanks.common.services.IGamePluginService;
import dk.grp1.tanks.common.services.IWeapon;
import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;

public class BigShotActivator implements BundleActivator {

    @Override
    public void start(BundleContext bundleContext) throws Exception {
        bundleContext.registerService(IGamePluginService.class.getName(), new BigShotPlugin(), null);
        bundleContext.registerService(IWeapon.class.getName(), new BigShotWeapon(), null);
    }

    @Override
    public void stop(BundleContext bundleContext) throws Exception {
        System.out.println( "STOPPING dk.grp1.tanks.bigshot" );
    }
}
=======
package dk.grp1.tanks.BigShot.internal;

import dk.grp1.tanks.common.services.IGamePluginService;
import dk.grp1.tanks.common.services.IWeapon;
import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;

public class BigShotActivator implements BundleActivator {

    @Override
    public void start(BundleContext bundleContext) throws Exception {
        bundleContext.registerService(IGamePluginService.class.getName(), new BigShotPlugin(), null);
    }

    @Override
    public void stop(BundleContext bundleContext) throws Exception {
        System.out.println( "STOPPING dk.grp1.tanks.bigshot" );
    }
}
>>>>>>> develop:dk.grp1.tanks.BigShot/src/main/java/dk/grp1/tanks/BigShot/internal/BigShotActivator.java
