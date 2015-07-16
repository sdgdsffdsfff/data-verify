package zx.soft.data.verify.api;


import org.restlet.Component;
import org.restlet.data.Protocol;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class BotServer {

    private static Logger LOG = LoggerFactory.getLogger(BotServer.class);

    private Component component;
    private BotApp app;
    private int port;
    private static boolean running;

    public BotServer(int port) {
        this.port = port;
        // Create a new Component.
        component = new Component();

        component.getServers().add(Protocol.HTTP, port);

        // Attach the application.
        app = new BotApp();

        component.getDefaultHost().attach("/bot", app);

        component.getContext().getParameters().set("maxThreads", "1000");

        BotApp.server = this;
    }

    public static boolean isRunning() {
        return running;
    }

    public void start() throws Exception {
        LOG.info("Starting DataVerificationBot on port " + port + "...");
        component.start();
        LOG.info("Started DataVerificationBot on port " + port);
        running = true;
        BotApp.started = System.currentTimeMillis();
    }

    public boolean canStop() throws Exception {
        return false;
    }

    public boolean stop(boolean force) throws Exception {
        if (!running) {
            return true;
        }
        if (!canStop() && !force) {
            LOG.warn("Running jobs - can't stop now.");
            return false;
        }
        LOG.info("Stopping DataVerificationBot on port " + port + "...");
        component.stop();
        LOG.info("Stopped DataVerificationBot on port " + port);
        running = false;
        return true;
    }

    public static void main(String[] args) throws Exception {
        if (args == null || args.length == 0) {
            System.out.println("usage: BotServer port");
            System.exit(-1);
        }
            
        int port = Integer.valueOf(args[0]);
        BotServer server = new BotServer(port);
        
        server.start();
    }
}
