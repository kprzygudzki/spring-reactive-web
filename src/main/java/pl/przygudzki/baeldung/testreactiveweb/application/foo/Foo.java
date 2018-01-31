package pl.przygudzki.baeldung.testreactiveweb.application.foo;

public final class Foo {

    public final Long id;
    public final String name;

    private Foo(Long id, String name) {
        this.id = id;
        this.name = name;
    }

    public static Foo create(Long id) {
        return new Foo(id, String.format("Foo number %s", id));
    }

}
