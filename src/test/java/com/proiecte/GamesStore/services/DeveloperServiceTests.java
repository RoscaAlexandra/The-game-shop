package com.proiecte.GamesStore.services;

import com.proiecte.GamesStore.domain.Developer;
import com.proiecte.GamesStore.repositories.DeveloperRepository;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import javax.transaction.Transactional;
import java.util.Set;
@SpringBootTest
@Transactional
@Slf4j
public class DeveloperServiceTests {


    @Autowired
    private DeveloperService developerService;

    @Autowired
    private DeveloperRepository developerRepository;

    @Test
    public void getAllDevelopers(){
        Set<Developer> developers = developerService.getAllDevelopers();
        log.info("getAll...");
        developers.forEach(developer -> log.info(developer.getName()));
    }

    @Test
    public void createDeveloper(){
        Developer developer = new Developer();
        developer.setName("ES");
        developer.setId(7);

        Developer newDeveloper = developerService.createDeveloper(developer);
        Set<Developer> developers = developerService.getAllDevelopers();
        log.info("getAll...");
        developers.forEach(developer1 -> log.info(developer1.getName()));

    }

    @Test
    public void editDeveloper() {
        Developer developer = new Developer();
        developer.setId(3);
        developer.setName("Es1");
        developerService.editDeveloper(3,developer);
        Set<Developer> developers = developerService.getAllDevelopers();
        log.info("getAll...");
        developers.forEach(devl -> log.info(devl.getName()));

    }

    @Test
    public void deleteAuthor(){
        developerService.deleteDeveloper(2);
        Set<Developer> authors = developerService.getAllDevelopers();
        log.info("getAll...");
        authors.forEach(devenew -> log.info(devenew.getName()));
    }


}
