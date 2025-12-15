package com.programstructure.clickconsumer.controller;

import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.concurrent.atomic.AtomicLong;

@RestController
public class ClickCountController {

    // j'utilise AtomicLong car il est thread-safe (le listener kafka et les requetes http peuvent s'exécuter dans des threads différents)
    private final AtomicLong totalClicks = new AtomicLong(0);

    @KafkaListener(topics = "click-counts")
    public void listen(Long count) {
        if (count != null) {
            this.totalClicks.set(count);
            System.out.println("Nombre total de clics mis à jour reçu : " + count);
        }
    }

    @GetMapping("/clicks/count")
    public Long getClickCount() {
        return totalClicks.get();
    }
}
