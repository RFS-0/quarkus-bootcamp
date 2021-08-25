package ch.open.repository;

import io.quarkus.hibernate.orm.panache.PanacheRepository;
import io.quarkus.panache.common.Page;
import io.quarkus.panache.common.Sort;

import javax.enterprise.context.ApplicationScoped;
import java.util.List;

@ApplicationScoped
public class FactRepository implements PanacheRepository<Fact> {

    public List<Fact> findLatest(int limit) {
        return findAll(Sort.descending("timestamp"))
            .page(Page.ofSize(limit))
            .list();
    }
}
