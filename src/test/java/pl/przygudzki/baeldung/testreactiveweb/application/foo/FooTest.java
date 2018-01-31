package pl.przygudzki.baeldung.testreactiveweb.application.foo;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class FooTest {

    @Test
    void shouldCreateFoo() {
        //given
        Long id = 123L;

        //when
        Foo foo = Foo.create(id);

        //then
        assertThat(foo).isNotNull();
        assertThat(foo.id).isEqualTo(id);
        assertThat(foo.name).isEqualTo("Foo number 123");
    }

}
