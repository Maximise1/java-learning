import java.nio.file.Path;
import java.util.Comparator;
import java.util.List;

import model.Book;
import model.Student;

public class StudentTask {
    public static void main(String[] args) {
        List<Student> students = FileReader.readStudents(Path.of("students.txt"));

        students.stream()
                .peek(System.out::println)
                .flatMap(s -> s.books().stream())
                .sorted(Comparator.comparingInt(Book::pages))
                .distinct()
                .filter(b -> b.year() > 2000)
                .limit(3)
                .map(Book::year)
                .findFirst()
                .ifPresentOrElse(
                    y -> System.out.println("Год выпуска найденной книги: " + y),
                    () -> System.out.println("Книга не найдена")
                );
    }
}
