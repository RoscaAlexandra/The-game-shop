package com.proiecte.GamesStore.controller;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import javax.transaction.Transactional;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

@Transactional
@RunWith(SpringRunner.class)
@SpringBootTest
public class GameControllerTests {

    @Autowired
    GameController gameController;

    @Before
    public void setUp() throws Exception {
    }


    @Test
    public void listGamesPageByPage() throws Exception {
        MockMvc mockMvc = MockMvcBuilders.standaloneSetup(gameController).build();
        mockMvc.perform(get("/game/gameList/page/1").param("page","1"))
                .andExpect(status().isOk())
                .andExpect(view().name("gameList"));

    }

    @Test
    @WithMockUser(username="admin",roles="ADMIN")
    public void addGame()  throws Exception {
        MockMvc mockMvc = MockMvcBuilders.standaloneSetup(gameController).build();
        mockMvc.perform(get("/game/add"))
                .andExpect(status().isOk())
                .andExpect(view().name("addGame"));

    }

    @Test
    @WithMockUser(username="admin",roles="ADMIN")
    public void saveGame()  throws Exception {
        MockMvc mockMvc = MockMvcBuilders.standaloneSetup(gameController).build();
        mockMvc.perform(get("/game/save"))
                .andExpect(status().isOk())
                .andExpect(view().name("redirect:/game/gameList/page/1"));

    }

    @Test
    @WithMockUser(username="admin",roles="ADMIN")
    public void editById() throws Exception {
        MockMvc mockMvc = MockMvcBuilders.standaloneSetup(gameController).build();
        mockMvc.perform(get("/edit/12").param("id","12"))
                .andExpect(status().isNotFound());
    }


    @Test
    @WithMockUser(username="admin",roles="ADMIN")
    public void editByValidId() throws Exception {
        MockMvc mockMvc = MockMvcBuilders.standaloneSetup(gameController).build();
        mockMvc.perform(get("/game/edit/2").param("id","2"))
                .andExpect(status().isOk())
                .andExpect(view().name("editGame"));
    }
    @Test
    @WithMockUser(username="admin",roles="ADMIN")
    public void updateById() throws Exception {
        MockMvc mockMvc = MockMvcBuilders.standaloneSetup(gameController).build();
        mockMvc.perform(get("/game/update/1").param("id","1"))
                .andExpect(view().name("redirect:/game/gameList/page/1"));
    }

    @Test
    @WithMockUser(username="ADMIN",roles="ADMIN")
    public void deleteById() throws Exception {
        MockMvc mockMvc = MockMvcBuilders.standaloneSetup(gameController).build();
        mockMvc.perform(get("/=game/delete/1").param("id","1"))
                .andExpect(view().name("redirect:/game/gameList/page/1"));
    }

}
