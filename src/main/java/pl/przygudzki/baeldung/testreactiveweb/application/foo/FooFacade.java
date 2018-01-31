package pl.przygudzki.baeldung.testreactiveweb.application.foo;

import reactor.core.publisher.Flux;

public class FooFacade {

    public Flux<Foo> createFoos() {
        return Flux.generate(
                () -> 0L,
                (state, sink) -> {
                    Foo foo = Foo.create(state);
                    sink.next(foo);
                    return (state + 1);
                });
    }

}
