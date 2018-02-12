package com.safeway.titan.dug.repository;

import java.util.List;

import com.safeway.titan.dug.domain.HotelByLetter;
import com.safeway.titan.dug.domain.HotelByLetterKey;

public interface HotelByLetterRepository {
    List<HotelByLetter> findByFirstLetter(String letter);
    HotelByLetter save(HotelByLetter hotelByLetter);
    void delete(HotelByLetterKey hotelByLetterKey);
}
