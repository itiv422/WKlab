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
public class TeacherTests {
    @LocalServerPort
    private int port;

    @Test
    @Sql(value = {"/add-teacher-with-lesson.sql"})
    public void getAllTeachersInXML() {
        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();
        headers.setAccept(Collections.singletonList(MediaType.TEXT_XML));
        HttpEntity<String> entity = new HttpEntity<>(headers);
        assertThat(
                restTemplate.exchange(
                        "http://localhost:" + port + "/teacher", HttpMethod.GET, entity, String.class
                ).getBody()).contains("<content><name>Jon</name><birthday><day>10</day><month>4</month><year>1988</year></birthday><id>1</id><lessons><lessons><name>math</name><duration>45</duration><id>2</id></lessons></lessons></content>");
    }

    @Test
    @Sql(value = {"/add-teacher-with-lesson.sql"})
    public void getAllTeachersInJSON() {
        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();
        headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
        HttpEntity<String> entity = new HttpEntity<>(headers);
        assertThat(restTemplate.exchange(
                "http://localhost:" + port + "/teacher", HttpMethod.GET, entity, String.class
        ).getBody()).contains("[{\"name\":\"Jon\",\"birthday\":{\"day\":10,\"month\":4,\"year\":1988},\"id\":1,\"lessons\":[{\"name\":\"math\",\"duration\":45,\"id\":2}]}]");
    }

    @Test
    @Sql(value = {"/add-teacher-with-lesson.sql"})
    public void getTeacherByIdInXML() {
        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();
        headers.setAccept(Collections.singletonList(MediaType.TEXT_XML));
        HttpEntity<String> entity = new HttpEntity<>(headers);
        assertThat(
                restTemplate.exchange(
                        "http://localhost:" + port + "/teacher/1", HttpMethod.GET, entity, String.class
                ).getBody()).contains("<name>Jon</name><birthday><day>10</day><month>4</month><year>1988</year></birthday><id>1</id><lessons><lessons><name>math</name><duration>45</duration><id>2</id></lessons></lessons>");
    }

    @Test
    @Sql(value = {"/add-teacher-with-lesson.sql"})
    public void getTeacherByIdInJSON() {
        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();
        headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
        HttpEntity<String> entity = new HttpEntity<>(headers);
        assertThat(
                restTemplate.exchange(
                        "http://localhost:" + port + "/teacher/1", HttpMethod.GET, entity, String.class
                ).getBody()).contains("{\"name\":\"Jon\",\"birthday\":{\"day\":10,\"month\":4,\"year\":1988},\"id\":1,\"lessons\":[{\"name\":\"math\",\"duration\":45,\"id\":2}]");
    }

    @Test
    @Sql(value = {"/add-teacher-with-lesson.sql"})
    public void addTeacherInJSON() {
        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();
        headers.setAccept(Collections.singletonList(MediaType.TEXT_XML));
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<String> entity = new HttpEntity<>("{\"name\":\"Kate\",\"birthday\":{\"day\":30,\"month\":12,\"year\":2000}}", headers);
        assertThat(restTemplate.exchange(
                "http://localhost:" + port + "/teacher", HttpMethod.POST, entity, String.class
        ).getBody()).contains("<Teacher><name>Kate</name><birthday><day>30</day><month>12</month><year>2000</year></birthday><id>3</id><lessons/></Teacher>");
    }

    @Test
    @Sql(value = {"/add-teacher-with-lesson.sql"})
    public void addTeacherInXML() {
        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();
        headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
        headers.setContentType(MediaType.TEXT_XML);
        HttpEntity<String> entity = new HttpEntity<>("<Teacher><name>Kate</name><birthday><day>30</day><month>12</month><year>2000</year></birthday></Teacher>", headers);
        assertThat(restTemplate.exchange(
                "http://localhost:" + port + "/teacher", HttpMethod.POST, entity, String.class
        ).getBody()).contains("{\"name\":\"Kate\",\"birthday\":{\"day\":30,\"month\":12,\"year\":2000},\"id\":3,\"lessons\":[]}");
    }

    @Test
    @Sql(value = {"/add-teacher-with-lesson.sql"})
    public void delTeacherById() {
        RestTemplate restTemplate = new RestTemplate();
        HttpEntity<String> entity = new HttpEntity<>("");
        assertThat(
                restTemplate.exchange(
                        "http://localhost:" + port + "/teacher/1", HttpMethod.DELETE, entity, String.class
                ).getStatusCodeValue()).isEqualTo(200);
    }

    @Test
    @Sql(value = {"/add-teacher-with-lesson.sql"})
    public void updateTeacherByIdInXML() {
        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();
        headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
        headers.setContentType(MediaType.TEXT_XML);
        HttpEntity<String> entity = new HttpEntity<>("<Teacher><name>Kate</name><birthday><day>30</day><month>12</month><year>2000</year></birthday></Teacher>", headers);
        assertThat(restTemplate.exchange(
                "http://localhost:" + port + "/teacher/1", HttpMethod.PUT, entity, String.class
        ).getBody()).contains("{\"name\":\"Kate\",\"birthday\":{\"day\":30,\"month\":12,\"year\":2000},\"id\":1,\"lessons\":[{\"name\":\"math\",\"duration\":45,\"id\":2}]}");
    }

    @Test
    @Sql(value = {"/add-teacher-with-lesson.sql"})
    public void updateTeacherByIdInJSON() {
        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();
        headers.setAccept(Collections.singletonList(MediaType.TEXT_XML));
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<String> entity = new HttpEntity<>("{\"name\":\"Kate\",\"birthday\":{\"day\":30,\"month\":12,\"year\":2000}}", headers);
        assertThat(restTemplate.exchange(
                "http://localhost:" + port + "/teacher/1", HttpMethod.PUT, entity, String.class
        ).getBody()).contains("<Teacher><name>Kate</name><birthday><day>30</day><month>12</month><year>2000</year></birthday><id>1</id><lessons><lessons><name>math</name><duration>45</duration><id>2</id></lessons></lessons></Teacher>");
    }

    @Test
    @Sql(value = {"/add-teachers-with-lessons.sql"})
    public void getBusiestTeacherInJSON() {
        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();
        headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
        HttpEntity<String> entity = new HttpEntity<>(headers);
        assertThat(
                restTemplate.exchange(
                        "http://localhost:" + port + "/teacher/filter/busiestTeacher", HttpMethod.GET, entity, String.class
                ).getBody()).contains("{\"name\":\"Mira\",\"birthday\":{\"day\":3,\"month\":3,\"year\":1977},\"id\":3,\"lessons\":[{\"name\":\"math\",\"duration\":60,\"id\":7},{\"name\":\"physics\",\"duration\":60,\"id\":8}]}");
    }

    @Test
    @Sql(value = {"/add-teachers-with-lessons.sql"})
    public void getBusiestTeacherInXML() {
        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();
        headers.setAccept(Collections.singletonList(MediaType.TEXT_XML));
        HttpEntity<String> entity = new HttpEntity<>(headers);
        assertThat(
                restTemplate.exchange(
                        "http://localhost:" + port + "/teacher/filter/busiestTeacher", HttpMethod.GET, entity, String.class
                ).getBody()).contains("<Teacher><name>Mira</name><birthday><day>3</day><month>3</month><year>1977</year></birthday><id>3</id><lessons><lessons><name>math</name><duration>60</duration><id>7</id></lessons><lessons><name>physics</name><duration>60</duration><id>8</id></lessons></lessons></Teacher>");
    }

    @Test(expected = HttpClientErrorException.NotFound.class)
    @Sql(value = {"/clean-tables.sql"})
    public void getNonExistentTeacher() {
        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();
        headers.setAccept(Collections.singletonList(MediaType.TEXT_XML));
        HttpEntity<String> entity = new HttpEntity<>(headers);
        restTemplate.exchange(
                "http://localhost:" + port + "/teacher/1", HttpMethod.GET, entity, String.class
        ).getBody();
    }

    @Test(expected = HttpClientErrorException.NotFound.class)
    @Sql(value = {"/clean-tables.sql"})
    public void delNonExistentTeacherById() {
        RestTemplate restTemplate = new RestTemplate();
        HttpEntity<String> entity = new HttpEntity<>("");
        restTemplate.exchange(
                "http://localhost:" + port + "/teacher/1", HttpMethod.DELETE, entity, String.class
        ).getStatusCodeValue();
    }

    @Test
    @Sql(value = {"/add-teacher-with-lesson.sql"})
    public void updateNonExistentTeacherById() {
        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();
        headers.setAccept(Collections.singletonList(MediaType.TEXT_XML));
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<String> entity = new HttpEntity<>("{\"name\":\"Kate\",\"birthday\":{\"day\":30,\"month\":12,\"year\":2000}}", headers);
        restTemplate.exchange(
                "http://localhost:" + port + "/teacher/1", HttpMethod.PUT, entity, String.class
        );
    }

    @Test
    @Sql(value = {"/add-teacher-with-lesson.sql"})
    public void notModifiedUpdateTeacherById() {
        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();
        headers.setAccept(Collections.singletonList(MediaType.TEXT_XML));
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<String> entity = new HttpEntity<>("{\"name\":\"Jon\",\"birthday\":{\"day\":10,\"month\":4,\"year\":1988}}", headers);
        assertThat(restTemplate.exchange(
                "http://localhost:" + port + "/teacher/1", HttpMethod.PUT, entity, String.class
        ).getStatusCode()).isEqualTo(HttpStatus.NOT_MODIFIED);
    }
}
