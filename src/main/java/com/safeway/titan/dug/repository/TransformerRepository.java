package com.safeway.titan.dug.repository;

import java.util.List;
import java.util.UUID;

import com.safeway.titan.dug.domain.Transformer;

public interface TransformerRepository  {
    Transformer save(Transformer hotel);
    Transformer update(Transformer hotel);
    Transformer findOne(UUID hotelId);
    void delete(UUID hotelId);
    List<Transformer> findByState(String state);
}
