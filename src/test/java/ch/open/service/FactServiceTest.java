package ch.open.service;

import ch.open.dto.NewFact;
import io.quarkus.test.TestTransaction;
import io.quarkus.test.junit.QuarkusTest;
import org.junit.jupiter.api.Test;

import javax.inject.Inject;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@QuarkusTest
@TestTransaction
class FactServiceTest {

    @Inject
    FactService factService;

    @Test
    void getFacts_limitAboveNumberOfFacts() {
        // given
        initFacts(3);

        // when
        var result = factService.getFacts(5);

        // then
        assertThat(result).hasSize(3);
    }

    @Test
    void getFacts_limitEqualsNumberOfFacts() {
        // given
        initFacts(3);

        // when
        var result = factService.getFacts(3);

        // then
        assertThat(result).hasSize(3);
    }

    @Test
    void getFacts_limitZero() {
        // given
        initFacts(3);

        // when + then
        assertThatThrownBy(() -> factService.getFacts(0))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    void getFacts_limitBelowNumberOfFacts() {
        // given
        factService.add(fact("Fact 1"));
        var result2 = factService.add(fact("Fact 2"));
        var result3 = factService.add(fact("Fact 3"));

        // when
        var result = factService.getFacts(2);

        // then
        assertThat(result).hasSize(2);
        assertThat(result).contains(result2, result3);
    }

    @Test
    void getFacts_noFacts_emptyResult() {
        // given
        initFacts(0);

        // when
        var result = factService.getFacts(2);

        // then
        assertThat(result).isEmpty();
    }

    @Test
    void getFacts_negativeLimit_emptyResult() {
        // given
        initFacts(7);

        // when + then
        assertThatThrownBy(() -> factService.getFacts(-1))
                .isInstanceOf(IllegalArgumentException.class);
    }

    private void initFacts(int num) {
        factService.deleteAll();
        for (int i = 1; i <= num; i++) {
            factService.add(fact("Fact " + i));
        }
    }

    private NewFact fact(String fact) {
        return new NewFact(fact);
    }
}
