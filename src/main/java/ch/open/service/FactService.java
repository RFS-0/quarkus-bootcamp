package ch.open.service;

import ch.open.dto.FactResult;
import ch.open.dto.NewFact;

import javax.enterprise.context.ApplicationScoped;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.stream.Collectors;

@ApplicationScoped
public class FactService {

    private final List<FactResult> facts = new CopyOnWriteArrayList<>();

    public List<FactResult> getFacts(int limit) {
        if (limit < 1) {
            throw new IllegalArgumentException();
        }
        var copy = new ArrayList<>(facts);
        Collections.reverse(copy);
        return copy.stream().limit(limit).collect(Collectors.toUnmodifiableList());
    }

    public FactResult add(NewFact newFact) {
        var fact = new FactResult(facts.size(), newFact.fact, LocalDateTime.now());
        facts.add(fact);
        return fact;
    }

    public void deleteAll() {
        facts.clear();
    }

    public Optional<FactResult> getFactFor(long id) {
        return facts.stream()
                .filter(fact -> id == fact.id)
                .findAny();
    }
}
