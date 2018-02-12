package com.safeway.titan.dug.repository;

import com.datastax.driver.core.querybuilder.QueryBuilder;
import com.datastax.driver.core.querybuilder.Select;
import com.safeway.titan.dug.domain.Transformer;

import org.springframework.data.cassandra.core.CassandraOperations;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public class TransformerRepositoryImpl implements TransformerRepository {
    
    private final CassandraOperations cassandraTemplate;
    
    public TransformerRepositoryImpl(CassandraOperations cassandraTemplate) {
        this.cassandraTemplate = cassandraTemplate;
    }
    
    @Override
    public Transformer save(Transformer hotel) {
        return this.cassandraTemplate.insert(hotel);
    }

    @Override
    public Transformer update(Transformer hotel) {
        return this.cassandraTemplate.update(hotel);
    }

    @Override
    public Transformer findOne(UUID hotelId) {
        return this.cassandraTemplate.selectOneById(Transformer.class, hotelId);
    }

    @Override
    public void delete(UUID hotelId) {
        this.cassandraTemplate.deleteById(Transformer.class, hotelId);
    }

    @Override
    public List<Transformer> findByState(String state) {
        Select select = QueryBuilder.select().from("hotels_by_state");
        select.where(QueryBuilder.eq("state", state));
        return this.cassandraTemplate.select(select, Transformer.class);
    }
}
