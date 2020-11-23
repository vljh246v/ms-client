package me.demo.msclient.web;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import me.demo.msclient.web.dto.PersonRequestDto;
import me.demo.msclient.web.dto.PersonResponseDto;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.RANDOM_PORT;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = RANDOM_PORT)
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
                .id(1L)
                .age(10)
                .firstName("lim")
                .lastName("1")
                .build();

        mvc.perform(post(url)
                .contentType(MediaType.APPLICATION_JSON)
                .content(new ObjectMapper().writeValueAsString(personRequestDto)))
                .andExpect(status().isOk());

        List<PersonRequestDto> body = this.restTemplate.getForObject("/person", List.class);

        PersonRequestDto expected = PersonRequestDto.builder()
                .id(personRequestDto.getId())
                .age(personRequestDto.getAge())
                .firstName(personRequestDto.getFirstName())
                .lastName(personRequestDto.getLastName())
                .build();
        ObjectMapper objectMapper = new ObjectMapper();

        assertEquals(objectMapper.writeValueAsString(body), objectMapper.writeValueAsString(Arrays.asList(expected)));
    }
}
