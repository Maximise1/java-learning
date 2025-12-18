import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import model.Student;
import model.Book;

public class FileReader {
    public static List<Student> readStudents(Path path) {
        try {
            List<Student> result = new ArrayList<>();
            List<String> lines = Files.readAllLines(path);

            String currentName = null;
            List<Book> books = new ArrayList<>();

            for (String line : lines) {
                if (line.isBlank()) continue;

                if (line.startsWith("Student:")) {
                    if (currentName != null) {
                        result.add(new Student(currentName, List.copyOf(books)));
                        books.clear();
                    }
                    currentName = line.substring(8);
                } else {
                    String[] p = line.split(",");
                    books.add(new Book(p[0], Integer.parseInt(p[1]), Integer.parseInt(p[2])));
                }
            }

            if (currentName != null) {
                result.add(new Student(currentName, List.copyOf(books)));
            }

            return result;
        } catch (IOException e) {
            System.out.println("Error: File not found.");
            System.out.println(e.getMessage());
            return List.of();
        }
    }
}
