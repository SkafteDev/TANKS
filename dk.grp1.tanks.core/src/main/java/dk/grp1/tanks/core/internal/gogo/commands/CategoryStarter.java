package dk.grp1.tanks.core.internal.gogo.commands;

import dk.grp1.tanks.common.services.IGamePluginService;
import org.osgi.framework.*;

import java.util.Dictionary;

public class CategoryStarter {

    private BundleContext bc;

    public CategoryStarter(BundleContext bc){
        this.bc = bc;
    }

    public void startCategory(String category) throws BundleException {

        for (Bundle bundle : bc.getBundles()) {
            String bundleCat = (String) bundle.getHeaders().get("Bundle-Category");

            if(bundleCat!= null && bundleCat.equals(category) && bundle.getState() == Bundle.RESOLVED){
                bundle.start();
            }
        }

    }

    public void stopCategory(String category) throws BundleException {
        for (Bundle bundle : bc.getBundles()) {
            String bundleCat = (String) bundle.getHeaders().get("Bundle-Category");

            if(bundleCat!= null && bundleCat.equals(category) && bundle.getState() == Bundle.ACTIVE){
                bundle.stop();
            }
        }
    }
}
