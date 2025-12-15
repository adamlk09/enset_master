package com.programstructure.clickprocessor.service;

import org.apache.kafka.common.serialization.Serdes;
import org.apache.kafka.streams.StreamsBuilder;
import org.apache.kafka.streams.kstream.KStream;
import org.apache.kafka.streams.kstream.KTable;
import org.apache.kafka.streams.kstream.Materialized;
import org.apache.kafka.streams.kstream.Produced;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.annotation.EnableKafkaStreams;

@Configuration
@EnableKafkaStreams
// EnableKafkaStreams utiliser pour dit a spring de preparer l'env pour kafka streams et de rendre un bean "StreamsBuilder" dispo pour l'injection
public class ClickStreamProcessor {

    private static final String INPUT_TOPIC = "clicks";
    private static final String OUTPUT_TOPIC = "click-counts";
    private static final String STORE_NAME = "clicks-count-store";

    @Bean
    public KStream<String, String> kStream(StreamsBuilder streamsBuilder) {

        // lire le flux du topic d'entrée
        KStream<String, String> clickStream = streamsBuilder.stream(INPUT_TOPIC);

        // regrouper par une clé statique pour avoir un comptage global
        KTable<String, Long> clickCounts = clickStream
                .groupBy((key, value) -> "total-clicks")
                .count(Materialized.as(STORE_NAME));

        // convertir la table de comptage (KTable) en flux (KStream) et l'envoyer au topic de sortie
        clickCounts.toStream()
                .to(OUTPUT_TOPIC, Produced.with(Serdes.String(), Serdes.Long()));
        return clickStream;
    }
}