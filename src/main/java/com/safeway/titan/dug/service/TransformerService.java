package com.safeway.titan.dug.service;


import java.util.List;
import java.util.UUID;

import com.safeway.titan.dug.domain.Transformer;
import com.safeway.titan.dug.domain.TransformerByLetter;

public interface TransformerService {
    Transformer save(Transformer hotel);
    Transformer update(Transformer hotel);
    Transformer findOne(UUID uuid);
    void delete(UUID uuid);

    List<TransformerByLetter> findHotelsStartingWith(String letter);
    List<Transformer> findHotelsInState(String state);
}
