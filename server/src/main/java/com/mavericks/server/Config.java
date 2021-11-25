package com.mavericks.server;

import com.mavericks.server.Algorithm;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import java.util.*;
import java.util.concurrent.Executor;


@Configuration
@EnableAsync
public class Config {

    @Bean
    public Map<String,Algorithm> getMap(){

        return new HashMap<>();
    }

    @Bean
    @Scope("prototype")
    public Algorithm getAlg(){
        return new Algorithm();
    }


}
