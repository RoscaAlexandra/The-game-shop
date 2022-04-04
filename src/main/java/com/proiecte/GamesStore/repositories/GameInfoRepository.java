package com.proiecte.GamesStore.repositories;

import com.proiecte.GamesStore.domain.GameInfo;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface GameInfoRepository extends CrudRepository<GameInfo, Long> {
    GameInfo findById(int id);
    GameInfo findByName(String name);
    GameInfo findGameInfoByName(String title);


    List<GameInfo> findAll();
}
