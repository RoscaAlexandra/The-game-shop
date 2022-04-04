package com.proiecte.GamesStore.repositories;

import com.proiecte.GamesStore.domain.Comanda;
import com.proiecte.GamesStore.domain.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ComandaRepository extends CrudRepository<Comanda,Integer> {
    List<Comanda> findByUser(User id);
    List<Comanda> findByIdAndUser(int id, User user);
    Page<Comanda> findByUserId(long us,Pageable pageable);

}
