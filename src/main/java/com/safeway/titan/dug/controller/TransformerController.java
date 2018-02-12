package com.safeway.titan.dug.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.safeway.titan.dug.domain.Transformer;
import com.safeway.titan.dug.domain.TransformerByLetter;
import com.safeway.titan.dug.service.TransformerService;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/hotels")
public class TransformerController {

    private final TransformerService hotelService;

    @Autowired
    public TransformerController(TransformerService hotelService) {
        this.hotelService = hotelService;
    }

    @GetMapping(path = "/{id}")
    public Transformer get(@PathVariable("id") UUID uuid) {
        return this.hotelService.findOne(uuid);
    }

    @PostMapping
    public ResponseEntity<Transformer> save(@RequestBody Transformer hotel) {
        Transformer savedHotel = this.hotelService.save(hotel);
        return new ResponseEntity<>(savedHotel, HttpStatus.CREATED);
    }

    @PutMapping
    public ResponseEntity<Transformer> update(@RequestBody Transformer hotel) {
        Transformer savedHotel = this.hotelService.update(hotel);
        return new ResponseEntity<>(savedHotel, HttpStatus.CREATED);
    }

    @DeleteMapping(path = "/{id}")
    public ResponseEntity<String> delete(@PathVariable("id") UUID uuid) {
        this.hotelService.delete(uuid);
        return new ResponseEntity<>("Deleted", HttpStatus.ACCEPTED);
    }

    @GetMapping(path = "/startingwith/{letter}")
    public List<TransformerByLetter> findHotelsWithLetter(@PathVariable("letter") String letter) {
        return this.hotelService.findHotelsStartingWith(letter);
    }

    @GetMapping(path = "/fromstate/{state}")
    public List<Transformer> findHotelsInState(@PathVariable("state") String state) {
        return this.hotelService.findHotelsInState(state);
    }
}
