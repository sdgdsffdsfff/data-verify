package zx.soft.data.verify.api;

import org.restlet.Application;
import org.restlet.Restlet;
import org.restlet.routing.Router;

import zx.soft.data.verify.api.impl.RAMBotManager;

public class BotApp extends Application {

	public static BotManager botMgr;

	static {
		botMgr = new RAMBotManager();
	}

	@Override
	public synchronized Restlet createInboundRoot() {
		getTunnelService().setEnabled(true);
		getTunnelService().setExtensionsTunnel(true);
		Router router = new Router(getContext());
		router.attach("/verify", BotResource.class);
		return router;
	}

}
