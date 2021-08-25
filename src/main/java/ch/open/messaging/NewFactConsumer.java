package ch.open.messaging;

import ch.open.dto.NewFact;
import ch.open.service.FactService;
import io.smallrye.reactive.messaging.annotations.Blocking;
import org.eclipse.microprofile.reactive.messaging.Incoming;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;

@ApplicationScoped
public class NewFactConsumer {

    private static final Logger log = LoggerFactory.getLogger(NewFactConsumer.class);

    public static final String FACTS_IN = "facts-in";

    @Inject
    FactService factService;

    @Incoming(FACTS_IN)
    @Blocking
    public void process(NewFact fact) {
        log.info("Incoming Kafka fact: {}", fact);
        factService.add(fact);
    }
}
