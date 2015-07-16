package zx.soft.data.verify.http;

public class Proxy {

	private String ip;
	private int port;
	private String username;
	private String password;

	public Proxy() {
	}

	public Proxy(String ip, int port) {
		this.ip = ip;
		this.port = port;
	}

	public Proxy(String ip, int port, String username, String password) {
		super();
		this.ip = ip;
		this.port = port;
		this.username = username;
		this.password = password;
	}

	public String getIp() {
		return ip;
	}

	public void setIp(String ip) {
		this.ip = ip;
	}

	public int getPort() {
		return port;
	}

	public void setPort(int port) {
		this.port = port;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

}
