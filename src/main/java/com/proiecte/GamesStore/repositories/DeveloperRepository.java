package com.proiecte.GamesStore.repositories;

import com.proiecte.GamesStore.domain.Developer;
import com.proiecte.GamesStore.domain.Game;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DeveloperRepository extends CrudRepository<Developer, Integer> {



    Developer findById(int Id);
    Developer findByName(String name);
    List<Developer> findByGames(Game game);

    @Query("select c from Developer c")
    Page<Developer> findAllPage(Pageable pageable);



}
