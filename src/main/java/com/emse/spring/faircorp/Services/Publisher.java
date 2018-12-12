///*
//package com.emse.spring.faircorp.Services;
//
//import org.eclipse.paho.client.mqttv3.*;
//import org.eclipse.paho.client.mqttv3.persist.MemoryPersistence;
//
//public class Publisher implements MqttCallback {
//
//    private String topic = "MQTT Examples";
//    private String content = "Message from MqttPublishSample";
//    private final int qos = 1;
//    private MqttClient client;
//
//    private String clientId = "JavaSample";
//
//
//
//    public Publisher (String host, String username, String password) throws MqttException {
//
//        MqttConnectOptions connOpts = new MqttConnectOptions();
//        connOpts.setCleanSession(true);
//        connOpts.setUserName(username);
//        connOpts.setPassword(password.toCharArray());
//
//        this.client = new MqttClient(host, clientId, new MemoryPersistence());
//        this.client.setCallback(this);
//        this.client.connect(connOpts);
//
//
//        System.out.println("Message published");
//        sampleClient.disconnect();
//        System.out.println("Disconnected");
//        System.exit(0);
//    }
//
//
//    // publish a String to the topic
//    public void sendMessage(String payload) throws MqttException {
//        MqttMessage message = new MqttMessage(payload.getBytes());
//        message.setQos(qos);
//        this.client.publish(this.topic, message); // Blocking publish
//
//        System.out.println("Message published");
//    }
//
//    */
///**
//     * @see MqttCallback#connectionLost(Throwable)
//     *//*
//
//    public void connectionLost(Throwable cause) {
//        System.out.println("Connection lost because: " + cause);
//        System.exit(1);
//    }
//
//    */
///**
//     * @see MqttCallback#deliveryComplete(IMqttDeliveryToken)
//     *//*
//
//    public void deliveryComplete(IMqttDeliveryToken token) {
//    }
//
//    */
///**
//     * @see MqttCallback#messageArrived(String, MqttMessage)
//     */
//
//    public void messageArrived(String topic, MqttMessage message){
//    }
//
//}
//*/
