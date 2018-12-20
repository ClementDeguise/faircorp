package com.emse.spring.faircorp.Services;



import com.emse.spring.faircorp.model.Light;
import com.emse.spring.faircorp.model.LightDto;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.eclipse.paho.client.mqttv3.*;
import org.eclipse.paho.client.mqttv3.persist.MemoryPersistence;




/**
 * A sample application that demonstrates how to use the Paho MQTT v3.1 Client blocking API.
 */


public class Subscriber implements MqttCallback {

    // initialisation
    private final int qos = 1;

    private MqttClient client;
    private String clientId = "SpringServer";
    public String message;
    public LightDto lightdto;
    public String username = "General";
    public String password = "aaa";


    public Subscriber (String host, String topic) throws MqttException {

        MqttConnectOptions conOpt = new MqttConnectOptions();
        conOpt.setCleanSession(true);
        conOpt.setUserName(this.username);
        conOpt.setPassword(this.password.toCharArray());

        // crée le client qui est notre serveur
        this.client = new MqttClient(host, clientId, new MemoryPersistence());
        this.client.setCallback(this);
        this.client.connect(conOpt);

        System.out.println("Connecting to host: "+host);
        System.out.println("Connected");

        //subscribe au topic
        this.client.subscribe(topic, qos);

    }



    // publish a String to the topic

    public void sendMessage(String payload, String topic) throws MqttException {
        MqttMessage message = new MqttMessage(payload.getBytes());
        message.setQos(qos);
        this.client.publish(topic, message); // Blocking publish
        System.out.println("Publishing message: "+ message + " on topic " + topic);
        System.out.println("Message published");
        this.client.disconnect();
        System.out.println("Disconnected");
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

    /**
     * HANDLE JSON RESPONSE
     */
    public void messageArrived(String topic, MqttMessage mes) throws MqttException {

        message = String.format("[%s] %s", topic, new String(mes.getPayload()));
        System.out.println(message);

        String resp = message.substring(0, 4);
        /** header JSON required, string contient SEULEMENT LES ID, STATUS, COLOR & ROOMID
         * On suppose que toutes les lights sont initialisées de base
         */
        if (resp.equals("JSON")) {
            // TRANSFORM STRING IN DTO
            //remove JSON header
            message = message.substring(5);
            Light light;

            ObjectMapper mapper = new ObjectMapper();
            try{
                lightdto = mapper.readValue(message, LightDto.class);
            }
            catch (Exception e) {
                e.printStackTrace();
            }




        }

    }





//    public String JsonResponse() {
//
//        String resp = this.message.substring(0, 4);
//        // header JSON required, string contient SEULEMENT LES ID? STATUS? COLOR & ROOMID
//        if (resp.equals("JSON")) {
//            // first, convert string into new dto
//            return resp;
//
//        }
//    }





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






}
