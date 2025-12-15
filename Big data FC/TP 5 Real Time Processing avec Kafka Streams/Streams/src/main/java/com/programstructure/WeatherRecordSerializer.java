package com.programstructure;

import org.apache.kafka.common.serialization.Serializer;

import java.nio.charset.StandardCharsets;
import java.util.Map;

public class WeatherRecordSerializer implements Serializer<WeatherRecord> {
    @Override
    public byte[] serialize(String topic, WeatherRecord data) {
        return data.toString().getBytes(StandardCharsets.UTF_8);
    }
}