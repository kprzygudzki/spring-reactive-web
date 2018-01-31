package pl.przygudzki.baeldung.testreactiveweb.application.foo;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import reactor.core.scheduler.Scheduler;
import reactor.core.scheduler.Schedulers;

@Configuration
public class FooConfiguration {

    @Bean
    FooFacade fooFacade() {
        return new FooFacade();
    }

    @Bean
    Scheduler timer() {
        return Schedulers.single();
    }

}
