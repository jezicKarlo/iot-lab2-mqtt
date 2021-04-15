package hr.fer.iot.lab2.mqtt;

import org.eclipse.paho.client.mqttv3.*;
import org.eclipse.paho.client.mqttv3.persist.MemoryPersistence;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;

public class TempHumSubscriber {

    private static final String BROKER = "tcp://localhost:1883";
    private static final String CLIENT_ID = "TempHumSubscriber";
    private static final String SUB_TOPIC = "sensor/#";

    public static void main(String[] args) {
        MemoryPersistence persistence = new MemoryPersistence();
        try {
            MqttClient client = new MqttClient(BROKER, CLIENT_ID, persistence);

            MqttConnectOptions options = new MqttConnectOptions();
            options.setCleanSession(true);

            System.out.println("Connecting to " + BROKER);
            client.connect(options);
            System.out.println("Connected");

            client.subscribe(SUB_TOPIC, 2,
                    (s, mqttMessage) ->
                            System.out.println(new String(mqttMessage.getPayload(), StandardCharsets.UTF_8)));

            try(BufferedReader br = new BufferedReader(new InputStreamReader(System.in))) {
                br.readLine();
            } catch (IOException e) { }

            client.disconnect();
            System.out.println("Disconnected");

        } catch (MqttException e) {
            System.out.println("reason " + e.getReasonCode());
            System.out.println("msg " + e.getMessage());
            System.out.println("loc " + e.getLocalizedMessage());
            System.out.println("cause " + e.getCause());
            System.out.println("excep " + e);
            e.printStackTrace();
        }
    }
}
