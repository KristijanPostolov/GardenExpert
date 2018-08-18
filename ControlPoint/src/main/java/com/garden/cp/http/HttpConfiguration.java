package com.garden.cp.http;

import com.google.gson.Gson;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class HttpConfiguration {

    @Bean
    public Gson gson() {
        return new Gson();
    }

}
