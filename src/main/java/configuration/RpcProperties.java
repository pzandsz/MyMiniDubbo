package configuration;

import com.sun.istack.internal.NotNull;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;

/**
 * @Author: zengpeng
 * @Date: 2020/1/719:16
 */
@Data
@Configuration
@ConfigurationProperties(prefix = "zp.mrpc")
@EnableConfigurationProperties(RpcProperties.class)
public class RpcProperties {

    @NotNull
    private Center center;

    @NotNull
    private Provider provider;

    @NotNull
    private Consumer consumer;

    @Data
    public static class Center{
        @NotNull
        private String host;
    }

    @Data
    public static class Provider{
        @NotNull
        private String port;

        @NotNull
        private String proxy;
    }

    @Data
    public static class Consumer{
        @NotNull
        private String url;
    }
}
