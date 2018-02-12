package com.safeway.titan.dug.comfiguration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.safeway.titan.dug.repository.HotelByLetterRepository;
import com.safeway.titan.dug.repository.HotelRepository;
import com.safeway.titan.dug.service.HotelService;
import com.safeway.titan.dug.service.HotelServiceImpl;

@Configuration
public class RootConfiguration {

    @Bean
    public HotelService hotelService(HotelRepository hotelRepository,
                                     HotelByLetterRepository hotelByLetterRepository) {
        return new HotelServiceImpl(hotelRepository, hotelByLetterRepository);
    }

}
