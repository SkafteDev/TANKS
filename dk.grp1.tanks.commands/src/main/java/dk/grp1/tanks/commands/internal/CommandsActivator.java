package dk.grp1.tanks.commands.internal;

import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;

import java.util.Hashtable;

public class CommandsActivator implements BundleActivator {

    @Override
    public void start(BundleContext bundleContext) throws Exception {
        Hashtable props = new Hashtable();
        props.put("osgi.command.scope", "tanksCommands");
        props.put("osgi.command.function", new String[]{"startCategory", "stopCategory"});
        bundleContext.registerService(CategoryStarter.class.getName(), new CategoryStarter(bundleContext), props);
    }

    @Override
    public void stop(BundleContext bundleContext) throws Exception {

    }
}
