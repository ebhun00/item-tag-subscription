package com.safeway.titan.dug.repository;

import java.util.List;
import java.util.UUID;

import com.safeway.titan.dug.domain.Hotel;

public interface HotelRepository  {
    Hotel save(Hotel hotel);
    Hotel update(Hotel hotel);
    Hotel findOne(UUID hotelId);
    void delete(UUID hotelId);
    List<Hotel> findByState(String state);
}
