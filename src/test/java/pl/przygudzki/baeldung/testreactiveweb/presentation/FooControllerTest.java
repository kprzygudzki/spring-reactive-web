package pl.przygudzki.baeldung.testreactiveweb.presentation;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import pl.przygudzki.MockitoExtension;
import pl.przygudzki.baeldung.testreactiveweb.application.foo.Foo;
import pl.przygudzki.baeldung.testreactiveweb.application.foo.FooFacade;
import reactor.core.publisher.Flux;
import reactor.test.StepVerifier;
import reactor.test.scheduler.VirtualTimeScheduler;

import static java.time.Duration.ofMillis;
import static java.time.Duration.ofSeconds;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class FooControllerTest {

    @Mock
    private FooFacade facade;

    private final VirtualTimeScheduler timer = VirtualTimeScheduler.create();

    private FooController fooController;

    @BeforeEach
    void setUp() {
        fooController = new FooController(facade, timer);
    }

    @Test
    void shouldReturnFoosInIntervalsOf1000Millis() {
        //given
        when(facade.createFoos()).thenReturn(Flux.range(0, 10).map(i -> Foo.create((long) i)));

        //when
        Flux<Foo> foos = fooController.getFoos();

        //then
        StepVerifier.withVirtualTime(() -> foos, () -> timer, 4L)
                .expectSubscription()
                .expectNoEvent(ofMillis(1000))
                .expectNextCount(1)
                .expectNoEvent(ofMillis(1000))
                .expectNextCount(1)
                .expectNoEvent(ofMillis(1000))
                .expectNextCount(1)
                .expectNoEvent(ofMillis(1000))
                .expectNextCount(1)
                .thenCancel()
                .verify(ofSeconds(3));
    }

}