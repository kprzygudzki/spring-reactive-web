package pl.przygudzki.baeldung.testreactiveweb.application.foo;

import org.junit.jupiter.api.Test;
import reactor.core.publisher.Flux;
import reactor.test.StepVerifier;

import static java.time.Duration.ofSeconds;
import static org.assertj.core.api.Assertions.assertThat;

class FooFacadeTest {

    private final FooFacade facade = new FooFacade();

    @Test
    void shouldCreateFoo() {
        //given

        //when
        Flux<Foo> foos = facade.createFoos();

        //then
        StepVerifier.create(foos)
                .assertNext(foo -> {
                    assertThat(foo.id).isEqualTo(0L);
                    assertThat(foo.name).isEqualTo("Foo number 0");
                })
                .thenCancel().verify(ofSeconds(10));
    }

    @Test
    void shouldCreateFoos() {
        //given

        //when
        Flux<Foo> foos = facade.createFoos();

        //then
        StepVerifier.create(foos)
                .assertNext(foo -> assertThat(foo.id).isEqualTo(0L))
                .assertNext(foo -> {
                    assertThat(foo.id).isEqualTo(1L);
                    assertThat(foo.name).isEqualTo("Foo number 1");
                })
                .expectNextCount(11)
                .assertNext(foo -> assertThat(foo.id).isEqualTo(13L))
                .expectNextCount(21)
                .assertNext(foo -> {
                    assertThat(foo.id).isEqualTo(35L);
                    assertThat(foo.name).isEqualTo("Foo number 35");
                })
                .thenCancel().verify(ofSeconds(10));
    }

}
