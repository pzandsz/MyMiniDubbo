package framework;

import lombok.*;

import java.io.Serializable;

/**
 * 远程调用存放的地址信息
 * @author 曾鹏
 */
@Data
@Setter
@Getter
@NoArgsConstructor
public class URL implements Serializable {
    private String hostname;
    private int port;

    public URL(String hostname, int port) {
        this.hostname = hostname;
        this.port = port;
    }

    @Override
    public String toString() {
        return "URL{" +
                "hostname='" + hostname + '\'' +
                ", port=" + port +
                '}';
    }
}
