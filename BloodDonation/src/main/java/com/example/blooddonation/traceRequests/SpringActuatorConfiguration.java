package com.example.blooddonation.traceRequests;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.actuate.trace.http.HttpTrace;
import org.springframework.boot.actuate.trace.http.HttpTraceRepository;
import org.springframework.boot.actuate.trace.http.InMemoryHttpTraceRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

import java.io.FileWriter;
import java.io.IOException;

@Configuration
public class SpringActuatorConfiguration {

    @Bean
    public HttpTraceRepository httpTraceRepository() throws IOException {
        return new InMemoryHttpTraceRepository() {
            ObjectMapper objectMapper = new ObjectMapper().registerModule(new JavaTimeModule());
            Logger logger = LoggerFactory.getLogger(InMemoryHttpTraceRepository.class);
            FileWriter fw=new FileWriter("E:\\Facultate\\FSEGA\\LicentaUltima\\licenta2\\BloodDonation\\requestsHistory.log",true);

            @Override
            public void add(HttpTrace trace) {
                try {

                    logger.info(objectMapper.writeValueAsString(trace));
                    fw.append("INFO: "+objectMapper.writeValueAsString(trace)+"\n");
                    fw.flush();


                } catch (JsonProcessingException e) {
                    logger.error(e.getMessage(), e);
                } catch (IOException e) {
                    e.printStackTrace();
                }
                super.add(trace);
            }
        };
    }
}