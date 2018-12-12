package com.emse.spring.faircorp.Services;


import org.eclipse.paho.client.mqttv3.*;
import org.eclipse.paho.client.mqttv3.persist.MemoryPersistence;

import java.net.URI;
import java.net.URISyntaxException;




/**
 * A sample application that demonstrates how to use the Paho MQTT v3.1 Client blocking API.
 */

//Asynchronous
public class Subscriber implements MqttCallback {

    // initialisation
    private final int qos = 1;
    private String topic = "actuator";
    private MqttClient client;
    private String clientId = "SpringServer";



    public Subscriber (String host, String username, String password) throws MqttException {

        MqttConnectOptions conOpt = new MqttConnectOptions();
        conOpt.setCleanSession(true);
        conOpt.setUserName(username);
        conOpt.setPassword(password.toCharArray());

        // crée le client qui est notre serveur
        this.client = new MqttClient(host, clientId, new MemoryPersistence());
        this.client.setCallback(this);
        this.client.connect(conOpt);

        System.out.println("Connecting to host: "+host);
        System.out.println("Connected");

        //subscribe au topic
        this.client.subscribe(this.topic, qos);

    }

/*
    public Subscriber(String uri) throws MqttException, URISyntaxException {
        this(new URI(uri));
    }

    // l'URL est juste un type  d'URI

    public Subscriber(URI uri) throws MqttException {
        String host = String.format("https://%s:%d", uri.getHost(), uri.getPort());
        String[] auth = this.getAuth(uri);
        String username = auth[0];
        String password = auth[1];
        String clientId = "SpringServer";
        if (!uri.getPath().isEmpty()) {
            this.topic = uri.getPath().substring(1);
        }

        MqttConnectOptions conOpt = new MqttConnectOptions();
        conOpt.setCleanSession(true);
        conOpt.setUserName(username);
        conOpt.setPassword(password.toCharArray());

        // crée le client qui est notre serveur
        this.client = new MqttClient(host, clientId, new MemoryPersistence());
        this.client.setCallback(this);
        this.client.connect(conOpt);

        //subscribe au topic
        this.client.subscribe(this.topic, qos);
    }

    private String[] getAuth(URI uri) {
        String a = uri.getAuthority();
        String[] first = a.split("@");
        return first[0].split(":");
    }
*/


    // publish a String to the topic
    public void sendMessage(String payload) throws MqttException {
        MqttMessage message = new MqttMessage(payload.getBytes());
        message.setQos(qos);
        this.client.publish(this.topic, message); // Blocking publish
        System.out.println("Publishing message: "+ message);
        System.out.println("Message published");
    }

    /**
     * @see MqttCallback#connectionLost(Throwable)
     */
    public void connectionLost(Throwable cause) {
        System.out.println("Connection lost because: " + cause);
        System.exit(1);
    }

    /**
     * @see MqttCallback#deliveryComplete(IMqttDeliveryToken)
     */
    public void deliveryComplete(IMqttDeliveryToken token) {
    }

    /**
     * @see MqttCallback#messageArrived(String, MqttMessage)
     */
    public void messageArrived(String topic, MqttMessage message) throws MqttException {
        System.out.println(String.format("[%s] %s", topic, new String(message.getPayload())));
    }



}
