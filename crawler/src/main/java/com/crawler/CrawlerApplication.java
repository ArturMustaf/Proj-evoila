package com.crawler;

import com.crawler.service.SchedulingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.EnableScheduling;

@EnableScheduling
@SpringBootApplication
public class CrawlerApplication {
    @Autowired
    SchedulingService schedulingService;

    public static void main(String[] args) {
        SpringApplication.run(CrawlerApplication.class, args);
    }


    /**
     * Fires the scheduled pull and send methods on init
     */
    @EventListener(ApplicationReadyEvent.class)
    public void doAfterInit() {
        schedulingService.pullAndSendAllData();
    }
}