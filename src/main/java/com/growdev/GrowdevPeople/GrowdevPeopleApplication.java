package com.growdev.GrowdevPeople;

import com.growdev.GrowdevPeople.config.RsaKeyProperties;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

@EnableConfigurationProperties(RsaKeyProperties.class)
@SpringBootApplication
public class GrowdevPeopleApplication {
    public static void main(String[] args) {
        SpringApplication.run(GrowdevPeopleApplication.class, args);
    }
}
