package com.mavericks.server.api;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Hashtable;
import java.util.Map;

@Configuration
public class Config {

    @Bean
    public Map<Long,Object[]> getMap(){
        return new Hashtable<Long,Object[]>();
    }

}
