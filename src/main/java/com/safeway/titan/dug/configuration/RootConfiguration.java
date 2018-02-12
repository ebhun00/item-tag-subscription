package com.safeway.titan.dug.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.safeway.titan.dug.repository.TransformerByLetterRepository;
import com.safeway.titan.dug.repository.TransformerRepository;
import com.safeway.titan.dug.service.TransformerService;
import com.safeway.titan.dug.service.TransformerServiceImpl;

@Configuration
public class RootConfiguration {

    @Bean
    public TransformerService hotelService(TransformerRepository hotelRepository,
                                     TransformerByLetterRepository hotelByLetterRepository) {
        return new TransformerServiceImpl(hotelRepository, hotelByLetterRepository);
    }

}
