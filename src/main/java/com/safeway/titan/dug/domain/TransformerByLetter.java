package com.safeway.titan.dug.domain;

import org.springframework.data.cassandra.mapping.PrimaryKey;
import org.springframework.data.cassandra.mapping.Table;

@Table("hotels_by_letter")
public class TransformerByLetter {

    public TransformerByLetter() {}

    public TransformerByLetter(Transformer hotel) {
        TransformerByLetterKey hotelByLetterKey = new TransformerByLetterKey();
        hotelByLetterKey.setFirstLetter(hotel.getName().substring(0, 1));
        hotelByLetterKey.setName(hotel.getName());
        hotelByLetterKey.setHotelId(hotel.getId());
        this.setHotelByLetterKey(hotelByLetterKey);
        this.setState(hotel.getState());
        this.setAddress(hotel.getAddress());
        this.setZip(hotel.getZip());
    }

    @PrimaryKey
    private TransformerByLetterKey hotelByLetterKey;

    private String address;

    private String state;

    private String zip;

    public TransformerByLetterKey getHotelByLetterKey() {
        return hotelByLetterKey;
    }

    public void setHotelByLetterKey(TransformerByLetterKey hotelByLetterKey) {
        this.hotelByLetterKey = hotelByLetterKey;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getZip() {
        return zip;
    }

    public void setZip(String zip) {
        this.zip = zip;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }
}
