package ch.open.resource;

import ch.open.dto.FactResult;
import ch.open.dto.NewFact;
import ch.open.service.FactService;
import io.quarkus.test.common.http.TestHTTPEndpoint;
import io.quarkus.test.junit.QuarkusTest;
import io.restassured.common.mapper.TypeRef;
import io.restassured.http.ContentType;
import org.apache.http.HttpStatus;
import org.junit.jupiter.api.Test;

import javax.inject.Inject;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

import static io.restassured.RestAssured.given;
import static org.assertj.core.api.Assertions.assertThat;

@QuarkusTest
@TestHTTPEndpoint(FactResource.class)
class FactResourceTest {

    @Inject
    FactService factService;

    @Test
    void getFacts_withLimit_serviceIsCalledWithGivenLimit() {
        // given
        factService.add(new NewFact("Fact 1"));
        factService.add(new NewFact("Fact 2"));
        factService.add(new NewFact("Fact 3"));
        factService.add(new NewFact("Fact 4"));

        var limit = 3;

        // when
        var facts = given()
                .queryParam("limit", limit)
                .when()
                .get()
                .then()
                .statusCode(HttpStatus.SC_OK)
                .extract()
                .as(new TypeRef<List<FactResult>>() {
                });

        // then
        assertThat(facts).hasSize(limit);

        var actualFacts = facts.stream().map(fact -> fact.fact).collect(Collectors.toUnmodifiableList());
        assertThat(actualFacts).containsExactly("Fact 4", "Fact 3", "Fact 2");
    }

    @Test
    void getFactForId_nonExistingId_statusNotFound() {
        // given
        var nonExistingId = Long.MIN_VALUE;

        // when + then
        given()
                .when()
                .get(Long.toString(nonExistingId))
                .then()
                .statusCode(HttpStatus.SC_NOT_FOUND);
    }

    @Test
    void getFactForId_existingId_correctResult() {
        // given
        var fact = new NewFact("Fact 1");

        var factResult = given()
                .body(fact)
                .contentType(ContentType.JSON)
                .when()
                .post()
                .then()
                .statusCode(HttpStatus.SC_CREATED)
                .extract()
                .as(FactResult.class);

        // when
        var response = given()
                .when()
                .get(Long.toString(factResult.id))
                .then()
                .statusCode(HttpStatus.SC_OK)
                .extract()
                .as(FactResult.class);

        // then
        assertThat(response).isEqualTo(factResult);
    }

    @Test
    void addFact_statusCreatedWithCorrectResponseBody() {
        // given
        var fact = new NewFact("Fact 1");

        // when + then
        var result = given()
                .body(fact)
                .contentType(ContentType.JSON)
                .when()
                .post()
                .then()
                .statusCode(HttpStatus.SC_CREATED)
                .extract()
                .as(FactResult.class);

        assertThat(result.fact).isEqualTo("Fact 1");
        assertThat(result.timestamp).isBeforeOrEqualTo(LocalDateTime.now());
    }

    @Test
    void getFacts_statusOk() {
        given()
                .when()
                .get()
                .then()
                .statusCode(HttpStatus.SC_OK);
    }
}