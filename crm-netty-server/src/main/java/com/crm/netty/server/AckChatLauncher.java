package com.crm.netty.server;

import com.corundumstudio.socketio.*;
import com.corundumstudio.socketio.listener.DataListener;
import com.crm.netty.chat.ChatObject;
import com.crm.netty.config.ServerConifg;
import org.I0Itec.zkclient.ZkClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.annotation.Resource;

/**
 * netty 服务器 ack服务器
 * @author huhong
 * @date 2019-06-25 11:19
 */
@Component
public class AckChatLauncher {

    private final static Logger LOGGER = LoggerFactory.getLogger(AckChatLauncher.class);

    @Resource
    private ServerConifg serverConifg;

    @Value("${crm.zk.root}")
    private String zkRoot = "/crmroot";

    @Resource
    private ZkClient zkClient;

    private  SocketIOServer server = null;

    @PostConstruct
    public void start() {
        server = new SocketIOServer(serverConifg.getConfig());
        server.addEventListener("ackevent1", ChatObject.class, new DataListener<ChatObject>() {
            @Override
            public void onData(final SocketIOClient client, ChatObject data, final AckRequest ackRequest) {

                // check is ack requested by client,
                // but it's not required check
                if (ackRequest.isAckRequested()) {
                    // send ack response with data to client
                    ackRequest.sendAckData("client message was delivered to server!", "yeah!");
                }

                // send message back to client with ack callback WITH data
                ChatObject ackChatObjectData = new ChatObject(data.getUserName(), "message with ack data");
                client.sendEvent("ackevent2", new AckCallback<String>(String.class) {
                    @Override
                    public void onSuccess(String result) {
                        System.out.println("ack from client: " + client.getSessionId() + " data: " + result);
                    }
                }, ackChatObjectData);

                ChatObject ackChatObjectData1 = new ChatObject(data.getUserName(), "message with void ack");
                client.sendEvent("ackevent3", new VoidAckCallback() {
                    @Override
                    protected void onSuccess() {
                        System.out.println("void ack from: " + client.getSessionId());
                    }

                }, ackChatObjectData1);
            }
        });
        server.start();
        LOGGER.info("启动 netty server 成功");

        registryZK(serverConifg);
    }

    /**
     * 注册服务到zk
     * @param serverConifg serverConifg
     * @author huhong
     * @date 2019-06-25 11:55
     */
    private void registryZK(ServerConifg serverConifg) {
        createRootNode();
        createNode(serverConifg);
    }

    /**
     * 创建临时节点
     * @param serverConifg serverConifg
     * @author huhong
     * @date 2019-06-25 14:05
     */
    private void createNode(ServerConifg serverConifg) {
        String path = zkRoot + "/ip-" + serverConifg.getConfig().getHostname() + ":" + serverConifg.getConfig().getPort();
        zkClient.createEphemeral(path);
    }

    /**
     * 创建根节点
     * @author huhong
     * @date 2019-06-25 14:06
     */
    private void createRootNode(){
        boolean exists = zkClient.exists(zkRoot);
        if (exists){
            return;
        }
        //创建 root
        zkClient.createPersistent(zkRoot) ;
    }

    @PreDestroy
    public void destroy() {
        server.stop();
        LOGGER.info("关闭 netty server 成功");
    }
}
