package com.safeway.titan.dug.service;


import java.util.List;
import java.util.UUID;

import com.safeway.titan.dug.domain.Hotel;
import com.safeway.titan.dug.domain.HotelByLetter;

public interface HotelService {
    Hotel save(Hotel hotel);
    Hotel update(Hotel hotel);
    Hotel findOne(UUID uuid);
    void delete(UUID uuid);

    List<HotelByLetter> findHotelsStartingWith(String letter);
    List<Hotel> findHotelsInState(String state);
}
