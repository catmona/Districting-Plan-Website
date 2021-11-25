package com.mavericks.server.api;

import com.mavericks.server.Algorithm;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableAsync;

import java.util.*;

@EnableAsync
@Configuration
public class Config {

    @Bean
    public Map<String,Algorithm> getMap(){

        return new HashMap<>();
    }



}
