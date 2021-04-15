package hr.fer.iot.lab2.mqtt;

import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttConnectOptions;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttMessage;
import org.eclipse.paho.client.mqttv3.persist.MemoryPersistence;

import java.nio.charset.StandardCharsets;

public class TempHumPublisher {
    private static final String BROKER = "tcp://localhost:1883";
    private static final String CLIENT_ID = "TempHumPublisher";
    private static final int QOS = 0;
    private static final String TEMP_TOPIC = "sensor/temperature";
    private static final String HUM_TOPIC = "sensor/humidity";

    public static void main(String[] args) {
        MemoryPersistence persistence = new MemoryPersistence();
        try {
            MqttClient client = new MqttClient(BROKER, CLIENT_ID, persistence);

            MqttConnectOptions options = new MqttConnectOptions();
            options.setCleanSession(true);

            System.out.println("Connecting to " + BROKER);
            client.connect(options);
            System.out.println("Connected");

            Sensor sensor = new Sensor();

            MqttMessage humidityMessage =
                    new MqttMessage(String.valueOf(sensor.getHumidity()).getBytes(StandardCharsets.UTF_8));
            humidityMessage.setQos(QOS);

            MqttMessage temperatureMessage =
                    new MqttMessage(String.valueOf(sensor.getTemperature()).getBytes(StandardCharsets.UTF_8));
            temperatureMessage.setQos(QOS);

            //for (int i = 0; i < 10000; i++) {
            System.out.println("Publish humidity");
            client.publish(HUM_TOPIC, humidityMessage);
            System.out.println("Published humidity");

            System.out.println("Publish temperature");
            client.publish(TEMP_TOPIC, temperatureMessage);
            System.out.println("Published temperature");
            //}

            client.disconnect();
            System.out.println("Disconnected");
            System.exit(0);
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
