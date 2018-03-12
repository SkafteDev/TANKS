package dk.grp1.tanks.core.internal;

import com.badlogic.gdx.ApplicationListener;
import dk.grp1.tanks.common.data.GameData;
import dk.grp1.tanks.common.data.World;
import dk.grp1.tanks.common.services.IEntityProcessingService;
import org.osgi.framework.BundleContext;
import org.osgi.framework.InvalidSyntaxException;
import org.osgi.framework.ServiceReference;

/**
 * Created by danie on 05-03-2018.
 */
public class Game implements ApplicationListener {
    private BundleContext bc;
    private World world;
    private GameData gameData;
    public Game(BundleContext bc) {
        this.bc = bc;
        gameData = new GameData();
        world = new World();
    }


    public void create() {

    }

    public void resize(int i, int i1) {

    }

    public void render() {
        processServices();
    }

    private void processServices(){
        ServiceReference[] serviceReferences = new ServiceReference[1];
        try {
            serviceReferences = bc.getAllServiceReferences(IEntityProcessingService.class.getName(),null);
        } catch (InvalidSyntaxException e) {
            e.printStackTrace();
        }
        if(serviceReferences != null) {
            for (ServiceReference s :
                    serviceReferences) {
                IEntityProcessingService processingService = (IEntityProcessingService) bc.getService(s);
                processingService.process(world, gameData);
            }
        }
    }
    public void pause() {

    }

    public void resume() {

    }

    public void dispose() {

    }
}
