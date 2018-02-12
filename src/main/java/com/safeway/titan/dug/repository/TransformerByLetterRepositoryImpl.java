package com.safeway.titan.dug.repository;

import com.datastax.driver.core.querybuilder.QueryBuilder;
import com.datastax.driver.core.querybuilder.Select;
import com.safeway.titan.dug.domain.TransformerByLetter;
import com.safeway.titan.dug.domain.TransformerByLetterKey;

import org.springframework.cassandra.core.CqlTemplate;
import org.springframework.data.cassandra.core.CassandraTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class TransformerByLetterRepositoryImpl implements TransformerByLetterRepository {
    private final CassandraTemplate cassandraTemplate;

    public TransformerByLetterRepositoryImpl(CassandraTemplate cassandraTemplate) {
        this.cassandraTemplate = cassandraTemplate;
    }

    @Override
    public List<TransformerByLetter> findByFirstLetter(String letter) {
        Select select = QueryBuilder.select().from("hotels_by_letter");
        select.where(QueryBuilder.eq("first_letter", letter));
        return this.cassandraTemplate.select(select, TransformerByLetter.class);
    }

    @Override
    public TransformerByLetter save(TransformerByLetter hotelByLetter) {
        return this.cassandraTemplate.insert(hotelByLetter);
    }

    @Override
    public void delete(TransformerByLetterKey hotelByLetterKey) {
        this.cassandraTemplate.deleteById(TransformerByLetter.class, hotelByLetterKey);
    }

}
