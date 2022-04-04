package com.proiecte.GamesStore.controller;

import com.proiecte.GamesStore.controllers.DeveloperController;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.validation.BindingResult;

import javax.transaction.Transactional;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

@Transactional
@RunWith(SpringRunner.class)
@SpringBootTest
public class DeveloperControllerTests {


    @Autowired
    DeveloperController developerController;

    @MockBean
    private BindingResult bindingResult;

    @Before
    public void setUp() throws Exception {
    }


    @Test
    public void listGamesPageByPage() throws Exception {
        MockMvc mockMvc = MockMvcBuilders.standaloneSetup(developerController).build();
        mockMvc.perform(get("/developer/developerList/page/1").param("page","1"))
                .andExpect(status().isOk())
                .andExpect(view().name("developerList"));

    }

    @Test
    @WithMockUser(username="ADMIN",roles="ADMIN")
    public void addDeveloper()  throws Exception {
        MockMvc mockMvc = MockMvcBuilders.standaloneSetup(developerController).build();
        mockMvc.perform(get("/developer/developerList/add"))
                .andExpect(status().isOk())
                .andExpect(view().name("addDeveloper"));

    }

    @Test
    @WithMockUser(username="ADMIN",roles="ADMIN")
    public void saveAuthor()  throws Exception {
        MockMvc mockMvc = MockMvcBuilders.standaloneSetup(developerController).build();
        //  BindingResult result = Mockito.mock(BindingResult.class);
        // Mockito.when(bindingResult.hasErrors()).thenReturn(false);
        mockMvc.perform(post("/developer/save/test"))
                .andExpect(view().name("redirect:/developer/developerList/page/1"));

    }

    @Test
    @WithMockUser(username="ADMIN",roles="ADMIN")
    public void editById() throws Exception {
        MockMvc mockMvc = MockMvcBuilders.standaloneSetup(developerController).build();
        mockMvc.perform(get("/edit/10").param("id","10"))
                .andExpect(status().isNotFound());

    }


    @Test
    @WithMockUser(username="ADMIN",roles="ADMIN")
    public void editByValidId() throws Exception {
        MockMvc mockMvc = MockMvcBuilders.standaloneSetup(developerController).build();
        mockMvc.perform(get("/developer/edit/1").param("id","1"))
                .andExpect(status().isOk())
                .andExpect(view().name("editDeveloper"));
    }


    @Test
    @WithMockUser(username="ADMIN",roles="ADMIN")
    public void deleteById() throws Exception {
        MockMvc mockMvc = MockMvcBuilders.standaloneSetup(developerController).build();
        mockMvc.perform(get("/developer/delete/3").param("id","1"))
                .andExpect(view().name("redirect:/developer/developerList/page/1"));
    }

    @Test
    @WithMockUser(username="ADMIN",roles="ADMIN")
    public void updateById() throws Exception {
        MockMvc mockMvc = MockMvcBuilders.standaloneSetup(developerController).build();
        mockMvc.perform(get("/developer/update/1/test").param("id","1"))
                .andExpect(view().name("redirect:/developer/developerList" +
                        "" +
                        "/page/1"));
    }
}
