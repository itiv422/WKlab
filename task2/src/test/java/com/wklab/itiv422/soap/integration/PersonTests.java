package com.wklab.itiv422.soap.integration;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.client.HttpServerErrorException;
import org.springframework.web.client.RestTemplate;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestPropertySource("/application-test.properties")
public class PersonTests {

    RestTemplate restTemplate;
    HttpEntity<String> entity;
    @LocalServerPort
    private int port;

    @Before
    public void before() {
        restTemplate = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.TEXT_XML);
        entity = new HttpEntity<>(
                "<soapenv:Envelope xmlns:soapenv=\"http://schemas.xmlsoap.org/soap/envelope/\" xmlns:gs=\"http://itiv422.wklab.com/soap/dto\">\n" +
                        "<soapenv:Header/>\n" +
                        "<soapenv:Body>\n" +
                        "\t<gs:getFriendsByPersonAndYearRequest>\n" +
                        "\t\t<gs:personId>1</gs:personId>\n" +
                        "\t\t<gs:year>1991</gs:year>\n" +
                        "\t</gs:getFriendsByPersonAndYearRequest>\n" +
                        "</soapenv:Body>\n" +
                        "</soapenv:Envelope>", headers);
    }

    @Test
    @Sql(value = {"/add-persons.sql"})
    public void getFriendsList() {
        assertThat(restTemplate.exchange(
                "http://localhost:" + port + "/soap", HttpMethod.POST, entity, String.class
        ).getBody()).contains("<ns2:friends><ns2:birthday><ns2:day>1</ns2:day><ns2:month>1</ns2:month><ns2:year>1991</ns2:year></ns2:birthday><ns2:name>Yana</ns2:name></ns2:friends>" +
                "<ns2:friends><ns2:birthday><ns2:day>1</ns2:day><ns2:month>1</ns2:month><ns2:year>1991</ns2:year></ns2:birthday><ns2:name>Max</ns2:name></ns2:friends>");
    }

    @Test
    @Sql(value = {"/clean-tables.sql"})
    public void getEmptyFriendsList() {
        String response = "";
        try {
            restTemplate.exchange(
                    "http://localhost:" + port + "/soap", HttpMethod.POST, entity, String.class
            ).getBody();
        } catch (HttpServerErrorException.InternalServerError ex) {
            response = ex.getResponseBodyAsString();
        }
        assertThat(response).contains("<SOAP-ENV:Fault><faultcode>SOAP-ENV:Client</faultcode><faultstring xml:lang=\"en\">Friend not found</faultstring><detail>");
    }

}
