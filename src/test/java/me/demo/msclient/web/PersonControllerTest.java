package me.demo.msclient.web;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.ObjectMapper;
import me.demo.msclient.web.dto.PersonRequestDto;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.WebApplicationContext;

import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.AssertionsForInterfaceTypes.assertThat;
import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.RANDOM_PORT;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = RANDOM_PORT, properties = {
        "spring.data.mongodb.host=localhost",
        "spring.data.mongodb.port=27017",
        "spring.data.mongodb.database=microservices",
        "spring.data.mongodb.username=micro",
        "spring.data.mongodb.password=micro",
})
public class PersonControllerTest {

    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate restTemplate;

    @Autowired
    private WebApplicationContext context;

    private MockMvc mvc;

    @Before
    public void setUp(){
        mvc = MockMvcBuilders
                .webAppContextSetup(context)
                .build();
    }

    @Test
    public void findAllTest() throws Exception {
        // when
        String url = "http://localhost:" + port + "/person";

        PersonRequestDto personRequestDto = PersonRequestDto
                .builder()
                .age(10)
                .firstName("lim")
                .lastName("1")
                .build();

        mvc.perform(post(url)
                .contentType(MediaType.APPLICATION_JSON)
                .content(new ObjectMapper().writeValueAsString(personRequestDto)))
                .andExpect(status().isOk());

        ResponseEntity<List<PersonRequestDto>> response =
                restTemplate.exchange("/person", HttpMethod.GET, null, new ParameterizedTypeReference<List<PersonRequestDto>>() {});


        PersonRequestDto expected = PersonRequestDto.builder()
                .age(personRequestDto.getAge())
                .firstName(personRequestDto.getFirstName())
                .lastName(personRequestDto.getLastName())
                .build();

        PersonRequestDto result = response.getBody().stream()
                .filter(it -> it.getAge() == expected.getAge() &&
                        it.getFirstName().equals(expected.getFirstName()) &&
                        it.getLastName().equals(expected.getLastName()))
                .findFirst().orElse(null);

        assertThat(result).isNotNull();
    }
}
