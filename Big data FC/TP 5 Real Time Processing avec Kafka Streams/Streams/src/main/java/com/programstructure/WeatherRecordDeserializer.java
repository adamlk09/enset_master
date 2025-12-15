package com.programstructure;

import org.apache.kafka.common.serialization.Deserializer;

import java.nio.charset.StandardCharsets;

public class WeatherRecordDeserializer implements Deserializer<WeatherRecord> {
    @Override
    public WeatherRecord deserialize(String topic, byte[] data) {
        return WeatherRecord.fromString(new String(data, StandardCharsets.UTF_8));
    }
}