package com.emse.spring.faircorp;


import com.emse.spring.faircorp.Services.Subscriber;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;


@SpringBootApplication
public class FaircorpApplication {

	public static void main(String[] args) {
		SpringApplication.run(FaircorpApplication.class, args);


		Subscriber s = null;
		try {
			s = new Subscriber("tcp://m20.cloudmqtt.com:15247", "actuator", "aaa");

		} catch (MqttException me) {
			System.out.println(me.getMessage());
		}

		if (s != null) {

			try {
				s.sendMessage("Hello");
				s.sendMessage("Hello 2");
			} catch (MqttException e) {
				System.out.println(e.getMessage());
			}
		}

	}

}




