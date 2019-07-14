package wklab.Task1Rest.integration;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.*;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import java.util.Collections;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestPropertySource("/application-test.properties")
public class LessonTest {
    @LocalServerPort
    private int port;

    @Test
    @Sql(value = {"/add-teacher-with-lesson.sql"})
    public void getLessonByIdInXML() {
        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();
        headers.setAccept(Collections.singletonList(MediaType.TEXT_XML));
        HttpEntity<String> entity = new HttpEntity<>(headers);
        assertThat(
                restTemplate.exchange(
                        "http://localhost:" + port + "/lesson/2", HttpMethod.GET, entity, String.class
                ).getBody()).contains("<name>math</name><duration>45</duration><id>2</id>");
    }

    @Test
    @Sql(value = {"/add-teacher-with-lesson.sql"})
    public void getLessonByIdInJSON() {
        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();
        headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
        HttpEntity<String> entity = new HttpEntity<>("body", headers);
        assertThat(restTemplate.exchange(
                "http://localhost:" + port + "/lesson/2", HttpMethod.GET, entity, String.class
        ).getBody()).contains("{\"name\":\"math\",\"duration\":45,\"id\":2}");
    }

    @Test
    @Sql(value = {"/add-teacher-with-lesson.sql"})
    public void getAllLessonsInXML() {
        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();
        headers.setAccept(Collections.singletonList(MediaType.TEXT_XML));
        HttpEntity<String> entity = new HttpEntity<>(headers);
        assertThat(
                restTemplate.exchange(
                        "http://localhost:" + port + "/lesson", HttpMethod.GET, entity, String.class
                ).getBody()).contains("<content><name>math</name><duration>45</duration><id>2</id></content>");
    }

    @Test
    @Sql(value = {"/add-teacher-with-lesson.sql"})
    public void getAllLessonsInJSON() {
        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();
        headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
        HttpEntity<String> entity = new HttpEntity<>(headers);
        assertThat(
                restTemplate.exchange(
                        "http://localhost:" + port + "/lesson", HttpMethod.GET, entity, String.class
                ).getBody()).contains("[{\"name\":\"math\",\"duration\":45,\"id\":2}]");
    }

    @Test
    @Sql(value = {"/add-teacher-with-lesson.sql"})
    public void addLessonInJSON() {
        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();
        headers.setAccept(Collections.singletonList(MediaType.TEXT_XML));
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<String> entity = new HttpEntity<>("{\"name\":\"physics\",\"duration\":45,\"teacherId\":1}", headers);
        assertThat(restTemplate.exchange(
                "http://localhost:" + port + "/lesson", HttpMethod.POST, entity, String.class
        ).getBody()).contains("<Lesson><name>physics</name><duration>45</duration><id>3</id></Lesson>");
    }

    @Test
    @Sql(value = {"/add-teacher-with-lesson.sql"})
    public void addLessonInXML() {
        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();
        headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
        headers.setContentType(MediaType.TEXT_XML);
        HttpEntity<String> entity = new HttpEntity<>("<Lesson><name>math2</name><duration>45</duration><teacherId>1</teacherId></Lesson>", headers);
        assertThat(restTemplate.exchange(
                "http://localhost:" + port + "/lesson", HttpMethod.POST, entity, String.class
        ).getBody()).contains("{\"name\":\"math2\",\"duration\":45,\"id\":3}");
    }

    @Test
    @Sql(value = {"/add-teacher-with-lesson.sql"})
    public void delLessonById() {
        RestTemplate restTemplate = new RestTemplate();
        HttpEntity<String> entity = new HttpEntity<>("");
        assertThat(
                restTemplate.exchange(
                        "http://localhost:" + port + "/lesson/2", HttpMethod.DELETE, entity, String.class
                ).getStatusCodeValue()).isEqualTo(200);
    }

    @Test
    @Sql(value = {"/add-teacher-with-lesson.sql"})
    public void updateLessonByIdInXML() {
        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();
        headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
        headers.setContentType(MediaType.TEXT_XML);
        HttpEntity<String> entity = new HttpEntity<>("<Lesson><name>physics</name><duration>60</duration><teacherId>1</teacherId></Lesson>", headers);
        assertThat(restTemplate.exchange(
                "http://localhost:" + port + "/lesson/2", HttpMethod.PUT, entity, String.class
        ).getBody()).contains("{\"name\":\"physics\",\"duration\":60,\"id\":2}");
    }

    @Test
    @Sql(value = {"/add-teacher-with-lesson.sql"})
    public void updateLessonByIdInJSON() {
        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();
        headers.setAccept(Collections.singletonList(MediaType.TEXT_XML));
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<String> entity = new HttpEntity<>("{\"name\":\"physics\",\"duration\":60,\"teacherId\":1}", headers);
        assertThat(restTemplate.exchange(
                "http://localhost:" + port + "/lesson/2", HttpMethod.PUT, entity, String.class
        ).getBody()).contains("<Lesson><name>physics</name><duration>60</duration><id>2</id></Lesson>");
    }


    @Test(expected = HttpClientErrorException.NotFound.class)
    @Sql(value = {"/clean-tables.sql"})
    public void getNonExistentLessonById() {
        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();
        headers.setAccept(Collections.singletonList(MediaType.TEXT_XML));
        HttpEntity<String> entity = new HttpEntity<>(headers);
        restTemplate.exchange(
                "http://localhost:" + port + "/lesson/2", HttpMethod.GET, entity, String.class
        ).getStatusCodeValue();
    }

    @Test(expected = HttpClientErrorException.NotFound.class)
    @Sql(value = {"/clean-tables.sql"})
    public void updateNonExistentLessonById() {
        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();
        headers.setAccept(Collections.singletonList(MediaType.TEXT_XML));
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<String> entity = new HttpEntity<>("{\"name\":\"physics\",\"duration\":60,\"teacherId\":1}", headers);
        restTemplate.exchange(
                "http://localhost:" + port + "/lesson/2", HttpMethod.PUT, entity, String.class
        );
    }

    @Test(expected = HttpClientErrorException.NotFound.class)
    @Sql(value = {"/clean-tables.sql"})
    public void DelNonExistentLessonById() {
        RestTemplate restTemplate = new RestTemplate();
        HttpEntity<String> entity = new HttpEntity<>("");
        restTemplate.exchange(
                "http://localhost:" + port + "/lesson/2", HttpMethod.DELETE, entity, String.class
        ).getStatusCodeValue();
    }

    @Test(expected = HttpClientErrorException.NotFound.class)
    @Sql(value = {"/clean-tables.sql"})
    public void addLessonWithNonExistentTeacher() {
        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();
        headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
        headers.setContentType(MediaType.TEXT_XML);
        HttpEntity<String> entity = new HttpEntity<>("<Lesson><name>math2</name><duration>45</duration><teacherId>1</teacherId></Lesson>", headers);
        assertThat(restTemplate.exchange(
                "http://localhost:" + port + "/lesson", HttpMethod.POST, entity, String.class
        ).getBody()).contains("{\"name\":\"math2\",\"duration\":45,\"id\":3}");
    }

    @Test
    @Sql(value = {"/add-teacher-with-lesson.sql"})
    public void notModifiedUpdateLessonById() {
        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();
        headers.setAccept(Collections.singletonList(MediaType.TEXT_XML));
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<String> entity = new HttpEntity<>("{\"name\":\"math\",\"duration\":45,\"teacherId\":1}", headers);
        assertThat(restTemplate.exchange(
                "http://localhost:" + port + "/lesson/2", HttpMethod.PUT, entity, String.class
        ).getStatusCode()).isEqualTo(HttpStatus.NOT_MODIFIED);
    }
}
