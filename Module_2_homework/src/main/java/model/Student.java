package model;

import java.util.List;
import java.util.stream.Collectors;

public record Student(
        String name,
        List<Book> books
) {
    @Override
    public String toString() {
        return name + " прочитал " + books.stream()
                .map(Book::title)
                .collect(Collectors.joining(", "));
    }
}
