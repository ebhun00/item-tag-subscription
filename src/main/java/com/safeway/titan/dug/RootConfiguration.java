package com.safeway.titan.dug;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.safeway.titan.repository.HotelByLetterRepository;
import com.safeway.titan.repository.HotelRepository;
import com.safeway.titan.service.HotelService;
import com.safeway.titan.service.HotelServiceImpl;

@Configuration
public class RootConfiguration {

    @Bean
    public HotelService hotelService(HotelRepository hotelRepository,
                                     HotelByLetterRepository hotelByLetterRepository) {
        return new HotelServiceImpl(hotelRepository, hotelByLetterRepository);
    }

}
