package com.emse.spring.faircorp.Services;



import com.emse.spring.faircorp.model.*;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.eclipse.paho.client.mqttv3.*;
import org.eclipse.paho.client.mqttv3.persist.MemoryPersistence;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.transaction.Transactional;


/**
 * A sample application that demonstrates how to use the Paho MQTT v3.1 Client blocking API.
 */

//@Service
public class Subscriber implements MqttCallback {

    @Autowired
    private LightDAO lightDao;
    @Autowired
    private RoomDAO roomDao;

    private LightController lthctrl;

    // initialisation
    private final int qos = 1;

    private MqttClient client;
    //private String clientId = "SpringServer";
    public String message;
    public String username = "General";
    public String password = "aaa";


    public Subscriber (String host, String topic, String clientId) throws MqttException {

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
     * La BDD est updatée en décalé
     */

    public void messageArrived(String topic, MqttMessage mes) throws MqttException {

        message = String.format("[%s] %s", topic, new String(mes.getPayload()));
        System.out.println(message);

        //Light light = lightDao.findById(9L).orElseThrow(IllegalArgumentException::new);

        String resp = message.substring(9, message.indexOf("/"));
        /** header JSON required, string contient SEULEMENT LES ID, STATUS, COLOR & ROOMID
         * On suppose que toutes les lights sont initialisées de base
         */


        //TODO rooms
        System.out.println(resp);
        if (resp.equals("JSON")) {
            // TRANSFORM STRING IN DTO
            //remove JSON header
            message = message.substring(message.indexOf("{"));
            //Light light;

            System.out.println(message);
            // renvoie toujours le JSON entier de la light
 //           ObjectMapper mapper = new ObjectMapper();
            try{

                HttpRequest httpRequest = new HttpRequest();

                String postRequest = httpRequest.PostRequest("api/lights", message);

                System.out.println(postRequest);





//                LightDto lightdto = mapper.readValue(message, LightDto.class);
//                //dto mapped
//                System.out.println(lightdto.getId());
//
//                //populate corresponding light class
//                Light light = lightDao.findById(9L).orElseThrow(IllegalArgumentException::new);

               // LightDto dto =lthctrl.create(lightdto);



                //Light light = em.find(Light.class, lightdto.getId());
//
//                System.out.println("light found");
//
//                Long roomId = lightdto.getRoomId();
//                Room room = roomDao.findById(roomId).orElse(null);
//
//                light.setColor(lightdto.getColor());
//                light.setSaturation(lightdto.getSaturation());
//                light.setStatus(lightdto.getStatus());
//                light.setRoom(room);
//
//                //DAO links classes and DB, save changes
//                lightDao.save(light);
//                System.out.println("saved");


                //this.client.disconnect();
                //System.out.println("Disconnected");

            }
            catch (Exception e) {
                e.printStackTrace();
            }
        }


    }





    public void Disconnect() throws MqttException {


        this.client.disconnect();
        System.out.println("Disconnected");
    }



//    public LightDto create(LightDto dto) {
//        Light light = null;
//
//
//        light = lightDao.save(new Light(roomDao.getOne(dto.getRoomId()), dto.getColor(), dto.getStatus(), dto.getSaturation()));
//        // methode de base de la DAO
//        // l'ordre d'appel est important et doit respecter celui du constructeur
//
//
//        if (dto.getId() != null) light = lightDao.findById(dto.getId()).orElse(null);
//
//        return new LightDto(light);
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
