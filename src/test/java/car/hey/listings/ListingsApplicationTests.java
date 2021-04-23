package car.hey.listings;

import car.hey.listings.model.Listing;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.core.io.FileSystemResource;
import org.springframework.http.*;
import org.springframework.util.LinkedMultiValueMap;

import java.io.IOException;
import java.io.StringWriter;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@Slf4j
class ListingsApplicationTests {

    public static final String LISTINGS_CSV_FILE = "listings.csv";

    @Autowired
    TestRestTemplate template;

    @Test
    void contextLoads() {
    }

    @Test
    @Order(1)
    public void fileShouldBeSuccessfullyUploaded() {
        LinkedMultiValueMap multipart = new LinkedMultiValueMap<>();
        multipart.add("file", new FileSystemResource(Path.of("src", "test",
                "resources",
                LISTINGS_CSV_FILE)));

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.MULTIPART_FORM_DATA);

        ParameterizedTypeReference<List<Listing>> responseType =
                new ParameterizedTypeReference<List<Listing>>() {
                };

        ResponseEntity<List<Listing>> response =
                template.exchange("/upload-csv-file/1",
                        HttpMethod.POST, new HttpEntity<>(multipart, headers)
                        , responseType);

        Assertions.assertEquals(HttpStatus.OK, response.getStatusCode());
    }

    @Test
    @Order(2)
    public void shouldFindTheUploadedListingsBySearching() {

        // look for black cars

        String url = "/search?color=black";

        HttpHeaders headers = new HttpHeaders();

        HttpEntity<?> entity = new HttpEntity<>(headers);

        ParameterizedTypeReference<List<Listing>> responseType =
                new ParameterizedTypeReference<List<Listing>>() {
                };

        ResponseEntity<List<Listing>> response =
                template.exchange(url, HttpMethod.GET, entity, responseType);

        List<Listing> list = response.getBody();

        Assertions.assertEquals(HttpStatus.OK, response.getStatusCode());
        // should find one
        Assertions.assertEquals(1, list.size());

        // look for "skoda" make listings

        url = "/search?make=skoda";

        response =
                template.exchange(url, HttpMethod.GET, entity, responseType);

        list = response.getBody();

        Assertions.assertEquals(HttpStatus.OK, response.getStatusCode());
        // should find 2
        Assertions.assertEquals(2, list.size());

    }

    @Test
    @Order(3)
    public void jsonListingsShouldBeSuccessfullySaved() {

        String url = "vehicle_listings/1";

        List<Listing> listings = new ArrayList<Listing>();

        listings.add(Listing.builder().code("5").dealer(1).model("tt").make(
                "audi").color("brown").price(12000f).powerInHP(124f).year(2008).build());

        listings.add(Listing.builder().code("6").dealer(1).model("quattro").make(
                "audi").color("black").price(32000f).powerInHP(224f).year(2012).build());

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        ObjectMapper mapper = new ObjectMapper();

        final StringWriter sw = new StringWriter();

        try {
            mapper.writeValue(sw, listings);
            HttpEntity<String> entity = new HttpEntity<String>(sw.toString(),
                    headers);

            ParameterizedTypeReference<List<Listing>> responseType =
                    new ParameterizedTypeReference<List<Listing>>() {
                    };

            ResponseEntity<List<Listing>> response =
                    template.exchange("/vehicle_listings/1",
                            HttpMethod.POST, entity
                            , responseType);

            Assertions.assertEquals(HttpStatus.OK, response.getStatusCode());

        } catch (IOException e) {
            log.error("Could not convert list to json during tests", e);
        }

    }

    @Test
    @Order(4)
    public void shouldFindTheUploadedJSONListingsBySearching() {

        // look for audis, should find 3 of them (with the latest 2 additions
        // from json endpoint

        String url = "/search?make=audi";

        HttpHeaders headers = new HttpHeaders();

        HttpEntity<?> entity = new HttpEntity<>(headers);

        ParameterizedTypeReference<List<Listing>> responseType =
                new ParameterizedTypeReference<List<Listing>>() {
                };

        ResponseEntity<List<Listing>> response =
                template.exchange(url, HttpMethod.GET, entity, responseType);

        List<Listing> list = response.getBody();

        Assertions.assertEquals(HttpStatus.OK, response.getStatusCode());
        // should find one
        Assertions.assertEquals(3, list.size());

    }
}
