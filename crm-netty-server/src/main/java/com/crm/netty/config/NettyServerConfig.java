package com.crm.netty.config;

        import org.I0Itec.zkclient.ZkClient;
        import org.springframework.beans.factory.annotation.Value;
        import org.springframework.context.annotation.Bean;
        import org.springframework.context.annotation.Configuration;

/**
 * @author huhong
 * @date 2019-06-25 13:44
 */
@Configuration
public class NettyServerConfig {

    @Value("${crm.zk.addr:}")
    private String zkAddr = "127.0.0.1:2181";

    @Value("${crm.zk.connect.timeout:}")
    private Integer zkConnectTimeout = 15000;

    @Bean
    public ZkClient zkClient() {
        return new ZkClient(zkAddr, zkConnectTimeout);
    }
}
