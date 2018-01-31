package pl.przygudzki.baeldung.testreactiveweb.presentation;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import pl.przygudzki.baeldung.testreactiveweb.application.foo.Foo;
import pl.przygudzki.baeldung.testreactiveweb.application.foo.FooFacade;
import reactor.core.publisher.Flux;
import reactor.core.scheduler.Scheduler;

import static java.time.Duration.ofMillis;
import static org.springframework.http.MediaType.APPLICATION_STREAM_JSON_VALUE;

@RestController
class FooController {

    private final FooFacade facade;
    private final Scheduler timer;

    public FooController(FooFacade facade, Scheduler timer) {
        this.facade = facade;
        this.timer = timer;
    }

    @GetMapping(path = "/foos", produces = APPLICATION_STREAM_JSON_VALUE)
    Flux<Foo> getFoos() {
        return facade.createFoos()
                .delayElements(ofMillis(1000), timer);
    }

}
