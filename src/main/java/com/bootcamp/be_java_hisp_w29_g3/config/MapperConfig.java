package com.bootcamp.be_java_hisp_w29_g3.config;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.module.SimpleModule;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateDeserializer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.converter.json.Jackson2ObjectMapperBuilder;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

@Configuration
public class MapperConfig {

    @Bean(name = "MyObjectMapper")
    public ObjectMapper objectMapper() {

        ObjectMapper objectMapper = Jackson2ObjectMapperBuilder.json()
                .featuresToEnable(DeserializationFeature.FAIL_ON_NULL_CREATOR_PROPERTIES)
                .featuresToEnable(DeserializationFeature.ADJUST_DATES_TO_CONTEXT_TIME_ZONE)
                .indentOutput(true)
                .modules(new JavaTimeModule())
                .build();

        DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");

        SimpleModule module = new SimpleModule();
        module.addDeserializer(LocalDate.class, new LocalDateDeserializer(dateFormatter));

        objectMapper.registerModule(module);

        return objectMapper;
    }

}
