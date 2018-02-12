package com.safeway.titan.repository;

import java.util.List;
import java.util.UUID;

import com.safeway.titan.domain.Hotel;

public interface HotelRepository  {
    Hotel save(Hotel hotel);
    Hotel update(Hotel hotel);
    Hotel findOne(UUID hotelId);
    void delete(UUID hotelId);
    List<Hotel> findByState(String state);
}
