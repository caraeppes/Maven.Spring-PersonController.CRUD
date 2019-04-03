package io.zipcoder.crudapp;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.BDDMockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@SpringBootTest
@AutoConfigureMockMvc
@RunWith(SpringRunner.class)
public class PersonControllerTest {

    @Autowired
    private MockMvc mvc;

    @MockBean
    private PersonRepository repository;

    @Test
    public void testGetPerson() throws Exception {
        int givenId = 0;
        BDDMockito
                .given(repository.findOne(givenId))
                .willReturn(new Person("Cara", "Eppes", givenId));

        String expectedContent = "{\"firstName\":\"Cara\",\"lastName\":\"Eppes\",\"id\":0}";
        this.mvc.perform(MockMvcRequestBuilders
                .get("/people/" + givenId))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().string(expectedContent));
    }

    @Test
    public void testCreatePerson() throws Exception {
        Person person = new Person("Cara", "Eppes", 0);

        BDDMockito
                .given(repository.save(person))
                .willReturn(person);

        String expectedContent = "{\"firstName\":\"Cara\",\"lastName\":\"Eppes\",\"id\":0}";
        this.mvc.perform(MockMvcRequestBuilders
                .post("/people/")
                .content(expectedContent)
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
        )
                .andExpect(MockMvcResultMatchers.status().isCreated())
                .andExpect(MockMvcResultMatchers.content().string(expectedContent));
    }

    @Test
    public void testGetPersonList() throws Exception {
        List<Person> expectedList = new ArrayList<>();
        expectedList.add(new Person("Cara", "Eppes", 0));
        expectedList.add(new Person("Bradley", "Cooper", 1));
        BDDMockito
                .given(repository.findAll())
                .willReturn(expectedList);

        String expectedContent = "[{\"firstName\":\"Cara\",\"lastName\":\"Eppes\",\"id\":0}" +
                ",{\"firstName\":\"Bradley\",\"lastName\":\"Cooper\",\"id\":1}]";
        this.mvc.perform(MockMvcRequestBuilders
                .get("/people/"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().string(expectedContent));
    }

}
