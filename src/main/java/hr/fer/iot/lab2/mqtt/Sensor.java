package hr.fer.iot.lab2.mqtt;

public class Sensor {

    private static final String TEMP_PACKAGE = "<=>€##408521538#node_01#0#TCA:26.13#";
    private static final String HUM_PACKAGE = "<=>€##408521538#node_01#0#HUMA:48#";

    private double temperature;
    private double humidity;

    public Sensor() {
        this.temperature = Double.parseDouble(TEMP_PACKAGE.split("#")[5].split(":")[1]);
        this.humidity = Double.parseDouble(HUM_PACKAGE.split("#")[5].split(":")[1]);
    }

    public double getTemperature() {
        return temperature;
    }

    public double getHumidity() {
        return humidity;
    }
}
