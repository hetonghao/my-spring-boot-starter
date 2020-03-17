package vc.thinker.config;//package vc.thinker.template.config;
//
//import com.alibaba.fastjson.JSONObject;
//import lombok.extern.slf4j.Slf4j;
//import org.eclipse.paho.client.mqttv3.*;
//import org.eclipse.paho.client.mqttv3.persist.MemoryPersistence;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.beans.factory.annotation.Value;
//import org.springframework.boot.autoconfigure.condition.ConditionalOnExpression;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.context.annotation.Lazy;
//import org.springframework.util.ResourceUtils;
//import vc.thinker.template.protocol.mqtt.DefaultMqttProtocol;
//import vc.thinker.template.protocol.mqtt.IMqttProtocol;
//import vc.thinker.template.protocol.util.SslUtil;
//
///**
// * Mqtt Config
// *
// * @author linanzhi
// * @date 2018年12月12日 下午3:17:33
// */
//@Slf4j
//@Configuration
//@ConditionalOnExpression("${mqtt.run:true}")
//public class MqttConfig implements MqttCallback {
//
//    @Value("${mqtt.run}")
//    private Boolean mqttRun;
//
//    @Value("${mqtt.connection.broker}")
//    private String broker;
//
//    @Value("${mqtt.connection.clientId}")
//    private String clientId;
//
//    @Value("${mqtt.connection.cleanSession}")
//    private boolean cleanSession;
//
//    @Value("${mqtt.connection.username}")
//    private String username;
//
//    @Value("${mqtt.connection.password}")
//    private String password;
//
//    MqttClient client = null;
//
//    @Value("${ssl.cert.path}")
//    private String certFile;
//
//    @Autowired
//    @Lazy(false)
//    private IMqttProtocol mqttProtocol;
//
//    /**
//     * 初始mqtt协议类型
//     *
//     * @return
//     */
//    @Bean
//    @Lazy(false)
//    public IMqttProtocol mqttProtocol() {
//        return new DefaultMqttProtocol();
//    }
//
//    // 重连时间，秒
//    private Integer reconnectionInterval = 3;
//    // 重连时间最大值
//    private final Integer reconnectionIntervalMax = 30;
//    // 重连时间递增
//    private final Integer reconnectionIncreasing = 3;
//
//    @Bean
//    public MqttClient mqttClient() {
//
//        MemoryPersistence persistence = new MemoryPersistence();
//
//        try {
//            client = new MqttClient(broker, clientId + System.currentTimeMillis(), persistence);
//            client.setCallback(this);
//            this.connect();
//            return client;
//        } catch (MqttSecurityException ex) {
//            log.error("", ex);
//        } catch (MqttException ex) {
//            log.error("", ex);
//        } finally {
//
//        }
//        return null;
//    }
//
//    /**
//     * 连接配制
//     *
//     * @return
//     */
//    private MqttConnectOptions getConnOpts() {
//        MqttConnectOptions connOpts = new MqttConnectOptions();
//        connOpts.setCleanSession(false);
//        connOpts.setUserName(username);
//        connOpts.setPassword(password.toCharArray());
//        try {
//            connOpts.setSocketFactory(SslUtil.getSocketFactory(
//                    ResourceUtils.getURL("classpath:").getPath() + certFile));
//        } catch (Exception e) {
//            e.printStackTrace();
//        } // ssl
//        connOpts.setKeepAliveInterval(30);
//        connOpts.setConnectionTimeout(60 * 5);
//        connOpts.setCleanSession(true);
//        return connOpts;
//    }
//
//    /**
//     * 连接
//     *
//     * @throws MqttSecurityException
//     * @throws MqttException
//     */
//    private void connect() throws MqttSecurityException, MqttException {
//        client.connect(getConnOpts());
//        client.subscribe(mqttProtocol.subscribe());
//        log.info("mqtt clinet connect [{}] Success", broker);
//    }
//
//    @Override
//    public void connectionLost(Throwable cause) {
//        log.error("mqtt connectionLost", cause);
//        log.info("mqtt 连接异常，{}稍后重新连接", reconnectionInterval);
//        try {
//            Thread.sleep(reconnectionInterval * 1000);
//        } catch (InterruptedException e1) {
//            log.error("", e1);
//        }
//        if (client != null && !client.isConnected()) {
//            try {
//                log.info("mqtt 重新连接");
//                this.connect();
//            } catch (Exception e) {
//                if (reconnectionInterval < reconnectionIntervalMax) {
//                    reconnectionInterval += reconnectionIncreasing;
//                }
//                connectionLost(e);
//            }
//        }
//    }
//
//    private boolean isClientStatus(String topic) {
//        return topic.endsWith("connected");
//    }
//
//    @Override
//    public void messageArrived(String topic, MqttMessage message) throws Exception {
//        String q = new String(message.getPayload());
//        log.info("原始消[{}]", q);
//        if (isClientStatus(topic)) {
//            log.info("topic:{},payload:{}", topic, q);
//            clientConnected(q);
//        } else {
//            mqttProtocol.handleMessage(topic, message.getPayload());
//        }
//    }
//
//    /**
//     * 客户端连接状态处理
//     *
//     * @param q
//     */
//    public void clientConnected(String q) {
//
//        JSONObject session = JSONObject.parseObject(q);
//        String clientId = session.getString("clientid");
//        if (session.containsKey("ipaddress")) {
//            log.info("{} 连接服务器", clientId);
//        } else {
//            log.info("{} 断开服务器", clientId);
//        }
//    }
//
//    @Override
//    public void deliveryComplete(IMqttDeliveryToken token) {
//        // TODO Auto-generated method stub
//    }
//
//}
