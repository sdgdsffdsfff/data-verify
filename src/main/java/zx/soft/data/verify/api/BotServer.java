package zx.soft.data.verify.api;

import org.restlet.Component;
import org.restlet.data.Protocol;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 启动服务类
 *
 * 启动命令：bin/tcl.sh start 8219
 *
 * @author wanggang
 *
 */
public class BotServer {

	private static Logger logger = LoggerFactory.getLogger(BotServer.class);

	private Component component;
	private int port;

	public BotServer(int port) {
		this.port = port;
		component = new Component();
		component.getServers().add(Protocol.HTTP, port);
		component.getDefaultHost().attach("/bot", new BotApp());
		component.getContext().getParameters().set("maxThreads", "1000");
	}

	public static void main(String[] args) throws Exception {
		//		if (args.length == 0) {
		//			System.out.println("Usage: BotServer <port>");
		//			System.exit(-1);
		//		}
		//		int port = Integer.valueOf(args[0]);
		BotServer server = new BotServer(8888);

		server.start();
	}

	public void start() throws Exception {
		logger.info("Starting data-verify on port " + port + "...");
		component.start();
		logger.info("Started data-verify on port " + port);
	}

	public void stop() throws Exception {
		logger.info("Stopping data-verify on port " + port + "...");
		component.stop();
		logger.info("Stoped data-verify on port " + port + "...");
	}

}
