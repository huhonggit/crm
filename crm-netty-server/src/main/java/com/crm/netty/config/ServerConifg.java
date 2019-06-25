package com.crm.netty.config;

import com.corundumstudio.socketio.Configuration;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;

import java.net.InetAddress;
import java.net.UnknownHostException;

/**
 * @author huhong
 * @date 2019-06-25 11:41
 */
@Component
public class ServerConifg {


    public Configuration getConfig(){

        String addr = null;
        try {
            addr = InetAddress.getLocalHost().getHostAddress();
        } catch (UnknownHostException e) {
            e.printStackTrace();
        }
        if (StringUtils.isEmpty(addr)){
            addr = "127.0.0.1";
        }
        Configuration config = new Configuration();
        config.setHostname(addr);
        config.setPort(9092);
        return config;
    }
}
