package com.safeway.titan.repository;

import java.util.List;

import com.safeway.titan.domain.HotelByLetter;
import com.safeway.titan.domain.HotelByLetterKey;

public interface HotelByLetterRepository {
    List<HotelByLetter> findByFirstLetter(String letter);
    HotelByLetter save(HotelByLetter hotelByLetter);
    void delete(HotelByLetterKey hotelByLetterKey);
}
