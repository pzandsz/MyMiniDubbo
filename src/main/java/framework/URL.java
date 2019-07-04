package framework;

import java.io.Serializable;

/**
 * 远程调用存放的地址信息
 * @author 曾鹏
 */
public class URL implements Serializable {
    private String hostname;
    private int port;

    public URL(String hostname, int port) {
        this.hostname = hostname;
        this.port = port;
    }

    public String getHostname() {
        return hostname;
    }

    public void setHostname(String hostname) {
        this.hostname = hostname;
    }

    public int getPort() {
        return port;
    }

    public void setPort(int port) {
        this.port = port;
    }
}
