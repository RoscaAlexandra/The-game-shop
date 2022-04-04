package com.proiecte.GamesStore.repositories;


import com.proiecte.GamesStore.domain.Order;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderRepository extends CrudRepository<Order, Integer> {

}

