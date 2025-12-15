package com.programstructure;

import org.apache.kafka.common.serialization.Serdes;
import org.apache.kafka.streams.*;
import org.apache.kafka.streams.kstream.*;

import java.util.Properties;

public class WeatherStreamApp {
    public static void main(String[] args) {
        Properties props = new Properties();
        props.put(StreamsConfig.APPLICATION_ID_CONFIG, "weather-stream-app");
        props.put(StreamsConfig.BOOTSTRAP_SERVERS_CONFIG, "localhost:9092");
        props.put(StreamsConfig.DEFAULT_KEY_SERDE_CLASS_CONFIG, Serdes.String().getClass());
        props.put(StreamsConfig.DEFAULT_VALUE_SERDE_CLASS_CONFIG, Serdes.String().getClass());

        StreamsBuilder builder = new StreamsBuilder();

        KStream<String, String> weatherStream = builder.stream("weather-data");

        KStream<String, WeatherRecord> filtered = weatherStream
                .mapValues(WeatherRecord::fromString)
                .filter((key, value) -> value.temperature > 30)
                .mapValues(value -> {
                    double fahrenheit = (value.temperature * 9 / 5) + 32;
                    return new WeatherRecord(value.station, fahrenheit, value.humidity);
                });

        KGroupedStream<String, WeatherRecord> groupedByStation = filtered
                .groupBy((key, value) -> value.station, Grouped.with(Serdes.String(), Serdes.serdeFrom(
                        new WeatherRecordSerializer(), new WeatherRecordDeserializer()
                )));

        KTable<String, WeatherRecord> averages = groupedByStation.aggregate(
                () -> new WeatherRecord("", 0, 0),
                (key, value, aggregate) -> {
                    double avgTemp = (aggregate.temperature + value.temperature) / 2;
                    double avgHum = (aggregate.humidity + value.humidity) / 2;
                    return new WeatherRecord(key, avgTemp, avgHum);
                },
                Materialized.with(Serdes.String(), Serdes.serdeFrom(
                        new WeatherRecordSerializer(), new WeatherRecordDeserializer()
                ))
        );

        averages.toStream()
                .peek((key, value) -> System.out.println(">> Produced: " + key + " => " + value))
                .mapValues(WeatherRecord::toString)
                .to("station-averages", Produced.with(Serdes.String(), Serdes.String()));

        KafkaStreams streams = new KafkaStreams(builder.build(), props);

        Runtime.getRuntime().addShutdownHook(new Thread(streams::close));

        streams.start();
    }
}