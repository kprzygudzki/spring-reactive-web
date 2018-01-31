package pl.przygudzki.baeldung.testreactiveweb.integration;

import lombok.Data;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;
import reactor.test.StepVerifier;

import static java.time.Duration.ofSeconds;
import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.http.MediaType.APPLICATION_STREAM_JSON;

@SpringBootTest
@ExtendWith(SpringExtension.class)
class FooIntegrationTest {

    private final WebClient client = WebClient.create("http://localhost:8080");

    @Test
    void shouldStreamFoos() {
        //given

        //when
        Flux<FooDto> foos = client.get()
                .uri("/foos")
                .accept(APPLICATION_STREAM_JSON)
                .retrieve()
                .bodyToFlux(FooDto.class)
                .take(10);

        //then
        StepVerifier.create(foos)
                .assertNext(foo -> {
                    assertThat(foo.getId()).isEqualTo(0);
                    assertThat(foo.getName()).isEqualTo("Foo number 0");
                })
                .expectNextCount(2)
                .assertNext(foo -> {
                    assertThat(foo.getId()).isEqualTo(3);
                    assertThat(foo.getName()).isEqualTo("Foo number 3");
                })
                .thenCancel()
                .verify(ofSeconds(10));
    }

    @Data
    private static class FooDto {
        public Long id;
        public String name;
    }

}
