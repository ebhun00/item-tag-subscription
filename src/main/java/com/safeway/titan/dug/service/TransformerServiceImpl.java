package com.safeway.titan.dug.service;

import java.util.List;
import java.util.UUID;

import com.safeway.titan.dug.domain.Transformer;
import com.safeway.titan.dug.domain.TransformerByLetter;
import com.safeway.titan.dug.repository.TransformerByLetterRepository;
import com.safeway.titan.dug.repository.TransformerRepository;

public class TransformerServiceImpl implements TransformerService {

    private final TransformerRepository hotelRepository;

    private final TransformerByLetterRepository hotelByLetterRepository;


    public TransformerServiceImpl(TransformerRepository hotelRepository,
                            TransformerByLetterRepository hotelByLetterRepository) {
        this.hotelRepository = hotelRepository;
        this.hotelByLetterRepository = hotelByLetterRepository;
    }

    @Override
    public Transformer save(Transformer hotel) {
        if (hotel.getId() == null) {
            hotel.setId(UUID.randomUUID());
        }
        this.hotelRepository.save(hotel);
        this.hotelByLetterRepository.save(new TransformerByLetter(hotel));
        return hotel;
    }

    @Override
    public Transformer update(Transformer hotel) {
        Transformer existingHotel = this.hotelRepository.findOne(hotel.getId());
        if (existingHotel != null) {
            this.hotelByLetterRepository.delete(new TransformerByLetter(existingHotel).getHotelByLetterKey());
            this.hotelRepository.update(hotel);
            this.hotelByLetterRepository.save(new TransformerByLetter(hotel));
        }
        return hotel;
    }

    @Override
    public Transformer findOne(UUID uuid) {
        return this.hotelRepository.findOne(uuid);
    }

    @Override
    public void delete(UUID uuid) {
        Transformer hotel = this.hotelRepository.findOne(uuid);
        if (hotel != null) {
            this.hotelRepository.delete(uuid);
            this.hotelByLetterRepository.delete(new TransformerByLetter(hotel).getHotelByLetterKey());
        }
    }

    @Override
    public List<TransformerByLetter> findHotelsStartingWith(String letter) {
        return this.hotelByLetterRepository.findByFirstLetter(letter);
    }

    @Override
    public List<Transformer> findHotelsInState(String state) {
        return this.hotelRepository.findByState(state);
    }
}
