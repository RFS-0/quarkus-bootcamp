package ch.open.service;

import ch.open.dto.FactResult;
import ch.open.dto.NewFact;
import ch.open.repository.Fact;
import ch.open.repository.FactRepository;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@ApplicationScoped
public class FactService {

    @Inject
    FactRepository factRepository;

    public List<FactResult> getFacts(int limit) {
        return factRepository.findLatest(limit).stream()
                .map(FactResult::from)
                .collect(Collectors.toUnmodifiableList());
    }

    @Transactional
    public FactResult add(NewFact newFact) {
        Fact fact = new Fact(newFact.fact, LocalDateTime.now());
        fact.persist();
        return FactResult.from(fact);
    }

    @Transactional
    public void deleteAll() {
        factRepository.deleteAll();
    }

    public Optional<FactResult> getFactFor(long id) {
        return factRepository.findByIdOptional(id)
                .map(FactResult::from);
    }
}
