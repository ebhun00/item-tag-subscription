package com.safeway.titan.dug.repository;

import java.util.List;

import com.safeway.titan.dug.domain.TransformerByLetter;
import com.safeway.titan.dug.domain.TransformerByLetterKey;

public interface TransformerByLetterRepository {
    List<TransformerByLetter> findByFirstLetter(String letter);
    TransformerByLetter save(TransformerByLetter hotelByLetter);
    void delete(TransformerByLetterKey hotelByLetterKey);
}
